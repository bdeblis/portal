/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pwc.us.common.model.payonline;


/**
 *
 * @author rturnau001
 */
public class OkGovResultPO {

    private String token;
    private String error;
    
    public String getToken(){
        return this.token;
    }
    
    public void setToken(String token) {
        this.token = token;
    }
    
    public String getError(){
        return this.error;
    }
    
    public void setError(String error) {
        this.error = error;
    }    
}
