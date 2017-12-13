package com.pwc.us.wsclient.payonline.rest;

import com.pwc.us.common.exception.OkGovIntegrationException;
import com.pwc.us.common.model.payonline.AddInfo;
import com.pwc.us.common.model.payonline.OkGovResultPO;
import com.pwc.us.common.model.payonline.TransactionPO;
import com.pwc.us.ws.client.payonline.model.Transaction;
import com.pwc.us.common.utils.JndiUtils;
import com.pwc.us.wsclient.payonline.PayOnlineRequestBuilder;
import com.pwc.us.wsclient.payonline.jaxb.Result;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.logging.Level;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Designed to handle all calls to and from the OK.gov online payment system.
 *
 * @author Roger
 */
public class PayOnlineController {

    private static final String OK_GOV_LOGIN_ID_FIELD = "login_id";
    private static final String OK_GOV_PASSWORD_FIELD = "password";
    private static final String OK_GOV_TOKEN_FIELD = "token";
    private static final String TRUSTSTORE_PASSWORD = JndiUtils.getTrustStorePassword();
    private PayOnlineRequestBuilder poBuilder = new PayOnlineRequestBuilder();
    private static final String TRUSTSTORE_LOCATION = JndiUtils.getTrustStoreLocation();
    private static final int MAX_CONNECTIONS = 50;
    private static final Logger logger = LoggerFactory.getLogger(PayOnlineController.class);
    private static PoolingHttpClientConnectionManager cm = null;
    private static Registry<ConnectionSocketFactory> socketFactoryRegistry = null;
    private static SSLContext sslContext = null;
    private static SSLConnectionSocketFactory sslsf = null;
    private static KeyStore trustStore = null;
    private CloseableHttpClient client = null;
    ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
        public String handleResponse(
                final HttpResponse response) throws ClientProtocolException, IOException {
            int status = response.getStatusLine().getStatusCode();
            if (status >= 200 && status < 300) {
                HttpEntity entity = response.getEntity();
                return entity != null ? EntityUtils.toString(entity) : null;
            } else {
                throw new ClientProtocolException("Unexpected reponse status: " + status);
            }
        }
    };

    public PayOnlineController() {
        try {
            connectClient();
        } catch (OkGovIntegrationException e) {
            String message = "Error creating connection in PayOnlineController.";
            logger.error(message, e);
        }
    }

    public OkGovResultPO getOkGovToken(TransactionPO transPO, String url) throws OkGovIntegrationException {
        OkGovResultPO ret = null;
        Transaction transactionObject = poBuilder.buildTransaction(transPO);
        try {
            String transXml = poBuilder.marshall(transactionObject);
            String resultXml = postXmlMessage(transXml, url);
            Result result = (Result) JAXBContext.newInstance(Result.class).createUnmarshaller().unmarshal(new StringReader(resultXml));
            ret = result.createResultPO();
        } catch (JAXBException e) {
            String message = "Caught JAXBException trying to marshall Transaction object to XML";
            logger.error(message, e);
            throw new OkGovIntegrationException(message, e);
        } catch (XMLStreamException e) {
            String message = "Caught XMLStreamException trying to marshall Transaction object to XML";
            logger.error(message, e);
            throw new OkGovIntegrationException(message, e);
        }
        return ret;
    }

    public TransactionPO getTransactionDetails(String token, String login, String password, String url) throws OkGovIntegrationException {
        if (client == null) {
            connectClient();
        }
        if(token == null)
        {
            logger.error("PayOnlineController.getTransactionDetails() token is null");
        }
        else{
            logger.info("PayOnlineController.getTransactionDetails() token "+token);
        }
        if(login == null)
        {
            logger.error("PayOnlineController.getTransactionDetails() login is null");
        }
        else{
            logger.info("PayOnlineController.getTransactionDetails() login "+login);
        }
        if(password == null)
        {
            logger.error("PayOnlineController.getTransactionDetails() password is null");
        }else{
            logger.info("PayOnlineController.getTransactionDetails() password "+password.substring(3)+"....");
        }
        if(url == null)
        {
            logger.error("PayOnlineController.getTransactionDetails() url is null");
        }else{
            logger.info("PayOnlineController.getTransactionDetails() url "+url);
        }
        
        TransactionPO ret = null;
        MultipartEntityBuilder entityBuilder = MultipartEntityBuilder.create();
        entityBuilder.addTextBody(OK_GOV_LOGIN_ID_FIELD, login);
        entityBuilder.addTextBody(OK_GOV_PASSWORD_FIELD, password);
        entityBuilder.addTextBody(OK_GOV_TOKEN_FIELD, token);
        
        HttpEntity entity = entityBuilder.build();
        String response = postEntityToUrl(entity, url);
        
        try {
            Transaction trans = (Transaction) JAXBContext.newInstance(Transaction.class).createUnmarshaller().unmarshal(new StringReader(response));
            ret = poBuilder.buildTransactionPO(trans);
        } catch (JAXBException e) {
            String message = "Caught JAXBException trying to unmarshall Transaction object into TransactionPO";
            logger.error(message, e);
            logger.error("response "+response);
            logger.error("token "+token);
            throw new OkGovIntegrationException(message, e);
        }
        return ret;
    }

    public String postXmlMessage(String xml, String url) throws OkGovIntegrationException {
        if (client == null) {
            connectClient();
        }
        HttpEntity entity = MultipartEntityBuilder.create().addTextBody("xml", xml).build();
        String response = postEntityToUrl(entity, url);
        return response;
    }

    private String postEntityToUrl(HttpEntity entity, String url) throws OkGovIntegrationException {
        HttpPost post = new HttpPost(url);
        post.setEntity(entity);
        String response = null;
        try {
            response = client.execute(post, responseHandler);
        } catch (IOException e) {
            String message = "Caught IOException while trying to send http POST to " + url;
            logger.error(message, e);
            throw new OkGovIntegrationException(message, e);
        }
        return response;
    }

    /**
     * Setting up the connection is a thicket of dependencies, so here's a
     * guide: truststore -- a binary file that contains the public keys for the
     * OK.gov certs. Depends on the existence of the truststore file. sslContext
     * -- Depends upon truststore being set up. sslsf -- the
     * SSLConnectionSocketFactory. Depends upon sslContext being set up. All of
     * these are static, so they only need to be initialized once. Because the
     * initialization methods cascade off each other, we can initalize all of
     * the above by simply calling the initializer to sslsf.
     * socketFactoryRegistry -- a registry of SSL connections required to use
     * the connection manager. Relies on the SSLConnectionSocketFactory cm --
     * The connection manager. A simple connection pool. Relies on the SSL
     * socketFactoryRegistry
     *
     * @param url
     * @throws OkGovIntegrationException
     */
    private void connectClient() throws OkGovIntegrationException {
        initializeConnectionManager();
        client = HttpClients.custom()
                .setConnectionManager(cm)
                .build();

    }

    private static void initializeTruststore() throws OkGovIntegrationException {
        File pKeyFile = new File(TRUSTSTORE_LOCATION);
        String pKeyPassword = TRUSTSTORE_PASSWORD;
        try {
         
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            InputStream keyInput = new FileInputStream(pKeyFile);
            keyStore.load(keyInput, pKeyPassword.toCharArray());
            keyInput.close();
        } catch (Exception ex) {
            logger.error("error from test code", ex);
            java.util.logging.Logger.getLogger(PayOnlineController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private static void initializeSslContext() throws OkGovIntegrationException {
        if (trustStore == null) {
            initializeTruststore();
        }
        try {
            if (sslContext == null) {
                sslContext = SSLContexts.custom()
                        .loadTrustMaterial(trustStore, null)
                        .build();
            }

        } catch (NoSuchAlgorithmException e) {
            String message = "Caught NoSuchAlgorithmException while trying to set up SSL Context in PayOnlineController";
            logger.error(message, e);
            throw new OkGovIntegrationException(message, e);
        } catch (KeyStoreException e) {
            String message = "Caught KeyStoreException while trying to set up SSL Context in PayOnlineController";
            logger.error(message, e);
            throw new OkGovIntegrationException(message, e);
        } catch (KeyManagementException e) {
            String message = "Caught KeyManagementException while trying to set up SSL Context in PayOnlineController";
            logger.error(message, e);
            throw new OkGovIntegrationException(message, e);
        }
    }

    private static void initializeSslConnectionFactory() throws OkGovIntegrationException {
        if (sslContext == null) {
            initializeSslContext();
        }
        X509HostnameVerifier delegate = SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER;
        OkGovHostVerifier okGovVer = new OkGovHostVerifier(delegate);
        // Allow TLSv1 protocol only
        sslsf = new SSLConnectionSocketFactory(
                sslContext, new String[]{"TLSv1"}, null, okGovVer);

    }

    private static void initializeSocketFactoryRegistry() throws OkGovIntegrationException {
        if (sslsf == null) {
            initializeSslConnectionFactory();
        }
        socketFactoryRegistry = RegistryBuilder
                .<ConnectionSocketFactory>create().register("https", sslsf)
                .build();
    }

    private static void initializeConnectionManager() throws OkGovIntegrationException {
        if (cm == null) {
            if (socketFactoryRegistry == null) {
                initializeSocketFactoryRegistry();
            }
            cm = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
            cm.setMaxTotal(MAX_CONNECTIONS);
        }
    }

    public static void setTrustStore(KeyStore trustStore) {
        PayOnlineController.trustStore = trustStore;
    }
}
