package com.pwc.us.wsclient.agentauth;

import com.pwc.us.common.exception.GwIntegrationException;
import com.pwc.us.common.exception.GwUserNotFoundInCcException;
import com.pwc.us.ws.client.agentauth.cc.Authentication;
import com.pwc.us.ws.client.agentauth.cc.Locale;
import com.pwc.us.ws.client.agentauth.cc.PortalSecurityAPIPortType;
import com.pwc.us.ws.client.agentauth.cc.RegistrationNotCompleteException_Exception;
import com.pwc.us.ws.client.agentauth.cc.UserAuthTypeInvalidException_Exception;
import com.pwc.us.ws.client.agentauth.cc.UserNotActiveException_Exception;
import com.pwc.us.ws.client.agentauth.cc.UserNotFoundException_Exception;
import com.pwc.us.wsclient.config.DefaultHeaderValues;
import com.pwc.us.wsclient.config.ServiceCreationUtil;
import com.pwc.us.common.utils.JndiUtils;
import java.net.MalformedURLException;
import javax.xml.ws.WebServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Roger
 */
public class CcAgentAuthentication {

    private static String WSDL = JndiUtils.getClaimCenterUrl() + "/ws/compsource/cc/api/portalauth/PortalSecurityAPI?WSDL";
    private static String SVC_QNAME_NAMESPACE_URI = "http://example.com/compsource/cc/api/portalauth/PortalSecurityAPI";
    private static String SVC_QNAME_LOCALPART = "PortalSecurityAPI";
    private static String PORT_QNAME_NAMESPACE_URI = "http://example.com/compsource/cc/api/portalauth/PortalSecurityAPI";
    private static String PORT_QNAME_LOCALPART = "PortalSecurityAPISoap11Port";
    private static final int REQUEST_TIMEOUT = 300000;
    private static final int CONNECT_TIMEOUT = 300000;
    private static final Authentication gwAuthentication = new Authentication();
    private static final Locale gwLocale = new Locale();
    private static final Logger logger = LoggerFactory.getLogger(BcAgentAuthentication.class);
    private static PortalSecurityAPIPortType port = null;

    static {
        gwAuthentication.setUsername(JndiUtils.getGwUser());
        gwAuthentication.setPassword(JndiUtils.getGwPassword());

        gwLocale.setValue(DefaultHeaderValues.LOCALE);
    }
    
    /**
     * Calls Guidewire PolicyCenter to determine if agent is an active user. If
     * successful, PolicyCenter will return a one-time-use security token.
     *
     * @param userName
     * @return
     * @throws GwIntegrationException
     */
    public String isAgentActive(String userName)
            throws GwIntegrationException, GwUserNotFoundInCcException {
        
        connectPort();

        String ret = null;
        try {
            ret = port.getUserSharedSecret(userName, gwAuthentication, gwLocale);
            logger.info("isUserActive response: " + ret);
        } catch (RegistrationNotCompleteException_Exception e) {
            String msg = "User " + userName + " has not yet registered. Unable to authenticate to PolicyCenter.";
            logger.error(msg, e);
            throw new GwIntegrationException(msg, e);
        } catch (com.pwc.us.ws.client.agentauth.cc.SOAPException_Exception e) {
            String msg = "SOAP Exception while calling PortalSecurityAPIPortType.isAgentActive.";
            logger.error(msg, e);
            throw new GwIntegrationException(msg, e);
        } catch (UserAuthTypeInvalidException_Exception e) {
            String msg = "Invalid Authorization Type for user " + userName + ".";
            logger.error(msg, e);
            throw new GwIntegrationException(msg, e);
        } catch (UserNotActiveException_Exception e) {
            String msg = "User " + userName + " is not active. Unable to authenticate to PolicyCenter.";
            logger.error(msg, e);
            throw new GwIntegrationException(msg, e);
        } catch (UserNotFoundException_Exception e) {
            String msg = "User " + userName + " cannot be found in the system. Unable to authenticate to PolicyCenter.";
            logger.error(msg, e);
            throw new GwUserNotFoundInCcException(msg, e);
        }
        return ret;
    }
    
    private void connectPort() throws GwIntegrationException {
        if (port == null) {
            try {
                port = ServiceCreationUtil.createService(WSDL,
                        SVC_QNAME_NAMESPACE_URI, SVC_QNAME_LOCALPART,
                        PORT_QNAME_NAMESPACE_URI, PORT_QNAME_LOCALPART,
                        PortalSecurityAPIPortType.class, REQUEST_TIMEOUT, CONNECT_TIMEOUT);
            } catch (WebServiceException e) {
                logger.error("could not create service", e);
                throw new GwIntegrationException(
                        "unable to create agent authentication web service for class PortalSecurityAPIPortType", e);

            } catch (MalformedURLException e) {
                logger.error("Malformed URL creating agent authentication web service for class PortalSecurityAPIPortType ");
                throw new GwIntegrationException("malformed URL while creating "
                        + "agent authentication web service for class PortalSecurityAPIPortType", e);
            }
        }
    }     
}
