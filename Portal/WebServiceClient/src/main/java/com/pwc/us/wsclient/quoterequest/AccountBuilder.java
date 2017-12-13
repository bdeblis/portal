package com.pwc.us.wsclient.quoterequest;

import com.pwc.us.common.model.AddressBO;
import com.pwc.us.common.model.ContactBO;
import com.pwc.us.common.model.quoterequest.AccountQR;
import com.pwc.us.common.utils.NullChecker;
import com.pwc.us.webservice.tools.CDataXMLStreamWriter;
import com.pwc.us.ws.client.quoterequest.model.account.ObjectFactory;
import com.pwc.us.ws.client.quoterequest.model.account.Account;
import com.pwc.us.ws.client.quoterequest.model.account.Address;
import com.pwc.us.ws.client.quoterequest.model.account.Contact;
import java.io.StringWriter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 * The builder object for the new submissions API Account object.
 *
 * @author Roger Turnau - <roger.turnau@us.pwc.com>
 */
public class AccountBuilder {

    private ObjectFactory factory = new ObjectFactory();

    /*
     * <Account>
     <AccountHolderContact>    
     <ns0:EmailAddress1>info@courtyard.com</ns0:EmailAddress1>    
     <ns0:FaxPhone>405-567-1008</ns0:FaxPhone>    
     <ns0:Name>Bill's Aquatic Wonderland</ns0:Name>
     <ns0:PrimaryAddress>
     <ns1:AddressLine1>1600 NW EXPRESSWAY</ns1:AddressLine1>
     <ns1:AddressLine2>Floor 10, Apt. 7</ns1:AddressLine2>
     <ns1:City>Oklahoma City</ns1:City>
     <ns1:County>Oklahoma County</ns1:County>
     <ns1:Description>Address created via Portal</ns1:Description>
     <ns1:PostalCode>73118</ns1:PostalCode>
     <ns1:State>OK</ns1:State>
     </ns0:PrimaryAddress>
     <ns0:Subtype>Company</ns0:Subtype>
     <ns0:TaxID>12-3412671</ns0:TaxID>
     <ns0:WorkPhone>405-567-1119</ns0:WorkPhone>
     </AccountHolderContact>
     <BusOpsDesc>Four star hotel with 400 rooms and restaurant with seating space for 50 diners</BusOpsDesc>
     </Account>
     */
    protected Account buildAccountObject(AccountQR accountQR) {
        Account account = factory.createAccount();
        if (NullChecker.isNotNullOrEmpty(accountQR.getAccountHolderContact())) {
            account.setAccountHolderContact(factory.createAccountAccountHolderContact(buildAccountContact(accountQR.getAccountHolderContact())));
        }

        if (NullChecker.isNotNullOrEmpty(accountQR.getBusOpsDesc())) {
            account.setBusOpsDesc(factory.createAccountBusOpsDesc(accountQR.getBusOpsDesc()));
        }

        return account;
    }

    protected String marshall(Account accountObject) throws JAXBException, XMLStreamException {
        String xml = null;

        JAXBContext jaxbContext = JAXBContext.newInstance(Account.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        XMLOutputFactory xof = XMLOutputFactory.newInstance();
        StringWriter stringWriter = new StringWriter();
        XMLStreamWriter streamWriter = xof.createXMLStreamWriter(stringWriter);
        CDataXMLStreamWriter cdataStreamWriter = new CDataXMLStreamWriter(streamWriter);

        // Marshal CertificateOfInsuranceExt
        JAXBElement<Account> jAccountObj = factory.createAccount(accountObject);
        jaxbMarshaller.marshal(jAccountObj, cdataStreamWriter);
        xml = stringWriter.toString();
        streamWriter.close();
        return xml;
    }

    private Contact buildAccountContact(ContactBO ct) {
        Contact contact = factory.createContact();

        if (NullChecker.isNotNullOrEmpty(ct.getPrimaryAddress())) {
            contact.setPrimaryAddress(factory.createContactPrimaryAddress(buildAddress(ct.getPrimaryAddress())));
        }
        if (NullChecker.isNotNullOrEmpty(ct.getEntityPerson())) {
            contact.setEntityPerson(factory.createContactEntityPerson(buildEntityPerson(ct)));
        }
        if (NullChecker.isNotNullOrEmpty(ct.getEmailAddress1())) {
            contact.setEmailAddress1(factory.createContactEmailAddress1(ct.getEmailAddress1()));
        }
        if (NullChecker.isNotNullOrEmpty(ct.getFaxPhone())) {
            contact.setFaxPhone(factory.createContactFaxPhone(ct.getFaxPhone()));
        }
        if (NullChecker.isNotNullOrEmpty(ct.getName())) {
            contact.setName(factory.createContactName(ct.getName()));
        }
        if (NullChecker.isNotNullOrEmpty(ct.getSubType())) {
            contact.setSubtype(factory.createContactSubtype(ct.getSubType()));
        }
        if (NullChecker.isNotNullOrEmpty(ct.getTaxID())) {
            contact.setTaxID(factory.createContactTaxID(ct.getTaxID()));
        }
        if (NullChecker.isNotNullOrEmpty(ct.getWorkPhone())) {
            contact.setWorkPhone(factory.createContactWorkPhone(ct.getWorkPhone()));
        }

        return contact;
    }

    private Address buildAddress(AddressBO adr) {
        Address address = factory.createAddress();
        // Build the Address
        if (NullChecker.isNotNullOrEmpty(adr.getAddressLine1())) {
            address.setAddressLine1(factory.createAddressAddressLine1(adr.getAddressLine1()));
        }
        if (NullChecker.isNotNullOrEmpty(adr.getAddressLine2())) {
            address.setAddressLine2(factory.createAddressAddressLine2(adr.getAddressLine2()));
        }
        if (NullChecker.isNotNullOrEmpty(adr.getCity())) {
            address.setCity(factory.createAddressCity(adr.getCity()));
        }
        if (NullChecker.isNotNullOrEmpty(adr.getCounty())) {
            address.setCounty(factory.createAddressCounty(adr.getCounty()));
        }
        if (NullChecker.isNotNullOrEmpty(adr.getPostalCode())) {
            address.setPostalCode(factory.createAddressPostalCode(adr.getPostalCode()));
        }
        if (NullChecker.isNotNullOrEmpty(adr.getState())) {
            address.setState(factory.createAddressState(adr.getState()));
        }
        return address;
    }

    private Contact.EntityPerson buildEntityPerson(ContactBO ct) {
        Contact.EntityPerson ep = factory.createContactEntityPerson();
        if (NullChecker.isNotNullOrEmpty(ct.getEntityPerson().getFirstName())) {
            ep.setFirstName(factory.createContactEntityPersonFirstName(ct.getEntityPerson().getFirstName()));
        }
        if (NullChecker.isNotNullOrEmpty(ct.getEntityPerson().getLastName())) {
            ep.setLastName(factory.createContactEntityPersonLastName(ct.getEntityPerson().getLastName()));
        }
        if (NullChecker.isNotNullOrEmpty(ct.getEntityPerson().getMiddleName())) {
            ep.setMiddleName(factory.createContactEntityPersonMiddleName(ct.getEntityPerson().getMiddleName()));
        }
        return ep;
    }
}
