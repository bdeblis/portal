package com.pwc.us.common.model;

/**
 *
 * @author Roger Turnau - <roger.turnau@us.pwc.com>
 */
public enum Gender {
    M("M", "Male"),
    F("F", "Female");
    
    private String abbrev;
    private String value;
    
    private Gender(String abbrev, String value) {
        this.abbrev = abbrev;
        this.value = value;
    }
    
    public String getAbbrev() {
        return this.abbrev;
    }
    
    public String getValue() {
        return this.value;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.getValue());
        return sb.toString();
    }
}
