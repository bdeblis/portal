package com.pwc.us.common.model.fnoi;

/**
 *
 * @author Roger Turnau - <roger.turnau@us.pwc.com>
 */
public enum InjuryTypeEnum {
    cumulative("Cumulative Trauma"),
    multiple("Multiple Injury"),
    occupational("Occupational Injury"),
    specific("Specific Injury");
    
    private String value;
    
    private InjuryTypeEnum(String value) {
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
