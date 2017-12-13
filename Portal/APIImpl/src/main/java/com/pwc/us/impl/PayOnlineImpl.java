package com.pwc.us.impl;

import com.google.inject.Inject;
import com.pwc.us.common.Authentication;
import com.pwc.us.common.PayOnline;
import com.pwc.us.common.exception.CsoUserUpdateException;
import com.pwc.us.common.exception.GwIntegrationException;
import com.pwc.us.common.exception.OkGovIntegrationException;
import com.pwc.us.common.model.Cashier;
import com.pwc.us.common.model.Policyholder;
import com.pwc.us.common.model.payonline.AddInfo;
import com.pwc.us.common.model.payonline.ItemPO;
import com.pwc.us.common.model.payonline.OkGovResultPO;
import com.pwc.us.common.model.payonline.PayOnlineBillingInfoPO;
import com.pwc.us.common.model.payonline.TransactionPO;
import com.pwc.us.common.utils.NullChecker;
import com.pwc.us.common.utils.JndiUtils;
import com.pwc.us.common.utils.PhoneHelper;
import com.pwc.us.wsclient.payonline.PayOnlineInt;
import com.pwc.us.wsclient.payonline.rest.PayOnlineController;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import javax.swing.text.MaskFormatter;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Roger
 */
public class PayOnlineImpl implements PayOnline {
    
    private static final int AUTOCOMPLETE_OFF = 0;
    private static final String ALLOWED_PAY_TYPES_CC = "CC";
    private static final String ALLOWED_PAY_TYPES_ALL = "ALL";
    private static final String OK_GOV_LOGIN = JndiUtils.getOkGovLogin();
    private static final String OK_GOV_PASSWORD = JndiUtils.getOkGovPassword();
    private static final String OK_GOV_URL = JndiUtils.getOkGovUrl();
    private static final String SUCCESS_URL = JndiUtils.getOkGovSuccessUrl();
    private static final String FAILURE_URL = JndiUtils.getOkGovFailureUrl();
    private static final String CANCEL_URL = JndiUtils.getOkGovCancelUrl();
    private static final String GET_TOKEN = "getToken.php";
    private static final String GET_TRANSACTION_INFO = "getTransactionInfo.php";
    private PayOnlineInt payInt = new PayOnlineInt();
    private PayOnlineController poc = new PayOnlineController();
    private static final String CDATA_START =  "<![CDATA[";
    private static final String CDATA_END =  "]]>";
    private static final Logger logger = LoggerFactory.getLogger(PayOnlineImpl.class);
    
    @Inject
    private Authentication authService;

    public boolean isEligibleForOnlinePayment(String pcAccount) throws GwIntegrationException {
        return payInt.eligibleForPayOnline(pcAccount);
    }

    public PayOnlineBillingInfoPO retrieveBillingInfo(String accountNumber) throws GwIntegrationException {
        BigDecimal bd = getUnpaidAmount(accountNumber);
        PayOnlineBillingInfoPO po = payInt.getBillingInfo(accountNumber);
        po.setUnpaidAmount(bd);
        return po;
    }

    public boolean makePaymentWithPaymentType(String transactionId, String accountNumber, double paymentAmount, Date receivedDate, String paymentType) throws GwIntegrationException {
        return payInt.makePaymentWithPaymentType(transactionId, accountNumber, paymentAmount, receivedDate,paymentType);
    }
    
    public String getAccountName(String acct) throws GwIntegrationException {
        return payInt.getAccountName(acct);
    }

    public OkGovResultPO getOkGovToken(String accountNumber, Policyholder user, double paymentAmount, String itemDesc,AddInfo addInfo) throws OkGovIntegrationException {
        TransactionPO trans = createTransaction(accountNumber, user, paymentAmount, itemDesc);
        /*
        ok.gov doesn't want pretty formatting for the zip and phone!
        just saving this because it might be useful in the future
        
        String zip="";
        String phone="";
        try {        
            MaskFormatter f = new MaskFormatter("#####-####");
            MaskFormatter g = new MaskFormatter("###-###-####");
            if(addInfo.getEmployerZip() != null){
             zip=f.valueToString(addInfo.getEmployerZip()).trim();
             if(zip.endsWith("-"))
                 zip = zip.substring(0,zip.length()-1);
            }
            if(addInfo.getEmpPhone() != null){
                phone = g.valueToString(addInfo.getEmpPhone().trim());
                if(phone.endsWith("-"))
                 phone = phone.substring(0,phone.length()-1);
            }
        } catch (ParseException ex) {
            logger.error("MaskFormatter error", ex);
        }
        */
        trans.setAddress1(addInfo.getAddressLine1());
        trans.setCityProvince(addInfo.getEmployerCity());
        trans.setNameOnAccount(addInfo.getEmpName());
        trans.setState(addInfo.getEmployerState());
        trans.setZip(addInfo.getEmployerZip());
        trans.setPhone(PhoneHelper.validatePhoneNumber(addInfo.getEmpPhone()));
        OkGovResultPO res = poc.getOkGovToken(trans, OK_GOV_URL + GET_TOKEN);
          
        return res;
    }
    
    public OkGovResultPO getOkGovToken(String name,String accountNumber, Cashier user, double paymentAmount, String itemDesc,AddInfo addInfo) throws OkGovIntegrationException {
       
        TransactionPO trans = createTransaction(accountNumber, user, paymentAmount, itemDesc);
        /*
        ok.gov doesn't want pretty formatting for the zip and phone!
        just saving this because it might be useful in the future
        
        String zip="";
        String phone="";
        try {        
            MaskFormatter f = new MaskFormatter("#####-####");
            MaskFormatter g = new MaskFormatter("###-###-####");
            if(addInfo.getEmployerZip() != null){
             zip=f.valueToString(addInfo.getEmployerZip()).trim();
             if(zip.endsWith("-"))
                 zip = zip.substring(0,zip.length()-1);
            }
            if(addInfo.getEmpPhone() != null){
                phone = g.valueToString(addInfo.getEmpPhone().trim());
                if(phone.endsWith("-"))
                 phone = phone.substring(0,phone.length()-1);
            }
        } catch (ParseException ex) {
            logger.error("MaskFormatter error", ex);
        }
        */
        trans.setAddress1(addInfo.getAddressLine1());
        trans.setCityProvince(addInfo.getEmployerCity());
        trans.setNameOnAccount(addInfo.getEmpName());
        trans.setState(addInfo.getEmployerState());
        trans.setZip(addInfo.getEmployerZip());
        trans.setPhone(PhoneHelper.validatePhoneNumber(addInfo.getEmpPhone()));
    /*    
        trans.setNameOnAccount(name+" - "+accountNumber);
        trans.setAddress1("Cashier - "+user.getFirstName()+" , "+user.getLastName());
        trans.setAddress2("");
        trans.setCityProvince("");
        trans.setState("");
        trans.setZip("");
    */    
        OkGovResultPO res = poc.getOkGovToken(trans, OK_GOV_URL + GET_TOKEN);
        
        return res;
    }
 
    
    private TransactionPO createTransaction(String accountNumber, Policyholder user, double paymentAmount, String itemDesc){
        TransactionPO trans = new TransactionPO();
        trans.setLoginId(OK_GOV_LOGIN);
        trans.setPassword(OK_GOV_PASSWORD);
        trans.setAutocomplete(AUTOCOMPLETE_OFF);
        if (NullChecker.isNotNullOrEmpty(user.getOkGovUserId()) && !user.getOkGovUserId().equals("null")) {
            trans.setCustomerId(user.getOkGovUserId());
        }
        String name="";
        try {
            name = getAccountName(user.getPolicyCenterAccountId());
        } catch (GwIntegrationException ex) {
            logger.error("Name is empty", ex);
        }
        trans.setNameOnAccount(name+" Acct. "+accountNumber);
        trans.setLocalReference(accountNumber);
        trans.setEmail(user.getEmail());
        trans.setPhone(user.getPhone());
        trans.setAllowedPayTypes(ALLOWED_PAY_TYPES_CC);
        trans.setAmountPaid(paymentAmount);
        trans.setSuccessUrl(SUCCESS_URL);
        trans.setFailureUrl(FAILURE_URL);
        trans.setCancelUrl(CANCEL_URL);
        DateTimeFormatter fmt = DateTimeFormat.forPattern("MM-dd-yyyy");
        String today = new DateTime().toString(fmt);
        trans.setMaxFutureDate(today);
        
        ItemPO item = new ItemPO();
        item.setCost(paymentAmount);
        item.setTitle(itemDesc);
        item.setQuantity(1);
        List<ItemPO> items = new ArrayList<ItemPO>();
        items.add(item);
        trans.setItems(items);
        
        return trans;
    }

    
     private TransactionPO createTransaction(String accountNumber, Cashier user, double paymentAmount, String itemDesc) {
        TransactionPO trans = new TransactionPO();
        trans.setLoginId(OK_GOV_LOGIN);
        trans.setPassword(OK_GOV_PASSWORD);
        trans.setAutocomplete(AUTOCOMPLETE_OFF);
        /*
        if (NullChecker.isNotNullOrEmpty(user.getOkGovUserId()) && !user.getOkGovUserId().equals("null")) {
            trans.setCustomerId(user.getOkGovUserId());
        }
        */
        trans.setCustomerId("");
        trans.setNameOnAccount(user.getCompanyName()+" Acct. "+accountNumber);
        trans.setLocalReference(accountNumber);
        trans.setEmail(user.getEmail());
        trans.setPhone(user.getPhone());
        trans.setAllowedPayTypes(ALLOWED_PAY_TYPES_ALL);
        trans.setAmountPaid(paymentAmount);
        trans.setSuccessUrl(SUCCESS_URL);
        trans.setFailureUrl(FAILURE_URL);
        trans.setCancelUrl(CANCEL_URL);
        DateTimeFormatter fmt = DateTimeFormat.forPattern("MM-dd-yyyy");
        String today = new DateTime().toString(fmt);
        trans.setMaxFutureDate(today);
        
        ItemPO item = new ItemPO();
        item.setCost(paymentAmount);
        item.setTitle(itemDesc);
        item.setQuantity(1);
        List<ItemPO> items = new ArrayList<ItemPO>();
        items.add(item);
        trans.setItems(items);
        
        return trans;
    }

    public void updateUserOkGovCustId(Cashier user, String okGovId) throws CsoUserUpdateException {
        authService.setOkGovCustomerId(user.getLogin(), okGovId);
    }
    
    
    
    public void updateUserOkGovCustId(Policyholder user, String okGovId) throws CsoUserUpdateException {
        authService.setOkGovCustomerId(user.getLogin(), okGovId);
    }

    public TransactionPO querySuccessfulTransaction(String token) throws OkGovIntegrationException {
        TransactionPO ret = poc.getTransactionDetails(token, OK_GOV_LOGIN, OK_GOV_PASSWORD, OK_GOV_URL + GET_TRANSACTION_INFO);
        return ret;
    }

    public BigDecimal getUnpaidAmount(String accountNumber) throws GwIntegrationException {
        return payInt.getUnpaidAmount(accountNumber);
    }
    
}
