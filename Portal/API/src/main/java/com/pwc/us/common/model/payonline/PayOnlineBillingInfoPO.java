package com.pwc.us.common.model.payonline;

import java.math.BigDecimal;
import java.util.Date;


/**
 *
 * @author Roger
 */
public class PayOnlineBillingInfoPO {

    protected String accountNumber;
    protected double currentAccountBalance;
    protected Date currentAccountBalanceTimestamp;
    protected double currentBalanceBilled;
    protected Date dueDate;
    protected double pastDueBalanceBilled;
    protected double totalAmountBilled;
    protected BigDecimal unpaidAmount;

    public BigDecimal getUnpaidAmount() {
        return unpaidAmount;
    }

    public void setUnpaidAmount(BigDecimal unpaidAmount) {
        this.unpaidAmount = unpaidAmount;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    
    
    /**
     * Gets the value of the currentAccountBalance property.
     * 
     */
    public double getCurrentAccountBalance() {
        return currentAccountBalance;
    }

    /**
     * Sets the value of the currentAccountBalance property.
     * 
     */
    public void setCurrentAccountBalance(double value) {
        this.currentAccountBalance = value;
    }

    /**
     * Gets the value of the currentAccountBalanceTimestamp property.
     * 
     * @return
     *     possible object is
     *     {@link Date }
     *     
     */
    public Date getCurrentAccountBalanceTimestamp() {
        return currentAccountBalanceTimestamp;
    }

    /**
     * Sets the value of the currentAccountBalanceTimestamp property.
     * 
     * @param value
     *     allowed object is
     *     {@link Date }
     *     
     */
    public void setCurrentAccountBalanceTimestamp(Date value) {
        this.currentAccountBalanceTimestamp = value;
    }

    /**
     * Gets the value of the currentBalanceBilled property.
     * 
     */
    public double getCurrentBalanceBilled() {
        return currentBalanceBilled;
    }

    /**
     * Sets the value of the currentBalanceBilled property.
     * 
     */
    public void setCurrentBalanceBilled(double value) {
        this.currentBalanceBilled = value;
    }

    /**
     * Gets the value of the dueDate property.
     * 
     * @return
     *     possible object is
     *     {@link Date }
     *     
     */
    public Date getDueDate() {
        return dueDate;
    }

    /**
     * Sets the value of the dueDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link Date }
     *     
     */
    public void setDueDate(Date value) {
        this.dueDate = value;
    }

    /**
     * Gets the value of the pastDueBalanceBilled property.
     * 
     */
    public double getPastDueBalanceBilled() {
        return pastDueBalanceBilled;
    }

    /**
     * Sets the value of the pastDueBalanceBilled property.
     * 
     */
    public void setPastDueBalanceBilled(double value) {
        this.pastDueBalanceBilled = value;
    }

    /**
     * Gets the value of the totalAmountBilled property.
     * 
     */
    public double getTotalAmountBilled() {
        return totalAmountBilled;
    }

    /**
     * Sets the value of the totalAmountBilled property.
     * 
     */
    public void setTotalAmountBilled(double value) {
        this.totalAmountBilled = value;
    }    
}
