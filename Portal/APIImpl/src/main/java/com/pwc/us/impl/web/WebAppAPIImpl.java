package com.pwc.us.impl.web;

import com.google.inject.Inject;
import com.pwc.us.common.Authentication;
import com.pwc.us.common.GwRegistrationService;
import com.pwc.us.common.WebAppAPI;
import com.pwc.us.common.exception.CsoAuthException;
import com.pwc.us.common.exception.CsoBadUserPassStringException;
import com.pwc.us.common.exception.CsoDuplicateUserException;
import com.pwc.us.common.exception.CsoPasswordRecoveryException;
import com.pwc.us.common.exception.CsoRegistrationPasswordInvalidException;
import com.pwc.us.common.exception.CsoRegistrationUsernameInvalidException;
import com.pwc.us.common.exception.CsoUserUpdateException;
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
import com.pwc.us.common.utils.JndiUtils;
import java.io.IOException;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.joda.time.DateTime;

/**
 *
 * @author Roger Turnau - <roger.turnau@us.pwc.com>
 */
public class WebAppAPIImpl implements WebAppAPI {

    @Inject
    private GwRegistrationService service;
    @Inject
    private Authentication authService;
    
    private static final int DAYS_TO_PWD_LINK_EXPIRATION = JndiUtils.getNumDaysToPwdLinkExpiration();

    public void loginPolicyholder(Policyholder ph) throws CsoAuthException, GwIntegrationException {
 
        try {
            String pcUser = service.gwAuthorizePh(ph);
            if (pcUser.equals("-1")) {
                throw new CsoAuthException("Policyholder does not have a valid account in PolicyCenter");
            }
            ph.setPolicyCenterAccountId(pcUser);
            authService.updateUser(ph);
        } catch (CsoUserUpdateException e) {
            throw new CsoAuthException("unable to login policyholder " + ph.getLogin(), e);
        }
    }
    
        public void loginCashier(Cashier cashier, String name, String password) throws CsoAuthException, GwIntegrationException {

         try {
            String pcUser = service.gwAuthorizePh(cashier,name,password);
            if (pcUser.equals("-1")) {
                throw new CsoAuthException("Cashier does not have a valid account in PolicyCenter");
            }
            cashier.setPolicyCenterAccountId(pcUser);
            authService.updateUser(cashier);
        } catch (CsoUserUpdateException e) {
            throw new CsoAuthException("unable to login cashier " + cashier.getLogin(), e);
        }
    }

    public String loginAgentToPc(Agent agent) throws GwIntegrationException, GwUserNotFoundInPcException {
        String securityToken = service.isAgentActiveInPc(agent);
        return securityToken;
    }
    
    public String loginAgentToBc(Agent agent) throws GwIntegrationException, GwUserNotFoundInBcException {
        String securityToken = service.isAgentActiveInBc(agent);
        return securityToken;
    }
    
    public String loginAgentToCc(Agent agent) throws GwIntegrationException, GwUserNotFoundInCcException {
        String securityToken = service.isAgentActiveInCc(agent);
        return securityToken;
    }
    

    public boolean isAccountLocked(String login) throws CsoAuthException {
        return authService.isAccountLocked(login);
    }

    public void registerPolicyholder(RegistrationInfo regInfo) throws GwIntegrationException, GwUserNotFoundInPcException,
    CsoBadUserPassStringException,  CsoRegistrationUsernameInvalidException,    CsoRegistrationPasswordInvalidException,  CsoAuthException, CsoDuplicateUserException {
        Policyholder ph = new Policyholder(regInfo.getUserName(), regInfo.getFirstName(), 
                regInfo.getLastName(), regInfo.getEmail(), regInfo.getPhoneNumber(), 
                regInfo.getChallengeQuestion(), regInfo.getChallengeAnswer(), null,
                regInfo.getPhPolicyNumber(), regInfo.getPhCompanyName(), null, null, false);
        
        String pcUser = service.gwAuthorizePh(ph);
        if (pcUser.equals("-1")) {
            throw new CsoAuthException("Policyholder does not have a valid account in PolicyCenter");
        }
        authService.registerUser(regInfo);
    }

    public void registerAgent(RegistrationInfo regInfo)
            throws CsoBadUserPassStringException,GwIntegrationException, GwUserNotFoundInPcException, GwUserAlreadyRegisteredException,
        CsoRegistrationUsernameInvalidException, CsoRegistrationPasswordInvalidException,   CsoAuthException, CsoDuplicateUserException {

        service.completeAgentRegistration(regInfo);
        authService.registerUser(regInfo);
    }
    
    public void deleteUserFromAuthService(String userName) throws CsoAuthException {
        authService.deleteUser(userName);
    }
    
    public PasswordRecover getPasswordRecoveryInfo(String login) throws CsoAuthException {
        return authService.getPasswordRecoveryInfo(login);
    }
    
    public void shutDownAuthService() {
        authService.shutDownConnectionPool();
    }

    public void sendPasswordRecoveryEmail(PasswordRecover recover) 
            throws CsoAuthException, CsoPasswordRecoveryException {
        String token = createPwRecoverSecurityToken();
        recover.setSecurityToken(token);
        recover.setTokenExpiration((new DateTime()).plusDays(DAYS_TO_PWD_LINK_EXPIRATION));
        authService.setPasswordRecoveryInfo(recover);

        try {
            PasswordRecoveryEmail.sendPasswordRecoveryEmail(recover);
        } catch (IOException e) {
            String message = "Unable to send recovery email -- IOException";
            throw new CsoPasswordRecoveryException(message, e);
        }
        
        
    }

    public void resetPassword(String login, String newPassword) throws CsoAuthException {
        authService.clearPasswordRecoveryInfo(login);
        authService.changeUserPassword(login, newPassword);
        
    }
    
    private String createPwRecoverSecurityToken() {
        SecureRandomNumberGenerator rand = new SecureRandomNumberGenerator();
        String secToken = rand.nextBytes(32).toBase64();
        return secToken;

    }


}
