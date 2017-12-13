package com.compsourceok.ldapdump;

import com.unboundid.ldap.sdk.BindResult;
import com.unboundid.ldap.sdk.LDAPConnection;
import com.unboundid.ldap.sdk.ResultCode;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Properties;
import javax.net.SocketFactory;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

/**
 *
 * @author isrk
 */
public class DumpLdifData {

    private String LDAP_ADMIN;
    private String LDAP_ADMIN_PASS;
    private String LDAP_URL;
    private int LDAP_PORT;
    private String destination;

    private FormatHandler formatHandler;
    private LDAPConnection connection;
    private static Logger logger = LoggerFactory.getLogger(DumpLdifData.class);

    Properties prop = new Properties();
    InputStream input = null;

    public DumpLdifData(String propertiesfile) {
        try {

            input = new FileInputStream(propertiesfile);
            try {
                InputStreamReader isr = new InputStreamReader(input, "UTF-8");
                // load a properties file
                prop.load(isr);

                LDAP_ADMIN = prop.getProperty("ldapadmin");
                LDAP_ADMIN_PASS = prop.getProperty("pass");
                LDAP_URL = prop.getProperty("url");
                LDAP_PORT = Integer.parseInt(prop.getProperty("port"));
                destination = prop.getProperty("dest");
                logger.debug("props '" + LDAP_ADMIN + "' '" + LDAP_ADMIN_PASS + "' '" + LDAP_URL + "' '" + LDAP_PORT + "' '" + destination + "'");
            } catch (IOException ex) {
                logger.error(ex.getMessage());
            }
        } catch (FileNotFoundException ex) {
            logger.error(ex.getMessage());
        }

    }

    public DumpLdifData(String admin, String pass, String url, int port, String dest) {
        LDAP_ADMIN = admin;
        LDAP_ADMIN_PASS = pass;
        LDAP_URL = url;
        LDAP_PORT = port;
        destination = dest;
    }

    public static void main(String[] args) {
        DumpLdifData dump = null;
        //          System.err.println(args[0]+" | "+args[1]+" | "+args[2]+" | "+args[3]+" | "+args[4]);
        try {
            if (args != null && args.length == 1) {
                dump = new DumpLdifData(args[0]);
            } else if (args.length == 5) {
                dump = new DumpLdifData(args[0], args[1], args[2], Integer.parseInt(args[3]), args[4]);
            } else {
                System.exit(1);
            }
            dump.dump();
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            logger.debug("args[0] " + args[0]);
        }

    }

    public void dump() throws Exception {
        SocketFactory sf = SocketFactory.getDefault();
        connection = new LDAPConnection(sf, LDAP_URL, LDAP_PORT);
        
        logger.debug("binding '" + LDAP_ADMIN + "' '" + LDAP_ADMIN_PASS + "'");
        BindResult res = connection.bind(LDAP_ADMIN, LDAP_ADMIN_PASS);
        ResultCode rc = res.getResultCode();
        if (rc.intValue() != 0) {
            logger.error("Unable to authenticate into LDAP Server. Please check configuration for admin DN");
            System.exit(1);
        }
        formatHandler = new LDIFFormatHandler();

        final File outputFile = new File(destination);
        final OutputStream outputStream = new FileOutputStream(outputFile);
        formatHandler.dump(connection, "o=compsourceok,dc=com", "(objectclass=*)", outputStream, logger);
    }

}
