package com.pwc.us.common.model;


/**
 *
 * Robert Snelling <robert.snelling@us.pwc.com>
 */

public class CertificateOfInsuranceRequest {

    private String policyNumber;
    private String policyHolderName;

    public String getPolicyHolderName() {
        return policyHolderName;
    }

    public void setPolicyHolderName(String policyHolderName) {
        this.policyHolderName = policyHolderName;
    }

    public String getPolicyNumber() {
        return policyNumber;
    }

    public void setPolicyNumber(String policyNumber) {
        this.policyNumber = policyNumber;
    }
    
    private String client;
    private Boolean deliverByEmail;
    private Boolean deliverByFax;
    private Boolean deliverByMail;
    private String emailAddress1;
    private String emailAddress2;
    private String emailAddress3;
    private String emailRecipientName1;
    private String emailRecipientName2;
    private String emailRecipientName3;
    private String faxNumber1;
    private String faxNumber2;
    private String faxNumber3;
    private String faxRecipientName1;
    private String faxRecipientName2;
    private String faxRecipientName3;
    private HolderAddress holderAddress;
    private String holderName;
    private Boolean isAlternateEmployer;
    private Boolean isWaiverOfSubrogation;
    private String projectName;
    private String requesterName;
    private String requesterPhone;
    private String specificInstructions;
    private String termDesc;
    private Integer termNumber;
    private PolicyTerm policyTerm;

    private Boolean withThirtyDayNotice;
    private String workPerformed;

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public Boolean getDeliverByEmail() {
        return deliverByEmail;
    }

    public void setDeliverByEmail(Boolean deliverByEmail) {
        this.deliverByEmail = deliverByEmail;
    }

    public Boolean getDeliverByFax() {
        return deliverByFax;
    }

    public void setDeliverByFax(Boolean deliverByFax) {
        this.deliverByFax = deliverByFax;
    }

    public Boolean getDeliverByMail() {
        return deliverByMail;
    }

    public void setDeliverByMail(Boolean deliverByMail) {
        this.deliverByMail = deliverByMail;
    }

    public String getEmailAddress1() {
        return emailAddress1;
    }

    public void setEmailAddress1(String emailAddress1) {
        this.emailAddress1 = emailAddress1;
    }

    public String getEmailAddress2() {
        return emailAddress2;
    }

    public void setEmailAddress2(String emailAddress2) {
        this.emailAddress2 = emailAddress2;
    }

    public String getEmailAddress3() {
        return emailAddress3;
    }

    public void setEmailAddress3(String emailAddress3) {
        this.emailAddress3 = emailAddress3;
    }

    public String getEmailRecipientName1() {
        return emailRecipientName1;
    }

    public void setEmailRecipientName1(String emailRecipientName1) {
        this.emailRecipientName1 = emailRecipientName1;
    }

    public String getEmailRecipientName2() {
        return emailRecipientName2;
    }

    public void setEmailRecipientName2(String emailRecipientName2) {
        this.emailRecipientName2 = emailRecipientName2;
    }

    public String getEmailRecipientName3() {
        return emailRecipientName3;
    }

    public void setEmailRecipientName3(String emailRecipientName3) {
        this.emailRecipientName3 = emailRecipientName3;
    }

    public String getFaxNumber1() {
        return faxNumber1;
    }

    public void setFaxNumber1(String faxNumber1) {
        this.faxNumber1 = faxNumber1;
    }

    public String getFaxNumber2() {
        return faxNumber2;
    }

    public void setFaxNumber2(String faxNumber2) {
        this.faxNumber2 = faxNumber2;
    }

    public String getFaxNumber3() {
        return faxNumber3;
    }

    public void setFaxNumber3(String faxNumber3) {
        this.faxNumber3 = faxNumber3;
    }

    public String getFaxRecipientName1() {
        return faxRecipientName1;
    }

    public void setFaxRecipientName1(String faxRecipientName1) {
        this.faxRecipientName1 = faxRecipientName1;
    }

    public String getFaxRecipientName2() {
        return faxRecipientName2;
    }

    public void setFaxRecipientName2(String faxRecipientName2) {
        this.faxRecipientName2 = faxRecipientName2;
    }

    public String getFaxRecipientName3() {
        return faxRecipientName3;
    }

    public void setFaxRecipientName3(String faxRecipientName3) {
        this.faxRecipientName3 = faxRecipientName3;
    }

    public HolderAddress getHolderAddress() {
        return holderAddress;
    }

    public void setHolderAddress(HolderAddress holderAddress) {
        this.holderAddress = holderAddress;
    }

    public String getHolderName() {
        return holderName;
    }

    public void setHolderName(String holderName) {
        this.holderName = holderName;
    }

    public Boolean getIsAlternateEmployer() {
        return isAlternateEmployer;
    }

    public void setIsAlternateEmployer(Boolean isAlternateEmployer) {
        this.isAlternateEmployer = isAlternateEmployer;
    }

    public Boolean getIsWaiverOfSubrogation() {
        return isWaiverOfSubrogation;
    }

    public void setIsWaiverOfSubrogation(Boolean isWaiverOfSubrogation) {
        this.isWaiverOfSubrogation = isWaiverOfSubrogation;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getRequesterName() {
        return requesterName;
    }

    public void setRequesterName(String requesterName) {
        this.requesterName = requesterName;
    }
    
    public String getRequesterPhone() {
        return requesterPhone;
    }

    public void setRequesterPhone(String requesterPhone) {
        this.requesterPhone = requesterPhone;
    }

    public String getSpecificInstructions() {
        return specificInstructions;
    }

    public void setSpecificInstructions(String specificInstructions) {
        this.specificInstructions = specificInstructions;
    }

    public String getTermDesc() {
        return termDesc;
    }

    public void setTermDesc(String termDesc) {
        this.termDesc = termDesc;
    }

    public Integer getTermNumber() {
        return termNumber;
    }

    public void setTermNumber(Integer termNumber) {
        this.termNumber = termNumber;
    }
    
    public PolicyTerm getPolicyTerm() {
        return policyTerm;
    }

    public void setPolicyTerm(PolicyTerm policyTerm) {
        this.policyTerm = policyTerm;
    }

    public Boolean getWithThirtyDayNotice() {
        return withThirtyDayNotice;
    }

    public void setWithThirtyDayNotice(Boolean withThirtyDayNotice) {
        this.withThirtyDayNotice = withThirtyDayNotice;
    }

    public String getWorkPerformed() {
        return workPerformed;
    }

    public void setWorkPerformed(String workPerformed) {
        this.workPerformed = workPerformed;
    }

    public static class HolderAddress {

        protected String addressLine1;
        protected String addressLine2;
        protected String city;
        protected String postalCode;
        protected String state;

        public String getAddressLine1() {
            return addressLine1;
        }

        public void setAddressLine1(String addressLine1) {
            this.addressLine1 = addressLine1;
        }

        public String getAddressLine2() {
            return addressLine2;
        }

        public void setAddressLine2(String addressLine2) {
            this.addressLine2 = addressLine2;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getPostalCode() {
            return postalCode;
        }

        public void setPostalCode(String postalCode) {
            this.postalCode = postalCode;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }
        
    }
    
    @Override
    public boolean equals(Object obj) {
        try { return policyNumber.equals(((CertificateOfInsuranceRequest) obj).getPolicyNumber()); }
        catch (Exception exc) { return false; }
    }
    @Override
    public int hashCode() {
        return 31 + ((policyNumber == null) ? 0 : policyNumber.hashCode());
    }
    @Override
    public String toString() {
        return String.format("%s %s", policyNumber, client);
    }

}
