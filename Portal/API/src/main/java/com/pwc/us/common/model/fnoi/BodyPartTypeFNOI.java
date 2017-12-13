
package com.pwc.us.common.model.fnoi;


/**
 * <p>Java class for BodyPartType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="BodyPartType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="head"/>
 *     &lt;enumeration value="neck"/>
 *     &lt;enumeration value="upper"/>
 *     &lt;enumeration value="trunk"/>
 *     &lt;enumeration value="lower"/>
 *     &lt;enumeration value="multiple"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
public enum BodyPartTypeFNOI {

    HEAD("head"),
    NECK("neck"),
    UPPER("upper"),
    TRUNK("trunk"),
    LOWER("lower"),
    MULTIPLE("multiple");
    private final String value;

    BodyPartTypeFNOI(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static BodyPartTypeFNOI fromValue(String v) {
        for (BodyPartTypeFNOI c: BodyPartTypeFNOI.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
