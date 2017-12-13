/*
 * COIRequest.java
 */
package com.pwc.us.common;

import com.pwc.us.common.exception.GwIntegrationException;
import com.pwc.us.common.model.OutOfStateAttachments;
import java.io.IOException;

/**
 *
 * @author generated-by-archetype
 * 
 */
public interface OutOfState {

public boolean processRequest(OutOfStateAttachments attachments) throws IOException, GwIntegrationException;


}
