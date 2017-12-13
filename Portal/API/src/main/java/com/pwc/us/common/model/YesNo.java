package com.pwc.us.common.model;

/**
 *
 * @author Roger Turnau - <roger.turnau@us.pwc.com>
 */
public enum YesNo {
    Y("Yes"),
    N("No");
    
    private String value;
    
    private YesNo(String value) {
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
