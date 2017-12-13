package com.pwc.us.webui.stripes.action;

import com.pwc.us.common.exception.GwIntegrationException;
import com.pwc.us.common.model.ContactBO;
import com.pwc.us.common.model.KeyName;
import com.pwc.us.common.model.quoterequest.AnswerQR;
import com.pwc.us.common.model.quoterequest.OwnerOfficerBO;
import com.pwc.us.common.model.quoterequest.PolicyContactRoleQR;
import com.pwc.us.common.model.quoterequest.PolicyLineAnswerQR;
import com.pwc.us.common.model.quoterequest.PolicyLineQR;
import com.pwc.us.common.model.quoterequest.PolicyPeriodQR;
import com.pwc.us.common.model.quoterequest.PriorPolicyQR;
import com.pwc.us.common.model.quoterequest.WC7AddlInterestExtQR;
import com.pwc.us.common.utils.NullChecker;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.DontValidate;
import net.sourceforge.stripes.action.ErrorResolution;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.LocalizableMessage;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.SimpleMessage;
import net.sourceforge.stripes.validation.LocalizableError;
import net.sourceforge.stripes.validation.ValidationError;
import net.sourceforge.stripes.validation.ValidationErrors;
import net.sourceforge.stripes.validation.ValidationMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Roger
 */
public class QuoteRequestActionBean extends QuoteRequestBaseActionBean {

    private static final String VIEW = "/WEB-INF/jsp/quote_request_form.jsp";
    private static final String SUBMITTED_VIEW = "/WEB-INF/jsp/quoteRequestSubmitted.jsp";
    private static final String ACCOUNT_HOLDER_SUBTYPE = "Company";
    private static final String ACCOUNT_HOLDER_ADDRESS_DESCRIPTION = "Address created via Portal";
    private static final String REQUEST_SUBMITTED = "qr.requestSubmitted";
    private static final String INTEGRATION_ERROR = "qr.Error.IntegrationError";
    private static final int CACHE_ERROR = 500;
    private static final Logger logger = LoggerFactory.getLogger(QuoteRequestActionBean.class);
    private static Set<String> abbreviations = new HashSet<String>( 
       ( Arrays.asList( new String[] {
	"AL", "AK", "AZ", "AR", "CA", "CO", "CT", "DE", "DC", "FL", "GA", "HI", "ID", "IL", "IN", 
        "IA", "KS", "KY", "LA", "ME", "MD", "MA", "MI", "MN", "MS", 
        "MO", "MT", "NE", "NV", "NH", "NJ", "NM", "NY", "NC", "ND", "OH", "OK", "OR", "PA", "RI", 
        "SC", "SD", "TN", "TX", "UT", "VT", "VA", "WA", "WV", "WI", "WY"
	} ) ) );


    @DefaultHandler
    public Resolution form() {
        if (NullChecker.isNullOrEmpty(orgTypeExt)
                || NullChecker.isNullOrEmpty(ownerOfficerRelationships)
                || NullChecker.isNullOrEmpty(addlIntRelationships)) {
            try {
                createCache();
            } catch (GwIntegrationException e) {
                ValidationError err = new LocalizableError("qr.Error.CacheError");
                getContext().getValidationErrors().addGlobalError(err);
                return new ErrorResolution(CACHE_ERROR, err.getMessage(Locale.US));
            }
        }
        return new ForwardResolution(VIEW);
    }

    public Resolution submit() {
        // Fixed issue where prior policies adds empty prior policy
        // even if no prior policy is entered.
        if (!this.hadPreviousCoverage) {
            this.priorPolicies.clear();
        }
        convertPhoneNumbers(accountHolderContact);
        convertPhoneNumbers(contactInfo);
        convertPhoneNumbers(auditor);
        createAccount();
        createPolicyPeriod();
        try {
            String result = quoteRequest.requestQuote(account, policyPeriod);
            getContext().getMessages().add(new LocalizableMessage(REQUEST_SUBMITTED, result));
        } catch (GwIntegrationException e) {
            String message = "Unable to send quote request to PolicyCenter";
            logger.error(message, e);
            getContext().getValidationErrors().addGlobalError(new LocalizableError(INTEGRATION_ERROR));
        }
        return new ForwardResolution(SUBMITTED_VIEW);
    }

    @DontValidate
    public Resolution cancel() {
        getContext().getMessages().add(
                new SimpleMessage("Action cancelled."));
        return new RedirectResolution(QuoteRequestActionBean.class);
    }

    private void createAccount() {
        accountHolderPrimaryAddress.setDescription(ACCOUNT_HOLDER_ADDRESS_DESCRIPTION);
        accountHolderContact.setPrimaryAddress(accountHolderPrimaryAddress);
        accountHolderContact.setSubType(ACCOUNT_HOLDER_SUBTYPE);
        account.setAccountHolderContact(accountHolderContact);
    }

    private void createPolicyPeriod() {
        policyPeriod.setPeriodAnswers(createPeriodAnswers());
        policyPeriod.setPolicy(createPriorPolicies());
        policyPeriod.setPolicyLocations(createPolicyLocations());
        policyPeriod.setPrimaryNamedInsured(primaryNamedInsured);
        policyPeriod.setWc7Line(createPolicyLine());
        //just hardcode this, it will never not be true
        policyPeriod.setIsNewFromPortal(Boolean.TRUE);
    }

    private PolicyPeriodQR.PeriodAnswers createPeriodAnswers() {
        PolicyPeriodQR.PeriodAnswers pas = new PolicyPeriodQR.PeriodAnswers();
        List<AnswerQR> answers = new ArrayList<AnswerQR>();
        answers.add(isTempStaffing);
        answers.add(opsOutsideOk);

        for (AnswerQR answer : answers) {
            answer = makeNullBooleansFalse(answer);
        }

        if (NullChecker.isNotNullOrEmpty(totalAnnualPayroll.getIntegerAnswer())) {
            answers.add(totalAnnualPayroll);
        }


        pas.setEntry(answers);
        return pas;
    }

    private PolicyPeriodQR.Policy createPriorPolicies() {
        PolicyPeriodQR.Policy pol = new PolicyPeriodQR.Policy();
        PolicyPeriodQR.Policy.PriorPolicies priors = new PolicyPeriodQR.Policy.PriorPolicies();
        priors.setEntry(this.priorPolicies);
        pol.setPriorPolicies(priors);
        return pol;
    }

    private PolicyPeriodQR.PolicyLocations createPolicyLocations() {
        PolicyPeriodQR.PolicyLocations locs = new PolicyPeriodQR.PolicyLocations();
        locs.setEntry(additionalLocations);
        return locs;
    }

    private PolicyLineQR createPolicyLine() {
        PolicyLineQR line = new PolicyLineQR();
        line.setEntityWC7WorkersCompLine(createPolicyLineEntity());
        line.setLineAnswers(createLineAnswers());
        return line;
    }

    private PolicyLineQR.EntityWC7WorkersCompLine createPolicyLineEntity() {
        PolicyLineQR.EntityWC7WorkersCompLine line = new PolicyLineQR.EntityWC7WorkersCompLine();
        line.setAdditionalInterests(createAdditionalInterests());

        if (NullChecker.isNotNullOrEmpty(this.ownerOfficers)) {
            line.setExcludedOwnerOfficers(createExcludedOwnerOfficers());
            line.setIncludedOwnerOfficers(createIncludedOwnerOfficers());
        }

        return line;
    }

    private PolicyLineQR.EntityWC7WorkersCompLine.AdditionalInterests createAdditionalInterests() {
        PolicyLineQR.EntityWC7WorkersCompLine.AdditionalInterests addlInts = new PolicyLineQR.EntityWC7WorkersCompLine.AdditionalInterests();
        List<WC7AddlInterestExtQR> intList = new ArrayList<WC7AddlInterestExtQR>();
        if (NullChecker.isNotNullOrEmpty(auditor)
                && NullChecker.isNotNullOrEmpty(auditor.getEntityPerson())) {
            WC7AddlInterestExtQR aud = quoteRequest.createAuditContact(this.auditor);
            intList.add(aud);
        }

        contactInfo.setEntityPerson(contactEntityPerson);
        WC7AddlInterestExtQR contact = quoteRequest.createContact(this.contactInfo);
        intList.add(contact);
        addlInts.setEntry(intList);

        return addlInts;
    }

    private PolicyLineQR.EntityWC7WorkersCompLine.ExcludedOwnerOfficers createExcludedOwnerOfficers() {
        PolicyLineQR.EntityWC7WorkersCompLine.ExcludedOwnerOfficers eos =
                new PolicyLineQR.EntityWC7WorkersCompLine.ExcludedOwnerOfficers();
        List<PolicyContactRoleQR> entries = new ArrayList<PolicyContactRoleQR>();

        for (OwnerOfficerBO off : this.ownerOfficers) {
            if (!off.isCoverageDesired()) {
                entries.add(quoteRequest.createExcludedOwnerOfficer(off));
            }
        }
        if (entries.size() > 0) {
            eos.setEntry(entries);
        } else {
            eos = null;
        }
        return eos;
    }

    private PolicyLineQR.EntityWC7WorkersCompLine.IncludedOwnerOfficers createIncludedOwnerOfficers() {
        PolicyLineQR.EntityWC7WorkersCompLine.IncludedOwnerOfficers ios =
                new PolicyLineQR.EntityWC7WorkersCompLine.IncludedOwnerOfficers();
        List<PolicyContactRoleQR> entries = new ArrayList<PolicyContactRoleQR>();

        for (OwnerOfficerBO off : this.ownerOfficers) {
            if (off.isCoverageDesired()) {
                entries.add(quoteRequest.createIncludedOwnerOfficer(off));
            }
        }

        ios.setEntry(entries);
        return ios;

    }

    private PolicyLineQR.LineAnswers createLineAnswers() {
        PolicyLineQR.LineAnswers answers = new PolicyLineQR.LineAnswers();
        List<PolicyLineAnswerQR> entries = new ArrayList<PolicyLineAnswerQR>();

        // Boolean answers -- create false replies for nulls
        entries.add(ownAnotherBusiness);
        entries.add(needWCAndLiability);
        entries.add(quoteAllOKBusiness);
        entries.add(employeesOutsideOK);
        entries.add(businessOperateOutsideOK);
        entries.add(terminatingBusiness);
        entries.add(associationDeniedInsurance);
        entries.add(employDomestics);
        entries.add(farmEmployees);
        entries.add(familyMembers);
        entries.add(hqOutsideOK);
        entries.add(oosEmployees);
        entries.add(boatsPlanes);
        entries.add(hazmat);
        entries.add(aboveOrBelowGround);
        entries.add(onOrOverWater);
        entries.add(engagedInOtherBusiness);
        entries.add(subcontractorsUsed);
        entries.add(anyWorkSublet);
        entries.add(safetyProgram);
        entries.add(groupTransportation);
        entries.add(ageRange);
        entries.add(seasonalEmployees);
        entries.add(volunteer);
        entries.add(disabledEmployees);
        entries.add(oosTravel);
        entries.add(athleticSponsorship);
        entries.add(physicalsRequired);
        entries.add(otherInsurance);
        entries.add(priorDeclined);
        entries.add(employeeHealthPlans);
        entries.add(laborInterchange);
        entries.add(leaseEmployees);
        entries.add(workAtHome);
        entries.add(taxLiens);
        entries.add(unpaidPremium);

        for (AnswerQR answer : entries) {
            answer = makeNullBooleansFalse(answer);
        }

        
        // add in non-boolean answers
        if (NullChecker.isNotNullOrEmpty(quoteAllOKBusinessExplain.getTextAnswer())) {
            entries.add(quoteAllOKBusinessExplain);
        }
        if (NullChecker.isNotNullOrEmpty(employeesOutsideOKText.getTextAnswer())) {
            entries.add(employeesOutsideOKText);
        }
        if (NullChecker.isNotNullOrEmpty(businessOperateOutsideOKText.getTextAnswer())) {
            entries.add(businessOperateOutsideOKText);
        }
        if (NullChecker.isNotNullOrEmpty(terminatingBusinessText.getTextAnswer())) {
            entries.add(terminatingBusinessText);
        }
        if (NullChecker.isNotNullOrEmpty(associationDeniedInsuranceText.getTextAnswer())) {
            entries.add(associationDeniedInsuranceText);
        }
        if (subcontractorsUsed.getBooleanAnswer()) {
            if(NullChecker.isNotNullOrEmpty(subcontractorsPercent.getIntegerAnswer())){
                Integer i = new Integer(subcontractorsPercent.getIntegerAnswer());
                subcontractorsPercent.setTextAnswer(i.toString());
               entries.add(subcontractorsPercent);
           }
        }
        answers.setEntry(entries);
        return answers;
    
    }
    private AnswerQR makeNullBooleansFalse(AnswerQR answer) {
        if (NullChecker.isNullOrEmpty(answer.getBooleanAnswer())) {
            answer.setBooleanAnswer(false);
        }
        return answer;
    }

    public void createCache() throws GwIntegrationException {
        try {
            getOrgTypeExt();
            getOwnerOfficerRelationships();
            getAddlIntRelationships();
        } catch (GwIntegrationException e) {
            String message = "Error retrieving lookup values from Guidewire in Quote Request Form";
            logger.error(message, e);
            throw new GwIntegrationException(message, e);
        }
    }

    public List<KeyName> getOrgTypeExt() throws GwIntegrationException {
        if (NullChecker.isNullOrEmpty(QuoteRequestActionBean.orgTypeExt)) {
            QuoteRequestActionBean.orgTypeExt = quoteRequest.getAccountOrgKeys();
        }
        return QuoteRequestActionBean.orgTypeExt;
    }

    public List<KeyName> getOwnerOfficerRelationships() throws GwIntegrationException {
        if (NullChecker.isNullOrEmpty(QuoteRequestActionBean.ownerOfficerRelationships)) {
            QuoteRequestActionBean.ownerOfficerRelationships = quoteRequest.getOwnerOfficerRelationships();
        }
        return QuoteRequestActionBean.ownerOfficerRelationships;
    }

    public List<KeyName> getAddlIntRelationships() throws GwIntegrationException {
        if (NullChecker.isNullOrEmpty(QuoteRequestActionBean.addlIntRelationships)) {
            QuoteRequestActionBean.addlIntRelationships = quoteRequest.getAddlIntRelationships();
        }
        return QuoteRequestActionBean.addlIntRelationships;
    }
    
    @ValidationMethod(on = "submit")
    public void validatePriorPolicyInformation(ValidationErrors errors)
    {
        boolean foundState = false;
        if(this.hadPreviousCoverage){
            for (PriorPolicyQR ppqr : this.priorPolicies) {
                if(!foundState && (ppqr.getCarriersStateExt() == null || !abbreviations.contains(ppqr.getCarriersStateExt())))
                {
                    ValidationError err = new LocalizableError("qr.Error.PreviousCoverage.StateMissing");
                    errors.addGlobalError(err);
                    break;
                }else
                    foundState = true;
            }
        }
    }

    @ValidationMethod(on = "submit")
    public void validateOwnOtherBusiness(ValidationErrors errors) {
        if (NullChecker.isNotNullOrEmpty(employeesOutsideOK)
                && NullChecker.isNotNullOrEmpty(employeesOutsideOK.getBooleanAnswer())
                && employeesOutsideOK.getBooleanAnswer()) {
            if (NullChecker.isNullOrEmpty(employeesOutsideOKText)
                    || NullChecker.isNullOrEmpty(employeesOutsideOKText.getTextAnswer())) {
                errors.add("employeesOutsideOKText.textAnswer", new LocalizableError("qr.Error.EmployeesOutsideOKText"));
            }
        }
    }

    private void convertPhoneNumbers(ContactBO contact) {
        String phone = contact.getWorkPhone();
        contact.setWorkPhone(convertPhoneNumber(phone));        
        phone = contact.getHomePhone();
        contact.setHomePhone(convertPhoneNumber(phone));
        //fax isn't required so check if it's there so we don't get an NPE
        if(NullChecker.isNotNullOrEmpty(contact.getFaxPhone())) {
            phone = contact.getFaxPhone();
            contact.setFaxPhone(convertPhoneNumber(phone));
        }

    }

private String convertPhoneNumber(String num) {
        if (NullChecker.isNotNullOrEmpty(num)) {
            return num.replaceAll("^\\(([2-9]\\d{2})\\)([2-9]\\d{2}-?\\d{4})$", "$1-$2");
        } else {
            return num;
        }
        
    }

public static boolean isInteger(String str) {
    try {
        Integer.parseInt(str);
        return true;
    } catch (NumberFormatException nfe) {
        return false;
    }
}
}
