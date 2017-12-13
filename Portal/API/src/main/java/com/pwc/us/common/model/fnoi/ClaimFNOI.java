package com.pwc.us.common.model.fnoi;

import com.pwc.us.common.model.fnoi.IncidentFNOI;
import com.pwc.us.common.model.fnoi.NoteFNOI;
import com.pwc.us.common.model.fnoi.PolicyFNOI;
import com.pwc.us.common.model.AddressBO;
import com.pwc.us.common.model.fnoi.EmploymentDataFNOI;
import com.pwc.us.common.model.fnoi.ClaimContactFNOI;
import java.util.ArrayList;
import java.util.List;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 *
 * @author Robert Snelling <robert.snelling@us.pwc.com>
 */


public class ClaimFNOI {

    protected ClaimFNOI.Contacts contacts;
    protected XMLGregorianCalendar dateRptdToEmployer;
    protected XMLGregorianCalendar deathDate;
    protected String description;
    protected EmploymentDataFNOI employmentData;
    protected String empQusValidity;
    protected ClaimFNOI.Incidents incidents;
    protected ClaimFNOI.Notes notes;
    protected XMLGregorianCalendar lossDate;
    protected AddressBO lossLocation;
    protected PolicyFNOI policy;
    protected String reportedByType;
    protected String notesString;
    protected String incidentOnly;

    /**
     * Gets the value of the contacts property.
     */
    public ClaimFNOI.Contacts getContacts() {
        return contacts;
    }

    /**
     * Sets the value of the contacts property.
     */
    public void setContacts(ClaimFNOI.Contacts value) {
        this.contacts = ((ClaimFNOI.Contacts ) value);
    }

    /**
     * Gets the value of the dateRptdToEmployer property.
     */
    public XMLGregorianCalendar getDateRptdToEmployer() {
        return dateRptdToEmployer;
    }

    /**
     * Sets the value of the dateRptdToEmployer property.
     */
    public void setDateRptdToEmployer(XMLGregorianCalendar value) {
        this.dateRptdToEmployer = ((XMLGregorianCalendar ) value);
    }

    /**
     * Gets the value of the deathDate property.
     */
    public XMLGregorianCalendar getDeathDate() {
        return deathDate;
    }

    /**
     * Sets the value of the deathDate property.
     */
    public void setDeathDate(XMLGregorianCalendar value) {
        this.deathDate = ((XMLGregorianCalendar ) value);
    }

    /**
     * Gets the value of the description property.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     */
    public void setDescription(String value) {
        this.description = ((String ) value);
    }

    /**
     * Gets the value of the employmentData property.
     */
    public EmploymentDataFNOI getEmploymentData() {
        return employmentData;
    }

    /**
     * Sets the value of the employmentData property.
     */
    public void setEmploymentData(EmploymentDataFNOI value) {
        this.employmentData = ((EmploymentDataFNOI ) value);
    }

    /**
     * Gets the value of the empQusValidity property.
     */
    public String getEmpQusValidity() {
        return empQusValidity;
    }

    /**
     * Sets the value of the empQusValidity property.
     */
    public void setEmpQusValidity(String value) {
        this.empQusValidity = ((String ) value);
    }

    public String getIncidentOnly() {
        return incidentOnly;
    }

    public void setIncidentOnly(String incidentReportQuestion) {
        this.incidentOnly = incidentReportQuestion;
    }
    
    

    /**
     * Gets the value of the incidents property.
     */
    public ClaimFNOI.Incidents getIncidents() {
        return incidents;
    }

    /**
     * Sets the value of the incidents property.
     */
    public void setIncidents(ClaimFNOI.Incidents value) {
        this.incidents = ((ClaimFNOI.Incidents ) value);
    }
    
    /**
     * Gets the value of the notes property.
     */
    public ClaimFNOI.Notes getNotes() {
        return notes;
    }

    /**
     * Sets the value of the notes property.
     */
    public void setNotes(ClaimFNOI.Notes value) {
        this.notes = ((ClaimFNOI.Notes ) value);
    }

    /**
     * Gets the value of the notes property.
     */
    public String getNotesString() {
        return this.notesString;
    }

    /**
     * Sets the value of the notes property.
     */
    public void setNotesString(String value) {
        this.notesString = value;
    }

    
    /**
     * Gets the value of the lossDate property.
     */
    public XMLGregorianCalendar getLossDate() {
        return lossDate;
    }

    /**
     * Sets the value of the lossDate property.
     */
    public void setLossDate(XMLGregorianCalendar value) {
        this.lossDate = ((XMLGregorianCalendar ) value);
    }

    /**
     * Gets the value of the lossLocation property.
     */
    public AddressBO getLossLocation() {
        return lossLocation;
    }

    /**
     * Sets the value of the lossLocation property.
     */
    public void setLossLocation(AddressBO value) {
        this.lossLocation = ((AddressBO ) value);
    }

    /**
     * Gets the value of the policy property.
     */
    public PolicyFNOI getPolicy() {
        return policy;
    }

    /**
     * Sets the value of the policy property.
     */
    public void setPolicy(PolicyFNOI value) {
        this.policy = ((PolicyFNOI ) value);
    }

    /**
     * Gets the value of the reportedByType property.
     */
    public String getReportedByType() {
        return reportedByType;
    }

    /**
     * Sets the value of the reportedByType property.
     */
    public void setReportedByType(String value) {
        this.reportedByType = ((String ) value);
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
     *         &lt;element name="Entry" type="{http://guidewire.com/cc/gx/compsource.cc.api.fnoi.gx.contact.claimcontactmodel}ClaimContact" maxOccurs="unbounded" minOccurs="0"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    public static class Contacts {

        protected List<ClaimContactFNOI> entry;

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
         * {@link ClaimContactFNOI }
         * 
         * 
         */
        public List<ClaimContactFNOI> getEntry() {
            if (entry == null) {
                entry = new ArrayList<ClaimContactFNOI>();
            }
            return this.entry;
        }

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
     *         &lt;element name="Entry" type="{http://guidewire.com/cc/gx/compsource.cc.api.fnoi.gx.incident.incidentmodel}Incident" maxOccurs="unbounded" minOccurs="0"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    public static class Incidents {

        protected List<IncidentFNOI> entry;

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
         * {@link IncidentFNOI }
         * 
         * 
         */
        public List<IncidentFNOI> getEntry() {
            if (entry == null) {
                entry = new ArrayList<IncidentFNOI>();
            }
            return this.entry;
        }

    }
    /*
     * colin kirk    
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     *  <xsd:element name="Notes" minOccurs="0" nillable="true">
     *      <xsd:complexType>
     *          <xsd:sequence>
     *              <xsd:element name="Entry" minOccurs="0" maxOccurs="unbounded" nillable="true" type="ns4:Note"/>
     *          </xsd:sequence>
     *      </xsd:complexType>
     *  </xsd:element>
     *  </pre>
     *
    */
    public static class Notes {
        
        protected List<NoteFNOI> entry;
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
         * {@link NoteFNOI }
         * 
         * 
         */
          public List<NoteFNOI> getEntry() {
            if (entry == null) {
                entry = new ArrayList<NoteFNOI>();
            }
            return this.entry;
        }
    
    }

}
