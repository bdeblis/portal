package com.pwc.us.common.model.quoterequest;

import java.util.List;

/**
 *
 * @author Roger Turnau - <roger.turnau@us.pwc.com>
 */
public class PolicyLineQR {

    private PolicyLineQR.EntityWC7WorkersCompLine entityWC7WorkersCompLine;
    private PolicyLineQR.LineAnswers lineAnswers;

    public EntityWC7WorkersCompLine getEntityWC7WorkersCompLine() {
        return entityWC7WorkersCompLine;
    }

    public void setEntityWC7WorkersCompLine(EntityWC7WorkersCompLine entityWC7WorkersCompLine) {
        this.entityWC7WorkersCompLine = entityWC7WorkersCompLine;
    }

    public LineAnswers getLineAnswers() {
        return lineAnswers;
    }

    public void setLineAnswers(LineAnswers lineAnswers) {
        this.lineAnswers = lineAnswers;
    }
    

    public static class EntityWC7WorkersCompLine {

        private PolicyLineQR.EntityWC7WorkersCompLine.AdditionalInterests additionalInterests;
        private PolicyLineQR.EntityWC7WorkersCompLine.ExcludedOwnerOfficers excludedOwnerOfficers;
        private PolicyLineQR.EntityWC7WorkersCompLine.IncludedOwnerOfficers includedOwnerOfficers;

        public AdditionalInterests getAdditionalInterests() {
            return additionalInterests;
        }

        public void setAdditionalInterests(AdditionalInterests additionalInterests) {
            this.additionalInterests = additionalInterests;
        }

        public ExcludedOwnerOfficers getExcludedOwnerOfficers() {
            return excludedOwnerOfficers;
        }

        public void setExcludedOwnerOfficers(ExcludedOwnerOfficers excludedOwnerOfficers) {
            this.excludedOwnerOfficers = excludedOwnerOfficers;
        }

        public IncludedOwnerOfficers getIncludedOwnerOfficers() {
            return includedOwnerOfficers;
        }

        public void setIncludedOwnerOfficers(IncludedOwnerOfficers includedOwnerOfficers) {
            this.includedOwnerOfficers = includedOwnerOfficers;
        }

        public static class AdditionalInterests {
            protected List<WC7AddlInterestExtQR> entry;

            public List<WC7AddlInterestExtQR> getEntry() {
                return entry;
            }

            public void setEntry(List<WC7AddlInterestExtQR> entry) {
                this.entry = entry;
            }
        }
        
        public static class ExcludedOwnerOfficers {
            
            protected List<PolicyContactRoleQR> entry;

            public List<PolicyContactRoleQR> getEntry() {
                return entry;
            }

            public void setEntry(List<PolicyContactRoleQR> entry) {
                this.entry = entry;
            }
        }
        
        public static class IncludedOwnerOfficers {
            protected List<PolicyContactRoleQR> entry;

            public List<PolicyContactRoleQR> getEntry() {
                return entry;
            }

            public void setEntry(List<PolicyContactRoleQR> entry) {
                this.entry = entry;
            }
        }
    }
    
    public static class LineAnswers {
        protected List<PolicyLineAnswerQR> entry;

        public List<PolicyLineAnswerQR> getEntry() {
            return entry;
        }

        public void setEntry(List<PolicyLineAnswerQR> entry) {
            this.entry = entry;
        }
        
    }
}
