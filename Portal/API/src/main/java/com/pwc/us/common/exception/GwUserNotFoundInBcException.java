package com.pwc.us.common.exception;

/**
 * This exception is thrown whenever a user who attempts to register or login
 * cannot be found in BillingCenter.
 * @author Roger Turnau - <roger.turnau@us.pwc.com>
 */
public class GwUserNotFoundInBcException extends Exception {
    public GwUserNotFoundInBcException(String message) {
        super(message);
    }
    
    public GwUserNotFoundInBcException(String message, Exception e) {
        super(message, e);
    }   
}