package com.pwc.us.common.model;


/**
 *
 * @author Robert Snelling <robert.snelling@us.pwc.com>
 */

public class AddressBO {

    protected String addressLine1;
    protected String addressLine2;
    protected String city;
    protected String county;
    protected String postalCode;
    protected String state;
    protected String description;

    /**
     * Gets the value of the addressLine1 property.
     */
    public String getAddressLine1() {
        return addressLine1;
    }

    /**
     * Sets the value of the addressLine1 property.
     */
    public void setAddressLine1(String value) {
        this.addressLine1 = ((String ) value);
    }

    /**
     * Gets the value of the addressLine2 property.
     */
    public String getAddressLine2() {
        return addressLine2;
    }

    /**
     * Sets the value of the addressLine2 property.
     */
    public void setAddressLine2(String value) {
        this.addressLine2 = ((String ) value);
    }

    /**
     * Gets the value of the city property.
     */
    public String getCity() {
        return city;
    }

    /**
     * Sets the value of the city property.
     */
    public void setCity(String value) {
        this.city = ((String ) value);
    }

    /**
     * Gets the value of the county property.
     */
    public String getCounty() {
        return county;
    }

    /**
     * Sets the value of the county property.
     */
    public void setCounty(String value) {
        this.county = ((String ) value);
    }

    /**
     * Gets the value of the postalCode property.
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * Sets the value of the postalCode property.
     */
    public void setPostalCode(String value) {
        this.postalCode = ((String ) value);
    }

    /**
     * Gets the value of the state property.
     */
    public String getState() {
        return state;
    }

    /**
     * Sets the value of the state property.
     */
    public void setState(String value) {
        this.state = ((String ) value);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    
}
