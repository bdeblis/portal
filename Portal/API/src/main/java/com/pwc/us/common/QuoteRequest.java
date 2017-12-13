package com.pwc.us.common;

import com.pwc.us.common.exception.GwIntegrationException;
import com.pwc.us.common.model.ContactBO;
import com.pwc.us.common.model.KeyName;
import com.pwc.us.common.model.quoterequest.AccountQR;
import com.pwc.us.common.model.quoterequest.OwnerOfficerBO;
import com.pwc.us.common.model.quoterequest.PolicyContactRoleQR;
import com.pwc.us.common.model.quoterequest.PolicyPeriodQR;
import com.pwc.us.common.model.quoterequest.WC7AddlInterestExtQR;
import java.util.List;

/**
 *
 * @author Roger
 */
public interface QuoteRequest {
    
    /**
     * Retrieves a list of organization types from PolicyCenter.
     * @return
     * @throws GwIntegrationException 
     */
    public List<KeyName> getAccountOrgKeys() throws GwIntegrationException;
    
    /**
     * Retrieves a list of Owner-Officer relationships from PolicyCenter, so they
     * can be displayed as a drop-down on the Request a Quote page.
     * @return
     * @throws GwIntegrationException 
     */
    public List<KeyName> getOwnerOfficerRelationships() throws GwIntegrationException;
    
    /**
     * Retrieves a list of additional interest relationships to display 
     * as a drop-down on the Request a Quote page.
     * @return
     * @throws GwIntegrationException 
     */
    public List<KeyName> getAddlIntRelationships() throws GwIntegrationException;
    
    /**
     * Submits the quote request to the integration layer so it can be sent
     * to PolicyCenter.
     * 
     * @param accountQr
     * @param policyPeriodQr
     * @return
     * @throws GwIntegrationException 
     */
    public String requestQuote(AccountQR accountQr, PolicyPeriodQR policyPeriodQr) throws GwIntegrationException;
    
    /**
     * Creates an audit contact, which is a kind of additional interest for the
     * PolicyCenter.
     * @param contact
     * @return 
     */
    public WC7AddlInterestExtQR createAuditContact(ContactBO contact);
    
    /**
     * Create a contact info additional interest record.
     * @param contact
     * @return 
     */
    public WC7AddlInterestExtQR createContact(ContactBO contact);
    
    /**
     * Create an excluded owner/officer for the quote request.
     * @param off
     * @return 
     */
    public PolicyContactRoleQR createExcludedOwnerOfficer(OwnerOfficerBO off);
    
    /**
     * Create an included owner/officer for the quote request.
     * @param off
     * @return 
     */
    public PolicyContactRoleQR createIncludedOwnerOfficer(OwnerOfficerBO off);
    
    
}
