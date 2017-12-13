package com.pwc.us.common.model.fnoi;

import java.util.ArrayList;
import java.util.List;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 *
 * @author Robert Snelling <robert.snelling@us.pwc.com>
 */

public class EmploymentDataFNOI {

    protected XMLGregorianCalendar hireDate;
    protected String hireState;
    protected XMLGregorianCalendar injuryStartTime;
    protected XMLGregorianCalendar lastWorkedDate;
    protected double wageAmount;
    protected EmploymentDataFNOI.WorkStatusChanges workStatusChanges;

    /**
     * Gets the value of the hireDate property.
     */
    public XMLGregorianCalendar getHireDate() {
        return hireDate;
    }

    /**
     * Sets the value of the hireDate property.
     */
    public void setHireDate(XMLGregorianCalendar value) {
        this.hireDate = ((XMLGregorianCalendar ) value);
    }

    /**
     * Gets the value of the hireState property.
     */
    public String getHireState() {
        return hireState;
    }

    /**
     * Sets the value of the hireState property.
     */
    public void setHireState(String value) {
        this.hireState = ((String ) value);
    }

    /**
     * Gets the value of the injuryStartTime property.
     */
    public XMLGregorianCalendar getInjuryStartTime() {
        return injuryStartTime;
    }

    /**
     * Sets the value of the injuryStartTime property.
     */
    public void setInjuryStartTime(XMLGregorianCalendar value) {
        this.injuryStartTime = ((XMLGregorianCalendar ) value);
    }

    /**
     * Gets the value of the lastWorkedDate property.
     */
    public XMLGregorianCalendar getLastWorkedDate() {
        return lastWorkedDate;
    }

    /**
     * Sets the value of the lastWorkedDate property.
     */
    public void setLastWorkedDate(XMLGregorianCalendar value) {
        this.lastWorkedDate = ((XMLGregorianCalendar ) value);
    }

    /**
     * Gets the value of the wageAmount property.
     */
    public double getWageAmount() {
        return wageAmount;
    }

    /**
     * Sets the value of the wageAmount property.
     */
    public void setWageAmount(double value) {
        this.wageAmount = (value);
    }

    /**
     * Gets the value of the workStatusChanges property.
     */
    public EmploymentDataFNOI.WorkStatusChanges getWorkStatusChanges() {
        return workStatusChanges;
    }

    /**
     * Sets the value of the workStatusChanges property.
     */
    public void setWorkStatusChanges(EmploymentDataFNOI.WorkStatusChanges value) {
        this.workStatusChanges = ((EmploymentDataFNOI.WorkStatusChanges ) value);
    }


    /**
     * <p>Java class for anonymous complex type.
     */
    public static class WorkStatusChanges {

        protected List<WorkStatusFNOI> entry;

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
         * {@link WorkStatusFNOI }
         * 
         * 
         */
        public List<WorkStatusFNOI> getEntry() {
            if (entry == null) {
                entry = new ArrayList<WorkStatusFNOI>();
            }
            return this.entry;
        }

    }

}
