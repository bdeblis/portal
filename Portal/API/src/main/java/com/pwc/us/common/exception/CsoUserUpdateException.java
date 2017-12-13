package com.pwc.us.common.exception;

/**
 *
 * @author Roger Turnau - <roger.turnau@us.pwc.com>
 */
public class CsoUserUpdateException extends Exception {
    public CsoUserUpdateException(String message) {
        super(message);
    }
    
    public CsoUserUpdateException(String message, Exception e) {
        super(message, e);
    }    
}
