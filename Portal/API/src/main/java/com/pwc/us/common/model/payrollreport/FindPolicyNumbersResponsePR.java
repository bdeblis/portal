
package com.pwc.us.common.model.payrollreport;

import java.util.ArrayList;
import java.util.List;


public class FindPolicyNumbersResponsePR {

    protected FindPolicyNumbersResponsePR.Return _return;
    
    public FindPolicyNumbersResponsePR.Return getReturn() {
        return _return;
    }

    
    public void setReturn(FindPolicyNumbersResponsePR.Return value) {
        this._return = value;
    }


    public static class Return {

        protected List<String> entry;
        
        public List<String> getEntry() {
            if (entry == null) {
                entry = new ArrayList<String>();
            }
            return this.entry;
        }

    }

}
