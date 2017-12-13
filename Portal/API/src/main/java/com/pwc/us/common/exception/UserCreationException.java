package com.pwc.us.common.exception;

/**
 *
 * @author Roger Turnau - <roger.turnau@us.pwc.com>
 */
public class UserCreationException extends Exception {

    public UserCreationException(String message) {
        super(message);
    }
    
    public UserCreationException(String message, Exception e) {
        super(message, e);
    }
    
    public UserCreationException(String message, Throwable e) {
        super(message, e);
    }
}
