/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pwc.us.common.model.fnoi;

/**
 *
 * @author Colin Kirk
 * Class representation of this chunk of the schema representing the Topic complex type within a note
            <xsd:element name="Topic" minOccurs="0" nillable="true">
                <xsd:complexType>
                    <xsd:sequence>
                    <xsd:element name="Description" minOccurs="0" nillable="true" type="xsd:string" gw:type="java.lang.String"/>
                    <xsd:element name="DisplayName" minOccurs="0" nillable="true" type="xsd:string" gw:type="java.lang.String"/>
                    </xsd:sequence>
                </xsd:complexType>
            </xsd:element>        
 * 
 * 
 */
public class TopicFNOI {
    

    protected String description;
    protected String displayName;


    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;            
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;   
     }
}



