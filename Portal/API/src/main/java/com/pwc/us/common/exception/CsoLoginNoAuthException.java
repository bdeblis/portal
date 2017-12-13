package com.pwc.us.common.exception;

/**
 *
 * @author Roger Turnau - <roger.turnau@us.pwc.com>
 */
public class CsoLoginNoAuthException extends Exception {
    public CsoLoginNoAuthException(String message) {
        super(message);
    }
    
    public CsoLoginNoAuthException(String message, Exception e) {
        super(message, e);
    }
 
}
