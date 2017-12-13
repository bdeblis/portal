
package com.pwc.us.common.model.payrollreport;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class FindPolicyStartAndEndDatesResponsePR {

    protected FindPolicyStartAndEndDatesResponsePR.Return _return;
    
    public FindPolicyStartAndEndDatesResponsePR.Return getReturn() {
        return _return;
    }

    
    public void setReturn(FindPolicyStartAndEndDatesResponsePR.Return value) {
        this._return = value;
    }


    public static class Return {

        protected List<Date> entry;
        
        public List<Date> getEntry() {
            if (entry == null) {
                entry = new ArrayList<Date>();
            }
            return this.entry;
        }

    }

}
