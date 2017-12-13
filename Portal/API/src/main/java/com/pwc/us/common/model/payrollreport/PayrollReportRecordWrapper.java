package com.pwc.us.common.model.payrollreport;

import java.io.Serializable;
import java.text.DecimalFormat;

/**
 *
 * Robert Snelling <robert.snelling@us.pwc.com>
 */
public class PayrollReportRecordWrapper  implements Comparable<PayrollReportRecordWrapper>, Serializable {


    protected String location;
    protected String classCode;
    protected String classCodeDescInd="";
    protected String classCodeShortDesc;
    protected Long fixedId;
    protected String basisType;
    protected Integer auditedAmount;
    protected Boolean primaryLoc;
    protected String displayName;
    protected boolean ownerOfficer;
    protected Boolean diseaseCode = false;
    

    public Boolean isDiseaseCode() {
        return diseaseCode;
    }

    public void setDiseaseCode(Boolean diseaseCode) {
        this.diseaseCode = diseaseCode;
    }

    public Boolean isPrimaryLoc() {
        return primaryLoc;
    }

    public void setPrimaryLoc(Boolean primaryLoc) {
        this.primaryLoc = primaryLoc;
    }

    public void setAuditAmountIsZero(boolean auditAmountIsZero) {
        this.auditAmountIsZero = auditAmountIsZero;
    }

    private final DecimalFormat formatter = new DecimalFormat("#,###");
    
    // This value is a necessary work-around for a problem in 
    // javascript distinguishing between this:
    // <input name="payrollReportTable[0].auditedAmount" value="0" class="payAmt numberFmt" type="text">
    // and this:
    // <input name="payrollReportTable[0].auditedAmount" class="payAmt numberFmt" type="text">
    // Tested under both jQuery and plain javascript, the value of both of these is "", even though the 
    // value of the first is clearly defined as "0".
    protected boolean auditAmountIsZero;

    public PayrollReportRecordWrapper() {
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getClassCode() {
        return classCode;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }

    public String getClassCodeDescInd() {
        return classCodeDescInd;
    }

    public void setClassCodeDescInd(String classCodeDescInd) {
        this.classCodeDescInd = classCodeDescInd;
    }

    public String getClassCodeShortDesc() {
        return classCodeShortDesc;
    }

    public void setClassCodeShortDesc(String classCodeShortDesc) {
        this.classCodeShortDesc = classCodeShortDesc;
    }

    public Long getFixedId() {
        return fixedId;
    }

    public void setFixedId(Long fixedId) {
        this.fixedId = fixedId;
    }

    public String getBasisType() {
        return basisType;
    }

    public void setBasisType(String basisType) {
        this.basisType = basisType;
    }

    public Integer getAuditedAmount() {
        return auditedAmount;
    }

    public void setAuditedAmount(Integer partialAmountExt) {
        this.auditedAmount = partialAmountExt;
        if (partialAmountExt == null) {
            this.auditAmountIsZero = false;
        } else if (partialAmountExt == 0) {
            this.auditAmountIsZero = true;
        }
        
    }

    public boolean isOwnerOfficer() {
        return ownerOfficer;
    }

    public void setOwnerOfficer(boolean ownerOfficer) {
        this.ownerOfficer = ownerOfficer;
    }

    public String getPrintAuditedAmount() {
        String retval = null;
        if (this.auditedAmount != null) {
            retval = "$" + formatter.format(this.auditedAmount);
        }
        return retval;
    }
    
    public boolean isAuditInformationIsZero()
    {
        return auditAmountIsZero;
    }

    public boolean isAuditAmountIsZero() {
        return auditAmountIsZero;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + (this.location != null ? this.location.hashCode() : 0);
        hash = 59 * hash + (this.classCode != null ? this.classCode.hashCode() : 0);
        hash = 59 * hash + (this.classCodeDescInd != null ? this.classCodeDescInd.hashCode() : 0);
        hash = 59 * hash + (this.ownerOfficer ? 1 : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PayrollReportRecordWrapper other = (PayrollReportRecordWrapper) obj;
 
        if ((this.classCode == null) ? (other.classCode != null) : !this.classCode.equals(other.classCode)) {
            return false;
        }
        
        if ((this.classCodeDescInd == null) ? (other.classCodeDescInd != null) : !this.classCodeDescInd.equals(other.classCodeDescInd)) {
            return false;
        }

        return true;
    }
    
     @Override public int compareTo(PayrollReportRecordWrapper that) {
            final int BEFORE = -1;
            final int EQUAL = 0;
            final int AFTER = 1;
            
            if(this.classCode.compareTo(that.classCode) < EQUAL) 
                return BEFORE;
            else if(this.classCode.compareTo(that.classCode) > EQUAL)
                return AFTER;
            if(this.classCodeDescInd.compareTo(that.classCodeDescInd) < EQUAL) 
                return BEFORE;
            else if(this.classCodeDescInd.compareTo(that.classCodeDescInd) > EQUAL)
                return AFTER;
            else if(this.location != null && that.location != null && this.location.compareTo(that.location) < EQUAL)
                return BEFORE;
            else if(this.location != null && that.location != null && this.location.compareTo(that.location) > EQUAL)
                return AFTER;
            
            return EQUAL;
     }


}
