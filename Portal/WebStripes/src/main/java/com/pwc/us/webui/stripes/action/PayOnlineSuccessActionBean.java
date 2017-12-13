package com.pwc.us.webui.stripes.action;

import com.google.inject.Inject;
import com.pwc.us.common.PayOnline;
import com.pwc.us.common.exception.CsoUserUpdateException;
import com.pwc.us.common.exception.GwIntegrationException;
import com.pwc.us.common.exception.OkGovIntegrationException;
import com.pwc.us.common.model.Cashier;
import com.pwc.us.common.model.Policyholder;
import com.pwc.us.common.model.payonline.TransactionPO;
import com.pwc.us.webui.stripes.util.Security;
import java.util.List;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.LocalizableMessage;
import net.sourceforge.stripes.action.Message;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.validation.LocalizableError;
import net.sourceforge.stripes.validation.ValidationErrors;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author rturnau001
 */
public class PayOnlineSuccessActionBean extends BaseActionBean {

    private static final String UNABLE_TO_STORE_OK_GOV_CUST_ID = "PayOnline.Error.UnableToStoreOkGovCustId";
    private static final String UNABLE_TO_CAPTURE_BILLING_INFO = "PayOnline.Error.UnableToRetrieveTransactionInfo";
    private static final String PAY_SUCCESS_MESSAGE = "PayOnline.Success";
    private static final String TRANS_DATE_PATTERN = "MM/dd/yyyy";
    private static final String RESULT_VIEW = "/WEB-INF/jsp/pay_online_result.jsp";
    private String token;
    private String processed_trans_id;
    private String customer_id;
    private static final Logger logger = LoggerFactory.getLogger(PayOnlineSuccessActionBean.class);
    @Inject
    PayOnline po;

    @DefaultHandler
    public Resolution form() {
        Policyholder policyUser = null;
        Cashier cashierUser = null;
        if (Security.checkIfPolicyholder()) 
            policyUser = (Policyholder) getUser().getSpecificType();
        else
            cashierUser = (Cashier) getUser().getSpecificType();
        ValidationErrors errors = this.getContext().getValidationErrors();
        List<Message> messages = this.getContext().getMessages();
        // store customer ID in user's record.
        if (this.customer_id != null) {
            try {
                if (Security.checkIfPolicyholder()) {
                    po.updateUserOkGovCustId(policyUser, this.getCustomer_id());
                    policyUser.setOkGovUserId(this.getCustomer_id());
                }
                else{
                    po.updateUserOkGovCustId(cashierUser, this.getCustomer_id());
                    cashierUser.setOkGovUserId(this.getCustomer_id());
                }
            } catch (CsoUserUpdateException e) {
                logger.error("CsoUserUpdateException while trying to store Ok.gov customer id", e);
                // this is not really an error -- more of a minor inconvenience for the user.
                messages.add(new LocalizableMessage(UNABLE_TO_STORE_OK_GOV_CUST_ID));
            }
        }
        try {
            // Query Ok.gov for transaction details.
            TransactionPO trans = po.querySuccessfulTransaction(token);
            DateTimeFormatter df = DateTimeFormat.forPattern(TRANS_DATE_PATTERN);
            String transDateStr = trans.getTransDate();
            
            DateTime transDate = df.parseDateTime(transDateStr);
            // Billed amount does not include the OK.gov transaction fee.
            double amt = trans.getAmountPaid() - trans.getOnlineFee();
            if (Security.checkIfPolicyholder()) 
                po.makePaymentWithPaymentType(trans.getProcessedTransId(), policyUser.getPolicyCenterAccountId(), amt, transDate.toDate(),trans.getPaymentType());
            else
                po.makePaymentWithPaymentType(trans.getProcessedTransId(), trans.getLocalReference(), amt, transDate.toDate(),trans.getPaymentType());
        } catch (OkGovIntegrationException e) {
            logger.error("Caught error trying to query successful transaction.", e);
            errors.addGlobalError(new LocalizableError(UNABLE_TO_CAPTURE_BILLING_INFO));
        } catch (GwIntegrationException e) {
            logger.error("Caught error trying to send transaction information to BillingCenter.", e);
            errors.addGlobalError(new LocalizableError(UNABLE_TO_CAPTURE_BILLING_INFO));
        }

        getContext().getMessages().add(new LocalizableMessage(PAY_SUCCESS_MESSAGE));
        return new ForwardResolution(RESULT_VIEW);
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }
    
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
    
    public String getProcessed_trans_id() {
        return processed_trans_id;
    }

    public void setProcessed_trans_id(String processed_trans_id) {
        this.processed_trans_id = processed_trans_id;
    }
}
