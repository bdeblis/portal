package com.pwc.us.webui.stripes.action;

import com.google.inject.Inject;
import com.pwc.us.common.QuoteRequest;
import com.pwc.us.common.model.AddressBO;
import com.pwc.us.common.model.ContactBO;
import com.pwc.us.common.model.KeyName;
import com.pwc.us.common.model.quoterequest.AccountQR;
import com.pwc.us.common.model.quoterequest.OwnerOfficerBO;
import com.pwc.us.common.model.quoterequest.PolicyLineAnswerQR;
import com.pwc.us.common.model.quoterequest.PolicyLocationQR;
import com.pwc.us.common.model.quoterequest.PolicyPeriodQR;
import com.pwc.us.common.model.quoterequest.PriorPolicyQR;
import java.util.ArrayList;
import java.util.List;
import net.sourceforge.stripes.validation.EmailTypeConverter;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidateNestedProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Roger
 */
public class QuoteRequestBaseActionBean extends BaseActionBean {
    protected static final String PHONE_REGEX = "^(\\([2-9]\\d{2}\\)|[2-9]\\d{2}-)[2-9]\\d{2}-\\d{4}$";

    protected static final String IS_TEMP_STAFF_OR_EMP_LEASING = "WC7PEOTemp";
    protected static final String OPERATIONS_OUTSIDE_OK = "WC7OutsideOK";
    protected static final String TOTAL_ANNUAL_PAYROLL = "WC7Payroll";
    protected static final String OK_OTHER_BUS_OOS = "WC7OKOtherBusOutOfstate";
    protected static final String NEED_OOS_COVERAGE = "WC7OOSCoverage";
    protected static final String OBTAIN_QUOTE_FOR_ALL_OK_BUS = "WC7OKBusQuote";
    protected static final String OBTAIN_QUOTE_FOR_ALL_OK_BUS_EXPLAIN = "WC7OKBusQuoteText";
    protected static final String OOS_CONTRACT_WORKERS = "WC7OOSContractWorkers";
    protected static final String OOS_CONTRACT_WORKERS_TEXT = "WC7OOSContractWorkersText";
    protected static final String OOS_ENTITIES = "WC7OOSEntities";
    protected static final String OOS_ENTITIES_LIST_STATES = "WC7OOSEntitiesText";
    protected static final String LIQUIDATION_OF_BUSINESS = "WC7Liquidation";
    protected static final String LIQUIDATION_OF_BUSINESS_EXPLAIN = "WC7LiquidationText";
    protected static final String COVERAGE_DENIED_OR_CANCELLED = "WC7Denied";
    protected static final String COVERAGE_DENIED_OR_CANCELLED_EXPLAIN = "WC7DeniedText";
    protected static final String DOMESTIC_EMPLOYEES = "WC7DomesticEmployees";
    protected static final String FARM_EMPLOYEES = "WC7FarmEmployees";
    protected static final String FAMILY_MEMBERS = "WC7FamilyMembers";
    protected static final String BUSINESS_HQ_OOS = "WC7OOS";
    protected static final String OOS_EMPLOYEES = "WC7OOSEmployees";
    protected static final String BOATS_PLANES = "WC7AircraftWatercraft";
    protected static final String HAZMAT = "WC7HazMat";
    protected static final String ABOVE_OR_BELOW_GROUND = "WC7AboveOrBelowGround";
    protected static final String ON_OR_OVER_WATER = "WC7OnOrOverWater";
    protected static final String ENGAGED_IN_OTHER_BUSINESS = "WC7OtherBusinesses";
    protected static final String SUBCONTRACTORS_USED = "WC7Subcontractors";
    protected static final String SUBCONTRACTORS_PERCENT = "WC7SubcontractorsPercent";
    protected static final String ANY_WORK_SUBLET = "WC7Sublet";
    protected static final String WRITTEN_SAFETY_PROGRAM = "WC7SafetyProgram";
    protected static final String GROUP_TRANSPORTATION = "WC7GroundTransport";
    protected static final String AGE_RANGE = "WC7AgeRange";
    protected static final String SEASONAL_EMPLOYEES = "WC7Seasonal";
    protected static final String VOLUNTEER = "WC7Volunteer";
    protected static final String DISABLED_EMPLOYEES = "WC7DisabledEmployees";
    protected static final String OUT_OF_STATE_TRAVEL = "WC7OutOfStateTravel";
    protected static final String ATHLETIC_SPONSORSHIP = "WC7AthleticSponser";
    protected static final String PHYSICALS_REQUIRED = "WC7PhysicalsRequired";
    protected static final String OTHER_INSURANCE_WITH_US = "WC7OtherInsurance";
    protected static final String PRIOR_COVERAGE_DECLINED = "WC7PriorDeclined";
    protected static final String EMPLOYEE_HEALTH_PLANS = "WC7EmployeeHeathPlans";
    protected static final String LABOR_INTERCHANGE = "WC7LaborInterchange";
    protected static final String LEASE_EMPLOYEES = "WC7LeaseEmployees";
    protected static final String WORK_FROM_HOME = "WC7WorkAtHome";
    protected static final String TAX_LIENS_OR_BANKRUPTCY = "WC7TaxLiens";
    protected static final String UNPAID_PREMIUMS = "WC7UnpaidPremimum";
    
    // The main request objects.
    
    @ValidateNestedProperties({
        @Validate(field = "name", required = true, maxlength = 60, on = "submit"),
        @Validate(field = "homePhone", on="submit", maxlength = 30,
                mask = PHONE_REGEX)
    })
    protected ContactBO accountHolderContact = new ContactBO();
    
    @ValidateNestedProperties({
        @Validate(field = "addressLine1", maxlength = 60, on = "submit"),
        @Validate(field = "county", maxlength = 60, on = "submit"),
        @Validate(field = "city", maxlength = 60, on = "submit"),
        @Validate(field = "state", maxlength = 2, on = "submit"),
        @Validate(field = "postalCode", maxlength = 60, on = "submit")
    }) 
    protected AddressBO accountHolderPrimaryAddress = new AddressBO();
    
    @ValidateNestedProperties({
        @Validate(field = "busOpsDesc", required = true, maxlength = 240, on = "submit")
    })
    
    
    
    protected AccountQR account = new AccountQR();
    
    @ValidateNestedProperties({
        @Validate(field = "orgTypeExt", required = true, on = "submit")
    })
    protected PolicyPeriodQR.PrimaryNamedInsured primaryNamedInsured = new PolicyPeriodQR.PrimaryNamedInsured();
    
    protected PolicyPeriodQR policyPeriod = new PolicyPeriodQR();
    
    // Answers to form questions.
    protected boolean hasFederalTaxId = false;
    
//    @ValidateNestedProperties({
//        @Validate(field = "booleanAnswer", required = true, on = "submit"),
//    })
    protected PolicyLineAnswerQR isTempStaffing = new PolicyLineAnswerQR(IS_TEMP_STAFF_OR_EMP_LEASING, false);
    
//    @ValidateNestedProperties({
//        @Validate(field = "booleanAnswer", required = true, on = "submit"),
//    })
    protected PolicyLineAnswerQR opsOutsideOk = new PolicyLineAnswerQR(OPERATIONS_OUTSIDE_OK, false);
    
    protected PolicyLineAnswerQR totalAnnualPayroll = new PolicyLineAnswerQR(TOTAL_ANNUAL_PAYROLL, 0);
    
    protected static List<KeyName> orgTypeExt;
    protected static List<KeyName> ownerOfficerRelationships;
    protected static List<KeyName> addlIntRelationships;
    
    
    private static Logger logger = LoggerFactory.getLogger(QuoteRequestBaseActionBean.class);
    
    // Auditor Contact

    
    protected ContactBO auditor = new ContactBO();
    protected ContactBO.EntityPerson auditorEntityPerson = new ContactBO.EntityPerson();
    protected AddressBO auditorAddress = new AddressBO();
    
    // Previous Coverage
    protected boolean hadPreviousCoverage = false;
    protected List<PriorPolicyQR> priorPolicies = new ArrayList<PriorPolicyQR>();

    // Line answers
    protected PolicyLineAnswerQR ownAnotherBusiness = new PolicyLineAnswerQR(OK_OTHER_BUS_OOS, false);
    protected PolicyLineAnswerQR needWCAndLiability = new PolicyLineAnswerQR(NEED_OOS_COVERAGE, false);
    protected PolicyLineAnswerQR quoteAllOKBusiness = new PolicyLineAnswerQR(OBTAIN_QUOTE_FOR_ALL_OK_BUS, false);  
    protected PolicyLineAnswerQR quoteAllOKBusinessExplain = new PolicyLineAnswerQR(OBTAIN_QUOTE_FOR_ALL_OK_BUS_EXPLAIN, "");
    protected PolicyLineAnswerQR employeesOutsideOK = new PolicyLineAnswerQR(OOS_CONTRACT_WORKERS, false);  
    protected PolicyLineAnswerQR employeesOutsideOKText = new PolicyLineAnswerQR(OOS_CONTRACT_WORKERS_TEXT, "");
    protected PolicyLineAnswerQR businessOperateOutsideOK = new PolicyLineAnswerQR(OOS_ENTITIES, false);
    protected PolicyLineAnswerQR businessOperateOutsideOKText = new PolicyLineAnswerQR(OOS_ENTITIES_LIST_STATES, "");
    protected PolicyLineAnswerQR terminatingBusiness = new PolicyLineAnswerQR(LIQUIDATION_OF_BUSINESS, false);
    protected PolicyLineAnswerQR terminatingBusinessText = new PolicyLineAnswerQR(LIQUIDATION_OF_BUSINESS_EXPLAIN, "");
    protected PolicyLineAnswerQR associationDeniedInsurance = new PolicyLineAnswerQR(COVERAGE_DENIED_OR_CANCELLED, false);
    protected PolicyLineAnswerQR associationDeniedInsuranceText = new PolicyLineAnswerQR(COVERAGE_DENIED_OR_CANCELLED_EXPLAIN, "");
    protected PolicyLineAnswerQR employDomestics = new PolicyLineAnswerQR(DOMESTIC_EMPLOYEES, false);
    protected PolicyLineAnswerQR farmEmployees = new PolicyLineAnswerQR(FARM_EMPLOYEES, false);
    protected PolicyLineAnswerQR familyMembers = new PolicyLineAnswerQR(FAMILY_MEMBERS, false);
    protected PolicyLineAnswerQR hqOutsideOK = new PolicyLineAnswerQR(BUSINESS_HQ_OOS, false);
    protected PolicyLineAnswerQR oosEmployees = new PolicyLineAnswerQR(OOS_EMPLOYEES, false);
    protected PolicyLineAnswerQR boatsPlanes = new PolicyLineAnswerQR(BOATS_PLANES, false);
    protected PolicyLineAnswerQR hazmat = new PolicyLineAnswerQR(HAZMAT, false);
    protected PolicyLineAnswerQR aboveOrBelowGround = new PolicyLineAnswerQR(ABOVE_OR_BELOW_GROUND, false);
    protected PolicyLineAnswerQR onOrOverWater = new PolicyLineAnswerQR(ON_OR_OVER_WATER, false);
    protected PolicyLineAnswerQR engagedInOtherBusiness = new PolicyLineAnswerQR(ENGAGED_IN_OTHER_BUSINESS, false);
    protected PolicyLineAnswerQR subcontractorsUsed = new PolicyLineAnswerQR(SUBCONTRACTORS_USED, false);
    protected PolicyLineAnswerQR subcontractorsPercent = new PolicyLineAnswerQR(SUBCONTRACTORS_PERCENT, "");
    protected PolicyLineAnswerQR anyWorkSublet = new PolicyLineAnswerQR(ANY_WORK_SUBLET, false);
    protected PolicyLineAnswerQR safetyProgram = new PolicyLineAnswerQR(WRITTEN_SAFETY_PROGRAM, false);
    protected PolicyLineAnswerQR groupTransportation = new PolicyLineAnswerQR(GROUP_TRANSPORTATION, false);
    protected PolicyLineAnswerQR ageRange = new PolicyLineAnswerQR(AGE_RANGE, false);
    protected PolicyLineAnswerQR seasonalEmployees = new PolicyLineAnswerQR(SEASONAL_EMPLOYEES, false);
    protected PolicyLineAnswerQR volunteer = new PolicyLineAnswerQR(VOLUNTEER, false);
    protected PolicyLineAnswerQR disabledEmployees = new PolicyLineAnswerQR(DISABLED_EMPLOYEES, false);
    protected PolicyLineAnswerQR oosTravel = new PolicyLineAnswerQR(OUT_OF_STATE_TRAVEL, false);
    protected PolicyLineAnswerQR athleticSponsorship = new PolicyLineAnswerQR(ATHLETIC_SPONSORSHIP, false);
    protected PolicyLineAnswerQR physicalsRequired = new PolicyLineAnswerQR(PHYSICALS_REQUIRED, false);
    protected PolicyLineAnswerQR otherInsurance = new PolicyLineAnswerQR(OTHER_INSURANCE_WITH_US, false);
    protected PolicyLineAnswerQR priorDeclined = new PolicyLineAnswerQR(PRIOR_COVERAGE_DECLINED, false);
    protected PolicyLineAnswerQR employeeHealthPlans = new PolicyLineAnswerQR(EMPLOYEE_HEALTH_PLANS, false);
    protected PolicyLineAnswerQR laborInterchange = new PolicyLineAnswerQR(LABOR_INTERCHANGE, false);
    protected PolicyLineAnswerQR leaseEmployees = new PolicyLineAnswerQR(LEASE_EMPLOYEES, false);
    protected PolicyLineAnswerQR workAtHome = new PolicyLineAnswerQR(WORK_FROM_HOME, false);
    protected PolicyLineAnswerQR taxLiens = new PolicyLineAnswerQR(TAX_LIENS_OR_BANKRUPTCY, false);
    protected PolicyLineAnswerQR unpaidPremium = new PolicyLineAnswerQR(UNPAID_PREMIUMS, false);
    
    // Owner Officers
    protected List<OwnerOfficerBO> ownerOfficers = new ArrayList<OwnerOfficerBO>();    
    
    // Additional Locations
    protected List<PolicyLocationQR> additionalLocations = new ArrayList<PolicyLocationQR>();
    
    // Contact Info
    @ValidateNestedProperties({
        @Validate(field = "firstName", required = true, maxlength = 30, on = "submit"),
        @Validate(field = "lastName", required = true, maxlength = 30, on = "submit")
    })  
    protected ContactBO.EntityPerson contactEntityPerson = new ContactBO.EntityPerson();
    
    @ValidateNestedProperties({
        @Validate(field = "workPhone", required = true, maxlength = 30, on = "submit",
                     mask = PHONE_REGEX),
        @Validate(field = "emailAddress1", maxlength = 60, on = "submit", converter=EmailTypeConverter.class),
        @Validate(field = "faxPhone", maxlength = 30, on = "submit",
                    mask = PHONE_REGEX),
        @Validate(field = "relationship", required = true, on = "submit")
    })  
    protected ContactBO contactInfo = new ContactBO();
    
    protected Boolean isNewFromPortal = true;
    
    public Boolean getIsNewFromPortal() {
        return isNewFromPortal;
    }

    public void setPolicyPeriod(Boolean isNewFromPortal) {
        this.isNewFromPortal = isNewFromPortal;
    }
    
    
    @Inject
    protected QuoteRequest quoteRequest;

    public PolicyPeriodQR getPolicyPeriod() {
        return policyPeriod;
    }

    public void setPolicyPeriod(PolicyPeriodQR policyPeriod) {
        this.policyPeriod = policyPeriod;
    }

    public AccountQR getAccount() {
        return account;
    }

    public void setAccount(AccountQR account) {
        this.account = account;

    }

    public boolean isHasFederalTaxId() {
        return hasFederalTaxId;
    }

    public void setHasFederalTaxId(boolean hasFederalTaxId) {
        this.hasFederalTaxId = hasFederalTaxId;
    }
    
    
    public PolicyLineAnswerQR getIsTempStaffing() {
        return isTempStaffing;
    }

    public void setIsTempStaffing(PolicyLineAnswerQR isTempStaffing) {
        this.isTempStaffing = isTempStaffing;
    }

    public PolicyLineAnswerQR getOpsOutsideOk() {
        return opsOutsideOk;
    }

    public void setOpsOutsideOk(PolicyLineAnswerQR opsOutsideOk) {
        this.opsOutsideOk = opsOutsideOk;
    }

    public PolicyLineAnswerQR getTotalAnnualPayroll() {
        return totalAnnualPayroll;
    }

    public void setTotalAnnualPayroll(PolicyLineAnswerQR totalAnnualPayroll) {
        this.totalAnnualPayroll = totalAnnualPayroll;
    }

    public PolicyLineAnswerQR getAssociationDeniedInsuranceText() {
        return associationDeniedInsuranceText;
    }

    public void setAssociationDeniedInsuranceText(PolicyLineAnswerQR associationDeniedInsuranceText) {
        this.associationDeniedInsuranceText = associationDeniedInsuranceText;
    }
    
    public List<PriorPolicyQR> getPriorPolicies() {
        return priorPolicies;
    }

    public void setPriorPolicies(List<PriorPolicyQR> priorPolicies) {
        this.priorPolicies = priorPolicies;
    }

    public ContactBO getAuditor() {
        return auditor;
    }

    public void setAuditor(ContactBO auditor) {
        this.auditor = auditor;
    }

    public AddressBO getAuditorAddress() {
        return auditorAddress;
    }

    public void setAuditorAddress(AddressBO auditorAddress) {
        this.auditorAddress = auditorAddress;
    }

    public boolean isHadPreviousCoverage() {
        return hadPreviousCoverage;
    }

    public void setHadPreviousCoverage(boolean hadPreviousCoverage) {
        this.hadPreviousCoverage = hadPreviousCoverage;
    }

    public PolicyLineAnswerQR getOwnAnotherBusiness() {
        return ownAnotherBusiness;
    }

    public void setOwnAnotherBusiness(PolicyLineAnswerQR ownAnotherBusiness) {
        this.ownAnotherBusiness = ownAnotherBusiness;
    }

    public PolicyLineAnswerQR getNeedWCAndLiability() {
        return needWCAndLiability;
    }

    public void setNeedWCAndLiability(PolicyLineAnswerQR needWCAndLiability) {
        this.needWCAndLiability = needWCAndLiability;
    }

    public PolicyLineAnswerQR getQuoteAllOKBusiness() {
        return quoteAllOKBusiness;
    }

    public void setQuoteAllOKBusiness(PolicyLineAnswerQR quoteAllOKBusiness) {
        this.quoteAllOKBusiness = quoteAllOKBusiness;
    }

    public PolicyLineAnswerQR getQuoteAllOKBusinessExplain() {
        return quoteAllOKBusinessExplain;
    }

    public void setQuoteAllOKBusinessExplain(PolicyLineAnswerQR quoteAllOKBusinessExplain) {
        this.quoteAllOKBusinessExplain = quoteAllOKBusinessExplain;
    }
    
    

    public PolicyLineAnswerQR getEmployeesOutsideOK() {
        return employeesOutsideOK;
    }

    public void setEmployeesOutsideOK(PolicyLineAnswerQR employeesOutsideOK) {
        this.employeesOutsideOK = employeesOutsideOK;
    }

    public PolicyLineAnswerQR getEmployeesOutsideOKText() {
        return employeesOutsideOKText;
    }

    public void setEmployeesOutsideOKText(PolicyLineAnswerQR employeesOutsideOKText) {
        this.employeesOutsideOKText = employeesOutsideOKText;
    }

    public PolicyLineAnswerQR getBusinessOperateOutsideOK() {
        return businessOperateOutsideOK;
    }

    public void setBusinessOperateOutsideOK(PolicyLineAnswerQR businessOperateOutsideOK) {
        this.businessOperateOutsideOK = businessOperateOutsideOK;
    }

    public PolicyLineAnswerQR getBusinessOperateOutsideOKText() {
        return businessOperateOutsideOKText;
    }

    public void setBusinessOperateOutsideOKText(PolicyLineAnswerQR businessOperateOutsideOKText) {
        this.businessOperateOutsideOKText = businessOperateOutsideOKText;
    }

    public PolicyLineAnswerQR getTerminatingBusiness() {
        return terminatingBusiness;
    }

    public void setTerminatingBusiness(PolicyLineAnswerQR terminatingBusiness) {
        this.terminatingBusiness = terminatingBusiness;
    }

    public PolicyLineAnswerQR getTerminatingBusinessText() {
        return terminatingBusinessText;
    }

    public void setTerminatingBusinessText(PolicyLineAnswerQR terminatingBusinessText) {
        this.terminatingBusinessText = terminatingBusinessText;
    }

    public PolicyLineAnswerQR getAssociationDeniedInsurance() {
        return associationDeniedInsurance;
    }

    public void setAssociationDeniedInsurance(PolicyLineAnswerQR associationDeniedInsurance) {
        this.associationDeniedInsurance = associationDeniedInsurance;
    }

    public PolicyLineAnswerQR getEmployDomestics() {
        return employDomestics;
    }

    public void setEmployDomestics(PolicyLineAnswerQR employDomestics) {
        this.employDomestics = employDomestics;
    }

    public PolicyLineAnswerQR getFarmEmployees() {
        return farmEmployees;
    }

    public void setFarmEmployees(PolicyLineAnswerQR farmEmployees) {
        this.farmEmployees = farmEmployees;
    }

    public PolicyLineAnswerQR getFamilyMembers() {
        return familyMembers;
    }

    public void setFamilyMembers(PolicyLineAnswerQR familyMembers) {
        this.familyMembers = familyMembers;
    }

    public PolicyLineAnswerQR getHqOutsideOK() {
        return hqOutsideOK;
    }

    public void setHqOutsideOK(PolicyLineAnswerQR hqOutsideOK) {
        this.hqOutsideOK = hqOutsideOK;
    }

    public PolicyLineAnswerQR getOosEmployees() {
        return oosEmployees;
    }

    public void setOosEmployees(PolicyLineAnswerQR oosEmployees) {
        this.oosEmployees = oosEmployees;
    }

    public PolicyLineAnswerQR getBoatsPlanes() {
        return boatsPlanes;
    }

    public void setBoatsPlanes(PolicyLineAnswerQR boatsPlanes) {
        this.boatsPlanes = boatsPlanes;
    }

    public PolicyLineAnswerQR getHazmat() {
        return hazmat;
    }

    public void setHazmat(PolicyLineAnswerQR hazmat) {
        this.hazmat = hazmat;
    }

    public PolicyLineAnswerQR getAboveOrBelowGround() {
        return aboveOrBelowGround;
    }

    public void setAboveOrBelowGround(PolicyLineAnswerQR aboveOrBelowGround) {
        this.aboveOrBelowGround = aboveOrBelowGround;
    }

    public PolicyLineAnswerQR getOnOrOverWater() {
        return onOrOverWater;
    }

    public void setOnOrOverWater(PolicyLineAnswerQR onOrOverWater) {
        this.onOrOverWater = onOrOverWater;
    }

    public PolicyLineAnswerQR getEngagedInOtherBusiness() {
        return engagedInOtherBusiness;
    }

    public void setEngagedInOtherBusiness(PolicyLineAnswerQR engagedInOtherBusiness) {
        this.engagedInOtherBusiness = engagedInOtherBusiness;
    }

    public PolicyLineAnswerQR getSubcontractorsUsed() {
        return subcontractorsUsed;
    }

    public void setSubcontractorsUsed(PolicyLineAnswerQR subcontractorsUsed) {
        this.subcontractorsUsed = subcontractorsUsed;
    }

    public PolicyLineAnswerQR getSubcontractorsPercent() {
        return subcontractorsPercent;
    }

    public void setSubcontractorsPercent(PolicyLineAnswerQR subcontractorsPercent) {
        this.subcontractorsPercent = subcontractorsPercent;
    }

    public PolicyLineAnswerQR getAnyWorkSublet() {
        return anyWorkSublet;
    }

    public void setAnyWorkSublet(PolicyLineAnswerQR anyWorkSublet) {
        this.anyWorkSublet = anyWorkSublet;
    }

    public PolicyLineAnswerQR getSafetyProgram() {
        return safetyProgram;
    }

    public void setSafetyProgram(PolicyLineAnswerQR safetyProgram) {
        this.safetyProgram = safetyProgram;
    }

    public PolicyLineAnswerQR getGroupTransportation() {
        return groupTransportation;
    }

    public void setGroupTransportation(PolicyLineAnswerQR groupTransportation) {
        this.groupTransportation = groupTransportation;
    }

    public PolicyLineAnswerQR getAgeRange() {
        return ageRange;
    }

    public void setAgeRange(PolicyLineAnswerQR ageRange) {
        this.ageRange = ageRange;
    }

    public PolicyLineAnswerQR getSeasonalEmployees() {
        return seasonalEmployees;
    }

    public void setSeasonalEmployees(PolicyLineAnswerQR seasonalEmployees) {
        this.seasonalEmployees = seasonalEmployees;
    }

    public PolicyLineAnswerQR getVolunteer() {
        return volunteer;
    }

    public void setVolunteer(PolicyLineAnswerQR volunteer) {
        this.volunteer = volunteer;
    }


    
    public PolicyLineAnswerQR getDisabledEmployees() {
        return this.disabledEmployees;
    }
    
    public void setDisabledEmployees(PolicyLineAnswerQR disabledEmployees) {
        this.disabledEmployees = disabledEmployees;
    }    
    
    public PolicyLineAnswerQR getOosTravel() {
        return this.oosTravel;
    }
    
    public void setOosTravel(PolicyLineAnswerQR oosTravel) {
        this.oosTravel = oosTravel;
    }    
    
    public PolicyLineAnswerQR getAthleticSponsorship() {
        return this.athleticSponsorship;
    }
    
    public void setAthleticSponsorship(PolicyLineAnswerQR athleticSponsorship) {
        this.athleticSponsorship = athleticSponsorship;
    }    
    
    public PolicyLineAnswerQR getPhysicalsRequired() {
        return this.physicalsRequired;
    }
    
    public void setPhysicalsRequired(PolicyLineAnswerQR physicalsRequired) {
        this.physicalsRequired = physicalsRequired;
    }    
    
    public PolicyLineAnswerQR getOtherInsurance() {
        return this.otherInsurance;
    }
    
    public void setOtherInsurance(PolicyLineAnswerQR otherInsurance) {
        this.otherInsurance = otherInsurance;
    }    
    
    public PolicyLineAnswerQR getPriorDeclined() {
        return this.priorDeclined;
    }
    
    public void setPriorDeclined(PolicyLineAnswerQR priorDeclined) {
        this.priorDeclined = priorDeclined;
    }    
    
    public PolicyLineAnswerQR getEmployeeHealthPlans() {
        return this.employeeHealthPlans;
    }
    
    public void setEmployeeHealthPlans(PolicyLineAnswerQR employeeHealthPlans) {
        this.employeeHealthPlans = employeeHealthPlans;
    }    
    
    public PolicyLineAnswerQR getLaborInterchange() {
        return this.laborInterchange;
    }
    
    public void setLaborInterchange(PolicyLineAnswerQR laborInterchange) {
        this.laborInterchange = laborInterchange;
    }    
    
    public PolicyLineAnswerQR getLeaseEmployees() {
        return this.leaseEmployees;
    }
    
    public void setLeaseEmployees(PolicyLineAnswerQR leaseEmployees) {
        this.leaseEmployees = leaseEmployees;
    }    
    
    public PolicyLineAnswerQR getWorkAtHome() {
        return this.workAtHome;
    }
    
    public void setWorkAtHome(PolicyLineAnswerQR workAtHome) {
        this.workAtHome = workAtHome;
    }    
    
    public PolicyLineAnswerQR getTaxLiens() {
        return this.taxLiens;
    }
    
    public void setTaxLiens(PolicyLineAnswerQR taxLiens) {
        this.taxLiens = taxLiens;
    }    
    
    public PolicyLineAnswerQR getUnpaidPremium() {
        return this.unpaidPremium;
    }
    
    public void setUnpaidPremium(PolicyLineAnswerQR unpaidPremium) {
        this.unpaidPremium = unpaidPremium;
    }    

    public List<OwnerOfficerBO> getOwnerOfficers() {
        return ownerOfficers;
    }

    public void setOwnerOfficers(List<OwnerOfficerBO> ownerOfficers) {
        this.ownerOfficers = ownerOfficers;
    }

    public List<PolicyLocationQR> getAdditionalLocations() {
        return additionalLocations;
    }

    public void setAdditionalLocations(List<PolicyLocationQR> additionalLocations) {
        this.additionalLocations = additionalLocations;
    }

    public ContactBO getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(ContactBO contactInfo) {
        this.contactInfo = contactInfo;
    }

    public ContactBO getAccountHolderContact() {
        return accountHolderContact;
    }

    public void setAccountHolderContact(ContactBO accountHolderContact) {
        this.accountHolderContact = accountHolderContact;
    }

    public AddressBO getAccountHolderPrimaryAddress() {
        return accountHolderPrimaryAddress;
    }

    public void setAccountHolderPrimaryAddress(AddressBO accountHolderPrimaryAddress) {
        this.accountHolderPrimaryAddress = accountHolderPrimaryAddress;
    }

    public PolicyPeriodQR.PrimaryNamedInsured getPrimaryNamedInsured() {
        return primaryNamedInsured;
    }

    public void setPrimaryNamedInsured(PolicyPeriodQR.PrimaryNamedInsured primaryNamedInsured) {
        this.primaryNamedInsured = primaryNamedInsured;
    }

    public ContactBO.EntityPerson getEntityPerson() {
        return contactEntityPerson;
    }

    public void setEntityPerson(ContactBO.EntityPerson entityPerson) {
        this.contactEntityPerson = entityPerson;
    }

    public QuoteRequest getQuoteRequest() {
        return quoteRequest;
    }

    public void setQuoteRequest(QuoteRequest quoteRequest) {
        this.quoteRequest = quoteRequest;
    }

    public ContactBO.EntityPerson getAuditorEntityPerson() {
        return auditorEntityPerson;
    }

    public void setAuditorEntityPerson(ContactBO.EntityPerson auditorEntityPerson) {
        this.auditorEntityPerson = auditorEntityPerson;
    }

    public ContactBO.EntityPerson getContactEntityPerson() {
        return contactEntityPerson;
    }

    public void setContactEntityPerson(ContactBO.EntityPerson contactEntityPerson) {
        this.contactEntityPerson = contactEntityPerson;
    }

}
