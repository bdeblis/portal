package com.pwc.us.webui.stripes.action;

import com.pwc.us.common.model.UsState;
import com.pwc.us.common.model.AddressBO;
import com.pwc.us.common.model.fnoi.BodyPartDetailsFNOI;
import com.pwc.us.common.model.fnoi.ClaimFNOI;
import com.pwc.us.common.model.ContactBO;
import com.pwc.us.common.model.fnoi.EmploymentDataFNOI;
import com.pwc.us.common.model.fnoi.IncidentFNOI;
import com.pwc.us.common.model.fnoi.NoteFNOI;
import com.pwc.us.common.model.fnoi.PolicyFNOI;
import com.pwc.us.common.model.fnoi.PolicyLocationFNOI;
import com.pwc.us.common.model.KeyName;
import com.pwc.us.common.model.fnoi.WorkStatusFNOI;
import com.pwc.us.common.utils.NullChecker;
import com.pwc.us.webui.stripes.converter.TimeTypeConverter;
import java.util.Date;
import java.util.List;
import net.sourceforge.stripes.validation.DateTypeConverter;
import net.sourceforge.stripes.validation.EmailTypeConverter;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidateNestedProperties;

/**
 *
 * Robert Snelling <robert.snelling@us.pwc.com>
 */
public class FNOIBaseActionBean extends BaseActionBean {

    protected static final String PHONE_REGEX = "^[2-9]\\d{2}-[2-9]\\d{2}-\\d{4}$";
    
    protected static final int WITNESS_AMT = 10;

    protected List<KeyName> primaryInjuryType;
    protected List<KeyName> cumulativeDetailedInjuryType;
    protected List<KeyName> multipleInjuryType;
    protected List<KeyName> specificDetailedInjuryType;
    protected List<KeyName> occupationalInjuryType;
    protected List<KeyName> bodyPartType;
    protected List<KeyName> accountOrgTypeExt;
    protected List<String> primaryInjuryList;
    protected List<String> cumulativeDetailedList;
    protected List<String> multipleInjuryList;
    protected List<String> bodyPartList;
    protected List<String> occupationalList;
    protected List<String> specificDetailedList;
    protected List<KeyName> headDetailedBodyParts;
    protected List<KeyName> neckDetailedBodyParts;
    protected List<KeyName> upperDetailedBodyParts;
    protected List<KeyName> trunkDetailedBodyParts;
    protected List<KeyName> lowerDetailedBodyParts;
    protected List<KeyName> multipleDetailedBodyParts;
    protected List<String> headDetailedBodyPartsList;
    protected List<String> neckDetailedBodyPartsList;
    protected List<String> upperDetailedBodyPartsList;
    protected List<String> trunkDetailedBodyPartsList;
    protected List<String> lowerDetailedBodyPartsList;
    protected List<String> multipleDetailedBodyPartsList;
    
    @ValidateNestedProperties({
        @Validate(field = "description", required = true, maxlength = 1333, on = "submit"),
        @Validate(field = "empQusValidity", required = true, on = "submit"),
        @Validate(field = "reportedByType", required = true, on = "submit"),
        @Validate(field="incidentOnly",required = true, on = "submit")
    })
    protected ClaimFNOI claim;
    
    @ValidateNestedProperties({
        @Validate(field = "taxID", required = true, maxlength = 30, mask = "^\\d{3}-\\d{2}-\\d{4}$", on = "submit"),
        @Validate(field="emailAddress1" , required = false, on="submit", maxlength = 60, converter=EmailTypeConverter.class),
        @Validate(field = "homePhone", on="submit", maxlength = 30, mask = PHONE_REGEX)})
    protected ContactBO claimant;
    
    
    protected ContactBO physician;
    protected ContactBO supervisor;

    @ValidateNestedProperties({
        @Validate(field="emailAddress1" , on="submit", maxlength = 60, converter=EmailTypeConverter.class),
        @Validate(field = "workPhone", on="submit", required = true, maxlength = 30,
                mask = PHONE_REGEX)
    })
    protected ContactBO reporter;
    protected List<ContactBO.EntityPerson> witnessList;

    @ValidateNestedProperties({
        @Validate(field="name" , on="submit", required = true, maxlength = 60),
        @Validate(field = "workPhone", on="submit", required = true, maxlength = 30,
                mask = PHONE_REGEX)
    })
    protected ContactBO policyHolder;
    
    @ValidateNestedProperties({
        @Validate(field = "lastName", required = true, maxlength = 30, on = "submit"),
        @Validate(field = "firstName", required = true, maxlength = 30, on = "submit"),
        @Validate(field = "middleName", maxlength = 30, on = "submit"),
        @Validate(field = "occupation", maxlength = 80, required = true, on = "submit")
    })
    protected ContactBO.EntityPerson claimantEP;
    
    @ValidateNestedProperties({
        @Validate(field = "lastName", maxlength = 30, on = "submit"),
        @Validate(field = "firstName", maxlength = 30, on = "submit"),
        @Validate(field = "middleName", maxlength = 30, on = "submit")
        
    })    
    protected ContactBO.EntityPerson physicianEP;
    
    @ValidateNestedProperties({
        @Validate(field = "lastName", maxlength = 30, on = "submit"),
        @Validate(field = "firstName", maxlength = 30, on = "submit")
    })    
    protected ContactBO.EntityPerson supervisorEP;
    
    @ValidateNestedProperties({
        @Validate(field = "lastName", required = true, maxlength = 30, on = "submit"),
        @Validate(field = "firstName", required = true, maxlength = 30, on = "submit")
    })    
    protected ContactBO.EntityPerson reporterEP;

    protected ContactBO.EntityPerson policyHolderEP;
    /*
    @ValidateNestedProperties({
        @Validate(field = "addressLine1", maxlength = 60, on = "submit"),
        @Validate(field = "addressLine2", maxlength = 60, on = "submit"),
        @Validate(field = "city", maxlength = 60, on = "submit"),
        @Validate(field = "postalCode", maxlength = 60, on = "submit")
    })
    */
    protected AddressBO lossLocation;
    
    
    @ValidateNestedProperties({
        @Validate(field = "addressLine1", required = true, maxlength = 60, on = "submit"),
        @Validate(field = "addressLine2", maxlength = 60, on = "submit"),
        @Validate(field = "city", required = true, maxlength = 60, on = "submit"),
        @Validate(field = "state", required = true, on = "submit"),
        @Validate(field = "postalCode", required = true, maxlength = 60, on = "submit")
    })    
    protected AddressBO claimantAddress;

    /*
    @ValidateNestedProperties({
        @Validate(field = "addressLine1", maxlength = 60, on = "submit"),
        @Validate(field = "addressLine2", maxlength = 60, on = "submit"),
        @Validate(field = "city", maxlength = 60, on = "submit"),
        @Validate(field = "postalCode", maxlength = 60, on = "submit")
    })    
    */
    protected AddressBO physicianAddress;

    @ValidateNestedProperties({
        @Validate(field = "addressLine1", required = true, maxlength = 60, on = "submit"),
        @Validate(field = "addressLine2", maxlength = 60, on = "submit"),
        @Validate(field = "city", required = true, maxlength = 60, on = "submit"),
        @Validate(field = "state", required = true, on = "submit"),
        @Validate(field = "postalCode", required = true, maxlength = 60, on = "submit")
    })    
    protected AddressBO policyLocationAddress;
    protected WorkStatusFNOI workStatus;
    
    @ValidateNestedProperties({
        @Validate(field= "wageAmount", minvalue = 0, mask = "^\\d*(.?\\d{2})?$")
    })
    protected EmploymentDataFNOI employmentData;
    protected IncidentFNOI incident;
    
    @ValidateNestedProperties({
        @Validate(field="generalInjuryType" , required = true, on = "submit" )
    })
    protected IncidentFNOI.EntityInjuryIncident injury;   
    
    protected BodyPartDetailsFNOI bodyPart;
    
    @ValidateNestedProperties({
        @Validate(field = "policyNumber", required = true, maxlength = 8, on = "submit"),
        @Validate(field = "accountTypeOfBusinessExt", maxlength = 1333, on = "submit"),
        @Validate(field = "insuredSICCode", maxlength = 6, on = "submit")
        //@Validate(field = "notes", maxlength = 255, on = "submit")
    })    
    protected PolicyFNOI policy;
    
    @ValidateNestedProperties({
        @Validate(field="locationNumber" , maxlength = 10, on = "submit" )
    })
    protected PolicyLocationFNOI policyLocation;
    
    protected String hireStateQuestion;
    
    @ValidateNestedProperties({        
        @Validate(field = "notes", maxlength = 255, on = "submit")
    })    
    protected NoteFNOI note;    
    
    protected NoteFNOI.Topic topic;

    
    // Dates & Times
    @Validate(converter = DateTypeConverter.class, expression="${dateOfHire < today}" , required = true, on = "submit" )
    protected Date dateOfHire;

    @Validate(converter = DateTypeConverter.class, expression="${dateOfBirth < today}" , required = true, on = "submit" )
    protected Date dateOfBirth;

    @Validate(converter = DateTypeConverter.class, expression="${dateOfAccident <= today}" , required = true, on = "submit" )
    protected Date dateOfAccident;

    @Validate(converter=TimeTypeConverter.class)
    protected Date timeOfAccident;
    
    @Validate(converter = DateTypeConverter.class, expression="${dateReported <= today}" , required = true, on = "submit" )
    protected Date dateReported;
    
    //new field for time reported
    @Validate(converter=TimeTypeConverter.class)
    protected Date timeReported;

    @Validate(converter=TimeTypeConverter.class)
    protected Date workDayTime;
    @Validate(converter = DateTypeConverter.class, expression="${lastWorkedDate <= today}" , on = "submit" )
    protected Date lastWorkedDate;
    @Validate(converter = DateTypeConverter.class, expression="${returnToWorkDate <= today}" , on = "submit" )
    protected Date returnToWorkDate;
    @Validate(converter = DateTypeConverter.class, expression="${deathDate <= today}" , on = "submit" )
    protected Date deathDate;
    protected String returnToWorkQuestion;
    protected String deathDateQuestion;
    @Validate(converter = DateTypeConverter.class, expression="${policyFromDate <= today}" , on = "submit" )
    protected Date policyFromDate;
    @Validate(converter = DateTypeConverter.class, on = "submit" )
    protected Date policyToDate;
    
    public UsState[] getUsStates() {
        return UsState.values();
    }


    public Date getDateOfAccident() {
        return dateOfAccident;
    }

    public void setDateOfAccident(Date dateOfAccident) {
        this.dateOfAccident = dateOfAccident;
    }

    public Date getTimeOfAccident() {
        return timeOfAccident;
    }

    public void setTimeOfAccident(Date timeOfAccident) {
        this.timeOfAccident = timeOfAccident;
    }

    public Date getDateReported() {
        return dateReported;
    }

    public void setDateReported(Date dateReported) {
        this.dateReported = dateReported;
    }
    
    
    public Date getTimeReported() {
        return timeReported;
    }

    public void setTimeReported(Date timeReported) {
        this.timeReported = timeReported;
    }
    
    public Date getWorkDayTime() {
        return workDayTime;
    }

    public void setWorkDayTime(Date workDayTime) {
        this.workDayTime = workDayTime;
    }

    public Date getLastWorkedDate() {
        return lastWorkedDate;
    }

    public void setLastWorkedDate(Date lastWorkedDate) {
        this.lastWorkedDate = lastWorkedDate;
    }

    public Date getReturnToWorkDate() {
        return returnToWorkDate;
    }

    public void setReturnToWorkDate(Date returnToWorkDate) {
        this.returnToWorkDate = returnToWorkDate;
    }

    public Date getDeathDate() {
        return deathDate;
    }

    public void setDeathDate(Date deathDate) {
        this.deathDate = deathDate;
    }

    public String getReturnToWorkQuestion() {
        return returnToWorkQuestion;
    }

    public void setReturnToWorkQuestion(String returnToWorkQuestion) {
        this.returnToWorkQuestion = returnToWorkQuestion;
    }
    
    public String getDeathDateQuestion() {
        return deathDateQuestion;
    }

    public void setDeathDateQuestion(String deathDateQuestion) {
        this.deathDateQuestion = deathDateQuestion;
    }
    
    public String getHireStateQuestion() {
        return hireStateQuestion;
    }

    public void setHireStateQuestion(String hireStateQuestion) {
        this.hireStateQuestion = hireStateQuestion;
    }

    public ClaimFNOI getClaim() {
        return claim;
    }

    public void setClaim(ClaimFNOI claim) {
        this.claim = claim;
    }

    public ContactBO getClaimant() {
        return claimant;
    }

    public void setClaimant(ContactBO claimant) {
        this.claimant = claimant;
    }

    public ContactBO getPhysician() {
        if (NullChecker.isNullOrEmpty(physician)) {
            physician = new ContactBO();
        }
        return physician;
    }

    public void setPhysician(ContactBO physician) {
        this.physician = physician;
    }

    public ContactBO getSupervisor() {
        if (NullChecker.isNullOrEmpty(supervisor)) {
            supervisor = new ContactBO();
        }
        return supervisor;
    }

    public void setSupervisor(ContactBO supervisor) {
        this.supervisor = supervisor;
    }

    public ContactBO getReporter() {
        if (NullChecker.isNullOrEmpty(supervisor)) {
            supervisor = new ContactBO();
        }
        return reporter;
    }

    public void setReporter(ContactBO reporter) {
        this.reporter = reporter;
    }

    public ContactBO getPolicyHolder() {
        if (NullChecker.isNullOrEmpty(policyHolder)) {
            policyHolder = new ContactBO();
        }
        return policyHolder;
    }

    public void setPolicyHolder(ContactBO policyHolder) {
        this.policyHolder = policyHolder;
    }

    public ContactBO.EntityPerson getClaimantEP() {
        return claimantEP;
    }

    public void setClaimantEP(ContactBO.EntityPerson claimantEP) {
        this.claimantEP = claimantEP;
    }

    public ContactBO.EntityPerson getPhysicianEP() {
        return physicianEP;
    }

    public void setPhysicianEP(ContactBO.EntityPerson physicianEP) {
        this.physicianEP = physicianEP;
    }

    public ContactBO.EntityPerson getSupervisorEP() {
        return supervisorEP;
    }

    public void setSupervisorEP(ContactBO.EntityPerson supervisorEP) {
        this.supervisorEP = supervisorEP;
    }

    public ContactBO.EntityPerson getReporterEP() {
        return reporterEP;
    }

    public void setReporterEP(ContactBO.EntityPerson reporterEP) {
        this.reporterEP = reporterEP;
    }

    public ContactBO.EntityPerson getPolicyHolderEP() {
        return policyHolderEP;
    }

    public void setPolicyHolderEP(ContactBO.EntityPerson policyHolderEP) {
        this.policyHolderEP = policyHolderEP;
    }

    public AddressBO getLossLocation() {
        return lossLocation;
    }

    public void setLossLocation(AddressBO lossLocation) {
        this.lossLocation = lossLocation;
    }

    public AddressBO getClaimantAddress() {
        return claimantAddress;
    }

    public void setClaimantAddress(AddressBO claimantAddress) {
        this.claimantAddress = claimantAddress;
    }

    public AddressBO getPhysicianAddress() {
        return physicianAddress;
    }

    public void setPhysicianAddress(AddressBO physicianAddress) {
        this.physicianAddress = physicianAddress;
    }

    public AddressBO getPolicyLocationAddress() {
        return policyLocationAddress;
    }

    public void setPolicyLocationAddress(AddressBO policyLocationAddress) {
        this.policyLocationAddress = policyLocationAddress;
    }

    public WorkStatusFNOI getWorkStatus() {
        if (NullChecker.isNullOrEmpty(workStatus)) {
            workStatus = new WorkStatusFNOI();
        }
        return workStatus;
    }

    public void setWorkStatus(WorkStatusFNOI workStatus) {
        this.workStatus = workStatus;
    }

    public EmploymentDataFNOI getEmploymentData() {
        if (NullChecker.isNullOrEmpty(employmentData)) {
            employmentData = new EmploymentDataFNOI();
        }
        return employmentData;
    }

    public void setEmploymentData(EmploymentDataFNOI employmentData) {
        this.employmentData = employmentData;
    }
    
    public NoteFNOI getNote() {
        if(NullChecker.isNullOrEmpty(note)) {
            note = new NoteFNOI();        }
        return note;
    }
    
    public void setNote(NoteFNOI note) {
        this.note = note;
    }
    
    public NoteFNOI.Topic getTopic() {
        if(NullChecker.isNullOrEmpty(topic)) {
            this.topic = new NoteFNOI.Topic(); 
        }
        return this.topic;
    }
    
       public void setTopic(NoteFNOI.Topic t) {
        this.topic = t;
    }

    public IncidentFNOI getIncident() {
        if (NullChecker.isNullOrEmpty(incident)) {
            incident = new IncidentFNOI();
        }
        return incident;
    }

    public void setIncident(IncidentFNOI incident) {
        this.incident = incident;
    }

    public IncidentFNOI.EntityInjuryIncident getInjury() {
        if (NullChecker.isNullOrEmpty(injury)) {
            injury = new IncidentFNOI.EntityInjuryIncident();
        }
        return injury;
    }

    public void setInjury(IncidentFNOI.EntityInjuryIncident injury) {
        this.injury = injury;
    }
 
   public BodyPartDetailsFNOI getBodyPart() {
        return bodyPart;
    }

    public void setBodyPart(BodyPartDetailsFNOI bodyPart) {
        this.bodyPart = bodyPart;
    }

    public PolicyFNOI getPolicy() {
        return policy;
    }

    public void setPolicy(PolicyFNOI policy) {
        this.policy = policy;
    }

    public PolicyLocationFNOI getPolicyLocation() {
        if (NullChecker.isNullOrEmpty(policyLocation)) {
            policyLocation = new PolicyLocationFNOI();
        }
        return policyLocation;
    }

    public void setPolicyLocation(PolicyLocationFNOI policyLocation) {
        this.policyLocation = policyLocation;
    }

    public List<String> getOccupationalList() {
        return occupationalList;
    }

    public void setOccupationalList(List<String> occupationalList) {
        this.occupationalList = occupationalList;
    }

    public List<String> getSpecificDetailedList() {
        return specificDetailedList;
    }

    public void setSpecificDetailedList(List<String> specificDetailedList) {
        this.specificDetailedList = specificDetailedList;
    }

    public List<String> getBodyPartList() {
        return bodyPartList;
    }

    public void setBodyPartList(List<String> bodyPartList) {
        this.bodyPartList = bodyPartList;
    }

    public List<String> getHeadDetailedBodyPartsList() {
        return headDetailedBodyPartsList;
    }

    public void setHeadDetailedBodyPartsList(List<String> headDetailedBodyPartsList) {
        this.headDetailedBodyPartsList = headDetailedBodyPartsList;
    }

    public List<String> getNeckDetailedBodyPartsList() {
        return neckDetailedBodyPartsList;
    }

    public void setNeckDetailedBodyPartsList(List<String> neckDetailedBodyPartsList) {
        this.neckDetailedBodyPartsList = neckDetailedBodyPartsList;
    }

    public List<String> getUpperDetailedBodyPartsList() {
        return upperDetailedBodyPartsList;
    }

    public void setUpperDetailedBodyPartsList(List<String> upperDetailedBodyPartsList) {
        this.upperDetailedBodyPartsList = upperDetailedBodyPartsList;
    }

    public List<String> getTrunkDetailedBodyPartsList() {
        return trunkDetailedBodyPartsList;
    }

    public void setTrunkDetailedBodyPartsList(List<String> trunkDetailedBodyPartsList) {
        this.trunkDetailedBodyPartsList = trunkDetailedBodyPartsList;
    }

    public List<String> getLowerDetailedBodyPartsList() {
        return lowerDetailedBodyPartsList;
    }

    public void setLowerDetailedBodyPartsList(List<String> lowerDetailedBodyPartsList) {
        this.lowerDetailedBodyPartsList = lowerDetailedBodyPartsList;
    }

    public List<String> getMultipleDetailedBodyPartsList() {
        return multipleDetailedBodyPartsList;
    }

    public void setMultipleDetailedBodyPartsList(List<String> multipleDetailedBodyPartsList) {
        this.multipleDetailedBodyPartsList = multipleDetailedBodyPartsList;
    }

    public List<ContactBO.EntityPerson> getWitnessList() {
        return witnessList;
    }

    public void setWitnessList(List<ContactBO.EntityPerson> witnessList) {
        this.witnessList = witnessList;
    }

    /*
     public Map<KeyNameNameFNOI, List<KeyNameNameFNOI>> getDetailedBodyPartMap() {
     return detailedBodyPartMap;
     }

     public void setDetailedBodyPartMap(Map<KeyNameNameFNOI, List<KeyNameNameFNOI>> detailedBodyPartMap) {
     this.detailedBodyPartMap = detailedBodyPartMap;
     }

     public Map<String, String> getDetailedBodyPartMapList() {
     return detailedBodyPartMapList;
     }

     public void setDetailedBodyPartMapList(Map<String, String> detailedBodyPartMapList) {
     this.detailedBodyPartMapList = detailedBodyPartMapList;
     }
     */
    
    public Date getDateOfHire() {
        return dateOfHire;
    }

    public void setDateOfHire(Date dateOfHire) {
        this.dateOfHire = dateOfHire;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Date getPolicyFromDate() {
        return policyFromDate;
    }

    public void setPolicyFromDate(Date policyFromDate) {
        this.policyFromDate = policyFromDate;
    }

    public Date getPolicyToDate() {
        return policyToDate;
    }

    public void setPolicyToDate(Date policyToDate) {
        this.policyToDate = policyToDate;
    }

    public Date getToday() {
        return new Date();
    }

    public List<String> getCumulativeDetailedList() {
        return cumulativeDetailedList;
    }

    public void setCumulativeDetailedList(List<String> cumulativeDetailedList) {
        this.cumulativeDetailedList = cumulativeDetailedList;
    }

    public List<String> getMultipleInjuryList() {
        return multipleInjuryList;
    }

    public void setMultipleInjuryList(List<String> multipleInjuryList) {
        this.multipleInjuryList = multipleInjuryList;
    }

    
}
