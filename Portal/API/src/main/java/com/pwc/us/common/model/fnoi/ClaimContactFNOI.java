package com.pwc.us.common.model.fnoi;

import com.pwc.us.common.model.ContactBO;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Robert Snelling <robert.snelling@us.pwc.com>
 */

public class ClaimContactFNOI {

    protected ContactBO contact;
    protected ClaimContactFNOI.Roles roles;

    /**
     * Gets the value of the contact property.
     */
    public ContactBO getContactFNOI() {
        return contact;
    }

    /**
     * Sets the value of the contact property.
     */
    public void setContactFNOI(ContactBO value) {
        this.contact = ((ContactBO ) value);
    }

    /**
     * Gets the value of the roles property.
     */
    public ClaimContactFNOI.Roles getRoles() {
        return roles;
    }

    /**
     * Sets the value of the roles property.
     */
    public void setRoles(ClaimContactFNOI.Roles value) {
        this.roles = ((ClaimContactFNOI.Roles ) value);
    }


    public static class Roles {

        protected List<ClaimContactRoleFNOI> entry;

        public List<ClaimContactRoleFNOI> getEntry() {
            if (entry == null) {
                entry = new ArrayList<ClaimContactRoleFNOI>();
            }
            return this.entry;
        }

    }

}
