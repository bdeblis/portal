/*
 * COIRequest.java
 */
package com.pwc.us.common;

import com.pwc.us.common.exception.GwFNOIRequiredFieldException;
import com.pwc.us.common.exception.GwIntegrationException;
import com.pwc.us.common.model.fnoi.BodyPartTypeFNOI;
import com.pwc.us.common.model.fnoi.ClaimFNOI;
import com.pwc.us.common.model.KeyName;
import java.util.List;

/**
 *
 * @author generated-by-archetype
 * 
 */
public interface FNOIRequest {

public String processRequest(ClaimFNOI request) throws GwIntegrationException, GwFNOIRequiredFieldException;

public List<KeyName> getSpecificDetailedInjuryTypeTypeKeys() throws GwIntegrationException;

public List<KeyName> getOccupationalInjuryTypeTypeKeys() throws GwIntegrationException;

public List<KeyName> getDetailedBodyPartTypeTypeKeys(BodyPartTypeFNOI primaryBodyPart) throws GwIntegrationException;

public List<KeyName> getBodyPartTypeTypeKeys() throws GwIntegrationException;

public List<KeyName> getAccountOrgTypeExtTypeKeys() throws GwIntegrationException;

public List<KeyName> getCumulativeDetailedInjuryTypeTypeKeys() throws GwIntegrationException;

public List<KeyName> getMultipleInjuryTypeTypeKeys() throws GwIntegrationException;

public List<KeyName> getPrimaryInjuryType() throws GwIntegrationException;

/**
     * 
     * 
     * Service methods should go here
     */
}
