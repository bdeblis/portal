
package com.pwc.us.common.model.payrollreport;


public class FindAuditInformationDetailsResponsePR {

    protected FindAuditInformationDetailsResponsePR.Return _return;

    public FindAuditInformationDetailsResponsePR.Return getReturn() {
        return _return;
    }

    public void setReturn(FindAuditInformationDetailsResponsePR.Return value) {
        this._return = value;
    }


    public static class Return {

        protected CSAuditInformationDetailsPR csAuditInformationDetails;

        public CSAuditInformationDetailsPR getCSAuditInformationDetails() {
            return csAuditInformationDetails;
        }

        public void setCSAuditInformationDetails(CSAuditInformationDetailsPR value) {
            this.csAuditInformationDetails = value;
        }

    }

}
