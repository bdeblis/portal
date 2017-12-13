package com.pwc.us.common.model.fnoi;

import java.util.ArrayList;
import java.util.List;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 *
 * @author Robert Snelling <robert.snelling@us.pwc.com>
 */

public class PolicyFNOI {

    protected String accountOrgTypeExt;
    protected String accountTypeOfBusinessExt;
    protected PolicyFNOI.Contacts contacts;
    protected XMLGregorianCalendar effectiveDate;
    protected XMLGregorianCalendar expirationDate;
    protected String insuredSICCode;
    protected String notes;
    protected PolicyFNOI.PolicyLocations policyLocations;
    protected String policyNumber;

    /**
     * Gets the value of the accountOrgTypeExt property.
     */
    public String getAccountOrgTypeExt() {
        return accountOrgTypeExt;
    }

    /**
     * Sets the value of the accountOrgTypeExt property.
     */
    public void setAccountOrgTypeExt(String value) {
        this.accountOrgTypeExt = ((String ) value);
    }

    /**
     * Gets the value of the accountTypeOfBusinessExt property.
     */
    public String getAccountTypeOfBusinessExt() {
        return accountTypeOfBusinessExt;
    }

    /**
     * Sets the value of the accountTypeOfBusinessExt property.
     */
    public void setAccountTypeOfBusinessExt(String value) {
        this.accountTypeOfBusinessExt = ((String ) value);
    }

    /**
     * Gets the value of the contacts property.
     */
    public PolicyFNOI.Contacts getContacts() {
        return contacts;
    }

    /**
     * Sets the value of the contacts property.
     */
    public void setContacts(PolicyFNOI.Contacts value) {
        this.contacts = ((PolicyFNOI.Contacts ) value);
    }

    /**
     * Gets the value of the effectiveDate property.
     */
    public XMLGregorianCalendar getEffectiveDate() {
        return effectiveDate;
    }

    /**
     * Sets the value of the effectiveDate property.
     */
    public void setEffectiveDate(XMLGregorianCalendar value) {
        this.effectiveDate = ((XMLGregorianCalendar ) value);
    }

    /**
     * Gets the value of the expirationDate property.
     */
    public XMLGregorianCalendar getExpirationDate() {
        return expirationDate;
    }

    /**
     * Sets the value of the expirationDate property.
     */
    public void setExpirationDate(XMLGregorianCalendar value) {
        this.expirationDate = ((XMLGregorianCalendar ) value);
    }

    /**
     * Gets the value of the insuredSICCode property.
     */
    public String getInsuredSICCode() {
        return insuredSICCode;
    }

    /**
     * Sets the value of the insuredSICCode property.
     */
    public void setInsuredSICCode(String value) {
        this.insuredSICCode = ((String ) value);
    }

    /**
     * Gets the value of the notes property.
     */
    public String getNotes() {
        return notes;
    }

    /**
     * Sets the value of the notes property.
     */
    public void setNotes(String value) {
        this.notes = ((String ) value);
    }

    /**
     * Gets the value of the policyLocations property.
     */
    public PolicyFNOI.PolicyLocations getPolicyLocations() {
        return policyLocations;
    }

    /**
     * Sets the value of the policyLocations property.
     */
    public void setPolicyLocations(PolicyFNOI.PolicyLocations value) {
        this.policyLocations = ((PolicyFNOI.PolicyLocations ) value);
    }

    /**
     * Gets the value of the policyNumber property.
     */
    public String getPolicyNumber() {
        return policyNumber;
    }

    /**
     * Sets the value of the policyNumber property.
     */
    public void setPolicyNumber(String value) {
        this.policyNumber = ((String ) value);
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="Entry" type="{http://guidewire.com/cc/gx/compsource.cc.api.fnoi.gx.contact.claimcontactmodel}ClaimContact" maxOccurs="unbounded" minOccurs="0"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    public static class Contacts {

        protected List<ClaimContactFNOI> entry;

        /**
         * Gets the value of the entry property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. This is why there is not a <CODE>set</CODE> method for the entry property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getEntry().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link ClaimContact }
         * 
         * 
         */
        public List<ClaimContactFNOI> getEntry() {
            if (entry == null) {
                entry = new ArrayList<ClaimContactFNOI>();
            }
            return this.entry;
        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="Entry" type="{http://guidewire.com/cc/gx/compsource.cc.api.fnoi.gx.policy.policylocationmodel}PolicyLocation" maxOccurs="unbounded" minOccurs="0"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    public static class PolicyLocations {

        protected List<PolicyLocationFNOI> entry;

        /**
         * Gets the value of the entry property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. This is why there is not a <CODE>set</CODE> method for the entry property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getEntry().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link PolicyLocationFNOI }
         * 
         * 
         */
        public List<PolicyLocationFNOI> getEntry() {
            if (entry == null) {
                entry = new ArrayList<PolicyLocationFNOI>();
            }
            return this.entry;
        }

    }

}
