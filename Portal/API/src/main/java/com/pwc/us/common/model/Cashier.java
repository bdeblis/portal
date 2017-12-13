/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pwc.us.common.model;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author isrk
 */
public class Cashier  extends User implements Serializable {
    
    private String policyNumber;
    private String companyName;
    private String okGovUserId;
    private String policyCenterAccountId;
    
    public Cashier(){
        
    }
    
        public Cashier(String login, String firstName,
            String lastName, String email, String phone,
            String challengeQuestion, String challengeAnswer, Date lastLogin,
            String policyNumber, String companyName, String okGovUserId,
            String pcAccountId, boolean accountLocked) {
        super(login, firstName, lastName, email, phone, 
                challengeQuestion, challengeAnswer, lastLogin,
                accountLocked);
        this.policyNumber = policyNumber;
        this.companyName = companyName;
        this.okGovUserId = okGovUserId;
        this.policyCenterAccountId = pcAccountId;
    }
    
    public String getPolicyNumber() {
        return policyNumber;
    }

    public void setPolicyNumber(String policyNumber) {
        this.policyNumber = policyNumber;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    
    public String getOkGovUserId() {
        return okGovUserId;
    }

    public void setOkGovUserId(String okGovUserId) {
        this.okGovUserId = okGovUserId;
    }

    public String getPolicyCenterAccountId() {
        return policyCenterAccountId;
    }

    public void setPolicyCenterAccountId(String policyCenterAccountId) {
        this.policyCenterAccountId = policyCenterAccountId;
    }
 
    
}
