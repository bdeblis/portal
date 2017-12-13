package com.pwc.us.common;

import com.pwc.us.common.exception.CsoAuthException;
import com.pwc.us.common.exception.CsoBadUserPassStringException;
import com.pwc.us.common.exception.CsoDuplicateUserException;
import com.pwc.us.common.exception.CsoPasswordRecoveryException;
import com.pwc.us.common.exception.CsoRegistrationPasswordInvalidException;
import com.pwc.us.common.exception.CsoRegistrationUsernameInvalidException;
import com.pwc.us.common.exception.GwIntegrationException;
import com.pwc.us.common.exception.GwUserAlreadyRegisteredException;
import com.pwc.us.common.exception.GwUserNotFoundInBcException;
import com.pwc.us.common.exception.GwUserNotFoundInCcException;
import com.pwc.us.common.exception.GwUserNotFoundInPcException;
import com.pwc.us.common.model.Agent;
import com.pwc.us.common.model.Cashier;
import com.pwc.us.common.model.PasswordRecover;
import com.pwc.us.common.model.Policyholder;
import com.pwc.us.common.model.RegistrationInfo;


/**
 *
 * @author Roger Turnau - <roger.turnau@us.pwc.com>
 */
public interface WebAppAPI {
    
    /**
     * Manages the business logic to log in a policyholder. 
     * @param ph
     * @throws CsoAuthException
     * @throws GwIntegrationException 
     */
    public void loginPolicyholder(Policyholder ph) throws CsoAuthException, GwIntegrationException;
   
     /**
     * Manages the business logic to log in a cashier. 
     * @param cashier
     * @throws CsoAuthException
     * @throws GwIntegrationException 
     */
    public void loginCashier(Cashier cashier, String name, String password) throws CsoAuthException, GwIntegrationException;
    
    
    
    /**
     * Manages business logic to log in an Agent. This is done by querying the 
     * agent's status in PolicyCenter, receiving a one-time-use security token
     * from PolicyCenter, and using that token to authenticate the user 
     * into PolicyCenter.
     * @param agent
     * @return
     * @throws GwIntegrationException 
     * @throws GwUserNotFoundInPcException
     */
    public String loginAgentToPc(Agent agent) throws GwIntegrationException, GwUserNotFoundInPcException;
    
    /**
     * The same logic as loginAgentToPc method, generalized out to BC for MGA users.
     * @param agent
     * @return
     * @throws GwIntegrationException
     * @throws GwUserNotFoundInBcException 
     */
    public String loginAgentToBc(Agent agent) throws GwIntegrationException, GwUserNotFoundInBcException;
    
    /**
     * The same logic as loginAgentToPc method, generalized out to CC for MGA users.
     * @param agent
     * @return
     * @throws GwIntegrationException
     * @throws GwUserNotFoundInCcException 
     */
    public String loginAgentToCc(Agent agent) throws GwIntegrationException, GwUserNotFoundInCcException;
    
    /**
     * Manages business logic to register a policyholder
     * @param regInfo
     * @throws GwIntegrationException
     * @throws GwUserNotFoundInPcException
     * @throws CsoAuthException
     * @throws CsoDuplicateUserException 
     */
    public void registerPolicyholder(RegistrationInfo regInfo) 
            throws GwIntegrationException, GwUserNotFoundInPcException, 
      CsoBadUserPassStringException,  CsoRegistrationPasswordInvalidException,CsoRegistrationUsernameInvalidException,    CsoAuthException, CsoDuplicateUserException;
    
    /**
     * Manages business logic to register an agent.
     * @param regInfo
     * @throws GwIntegrationException
     * @throws GwUserNotFoundInPcException
     * @throws GwUserAlreadyRegisteredException
     * @throws CsoAuthException
     * @throws CsoDuplicateUserException 
     */
    public void registerAgent(RegistrationInfo regInfo) 
            throws CsoBadUserPassStringException, GwIntegrationException, GwUserNotFoundInPcException, 
            GwUserAlreadyRegisteredException,
       CsoRegistrationPasswordInvalidException, CsoRegistrationUsernameInvalidException,    CsoAuthException, CsoDuplicateUserException;
    
    /**
     * Deletes a user from the authentication store.
     * @param userName
     * @throws CsoAuthException 
     */
    public void deleteUserFromAuthService(String userName) throws CsoAuthException;
    
    /**
     * Gets all information needed to recover a user's password.
     * @param login
     * @return
     * @throws CsoAuthException 
     */
    public PasswordRecover getPasswordRecoveryInfo(String login) throws CsoAuthException;
    
    /**
     * Updates the user's LDAP information with password recovery attributes,
     * and sends a reset password email to the user.
     * @param recover 
     */
    public void sendPasswordRecoveryEmail(PasswordRecover recover) 
            throws CsoAuthException, CsoPasswordRecoveryException;
    
    /**
     * Changes a user's password, unlocks the user's account, clears the 
     * user's security token, and clears the token expiration date.
     * @param login 
     */
    public void resetPassword(String login, String newPassword) throws CsoAuthException;
    
    /**
     * Checks if a user's account is locked.
     * @param login
     * @return
     * @throws CsoAuthException 
     */
    public boolean isAccountLocked(String login) throws CsoAuthException;
    
    /**
     * Closes all connections to the authentication service.
     */
    public void shutDownAuthService();
}
