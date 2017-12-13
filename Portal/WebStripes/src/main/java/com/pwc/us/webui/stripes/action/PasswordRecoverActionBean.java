package com.pwc.us.webui.stripes.action;

import com.pwc.us.common.exception.CsoAuthException;
import com.pwc.us.common.exception.CsoPasswordRecoveryException;
import com.pwc.us.common.model.PasswordRecover;
import com.pwc.us.common.utils.FilterUtils;
import freemarker.template.utility.StringUtil;
import java.io.UnsupportedEncodingException;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.LocalizableMessage;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.validation.LocalizableError;
import net.sourceforge.stripes.validation.SimpleError;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidationErrors;
import net.sourceforge.stripes.validation.ValidationMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Roger Turnau - <roger.turnau@us.pwc.com>
 */
public class PasswordRecoverActionBean extends BaseActionBean {

    public static String VIEW = "/WEB-INF/jsp/user/passwordRecoverChallenge.jsp";
    public static String MAIL_SENT_VIEW = "/WEB-INF/jsp/user/passwordRecoverMailSent.jsp";
    public static String CHANGE_PASSWORD_VIEW = "/WEB-INF/jsp/user/changePassword.jsp";
    private static Logger logger = LoggerFactory.getLogger(PasswordRecoverActionBean.class);
    private static final String RECOVERY_INFO = "recoveryInfo";
    private static final String USER_NOT_FOUND = "pwrecover.error.userNotFound";
    private static final String CSO_AUTH_EXCEPTION_ERROR = "pwrecover.error.authError";
    private static final String ANSWER_ERROR = "pwrecover.error.answerError";
    private static final String MAIL_SENT = "pwrecover.mailSent";
    private static final String PASSWORD_CHANGED = "pwrecover.passwordChanged";
    private static final String CSO_EMAIL_CREATION_ERROR = "pwrecover.error.emailError";
    private static final String UNABLE_TO_CREATE_EMAIL_URL = "pwrecover.unableToCreateUrl";
    private static final String EMAIL_TOKENS_DO_NOT_MATCH = "pwrecover.error.tokensNotMatching";
    private static final String PASSWORDS_MUST_MATCH = "validation.required.passwordsMustMatch";
    private static final String UNABLE_TO_CHANGE_PWD = "pwrecover.error.unableToChangePwd";
    private static final String PASSWORD_TOO_SHORT = "pwrecover.error.passwordTooShort";
    private static final String PASSWORD_TOO_LONG = "pwrecover.error.passwordTooLong";
    private static final String PASSWORD_BAD_CHARS = "pwrecover.error.passwordBadChars";
    private static final String PASSWORD_EXPRESSION = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$";
    private static final int MIN_PASSWORD_LENGTH = 8;
    private static final int MAX_PASSWORD_LENGTH = 32;
    private static final String UTF8 = "UTF-8";
    private static final String LOGIN = "login";
    private PasswordRecover recover = new PasswordRecover();
    private String testAnswer;
    private String login;
    private String token;
    private String testPassword1;
    private String testPassword2;

    @DefaultHandler
    public Resolution passwordRecoverForm() {
        return new ForwardResolution(VIEW);
    }

    public Resolution setUsername() {
        ValidationErrors errors = this.getContext().getValidationErrors();
        try {
            logger.info("Password Recovery Request for user " + recover.getUsername());
            this.recover = api.getPasswordRecoveryInfo(recover.getUsername());
            if (this.recover == null) {
                errors.addGlobalError(new LocalizableError(USER_NOT_FOUND));
            } else {
                this.getContext().getRequest().getSession().setAttribute(RECOVERY_INFO, recover);
            }
        } catch (CsoAuthException e) {
            logger.info("CsoAuthException while trying to recover password for user " + recover.getUsername(), e);
            errors.addGlobalError(new LocalizableError(CSO_AUTH_EXCEPTION_ERROR));
            this.recover = null;
        }
        return new ForwardResolution(VIEW);
    }

    public Resolution testAnswer() {
        ValidationErrors errors = this.getContext().getValidationErrors();
        this.setRecover((PasswordRecover) this.getContext().getRequest().getSession().getAttribute(RECOVERY_INFO));
        String test = this.getTestAnswer().trim();
        String answer = recover.getAnswer().trim();
        if (answer.compareToIgnoreCase(test) == 0) {
            logger.info("Password recovery answer success for user " + recover.getUsername());
            StringBuffer path = this.getContext().getRequest().getRequestURL();
            recover.setHost(path);
            try {
                api.sendPasswordRecoveryEmail(recover);
            } catch (CsoAuthException e) {
                logger.info("CsoAuthException while trying to recover password for user " + recover.getUsername(), e);
                errors.addGlobalError(new LocalizableError(CSO_AUTH_EXCEPTION_ERROR));
            } catch (CsoPasswordRecoveryException e) {
                logger.info("CsoPasswordRecoveryException while trying to recover password for user " + recover.getUsername(), e);
                errors.addGlobalError(new LocalizableError(CSO_AUTH_EXCEPTION_ERROR));
            }

            if (errors.isEmpty()) {
                getContext().getMessages().add(new LocalizableMessage(MAIL_SENT));
            }


        } else {
            logger.info("Password recovery answer failed for user " + recover.getUsername() + 
                    ". Attempted challenge answer: " + this.testAnswer);
            errors.addGlobalError(new LocalizableError(ANSWER_ERROR));
        }
        return new ForwardResolution(MAIL_SENT_VIEW);
    }

    public Resolution retrieveLink() {
        PasswordRecover rec2 = null;
        Resolution ret = new ForwardResolution(MAIL_SENT_VIEW);
        ValidationErrors errors = this.getContext().getValidationErrors();

        try {
            if (this.login == null) {
                errors.addGlobalError(new LocalizableError(EMAIL_TOKENS_DO_NOT_MATCH));
                return ret;
            }
            rec2 = api.getPasswordRecoveryInfo(login);
            if (rec2 != null) {
                String testTok = StringUtil.URLEnc(rec2.getSecurityToken(), UTF8);
                String testTok2 = StringUtil.URLEnc(this.token, UTF8);
                if (testTok2.equals(testTok)) {
                    this.getContext().getRequest().getSession().setAttribute(LOGIN, login);
                    ret = new ForwardResolution(CHANGE_PASSWORD_VIEW);
                } else {
                    errors.addGlobalError(new LocalizableError(EMAIL_TOKENS_DO_NOT_MATCH));
                }
            }
        } catch (CsoAuthException e) {
            logger.error("CsoAuthException while trying to return from email link", e);
            errors.addGlobalError(new LocalizableError(CSO_AUTH_EXCEPTION_ERROR));
        } catch (UnsupportedEncodingException e) {
            logger.error("UnsupportedEncodingException while trying to URLEncode test token", e);
            errors.addGlobalError(new LocalizableError(CSO_AUTH_EXCEPTION_ERROR));
        }

        return ret;
    }

    public Resolution changePassword() {
        try {
            String user = (String) this.getContext().getRequest().getSession().getAttribute(LOGIN);
            api.resetPassword(user, testPassword1);
            this.getContext().getRequest().getSession().removeAttribute(LOGIN);
            String url = this.getContext().getRequest().getRequestURL().toString();
            String linkUrl = url.replace("PasswordRecover.action", "Login.action");
            getContext().getMessages().add(new LocalizableMessage(PASSWORD_CHANGED, linkUrl));
            logger.info("Password changed for user " + user);
        } catch (CsoAuthException e) {
            String message = "Unable to change password for user " + login;
            logger.error(message, e);
            this.getContext().getValidationErrors().addGlobalError(new LocalizableError(UNABLE_TO_CHANGE_PWD));
        }
        return new ForwardResolution(MAIL_SENT_VIEW);
    }

    @ValidationMethod(on = "changePassword")
    public void checkPasswordsMatch(ValidationErrors errors) {
        if (!this.testPassword1.equals(this.testPassword2)) {
            errors.add("testPassword1", new LocalizableError(PASSWORDS_MUST_MATCH));
        }
        if (!this.testPassword1.matches(PASSWORD_EXPRESSION)) {
            errors.add("registration.password", new SimpleError(
                    "Must contain at least 1 upper-case letter, 1 lower-case letter, 1 number, no spaces and be 8 characters minimum"));
        }
        String user = (String) this.getContext().getRequest().getSession().getAttribute(LOGIN);
        if(this.testPassword1.equals(user))
        {
           errors.add("registration.password", new SimpleError(
                    "Your password cannot be exactly the same as your user name"));
        }
        
    }
    
    
    @ValidationMethod(on = "changePassword")
    public void checkPasswordLength(ValidationErrors errors) {
        if (this.testPassword1.length() < MIN_PASSWORD_LENGTH) {
            errors.add("testPassword1", new LocalizableError(PASSWORD_TOO_SHORT));
        }
        if (this.testPassword1.length() > MAX_PASSWORD_LENGTH) {
            errors.add("testPassword1", new LocalizableError(PASSWORD_TOO_LONG));
        }
    }

    public void setRecover(PasswordRecover recover) {
        this.recover = recover;
    }

    public PasswordRecover getRecover() {
        return this.recover;
    }

    public void setTestAnswer(String testAnswer) {
        this.testAnswer = testAnswer;
    }

    public String getTestAnswer() {
        return this.testAnswer;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = FilterUtils.escapeLDAPSearchFilter(login);
    }

    public String getTestPassword1() {
        return testPassword1;
    }

    public void setTestPassword1(String testPassword1) {
        this.testPassword1 = testPassword1;
    }

    public String getTestPassword2() {
        return testPassword2;
    }

    public void setTestPassword2(String testPassword2) {
        this.testPassword2 = testPassword2;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
   
}
