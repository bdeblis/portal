package com.pwc.us.common;

import com.pwc.us.common.exception.GwAuditIsCompleteException;
import com.pwc.us.common.exception.GwAuditIsEditableException;
import com.pwc.us.common.exception.GwIntegrationException;
import com.pwc.us.common.exception.GwNoResultsFoundException;
import com.pwc.us.common.model.payrollreport.CSAuditInformationDetailsPR;
import com.pwc.us.common.model.payrollreport.CSPremiumDetailsPR;
import com.pwc.us.common.model.payrollreport.CSUpdateCoveredEmployeeResponsePR;
import com.pwc.us.common.model.payrollreport.FindAuditInformationDetailsResponsePR;
import com.pwc.us.common.model.payrollreport.FindAuditInformationsResponsePR;
import com.pwc.us.common.model.payrollreport.FindPolicyInformationResponsePR;
import com.pwc.us.common.model.payrollreport.FindPolicyNumbersResponsePR;
import com.pwc.us.common.model.payrollreport.FindPolicyStartAndEndDatesResponsePR;
import com.pwc.us.common.model.payrollreport.UpdateCoveredEmployeesAndCalculatePremiumsPR;
import com.pwc.us.common.model.payrollreport.UpdateCoveredEmployeesPR;

/**
 *
 * @author Robert Snelling (robert.snelling@us.pwc.com)
 */
public interface PayrollReport {
    
    public FindPolicyStartAndEndDatesResponsePR.Return findPolicyStartAndEndDates(String policyNumber) throws GwIntegrationException;
    
    public boolean policyDateIsValid(String policyNumber, String fnoiDate)  throws GwIntegrationException;
    
    public FindPolicyNumbersResponsePR.Return findPolicyNumbers(String accountNumber) throws GwIntegrationException ;

    public FindPolicyInformationResponsePR.Return findPolicyInformation(String policyNumber) throws GwIntegrationException ;

    public FindAuditInformationsResponsePR.Return findAuditInformations(String policyNumber, Boolean includeFinalAudits) throws GwIntegrationException ;

    public FindAuditInformationDetailsResponsePR.Return findAuditInformationDetails(String auditInformationPublicId) throws GwIntegrationException, 
            GwNoResultsFoundException;

    public void updateCoveredEmployees(String auditInformationPublicId, UpdateCoveredEmployeesPR.CoveredEmployees coveredEmployees) throws GwIntegrationException;

    public CSUpdateCoveredEmployeeResponsePR updateCoveredEmployeesAndCalculatePremiums(String auditInformationPublicId, UpdateCoveredEmployeesAndCalculatePremiumsPR.CoveredEmployees coveredEmployees, String comment) throws GwIntegrationException;;

    public void updateCoveredEmployee(String auditInformationPublicId, Long coveredEmployeeFixedId, Integer amount) throws GwIntegrationException;

    public double calculatePremiums(String auditInformationPublicId) throws GwIntegrationException, GwAuditIsCompleteException, GwAuditIsEditableException;
    
    public void completeAuditProcess(String auditInformationPublicId) throws GwIntegrationException;
    
    public CSUpdateCoveredEmployeeResponsePR updateCoveredEmployeesAndCompleteAuditProcess(String auditInformationPublicId, UpdateCoveredEmployeesAndCalculatePremiumsPR.CoveredEmployees coveredEmployees, String comment) throws GwIntegrationException;
    
    public CSPremiumDetailsPR updateAuditInformationDetails(
            String auditInformationPublicId,
           CSAuditInformationDetailsPR.CoveredEmployees coveredEmployees,
           CSAuditInformationDetailsPR.SupplDiseaseExposures supplDiseaseExposures,
            String auditMethod,
            String auditDescription,
            Boolean requestQuote,
            Boolean completeAudit) throws GwIntegrationException ;
    
    /**
     * Determines whether or not the policy number is a valid entry in the GW system.
     * We are not testing whether or not the policy is in force.
     * 
     * @param policyNumber
     * @return true if the policy number number exists in PC, false if not.
     * @throws GwIntegrationException 
     */
    public boolean isPolicyNumberValid(String policyNumber) throws GwIntegrationException;
    
    
}
