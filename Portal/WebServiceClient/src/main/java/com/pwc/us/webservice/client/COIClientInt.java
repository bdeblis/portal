/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pwc.us.webservice.client;

import com.pwc.us.common.exception.GwIntegrationException;
import com.pwc.us.ws.client.coi.*;
import com.pwc.us.wsclient.config.DefaultHeaderValues;
import com.pwc.us.wsclient.config.ServiceCreationUtil;
import com.pwc.us.common.utils.JndiUtils;
import java.net.MalformedURLException;
import java.util.List;
import javax.xml.ws.WebServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * Robert Snelling <robert.snelling@us.pwc.com>
 */
public class COIClientInt {

                
    private static String WSDL = JndiUtils.getPolicyCenterUrl() + "/ws/compsource/pc/api/coi/CSNewCOIRequestAPI?WSDL";
    private static String SVC_QNAME_NAMESPACE_URI = "http://guidewire.com/compsource/pc/api/coi/CSNewCOIRequestAPI";
    private static String SVC_QNAME_LOCALPART = "CSNewCOIRequestAPI";
    private static String PORT_QNAME_NAMESPACE_URI = "http://guidewire.com/compsource/pc/api/coi/CSNewCOIRequestAPI";
    private static String PORT_QNAME_LOCALPART = "CSNewCOIRequestAPISoap11Port";
    private static final int REQUEST_TIMEOUT = 300000;
    private static final int CONNECT_TIMEOUT = 300000;
    private static final com.pwc.us.ws.client.coi.Authentication gwAuthentication = new com.pwc.us.ws.client.coi.Authentication();
    private static final com.pwc.us.ws.client.coi.Locale gwLocale = new com.pwc.us.ws.client.coi.Locale();
    private static final Logger logger = LoggerFactory.getLogger(COIClientInt.class);
    private static CSNewCOIRequestAPIPortType port = null;
    
    static {
        gwAuthentication.setUsername(JndiUtils.getGwUser());
        gwAuthentication.setPassword(JndiUtils.getGwPassword());

        gwLocale.setValue(DefaultHeaderValues.LOCALE);
    }
    
    public List<COIPolicyTerm> getPolicyTerms(String policyNumber) throws GwIntegrationException {
        List<COIPolicyTerm> terms = null;
        
        connectPort();

        try {            
            com.sun.xml.ws.transport.http.client.HttpTransportPipe.dump=true;
            GetPolicyTermsResponse.Return returns = port.getPolicyTerms(policyNumber,gwAuthentication, gwLocale);
            terms = returns.getEntry();
            System.out.println("Result = " + terms);

        } catch (Exception e) {
            String msg = "Caught Exception in COI getPolicyTerms";
            logger.error(msg, e);
            throw new GwIntegrationException(msg, e);
        }
        return terms;

    }
    
    public boolean createNewCOIRequest(String policyNumber, String requestXML) throws GwIntegrationException {
         boolean response = false;
        try {
                com.sun.xml.ws.transport.http.client.HttpTransportPipe.dump=true;
                response = port.createNewCOIRequest(policyNumber, requestXML,gwAuthentication,gwLocale);

                System.out.println("Result = " + response);

        } catch (RequiredFieldException_Exception e) {
            String msg = "Caught RequiredFieldException_Exception in COI Request";
            logger.error(msg, e);
            throw new GwIntegrationException(msg, e);
        } catch (SOAPException_Exception e) {
            String msg = "Caught SOAPException_Exception in COI Request";
            logger.error(msg, e);
            throw new GwIntegrationException(msg, e);            
        } catch (XmlException_Exception e) {
            String msg = "Caught XmlException_Exception in COI Request";
            logger.error(msg, e);
            throw new GwIntegrationException(msg, e);            
        }
        return response;
    }
    
    private void connectPort() throws GwIntegrationException {
        if (port == null) {
            try {
                port = ServiceCreationUtil.createService(WSDL,
                        SVC_QNAME_NAMESPACE_URI, SVC_QNAME_LOCALPART,
                        PORT_QNAME_NAMESPACE_URI, PORT_QNAME_LOCALPART,
                        CSNewCOIRequestAPIPortType.class, REQUEST_TIMEOUT, CONNECT_TIMEOUT);
            } catch (WebServiceException e) {
                logger.error("could not create service", e);
                throw new GwIntegrationException(
                        "unable to create certificate of insurance web service for class CSNewCOIRequestAPIPortType", e);

            } catch (MalformedURLException e) {
                logger.error("Malformed URL creating certificate of insurance web service for class CSNewCOIRequestAPIPortType ");
                throw new GwIntegrationException("malformed URL while creating "
                        + "certificate of insurance web service for class CSNewCOIRequestAPIPortType", e);
            }
        }
    }
}
