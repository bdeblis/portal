package com.pwc.us.common.exception;

/**
 * This exception is thrown whenever a user who attempts to register or login
 * cannot be found in ClaimsCenter.
 * @author Roger Turnau - <roger.turnau@us.pwc.com>
 */
public class GwUserNotFoundInCcException extends Exception {
    public GwUserNotFoundInCcException(String message) {
        super(message);
    }
    
    public GwUserNotFoundInCcException(String message, Exception e) {
        super(message, e);
    }   
}