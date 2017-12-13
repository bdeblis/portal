package com.pwc.us.common.model.payrollreport;

import java.math.BigDecimal;

/**
 *
 * @author rturnau001
 */
public class CSUpdateCoveredEmployeeResponsePR {

    
    protected BigDecimal calculatedPremium;
    protected String publicId;

    /**
     * Gets the value of the calculatedPremium property.   
     */
    public BigDecimal getCalculatedPremium() {
        return calculatedPremium;
    }

    /**
     * Sets the value of the calculatedPremium property.   
     */
    public void setCalculatedPremium(BigDecimal calcPremium) {
        this.calculatedPremium = calcPremium;
    }

    /**
     * Gets the value of the publicId property.    
     */
    public String getPublicId() {
        return publicId;
    }

    /**
     * Sets the value of the publicId property.  
     */
    public void setPublicId(String publicId) {
        this.publicId = publicId;
    }

}
