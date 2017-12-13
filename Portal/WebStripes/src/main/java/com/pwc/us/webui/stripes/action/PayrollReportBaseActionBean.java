package com.pwc.us.webui.stripes.action;

import com.pwc.us.common.model.payrollreport.PayrollReportRecordWrapper;
import com.pwc.us.common.model.payrollreport.ReportPeriodWrapper;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 *
 * Robert Snelling <robert.snelling@us.pwc.com>
 */
public class PayrollReportBaseActionBean extends BaseActionBean {
    
    
    protected String prPolicyNumber;
    protected ReportPeriodWrapper policyPeriod;
    protected String prPublicID;
    protected List<PayrollReportRecordWrapper> payrollReportTable = new ArrayList<PayrollReportRecordWrapper>();
    protected List<PayrollReportRecordWrapper> clonedPayrollReportTable = new ArrayList<PayrollReportRecordWrapper>();
    protected List<PayrollReportRecordWrapper> ownerOfficersTable = new ArrayList<PayrollReportRecordWrapper>();
    protected List<PayrollReportRecordWrapper> clonedOwnerOfficersTable = new ArrayList<PayrollReportRecordWrapper>();
    protected List<PayrollReportRecordWrapper> diseaseCodeTable = new ArrayList<PayrollReportRecordWrapper>();
 //   protected List<PayrollReportRecordWrapper> clonedDiseaseCodeTable = new ArrayList<PayrollReportRecordWrapper>();
    protected HashMap <String,Integer> totalMap = new HashMap<String,Integer>();
    protected double premium;
    protected String statusMsg;
    protected Date today;
    DecimalFormat formatter = new DecimalFormat("#,###");

    public String getStatusMsg() {
        return statusMsg;
    }

    public void setStatusMsg(String statusMsg) {
        this.statusMsg = statusMsg;
    }

    public List<PayrollReportRecordWrapper> getDiseaseCodeTable() {
        return diseaseCodeTable;
    }

    public void setDiseaseCodeTable(List<PayrollReportRecordWrapper> diseaseCodeTable) {
        this.diseaseCodeTable = diseaseCodeTable;
    }

    public List<PayrollReportRecordWrapper> getOwnerOfficersTable() {
        return ownerOfficersTable;
    }

    public void setOwnerOfficersTable(List<PayrollReportRecordWrapper> ownerOfficersTable) {
        this.ownerOfficersTable = ownerOfficersTable;
    }

    public double getPremium() {
        return premium;
    }

    public void setPremium(double premium) {
        this.premium = premium;
    }
    
    public List<PayrollReportRecordWrapper> getPayrollReportTable() {
        return payrollReportTable;
    }

    public void setPayrollReportTable(ArrayList<PayrollReportRecordWrapper> payrollReportTable) {
        this.payrollReportTable = payrollReportTable;
    }
    
    public String getPrPolicyNumber() {
        return prPolicyNumber;
    }

    public void setPrPolicyNumber(String prPolicyNumber) {
        this.prPolicyNumber = prPolicyNumber;
    }
    
    public String getPrPublicID() {
        return prPublicID;
    }

    public void setPrPublicID(String prPublicID) {
        this.prPublicID = prPublicID;
    }
    
    public ReportPeriodWrapper getPolicyPeriod() {
        return policyPeriod;
    }

    public void setPolicyPeriod(ReportPeriodWrapper policyPeriod) {
        this.policyPeriod = policyPeriod;
    }
    
    public String getToday() {
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
        return sdf.format(new Date());
    }
    
    public String getPrintPremium() {
        String str = "$" + formatter.format(this.premium);
        return str;
    }

}
