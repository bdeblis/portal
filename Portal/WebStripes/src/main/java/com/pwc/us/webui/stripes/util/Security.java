package com.pwc.us.webui.stripes.util;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.subject.Subject;

/**
 *
 * @author Roger
 */
public class Security {
    
    private static final String AGENT_ROLE = "agent";
    private static final String POLICYHOLDER_ROLE = "policyholder";
    private static final String CASHIER_ROLE = "cashier";
    
    /**
     * This function returns false the logged-in user
     * is an agent.
     */
    public static boolean checkIfAgent() {
        Subject currentUser = SecurityUtils.getSubject();
        try {
            currentUser.checkRole(AGENT_ROLE);
            return true;
        } catch (AuthorizationException e){
            return false;
        }
    }
    
    public static boolean checkIfPolicyholder() {
        Subject currentUser = SecurityUtils.getSubject();
        try {
            currentUser.checkRole(POLICYHOLDER_ROLE);
            return true;
        } catch (AuthorizationException e){
            return false;
        }
    }
    
    public static boolean checkIfCashier() {
        Subject currentUser = SecurityUtils.getSubject();
        try {
            currentUser.checkRole(CASHIER_ROLE);
            return true;
        } catch (AuthorizationException e){
            return false;
        }
    }
    
}
