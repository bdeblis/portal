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
import com.pwc.us.common.utils.DateTimeHelper;
import com.pwc.us.common.utils.NullChecker;
import com.pwc.us.webservice.tools.CDataXMLStreamWriter;
import com.pwc.us.ws.client.quoterequest.model.policyperiod.Address;
import com.pwc.us.ws.client.quoterequest.model.policyperiod.Contact;
import com.pwc.us.ws.client.quoterequest.model.policyperiod.Contact.EntityPerson;
import com.pwc.us.ws.client.quoterequest.model.policyperiod.ObjectFactory;
import com.pwc.us.ws.client.quoterequest.model.policyperiod.PeriodAnswer;
import com.pwc.us.ws.client.quoterequest.model.policyperiod.PolicyContactRole;
import com.pwc.us.ws.client.quoterequest.model.policyperiod.PolicyLine;
import com.pwc.us.ws.client.quoterequest.model.policyperiod.PolicyLineAnswer;
import com.pwc.us.ws.client.quoterequest.model.policyperiod.PolicyLocation;
import com.pwc.us.ws.client.quoterequest.model.policyperiod.PolicyLocation.AccountLocation;
import com.pwc.us.ws.client.quoterequest.model.policyperiod.PolicyPeriod;
import com.pwc.us.ws.client.quoterequest.model.policyperiod.PolicyPeriod.PeriodAnswers;
import com.pwc.us.ws.client.quoterequest.model.policyperiod.PolicyPeriod.Policy;
import com.pwc.us.ws.client.quoterequest.model.policyperiod.PolicyPeriod.Policy.PriorPolicies;
import com.pwc.us.ws.client.quoterequest.model.policyperiod.PriorPolicy;
import com.pwc.us.ws.client.quoterequest.model.policyperiod.WC7AddlInterestExt;
import java.io.StringWriter;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.DatatypeConfigurationException;
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
public class PolicyPeriodBuilder {

    private static final Logger logger = LoggerFactory.getLogger(PolicyPeriodBuilder.class);
    private ObjectFactory factory = new ObjectFactory();

//<PolicyPeriod>
//    <PeriodAnswers>
//           ... 
//    </PeriodAnswers>  
//    <Policy>    
//           ...   
//    </Policy> 
//    <PolicyLocations>
//           ... 
//    </PolicyLocations>
//    <PrimaryNamedInsured>
//           ... 
//    </PrimaryNamedInsured>
//    <WC7Line>    
//           ... 
//    </WC7Line>
//    <IsNewFromPortal>
//          ...
//    </IsNewFromPortal>
//</PolicyPeriod>
    protected PolicyPeriod createPolicyPeriodObject(PolicyPeriodQR qrPeriod)
            throws GwIntegrationException {
        PolicyPeriod pperiod = factory.createPolicyPeriod();
        if (NullChecker.isNotNullOrEmpty(qrPeriod)) {

            if (NullChecker.isNotNullOrEmpty(qrPeriod.getPeriodAnswers())
                    && NullChecker.isNotNullOrEmpty(qrPeriod.getPeriodAnswers().getEntry())) {
                pperiod.setPeriodAnswers(factory.createPolicyPeriodPeriodAnswers(createPeriodAnswers(qrPeriod)));
            }

            try {
                if (NullChecker.isNotNullOrEmpty(qrPeriod.getPolicy())
                        && NullChecker.isNotNullOrEmpty(qrPeriod.getPolicy().getPriorPolicies())
                        && NullChecker.isNotNullOrEmpty(qrPeriod.getPolicy().getPriorPolicies().getEntry())) {
                    pperiod.setPolicy(factory.createPolicyPeriodPolicy(createPriorPolicies(qrPeriod)));
                }
            } catch (DatatypeConfigurationException e) {
                String message = "Unable to create dates for prior policies";
                throw new GwIntegrationException(message, e);
            } catch (ParseException e) {
                String message = "Unable to create dates for prior policies";
                throw new GwIntegrationException(message, e);                
            }

            if (NullChecker.isNotNullOrEmpty(qrPeriod.getPolicyLocations())
                    && NullChecker.isNotNullOrEmpty(qrPeriod.getPolicyLocations().getEntry())) {
                pperiod.setPolicyLocations(factory.createPolicyPeriodPolicyLocations(createPolicyLocations(qrPeriod)));
            }

            if (NullChecker.isNotNullOrEmpty(qrPeriod.getPrimaryNamedInsured())
                    && NullChecker.isNotNullOrEmpty(qrPeriod.getPrimaryNamedInsured().getOrgTypeExt())) {
                pperiod.setPrimaryNamedInsured(factory.createPolicyPeriodPrimaryNamedInsured(createPrimaryNamedInsured(qrPeriod)));
            }

            if (NullChecker.isNotNullOrEmpty(qrPeriod.getWc7Line())) {
                pperiod.setWC7Line(factory.createPolicyPeriodWC7Line(createWc7Line(qrPeriod)));
            }
            
            if (NullChecker.isNotNullOrEmpty(qrPeriod.getIsNewFromPortal())) {
               pperiod.setIsNewFromPortalExt(factory.createPolicyPeriodIsNewFromPortalExt(qrPeriod.getIsNewFromPortal()));                
            }
        }
        return pperiod;
    }

//    <PeriodAnswers>
//        <Entry>
//            <ns0:BooleanAnswer>false</ns0:BooleanAnswer>
//            <ns0:QuestionCode>WC7PEOTemp</ns0:QuestionCode>
//        </Entry>
//        <Entry>
//            <ns0:BooleanAnswer>false</ns0:BooleanAnswer>
//            <ns0:QuestionCode>WC7OutsideOK</ns0:QuestionCode>
//        </Entry>
//        <Entry>
//            <ns0:IntegerAnswer>1000000</ns0:IntegerAnswer>
//            <ns0:QuestionCode>WC7Payroll</ns0:QuestionCode>
//        </Entry>
//    </PeriodAnswers>  
    private PeriodAnswers createPeriodAnswers(PolicyPeriodQR qrPeriod) {
        PeriodAnswers answers = factory.createPolicyPeriodPeriodAnswers();
        List<PeriodAnswer> entries = answers.getEntry();
        for (AnswerQR answer : qrPeriod.getPeriodAnswers().getEntry()) {
            PeriodAnswer a = factory.createPeriodAnswer();
            a.setQuestionCode(factory.createPeriodAnswerQuestionCode(answer.getQuestionCode()));
            if (NullChecker.isNotNullOrEmpty(answer.getIntegerAnswer())) {
                a.setIntegerAnswer(factory.createPeriodAnswerIntegerAnswer(answer.getIntegerAnswer()));
            }
            if (NullChecker.isNotNullOrEmpty(answer.getBooleanAnswer())) {
                a.setBooleanAnswer(factory.createPeriodAnswerBooleanAnswer(answer.getBooleanAnswer()));
            }

            entries.add(a);
        }
        return answers;
    }

    //    <Policy>    
//        <PriorPolicies>      
//            <Entry>        
//                <ns1:AnniversaryDate_Ext>2012-07-14T00:00:00+01:00</ns1:AnniversaryDate_Ext>        
//                <ns1:Carrier>Maverick Insurance</ns1:Carrier>        
//                <ns1:CarriersState_Ext>OK</ns1:CarriersState_Ext>        
//                <ns1:EffectiveDate>2012-07-12T00:00:00+01:00</ns1:EffectiveDate>        
//                <ns1:ExpirationDate>2012-07-13T00:00:00+01:00</ns1:ExpirationDate>  
//                <ns1:ExpMod>0.5900</ns1:ExpMod> 
//                <ns1:ExpModEffDate_Ext>2011-07-15T00:00:00+01:00</ns1:ExpModEffDate_Ext>        
//                <ns1:PolicyNumber>123456789</ns1:PolicyNumber>  
//                <ns1:PolicyPeriod_Ext>December 2012</ns1:PolicyPeriod_Ext>      
//            </Entry>    
//        </PriorPolicies>  
//    </Policy> 
    private PolicyPeriod.Policy createPriorPolicies(PolicyPeriodQR qrPeriod) throws DatatypeConfigurationException, ParseException {
        Policy policy = factory.createPolicyPeriodPolicy();
        PriorPolicies priors = factory.createPolicyPeriodPolicyPriorPolicies();
        List<PriorPolicy> entries = priors.getEntry();

        for (PriorPolicyQR qrpp : qrPeriod.getPolicy().getPriorPolicies().getEntry()) {
            PriorPolicy pp = factory.createPriorPolicy();
            XMLGregorianCalendar xmlCal = null;
            if (NullChecker.isNotNullOrEmpty(qrpp.getAnniversaryDateExt())) {
                xmlCal = DateTimeHelper.dateToXMLGregorianCalendar(qrpp.getAnniversaryDateExt());
                pp.setAnniversaryDateExt(factory.createPriorPolicyAnniversaryDateExt(xmlCal));
            }
            if (NullChecker.isNotNullOrEmpty(qrpp.getCarrier())) {
                pp.setCarrier(factory.createPriorPolicyCarrier(qrpp.getCarrier()));
            }
            if (NullChecker.isNotNullOrEmpty(qrpp.getCarriersStateExt())) {
                pp.setCarriersStateExt(factory.createPriorPolicyCarriersStateExt(qrpp.getCarriersStateExt()));
            }
            if (NullChecker.isNotNullOrEmpty(qrpp.getEffectiveDate())) {
                xmlCal = DateTimeHelper.dateToXMLGregorianCalendar(qrpp.getEffectiveDate());
                pp.setEffectiveDate(factory.createPriorPolicyEffectiveDate(xmlCal));
            } else {
                xmlCal = DateTimeHelper.dateToXMLGregorianCalendar(new Date());
                pp.setEffectiveDate(factory.createPriorPolicyEffectiveDate(xmlCal));
            }
            if (NullChecker.isNotNullOrEmpty(qrpp.getExpirationDate())) {
                xmlCal = DateTimeHelper.dateToXMLGregorianCalendar(qrpp.getExpirationDate());
                pp.setExpirationDate(factory.createPriorPolicyExpirationDate(xmlCal));
            }
            if (NullChecker.isNotNullOrEmpty(qrpp.getExpMod())) {
                pp.setExpMod(factory.createPriorPolicyExpMod(qrpp.getExpMod()));
            }
            if (NullChecker.isNotNullOrEmpty(qrpp.getExpModEffDateExt())) {
                xmlCal = DateTimeHelper.dateToXMLGregorianCalendar(qrpp.getExpModEffDateExt());
                pp.setExpModEffDateExt(factory.createPriorPolicyExpModEffDateExt(xmlCal));
            }
            if (NullChecker.isNotNullOrEmpty(qrpp.getPolicyNumber())) {
                pp.setPolicyNumber(factory.createPriorPolicyPolicyNumber(qrpp.getPolicyNumber()));
            }
            if (NullChecker.isNotNullOrEmpty(qrpp.getPolicyPeriodExt())) {
                pp.setPolicyPeriodExt(factory.createPriorPolicyPolicyPeriodExt(qrpp.getPolicyPeriodExt()));
            }
            if (NullChecker.isNotNullOrEmpty(qrpp.getRatingIdExt())) {
                pp.setRatingIdExt(factory.createPriorPolicyRatingIdExt(qrpp.getRatingIdExt()));
            }
            entries.add(pp);
        }
        policy.setPriorPolicies(factory.createPolicyPeriodPolicyPriorPolicies(priors));

        return policy;
    }

//    <PolicyLocations>
//        <Entry>
//            <ns8:AccountLocation>
//                <ns8:AddressLine1>3501 NW EXPRESSWAY</ns8:AddressLine1>
//                <ns8:AddressLine2>Plaza 1</ns8:AddressLine2>
//                <ns8:City>Oklahoma City</ns8:City>
//                <ns8:County>Oklahoma County</ns8:County>
//                <ns8:LocationName>Spare Warehouse</ns8:LocationName>
//                <ns8:Phone>683-848-9191</ns8:Phone>
//                <ns8:PostalCode>12341</ns8:PostalCode>
//                <ns8:State>OK</ns8:State>
//            </ns8:AccountLocation>
//        </Entry>
//        <Entry>
//            <ns8:AccountLocation>
//                <ns8:AddressLine1>3501 NW EXPRESSWAY</ns8:AddressLine1>
//                <ns8:AddressLine2>Plaza 1</ns8:AddressLine2>
//                <ns8:City>Oklahoma City</ns8:City>
//                <ns8:County>Oklahoma County</ns8:County>
//                <ns8:LocationName>Spare Warehouse</ns8:LocationName>
//                <ns8:Phone>683-848-9191</ns8:Phone>
//                <ns8:PostalCode>12341</ns8:PostalCode>
//                <ns8:State>OK</ns8:State>
//            </ns8:AccountLocation>
//        </Entry>
//    </PolicyLocations>
    private PolicyPeriod.PolicyLocations createPolicyLocations(PolicyPeriodQR qrPeriod) {
        PolicyPeriodQR.PolicyLocations plqr = qrPeriod.getPolicyLocations();
        List<PolicyLocationQR> locations = plqr.getEntry();

        PolicyPeriod.PolicyLocations ret = factory.createPolicyPeriodPolicyLocations();

        for (PolicyLocationQR location : locations) {
            PolicyLocationQR.AccountLocation alqr = location.getAccountLocation();
            if (NullChecker.isNotNullOrEmpty(alqr)) {
                PolicyLocation l = factory.createPolicyLocation();
                AccountLocation al = factory.createPolicyLocationAccountLocation();
                if (NullChecker.isNotNullOrEmpty(alqr.getAddressLine1())) {
                    al.setAddressLine1(factory.createPolicyLocationAccountLocationAddressLine1(alqr.getAddressLine1()));
                }
                if (NullChecker.isNotNullOrEmpty(alqr.getAddressLine2())) {
                    al.setAddressLine2(factory.createPolicyLocationAccountLocationAddressLine2(alqr.getAddressLine2()));
                }
                if (NullChecker.isNotNullOrEmpty(alqr.getCity())) {
                    al.setCity(factory.createPolicyLocationAccountLocationCity(alqr.getCity()));
                }
                if (NullChecker.isNotNullOrEmpty(alqr.getCounty())) {
                    al.setCounty(factory.createPolicyLocationAccountLocationCounty(alqr.getCounty()));
                }
                if (NullChecker.isNotNullOrEmpty(alqr.getLocationName())) {
                    al.setLocationName(factory.createPolicyLocationAccountLocationLocationName(alqr.getLocationName()));
                }
                if (NullChecker.isNotNullOrEmpty(alqr.getPhone())) {
                    al.setPhone(factory.createPolicyLocationAccountLocationPhone(alqr.getPhone()));
                }
                if (NullChecker.isNotNullOrEmpty(alqr.getPostalCode())) {
                    al.setPostalCode(factory.createPolicyLocationAccountLocationPostalCode(alqr.getPostalCode()));
                }
                if (NullChecker.isNotNullOrEmpty(alqr.getState())) {
                    al.setState(factory.createPolicyLocationAccountLocationState(alqr.getState()));
                }
                l.setAccountLocation(factory.createPolicyLocationAccountLocation(al));
                ret.getEntry().add(l);
            }
        }

        return ret;
    }

//    <PrimaryNamedInsured>
//        <OrgType_Ext>individual</OrgType_Ext>
//    </PrimaryNamedInsured>
    private PolicyPeriod.PrimaryNamedInsured createPrimaryNamedInsured(PolicyPeriodQR qrPeriod) {
        PolicyPeriod.PrimaryNamedInsured pni = factory.createPolicyPeriodPrimaryNamedInsured();
        if (NullChecker.isNotNullOrEmpty(qrPeriod.getPrimaryNamedInsured())
                && NullChecker.isNotNullOrEmpty(qrPeriod.getPrimaryNamedInsured().getOrgTypeExt())) {
            pni.setOrgTypeExt(factory.createPolicyPeriodPrimaryNamedInsuredOrgTypeExt(qrPeriod.getPrimaryNamedInsured().getOrgTypeExt()));
        }

        return pni;
    }

//    <WC7Line>    
//        <ns2:entity-WC7WorkersCompLine>      
//           ...   
//        </ns2:entity-WC7WorkersCompLine>    
//        <ns2:LineAnswers>      
//           ... 
//        </ns2:LineAnswers>
//    </WC7Line>
    private PolicyLine createWc7Line(PolicyPeriodQR qrPeriod) {
        PolicyLine pline = factory.createPolicyLine();
        PolicyLineQR wc7Qr = null;
        PolicyLineQR.EntityWC7WorkersCompLine lineCompQR = null;
        PolicyLineQR.LineAnswers lineAnswersQR = null;
        if (NullChecker.isNotNullOrEmpty(qrPeriod.getWc7Line())) {
            wc7Qr = qrPeriod.getWc7Line();
            if (NullChecker.isNotNullOrEmpty(wc7Qr.getEntityWC7WorkersCompLine())) {
                lineCompQR = wc7Qr.getEntityWC7WorkersCompLine();
                pline.setEntityWC7WorkersCompLine(factory.createPolicyLineEntityWC7WorkersCompLine(createEntityWC7WorkersCompLine(lineCompQR)));
            }

            if (NullChecker.isNotNullOrEmpty(wc7Qr.getLineAnswers())) {
                lineAnswersQR = wc7Qr.getLineAnswers();
                pline.setLineAnswers(factory.createPolicyLineLineAnswers(createLineAnswers(lineAnswersQR)));
                // create line answers
            }
        }
        return pline;
    }   

//        <ns2:entity-WC7WorkersCompLine>      
//            <ns2:AdditionalInterests>        
//                 ...
//            </ns2:AdditionalInterests>      
//            <ns2:ExcludedOwnerOfficers>        
//                  ...
//            </ns2:ExcludedOwnerOfficers>      
//            <ns2:IncludedOwnerOfficers>        
//                  ...   
//            </ns2:IncludedOwnerOfficers>    
//        </ns2:entity-WC7WorkersCompLine>    
    private PolicyLine.EntityWC7WorkersCompLine createEntityWC7WorkersCompLine(PolicyLineQR.EntityWC7WorkersCompLine lineCompQR) {
        PolicyLine.EntityWC7WorkersCompLine entWc7Line = factory.createPolicyLineEntityWC7WorkersCompLine();

        if (NullChecker.isNotNullOrEmpty(lineCompQR.getAdditionalInterests())) {
            entWc7Line.setAdditionalInterests(factory.createPolicyLineEntityWC7WorkersCompLineAdditionalInterests(createAdditionalInterests(lineCompQR)));
        }
        if (NullChecker.isNotNullOrEmpty(lineCompQR.getExcludedOwnerOfficers())) {
            entWc7Line.setExcludedOwnerOfficers(factory.createPolicyLineEntityWC7WorkersCompLineExcludedOwnerOfficers(createExcludedOwnerOfficers(lineCompQR)));
        }
        if (NullChecker.isNotNullOrEmpty(lineCompQR.getIncludedOwnerOfficers())) {
            entWc7Line.setIncludedOwnerOfficers(factory.createPolicyLineEntityWC7WorkersCompLineIncludedOwnerOfficers(createIncludedOwnerOfficers(lineCompQR)));
        }

        return entWc7Line;
    }

//            <ns2:AdditionalInterests>        
//                <ns2:Entry>          
//                    <ns3:AdditionalInterestType>CONTACT_Ext</ns3:AdditionalInterestType>    
//                    <ns3:PolicyAddlInterest>      
//                         ...          
//                    </ns3:PolicyAddlInterest>          
//                    <ns3:Relationship>other</ns3:Relationship>        
//                </ns2:Entry>      
//            </ns2:AdditionalInterests>   
    private PolicyLine.EntityWC7WorkersCompLine.AdditionalInterests createAdditionalInterests(PolicyLineQR.EntityWC7WorkersCompLine lineCompQR) {
        PolicyLine.EntityWC7WorkersCompLine.AdditionalInterests addlInt =
                factory.createPolicyLineEntityWC7WorkersCompLineAdditionalInterests();

        PolicyLineQR.EntityWC7WorkersCompLine.AdditionalInterests aiqr = null;
        if (NullChecker.isNotNullOrEmpty(lineCompQR.getAdditionalInterests())) {
            aiqr = lineCompQR.getAdditionalInterests();
            List<WC7AddlInterestExtQR> entries = aiqr.getEntry();
            for (WC7AddlInterestExtQR entry : entries) {
                WC7AddlInterestExt intExt = factory.createWC7AddlInterestExt();
                if (NullChecker.isNotNullOrEmpty(entry.getAdditionalInterestType())) {
                    intExt.setAdditionalInterestType(factory.createWC7AddlInterestExtAdditionalInterestType(entry.getAdditionalInterestType()));
                }
                
                intExt.setPolicyAddlInterest(factory.createWC7AddlInterestExtPolicyAddlInterest(createPolicyContactRole(entry.getPolicyAddlInterest())));
                
                if (NullChecker.isNotNullOrEmpty(entry.getRelationship())) {
                    intExt.setRelationship(factory.createWC7AddlInterestExtRelationship(entry.getRelationship()));
                }

                addlInt.getEntry().add(intExt);
            }
        }
        return addlInt;
    }
    
    private PolicyLine.EntityWC7WorkersCompLine.ExcludedOwnerOfficers createExcludedOwnerOfficers(PolicyLineQR.EntityWC7WorkersCompLine lineCompQR) {
        PolicyLine.EntityWC7WorkersCompLine.ExcludedOwnerOfficers excludedOfficers = 
                factory.createPolicyLineEntityWC7WorkersCompLineExcludedOwnerOfficers();
        
        PolicyLineQR.EntityWC7WorkersCompLine.ExcludedOwnerOfficers eoqr = null;
        if (NullChecker.isNotNullOrEmpty(lineCompQR.getExcludedOwnerOfficers())) {
            eoqr = lineCompQR.getExcludedOwnerOfficers();
            List<PolicyContactRoleQR> entries = eoqr.getEntry();
            for (PolicyContactRoleQR entry : entries) {
                PolicyContactRole pcr = createPolicyContactRole(entry);
                excludedOfficers.getEntry().add(pcr);
            }
        }
        return excludedOfficers;
    }
    
    private PolicyLine.EntityWC7WorkersCompLine.IncludedOwnerOfficers createIncludedOwnerOfficers(PolicyLineQR.EntityWC7WorkersCompLine lineCompQR) {
        PolicyLine.EntityWC7WorkersCompLine.IncludedOwnerOfficers includedOfficers = 
                factory.createPolicyLineEntityWC7WorkersCompLineIncludedOwnerOfficers();
        
        PolicyLineQR.EntityWC7WorkersCompLine.IncludedOwnerOfficers ioqr = null;
        if (NullChecker.isNotNullOrEmpty(lineCompQR.getIncludedOwnerOfficers())) {
            ioqr = lineCompQR.getIncludedOwnerOfficers();
            List<PolicyContactRoleQR> entries = ioqr.getEntry();
            for (PolicyContactRoleQR entry : entries) {
                PolicyContactRole pcr = createPolicyContactRole(entry);
                includedOfficers.getEntry().add(pcr);
            }
        }
        return includedOfficers;
    }
    
    
    
    


//        <ns3:PolicyAddlInterest>      
//            <ns4:AccountContactRole>        
//               ...          
//            </ns4:AccountContactRole>            
//            <ns4:Subtype>PolicyAddlInterest</ns4:Subtype>          
//        </ns3:PolicyAddlInterest>       
    private PolicyContactRole createPolicyContactRole(PolicyContactRoleQR pcrqr) {
        PolicyContactRole pcr = factory.createPolicyContactRole();
        PolicyContactRoleQR.AccountContactRole acrqr = pcrqr.getAccountContactRole();
        PolicyContactRoleQR.EntityWC7PolicyOwnerOfficer eoqr = pcrqr.getEntityWC7PolicyOwnerOfficer();
        
        
        if (NullChecker.isNotNullOrEmpty(acrqr)) {
            pcr.setAccountContactRole(factory.createPolicyContactRoleAccountContactRole(createAccountContactRole(acrqr)));
        }
        if (NullChecker.isNotNullOrEmpty(eoqr)) {
            PolicyContactRole.EntityWC7PolicyOwnerOfficer pwner = createWc7PolicyOwnerOfficer(eoqr);
            pcr.setEntityWC7PolicyOwnerOfficer(factory.createPolicyContactRoleEntityWC7PolicyOwnerOfficer(pwner));
        }
        pcr.setSubtype(factory.createPolicyContactRoleSubtype(pcrqr.getSubtype()));
        return pcr;
    }
    
//    <ns4:entity-WC7PolicyOwnerOfficer>            
//        <ns4:ActiveInBusiness_Ext>true</ns4:ActiveInBusiness_Ext>            
//        <ns4:RelationshipTitle>Dir</ns4:RelationshipTitle>          
//    </ns4:entity-WC7PolicyOwnerOfficer>   
    private PolicyContactRole.EntityWC7PolicyOwnerOfficer createWc7PolicyOwnerOfficer(PolicyContactRoleQR.EntityWC7PolicyOwnerOfficer entityQr) {
        PolicyContactRole.EntityWC7PolicyOwnerOfficer off = factory.createPolicyContactRoleEntityWC7PolicyOwnerOfficer();
        off.setActiveInBusinessExt(factory.createPolicyContactRoleEntityWC7PolicyOwnerOfficerActiveInBusinessExt(entityQr.getActiveInBusinessExt()));
        off.setRelationshipTitle(factory.createPolicyContactRoleEntityWC7PolicyOwnerOfficerRelationshipTitle(entityQr.getRelationshipTitle()));
        return off;
    }

//    <ns4:AccountContactRole>            
//        <ns4:AccountContact>              
//            <ns4:Contact>                
//               ...             
//            </ns4:Contact>            
//        </ns4:AccountContact>            
//        <ns4:Subtype>OwnerOfficer</ns4:Subtype>          
//    </ns4:AccountContactRole>  
    private PolicyContactRole.AccountContactRole createAccountContactRole(PolicyContactRoleQR.AccountContactRole acrqr) {
        PolicyContactRole.AccountContactRole acr = factory.createPolicyContactRoleAccountContactRole();
        if (NullChecker.isNotNullOrEmpty(acrqr.getAccountContact())) {
            PolicyContactRoleQR.AccountContactRole.AccountContact acqr = acrqr.getAccountContact();
            acr.setAccountContact(factory.createPolicyContactRoleAccountContactRoleAccountContact(createAccountContact(acqr)));
        }
        if (NullChecker.isNotNullOrEmpty(acrqr.getSubtype())) {
            acr.setSubtype(factory.createPolicyContactRoleAccountContactRoleSubtype(acrqr.getSubtype()));
        }
        return acr;
    }
    
//        <ns4:AccountContact>                
//             ...             
//        </ns4:AccountContact>
    private PolicyContactRole.AccountContactRole.AccountContact createAccountContact(PolicyContactRoleQR.AccountContactRole.AccountContact acqr) {
        PolicyContactRole.AccountContactRole.AccountContact ac = factory.createPolicyContactRoleAccountContactRoleAccountContact();
        if (NullChecker.isNotNullOrEmpty(acqr.getContact())) {
            ac.setContact(factory.createPolicyContactRoleAccountContactRoleAccountContactContact(createContact(acqr.getContact())));
        }
        return ac;
    }
    
//        <ns4:Contact>     
//            <ns5:entity-Person>                    
//                 ...               
//            </ns5:entity-Person>                  
//            <ns5:EmailAddress1>mss@guidewire.com</ns5:EmailAddress1>                  
//            <ns5:FaxPhone>124-567-1111</ns5:FaxPhone>                  
//            <ns5:PrimaryAddress>                    
//                 ...              
//            </ns5:PrimaryAddress>                  
//            <ns5:Subtype>Person</ns5:Subtype>                  
//            <ns5:TaxID>114-61-1880</ns5:TaxID>                  
//            <ns5:WorkPhone>124-567-0081</ns5:WorkPhone>                
//        </ns4:Contact> 
    private Contact createContact(ContactBO contactQr) {
        Contact contact = factory.createContact();
        if (NullChecker.isNotNullOrEmpty(contactQr.getEntityPerson())) {
            contact.setEntityPerson(factory.createContactEntityPerson(createEntityPerson(contactQr.getEntityPerson())));
        }
        if (NullChecker.isNotNullOrEmpty(contactQr.getEmailAddress1())) {
            contact.setEmailAddress1(factory.createContactEmailAddress1(contactQr.getEmailAddress1()));
        }
        if (NullChecker.isNotNullOrEmpty(contactQr.getFaxPhone())) {
            contact.setFaxPhone(factory.createContactFaxPhone(contactQr.getFaxPhone()));
        }
        if (NullChecker.isNotNullOrEmpty(contactQr.getPrimaryAddress())) {
            contact.setPrimaryAddress(factory.createContactPrimaryAddress(createPrimaryAddress(contactQr.getPrimaryAddress())));
        }
        if (NullChecker.isNotNullOrEmpty(contactQr.getSubType())) {
            contact.setSubtype(factory.createContactSubtype(contactQr.getSubType()));
        }
        if (NullChecker.isNotNullOrEmpty(contactQr.getTaxID())) {
            contact.setTaxID(factory.createContactTaxID(contactQr.getTaxID()));
        }
        if (NullChecker.isNotNullOrEmpty(contactQr.getWorkPhone())) {
            contact.setWorkPhone(factory.createContactWorkPhone(contactQr.getWorkPhone()));
        }
        
        return contact;
    }
    
//        <ns5:entity-Person>                    
//            <ns5:FirstName>Mike</ns5:FirstName>                    
//            <ns5:LastName>Sharp</ns5:LastName>                    
//            <ns5:MiddleName>S</ns5:MiddleName>                  
//        </ns5:entity-Person>   
    
    private EntityPerson createEntityPerson(ContactBO.EntityPerson epqr) {
        EntityPerson ep = factory.createContactEntityPerson();
        if (NullChecker.isNotNullOrEmpty(epqr.getFirstName())) {
            ep.setFirstName(factory.createContactEntityPersonFirstName(epqr.getFirstName()));
            ep.setMiddleName(factory.createContactEntityPersonMiddleName(epqr.getMiddleName()));
            ep.setLastName(factory.createContactEntityPersonLastName(epqr.getLastName()));
        }
        
        return ep;
    }
    
//    <ns5:PrimaryAddress>                    
//        <ns6:AddressLine1>100 East Reno Avenue</ns6:AddressLine1>                    
//        <ns6:AddressLine2>Floor 5, Suite 132</ns6:AddressLine2>                    
//        <ns6:City>Oklahoma City</ns6:City>                    
//        <ns6:County>San Mateo</ns6:County>                    
//        <ns6:Description>Created by Portal</ns6:Description>                    
//        <ns6:PostalCode>73104</ns6:PostalCode>                    
//        <ns6:State>OK</ns6:State>                  
//    </ns5:PrimaryAddress>  
    private Address createPrimaryAddress(AddressBO addrQr) {
        Address addr = factory.createAddress();
        if (NullChecker.isNotNullOrEmpty(addrQr.getAddressLine1())) {
            addr.setAddressLine1(factory.createAddressAddressLine1(addrQr.getAddressLine1()));
        }
        if (NullChecker.isNotNullOrEmpty(addrQr.getAddressLine2())) {
            addr.setAddressLine2(factory.createAddressAddressLine2(addrQr.getAddressLine2()));
        }
        if (NullChecker.isNotNullOrEmpty(addrQr.getCity())) {
            addr.setCity(factory.createAddressCity(addrQr.getCity()));
        }
        if (NullChecker.isNotNullOrEmpty(addrQr.getCounty())) {
            addr.setCounty(factory.createAddressCounty(addrQr.getCounty()));
        }
        if (NullChecker.isNotNullOrEmpty(addrQr.getDescription())) {
            addr.setDescription(factory.createAddressDescription(addrQr.getDescription()));
        }
        if (NullChecker.isNotNullOrEmpty(addrQr.getPostalCode())) {
            addr.setPostalCode(factory.createAddressPostalCode(addrQr.getPostalCode()));
        }
        if (NullChecker.isNotNullOrEmpty(addrQr.getState())) {
            addr.setState(factory.createAddressState(addrQr.getState()));
        }
        
        return addr;
    }
    
    PolicyLine.LineAnswers createLineAnswers(PolicyLineQR.LineAnswers lineAnswersQR) {
        PolicyLine.LineAnswers answers = factory.createPolicyLineLineAnswers();
        if (NullChecker.isNotNullOrEmpty(lineAnswersQR) && NullChecker.isNotNullOrEmpty(lineAnswersQR.getEntry())) {
            for (PolicyLineAnswerQR aqr : lineAnswersQR.getEntry()) {
                PolicyLineAnswer lineAns = factory.createPolicyLineAnswer();
                lineAns.setQuestionCode(factory.createPolicyLineAnswerQuestionCode(aqr.getQuestionCode()));
                if (NullChecker.isNotNullOrEmpty(aqr.getBooleanAnswer())) {
                    lineAns.setBooleanAnswer(factory.createPolicyLineAnswerBooleanAnswer(aqr.getBooleanAnswer()));
                }
                if (NullChecker.isNotNullOrEmpty(aqr.getTextAnswer())) {
                    lineAns.setTextAnswer(factory.createPolicyLineAnswerTextAnswer(aqr.getTextAnswer()));
                }
                answers.getEntry().add(lineAns);
            }
        }
        
        return answers;
    }
    

    protected String marshall(PolicyPeriod policyPeriodObject) throws JAXBException, XMLStreamException {
        String xml = null;

        JAXBContext jaxbContext = JAXBContext.newInstance(PolicyPeriod.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        XMLOutputFactory xof = XMLOutputFactory.newInstance();
        StringWriter stringWriter = new StringWriter();
        XMLStreamWriter streamWriter = xof.createXMLStreamWriter(stringWriter);
        CDataXMLStreamWriter cdataStreamWriter = new CDataXMLStreamWriter(streamWriter);

        JAXBElement<PolicyPeriod> jaxb = factory.createPolicyPeriod(policyPeriodObject);
        jaxbMarshaller.marshal(jaxb, cdataStreamWriter);
        xml = stringWriter.toString();
        streamWriter.close();
        return xml;
    }
}
