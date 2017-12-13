/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pwc.us.common.exception;

/**
 *
 * @author Robert Snelling - <robert.snelling@us.pwc.com>
 */
public class GwAuditIsCompleteException extends Exception {
    public GwAuditIsCompleteException(String message) {
        super(message);
    }
    
    public GwAuditIsCompleteException(String message, Exception e) {
        super(message, e);
    }       
}
