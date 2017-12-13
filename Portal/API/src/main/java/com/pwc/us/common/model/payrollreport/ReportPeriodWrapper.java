package com.pwc.us.common.model.payrollreport;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.Serializable;


/**
 *
 * Robert Snelling <robert.snelling@us.pwc.com>
 */

public class ReportPeriodWrapper implements Serializable, Comparable <ReportPeriodWrapper>{

    protected String period;
    protected Date endDate;
    protected Date startDate;
    protected String publicID;
    protected String policyTerm;
    protected Boolean editable;
    protected Boolean complete;
    protected Boolean finalAudit;
    protected Boolean open;
    protected Boolean premiumReport;
    protected Boolean scheduled;
    protected Double premium;
    protected String policyStatus;
    
    SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");

    public ReportPeriodWrapper() {
    }

    public String getPeriod() {
        return period;
    }
    
    public String getPeriodStartDisplay()
    {
        if(period != null)
        {
            int i = period.indexOf('-', 1 + period.indexOf('-', 1 + period.indexOf('-')));
            return period.substring(0, i).trim();
        }
        return "";
    }
    
    public String getPeriodEndDisplay()
    {
        if(period != null)
        {
            int i = period.indexOf('-', 1 + period.indexOf('-', 1 + period.indexOf('-')));
            return period.substring(i+1).trim();
        }
        return "";
    }
    public void setPeriod(String period) {
        this.period = period;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date value) {
        this.startDate = value;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date value) {
        this.endDate = value;
    }        
        
    public String getPublicID() {
    return publicID;
    }

    public void setPublicID(String value) {
    this.publicID = value;
    }

    public String getPolicyTerm() {
        return policyTerm;
    }

    public void setPolicyTerm(String value) {
        this.policyTerm = value;
    }
    
    public Boolean getEditable() {
        return editable;
    }

    public void setEditable(Boolean editable) {
        this.editable = editable;
    }

    public Boolean getComplete() {
        return complete;
    }

    public void setComplete(Boolean complete) {
        this.complete = complete;
    }

    public Boolean getFinalAudit() {
        return finalAudit;
    }

    public void setFinalAudit(Boolean finalAudit) {
        this.finalAudit = finalAudit;
    }

    public Boolean getOpen() {
        return open;
    }

    public void setOpen(Boolean open) {
        this.open = open;
    }

    public Boolean getPremiumReport() {
        return premiumReport;
    }

    public void setPremiumReport(Boolean premiumReport) {
        this.premiumReport = premiumReport;
    }

    public Boolean getScheduled() {
        return scheduled;
    }

    public void setScheduled(Boolean scheduled) {
        this.scheduled = scheduled;
    }

    public void formatPeriod () {
        setPeriod(String.format("%s - %s", sdf.format(startDate), sdf.format(endDate)));
    }

    public Double getPremium() {
        return premium;
    }

    public void setPremium(Double premium) {
        this.premium = premium;
    }
    
    public String getPolicyStatus() {
        return policyStatus;
    }

    public void setPolicyStatus(String policyStatus) {
        this.policyStatus = policyStatus;
    }    

    @Override
    public int compareTo(ReportPeriodWrapper o) {
        return this.startDate.compareTo(o.startDate);
    }

    @Override
    public String toString() {
        return "ReportPeriodWrapper{" + "period=" + period + ", endDate=" + endDate + ", startDate=" + startDate + ", publicID=" + publicID + ", policyTerm=" + policyTerm + ", editable=" + editable + ", complete=" + complete + ", finalAudit=" + finalAudit + ", open=" + open + ", premiumReport=" + premiumReport + ", scheduled=" + scheduled + ", premium=" + premium + ", policyStatus=" + policyStatus + ", sdf=" + sdf + '}';
    }

}
