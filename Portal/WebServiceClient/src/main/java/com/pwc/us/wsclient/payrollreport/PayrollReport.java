package com.pwc.us.wsclient.payrollreport;

import com.pwc.us.common.exception.GwAuditIsCompleteException;
import com.pwc.us.common.exception.GwAuditIsEditableException;
import com.pwc.us.common.exception.GwIntegrationException;
import com.pwc.us.common.exception.GwNoResultsFoundException;
import com.pwc.us.ws.client.payrollreport.AuditCanCompleteException_Exception;
import com.pwc.us.ws.client.payrollreport.AuditCanRequestQuoteException_Exception;
import com.pwc.us.ws.client.payrollreport.AuditHasBeenStartedException_Exception;
import com.pwc.us.ws.client.payrollreport.AuditIsCompleteException_Exception;
import com.pwc.us.ws.client.payrollreport.AuditIsEditableException_Exception;
import com.pwc.us.ws.client.payrollreport.AuditIsWaivedException_Exception;
import com.pwc.us.ws.client.payrollreport.PayrollReportAPIPortType;
import com.pwc.us.ws.client.payrollreport.Authentication;
import com.pwc.us.ws.client.payrollreport.FindAuditInformationDetailsResponse;
import com.pwc.us.ws.client.payrollreport.FindAuditInformationsResponse;
import com.pwc.us.ws.client.payrollreport.FindPolicyInformationResponse;
import com.pwc.us.ws.client.payrollreport.FindPolicyNumbersResponse;
import com.pwc.us.ws.client.payrollreport.Locale;
import com.pwc.us.ws.client.payrollreport.NoResultsFoundException_Exception;
import com.pwc.us.ws.client.payrollreport.RequiredFieldException_Exception;
import com.pwc.us.ws.client.payrollreport.SOAPException_Exception;
import com.pwc.us.ws.client.payrollreport.UpdateCoveredEmployees;
import com.pwc.us.ws.client.payrollreport.UpdateCoveredEmployeesAndCalculatePremiums;
import com.pwc.us.ws.client.payrollreport.XmlException_Exception;
import com.pwc.us.wsclient.config.DefaultHeaderValues;
import com.pwc.us.wsclient.config.ServiceCreationUtil;
import com.pwc.us.common.utils.JndiUtils;
import com.pwc.us.ws.client.payrollreport.CSPremiumDetails;
import com.pwc.us.ws.client.payrollreport.FindPolicyStartAndEndDatesResponse;
import com.pwc.us.ws.client.payrollreport.UpdateAuditInformationDetails;
import com.pwc.us.ws.client.payrollreport.UpdateAuditInformationDetailsResponse;
import com.pwc.us.ws.client.payrollreport.UpdateCoveredEmployeesAndCalculatePremiumsResponse;
import com.pwc.us.ws.client.payrollreport.UpdateCoveredEmployeesAndCompleteAuditProcess;
import com.pwc.us.ws.client.payrollreport.UpdateCoveredEmployeesAndCompleteAuditProcessResponse;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import javax.xml.ws.WebServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 *
 * Robert Snelling <robert.snelling@us.pwc.com>
 */
public class PayrollReport {

    private static String WSDL = JndiUtils.getPolicyCenterUrl() + "/ws/compsource/pc/api/payrollreport/PayrollReportAPI?WSDL";
    private static String SVC_QNAME_NAMESPACE_URI = "http://guidewire.com/compsource/pc/api/payrollreport/PayrollReportAPI";
    private static String SVC_QNAME_LOCALPART = "PayrollReportAPI";
    private static String PORT_QNAME_NAMESPACE_URI = "http://guidewire.com/compsource/pc/api/payrollreport/PayrollReportAPI";
    private static String PORT_QNAME_LOCALPART = "PayrollReportAPISoap11Port";
    private static final int REQUEST_TIMEOUT = 300000;
    private static final int CONNECT_TIMEOUT = 300000;
    private static final Authentication gwAuthentication = new Authentication();
    private static final Locale gwLocale = new Locale();
    private static final Logger logger = LoggerFactory.getLogger(PayrollReport.class);
    private static PayrollReportAPIPortType port = null;

    static {
        gwAuthentication.setUsername(JndiUtils.getGwUser());
        gwAuthentication.setPassword(JndiUtils.getGwPassword());

        gwLocale.setValue(DefaultHeaderValues.LOCALE);
    }

    public FindPolicyNumbersResponse.Return findPolicyNumbers(String accountNumber) throws GwIntegrationException {
        FindPolicyNumbersResponse.Return response = null;
        try {
            connectPort();
            response = port.findPolicyNumbers(accountNumber, gwAuthentication, gwLocale);
        } catch (RequiredFieldException_Exception e) {
            String message = "Caught Required Field exception trying to find Policy Numbers.";
            logger.error(message, e);
            throw new GwIntegrationException(message, e);
        } catch (SOAPException_Exception e) {
            String message = "Caught SOAP exception trying to find Policy Numbers.";
            logger.error(message, e);
            throw new GwIntegrationException(message, e);
        } catch (XmlException_Exception e) {
            String message = "Caught XML conversion exception trying to find Policy Numbers.";
            logger.error(message, e);
            throw new GwIntegrationException(message, e);
        }
        return response;
    }

    public boolean policyDateIsValid(String policyNumber, String fnoiDate) throws GwIntegrationException {
        boolean response = false;
        try {
            connectPort();
            response = port.policyDateIsValid(policyNumber, fnoiDate, gwAuthentication, gwLocale);

        } catch (RequiredFieldException_Exception e) {
            String message = "Caught Required Field exception trying to find Policy Numbers.";
            logger.error(message, e);
            throw new GwIntegrationException(message, e);

        } catch (SOAPException_Exception e) {
            String message = "Caught SOAP exception trying to find Policy Numbers.";
            logger.error(message, e);
            throw new GwIntegrationException(message, e);
        } catch (XmlException_Exception e) {
            String message = "Caught XML conversion exception trying to find Policy Numbers.";
            logger.error(message, e);
            throw new GwIntegrationException(message, e);
        }
        return response;
    }

    public FindPolicyStartAndEndDatesResponse.Return findPolicyStartAndEndDates(String policyNumber) throws GwIntegrationException {
        FindPolicyStartAndEndDatesResponse.Return response = null;
        try {
            connectPort();
            response = port.findPolicyStartAndEndDates(policyNumber, gwAuthentication, gwLocale);
        } catch (RequiredFieldException_Exception e) {
            String message = "Caught Required Field exception trying to find Policy Dates.";
            logger.error(message, e);
            throw new GwIntegrationException(message, e);
        } catch (SOAPException_Exception e) {
            String message = "Caught SOAP exception trying to find Policy Dates.";
            logger.error(message, e);
            throw new GwIntegrationException(message, e);
        } catch (XmlException_Exception e) {
            String message = "Caught XML conversion exception trying to find Policy Dates.";
            logger.error(message, e);
            throw new GwIntegrationException(message, e);
        }
        return response;
    }

    public FindPolicyInformationResponse.Return findPolicyInformation(String policyNumber) throws GwIntegrationException {
        FindPolicyInformationResponse.Return response = null;
        try {
            connectPort();
            response = port.findPolicyInformation(policyNumber, gwAuthentication, gwLocale);
        } catch (RequiredFieldException_Exception e) {
            String message = "Caught Required Field exception trying to find Policy Information.";
            logger.error(message, e);
            throw new GwIntegrationException(message, e);
        } catch (SOAPException_Exception e) {
            String message = "Caught SOAP exception trying to find Policy Information.";
            logger.error(message, e);
            throw new GwIntegrationException(message, e);
        } catch (XmlException_Exception e) {
            String message = "Caught XML conversion exception trying to find Policy Information.";
            logger.error(message, e);
            throw new GwIntegrationException(message, e);
        }
        return response;
    }

    public void completeAuditProcess(String auditInformationPublicId) throws GwIntegrationException {
        try {
            connectPort();
            port.completeAuditProcess(auditInformationPublicId, gwAuthentication, gwLocale);
        } catch (AuditCanCompleteException_Exception e) {
            String message = "Caught Audit Can Complete exception trying to Complete Audit Process.";
            logger.error(message, e);
            throw new GwIntegrationException(message, e);
        } catch (AuditCanRequestQuoteException_Exception e) {
            String message = "Caught Audit Can Request Quote exception trying to Complete Audit Process.";
            logger.error(message, e);
            throw new GwIntegrationException(message, e);
        } catch (NoResultsFoundException_Exception e) {
            String message = "Caught No Results Found exception trying to Complete Audit Process.";
            logger.error(message, e);
            throw new GwIntegrationException(message, e);
        } catch (SOAPException_Exception e) {
            String message = "Caught SOAP exception trying to Complete Audit Process.";
            logger.error(message, e);
            throw new GwIntegrationException(message, e);
        } catch (RequiredFieldException_Exception e) {
            String message = "Caught Required Field exception trying to Complete Audit Process.";
            logger.error(message, e);
            throw new GwIntegrationException(message, e);
        } catch (XmlException_Exception e) {
            String message = "Caught XML conversion exception trying to Complete Audit Process.";
            logger.error(message, e);
            throw new GwIntegrationException(message, e);
        } catch (AuditHasBeenStartedException_Exception e) {
            String message = "Caught Audit Has Been Started exception trying to Complete Audit Process.";
            logger.error(message, e);
            throw new GwIntegrationException(message, e);
        } catch (AuditIsCompleteException_Exception e) {
            String message = "Caught Audit Is Complete exception trying to Complete Audit Process.";
            logger.error(message, e);
            throw new GwIntegrationException(message, e);
        } catch (AuditIsEditableException_Exception e) {
            String message = "Caught Audit Is Editable exception trying to Complete Audit Process.";
            logger.error(message, e);
            throw new GwIntegrationException(message, e);
        } catch (AuditIsWaivedException_Exception e) {
            String message = "Caught Audit Is Waived exception trying to Complete Audit Process.";
            logger.error(message, e);
            throw new GwIntegrationException(message, e);
        }
    }

    public FindAuditInformationsResponse.Return findAuditInformations(String policyNumber, Boolean includeFinalAudits) throws GwIntegrationException {
        FindAuditInformationsResponse.Return response = null;
        try {
            connectPort();
            response = port.findAuditInformations(policyNumber, includeFinalAudits, gwAuthentication, gwLocale);
        } catch (RequiredFieldException_Exception e) {
            String message = "Caught Required Field exception trying to find Audit Information.";
            logger.error(message, e);
            throw new GwIntegrationException(message, e);
        } catch (SOAPException_Exception e) {
            String message = "Caught SOAP exception trying to find Audit Information.";
            logger.error(message, e);
            throw new GwIntegrationException(message, e);
        } catch (XmlException_Exception e) {
            String message = "Caught XML conversion exception trying to find Audit Information.";
            logger.error(message, e);
            throw new GwIntegrationException(message, e);
        }
        return response;
    }

    public FindAuditInformationDetailsResponse.Return findAuditInformationDetails(String auditInformationPublicId) throws GwIntegrationException,
            GwNoResultsFoundException {
        FindAuditInformationDetailsResponse.Return response = null;
        try {
            connectPort();
            response = port.findAuditInformationDetails(auditInformationPublicId, gwAuthentication, gwLocale);
        } catch (NoResultsFoundException_Exception e) {
            String message = "Caught No Results Found Exception trying to find Audit Information Details.";
            logger.error(message, e);
            throw new GwNoResultsFoundException(message, e);
        } catch (AuditHasBeenStartedException_Exception e) {
            String message = "Caught Audit Has Been Started exception trying to find Audit Information Details.";
            logger.error(message, e);
            throw new GwIntegrationException(message, e);
        } catch (RequiredFieldException_Exception e) {
            String message = "Caught Required Field exception trying to find Audit Information Details.";
            logger.error(message, e);
            throw new GwIntegrationException(message, e);
        } catch (SOAPException_Exception e) {
            String message = "Caught SOAP exception trying to find Audit Information Details.";
            logger.error(message, e);
            throw new GwIntegrationException(message, e);
        } catch (XmlException_Exception e) {
            String message = "Caught XML conversion exception trying to find Audit Information Details.";
            logger.error(message, e);
            throw new GwIntegrationException(message, e);
        }
        return response;
    }

    public CSPremiumDetails updateAuditInformationDetails(
            String auditInformationPublicId,
           UpdateAuditInformationDetails.CoveredEmployees coveredEmployees,
           UpdateAuditInformationDetails.SupplDiseaseExposures supplDiseaseExposures,
            String auditMethod,
            String auditDescription,
            Boolean requestQuote,
            Boolean completeAudit) throws GwIntegrationException  {
        UpdateAuditInformationDetailsResponse.Return r = null;
        try {
            connectPort();
          r =  port.updateAuditInformationDetails(auditInformationPublicId, coveredEmployees, supplDiseaseExposures, 
                    auditMethod, auditDescription, requestQuote, completeAudit, 
                    gwAuthentication, gwLocale);
          
        } catch (GwIntegrationException ex) {
            String message = "Caught GwIntegrationException trying to perform updateAuditInformationDetails.";
            logger.error(message, ex);
            throw new GwIntegrationException(message, ex);
        } catch (AuditCanCompleteException_Exception ex) {
            String message = "Caught GwIntegrationException trying to perform updateAuditInformationDetails.";
            logger.error(message, ex);
        } catch (AuditCanRequestQuoteException_Exception ex) {
            String message = "Caught AuditCanRequestQuoteException_Exception trying to perform updateAuditInformationDetails.";
            logger.error(message, ex);
        } catch (AuditHasBeenStartedException_Exception ex) {
            String message = "Caught GwIntegrationException trying to perform updateAuditInformationDetails.";
            logger.error(message, ex);
        } catch (AuditIsCompleteException_Exception ex) {
            String message = "Caught AuditHasBeenStartedException_Exception trying to perform updateAuditInformationDetails.";
            logger.error(message, ex);
        } catch (AuditIsEditableException_Exception ex) {
            String message = "Caught AuditIsEditableException_Exception trying to perform updateAuditInformationDetails.";
            logger.error(message, ex);
        } catch (AuditIsWaivedException_Exception ex) {
            String message = "Caught AuditIsWaivedException_Exception trying to perform updateAuditInformationDetails.";
            logger.error(message, ex);
        } catch (NoResultsFoundException_Exception ex) {
            String message = "Caught NoResultsFoundException_Exception trying to perform updateAuditInformationDetails.";
            logger.error(message, ex);
        } catch (RequiredFieldException_Exception ex) {
            String message = "Caught RequiredFieldException_Exception trying to perform updateAuditInformationDetails.";
            logger.error(message, ex);
        } catch (SOAPException_Exception ex) {
            String message = "Caught SOAPException_Exception trying to perform updateAuditInformationDetails.";
            logger.error(message, ex);
        } catch (XmlException_Exception ex) {
            String message = "Caught XmlException_Exception trying to perform updateAuditInformationDetails.";
            logger.error(message, ex);
        }
        return r.getCSPremiumDetails();
    }

  
    @Deprecated
    public void updateCoveredEmployees(String auditInformationPublicId, UpdateCoveredEmployees.CoveredEmployees coveredEmployees) throws GwIntegrationException {
        try {
            connectPort();
            port.updateCoveredEmployees(auditInformationPublicId, coveredEmployees, gwAuthentication, gwLocale);
        } catch (AuditIsCompleteException_Exception e) {
            String message = "Caught Audit Is Complete exception trying to update Covered Employees.";
            logger.error(message, e);
            throw new GwIntegrationException(message, e);
        } catch (AuditIsEditableException_Exception e) {
            String message = "Caught Audit Is Editable exception trying to update Covered Employees.";
            logger.error(message, e);
            throw new GwIntegrationException(message, e);
        } catch (AuditIsWaivedException_Exception e) {
            String message = "Caught Audit Is Waived exception trying to update Covered Employees.";
            logger.error(message, e);
            throw new GwIntegrationException(message, e);
        } catch (AuditHasBeenStartedException_Exception e) {
            String message = "Caught Audit Has Been Started exception trying to update Covered Employees.";
            logger.error(message, e);
            throw new GwIntegrationException(message, e);
        } catch (RequiredFieldException_Exception e) {
            String message = "Caught Required Field exception trying to update Covered Employees.";
            logger.error(message, e);
            throw new GwIntegrationException(message, e);
        } catch (NoResultsFoundException_Exception e) {
            String message = "Caught No Results Found exception trying to update Covered Employees.";
            logger.error(message, e);
            throw new GwIntegrationException(message, e);
        } catch (SOAPException_Exception e) {
            String message = "Caught SOAP exception trying to update Covered Employees.";
            logger.error(message, e);
            throw new GwIntegrationException(message, e);
        } catch (XmlException_Exception e) {
            String message = "Caught XML conversion exception trying to update Covered Employees.";
            logger.error(message, e);
            throw new GwIntegrationException(message, e);
        }
    }

    @Deprecated
    public UpdateCoveredEmployeesAndCalculatePremiumsResponse.Return updateCoveredEmployeesAndCalculatePremiums(String auditInformationPublicId, UpdateCoveredEmployeesAndCalculatePremiums.CoveredEmployees coveredEmployees, String comment) throws GwIntegrationException {
        UpdateCoveredEmployeesAndCalculatePremiumsResponse.Return response = null;

        try {
            connectPort();
            response = port.updateCoveredEmployeesAndCalculatePremiums(auditInformationPublicId, coveredEmployees, comment, gwAuthentication, gwLocale);
        } catch (AuditIsCompleteException_Exception e) {
            String message = "Caught Audit Is Complete exception trying to update Covered Employees And Calculate Premiums.";
            logger.error(message, e);
            throw new GwIntegrationException(message, e);
        } catch (AuditCanRequestQuoteException_Exception e) {
            String message = "Caught Audit Can Request Quote exception trying to update Covered Employees And Calculate Premiums.";
            logger.error(message, e);
            throw new GwIntegrationException(message, e);
        } catch (AuditIsEditableException_Exception e) {
            String message = "Caught Audit Is Editable exception trying to update Covered Employees And Calculate Premiums..";
            logger.error(message, e);
            throw new GwIntegrationException(message, e);
        } catch (AuditIsWaivedException_Exception e) {
            String message = "Caught Audit Is Waived exception trying to update Covered Employees And Calculate Premiums..";
            logger.error(message, e);
            throw new GwIntegrationException(message, e);
        } catch (AuditHasBeenStartedException_Exception e) {
            String message = "Caught Audit Has Been Started exception trying to update Covered Employees And Calculate Premiums..";
            logger.error(message, e);
            throw new GwIntegrationException(message, e);
        } catch (RequiredFieldException_Exception e) {
            String message = "Caught Required Field exception trying to update Covered Employees And Calculate Premiums..";
            logger.error(message, e);
            throw new GwIntegrationException(message, e);
        } catch (NoResultsFoundException_Exception e) {
            String message = "Caught No Results Found exception trying to update Covered Employees And Calculate Premiums.";
            logger.error(message, e);
            throw new GwIntegrationException(message, e);
        } catch (SOAPException_Exception e) {
            String message = "Caught SOAP exception trying to update Covered Employees And Calculate Premiums.";
            logger.error(message, e);
            throw new GwIntegrationException(message, e);
        } catch (XmlException_Exception e) {
            String message = "Caught XML conversion exception trying to update Covered Employees And Calculate Premiums.";
            logger.error(message, e);
            throw new GwIntegrationException(message, e);
        }
        return response;
    }

    @Deprecated
    public UpdateCoveredEmployeesAndCompleteAuditProcessResponse.Return updateCoveredEmployeesAndCompleteAuditProcess(String auditInformationPublicId, UpdateCoveredEmployeesAndCompleteAuditProcess.CoveredEmployees coveredEmployees, String comment) throws GwIntegrationException {
        UpdateCoveredEmployeesAndCompleteAuditProcessResponse.Return response = null;
        try {
            connectPort();
            response = port.updateCoveredEmployeesAndCompleteAuditProcess(auditInformationPublicId, coveredEmployees, comment, gwAuthentication, gwLocale);
        } catch (AuditCanCompleteException_Exception e) {
            String message = "Caught Audit Can Complete exception trying to update and finalize Covered Employees.";
            logger.error(message, e);
            throw new GwIntegrationException(message, e);
        } catch (AuditCanRequestQuoteException_Exception e) {
            String message = "Caught Audit Can Request Quote exception trying to update and finalize Covered Employees.";
            logger.error(message, e);
            throw new GwIntegrationException(message, e);
        } catch (AuditIsCompleteException_Exception e) {
            String message = "Caught Audit Is Complete exception trying to update and finalize Covered Employees.";
            logger.error(message, e);
            throw new GwIntegrationException(message, e);
        } catch (AuditIsEditableException_Exception e) {
            String message = "Caught Audit Is Editable exception trying to update and finalize Covered Employees.";
            logger.error(message, e);
            throw new GwIntegrationException(message, e);
        } catch (AuditIsWaivedException_Exception e) {
            String message = "Caught Audit Is Waived exception trying to update and finalize Covered Employees.";
            logger.error(message, e);
            throw new GwIntegrationException(message, e);
        } catch (AuditHasBeenStartedException_Exception e) {
            String message = "Caught Audit Has Been Started exception trying to update and finalize Covered Employees.";
            logger.error(message, e);
            throw new GwIntegrationException(message, e);
        } catch (RequiredFieldException_Exception e) {
            String message = "Caught Required Field exception trying to update and finalize Covered Employees.";
            logger.error(message, e);
            throw new GwIntegrationException(message, e);
        } catch (NoResultsFoundException_Exception e) {
            String message = "Caught No Results Found exception trying to update and finalize Covered Employees.";
            logger.error(message, e);
            throw new GwIntegrationException(message, e);
        } catch (SOAPException_Exception e) {
            String message = "Caught SOAP exception trying to update and finalize Covered Employees.";
            logger.error(message, e);
            throw new GwIntegrationException(message, e);
        } catch (XmlException_Exception e) {
            String message = "Caught XML conversion exception trying to update and finalize Covered Employees.";
            logger.error(message, e);
            throw new GwIntegrationException(message, e);
        }
        return response;
    }

    @Deprecated /*  */
    public void updateCoveredEmployee(String auditInformationPublicId, Long coveredEmployeeFixedId, Integer amount) throws GwIntegrationException {
        try {
            connectPort();
            port.updateCoveredEmployee(auditInformationPublicId, coveredEmployeeFixedId, amount, gwAuthentication, gwLocale);
        } catch (AuditIsCompleteException_Exception e) {
            String message = "Caught Audit Is Complete exception trying to update Covered Employee.";
            logger.error(message, e);
            throw new GwIntegrationException(message, e);
        } catch (AuditIsEditableException_Exception e) {
            String message = "Caught Audit Is Editable exception trying to update Covered Employee.";
            logger.error(message, e);
            throw new GwIntegrationException(message, e);
        } catch (AuditIsWaivedException_Exception e) {
            String message = "Caught Audit Is Waived exception trying to update Covered Employee.";
            logger.error(message, e);
            throw new GwIntegrationException(message, e);
        } catch (AuditHasBeenStartedException_Exception e) {
            String message = "Caught Audit Has Been Started exception trying to update Covered Employee.";
            logger.error(message, e);
            throw new GwIntegrationException(message, e);
        } catch (RequiredFieldException_Exception e) {
            String message = "Caught Required Field exception trying to update Covered Employee.";
            logger.error(message, e);
            throw new GwIntegrationException(message, e);
        } catch (NoResultsFoundException_Exception e) {
            String message = "Caught No Results Found exception trying to update Covered Employee.";
            logger.error(message, e);
            throw new GwIntegrationException(message, e);
        } catch (SOAPException_Exception e) {
            String message = "Caught SOAP exception trying to update Covered Employee.";
            logger.error(message, e);
            throw new GwIntegrationException(message, e);
        } catch (XmlException_Exception e) {
            String message = "Caught XML conversion exception trying to update Covered Employee.";
            logger.error(message, e);
            throw new GwIntegrationException(message, e);
        }
    }

    public BigDecimal calculatePremiums(String auditInformationPublicId) throws GwIntegrationException, GwAuditIsCompleteException, GwAuditIsEditableException {
        BigDecimal response = null;

        try {
            connectPort();
            response = port.calculatePremiums(auditInformationPublicId, gwAuthentication, gwLocale);
        } catch (AuditCanRequestQuoteException_Exception e) {
            String message = "Caught Audit Can Request Quote exception trying to Calculate Premiums.";
            logger.error(message, e);
            throw new GwIntegrationException(message, e);
        } catch (NoResultsFoundException_Exception e) {
            String message = "Caught No Results Found exception trying to Calculate Premiums.";
            logger.error(message, e);
            throw new GwIntegrationException(message, e);
        } catch (SOAPException_Exception e) {
            String message = "Caught SOAP exception trying to Calculate Premiums.";
            logger.error(message, e);
            throw new GwIntegrationException(message, e);
        } catch (RequiredFieldException_Exception e) {
            String message = "Caught Required Field exception trying to Calculate Premiums.";
            logger.error(message, e);
            throw new GwIntegrationException(message, e);
        } catch (XmlException_Exception e) {
            String message = "Caught XML conversion exception trying to Calculate Premiums.";
            logger.error(message, e);
            throw new GwIntegrationException(message, e);
        } catch (AuditHasBeenStartedException_Exception e) {
            String message = "Caught Audit Has Been Started exception trying to Calculate Premiums.";
            logger.error(message, e);
            throw new GwIntegrationException(message, e);
        } catch (AuditIsCompleteException_Exception e) {
            String message = "Caught Audit Is Complete exception trying to Calculate Premiums.";
            logger.error(message, e);
            throw new GwAuditIsCompleteException(message, e);
        } catch (AuditIsEditableException_Exception e) {
            String message = "Caught Audit Is Editable exception trying to Calculate Premiums.";
            logger.error(message, e);
            throw new GwAuditIsEditableException(message, e);
        } catch (AuditIsWaivedException_Exception e) {
            String message = "Caught Audit Is Waived exception trying to Calculate Premiums.";
            logger.error(message, e);
            throw new GwIntegrationException(message, e);
        }
        return response;
    }

    private void connectPort() throws GwIntegrationException {
        if (port == null) {
            try {
                port = ServiceCreationUtil.createService(WSDL,
                        SVC_QNAME_NAMESPACE_URI, SVC_QNAME_LOCALPART,
                        PORT_QNAME_NAMESPACE_URI, PORT_QNAME_LOCALPART,
                        PayrollReportAPIPortType.class, REQUEST_TIMEOUT, CONNECT_TIMEOUT);
            } catch (WebServiceException e) {
                logger.error("could not create service", e);
                throw new GwIntegrationException(
                        "unable to create first notice of injury web service for class PayrollReportAPIPortType", e);

            } catch (MalformedURLException e) {
                logger.error("Malformed URL creating first notice of injury web service for class PayrollReportAPIPortType ");
                throw new GwIntegrationException("malformed URL while creating "
                        + "request a quote web service for class PayrollReportAPIPortType", e);
            }
        }
    }

 
}
