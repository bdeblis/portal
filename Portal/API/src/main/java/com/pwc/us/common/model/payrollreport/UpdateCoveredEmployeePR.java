
package com.pwc.us.common.model.payrollreport;

public class UpdateCoveredEmployeePR {

    protected String auditInformationPublicId;
    protected Long coveredEmployeeFixedId;
    protected Integer amount;

    
    public String getAuditInformationPublicId() {
        return auditInformationPublicId;
    }

    
    public void setAuditInformationPublicId(String value) {
        this.auditInformationPublicId = value;
    }

    
    public Long getCoveredEmployeeFixedId() {
        return coveredEmployeeFixedId;
    }

    public void setCoveredEmployeeFixedId(Long value) {
        this.coveredEmployeeFixedId = value;
    }

    
    public Integer getAmount() {
        return amount;
    }

    
    public void setAmount(Integer value) {
        this.amount = value;
    }

}
