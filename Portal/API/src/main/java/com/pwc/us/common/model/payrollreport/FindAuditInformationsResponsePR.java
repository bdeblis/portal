
package com.pwc.us.common.model.payrollreport;

import java.util.ArrayList;
import java.util.List;



public class FindAuditInformationsResponsePR {

    protected FindAuditInformationsResponsePR.Return _return;
    
    public FindAuditInformationsResponsePR.Return getReturn() {
        return _return;
    }
    
    public void setReturn(FindAuditInformationsResponsePR.Return value) {
        this._return = value;
    }


    public static class Return {

        protected List<FindAuditInformationsResponsePR.Return.Entry> entry;

        public List<FindAuditInformationsResponsePR.Return.Entry> getEntry() {
            if (entry == null) {
                entry = new ArrayList<FindAuditInformationsResponsePR.Return.Entry>();
            }
            return this.entry;
        }

        public static class Entry {

            protected CSAuditInformationPR csAuditInformation;

            public CSAuditInformationPR getCSAuditInformation() {
                return csAuditInformation;
            }

            
            public void setCSAuditInformation(CSAuditInformationPR value) {
                this.csAuditInformation = value;
            }

        }

    }

}
