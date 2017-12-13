/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pwc.us.common.utils;

import java.lang.reflect.*;
import java.util.*;

/**
 *
 * @author Robert snelling
 */
public class NullChecker
{
    static public boolean isNullOrEmpty(String str)
    {
        return str == null || str.isEmpty();
    }

    static public boolean isNotNullOrEmpty(String str)
    {
        return !isNullOrEmpty(str);
    }
    
    static public boolean isNullOrEmpty(Collection col)
    {
        return col == null || col.isEmpty();
    }
    
    static public boolean isNotNullOrEmpty(Collection col)
    {
        return !isNullOrEmpty(col);
    }

    static public boolean isNullOrEmpty(Object[] ar)
    {
        return ar == null || ar.length == 0;
    }

    static public boolean isNotNullOrEmpty(Object[] ar)
    {
        return !isNullOrEmpty(ar);
    }
    
    static public boolean isNullOrEmpty(Object obj)
    {
        return obj == null
               || (obj instanceof String && ((String)obj).length() == 0)
               || (obj instanceof Collection && ((Collection)obj).isEmpty())
               || (obj.getClass().isArray() && Array.getLength(obj) == 0)
               || (obj.toString().isEmpty());
    }
    
    static public boolean isNotNullOrEmpty(Object obj)
    {
        return !isNullOrEmpty(obj);
    }

    static public Boolean isNullThenFalse(Boolean b)
    {
        if (isNullOrEmpty(b)) {
            b = false;
        };
        return b;
    }
    
    static public String blankIfNull(String ii)
    {
        if(ii == null)
            return "";
        else
            return ii;
    }
    
    static public Integer zeroIfNull(Integer i)
    {
        if(i == null)
            return 0;
        else
            return i;
    }

}
