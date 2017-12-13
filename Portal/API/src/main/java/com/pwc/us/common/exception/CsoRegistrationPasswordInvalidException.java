package com.pwc.us.common.exception;

/**
 * Custom exception to hide LDAPExceptions (which are implementation-specific)
 * @author Roger Turnau - <roger.turnau@us.pwc.com>
 */
public class CsoRegistrationPasswordInvalidException extends Exception {
    public CsoRegistrationPasswordInvalidException(String message) {
        super(message);
    }
    
    public CsoRegistrationPasswordInvalidException(String message, Exception e) {
        super(message, e);
    }    
}
