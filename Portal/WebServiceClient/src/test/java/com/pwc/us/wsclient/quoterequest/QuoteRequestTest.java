package com.pwc.us.wsclient.quoterequest;

import com.pwc.us.common.exception.GwIntegrationException;
import com.pwc.us.ws.client.quoterequest.TypekeyName;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

/**
 *
 * @author Roger Turnau - <roger.turnau@us.pwc.com>
 */
@Ignore  public class QuoteRequestTest {

    public QuoteRequestTest() {
    }

    /**
     * Test of getAccountOrgKeys method, of class QuoteRequest.
     */
    @Test
    public void testGetAccountOrgKeys() {
        System.out.println("getAccountOrgKeys");
        QuoteRequest instance = new QuoteRequest();
        List<TypekeyName> result = null;
        String expectedCode = "corporation";
        try {
            result = instance.getAccountOrgKeys();
        } catch (GwIntegrationException e) {
            fail(e.toString());
        }
        assertNotNull(result);
        boolean foundIt = testExpectedCodeFound(result, expectedCode);
        assertTrue("could not find expected code in list of returned codes", foundIt);
    }

    /**
     * Test of getAddlIntRelationships method, of class QuoteRequest.
     */
    @Test
    public void testGetAddlIntRelationships() {
        System.out.println("getAddlIntRelationships");
        QuoteRequest instance = new QuoteRequest();
        List<TypekeyName> result = null;
        String expectedCode = "agent";
        try {
            result = instance.getAddlIntRelationships();
        } catch (GwIntegrationException e) {
            fail(e.toString());
        }
        assertNotNull(result);
        boolean foundIt = testExpectedCodeFound(result, expectedCode);
        assertTrue("could not find expected code in list of returned codes", foundIt);
    }

    /**
     * Test of getOwnerOfficerRelationships method, of class QuoteRequest.
     */
    @Test
    public void testGetOwnerOfficerRelationships() {
        System.out.println("getOwnerOfficerRelationships");
        QuoteRequest instance = new QuoteRequest();
        List<TypekeyName> result = null;
        String expectedCode = "Officer";
        try {
            result = instance.getOwnerOfficerRelationships();
        } catch (GwIntegrationException e) {
            fail(e.toString());
        }
        assertNotNull(result);
        boolean foundIt = testExpectedCodeFound(result, expectedCode);
        assertTrue("could not find expected code in list of returned codes", foundIt);
    }
    
    private boolean testExpectedCodeFound(List<TypekeyName> list, String expectedCode) {
        boolean foundIt = false;
        for (TypekeyName name : list) {
            if (name.getCode().equals(expectedCode)) {
                foundIt = true;
            }
        }
        return foundIt;
        
    }
}