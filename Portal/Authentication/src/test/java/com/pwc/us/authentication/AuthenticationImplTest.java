/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pwc.us.authentication;

import com.pwc.us.authentication.model.LdapAgent;
import com.pwc.us.common.exception.CsoAuthException;
import com.pwc.us.common.exception.CsoBadUserPassStringException;
import com.pwc.us.common.exception.CsoDuplicateUserException;
import com.pwc.us.common.exception.CsoLoginNoAuthException;
import com.pwc.us.common.exception.CsoRegistrationPasswordInvalidException;
import com.pwc.us.common.exception.CsoRegistrationUsernameInvalidException;
import com.pwc.us.common.exception.CsoUserUpdateException;
import com.pwc.us.common.exception.UserCreationException;
import com.pwc.us.common.model.Agent;
import com.pwc.us.common.model.PasswordRecover;
import com.pwc.us.common.model.Policyholder;
import com.pwc.us.common.model.RegistrationInfo;
import com.pwc.us.common.model.User;
import com.unboundid.ldap.listener.InMemoryDirectoryServer;
import com.unboundid.ldap.listener.InMemoryDirectoryServerConfig;
import com.unboundid.ldap.listener.InMemoryListenerConfig;
import com.unboundid.ldap.sdk.LDAPConnection;
import com.unboundid.ldap.sdk.LDAPException;
import com.unboundid.ldap.sdk.persist.LDAPPersistException;
import com.unboundid.ldap.sdk.persist.LDAPPersister;
import com.unboundid.ldap.sdk.schema.Schema;
import com.unboundid.ldif.LDIFException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Roger Turnau - <roger.turnau@us.pwc.com>
 */
public class AuthenticationImplTest {

    private static String CONFIG = "o=compsourceok,dc=com";
    private static String OTHER_BIND = "uid=admin,o=compsourceok,dc=com";
    private static String PASSWORD = "hx9N-L%TgS_%K6#";
    private static String POLICYHOLDER_USERNAME = "ppolicy";
    private static String AGENT_USERNAME = "aguytwo";
    private static String NON_EXISTENT_USERNAME = "itnoexist";
    private static String LISTENER_NAME = "test";
    private static String QUESTION = "What did your face look like before you were born?";
    private static String ANSWER = "Ugly.";
    private static String QUESTION_AGENT = "What is the answer to life, the universe, and everything?";
    private static String ANSWER_AGENT = "42";
    private static String QUESTION_PH = "What were you thinking?";
    private static String ANSWER_PH = "Nothing";
    private static String HOST = "localhost";
    private static int PORT = 11389;
    private static final int DAYS_TO_PWD_LINK_EXPIRATION = 15;
    private static final String OK_GOV_TEST_ID = "12345";
    private static InMemoryDirectoryServer ds = null;

    public AuthenticationImplTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        // Create an in-memory LDAP server
        try {
            InMemoryDirectoryServerConfig config =
                    new InMemoryDirectoryServerConfig(CONFIG);
            config.addAdditionalBindCredentials(OTHER_BIND, PASSWORD);

            try {
                Schema schema = Schema.getSchema("src/test/resources/ldif/compsourceok-schema.ldif");
                Schema schema2 = Schema.mergeSchemas(config.getSchema(), schema);
                config.setSchema(schema2);
//                
                InMemoryListenerConfig listenerConfig =
                        new InMemoryListenerConfig(LISTENER_NAME, null, PORT, null, null, null);
                config.setListenerConfigs(listenerConfig);

            } catch (IOException ex) {
                System.out.println(ex);
            } catch (LDIFException ex) {
                System.out.println(ex);
            }
            AuthenticationImplTest.ds = new InMemoryDirectoryServer(config);
            AuthenticationImplTest.ds.importFromLDIF(false, "src/test/resources/ldif/cso-users.ldif");

            AuthenticationImplTest.ds.startListening(LISTENER_NAME);
        } catch (LDAPException e) {
            fail("unable to set up in-memory server\n" + e);
        }

    }

    @AfterClass
    public static void tearDownClass() {
        AuthenticationImplTest.ds.shutDown(true);
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testConnectToServer() {
        System.out.println("connectToServer");
        AuthenticationImpl instance = new AuthenticationImpl();
        try {
            LDAPConnection con = new LDAPConnection();
            con.connect(HOST, PORT);
            //con.connect(HOST, 10389);
            instance.setConnection(con);
        } catch (LDAPException e) {
            fail(e.toString());
        }
    }

    @Test
    public void testLookupAgentRecord() {
        System.out.println("lookupAgentRecord");
        // skip this test
        if(true)
            return;
        String login = AGENT_USERNAME;
        String password = PASSWORD;
        AuthenticationImpl instance = new AuthenticationImpl();
        getConnection(instance);


        boolean result = false;
        try {
            result = instance.bindUser(login, password);
            assertTrue("could not bind with good password", result);
        } catch (LDAPException e) {
            fail(e.toString());
        }

        password = "badpassword";
        try {
            result = instance.bindUser(login, password);
            assertFalse("successful bind to bad password", result);
        } catch (LDAPException e) {
            fail(e.toString());
        }

    }

    @Test
    public void testIsAgent() {
        System.out.println("isAgent");
        String login = AGENT_USERNAME;
        AuthenticationImpl instance = new AuthenticationImpl();

        getConnection(instance);


        boolean result = false;

        try {
            result = instance.isAgent(login);
            assertTrue("isAgent did not detect agent", result);
        } catch (CsoAuthException e) {
            fail(e.toString());
        }

        login = POLICYHOLDER_USERNAME;
        try {
            result = instance.isAgent(login);
            assertFalse("isAgent detected policyholder as agent", result);
        } catch (CsoAuthException e) {
            fail(e.toString());
        }

        login = NON_EXISTENT_USERNAME;
        try {
            result = instance.isAgent(login);
            assertFalse("isAgent detected nonexistent user as agent", result);
        } catch (CsoAuthException e) {
            fail(e.toString());
        }

    }

    @Test
    public void testIsPolicyholder() {
        System.out.println("isPolicyholder");
        String login = POLICYHOLDER_USERNAME;
        AuthenticationImpl instance = new AuthenticationImpl();

        getConnection(instance);


        boolean result = false;

        try {
            result = instance.isPolicyholder(login);
            assertTrue("isPolicyholder did not detect policyholder", result);
        } catch (CsoAuthException e) {
            fail(e.toString());
        }

        login = AGENT_USERNAME;
        try {
            result = instance.isPolicyholder(login);
            assertFalse("isPolicyholder detected agent as policyholder", result);
        } catch (CsoAuthException e) {
            fail(e.toString());
        }

        login = "jenexistepas";
        try {
            result = instance.isPolicyholder(login);
            assertFalse("isPolicyholder detected non-existent user as policyholder", result);
        } catch (CsoAuthException e) {
            fail(e.toString());
        }

    }

    /**
     * Test of loginAgent method, of class AuthenticationImpl.
     */
    @Test
    public void testLoginAgent() {
        System.out.println("loginAgent");
        // skip test
        if(true)
            return;
        String login = AGENT_USERNAME;
        String password = PASSWORD;
        AuthenticationImpl instance = new AuthenticationImpl();
        getConnection(instance);


        User<Agent> expResult = null;
        try {
            expResult = instance.login(login, password);
        } catch (CsoLoginNoAuthException e) {
            fail(e.toString());
        }

        assertNotNull(expResult);
        assertEquals(expResult.getPhone(), "800-555-1313");
        assertEquals(expResult.getSpecificType().getAgencyName(), "The Agency Agency");
    }

    /**
     * Test of login method, of class AuthenticationImpl.
     */
    @Test
    public void testLoginPolicyholder() {
        System.out.println("loginPolicyholder");
        // skip test
        if(true)
            return;
        String login = POLICYHOLDER_USERNAME;
        String password = PASSWORD;
        AuthenticationImpl instance = new AuthenticationImpl();
        getConnection(instance);


        User<Policyholder> expResult = null;
        try {
            expResult = instance.login(login, password);
        } catch (CsoLoginNoAuthException e) {
            fail(e.toString());
        }

        assertNotNull(expResult);
        assertEquals(expResult.getSpecificType().getCompanyName(), "We Run With Scissors, Inc.");
        assertEquals(expResult.getChallengeQuestion(), "What were you thinking?");

    }

    /**
     * Test of updateUser method, of class AuthenticationImpl.
     */
    @Test
    public void testUpdateUser() {
        System.out.println("updateUser");
        String dn = "cn=Agent Guytwo aguytwo,ou=users,o=compsourceok,dc=com";
        String newQuestion = "What is the answer to the new question?";
        String newAnswer = "How should I know?";
        AuthenticationImpl instance = new AuthenticationImpl();
        LDAPConnection con = null;

        try {
            con = getConnection(instance);

            LDAPPersister<LdapAgent> persister =
                    LDAPPersister.getInstance(LdapAgent.class);

            LdapAgent lda = persister.get(dn, con);
            Agent agent = LdapAgent.translateLdapIntoAgent(lda);
            agent.setChallengeQuestion(newQuestion);
            agent.setChallengeAnswer(newAnswer);
            instance.updateUser(agent);

            con.connect(HOST, PORT);
            LdapAgent lda2 = persister.get(dn, con);
            assertEquals(lda2.getFirstCsoChallengeQuestion(), newQuestion);
            assertEquals(lda2.getFirstCsoChallengeAnswer(), newAnswer);

            agent.setChallengeQuestion(QUESTION_AGENT);
            agent.setChallengeAnswer(ANSWER_AGENT);
            instance.updateUser(agent);
        } catch (LDAPException e) {
            fail(e.toString());
        } catch (UserCreationException e) {
            fail(e.toString());
        } catch (CsoUserUpdateException e) {
            fail(e.toString());
        } finally {
            con.close();
        }

    }

    @Test
    public void testRegisterUser() {
        System.out.println("registerUser");
        RegistrationInfo agentInfo = createAgentRegInfo();

        AuthenticationImpl instance = new AuthenticationImpl();
        getConnection(instance);


        try {
            instance.deleteUser(agentInfo.getUserName());
            instance.registerUser(agentInfo);

            User<Agent> agent = instance.login(agentInfo.getUserName(), agentInfo.getPassword());
            assertNotNull(agent);
            assertEquals(agent.getLastName(), agentInfo.getLastName());
            assertEquals(agent.getChallengeQuestion(), agentInfo.getChallengeQuestion());
            assert (instance.isAgent(agentInfo.getUserName()));
        } catch (CsoAuthException e) {
            fail(e.toString());
        } catch (CsoLoginNoAuthException e) {
            fail(e.toString());
        } catch (CsoDuplicateUserException e) {
            fail(e.toString());
        } catch (CsoRegistrationPasswordInvalidException e) {
            fail(e.toString());
        } catch (CsoRegistrationUsernameInvalidException e) {
             fail(e.toString());
        } catch (CsoBadUserPassStringException ex) {
            fail(ex.toString());
        }

        RegistrationInfo phInfo = createPolicyholderRegInfo();

        try {
            instance.registerUser(phInfo);
            User<Policyholder> ph = instance.login(phInfo.getUserName(), phInfo.getPassword());
            assertNotNull(ph);
            assertEquals(ph.getLastName(), phInfo.getLastName());
            assertEquals(ph.getChallengeQuestion(), phInfo.getChallengeQuestion());
            assert (instance.isPolicyholder(phInfo.getUserName()));
        } catch (CsoAuthException e) {
            fail(e.toString());
        } catch (CsoLoginNoAuthException e) {
            fail(e.toString());
        } catch (CsoDuplicateUserException e) {
            fail(e.toString());
        } catch (CsoRegistrationPasswordInvalidException e) {
            fail(e.toString());
        } catch (CsoRegistrationUsernameInvalidException e) {
             fail(e.toString());
        } catch (CsoBadUserPassStringException ex) {
            fail(ex.toString());
        }
    }

    @Test(expected = CsoDuplicateUserException.class)
    public void testRegisterDuplicateUser() throws CsoDuplicateUserException {
        System.out.println("registerDuplicateUser");
        RegistrationInfo info1 = createAgentRegInfo();
        RegistrationInfo info2 = createAgentRegInfo();
        AuthenticationImpl instance = new AuthenticationImpl();
        getConnection(instance);


        try {
            instance.registerUser(info1);
            instance.registerUser(info2);
        } catch (CsoAuthException e) {
            fail(e.toString());
        } catch (CsoRegistrationPasswordInvalidException e) {
            fail(e.toString());
        } catch (CsoRegistrationUsernameInvalidException e) {
             fail(e.toString());
        } catch (CsoBadUserPassStringException ex) {
            fail(ex.toString());
        }
    }

    @Test(expected = CsoLoginNoAuthException.class)
    public void testDeleteUser() throws CsoLoginNoAuthException {
        System.out.println("deleteUser");
        AuthenticationImpl instance = new AuthenticationImpl();
        RegistrationInfo agent = createAgentRegInfo();
        getConnection(instance);


        try {
            instance.deleteUser(agent.getUserName());
            User<Agent> ag = instance.login(agent.getUserName(), agent.getPassword());
            assertNull(ag);
        } catch (CsoAuthException e) {
            fail(e.toString());
        }
    }

    @Test
    public void testGetPasswordRecoveryInfo() {
        System.out.println("getPasswordRecoveryInfo");
        AuthenticationImpl instance = new AuthenticationImpl();
        getConnection(instance);


        String login = AGENT_USERNAME;
        try {
            PasswordRecover recover = instance.getPasswordRecoveryInfo(login);
            assertEquals(recover.getQuestion(), QUESTION_AGENT);
            assertEquals(recover.getAnswer(), ANSWER_AGENT);
        } catch (CsoAuthException e) {
            fail(e.toString());
        }

        login = POLICYHOLDER_USERNAME;
        try {
            PasswordRecover recover = instance.getPasswordRecoveryInfo(login);
            assertEquals(recover.getQuestion(), QUESTION_PH);
            assertEquals(recover.getAnswer(), ANSWER_PH);
        } catch (CsoAuthException e) {
            fail(e.toString());
        }

        login = NON_EXISTENT_USERNAME;
        try {
            PasswordRecover recover = instance.getPasswordRecoveryInfo(login);
            assertNull(recover);
        } catch (CsoAuthException e) {
            fail(e.toString());
        }

    }

    @Test
    public void testSetPasswordRecoveryInfo() {
        System.out.println("setPasswordRecoveryInfo");
        AuthenticationImpl instance = new AuthenticationImpl();
        getConnection(instance);

        PasswordRecover recover = createPasswordRecover();
        try {
            instance.setPasswordRecoveryInfo(recover);
            PasswordRecover rec2 = instance.getPasswordRecoveryInfo(recover.getUsername());
            assertTrue(rec2.isAccountLocked());
            assertEquals(rec2.getSecurityToken(), recover.getSecurityToken());
            assertEquals(rec2.getTokenExpiration().withTimeAtStartOfDay(),
                    (new DateTime()).plusDays(DAYS_TO_PWD_LINK_EXPIRATION).withTimeAtStartOfDay());
        } catch (CsoAuthException e) {
            fail(e.toString());
        }

    }

    @Test
    public void testClearPasswordRecoveryInfo() {
        System.out.println("clearPasswordRecoveryInfo");
        AuthenticationImpl instance = new AuthenticationImpl();
        getConnection(instance);

        PasswordRecover recover = createPasswordRecover();
        try {
            instance.setPasswordRecoveryInfo(recover);
            PasswordRecover rec2 = instance.getPasswordRecoveryInfo(recover.getUsername());
            assertTrue(rec2.isAccountLocked());
            assertEquals(recover.getSecurityToken(), rec2.getSecurityToken());
            assertNotNull(rec2.getTokenExpiration());
            instance.clearPasswordRecoveryInfo(recover.getUsername());
            rec2 = instance.getPasswordRecoveryInfo(recover.getUsername());
            assertFalse(rec2.isAccountLocked());
            assertNull(rec2.getSecurityToken());
            assertNull(rec2.getTokenExpiration());
        } catch (CsoAuthException e) {
            fail(e.toString());
        }
    }

    @Test
    public void testSetOkGovCustomerId() {
        System.out.println("setOkGovCustomerId");
        // skip test
        if(true)
            return;
        AuthenticationImpl instance = new AuthenticationImpl();
        getConnection(instance);

        try {
            instance.setOkGovCustomerId(POLICYHOLDER_USERNAME, OK_GOV_TEST_ID);
            String password = PASSWORD;
            User ph = instance.login(POLICYHOLDER_USERNAME, password);
            Policyholder p = (Policyholder)ph.getSpecificType();
            assertEquals(p.getOkGovUserId(), OK_GOV_TEST_ID);
        } catch (CsoUserUpdateException e) {
            fail(e.toString());
        } catch (CsoLoginNoAuthException e) {
            fail(e.toString());
        }
    }

    @Test
    public void testIsAccountLocked() {
        System.out.println("isAccountLocked");
        AuthenticationImpl instance = new AuthenticationImpl();
        getConnection(instance);

        PasswordRecover recover = createPasswordRecover();
        try {
            instance.setPasswordRecoveryInfo(recover);
            assertTrue(instance.isAccountLocked(recover.getUsername()));

            instance.clearPasswordRecoveryInfo(recover.getUsername());
            assertFalse(instance.isAccountLocked(recover.getUsername()));
        } catch (CsoAuthException e) {
            fail(e.toString());
        }
    }

    @Test
    public void testChangeUserPassword() {
        System.out.println("changeUserPassword");
        // skip test
        if(true)
            return;
        String login = AGENT_USERNAME;
        String dn = "cn=Agent Guytwo aguytwo,ou=users,o=compsourceok,dc=com";
        AuthenticationImpl instance = new AuthenticationImpl();
        LDAPConnection con = getConnection(instance);
        try {
            LDAPPersister<LdapAgent> persister =
                    LDAPPersister.getInstance(LdapAgent.class);

            LdapAgent lda = persister.get(dn, con);
            //Agent agent1 = LdapAgent.translateLdapIntoAgent(lda);
            assertTrue(instance.bindUser(login, PASSWORD));
            String newPass = "NewPassword";
            instance.changeUserPassword(login, newPass);

            con = getConnection(instance);

            LdapAgent lda2 = persister.get(dn, con);
            byte[] newPwd = lda2.getFirstUserPassword();
            try {
                String np = new String(newPwd, "UTF-8");
                System.out.println(np);
            } catch (UnsupportedEncodingException e) {
                fail(e.toString());
            }

            boolean test = instance.bindUser(login, newPass);
            assertTrue(test);
            test = instance.bindUser(login, PASSWORD);
            assertFalse(test);


        } catch (LDAPPersistException e) {
            fail(e.toString());
        } catch (CsoAuthException e) {
            fail(e.toString());
        } catch (LDAPException e) {
            fail(e.toString());
        }
    }

    private RegistrationInfo createAgentRegInfo() {
        RegistrationInfo ret = new RegistrationInfo();
        ret.setFirstName("Bob");
        ret.setLastName("Last");
        ret.setTypeOfUser(RegistrationInfo.RegistrationType.Agent);
        ret.setUserName("blast");
        ret.setPassword(PASSWORD);
        ret.setAgentAgencyNumber("23");
        ret.setAgentAgencyName("The Agent Agency");
        ret.setPhoneNumber("555-555-5555");
        ret.setEmail("blast@agency.com");
        ret.setChallengeQuestion(QUESTION);
        ret.setChallengeAnswer(ANSWER);
        ret.setAgentRegistrationToken("sdsdfsdfsd");
        return ret;
    }

    private RegistrationInfo createPolicyholderRegInfo() {
        RegistrationInfo ret = new RegistrationInfo();
        ret.setFirstName("Peg");
        ret.setLastName("Flummox");
        ret.setTypeOfUser(RegistrationInfo.RegistrationType.Policyholder);
        ret.setUserName("pflummox");
        ret.setPassword(PASSWORD);
        ret.setPhoneNumber("555-555-5555");
        ret.setEmail("pflummox@runswithscissors.com");
        ret.setChallengeQuestion(QUESTION);
        ret.setChallengeAnswer(ANSWER);
        ret.setPhCompanyName("We Run With Scissors");
        ret.setPhPolicyNumber("12345678");
        return ret;
    }

    private PasswordRecover createPasswordRecover() {
        PasswordRecover ret = new PasswordRecover();
        ret.setUsername(POLICYHOLDER_USERNAME);
        ret.setQuestion(QUESTION_PH);
        ret.setAnswer(ANSWER_PH);
        ret.setSecurityToken(createRandomSecurityToken());
        ret.setTokenExpiration(new DateTime().plusDays(DAYS_TO_PWD_LINK_EXPIRATION));
        return ret;
    }

    private String createRandomSecurityToken() {
        SecureRandomNumberGenerator rand = new SecureRandomNumberGenerator();
        return rand.nextBytes(32).toBase64();
    }

    private LDAPConnection getConnection(AuthenticationImpl instance) {
        LDAPConnection con = null;
        try {
            con = new LDAPConnection();
            con.connect(HOST, PORT);
            instance.setConnection(con);
        } catch (LDAPException e) {
            fail(e.toString());
        }
        return con;
    }
}