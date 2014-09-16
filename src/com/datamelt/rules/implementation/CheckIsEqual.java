/*
 * Created on 15.09.2006
 *
 * all code by uwe geercken
 */
package com.datamelt.rules.implementation;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * <p>Checks if a string is equal to another string, if two numbers are equal or if two dates are equal.</p>
 * 
 * <p>
 * The first argument of a method is always the value of the field that one wants to check. The second argument is either another field 
 * or an expected (fixed) value to check against the first value.
 * </p>
 * <p>
 * Some methods may have additional arguments that can be passed to it.
 * </p>
 * 
 * @author uwe geercken
 */
public class CheckIsEqual extends GenericCheck
{
    /**
     * checks two long values for equality 
     */
    public static boolean evaluate(long value1, long value2)
    {
        if(value1 == value2)
        {
            return true; 
        }
        else
        {
            return false;
        }
    }
    
    /**
     * checks a long and integer value for equality 
     */
    public static boolean evaluate(long value1, int value2)
    {
        if(value1 == value2)
        {
            return true; 
        }
        else
        {
            return false;
        }
    }
    
    /**
     * checks a long and integer value for equality 
     */
    public static boolean evaluate(int value1, long value2)
    {
        if(value1 == value2)
        {
            return true; 
        }
        else
        {
            return false;
        }
    }
    
    /**
     * checks two double values for equality 
     */
    public static boolean evaluate(double value1, double value2)
    {
        if(value1 == value2)
        {
            return true; 
        }
        else
        {
            return false;
        }
    }
    
    /**
     * checks two float values for equality 
     */
    public static boolean evaluate(float value1, float value2)
    {
        if(value1 == value2)
        {
            return true; 
        }
        else
        {
            return false;
        }
    }
    
    /**
     * checks two integer values for equality 
     */
    public static boolean evaluate(int value1, int value2)
    {
        if(value1 == value2)
        {
            return true; 
        }
        else
        {
            return false;
        }
    }
    
    /**
     * checks two boolean values for equality 
     */
    public static boolean evaluate(boolean value1, boolean value2)
    {
        return value1==value2; 
    }
    
    /**
     * checks if a string is equal to another string and ignores or does not ignore
     * the case of the values
     */
    public static boolean evaluate(String value1, String value2, boolean ignoreCase)
    {
    	if(value1!=null && value2 !=null)
    	{
	        if(ignoreCase)
	        {
		        if(value1.toLowerCase().equals(value2.toLowerCase()))
		        {
		            return true; 
		        }
		        else
		        {
		            return false;
		        }
	        }
	        else
	        {
	            if(value1.equals(value2))
	            {
	                return true; 
	            }
	            else
	            {
	                return false;
	            }
	
	        }
    	}
    	else
    	{
    		return false;
    	}
    }
    
    /**
     * checks if a string is equal to another string
     */
    public static boolean evaluate(String value1, String value2)
    {
    	if(value1!=null && value2 !=null)
    	{
    		return evaluate(value1,value2,false);
    	}
    	else
    	{
    		return false;
    	}
    }
    
    /**
     * checks, if the first date string is equal to the second
     * date string using the given date format
     */
    public static boolean evaluate(String value1, String value2, String format)
    {
        
        SimpleDateFormat sdf = new SimpleDateFormat(format);

        boolean result = false;
        
        try
        {
	       	Date date1 = sdf.parse(value1);
	        Date date2 = sdf.parse(value2);
	        
	        Calendar cal1 = Calendar.getInstance();
	        cal1.setTime(date1);
	        
	        Calendar cal2 = Calendar.getInstance();
	        cal2.setTime(date2);
	        
	        result = cal1.equals(cal2);
        }
        catch(Exception ex)
        {
        	
        }
        return result;
    
    }
    
    /**
     * checks, if the date is equal to the date string using the given
     * date format.
     */
    public static boolean evaluate(Date date1, String value2, String format)
    {
        
        SimpleDateFormat sdf = new SimpleDateFormat(format);

        boolean result = false;
        
        try
        {
	        Date date2 = sdf.parse(value2);
	        
	        String date1String = sdf.format(date1);
	        String date2String = sdf.format(date2);
	        
	        result = date1String.equals(date2String);
        }
        catch(Exception ex)
        {
        	
        }
        return result;
    
    }
    
    /**
     * checks, if the date is equal to the date string using the default
     * date format.
     */
    public static boolean evaluate(Date date1, String value2)
    {
        
        SimpleDateFormat sdf = new SimpleDateFormat(CheckConstants.DEFAULT_DATE_FORMAT);
        
        boolean result = false;
        
        try
        {
	        Date date2 = sdf.parse(value2);
	        
	        String date1String = sdf.format(date1);
	        String date2String = sdf.format(date2);
	        
	        result = date1String.equals(date2String);
        }
        catch(Exception ex)
        {
        	
        }
        return result;
    
    }
    
    /**
     * checks, if the date is equal to the other date using the default
     * date format.
     */
    public static boolean evaluate(Date date1, Date date2)
    {
        
    	SimpleDateFormat sdf = new SimpleDateFormat(CheckConstants.DEFAULT_DATE_FORMAT);
    	
    	String date1String = sdf.format(date1);
    	String date2String = sdf.format(date2);
    	
        return date1String.equals(date2String);
    
    }
}
