/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pwc.us.wsclient.fnoi;

import com.pwc.us.common.model.fnoi.BodyPartDetailsFNOI;
import com.pwc.us.common.model.fnoi.ClaimContactFNOI;
import com.pwc.us.common.model.fnoi.ClaimContactRoleFNOI;
import com.pwc.us.common.model.fnoi.ClaimFNOI;
import com.pwc.us.common.model.ContactBO;
import com.pwc.us.common.model.fnoi.IncidentFNOI;
import com.pwc.us.common.model.fnoi.PolicyFNOI;
import com.pwc.us.common.model.fnoi.PolicyLocationFNOI;
import com.pwc.us.common.model.fnoi.WorkStatusFNOI;
import com.pwc.us.common.model.AddressBO;
import com.pwc.us.common.model.fnoi.BodyPartTypeFNOI;
import com.pwc.us.common.model.fnoi.EmploymentDataFNOI;
import com.pwc.us.common.model.KeyName;
import com.pwc.us.common.model.fnoi.NoteFNOI;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.junit.Test;
import static org.junit.Assert.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 *
 * Robert Snelling <robert.snelling@us.pwc.com>
 */
public class FNOIClientTester {
    private static final Logger logger = LoggerFactory.getLogger(FNOIClientTester.class);
    private static final Logger claimsLog = LoggerFactory.getLogger("claimsLog");
    public FNOIClientTester() {
        
    }
    
    @Test
    public void testFNOI() {

        String createNewFNOI;
        List<KeyName> specificDetailedInjuryTypeTypeKeys;
        List<KeyName> occupationalInjuryTypeTypeKeys;
        List<KeyName> detailedBodyPartTypeTypeKeys;
        List<KeyName> bodyPartTypeTypeKeys;
        List<KeyName> accountOrgTypeExtTypeKeys;
        List<KeyName> primaryInjuryTypeTypeKeys;
        boolean expResult = true;
        boolean result = true;
        
        claimsLog.error("from fnoiclientester...");

        try {
            FNOIClientTester tester = new FNOIClientTester();
            FNOIRequestBuilder client = new FNOIRequestBuilder();
            specificDetailedInjuryTypeTypeKeys = client.getSpecificDetailedInjuryTypeTypeKeys();
            System.out.println("The specificDetailedInjuryTypeTypeKeys is" + specificDetailedInjuryTypeTypeKeys);            
            occupationalInjuryTypeTypeKeys = client.getOccupationalInjuryTypeTypeKeys();
            System.out.println("The occupationalInjuryTypeTypeKeys is" + occupationalInjuryTypeTypeKeys);            
            detailedBodyPartTypeTypeKeys = client.getDetailedBodyPartTypeTypeKeys(BodyPartTypeFNOI.UPPER);
            System.out.println("The detailedBodyPartTypeTypeKeys is" + detailedBodyPartTypeTypeKeys);            
            bodyPartTypeTypeKeys = client.getBodyPartTypeTypeKeys();
            System.out.println("The bodyPartTypeTypeKeys is" + bodyPartTypeTypeKeys);            
            accountOrgTypeExtTypeKeys = client.getAccountOrgTypeExtTypeKeys();
            System.out.println("The accountOrgTypeExtTypeKeys is" + accountOrgTypeExtTypeKeys);            
            primaryInjuryTypeTypeKeys = client.getPrimaryInjuryTypeTypeKeys();
            System.out.println("The primaryInjuryTypeTypeKeys are "+primaryInjuryTypeTypeKeys);
            ClaimFNOI claim = tester.createClaimFNOI();
            createNewFNOI = client.processRequest(claim);
            System.out.println("The createNewFNOI is" + createNewFNOI);
            

        } catch (ParseException e) {
            result = false;
            claimsLog.error("from FNOIClientTester "+e.getMessage());
            e.printStackTrace();
        } catch (DatatypeConfigurationException ex) {
            result = false;
            claimsLog.error("from FNOIClientTester "+ex.getMessage());
            ex.printStackTrace();
        } catch (Exception e) {
            claimsLog.error("from FNOIClientTester "+e.getMessage());
            result = false;
            e.printStackTrace();
        }
        assertEquals(expResult, result);
    }

    public ClaimFNOI createClaimFNOI() throws ParseException,
            DatatypeConfigurationException {

        // JAXB Claim Objects
        ClaimFNOI claim = new ClaimFNOI();
        ClaimFNOI.Contacts contacts = new ClaimFNOI.Contacts();
        List<ClaimContactFNOI> claimContacts = contacts.getEntry();
        ClaimFNOI.Incidents incidents = new ClaimFNOI.Incidents();
        ClaimFNOI.Notes notes = new ClaimFNOI.Notes();
        List<IncidentFNOI> incidentList = incidents.getEntry();
        List<NoteFNOI> noteList = notes.getEntry();

        // Build and Set the Contacts on the Claim
        buildContacts(claimContacts, "Johnny", "Tester", "M",
                "1980-10-15 12:00:00.0000000", "G", "Tester", "claimant");
        buildContacts(claimContacts, "Bill", "Reporter", "F",
                "1980-10-15 12:00:00.0000000", "G", "Programmer", "reporter");
        buildContacts(claimContacts, "Doctor", "Johns", "M",
                "1980-10-15 12:00:00.0000000", "G", "Doctor", "FirstIntakeDoctor");
        buildContacts(claimContacts, "Manager", "Smith", "M",
                "1980-10-15 12:00:00.0000000", "G", "Manager", "supervisor");
        buildContacts(claimContacts, "Salley", "Sees", "F",
                "1980-10-15 12:00:00.0000000", "G", "Associate", "witness");
        
        claim.setContacts(contacts);

        // setDateRptdToEmployer
        claim.setDateRptdToEmployer(stringToXMLGregorianCalendar("2013-10-15 12:00:00.0000000"));

        // setDeathDate
        claim.setDeathDate(stringToXMLGregorianCalendar("2013-10-15 12:00:00.0000000"));

        // setDescription
        claim.setDescription("This is the description");

        // setEmpQusValidity
        claim.setEmpQusValidity("No");

        EmploymentDataFNOI ed = new EmploymentDataFNOI();
        EmploymentDataFNOI.WorkStatusChanges wsChanges = new EmploymentDataFNOI.WorkStatusChanges();
        List<WorkStatusFNOI> wsList = wsChanges.getEntry();
        WorkStatusFNOI ws = new WorkStatusFNOI();
        ws.setStatus("stopped_work");
        ws.setStatusDate(stringToXMLGregorianCalendar("2013-10-15 12:00:00.0000000"));
        wsList.add(ws);

        ed.setWorkStatusChanges(wsChanges);
        ed.setHireDate(stringToXMLGregorianCalendar("2012-10-15 12:00:00.0000000"));
        ed.setHireState("OK");
        ed.setInjuryStartTime(stringToXMLGregorianCalendar("2013-10-15 12:00:00.0000000"));
        ed.setLastWorkedDate(stringToXMLGregorianCalendar("2013-10-15 12:00:00.0000000"));
        ed.setWageAmount(300000);

        claim.setEmploymentData(ed);

        IncidentFNOI incident = new IncidentFNOI();
        IncidentFNOI.EntityInjuryIncident injury = new IncidentFNOI.EntityInjuryIncident();
        IncidentFNOI.EntityInjuryIncident.BodyParts bodyParts = new IncidentFNOI.EntityInjuryIncident.BodyParts();
        List<BodyPartDetailsFNOI> bodyPartsList = bodyParts.getEntry();
        
        NoteFNOI note = new NoteFNOI();
        NoteFNOI.Topic topic = new NoteFNOI.Topic();
        topic.setDescription("insured_ext");
        topic.setDisplayName("insured_ext");
        note.setSubject("Portal Comments");
        note.setBody("Comments body from test");
        note.setTopic(topic);
        noteList.add(note);
        
       

        BodyPartDetailsFNOI bodyPart = new BodyPartDetailsFNOI();
        bodyPart.setDetailedBodyPart("11");
        bodyPart.setPrimaryBodyPart("head");
        bodyPartsList.add(bodyPart);

        injury.setBodyParts(bodyParts);
        injury.setDetailedInjuryType("62");
        injury.setGeneralInjuryType("occupational");

        incident.setEntityInjuryIncident(injury);
        incident.setSeverity("fatal");
        incidentList.add(incident);
        
        
        
        claim.setIncidents(incidents);
        claim.setNotes(notes);

        // setLossDate
        claim.setLossDate(stringToXMLGregorianCalendar("2013-10-15 12:30:00.0000000"));

        // setLossLocation
        claim.setLossLocation(buildAddress("456 Main St", "Apt 2", "Any City", "Any County", "OK", "55555"));

        // Build and Set the Contacts on the Policy
        PolicyFNOI policy = new PolicyFNOI();
        PolicyFNOI.Contacts policyContacts = new PolicyFNOI.Contacts();
        PolicyFNOI.PolicyLocations policyLocations = new PolicyFNOI.PolicyLocations();
        List<ClaimContactFNOI> policyClaimContacts = policyContacts.getEntry();
        List<PolicyLocationFNOI> policyLocationsList = policyLocations.getEntry();

        buildContacts(policyClaimContacts, "Johnny", "Policyholder", "M",
                "1970-10-15 12:00:00.0000000", "G", "Tester", "insured");
        policy.setContacts(policyContacts);
        PolicyLocationFNOI policyLocation = new PolicyLocationFNOI();
        policyLocation.setAddress(buildAddress("456 Main St", "Apt 2", "Any City", "Any County", "OK", "55555"));
        policyLocation.setLocationNumber("122");
        policyLocationsList.add(policyLocation);
        policy.setPolicyLocations(policyLocations);
        policy.setAccountOrgTypeExt("llc");
        policy.setAccountTypeOfBusinessExt("Business");
        policy.setEffectiveDate(stringToXMLGregorianCalendar("2013-10-15 12:00:00.0000000"));
        policy.setExpirationDate(stringToXMLGregorianCalendar("2015-10-15 00:00:00.0000000"));
        policy.setInsuredSICCode("Code");
        policy.setNotes("No notes here");
        policy.setPolicyNumber("00000011");
        claim.setPolicy(policy);

        // setReportedByType
        claim.setReportedByType("self");

        return claim;
    }

    private void buildContacts(List<ClaimContactFNOI> claimContacts,
            String firstName, String lastName, String gender,
            String dob, String middleName, String occupation,
            String role)
            throws ParseException,
            DatatypeConfigurationException {

        ClaimContactFNOI cc = new ClaimContactFNOI();
        ContactBO c = new ContactBO();
        ContactBO.EntityPerson ep = new ContactBO.EntityPerson();
        ClaimContactFNOI.Roles roles = new ClaimContactFNOI.Roles();
        List<ClaimContactRoleFNOI> ccRole = roles.getEntry();

        //Build the Contact.EntityPerson
        ep.setDateOfBirth(stringToXMLGregorianCalendar(dob));
        ep.setFirstName(firstName);
        ep.setLastName(lastName);
        ep.setGender(gender);
        ep.setMiddleName(middleName);
        ep.setOccupation(occupation);

        // Build the Contact
        c.setEmailAddress1("fakeemail@testing.com");
        c.setHomePhone("888-555-4141");
        c.setName("Fake Account");
        c.setTaxID("12-3456789");
        c.setWorkPhone("888-555-4242");
        c.setEntityPerson(ep);
        c.setPrimaryAddress(buildAddress("123 Main St", "Apt 2", "Any City", "Any County", "OK", "55555"));

        ClaimContactRoleFNOI role1 = new ClaimContactRoleFNOI();
        role1.setRole(role);
        ccRole.add(role1);

        // Build the ClaimContact
        cc.setContactFNOI(c);
        cc.setRoles(roles);

        // Add to the List<ClaimContact> 
        claimContacts.add(cc);
    }

    private AddressBO buildAddress(String add1, String add2, String city, String county, String st, String zip) {
        AddressBO address = new AddressBO();
        // Build the Address

        address.setAddressLine1(add1);
        address.setAddressLine2(add2);
        address.setCity(city);
        address.setCounty(county);
        address.setPostalCode(zip);
        address.setState(st);
        return address;
    }

    public XMLGregorianCalendar stringToXMLGregorianCalendar(String s)
            throws ParseException,
            DatatypeConfigurationException {
        XMLGregorianCalendar result = null;

        Date date;
        SimpleDateFormat simpleDateFormat;
        GregorianCalendar gregorianCalendar;

        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSSS");
        date = simpleDateFormat.parse(s);
        gregorianCalendar =
                (GregorianCalendar) GregorianCalendar.getInstance();
        gregorianCalendar.setTime(date);
        result = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar);

        return result;
    }
}
