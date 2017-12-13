/*
 * Login.java
 *
 */
package com.pwc.us.webui.stripes.action;

import com.pwc.us.common.exception.CsoAuthException;
import com.pwc.us.common.exception.GwIntegrationException;
import com.pwc.us.common.exception.GwUserNotFoundInPcException;
import com.pwc.us.common.model.Agent;
import com.pwc.us.common.model.Cashier;
import com.pwc.us.common.model.Policyholder;
import com.pwc.us.common.model.User;
import com.pwc.us.common.utils.JndiUtils;
import com.pwc.us.webui.stripes.util.FilterUtils;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.DontValidate;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.validation.LocalizableError;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidationErrors;
import net.sourceforge.stripes.validation.ValidationMethod;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 */
public class Login extends BaseActionBean {

    private static final String VIEW = "/WEB-INF/jsp/user/login.jsp";
    private static final String SHARED_SECRET_HEADER = "SharedSecret";
    private static final String USERNAME_HEADER = "userName";
    private static final String POLICY_CENTER_URL = JndiUtils.getPolicyCenterUrl() + "/PolicyCenter.do";
    // The mock PC URL. The request filter will substitute in the correct URI
    //private static final String POLICY_CENTER_URL = "/sendToPolicyCenter";
    private static Logger logger = LoggerFactory.getLogger(Login.class);
    @Validate(required = true)
    private String username;
    @Validate(required = true, minlength = 4)
    private String password;
    private String targetUrl;

    /**
     * <p>Construct a new Page instance.</p>
     */
    public Login() {
    }

    @DefaultHandler
    @DontValidate
    public Resolution showLoginForm() {
        return new ForwardResolution(VIEW);
    }

    /**
     * In the case of policyholders, the system simply forwards them on to the
     * main menu. In the case of agents, it packs the username and security
     * token into the HTTP header and redirects to PolicyCenter.
     *
     * @return
     */
    public Resolution login() {
        Resolution ret = null;
        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession();
        logger.info("browser user agent "+this.getContext().getRequest().getHeader("user-agent"));
        if (this.getContext().getResponse().containsHeader(SHARED_SECRET_HEADER)) {
            String sharedSecret = (String) session.getAttribute(SHARED_SECRET_HEADER);
            String user = (String) session.getAttribute(USERNAME_HEADER);
            ret = new RedirectResolution(POLICY_CENTER_URL, false)
                    .addParameter(SHARED_SECRET_HEADER, sharedSecret)
                    .addParameter(USERNAME_HEADER, user);
        } else {
            SavedRequest req = (SavedRequest) session.getAttribute(WebUtils.SAVED_REQUEST_KEY);
            if (req != null) {
                logger.info("URI: " + req.getRequestURI());
                logger.info("browser user agent "+this.getContext().getRequest().getHeader("user-agent"));
                ret = new RedirectResolution(req.getRequestURI(), false);
            } else {
                ret = new ForwardResolution(HelloActionBean.class);
            }
        }
        return ret;
    }

    @ValidationMethod
    public void validateUser(ValidationErrors errors) {
        Subject currentUser = SecurityUtils.getSubject();
        AuthenticationToken token = new UsernamePasswordToken(this.getUsername(), this.getPassword());
        try {
            currentUser.login(token);
            User user = this.getUser();
            if (user.getSpecificType() instanceof Policyholder) {
                api.loginPolicyholder((Policyholder) user.getSpecificType());
 
            }
            if (user.getSpecificType() instanceof Agent) {
                String sharedSecret = api.loginAgentToPc((Agent) user.getSpecificType());
                this.getContext().getResponse().addHeader(SHARED_SECRET_HEADER, sharedSecret);
                this.getContext().getResponse().addHeader(USERNAME_HEADER, user.getLogin());
                this.getContext().getRequest().setAttribute(SHARED_SECRET_HEADER, sharedSecret);
                this.getContext().getRequest().setAttribute(USERNAME_HEADER, user.getLogin());
                currentUser.getSession().setAttribute(SHARED_SECRET_HEADER, sharedSecret);
                currentUser.getSession().setAttribute(USERNAME_HEADER, user.getLogin());
            }
            if(user.getSpecificType() instanceof Cashier){
                api.loginCashier((Cashier) user.getSpecificType(),this.getUsername(), this.getPassword());
            }
        } catch (LockedAccountException e) {
            logger.info("user " + this.getUsername() + " tried to login with a locked account.");
            errors.addGlobalError(new LocalizableError("PortalAuth.Error.accountLocked", this.getUsername()));
            currentUser.logout();
        } catch (AuthenticationException ex) {
            logger.info("unable to login user " + this.getUsername());
            errors.addGlobalError(new LocalizableError("PortalAuth.Error.PortalCredentialsInvalid", this.getUsername()));
            currentUser.logout();
        } catch (CsoAuthException ex) {
            logger.error("fatal error trying to log in user " + this.getUsername(), ex);
            errors.addGlobalError(new LocalizableError("PortalAuth.Error.DSUnavailable", this.getUsername()));
            currentUser.logout();
        } catch (GwIntegrationException ex) {
            logger.error("fatal error trying to log in user " + this.getUsername(), ex);
            errors.addGlobalError(new LocalizableError("PortalAuth.Error.PCWSServerError", this.getUsername()));
            currentUser.logout();
        } catch (GwUserNotFoundInPcException ex) {
            logger.error("unable to login user " + this.getUsername(), ex);
            errors.addGlobalError(new LocalizableError("PortalAuth.Error.PCCredentialsInvalid", this.getUsername()));
            currentUser.logout();
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = FilterUtils.escapeLDAPSearchFilter(username);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * The URL the user was trying to access (null if the login page was
     * accessed directly).
     */
    public String getTargetUrl() {
        return targetUrl;
    }

    /**
     * The URL the user was trying to access (null if the login page was
     * accessed directly).
     */
    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
    }
    

}
