/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pwc.us.common.exception;

/**
 *
 * @author Roger Turnau - <roger.turnau@us.pwc.com>
 */
public class GwFNOIRequiredFieldException extends Exception {
    public GwFNOIRequiredFieldException(String message) {
        super(message);
    }
    
    public GwFNOIRequiredFieldException(String message, Exception e) {
        super(message, e);
    }       
}
