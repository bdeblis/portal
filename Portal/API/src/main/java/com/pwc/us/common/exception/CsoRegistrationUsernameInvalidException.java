package com.pwc.us.common.exception;

/**
 * Custom exception to hide LDAPExceptions (which are implementation-specific)
 * @author Roger Turnau - <roger.turnau@us.pwc.com>
 */
public class CsoRegistrationUsernameInvalidException extends Exception {
    public CsoRegistrationUsernameInvalidException(String message) {
        super(message);
    }
    
    public CsoRegistrationUsernameInvalidException(String message, Exception e) {
        super(message, e);
    }    
}
