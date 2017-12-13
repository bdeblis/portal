package com.pwc.us.common;

import com.pwc.us.common.exception.CsoAuthException;
import com.pwc.us.common.exception.CsoBadUserPassStringException;
import com.pwc.us.common.exception.CsoDuplicateUserException;
import com.pwc.us.common.exception.CsoLoginNoAuthException;
import com.pwc.us.common.exception.CsoRegistrationPasswordInvalidException;
import com.pwc.us.common.exception.CsoRegistrationUsernameInvalidException;
import com.pwc.us.common.exception.CsoUserUpdateException;
import com.pwc.us.common.model.PasswordRecover;
import com.pwc.us.common.model.RegistrationInfo;
import com.pwc.us.common.model.User;

/**
 * The Authentication API for the Portal. Currently implemented in 
 * the Authentication Module, interfacing with ApacheDS.
 * @author Roger Turnau - <roger.turnau@us.pwc.com>
 */
public interface Authentication {
    /**
     * The main login procedure. Accepts a username and password, and returns
     * a User object. 
     * @param login
     * @param password
     * @return a User object that can be specialized to either Agent or Policyholder
     * @throws CsoLoginNoAuthException 
     */
    public User login(String login, String password) throws CsoLoginNoAuthException;
    
    /**
     * Checks if the login ID belongs to an agent.
     * @param login
     * @return
     * @throws CsoAuthException 
     */
    public boolean isAgent(String login) throws CsoAuthException;
    
    /**
     * Checks if the login ID belongs to a policyholder
     * @param login
     * @return
     * @throws CsoAuthException 
     */
    public boolean isPolicyholder(String login) throws CsoAuthException;
    
        /**
     * Checks if the login ID belongs to a cashier
     * @param login
     * @return
     * @throws CsoAuthException 
     */
    public boolean isCashier(String login) throws CsoAuthException;
    
    /**
     * Checks to see if this account is locked.
     * @param login
     * @return
     * @throws CsoAuthException 
     */
    public boolean isAccountLocked(String login) throws CsoAuthException;
    
    /**
     * Retrieves all information necessary to recover a user's password
     * @param login
     * @return
     * @throws CsoAuthException 
     */
    public PasswordRecover getPasswordRecoveryInfo(String login) 
            throws CsoAuthException;
    
    /**
     * Sets the following three attributes in the authentication server:
     *    1) Sets the secure password recovery token
     *    2) Sets the recovery token expiration date
     *    3) Locks the account so that the current password no longer works.
     * @param login
     * @throws CsoAuthException 
     */
    public void setPasswordRecoveryInfo(PasswordRecover recover)
            throws CsoAuthException;
    
    /**
     * Unlocks the user's account, clears the user's security token, 
     * and clears the token expiration date.
     * @param login 
     * @throws CsoAuthException
     */
    public void clearPasswordRecoveryInfo(String login)
            throws CsoAuthException;
    
    /**
     * Sets the user's password to a new password.
     * @param login
     * @param newPassword 
     */
    public void changeUserPassword(String login, String newPassword)
            throws CsoAuthException;
    
    /**
     * Sets the Ok.gov customer ID in the policyholder's record.
     * @param login
     * @param okGovId
     * @throws CsoUserUpdateException 
     */
    public void setOkGovCustomerId(String login, String okGovId) throws CsoUserUpdateException;
    
    /**
     * Connect to the server that provides authentication services.
     * @return
     * @throws CsoAuthException 
     */
    public void connectToServer() throws CsoAuthException;
    
    /**
     * Updates a User record with new attributes.
     * @param user
     * @throws CsoAuthException 
     */
    public void updateUser(User user) throws CsoUserUpdateException;
    
    /**
     * Updates a User record with new attributes.
     * @param user
     * @throws CsoAuthException 
     * @throws CsoDuplicateUserException -- if the registration already exists
     */
    public void registerUser(RegistrationInfo info) throws CsoBadUserPassStringException, CsoRegistrationUsernameInvalidException, CsoRegistrationPasswordInvalidException,CsoAuthException, CsoDuplicateUserException;
    
    /**
     * Deletes a user from the LDAP server
     * @param login
     * @throws CsoAuthException 
     */
    public void deleteUser(String login) throws CsoAuthException;
    
    /**
     * Shuts down the connection pool. 
     */
    public void shutDownConnectionPool();
    
}
