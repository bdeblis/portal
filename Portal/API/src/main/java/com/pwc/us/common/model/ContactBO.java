package com.pwc.us.common.model;

import javax.xml.datatype.XMLGregorianCalendar;

/**
 * 
 * @author Robert Snelling <robert.snelling@us.pwc.com>
 */

public class ContactBO {

    protected ContactBO.EntityPerson entityPerson;
    protected String emailAddress1;
    protected String homePhone;
    protected String name;
    protected AddressBO primaryAddress;
    protected String taxID;
    protected String workPhone;
    protected String faxPhone;
    protected String subType;
    protected String relationship;

    /**
     * Gets the value of the entityPerson property.
     */
    public ContactBO.EntityPerson getEntityPerson() {
        return entityPerson;
    }

    /**
     * Sets the value of the entityPerson property.
     */
    public void setEntityPerson(ContactBO.EntityPerson value) {
        this.entityPerson = ((ContactBO.EntityPerson ) value);
    }

    /**
     * Gets the value of the emailAddress1 property.
     */
    public String getEmailAddress1() {
        return emailAddress1;
    }

    /**
     * Sets the value of the emailAddress1 property.
     */
    public void setEmailAddress1(String value) {
        this.emailAddress1 = ((String ) value);
    }

    /**
     * Gets the value of the homePhone property.
     */
    public String getHomePhone() {
        return homePhone;
    }

    /**
     * Sets the value of the homePhone property.
     */
    public void setHomePhone(String value) {
        this.homePhone = ((String ) value);
    }

    /**
     * Gets the value of the name property.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     */
    public void setName(String value) {
        this.name = ((String ) value);
    }

    /**
     * Gets the value of the primaryAddress property.
     */
    public AddressBO getPrimaryAddress() {
        return primaryAddress;
    }

    /**
     * Sets the value of the primaryAddress property.
     */
    public void setPrimaryAddress(AddressBO value) {
        this.primaryAddress = ((AddressBO ) value);
    }

    /**
     * Gets the value of the taxID property.
     */
    public String getTaxID() {
        return taxID;
    }

    /**
     * Sets the value of the taxID property.
     */
    public void setTaxID(String value) {
        this.taxID = ((String ) value);
    }

    /**
     * Gets the value of the workPhone property.
     */
    public String getWorkPhone() {
        return workPhone;
    }

    /**
     * Sets the value of the workPhone property.
     */
    public void setWorkPhone(String value) {
        this.workPhone = ((String ) value);
    }

    /**
     * Sets the value of the faxPhone property.
     */
    public String getFaxPhone() {
        return faxPhone;
    }

    /**
     * Sets the value of the faxPhone property.
     */
    public void setFaxPhone(String faxPhone) {
        this.faxPhone = faxPhone;
    }

    public String getSubType() {
        return subType;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }
    
    

    /**
     * <p>Java class for anonymous complex type.
     */
    public static class EntityPerson {

        protected XMLGregorianCalendar dateOfBirth;
        protected String firstName;
        protected String gender;
        protected String lastName;
        protected String middleName;
        protected String occupation;

        /**
         * Gets the value of the dateOfBirth property.
         */
        public XMLGregorianCalendar getDateOfBirth() {
            return dateOfBirth;
        }

        /**
         * Sets the value of the dateOfBirth property.
         */
        public void setDateOfBirth(XMLGregorianCalendar value) {
            this.dateOfBirth = ((XMLGregorianCalendar ) value);
        }

        /**
         * Gets the value of the firstName property.
         */
        public String getFirstName() {
            return firstName;
        }

        /**
         * Sets the value of the firstName property.
         */
        public void setFirstName(String value) {
            this.firstName = ((String ) value);
        }

        /**
         * Gets the value of the gender property.
         */
        public String getGender() {
            return gender;
        }

        /**
         * Sets the value of the gender property.
         */
        public void setGender(String value) {
            this.gender = ((String ) value);
        }

        /**
         * Gets the value of the lastName property.
         */
        public String getLastName() {
            return lastName;
        }

        /**
         * Sets the value of the lastName property.
         */
        public void setLastName(String value) {
            this.lastName = ((String ) value);
        }

        /**
         * Gets the value of the middleName property.
         */
        public String getMiddleName() {
            return middleName;
        }

        /**
         * Sets the value of the middleName property.
         */
        public void setMiddleName(String value) {
            this.middleName = ((String ) value);
        }

        /**
         * Gets the value of the occupation property.
         */
        public String getOccupation() {
            return occupation;
        }

        /**
         * Sets the value of the occupation property.
         */
        public void setOccupation(String value) {
            this.occupation = ((String ) value);
        }

    }

}
