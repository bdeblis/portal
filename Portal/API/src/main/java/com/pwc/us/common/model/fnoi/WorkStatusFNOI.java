package com.pwc.us.common.model.fnoi;

import javax.xml.datatype.XMLGregorianCalendar;

/**
 *
 * @author Robert Snelling <robert.snelling@us.pwc.com>
 */

public class WorkStatusFNOI {

    protected String status;
    protected XMLGregorianCalendar statusDate;

    /**
     * Gets the value of the status property.
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     */
    public void setStatus(String value) {
        this.status = ((String ) value);
    }

    /**
     * Gets the value of the statusDate property.
     */
    public XMLGregorianCalendar getStatusDate() {
        return statusDate;
    }

    /**
     * Sets the value of the statusDate property.
     */
    public void setStatusDate(XMLGregorianCalendar value) {
        this.statusDate = ((XMLGregorianCalendar ) value);
    }

}
