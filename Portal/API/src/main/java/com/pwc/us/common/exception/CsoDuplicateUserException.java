package com.pwc.us.common.exception;

/**
 *
 * @author Roger Turnau - <roger.turnau@us.pwc.com>
 */
public class CsoDuplicateUserException extends Exception {
    public CsoDuplicateUserException(String message) {
        super(message);
    }
    
    public CsoDuplicateUserException(String message, Exception e) {
        super(message, e);
    }     
}
