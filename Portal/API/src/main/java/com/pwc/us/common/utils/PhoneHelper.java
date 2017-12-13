/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pwc.us.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author ISRK
 */
public class PhoneHelper {

    
    public static void main(String args[]){
        String ph1 = "4054442937";
        String ph2 = "405-444-2937";
        String ph3 = "405-444-2937 x23";
        String ph4 = "897-654-6546 x5466";
        
        System.out.println("phone1 '"+validatePhoneNumber(ph1)+"'");
        System.out.println("phone2 '"+validatePhoneNumber(ph2)+"'");
        System.out.println("phone3 '"+validatePhoneNumber(ph3)+"'");
        System.out.println("phone4 '"+validatePhoneNumber(ph4)+"'");
    }
    
    public static String validatePhoneNumber(String phoneNo) {
        String PHONE_REGEX = "^[2-9]\\d{2}-[2-9]\\d{2}-\\d{4}$";
        Pattern p = Pattern.compile(PHONE_REGEX);
        if (phoneNo == null || phoneNo.isEmpty()) {
            return "";
        }
        Matcher m = p.matcher(phoneNo.trim());
        if (!m.matches()) {
            if (phoneNo.length() < 12) {
                return "";
            }
            String ph = phoneNo.substring(0, 12);
            m = p.matcher(ph);
            if (!m.matches()) {
                return "";
            } else {
                return ph;
            }
        }
        return phoneNo;
    }

}
