/*
 * APIImpl.java
 */
package com.pwc.us.impl;

import com.pwc.us.common.PayrollReport;
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
import com.pwc.us.common.utils.NullChecker;
import com.pwc.us.ws.client.payrollreport.CSCoveredEmployee;
import com.pwc.us.ws.client.payrollreport.CSPremiumDetails;
import com.pwc.us.ws.client.payrollreport.CSSupplDiseaseExposure;
import com.pwc.us.ws.client.payrollreport.ObjectFactory;
import com.pwc.us.ws.client.payrollreport.UpdateAuditInformationDetails;
import com.pwc.us.wsclient.payrollreport.PayrollReportBuilder;
import java.util.logging.Logger;

/**
 *
 * @author generated-by-archetype
 *
 */
public class PayrollReportImpl implements PayrollReport {

    private final ObjectFactory factory = new ObjectFactory();
    private static final Logger logger = Logger.getLogger(PayrollReportImpl.class.getName());
    PayrollReportBuilder builder = new PayrollReportBuilder();

    public FindPolicyNumbersResponsePR.Return findPolicyNumbers(String accountNumber) throws GwIntegrationException {
        FindPolicyNumbersResponsePR.Return responsePR = builder.findPolicyNumbers(accountNumber);
        return responsePR;
    }
    
     public FindPolicyStartAndEndDatesResponsePR.Return findPolicyStartAndEndDates(String policyNumber) throws GwIntegrationException{
         FindPolicyStartAndEndDatesResponsePR.Return responsePr = builder.findPolicyStartAndEndDates(policyNumber);
         return responsePr;
     }
     
         
    public boolean policyDateIsValid(String policyNumber, String date) throws GwIntegrationException {
        return builder.policyDateIsValid(policyNumber, date);
    }


    public FindPolicyInformationResponsePR.Return findPolicyInformation(String policyNumber) throws GwIntegrationException {
        FindPolicyInformationResponsePR.Return responsePR = builder.findPolicyInformation(policyNumber);
        return responsePR;
    }

    public FindAuditInformationsResponsePR.Return findAuditInformations(String policyNumber, Boolean includeFinalAudits) throws GwIntegrationException {
        FindAuditInformationsResponsePR.Return responsePR = builder.findAuditInformations(policyNumber, includeFinalAudits);
        return responsePR;
    }

    public FindAuditInformationDetailsResponsePR.Return findAuditInformationDetails(String auditInformationPublicId) throws GwIntegrationException, 
            GwNoResultsFoundException {
        FindAuditInformationDetailsResponsePR.Return responsePR = builder.findAuditInformationDetails(auditInformationPublicId);
        return responsePR;
    }

    public void updateCoveredEmployees(String auditInformationPublicId, UpdateCoveredEmployeesPR.CoveredEmployees coveredEmployeesPR) throws GwIntegrationException {
        builder.updateCoveredEmployees(auditInformationPublicId, coveredEmployeesPR);
    }

    public CSUpdateCoveredEmployeeResponsePR updateCoveredEmployeesAndCalculatePremiums(String auditInformationPublicId, UpdateCoveredEmployeesAndCalculatePremiumsPR.CoveredEmployees coveredEmployeesPR, String comment) throws GwIntegrationException {
        CSUpdateCoveredEmployeeResponsePR response = builder.updateCoveredEmployeesAndCalculatePremiums(auditInformationPublicId, coveredEmployeesPR, comment);
        return response;
    }

    public void updateCoveredEmployee(String auditInformationPublicId, Long coveredEmployeeFixedId, Integer amount) throws GwIntegrationException {
            builder.updateCoveredEmployee(auditInformationPublicId, coveredEmployeeFixedId, amount);
    }

    public double calculatePremiums(String auditInformationPublicId) throws GwIntegrationException, GwAuditIsCompleteException, GwAuditIsEditableException {
        double response = builder.calculatePremiums(auditInformationPublicId);
        return response;
    }

    public void completeAuditProcess(String auditInformationPublicId) throws GwIntegrationException{
        builder.completeAuditProcess(auditInformationPublicId);
    }

    public boolean isPolicyNumberValid(String policyNumber) throws GwIntegrationException {
        return builder.isPolicyNumberValid(policyNumber);
    }

    public CSUpdateCoveredEmployeeResponsePR updateCoveredEmployeesAndCompleteAuditProcess(String auditInformationPublicId, UpdateCoveredEmployeesAndCalculatePremiumsPR.CoveredEmployees coveredEmployees, String comment) throws GwIntegrationException {
        return builder.updateCoveredEmployeesAndCompleteAuditProcess(auditInformationPublicId, coveredEmployees, comment);
    }
    
    public CSPremiumDetailsPR updateAuditInformationDetails(String auditInformationPublicId, CSAuditInformationDetailsPR.CoveredEmployees coveredEmployees, CSAuditInformationDetailsPR.SupplDiseaseExposures supplDiseaseExposures, String auditMethod, String auditDescription, Boolean requestQuote, Boolean completeAudit) throws GwIntegrationException {
        CSPremiumDetails r = null;
       r = builder.updateAuditInformationDetails(auditInformationPublicId, convertCSAudit2UpdateAudit(coveredEmployees), convertCSAudit2UpdateAudit(supplDiseaseExposures), auditMethod, auditDescription, requestQuote, completeAudit);
        CSPremiumDetailsPR cpr = new CSPremiumDetailsPR();
        cpr.setEstimatedAnnualPremium(r.getEstimatedAnnualPremium().getValue());
        CSPremiumDetailsPR.AuditInformation audit = new CSPremiumDetailsPR.AuditInformation();
        audit.setPublicID(auditInformationPublicId);
        cpr.setAuditInformation(audit);
        return cpr;
    }

    protected UpdateAuditInformationDetails.SupplDiseaseExposures convertCSAudit2UpdateAudit(CSAuditInformationDetailsPR.SupplDiseaseExposures supplDiseaseExposures) {
        UpdateAuditInformationDetails.SupplDiseaseExposures coveredDiseasePR = new UpdateAuditInformationDetails.SupplDiseaseExposures();
        for (CSAuditInformationDetailsPR.SupplDiseaseExposures.Entry r : supplDiseaseExposures.getEntry()) {

            if (NullChecker.isNotNullOrEmpty(r)) {
                UpdateAuditInformationDetails.SupplDiseaseExposures.Entry entryPR = new UpdateAuditInformationDetails.SupplDiseaseExposures.Entry();
                CSSupplDiseaseExposure css = new CSSupplDiseaseExposure();
                CSSupplDiseaseExposure.SupplDiseaseExposure supp = new CSSupplDiseaseExposure.SupplDiseaseExposure();

                CSSupplDiseaseExposure.SupplDiseaseExposure.FixedId fixedId = new CSSupplDiseaseExposure.SupplDiseaseExposure.FixedId();
                if (NullChecker.isNotNullOrEmpty(r.getSupplDiseaseExposure().getFixedId())) {
                    fixedId.setObjValue(factory.createCSSupplDiseaseExposureSupplDiseaseExposureFixedIdObjValue(r.getSupplDiseaseExposure().getFixedId().getObjValue()));
                }
          
                if(NullChecker.isNotNullOrEmpty(r.getSupplDiseaseExposure().getAuditedAmount())){
                    supp.setAuditedAmount(factory.createCSSupplDiseaseExposureSupplDiseaseExposureAuditedAmount(r.getSupplDiseaseExposure().getAuditedAmount()));
                }else{
                  supp.setAuditedAmount(factory.createCSSupplDiseaseExposureSupplDiseaseExposureAuditedAmount(0));
                }
                
                supp.setFixedId(factory.createCSSupplDiseaseExposureSupplDiseaseExposureFixedId(fixedId));
                css.setSupplDiseaseExposure(factory.createCSSupplDiseaseExposureSupplDiseaseExposure(supp));

                entryPR.setCSSupplDiseaseExposure(css);
                coveredDiseasePR.getEntry().add(entryPR);

            }
        }
        return coveredDiseasePR;
    }

    public UpdateAuditInformationDetails.CoveredEmployees convertCSAudit2UpdateAudit(CSAuditInformationDetailsPR.CoveredEmployees coveredEmployees) {
        UpdateAuditInformationDetails.CoveredEmployees coveredEmployeesPR = new UpdateAuditInformationDetails.CoveredEmployees();
        for (CSAuditInformationDetailsPR.CoveredEmployees.Entry r : coveredEmployees.getEntry()) {
            UpdateAuditInformationDetails.CoveredEmployees.Entry entryPR = new UpdateAuditInformationDetails.CoveredEmployees.Entry();
            CSCoveredEmployee ce = new CSCoveredEmployee();
            CSCoveredEmployee.CoveredEmployee cov = new CSCoveredEmployee.CoveredEmployee();

            CSCoveredEmployee.CoveredEmployee.FixedId fixedId = new CSCoveredEmployee.CoveredEmployee.FixedId();
            if(NullChecker.isNotNullOrEmpty(r.getCoveredEmployee().getFixedId())){
                fixedId.setObjValue(factory.createCSCoveredEmployeeCoveredEmployeeFixedIdObjValue(r.getCoveredEmployee().getFixedId().getObjValue()));
            }

            if(NullChecker.isNotNullOrEmpty(r.getCoveredEmployee().getAuditedAmount())){
                cov.setAuditedAmount(factory.createCSCoveredEmployeeCoveredEmployeeAuditedAmount(r.getCoveredEmployee().getAuditedAmount()));
            }
            
            
            cov.setFixedId(factory.createCSCoveredEmployeeCoveredEmployeeFixedId(fixedId));
            ce.setCoveredEmployee(factory.createCSCoveredEmployeeCoveredEmployee(cov));

            entryPR.setCSCoveredEmployee(ce);
            coveredEmployeesPR.getEntry().add(entryPR);
        }
        return coveredEmployeesPR;
    }
    
}
