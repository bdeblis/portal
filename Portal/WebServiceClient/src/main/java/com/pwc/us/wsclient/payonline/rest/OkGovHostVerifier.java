package com.pwc.us.wsclient.payonline.rest;

import javax.net.ssl.SSLException;
import org.apache.http.conn.ssl.AbstractVerifier;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Here's the problem: ok.gov's test certificate is registered to test.www.ok.gov, 
 * but the test server is hosted at testcheckout.ok.gov. So we need to override
 * the standard X509 hostname verifier in order to accept a certificate from OK.gov
 * regardless of its prefix. This balances safety with usability -- we still get 
 * a cert-level guarantee that our payment request is going to ok.gov, but the 
 * test cert hostname mismatch no longer causes SSL errors.
 * 
 * Additionally, both test and production certificates should be stored in the 
 * Portal's truststore.
 * @author Roger
 */
public class OkGovHostVerifier extends AbstractVerifier {
    
    private final X509HostnameVerifier delegate;
    private static final Logger logger = LoggerFactory.getLogger(OkGovHostVerifier.class);
    private static final String TEST_CERT_PREFIX = "test.www.";
    private static final String TEST_CHECKOUT_PREFIX = "testcheckout.";
    
    public OkGovHostVerifier(final X509HostnameVerifier delegate) {
        this.delegate = delegate;
    }

    @Override
    public void verify(String host, String[] cns, String[] subjectAlts) throws SSLException {
        boolean ok = false;
        try {
            delegate.verify(host, cns, subjectAlts);
        } catch (SSLException e) {
            for (String cn : cns) {
                if (cn.startsWith(TEST_CERT_PREFIX)) {
                    try {
                        String newCn = cn.substring(cn.indexOf(".", cn.indexOf(".")+1)+1);
                        logger.debug("newCn: " + newCn);
                        delegate.verify(host, new String[] {TEST_CHECKOUT_PREFIX + newCn}, subjectAlts);
                        ok = true;
                    } catch (SSLException e2) { 
                        logger.info("failed to validate cert for cn: " + cn);
                    }
                }
            }
            if (!ok) throw e;
        }
    }
    
}
