/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pwc.us.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 *
 * @author rsnelling037
 */
public class DateTimeHelper {
    
    static public XMLGregorianCalendar dateToXMLGregorianCalendar(Date d)
            throws ParseException,
            DatatypeConfigurationException {

        return dateTimeToXMLGregorianCalendar(d, null);
    }

    static public XMLGregorianCalendar dateTimeToXMLGregorianCalendar(Date d, Date t)
            throws ParseException,
            DatatypeConfigurationException {
        XMLGregorianCalendar result = null;
        GregorianCalendar gregorianCalendar;
                
        if (!(NullChecker.isNullOrEmpty(t))) {
            d.setHours(t.getHours());
            d.setMinutes(t.getMinutes());
        }
        gregorianCalendar =
                (GregorianCalendar) GregorianCalendar.getInstance();
        gregorianCalendar.setTime(d);
        result = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar);

        return result;
    }
    
    /*
     * Converts XMLGregorianCalendar to java.util.Date in Java
     */
   static public Date xmlGregorianCalendarToDate(XMLGregorianCalendar calendar){
        if(calendar == null) {
            return null;
        }
        return calendar.toGregorianCalendar().getTime();
    }
   
   /*
   * Converts date strings from MM-DD-YYY to YYYY-MM-DD (required by GW services)
   */
   static public String flipDateStringFormat(String date) throws ParseException {
       final String DATE_REGEX = "^(\\d{1,2})[\\/-](\\d{1,2})[\\/-](\\d{4})$";
       Pattern p = Pattern.compile(DATE_REGEX);
       Matcher m = p.matcher(date);
       if (!m.matches()) {
           throw new ParseException("Date is not in expected format.", 0);
       }
       String year = m.group(3);
       String month = m.group(1);
       String day = m.group(2);
       String outDate = year + "-" + month + "-" + day;
       return outDate;
   }
   
   static public boolean isThisDateValid(String dateToValidate, String dateFromat){

		if(dateToValidate == null){
			return false;
		}

		SimpleDateFormat sdf = new SimpleDateFormat(dateFromat);
		sdf.setLenient(false);

		try {

			//if not valid, it will throw ParseException
			Date date = sdf.parse(dateToValidate);
			System.out.println(date);

		} catch (ParseException e) {

			e.printStackTrace();
			return false;
		}

		return true;
	}
    
}
