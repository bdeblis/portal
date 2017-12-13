package com.pwc.us.webui.stripes.action;

import com.google.inject.Inject;
import com.pwc.us.common.PayOnline;
import com.pwc.us.common.PayrollReport;
import com.pwc.us.common.exception.GwIntegrationException;
import com.pwc.us.common.exception.OkGovIntegrationException;
import com.pwc.us.common.model.Policyholder;
import com.pwc.us.common.model.Cashier;
import com.pwc.us.common.model.payonline.AddInfo;
import com.pwc.us.common.model.payonline.OkGovResultPO;
import com.pwc.us.common.model.payonline.PayOnlineBillingInfoPO;
import com.pwc.us.common.model.payrollreport.CSPolicyPeriodPR;
import com.pwc.us.common.model.payrollreport.FindPolicyInformationResponsePR;
import com.pwc.us.common.model.payrollreport.FindPolicyNumbersResponsePR;
import com.pwc.us.common.utils.NullChecker;
import com.pwc.us.common.utils.JndiUtils;
import com.pwc.us.common.utils.PhoneHelper;
import com.pwc.us.webui.stripes.util.Security;
import com.pwc.us.wsclient.phauth.PhAuthentication;
import java.math.BigDecimal;
import java.util.Date;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.validation.LocalizableError;
import net.sourceforge.stripes.validation.ValidationErrors;
import net.sourceforge.stripes.validation.ValidationMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author rturnau001
 */
public class PayOnlineActionBean extends BaseActionBean {

    private static final String VIEW = "/WEB-INF/jsp/pay_online.jsp";
    private static final String RESULT_VIEW = "/WEB-INF/jsp/pay_online_result.jsp";
    private static final String OK_GOV_START_PAYMENT_URL = JndiUtils.getOkGovUrl() + "start.php?token=";
    private static final String OTHER_AMT = "otherAmt";
    private static final String COMP_SOURCE_PAYMENT_DESC = "CompSource Premium Payment";
    private static final String UNABLE_TO_DETERMINE_ELIGIBILITY_ERROR = "PayOnline.Error.UnableToDetermineEligibility";
    private static final String UNABLE_TO_RETRIEVE_BILLING_INFO = "PayOnline.Error.UnableToRetrieveBillingInfo";
    private static final String INVALID_ACCOUNT_NUMBER = "PayOnline.Error.InvalidAccount";
    private static final String OK_GOV_DOWN_ERROR = "PayOnline.Error.OkGovDown";
    private static final String AMT_IS_ZERO_ERROR = "PayOnline.Error.AmtIsZero";

    //private Policyholder user;
    private String policyHolderName = null;
    private String accountNumber = null;
    private String token;
    private double amountToPay;
    private BigDecimal unpaidAmount = null;

    PhAuthentication phAuth = new PhAuthentication();
    private Double otherAmount = null;
    private String selectedPayAmt = null;
    private Date dateToday = new Date();
    private PayOnlineBillingInfoPO billingInfo = null;
    private static final Logger logger = LoggerFactory.getLogger(PayOnlineActionBean.class);
    @Inject
    PayOnline po;
    @Inject
    private PayrollReport pr;

    @DefaultHandler
    public Resolution form() {
        if (!Security.checkIfPolicyholder() && !Security.checkIfCashier()) {
            return new ForwardResolution(Logout.class);
        }
        Policyholder policyUser = null;
        Cashier cashierUser = null;
        Resolution ret = null;
        boolean eligible = false;
        if (Security.checkIfPolicyholder()) {
            policyUser = (Policyholder) getUser().getSpecificType();

            try {
                eligible = po.isEligibleForOnlinePayment(policyUser.getPolicyCenterAccountId());
                policyHolderName = AccountNameFilter.filterAccountName(po.getAccountName(policyUser.getPolicyCenterAccountId()));
                logger.info("payment policyholder name before is "+policyHolderName);
                if (policyHolderName != null) {
                    policyHolderName += " " + policyUser.getPolicyCenterAccountId();
                    if (policyHolderName.length() > 200) {
                        policyHolderName = policyHolderName.substring(0, 199);
                    }
                    logger.info("payment policyholder name after is "+policyHolderName);
                }
            } catch (GwIntegrationException e) {
                String message = "Unable to determine whether user " + policyUser.getFirstName() + " " + policyUser.getLastName() + " is eligible for payment.";
                logger.error(message, e);
                getContext().getValidationErrors().addGlobalError(new LocalizableError(UNABLE_TO_DETERMINE_ELIGIBILITY_ERROR));
            }

        } else {

            if (Security.checkIfCashier()) {
                eligible = true;
            }
        }

        if (eligible) {
            try {
                retrieveBillingInfo();
                ret = new ForwardResolution(VIEW);
            } catch (GwIntegrationException e) {
                String message = null;
                if (policyUser != null) {
                    message = "Unable to retrieve billing information for account " + policyUser.getPolicyCenterAccountId();
                } else {
                    message = "Unable to retrieve billing information for account " + cashierUser.getPolicyCenterAccountId();
                }
                logger.error(message, e);
                getContext().getValidationErrors().addGlobalError(new LocalizableError(UNABLE_TO_RETRIEVE_BILLING_INFO));
                ret = new ForwardResolution(RESULT_VIEW);
            }

        } else {
            getContext().getValidationErrors().addGlobalError(new LocalizableError(UNABLE_TO_DETERMINE_ELIGIBILITY_ERROR));
            ret = new ForwardResolution(RESULT_VIEW);
        }

        return ret;
    }
    

    public AddInfo getBillingAddress(String accountNumber){
        AddInfo ai = new AddInfo();
        // this data should be overwritten by the code below, but this will help if
        // for some reason, the employee name is blank.
        if(accountNumber != null)
            ai.setEmpName(accountNumber);
        try {
            FindPolicyNumbersResponsePR.Return pn = pr.findPolicyNumbers(accountNumber);
            if (pn == null || pn.getEntry() == null || pn.getEntry().size() < 1) {
                String add = phAuth.queryAccountAddress(accountNumber);
                String addressStuff [] = add.split("\\^");
                if(addressStuff.length < 6 )
                return ai; // return empty address info
                else{
                    ai.setEmpName(addressStuff[0]+" Acct. "+accountNumber);
                    ai.setAddressLine1(addressStuff[1]);
                    ai.setEmployerCity(addressStuff[2]);
                    ai.setEmployerState(addressStuff[3]);
                    ai.setEmployerZip(addressStuff[4]);
                    ai.setEmpPhone(addressStuff[5]);
                    return ai;
                }
            }
            FindPolicyInformationResponsePR.Return info = pr.findPolicyInformation(pn.getEntry().get(0));
            CSPolicyPeriodPR c = null;
            if (NullChecker.isNotNullOrEmpty(info)) {
                c = info.getCSPolicyPeriod();
            }

            if (c != null) {
                ai = new AddInfo(c.getEmployerAddressLine1(), c.getEmployerCity(),
                AccountNameFilter.filterAccountName(c.getEmpName())+" "+accountNumber, c.getEmployerState(), c.getEmployerZip(),PhoneHelper.validatePhoneNumber(c.getEmpPhone()));
            }
        } catch (GwIntegrationException ex) {
            java.util.logging.Logger.getLogger(PayOnlineActionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ai;
    }

    public Resolution submit() {
        Resolution ret = null;
        Cashier cashierUser = null;
        Policyholder policyUser = null;
        if (Security.checkIfPolicyholder()) {
            policyUser = (Policyholder) getUser().getSpecificType();
        } else {
            cashierUser = (Cashier) getUser().getSpecificType();
        }
        ValidationErrors errors = this.getContext().getValidationErrors();
        double payAmt = 0.0;
        if (this.getAmountToPay() != -1 && this.selectedPayAmt != null && !this.selectedPayAmt.equals(OTHER_AMT)) {
            payAmt = this.getAmountToPay();
        } else {
            payAmt = this.otherAmount;
        }

        try {
            OkGovResultPO res = null;
            if (accountNumber == null) {
                accountNumber = policyUser.getPolicyCenterAccountId();
            }
            if(accountNumber.contains("pc:"))
                accountNumber="";
            AddInfo addInfo = getBillingAddress(accountNumber);
            if (addInfo == null || addInfo.getAddressLine1().isEmpty()) {
                logger.error("OkGovIntegrationException while trying to obtain payment token from Ok.gov");
                errors.addGlobalError(new LocalizableError(INVALID_ACCOUNT_NUMBER));
                return new ForwardResolution(RESULT_VIEW);
            }
            if (Security.checkIfPolicyholder()) {
                res = po.getOkGovToken(policyUser.getPolicyCenterAccountId(), policyUser, payAmt, COMP_SOURCE_PAYMENT_DESC, addInfo);
            } else {
                policyHolderName = AccountNameFilter.filterAccountName(policyHolderName);
                logger.info("payment policyholder name before is "+policyHolderName);
                if (policyHolderName != null && policyHolderName.length() > 200) {
                        policyHolderName = policyHolderName.substring(0, 199);
                        logger.info("payment policyholder name after is "+policyHolderName);
                }
                if (addInfo.getEmpName().isEmpty()) {
                    addInfo.setEmpName(policyHolderName);
                }
                res = po.getOkGovToken(policyHolderName, accountNumber, cashierUser, payAmt, COMP_SOURCE_PAYMENT_DESC, addInfo);
            }
            /*
             Note: sometimes the token is null later in the process chain,
             so let's save it in the session just in case.
             */
            if (NullChecker.isNullOrEmpty(res.getToken())) {
                String message = "Error ok.gov token is null ";
                logger.error(message);
                throw new OkGovIntegrationException(message);
            }

            if (NullChecker.isNotNullOrEmpty(res) && NullChecker.isNotNullOrEmpty(res.getError())) {
                String message = "Error returned from OK.gov: " + res.getError();
                logger.error(message);
                throw new OkGovIntegrationException(message);
            }
            getContext().getServletContext().setAttribute("okgovtoken", res.getToken());
            this.setToken(res.getToken());
            String okGovUrl = OK_GOV_START_PAYMENT_URL + this.getToken();
            ret = new RedirectResolution(okGovUrl, false);
        } catch (OkGovIntegrationException e) {
            logger.error("OkGovIntegrationException while trying to obtain payment token from Ok.gov", e);
            errors.addGlobalError(new LocalizableError(OK_GOV_DOWN_ERROR));
            ret = new ForwardResolution(RESULT_VIEW);
        } 

        return ret;
    }

    public Resolution cancel() {
        return new ForwardResolution(PayOnlineCancelActionBean.class);
    }

    @ValidationMethod(on = "submit")
    public void validateNameAndAccount(ValidationErrors errors) {
        if (!Security.checkIfPolicyholder()) {
            if (NullChecker.isNullOrEmpty(this.accountNumber)) {
                errors.addGlobalError(new LocalizableError("PayOnline.Error.AccountNumber"));
            }
            if (NullChecker.isNullOrEmpty(this.policyHolderName)) {
                errors.addGlobalError(new LocalizableError("PayOnline.Error.PolicyHolderName"));
            }
        }
    }

    @ValidationMethod(on = "submit")
    public void validateOtherAmount(ValidationErrors errors) {
        if (NullChecker.isNotNullOrEmpty(this.getSelectedPayAmt()) && this.getSelectedPayAmt().equals(OTHER_AMT)) {
            if (this.getOtherAmount() == null) {
                errors.add("otherAmount",
                        new LocalizableError("PayOnline.Error.MustSupplyOtherAmount"));
            }
            if (this.getOtherAmount() <= 0) {
                errors.add("otherAmount",
                        new LocalizableError("PayOnline.Error.OtherAmountMustBeGreaterThanOne"));
            }
        }
    }

    @ValidationMethod(on = "submit")
    public void validateAmtGreaterThanZero(ValidationErrors errors) {
        double payAmt = 0.0;
        if (this.selectedPayAmt != null && this.getAmountToPay() != -1 && !this.selectedPayAmt.equals(OTHER_AMT)) {
            payAmt = this.getAmountToPay();
        } else if (this.otherAmount != null) {
            payAmt = this.otherAmount;
        }
        if (this.otherAmount == null && payAmt <= 0.0) {
            errors.addGlobalError(new LocalizableError(AMT_IS_ZERO_ERROR));
        }
    }

    public void retrieveBillingInfo() throws GwIntegrationException {
        Policyholder policyUser = null;
        Cashier cashierUser = null;
        if (Security.checkIfPolicyholder()) {
            policyUser = (Policyholder) getUser().getSpecificType();
            this.billingInfo = po.retrieveBillingInfo(policyUser.getPolicyCenterAccountId());
        } else {
            cashierUser = (Cashier) getUser().getSpecificType();
            this.billingInfo = po.retrieveBillingInfo(cashierUser.getPolicyCenterAccountId());
        }
    }

    public Date getDateToday() {
        return dateToday;
    }

    public PayOnlineBillingInfoPO getBillingInfo() {
        return billingInfo;
    }

    public void setBillingInfo(PayOnlineBillingInfoPO billingInfo) {
        this.billingInfo = billingInfo;
    }

    public Double getOtherAmount() {
        return otherAmount;
    }

    public void setOtherAmount(Double otherAmount) {
        this.otherAmount = otherAmount;
    }

    public String getSelectedPayAmt() {
        return selectedPayAmt;
    }

    public void setSelectedPayAmt(String selectedPayAmt) {
        this.selectedPayAmt = selectedPayAmt;
    }

    public String getPolicyHolderName() {
        return policyHolderName;
    }

    public void setPolicyHolderName(String policyHolderName) {
        this.policyHolderName = policyHolderName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public double getAmountToPay() {
        return amountToPay;
    }

    public void setAmountToPay(double amountToPay) {
        this.amountToPay = amountToPay;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public BigDecimal getUnpaidAmount() {
        return unpaidAmount;
    }

    public void setUnpaidAmount(BigDecimal currentAccountBalance) {
        this.unpaidAmount = currentAccountBalance;
    }

}
