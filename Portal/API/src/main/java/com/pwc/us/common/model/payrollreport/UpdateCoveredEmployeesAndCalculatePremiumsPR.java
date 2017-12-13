package com.pwc.us.common.model.payrollreport;

import java.util.ArrayList;
import java.util.List;

public class UpdateCoveredEmployeesAndCalculatePremiumsPR {

    protected String auditInformationPublicId;
    protected UpdateCoveredEmployeesAndCalculatePremiumsPR.CoveredEmployees coveredEmployees;
    protected String description;
    
    public String getAuditInformationPublicId() {
        return auditInformationPublicId;
    }
    
    public void setAuditInformationPublicId(String value) {
        this.auditInformationPublicId = value;
    }
    
    public UpdateCoveredEmployeesAndCalculatePremiumsPR.CoveredEmployees getCoveredEmployees() {
        return coveredEmployees;
    }

    public void setCoveredEmployees(UpdateCoveredEmployeesAndCalculatePremiumsPR.CoveredEmployees value) {
        this.coveredEmployees = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String value) {
        this.description = value;
    }    
    
    public static class CoveredEmployees {
        
        protected List<UpdateCoveredEmployeesAndCalculatePremiumsPR.CoveredEmployees.Entry> entry;

        public List<UpdateCoveredEmployeesAndCalculatePremiumsPR.CoveredEmployees.Entry> getEntry() {
            if (entry == null) {
                entry = new ArrayList<UpdateCoveredEmployeesAndCalculatePremiumsPR.CoveredEmployees.Entry>();
            }
            return this.entry;
        }

        public static class Entry {
            
            protected CSCoveredEmployeePR csCoveredEmployee;
            
            public CSCoveredEmployeePR getCSCoveredEmployee() {
                return csCoveredEmployee;
            }
            
            public void setCSCoveredEmployee(CSCoveredEmployeePR value) {
                this.csCoveredEmployee = value;
            }
        }
    }
}
