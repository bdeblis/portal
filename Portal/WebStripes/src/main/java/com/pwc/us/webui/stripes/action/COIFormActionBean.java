package com.pwc.us.webui.stripes.action;

import com.google.inject.Inject;
import com.pwc.us.common.COIRequest;
import com.pwc.us.common.PayrollReport;
import com.pwc.us.common.exception.GwIntegrationException;
import com.pwc.us.common.model.CertificateOfInsuranceRequest;
import com.pwc.us.common.model.PolicyTerm;
import com.pwc.us.common.model.Policyholder;
import com.pwc.us.common.model.UsState;
import com.pwc.us.common.model.User;
import com.pwc.us.common.model.payrollreport.CSPolicyPeriodPR;
import com.pwc.us.common.model.payrollreport.FindPolicyInformationResponsePR;
import com.pwc.us.common.model.payrollreport.FindPolicyNumbersResponsePR;
import com.pwc.us.common.utils.NullChecker;
import com.pwc.us.webui.stripes.util.Security;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sourceforge.stripes.action.Before;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.DontValidate;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.SimpleMessage;
import net.sourceforge.stripes.controller.LifecycleStage;
import net.sourceforge.stripes.validation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * Robert Snelling <robert.snelling@us.pwc.com>
 */
public class COIFormActionBean extends BaseActionBean {

    protected static final String PHONE_REGEX = "^[2-9]\\d{2}-[2-9]\\d{2}-\\d{4}$";
    private static Logger logger = LoggerFactory.getLogger(COIFormActionBean.class);
    private static final String FORM = "/WEB-INF/jsp/coi_form.jsp";
    private static final String INNER_FORM = "/WEB-INF/jsp/coi_inner_form.jsp";
    private List<PolicyTerm> terms;
    protected List<String> policyNumbers;
    protected String coiPolicyNumber;
    private Map<Integer, String> termsMap = new HashMap<Integer, String>();
    @Inject
    private COIRequest coiRequest;
    @Inject
    private PayrollReport pr;

    /**
     * Sets the Stripes Success for Ajax
     */
    @Before(stages = {LifecycleStage.BindingAndValidation})
    public void fillData() {
        getContext().getResponse().setHeader("Stripes-Success", "OK");
    }

    @DefaultHandler
    public Resolution form() {
        if (!Security.checkIfPolicyholder()) {
            return new ForwardResolution(Logout.class);
        }
        
        Resolution ret = null;
        User user = getUser();
        Policyholder policyholder = (Policyholder) user.getSpecificType();

        try {
            coiPolicyNumber = policyholder.getPolicyNumber();
            policyNumbers = getPolicyNumbers(policyholder.getPolicyCenterAccountId());
            terms = getTerms(coiPolicyNumber);
            //getContext().getRequest().setAttribute("terms", terms);
            ret = new ForwardResolution(FORM);
        } catch (GwIntegrationException ex) {
            logger.error("fatal error trying to access policy terms for " + policyholder.getPolicyNumber(), ex);
            getContext().getValidationErrors().addGlobalError(new LocalizableError("COIRequest.Error.GetTermsFailed", this.getCertificateOfInsuranceRequest()));
            ret = new ForwardResolution(FORM);
        }

        return ret;
    }

    public Resolution submit() {
        Resolution ret = null;
        CertificateOfInsuranceRequest request = getCertificateOfInsuranceRequest();
        try {
            termsMap = getTermsMap(request.getPolicyNumber());
            request.setPolicyNumber(coiPolicyNumber);
            request.setTermDesc(termsMap.get(request.getTermNumber()));
            boolean response = coiRequest.processRequest(request);
            //Condition the message baseed on the returned value
            if (response == true) {
                getContext().getMessages().add(
                        new SimpleMessage("Certificate of Insurance Request has been submitted for policy number {0}.",
                        getContext().getRequest().getSession().getAttribute("policy")));
                ret = new ForwardResolution(HelloActionBean.class);
            } else {
                logger.info("Unable to process COI Request " + this.getCertificateOfInsuranceRequest());
                this.getContext().getValidationErrors().addGlobalError(new LocalizableError("COIRequest.Error.RequestFailed", this.getCertificateOfInsuranceRequest()));
                getContext().getRequest().setAttribute("terms", terms);

                ret = new ForwardResolution(this.getClass(), "form");
            }
        } catch (GwIntegrationException ex) {
            logger.error("Unable to process COI Request " + this.getCertificateOfInsuranceRequest(), ex);
            getContext().getValidationErrors().addGlobalError(new LocalizableError("COIRequest.Error.RequestFailed", this.getCertificateOfInsuranceRequest()));
            getContext().getRequest().setAttribute("terms", terms);
            ret = new ForwardResolution(this.getClass(), "form");
        }
        return ret;
    }

    @DontValidate
    public Resolution cancel() {
        getContext().getMessages().add(
                new SimpleMessage("Action cancelled."));
        return new RedirectResolution(HelloActionBean.class);
    }

    @ValidationMethod(on = "submit")
    public void validateCheckBoxes(ValidationErrors errors) {
        boolean selected = false;
        CertificateOfInsuranceRequest request = getCertificateOfInsuranceRequest();

        if (NullChecker.isNullThenFalse(request.getDeliverByEmail()) == true
                || NullChecker.isNullThenFalse(request.getDeliverByFax()) == true
                || NullChecker.isNullThenFalse(request.getDeliverByMail()) == true) {
            selected = true;
        }

        if (!selected) {
            errors.add("deliveryMethodError",
                    new LocalizableError("COIRequest.Error.DeliveryMethodRequired"));
        }

        if (selected) {
            if (NullChecker.isNullThenFalse(request.getDeliverByEmail()) == true) {

                // Validate Email1 Recipient and Address
                if (NullChecker.isNotNullOrEmpty(request.getEmailRecipientName1())
                        && NullChecker.isNotNullOrEmpty(request.getEmailAddress1())) {

                    // Validate Email2 Recipient and Address
                    if ((NullChecker.isNotNullOrEmpty(request.getEmailRecipientName2())
                            && NullChecker.isNotNullOrEmpty(request.getEmailAddress2()))
                            || (NullChecker.isNullOrEmpty(request.getEmailRecipientName2())
                            && NullChecker.isNullOrEmpty(request.getEmailAddress2()))) {

                        if ((NullChecker.isNotNullOrEmpty(request.getEmailRecipientName3())
                                && NullChecker.isNotNullOrEmpty(request.getEmailAddress3()))
                                && (NullChecker.isNullOrEmpty(request.getEmailRecipientName2())
                                && NullChecker.isNullOrEmpty(request.getEmailAddress2()))) {

                            // Email 2 Fields need to be populated before Email 3.
                            errors.add("certificateOfInsuranceRequest.emailAddress2",
                                    new LocalizableError("COIRequest.Error.DeliveryEmail2Before3"));
                        }

                        if ((NullChecker.isNotNullOrEmpty(request.getEmailRecipientName3())
                                && NullChecker.isNotNullOrEmpty(request.getEmailAddress3()))
                                || (NullChecker.isNullOrEmpty(request.getEmailRecipientName3())
                                && NullChecker.isNullOrEmpty(request.getEmailAddress3()))) {
                            // Nothing to do here at this time.
                        } else {
                            if (NullChecker.isNullOrEmpty(request.getEmailRecipientName3())) {
                                errors.add("certificateOfInsuranceRequest.emailRecipientName3",
                                        new LocalizableError("COIRequest.Error.DeliveryRecipientRequired"));
                            }

                            if (NullChecker.isNullOrEmpty(request.getEmailAddress3())) {
                                errors.add("certificateOfInsuranceRequest.emailAddress3",
                                        new LocalizableError("COIRequest.Error.DeliveryEmailRequired"));
                            }
                        }

                    } else {
                        if (NullChecker.isNullOrEmpty(request.getEmailRecipientName2())) {
                            errors.add("certificateOfInsuranceRequest.emailRecipientName2",
                                    new LocalizableError("COIRequest.Error.DeliveryRecipientRequired"));
                        }

                        if (NullChecker.isNullOrEmpty(request.getEmailAddress2())) {
                            errors.add("certificateOfInsuranceRequest.emailAddress2",
                                    new LocalizableError("COIRequest.Error.DeliveryEmailRequired"));
                        }
                    }
                } else {
                    if (NullChecker.isNullOrEmpty(request.getEmailRecipientName1())) {
                        errors.add("certificateOfInsuranceRequest.emailRecipientName1",
                                new LocalizableError("COIRequest.Error.DeliveryRecipientRequired"));
                    }

                    if (NullChecker.isNullOrEmpty(request.getEmailAddress1())) {
                        errors.add("certificateOfInsuranceRequest.emailAddress1",
                                new LocalizableError("COIRequest.Error.DeliveryEmailRequired"));
                    }
                }
            }

            if (NullChecker.isNullThenFalse(request.getDeliverByFax()) == true) {

                // Validate Fax1 Recipient and Address
                if (NullChecker.isNotNullOrEmpty(request.getFaxRecipientName1())
                        && NullChecker.isNotNullOrEmpty(request.getFaxNumber1())) {

                    // Validate Fax2 Recipient and Address
                    if ((NullChecker.isNotNullOrEmpty(request.getFaxRecipientName2())
                            && NullChecker.isNotNullOrEmpty(request.getFaxNumber2()))
                            || (NullChecker.isNullOrEmpty(request.getFaxRecipientName2())
                            && NullChecker.isNullOrEmpty(request.getFaxNumber2()))) {

                        if ((NullChecker.isNotNullOrEmpty(request.getFaxRecipientName3())
                                && NullChecker.isNotNullOrEmpty(request.getFaxNumber3()))
                                && (NullChecker.isNullOrEmpty(request.getFaxRecipientName2())
                                && NullChecker.isNullOrEmpty(request.getFaxNumber2()))) {

                            // Fax 2 Fields need to be populated before Fax 3.
                            errors.add("certificateOfInsuranceRequest.faxNumber2",
                                    new LocalizableError("COIRequest.Error.DeliveryFax2Before3"));
                        }

                        if ((NullChecker.isNotNullOrEmpty(request.getFaxRecipientName3())
                                && NullChecker.isNotNullOrEmpty(request.getFaxNumber3()))
                                || (NullChecker.isNullOrEmpty(request.getFaxRecipientName3())
                                && NullChecker.isNullOrEmpty(request.getFaxNumber3()))) {
                            // Nothing to do here at this time.
                        } else {
                            if (NullChecker.isNullOrEmpty(request.getFaxRecipientName3())) {
                                errors.add("certificateOfInsuranceRequest.faxRecipientName3",
                                        new LocalizableError("COIRequest.Error.DeliveryRecipientRequired"));
                            }

                            if (NullChecker.isNullOrEmpty(request.getFaxNumber3())) {
                                errors.add("certificateOfInsuranceRequest.faxNumber3",
                                        new LocalizableError("COIRequest.Error.DeliveryFaxRequired"));
                            }
                        }
                    } else {
                        if (NullChecker.isNullOrEmpty(request.getFaxRecipientName2())) {
                            errors.add("certificateOfInsuranceRequest.faxRecipientName2",
                                    new LocalizableError("COIRequest.Error.DeliveryRecipientRequired"));
                        }

                        if (NullChecker.isNullOrEmpty(request.getFaxNumber2())) {
                            errors.add("certificateOfInsuranceRequest.faxNumber2",
                                    new LocalizableError("COIRequest.Error.DeliveryFaxRequired"));
                        }
                    }
                } else {
                    if (NullChecker.isNullOrEmpty(request.getFaxRecipientName1())) {
                        errors.add("certificateOfInsuranceRequest.faxRecipientName1",
                                new LocalizableError("COIRequest.Error.DeliveryRecipientRequired"));
                    }

                    if (NullChecker.isNullOrEmpty(request.getFaxNumber1())) {
                        errors.add("certificateOfInsuranceRequest.faxNumber1",
                                new LocalizableError("COIRequest.Error.DeliveryFaxRequired"));
                    }
                }
            }
        }
    }
    @ValidateNestedProperties({
        @Validate(field = "isAlternateEmployer", required = true, on = "submit"),
        @Validate(field = "policyHolderName", required = true, on = "submit"),
        @Validate(field = "holderAddress.addressLine1", required = true, on = "submit"),
        @Validate(field = "holderAddress.city", required = true, on = "submit"),
        @Validate(field = "holderAddress.postalCode", required = true, on = "submit"),
        @Validate(field = "holderAddress.state", required = true, on = "submit"),
        @Validate(field = "termNumber", required = true, on = "submit"),
        @Validate(field = "holderName", required = true, on = "submit"),
        @Validate(field = "requesterName", required = true, on = "submit"),
        @Validate(field = "requesterPhone", required = true, on = "submit", maxlength = 30, mask = PHONE_REGEX),
        @Validate(field = "emailAddress1", on = "submit", converter = EmailTypeConverter.class),
        @Validate(field = "emailAddress2", on = "submit", converter = EmailTypeConverter.class),
        @Validate(field = "emailAddress3", on = "submit", converter = EmailTypeConverter.class),
        @Validate(field = "faxNumber1", on = "submit",
                mask = "^(\\+?1-?)?(\\([2-9]\\d{2}\\)|[2-9]\\d{2})-?[2-9]\\d{2}-?\\d{4}$"),
        @Validate(field = "faxNumber2", on = "submit",
                mask = "^(\\+?1-?)?(\\([2-9]\\d{2}\\)|[2-9]\\d{2})-?[2-9]\\d{2}-?\\d{4}$"),
        @Validate(field = "faxNumber3", on = "submit",
                mask = "^(\\+?1-?)?(\\([2-9]\\d{2}\\)|[2-9]\\d{2})-?[2-9]\\d{2}-?\\d{4}$")})
    public CertificateOfInsuranceRequest certificateOfInsuranceRequest;

    public CertificateOfInsuranceRequest getCertificateOfInsuranceRequest() {
        if (certificateOfInsuranceRequest == null) {
            certificateOfInsuranceRequest = new CertificateOfInsuranceRequest();
        }
        return certificateOfInsuranceRequest;
    }

    public void setCertificateOfInsurance(CertificateOfInsuranceRequest certificateOfInsuranceRequest) {
        this.certificateOfInsuranceRequest = certificateOfInsuranceRequest;
    }

    public void buildCachedItems(String policy) throws GwIntegrationException {

        String cachedPolicy = (String) getContext().getRequest().getSession().getAttribute("policy");
        List<PolicyTerm> cachedTerms = (List<PolicyTerm>) getContext().getRequest().getSession().getAttribute("terms");
        Map<Integer, String> cachedTermsMap = (HashMap<Integer, String>) getContext().getRequest().getSession().getAttribute("termsMap");

        // Check to see if new or different policy is being processed
        if (NullChecker.isNullOrEmpty(cachedPolicy) || !policy.equals(cachedPolicy)) {
            getContext().getRequest().getSession().setAttribute("policy", policy);
            cachedTerms = null;
            cachedTermsMap = null;
        }

        if (NullChecker.isNullOrEmpty(cachedTerms)) {
            cachedTerms = coiRequest.getPolicyTerms(policy);
            getContext().getRequest().getSession().setAttribute("terms", cachedTerms);
        }

        if (NullChecker.isNullOrEmpty(cachedTermsMap)) {
            if (cachedTermsMap == null) {
                cachedTermsMap = new HashMap<Integer, String>();
            }

            for (PolicyTerm t : cachedTerms) {
                cachedTermsMap.put(t.termNumber, t.termDescription);
            }
            getContext().getRequest().getSession().setAttribute("termsMap", cachedTermsMap);
        }
    }

    public List<PolicyTerm> getTerms(String policy) throws GwIntegrationException {
        List<PolicyTerm> cachedTerms = (List<PolicyTerm>) getContext().getRequest().getSession().getAttribute("terms");
        String cachedPolicy = (String) getContext().getRequest().getSession().getAttribute("policy");
        // Check to see if new or different policy is being processed
        if (NullChecker.isNullOrEmpty(cachedPolicy) || !policy.equals(cachedPolicy)) {
            cachedTerms = null;
        }
        if (NullChecker.isNullOrEmpty(cachedTerms)) {
            buildCachedItems(policy);
            cachedTerms = (List<PolicyTerm>) getContext().getRequest().getSession().getAttribute("terms");
        }
        return cachedTerms;
    }

    public Map<Integer, String> getTermsMap(String policy) throws GwIntegrationException {
        Map<Integer, String> cachedTermsMap = (Map<Integer, String>) getContext().getRequest().getSession().getAttribute("termsMap");
        if (NullChecker.isNullOrEmpty(cachedTermsMap)) {
            buildCachedItems(policy);
            cachedTermsMap = (Map<Integer, String>) getContext().getRequest().getSession().getAttribute("termsMap");
        }
        return cachedTermsMap;
    }
    
    public String getCoiPolicyNumber() {
        return coiPolicyNumber;
    }

    public void setCoiPolicyNumber(String coiPolicyNumber) {
        this.coiPolicyNumber = coiPolicyNumber;
    }

    public UsState[] getUsStates() {
        return UsState.values();
    }

    public Resolution updateTerms() {
        try {
            terms = getTerms(coiPolicyNumber);
        } catch (GwIntegrationException ex) {
            logger.error("fatal error trying to access policy periods for " + coiPolicyNumber, ex);
            getContext().getValidationErrors().addGlobalError(new LocalizableError("PayrollReport.Error.GetPolicyPeriodsFailed", coiPolicyNumber));
        }
        return new ForwardResolution(INNER_FORM);
    }

    public List<String> getPolicyNumbers(String accountID) throws GwIntegrationException {
        String cachedAccountID = (String) getContext().getRequest().getSession().getAttribute("accountID");
        List<String> cachedPolicyNumbers = (List<String>) getContext().getRequest().getSession().getAttribute("policyNumbers");
        String status = "Active";

        // Check to see if new or different policy is being processed
        if (NullChecker.isNullOrEmpty(cachedAccountID) || !accountID.equals(cachedAccountID)) {
            getContext().getRequest().getSession().setAttribute("accountID", accountID);
            cachedPolicyNumbers = null;
        }

        if (NullChecker.isNullOrEmpty(cachedPolicyNumbers)) {
            FindPolicyNumbersResponsePR.Return response;
            cachedPolicyNumbers = new ArrayList<String>();
            response = pr.findPolicyNumbers(accountID);

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
                                        if (NullChecker.isNotNullOrEmpty(code) && code.equalsIgnoreCase(status)) {
                                            cachedPolicyNumbers.add(s);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            getContext().getRequest().getSession().setAttribute("policyNumbers", cachedPolicyNumbers);
        }

        return cachedPolicyNumbers;
    }
}
