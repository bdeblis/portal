package com.pwc.us.common.model.payrollreport;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FindPolicyInformationDates {

    protected FindPolicyInformationDates.Return _return;

    public FindPolicyInformationDates.Return getReturn() {
        return _return;
    }

    public void setReturn(FindPolicyInformationDates.Return value) {
        this._return = value;
    }

    public static class Return {

        protected List<FindPolicyInformationDates.Return.Entry> entry;

        public List<FindPolicyInformationDates.Return.Entry> getEntry() {
            if (entry == null) {
                entry = new ArrayList<FindPolicyInformationDates.Return.Entry>();
            }
            return this.entry;
        }

        public static class Entry {

            protected Date date;

            public Date getDate() {
                return date;
            }

            public void setDate(Date value) {
                this.date = value;
            }

        }

    }

}
