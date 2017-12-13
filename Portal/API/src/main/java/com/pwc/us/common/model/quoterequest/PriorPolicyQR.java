package com.pwc.us.common.model.quoterequest;

import java.math.BigDecimal;
import java.util.Date;
import org.joda.time.DateTime;

/**
 *
 * @author Roger Turnau - <roger.turnau@us.pwc.com>
 */
public class PriorPolicyQR {
    
    private Date anniversaryDateExt;
    private String carrier;
    private String carriersStateExt;    
    private Date effectiveDate;
    private Date expirationDate;
    private BigDecimal expMod;
    private Date expModEffDateExt;
    private String policyNumber;
    private String policyPeriodExt;
    private String ratingIdExt;

    public Date getAnniversaryDateExt() {
        return anniversaryDateExt;
    }

    public void setAnniversaryDateExt(Date anniversaryDateExt) {
        this.anniversaryDateExt = anniversaryDateExt;
    }

    public String getCarrier() {
        return carrier;
    }

    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }

    public String getCarriersStateExt() {
        return carriersStateExt;
    }

    public void setCarriersStateExt(String carriersStateExt) {
        this.carriersStateExt = carriersStateExt;
    }

    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public BigDecimal getExpMod() {
        return expMod;
    }

    public void setExpMod(BigDecimal expMod) {
        this.expMod = expMod;
    }

    public Date getExpModEffDateExt() {
        return expModEffDateExt;
    }

    public void setExpModEffDateExt(Date expModEffDateExt) {
        this.expModEffDateExt = expModEffDateExt;
    }

    public String getPolicyNumber() {
        return policyNumber;
    }

    public void setPolicyNumber(String policyNumber) {
        this.policyNumber = policyNumber;
    }

    public String getPolicyPeriodExt() {
        return policyPeriodExt;
    }

    public void setPolicyPeriodExt(String policyPeriodExt) {
        this.policyPeriodExt = policyPeriodExt;
    }

    public String getRatingIdExt() {
        return ratingIdExt;
    }

    public void setRatingIdExt(String ratingIdExt) {
        this.ratingIdExt = ratingIdExt;
    }
    
    
    
}
