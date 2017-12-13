
package com.pwc.us.common.model.payrollreport;

import java.util.HashMap;
import java.util.Map;
import javax.xml.namespace.QName;



public class TransactionIdPR {

    protected String value;
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();
    
    public String getValue() {
        return value;
    }

    
    public void setValue(String value) {
        this.value = value;
    }

    
    public Map<QName, String> getOtherAttributes() {
        return otherAttributes;
    }

}
