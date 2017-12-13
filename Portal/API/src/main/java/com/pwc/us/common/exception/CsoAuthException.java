package com.pwc.us.common.exception;

/**
 * Custom exception to hide LDAPExceptions (which are implementation-specific)
 * @author Roger Turnau - <roger.turnau@us.pwc.com>
 */
public class CsoAuthException extends Exception {
    public CsoAuthException(String message) {
        super(message);
    }
    
    public CsoAuthException(String message, Exception e) {
        super(message, e);
    }    
}
