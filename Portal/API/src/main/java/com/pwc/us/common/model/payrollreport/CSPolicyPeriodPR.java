
package com.pwc.us.common.model.payrollreport;

public class CSPolicyPeriodPR {

    protected CSPolicyPeriodPR.PolicyPeriod policyPeriod;
    protected String EmployerAddressLine1;
    protected String EmployerCity;
    protected String EmpName;
    protected String EmpPhone;
    protected String EmployerState;
    protected String EmployerZip;

    public String getEmployerState() {
        return EmployerState;
    }

    public void setEmployerState(String EmployerState) {
        this.EmployerState = EmployerState;
    }

    public String getEmployerZip() {
        return EmployerZip;
    }

    public void setEmployerZip(String EmployerZip) {
        this.EmployerZip = EmployerZip;
    }

    public String getEmpPhone() {
        return EmpPhone;
    }

    public void setEmpPhone(String EmpPhone) {
        this.EmpPhone = EmpPhone;
    }
    
    
    
    public String getEmpName() {
        return EmpName;
    }

    public void setEmpName(String EmpName) {
        this.EmpName = EmpName;
    }
    
    

    public String getEmployerAddressLine1() {
        return EmployerAddressLine1;
    }

    public void setEmployerAddressLine1(String EmployerAddressLine1) {
        this.EmployerAddressLine1 = EmployerAddressLine1;
    }

    public String getEmployerCity() {
        return EmployerCity;
    }

    public void setEmployerCity(String EmployerCity) {
        this.EmployerCity = EmployerCity;
    }
    
    
    
    public CSPolicyPeriodPR.PolicyPeriod getPolicyPeriod() {
        return policyPeriod;
    }
    
    public void setPolicyPeriod(CSPolicyPeriodPR.PolicyPeriod value) {
        this.policyPeriod = ((CSPolicyPeriodPR.PolicyPeriod ) value);
    }

    public static class PolicyPeriod {

        protected CSPolicyPeriodPR.PolicyPeriod.Policy policy;
        protected String policyNumber;

        public CSPolicyPeriodPR.PolicyPeriod.Policy getPolicy() {
            return policy;
        }
        
        public void setPolicy(CSPolicyPeriodPR.PolicyPeriod.Policy value) {
            this.policy = ((CSPolicyPeriodPR.PolicyPeriod.Policy ) value);
        }
        
        public String getPolicyNumber() {
            return policyNumber;
        }
        
        public void setPolicyNumber(String value) {
            this.policyNumber = ((String ) value);
        }

        public static class Policy {

            protected CSPolicyPeriodPR.PolicyPeriod.Policy.Account account;
            
            public CSPolicyPeriodPR.PolicyPeriod.Policy.Account getAccount() {
                return account;
            }

            
            public void setAccount(CSPolicyPeriodPR.PolicyPeriod.Policy.Account value) {
                this.account = ((CSPolicyPeriodPR.PolicyPeriod.Policy.Account ) value);
            }


            
            public static class Account {

                protected CSPolicyPeriodPR.PolicyPeriod.Policy.Account.AccountStatus accountStatus;

                
                public CSPolicyPeriodPR.PolicyPeriod.Policy.Account.AccountStatus getAccountStatus() {
                    return accountStatus;
                }
                
                public void setAccountStatus(CSPolicyPeriodPR.PolicyPeriod.Policy.Account.AccountStatus value) {
                    this.accountStatus = ((CSPolicyPeriodPR.PolicyPeriod.Policy.Account.AccountStatus ) value);
                }


                
                public static class AccountStatus {

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
