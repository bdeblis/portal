/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pwc.us.wsclient.fnoi;

import com.pwc.us.common.exception.GwFNOIRequiredFieldException;
import com.pwc.us.common.exception.GwIntegrationException;
import com.pwc.us.ws.client.fnoi.*;
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
public class FNOIClientInt {

    private static String WSDL = JndiUtils.getClaimCenterUrl() + "/ws/compsource/cc/api/fnoi/CSNewFNOIAPI?WSDL";
    private static String SVC_QNAME_NAMESPACE_URI = "http://guidewire.com/compsource/cc/api/fnoi/CSNewFNOIAPI";
    private static String SVC_QNAME_LOCALPART = "CSNewFNOIAPI";
    private static String PORT_QNAME_NAMESPACE_URI = "http://guidewire.com/compsource/cc/api/fnoi/CSNewFNOIAPI";
    private static String PORT_QNAME_LOCALPART = "CSNewFNOIAPISoap11Port";
    private static final int REQUEST_TIMEOUT = 480000;
    private static final int CONNECT_TIMEOUT = 480000;
    private static final com.pwc.us.ws.client.fnoi.Authentication gwAuthentication = new com.pwc.us.ws.client.fnoi.Authentication();
    private static final com.pwc.us.ws.client.fnoi.Locale gwLocale = new com.pwc.us.ws.client.fnoi.Locale();
    private static final Logger logger = LoggerFactory.getLogger(FNOIClientInt.class);
    private static CSNewFNOIAPIPortType port = null;
    private static final Logger claimsLog = LoggerFactory.getLogger("claimsLog");
    
    static {
        gwAuthentication.setUsername(JndiUtils.getGwUser());
        gwAuthentication.setPassword(JndiUtils.getGwPassword());

        gwLocale.setValue(DefaultHeaderValues.LOCALE);
    }
    
    public String createNewFNOI(String fnoiXML,String incidentOnly) throws GwIntegrationException, GwFNOIRequiredFieldException {
        logger.error("\nbegin\n"+fnoiXML+"\nend\n");

        String response;
        try {
                com.sun.xml.ws.transport.http.client.HttpTransportPipe.dump=true;
                connectPort();
                if(incidentOnly.equalsIgnoreCase("no"))
                    response = port.createNewFNOI(fnoiXML, gwAuthentication, gwLocale);
                else
                    response = port.createNewFNOIIncidentReport(fnoiXML, gwAuthentication, gwLocale);
                System.out.println("Result = " + response);

        } catch (RequiredFieldException_Exception e) {
            String msg = "ErrorMessage="+"\"Caught RequiredFieldException_Exception in FNOI Request\"";
            logger.error(msg, e);
            claimsLog.error(msg+" Task=CreateNewFNOI Status=Failed ErrorType=RequiredFieldException_Exception "+GetContactInfoFromXml.parseForContact(fnoiXML),e);
            throw new GwFNOIRequiredFieldException(msg, e);
        } catch (SOAPException_Exception e) {
            String msg = "ErrorMessage="+"\"Caught SOAPException_Exception in FNOI Request\"";
            logger.error(msg, e);
            claimsLog.error(msg+" Task=CreateNewFNOI Status=Failed ErrorType=SOAPException_Exception "+GetContactInfoFromXml.parseForContact(fnoiXML),e);
            throw new GwIntegrationException(msg, e);            
        } catch (XmlException_Exception e) {
            String msg = "ErrorMessage="+"\"Caught XmlException_Exception in FNOI Request\"";
            logger.error(msg, e);
            claimsLog.error(msg+" Task=CreateNewFNOI Status=Failed ErrorType=XmlException_Exception "+GetContactInfoFromXml.parseForContact(fnoiXML),e);
            throw new GwIntegrationException(msg, e);            
        } catch (FieldConversionException_Exception e) {
            String msg = "ErrorMessage="+"\"Caught FieldConversionException_Exception in FNOI Request\"";
            logger.error(msg, e);
            claimsLog.error(msg+" Task=CreateNewFNOI Status=Failed ErrorType=FieldConversionException_Exception\""+GetContactInfoFromXml.parseForContact(fnoiXML),e);
            throw new GwIntegrationException(msg, e);
        } catch (ConnectException_Exception e) {
            String msg = "ErrorMessage="+"\"Caught ConnectException_Exception in FNOI Request\"";
            logger.error(msg, e);
            claimsLog.error(msg+" Task=CreateNewFNOI Status=Failed ErrorType=ConnectException_Exception "+GetContactInfoFromXml.parseForContact(fnoiXML),e);
            throw new GwIntegrationException(msg, e);            
        }
        return response;
        
    }

    public List<TypekeyName> getAccountOrgTypeExtTypeKeys() throws GwIntegrationException {
        List<TypekeyName> list = null;
        GetAccountOrgTypeExtTypeKeysResponse.Return response;
        connectPort();
        
        try {
                com.sun.xml.ws.transport.http.client.HttpTransportPipe.dump=true;
                response = port.getAccountOrgTypeExtTypeKeys(gwAuthentication, gwLocale);
                list = response.getEntry();

        } catch (Exception e) {
            String msg = "ErrorMessage="+"\"Caught Exception in FNOI getAccountOrgTypeExtTypeKeys\"";
            logger.error(msg, e);
            claimsLog.error(msg+" Task=GetAccountOrgTypeExtTypeKeys Status=Failed ErrorType=Exception ",e);
            throw new GwIntegrationException(msg, e);
        }
        return list;
    }

    public List<TypekeyName> getBodyPartTypeTypeKeys() throws GwIntegrationException {
        List<TypekeyName> list = null;
        GetBodyPartTypeTypeKeysResponse.Return response;
        connectPort();
        
        try {
                com.sun.xml.ws.transport.http.client.HttpTransportPipe.dump=true;
                response = port.getBodyPartTypeTypeKeys(gwAuthentication, gwLocale);
                list = response.getEntry();

        } catch (Exception e) {
            String msg = "ErrorMessage="+"\"Caught Exception in FNOI getBodyPartTypeTypeKeys\"";
            logger.error(msg, e);
            claimsLog.error(msg+" Task=GetBodyPartTypeTypeKeys Status=Failed ErrorType=Exception ",e);
            throw new GwIntegrationException(msg, e);
        }
        return list;
    }

    public List<TypekeyName> getDetailedBodyPartTypeTypeKeys(BodyPartType primaryBodyPart) throws GwIntegrationException {
        List<TypekeyName> list = null;
        GetDetailedBodyPartTypeTypeKeysResponse.Return response;
        connectPort();
        
        try {
                com.sun.xml.ws.transport.http.client.HttpTransportPipe.dump=true;
                response = port.getDetailedBodyPartTypeTypeKeys(primaryBodyPart, gwAuthentication, gwLocale);
                list = response.getEntry();

        } catch (Exception e) {
            String msg = "ErrorMessage="+"\"Caught Exception in FNOI getDetailedBodyPartTypeTypeKeys\"";
            logger.error(msg, e);
            claimsLog.error(msg+" Task=GetDetailedBodyPartTypeTypeKeys Status=Failed ErrorType=Exception ",e);
            throw new GwIntegrationException(msg, e);
        }
        return list;
    }

    public List<TypekeyName> getOccupationalInjuryTypeTypeKeys() throws GwIntegrationException {
        List<TypekeyName> list = null;
        GetOccupationalInjuryTypeTypeKeysResponse.Return response;
        connectPort();
        
        try {
                com.sun.xml.ws.transport.http.client.HttpTransportPipe.dump=true;
                response = port.getOccupationalInjuryTypeTypeKeys(gwAuthentication, gwLocale);
                list = response.getEntry();

        } catch (Exception e) {
            String msg = "ErrorMessage="+"\"Caught Exception in FNOI getOccupationalInjuryTypeTypeKeys\"";
            logger.error(msg, e);
            claimsLog.error(msg+" Task=GetOccupationalInjuryTypeTypeKeys Status=Failed ErrorType=Exception ",e);
            throw new GwIntegrationException(msg, e);
        }
        return list;
    }

    public List<TypekeyName> getCumulativeInjuryTypeTypeKeys() throws GwIntegrationException {
        List<TypekeyName> list = null;
        GetCumulativeDetailedInjuryTypeTypeKeysResponse.Return response;
        connectPort();

        try {
            com.sun.xml.ws.transport.http.client.HttpTransportPipe.dump = true;
            response = port.getCumulativeDetailedInjuryTypeTypeKeys(gwAuthentication, gwLocale);
            list = response.getEntry();

        } catch (Exception e) {
            String msg = "ErrorMessage="+"\"Caught Exception in FNOI getCumulativeInjuryTypeTypeKeys\"";
            logger.error(msg, e);
            claimsLog.error(msg + " Task=GetCumulativeInjuryTypeTypeKeys Status=Failed ErrorType=Exception ", e);
            throw new GwIntegrationException(msg, e);
        }
        return list;
    }

    public List<TypekeyName> getMultipleInjuryTypeTypeKeys() throws GwIntegrationException {
        List<TypekeyName> list = null;
        GetMultipleInjuryTypeTypeKeysResponse.Return response;
        connectPort();

        try {
            com.sun.xml.ws.transport.http.client.HttpTransportPipe.dump = true;
            response = port.getMultipleInjuryTypeTypeKeys(gwAuthentication, gwLocale);
            list = response.getEntry();

        } catch (Exception e) {
            String msg = "ErrorMessage="+"\"Caught Exception in FNOI getMultipleInjuryTypeTypeKeys\"";
            logger.error(msg, e);
            claimsLog.error(msg + " Task=GetMultipleInjuryTypeTypeKeys Status=Failed ErrorType=Exception ", e);
            throw new GwIntegrationException(msg, e);
        }
        return list;
    }

    public List<TypekeyName> getPrimaryInjuryTypeTypeKeys() throws GwIntegrationException {
        List<TypekeyName> list = null;
        GetPrimaryInjuryTypeResponse.Return response;
        connectPort();

        try {
            com.sun.xml.ws.transport.http.client.HttpTransportPipe.dump = true;
            response = port.getPrimaryInjuryType(gwAuthentication, gwLocale);
            list = response.getEntry();

        } catch (Exception e) {
            String msg = "ErrorMessage="+"\"Caught Exception in FNOI getPrimaryInjuryTypeTypeKeys\"";
            logger.error(msg, e);
            claimsLog.error(msg + " Task=GetPrimaryInjuryTypeTypeKeys Status=Failed ErrorType=Exception ", e);
            throw new GwIntegrationException(msg, e);
        }
        return list;
    }

    public List<TypekeyName> getSpecificDetailedInjuryTypeTypeKeys() throws GwIntegrationException {
        List<TypekeyName> list = null;
        GetSpecificDetailedInjuryTypeTypeKeysResponse.Return response;
        connectPort();
        
        try {
                com.sun.xml.ws.transport.http.client.HttpTransportPipe.dump=true;
                response = port.getSpecificDetailedInjuryTypeTypeKeys(gwAuthentication, gwLocale);
                list = response.getEntry();

        } catch (Exception e) {
            String msg = "ErrorMessage="+"\"Caught Exception in FNOI getSpecificDetailedInjuryTypeTypeKeys\"";
            logger.error(msg, e);
            claimsLog.error(msg+" Task=GetSpecifictDetailedInjuryTypeTypeKeys Status=Failed ErrorType=Exception ",e);
            throw new GwIntegrationException(msg, e);
        }
        return list;
    }
    
    private void connectPort() throws GwIntegrationException {
        if (port == null) {
            try {
                port = ServiceCreationUtil.createService(WSDL,
                        SVC_QNAME_NAMESPACE_URI, SVC_QNAME_LOCALPART,
                        PORT_QNAME_NAMESPACE_URI, PORT_QNAME_LOCALPART,
                        CSNewFNOIAPIPortType.class, REQUEST_TIMEOUT, CONNECT_TIMEOUT);
            } catch (WebServiceException e) {
                logger.error("could not create service", e);
                claimsLog.error("ErrorMessage="+"\"Could not create service"+"\" Task=ConnectToPort Status=Failed ErrorType=WebServiceException ",e);
                throw new GwIntegrationException(
                        "unable to create first notice of injury web service for class CSNewFNOIAPIPortType", e);

            } catch (MalformedURLException e) {
                logger.error("Malformed URL creating first notice of injury web service for class CSNewFNOIAPIPortType ");
                claimsLog.error("ErrorMessage="+"\"Malformed URL creating first notice of injury web service for class CSNewFNOIAPIPortType"+"\" Task=ConnectToPort Status=Failed ErrorType=MalformedURLException ",e);
                throw new GwIntegrationException("malformed URL while creating "
                        + "first notice of injury web service for class CSNewFNOIAPIPortType", e);
            }
        }
    }
}
