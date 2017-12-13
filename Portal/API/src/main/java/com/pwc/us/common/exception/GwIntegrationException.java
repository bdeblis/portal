/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pwc.us.common.exception;

/**
 *
 * @author Roger Turnau - <roger.turnau@us.pwc.com>
 */
public class GwIntegrationException extends Exception {
    public GwIntegrationException(String message) {
        super(message);
    }
    
    public GwIntegrationException(String message, Exception e) {
        super(message, e);
    }       
}
