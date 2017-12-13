package com.pwc.us.common.model.payrollreport;


public class CSSupplDiseaseExposurePR {
    
    protected CSSupplDiseaseExposurePR.SupplDiseaseExposure supplDiseaseExposure;
    
    public CSSupplDiseaseExposurePR.SupplDiseaseExposure getSupplDiseaseExposure() {
        return supplDiseaseExposure;
    }
    
    public void setSupplDiseaseExposure(CSSupplDiseaseExposurePR.SupplDiseaseExposure value) {
        this.supplDiseaseExposure = ((CSSupplDiseaseExposurePR.SupplDiseaseExposure ) value);
    }

            public static class SupplDiseaseExposure {

                protected Integer auditedAmount;
                protected CSSupplDiseaseExposurePR.SupplDiseaseExposure.FixedId fixedId;
                protected CSSupplDiseaseExposurePR.SupplDiseaseExposure.DiseaseCode diseaseCode;
                protected CSSupplDiseaseExposurePR.SupplDiseaseExposure.Location location;

                public void setDiseaseCode(CSSupplDiseaseExposurePR.SupplDiseaseExposure.DiseaseCode value) {
                    this.diseaseCode = ((CSSupplDiseaseExposurePR.SupplDiseaseExposure.DiseaseCode) value);
                }

                public CSSupplDiseaseExposurePR.SupplDiseaseExposure.DiseaseCode getDiseaseCode() {
                    return diseaseCode;
                }

                public Integer getAuditedAmount() {
                    return auditedAmount;
                }

                public void setAuditedAmount(Integer value) {
                    this.auditedAmount = ((Integer) value);
                }

                public CSSupplDiseaseExposurePR.SupplDiseaseExposure.FixedId getFixedId() {
                    return fixedId;
                }

                public void setFixedId(CSSupplDiseaseExposurePR.SupplDiseaseExposure.FixedId value) {
                    this.fixedId = ((CSSupplDiseaseExposurePR.SupplDiseaseExposure.FixedId) value);
                }

                public CSSupplDiseaseExposurePR.SupplDiseaseExposure.Location getLocation() {
                    return location;
                }

                public void setLocation(CSSupplDiseaseExposurePR.SupplDiseaseExposure.Location value) {
                    this.location = ((CSSupplDiseaseExposurePR.SupplDiseaseExposure.Location) value);
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

                    protected CSSupplDiseaseExposurePR.SupplDiseaseExposure.Location.State state;

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

                    public CSSupplDiseaseExposurePR.SupplDiseaseExposure.Location.State getState() {
                        return state;
                    }

                    public void setState(CSSupplDiseaseExposurePR.SupplDiseaseExposure.Location.State value) {
                        this.state = ((CSSupplDiseaseExposurePR.SupplDiseaseExposure.Location.State) value);
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
