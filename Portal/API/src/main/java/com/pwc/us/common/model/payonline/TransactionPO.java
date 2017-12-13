package com.pwc.us.common.model.payonline;

import java.util.List;

/**
 *
 * @author Roger
 */
public class TransactionPO {

    protected String loginId;
    protected String password;
    protected String customerId;
    protected int autocomplete;
    protected String nameOnAccount;
    protected String localReference;
    protected String address1;
    protected String address2;
    protected String cityProvince;
    protected String state;
    protected String country;
    protected String zip;
    protected String email;
    protected String phone;
    protected double amountPaid;
    protected String extra01;
    protected String extra02;
    protected String extra03;
    protected String extra04;
    protected String extra05;
    protected String maxFutureDate;
    protected String successUrl;
    protected String failureUrl;
    protected String cancelUrl;
    protected String allowedPayTypes;
    protected List<ItemPO> items;
    
    protected String initiatePaymentDate;
    protected String transDate;
    protected String processedTransId;
    protected String paymentType;
    protected double onlineFee;
    protected String lastFourAcct;
    protected String token;
    protected String failedDate;
    protected String failedReason;
    protected String pendingTransId;
    protected String cancelled;
    protected String cancelledDate;
    protected String cancelledNotes;
    protected String authCode;
    
    

    /**
     * Gets the value of the loginId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLoginId() {
        return loginId;
    }

    /**
     * Sets the value of the loginId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLoginId(String value) {
        this.loginId = value;
    }

    /**
     * Gets the value of the password property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the value of the password property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPassword(String value) {
        this.password = value;
    }

    /**
     * Gets the value of the customerId property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public String getCustomerId() {
        return customerId;
    }

    /**
     * Sets the value of the customerId property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setCustomerId(String value) {
        this.customerId = value;
    }

    /**
     * Gets the value of the autocomplete property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public int getAutocomplete() {
        return autocomplete;
    }

    /**
     * Sets the value of the autocomplete property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setAutocomplete(int value) {
        this.autocomplete = value;
    }

    /**
     * Gets the value of the nameOnAccount property.
     * 
     * @return
     *     possible object is
     *     {@link NameOnAccount }
     *     
     */
    public String getNameOnAccount() {
        return nameOnAccount;
    }

    /**
     * Sets the value of the nameOnAccount property.
     * 
     * @param value
     *     allowed object is
     *     {@link NameOnAccount }
     *     
     */
    public void setNameOnAccount(String value) {
        this.nameOnAccount = value;
    }

    /**
     * Gets the value of the localReference property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLocalReference() {
        return localReference;
    }

    /**
     * Sets the value of the localReference property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLocalReference(String value) {
        this.localReference = value;
    }

    /**
     * Gets the value of the address1 property.
     * 
     * @return
     *     possible object is
     *     {@link Address1 }
     *     
     */
    public String getAddress1() {
        return address1;
    }

    /**
     * Sets the value of the address1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Address1 }
     *     
     */
    public void setAddress1(String value) {
        this.address1 = value;
    }

    /**
     * Gets the value of the address2 property.
     * 
     * @return
     *     possible object is
     *     {@link Address2 }
     *     
     */
    public String getAddress2() {
        return address2;
    }

    /**
     * Sets the value of the address2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Address2 }
     *     
     */
    public void setAddress2(String value) {
        this.address2 = value;
    }

    /**
     * Gets the value of the cityProvince property.
     * 
     * @return
     *     possible object is
     *     {@link CityProvince }
     *     
     */
    public String getCityProvince() {
        return cityProvince;
    }

    /**
     * Sets the value of the cityProvince property.
     * 
     * @param value
     *     allowed object is
     *     {@link CityProvince }
     *     
     */
    public void setCityProvince(String value) {
        this.cityProvince = value;
    }

    /**
     * Gets the value of the state property.
     * 
     * @return
     *     possible object is
     *     {@link State }
     *     
     */
    public String getState() {
        return state;
    }

    /**
     * Sets the value of the state property.
     * 
     * @param value
     *     allowed object is
     *     {@link State }
     *     
     */
    public void setState(String value) {
        this.state = value;
    }

    /**
     * Gets the value of the country property.
     * 
     * @return
     *     possible object is
     *     {@link Country }
     *     
     */
    public String getCountry() {
        return country;
    }

    /**
     * Sets the value of the country property.
     * 
     * @param value
     *     allowed object is
     *     {@link Country }
     *     
     */
    public void setCountry(String value) {
        this.country = value;
    }

    /**
     * Gets the value of the zip property.
     * 
     * @return
     *     possible object is
     *     {@link Zip }
     *     
     */
    public String getZip() {
        return zip;
    }

    /**
     * Sets the value of the zip property.
     * 
     * @param value
     *     allowed object is
     *     {@link Zip }
     *     
     */
    public void setZip(String value) {
        this.zip = value;
    }

    /**
     * Gets the value of the email property.
     * 
     * @return
     *     possible object is
     *     {@link Email }
     *     
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the value of the email property.
     * 
     * @param value
     *     allowed object is
     *     {@link Email }
     *     
     */
    public void setEmail(String value) {
        this.email = value;
    }

    /**
     * Gets the value of the phone property.
     * 
     * @return
     *     possible object is
     *     {@link Phone }
     *     
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets the value of the phone property.
     * 
     * @param value
     *     allowed object is
     *     {@link Phone }
     *     
     */
    public void setPhone(String value) {
        this.phone = value;
    }

    /**
     * Gets the value of the amountPaid property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public double getAmountPaid() {
        return amountPaid;
    }

    /**
     * Sets the value of the amountPaid property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setAmountPaid(double value) {
        this.amountPaid = value;
    }

    /**
     * Gets the value of the extra01 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExtra01() {
        return extra01;
    }

    /**
     * Sets the value of the extra01 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExtra01(String value) {
        this.extra01 = value;
    }

    /**
     * Gets the value of the extra02 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExtra02() {
        return extra02;
    }

    /**
     * Sets the value of the extra02 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExtra02(String value) {
        this.extra02 = value;
    }

    /**
     * Gets the value of the extra03 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExtra03() {
        return extra03;
    }

    /**
     * Sets the value of the extra03 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExtra03(String value) {
        this.extra03 = value;
    }

    /**
     * Gets the value of the extra04 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExtra04() {
        return extra04;
    }

    /**
     * Sets the value of the extra04 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExtra04(String value) {
        this.extra04 = value;
    }

    /**
     * Gets the value of the extra05 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExtra05() {
        return extra05;
    }

    /**
     * Sets the value of the extra05 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExtra05(String value) {
        this.extra05 = value;
    }

    /**
     * Gets the value of the maxFutureDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMaxFutureDate() {
        return maxFutureDate;
    }

    /**
     * Sets the value of the maxFutureDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMaxFutureDate(String value) {
        this.maxFutureDate = value;
    }

    /**
     * Gets the value of the successUrl property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSuccessUrl() {
        return successUrl;
    }

    /**
     * Sets the value of the successUrl property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSuccessUrl(String value) {
        this.successUrl = value;
    }

    /**
     * Gets the value of the failureUrl property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFailureUrl() {
        return failureUrl;
    }

    /**
     * Sets the value of the failureUrl property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFailureUrl(String value) {
        this.failureUrl = value;
    }

    /**
     * Gets the value of the cancelUrl property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCancelUrl() {
        return cancelUrl;
    }

    /**
     * Sets the value of the cancelUrl property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCancelUrl(String value) {
        this.cancelUrl = value;
    }

    /**
     * Gets the value of the allowedPayTypes property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAllowedPayTypes() {
        return allowedPayTypes;
    }

    /**
     * Sets the value of the allowedPayTypes property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAllowedPayTypes(String value) {
        this.allowedPayTypes = value;
    }

    /**
     * Gets the value of the items property.
     * 
     * @return
     *     possible object is
     *     {@link Items }
     *     
     */
    public List<ItemPO> getItems() {
        return items;
    }

    /**
     * Sets the value of the items property.
     * 
     * @param value
     *     allowed object is
     *     {@link Items }
     *     
     */
    public void setItems(List<ItemPO> value) {
        this.items = value;
    }

    public String getInitiatePaymentDate() {
        return initiatePaymentDate;
    }

    public void setInitiatePaymentDate(String initiatePaymentDate) {
        this.initiatePaymentDate = initiatePaymentDate;
    }

    public String getTransDate() {
        return transDate;
    }

    public void setTransDate(String transDate) {
        this.transDate = transDate;
    }

    public String getProcessedTransId() {
        return processedTransId;
    }

    public void setProcessedTransId(String processedTransId) {
        this.processedTransId = processedTransId;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public double getOnlineFee() {
        return onlineFee;
    }

    public void setOnlineFee(double onlineFee) {
        this.onlineFee = onlineFee;
    }

    public String getLastFourAcct() {
        return lastFourAcct;
    }

    public void setLastFourAcct(String lastFourAcct) {
        this.lastFourAcct = lastFourAcct;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getFailedDate() {
        return failedDate;
    }

    public void setFailedDate(String failedDate) {
        this.failedDate = failedDate;
    }

    public String getFailedReason() {
        return failedReason;
    }

    public void setFailedReason(String failedReason) {
        this.failedReason = failedReason;
    }

    public String getPendingTransId() {
        return pendingTransId;
    }

    public void setPendingTransId(String pendingTransId) {
        this.pendingTransId = pendingTransId;
    }

    public String getCancelled() {
        return cancelled;
    }

    public void setCancelled(String cancelled) {
        this.cancelled = cancelled;
    }

    public String getCancelledDate() {
        return cancelledDate;
    }

    public void setCancelledDate(String cancelledDate) {
        this.cancelledDate = cancelledDate;
    }

    public String getCancelledNotes() {
        return cancelledNotes;
    }

    public void setCancelledNotes(String cancelledNotes) {
        this.cancelledNotes = cancelledNotes;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }
}
