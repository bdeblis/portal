
package com.pwc.us.common.model.payrollreport;

import java.util.ArrayList;
import java.util.List;



public class UpdateCoveredEmployeesPR {

    protected String auditInformationPublicId;
    protected UpdateCoveredEmployeesPR.CoveredEmployees coveredEmployees;

    
    public String getAuditInformationPublicId() {
        return auditInformationPublicId;
    }

    
    public void setAuditInformationPublicId(String value) {
        this.auditInformationPublicId = value;
    }

    
    public UpdateCoveredEmployeesPR.CoveredEmployees getCoveredEmployees() {
        return coveredEmployees;
    }

    
    public void setCoveredEmployees(UpdateCoveredEmployeesPR.CoveredEmployees value) {
        this.coveredEmployees = value;
    }


    public static class CoveredEmployees {

        protected List<UpdateCoveredEmployeesPR.CoveredEmployees.Entry> entry;

        
        public List<UpdateCoveredEmployeesPR.CoveredEmployees.Entry> getEntry() {
            if (entry == null) {
                entry = new ArrayList<UpdateCoveredEmployeesPR.CoveredEmployees.Entry>();
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
