/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pwc.us.wsclient.quoterequest;

import com.pwc.us.common.exception.GwIntegrationException;
import com.pwc.us.common.model.AddressBO;
import com.pwc.us.common.model.ContactBO;
import com.pwc.us.common.model.quoterequest.AnswerQR;
import com.pwc.us.common.model.quoterequest.PolicyContactRoleQR;
import com.pwc.us.common.model.quoterequest.PolicyLineAnswerQR;
import com.pwc.us.common.model.quoterequest.PolicyLineQR;
import com.pwc.us.common.model.quoterequest.PolicyLocationQR;
import com.pwc.us.common.model.quoterequest.PolicyPeriodQR;
import com.pwc.us.common.model.quoterequest.PriorPolicyQR;
import com.pwc.us.common.model.quoterequest.WC7AddlInterestExtQR;
import com.pwc.us.ws.client.quoterequest.model.policyperiod.PolicyPeriod;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import org.joda.time.DateTime;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Roger
 */
public class PolicyPeriodBuilderTest {

    public static final int ROUNDING_SCALE = 5;

    public PolicyPeriodBuilderTest() {
    }

    /**
     * Test of createPolicyPeriodObject method, of class PolicyPeriodBuilder.
     */
    @Test
    public void testCreatePolicyPeriodObject() {
        System.out.println("createPolicyPeriodObject");
        PolicyPeriodQR qrPeriod = createTestPolicyPeriod();
        PolicyPeriodBuilder instance = new PolicyPeriodBuilder();
        PolicyPeriod result = null;
        try {
            result = instance.createPolicyPeriodObject(qrPeriod);
        } catch (GwIntegrationException e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
        assertNotNull(result);
    }

    /**
     * Test of marshall method, of class PolicyPeriodBuilder.
     */
    @Test
    public void testMarshall() throws Exception {
        System.out.println("marshall");
        PolicyPeriodQR qrPeriod = createTestPolicyPeriod();
        PolicyPeriodBuilder instance = new PolicyPeriodBuilder();
        PolicyPeriod policyPeriodObject = instance.createPolicyPeriodObject(qrPeriod);

        String result = instance.marshall(policyPeriodObject);
        assertNotNull(result);
        System.out.println(result);

    }

    private PolicyPeriodQR createTestPolicyPeriod() {
        PolicyPeriodQR pp = new PolicyPeriodQR();
        pp.setPeriodAnswers(createTestPeriodAnswers());
        pp.setPolicy(createPriorPolicies());
        pp.setPolicyLocations(createPolicyLocations());
        pp.setPrimaryNamedInsured(createPrimaryNamedInsured());

        pp.setWc7Line(createPolicyLine());

        return pp;
    }

    private PolicyPeriodQR.PeriodAnswers createTestPeriodAnswers() {
        PolicyPeriodQR.PeriodAnswers pas = new PolicyPeriodQR.PeriodAnswers();
        List<AnswerQR> answers = new ArrayList<AnswerQR>();
        AnswerQR answer1, answer2, answer3;
        answer1 = new AnswerQR();
        answer1.setQuestionCode("WC7PEOTemp");
        answer1.setBooleanAnswer(Boolean.FALSE);
        answers.add(answer1);

        answer2 = new AnswerQR();
        answer2.setQuestionCode("WC7OutsideOK");
        answer2.setBooleanAnswer(Boolean.FALSE);
        answers.add(answer2);

        answer3 = new AnswerQR();
        answer3.setQuestionCode("WC7Payroll");
        answer3.setIntegerAnswer(100000);
        answers.add(answer3);

        pas.setEntry(answers);

        return pas;
    }

    private PolicyPeriodQR.Policy createPriorPolicies() {
        PolicyPeriodQR.Policy pol = new PolicyPeriodQR.Policy();
        pol.setPriorPolicies(new PolicyPeriodQR.Policy.PriorPolicies());
        List<PriorPolicyQR> priorList = new ArrayList<PriorPolicyQR>();

        PriorPolicyQR prior1 = new PriorPolicyQR();
        prior1.setAnniversaryDateExt(new DateTime("2012-07-14T00:00:00.000-06:00").toDate());
        prior1.setCarrier("Maverick Insurance");
        prior1.setCarriersStateExt("OK");
        prior1.setEffectiveDate(new DateTime("2012-07-12T00:00:00-06:00").toDate());
        prior1.setExpirationDate(new DateTime("2013-07-13T00:00:00-06:00").toDate());
        prior1.setExpMod(new BigDecimal(0.5900).setScale(ROUNDING_SCALE, RoundingMode.HALF_UP));
        prior1.setExpModEffDateExt(new DateTime("2011-07-15T00:00:00-06:00").toDate());
        prior1.setPolicyNumber("123456789");
        prior1.setPolicyPeriodExt("December 2012");
        prior1.setRatingIdExt("23423423");

        PriorPolicyQR prior2 = new PriorPolicyQR();
        prior2.setAnniversaryDateExt(new DateTime("2010-07-14T00:00:00.000-06:00").toDate());
        prior2.setCarrier("Gunther Brawn Insurance");
        prior2.setCarriersStateExt("OK");
        prior2.setEffectiveDate(new DateTime("2010-07-12T00:00:00-06:00").toDate());
        prior2.setExpirationDate(new DateTime("2011-07-13T00:00:00-06:00").toDate());
        prior2.setExpMod(new BigDecimal(0.5900).setScale(ROUNDING_SCALE, RoundingMode.HALF_UP));
        prior2.setExpModEffDateExt(new DateTime("2009-07-15T00:00:00-06:00").toDate());
        prior2.setPolicyNumber("987654321");
        prior2.setPolicyPeriodExt("August 2010");
        prior2.setRatingIdExt("23423423");

        priorList.add(prior1);
        priorList.add(prior2);
        pol.getPriorPolicies().setEntry(priorList);

        return pol;
    }

    private PolicyPeriodQR.PolicyLocations createPolicyLocations() {
        PolicyPeriodQR.PolicyLocations locs = new PolicyPeriodQR.PolicyLocations();
        List<PolicyLocationQR> lst = new ArrayList<PolicyLocationQR>();

        PolicyLocationQR pl1 = new PolicyLocationQR();
        PolicyLocationQR.AccountLocation al1 = new PolicyLocationQR.AccountLocation();
        al1.setAddressLine1("3501 NW EXPRESSWAY");
        al1.setAddressLine2("Plaza 1");
        al1.setCity("Oklahoma City");
        al1.setCounty("Oklahoma County");
        al1.setLocationName("Spare Warehouse");
        al1.setPhone("683-848-9191");
        al1.setPostalCode("12341");
        al1.setState("OK");
        pl1.setAccountLocation(al1);
        lst.add(pl1);

        PolicyLocationQR pl2 = new PolicyLocationQR();
        PolicyLocationQR.AccountLocation al2 = new PolicyLocationQR.AccountLocation();
        al2.setAddressLine1("1568 Breezy Win Lane");
        al2.setAddressLine2("Suite 4048");
        al2.setCity("Oklahoma City");
        al2.setCounty("Oklahoma County");
        al2.setLocationName("Main Office");
        al2.setPhone("683-848-9191");
        al2.setPostalCode("12341");
        al2.setState("OK");
        pl2.setAccountLocation(al2);
        lst.add(pl2);

        locs.setEntry(lst);

        return locs;
    }

    private PolicyPeriodQR.PrimaryNamedInsured createPrimaryNamedInsured() {
        PolicyPeriodQR.PrimaryNamedInsured pni = new PolicyPeriodQR.PrimaryNamedInsured();
        pni.setOrgTypeExt("individual");
        return pni;
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
        line.setExcludedOwnerOfficers(createExcludedOwnerOfficers());
        line.setIncludedOwnerOfficers(createIncludedOwnerOfficers());
        return line;
    }

    private PolicyLineQR.EntityWC7WorkersCompLine.AdditionalInterests createAdditionalInterests() {
        PolicyLineQR.EntityWC7WorkersCompLine.AdditionalInterests addlInts = new PolicyLineQR.EntityWC7WorkersCompLine.AdditionalInterests();

        WC7AddlInterestExtQR entry = new WC7AddlInterestExtQR();
        entry.setRelationship("other");
        List<WC7AddlInterestExtQR> intList = new ArrayList<WC7AddlInterestExtQR>();
        entry.setAdditionalInterestType("CONTACT_Ext");
        PolicyContactRoleQR pcr = new PolicyContactRoleQR();
        pcr.setSubtype("PolicyAddlInterest");
        PolicyContactRoleQR.AccountContactRole acr = new PolicyContactRoleQR.AccountContactRole();
        acr.setAccountContact(createAccountContact1());
        acr.setSubtype("AdditionalInterest");
        pcr.setAccountContactRole(acr);
        entry.setPolicyAddlInterest(pcr);
        intList.add(entry);

        entry = new WC7AddlInterestExtQR();
        entry.setRelationship("other");
        entry.setAdditionalInterestType("CONTACT_Ext");
        acr = new PolicyContactRoleQR.AccountContactRole();
        pcr = new PolicyContactRoleQR();
        pcr.setSubtype("PolicyAddlInterest");
        acr.setAccountContact(createAccountContact2());
        pcr.setAccountContactRole(acr);
        acr.setSubtype("AdditionalInterest");
        entry.setPolicyAddlInterest(pcr);
        intList.add(entry);

        addlInts.setEntry(intList);

        return addlInts;
    }

    private PolicyLineQR.EntityWC7WorkersCompLine.ExcludedOwnerOfficers createExcludedOwnerOfficers() {
        PolicyLineQR.EntityWC7WorkersCompLine.ExcludedOwnerOfficers eos =
                new PolicyLineQR.EntityWC7WorkersCompLine.ExcludedOwnerOfficers();
        
        List<PolicyContactRoleQR> entries = new ArrayList<PolicyContactRoleQR>();
        PolicyContactRoleQR.EntityWC7PolicyOwnerOfficer officer = new PolicyContactRoleQR.EntityWC7PolicyOwnerOfficer();
        officer.setActiveInBusinessExt(Boolean.TRUE);
        officer.setRelationshipTitle("Dir");

        PolicyContactRoleQR pcr = new PolicyContactRoleQR();
        pcr.setSubtype("WC7ExcludedOwnerOfficer");
        PolicyContactRoleQR.AccountContactRole acr = new PolicyContactRoleQR.AccountContactRole();
        acr.setAccountContact(createAccountContact1());
        acr.setSubtype("OwnerOfficer");
        pcr.setAccountContactRole(acr);
        pcr.setEntityWC7PolicyOwnerOfficer(officer);
        entries.add(pcr);

        pcr = new PolicyContactRoleQR();
        pcr.setSubtype("WC7ExcludedOwnerOfficer");
        acr = new PolicyContactRoleQR.AccountContactRole();
        acr.setAccountContact(createAccountContact2());
        acr.setSubtype("OwnerOfficer");
        pcr.setAccountContactRole(acr);
        pcr.setEntityWC7PolicyOwnerOfficer(officer);
        entries.add(pcr);
        eos.setEntry(entries);

        return eos;
    }
    
    private PolicyLineQR.EntityWC7WorkersCompLine.IncludedOwnerOfficers createIncludedOwnerOfficers() {
        PolicyLineQR.EntityWC7WorkersCompLine.IncludedOwnerOfficers ios =
                new PolicyLineQR.EntityWC7WorkersCompLine.IncludedOwnerOfficers();
        


        List<PolicyContactRoleQR> entries = new ArrayList<PolicyContactRoleQR>();
        PolicyContactRoleQR.EntityWC7PolicyOwnerOfficer officer = new PolicyContactRoleQR.EntityWC7PolicyOwnerOfficer();
        officer.setActiveInBusinessExt(Boolean.TRUE);
        officer.setRelationshipTitle("Officer");

        PolicyContactRoleQR pcr = new PolicyContactRoleQR();
        pcr.setSubtype("WC7IncludedOwnerOfficer");
        PolicyContactRoleQR.AccountContactRole acr = new PolicyContactRoleQR.AccountContactRole();
        acr.setAccountContact(createAccountContact1());
        acr.setSubtype("OwnerOfficer");
        pcr.setAccountContactRole(acr);
        pcr.setEntityWC7PolicyOwnerOfficer(officer);
        entries.add(pcr);

        pcr = new PolicyContactRoleQR();
        pcr.setSubtype("WC7IncludedOwnerOfficer");
        acr = new PolicyContactRoleQR.AccountContactRole();
        acr.setAccountContact(createAccountContact2());
        acr.setSubtype("OwnerOfficer");
        pcr.setAccountContactRole(acr);
        pcr.setEntityWC7PolicyOwnerOfficer(officer);
        entries.add(pcr);
        ios.setEntry(entries);

        return ios;        
    }

    private PolicyContactRoleQR.AccountContactRole.AccountContact createAccountContact1() {
        PolicyContactRoleQR.AccountContactRole.AccountContact ac =
                new PolicyContactRoleQR.AccountContactRole.AccountContact();
        ContactBO contact = new ContactBO();
        contact.setEmailAddress1("mss@guidewire.com");
        contact.setFaxPhone("124-567-1111");
        contact.setPrimaryAddress(createPrimaryAddress1());
        contact.setEntityPerson(createEntityPerson1());
        contact.setSubType("Person");
        contact.setTaxID("111-11-1111");
        contact.setWorkPhone("555-555-5555");
        ac.setContact(contact);

        return ac;
    }

    private PolicyContactRoleQR.AccountContactRole.AccountContact createAccountContact2() {
        PolicyContactRoleQR.AccountContactRole.AccountContact ac =
                new PolicyContactRoleQR.AccountContactRole.AccountContact();
        ContactBO contact = new ContactBO();
        contact.setEmailAddress1("email@email.com");
        contact.setFaxPhone("111-111-1111");
        contact.setPrimaryAddress(createPrimaryAddress2());
        contact.setEntityPerson(createEntityPerson2());
        contact.setSubType("Person");
        contact.setTaxID("111-11-1111");
        contact.setWorkPhone("555-555-5555");
        ac.setContact(contact);

        return ac;
    }

    private AddressBO createPrimaryAddress1() {
        AddressBO address = new AddressBO();
        address.setAddressLine1("100 East Reno Avenue");
        address.setAddressLine2("Floor 5, Suite 132");
        address.setCity("Oklahoma City");
        address.setCounty("Pottawatomie");
        address.setDescription("Created by Portal");
        address.setPostalCode("73104");
        address.setState("OK");

        return address;
    }

    private AddressBO createPrimaryAddress2() {
        AddressBO address = new AddressBO();
        address.setAddressLine1("444 Primrose Path");
        address.setAddressLine2("Suite Tooth");
        address.setCity("Oklahoma City");
        address.setCounty("Comanche");
        address.setDescription("Created by Portal");
        address.setPostalCode("73104");
        address.setState("OK");

        return address;
    }

    private ContactBO.EntityPerson createEntityPerson1() {
        ContactBO.EntityPerson ep = new ContactBO.EntityPerson();
        ep.setFirstName("Bob");
        ep.setMiddleName("T.");
        ep.setLastName("Loblaw");

        return ep;
    }

    private ContactBO.EntityPerson createEntityPerson2() {
        ContactBO.EntityPerson ep = new ContactBO.EntityPerson();
        ep.setFirstName("Molly");
        ep.setMiddleName("T.");
        ep.setLastName("Loosetooth");

        return ep;
    }
    
    private PolicyLineQR.LineAnswers createLineAnswers() {
        PolicyLineQR.LineAnswers answers = new PolicyLineQR.LineAnswers();
        List<PolicyLineAnswerQR> entries = new ArrayList<PolicyLineAnswerQR>();
        entries.add(createPolicyLineAnswer1());
        entries.add(createPolicyLineAnswer2());
        entries.add(createPolicyLineAnswer3());
        answers.setEntry(entries);
        return answers;
    }
    
    private PolicyLineAnswerQR createPolicyLineAnswer1() {
        PolicyLineAnswerQR lineAnswer = new PolicyLineAnswerQR("WC7AgeRange", false);
        return lineAnswer;
    }
    
    private PolicyLineAnswerQR createPolicyLineAnswer2() {
        PolicyLineAnswerQR lineAnswer = new PolicyLineAnswerQR("WC7Liquidation", false);
        return lineAnswer;
    }
    
    private PolicyLineAnswerQR createPolicyLineAnswer3() {
        PolicyLineAnswerQR lineAnswer = new PolicyLineAnswerQR("WC7DomesticEmployees", false);
        return lineAnswer;
    }
}