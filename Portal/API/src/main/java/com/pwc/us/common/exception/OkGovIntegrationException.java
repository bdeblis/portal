package com.pwc.us.common.exception;

/**
 *
 * @author Roger
 */
public class OkGovIntegrationException extends Exception {
    public OkGovIntegrationException(String message) {
        super(message);
    }
    
    public OkGovIntegrationException(String message, Exception e) {
        super(message, e);
    }  
}
