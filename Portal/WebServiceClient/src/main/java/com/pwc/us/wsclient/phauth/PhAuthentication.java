/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pwc.us.wsclient.phauth;

import com.pwc.us.common.exception.GwIntegrationException;
import com.pwc.us.ws.client.phauth.Authentication;
import com.pwc.us.ws.client.phauth.FieldFormatException_Exception;
import com.pwc.us.ws.client.phauth.Locale;
import com.pwc.us.ws.client.phauth.PolicyholderAPIPortType;
import com.pwc.us.ws.client.phauth.SOAPException_Exception;
import com.pwc.us.wsclient.config.DefaultHeaderValues;
import com.pwc.us.wsclient.config.ServiceCreationUtil;
import com.pwc.us.common.utils.JndiUtils;
import com.pwc.us.ws.client.phauth.RequiredFieldException_Exception;
import java.net.MalformedURLException;
import java.util.logging.Level;
import javax.xml.ws.WebServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Roger Turnau - <roger.turnau@us.pwc.com>
 */
public class PhAuthentication {

    private static String WSDL = JndiUtils.getPolicyCenterUrl() + "/ws/compsource/pc/api/isactive/policyholder/PolicyholderAPI?WSDL";
    private static String SVC_QNAME_NAMESPACE_URI = "http://example.com/compsource/pc/api/isactive/policyholder/PolicyholderAPI";
    private static String SVC_QNAME_LOCALPART = "PolicyholderAPI";
    private static String PORT_QNAME_NAMESPACE_URI = "http://example.com/compsource/pc/api/isactive/policyholder/PolicyholderAPI";
    private static String PORT_QNAME_LOCALPART = "PolicyholderAPISoap11Port";
    private static final int REQUEST_TIMEOUT = 300000;
    private static final int CONNECT_TIMEOUT = 300000;
    private static final Authentication gwAuthentication = new Authentication();
    ;
    private static final Locale gwLocale = new Locale();
    private static final Logger logger = LoggerFactory.getLogger(PhAuthentication.class);
    private static PolicyholderAPIPortType port = null;

    static {
        gwAuthentication.setUsername(JndiUtils.getGwUser());
        gwAuthentication.setPassword(JndiUtils.getGwPassword());

        gwLocale.setValue(DefaultHeaderValues.LOCALE);
    }
    
    public String queryCashierAuth(String name, String password) throws GwIntegrationException
    {
        connectPort();

        String ret = null;
        try {
            ret = port.queryCashierAuth(name,password, gwAuthentication, gwLocale);
        } catch (SOAPException_Exception ex) {
            String msg = "SOAP Exception while calling port PolicyholderAPIPortType.";
            logger.error(msg, ex);
            throw new GwIntegrationException(msg, ex);
        }
        return ret;
    }
    
    public String queryAccountAddress(String accountNumber) throws GwIntegrationException {
        connectPort();
                String ret = null;
        try {
            ret = port.queryAccountAddress(accountNumber, gwAuthentication, gwLocale);
        } catch (RequiredFieldException_Exception ex) {
            String msg = "SOAP Exception while calling port PolicyholderAPIPortType.";
            logger.error(msg, ex);
            throw new GwIntegrationException(msg, ex);
        }
        return ret;
    }

    public String queryAcctUsingPolicyNum(String policyNum)
            throws GwIntegrationException {
        connectPort();

        String ret = null;
        try {
            ret = port.queryAcctUsingPolicyNum(policyNum, gwAuthentication, gwLocale);
        } catch (FieldFormatException_Exception ex) {
            String msg = "Field Format Exception while calling port PolicyholderAPIPortType.";
            logger.error(msg, ex);
            throw new GwIntegrationException(msg, ex);
        } catch (SOAPException_Exception ex) {
            String msg = "SOAP Exception while calling port PolicyholderAPIPortType.";
            logger.error(msg, ex);
            throw new GwIntegrationException(msg, ex);
        }
        return ret;
    }
    
    private void connectPort() throws GwIntegrationException {
        if (port == null) {
            try {
                port = ServiceCreationUtil.createService(WSDL,
                        SVC_QNAME_NAMESPACE_URI, SVC_QNAME_LOCALPART,
                        PORT_QNAME_NAMESPACE_URI, PORT_QNAME_LOCALPART,
                        PolicyholderAPIPortType.class, REQUEST_TIMEOUT, CONNECT_TIMEOUT);
            } catch (WebServiceException e) {
                logger.error("could not create service", e);
                throw new GwIntegrationException(
                        "unable to create policyholder authentication web service for class PolicyholderAPIPortType", e);
            } catch (MalformedURLException e) {
                logger.error("Malformed URL creating agent authentication web service for class PolicyholderAPIPortType ");
                throw new GwIntegrationException("malformed URL while creating "
                        + "policyholder authentication web service for class PolicyholderAPIPortType", e);
            }
        }
    }

}
