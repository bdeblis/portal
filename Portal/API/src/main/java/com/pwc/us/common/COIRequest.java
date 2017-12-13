/*
 * COIRequest.java
 */
package com.pwc.us.common;

import com.pwc.us.common.exception.GwIntegrationException;
import com.pwc.us.common.model.CertificateOfInsuranceRequest;
import com.pwc.us.common.model.PolicyTerm;
import java.util.List;

/**
 *
 * @author generated-by-archetype
 * 
 */
public interface COIRequest {

public boolean processRequest(CertificateOfInsuranceRequest request) throws GwIntegrationException;

public List<PolicyTerm> getPolicyTerms(String polictyNumber) throws GwIntegrationException;


/**
     * 
     * 
     * Service methods should go here
     */
}
