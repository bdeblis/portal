/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pwc.us.common.model.payrollreport;

import java.math.BigDecimal;

public class CSPremiumDetailsPR {

   
    CSPremiumDetailsPR.AuditInformation auditInformation;
    BigDecimal estimatedAnnualPremium;

    public CSPremiumDetailsPR.AuditInformation getAuditInformation() {
        return auditInformation;
    }

 
    public void setAuditInformation (CSPremiumDetailsPR.AuditInformation value) {
        this.auditInformation = value;
    }


    public BigDecimal getEstimatedAnnualPremium() {
        return estimatedAnnualPremium;
    }


    public void setEstimatedAnnualPremium(BigDecimal value) {
        this.estimatedAnnualPremium = value;
    }


    public static class AuditInformation {

        
        protected String publicID;

        public String getPublicID() {
            return publicID;
        }


        public void setPublicID(String value) {
            this.publicID =  value;
        }

    }

}
