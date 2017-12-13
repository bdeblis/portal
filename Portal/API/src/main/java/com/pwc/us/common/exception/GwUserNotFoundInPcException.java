package com.pwc.us.common.exception;

/**
 * This exception is thrown whenever a user who attempts to register or login
 * cannot be found in PolicyCenter.
 * @author Roger Turnau - <roger.turnau@us.pwc.com>
 */
public class GwUserNotFoundInPcException extends Exception {
    public GwUserNotFoundInPcException(String message) {
        super(message);
    }
    
    public GwUserNotFoundInPcException(String message, Exception e) {
        super(message, e);
    }   
}
