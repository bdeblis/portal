package com.pwc.us.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.properties.EncryptableProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The main JNDI Lookup utility, shared between all modules of the app.
 *
 * @author Roger
 */
public class JndiUtils {

    private static Logger logger = LoggerFactory.getLogger(JndiUtils.class);
    private static Context ctx = null;
    private static String gwuser = null;
    private static String gwpass = null;
    private static String ldapAdminPass = null;
    private static String okGovLogin = null;
    private static String okGovPassword = null;

    public static String getConfigPath() {
        String name = null;
        try {
            Context ct = getContext();
            name = (String) ct.lookup("cso-portal/configPath");
        } catch (NamingException e) {
            logger.error("Error: " + e.getExplanation(), e);
        }
        return name;
    }

    /**
     * Returns the WSDL URL root for the system.
     *
     * @return
     */
    public static String getPolicyCenterUrl() {
        String name = null;
        try {
            Context ct = getContext();
            name = (String) ct.lookup("cso-portal/policyCenterUrl");
        } catch (NamingException e) {
            logger.error("Error: " + e.getExplanation(), e);
        }
        return name;
    }

    public static String getClaimCenterUrl() {
        String name = null;
        try {
            Context ct = getContext();
            name = (String) ct.lookup("cso-portal/claimCenterUrl");
        } catch (NamingException e) {
            logger.error("Error: " + e.getExplanation(), e);
        }
        return name;
    }

    public static String getBillingCenterUrl() {
        String name = null;
        try {
            Context ct = getContext();
            name = (String) ct.lookup("cso-portal/billingCenterUrl");
        } catch (NamingException e) {
            logger.error("Error: " + e.getExplanation(), e);
        }
        return name;
    }

    public static String getOkGovUrl() {
        String name = null;
        try {
            Context ct = getContext();
            name = (String) ct.lookup("cso-portal/okGovUrl");
        } catch (NamingException e) {
            logger.error("Error: " + e.getExplanation(), e);
        }
        return name;
    }

    public static String getTrustStoreLocation() {
        String name = null;
        try {
            Context ct = getContext();
            name = (String) ct.lookup("cso-portal/trustStoreLocation");
        } catch (NamingException e) {
            logger.error("Error: " + e.getExplanation(), e);
        }
        return name;
    }

    public static String getTrustStorePassword() {
        String name = null;
        try {
            Context ct = getContext();
            name = (String) ct.lookup("cso-portal/trustStorePassword");
        } catch (NamingException e) {
            logger.error("Error: " + e.getExplanation(), e);
        }
        return name;
    }

    public static String getOkGovSuccessUrl() {
        String name = null;
        try {
            Context ct = getContext();
            name = (String) ct.lookup("cso-portal/okGovSuccessUrl");
        } catch (NamingException e) {
            logger.error("Error: " + e.getExplanation(), e);
        }
        return name;
    }

    public static String getOkGovFailureUrl() {
        String name = null;
        try {
            Context ct = getContext();
            name = (String) ct.lookup("cso-portal/okGovFailureUrl");
        } catch (NamingException e) {
            logger.error("Error: " + e.getExplanation(), e);
        }
        return name;
    }

    public static String getOkGovCancelUrl() {
        String name = null;
        try {
            Context ct = getContext();
            name = (String) ct.lookup("cso-portal/okGovCancelUrl");
        } catch (NamingException e) {
            logger.error("Error: " + e.getExplanation(), e);
        }
        return name;
    }

    /**
     * Returns the admin DN for the LDAP server.
     *
     * @return
     */
    public static String getLdapAdmin() {
        String name = null;
        try {
            Context ct = getContext();
            name = (String) ct.lookup("cso-portal/ldapAdmin");
        } catch (NamingException e) {
            logger.error("Error: " + e.getExplanation(), e);
        }
        return name;
    }

    /**
     * Returns the admin password for the LDAP server.
     *
     * @return
     */
    public static String getLdapAdminPass() {
        String name = null;

        Context ct = getContext();

        return ldapAdminPass;
    }

    /**
     * Returns the admin password for the LDAP server.
     *
     * @return
     */
    public static String getLdapUrl() {
        String name = null;
        try {
            Context ct = getContext();
            name = (String) ct.lookup("cso-portal/ldapUrl");
        } catch (NamingException e) {
            logger.error("Error: " + e.getExplanation(), e);
        }
        return name;
    }

    /**
     * Returns the admin password for the LDAP server.
     *
     * @return
     */
    public static int getLdapPort() {
        Integer num = 0;
        try {
            Context ct = getContext();
            String name = (String) ct.lookup("cso-portal/ldapPort");
            num = Integer.valueOf(name);
        } catch (NamingException e) {
            logger.error("Error: " + e.getExplanation(), e);
        }
        return num;
    }

    /**
     * Returns the number of connections for the LDAP server.
     *
     * @return
     */
    public static int getNumConnections() {
        Integer num = 0;
        try {
            Context ct = getContext();
            String name = (String) ct.lookup("cso-portal/numConnections");
            num = Integer.valueOf(name);
        } catch (NamingException e) {
            logger.error("Error: " + e.getExplanation(), e);
        }
        return num;
    }

    /**
     * Returns the WSDL URL root for the system.
     *
     * @return
     */
    public static String getSmtpHost() {
        String name = null;
        try {
            Context ct = getContext();
            name = (String) ct.lookup("cso-portal/smtpHost");
        } catch (NamingException e) {
            logger.error("Error: " + e.getExplanation(), e);
        }
        return name;
    }

    public static String getSmtpPort() {
        String name = null;
        try {
            Context ct = getContext();
            name = (String) ct.lookup("cso-portal/smtpPort");
        } catch (NamingException e) {
            logger.error("Error: " + e.getExplanation(), e);
        }
        return name;
    }

    public static String getSmtpFrom() {
        String name = null;
        try {
            Context ct = getContext();
            name = (String) ct.lookup("cso-portal/smtpFrom");
        } catch (NamingException e) {
            logger.error("Error: " + e.getExplanation(), e);
        }
        return name;
    }

    public static String getSmtpSubject() {
        String name = null;
        try {
            Context ct = getContext();
            name = (String) ct.lookup("cso-portal/smtpSubject");
        } catch (NamingException e) {
            logger.error("Error: " + e.getExplanation(), e);
        }
        return name;
    }

    public static int getNumDaysToPwdLinkExpiration() {
        int num = 0;
        try {
            Context ct = getContext();
            String name = (String) ct.lookup("cso-portal/numPwdLinkExpirDays");
            num = Integer.valueOf(name);
        } catch (NamingException e) {
            logger.error("Error: " + e.getExplanation(), e);
        }
        return num;
    }

    public static String getSmtpToOOS() {
        String name = null;
        try {
            Context ct = getContext();
            name = (String) ct.lookup("cso-portal/smtpToOOS");
        } catch (NamingException e) {
            logger.error("Error: " + e.getExplanation(), e);
        }
        return name;
    }

    public static String getSmtpSubjectOOS() {
        String name = null;
        try {
            Context ct = getContext();
            name = (String) ct.lookup("cso-portal/smtpSubjectOOS");
        } catch (NamingException e) {
            logger.error("Error: " + e.getExplanation(), e);
        }
        return name;
    }

    public static String getOkGovLogin() {
        String name = null;
        
            Context ct = getContext();

       
        return okGovLogin;
    }

    public static String getOkGovPassword() {
        String name = null;
        
            Context ct = getContext();

       
        return okGovPassword;
    }

    public static boolean isJUnitTest() {
    StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
    List<StackTraceElement> list = Arrays.asList(stackTrace);
    for (StackTraceElement element : list) {
        if (element.getClassName().startsWith("org.junit.")) {
            return true;
        }           
    }
    return false;
}

    private static Context getContext() {
        if (ctx == null) {
            try {
                Context initContext = new InitialContext();
                ctx = (Context) initContext.lookup("java:/comp/env");
            } catch (NamingException e) {
                logger.error("Error: getting initial context " + e.getExplanation(), e);
            }
        }
        if (gwuser == null) {
            logger.debug("setting up sensitive values");
            Properties prop = new Properties();
            InputStream input = null;
            try {
                input = new FileInputStream("/etc/properties" + File.separator + "cso-secretkey.properties");
            } catch (FileNotFoundException ex) {
                System.err.println("cant find properties file");
                logger.error("cant find properties file");
            }
            try {
                // load a properties file
                prop.load(input);
            } catch (IOException ex) {
                logger.error("cant load properties file");
            }

            String key = prop.getProperty("key");

            StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
            encryptor.setPassword(key); // could be got from web, env variable...
            Properties props = new EncryptableProperties(encryptor);

            try {
                props.load(new FileInputStream("/etc/properties" + File.separator + "cso-sensitive.properties"));
            } catch (IOException ex) {
                logger.error("cant load cso-sensitive.properties file");
            }
            if(false) // isJUnitTest()
            {
                gwuser = props.getProperty("su");
                gwpass = props.getProperty("5br49");
            }else{
            gwuser = props.getProperty("gwuser");
            gwpass = props.getProperty("gwpassword");
            }
            ldapAdminPass = props.getProperty("ldapAdminPass");
            okGovLogin = props.getProperty("okGovLogin");
            okGovPassword = props.getProperty("okGovPassword");
        }
        return ctx;
    }

    public static String getGwUser() {
        Context ct;
        if (ctx == null) {
            ct = getContext();
        }
        return gwuser;
    }

    public static String getGwPassword() {
        Context ct;
        if (ctx == null) {
            ct = getContext();
        }
        return gwpass;
    }
}
