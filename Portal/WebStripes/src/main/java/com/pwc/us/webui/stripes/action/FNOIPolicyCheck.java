/*
 * Login.java
 *
 */
package com.pwc.us.webui.stripes.action;

import com.google.inject.Inject;
import com.pwc.us.common.PayrollReport;
import com.pwc.us.common.exception.GwIntegrationException;
import com.pwc.us.common.model.User;
import com.pwc.us.common.model.payrollreport.CSPolicyPeriodPR;
import com.pwc.us.common.model.payrollreport.FindPolicyInformationResponsePR;
import com.pwc.us.common.utils.NullChecker;
import com.pwc.us.common.utils.DateTimeHelper;
import java.text.ParseException;
import java.util.logging.Level;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.DontValidate;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.validation.LocalizableError;
import net.sourceforge.stripes.validation.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 */
public class FNOIPolicyCheck extends BaseActionBean {

    private static final String FORM = "/WEB-INF/jsp/fnoiPolicyCheck.jsp";
    private static Logger logger = LoggerFactory.getLogger(FNOIPolicyCheck.class);
    private static final Logger claimsLog = LoggerFactory.getLogger("claimsLog");
    @Validate(required = true, minlength = 8, on = "submit")
    private String policyNumber;
    
    @Validate(required = true, minlength = 10, on = "submit")
    private String dateOfAccident;


    //@Inject
    //private GwRegistrationService gwService;
    @Inject
    private PayrollReport pr;
    
    @DefaultHandler
    @DontValidate
    public Resolution form() {
        Resolution ret = null;

        ret = new ForwardResolution(FORM);

        return ret;
    }

    /**
     * In the case of policyholders, the system simply forwards them on to the
     * main menu. In the case of agents, it packs the username and security
     * token into the HTTP header and redirects to PolicyCenter.
     *
     * @return
     */
    public Resolution submit() {
        Resolution ret = null;
        try {            
            if (!pr.isPolicyNumberValid(policyNumber)) {
                String info="";
                User user = getUser();
                if(user != null){
                   info = " Name=\""+user.getFirstName()+" "+user.getLastName()+"\" Phone="+user.getPhone()+" Email="+user.getEmail();
                }
                logger.info("Unable to process FNOI Request: PolicyNumber=" + policyNumber);
                claimsLog.error("ErrorMessage="+"\"Unable to process FNOI Request:"+"\" Status=Failed PolicyNumber="+ policyNumber+" Task=PolicyNumberValidation "+info);
                this.getContext().getValidationErrors().addGlobalError(new LocalizableError("FNOIRequest.Error.InvalidPolicyNumber", policyNumber));
                ret = new ForwardResolution(this.getClass(), "form");
            } else {
                String newDate = DateTimeHelper.flipDateStringFormat(dateOfAccident);
                newDate += " 00:02:00"; // add two minutes so that an accident won't occur before a policy becomes effective at 12:01 a.m.
                // The format becomes yyyy-mm-dd 00:02:00 , two minutes after midnight
                boolean validPolicy = pr.policyDateIsValid(policyNumber, newDate);
                if (!validPolicy) {
                    throw new ParseException("Date invalid",0);
                }
                
               FindPolicyInformationResponsePR.Return info = pr.findPolicyInformation(policyNumber);
               CSPolicyPeriodPR c = null;
               if (NullChecker.isNotNullOrEmpty(info)) {
                  c = info.getCSPolicyPeriod();
               }
               getContext().getRequest().getSession().setAttribute("fnoiDateOfAccident", dateOfAccident);
                getContext().getRequest().getSession().setAttribute("fnoiPolicyNumber", policyNumber);
                getContext().getRequest().getSession().setAttribute("claimsubmissionstarttime", System.currentTimeMillis());
                if(c != null){
                    if (c.getEmployerAddressLine1() != null) {
                        getContext().getRequest().getSession().setAttribute("empadd1",c.getEmployerAddressLine1());
                    }
                    if (c.getEmployerCity() != null) {
                        getContext().getRequest().getSession().setAttribute("empcity",c.getEmployerCity());
                    }
                    if (c.getEmpName() != null) {
                        getContext().getRequest().getSession().setAttribute("empname",c.getEmpName());
                    }
                    if (c.getEmpPhone() != null) {
                        getContext().getRequest().getSession().setAttribute("empphone",c.getEmpPhone());
                    }
                    if (c.getEmployerState() != null) {
                        getContext().getRequest().getSession().setAttribute("empstate", c.getEmployerState());
                    }
                    if (c.getEmployerZip() != null) {
                        getContext().getRequest().getSession().setAttribute("empzip", c.getEmployerZip());
                }
                }
                
                ret = new RedirectResolution(FNOIFormActionBean.class);
            }
        } catch (GwIntegrationException ex) {
                String info="";
                User user = getUser();
                if(user != null){
                   info = " Name=\""+user.getFirstName()+" "+user.getLastName()+"\" Phone="+user.getPhone()+" Email="+user.getEmail();
                }
                logger.info("Unable to process FNOI Request: Invalid Policy Number - " + policyNumber);
                this.getContext().getValidationErrors().addGlobalError(new LocalizableError("FNOIRequest.Error.InvalidPolicyNumber", policyNumber));
                claimsLog.error("ErrorMessage="+"\"Unable to process FNOI Request: invalid policy number"+"\" Task=PolicyNumberValidation Status=Failed ErrorType=GwIntegrationException PolicyNumber=" + policyNumber+info);
                ret = new ForwardResolution(this.getClass(), "form");
        } catch (ParseException ex) {
                String info="";
                User user = getUser();
                if(user != null){
                   info = " Name=\""+user.getFirstName()+" "+user.getLastName()+"\" Phone="+user.getPhone()+" Email="+user.getEmail();
                }
                logger.error("Unable to process FNOI Request: Invalid Date of accident - " + dateOfAccident +" policy number" + policyNumber);
                claimsLog.error("ErrorMessage="+"\"Unable to process FNOI Request: Invalid Date of accident - " + dateOfAccident +"\"  Task=PolicyNumberValidation Status=Failed ErrorType=ParseException PolicyNumber=" + policyNumber+info);
                this.getContext().getValidationErrors().addGlobalError(new LocalizableError("FNOIRequest.Error.InvalidDateOfAccident", dateOfAccident));
                ret = new ForwardResolution(this.getClass(), "form");
            
            java.util.logging.Logger.getLogger(FNOIPolicyCheck.class.getName()).log(Level.SEVERE, null, ex);
        } 

        return ret;
    }

    public String getPolicyNumber() {
        return policyNumber;
    }

    public void setPolicyNumber(String policyNumber) {
        this.policyNumber = policyNumber;
    }
    
    public String getDateOfAccident() {
        return dateOfAccident;
    }

    public void setDateOfAccident(String dateOfAccident) {
        this.dateOfAccident = dateOfAccident;
    }
}
