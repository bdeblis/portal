/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pwc.us.common.exception;

/**
 *
 * @author Robert Snelling - <robert.snelling@us.pwc.com>
 */
public class GwNoResultsFoundException extends Exception {
    public GwNoResultsFoundException(String message) {
        super(message);
    }
    
    public GwNoResultsFoundException(String message, Exception e) {
        super(message, e);
    }       
}
