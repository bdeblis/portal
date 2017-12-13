
package com.pwc.us.common.model;

import java.io.Serializable;

public class PolicyTerm implements Serializable, Comparable {

    public String termDescription;
    public Integer termNumber;

    /**
     * Gets the value of the termDescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTermDescription() {
        return termDescription;
    }

    /**
     * Sets the value of the termDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTermDescription(String value) {
        this.termDescription = value;
    }

    /**
     * Gets the value of the termNumber property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getTermNumber() {
        return termNumber;
    }

    /**
     * Sets the value of the termNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setTermNumber(Integer value) {
        this.termNumber = value;
    }
    
    @Override
    public String toString() {
        return String.format("%s - %s", termNumber, termDescription);
    }

    @Override
    public int compareTo(Object t) {
        PolicyTerm pt = (PolicyTerm)t;
        return this.termNumber.compareTo(pt.termNumber);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 61 * hash + (this.termNumber != null ? this.termNumber.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PolicyTerm other = (PolicyTerm) obj;
        if (this.termNumber != other.termNumber && (this.termNumber == null || !this.termNumber.equals(other.termNumber))) {
            return false;
        }
        return true;
    }

    
    

}
