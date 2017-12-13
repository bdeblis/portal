package com.pwc.us.wsclient.payonline;

import com.pwc.us.common.exception.GwIntegrationException;
import com.pwc.us.common.model.payonline.PayOnlineBillingInfoPO;
import com.pwc.us.common.utils.DateTimeHelper;
import com.pwc.us.common.utils.NullChecker;
import com.pwc.us.ws.client.payonline.PayOnlineAPIPortType;
import com.pwc.us.ws.client.payonline.Authentication;
import com.pwc.us.ws.client.payonline.Locale;
import com.pwc.us.ws.client.payonline.PayOnlineBillingInfo;
import com.pwc.us.wsclient.config.DefaultHeaderValues;
import com.pwc.us.wsclient.config.ServiceCreationUtil;
import com.pwc.us.common.utils.JndiUtils;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.util.Date;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.ws.WebServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Roger
 */
public class PayOnlineInt {

    private static String WSDL = JndiUtils.getBillingCenterUrl() + "/ws/compsource/bc/api/payonline/PayOnlineAPI?WSDL";
    private static String SVC_QNAME_NAMESPACE_URI = "http://guidewire.com/compsource/bc/api/payonline/PayOnlineAPI";
    private static String SVC_QNAME_LOCALPART = "PayOnlineAPI";
    private static String PORT_QNAME_NAMESPACE_URI = "http://guidewire.com/compsource/bc/api/payonline/PayOnlineAPI";
    private static String PORT_QNAME_LOCALPART = "PayOnlineAPISoap11Port";
    private static final int REQUEST_TIMEOUT = 180000;
    private static final int CONNECT_TIMEOUT = 180000;
    private static final Authentication gwAuthentication = new Authentication();
    private static final Locale gwLocale = new Locale();
    private static final Logger logger = LoggerFactory.getLogger(PayOnlineInt.class);
    private static PayOnlineAPIPortType port = null;

    static {
        gwAuthentication.setUsername(JndiUtils.getGwUser());
        gwAuthentication.setPassword(JndiUtils.getGwPassword());

        gwLocale.setValue(DefaultHeaderValues.LOCALE);
    }
    
    public String getAccountName(String accountNumber)throws GwIntegrationException {
        connectPort();
        String ret = port.getAccountName(accountNumber, gwAuthentication, gwLocale);
        return ret;
    }
    
    public BigDecimal getUnpaidAmount(String accountNumber)throws GwIntegrationException {
        connectPort();
        BigDecimal ret = port.getUnpaidAmount(accountNumber, gwAuthentication, gwLocale);
        return ret;
    }
            

    public boolean eligibleForPayOnline(String accountNumber) throws GwIntegrationException {
        connectPort();
        boolean ret = port.eligibleForPayOnline(accountNumber, gwAuthentication, gwLocale);
        return ret;
    }

    public PayOnlineBillingInfoPO getBillingInfo(String accountNumber) throws GwIntegrationException {
        connectPort();
        PayOnlineBillingInfo info = port.getBillingInfo(accountNumber, gwAuthentication, gwLocale);
        PayOnlineBillingInfoPO ret = new PayOnlineBillingInfoPO();
        ret.setAccountNumber(accountNumber);
        if (NullChecker.isNotNullOrEmpty(info)) {
            if (NullChecker.isNotNullOrEmpty(info.getCurrentAccountBalance())) {
                ret.setCurrentAccountBalance(info.getCurrentAccountBalance());
            }
            if (NullChecker.isNotNullOrEmpty(info.getCurrentAccountBalanceTimestamp())) {
                ret.setCurrentAccountBalanceTimestamp(DateTimeHelper.xmlGregorianCalendarToDate(info.getCurrentAccountBalanceTimestamp()));
            }
            if (NullChecker.isNotNullOrEmpty(info.getCurrentBalanceBilled())) {
                ret.setCurrentBalanceBilled(info.getCurrentBalanceBilled());
            }
            if (NullChecker.isNotNullOrEmpty(info.getDueDate())) {
                ret.setDueDate(DateTimeHelper.xmlGregorianCalendarToDate(info.getDueDate()));
            }
            if (NullChecker.isNotNullOrEmpty(info.getPastDueBalanceBilled())) {
                ret.setPastDueBalanceBilled(info.getPastDueBalanceBilled());
            }
            if (NullChecker.isNotNullOrEmpty(info.getTotalAmountBilled())) {
                ret.setTotalAmountBilled(info.getTotalAmountBilled());
            }
        }
        return ret;
    }

    public boolean makePaymentWithPaymentType(String transactionId, String accountNumber, double paymentAmount, Date receivedDate, String paymentType) throws GwIntegrationException {
        connectPort();
        boolean ret = false;
        try {
            ret = port.makePaymentWithPaymentType(transactionId, accountNumber, paymentAmount, DateTimeHelper.dateToXMLGregorianCalendar(receivedDate), paymentType ,gwAuthentication, gwLocale);
        } catch (ParseException e) {
            String message = "Caught ParseException while trying to call makePayment in BillingCenter.";
            logger.error(message, e);
            throw new GwIntegrationException(message, e);
        } catch (DatatypeConfigurationException e) {
            String message = "Caught DatatypeConfigurationException while trying to call makePayment in BillingCenter.";
            logger.error(message, e);
            throw new GwIntegrationException(message, e);
        }

        return ret;
    }

    private void connectPort() throws GwIntegrationException {
        if (port == null) {
            try {
                port = ServiceCreationUtil.createService(WSDL,
                        SVC_QNAME_NAMESPACE_URI, SVC_QNAME_LOCALPART,
                        PORT_QNAME_NAMESPACE_URI, PORT_QNAME_LOCALPART,
                        PayOnlineAPIPortType.class, REQUEST_TIMEOUT, CONNECT_TIMEOUT);
            } catch (WebServiceException e) {
                logger.error("could not create service", e);
                throw new GwIntegrationException(
                        "unable to create first notice of injury web service for class CSNewSubmissionAPIPortType", e);

            } catch (MalformedURLException e) {
                logger.error("Malformed URL creating first notice of injury web service for class CSNewSubmissionAPIPortType ");
                throw new GwIntegrationException("malformed URL while creating "
                        + "request a quote web service for class CSNewSubmissionAPIPortType", e);
            }
        }
    }
}
