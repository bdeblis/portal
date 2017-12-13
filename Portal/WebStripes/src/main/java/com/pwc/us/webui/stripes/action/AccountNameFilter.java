/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pwc.us.webui.stripes.action;

import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author isrk
 */
public class AccountNameFilter {
    /*
    
     letters, numbers, commas (,), periods (.), hypens (-), ampersands (&) and quotes (')  
    
     */

    static String allowed = "[^a-zA-Z0-9,.\\&'-]+";
    static String test[] = {"BILL & RUTHS SUB SHOP #3 - 01542044",
        "Blaine County Chem=Cans Acct. 02142113",
        "C3:23 Welding & Fabrication LLC - 02713137",
        "CABLE CONNECTIONS 24/7 LLC - 02584566",
        "Cable Connections 24/7 LLC Acct. 02584566",
        "Circle J/A Racing LLC Acct. 02586751",
        "Elk City Outdoor LLC/Outcast Oilfield Services LLC Acct. 02489632",
        "J & M BRICK/ROCK MASONRY LLC - 02631602",
        "Phillips Drywall/acoustical Acct. 01945362",
        "Prestige Builders Commercial/Residential LLC Acct. 02627944",
        "Randy's Professional Painting, LLC/Touch of Class Painting, LLC Acct. 02634649",
        "Sooner Waterproofing@hotmail.com Acct. 02212476",
        "TJ Services/Grime Busters Cleaning Acct. 02757483",
        "Top roof/Lawn boss Acct. 02534721",
        "TWENTY/TWENTY OIL & GAS INC - 00332161",
        "Twenty/Twenty Oil & Gas Inc Acct. 00332161",
        "ups store #3683 Acct. 02238793",
        "Warrior Contracting, LLC (Formerly Rich Concrete) Acct. 02541697",
    ".,<>'&-02730574",
    ".,&lt;&gt;-'&02730574"};

    public static void main(String[] args) {
        
       String test1 = StringUtils.replacePattern("abcd^lhmil#", allowed,"");
       System.out.println(test1.length());
       System.out.println("abcd^lhmil#".length());
if(test1.length() != "abcd^lhmil#".length())
    System.out.println("bad");
else
    System.out.println("good");
       
        for (int i = 0; i < test.length; i++) {
            String t = test[i];
            System.out.println(i+" before "+t+" after "+filterAccountName(t));
        }
    }
    
    public static String removeSpecial(String name){
        if(name == null)
            return "";
        return name.replaceAll(allowed,"");
    }
    
    public static String filterAccountName(String name){
        if(name == null)
            return "";
        String n = name.replace("&lt;","");
        n = n.replace("&gt;","");
        return n.replaceAll(allowed," ");
    }

}
