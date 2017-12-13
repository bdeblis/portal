package com.pwc.us.common.model.quoterequest;

import com.pwc.us.common.model.ContactBO;

/**
 * A specialization of the ContactBO to allow for fields that are specific to
 * Owner/Officer fields in the Quote Request form.
 * @author Roger
 */
public class OwnerOfficerBO extends ContactBO {
    
    protected boolean activeInBusiness;
    protected boolean coverageDesired;

    public boolean isActiveInBusiness() {
        return activeInBusiness;
    }

    public void setActiveInBusiness(boolean activeInBusiness) {
        this.activeInBusiness = activeInBusiness;
    }

    public boolean isCoverageDesired() {
        return coverageDesired;
    }

    public void setCoverageDesired(boolean coverageDesired) {
        this.coverageDesired = coverageDesired;
    }
    
    
    
}
