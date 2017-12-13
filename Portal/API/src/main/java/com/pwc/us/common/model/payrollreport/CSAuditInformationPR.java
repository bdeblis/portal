
package com.pwc.us.common.model.payrollreport;

import java.math.BigDecimal;
import javax.xml.datatype.XMLGregorianCalendar;

public class CSAuditInformationPR {

    protected CSAuditInformationPR.DisplayableAuditInfo displayableAuditInfo;
    protected String policyStatus;
    public CSAuditInformationPR.DisplayableAuditInfo getDisplayableAuditInfo() {
        return displayableAuditInfo;
    }

    public void setDisplayableAuditInfo(CSAuditInformationPR.DisplayableAuditInfo value) {
        this.displayableAuditInfo = value;
    }

    public String getPolicyStatus() {
        return policyStatus;
    }

    public void setPolicyStatus(String policyStatus) {
        this.policyStatus = policyStatus;
    }


    public static class DisplayableAuditInfo {

        protected CSAuditInformationPR.DisplayableAuditInfo.ActualInfo actualInfo;
        protected XMLGregorianCalendar closeDate;
        protected XMLGregorianCalendar dueDate;
        protected XMLGregorianCalendar endDate;
        protected XMLGregorianCalendar initDate;
        protected String method;
        protected XMLGregorianCalendar startDate;
        protected String status;
        protected BigDecimal totalCost;
        protected BigDecimal transactionAmount;
        protected String type;
        protected String policyStatus;

        
        
        public CSAuditInformationPR.DisplayableAuditInfo.ActualInfo getActualInfo() {
            return actualInfo;
        }

        public void setActualInfo(CSAuditInformationPR.DisplayableAuditInfo.ActualInfo value) {
            this.actualInfo = ((CSAuditInformationPR.DisplayableAuditInfo.ActualInfo ) value);
        }


        public XMLGregorianCalendar getCloseDate() {
            return closeDate;
        }

        public void setCloseDate(XMLGregorianCalendar value) {
            this.closeDate = ((XMLGregorianCalendar ) value);
        }


        public XMLGregorianCalendar getDueDate() {
            return dueDate;
        }

        public void setDueDate(XMLGregorianCalendar value) {
            this.dueDate = ((XMLGregorianCalendar ) value);
        }


        public XMLGregorianCalendar getEndDate() {
            return endDate;
        }

        public void setEndDate(XMLGregorianCalendar value) {
            this.endDate = ((XMLGregorianCalendar ) value);
        }

        public XMLGregorianCalendar getInitDate() {
            return initDate;
        }

        public void setInitDate(XMLGregorianCalendar value) {
            this.initDate = ((XMLGregorianCalendar ) value);
        }

        
        public String getMethod() {
            return method;
        }

        
        public void setMethod(String value) {
            this.method = ((String ) value);
        }

        
        public XMLGregorianCalendar getStartDate() {
            return startDate;
        }

        
        public void setStartDate(XMLGregorianCalendar value) {
            this.startDate = ((XMLGregorianCalendar ) value);
        }

        
        public String getStatus() {
            return status;
        }

        
        public void setStatus(String value) {
            this.status = ((String ) value);
        }

        
        public BigDecimal getTotalCost() {
            return totalCost;
        }

        
        public void setTotalCost(BigDecimal value) {
            this.totalCost = ((BigDecimal ) value);
        }

        
        public BigDecimal getTransactionAmount() {
            return transactionAmount;
        }

        
        public void setTransactionAmount(BigDecimal value) {
            this.transactionAmount = ((BigDecimal ) value);
        }

        
        public String getType() {
            return type;
        }

        
        public void setType(String value) {
            this.type = ((String ) value);
        }


        
        public static class ActualInfo {

            protected Boolean isComplete;
            protected Boolean isEditable;
            protected Boolean isFinalAudit;
            protected Boolean isOpen;
            protected Boolean isPremiumReport;
            protected Boolean isScheduled;
            protected CSAuditInformationPR.DisplayableAuditInfo.ActualInfo.PolicyTerm policyTerm;
            protected String publicID;

            public Boolean getIsComplete() {
                return isComplete;
            }

            public void setIsComplete(Boolean value) {
                this.isComplete = ((Boolean ) value);
            }

            public Boolean getIsEditable() {
                return isEditable;
            }

            public void setIsEditable(Boolean value) {
                this.isEditable = ((Boolean ) value);
            }

            public Boolean getIsFinalAudit() {
                return isFinalAudit;
            }

            public void setIsFinalAudit(Boolean value) {
                this.isFinalAudit = ((Boolean ) value);
            }

            public Boolean getIsOpen() {
                return isOpen;
            }

            public void setIsOpen(Boolean value) {
                this.isOpen = ((Boolean ) value);
            }

            public Boolean getIsPremiumReport() {
                return isPremiumReport;
            }

            public void setIsPremiumReport(Boolean value) {
                this.isPremiumReport = ((Boolean ) value);
            }

            public Boolean getIsScheduled() {
                return isScheduled;
            }

            public void setIsScheduled(Boolean value) {
                this.isScheduled = ((Boolean ) value);
            }

            public CSAuditInformationPR.DisplayableAuditInfo.ActualInfo.PolicyTerm getPolicyTerm() {
                return policyTerm;
            }

            public void setPolicyTerm(CSAuditInformationPR.DisplayableAuditInfo.ActualInfo.PolicyTerm value) {
                this.policyTerm = ((CSAuditInformationPR.DisplayableAuditInfo.ActualInfo.PolicyTerm ) value);
            }

            public String getPublicID() {
                return publicID;
            }

            public void setPublicID(String value) {
                this.publicID = ((String ) value);
            }


            public static class PolicyTerm {

                protected String displayName;
                protected CSAuditInformationPR.DisplayableAuditInfo.ActualInfo.PolicyTerm.Policy policy;

                public Policy getPolicy() {
                    return policy;
                }

                public void setPolicy(Policy policy) {
                    this.policy = policy;
                }


                public String getDisplayName() {
                    return displayName;
                }

                public void setDisplayName(String value) {
                    this.displayName = ((String ) value);
                }
                
                public static class Policy {

                    protected CSAuditInformationPR.DisplayableAuditInfo.ActualInfo.PolicyTerm.Policy.LatestPeriod latestPeriod;
                    
                    public CSAuditInformationPR.DisplayableAuditInfo.ActualInfo.PolicyTerm.Policy.LatestPeriod getLatestPeriod() {
                        return latestPeriod;
                    }
                    
                    public void setLatestPeriod(CSAuditInformationPR.DisplayableAuditInfo.ActualInfo.PolicyTerm.Policy.LatestPeriod value) {
                        this.latestPeriod = ((CSAuditInformationPR.DisplayableAuditInfo.ActualInfo.PolicyTerm.Policy.LatestPeriod ) value);
                    }
                    
                    public static class LatestPeriod {
                        
                        protected String displayPolicyNumber;
                        
                        public String getDisplayPolicyNumber() {
                            return displayPolicyNumber;
                        }

                        public void setDisplayPolicyNumber(String value) {
                            this.displayPolicyNumber = value;
                        }
                    }
                }
            }
        }
    }
}
