/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pwc.us.common.model;

import java.io.Serializable;
import java.util.Date;

/**
 * The basic model for a CompSource Agent. Inherits from User.
 *
 * @author Roger Turnau - <roger.turnau@us.pwc.com>
 */
public class Agent extends User implements Serializable {

    protected long agencyNumber;
    protected String agencyName;

    public Agent() {
    }

    public Agent(String login, String firstName,
            String lastName, String email, String phone,
            String challengeQuestion, String challengeAnswer, Date lastLogin,
            long agencyNumber, String agencyName, boolean accountLocked) {
        super(login, firstName, lastName, email, phone,
                challengeQuestion, challengeAnswer, lastLogin,
                accountLocked);
        this.agencyNumber = agencyNumber;
        this.agencyName = agencyName;
    }

    public long getAgencyNumber() {
        return agencyNumber;
    }

    public void setAgencyNumber(long agencyNumber) {
        this.agencyNumber = agencyNumber;
    }

    public String getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }
}
