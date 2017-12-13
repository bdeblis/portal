
package com.pwc.us.common.model.payrollreport;

import java.util.ArrayList;
import java.util.List;


public class FindCoveredEmployeesResponsePR {

    protected FindCoveredEmployeesResponsePR.Return _return;

    
    public FindCoveredEmployeesResponsePR.Return getReturn() {
        return _return;
    }

    
    public void setReturn(FindCoveredEmployeesResponsePR.Return value) {
        this._return = value;
    }


    
    public static class Return {

        protected List<FindCoveredEmployeesResponsePR.Return.Entry> entry;

        public List<FindCoveredEmployeesResponsePR.Return.Entry> getEntry() {
            if (entry == null) {
                entry = new ArrayList<FindCoveredEmployeesResponsePR.Return.Entry>();
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
