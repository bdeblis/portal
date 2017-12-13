package com.pwc.us.common.model.fnoi;

import com.pwc.us.common.model.AddressBO;

/**
 *
 * @author Robert Snelling <robert.snelling@us.pwc.com>
 */

public class PolicyLocationFNOI {

    protected AddressBO address;
    protected String locationNumber;

    /**
     * Gets the value of the address property.
     */
    public AddressBO getAddress() {
        return address;
    }

    /**
     * Sets the value of the address property.
     */
    public void setAddress(AddressBO value) {
        this.address = ((AddressBO ) value);
    }

    /**
     * Gets the value of the locationNumber property.
     */
    public String getLocationNumber() {
        return locationNumber;
    }

    /**
     * Sets the value of the locationNumber property.
     */
    public void setLocationNumber(String value) {
        this.locationNumber = ((String ) value);
    }

}
