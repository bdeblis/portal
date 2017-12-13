/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pwc.us.common.model.fnoi;

import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Colin Kirk
 * Class representation of this chunk of the schema
<xsd:schema xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:gw="http://guidewire.com/xsd" xmlns="http://guidewire.com/cc/gx/compsource.cc.api.fnoi.gx.note.notemodel" targetNamespace="http://guidewire.com/cc/gx/compsource.cc.api.fnoi.gx.note.notemodel" elementFormDefault="qualified">
<xsd:element name="Note" type="Note" nillable="true"/>
    <xsd:complexType name="Note">
        <xsd:sequence>
            <xsd:element name="Body" minOccurs="0" nillable="true" type="xsd:string" gw:type="java.lang.String"/>
            <xsd:element name="Subject" minOccurs="0" nillable="true" type="xsd:string" gw:type="java.lang.String"/>
            <xsd:element name="Topic" minOccurs="0" nillable="true">
                <xsd:complexType>
                    <xsd:sequence>
                    <xsd:element name="Description" minOccurs="0" nillable="true" type="xsd:string" gw:type="java.lang.String"/>
                    <xsd:element name="DisplayName" minOccurs="0" nillable="true" type="xsd:string" gw:type="java.lang.String"/>
                    </xsd:sequence>
                </xsd:complexType>
            </xsd:element>
        </xsd:sequence>
    </xsd:complexType>
</xsd:schema>
 * 
 * 
 */
public class NoteFNOI {
    
    protected String body;
    protected String subject;
    protected NoteFNOI.Topic topic;
    
    /**
     * returns the value of the comment body 
     */
    public String getBody() {
        return this.body;
    }
    
     /**
     * returns the value of the comment subject 
     */
    public String getSubject() {
        return this.subject;
    }
    
     /**
     * returns the note topic object,
     * instantiates new one if null
     */
    public NoteFNOI.Topic getTopic() {
        if(this.topic == null)
            return new NoteFNOI.Topic();
        return this.topic;                
    }
    
     /**
     * sets the value of the note topic object
     */
    public void setTopic(NoteFNOI.Topic topic) {        
        this.topic = topic;
    }
    
     /**
     * sets the value of the comment body 
     */
    public void setBody(String body) {
        this.body = body;
    }
    
    /**
     * returns the value of the comment subject 
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }    
    
     /**
     * Static class representing the Topic of the Note
     */
    public static class Topic {
             

        protected String description;
        protected String displayName;            


      /**
       * returns the value of the topic description
       * should be the string representation of the GW typekey value
       */
       public String getDescription() {
           return this.description;
       }

      /**
       * sets the value of the topic description
       * should be the string representation of the GW typekey value
       */
       public void setDescription(String desc) {
           this.description = desc;            
       }

      /**
       * returns the value of the topic displayName
       * should be the string representation of the GW typekey value
       */
       public String getDisplayName() {
           return this.displayName;
       }

      /**
       * sets the value of the topic displayName
       * should be the string representation of the GW typekey value
       */
       public void setDisplayName(String dn) {
           this.displayName = dn;   
        }
    }
}
