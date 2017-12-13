package com.pwc.us.authentication;

import com.google.inject.Singleton;
import com.pwc.us.common.utils.JndiUtils;
import com.pwc.us.authentication.model.LdapAgent;
import com.pwc.us.authentication.model.LdapCashier;
import com.pwc.us.authentication.model.LdapPerson;
import com.pwc.us.authentication.model.LdapPolicyholder;
import com.pwc.us.common.Authentication;
import com.pwc.us.common.exception.CsoAuthException;
import com.pwc.us.common.exception.CsoBadUserPassStringException;
import com.pwc.us.common.exception.CsoDuplicateUserException;
import com.pwc.us.common.exception.CsoLoginNoAuthException;
import com.pwc.us.common.exception.CsoRegistrationPasswordInvalidException;
import com.pwc.us.common.exception.CsoRegistrationUsernameInvalidException;
import com.pwc.us.common.exception.CsoUserUpdateException;
import com.pwc.us.common.exception.UserCreationException;
import com.pwc.us.common.model.Agent;
import com.pwc.us.common.model.Cashier;
import com.pwc.us.common.model.PasswordRecover;
import com.pwc.us.common.model.Policyholder;
import com.pwc.us.common.model.RegistrationInfo;
import com.pwc.us.common.model.User;
import com.unboundid.ldap.sdk.BindResult;
import com.unboundid.ldap.sdk.LDAPConnection;
import com.unboundid.ldap.sdk.LDAPConnectionPool;
import com.unboundid.ldap.sdk.LDAPException;
import com.unboundid.ldap.sdk.LDAPResult;
import com.unboundid.ldap.sdk.Modification;
import com.unboundid.ldap.sdk.ModificationType;
import com.unboundid.ldap.sdk.ModifyRequest;
import com.unboundid.ldap.sdk.ResultCode;
import com.unboundid.ldap.sdk.SearchResult;
import com.unboundid.ldap.sdk.SearchResultEntry;
import com.unboundid.ldap.sdk.SearchScope;
import com.unboundid.ldap.sdk.persist.LDAPPersister;
import java.util.ArrayList;
import java.util.List;
import javax.net.SocketFactory;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Roger Turnau - <roger.turnau@us.pwc.com>
 */
@Singleton
public class AuthenticationImpl implements Authentication {

    private static String LDAP_ADMIN = JndiUtils.getLdapAdmin();
    private static String LDAP_ADMIN_PASS = JndiUtils.getLdapAdminPass();
    private static String LDAP_URL = JndiUtils.getLdapUrl();
    private static int LDAP_PORT = JndiUtils.getLdapPort();
    private static int NUM_CONNECTIONS = JndiUtils.getNumConnections();
    private static final String CSO_AGENT_OBJECT_CLASS = "csoAgent";
    private static final String CSO_CASHIER_OBJECT_CLASS = "csoCashier";
    private static final String CSO_POLICYHOLDER_OBJECT_CLASS = "csoPolicyholder";
    private static final String CSO_PASS_SECURITY_TOKEN = "csoPassChgSecurityToken";
    private static final String CSO_PASS_EXPIR_DATE = "csoPassChgExpirDate";
    private static final String CSO_ACCT_LOCKED = "csoAccountLocked";
    private static final String USER_PASSWORD = "userPassword";
    private static final String OK_GOV_USER_ID = "csoOkGovUserId";
    private static final String CSO_USER_PARENT_DN = "ou=users,o=compsourceok,dc=com";
    private static LDAPConnectionPool conPool = null;
    private static Logger logger = LoggerFactory.getLogger(AuthenticationImpl.class);

    public AuthenticationImpl() {
    }

    /**
     * Connects to the Portal's Authentication LDAP server.
     *
     * @throws LDAPException
     */
    public void connectToServer() throws CsoAuthException {
        if (AuthenticationImpl.conPool == null) {
            try {
                SocketFactory sf = SocketFactory.getDefault();
                LDAPConnection con = new LDAPConnection(sf, LDAP_URL, LDAP_PORT);

                AuthenticationImpl.conPool = new LDAPConnectionPool(con, NUM_CONNECTIONS);
                BindResult res = AuthenticationImpl.conPool.bind(LDAP_ADMIN, LDAP_ADMIN_PASS);
                ResultCode rc = res.getResultCode();
                if (rc.intValue() != 0) {
                    String msg = "Unable to authenticate into LDAP Server. Please check configuration for admin DN";
                    logger.error(msg);
                    throw new CsoAuthException(msg);
                }
            } catch (LDAPException ex) {
                String msg = "unable to connect to LDAP server";
                logger.error(msg, ex);
                throw new CsoAuthException(msg, ex);
            }
        }
    }

    public boolean bindUser(String login, String password) throws LDAPException {
        boolean ret = false;
        LDAPConnection con = null;
        String dn = getDnForUid(login);
        try {
            con = conPool.getConnection();
            refreshConnection(con);
            BindResult br = con.bind(dn, password);
            return (br.getResultCode().intValue() == 0);
        } catch (LDAPException e) {
            if (e.getResultCode() == ResultCode.INVALID_CREDENTIALS) {
                return false;
            }
            throw e;
        } finally {
            con.close();
        }
    }
    
    public boolean isCashier(String login) throws CsoAuthException {
        boolean ret = false;
        try {
            ret = isObjectClass(login, CSO_CASHIER_OBJECT_CLASS);
        } catch (LDAPException e) {
            throw new CsoAuthException("could not determine if login is cashier", e);
        }
        return ret;
    }

    public boolean isAgent(String login) throws CsoAuthException {
        boolean ret = false;
        try {
            ret = isObjectClass(login, CSO_AGENT_OBJECT_CLASS);
        } catch (LDAPException e) {
            throw new CsoAuthException("could not determine if login is agent", e);
        }
        return ret;
    }

    public boolean isPolicyholder(String login) throws CsoAuthException {
        boolean ret = false;
        try {
            ret = isObjectClass(login, CSO_POLICYHOLDER_OBJECT_CLASS);
        } catch (LDAPException e) {
            throw new CsoAuthException("could not determine if login is a policyholder", e);
        }
        return ret;
    }
    
    public boolean isAccountLocked(String login) throws CsoAuthException {
        boolean ret = false;
        try {
            SearchResultEntry entry = getUserRecord(login);
            LdapPerson ldp = LdapPerson.decode(entry);
            if (ldp.getCsoAccountLocked() != null) {
                ret = ldp.getCsoAccountLocked();
            }
        } catch (LDAPException e) {
            throw new CsoAuthException("Unable to check if user account " + login + " is locked.", e);
        }
        return ret;
    }

    public PasswordRecover getPasswordRecoveryInfo(String login) throws CsoAuthException {
        PasswordRecover recover = null;
        try {
            SearchResultEntry entry = getUserRecord(login);
            if (entry != null) {
                recover = new PasswordRecover();
                recover.setUsername(login);
                LdapPerson ldp = LdapPerson.decode(entry);
                recover.setQuestion(ldp.getFirstCsoChallengeQuestion());
                recover.setAnswer(ldp.getFirstCsoChallengeAnswer());
                recover.setFullName(ldp.getFirstGivenName() + " " + ldp.getFirstSn());
                recover.setEmail(ldp.getFirstMail());
                if (ldp.getFirstCsoPassChgSecurityToken() != null) {
                    recover.setSecurityToken(ldp.getFirstCsoPassChgSecurityToken());
                }
                if (ldp.getCsoPassChgExpirDate() != null) {
                    recover.setTokenExpiration(new DateTime(ldp.getCsoPassChgExpirDate()));
                }
                if (ldp.getCsoAccountLocked() != null) {
                    recover.setAccountLocked(ldp.getCsoAccountLocked());
                }
            }
        } catch (LDAPException e) {
            throw new CsoAuthException("unable to retrieve user record for " + login, e);
        }
        return recover;
    }

    /**
     * Sets the following three attributes in the authentication server: 1) Sets
     * the secure password recovery token 2) Sets the recovery token expiration
     * date 3) Locks the account so that the current password no longer works.
     *
     * @param recover - password recovery info, which must have a username,
     * security token, and token expiration date already populated
     * @throws CsoAuthException
     */
    public void setPasswordRecoveryInfo(PasswordRecover recover) throws CsoAuthException {
        LDAPConnection con = null;
        try {
            con = AuthenticationImpl.conPool.getConnection();
            refreshConnection(con);
            SearchResultEntry entry = getUserRecord(recover.getUsername());
            LDAPPersister<LdapPerson> persister
                    = LDAPPersister.getInstance(LdapPerson.class);
            LdapPerson ldp = persister.decode(entry);
            ldp.setCsoPassChgSecurityToken(recover.getSecurityToken());
            ldp.setCsoAccountLocked(true);
            ldp.setCsoPassChgExpirDate(recover.getTokenExpiration().toDate());
            persister.modify(ldp, con, null, false);

        } catch (LDAPException e) {
            throw new CsoAuthException(
                    "Unable to set password recovery information for user " + recover.getUsername(), e);
        } finally {
            if (con != null) {
                con.close();
            }
        }
    }

    public void clearPasswordRecoveryInfo(String login) throws CsoAuthException {
        LDAPConnection con = null;
        try {
            con = AuthenticationImpl.conPool.getConnection();
            refreshConnection(con);
            String dn = getDnForUid(login);
            final List<Modification> mods = new ArrayList<Modification>();
            mods.add(new Modification(ModificationType.DELETE, CSO_PASS_SECURITY_TOKEN));
            mods.add(new Modification(ModificationType.DELETE, CSO_PASS_EXPIR_DATE));
            mods.add(new Modification(ModificationType.DELETE, CSO_ACCT_LOCKED));
            final ModifyRequest modRequest = new ModifyRequest(dn, mods);
            LDAPResult result = con.modify(modRequest);
            if (result.getResultCode().intValue() != 0) {
                throw new CsoAuthException("unable to delete attributes from dn " + dn);
            }
        } catch (LDAPException e) {
            throw new CsoAuthException(
                    "Unable to clear password recovery information for user " + login, e);
        } finally {
            if (con != null) {
                con.close();
            }
        }
    }

    public void changeUserPassword(String login, String newPassword)
            throws CsoAuthException {
        LDAPConnection con = null;
        try {
            con = AuthenticationImpl.conPool.getConnection();
            refreshConnection(con);

            String dn = getDnForUid(login);
            final List<Modification> mods = new ArrayList<Modification>();
            mods.add(new Modification(ModificationType.REPLACE, USER_PASSWORD, newPassword));
            final ModifyRequest modRequest = new ModifyRequest(dn, mods);
            LDAPResult result = con.modify(modRequest);
            if (result.getResultCode().intValue() != 0) {
                throw new CsoAuthException("unable to change password for LDAP entry " + dn);
            }
            
        } catch (LDAPException e) {
            String message = "Unable to set new password for user " + login;
            logger.error(message, e);
            throw new CsoAuthException(message, e);
        } finally {
            if (con != null) {
                con.close();
            }
        }
    }
    
    public void setOkGovCustomerId(String login, String okGovId) throws CsoUserUpdateException {
        LDAPConnection con = null;
        try {
            con = AuthenticationImpl.conPool.getConnection();
            refreshConnection(con);

            String dn = getDnForUid(login);
            final List<Modification> mods = new ArrayList<Modification>();
            mods.add(new Modification(ModificationType.REPLACE, OK_GOV_USER_ID, okGovId));
            final ModifyRequest modRequest = new ModifyRequest(dn, mods);
            LDAPResult result = con.modify(modRequest);
            if (result.getResultCode().intValue() != 0) {
                throw new CsoUserUpdateException("unable to add ok.gov user ID for LDAP entry " + dn);
            }
            
        } catch (LDAPException e) {
            String message = "unable to add ok.gov user ID for user " + login;
            logger.error(message, e);
            throw new CsoUserUpdateException(message, e);
        } finally {
            if (con != null) {
                con.close();
            }
        }
    }    

    @Override
    public User login(String login, String password)
            throws CsoLoginNoAuthException {
        User retUser = null;
        try {
            if (bindUser(login, password)) {
                SearchResultEntry entry = getUserRecord(login);
                if (isAgent(login)) {
                    LdapAgent lda = LdapAgent.decode(entry);
                    User<Agent> user = LdapAgent.translateLdapIntoAgent(lda);
                    retUser = user;
                } else if (isPolicyholder(login)) {
                    LdapPolicyholder ldp = LdapPolicyholder.decode(entry);
                    User<Policyholder> user = LdapPolicyholder.translateLdapIntoPolicyholder(ldp);
                    retUser = user;
                } else if (isCashier(login)) {
                   LdapCashier ldp = LdapCashier.decode(entry);
                    User <Cashier> user = LdapCashier.translateLdapIntoCashier(ldp);
                    retUser = user;
                }
            }
        } catch (LDAPException e) {
            logger.error("Login failure for agent " + login);
            throw new CsoLoginNoAuthException("unable to complete login for user " + login, e);
        } catch (UserCreationException e) {
            logger.error("Login failure for agent " + login);
            throw new CsoLoginNoAuthException("unable to complete login for user " + login, e);
        } catch (CsoAuthException e) {
            logger.error("Login failure for user " + login);
            throw new CsoLoginNoAuthException("unable to complete login for user " + login, e);
        }

        if (retUser == null) {
            String message = "Login failure. Unable to complete login for user " + login;
            throw new CsoLoginNoAuthException(message);
        }

        return retUser;
    }

    public void updateUser(User user) throws CsoUserUpdateException {
        LDAPConnection con = null;
        try {
            SearchResultEntry entry = getUserRecord(user.getLogin());
            con = conPool.getConnection();
            refreshConnection(con);
            if (isAgent(user.getLogin())) {
                LDAPPersister<LdapAgent> persister
                        = LDAPPersister.getInstance(LdapAgent.class);

                LdapAgent lda = persister.get(entry.getDN(), con);
                LdapAgent ldaNew = LdapAgent.updateLdapAgent(lda, (Agent) user.getSpecificType());
                persister.modify(ldaNew, con, null, false);
            }

            if (isPolicyholder(user.getLogin())) {
                LDAPPersister<LdapPolicyholder> persister
                        = LDAPPersister.getInstance(LdapPolicyholder.class);

                LdapPolicyholder lda = persister.get(entry.getDN(), con);
                LdapPolicyholder ldaNew = LdapPolicyholder.updateLdapPolicyholder(lda, (Policyholder) user.getSpecificType());
                persister.modify(ldaNew, con, null, false);
            }
            if (isCashier(user.getLogin())) {
                LDAPPersister<LdapCashier> persister
                        = LDAPPersister.getInstance(LdapCashier.class);

                LdapCashier lda = persister.get(entry.getDN(), con);
                LdapCashier ldaNew = LdapCashier.updateLdapCashier(lda, (Cashier) user.getSpecificType());
                persister.modify(ldaNew, con, null, false);
            }
        } catch (LDAPException e) {
            String message = "Unable to update LDAP entry for user " + user.getLogin();
            logger.error(message);
            throw new CsoUserUpdateException(message, e);
        } catch (CsoAuthException e) {
            String message = "Unable to update LDAP entry for user " + user.getLogin();
            logger.error(message);
            throw new CsoUserUpdateException(message, e);
        } finally {
            if (con != null) {
                con.close();
            }
        }
    }

    public void registerUser(RegistrationInfo info) throws CsoBadUserPassStringException, CsoRegistrationUsernameInvalidException, CsoRegistrationPasswordInvalidException ,CsoAuthException, CsoDuplicateUserException {
        LDAPConnection con = null;
        try {
            if (info.getUserName().toLowerCase().contains(info.getPassword().toLowerCase())) {
                logger.error("Password cannot contain parts of the username");
                throw new CsoRegistrationPasswordInvalidException("Password cannot contain parts of the username");
            }
            if (info.getPassword().toLowerCase().contains(info.getUserName().toLowerCase())) {
                logger.error("Username cannot contain parts of the password");
                throw new CsoRegistrationUsernameInvalidException("Username cannot contain parts of the password");
            }

            con = conPool.getConnection();
            refreshConnection(con);
            // make sure we don't already have a user for this login
            if (getUserRecord(info.getUserName()) != null) {
                String message = "The user " + info.getUserName() + " already exists in the system.";
                throw new CsoDuplicateUserException(message);
            }
            if (info.getTypeOfUser() == RegistrationInfo.RegistrationType.Agent) {
                LDAPPersister<LdapAgent> persister
                        = LDAPPersister.getInstance(LdapAgent.class);
                LdapAgent lda = LdapAgent.translateRegInfoIntoAgent(info);
                persister.add(lda, con, CSO_USER_PARENT_DN);
            }

            if (info.getTypeOfUser() == RegistrationInfo.RegistrationType.Policyholder) {
                LDAPPersister<LdapPolicyholder> persister
                        = LDAPPersister.getInstance(LdapPolicyholder.class);
                LdapPolicyholder lph = LdapPolicyholder.translateRegInfoIntoPolicyHolder(info);
                persister.add(lph, con, CSO_USER_PARENT_DN);
            }
            // use change password to make sure password is not changed as plain text.
            changeUserPassword(info.getUserName(), info.getPassword());

        } catch (LDAPException e) {
            String message = "Unable to insert LDAP entry for user " + info.getUserName();
            logger.error(message, e);
            throw new CsoAuthException(message, e);
        } finally {
            if (con != null) {
                con.close();
            }
        }
    }

    public void deleteUser(String login) throws CsoAuthException {
        LDAPConnection con = null;
        try {
            con = conPool.getConnection();
            refreshConnection(con);
            String dn = getDnForUid(login);
            if (dn != null) {
                conPool.delete(dn);
            }
        } catch (LDAPException e) {
            throw new CsoAuthException("Unable to delete LDAP entry for user " + login, e);
        } finally {
            if (con != null) {
                con.close();
            }
        }

    }

    public void setConnection(LDAPConnection con) throws LDAPException {
        AuthenticationImpl.conPool = new LDAPConnectionPool(con, NUM_CONNECTIONS);
    }

    public void shutDownConnectionPool() {
        AuthenticationImpl.conPool.close();
        AuthenticationImpl.conPool = null;
    }

    private String getDnForUid(String login) throws LDAPException {
        SearchResultEntry entry = this.getUserRecord(login);
        String dn = null;
        if (entry != null) {
            dn = entry.getDN();
        }
        return dn;
    }

    private SearchResultEntry getUserRecord(String login) throws LDAPException {
        SearchResultEntry ret;
        LDAPConnection con = AuthenticationImpl.conPool.getConnection();
        refreshConnection(con);

        SearchResult sr = con.search("ou=users,o=compsourceok,dc=com",
                SearchScope.SUB, "(uid=" + login + ")");
        if (sr.getEntryCount() == 0) {
            ret = null;
        } else {
            ret = sr.getSearchEntries().get(0);
        }
        con.close();
        return ret;
    }

    private boolean isObjectClass(String login, String objectClass)
            throws LDAPException {
        boolean ret = false;
        SearchResultEntry entry = this.getUserRecord(login);
        if (entry != null) {
            String[] ocs = entry.getObjectClassValues();
            for (String s : ocs) {
                if (s.equals(objectClass)) {
                    ret = true;
                }
            }
        }
        return ret;
    }

    private void refreshConnection(LDAPConnection con) throws LDAPException {
        con.bind(LDAP_ADMIN, LDAP_ADMIN_PASS);
    }

}
