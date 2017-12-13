package com.pwc.us.common.model.fnoi;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Robert Snelling <robert.snelling@us.pwc.com>
 */

public class IncidentFNOI {

    protected IncidentFNOI.EntityInjuryIncident entityInjuryIncident;
    protected String severity;

    /**
     * Gets the value of the entityInjuryIncident property.
     */
    public IncidentFNOI.EntityInjuryIncident getEntityInjuryIncident() {
        return entityInjuryIncident;
    }

    /**
     * Sets the value of the entityInjuryIncident property.
     */
    public void setEntityInjuryIncident(IncidentFNOI.EntityInjuryIncident value) {
        this.entityInjuryIncident = ((IncidentFNOI.EntityInjuryIncident ) value);
    }

    /**
     * Gets the value of the severity property.
     */
    public String getSeverity() {
        return severity;
    }

    /**
     * Sets the value of the severity property.
     */
    public void setSeverity(String value) {
        this.severity = ((String ) value);
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="BodyParts" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="Entry" type="{http://guidewire.com/cc/gx/compsource.cc.api.fnoi.gx.incident.bodypartdetailsmodel}BodyPartDetails" maxOccurs="unbounded" minOccurs="0"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="DetailedInjuryType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="GeneralInjuryType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    public static class EntityInjuryIncident {

        protected IncidentFNOI.EntityInjuryIncident.BodyParts bodyParts;
        protected String detailedInjuryType;
        protected String generalInjuryType;
        protected String occupationalInjuryType;
        protected String multipleInjuryType;
        protected String cumulativeInjuryType;
        protected String specificDetailedInjuryType;

        public String getSpecificDetailedInjuryType() {
            return specificDetailedInjuryType;
        }

        public void setSpecificDetailedInjuryType(String specificDetailedInjuryType) {
            this.specificDetailedInjuryType = specificDetailedInjuryType;
        }
        

        public String getCumulativeInjuryType() {
            return cumulativeInjuryType;
        }

        public void setCumulativeInjuryType(String cumulativeInjuryType) {
            this.cumulativeInjuryType = cumulativeInjuryType;
        }

        public String getOccupationalInjuryType() {
            return occupationalInjuryType;
        }

        public void setOccupationalInjuryType(String occupationalInjuryType) {
            this.occupationalInjuryType = occupationalInjuryType;
        }

        public String getMultipleInjuryType() {
            return multipleInjuryType;
        }

        public void setMultipleInjuryType(String multipleInjuryType) {
            this.multipleInjuryType = multipleInjuryType;
        }

        
        /**
         * Gets the value of the bodyParts property.
         */
        public IncidentFNOI.EntityInjuryIncident.BodyParts getBodyParts() {
            return bodyParts;
        }

        /**
         * Sets the value of the bodyParts property.
         */
        public void setBodyParts(IncidentFNOI.EntityInjuryIncident.BodyParts value) {
            this.bodyParts = ((IncidentFNOI.EntityInjuryIncident.BodyParts ) value);
        }

        /**
         * Gets the value of the detailedInjuryType property.
         */
        public String getDetailedInjuryType() {
            return detailedInjuryType;
        }

        /**
         * Sets the value of the detailedInjuryType property.
         */
        public void setDetailedInjuryType(String value) {
            this.detailedInjuryType = ((String ) value);
        }

        /**
         * Gets the value of the generalInjuryType property.
         */
        public String getGeneralInjuryType() {
            return generalInjuryType;
        }

        /**
         * Sets the value of the generalInjuryType property.
         */
        public void setGeneralInjuryType(String value) {
            this.generalInjuryType = ((String ) value);
        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element name="Entry" type="{http://guidewire.com/cc/gx/compsource.cc.api.fnoi.gx.incident.bodypartdetailsmodel}BodyPartDetails" maxOccurs="unbounded" minOccurs="0"/>
         *       &lt;/sequence>
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        public static class BodyParts {

            protected List<BodyPartDetailsFNOI> entry;

            /**
             * Gets the value of the entry property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. This is why there is not a <CODE>set</CODE> method for the entry property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getEntry().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link BodyPartDetails }
             * 
             * 
             */
            public List<BodyPartDetailsFNOI> getEntry() {
                if (entry == null) {
                    entry = new ArrayList<BodyPartDetailsFNOI>();
                }
                return this.entry;
            }

        }

    }

}
