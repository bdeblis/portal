/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pwc.us.wsclient.fnoi;

import com.pwc.us.common.exception.GwIntegrationException;
import com.pwc.us.common.exception.GwFNOIRequiredFieldException;
import com.pwc.us.common.model.AddressBO;
import com.pwc.us.common.model.fnoi.BodyPartDetailsFNOI;
import com.pwc.us.common.model.fnoi.BodyPartTypeFNOI;
import com.pwc.us.common.model.fnoi.ClaimContactFNOI;
import com.pwc.us.common.model.fnoi.ClaimContactRoleFNOI;
import com.pwc.us.common.model.fnoi.ClaimFNOI;
import com.pwc.us.common.model.fnoi.IncidentFNOI;
import com.pwc.us.common.model.fnoi.NoteFNOI;
import com.pwc.us.common.model.fnoi.PolicyLocationFNOI;
import com.pwc.us.common.model.KeyName;
import com.pwc.us.common.model.fnoi.WorkStatusFNOI;
import com.pwc.us.ws.client.fnoi.TypekeyName;
import com.pwc.us.common.utils.NullChecker;
import com.pwc.us.webservice.tools.CDataXMLStreamWriter;
import com.pwc.us.ws.client.fnoi.BodyPartType;
import com.pwc.us.ws.client.fnoi.model.Address;
import com.pwc.us.ws.client.fnoi.model.BodyPartDetails;
import com.pwc.us.ws.client.fnoi.model.Claim;
import com.pwc.us.ws.client.fnoi.model.ClaimContact;
import com.pwc.us.ws.client.fnoi.model.ClaimContactRole;
import com.pwc.us.ws.client.fnoi.model.Contact;
import com.pwc.us.ws.client.fnoi.model.EmploymentData;
import com.pwc.us.ws.client.fnoi.model.Incident;
import com.pwc.us.ws.client.fnoi.model.Note;
import com.pwc.us.ws.client.fnoi.model.ObjectFactory;
import com.pwc.us.ws.client.fnoi.model.Policy;
import com.pwc.us.ws.client.fnoi.model.PolicyLocation;
import com.pwc.us.ws.client.fnoi.model.WorkStatus;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Robert Snelling <robert.snelling@us.pwc.com>
 */
public class FNOIRequestBuilder {

    private static final Logger logger = LoggerFactory.getLogger(FNOIRequestBuilder.class);
    private static final ObjectFactory factory = new ObjectFactory();
    static final Logger claimsLog = LoggerFactory.getLogger("claimsLog");

    public List<KeyName> getPrimaryInjuryTypeTypeKeys() throws GwIntegrationException {
        FNOIClientInt clientInt = new FNOIClientInt();
        return getTypekeyNames(clientInt.getPrimaryInjuryTypeTypeKeys());
    }
    
    public List<KeyName> getMultipleInjuryTypeTypeKeys() throws GwIntegrationException {
        FNOIClientInt clientInt = new FNOIClientInt();
        return getTypekeyNames(clientInt.getMultipleInjuryTypeTypeKeys());
    }

    public List<KeyName> getCumulativeDetailedInjuryTypeTypeKeys() throws GwIntegrationException {
        FNOIClientInt clientInt = new FNOIClientInt();
        return getTypekeyNames(clientInt.getCumulativeInjuryTypeTypeKeys());
    }
    
    public List<KeyName> getSpecificDetailedInjuryTypeTypeKeys() throws GwIntegrationException {
        FNOIClientInt clientInt = new FNOIClientInt();
        return getTypekeyNames(clientInt.getSpecificDetailedInjuryTypeTypeKeys());
    }

    public List<KeyName> getOccupationalInjuryTypeTypeKeys() throws GwIntegrationException {
        FNOIClientInt clientInt = new FNOIClientInt();
        return getTypekeyNames(clientInt.getOccupationalInjuryTypeTypeKeys());
    }

    public List<KeyName> getDetailedBodyPartTypeTypeKeys(BodyPartTypeFNOI primaryBodyPart) throws GwIntegrationException {
        FNOIClientInt clientInt = new FNOIClientInt();
        return getTypekeyNames(clientInt.getDetailedBodyPartTypeTypeKeys(BodyPartType.fromValue(primaryBodyPart.value())));
    }

    public List<KeyName> getBodyPartTypeTypeKeys() throws GwIntegrationException {
        FNOIClientInt clientInt = new FNOIClientInt();
        return getTypekeyNames(clientInt.getBodyPartTypeTypeKeys());
    }

    public List<KeyName> getAccountOrgTypeExtTypeKeys() throws GwIntegrationException {
        FNOIClientInt clientInt = new FNOIClientInt();
        return getTypekeyNames(clientInt.getAccountOrgTypeExtTypeKeys());
    }

    private List<KeyName> getTypekeyNames(List<TypekeyName> typekeyNames) {
        List<KeyName> listTypekeyNames = new ArrayList<KeyName>();

        for (TypekeyName t : typekeyNames) {
            KeyName typekeyNamePOJO = new KeyName();
            typekeyNamePOJO.setCode(t.getCode());
            typekeyNamePOJO.setName(t.getName());
            listTypekeyNames.add(typekeyNamePOJO);
        }
        return listTypekeyNames;
    }
    
    private String getErrorContact(ClaimContactFNOI ccfnoi){
            
            String email = "";
            String homePhone = "";
            String workPhone = "";
            String name = "";
            if(NullChecker.isNullOrEmpty(ccfnoi))
                return " no contact info available ";
            if(!NullChecker.isNotNullOrEmpty(ccfnoi.getContactFNOI().getEmailAddress1()))
                email = ccfnoi.getContactFNOI().getEmailAddress1();
             homePhone = ccfnoi.getContactFNOI().getHomePhone();
            workPhone = ccfnoi.getContactFNOI().getWorkPhone();            
            name = ccfnoi.getContactFNOI().getName();
            return " "+name+" "+email+" "+homePhone+" "+workPhone+" ";
    }

    public String processRequest(ClaimFNOI claimFNOI) throws GwIntegrationException, GwFNOIRequiredFieldException {
        String incidentOnly = claimFNOI.getIncidentOnly();
        //Step 1: Build the Claim Object
        Claim claim = buildFNOIObject(claimFNOI);

        //Step 2: Turn the request into XML
        String fnoiRequestXML = null;
        try {
            fnoiRequestXML = fnoiMarshalling(claim);
        } catch (JAXBException e) {
            
            String message = "Caught JAXBException trying to create new claim.";
            claimsLog.error(message+" Task=ProcessClaimRequest Status=Failed PolicyNumber="+claimFNOI.getPolicy().getPolicyNumber()+getErrorContact(claimFNOI.getContacts().getEntry().get(0)),e);
            logger.error(message, e);
            throw new GwIntegrationException(message, e);            
        } catch (XMLStreamException e) {
            String message = "Caught XMLStreamException trying to create new claim.";
            claimsLog.error(message+" Task=ProcessClaimRequest Status=Failed PolicyNumber="+claimFNOI.getPolicy().getPolicyNumber()+claimFNOI.getPolicy().getPolicyNumber()+getErrorContact(claimFNOI.getContacts().getEntry().get(0)),e);
            logger.error(message, e);
            throw new GwIntegrationException(message, e);              
        }

        //Step 3: Submit the FNOI Request
        String ret = submitFNOIRequest(fnoiRequestXML,incidentOnly);

        return ret;
    }

    private Claim buildFNOIObject(ClaimFNOI claimFNOI) {

        // JAXB Claim Objects
        Claim claim = factory.createClaim();
        Claim.Contacts contacts = factory.createClaimContacts();
        List<ClaimContact> claimContacts = contacts.getEntry();
        Claim.Incidents incidents = factory.createClaimIncidents();
        List<Incident> incidentList = incidents.getEntry();
        Claim.Notes notes = factory.createClaimNotes();
        List<Note> noteList = notes.getEntry();
        Policy policy = factory.createPolicy();
        Policy.Contacts policyContacts = factory.createPolicyContacts();
        Policy.PolicyLocations policyLocations = factory.createPolicyPolicyLocations();
        List<PolicyLocation> policyLocationList = policyLocations.getEntry();
        List<ClaimContact> policyClaimContacts = policyContacts.getEntry();

        // FNOI Claim Objects
        List<ClaimContactFNOI> claimContactsFNOI = new ArrayList<ClaimContactFNOI>();
        List<WorkStatusFNOI> wsFNOIList = new ArrayList<WorkStatusFNOI>();
        List<IncidentFNOI> incidentsFNOI = new ArrayList<IncidentFNOI>();
        List<NoteFNOI> notesFNOI = new ArrayList<NoteFNOI>();
        List<PolicyLocationFNOI> policyLocationsFNOI = new ArrayList<PolicyLocationFNOI>();

        /**
         * setContacts - XML and Objects
         * <Claim> ClaimFNOI
         * <Contacts> ClaimFNOI.Contacts
         * <Entry> List<ClaimContactFNOI>
         * <Contact> ContactFNOI
         * <entity-Person> ContactFNOI.EntityPerson
         * <FirstName>C*30</FirstName>
         * </entity-Person>
         * </Contact>
         * <Roles> ClaimContactFNOI.Roles
         * <Entry>
         * <Role>claimant</Role> ClaimContactRoleFNOI
         * </Entry>
         * </Roles>
         * </Entry>
         * </Contacts>
         * </Claim>
         */
        if (NullChecker.isNotNullOrEmpty(claimFNOI.getContacts()) && NullChecker.isNotNullOrEmpty(claimFNOI.getContacts().getEntry())) {
            claimContactsFNOI = claimFNOI.getContacts().getEntry();
        }

        // Build and Set the Contacts on the Claim
        buildContacts(claimContactsFNOI, claimContacts);
        claim.setContacts(factory.createClaimContacts(contacts));

        // setDateRptdToEmployer
        claim.setDateRptdToEmployer(factory.createClaimDateRptdToEmployer(claimFNOI.getDateRptdToEmployer()));

        // setDeathDate
        claim.setDeathDate(factory.createClaimDeathDate(claimFNOI.getDeathDate()));

        // setDescription
        claim.setDescription(factory.createClaimDescription(claimFNOI.getDescription()));

        // setEmpQusValidity
        claim.setEmpQusValidity(factory.createClaimEmpQusValidity(claimFNOI.getEmpQusValidity()));

        /**
         * setEmploymentData - XML and Objects
         * <Claim> ClaimFNOI
         * <EmploymentData> EmploymentData
         * <WorkStatusChanges> EmploymentDataFNOI.WorkStatusChanges
         * <Entry> List<WorkStatusFNOI>
         * <Status>fullduty/restricted_work</Status>
         * <StatusDate>YYYY-MM-DD hh:mm:ss.0000000</StatusDate>
         * </Entry>
         * </WorkStatusChanges>
         * </EmploymentData>
         * </Claim>
         */
        EmploymentData ed = factory.createEmploymentData();
        EmploymentData.WorkStatusChanges wsChanges = factory.createEmploymentDataWorkStatusChanges();
        List<WorkStatus> wsList = wsChanges.getEntry();

        wsFNOIList = claimFNOI.getEmploymentData().getWorkStatusChanges().getEntry();
        // Build the WorkStatus List
        for (WorkStatusFNOI wsFNOI : wsFNOIList) {
            WorkStatus ws = factory.createWorkStatus();
            ws.setStatus(factory.createWorkStatusStatus(wsFNOI.getStatus()));
            ws.setStatusDate(factory.createWorkStatusStatusDate(wsFNOI.getStatusDate()));
            wsList.add(ws);
        }
        ed.setWorkStatusChanges(factory.createEmploymentDataWorkStatusChanges(wsChanges));
        ed.setHireDate(factory.createEmploymentDataHireDate(claimFNOI.getEmploymentData().getHireDate()));
        ed.setHireState(factory.createEmploymentDataHireState(claimFNOI.getEmploymentData().getHireState()));
        ed.setInjuryStartTime(factory.createEmploymentDataInjuryStartTime(claimFNOI.getEmploymentData().getInjuryStartTime()));
        ed.setLastWorkedDate(factory.createEmploymentDataLastWorkedDate(claimFNOI.getEmploymentData().getLastWorkedDate()));
        ed.setWageAmount(factory.createEmploymentDataWageAmount(Double.toString(claimFNOI.getEmploymentData().getWageAmount())));


        claim.setEmploymentData(factory.createClaimEmploymentData(ed));

        /* setIncidents
         * <Claim>
         *   <Incidents>                                                ClaimFNOI.Incidents
         *     <Entry>                                                  List<IncidentFNOI>
         *       <entity-InjuryIncident>                                IncidentFNOI.EntityInjuryIncident
         *        <DetailedInjuryType>XX</DetailedInjuryType>
         *         <GeneralInjuryType>occupational</GeneralInjuryType>
         *         <BodyParts>                                          IncidentFNOI.EntityInjuryIncident.BodyParts
         *           <Entry>                                            List<BodyPartDetailsFNOI>
         *             <PrimaryBodyPart>C*8</PrimaryBodyPart>           BodyPartDetailsFNOI
         *             <DetailedBodyPart>C*2</DetailedBodyPart>
         *           </Entry>
         *         </BodyParts>
         *       </entity-InjuryIncident>
         *     </Entry>
         *   </Incidents>
         * </Claim>
         */
        if (NullChecker.isNotNullOrEmpty(claimFNOI.getIncidents()) && NullChecker.isNotNullOrEmpty(claimFNOI.getIncidents().getEntry())) {
            incidentsFNOI = claimFNOI.getIncidents().getEntry();
        }
        for (IncidentFNOI iFNOI : incidentsFNOI) {
            Incident incident = factory.createIncident();
            Incident.EntityInjuryIncident injury = factory.createIncidentEntityInjuryIncident();
            Incident.EntityInjuryIncident.BodyParts bodyParts = factory.createIncidentEntityInjuryIncidentBodyParts();
            List<BodyPartDetails> bodyPartsList = bodyParts.getEntry();

            // Build the BodyPartsList
            for (BodyPartDetailsFNOI bpFNOI : iFNOI.getEntityInjuryIncident().getBodyParts().getEntry()) {
                BodyPartDetails bodyPart = factory.createBodyPartDetails();
                bodyPart.setDetailedBodyPart(factory.createBodyPartDetailsDetailedBodyPart(bpFNOI.getDetailedBodyPart()));
                bodyPart.setPrimaryBodyPart(factory.createBodyPartDetailsPrimaryBodyPart(bpFNOI.getPrimaryBodyPart()));
                bodyPartsList.add(bodyPart);
            }

            injury.setBodyParts(factory.createIncidentEntityInjuryIncidentBodyParts(bodyParts));
            injury.setDetailedInjuryType(factory.createIncidentEntityInjuryIncidentDetailedInjuryType(iFNOI.getEntityInjuryIncident().getDetailedInjuryType()));
            injury.setGeneralInjuryType(factory.createIncidentEntityInjuryIncidentGeneralInjuryType(iFNOI.getEntityInjuryIncident().getGeneralInjuryType()));

            incident.setEntityInjuryIncident(factory.createIncidentEntityInjuryIncident(injury));
            incident.setSeverity(factory.createIncidentSeverity(iFNOI.getSeverity()));

            incidentList.add(incident);
        }
        claim.setIncidents(factory.createClaimIncidents(incidents));
        
        //handle the comments field
        
        if (NullChecker.isNotNullOrEmpty(claimFNOI.getNotes()) && NullChecker.isNotNullOrEmpty(claimFNOI.getNotes().getEntry())) {
            notesFNOI = claimFNOI.getNotes().getEntry();
        }
        
        //colin kirk
        //as of now we will only ever have one note
        //however, the note entity in the Claim schema exists in an array
        //so be flexible
        for(NoteFNOI nFNOI : notesFNOI) {            
            Note n = factory.createNote();
            Note.Topic t = factory.createNoteTopic();
            t.setDescription(factory.createNoteTopicDescription(nFNOI.getTopic().getDescription()));
            t.setDisplayName(factory.createNoteTopicDisplayName(nFNOI.getTopic().getDisplayName()));
            n.setSubject(factory.createNoteSubject(nFNOI.getSubject()));
            n.setBody(factory.createNoteBody(nFNOI.getBody()));
            n.setTopic(factory.createNoteTopic(t));
            noteList.add(n);            
        }
        
        claim.setNotes(factory.createClaimNotes((notes)));

        // setLossDate
        if (NullChecker.isNotNullOrEmpty(claimFNOI.getLossDate())) {
            claim.setLossDate(factory.createClaimLossDate(claimFNOI.getLossDate()));
        }

        // setLossLocation
        if (NullChecker.isNotNullOrEmpty(claimFNOI.getLossLocation())) {
            claim.setLossLocation(factory.createClaimLossLocation(buildAddress(claimFNOI.getLossLocation())));
        }

        
        /* setNotes
         * <Claim>
         *  <Notes>                                                     ClaimFNOI.Notes
         *    <Entry>                                                   List<NotesFNOI>
         *      <Body>xx</Body>                                         NoteFNOI.Body
         *      <Subject>xx</Subject>                                   NoteFNOI.Subject                                   
         *      <Topic>                                                 NotesFNOI.Topic
         *        <Entry>                                               
         *          <Description>xx</Description>                       NotesFNOI.Topic.Description
         *          <DisplayName>xx</DisplayName>                       NotesFNOI.Topic.DisplayName                      
         *        </Entry>
         *      </Topic>
         *    </Entry>
         *  </Notes>
         * </Claim>         
         */
         if (NullChecker.isNotNullOrEmpty(claimFNOI.getNotes()) && NullChecker.isNotNullOrEmpty(claimFNOI.getNotes().getEntry())) {
            notesFNOI = claimFNOI.getNotes().getEntry();
        }
             


        // Build and Set the Contacts on the Policy
        buildContacts(claimFNOI.getPolicy().getContacts().getEntry(), policyClaimContacts);
        policy.setContacts(factory.createPolicyContacts(policyContacts));
        policyLocationsFNOI = claimFNOI.getPolicy().getPolicyLocations().getEntry();
        for (PolicyLocationFNOI policyLocationFNOI : policyLocationsFNOI) {
            PolicyLocation policyLocation = factory.createPolicyLocation();
            policyLocation.setAddress(factory.createPolicyLocationAddress(buildAddress(policyLocationFNOI.getAddress())));
            policyLocation.setLocationNumber(factory.createPolicyLocationLocationNumber(policyLocationFNOI.getLocationNumber()));
            policyLocationList.add(policyLocation);
        }
        policy.setPolicyLocations(factory.createPolicyPolicyLocations(policyLocations));
        policy.setAccountOrgTypeExt(factory.createPolicyAccountOrgTypeExt(claimFNOI.getPolicy().getAccountOrgTypeExt()));
        policy.setAccountTypeOfBusinessExt(factory.createPolicyAccountTypeOfBusinessExt(claimFNOI.getPolicy().getAccountTypeOfBusinessExt()));
        policy.setEffectiveDate(factory.createPolicyEffectiveDate(claimFNOI.getPolicy().getEffectiveDate()));
        policy.setExpirationDate(factory.createPolicyExpirationDate(claimFNOI.getPolicy().getExpirationDate()));
        policy.setInsuredSICCode(factory.createPolicyInsuredSICCode(claimFNOI.getPolicy().getInsuredSICCode()));
        policy.setNotes(factory.createPolicyNotes(claimFNOI.getPolicy().getNotes()));
        policy.setPolicyNumber(factory.createPolicyPolicyNumber(claimFNOI.getPolicy().getPolicyNumber()));
        claim.setPolicy(factory.createClaimPolicy(policy));
        

        // setReportedByType
        claim.setReportedByType(factory.createClaimReportedByType(claimFNOI.getReportedByType()));

        return claim;
    }

    private String fnoiMarshalling(Claim claimObject) throws JAXBException, XMLStreamException {
        String fnoiXML = "";

        JAXBContext jaxbContext = JAXBContext.newInstance(Claim.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        XMLOutputFactory xof = XMLOutputFactory.newInstance();
        StringWriter stringWriter = new StringWriter();
        XMLStreamWriter streamWriter = xof.createXMLStreamWriter(stringWriter);
        CDataXMLStreamWriter cdataStreamWriter = new CDataXMLStreamWriter(streamWriter);

        // Marshal CertificateOfInsuranceExt
        JAXBElement<Claim> jClaimObject = factory.createClaim(claimObject);
        jaxbMarshaller.marshal(jClaimObject, cdataStreamWriter);
        fnoiXML = stringWriter.toString();
        streamWriter.close();
        return fnoiXML;
    }

    private String submitFNOIRequest(String requestXML,String incidentOnly) throws GwIntegrationException, GwFNOIRequiredFieldException {

        FNOIClientInt clientInt = new FNOIClientInt();
        String ret = clientInt.createNewFNOI(requestXML,incidentOnly);
        return ret;
    }

    private void buildContacts(List<ClaimContactFNOI> claimContactsFNOI, List<ClaimContact> claimContacts) {

        for (ClaimContactFNOI ccFNOI : claimContactsFNOI) {
            ClaimContact cc = factory.createClaimContact();
            Contact c = factory.createContact();
            Contact.EntityPerson ep = factory.createContactEntityPerson();
            ClaimContact.Roles roles = factory.createClaimContactRoles();
            List<ClaimContactRole> ccRole = roles.getEntry();

            //Build the Contact.EntityPerson
            if (NullChecker.isNotNullOrEmpty(ccFNOI.getContactFNOI().getEntityPerson())) {            
                if (NullChecker.isNotNullOrEmpty(ccFNOI.getContactFNOI().getEntityPerson().getDateOfBirth())) {
                    ep.setDateOfBirth(factory.createContactEntityPersonDateOfBirth(ccFNOI.getContactFNOI().getEntityPerson().getDateOfBirth()));
                }
                if (NullChecker.isNotNullOrEmpty(ccFNOI.getContactFNOI().getEntityPerson().getFirstName())) {                
                    ep.setFirstName(factory.createContactEntityPersonFirstName(ccFNOI.getContactFNOI().getEntityPerson().getFirstName()));
                }
                if (NullChecker.isNotNullOrEmpty(ccFNOI.getContactFNOI().getEntityPerson().getLastName())) {                
                    ep.setLastName(factory.createContactEntityPersonLastName(ccFNOI.getContactFNOI().getEntityPerson().getLastName()));
                }
                if (NullChecker.isNotNullOrEmpty(ccFNOI.getContactFNOI().getEntityPerson().getGender())) {                
                    ep.setGender(factory.createContactEntityPersonGender(ccFNOI.getContactFNOI().getEntityPerson().getGender()));
                }
                if (NullChecker.isNotNullOrEmpty(ccFNOI.getContactFNOI().getEntityPerson().getMiddleName())) {                
                    ep.setMiddleName(factory.createContactEntityPersonMiddleName(ccFNOI.getContactFNOI().getEntityPerson().getMiddleName()));
                }
                if (NullChecker.isNotNullOrEmpty(ccFNOI.getContactFNOI().getEntityPerson().getOccupation())) {                
                    ep.setOccupation(factory.createContactEntityPersonOccupation(ccFNOI.getContactFNOI().getEntityPerson().getOccupation()));
                }
            }

            // Build the Contact
            if (NullChecker.isNotNullOrEmpty(ccFNOI.getContactFNOI().getEmailAddress1())) {
                c.setEmailAddress1(factory.createContactEmailAddress1(ccFNOI.getContactFNOI().getEmailAddress1()));
            }
            if (NullChecker.isNotNullOrEmpty(ccFNOI.getContactFNOI().getHomePhone())) {
                c.setHomePhone(factory.createContactHomePhone(ccFNOI.getContactFNOI().getHomePhone()));
            }
            if (NullChecker.isNotNullOrEmpty(ccFNOI.getContactFNOI().getName())) {
                c.setName(factory.createContactName(ccFNOI.getContactFNOI().getName()));
            }
            if (NullChecker.isNotNullOrEmpty(ccFNOI.getContactFNOI().getTaxID())) {
                c.setTaxID(factory.createContactTaxID(ccFNOI.getContactFNOI().getTaxID()));
            }
            if (NullChecker.isNotNullOrEmpty(ccFNOI.getContactFNOI().getWorkPhone())) {
                c.setWorkPhone(factory.createContactWorkPhone(ccFNOI.getContactFNOI().getWorkPhone()));
            }
            c.setEntityPerson(factory.createContactEntityPerson(ep));

            if (NullChecker.isNotNullOrEmpty(ccFNOI.getContactFNOI().getPrimaryAddress())) {
                c.setPrimaryAddress(factory.createContactPrimaryAddress(buildAddress(ccFNOI.getContactFNOI().getPrimaryAddress())));
            }

            // Build the ContractRoles
            for (ClaimContactRoleFNOI ccFNOIRole : ccFNOI.getRoles().getEntry()) {
                ClaimContactRole role = factory.createClaimContactRole();
                role.setRole(factory.createClaimContactRoleRole(ccFNOIRole.getRole()));
                ccRole.add(role);
            }

            // Build the ClaimContact
            cc.setContact(factory.createClaimContactContact(c));
            cc.setRoles(factory.createClaimContactRoles(roles));

            // Add to the List<ClaimContact> 
            claimContacts.add(cc);
        }
    }

    private Address buildAddress(AddressBO addressFNOI) {
        Address address = factory.createAddress();
        // Build the Address
        if (NullChecker.isNotNullOrEmpty(addressFNOI)) {
            if (NullChecker.isNotNullOrEmpty(addressFNOI.getAddressLine1())) {
                address.setAddressLine1(factory.createAddressAddressLine1(addressFNOI.getAddressLine1()));
            }
            if (NullChecker.isNotNullOrEmpty(addressFNOI.getAddressLine2())) {
                address.setAddressLine2(factory.createAddressAddressLine2(addressFNOI.getAddressLine2()));
            }
            if (NullChecker.isNotNullOrEmpty(addressFNOI.getCity())) {
                address.setCity(factory.createAddressCity(addressFNOI.getCity()));
            }
            if (NullChecker.isNotNullOrEmpty(addressFNOI.getCounty())) {
                address.setCounty(factory.createAddressCounty(addressFNOI.getCounty()));
            }
            if (NullChecker.isNotNullOrEmpty(addressFNOI.getPostalCode())) {
                address.setPostalCode(factory.createAddressPostalCode(addressFNOI.getPostalCode()));
            }
            if (NullChecker.isNotNullOrEmpty(addressFNOI.getState())) {
                address.setState(factory.createAddressState(addressFNOI.getState()));
            }
        }
        return address;
    }
}
