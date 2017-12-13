package com.pwc.us.common.exception;

/**
 * This exception is thrown when a user who is already registered in Guidewire
 * attempts to register again.
 * @author Roger Turnau - <roger.turnau@us.pwc.com>
 */
public class GwUserAlreadyRegisteredException extends Exception {
    public GwUserAlreadyRegisteredException(String message) {
        super(message);
    }
    
    public GwUserAlreadyRegisteredException(String message, Exception e) {
        super(message, e);
    }       
}
