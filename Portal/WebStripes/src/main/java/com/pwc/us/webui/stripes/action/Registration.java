package com.pwc.us.webui.stripes.action;

import com.pwc.us.common.exception.CsoAuthException;
import com.pwc.us.common.exception.CsoBadUserPassStringException;
import com.pwc.us.common.exception.CsoDuplicateUserException;
import com.pwc.us.common.exception.CsoRegistrationPasswordInvalidException;
import com.pwc.us.common.exception.CsoRegistrationUsernameInvalidException;
import com.pwc.us.common.exception.GwIntegrationException;
import com.pwc.us.common.exception.GwUserAlreadyRegisteredException;
import com.pwc.us.common.exception.GwUserNotFoundInPcException;
import com.pwc.us.common.model.RegistrationInfo;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.DontValidate;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.LocalizableMessage;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.validation.EmailTypeConverter;
import net.sourceforge.stripes.validation.LocalizableError;
import net.sourceforge.stripes.validation.SimpleError;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidateNestedProperties;
import net.sourceforge.stripes.validation.ValidationErrors;
import net.sourceforge.stripes.validation.ValidationMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Roger Turnau - <roger.turnau@us.pwc.com>
 */
public class Registration extends BaseActionBean {

    private static String VIEW = "/WEB-INF/jsp/user/registration.jsp";
    private RegistrationInfo registration;
    private static Logger logger = LoggerFactory.getLogger(Registration.class);
    private static final String PORTALREG_ERROR_PCWEBSERVICEDOWN = "PortalReg.Error.PCWSDown";
    private static final String PORTALREG_ERROR_PCWEBSERVICEERROR = "PortalReg.Error.PCWSServerError";
    private static final String REGISTRATION_ERROR_AGENTREGINVALID = "registration.error.agentRegistrationInvalid";
    private static final String REGISTRATION_ERROR_PHREGINVALID = "registration.error.phRegistrationInvalid";
    private static final String REGISTRATION_ERROR_DUPLICATEUSER = "registration.error.userAlreadyInSystem";
    private static final String REGISTRATION_ERROR_PW_INVALID = "registration.error.registrationPasswordInvalid";
    private static final String REGISTRATION_ERROR_USER_INVALID = "registration.error.registrationUsernameInvalid";
    private static final String REGISTRATION_ERROR_USER_NAME_BAD_CHARS = "registration.error.registrationUsernameBadChars";
    private static final String PASSWORD_EXPRESSION = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$";
    private static final String allowed = "^[a-zA-Z0-9,.\\&'-]{4,}$";

   static String test[] ={"test\"",
        "test`",
        "test~",
        "test!",
        "test@",
        "test#",
        "test$",
        "test%",
        "test^",
        "test*",
        "test(",
        "test)",
        "test_",
        "test+",
        "test=",
        "test{",
        "test}",
        "test|",
        "test:",
        "test?",
        "test[",
        "test]",
        "test\\",
        "test;",
        "test/",
        "test>",
        "test<"};
    /*
     ^                 # start-of-string
     (?=.*[0-9])       # a digit must occur at least once
     (?=.*[a-z])       # a lower case letter must occur at least once
     (?=.*[A-Z])       # an upper case letter must occur at least once
     (?=\\S+$)         # no whitespace allowed in the entire string
     .{8,}             # anything, at least eight places though
     $                 # end-of-string

     */

    public static void main(String[] args) {
        Registration r = new Registration();
        System.out.println(r.allCharsAreValid("a-E1.9&3245"));
        for (int i = 0; i < test.length; i++) {
            String t = test[i];
            System.out.println(i+" "+t+" "+r.allCharsAreValid(t));
        }
        
    }

    @DontValidate
    @DefaultHandler
    public Resolution showRegistrationForm() {
        return new ForwardResolution(VIEW);
    }

    public Resolution register() {
        ValidationErrors errors = this.getContext().getValidationErrors();
        Resolution retval = null;
        if (registration.getTypeOfUser() == RegistrationInfo.RegistrationType.Agent) {
            retval = registerAgent(errors);
        }

        if (registration.getTypeOfUser() == RegistrationInfo.RegistrationType.Policyholder) {
            retval = registerPolicyholder(errors);
        }

        if (errors.isEmpty()) {
            this.getContext().getMessages().add(new LocalizableMessage("registration.success.message", registration.getUserName()));
            retval = new RedirectResolution(Login.class);
        } else {
            retval = new ForwardResolution(Registration.class, "showRegistrationForm");
        }
        return retval;
    }

    public Resolution registerAgent(ValidationErrors errors) {
        Resolution ret = null;
        try {
            api.registerAgent(registration);
        } catch (GwIntegrationException e) {
            logger.info("GWIntegrationException while trying to register user " + registration.getUserName(), e);
            String s = (e.getMessage() == null) ? "None" : e.getMessage();
            errors.addGlobalError(new LocalizableError(PORTALREG_ERROR_PCWEBSERVICEERROR, s));
            deleteUser();
        } catch (GwUserNotFoundInPcException e) {
            logger.info("Username " + registration.getUserName() + " cannot be found in PolicyCenter.", e);
            errors.addGlobalError(new LocalizableError(PORTALREG_ERROR_PCWEBSERVICEDOWN));
            deleteUser();
        } catch (GwUserAlreadyRegisteredException e) {
            logger.info("Username " + registration.getUserName() + " has already registered in PolicyCenter.", e);
            errors.addGlobalError(new LocalizableError(REGISTRATION_ERROR_AGENTREGINVALID));
        } catch (CsoAuthException e) {
            logger.info("CsoAuthException while trying to register user " + registration.getUserName(), e);
            errors.addGlobalError(new LocalizableError(REGISTRATION_ERROR_AGENTREGINVALID));
            deleteUser();
        } catch (CsoDuplicateUserException e) {
            logger.info("The user " + registration.getUserName() + " has already registered on the Portal. An LDAP entry for this username already exists.", e);
            errors.addGlobalError(new LocalizableError(REGISTRATION_ERROR_DUPLICATEUSER, registration.getUserName()));
        } catch (CsoRegistrationPasswordInvalidException e) {
            logger.info("The user " + registration.getUserName() + " Bad Password", e);
            errors.addGlobalError(new LocalizableError(REGISTRATION_ERROR_PW_INVALID, registration.getUserName()));
        } catch (CsoRegistrationUsernameInvalidException e) {
            logger.info("The user " + registration.getUserName() + " Bad username.", e);
            errors.addGlobalError(new LocalizableError(REGISTRATION_ERROR_USER_INVALID, registration.getUserName()));
        } catch (CsoBadUserPassStringException ex) {
            logger.info("The user " + registration.getUserName() + " Bad username.", ex);
            errors.addGlobalError(new LocalizableError(REGISTRATION_ERROR_USER_NAME_BAD_CHARS, registration.getUserName()));
        }

        return ret;
    }

    public Resolution registerPolicyholder(ValidationErrors errors) {
        Resolution ret = null;
        try {
            api.registerPolicyholder(registration);
        } catch (GwIntegrationException e) {
            logger.info("GWIntegrationException while trying to register user " + registration.getUserName(), e);
            String s = (e.getMessage() == null) ? "None" : e.getMessage();
            errors.addGlobalError(new LocalizableError(PORTALREG_ERROR_PCWEBSERVICEERROR, s));
        } catch (GwUserNotFoundInPcException e) {
            logger.info("User " + registration.getUserName() + " does not have a) an active account in PolicyCenter, b) an active policy in PolicyCenter, or c) both.", e);
            errors.addGlobalError(new LocalizableError(REGISTRATION_ERROR_PHREGINVALID));
        } catch (CsoAuthException e) {
            logger.info("CsoAuthException while trying to register user " + registration.getUserName(), e);
            errors.addGlobalError(new LocalizableError(REGISTRATION_ERROR_PHREGINVALID));
        } catch (CsoDuplicateUserException e) {
            logger.info("The user " + registration.getUserName() + " has already registered on the Portal. An LDAP entry for this username already exists.", e);
            errors.addGlobalError(new LocalizableError(REGISTRATION_ERROR_DUPLICATEUSER, registration.getUserName()));
        } catch (CsoRegistrationPasswordInvalidException e) {
            logger.info("The user " + registration.getUserName() + " Bad password.", e);
            errors.addGlobalError(new LocalizableError(REGISTRATION_ERROR_PW_INVALID, registration.getUserName()));
        } catch (CsoRegistrationUsernameInvalidException e) {
            logger.info("The user " + registration.getUserName() + " Bad username.", e);
            errors.addGlobalError(new LocalizableError(REGISTRATION_ERROR_USER_INVALID, registration.getUserName()));
        } catch (CsoBadUserPassStringException ex) {
            logger.info("The user " + registration.getUserName() + " Bad username.", ex);
            errors.addGlobalError(new LocalizableError(REGISTRATION_ERROR_USER_NAME_BAD_CHARS, registration.getUserName()));
        }

        return ret;
    }

    private void deleteUser() {
        try {
            api.deleteUserFromAuthService(registration.getUserName());
        } catch (CsoAuthException e2) {
            logger.error("Unable to delete failed registration from Auth server", e2);
        }
    }

    @ValidationMethod(on = "register")
    public void checkRequiredFieldsForAgent(ValidationErrors errors) {
        if (registration.getTypeOfUser() == RegistrationInfo.RegistrationType.Agent) {
            if (registration.getAgentAgencyName() == null || registration.getAgentAgencyName().isEmpty()) {
                errors.add("registration.agentAgencyName",
                        new SimpleError("Agency Name is required."));
            }
            if (registration.getAgentAgencyNumber() == null || registration.getAgentAgencyNumber().isEmpty()) {
                errors.add("registration.agentAgencyNumber",
                        new SimpleError("Agency Number is required."));
            }
            if (registration.getAgentRegistrationToken() == null || registration.getAgentRegistrationToken().isEmpty()) {
                errors.add("registration.agentRegistrationToken",
                        new SimpleError("Registration key is required."));
            }
        }
    }

    private boolean allCharsAreValid(String test) {
        boolean valid = false;
        if (test.matches(allowed)) {
            valid = true;
        }
        return valid;
    }

    @ValidationMethod(on = "register")
    public void checkRequiredFieldsForPolicyholder(ValidationErrors errors) {
        if (registration.getTypeOfUser() == RegistrationInfo.RegistrationType.Policyholder) {
            if (registration.getPhPolicyNumber() == null || registration.getPhPolicyNumber().isEmpty()) {
                errors.add("registration.phPolicyNumber",
                        new SimpleError("PolicyNumber is required."));
            }
            if (registration.getPhCompanyName() == null || registration.getPhCompanyName().isEmpty()) {
                errors.add("registration.phCompanyName",
                        new SimpleError("Company Name is required."));
            }
            if (!allCharsAreValid(registration.getUserName())) {
                errors.add("registration.userName",
                        new SimpleError("Only certain special characters are allowed in the username."
                                + "Those special characters include: commas (,), periods (.), hyphens ( - ), ampersands (&), and single quotation mark(\')."));
            }
        }
    }

    @ValidationMethod(on = "register")
    public void checkPasswordsAreSame(ValidationErrors errors) {
        if (!this.registration.getPassword().equals(this.registration.getPasswordAgain())) {
            errors.add("registration.password", new SimpleError(
                    "The entered passwords must match."));
        }

        if (!this.registration.getPassword().matches(PASSWORD_EXPRESSION)) {
            errors.add("registration.password", new SimpleError(
                    "Must contain at least 1 upper-case letter, 1 lower-case letter, 1 number, no spaces and be 8 characters minimum"));
        }

        if (this.registration.getPassword().equals(registration.getUserName())) {
            errors.add("registration.password", new SimpleError(
                    "Your password cannot be exactly the same as your user name"));
        }
    }

    public RegistrationInfo getRegistration() {
        return this.registration;
    }

    @ValidateNestedProperties({
        @Validate(field = "typeOfUser", label = "Type of User", required = true),
        @Validate(field = "userName", label = "Username", required = true, minlength = 4),
        @Validate(field = "password", label = "Password", required = true, minlength = 4),
        @Validate(field = "passwordAgain", label = "Enter Password Again", required = true, minlength = 4),
        @Validate(field = "email", label = "Email", required = true,
                converter = EmailTypeConverter.class),
        @Validate(field = "firstName", label = "First Name", required = true),
        @Validate(field = "lastName", label = "Last Name", required = true),
        @Validate(field = "phoneNumber", label = "Phone Number", required = true,
                mask = "^(\\+?1-?)?(\\([2-9]\\d{2}\\)|[2-9]\\d{2})-?[2-9]\\d{2}-?\\d{4}$"),
        @Validate(field = "challengeQuestion", label = "Challenge Question", required = true),
        @Validate(field = "challengeAnswer", label = "Challenge Answer", required = true),
        @Validate(field = "phPolicyNumber", label = "Policy Number", mask = "\\d{8}")
    })
    public void setRegistration(RegistrationInfo registration) {
        this.registration = registration;
    }
}
