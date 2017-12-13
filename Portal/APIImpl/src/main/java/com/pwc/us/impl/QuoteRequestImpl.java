package com.pwc.us.impl;

import com.pwc.us.common.QuoteRequest;
import com.pwc.us.common.exception.GwIntegrationException;
import com.pwc.us.common.model.ContactBO;
import com.pwc.us.common.model.KeyName;
import com.pwc.us.common.model.quoterequest.AccountQR;
import com.pwc.us.common.model.quoterequest.OwnerOfficerBO;
import com.pwc.us.common.model.quoterequest.PolicyContactRoleQR;
import com.pwc.us.common.model.quoterequest.PolicyPeriodQR;
import com.pwc.us.common.model.quoterequest.WC7AddlInterestExtQR;
import com.pwc.us.wsclient.quoterequest.QuoteRequestBuilder;
import java.util.List;

/**
 * The implementation for the main Request a Quote functionality.
 *
 * @author Roger
 */
public class QuoteRequestImpl implements QuoteRequest {

    protected static final String AUDITOR_EXT = "AUDITOR_Ext";
    protected static final String CONTACT_EXT = "CONTACT_Ext";
    protected static final String CONTACT_SUBTYPE = "Person";
    protected static final String POLICY_CONTACT_ROLE_SUBTYPE = "PolicyAddlInterest";
    protected static final String ACCOUNT_CONTACT_ROLE_SUBTYPE = "AdditionalInterest";
    protected static final String EXCLUDED_OWNER_OFFICER_SUBTYPE = "WC7ExcludedOwnerOfficer";
    protected static final String INCLUDED_OWNER_OFFICER_SUBTYPE = "WC7IncludedOwnerOfficer";
    protected static final String OWNER_OFFICER_SUBTYPE = "OwnerOfficer";
    protected static final String AUDITOR_RELATIONSHIP_TYPE = "other";
    
    QuoteRequestBuilder builder = new QuoteRequestBuilder();

    public List<KeyName> getAccountOrgKeys() throws GwIntegrationException {
        return builder.getAccountOrgKeys();
    }

    public List<KeyName> getOwnerOfficerRelationships() throws GwIntegrationException {
        return builder.getOwnerOfficerRelationships();
    }

    public List<KeyName> getAddlIntRelationships() throws GwIntegrationException {
        return builder.getAddlIntRelationships();
    }

    public String requestQuote(AccountQR accountQr, PolicyPeriodQR policyPeriodQr) throws GwIntegrationException {
        return builder.processRequest(accountQr, policyPeriodQr);
    }
    
    
    public WC7AddlInterestExtQR createAuditContact(ContactBO contact) {
        WC7AddlInterestExtQR newAuditor = new WC7AddlInterestExtQR();
        PolicyContactRoleQR policyContactRole = new PolicyContactRoleQR();
        contact.setSubType(CONTACT_SUBTYPE);
        policyContactRole.setSubtype(POLICY_CONTACT_ROLE_SUBTYPE);
        PolicyContactRoleQR.AccountContactRole accountContactRole = new PolicyContactRoleQR.AccountContactRole();
        PolicyContactRoleQR.AccountContactRole.AccountContact accountContact = new PolicyContactRoleQR.AccountContactRole.AccountContact();
        accountContact.setContact(contact);
        accountContactRole.setAccountContact(accountContact);
        accountContactRole.setSubtype(ACCOUNT_CONTACT_ROLE_SUBTYPE);
        policyContactRole.setAccountContactRole(accountContactRole);
        newAuditor.setPolicyAddlInterest(policyContactRole);
        newAuditor.setAdditionalInterestType(AUDITOR_EXT);
        newAuditor.setRelationship(AUDITOR_RELATIONSHIP_TYPE);

        return newAuditor;
    }

    public WC7AddlInterestExtQR createContact(ContactBO contact) {
        WC7AddlInterestExtQR newContact = new WC7AddlInterestExtQR();
        PolicyContactRoleQR policyContactRole = new PolicyContactRoleQR();
        contact.setSubType(CONTACT_SUBTYPE);
        policyContactRole.setSubtype(POLICY_CONTACT_ROLE_SUBTYPE);
        PolicyContactRoleQR.AccountContactRole accountContactRole = new PolicyContactRoleQR.AccountContactRole();
        PolicyContactRoleQR.AccountContactRole.AccountContact accountContact = new PolicyContactRoleQR.AccountContactRole.AccountContact();
        accountContact.setContact(contact);
        accountContactRole.setAccountContact(accountContact);
        accountContactRole.setSubtype(ACCOUNT_CONTACT_ROLE_SUBTYPE);
        policyContactRole.setAccountContactRole(accountContactRole);
        newContact.setPolicyAddlInterest(policyContactRole);
        newContact.setAdditionalInterestType(CONTACT_EXT);
        newContact.setRelationship(contact.getRelationship());

        return newContact;        
    }
    
    public PolicyContactRoleQR createExcludedOwnerOfficer(OwnerOfficerBO off) {
        PolicyContactRoleQR pcr = new PolicyContactRoleQR();
        PolicyContactRoleQR.EntityWC7PolicyOwnerOfficer officer = new PolicyContactRoleQR.EntityWC7PolicyOwnerOfficer();
        off.setSubType(CONTACT_SUBTYPE);
        officer.setActiveInBusinessExt(off.isActiveInBusiness());
        officer.setRelationshipTitle(off.getRelationship());
        pcr.setEntityWC7PolicyOwnerOfficer(officer);
        pcr.setSubtype(EXCLUDED_OWNER_OFFICER_SUBTYPE);
        PolicyContactRoleQR.AccountContactRole accountContactRole = new PolicyContactRoleQR.AccountContactRole();
        PolicyContactRoleQR.AccountContactRole.AccountContact accountContact = new PolicyContactRoleQR.AccountContactRole.AccountContact();
        accountContact.setContact(off);
        accountContactRole.setAccountContact(accountContact);
        accountContactRole.setSubtype(OWNER_OFFICER_SUBTYPE);
        pcr.setAccountContactRole(accountContactRole);             
        
        return pcr;
    }

    public PolicyContactRoleQR createIncludedOwnerOfficer(OwnerOfficerBO off) {
        PolicyContactRoleQR pcr = new PolicyContactRoleQR();
        PolicyContactRoleQR.EntityWC7PolicyOwnerOfficer officer = new PolicyContactRoleQR.EntityWC7PolicyOwnerOfficer();
        off.setSubType(CONTACT_SUBTYPE);
        officer.setActiveInBusinessExt(off.isActiveInBusiness());
        officer.setRelationshipTitle(off.getRelationship());
        pcr.setEntityWC7PolicyOwnerOfficer(officer);
        pcr.setSubtype(INCLUDED_OWNER_OFFICER_SUBTYPE);
        PolicyContactRoleQR.AccountContactRole accountContactRole = new PolicyContactRoleQR.AccountContactRole();
        PolicyContactRoleQR.AccountContactRole.AccountContact accountContact = new PolicyContactRoleQR.AccountContactRole.AccountContact();
        accountContact.setContact(off);
        accountContactRole.setAccountContact(accountContact);
        accountContactRole.setSubtype(OWNER_OFFICER_SUBTYPE);
        pcr.setAccountContactRole(accountContactRole);             
        
        return pcr;
    }
}
