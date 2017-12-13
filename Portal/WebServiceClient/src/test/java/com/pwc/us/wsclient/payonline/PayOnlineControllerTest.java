package com.pwc.us.wsclient.payonline;

import com.pwc.us.wsclient.payonline.rest.OkGovHostVerifier;
import com.pwc.us.wsclient.payonline.rest.PayOnlineController;
import com.pwc.us.common.exception.OkGovIntegrationException;
import com.pwc.us.common.model.payonline.TransactionPO;
import com.pwc.us.wsclient.payonline.jaxb.Result;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import javax.net.ssl.SSLContext;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
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
import org.junit.Test;

/**
 *
 * @author Roger
 */
public class PayOnlineControllerTest {

    private static final String TRUSTSTORE_TEST_LOCATION = "src/main/java/.portalTrustStore";
    private static final String TRUSTSTORE_PASSWORD = "password";
    private static final String USER = "compsource";
    private static final String PASSWORD = "compsourcetest";
    private String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
            + "<transaction>\n"
            + "    <login_id>compsource</login_id>\n"
            + "    <password>compsourcetest</password>\n"
            + "    <customer_id></customer_id>\n"
            + "    <autocomplete>0</autocomplete>\n"
            + "    <name_on_account><![CDATA[Bob Jones & Sons]]></name_on_account>\n"
            + "    <local_reference><![CDATA[STS1111111111]]></local_reference>\n"
            + "    <address_1><![CDATA[111 Main St.]]></address_1>\n"
            + "    <address_2><![CDATA[Suite 10]]></address_2>\n"
            + "    <city_province>OKC</city_province>\n"
            + "    <state>OK</state>\n"
            + "    <country>US</country>\n"
            + "    <zip>99999</zip>\n"
            + "    <email>rcking@compsourcemutual.com</email>\n"
            + "    <phone>4053333333</phone>\n"
            + "    <amount_paid>100</amount_paid>\n"
            + "    <extra01><![CDATA[10/31/2012]]></extra01>\n"
            + "    <extra02><![CDATA[STS1111111111]]></extra02>\n"
            + "    <extra03><![CDATA[Extra data3]]></extra03>\n"
            + "    <extra04><![CDATA[Extra data4]]></extra04>\n"
            + "    <extra05><![CDATA[Extra data5]]></extra05>\n"
            + "    <max_future_date>2/15/2020</max_future_date>\n"
            + "    <success_url><![CDATA[https://www.example.com/PayOnlineSuccess.action]]></success_url>\n"
            + "    <failure_url><![CDATA[https://www.example.com/PayOnlineFailure.action]]></failure_url>\n"
            + "    <cancel_url><![CDATA[https://www.example.com/PayOnlineCancel.action]]></cancel_url>\n"
            + "    <allowed_pay_types>ALL</allowed_pay_types>\n"
            + "    <items>\n"
            + "        <item>\n"
            + "            <title><![CDATA[Tax Due]]></title>\n"
            + "            <description><![CDATA[This is a description]]></description>\n"
            + "            <quantity>1</quantity>\n"
            + "            <cost>100.00</cost>\n"
            + "        </item>   \n"
            + "    </items>\n"
            + "</transaction>";

    @Test
    public void simplePostTest() {
        String url = "https://testcheckout.ok.gov/commoncheckout/getToken.php";
        CloseableHttpClient client = null;
        try {
            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());

            FileInputStream instream = new FileInputStream(new File(TRUSTSTORE_TEST_LOCATION));

            trustStore.load(instream, "password".toCharArray());

            instream.close();

            X509HostnameVerifier delegate = SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER;
            OkGovHostVerifier okGovVer = new OkGovHostVerifier(delegate);

            // Trust own CA and all self-signed certs
            SSLContext sslcontext = SSLContexts.custom()
                    .loadTrustMaterial(trustStore, null)
                    .build();
            // Allow TLSv1 protocol only
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                    sslcontext, new String[]{"TLSv1"}, null, okGovVer);
            Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder
                    .<ConnectionSocketFactory>create().register("https", sslsf)
                    .build();
            PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
            cm.setMaxTotal(50);

            client = HttpClients.custom()
                    .setConnectionManager(cm)
                    .setSSLSocketFactory(sslsf)
                    .build();

            HttpEntity entity = MultipartEntityBuilder.create().addTextBody("xml", xml).build();
            HttpPost post = new HttpPost(url);
            post.setEntity(entity);

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

            String response = client.execute(post, responseHandler);
            Result result = (Result) JAXBContext.newInstance(Result.class).createUnmarshaller().unmarshal(new StringReader(response));
            System.out.println("result.token: " + result.getToken());
            System.out.println("ok.gov response: " + response);

        } catch (KeyStoreException e) {
            System.out.println("caught KeyStoreException");
            System.out.println(e);
        } catch (FileNotFoundException e) {
            System.out.println("caught FileNotFoundException");
            System.out.println(e);
        } catch (IOException e) {
            System.out.println("caught IOException");
            System.out.println(e);
        } catch (NoSuchAlgorithmException e) {
            System.out.println("caught NoSuchAlgorithmException");
            System.out.println(e);
        } catch (CertificateException e) {
            System.out.println("caught CertificateException");
            System.out.println(e);
        } catch (KeyManagementException e) {
            System.out.println("caught KeyManagementException");
            System.out.println(e);
        } catch (JAXBException e) {
            System.out.println("caught JAXBException");
            System.out.println(e);
        } finally {
            try {
                if (client != null) {
                    client.close();
                }
            } catch (IOException e) {
            }
        }
    }

    @Test
    public void postXmlMessageTest() {
        System.out.println("postXmlMessage");
        String url = "https://testcheckout.ok.gov/commoncheckout/getToken.php";
        String url2 = "https://testcheckout.ok.gov/commoncheckout/getTransactionInfo.php";
        PayOnlineController.setTrustStore(loadTestTrustStore());
        PayOnlineController instance = new PayOnlineController();
        String response = null;
        TransactionPO response2 = null;
        try {
            response = instance.postXmlMessage(xml, url);
            Result result = (Result) JAXBContext.newInstance(Result.class).createUnmarshaller().unmarshal(new StringReader(response));
            response2 = instance.getTransactionDetails(result.getToken(), USER, PASSWORD, url2);
        } catch (OkGovIntegrationException e) {
            //fail("Unable to post to OK.gov");
            System.out.println(e);
        } catch (JAXBException e) {
            System.out.println(e);
        }
        System.out.println("postXmlMessage response: " + response);
    }

    private KeyStore loadTestTrustStore() {
        FileInputStream instream = null;
        KeyStore trustStore = null;
        try {
            trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            instream = new FileInputStream(new File(TRUSTSTORE_TEST_LOCATION));
            trustStore.load(instream, TRUSTSTORE_PASSWORD.toCharArray());
            
        } catch (IOException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } finally {
            try {
                if (instream != null) {
                    instream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return trustStore;
    }
}
