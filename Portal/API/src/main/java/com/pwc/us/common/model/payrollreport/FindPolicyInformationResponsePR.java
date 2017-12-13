
package com.pwc.us.common.model.payrollreport;

public class FindPolicyInformationResponsePR {

    protected FindPolicyInformationResponsePR.Return _return;

    public FindPolicyInformationResponsePR.Return getReturn() {
        return _return;
    }
    
    public void setReturn(FindPolicyInformationResponsePR.Return value) {
        this._return = value;
    }


    
    public static class Return {

        protected CSPolicyPeriodPR csPolicyPeriod;

        public CSPolicyPeriodPR getCSPolicyPeriod() {
            return csPolicyPeriod;
        }
        
        public void setCSPolicyPeriod(CSPolicyPeriodPR value) {
            this.csPolicyPeriod = value;
        }
    }
}
