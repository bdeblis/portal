package com.pwc.us.common.model;

import java.io.Serializable;
import java.util.Date;

/**
 * The basic model object for a user for the CompSource Portal.
 * It is abstract because users must be either Agent or Policyholder
 * types.
 * 
 * @author Roger Turnau - <roger.turnau@us.pwc.com>
 */
public abstract class User<T extends User> implements Serializable {
    
    protected String login;
    protected String firstName;
    protected String lastName;
    protected String email;
    protected String phone;
    protected String challengeQuestion;
    protected String challengeAnswer;
    protected Date lastLogin;
    protected boolean accountLocked;
    
    public User() {}
    
    public User(String login, String firstName,
            String lastName, String email, String phone,
            String challengeQuestion, String challengeAnswer, Date lastLogin,
            boolean accountLocked) {
        this.login = login;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.challengeQuestion = challengeQuestion;
        this.challengeAnswer = challengeAnswer;
        this.lastLogin = lastLogin;
        this.accountLocked = accountLocked;
    }
    
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getChallengeQuestion() {
        return challengeQuestion;
    }

    public void setChallengeQuestion(String challengeQuestion) {
        this.challengeQuestion = challengeQuestion;
    }

    public String getChallengeAnswer() {
        return challengeAnswer;
    }

    public void setChallengeAnswer(String challengeAnswer) {
        this.challengeAnswer = challengeAnswer;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }
    
    public boolean isAccountLocked() {
        return accountLocked;
    }

    public void setAccountLocked(boolean accountLocked) {
        this.accountLocked = accountLocked;
    }
    
    public T getSpecificType() {
        return (T)this;
    }
    
}
