package com.pwc.us.common.model;

/**
 *
 * @author Roger Turnau - <roger.turnau@us.pwc.com>
 */
public enum AmPm {
    AM("AM"),
    PM("PM");
    
    private String value;
    
    private AmPm(String value) {
        this.value = value;
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
