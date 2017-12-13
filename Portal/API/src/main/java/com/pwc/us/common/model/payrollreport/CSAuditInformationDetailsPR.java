
package com.pwc.us.common.model.payrollreport;
import java.util.ArrayList;
import java.util.List;
public class CSAuditInformationDetailsPR {
    
    protected CSAuditInformationDetailsPR.CoveredEmployees coveredEmployees;
    
    protected CSAuditInformationDetailsPR.SupplDiseaseExposures supplDiseaseExposures;

    protected CSAuditInformationDetailsPR.IncludedOwnerOfficers includedOwnerOfficers;
    
    public CSAuditInformationDetailsPR.CoveredEmployees getCoveredEmployees() {
        return coveredEmployees;
    }
    
    public void setSupplDiseaseExposures(CSAuditInformationDetailsPR.SupplDiseaseExposures value) {
        this.supplDiseaseExposures = ((CSAuditInformationDetailsPR.SupplDiseaseExposures) value);
    }

    public CSAuditInformationDetailsPR.SupplDiseaseExposures getSupplDiseaseExposures() {
        return supplDiseaseExposures;
    }

    public void setCoveredEmployees(CSAuditInformationDetailsPR.CoveredEmployees value) {
        this.coveredEmployees = ((CSAuditInformationDetailsPR.CoveredEmployees ) value);
    }
    
    public CSAuditInformationDetailsPR.IncludedOwnerOfficers getIncludedOwnerOfficers() {
        return includedOwnerOfficers;
    }
    
    public void setIncludedOwnerOfficers(CSAuditInformationDetailsPR.IncludedOwnerOfficers value) {
        this.includedOwnerOfficers = ((CSAuditInformationDetailsPR.IncludedOwnerOfficers ) value);
    }
    
    public static class SupplDiseaseExposures {

        protected List<CSAuditInformationDetailsPR.SupplDiseaseExposures.Entry> entry;

        public List<CSAuditInformationDetailsPR.SupplDiseaseExposures.Entry> getEntry() {
            if (entry == null) {
                entry = new ArrayList<CSAuditInformationDetailsPR.SupplDiseaseExposures.Entry>();
            }
            return this.entry;
        }

        public static class Entry {

            protected CSAuditInformationDetailsPR.SupplDiseaseExposures.Entry.SupplDiseaseExposure supplDiseaseExposure;

            public CSAuditInformationDetailsPR.SupplDiseaseExposures.Entry.SupplDiseaseExposure getSupplDiseaseExposure() {
                return supplDiseaseExposure;
            }

            public void setSupplDiseaseExposure(CSAuditInformationDetailsPR.SupplDiseaseExposures.Entry.SupplDiseaseExposure value) {
                this.supplDiseaseExposure = ((CSAuditInformationDetailsPR.SupplDiseaseExposures.Entry.SupplDiseaseExposure) value);
            }

            public static class SupplDiseaseExposure {

                protected Integer auditedAmount;
                protected CSAuditInformationDetailsPR.SupplDiseaseExposures.Entry.SupplDiseaseExposure.FixedId fixedId;
                protected CSAuditInformationDetailsPR.SupplDiseaseExposures.Entry.SupplDiseaseExposure.DiseaseCode diseaseCode;
                protected CSAuditInformationDetailsPR.SupplDiseaseExposures.Entry.SupplDiseaseExposure.Location location;

                public void setDiseaseCode(CSAuditInformationDetailsPR.SupplDiseaseExposures.Entry.SupplDiseaseExposure.DiseaseCode value) {
                    this.diseaseCode = ((CSAuditInformationDetailsPR.SupplDiseaseExposures.Entry.SupplDiseaseExposure.DiseaseCode) value);
                }

                public CSAuditInformationDetailsPR.SupplDiseaseExposures.Entry.SupplDiseaseExposure.DiseaseCode getDiseaseCode() {
                    return diseaseCode;
                }

                public Integer getAuditedAmount() {
                    return auditedAmount;
                }

                public void setAuditedAmount(Integer value) {
                    this.auditedAmount = ((Integer) value);
                }

                public CSAuditInformationDetailsPR.SupplDiseaseExposures.Entry.SupplDiseaseExposure.FixedId getFixedId() {
                    return fixedId;
                }

                public void setFixedId(CSAuditInformationDetailsPR.SupplDiseaseExposures.Entry.SupplDiseaseExposure.FixedId value) {
                    this.fixedId = ((CSAuditInformationDetailsPR.SupplDiseaseExposures.Entry.SupplDiseaseExposure.FixedId) value);
                }

                public CSAuditInformationDetailsPR.SupplDiseaseExposures.Entry.SupplDiseaseExposure.Location getLocation() {
                    return location;
                }

                public void setLocation(CSAuditInformationDetailsPR.SupplDiseaseExposures.Entry.SupplDiseaseExposure.Location value) {
                    this.location = ((CSAuditInformationDetailsPR.SupplDiseaseExposures.Entry.SupplDiseaseExposure.Location) value);
                }

                public static class DiseaseCode {

                    protected String code;
                    protected String supplDiseaseLoadingType;

                    public String getCode() {
                        return code;
                    }

                    public void setCode(String code) {
                        this.code = code;
                    }

                    public String getSupplDiseaseLoadingType() {
                        return supplDiseaseLoadingType;
                    }

                    public void setSupplDiseaseLoadingType(String supplDiseaseLoadingType) {
                        this.supplDiseaseLoadingType = supplDiseaseLoadingType;
                    }

                }

                public static class FixedId {

                    protected Long objValue;

                    public Long getObjValue() {
                        return objValue;
                    }

                    public void setObjValue(Long value) {
                        this.objValue = ((Long) value);
                    }

                }

                public static class Location {

                    protected String addressLine1;

                    protected String addressLine2;

                    protected String addressLine3;

                    protected String city;

                    protected String displayName;

                    protected Integer locationNum;

                    protected String postalCode;

                    protected Boolean primaryLoc;

                    protected CSAuditInformationDetailsPR.SupplDiseaseExposures.Entry.SupplDiseaseExposure.Location.State state;

                    public String getAddressLine1() {
                        return addressLine1;
                    }

                    public void setAddressLine1(String value) {
                        this.addressLine1 = ((String) value);
                    }

                    public String getAddressLine2() {
                        return addressLine2;
                    }

                    public void setAddressLine2(String value) {
                        this.addressLine2 = ((String) value);
                    }

                    public String getAddressLine3() {
                        return addressLine3;
                    }

                    public void setAddressLine3(String value) {
                        this.addressLine3 = ((String) value);
                    }

                    public String getCity() {
                        return city;
                    }

                    public void setCity(String value) {
                        this.city = ((String) value);
                    }

                    public String getDisplayName() {
                        return displayName;
                    }

                    public void setDisplayName(String value) {
                        this.displayName = ((String) value);
                    }

                    public Integer getLocationNum() {
                        return locationNum;
                    }

                    public void setLocationNum(Integer value) {
                        this.locationNum = ((Integer) value);
                    }

                    public String getPostalCode() {
                        return postalCode;
                    }

                    public void setPostalCode(String value) {
                        this.postalCode = ((String) value);
                    }

                    public Boolean isPrimaryLoc() {
                        return primaryLoc;
                    }

                    public void setPrimaryLoc(Boolean primaryLoc) {
                        this.primaryLoc = primaryLoc;
                    }

                    public CSAuditInformationDetailsPR.SupplDiseaseExposures.Entry.SupplDiseaseExposure.Location.State getState() {
                        return state;
                    }

                    public void setState(CSAuditInformationDetailsPR.SupplDiseaseExposures.Entry.SupplDiseaseExposure.Location.State value) {
                        this.state = ((CSAuditInformationDetailsPR.SupplDiseaseExposures.Entry.SupplDiseaseExposure.Location.State) value);
                    }

                    public static class State {

                        protected String code;

                        protected String description;

                        public String getCode() {
                            return code;
                        }

                        public void setCode(String value) {
                            this.code = ((String) value);
                        }

                        public String getDescription() {
                            return description;
                        }

                        public void setDescription(String value) {
                            this.description = ((String) value);
                        }
                    }
                }
            }
        }
    }
    
    public static class CoveredEmployees {
        
        protected List<CSAuditInformationDetailsPR.CoveredEmployees.Entry> entry;
        
        public List<CSAuditInformationDetailsPR.CoveredEmployees.Entry> getEntry() {
            if (entry == null) {
                entry = new ArrayList<CSAuditInformationDetailsPR.CoveredEmployees.Entry>();
            }
            return this.entry;
        }
        
        public static class Entry {
            
            protected Integer basisForRating;
            
            protected CSAuditInformationDetailsPR.CoveredEmployees.Entry.CoveredEmployee coveredEmployee;
            protected String action;
            
            public Integer getBasisForRating() {
                return basisForRating;
            }
            
            public void setBasisForRating(Integer value) {
                this.basisForRating = ((Integer ) value);
            }
            
            public CSAuditInformationDetailsPR.CoveredEmployees.Entry.CoveredEmployee getCoveredEmployee() {
                return coveredEmployee;
            }
            
            public void setCoveredEmployee(CSAuditInformationDetailsPR.CoveredEmployees.Entry.CoveredEmployee value) {
                this.coveredEmployee = ((CSAuditInformationDetailsPR.CoveredEmployees.Entry.CoveredEmployee ) value);
            }
            
            public String getAction() {
                return action;
            }
            
            public void setAction(String value) {
                this.action = value;
            }
            
            
            public static class CoveredEmployee {
                
                protected String basisType;
                
                protected CSAuditInformationDetailsPR.CoveredEmployees.Entry.CoveredEmployee.ClassCode classCode;
                
                protected CSAuditInformationDetailsPR.CoveredEmployees.Entry.CoveredEmployee.FixedId fixedId;
                
                protected CSAuditInformationDetailsPR.CoveredEmployees.Entry.CoveredEmployee.Location location;
                
                protected Integer auditedAmount;
                
                public String getBasisType() {
                    return basisType;
                }
                
                public void setBasisType(String value) {
                    this.basisType = ((String ) value);
                }
                
                public CSAuditInformationDetailsPR.CoveredEmployees.Entry.CoveredEmployee.ClassCode getClassCode() {
                    return classCode;
                }
                
                public void setClassCode(CSAuditInformationDetailsPR.CoveredEmployees.Entry.CoveredEmployee.ClassCode value) {
                    this.classCode = ((CSAuditInformationDetailsPR.CoveredEmployees.Entry.CoveredEmployee.ClassCode ) value);
                }
                
                public CSAuditInformationDetailsPR.CoveredEmployees.Entry.CoveredEmployee.FixedId getFixedId() {
                    return fixedId;
                }

                public void setFixedId(CSAuditInformationDetailsPR.CoveredEmployees.Entry.CoveredEmployee.FixedId value) {
                    this.fixedId = ((CSAuditInformationDetailsPR.CoveredEmployees.Entry.CoveredEmployee.FixedId ) value);
                }
                
                
                public CSAuditInformationDetailsPR.CoveredEmployees.Entry.CoveredEmployee.Location getLocation() {
                    return location;
                }
                
                public void setLocation(CSAuditInformationDetailsPR.CoveredEmployees.Entry.CoveredEmployee.Location value) {
                    this.location = ((CSAuditInformationDetailsPR.CoveredEmployees.Entry.CoveredEmployee.Location ) value);
                }
                
                public Integer getAuditedAmount() {
                    return auditedAmount;
                }
                
                public void setAuditedAmount(Integer value) {
                    this.auditedAmount = ((Integer ) value);
                }
                
                public static class ClassCode {
                    
                    protected String code;
                    
                    protected String descInd;
                    
                    protected String shortDesc;
                    
                    public String getCode() {
                        return code;
                    }
                    
                    public void setCode(String value) {
                        this.code = ((String ) value);
                    }
                    
                    public String getDescInd() {
                        return descInd;
                    }
                    
                    public void setDescInd(String value) {
                        this.descInd = ((String ) value);
                    }
                    
                    public String getShortDesc() {
                        return shortDesc;
                    }
                    
                    public void setShortDesc(String value) {
                        this.shortDesc = ((String ) value);
                    }
                }
                
                public static class FixedId {

                    protected Long objValue;

                    public Long getObjValue() {
                        return objValue;
                    }

                    public void setObjValue(Long value) {
                        this.objValue = ((Long ) value);
                    }

                }
                
                
                public static class Location {
                    
                    protected String addressLine1;
                    
                    protected String addressLine2;
                    
                    protected String addressLine3;
                    
                    protected String city;
                    
                    protected String displayName;
                    
                    protected Integer locationNum;
                    
                    protected String postalCode;
                    
                    protected Boolean primaryLoc;

                    protected CSAuditInformationDetailsPR.CoveredEmployees.Entry.CoveredEmployee.Location.State state;
                    
                    public String getAddressLine1() {
                        return addressLine1;
                    }
                    
                    public void setAddressLine1(String value) {
                        this.addressLine1 = ((String ) value);
                    }
                    
                    public String getAddressLine2() {
                        return addressLine2;
                    }
                    
                    public void setAddressLine2(String value) {
                        this.addressLine2 = ((String ) value);
                    }
                    
                    public String getAddressLine3() {
                        return addressLine3;
                    }
                    
                    public void setAddressLine3(String value) {
                        this.addressLine3 = ((String ) value);
                    }
                    
                    public String getCity() {
                        return city;
                    }
                    
                    public void setCity(String value) {
                        this.city = ((String ) value);
                    }
                    
                    public String getDisplayName() {
                        return displayName;
                    }
                    
                    public void setDisplayName(String value) {
                        this.displayName = ((String ) value);
                    }
                    
                    public Integer getLocationNum() {
                        return locationNum;
                    }
                    
                    public void setLocationNum(Integer value) {
                        this.locationNum = ((Integer ) value);
                    }
                    
                    public String getPostalCode() {
                        return postalCode;
                    }
                    
                    public void setPostalCode(String value) {
                        this.postalCode = ((String ) value);
                    }
                    
                    public Boolean isPrimaryLoc() {
                        return primaryLoc;
                    }

                    public void setPrimaryLoc(Boolean primaryLoc) {
                        this.primaryLoc = primaryLoc;
                    }

                    public CSAuditInformationDetailsPR.CoveredEmployees.Entry.CoveredEmployee.Location.State getState() {
                        return state;
                    }
                    
                    public void setState(CSAuditInformationDetailsPR.CoveredEmployees.Entry.CoveredEmployee.Location.State value) {
                        this.state = ((CSAuditInformationDetailsPR.CoveredEmployees.Entry.CoveredEmployee.Location.State ) value);
                    }
                    
                    
                    public static class State {
                        
                        protected String code;
                        
                        protected String description;
                        
                        public String getCode() {
                            return code;
                        }
                        
                        public void setCode(String value) {
                            this.code = ((String ) value);
                        }
                        
                        public String getDescription() {
                            return description;
                        }
                        
                        public void setDescription(String value) {
                            this.description = ((String ) value);
                        }
                    }
                }
            }
        }
    }
    
    
    public static class IncludedOwnerOfficers {
        
        protected List<CSAuditInformationDetailsPR.IncludedOwnerOfficers.Entry> entry;
        
        public List<CSAuditInformationDetailsPR.IncludedOwnerOfficers.Entry> getEntry() {
            if (entry == null) {
                entry = new ArrayList<CSAuditInformationDetailsPR.IncludedOwnerOfficers.Entry>();
            }
            return this.entry;
        }
        
        
        public static class Entry {
            
            protected CSAuditInformationDetailsPR.IncludedOwnerOfficers.Entry.IncludedOwnerOfficer includedOwnerOfficer;
            
            protected Integer periodProRatedMiscValue;
                        protected String action;
            
            public CSAuditInformationDetailsPR.IncludedOwnerOfficers.Entry.IncludedOwnerOfficer getIncludedOwnerOfficer() {
                return includedOwnerOfficer;
            }
            
            public void setIncludedOwnerOfficer(CSAuditInformationDetailsPR.IncludedOwnerOfficers.Entry.IncludedOwnerOfficer value) {
                this.includedOwnerOfficer = ((CSAuditInformationDetailsPR.IncludedOwnerOfficers.Entry.IncludedOwnerOfficer ) value);
            }
            
            public Integer getPeriodProRatedMiscValue() {
                return periodProRatedMiscValue;
            }
            
            public void setPeriodProRatedMiscValue(Integer value) {
                this.periodProRatedMiscValue = ((Integer ) value);
            }
            
            public String getAction() {
                return action;
            }
            
            public void setAction(String value) {
                this.action = value;
            }
            
            
            public static class IncludedOwnerOfficer {
                
                protected CSAuditInformationDetailsPR.IncludedOwnerOfficers.Entry.IncludedOwnerOfficer.AccountContactRole accountContactRole;
                
                protected CSAuditInformationDetailsPR.IncludedOwnerOfficers.Entry.IncludedOwnerOfficer.ItemRelationshipTitle itemRelationshipTitle;
                
                protected CSAuditInformationDetailsPR.IncludedOwnerOfficers.Entry.IncludedOwnerOfficer.Jurisdiction jurisdiction;
                
                protected CSAuditInformationDetailsPR.IncludedOwnerOfficers.Entry.IncludedOwnerOfficer.NamedInsured namedInsured;
                
                protected CSAuditInformationDetailsPR.IncludedOwnerOfficers.Entry.IncludedOwnerOfficer.WC7ClassCode wc7ClassCode;
                
                public CSAuditInformationDetailsPR.IncludedOwnerOfficers.Entry.IncludedOwnerOfficer.AccountContactRole getAccountContactRole() {
                    return accountContactRole;
                }
                
                public void setAccountContactRole(CSAuditInformationDetailsPR.IncludedOwnerOfficers.Entry.IncludedOwnerOfficer.AccountContactRole value) {
                    this.accountContactRole = ((CSAuditInformationDetailsPR.IncludedOwnerOfficers.Entry.IncludedOwnerOfficer.AccountContactRole ) value);
                }
                
                public CSAuditInformationDetailsPR.IncludedOwnerOfficers.Entry.IncludedOwnerOfficer.ItemRelationshipTitle getItemRelationshipTitle() {
                    return itemRelationshipTitle;
                }
                
                public void setItemRelationshipTitle(CSAuditInformationDetailsPR.IncludedOwnerOfficers.Entry.IncludedOwnerOfficer.ItemRelationshipTitle value) {
                    this.itemRelationshipTitle = ((CSAuditInformationDetailsPR.IncludedOwnerOfficers.Entry.IncludedOwnerOfficer.ItemRelationshipTitle ) value);
                }
                
                public CSAuditInformationDetailsPR.IncludedOwnerOfficers.Entry.IncludedOwnerOfficer.Jurisdiction getJurisdiction() {
                    return jurisdiction;
                }
                
                public void setJurisdiction(CSAuditInformationDetailsPR.IncludedOwnerOfficers.Entry.IncludedOwnerOfficer.Jurisdiction value) {
                    this.jurisdiction = ((CSAuditInformationDetailsPR.IncludedOwnerOfficers.Entry.IncludedOwnerOfficer.Jurisdiction ) value);
                }
                
                public CSAuditInformationDetailsPR.IncludedOwnerOfficers.Entry.IncludedOwnerOfficer.NamedInsured getNamedInsured() {
                    return namedInsured;
                }
                
                public void setNamedInsured(CSAuditInformationDetailsPR.IncludedOwnerOfficers.Entry.IncludedOwnerOfficer.NamedInsured value) {
                    this.namedInsured = ((CSAuditInformationDetailsPR.IncludedOwnerOfficers.Entry.IncludedOwnerOfficer.NamedInsured ) value);
                }
                
                public CSAuditInformationDetailsPR.IncludedOwnerOfficers.Entry.IncludedOwnerOfficer.WC7ClassCode getWC7ClassCode() {
                    return wc7ClassCode;
                }
                
                public void setWC7ClassCode(CSAuditInformationDetailsPR.IncludedOwnerOfficers.Entry.IncludedOwnerOfficer.WC7ClassCode value) {
                    this.wc7ClassCode = ((CSAuditInformationDetailsPR.IncludedOwnerOfficers.Entry.IncludedOwnerOfficer.WC7ClassCode ) value);
                }
                
                
                public static class AccountContactRole {
                    
                    protected CSAuditInformationDetailsPR.IncludedOwnerOfficers.Entry.IncludedOwnerOfficer.AccountContactRole.AccountContact accountContact;
                    
                    public CSAuditInformationDetailsPR.IncludedOwnerOfficers.Entry.IncludedOwnerOfficer.AccountContactRole.AccountContact getAccountContact() {
                        return accountContact;
                    }
                    
                    public void setAccountContact(CSAuditInformationDetailsPR.IncludedOwnerOfficers.Entry.IncludedOwnerOfficer.AccountContactRole.AccountContact value) {
                        this.accountContact = ((CSAuditInformationDetailsPR.IncludedOwnerOfficers.Entry.IncludedOwnerOfficer.AccountContactRole.AccountContact ) value);
                    }
                    
                    
                    public static class AccountContact {
                        
                        protected CSAuditInformationDetailsPR.IncludedOwnerOfficers.Entry.IncludedOwnerOfficer.AccountContactRole.AccountContact.Contact contact;
                        
                        public CSAuditInformationDetailsPR.IncludedOwnerOfficers.Entry.IncludedOwnerOfficer.AccountContactRole.AccountContact.Contact getContact() {
                            return contact;
                        }
                        
                        public void setContact(CSAuditInformationDetailsPR.IncludedOwnerOfficers.Entry.IncludedOwnerOfficer.AccountContactRole.AccountContact.Contact value) {
                            this.contact = ((CSAuditInformationDetailsPR.IncludedOwnerOfficers.Entry.IncludedOwnerOfficer.AccountContactRole.AccountContact.Contact ) value);
                        }
                        
                        
                        public static class Contact {
                            
                            protected String displayName;
                            
                            public String getDisplayName() {
                                return displayName;
                            }
                            
                            public void setDisplayName(String value) {
                                this.displayName = ((String ) value);
                            }
                        }
                    }
                }
                
                
                public static class ItemRelationshipTitle {
                    
                    protected String displayName;
                    
                    public String getDisplayName() {
                        return displayName;
                    }
                    
                    public void setDisplayName(String value) {
                        this.displayName = ((String ) value);
                    }
                }
                
                
                public static class Jurisdiction {
                    
                    protected String displayName;
                    
                    public String getDisplayName() {
                        return displayName;
                    }
                    
                    public void setDisplayName(String value) {
                        this.displayName = ((String ) value);
                    }
                }
                
                
                public static class NamedInsured {
                    
                    protected String displayName;
                    
                    public String getDisplayName() {
                        return displayName;
                    }
                    
                    public void setDisplayName(String value) {
                        this.displayName = ((String ) value);
                    }
                }
                
                public static class WC7ClassCode {
                    
                    protected String code;
                    
                    protected String descInd;
                    
                    protected String shortDesc;
                    
                    public String getCode() {
                        return code;
                    }
                    
                    public void setCode(String value) {
                        this.code = ((String ) value);
                    }
                    
                    public String getDescInd() {
                        return descInd;
                    }
                    
                    public void setDescInd(String value) {
                        this.descInd = ((String ) value);
                    }
                    
                    public String getShortDesc() {
                        return shortDesc;
                    }
                    
                    public void setShortDesc(String value) {
                        this.shortDesc = ((String ) value);
                    }
                }
            }
        }
    }
}
