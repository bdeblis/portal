package com.pwc.us.common;

import com.pwc.us.common.exception.CsoUserUpdateException;
import com.pwc.us.common.exception.GwIntegrationException;
import com.pwc.us.common.exception.OkGovIntegrationException;
import com.pwc.us.common.model.Cashier;
import com.pwc.us.common.model.Policyholder;
import com.pwc.us.common.model.payonline.OkGovResultPO;
import com.pwc.us.common.model.payonline.PayOnlineBillingInfoPO;
import com.pwc.us.common.model.payonline.TransactionPO;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author Roger
 */
public interface PayOnline {
    
    public boolean isEligibleForOnlinePayment(String pcAccount) throws GwIntegrationException;
    
    public PayOnlineBillingInfoPO retrieveBillingInfo(String accountNumber) throws GwIntegrationException;
    
    public boolean makePaymentWithPaymentType(String transactionId, String accountNumber, double paymentAmount, Date receivedDate, String paymentType) throws GwIntegrationException;
    
    public OkGovResultPO getOkGovToken(String accountNumber, Policyholder user, double paymentAmount, String itemDesc) throws OkGovIntegrationException;
    
    public OkGovResultPO getOkGovToken(String name,String accountNumber, Cashier user, double paymentAmount, String itemDesc) throws OkGovIntegrationException;
    
    public void updateUserOkGovCustId(Policyholder user, String okGovId) throws CsoUserUpdateException;
    
    public void updateUserOkGovCustId(Cashier user, String okGovId) throws CsoUserUpdateException;
    
    public TransactionPO querySuccessfulTransaction(String token) throws OkGovIntegrationException;
    
    public String getAccountName(String accountNumber) throws GwIntegrationException;
    
    public BigDecimal getUnpaidAmount(String accountNumber) throws GwIntegrationException;
    
}
