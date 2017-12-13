/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pwc.us.common.exception;

/**
 *
 * @author Roger
 */
public class GwAuditIsEditableException extends Exception {

    public GwAuditIsEditableException(String message) {
        super(message);
    }

    public GwAuditIsEditableException(String message, Exception e) {
        super(message, e);
    }
}
