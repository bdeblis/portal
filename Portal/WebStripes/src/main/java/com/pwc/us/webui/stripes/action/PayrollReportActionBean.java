package com.pwc.us.webui.stripes.action;

import com.google.inject.Inject;
import com.pwc.us.common.PayrollReport;
import com.pwc.us.common.exception.GwIntegrationException;
import com.pwc.us.common.exception.GwNoResultsFoundException;
import com.pwc.us.common.model.Policyholder;
import com.pwc.us.common.model.User;
import com.pwc.us.common.model.payrollreport.CSAuditInformationDetailsPR;
import com.pwc.us.common.model.payrollreport.CSAuditInformationPR;
import com.pwc.us.common.model.payrollreport.CSPolicyPeriodPR;
import com.pwc.us.common.model.payrollreport.CSPremiumDetailsPR;
import com.pwc.us.common.model.payrollreport.FindAuditInformationDetailsResponsePR;
import com.pwc.us.common.model.payrollreport.FindAuditInformationsResponsePR;
import com.pwc.us.common.model.payrollreport.FindAuditInformationsResponsePR.Return.Entry;
import com.pwc.us.common.model.payrollreport.FindPolicyInformationResponsePR;
import com.pwc.us.common.model.payrollreport.FindPolicyNumbersResponsePR;
import com.pwc.us.common.model.payrollreport.PayrollReportRecordWrapper;
import com.pwc.us.common.model.payrollreport.ReportPeriodWrapper;
import com.pwc.us.common.utils.DateTimeHelper;
import com.pwc.us.common.utils.NullChecker;
import java.io.StringReader;
import com.pwc.us.webui.stripes.util.Security;
import com.pwc.us.ws.client.payrollreport.ObjectFactory;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import net.sourceforge.stripes.action.Before;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.DontValidate;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.SimpleMessage;
import net.sourceforge.stripes.action.StreamingResolution;
import net.sourceforge.stripes.controller.LifecycleStage;
import net.sourceforge.stripes.validation.LocalizableError;
import net.sourceforge.stripes.validation.ValidationError;
import net.sourceforge.stripes.validation.ValidationErrorHandler;
import net.sourceforge.stripes.validation.ValidationErrors;
import net.sourceforge.stripes.validation.ValidationMethod;
import org.apache.commons.lang3.SerializationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * Robert Snelling <robert.snelling@us.pwc.com>
 */
public class PayrollReportActionBean extends PayrollReportBaseActionBean implements ValidationErrorHandler {

    private static final Logger logger = LoggerFactory.getLogger(PayrollReportActionBean.class);
    private static final String PRESENT = "/WEB-INF/jsp/payroll_report.jsp";
    private static final String PERIODS = "/WEB-INF/jsp/payroll_report_periods.jsp";
    private static final String FORM = "/WEB-INF/jsp/payroll_report_form.jsp";
    private static final String PRINT = "/WEB-INF/jsp/payroll_report_print.jsp";
    private static final String PR_POLICY_NUMBER_CACHE = "prPolicyNumber";
    private static final String PAYROLL = "payrollReport";
    private static final String DISEASE_CODES = "diseaseCode";
    private static final String TOT_MAP = "totalMap";
    private static final String PERIOD_START_DATE_CACHE = "periodStartDate";
    private static final String PR_REPORT_PERIODS_MAP_CACHE = "prReportPeriodsMap";
    private static final String ACCOUNT_ID_CACHE = "accountID";
    private static final String POLICY_NUMBERS_CACHE = "policyNumbers";
    private static final String POLICY_STATUS_ACTIVE = "Active";
    private static final String STRIPES_SUCCESS_HEADER = "Stripes-Success";
    private static final String STRIPES_HEADER_OK = "OK";
    private static final String REPORT_COMPLETE_HEADER = "IsComplete";
    private static final String REPORT_EDITABLE_HEADER = "IsEditable";
    private static final String REPORT_FINAL_AUDIT_HEADER = "IsFinalAudit";
    private static final String REPORT_OPEN_HEADER = "IsOpen";
    private static final String GET_POLICY_PERIODS_FAILED = "PayrollReport.Error.GetPolicyPeriodsFailed";
    // PREFORMATTED MESSAGES
    protected static final String PREMIUM_ISSUE_MESSAGE = "Press Calculate to Calculate Premium.";
    protected List<String> policyNumbers;
    protected Map<String, ReportPeriodWrapper> policyPeriodsMap;
    protected User prUser;
    protected Policyholder prPolicyholder;
    protected CSAuditInformationDetailsPR.CoveredEmployees coveredEmployees;
    protected CSAuditInformationDetailsPR.IncludedOwnerOfficers ownersOfficers;
    protected CSAuditInformationDetailsPR.SupplDiseaseExposures supplDiseaseExposures;
    @Inject
    private PayrollReport pr;
    private final ObjectFactory factory = new ObjectFactory();

    @DefaultHandler
    public Resolution present() {
        if (!Security.checkIfPolicyholder()) {
            return new ForwardResolution(Logout.class);
        }
        clearCache();
        prUser = getUser();
        prPolicyholder = (Policyholder) prUser.getSpecificType();

        try {
            policyNumbers = getPolicyNumbers(prPolicyholder.getPolicyCenterAccountId());

        } catch (GwIntegrationException ex) {
            logger.error("fatal error trying to access policy numbers for " + prPolicyholder.getPolicyCenterAccountId(), ex);
            getContext().getValidationErrors().addGlobalError(new LocalizableError("PayrollReport.Error.GetPoliciesFailed", prPolicyholder.getPolicyCenterAccountId()));
        }
        return new ForwardResolution(PRESENT);
    }

    public Resolution print() {
        return new ForwardResolution(PRINT);
    }

    /**
     * Fills all the needed data from the DB.
     */
    @Before(stages = {LifecycleStage.BindingAndValidation})
    public void fillData() {
        getContext().getResponse().setHeader(STRIPES_SUCCESS_HEADER, STRIPES_HEADER_OK);
    }

    @ValidationMethod(on = "complete")
    public void validatePayrollAmounts(ValidationErrors errors) {
        HashMap amountMap = new HashMap();

        for (int x = 0; x < payrollReportTable.size(); x++) {
            PayrollReportRecordWrapper rpt = payrollReportTable.get(x);
            if (NullChecker.isNotNullOrEmpty(rpt) && rpt.isOwnerOfficer()) {
                amountMap.put(rpt.getClassCode(), rpt.getClassCode());
            }
        }

        for (int x = 0; x < payrollReportTable.size(); x++) {
            PayrollReportRecordWrapper rpt = payrollReportTable.get(x);

            if (NullChecker.isNotNullOrEmpty(rpt) && !rpt.isOwnerOfficer()) {
                if (NullChecker.isNullOrEmpty(rpt.getAuditedAmount())) {
                    errors.add("payrollReportTable[" + x + "]", new LocalizableError("PayrollReport.Error.AuditAmountIsEmpty"));
                    return;
                } else if (rpt.getAuditedAmount() < 0) {
                    errors.add("payrollReportTable[" + x + "]", new LocalizableError("PayrollReport.Error.AuditAmountIsNegative"));
                    return;
                } else if (amountMap.containsKey(rpt.getClassCode()) && rpt.getAuditedAmount() < 0) {
                    errors.add("payrollReportTable[" + x + "]", new LocalizableError("PayrollReport.Error.IncludeCoveredEmployees"));
                    return;
                }

            }

        }
    }

    public static Integer lastInteger(String s) {
        String t = s.replaceAll(",", "");
        int i = t.length();
        while (i > 0 && Character.isDigit(t.charAt(i - 1))) {
            i--;
        }
        return new Integer(t.substring(i));
    }

    public Resolution complete() {

        Resolution ret = null;

        try {
            try {
//printPayrollReports("complete:",payrollReportTable);
                clonedPayrollReportTable = (List<PayrollReportRecordWrapper>) getContext().getRequest().getSession().getAttribute(PAYROLL);
//                printPayrollReports("complete before clonedpayroll", clonedPayrollReportTable);
                payrollReportTable = updateReportTable(clonedPayrollReportTable, payrollReportTable);
//                printPayrollReports("complete after payrollreporttable", payrollReportTable);
                getContext().getRequest().getSession().removeAttribute(PAYROLL);
                getContext().getRequest().getSession().removeAttribute(DISEASE_CODES);
            } catch (GwIntegrationException ex) {
                logger.error(ex.getMessage());
            }

            CSAuditInformationDetailsPR.CoveredEmployees preport = createPayrollReportTables(payrollReportTable);
            CSAuditInformationDetailsPR.SupplDiseaseExposures supplDiseaseExposurePR = createDiseaseReportTables(payrollReportTable);
            CSPremiumDetailsPR response = pr.updateAuditInformationDetails(prPublicID, preport, supplDiseaseExposurePR, "portal", "portal", true, true);

            this.setPremium(response.getEstimatedAnnualPremium().doubleValue());
            this.setPrPublicID(response.getAuditInformation().getPublicID());
            policyPeriodsMap = getPolicyPeriodsMap(prPolicyNumber);
            ReportPeriodWrapper newReportPeriod = policyPeriodsMap.get(this.getPrPublicID());
            this.setPolicyPeriod(newReportPeriod);

            // display the payroll report with updated values
            ret = updatePolicyForm();

        } catch (GwIntegrationException ex) {
            logger.error("fatal error trying to access complete audit for " + prPublicID, ex);

            if (policyPeriod != null && policyPeriod.getComplete()) {
                logger.error("fatal error trying to access complete audit for " + prPublicID, ex);
                getContext().getValidationErrors().add("payrollReportButtons", new LocalizableError("PayrollReport.Error.CompleteFinalAudit", prPolicyNumber));
            } else if (policyPeriod != null && (policyPeriod.getPolicyStatus().equalsIgnoreCase("CANCELING") ||
                    policyPeriod.getPolicyStatus().equalsIgnoreCase("FINAL AUDIT COMPLETED") )) {
                logger.error("fatal error trying to access final audit for " + prPublicID, ex);
                getContext().getValidationErrors().add("payrollReportButtons", new LocalizableError("PayrollReport.Error.ReportIsInAuditOrCancellation", prPolicyNumber));
            } else {
                getContext().getValidationErrors().add("payrollReportButtons", new LocalizableError("PayrollReport.Error.Generic", prPolicyNumber));
            }
            ret = showErrorHtml(getContext().getValidationErrors());
        } finally {
            getContext().getRequest().getSession().removeAttribute(PAYROLL);
            getContext().getRequest().getSession().removeAttribute(DISEASE_CODES);
        }
        return ret;
    }

    @DontValidate
    public Resolution cancel() {
        clearCache();

        return new RedirectResolution(PayrollReportActionBean.class);
    }

    public Resolution updatePolicyPeriods() {
        try {
            // clear any messages on the screen.
            getContext().getMessages().clear();
            policyPeriodsMap = getPolicyPeriodsMap(prPolicyNumber);
        } catch (GwIntegrationException ex) {
            logger.error("fatal error trying to access policy periods for " + prPolicyNumber, ex);
            getContext().getValidationErrors().addGlobalError(new LocalizableError(GET_POLICY_PERIODS_FAILED, prPolicyNumber));
        }
        return new ForwardResolution(PERIODS);
    }

    public Resolution updatePolicyForm() {
        try {
            String cachedPolicyNumber = (String) getContext().getRequest().getSession().getAttribute(PR_POLICY_NUMBER_CACHE);
            if (NullChecker.isNullOrEmpty(cachedPolicyNumber)) {
                if (NullChecker.isNotNullOrEmpty(this.prPolicyNumber)) {
                    cachedPolicyNumber = this.prPolicyNumber;
                }
            }
            policyPeriodsMap = getPolicyPeriodsMap(cachedPolicyNumber);
         //   dumpMap(policyPeriodsMap);
            policyPeriod = policyPeriodsMap.get(prPublicID);
            /*
            When the app comes back to the payroll report screen after finalize,
            The publicid has changed! The code changes store the start date
            of the policy period so that it can be used later. Without this, the
            policy number and reporting period are returned blank.
            */
            if(policyPeriod != null)
            {
                getContext().getRequest().getSession().setAttribute(PERIOD_START_DATE_CACHE,policyPeriod.getStartDate());
            }
            if (policyPeriod == null) {
                String pid = findPublicIdFromPolicyPeriodStart((Date) getContext().getRequest().getSession().getAttribute(PERIOD_START_DATE_CACHE),policyPeriodsMap);
//                String pid1 = this.setPrPublicID(response.getAuditInformation().getPublicID());
                policyPeriodsMap = getPolicyPeriodsMap(cachedPolicyNumber, true);
                policyPeriod = policyPeriodsMap.get(pid);
            } else if (!policyPeriod.getEditable()) {
                policyPeriod.setEditable(Boolean.FALSE);
                getContext().getValidationErrors().addGlobalError(new LocalizableError("PayrollReport.Error.Generic", prPolicyNumber));
                getContext().getMessages().add(new SimpleMessage(policyPeriod.getPolicyStatus()));
            }
            String pubId = policyPeriod == null ? prPublicID : policyPeriod.getPublicID();
            if (policyPeriod != null && NullChecker.isNotNullOrEmpty(policyPeriod.getPremium())) {
                this.setPremium(policyPeriod.getPremium());
            }

            // TODO - combine these lists.
            payrollReportTable = new ArrayList<PayrollReportRecordWrapper>();
            ownerOfficersTable = new ArrayList<PayrollReportRecordWrapper>();

            FindAuditInformationDetailsResponsePR.Return response = pr.findAuditInformationDetails(pubId);

            if (response != null && NullChecker.isNotNullOrEmpty(response.getCSAuditInformationDetails())) {
                clonedPayrollReportTable = new ArrayList<PayrollReportRecordWrapper>();
                createCoveredEmployeeEntries(response);
                createSupplDiseaseEntries(response);
                payrollReportTable.addAll(diseaseCodeTable);

                if (diseaseCodeTable != null && !diseaseCodeTable.isEmpty()) {
                    clonedPayrollReportTable.addAll(diseaseCodeTable);
                }

                clonedOwnerOfficersTable = new ArrayList<PayrollReportRecordWrapper>();
                createIncludedOwnerOfficers(response);

  //              printPayrollReports("cloned payrollreporttable", clonedPayrollReportTable);
                Collections.sort(payrollReportTable);

                getContext().getRequest().getSession().setAttribute(PAYROLL, clonedPayrollReportTable);
                payrollReportTable.addAll(ownerOfficersTable);
                ListIterator<PayrollReportRecordWrapper> listIterator = payrollReportTable.listIterator();
   //             printPayrollReports("before payrollreporttable", payrollReportTable);


                

                PayrollReportRecordWrapper prrw = null;

                // add up the totals for each class code
                while (listIterator.hasNext()) {

                    prrw = listIterator.next();
                    if(prrw.isOwnerOfficer())
                        continue;
                    if( prrw.getAuditedAmount() == null)
                        continue;
                    if (!totalMap.containsKey(prrw.getClassCode() + NullChecker.blankIfNull(prrw.getClassCodeDescInd()))) {
                        totalMap.put(prrw.getClassCode() + NullChecker.blankIfNull(prrw.getClassCodeDescInd()), prrw.getAuditedAmount());
                    } else {
                        Integer i = totalMap.get(prrw.getClassCode() + NullChecker.blankIfNull(prrw.getClassCodeDescInd()));
                        totalMap.put(prrw.getClassCode() + NullChecker.blankIfNull(prrw.getClassCodeDescInd()), i + NullChecker.zeroIfNull(prrw.getAuditedAmount()));
                    }

                }
                removeDuplicates(payrollReportTable);
//                printTotalMap("total map in update policy form", totalMap);
            } else {
                logger.error("fatal error trying to access payroll form for " + prPolicyNumber + " " + prPublicID + ": returned record was null");
                getContext().getValidationErrors().addGlobalError(new LocalizableError(GET_POLICY_PERIODS_FAILED, prPolicyNumber + " " + prPublicID));
            }

        } catch (GwIntegrationException ex) {
            logger.error("fatal error trying to access payroll form for " + prPolicyNumber + " " + prPublicID, ex);
            getContext().getValidationErrors().addGlobalError(new LocalizableError(GET_POLICY_PERIODS_FAILED, prPolicyNumber + " " + prPublicID));
        } catch (GwNoResultsFoundException ex) {
            logger.error("fatal error - no records found for payroll report " + prPolicyNumber + " " + prPublicID, ex);
            getContext().getValidationErrors().addGlobalError(new LocalizableError("PayrollReport.Error.GetAuditDetailsNoRecords", prPolicyNumber + " " + prPublicID));
        }
        getContext().getRequest().getSession().setAttribute(TOT_MAP, totalMap);
//        printPayrollReports("after", payrollReportTable);
//        printTotalMap("total map updatePolicyForm", totalMap);
        return new ForwardResolution(FORM);
    }

    public Map<String, ReportPeriodWrapper> getPolicyPeriodsMap(String policyNumber) throws GwIntegrationException {
        return getPolicyPeriodsMap(policyNumber, true);
    }

    public Map<String, ReportPeriodWrapper> getPolicyPeriodsMap(String policyNumber, boolean rebuildCache) throws GwIntegrationException {
        String cachedPolicyNumber = (String) getContext().getRequest().getSession().getAttribute(PR_POLICY_NUMBER_CACHE);
        Map<String, ReportPeriodWrapper> cachedReportPeriodsMap = (HashMap<String, ReportPeriodWrapper>) getContext().getRequest().getSession().getAttribute(PR_REPORT_PERIODS_MAP_CACHE);

        // Check to see if new or different policy is being processed
        if (NullChecker.isNullOrEmpty(cachedPolicyNumber) || !policyNumber.equals(cachedPolicyNumber)) {
            getContext().getRequest().getSession().setAttribute(PR_POLICY_NUMBER_CACHE, policyNumber);
            this.setPrPolicyNumber(policyNumber);
            cachedReportPeriodsMap = null;
            clearPolicyPeriodCache();
        }

        if (rebuildCache) {
            cachedReportPeriodsMap = null;
            clearPolicyPeriodCache();
        }

        if (NullChecker.isNullOrEmpty(cachedReportPeriodsMap)) {
            cachedReportPeriodsMap = new HashMap<String, ReportPeriodWrapper>();

            FindAuditInformationsResponsePR.Return response = pr.findAuditInformations(policyNumber, Boolean.FALSE);

            for (Entry e : response.getEntry()) {
                ReportPeriodWrapper rpWrapper = new ReportPeriodWrapper();

                if (NullChecker.isNotNullOrEmpty(e)) {
                    rpWrapper.setPolicyStatus(e.getCSAuditInformation().getPolicyStatus());
                    CSAuditInformationPR csAuditInformationPR = e.getCSAuditInformation();
                    if (NullChecker.isNotNullOrEmpty(csAuditInformationPR)) {
                        CSAuditInformationPR.DisplayableAuditInfo displayableAuditInfoPR = csAuditInformationPR.getDisplayableAuditInfo();
                        if (NullChecker.isNotNullOrEmpty(displayableAuditInfoPR)) {
                            rpWrapper.setStartDate(DateTimeHelper.xmlGregorianCalendarToDate(displayableAuditInfoPR.getStartDate()));
                            rpWrapper.setEndDate(DateTimeHelper.xmlGregorianCalendarToDate(displayableAuditInfoPR.getEndDate()));
                            if (NullChecker.isNotNullOrEmpty(displayableAuditInfoPR.getTransactionAmount())) {
                                rpWrapper.setPremium(displayableAuditInfoPR.getTransactionAmount().doubleValue());
                            }
                            rpWrapper.formatPeriod();
                            CSAuditInformationPR.DisplayableAuditInfo.ActualInfo actualInfoPR = displayableAuditInfoPR.getActualInfo();
                            if (NullChecker.isNotNullOrEmpty(actualInfoPR)) {
                                rpWrapper.setPublicID(actualInfoPR.getPublicID());
                                rpWrapper.setComplete(actualInfoPR.getIsComplete());
                                getContext().getResponse().setHeader(REPORT_COMPLETE_HEADER, actualInfoPR.getIsComplete().toString());
//testing                                
                                rpWrapper.setEditable(actualInfoPR.getIsEditable());
                                
//rpWrapper.setEditable(false);                                
                                
                                getContext().getResponse().setHeader(REPORT_EDITABLE_HEADER, actualInfoPR.getIsEditable().toString());
                                rpWrapper.setFinalAudit(actualInfoPR.getIsFinalAudit());
                                getContext().getResponse().setHeader(REPORT_FINAL_AUDIT_HEADER, actualInfoPR.getIsFinalAudit().toString());
                                rpWrapper.setOpen(actualInfoPR.getIsOpen());
                                getContext().getResponse().setHeader(REPORT_OPEN_HEADER, actualInfoPR.getIsOpen().toString());
                                rpWrapper.setPremiumReport(actualInfoPR.getIsPremiumReport());
                                rpWrapper.setScheduled(actualInfoPR.getIsScheduled());
                                CSAuditInformationPR.DisplayableAuditInfo.ActualInfo.PolicyTerm policyTermPR = actualInfoPR.getPolicyTerm();
                                if (NullChecker.isNotNullOrEmpty(policyTermPR)) {
                                    CSAuditInformationPR.DisplayableAuditInfo.ActualInfo.PolicyTerm.Policy policyTermPolicyPR = policyTermPR.getPolicy();
                                    if (NullChecker.isNotNullOrEmpty(policyTermPolicyPR)) {
                                        CSAuditInformationPR.DisplayableAuditInfo.ActualInfo.PolicyTerm.Policy.LatestPeriod latestPeriodPR = policyTermPolicyPR.getLatestPeriod();
                                        if (NullChecker.isNotNullOrEmpty(latestPeriodPR)) {
                                            rpWrapper.setPolicyTerm(latestPeriodPR.getDisplayPolicyNumber());
                                        } else {
                                            rpWrapper.setPolicyTerm(policyNumber + "*");
                                        }
                                    } else {
                                        rpWrapper.setPolicyTerm(policyNumber + "*");
                                    }
                                }
                                cachedReportPeriodsMap.put(rpWrapper.getPublicID(), rpWrapper);
                            }
                        }
                    }
                }
            }
        }
        cachedReportPeriodsMap = MapUtil.sortByValue(cachedReportPeriodsMap);
/*
        Iterator<Map.Entry<String, ReportPeriodWrapper>> entries = cachedReportPeriodsMap.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry<String, ReportPeriodWrapper> entry = entries.next();
        logger.error(entry.getValue().toString());

        }
  logger.error("================================");
        */
        Map<String, ReportPeriodWrapper> newMap = new HashMap<String, ReportPeriodWrapper>();
        
        Iterator<Map.Entry<String, ReportPeriodWrapper>> entries = cachedReportPeriodsMap.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry<String, ReportPeriodWrapper> entry = entries.next();
  //      logger.error(entry.getValue().toString());
            newMap.put(entry.getValue().getPublicID(), entry.getValue());
            if((entry.getValue().getPremium() == null))
                break;
        }
     
        getContext().getRequest().getSession().setAttribute(PR_REPORT_PERIODS_MAP_CACHE, newMap);

        return newMap;
    }

    public List<String> getPolicyNumbers(String accountID) throws GwIntegrationException {
        String cachedAccountID = (String) getContext().getRequest().getSession().getAttribute(ACCOUNT_ID_CACHE);
        List<String> cachedPolicyNumbers = (List<String>) getContext().getRequest().getSession().getAttribute(POLICY_NUMBERS_CACHE);

        // Check to see if new or different policy is being processed
        if (NullChecker.isNullOrEmpty(cachedAccountID) || !accountID.equals(cachedAccountID)) {
            getContext().getRequest().getSession().setAttribute(ACCOUNT_ID_CACHE, accountID);
            cachedPolicyNumbers = null;
            clearPolicyNumbersCache();
        }

        if (NullChecker.isNullOrEmpty(cachedPolicyNumbers)) {
            cachedPolicyNumbers = new ArrayList<String>();
            FindPolicyNumbersResponsePR.Return response = pr.findPolicyNumbers(accountID);

            for (String s : response.getEntry()) {
                FindPolicyInformationResponsePR.Return info = pr.findPolicyInformation(s);
                if (NullChecker.isNotNullOrEmpty(info)) {
                    CSPolicyPeriodPR csPolicyPeriodPR = info.getCSPolicyPeriod();
                    if (NullChecker.isNotNullOrEmpty(csPolicyPeriodPR)) {
                        CSPolicyPeriodPR.PolicyPeriod policyPeriodPR = csPolicyPeriodPR.getPolicyPeriod();
                        if (NullChecker.isNotNullOrEmpty(policyPeriodPR)) {
                            CSPolicyPeriodPR.PolicyPeriod.Policy policyPR = policyPeriodPR.getPolicy();
                            if (NullChecker.isNotNullOrEmpty(policyPR)) {
                                CSPolicyPeriodPR.PolicyPeriod.Policy.Account accountPR = policyPR.getAccount();
                                if (NullChecker.isNotNullOrEmpty(accountPR)) {
                                    CSPolicyPeriodPR.PolicyPeriod.Policy.Account.AccountStatus accountStatusPR = accountPR.getAccountStatus();
                                    if (NullChecker.isNotNullOrEmpty(accountStatusPR)) {
                                        String code = accountStatusPR.getCode();
                                        if (NullChecker.isNotNullOrEmpty(code) && code.equalsIgnoreCase(POLICY_STATUS_ACTIVE)) {
                                            cachedPolicyNumbers.add(s);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            getContext().getRequest().getSession().setAttribute(POLICY_NUMBERS_CACHE, cachedPolicyNumbers);
        }

        return cachedPolicyNumbers;
    }

    protected CSAuditInformationDetailsPR.SupplDiseaseExposures createDiseaseReportTables(List<PayrollReportRecordWrapper> reportTable) {
        CSAuditInformationDetailsPR.SupplDiseaseExposures coveredDiseasePR = new CSAuditInformationDetailsPR.SupplDiseaseExposures();
        for (PayrollReportRecordWrapper r : reportTable) {
            if (r.isDiseaseCode()) {
                CSAuditInformationDetailsPR.SupplDiseaseExposures.Entry entryPR = new CSAuditInformationDetailsPR.SupplDiseaseExposures.Entry();

                CSAuditInformationDetailsPR.SupplDiseaseExposures.Entry.SupplDiseaseExposure supplDiseaseExposure
                        = new CSAuditInformationDetailsPR.SupplDiseaseExposures.Entry.SupplDiseaseExposure();
                CSAuditInformationDetailsPR.SupplDiseaseExposures.Entry.SupplDiseaseExposure.FixedId fixedId
                        = new CSAuditInformationDetailsPR.SupplDiseaseExposures.Entry.SupplDiseaseExposure.FixedId();
                CSAuditInformationDetailsPR.SupplDiseaseExposures.Entry.SupplDiseaseExposure.Location location = new CSAuditInformationDetailsPR.SupplDiseaseExposures.Entry.SupplDiseaseExposure.Location();
                location.setPrimaryLoc(r.isPrimaryLoc());

                fixedId.setObjValue(r.getFixedId());
                supplDiseaseExposure.setFixedId(fixedId);
                supplDiseaseExposure.setLocation(location);
                supplDiseaseExposure.setAuditedAmount(r.getAuditedAmount());
                entryPR.setSupplDiseaseExposure(supplDiseaseExposure);
                coveredDiseasePR.getEntry().add(entryPR);
            }
        }

        return coveredDiseasePR;
    }

    protected CSAuditInformationDetailsPR.CoveredEmployees createPayrollReportTables(List<PayrollReportRecordWrapper> reportTable) {
        CSAuditInformationDetailsPR.CoveredEmployees coveredEmployeesPR = new CSAuditInformationDetailsPR.CoveredEmployees();

        for (PayrollReportRecordWrapper r : reportTable) {

            if (!r.isOwnerOfficer() && !r.isDiseaseCode()) {

                CSAuditInformationDetailsPR.CoveredEmployees.Entry entryPR = new CSAuditInformationDetailsPR.CoveredEmployees.Entry();

                CSAuditInformationDetailsPR.CoveredEmployees.Entry.CoveredEmployee csCoveredEmployeePR = new CSAuditInformationDetailsPR.CoveredEmployees.Entry.CoveredEmployee();

                CSAuditInformationDetailsPR.CoveredEmployees.Entry.CoveredEmployee.FixedId fixedIdPR = new CSAuditInformationDetailsPR.CoveredEmployees.Entry.CoveredEmployee.FixedId();
                fixedIdPR.setObjValue(r.getFixedId());
                csCoveredEmployeePR.setFixedId(fixedIdPR);
                csCoveredEmployeePR.setAuditedAmount(r.getAuditedAmount());
                entryPR.setCoveredEmployee(csCoveredEmployeePR);
                coveredEmployeesPR.getEntry().add(entryPR);
            }
        }
        return coveredEmployeesPR;
    }

    protected void createSupplDiseaseEntries(FindAuditInformationDetailsResponsePR.Return response) {

        if (NullChecker.isNotNullOrEmpty(response.getCSAuditInformationDetails().getSupplDiseaseExposures())) {
            supplDiseaseExposures = response.getCSAuditInformationDetails().getSupplDiseaseExposures();
            if (supplDiseaseExposures != null && NullChecker.isNotNullOrEmpty(supplDiseaseExposures.getEntry())) {

                for (CSAuditInformationDetailsPR.SupplDiseaseExposures.Entry e : supplDiseaseExposures.getEntry()) {
                    PayrollReportRecordWrapper record = new PayrollReportRecordWrapper();
                    if (e != null && NullChecker.isNotNullOrEmpty(e.getSupplDiseaseExposure())) {

                        record.setAuditedAmount(e.getSupplDiseaseExposure().getAuditedAmount());
                        record.setBasisType("diseasecode");
                        record.setDiseaseCode(Boolean.TRUE);
                        record.setLocation(e.getSupplDiseaseExposure().getLocation().getAddressLine1() + "     " + e.getSupplDiseaseExposure().getLocation().getCity());

                        if (NullChecker.isNotNullOrEmpty(e.getSupplDiseaseExposure().getDiseaseCode())) {
                            record.setAuditedAmount(e.getSupplDiseaseExposure().getAuditedAmount());
                            record.setClassCode(e.getSupplDiseaseExposure().getDiseaseCode().getCode());
                            record.setClassCodeDescInd("");
                            record.setClassCodeShortDesc(e.getSupplDiseaseExposure().getDiseaseCode().getSupplDiseaseLoadingType());
                            if (NullChecker.isNullOrEmpty(e.getSupplDiseaseExposure().getLocation().isPrimaryLoc())) {
                                logger.error("primaryLoc is null");
                            } else {
                                record.setPrimaryLoc(e.getSupplDiseaseExposure().getLocation().isPrimaryLoc());
                            }

                            if (NullChecker.isNotNullOrEmpty(e.getSupplDiseaseExposure().getFixedId())) {
                                record.setFixedId(e.getSupplDiseaseExposure().getFixedId().getObjValue());
                            }
                        }
                    }

                    if (NullChecker.isNotNullOrEmpty(record)) {
                        //      record.setOwnerOfficer(false);
                        diseaseCodeTable.add(record);
                    }
                }
            }

        }

    }

    protected void createCoveredEmployeeEntries(FindAuditInformationDetailsResponsePR.Return response) {
        try {
            if (NullChecker.isNotNullOrEmpty(response.getCSAuditInformationDetails().getCoveredEmployees())) {
                coveredEmployees = response.getCSAuditInformationDetails().getCoveredEmployees();

                if (coveredEmployees != null && NullChecker.isNotNullOrEmpty(coveredEmployees.getEntry())) {
                    for (CSAuditInformationDetailsPR.CoveredEmployees.Entry e : coveredEmployees.getEntry()) {
                        PayrollReportRecordWrapper record = new PayrollReportRecordWrapper();

                        if (e != null && NullChecker.isNotNullOrEmpty(e.getCoveredEmployee())) {
                            record.setAuditedAmount(e.getCoveredEmployee().getAuditedAmount());
                            record.setBasisType(e.getCoveredEmployee().getBasisType());
                            record.setLocation(e.getCoveredEmployee().getLocation().getAddressLine1() + "     " + e.getCoveredEmployee().getLocation().getCity());

                            if (NullChecker.isNotNullOrEmpty(e.getCoveredEmployee().getClassCode())) {
                                record.setClassCode(e.getCoveredEmployee().getClassCode().getCode());
                                record.setClassCodeDescInd(e.getCoveredEmployee().getClassCode().getDescInd());
                                record.setClassCodeShortDesc(e.getCoveredEmployee().getClassCode().getShortDesc().trim());
                                if (NullChecker.isNullOrEmpty(e.getCoveredEmployee().getLocation().isPrimaryLoc())) {
                                    logger.error("primaryLoc is null");
                                } else {
                                    record.setPrimaryLoc(e.getCoveredEmployee().getLocation().isPrimaryLoc());
                                }
                            }

                            if (NullChecker.isNotNullOrEmpty(e.getCoveredEmployee().getLocation().isPrimaryLoc())) {
                                record.setPrimaryLoc(e.getCoveredEmployee().getLocation().isPrimaryLoc());
                            } else {
                                logger.error("primaryLoc value is null");
                            }

                            if (NullChecker.isNotNullOrEmpty(e.getCoveredEmployee().getFixedId())) {
                                record.setFixedId(e.getCoveredEmployee().getFixedId().getObjValue());
                            }
                        }

                        if (NullChecker.isNotNullOrEmpty(record)) {
                            record.setOwnerOfficer(false);
                            payrollReportTable.add(record);
                            clonedPayrollReportTable.add(SerializationUtils.clone(record));
                        }
                    }

                }

            }

        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }
    }

    protected void createIncludedOwnerOfficers(FindAuditInformationDetailsResponsePR.Return response) {
        if (NullChecker.isNotNullOrEmpty(response.getCSAuditInformationDetails().getIncludedOwnerOfficers())) {
            ownersOfficers = response.getCSAuditInformationDetails().getIncludedOwnerOfficers();

            if (ownersOfficers != null && NullChecker.isNotNullOrEmpty(ownersOfficers.getEntry())) {
                for (CSAuditInformationDetailsPR.IncludedOwnerOfficers.Entry e : ownersOfficers.getEntry()) {
                    PayrollReportRecordWrapper record = new PayrollReportRecordWrapper();

                    //Entry.OwnersOfficers 
                    if (NullChecker.isNotNullOrEmpty(e.getIncludedOwnerOfficer())) {
                        if (NullChecker.isNotNullOrEmpty(e.getIncludedOwnerOfficer().getAccountContactRole())) {
                            if (NullChecker.isNotNullOrEmpty(e.getIncludedOwnerOfficer().getAccountContactRole().getAccountContact())) {
                                if (NullChecker.isNotNullOrEmpty(e.getIncludedOwnerOfficer().getAccountContactRole().getAccountContact().getContact())) {
                                    if (NullChecker.isNotNullOrEmpty(e.getIncludedOwnerOfficer().getAccountContactRole().getAccountContact().getContact().getDisplayName())) {
                                        record.setDisplayName(e.getIncludedOwnerOfficer().getAccountContactRole().getAccountContact().getContact().getDisplayName());
                                    }
                                }
                            }

                        }

                        if (NullChecker.isNotNullOrEmpty(e.getIncludedOwnerOfficer().getWC7ClassCode())) {
                            record.setAuditedAmount(e.getPeriodProRatedMiscValue());

                            //Entry.CoveredEmployee.ClassCode
                            if (NullChecker.isNotNullOrEmpty(e.getIncludedOwnerOfficer().getWC7ClassCode())) {
                                record.setClassCode(e.getIncludedOwnerOfficer().getWC7ClassCode().getCode());
                                record.setClassCodeDescInd(e.getIncludedOwnerOfficer().getWC7ClassCode().getDescInd());
                                record.setClassCodeShortDesc(e.getIncludedOwnerOfficer().getWC7ClassCode().getShortDesc().trim());
                            }
                        }
                    }
                    if (record.getAuditedAmount() == null) {
                        record.setAuditedAmount(0);
                    }
                    record.setPrimaryLoc(Boolean.FALSE);
                    if (NullChecker.isNotNullOrEmpty(record)) {
                        record.setOwnerOfficer(true);
                        ownerOfficersTable.add(record);
                        clonedOwnerOfficersTable.add(SerializationUtils.clone(record));
                    }
                }
            }
        }
    }

    /**
     * Converts errors to HTML and streams them back to the browser.
     */
    public Resolution handleValidationErrors(ValidationErrors errors) throws Exception {
        return showErrorHtml(errors);
    }

    private Resolution showErrorHtml(ValidationErrors errors) {
        StringBuilder message = new StringBuilder();
        message.append("<div class=\"error\" style=\"color:#b72222; font-weight: bold\"><ol>");
        for (List<ValidationError> fieldErrors : errors.values()) {
            for (ValidationError error : fieldErrors) {
                message.append("<li style=\"color: #b72222;\">");
                message.append(error.getMessage(getContext().getLocale()));
                message.append("</li>");
            }
        }
        message.append("</ol></div>");

        return new StreamingResolution("text/html", new StringReader(message.toString()));
    }

    private void clearCache() {
        getContext().getRequest().getSession().removeAttribute(PR_POLICY_NUMBER_CACHE);
        getContext().getRequest().getSession().removeAttribute(PR_REPORT_PERIODS_MAP_CACHE);
        getContext().getRequest().getSession().removeAttribute(ACCOUNT_ID_CACHE);
        getContext().getRequest().getSession().removeAttribute(POLICY_NUMBERS_CACHE);
    }

    private void clearPolicyPeriodCache() {
        getContext().getRequest().getSession().removeAttribute(PR_REPORT_PERIODS_MAP_CACHE);
    }

    private void clearPolicyNumbersCache() {
        getContext().getRequest().getSession().removeAttribute(POLICY_NUMBERS_CACHE);
    }

    private void printPayrollReports(String message, List<PayrollReportRecordWrapper> prrw) {

        logger.debug(message);
        if (prrw == null) {
            logger.debug("null value");
        }
        for (PayrollReportRecordWrapper prt : prrw) {
            logger.debug(prt.getClassCode() + " "+ prt.getClassCodeDescInd() + " officer " + prt.isOwnerOfficer()+ " "+ prt.getPrintAuditedAmount() + " primary " + prt.isPrimaryLoc());
        }
    }

    private void printTotalMap(String message, HashMap<String, Integer> m) {
        logger.debug(message);
        for (Map.Entry<String, Integer> entry : m.entrySet()) {
            logger.debug(entry.getKey() + " : " + entry.getValue());
        }

    }

    private Map<String, Integer> listToMap(List<PayrollReportRecordWrapper> list) {
        Map<String, Integer> map = new HashMap();
        for (PayrollReportRecordWrapper i : list) {
            if(i.isOwnerOfficer())
                continue;
            String s = i.getClassCodeDescInd();
            if (s == null) {
                s = "";
            }
            if (i.getClassCode() == null) {
                logger.error("classcode is null");
            }
            if(i.isPrimaryLoc() == null){
                logger.error("primary loc is null "+i.getClassCode()+" "+i.getClassCodeDescInd()+" "+i.getAuditedAmount());
            }
            map.put(i.getClassCode() + s, i.getAuditedAmount());
        }
        return map;
    }

    private List<PayrollReportRecordWrapper> updateReportTable(List<PayrollReportRecordWrapper> clonedPayroll,
            List<PayrollReportRecordWrapper> payrollReportTable) throws GwIntegrationException {

        if (payrollReportTable == null || clonedPayroll == null) {
            return clonedPayroll;
        }
  //      printPayrollReports("updatereporttable enter clonedPayroll", clonedPayroll);
  //      printPayrollReports("updatereporttable enter PayrollReportTable", payrollReportTable);
        Map<String, Integer> m = listToMap(payrollReportTable);
        // totally clear out audited amount
        for (PayrollReportRecordWrapper prt : clonedPayroll) {
            prt.setAuditedAmount(0);
        }

        for (PayrollReportRecordWrapper prt : clonedPayroll) {
            if(prt.isOwnerOfficer() )
                continue;
                Integer tot = m.get(prt.getClassCode() + NullChecker.blankIfNull(prt.getClassCodeDescInd()));
                if(tot == null)
                    continue;
                prt.setAuditedAmount(tot);
              // remove entry so that it won't be reused
                m.remove(prt.getClassCode() + NullChecker.blankIfNull(prt.getClassCodeDescInd()));
        }
        getContext().getRequest().getSession().removeAttribute(TOT_MAP);
 //       printPayrollReports("updatereporttable leave clonedPayroll", clonedPayroll);
        return clonedPayroll;
    }
    
    String findPublicIdFromPolicyPeriodStart(Date start,Map<String, ReportPeriodWrapper> policyPeriodsMap)
    {
        Set keys = policyPeriodsMap.keySet();
        for (Iterator it = keys.iterator(); it.hasNext();) {
            ReportPeriodWrapper rpw = policyPeriodsMap.get(it.next());
            if(rpw.getStartDate().equals(start))
                return rpw.getPublicID();
        }
        return null;
    }
    
    void dumpMap(Map<String, ReportPeriodWrapper> policyPeriodsMap)
    {
        Set keys = policyPeriodsMap.keySet();
        for (Iterator it = keys.iterator(); it.hasNext();) {
            ReportPeriodWrapper rpw = policyPeriodsMap.get(it.next());
            dump("debugging map",rpw);
        }
    }

    void dump(String comment, ReportPeriodWrapper p) {
        
        logger.debug(comment + " " + p.getPeriodStartDisplay() + " " + p.getPeriodEndDisplay());
        logger.debug("public id "+p.getPublicID());
        logger.debug("payrollperiod complete " + p.getComplete());
        logger.debug("payrollperiod final audit " + p.getFinalAudit());
        logger.debug("payrollperiod open " + p.getOpen());
        logger.debug("payrollperiod premium report " + p.getPremiumReport());
        logger.debug("payrollperiod scheduled " + p.getScheduled());
        logger.debug("payrollperiod editable " + p.getEditable());
    }

    public int removeDuplicates(List<PayrollReportRecordWrapper> reports) {

        int size = reports.size();
        int duplicates = 0;

    // not using a method in the check also speeds up the execution
        // also i must be less that size-1 so that j doesn't
        // throw IndexOutOfBoundsException
        for (int i = 0; i < size - 1; i++) {
        // start from the next item after strings[i]
            // since the ones before are checked
            for (int j = i + 1; j < size; j++) {
            // no need for if ( i == j ) here
                if( reports.get(j).isOwnerOfficer() )
                    continue;

                if (!reports.get(j).equals(reports.get(i)) ) {
                    continue;
                }
                duplicates++;
                Integer currentAmount = reports.get(i).getAuditedAmount();
                if (currentAmount == null) {
                    reports.get(i).setAuditedAmount(0);
                    currentAmount = 0;
                }
                Integer amount = reports.get(j).getAuditedAmount();
                if (amount == null) {
                    reports.get(j).setAuditedAmount(0);
                    amount = 0;
                }
                reports.get(i).setAuditedAmount(currentAmount + amount);
                reports.remove(j);
                // decrease j because the array got re-indexed
                j--;
                // decrease the size of the array
                size--;
            } // for j
        } // for i

        return duplicates;

    }

}
