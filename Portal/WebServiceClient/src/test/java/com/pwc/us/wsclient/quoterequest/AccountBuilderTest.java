package com.pwc.us.wsclient.quoterequest;

import com.pwc.us.common.model.AddressBO;
import com.pwc.us.common.model.ContactBO;
import com.pwc.us.common.model.quoterequest.AccountQR;
import com.pwc.us.ws.client.quoterequest.model.account.Account;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Roger
 */
public class AccountBuilderTest {
    
    public AccountBuilderTest() {
    }

    /**
     * Test of marshall method, of class AccountBuilder.
     */
    @Test
    public void testMarshall() throws Exception {
        System.out.println("marshall");
        AccountQR act = createAccount();
        AccountBuilder instance = new AccountBuilder();
        Account accountObject = instance.buildAccountObject(act);        
        String result = instance.marshall(accountObject);
        assertNotNull(result);
        System.out.println(result);
    }
    
    private AccountQR createAccount() {
        AccountQR act = new AccountQR();
        
        act.setAccountHolderContact(createAccountHolderContact());
        act.setBusOpsDesc("This Thing we Do");
        
        return act;
    }
    
    private ContactBO createAccountHolderContact() {
        ContactBO ctc = new ContactBO();
        ctc.setPrimaryAddress(createPrimaryAddress());
        ctc.setEmailAddress1("bob@bob.com");
        ctc.setEntityPerson(createEntityPerson());
        return ctc;
    }
    
    private AddressBO createPrimaryAddress() {
        AddressBO addr = new AddressBO();
        addr.setAddressLine1("14 Far Wellin Lane");
        addr.setAddressLine2("Suite 30");
        addr.setCity("Oklahoma City");
        addr.setCity("Oklahoma County");
        addr.setPostalCode("12345");
        addr.setState("OK");
        
        
        return addr;
    }
    
    private ContactBO.EntityPerson createEntityPerson() {
        ContactBO.EntityPerson ep = new ContactBO.EntityPerson();
        ep.setFirstName("Bob");
        ep.setMiddleName("M.");
        ep.setLastName("Loblaw");
        return ep;
    }
    
}