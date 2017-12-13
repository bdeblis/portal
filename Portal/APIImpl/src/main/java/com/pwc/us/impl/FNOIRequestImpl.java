/*
 * APIImpl.java
 */
package com.pwc.us.impl;

import com.pwc.us.common.FNOIRequest;
import com.pwc.us.common.exception.GwIntegrationException;
import com.pwc.us.common.exception.GwFNOIRequiredFieldException;
import com.pwc.us.common.model.fnoi.BodyPartTypeFNOI;
import com.pwc.us.common.model.fnoi.ClaimFNOI;
import com.pwc.us.common.model.KeyName;
import com.pwc.us.wsclient.fnoi.FNOIRequestBuilder;
import java.util.List;
import java.util.logging.Logger;

/**
 *
 * @author generated-by-archetype
 *
 */
public class FNOIRequestImpl implements FNOIRequest {

    private static Logger logger = Logger.getLogger(FNOIRequestImpl.class.getName());

	// Submits the COI Request
    public String processRequest(ClaimFNOI request) throws GwIntegrationException, GwFNOIRequiredFieldException {
        FNOIRequestBuilder builder = new FNOIRequestBuilder();
        String response = builder.processRequest(request);
        return response;
    }
    
    public List<KeyName> getSpecificDetailedInjuryTypeTypeKeys() throws GwIntegrationException {
        FNOIRequestBuilder builder = new FNOIRequestBuilder();
        List<KeyName> response = builder.getSpecificDetailedInjuryTypeTypeKeys();
        return response;
    }

    public List<KeyName> getOccupationalInjuryTypeTypeKeys() throws GwIntegrationException {
        FNOIRequestBuilder builder = new FNOIRequestBuilder();
        List<KeyName> response = builder.getOccupationalInjuryTypeTypeKeys();
        return response;
        
    }

    public List<KeyName> getDetailedBodyPartTypeTypeKeys(BodyPartTypeFNOI primaryBodyPart) throws GwIntegrationException {
        FNOIRequestBuilder builder = new FNOIRequestBuilder();
        List<KeyName> response = builder.getDetailedBodyPartTypeTypeKeys(primaryBodyPart);
        return response;
        
    }

    public List<KeyName> getBodyPartTypeTypeKeys() throws GwIntegrationException {
        FNOIRequestBuilder builder = new FNOIRequestBuilder();
        List<KeyName> response = builder.getBodyPartTypeTypeKeys();
        return response;
        
    }

    public List<KeyName> getAccountOrgTypeExtTypeKeys() throws GwIntegrationException {
        FNOIRequestBuilder builder = new FNOIRequestBuilder();
        List<KeyName> response = builder.getAccountOrgTypeExtTypeKeys();
        return response;
        
    }
    
    public List<KeyName> getCumulativeDetailedInjuryTypeTypeKeys() throws GwIntegrationException {
        FNOIRequestBuilder builder = new FNOIRequestBuilder();
        List<KeyName> response = builder.getCumulativeDetailedInjuryTypeTypeKeys();
        return response;
    }

    public List<KeyName> getMultipleInjuryTypeTypeKeys() throws GwIntegrationException {
        FNOIRequestBuilder builder = new FNOIRequestBuilder();
        List<KeyName> response = builder.getMultipleInjuryTypeTypeKeys();
        return response;
    }

    public List<KeyName> getPrimaryInjuryType() throws GwIntegrationException {
                FNOIRequestBuilder builder = new FNOIRequestBuilder();
                List<KeyName> response = builder.getPrimaryInjuryTypeTypeKeys();
                return response;
    }
    	
	/**
	 * Implementation of API Methods should be done here
	 * TODO Implement the API Methods
	 */
}
