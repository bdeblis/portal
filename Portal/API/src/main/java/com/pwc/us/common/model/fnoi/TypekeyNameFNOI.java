
package com.pwc.us.common.model.fnoi;

public class TypekeyNameFNOI {

    public String code;
    public String name;

    /**
     * Gets the value of the code property.
     */
    public String getCode() {
        return code;
    }

    /**
     * Sets the value of the code property.
     * 
     * @param value
     */
    public void setCode(String value) {
        this.code = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     */
    public void setName(String value) {
        this.name = value;
    }
    
       @Override
    public String toString() {
        return String.format("%s - %s", code, name);
    }


}
