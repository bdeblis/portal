/*
 * APIImpl.java
 */
package com.pwc.us.impl;

import com.pwc.us.common.COIRequest;
import com.pwc.us.common.exception.GwIntegrationException;
import com.pwc.us.common.model.CertificateOfInsuranceRequest;
import com.pwc.us.common.model.PolicyTerm;
import com.pwc.us.webservice.client.COIRequestBuilder;
import java.util.List;
import java.util.logging.Logger;

/**
 *
 * @author generated-by-archetype
 *
 */
public class COIRequestImpl implements COIRequest {

    private static Logger logger = Logger.getLogger(COIRequestImpl.class.getName());

	// Submits the COI Request
    public boolean processRequest(CertificateOfInsuranceRequest request) throws GwIntegrationException {
        COIRequestBuilder builder = new COIRequestBuilder();
        boolean response = builder.processRequest(request);
        return response;
    }
    
    public List<PolicyTerm> getPolicyTerms(String policyNumber) throws GwIntegrationException {
        COIRequestBuilder builder = new COIRequestBuilder();
        List<PolicyTerm> terms = null;
        
        terms = builder.getPolicyTerms(policyNumber);
        return terms;

    }


    	
	/**
	 * Implementation of API Methods should be done here
	 * TODO Implement the API Methods
	 */
}
