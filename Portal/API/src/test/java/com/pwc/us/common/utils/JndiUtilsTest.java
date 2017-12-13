package com.pwc.us.common.utils;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Roger
 */
public class JndiUtilsTest {

    private static final String HOST = "relay.compsourcemutual.com";
    private static final String PORT = "587";
    private static final String FROM = "noreply@compsourcemutual.com";
    private static final String SUBJECT = "Your Password Recovery Request from CompSource";
    private static final int NUM_DAYS_TO_PWD_EXPIRATION = 15;
    private static final String SUBJECT_OOS = "Out Of State Request";
    private static final String TO_OOS = "rgsnelling@gmail.com";

    public JndiUtilsTest() {
    }

    /**
     * Test of getPolicyCenterUrl method, of class JndiUtils.
     */
    @Test
    public void testGetPolicyCenterUrl() {
        System.out.println("getPolicyCenterUrl");
        String expResult = "http://vm-gwint1.s-i-f.local:8180/pc";
        String result = JndiUtils.getPolicyCenterUrl();
        assertEquals(expResult, result);
    }

    /**
     * Test of getPolicyCenterUrl method, of class JndiUtils.
     */
    @Test
    public void testGetClaimCenterUrl() {
        System.out.println("getWsdlUrlRootCC");
        String expResult = "http://vm-gwint1.s-i-f.local:8080/cc";
        String result = JndiUtils.getClaimCenterUrl();
        System.out.println(result);
        assertEquals(expResult, result);
    }

    /**
     * Test of getBillingCenterUrl method, of class JndiUtils.
     */
    @Test
    public void testGetBillingCenterUrl() {
        System.out.println("getWsdlUrlRootBC");
        String expResult = "http://vm-gwint1.s-i-f.local:8580/bc";
        String result = JndiUtils.getBillingCenterUrl();
        System.out.println(result);
        assertEquals(expResult, result);
    }

    @Test
    public void testGetOkGovUrl() {
        System.out.println("getOkGovUrl");
        String expResult = "https://testcheckout.ok.gov/commoncheckout/";
        String result = JndiUtils.getOkGovUrl();
        assertEquals(expResult, result);
    }

    /**
     * Test of getOkGovSuccessUrl method, of class JndiUtils.
     */
    @Test
    public void testGetOkGovSuccessUrl() {
        System.out.println("getOkGovSuccessUrl");
        String expResult = "https://localhost:8443/compsource-portal-webui-stripes/PayOnlineSuccess.action";
        String result = JndiUtils.getOkGovSuccessUrl();
        assertEquals(expResult, result);
    }

    /**
     * Test of getOkGovFailureUrl method, of class JndiUtils.
     */
    @Test
    public void testGetOkGovFailureUrl() {
        System.out.println("getOkGovFailureUrl");
        String expResult = "https://localhost:8443/compsource-portal-webui-stripes/PayOnlineFailure.action";
        String result = JndiUtils.getOkGovFailureUrl();
        assertEquals(expResult, result);
    }

    /**
     * Test of getOkGovCancelUrl method, of class JndiUtils.
     */
    @Test
    public void testGetOkGovCancelUrl() {
        System.out.println("getOkGovCancelUrl");
        String expResult = "https://localhost:8443/compsource-portal-webui-stripes/PayOnlineCancel.action";
        String result = JndiUtils.getOkGovCancelUrl();
        assertEquals(expResult, result);
    }

    /**
     * Test of getOkGovCancelUrl method, of class JndiUtils.
     */
    @Test
    public void testGetTrustStoreLocation() {
        System.out.println("getTrustStoreLocation");
        String expResult = ".portalTrustStore";
        String result = JndiUtils.getTrustStoreLocation();
        assertEquals(expResult, result);
    }

    /**
     * Test of getLdapAdmin method, of class JndiUtils.
     */
    @Test
    public void testGetLdapAdmin() {
        System.out.println("getLdapAdmin");
        String expResult = "uid=admin,o=compsourceok,dc=com";
        String result = JndiUtils.getLdapAdmin();
        assertEquals(expResult, result);
    }

    /**
     * Test of getLdapAdminPass method, of class JndiUtils.
     */
    @Test
    public void testGetLdapAdminPass() {
        System.out.println("getLdapAdminPass");
        String expResult = "hx9N-L%TgS_%K6#";
        String result = JndiUtils.getLdapAdminPass();
        assertEquals(expResult, result);
    }

    /**
     * Test of getLdapUrl method, of class JndiUtils.
     */
    @Test
    public void testGetLdapUrl() {
        System.out.println("getLdapUrl");
        String expResult = "localhost";
        String result = JndiUtils.getLdapUrl();
        assertEquals(expResult, result);

    }

    /**
     * Test of getLdapPort method, of class JndiUtils.
     */
    @Test
    public void testGetLdapPort() {
        System.out.println("getLdapPort");
        int expResult = 10389;
        int result = JndiUtils.getLdapPort();
        assertEquals(expResult, result);
    }

    /**
     * Test of getNumConnections method, of class JndiUtils.
     */
    @Test
    public void testGetNumConnections() {
        System.out.println("getNumConnections");
        int expResult = 10;
        int result = JndiUtils.getNumConnections();
        assertEquals(expResult, result);
    }

    /**
     * Test of getSmtpHost method, of class JndiUtils.
     */
    @Test
    public void testGetSmtpHost() {
        System.out.println("getSmtpHost");
        String expResult = HOST;
        String result = JndiUtils.getSmtpHost();
        assertEquals(expResult, result);
    }

    /**
     * Test of getSmtpPort method, of class JndiUtils.
     */
    @Test
    public void testGetSmtpPort() {
        System.out.println("getSmtpPort");
        String expResult = PORT;
        String result = JndiUtils.getSmtpPort();
        assertEquals(expResult, result);
    }

    /**
     * Test of getSmtpFrom method, of class JndiUtils.
     */
    @Test
    public void testGetSmtpFrom() {
        System.out.println("getSmtpFrom");
        String expResult = FROM;
        String result = JndiUtils.getSmtpFrom();
        assertEquals(expResult, result);
    }

    /**
     * Test of getSmtpSubject method, of class JndiUtils.
     */
    @Test
    public void testGetSmtpSubject() {
        System.out.println("getSmtpSubject");
        String expResult = SUBJECT;
        String result = JndiUtils.getSmtpSubject();
        assertEquals(expResult, result);
    }

    @Test
    public void testGetNumDaysToPwdLinkExpiration() {
        System.out.println("getNumDaysToPwdLinkExpiration");
        int expResult = NUM_DAYS_TO_PWD_EXPIRATION;
        int result = JndiUtils.getNumDaysToPwdLinkExpiration();
        assertEquals(expResult, result);
    }

    /**
     * Test of getSmtpSubjectOOS method, of class JndiUtils.
     */
    @Test
    public void testGetSmtpSubjectOOS() {
        System.out.println("getSmtpSubjectOOS");
        String expResult = SUBJECT_OOS;
        String result = JndiUtils.getSmtpSubjectOOS();
        assertEquals(expResult, result);
    }

    /**
     * Test of getSmtpToOOS method, of class JndiUtils.
     */
    @Test
    public void testGetSmtpToOOS() {
        System.out.println("getSmtpToOOS");
        String expResult = TO_OOS;
        String result = JndiUtils.getSmtpToOOS();
        assertEquals(expResult, result);
    }

    @Test
    public void testGetOkGovLogin() {
        System.out.println("getOkGovLogin");
        String expResult = "compsource";
        String result = JndiUtils.getOkGovLogin();
        assertEquals(expResult, result);
    }

    @Test
    public void testGetOkGovPassword() {
        System.out.println("getOkGovPassword");
        String expResult = "compsourcetest";
        String result = JndiUtils.getOkGovPassword();
        assertEquals(expResult, result);
    }
}