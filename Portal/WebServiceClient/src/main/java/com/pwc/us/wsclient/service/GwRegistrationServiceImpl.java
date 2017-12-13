package com.pwc.us.wsclient.service;


import com.pwc.us.common.GwRegistrationService;
import com.pwc.us.common.exception.GwIntegrationException;
import com.pwc.us.common.exception.GwUserAlreadyRegisteredException;
import com.pwc.us.common.exception.GwUserNotFoundInBcException;
import com.pwc.us.common.exception.GwUserNotFoundInCcException;
import com.pwc.us.common.exception.GwUserNotFoundInPcException;
import com.pwc.us.common.model.Agent;
import com.pwc.us.common.model.Policyholder;
import com.pwc.us.common.model.Cashier;
import com.pwc.us.common.model.RegistrationInfo;
import com.pwc.us.wsclient.agentauth.PcAgentAuthentication;
import com.pwc.us.wsclient.agentauth.BcAgentAuthentication;
import com.pwc.us.wsclient.agentauth.CcAgentAuthentication;
import com.pwc.us.wsclient.phauth.PhAuthentication;

/**
 * Implementation of the Guidewire Integration code for 
 * Policyholder and Agent login.
 * @author Roger Turnau - <roger.turnau@us.pwc.com>
 */
public class GwRegistrationServiceImpl implements GwRegistrationService {
    
    PcAgentAuthentication pcAgentAuth = new PcAgentAuthentication();
    BcAgentAuthentication bcAgentAuth = new BcAgentAuthentication();
    CcAgentAuthentication ccAgentAuth = new CcAgentAuthentication();
    PhAuthentication phAuth = new PhAuthentication();
    
      public String gwAuthorizePh(Cashier ph,String name, String password) throws GwIntegrationException {
        String accountNumber = phAuth.queryCashierAuth(name,password);
        if (accountNumber.equals("-1")) {
            throw new GwIntegrationException("Cashier " + ph.getLogin() + 
                    " did not supply valid Guidewire Policy Number");
        }
        return accountNumber;
    }

    

    public String gwAuthorizePh(Policyholder ph) throws GwIntegrationException {
        String accountNumber = phAuth.queryAcctUsingPolicyNum(ph.getPolicyNumber());
        if (accountNumber.equals("-1")) {
            throw new GwIntegrationException("Policyholder " + ph.getLogin() + 
                    " did not supply valid Guidewire Policy Number");
        }

        return accountNumber;
    }

    public String isAgentActiveInPc(Agent agent) throws GwIntegrationException, GwUserNotFoundInPcException {
        String result = pcAgentAuth.isAgentActive(agent.getLogin());
        return result;
    }
    
    public String isAgentActiveInBc(Agent agent) throws GwIntegrationException, GwUserNotFoundInBcException {
        String result = bcAgentAuth.isAgentActive(agent.getLogin());
        return result;
    }
    
    public String isAgentActiveInCc(Agent agent) throws GwIntegrationException, GwUserNotFoundInCcException {
        String result = ccAgentAuth.isAgentActive(agent.getLogin());
        return result;
    }
    
    public boolean completeAgentRegistration(RegistrationInfo regInfo) 
            throws GwIntegrationException, GwUserNotFoundInPcException, GwUserAlreadyRegisteredException {
        boolean result = pcAgentAuth.completeUserRegistration(regInfo.getUserName(), regInfo.getAgentRegistrationToken(),regInfo.getAgentAgencyNumber());
        return result;
    }
    
    public boolean checkPolicyNumber(String policyNumber) throws GwIntegrationException {
        boolean res = false;
        String result = phAuth.queryAcctUsingPolicyNum(policyNumber);
        if (!result.equals("-1")) {
            res = true;
        }
        return res;
    }
    
}
