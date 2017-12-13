package com.pwc.us.common.model.quoterequest;

import com.pwc.us.common.model.ContactBO;

/**
 *
 * @author Roger Turnau - <roger.turnau@us.pwc.com>
 */
public class PolicyContactRoleQR {
    protected PolicyContactRoleQR.EntityWC7PolicyOwnerOfficer entityWC7PolicyOwnerOfficer;
    protected PolicyContactRoleQR.AccountContactRole accountContactRole;
    protected String subtype;

    public EntityWC7PolicyOwnerOfficer getEntityWC7PolicyOwnerOfficer() {
        return entityWC7PolicyOwnerOfficer;
    }

    public void setEntityWC7PolicyOwnerOfficer(EntityWC7PolicyOwnerOfficer entityWC7PolicyOwnerOfficer) {
        this.entityWC7PolicyOwnerOfficer = entityWC7PolicyOwnerOfficer;
    }

    public AccountContactRole getAccountContactRole() {
        return accountContactRole;
    }

    public void setAccountContactRole(AccountContactRole accountContactRole) {
        this.accountContactRole = accountContactRole;
    }

    public String getSubtype() {
        return subtype;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }
    
    
    
    public static class AccountContactRole {
        
        protected PolicyContactRoleQR.AccountContactRole.AccountContact accountContact;
        protected String subtype;

        public AccountContact getAccountContact() {
            return accountContact;
        }

        public void setAccountContact(AccountContact accountContact) {
            this.accountContact = accountContact;
        }

        public String getSubtype() {
            return subtype;
        }

        public void setSubtype(String subtype) {
            this.subtype = subtype;
        }

        public static class AccountContact {
            
            protected ContactBO contact;

            public ContactBO getContact() {
                return contact;
            }

            public void setContact(ContactBO contact) {
                this.contact = contact;
            }

        }

    }


    public static class EntityWC7PolicyOwnerOfficer {
        
        protected Boolean activeInBusinessExt;
        protected String relationshipTitle;

        public Boolean getActiveInBusinessExt() {
            return activeInBusinessExt;
        }

        public void setActiveInBusinessExt(Boolean activeInBusinessExt) {
            this.activeInBusinessExt = activeInBusinessExt;
        }

        public String getRelationshipTitle() {
            return relationshipTitle;
        }

        public void setRelationshipTitle(String relationshipTitle) {
            this.relationshipTitle = relationshipTitle;
        }
    }
    
}
