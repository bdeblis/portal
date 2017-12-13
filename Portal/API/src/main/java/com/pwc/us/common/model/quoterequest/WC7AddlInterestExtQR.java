package com.pwc.us.common.model.quoterequest;

/**
 *
 * @author Roger Turnau - <roger.turnau@us.pwc.com>
 */
public class WC7AddlInterestExtQR {
    
    private String additionalInterestType;
    private PolicyContactRoleQR policyAddlInterest;
    private PolicyContactRoleQR.EntityWC7PolicyOwnerOfficer policyAddlInterestEntityOfficer;
    private String relationship;    

    public String getAdditionalInterestType() {
        return additionalInterestType;
    }

    public void setAdditionalInterestType(String additionalInterestType) {
        this.additionalInterestType = additionalInterestType;
    }

    public PolicyContactRoleQR getPolicyAddlInterest() {
        return policyAddlInterest;
    }

    public void setPolicyAddlInterest(PolicyContactRoleQR policyAddlInterest) {
        this.policyAddlInterest = policyAddlInterest;
    }
    

    public PolicyContactRoleQR.EntityWC7PolicyOwnerOfficer getPolicyAddlInterestEntityOfficer() {
        return policyAddlInterestEntityOfficer;
    }

    public void setPolicyAddlInterestEntityOfficer(PolicyContactRoleQR.EntityWC7PolicyOwnerOfficer policyAddlInterestEntityOfficer) {
        this.policyAddlInterestEntityOfficer = policyAddlInterestEntityOfficer;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }
}
