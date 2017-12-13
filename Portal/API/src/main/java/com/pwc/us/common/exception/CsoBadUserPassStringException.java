package com.pwc.us.common.exception;

/**
 * Custom exception to hide LDAPExceptions (which are implementation-specific)
 * @author Roger Turnau - <roger.turnau@us.pwc.com>
 */
public class CsoBadUserPassStringException extends Exception {
    public CsoBadUserPassStringException(String message) {
        super(message);
    }
    
    public CsoBadUserPassStringException(String message, Exception e) {
        super(message, e);
    }    
}
