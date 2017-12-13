/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pwc.us.webservice.client;

import com.pwc.us.common.exception.GwIntegrationException;
import com.pwc.us.common.model.CertificateOfInsuranceRequest;
import com.pwc.us.common.model.PolicyTerm;
import com.pwc.us.common.utils.NullChecker;
import com.pwc.us.webservice.tools.CDataXMLStreamWriter;
import com.pwc.us.ws.client.coi.COIPolicyTerm;
import com.pwc.us.ws.client.coi.model.CertificateOfInsuranceExt;
import com.pwc.us.ws.client.coi.model.ObjectFactory;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
public class COIRequestBuilder {

    String policyNumber = "";
    private static final Logger logger = LoggerFactory.getLogger(COIRequestBuilder.class);

    public List<PolicyTerm> getPolicyTerms(String policyNumber) throws GwIntegrationException {
        COIClientInt clientInt = new COIClientInt();
        List<COIPolicyTerm> jaxbTerms = null;
        List<PolicyTerm> terms = new ArrayList<PolicyTerm>();

        jaxbTerms = clientInt.getPolicyTerms(policyNumber);
        for (COIPolicyTerm t : jaxbTerms) {
            PolicyTerm term = new PolicyTerm();
            term.setTermNumber(t.getTermNumber());
            term.setTermDescription(t.getTermDescription());
            terms.add(term);
        }
        
        // make a sorted, unique list
        Set <PolicyTerm> uniqueTerms = new HashSet<PolicyTerm>();
        for(PolicyTerm t : terms)
        {
            uniqueTerms.add(t);
        }
        List sortedUniqueTerms = new ArrayList(uniqueTerms);
        Collections.sort(sortedUniqueTerms);
        return sortedUniqueTerms;
    }

    public boolean processRequest(CertificateOfInsuranceRequest request) throws GwIntegrationException {

        //Step 1: Build the CertificateOfInsuranceExt Object
        CertificateOfInsuranceExt coiRequestObject = buildCOIObject(request);

        //Step 2: Turn the request into XML
        String coiRequestXML = null;
        try {
            coiRequestXML = coiMarshalling(coiRequestObject);
        } catch (JAXBException e) {
            String message = "Caught JAXBException trying to create new claim.";
            logger.error(message, e);
            throw new GwIntegrationException(message, e);            
        } catch (XMLStreamException e) {
            String message = "Caught XMLStreamException trying to create new claim.";
            logger.error(message, e);
            throw new GwIntegrationException(message, e);              
        }

        //Step 3: Submit the COI Request
        return submitCOIRequest(policyNumber, coiRequestXML);

    }

    private CertificateOfInsuranceExt buildCOIObject(CertificateOfInsuranceRequest request) {

        ObjectFactory objectFactory = new ObjectFactory();
        CertificateOfInsuranceExt coiObject = objectFactory.createCertificateOfInsuranceExt();

        policyNumber = request.getPolicyNumber();

        if (NullChecker.isNotNullOrEmpty(request.getClient())) {
            coiObject.setClient(objectFactory.createCertificateOfInsuranceExtClient(request.getClient()));
        }

        coiObject.setDeliverByMail(objectFactory.createCertificateOfInsuranceExtDeliverByMail(NullChecker.isNullThenFalse(request.getDeliverByMail())));
        coiObject.setDeliverByEmail(objectFactory.createCertificateOfInsuranceExtDeliverByEmail(NullChecker.isNullThenFalse(request.getDeliverByEmail())));
        coiObject.setDeliverByFax(objectFactory.createCertificateOfInsuranceExtDeliverByFax(NullChecker.isNullThenFalse(request.getDeliverByFax())));

        if (NullChecker.isNullThenFalse(request.getDeliverByEmail())) {
            if (NullChecker.isNotNullOrEmpty(request.getEmailAddress1())) {
                coiObject.setEmailAddress1(objectFactory.createCertificateOfInsuranceExtEmailAddress1(request.getEmailAddress1()));
            }
            if (NullChecker.isNotNullOrEmpty(request.getEmailAddress2())) {
                coiObject.setEmailAddress2(objectFactory.createCertificateOfInsuranceExtEmailAddress2(request.getEmailAddress2()));
            }
            if (NullChecker.isNotNullOrEmpty(request.getEmailAddress3())) {
                coiObject.setEmailAddress3(objectFactory.createCertificateOfInsuranceExtEmailAddress3(request.getEmailAddress3()));
            }
            if (NullChecker.isNotNullOrEmpty(request.getEmailRecipientName1())) {
                coiObject.setEmailRecipientName1(objectFactory.createCertificateOfInsuranceExtEmailRecipientName1(request.getEmailRecipientName1()));
            }
            if (NullChecker.isNotNullOrEmpty(request.getEmailRecipientName2())) {
                coiObject.setEmailRecipientName2(objectFactory.createCertificateOfInsuranceExtEmailRecipientName2(request.getEmailRecipientName2()));
            }
            if (NullChecker.isNotNullOrEmpty(request.getEmailRecipientName3())) {
                coiObject.setEmailRecipientName3(objectFactory.createCertificateOfInsuranceExtEmailRecipientName3(request.getEmailRecipientName3()));
            }
        }

        if (NullChecker.isNullThenFalse(request.getDeliverByFax())) {
            if (NullChecker.isNotNullOrEmpty(request.getFaxNumber1())) {
                coiObject.setFaxNumber1(objectFactory.createCertificateOfInsuranceExtFaxNumber1(request.getFaxNumber1()));
            }
            if (NullChecker.isNotNullOrEmpty(request.getFaxNumber2())) {
                coiObject.setFaxNumber2(objectFactory.createCertificateOfInsuranceExtFaxNumber2(request.getFaxNumber2()));
            }
            if (NullChecker.isNotNullOrEmpty(request.getFaxNumber3())) {
                coiObject.setFaxNumber3(objectFactory.createCertificateOfInsuranceExtFaxNumber3(request.getFaxNumber3()));
            }
            if (NullChecker.isNotNullOrEmpty(request.getFaxRecipientName1())) {
                coiObject.setFaxRecipientName1(objectFactory.createCertificateOfInsuranceExtFaxRecipientName1(request.getFaxRecipientName1()));
            }
            if (NullChecker.isNotNullOrEmpty(request.getEmailRecipientName2())) {
                coiObject.setFaxRecipientName2(objectFactory.createCertificateOfInsuranceExtFaxRecipientName2(request.getFaxRecipientName2()));
            }
            if (NullChecker.isNotNullOrEmpty(request.getEmailRecipientName3())) {
                coiObject.setFaxRecipientName3(objectFactory.createCertificateOfInsuranceExtFaxRecipientName3(request.getFaxRecipientName3()));
            }
        }


        CertificateOfInsuranceExt.HolderAddress holderAddress = objectFactory.createCertificateOfInsuranceExtHolderAddress();
        if (NullChecker.isNotNullOrEmpty(request.getHolderAddress())) {
            if (NullChecker.isNotNullOrEmpty(request.getHolderAddress().getAddressLine1())) {
                holderAddress.setAddressLine1(objectFactory.createCertificateOfInsuranceExtHolderAddressAddressLine1(request.getHolderAddress().getAddressLine1()));
            }

            if (NullChecker.isNotNullOrEmpty(request.getHolderAddress().getAddressLine2())) {
                holderAddress.setAddressLine2(objectFactory.createCertificateOfInsuranceExtHolderAddressAddressLine2(request.getHolderAddress().getAddressLine2()));
            }

            if (NullChecker.isNotNullOrEmpty(request.getHolderAddress().getCity())) {
                holderAddress.setCity(objectFactory.createCertificateOfInsuranceExtHolderAddressCity(request.getHolderAddress().getCity()));
            }

            if (NullChecker.isNotNullOrEmpty(request.getHolderAddress().getState())) {
                holderAddress.setState(objectFactory.createCertificateOfInsuranceExtHolderAddressState(request.getHolderAddress().getState()));
            }

            if (NullChecker.isNotNullOrEmpty(request.getHolderAddress().getPostalCode())) {
                holderAddress.setPostalCode(objectFactory.createCertificateOfInsuranceExtHolderAddressPostalCode(request.getHolderAddress().getPostalCode()));
            }

            if (NullChecker.isNotNullOrEmpty(request.getHolderAddress().getCity())) {
                holderAddress.setCity(objectFactory.createCertificateOfInsuranceExtHolderAddressCity(request.getHolderAddress().getCity()));
            }


        }
        coiObject.setHolderAddress(objectFactory.createCertificateOfInsuranceExtHolderAddress(holderAddress));


        if (NullChecker.isNotNullOrEmpty(request.getHolderName())) {
            coiObject.setHolderName(objectFactory.createCertificateOfInsuranceExtHolderName(request.getHolderName()));
        }

        coiObject.setIsAlternateEmployer(objectFactory.createCertificateOfInsuranceExtIsAlternateEmployer(NullChecker.isNullThenFalse(request.getIsAlternateEmployer())));
        coiObject.setIsWaiverOfSubrogation(objectFactory.createCertificateOfInsuranceExtIsWaiverOfSubrogation(NullChecker.isNullThenFalse(request.getIsWaiverOfSubrogation())));
        coiObject.setWithThirtyDayNotice(objectFactory.createCertificateOfInsuranceExtWithThirtyDayNotice(NullChecker.isNullThenFalse(request.getWithThirtyDayNotice())));

        if (NullChecker.isNotNullOrEmpty(request.getProjectName())) {
            coiObject.setProjectName(objectFactory.createCertificateOfInsuranceExtProjectName(request.getProjectName()));
        }

        if (NullChecker.isNotNullOrEmpty(request.getRequesterName())) {
            coiObject.setRequesterName(objectFactory.createCertificateOfInsuranceExtRequesterName(request.getRequesterName()));
        }

        if (NullChecker.isNotNullOrEmpty(request.getRequesterPhone())) {
            coiObject.setRequesterPhone(objectFactory.createCertificateOfInsuranceExtRequesterPhone(request.getRequesterPhone()));
        }

        if (NullChecker.isNotNullOrEmpty(request.getTermNumber())) {
            coiObject.setTermNumber(objectFactory.createCertificateOfInsuranceExtTermNumber(request.getTermNumber()));
        }
        if (NullChecker.isNotNullOrEmpty(request.getTermDesc())) {
            coiObject.setTermDesc(objectFactory.createCertificateOfInsuranceExtTermDesc(request.getTermDesc()));
        }

        if (NullChecker.isNotNullOrEmpty(request.getWorkPerformed())) {
            coiObject.setWorkPerformed(objectFactory.createCertificateOfInsuranceExtWorkPerformed(request.getWorkPerformed()));
        }

        if (NullChecker.isNotNullOrEmpty(request.getSpecificInstructions())) {
            coiObject.setSpecificInstructions(objectFactory.createCertificateOfInsuranceExtSpecificInstructions(request.getSpecificInstructions()));
        }

        return coiObject;
    }

    private String coiMarshalling(CertificateOfInsuranceExt coiObject) throws JAXBException, XMLStreamException {
        String coiXML = "";
        JAXBContext jaxbContext = JAXBContext.newInstance(CertificateOfInsuranceExt.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        XMLOutputFactory xof = XMLOutputFactory.newInstance();
        StringWriter stringWriter = new StringWriter();
        XMLStreamWriter streamWriter = xof.createXMLStreamWriter(stringWriter);
        CDataXMLStreamWriter cdataStreamWriter = new CDataXMLStreamWriter(streamWriter);

        // Marshal CertificateOfInsuranceExt
        ObjectFactory objectFactory = new ObjectFactory();
        JAXBElement<CertificateOfInsuranceExt> jeCOIObject = objectFactory.createCertificateOfInsuranceExt(coiObject);
        jaxbMarshaller.marshal(jeCOIObject, cdataStreamWriter);
        coiXML = stringWriter.toString();
        streamWriter.close();
        return coiXML;
    }

    private boolean submitCOIRequest(String policyNumber, String requestXML) throws GwIntegrationException {

        COIClientInt clientInt = new COIClientInt();
        return clientInt.createNewCOIRequest(policyNumber, requestXML);
    }
}
