package com.pwc.us.common.exception;

/**
 *
 * @author Roger Turnau - <roger.turnau@us.pwc.com>
 */
public class CsoPasswordRecoveryException extends Exception {
    public CsoPasswordRecoveryException(String message) {
        super(message);
    }
    
    public CsoPasswordRecoveryException(String message, Exception e) {
        super(message, e);
    }
}
