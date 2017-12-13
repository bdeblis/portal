/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pwc.us.common.model.quoterequest;

import com.pwc.us.common.model.ContactBO;

/**
 *
 * @author Roger Turnau - <roger.turnau@us.pwc.com>
 */
public class AccountQR {
    private ContactBO accountHolderContact;
    private String busOpsDesc;

    public ContactBO getAccountHolderContact() {
        return accountHolderContact;
    }

    public void setAccountHolderContact(ContactBO accountHolderContact) {
        this.accountHolderContact = accountHolderContact;
    }

    public String getBusOpsDesc() {
        return busOpsDesc;
    }

    public void setBusOpsDesc(String busOpsDesc) {
        this.busOpsDesc = busOpsDesc;
    }
    
}
