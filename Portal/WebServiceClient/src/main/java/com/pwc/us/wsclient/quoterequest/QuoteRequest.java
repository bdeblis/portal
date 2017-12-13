package com.pwc.us.wsclient.quoterequest;

import com.pwc.us.common.exception.GwIntegrationException;
import com.pwc.us.ws.client.quoterequest.Authentication;
import com.pwc.us.ws.client.quoterequest.CSNewSubmissionAPIPortType;
import com.pwc.us.ws.client.quoterequest.FieldConversionException_Exception;
import com.pwc.us.ws.client.quoterequest.GetAccountOrgTypeTypeKeysResponse;
import com.pwc.us.ws.client.quoterequest.GetAddlIntRelationshipExtTypeKeysResponse;
import com.pwc.us.ws.client.quoterequest.GetOwnerOfficerRelationshipTypeKeysResponse;
import com.pwc.us.ws.client.quoterequest.Locale;
import com.pwc.us.ws.client.quoterequest.RequiredFieldException_Exception;
import com.pwc.us.ws.client.quoterequest.SOAPException_Exception;
import com.pwc.us.ws.client.quoterequest.TypekeyName;
import com.pwc.us.ws.client.quoterequest.XmlException_Exception;
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
 * @author Roger Turnau - <roger.turnau@us.pwc.com>
 */
public class QuoteRequest {

    private static String WSDL = JndiUtils.getPolicyCenterUrl() + "/ws/compsource/pc/api/newsubmission/CSNewSubmissionAPI?WSDL";
    private static String SVC_QNAME_NAMESPACE_URI = "http://guidewire.com/compsource/pc/api/newsubmission/CSNewSubmissionAPI";
    private static String SVC_QNAME_LOCALPART = "CSNewSubmissionAPI";
    private static String PORT_QNAME_NAMESPACE_URI = "http://guidewire.com/compsource/pc/api/newsubmission/CSNewSubmissionAPI";
    private static String PORT_QNAME_LOCALPART = "CSNewSubmissionAPISoap11Port";
    private static final int REQUEST_TIMEOUT = 300000;
    private static final int CONNECT_TIMEOUT = 300000;
    private static final Authentication gwAuthentication = new Authentication();
    private static final Locale gwLocale = new Locale();
    private static final Logger logger = LoggerFactory.getLogger(QuoteRequest.class);
    private static CSNewSubmissionAPIPortType port = null;

    static {
        gwAuthentication.setUsername(JndiUtils.getGwUser());
        gwAuthentication.setPassword(JndiUtils.getGwPassword());

        gwLocale.setValue(DefaultHeaderValues.LOCALE);
    }

    public String createNewSubmission(String accountDataXML, String submissionDataXML) throws GwIntegrationException {
        String response = null;
        try {
            connectPort();
            response = port.createNewSubmission(accountDataXML, submissionDataXML, gwAuthentication, gwLocale);
        } catch (FieldConversionException_Exception e) {
            String message = "Caught Field conversion exception trying to create new submission.";
            logger.error(message, e);
            throw new GwIntegrationException(message, e);
        } catch (RequiredFieldException_Exception e) {
            String message = "Caught Required Field conversion exception trying to create new submission.";
            logger.error(message, e);
            throw new GwIntegrationException(message, e);
        } catch (SOAPException_Exception e) {
            String message = "Caught SOAP exception trying to create new submission.";
            logger.error(message, e);
            throw new GwIntegrationException(message, e);
        } catch (XmlException_Exception e) {
            String message = "Caught XML conversion exception trying to create new submission.";
            logger.error(message, e);
            throw new GwIntegrationException(message, e);
        }
        return response;
    }

    public List<TypekeyName> getAccountOrgKeys() throws GwIntegrationException {
        List<TypekeyName> orgKeys = null;
        GetAccountOrgTypeTypeKeysResponse.Return response = null;
        connectPort();

        response = port.getAccountOrgTypeTypeKeys(gwAuthentication, gwLocale);
        orgKeys = response.getEntry();

        return orgKeys;
    }

    public List<TypekeyName> getAddlIntRelationships() throws GwIntegrationException {
        List<TypekeyName> addlIntRelationships = null;
        GetAddlIntRelationshipExtTypeKeysResponse.Return response = null;
        connectPort();

        response = port.getAddlIntRelationshipExtTypeKeys(gwAuthentication, gwLocale);
        addlIntRelationships = response.getEntry();
        return addlIntRelationships;
    }

    public List<TypekeyName> getOwnerOfficerRelationships() throws GwIntegrationException {
        List<TypekeyName> offRelationships = null;
        GetOwnerOfficerRelationshipTypeKeysResponse.Return response = null;
        connectPort();

        response = port.getOwnerOfficerRelationshipTypeKeys(gwAuthentication, gwLocale);
        offRelationships = response.getEntry();
        return offRelationships;
    }

    private void connectPort() throws GwIntegrationException {
        if (port == null) {
            try {
                port = ServiceCreationUtil.createService(WSDL,
                        SVC_QNAME_NAMESPACE_URI, SVC_QNAME_LOCALPART,
                        PORT_QNAME_NAMESPACE_URI, PORT_QNAME_LOCALPART,
                        CSNewSubmissionAPIPortType.class, REQUEST_TIMEOUT, CONNECT_TIMEOUT);
            } catch (WebServiceException e) {
                logger.error("could not create service", e);
                throw new GwIntegrationException(
                        "unable to create first notice of injury web service for class CSNewSubmissionAPIPortType", e);

            } catch (MalformedURLException e) {
                logger.error("Malformed URL creating first notice of injury web service for class CSNewSubmissionAPIPortType ");
                throw new GwIntegrationException("malformed URL while creating "
                        + "request a quote web service for class CSNewSubmissionAPIPortType", e);
            }
        }
    }
}
