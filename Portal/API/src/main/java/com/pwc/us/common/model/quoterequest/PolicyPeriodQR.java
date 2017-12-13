package com.pwc.us.common.model.quoterequest;

import java.util.List;

/**
 *
 * @author Roger Turnau - <roger.turnau@us.pwc.com>
 */
public class PolicyPeriodQR {

    protected PolicyPeriodQR.PeriodAnswers periodAnswers;
    protected PolicyPeriodQR.Policy policy;
    protected PolicyPeriodQR.PolicyLocations policyLocations;
    protected PolicyPeriodQR.PrimaryNamedInsured primaryNamedInsured;
    protected PolicyLineQR wc7Line;
    //default this to true for obvious reasons
    protected boolean IsNewFromPortal = true; 
    
    public boolean getIsNewFromPortal() {
        return IsNewFromPortal;
    }

    public void setIsNewFromPortal(boolean IsNewFromPortal) {
        this.IsNewFromPortal = IsNewFromPortal;
    }

    public PeriodAnswers getPeriodAnswers() {
        return periodAnswers;
    }

    public void setPeriodAnswers(PeriodAnswers periodAnswers) {
        this.periodAnswers = periodAnswers;
    }

    public Policy getPolicy() {
        return policy;
    }

    public void setPolicy(Policy policy) {
        this.policy = policy;
    }

    public PolicyLocations getPolicyLocations() {
        return policyLocations;
    }

    public void setPolicyLocations(PolicyLocations policyLocations) {
        this.policyLocations = policyLocations;
    }

    public PrimaryNamedInsured getPrimaryNamedInsured() {
        return primaryNamedInsured;
    }

    public void setPrimaryNamedInsured(PrimaryNamedInsured primaryNamedInsured) {
        this.primaryNamedInsured = primaryNamedInsured;
    }

    public PolicyLineQR getWc7Line() {
        return wc7Line;
    }

    public void setWc7Line(PolicyLineQR wc7Line) {
        this.wc7Line = wc7Line;
    }
    
    

    public static class PeriodAnswers {

        protected List<AnswerQR> entry;

        public List<AnswerQR> getEntry() {
            return entry;
        }

        public void setEntry(List<AnswerQR> entry) {
            this.entry = entry;
        }
        
    }

    public static class Policy {

        protected PolicyPeriodQR.Policy.PriorPolicies priorPolicies;

        public PriorPolicies getPriorPolicies() {
            return priorPolicies;
        }

        public void setPriorPolicies(PriorPolicies priorPolicies) {
            this.priorPolicies = priorPolicies;
        }

        
        public static class PriorPolicies {

            protected List<PriorPolicyQR> entry;

            public List<PriorPolicyQR> getEntry() {
                return entry;
            }

            public void setEntry(List<PriorPolicyQR> entry) {
                this.entry = entry;
            }
            
            
        }
    }

    public static class PolicyLocations {

        protected List<PolicyLocationQR> entry;

        public List<PolicyLocationQR> getEntry() {
            return entry;
        }

        public void setEntry(List<PolicyLocationQR> entry) {
            this.entry = entry;
        }
        
    }

    public static class PrimaryNamedInsured {

        protected String orgTypeExt;

        public String getOrgTypeExt() {
            return orgTypeExt;
        }

        public void setOrgTypeExt(String orgTypeExt) {
            this.orgTypeExt = orgTypeExt;
        }
        
    }
}
