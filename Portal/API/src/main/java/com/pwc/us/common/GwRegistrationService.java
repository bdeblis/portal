/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pwc.us.common;

import com.pwc.us.common.exception.GwIntegrationException;
import com.pwc.us.common.exception.GwUserAlreadyRegisteredException;
import com.pwc.us.common.exception.GwUserNotFoundInBcException;
import com.pwc.us.common.exception.GwUserNotFoundInCcException;
import com.pwc.us.common.exception.GwUserNotFoundInPcException;
import com.pwc.us.common.model.Agent;
import com.pwc.us.common.model.Cashier;
import com.pwc.us.common.model.Policyholder;
import com.pwc.us.common.model.RegistrationInfo;

/**
 *
 * @author Roger Turnau - <roger.turnau@us.pwc.com>
 */
public interface GwRegistrationService {
    /**
     * Authorize the Policyholder against Guidewire PolicyCenter (used for
     * both login and policyholder registration)
     * @param ph
     * @return the PolicyCenter Account number for this policyholder
     */
    public String gwAuthorizePh(Policyholder ph) throws GwIntegrationException;
    
        /**
     * Authorize the Policyholder against Guidewire PolicyCenter (used for
     * both login and policyholder registration)
     * @param ph
     * @return the PolicyCenter Account number for this policyholder
     */
    public String gwAuthorizePh(Cashier cashier,String name, String password) throws GwIntegrationException;
    
    /**
     * Calls Guidewire PolicyCenter to determine if agent is an active user. 
     * If successful, PolicyCenter will return a one-time-use security token.
     * @param agent
     * @return
     * @throws GwIntegrationException 
     */
    public String isAgentActiveInPc(Agent agent) throws GwIntegrationException, GwUserNotFoundInPcException;
    
    public String isAgentActiveInBc(Agent agent) throws GwIntegrationException, GwUserNotFoundInBcException;
    
    public String isAgentActiveInCc(Agent agent) throws GwIntegrationException, GwUserNotFoundInCcException;
    
    /**
     * Allows the user, at registration time, to confirm their registration
     * against PolicyCenter. In order to successfully validate, the agent
     * must have an active user account in PolicyCenter, and the userToken entered
     * on the Registration form must match the token in PolicyCenter.
     * @param regInfo
     * @return
     * @throws GwIntegrationException
     * @throws GwUserNotFoundInPcException
     * @throws GwUserAlreadyRegisteredException 
     */
    public boolean completeAgentRegistration(RegistrationInfo regInfo) 
            throws GwIntegrationException, GwUserNotFoundInPcException, GwUserAlreadyRegisteredException;
    
    /**
     * Checks if a policy number is valid. 
     * @param policyNumber
     * @return
     * @throws GwIntegrationException 
     */
    public boolean checkPolicyNumber(String policyNumber) throws GwIntegrationException;
    
    
}
