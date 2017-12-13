package com.pwc.us.common.model.fnoi;

/**
 *
 * @author Robert Snelling <robert.snelling@us.pwc.com>
 */

public class ClaimContactRoleFNOI {

    protected String role;
    
    public ClaimContactRoleFNOI () {
    }
    
    public ClaimContactRoleFNOI (String r) {
        this.role = r;
    }

    /**
     * Gets the value of the role property.
     */
    public String getRole() {
        return role;
    }

    /**
     * Sets the value of the role property.
     */
    public void setRole(String value) {
        this.role = ((String ) value);
    }

}
