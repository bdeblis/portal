package com.pwc.us.wsclient.payrollreport;

import com.pwc.us.common.exception.GwAuditIsCompleteException;
import com.pwc.us.common.exception.GwAuditIsEditableException;
import com.pwc.us.ws.client.payrollreport.FindPolicyNumbersResponse;
import com.pwc.us.common.exception.GwIntegrationException;
import com.pwc.us.common.exception.GwNoResultsFoundException;
import com.pwc.us.common.model.payrollreport.CSAuditInformationDetailsPR;
import com.pwc.us.common.model.payrollreport.CSAuditInformationPR;
import com.pwc.us.common.model.payrollreport.CSCoveredEmployeePR;
import com.pwc.us.common.model.payrollreport.CSPolicyPeriodPR;
import com.pwc.us.common.model.payrollreport.CSUpdateCoveredEmployeeResponsePR;
import com.pwc.us.common.model.payrollreport.FindAuditInformationDetailsResponsePR;
import com.pwc.us.common.model.payrollreport.FindAuditInformationsResponsePR;
import com.pwc.us.common.model.payrollreport.FindPolicyInformationResponsePR;
import com.pwc.us.common.model.payrollreport.FindPolicyNumbersResponsePR;
import com.pwc.us.common.model.payrollreport.FindPolicyStartAndEndDatesResponsePR;
import com.pwc.us.common.model.payrollreport.UpdateCoveredEmployeesAndCalculatePremiumsPR;
import com.pwc.us.common.model.payrollreport.UpdateCoveredEmployeesPR;
import com.pwc.us.common.utils.NullChecker;
import com.pwc.us.webservice.tools.CDataXMLStreamWriter;
import com.pwc.us.ws.client.payrollreport.CSAuditInformation;
import com.pwc.us.ws.client.payrollreport.CSAuditInformationDetails;
import com.pwc.us.ws.client.payrollreport.CSCoveredEmployee;
import com.pwc.us.ws.client.payrollreport.CSPolicyPeriod;
import com.pwc.us.ws.client.payrollreport.CSPremiumDetails;
import com.pwc.us.ws.client.payrollreport.FindAuditInformationDetailsResponse;
import com.pwc.us.ws.client.payrollreport.FindAuditInformationsResponse;
import com.pwc.us.ws.client.payrollreport.FindPolicyInformationResponse;
import com.pwc.us.ws.client.payrollreport.FindPolicyStartAndEndDatesResponse;
import com.pwc.us.ws.client.payrollreport.ObjectFactory;
import com.pwc.us.ws.client.payrollreport.UpdateAuditInformationDetails;
import com.pwc.us.ws.client.payrollreport.UpdateCoveredEmployees;
import com.pwc.us.ws.client.payrollreport.UpdateCoveredEmployeesAndCalculatePremiums;
import com.pwc.us.ws.client.payrollreport.UpdateCoveredEmployeesAndCalculatePremiumsResponse;
import com.pwc.us.ws.client.payrollreport.UpdateCoveredEmployeesAndCompleteAuditProcess;
import com.pwc.us.ws.client.payrollreport.UpdateCoveredEmployeesAndCompleteAuditProcessResponse;
import java.io.StringWriter;
import java.math.BigDecimal;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Roger Turnau - <roger.turnau@us.pwc.com>
 */
public class PayrollReportBuilder {

    private static final Logger logger = LoggerFactory.getLogger(PayrollReportBuilder.class);
    private final PayrollReport client = new PayrollReport();
    private final ObjectFactory factory = new ObjectFactory();

    public FindPolicyNumbersResponsePR.Return findPolicyNumbers(String accountNumber) throws GwIntegrationException {
        FindPolicyNumbersResponsePR.Return responsePR = new FindPolicyNumbersResponsePR.Return();

        FindPolicyNumbersResponse.Return response = null;
        response = client.findPolicyNumbers(accountNumber);

        for (String s : response.getEntry()) {
            responsePR.getEntry().add(s);
        }

        return responsePR;
    }
    
    public FindPolicyStartAndEndDatesResponsePR.Return findPolicyStartAndEndDates(String policyNumber) throws GwIntegrationException {
        FindPolicyStartAndEndDatesResponsePR.Return responsePR = new FindPolicyStartAndEndDatesResponsePR.Return();

        FindPolicyStartAndEndDatesResponse.Return response = null;
        response = client.findPolicyStartAndEndDates(policyNumber);

        for (XMLGregorianCalendar d : response.getEntry()) {
            responsePR.getEntry().add(d.toGregorianCalendar().getTime());
        }

        return responsePR;
    }
    
    
    public boolean policyDateIsValid(String policyNumber, String fnoiDate) throws GwIntegrationException {
        return client.policyDateIsValid(policyNumber, fnoiDate);
    }

    public FindPolicyInformationResponsePR.Return findPolicyInformation(String policyNumber) throws GwIntegrationException {
        FindPolicyInformationResponsePR.Return responsePR = new FindPolicyInformationResponsePR.Return();
        CSPolicyPeriodPR csPolicyPeriodPR = new CSPolicyPeriodPR();
        CSPolicyPeriodPR.PolicyPeriod policyPeriodPR = new CSPolicyPeriodPR.PolicyPeriod();
        CSPolicyPeriodPR.PolicyPeriod.Policy policyPR = new CSPolicyPeriodPR.PolicyPeriod.Policy();
        CSPolicyPeriodPR.PolicyPeriod.Policy.Account accountPR = new CSPolicyPeriodPR.PolicyPeriod.Policy.Account();
        CSPolicyPeriodPR.PolicyPeriod.Policy.Account.AccountStatus accountStatusPR = new CSPolicyPeriodPR.PolicyPeriod.Policy.Account.AccountStatus();

        FindPolicyInformationResponse.Return response;
        CSPolicyPeriod csPolicyPeriod;
        CSPolicyPeriod.PolicyPeriod policyPeriod;
        CSPolicyPeriod.PolicyPeriod.Policy policy;
        CSPolicyPeriod.PolicyPeriod.Policy.Account account;
        CSPolicyPeriod.PolicyPeriod.Policy.Account.AccountStatus accountStatus;

        response = client.findPolicyInformation(policyNumber);
        csPolicyPeriod = response.getCSPolicyPeriod();
        policyPeriod = csPolicyPeriod.getPolicyPeriod().getValue();
        policy = policyPeriod.getPolicy().getValue();
        account = policy.getAccount().getValue();
        accountStatus = account.getAccountStatus().getValue();

        accountStatusPR.setCode(accountStatus.getCode().getValue());
        accountStatusPR.setDescription(accountStatus.getDescription().getValue());
        accountPR.setAccountStatus(accountStatusPR);
        policyPR.setAccount(accountPR);
        policyPeriodPR.setPolicy(policyPR);
        policyPeriodPR.setPolicyNumber(policyPeriod.getPolicyNumber().getValue());
        csPolicyPeriodPR.setPolicyPeriod(policyPeriodPR);
        csPolicyPeriodPR.setEmpName(csPolicyPeriod.getEmpName().getValue());
        if (NullChecker.isNotNullOrEmpty(csPolicyPeriod.getEmployerAddressLine1())) {
            csPolicyPeriodPR.setEmployerAddressLine1(csPolicyPeriod.getEmployerAddressLine1().getValue());
        } else {
            csPolicyPeriodPR.setEmployerAddressLine1("not provided");
        }
        if (NullChecker.isNotNullOrEmpty(csPolicyPeriod.getEmployerCity())) {
            csPolicyPeriodPR.setEmployerCity(csPolicyPeriod.getEmployerCity().getValue());
        } else {
            csPolicyPeriodPR.setEmployerCity("not provided");
        }

        if (NullChecker.isNotNullOrEmpty(csPolicyPeriod.getEmpPhone())) {
            csPolicyPeriodPR.setEmpPhone(csPolicyPeriod.getEmpPhone().getValue());
        } else {
            csPolicyPeriodPR.setEmpPhone("not provided");
        }

        if (NullChecker.isNotNullOrEmpty(csPolicyPeriod.getEmployerState())) {
            csPolicyPeriodPR.setEmployerState(csPolicyPeriod.getEmployerState().getValue());
        } else {
            csPolicyPeriodPR.setEmployerState("not provided");
        }

        if (NullChecker.isNotNullOrEmpty(csPolicyPeriod.getEmployerZip())) {
            csPolicyPeriodPR.setEmployerZip(csPolicyPeriod.getEmployerZip().getValue());
        } else {
            csPolicyPeriodPR.setEmployerZip("not provided");
        }

        responsePR.setCSPolicyPeriod(csPolicyPeriodPR);

        return responsePR;
    }

    public FindAuditInformationsResponsePR.Return findAuditInformations(String policyNumber, Boolean includeFinalAudits) throws GwIntegrationException {
        FindAuditInformationsResponsePR.Return responsePR = new FindAuditInformationsResponsePR.Return();

        FindAuditInformationsResponse.Return response = null;
        response = client.findAuditInformations(policyNumber, includeFinalAudits);

        for (FindAuditInformationsResponse.Return.Entry e : response.getEntry()) {
            CSAuditInformationPR.DisplayableAuditInfo.ActualInfo.PolicyTerm.Policy.LatestPeriod latestPeriodPR = new CSAuditInformationPR.DisplayableAuditInfo.ActualInfo.PolicyTerm.Policy.LatestPeriod();
            CSAuditInformationPR.DisplayableAuditInfo.ActualInfo.PolicyTerm.Policy policyPR = new CSAuditInformationPR.DisplayableAuditInfo.ActualInfo.PolicyTerm.Policy();
            CSAuditInformationPR.DisplayableAuditInfo.ActualInfo.PolicyTerm policyTermPR = new CSAuditInformationPR.DisplayableAuditInfo.ActualInfo.PolicyTerm();
            CSAuditInformationPR.DisplayableAuditInfo.ActualInfo actualInfoPR = new CSAuditInformationPR.DisplayableAuditInfo.ActualInfo();
            CSAuditInformationPR.DisplayableAuditInfo displayableAuditInfoPR = new CSAuditInformationPR.DisplayableAuditInfo();
            CSAuditInformationPR csAuditInformationPR = new CSAuditInformationPR();
            FindAuditInformationsResponsePR.Return.Entry entryPR = new FindAuditInformationsResponsePR.Return.Entry();

            if (NullChecker.isNotNullOrEmpty(e)) {

                if (NullChecker.isNotNullOrEmpty(e.getCSAuditInformation())) {
                    CSAuditInformation csAuditInformation = e.getCSAuditInformation();
          
                    csAuditInformationPR.setPolicyStatus(e.getCSAuditInformation().getPolicyStatus().getValue());

                    if (NullChecker.isNotNullOrEmpty(csAuditInformation.getDisplayableAuditInfo())) {
                        CSAuditInformation.DisplayableAuditInfo displayableAuditInfo = csAuditInformation.getDisplayableAuditInfo().getValue();

                        if (NullChecker.isNotNullOrEmpty(displayableAuditInfo.getActualInfo())) {
                            CSAuditInformation.DisplayableAuditInfo.ActualInfo actualInfo = displayableAuditInfo.getActualInfo().getValue();

                            if (NullChecker.isNotNullOrEmpty(actualInfo.getPolicyTerm())) {
                                CSAuditInformation.DisplayableAuditInfo.ActualInfo.PolicyTerm policyTerm = actualInfo.getPolicyTerm().getValue();

                                if (NullChecker.isNotNullOrEmpty(policyTerm.getDisplayName())) {
                                    policyTermPR.setDisplayName(policyTerm.getDisplayName().getValue());
                                }

                                if (NullChecker.isNotNullOrEmpty(policyTerm.getPolicy())) {
                                    CSAuditInformation.DisplayableAuditInfo.ActualInfo.PolicyTerm.Policy policy = policyTerm.getPolicy().getValue();

                                    if (NullChecker.isNotNullOrEmpty(policy.getLatestPeriod())) {
                                        CSAuditInformation.DisplayableAuditInfo.ActualInfo.PolicyTerm.Policy.LatestPeriod latestPeriod = policy.getLatestPeriod().getValue();

                                        if (NullChecker.isNotNullOrEmpty(latestPeriod.getDisplayPolicyNumber())) {
                                            latestPeriodPR.setDisplayPolicyNumber(latestPeriod.getDisplayPolicyNumber().getValue());
                                            policyPR.setLatestPeriod(latestPeriodPR);
                                            policyTermPR.setPolicy(policyPR);
                                        }
                                    }
                                }
                            }

                            if (NullChecker.isNotNullOrEmpty(actualInfo.getIsComplete())) {
                                actualInfoPR.setIsComplete(actualInfo.getIsComplete().getValue());
                            }
                            if (NullChecker.isNotNullOrEmpty(actualInfo.getIsEditable())) {
                                actualInfoPR.setIsEditable(actualInfo.getIsEditable().getValue());
                            }
                            if (NullChecker.isNotNullOrEmpty(actualInfo.getIsFinalAudit())) {
                                actualInfoPR.setIsFinalAudit(actualInfo.getIsFinalAudit().getValue());
                            }
                            if (NullChecker.isNotNullOrEmpty(actualInfo.getIsOpen())) {
                                actualInfoPR.setIsOpen(actualInfo.getIsOpen().getValue());
                            }
                            if (NullChecker.isNotNullOrEmpty(actualInfo.getIsPremiumReport())) {
                                actualInfoPR.setIsPremiumReport(actualInfo.getIsPremiumReport().getValue());
                            }
                            if (NullChecker.isNotNullOrEmpty(actualInfo.getIsScheduled())) {
                                actualInfoPR.setIsScheduled(actualInfo.getIsScheduled().getValue());
                            }
                            if (NullChecker.isNotNullOrEmpty(policyTermPR)) {
                                actualInfoPR.setPolicyTerm(policyTermPR);
                            }
                            if (NullChecker.isNotNullOrEmpty(actualInfo.getPublicID())) {
                                actualInfoPR.setPublicID(actualInfo.getPublicID().getValue());
                            }

                            if (NullChecker.isNotNullOrEmpty(actualInfoPR)) {
                                displayableAuditInfoPR.setActualInfo(actualInfoPR);
                            }
                            if (NullChecker.isNotNullOrEmpty(displayableAuditInfo.getCloseDate())) {
                                displayableAuditInfoPR.setCloseDate(displayableAuditInfo.getCloseDate().getValue());
                            }
                            if (NullChecker.isNotNullOrEmpty(displayableAuditInfo.getDueDate())) {
                                displayableAuditInfoPR.setDueDate(displayableAuditInfo.getDueDate().getValue());
                            }
                            if (NullChecker.isNotNullOrEmpty(displayableAuditInfo.getEndDate())) {
                                displayableAuditInfoPR.setEndDate(displayableAuditInfo.getEndDate().getValue());
                            }
                            if (NullChecker.isNotNullOrEmpty(displayableAuditInfo.getInitDate())) {
                                displayableAuditInfoPR.setInitDate(displayableAuditInfo.getInitDate().getValue());
                            }
                            if (NullChecker.isNotNullOrEmpty(displayableAuditInfo.getMethod())) {
                                displayableAuditInfoPR.setMethod(displayableAuditInfo.getMethod().getValue());
                            }
                            if (NullChecker.isNotNullOrEmpty(displayableAuditInfo.getStartDate())) {
                                displayableAuditInfoPR.setStartDate(displayableAuditInfo.getStartDate().getValue());
                            }
                            if (NullChecker.isNotNullOrEmpty(displayableAuditInfo.getStatus())) {
                                displayableAuditInfoPR.setStatus(displayableAuditInfo.getStatus().getValue());
                            }
                            if (NullChecker.isNotNullOrEmpty(displayableAuditInfo.getTotalCost())) {
                                displayableAuditInfoPR.setTotalCost(displayableAuditInfo.getTotalCost().getValue());
                            }
                            if (NullChecker.isNotNullOrEmpty(displayableAuditInfo.getTransactionAmount())) {
                                displayableAuditInfoPR.setTransactionAmount(displayableAuditInfo.getTransactionAmount().getValue());
                            }
                            if (NullChecker.isNotNullOrEmpty(displayableAuditInfo.getType())) {
                                displayableAuditInfoPR.setType(displayableAuditInfo.getType().getValue());
                            }

                            if (NullChecker.isNotNullOrEmpty(displayableAuditInfoPR)) {
                                csAuditInformationPR.setDisplayableAuditInfo(displayableAuditInfoPR);
                            }

                            if (NullChecker.isNotNullOrEmpty(csAuditInformationPR)) {
                                entryPR.setCSAuditInformation(csAuditInformationPR);
                            }

                            if (NullChecker.isNotNullOrEmpty(entryPR)) {
                                responsePR.getEntry().add(entryPR);
                            }
                        }
                    }
                }
            }
        }

        return responsePR;
    }

    public FindAuditInformationDetailsResponsePR.Return findAuditInformationDetails(String auditInformationPublicId) throws GwIntegrationException,
            GwNoResultsFoundException {
        FindAuditInformationDetailsResponsePR.Return responsePR = new FindAuditInformationDetailsResponsePR.Return();
        CSAuditInformationDetailsPR csAuditInformationDetailsPR = new CSAuditInformationDetailsPR();
        CSAuditInformationDetailsPR.CoveredEmployees coveredEmployeesPR = new CSAuditInformationDetailsPR.CoveredEmployees();
        CSAuditInformationDetailsPR.IncludedOwnerOfficers includedOwnerOfficersPR = new CSAuditInformationDetailsPR.IncludedOwnerOfficers();
        CSAuditInformationDetailsPR.SupplDiseaseExposures supplDiseaseExposuresPR = new CSAuditInformationDetailsPR.SupplDiseaseExposures();

        CSAuditInformationDetails csAuditInformationDetails;
        CSAuditInformationDetails.CoveredEmployees coveredEmployees;
        CSAuditInformationDetails.IncludedOwnerOfficers includedOwnerOfficers;
        CSAuditInformationDetails.SupplDiseaseExposures supplDiseaseExposures;

        FindAuditInformationDetailsResponse.Return response;
        response = client.findAuditInformationDetails(auditInformationPublicId);

        if (NullChecker.isNotNullOrEmpty(response.getCSAuditInformationDetails())) {
            csAuditInformationDetails = response.getCSAuditInformationDetails();
            // handle the disease codes, if any        
            if (NullChecker.isNotNullOrEmpty(csAuditInformationDetails.getSupplDiseaseExposures())) {
                // disease code iteration
                
                supplDiseaseExposures = csAuditInformationDetails.getSupplDiseaseExposures().getValue();
                
                for (CSAuditInformationDetails.SupplDiseaseExposures.Entry e : supplDiseaseExposures.getEntry()) {
                    
                    CSAuditInformationDetailsPR.SupplDiseaseExposures.Entry entryPR = new CSAuditInformationDetailsPR.SupplDiseaseExposures.Entry();
                    CSAuditInformationDetailsPR.SupplDiseaseExposures.Entry.SupplDiseaseExposure supplDiseaseExposurePR = new CSAuditInformationDetailsPR.SupplDiseaseExposures.Entry.SupplDiseaseExposure();
                    CSAuditInformationDetailsPR.SupplDiseaseExposures.Entry.SupplDiseaseExposure.FixedId fixedIdPR = new CSAuditInformationDetailsPR.SupplDiseaseExposures.Entry.SupplDiseaseExposure.FixedId();
                    CSAuditInformationDetailsPR.SupplDiseaseExposures.Entry.SupplDiseaseExposure.DiseaseCode diseaseCodePR = new CSAuditInformationDetailsPR.SupplDiseaseExposures.Entry.SupplDiseaseExposure.DiseaseCode();
                    CSAuditInformationDetailsPR.SupplDiseaseExposures.Entry.SupplDiseaseExposure.Location locationPR = new CSAuditInformationDetailsPR.SupplDiseaseExposures.Entry.SupplDiseaseExposure.Location();
                    CSAuditInformationDetailsPR.SupplDiseaseExposures.Entry.SupplDiseaseExposure.Location.State statePR = new CSAuditInformationDetailsPR.SupplDiseaseExposures.Entry.SupplDiseaseExposure.Location.State();
                    
                    if (NullChecker.isNotNullOrEmpty(e.getSupplDiseaseExposure())) {
                        CSAuditInformationDetails.SupplDiseaseExposures.Entry.SupplDiseaseExposure supplDiseaseExposure = e.getSupplDiseaseExposure().getValue();
                        
                        if (NullChecker.isNotNullOrEmpty(supplDiseaseExposure.getFixedId())) {
                            CSAuditInformationDetails.SupplDiseaseExposures.Entry.SupplDiseaseExposure.FixedId fixedId = supplDiseaseExposure.getFixedId().getValue();
                            if (NullChecker.isNotNullOrEmpty(fixedId.getObjValue())) {
                                fixedIdPR.setObjValue(fixedId.getObjValue().getValue());
                            }
                        }
                        
                        if (NullChecker.isNotNullOrEmpty(supplDiseaseExposure.getDiseaseCode())) {
                            
                            CSAuditInformationDetails.SupplDiseaseExposures.Entry.SupplDiseaseExposure.DiseaseCode diseaseCode = supplDiseaseExposure.getDiseaseCode().getValue();
                            if (NullChecker.isNotNullOrEmpty(diseaseCode.getCode())) {
                                diseaseCodePR.setCode(diseaseCode.getCode().getValue());
                            }
                            if (NullChecker.isNotNullOrEmpty(diseaseCode.getSupplDiseaseLoadingType())) {
                                diseaseCodePR.setSupplDiseaseLoadingType(diseaseCode.getSupplDiseaseLoadingType().getValue());
                            }
                        }
                        if (NullChecker.isNotNullOrEmpty(supplDiseaseExposure.getLocation())) {
                            CSAuditInformationDetails.SupplDiseaseExposures.Entry.SupplDiseaseExposure.Location location = supplDiseaseExposure.getLocation().getValue();
                            
                            if (NullChecker.isNotNullOrEmpty(location.getState())) {
                                CSAuditInformationDetails.SupplDiseaseExposures.Entry.SupplDiseaseExposure.Location.State state = location.getState().getValue();
                                if (NullChecker.isNotNullOrEmpty(state.getCode())) {
                                    statePR.setCode(state.getCode().getValue());
                                }
                                if (NullChecker.isNotNullOrEmpty(state.getDescription())) {
                                    statePR.setDescription(state.getDescription().getValue());
                                }
                            }
                            if (NullChecker.isNotNullOrEmpty(location.getAddressLine1())) {
                                locationPR.setAddressLine1(location.getAddressLine1().getValue());
                            }
                            if (NullChecker.isNotNullOrEmpty(location.getAddressLine2())) {
                                locationPR.setAddressLine2(location.getAddressLine2().getValue());
                            }
                            if (NullChecker.isNotNullOrEmpty(location.getAddressLine3())) {
                                locationPR.setAddressLine3(location.getAddressLine3().getValue());
                            }
                            if (NullChecker.isNotNullOrEmpty(location.getCity())) {
                                locationPR.setCity(location.getCity().getValue());
                            }
                            if (NullChecker.isNotNullOrEmpty(location.getDisplayName())) {
                                locationPR.setDisplayName(location.getDisplayName().getValue());
                            }
                            if (NullChecker.isNotNullOrEmpty(location.getLocationNum())) {
                                locationPR.setLocationNum(location.getLocationNum().getValue());
                            }
                            if (NullChecker.isNotNullOrEmpty(location.getPrimaryLoc())) {
                                locationPR.setPrimaryLoc(location.getPrimaryLoc().getValue());
                            } else {
                                logger.error("primaryLoc value is null");
                            }
                            
                            if (NullChecker.isNotNullOrEmpty(location.getPostalCode())) {
                                locationPR.setPostalCode(location.getPostalCode().getValue());
                            }
                            if (NullChecker.isNotNullOrEmpty(statePR)) {
                                locationPR.setState(statePR);
                            }
                        }
                        if (NullChecker.isNotNullOrEmpty(diseaseCodePR)) {
                            supplDiseaseExposurePR.setDiseaseCode(diseaseCodePR);
                        }
                        
                        if (NullChecker.isNotNullOrEmpty(locationPR)) {
                            supplDiseaseExposurePR.setLocation(locationPR);
                        }
                        if (NullChecker.isNotNullOrEmpty(fixedIdPR)) {
                            supplDiseaseExposurePR.setFixedId(fixedIdPR);
                        }
                        if (NullChecker.isNotNullOrEmpty(supplDiseaseExposure.getAuditedAmount())) {
                            supplDiseaseExposurePR.setAuditedAmount(supplDiseaseExposure.getAuditedAmount().getValue());
                        } else {
                            supplDiseaseExposurePR.setAuditedAmount(0);
                        }
                        
                    }
                    
                    if (NullChecker.isNotNullOrEmpty(supplDiseaseExposurePR)) {
                        entryPR.setSupplDiseaseExposure(supplDiseaseExposurePR);
                    }
                    
                    if (NullChecker.isNotNullOrEmpty(entryPR)) {
                        supplDiseaseExposuresPR.getEntry().add(entryPR);
                    }
                }
                
            } // disease codes complete

            if (NullChecker.isNotNullOrEmpty(csAuditInformationDetails.getCoveredEmployees())) {
                coveredEmployees = csAuditInformationDetails.getCoveredEmployees().getValue();

                // CoveredEmployees Iteration
                for (CSAuditInformationDetails.CoveredEmployees.Entry e : coveredEmployees.getEntry()) {
                    CSAuditInformationDetailsPR.CoveredEmployees.Entry entryPR = new CSAuditInformationDetailsPR.CoveredEmployees.Entry();
                    CSAuditInformationDetailsPR.CoveredEmployees.Entry.CoveredEmployee coveredEmployeePR = new CSAuditInformationDetailsPR.CoveredEmployees.Entry.CoveredEmployee();
                    CSAuditInformationDetailsPR.CoveredEmployees.Entry.CoveredEmployee.ClassCode classCodePR = new CSAuditInformationDetailsPR.CoveredEmployees.Entry.CoveredEmployee.ClassCode();
                    CSAuditInformationDetailsPR.CoveredEmployees.Entry.CoveredEmployee.FixedId fixedIdPR = new CSAuditInformationDetailsPR.CoveredEmployees.Entry.CoveredEmployee.FixedId();
                    CSAuditInformationDetailsPR.CoveredEmployees.Entry.CoveredEmployee.Location locationPR = new CSAuditInformationDetailsPR.CoveredEmployees.Entry.CoveredEmployee.Location();
                    CSAuditInformationDetailsPR.CoveredEmployees.Entry.CoveredEmployee.Location.State statePR = new CSAuditInformationDetailsPR.CoveredEmployees.Entry.CoveredEmployee.Location.State();

                    if (NullChecker.isNotNullOrEmpty(e.getCoveredEmployee())) {
                        CSAuditInformationDetails.CoveredEmployees.Entry.CoveredEmployee coveredEmployee = e.getCoveredEmployee().getValue();
                        if (NullChecker.isNotNullOrEmpty(e.getCoveredEmployee())) {
                            CSAuditInformationDetails.CoveredEmployees.Entry.CoveredEmployee.ClassCode classCode = coveredEmployee.getClassCode().getValue();
                            if (NullChecker.isNotNullOrEmpty(classCode.getCode())) {
                                classCodePR.setCode(classCode.getCode().getValue());
                            }
                            if (NullChecker.isNotNullOrEmpty(classCode.getDescInd())) {
                                classCodePR.setDescInd(classCode.getDescInd().getValue());
                            }
                            if (NullChecker.isNotNullOrEmpty(classCode.getShortDesc())) {
                                classCodePR.setShortDesc(classCode.getShortDesc().getValue());
                            }
                        }
                        if (NullChecker.isNotNullOrEmpty(e.getCoveredEmployee())) {
                            CSAuditInformationDetails.CoveredEmployees.Entry.CoveredEmployee.FixedId fixedId = coveredEmployee.getFixedId().getValue();
                            if (NullChecker.isNotNullOrEmpty(fixedId.getObjValue())) {
                                fixedIdPR.setObjValue(fixedId.getObjValue().getValue());
                            }
                        }
                        if (NullChecker.isNotNullOrEmpty(e.getCoveredEmployee())) {
                            CSAuditInformationDetails.CoveredEmployees.Entry.CoveredEmployee.Location location = coveredEmployee.getLocation().getValue();
                            if (NullChecker.isNotNullOrEmpty(e.getCoveredEmployee())) {
                                CSAuditInformationDetails.CoveredEmployees.Entry.CoveredEmployee.Location.State state = location.getState().getValue();
                                if (NullChecker.isNotNullOrEmpty(state.getCode())) {
                                    statePR.setCode(state.getCode().getValue());
                                }
                                if (NullChecker.isNotNullOrEmpty(state.getDescription())) {
                                    statePR.setDescription(state.getDescription().getValue());
                                }
                            }
                            if (NullChecker.isNotNullOrEmpty(location.getAddressLine1())) {
                                locationPR.setAddressLine1(location.getAddressLine1().getValue());
                            }
                            if (NullChecker.isNotNullOrEmpty(location.getAddressLine2())) {
                                locationPR.setAddressLine2(location.getAddressLine2().getValue());
                            }
                            if (NullChecker.isNotNullOrEmpty(location.getAddressLine3())) {
                                locationPR.setAddressLine3(location.getAddressLine3().getValue());
                            }
                            if (NullChecker.isNotNullOrEmpty(location.getCity())) {
                                locationPR.setCity(location.getCity().getValue());
                            }
                            if (NullChecker.isNotNullOrEmpty(location.getDisplayName())) {
                                locationPR.setDisplayName(location.getDisplayName().getValue());
                            }
                            if (NullChecker.isNotNullOrEmpty(location.getLocationNum())) {
                                locationPR.setLocationNum(location.getLocationNum().getValue());
                            }
                            if (NullChecker.isNotNullOrEmpty(location.getPrimaryLoc())) {
                                locationPR.setPrimaryLoc(location.getPrimaryLoc().getValue());
                            } else {
                                logger.error("primaryLoc value is null");
                            }
                            
                            if (NullChecker.isNotNullOrEmpty(location.getPostalCode())) {
                                locationPR.setPostalCode(location.getPostalCode().getValue());
                            }
                            if (NullChecker.isNotNullOrEmpty(statePR)) {
                                locationPR.setState(statePR);
                            }
                        }
                        if (NullChecker.isNotNullOrEmpty(coveredEmployee.getBasisType())) {
                            coveredEmployeePR.setBasisType(coveredEmployee.getBasisType().getValue());
                        }
                        if (NullChecker.isNotNullOrEmpty(classCodePR)) {
                            coveredEmployeePR.setClassCode(classCodePR);
                        }
                        if (NullChecker.isNotNullOrEmpty(locationPR)) {
                            coveredEmployeePR.setLocation(locationPR);
                        }
                        if (NullChecker.isNotNullOrEmpty(fixedIdPR)) {
                            coveredEmployeePR.setFixedId(fixedIdPR);
                        }
                        if (NullChecker.isNotNullOrEmpty(coveredEmployee.getAuditedAmount())) {
                            coveredEmployeePR.setAuditedAmount(coveredEmployee.getAuditedAmount().getValue());
                        }

                    }

                    if (NullChecker.isNotNullOrEmpty(coveredEmployeePR)) {
                        entryPR.setCoveredEmployee(coveredEmployeePR);
                    }
                    if (NullChecker.isNotNullOrEmpty(e.getAction())) {
                        entryPR.setAction(e.getAction());
                    }
                    if (NullChecker.isNotNullOrEmpty(e.getBasisForRating())) {
                        entryPR.setBasisForRating(e.getBasisForRating().getValue());
                    }

                    if (NullChecker.isNotNullOrEmpty(entryPR)) {
                        coveredEmployeesPR.getEntry().add(entryPR);
                    }
                }
            }

            if (NullChecker.isNotNullOrEmpty(csAuditInformationDetails.getIncludedOwnerOfficers())) {
                includedOwnerOfficers = csAuditInformationDetails.getIncludedOwnerOfficers().getValue();

                for (CSAuditInformationDetails.IncludedOwnerOfficers.Entry e : includedOwnerOfficers.getEntry()) {
                    CSAuditInformationDetailsPR.IncludedOwnerOfficers.Entry entryPR = new CSAuditInformationDetailsPR.IncludedOwnerOfficers.Entry();
                    CSAuditInformationDetailsPR.IncludedOwnerOfficers.Entry.IncludedOwnerOfficer includedOwnerOfficerPR = new CSAuditInformationDetailsPR.IncludedOwnerOfficers.Entry.IncludedOwnerOfficer();
                    CSAuditInformationDetailsPR.IncludedOwnerOfficers.Entry.IncludedOwnerOfficer.AccountContactRole accountContactRolePR = new CSAuditInformationDetailsPR.IncludedOwnerOfficers.Entry.IncludedOwnerOfficer.AccountContactRole();
                    CSAuditInformationDetailsPR.IncludedOwnerOfficers.Entry.IncludedOwnerOfficer.AccountContactRole.AccountContact accountContactPR = new CSAuditInformationDetailsPR.IncludedOwnerOfficers.Entry.IncludedOwnerOfficer.AccountContactRole.AccountContact();
                    CSAuditInformationDetailsPR.IncludedOwnerOfficers.Entry.IncludedOwnerOfficer.AccountContactRole.AccountContact.Contact contactPR = new CSAuditInformationDetailsPR.IncludedOwnerOfficers.Entry.IncludedOwnerOfficer.AccountContactRole.AccountContact.Contact();
                    CSAuditInformationDetailsPR.IncludedOwnerOfficers.Entry.IncludedOwnerOfficer.ItemRelationshipTitle itemRelationshipTitlePR = new CSAuditInformationDetailsPR.IncludedOwnerOfficers.Entry.IncludedOwnerOfficer.ItemRelationshipTitle();
                    CSAuditInformationDetailsPR.IncludedOwnerOfficers.Entry.IncludedOwnerOfficer.Jurisdiction jurisdictionPR = new CSAuditInformationDetailsPR.IncludedOwnerOfficers.Entry.IncludedOwnerOfficer.Jurisdiction();
                    CSAuditInformationDetailsPR.IncludedOwnerOfficers.Entry.IncludedOwnerOfficer.NamedInsured nameInsuredPR = new CSAuditInformationDetailsPR.IncludedOwnerOfficers.Entry.IncludedOwnerOfficer.NamedInsured();
                    CSAuditInformationDetailsPR.IncludedOwnerOfficers.Entry.IncludedOwnerOfficer.WC7ClassCode wc7ClassCodePR = new CSAuditInformationDetailsPR.IncludedOwnerOfficers.Entry.IncludedOwnerOfficer.WC7ClassCode();

                    if (NullChecker.isNotNullOrEmpty(e.getIncludedOwnerOfficer())) {
                        CSAuditInformationDetails.IncludedOwnerOfficers.Entry.IncludedOwnerOfficer includedOwnerOfficer = e.getIncludedOwnerOfficer().getValue();
                        if (NullChecker.isNotNullOrEmpty(includedOwnerOfficer.getAccountContactRole())) {
                            CSAuditInformationDetails.IncludedOwnerOfficers.Entry.IncludedOwnerOfficer.AccountContactRole accountContactRole = includedOwnerOfficer.getAccountContactRole().getValue();
                            if (NullChecker.isNotNullOrEmpty(e.getIncludedOwnerOfficer())) {
                                CSAuditInformationDetails.IncludedOwnerOfficers.Entry.IncludedOwnerOfficer.AccountContactRole.AccountContact accountContact = accountContactRole.getAccountContact().getValue();
                                if (NullChecker.isNotNullOrEmpty(e.getIncludedOwnerOfficer())) {
                                    CSAuditInformationDetails.IncludedOwnerOfficers.Entry.IncludedOwnerOfficer.AccountContactRole.AccountContact.Contact contact = accountContact.getContact().getValue();
                                    if (NullChecker.isNotNullOrEmpty(contact.getDisplayName())) {
                                        contactPR.setDisplayName(contact.getDisplayName().getValue());
                                    }
                                    if (NullChecker.isNotNullOrEmpty(contactPR)) {
                                        accountContactPR.setContact(contactPR);
                                    }
                                    if (NullChecker.isNotNullOrEmpty(accountContactPR)) {
                                        accountContactRolePR.setAccountContact(accountContactPR);
                                    }
                                }
                            }
                        }

                        if (NullChecker.isNotNullOrEmpty(includedOwnerOfficer.getItemRelationshipTitle())) {
                            CSAuditInformationDetails.IncludedOwnerOfficers.Entry.IncludedOwnerOfficer.ItemRelationshipTitle itemRelationshipTitle = includedOwnerOfficer.getItemRelationshipTitle().getValue();
                            if (NullChecker.isNotNullOrEmpty(itemRelationshipTitle.getDisplayName())) {
                                itemRelationshipTitlePR.setDisplayName(itemRelationshipTitle.getDisplayName().getValue());
                            }
                        }
                        if (NullChecker.isNotNullOrEmpty(includedOwnerOfficer.getJurisdiction())) {
                            CSAuditInformationDetails.IncludedOwnerOfficers.Entry.IncludedOwnerOfficer.Jurisdiction jurisdiction = includedOwnerOfficer.getJurisdiction().getValue();
                            if (NullChecker.isNotNullOrEmpty(jurisdiction.getDisplayName())) {
                                jurisdictionPR.setDisplayName(jurisdiction.getDisplayName().getValue());
                            }

                        }
                        if (NullChecker.isNotNullOrEmpty(includedOwnerOfficer.getNamedInsured())) {
                            CSAuditInformationDetails.IncludedOwnerOfficers.Entry.IncludedOwnerOfficer.NamedInsured nameInsured = includedOwnerOfficer.getNamedInsured().getValue();
                            if (NullChecker.isNotNullOrEmpty(nameInsured.getDisplayName())) {
                                nameInsuredPR.setDisplayName(nameInsured.getDisplayName().getValue());
                            }

                        }
                        if (NullChecker.isNotNullOrEmpty(includedOwnerOfficer.getWC7ClassCode())) {
                            CSAuditInformationDetails.IncludedOwnerOfficers.Entry.IncludedOwnerOfficer.WC7ClassCode wc7ClassCode = includedOwnerOfficer.getWC7ClassCode().getValue();
                            if (NullChecker.isNotNullOrEmpty(wc7ClassCode.getCode())) {
                                wc7ClassCodePR.setCode(wc7ClassCode.getCode().getValue());
                            }
                            if (NullChecker.isNotNullOrEmpty(wc7ClassCode.getDescInd())) {
                                wc7ClassCodePR.setDescInd(wc7ClassCode.getDescInd().getValue());
                            }
                            if (NullChecker.isNotNullOrEmpty(wc7ClassCode.getShortDesc())) {
                                wc7ClassCodePR.setShortDesc(wc7ClassCode.getShortDesc().getValue());
                            }
                        }

                        if (NullChecker.isNotNullOrEmpty(accountContactRolePR)) {
                            includedOwnerOfficerPR.setAccountContactRole(accountContactRolePR);
                        }
                        if (NullChecker.isNotNullOrEmpty(itemRelationshipTitlePR)) {
                            includedOwnerOfficerPR.setItemRelationshipTitle(itemRelationshipTitlePR);
                        }
                        if (NullChecker.isNotNullOrEmpty(jurisdictionPR)) {
                            includedOwnerOfficerPR.setJurisdiction(jurisdictionPR);
                        }
                        if (NullChecker.isNotNullOrEmpty(nameInsuredPR)) {
                            includedOwnerOfficerPR.setNamedInsured(nameInsuredPR);
                        }
                        if (NullChecker.isNotNullOrEmpty(wc7ClassCodePR)) {
                            includedOwnerOfficerPR.setWC7ClassCode(wc7ClassCodePR);
                        }

                        if (NullChecker.isNotNullOrEmpty(includedOwnerOfficerPR)) {
                            entryPR.setIncludedOwnerOfficer(includedOwnerOfficerPR);
                        }
                        if (NullChecker.isNotNullOrEmpty(e.getAction())) {
                            entryPR.setAction(e.getAction());
                        }
                        if (NullChecker.isNotNullOrEmpty(e.getPeriodProRatedMiscValue())) {
                            entryPR.setPeriodProRatedMiscValue(e.getPeriodProRatedMiscValue().getValue());
                        }

                        if (NullChecker.isNotNullOrEmpty(entryPR)) {
                            includedOwnerOfficersPR.getEntry().add(entryPR);
                        }
                    }

                }
            }

            if (NullChecker.isNotNullOrEmpty(supplDiseaseExposuresPR)) {
                csAuditInformationDetailsPR.setSupplDiseaseExposures(supplDiseaseExposuresPR);
            }
            
            if (NullChecker.isNotNullOrEmpty(coveredEmployeesPR)) {
                csAuditInformationDetailsPR.setCoveredEmployees(coveredEmployeesPR);
            }

            if (NullChecker.isNotNullOrEmpty(includedOwnerOfficersPR)) {
                csAuditInformationDetailsPR.setIncludedOwnerOfficers(includedOwnerOfficersPR);
            }

            if (NullChecker.isNotNullOrEmpty(csAuditInformationDetailsPR)) {
                responsePR.setCSAuditInformationDetails(csAuditInformationDetailsPR);
            }
        }

        return responsePR;
    }

    public void updateCoveredEmployees(String auditInformationPublicId, UpdateCoveredEmployeesPR.CoveredEmployees coveredEmployeesPR) throws GwIntegrationException {
        UpdateCoveredEmployees.CoveredEmployees coveredEmployees = new UpdateCoveredEmployees.CoveredEmployees();

        for (UpdateCoveredEmployeesPR.CoveredEmployees.Entry e : coveredEmployeesPR.getEntry()) {
            CSCoveredEmployeePR csCoveredEmployeePR = e.getCSCoveredEmployee();
            CSCoveredEmployeePR.CoveredEmployee coveredEmployeePR = csCoveredEmployeePR.getCoveredEmployee();
            CSCoveredEmployeePR.CoveredEmployee.FixedId fixedIdPR = coveredEmployeePR.getFixedId();

            CSCoveredEmployee.CoveredEmployee.FixedId fixedId = new CSCoveredEmployee.CoveredEmployee.FixedId();
            CSCoveredEmployee.CoveredEmployee coveredEmployee = new CSCoveredEmployee.CoveredEmployee();
            CSCoveredEmployee csCoveredEmployee = new CSCoveredEmployee();
            UpdateCoveredEmployees.CoveredEmployees.Entry entry = new UpdateCoveredEmployees.CoveredEmployees.Entry();

            fixedId.setObjValue(factory.createCSCoveredEmployeeCoveredEmployeeFixedIdObjValue(fixedIdPR.getObjValue()));

            coveredEmployee.setAuditedAmount(factory.createCSCoveredEmployeeCoveredEmployeeAuditedAmount(coveredEmployeePR.getAuditedAmount()));
            coveredEmployee.setFixedId(factory.createCSCoveredEmployeeCoveredEmployeeFixedId(fixedId));

            csCoveredEmployee.setCoveredEmployee(factory.createCSCoveredEmployeeCoveredEmployee(coveredEmployee));

            entry.setCSCoveredEmployee(csCoveredEmployee);

            coveredEmployees.getEntry().add(entry);

        }

        client.updateCoveredEmployees(auditInformationPublicId, coveredEmployees);
    }

    @Deprecated
    public CSUpdateCoveredEmployeeResponsePR updateCoveredEmployeesAndCalculatePremiums(String auditInformationPublicId, UpdateCoveredEmployeesAndCalculatePremiumsPR.CoveredEmployees coveredEmployeesPR, String comment) throws GwIntegrationException {
        CSUpdateCoveredEmployeeResponsePR response = null;
        UpdateCoveredEmployeesAndCalculatePremiums.CoveredEmployees coveredEmployees = new UpdateCoveredEmployeesAndCalculatePremiums.CoveredEmployees();

        for (UpdateCoveredEmployeesAndCalculatePremiumsPR.CoveredEmployees.Entry e : coveredEmployeesPR.getEntry()) {
            CSCoveredEmployeePR csCoveredEmployeePR = e.getCSCoveredEmployee();
            CSCoveredEmployeePR.CoveredEmployee coveredEmployeePR = csCoveredEmployeePR.getCoveredEmployee();
            CSCoveredEmployeePR.CoveredEmployee.FixedId fixedIdPR = coveredEmployeePR.getFixedId();

            CSCoveredEmployee.CoveredEmployee.FixedId fixedId = new CSCoveredEmployee.CoveredEmployee.FixedId();
            CSCoveredEmployee.CoveredEmployee coveredEmployee = new CSCoveredEmployee.CoveredEmployee();
            CSCoveredEmployee csCoveredEmployee = new CSCoveredEmployee();
            UpdateCoveredEmployeesAndCalculatePremiums.CoveredEmployees.Entry entry = new UpdateCoveredEmployeesAndCalculatePremiums.CoveredEmployees.Entry();

            fixedId.setObjValue(factory.createCSCoveredEmployeeCoveredEmployeeFixedIdObjValue(fixedIdPR.getObjValue()));
            coveredEmployee.setAuditedAmount(factory.createCSAuditInformationDetailsCoveredEmployeesEntryCoveredEmployeeAuditedAmount(coveredEmployeePR.getAuditedAmount()));

            coveredEmployee.setFixedId(factory.createCSCoveredEmployeeCoveredEmployeeFixedId(fixedId));

            csCoveredEmployee.setCoveredEmployee(factory.createCSCoveredEmployeeCoveredEmployee(coveredEmployee));

            entry.setCSCoveredEmployee(csCoveredEmployee);

            coveredEmployees.getEntry().add(entry);
        }

        UpdateCoveredEmployeesAndCalculatePremiumsResponse.Return resp = client.updateCoveredEmployeesAndCalculatePremiums(auditInformationPublicId, coveredEmployees, comment);
        response = new CSUpdateCoveredEmployeeResponsePR();
        response.setCalculatedPremium(resp.getCSUpdateCoveredEmployeeResponse().getCalculatedPremium().getValue());
        response.setPublicId(resp.getCSUpdateCoveredEmployeeResponse().getPublicId().getValue());
        return response;
    }

    
    public CSPremiumDetails updateAuditInformationDetails(
            String auditInformationPublicId,
            UpdateAuditInformationDetails.CoveredEmployees coveredEmployees,
            UpdateAuditInformationDetails.SupplDiseaseExposures supplDiseaseExposures,
            String auditMethod,
            String auditDescription,
            Boolean requestQuote,
            Boolean completeAudit) throws GwIntegrationException {
       return client.updateAuditInformationDetails(auditInformationPublicId, coveredEmployees, supplDiseaseExposures, auditMethod, auditDescription, requestQuote, completeAudit);
    }
    
    public void updateCoveredEmployee(String auditInformationPublicId, Long coveredEmployeeFixedId, Integer amount) throws GwIntegrationException {
        client.updateCoveredEmployee(auditInformationPublicId, coveredEmployeeFixedId, amount);
    }

    public void completeAuditProcess(String auditInformationPublicId) throws GwIntegrationException {
        client.completeAuditProcess(auditInformationPublicId);
    }

    public double calculatePremiums(String auditInformationPublicId) throws GwIntegrationException, GwAuditIsCompleteException, GwAuditIsEditableException {
        BigDecimal response = null;

        response = client.calculatePremiums(auditInformationPublicId);

        return response.doubleValue();
    }

    @Deprecated
    public CSUpdateCoveredEmployeeResponsePR updateCoveredEmployeesAndCompleteAuditProcess(String auditInformationPublicId, UpdateCoveredEmployeesAndCalculatePremiumsPR.CoveredEmployees coveredEmployeesPR, String comment) throws GwIntegrationException {
        CSUpdateCoveredEmployeeResponsePR response = null;
        UpdateCoveredEmployeesAndCompleteAuditProcess.CoveredEmployees coveredEmployees = new UpdateCoveredEmployeesAndCompleteAuditProcess.CoveredEmployees();

        for (UpdateCoveredEmployeesAndCalculatePremiumsPR.CoveredEmployees.Entry e : coveredEmployeesPR.getEntry()) {
            CSCoveredEmployeePR csCoveredEmployeePR = e.getCSCoveredEmployee();
            CSCoveredEmployeePR.CoveredEmployee coveredEmployeePR = csCoveredEmployeePR.getCoveredEmployee();
            CSCoveredEmployeePR.CoveredEmployee.FixedId fixedIdPR = coveredEmployeePR.getFixedId();

            CSCoveredEmployee.CoveredEmployee.FixedId fixedId = new CSCoveredEmployee.CoveredEmployee.FixedId();
            CSCoveredEmployee.CoveredEmployee coveredEmployee = new CSCoveredEmployee.CoveredEmployee();
            CSCoveredEmployee csCoveredEmployee = new CSCoveredEmployee();
            UpdateCoveredEmployeesAndCompleteAuditProcess.CoveredEmployees.Entry entry = new UpdateCoveredEmployeesAndCompleteAuditProcess.CoveredEmployees.Entry();

            fixedId.setObjValue(factory.createCSCoveredEmployeeCoveredEmployeeFixedIdObjValue(fixedIdPR.getObjValue()));
            coveredEmployee.setAuditedAmount(factory.createCSCoveredEmployeeCoveredEmployeeAuditedAmount(coveredEmployeePR.getAuditedAmount()));

            coveredEmployee.setFixedId(factory.createCSCoveredEmployeeCoveredEmployeeFixedId(fixedId));

            csCoveredEmployee.setCoveredEmployee(factory.createCSCoveredEmployeeCoveredEmployee(coveredEmployee));

            entry.setCSCoveredEmployee(csCoveredEmployee);

            coveredEmployees.getEntry().add(entry);
        }

        UpdateCoveredEmployeesAndCompleteAuditProcessResponse.Return resp = client.updateCoveredEmployeesAndCompleteAuditProcess(auditInformationPublicId, coveredEmployees, comment);
        response = new CSUpdateCoveredEmployeeResponsePR();
        response.setCalculatedPremium(resp.getCSUpdateCoveredEmployeeResponse().getCalculatedPremium().getValue());
        response.setPublicId(resp.getCSUpdateCoveredEmployeeResponse().getPublicId().getValue());
        return response;
    }

    /**
     * Determines whether or not the policy number is a valid entry in the GW
     * system. We are not testing whether or not the policy is in force.
     *
     * @param policyNumber
     * @return
     * @throws GwIntegrationException
     */
    public boolean isPolicyNumberValid(String policyNumber) throws GwIntegrationException {
        boolean ret = false;
        FindPolicyInformationResponse.Return response;

        response = client.findPolicyInformation(policyNumber);
        if (NullChecker.isNotNullOrEmpty(response)
                && NullChecker.isNotNullOrEmpty(response.getCSPolicyPeriod())
                && NullChecker.isNotNullOrEmpty(response.getCSPolicyPeriod().getPolicyPeriod())
                && NullChecker.isNotNullOrEmpty(response.getCSPolicyPeriod().getPolicyPeriod().getValue())) {
            ret = true;
        }

        return ret;
    }

    protected String marshall(CSCoveredEmployee coveredEmployeeObject) throws JAXBException, XMLStreamException {
        String xml = null;

        JAXBContext jaxbContext = JAXBContext.newInstance(CSCoveredEmployee.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        XMLOutputFactory xof = XMLOutputFactory.newInstance();
        StringWriter stringWriter = new StringWriter();
        XMLStreamWriter streamWriter = xof.createXMLStreamWriter(stringWriter);
        CDataXMLStreamWriter cdataStreamWriter = new CDataXMLStreamWriter(streamWriter);

        JAXBElement<CSCoveredEmployee> jaxb = factory.createCSCoveredEmployee(coveredEmployeeObject);
        jaxbMarshaller.marshal(jaxb, cdataStreamWriter);
        xml = stringWriter.toString();
        streamWriter.close();
        return xml;
    }
    
}
