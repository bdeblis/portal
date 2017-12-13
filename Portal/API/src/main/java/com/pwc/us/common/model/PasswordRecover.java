package com.pwc.us.common.model;

import com.pwc.us.common.utils.FilterUtils;
import org.joda.time.DateTime;

/**
 *
 * @author Roger Turnau - <roger.turnau@us.pwc.com>
 */
public class PasswordRecover {

    private String username;
    private String email;
    private String question;
    private String answer;
    private String fullName;
    private String securityToken;
    private DateTime tokenExpiration;
    private StringBuffer host;
    private boolean accountLocked;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = FilterUtils.escapeLDAPSearchFilter(username);
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getFullName() {
        return this.fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSecurityToken() {
        return securityToken;
    }

    public void setSecurityToken(String securityToken) {
        this.securityToken = securityToken;
    }

    public void setHost(StringBuffer host) {
        this.host = host;
    }

    public StringBuffer getHost() {
        return this.host;
    }

    public DateTime getTokenExpiration() {
        return tokenExpiration;
    }

    public void setTokenExpiration(DateTime tokenExpiration) {
        this.tokenExpiration = tokenExpiration;
    }

    public boolean isAccountLocked() {
        return accountLocked;
    }

    public void setAccountLocked(boolean accountLocked) {
        this.accountLocked = accountLocked;
    }

  
}
