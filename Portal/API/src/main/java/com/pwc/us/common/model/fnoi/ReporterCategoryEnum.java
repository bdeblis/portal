package com.pwc.us.common.model.fnoi;

import com.pwc.us.common.model.*;

/**
 *
 * @author Roger Turnau - <roger.turnau@us.pwc.com>
 */
public enum ReporterCategoryEnum {
    self("Owner"),
    employee("Company Representative"),
    agent("Agent"),
    claimant("Injured Worker");
    
    private String value;
    
    private ReporterCategoryEnum(String value) {
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
