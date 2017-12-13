package com.pwc.us.common.model;

import com.pwc.us.common.utils.FilterUtils;

/**
 * Registration form info.
 *
 * @author Roger Turnau - <roger.turnau@us.pwc.com>
 */
public class RegistrationInfo {

    public enum RegistrationType {
        Agent,
        Policyholder
    }
    
    private RegistrationType typeOfUser;
    private String userName;
    private String password;
    private String passwordAgain;
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String phPolicyNumber;
    private String phCompanyName;
    private String agentAgencyNumber;
    private String agentAgencyName;
    private String agentRegistrationToken;
    private String challengeQuestion;
    private String challengeAnswer;
    
    public RegistrationType getTypeOfUser() {
        return this.typeOfUser;
    }
    
    public void setTypeOfUser(RegistrationType typeOfUser) {
        this.typeOfUser = typeOfUser;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String username) {
        this.userName = FilterUtils.escapeLDAPSearchFilter(username);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordAgain() {
        return passwordAgain;
    }

    public void setPasswordAgain(String passwordAgain) {
        this.passwordAgain = passwordAgain;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhPolicyNumber() {
        return phPolicyNumber;
    }

    public void setPhPolicyNumber(String phPolicyNumber) {
        this.phPolicyNumber = phPolicyNumber;
    }

    public String getPhCompanyName() {
        return phCompanyName;
    }

    public void setPhCompanyName(String phCompanyName) {
        this.phCompanyName = phCompanyName;
    }

    public String getAgentAgencyNumber() {
        return agentAgencyNumber;
    }

    public void setAgentAgencyNumber(String agentAgencyNumber) {
        this.agentAgencyNumber = agentAgencyNumber;
    }

    public String getAgentAgencyName() {
        return agentAgencyName;
    }

    public void setAgentAgencyName(String agentAgencyName) {
        this.agentAgencyName = agentAgencyName;
    }

    public String getAgentRegistrationToken() {
        return agentRegistrationToken;
    }

    public void setAgentRegistrationToken(String agentRegistrationToken) {
        this.agentRegistrationToken = agentRegistrationToken;
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

}
