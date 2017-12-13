/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pwc.us.wsclient.fnoi;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author isrk
 */
public class GetContactInfoFromXml {
    
    public static void main(String args[]){
        try {
            
          String t =  GetContactInfoFromXml.readFile("\\downloads\\fnoixmltest.xml");
            System.out.println(GetContactInfoFromXml.parseForContact(t));
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(FNOIClientInt.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static String readFile(String fileName) throws IOException {
    BufferedReader br = new BufferedReader(new FileReader(fileName));
    try {
        StringBuilder sb = new StringBuilder();
        String line = br.readLine();

        while (line != null) {
            sb.append(line);
            sb.append("\n");
            line = br.readLine();
        }
        return sb.toString();
    } finally {
        br.close();
    }
}
    
    public static String parseForContact(String fnoiXML){
        StringBuffer results = new StringBuffer();

        String data[] = {"","","","","",""};
        
        String lines[] = fnoiXML.split("\\r?\\n|\\r");
        Pattern[] patterns = new Pattern[6];
        patterns[0] = Pattern.compile("<FirstName>(.+?)</FirstName>");
        patterns[1] = Pattern.compile("<LastName>(.+?)</LastName>");
        patterns[2] = Pattern.compile("<WorkPhone>(.+?)</WorkPhone>");
        patterns[3] = Pattern.compile("<EmailAddress1>(.+?)</EmailAddress1>");
        patterns[4] = Pattern.compile("<ns4:Role>(.+?)</ns4:Role>");
        patterns[5] = Pattern.compile("<ns12:PolicyNumber>(.+?)</ns12:PolicyNumber>");
        Matcher[] matches = new Matcher[6];
        
        
        for(String s : lines){
            /*
            matches[5] = patterns[5].matcher(s);
            if(matches[5].groupCount() > 0)
                System.out.println(matches[5].group(0));
            if(matches[5].find() && matches[5].group(1).equalsIgnoreCase("reporter") ){
                break;
            }
                    */
            for(int i = 0;i <= 5;i++){
                matches[i] = patterns[i].matcher(s);
                if(matches[i].find()){
                    data[i] = matches[i].group(1);
                }
            }
            
        }
        
        return " Name=\""+data[0]+" "+data[1]+"\"+ Phone="+data[2]+" Email="+data[3]+" policy number="+data[5];
    }
    
}
