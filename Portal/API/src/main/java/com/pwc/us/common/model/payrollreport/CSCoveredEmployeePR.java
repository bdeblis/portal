package com.pwc.us.common.model.payrollreport;


public class CSCoveredEmployeePR {
    
    protected CSCoveredEmployeePR.CoveredEmployee coveredEmployee;
    
    public CSCoveredEmployeePR.CoveredEmployee getCoveredEmployee() {
        return coveredEmployee;
    }
    
    public void setCoveredEmployee(CSCoveredEmployeePR.CoveredEmployee value) {
        this.coveredEmployee = ((CSCoveredEmployeePR.CoveredEmployee ) value);
    }

    public static class CoveredEmployee {

        protected CSCoveredEmployeePR.CoveredEmployee.FixedId fixedId;
        protected Integer auditedAmount;
        
        public Integer getAuditedAmount() {
            return auditedAmount;
        }
        
        public void setAuditedAmount(Integer value) {
            this.auditedAmount = ((Integer ) value);
        }
        
        public CSCoveredEmployeePR.CoveredEmployee.FixedId getFixedId() {
            return fixedId;
        }

        public void setFixedId(CSCoveredEmployeePR.CoveredEmployee.FixedId value) {
            this.fixedId = ((CSCoveredEmployeePR.CoveredEmployee.FixedId ) value);
        }        
        public static class FixedId {

            protected Long objValue;

            public Long getObjValue() {
                return objValue;
            }

            public void setObjValue(Long value) {
                this.objValue = ((Long ) value);
            }

        }        
    }
}
