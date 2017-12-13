package com.pwc.us.webui.stripes.action;

import com.pwc.us.common.exception.GwIntegrationException;
import com.pwc.us.common.exception.GwUserNotFoundInBcException;
import com.pwc.us.common.exception.GwUserNotFoundInCcException;
import com.pwc.us.common.exception.GwUserNotFoundInPcException;
import com.pwc.us.common.model.Agent;
import com.pwc.us.common.model.User;
import com.pwc.us.common.utils.JndiUtils;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.LocalizableError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Enables the Portal to be the auth interceptor for MGA authentication.
 *
 * @author Roger
 */
@UrlBinding("/mga/{$application}/{path}")
public class MgaActionBean extends BaseActionBean {

    private static final String SHARED_SECRET_HEADER = "SharedSecret";
    private static final String USERNAME_HEADER = "userName";
    private static final String POLICY_CENTER_URL = JndiUtils.getPolicyCenterUrl() + "/PolicyCenter.do";
    private static final String BILLING_CENTER_URL = JndiUtils.getBillingCenterUrl() + "/BillingCenter.do";
    private static final String CLAIMS_CENTER_URL = JndiUtils.getClaimCenterUrl() + "/ClaimCenter.do";
    private String path = null;
    private static Logger logger = LoggerFactory.getLogger(MgaActionBean.class);

    public Resolution bc() {
        Resolution res = null;
        User user = this.getUser();
        try {
            if (user.getSpecificType() instanceof Agent) {
                if (this.path == null) {
                    this.path = BILLING_CENTER_URL;
                }
                Agent agent = (Agent) user.getSpecificType();
                String sharedSecret = api.loginAgentToBc(agent);
                res = redirectUser(agent, this.path, sharedSecret);
            } else {
                res = new RedirectResolution(HelloActionBean.class);
            }
        } catch (GwIntegrationException e) {
            logger.error("fatal error trying mga auth to BC for user " + user.getLogin(), e);
            this.getContext().getValidationErrors().addGlobalError(new LocalizableError("PortalAuth.Error.PCWSServerError", user.getLogin()));
            res = new ForwardResolution(Logout.class);
        } catch (GwUserNotFoundInBcException e) {
            logger.error("unable to authenticate user " + user.getLogin() + " into BC.", e);
            this.getContext().getValidationErrors().addGlobalError(new LocalizableError("PortalAuth.Error.BCCredentialsInvalid", user.getLogin()));
            res = new ForwardResolution(Logout.class);
        }
        return res;
    }

    public Resolution pc() {
        Resolution res = null;
        User user = this.getUser();
        try {
            if (user.getSpecificType() instanceof Agent) {
                if (this.path == null) {
                    this.path = POLICY_CENTER_URL;
                }
                Agent agent = (Agent) user.getSpecificType();
                String sharedSecret = api.loginAgentToPc(agent);
                res = redirectUser(agent, this.path, sharedSecret);
            } else {
                res = new RedirectResolution(HelloActionBean.class);
            }
        } catch (GwIntegrationException e) {
            logger.error("fatal error trying to log in user " + user.getLogin(), e);
            this.getContext().getValidationErrors().addGlobalError(new LocalizableError("PortalAuth.Error.PCWSServerError", user.getLogin()));
            res = new ForwardResolution(Logout.class);
        } catch (GwUserNotFoundInPcException e) {
            logger.error("unable to authenticate user " + user.getLogin() + " into PC.", e);
            this.getContext().getValidationErrors().addGlobalError(new LocalizableError("PortalAuth.Error.BCCredentialsInvalid", user.getLogin()));
            res = new ForwardResolution(Logout.class);
        }
        return res;
    }

    public Resolution cc() {
        Resolution res = null;
        User user = this.getUser();
        try {
            if (user.getSpecificType() instanceof Agent) {
                if (this.path == null) {
                    this.path = CLAIMS_CENTER_URL;
                }
                Agent agent = (Agent) user.getSpecificType();
                String sharedSecret = api.loginAgentToCc(agent);
                res = redirectUser(agent, this.path, sharedSecret);
            } else {
                res = new RedirectResolution(HelloActionBean.class);
            }
        } catch (GwIntegrationException e) {
            logger.error("fatal error trying to log in user " + user.getLogin(), e);
            this.getContext().getValidationErrors().addGlobalError(new LocalizableError("PortalAuth.Error.PCWSServerError", user.getLogin()));
            res = new ForwardResolution(Logout.class);
        } catch (GwUserNotFoundInCcException e) {
            logger.error("unable to login user " + user.getLogin(), e);
            this.getContext().getValidationErrors().addGlobalError(new LocalizableError("PortalAuth.Error.BCCredentialsInvalid", user.getLogin()));
            res = new ForwardResolution(Logout.class);
        }
        return res;
    }

    private Resolution redirectUser(Agent agent, String url, String sharedSecret) {
        return new RedirectResolution(url, false)
                .addParameter(SHARED_SECRET_HEADER, sharedSecret)
                .addParameter(USERNAME_HEADER, agent.getLogin());

    }

    public String getPath() {
        return this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
