package com.datamelt.rules.implementation;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Checks if one value is smaller or equal compared to the other value. In case a string value is used, it checks, if the length of the
 * string if smaller or equal to the given length.
 * <p>
 * The first parameter of a given method is always the value of the field that shall be checked. The second parameter is either another field to check against 
 * or an expected value (fixed value) to check against the first value.
 * 
 * @author uwe geercken
 */
public class CheckIsSmallerOrEqual extends GenericCheck
{
	/**
     * Evaluates if the string length is smaller or equal to the given length.
     * 
     * @param value		the value to compare
     * @param length	the length to compare against
     * @return			indication if the length of the value is smaller or equal
     */
    public static boolean evaluate(String value, int length)
    {
        if(value!=null)
        {
	    	if(value.length()<= length)
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
        	return false;
        }
    }
    
    /**
     * Evaluates if the string length is smaller or equal to the given length.
     * 
     * @param value		the value to compare
     * @param length	the length to compare against
     * @return			indication if the length of the value is smaller or equal
     */
    public static boolean evaluate(String value, long length)
    {
        if(value!=null)
        {
	    	if(value.length()<= length)
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
        	return false;
        }
    }
    
    /**
     * Evaluates if the first value is smaller or equal to the second value.
     * 
     * @param value1	the first value for comparison
     * @param value2	the second value for comparison
     * @return			indication if the first value is smaller or equal to the second value
     */
    public static boolean evaluate(long value1, long value2)
    {
        if(value1 <= value2)
        {
            return true; 
        }
        else
        {
            return false;
        }
    }
    
    /**
     * Evaluates if the first value is smaller or equal to the second value.
     * 
     * @param value1	the first value for comparison
     * @param value2	the second value for comparison
     * @return			indication if the first value is smaller or equal to the second value
     */
    public static boolean evaluate(int value1, int value2)
    {
        if(value1 <= value2)
        {
            return true; 
        }
        else
        {
            return false;
        }
    }
    
    /**
     * Evaluates if the first value is smaller or equal to the second value.
     * 
     * @param value1	the first value for comparison
     * @param value2	the second value for comparison
     * @return			indication if the first value is smaller or equal to the second value
     */
    public static boolean evaluate(double value1, double value2)
    {
        if(value1 <= value2)
        {
            return true; 
        }
        else
        {
            return false;
        }
    }
    
    /**
     * Evaluates if the first value is smaller or equal to the second value.
     * 
     * @param value1	the first value for comparison
     * @param value2	the second value for comparison
     * @return			indication if the first value is smaller or equal to the second value
     */
    public static boolean evaluate(double value1, long value2)
    {
        if(value1 <= value2)
        {
            return true; 
        }
        else
        {
            return false;
        }
    }
    
    /**
     * Evaluates if the first value is smaller or equal to the second value.
     * 
     * @param value1	the first value for comparison
     * @param value2	the second value for comparison
     * @return			indication if the first value is smaller or equal to the second value
     */
    public static boolean evaluate(double value1, float value2)
    {
        if(value1 <= value2)
        {
            return true; 
        }
        else
        {
            return false;
        }
    }
    
    /**
     * Evaluates if the first value is smaller or equal to the second value.
     * 
     * @param value1	the first value for comparison
     * @param value2	the second value for comparison
     * @return			indication if the first value is smaller or equal to the second value
     */
    public static boolean evaluate(double value1, int value2)
    {
        if(value1 <= value2)
        {
            return true; 
        }
        else
        {
            return false;
        }
    }
    
    /**
     * Evaluates if the first value is smaller or equal to the second value.
     * 
     * @param value1	the first value for comparison
     * @param value2	the second value for comparison
     * @return			indication if the first value is smaller or equal to the second value
     */
    public static boolean evaluate(float value1, int value2)
    {
        if(value1 <= value2)
        {
            return true; 
        }
        else
        {
            return false;
        }
    }
    
    /**
     * Evaluates if the first value is smaller or equal to the second value.
     * 
     * @param value1	the first value for comparison
     * @param value2	the second value for comparison
     * @return			indication if the first value is smaller or equal to the second value
     */
    public static boolean evaluate(float value1, long value2)
    {
        if(value1 <= value2)
        {
            return true; 
        }
        else
        {
            return false;
        }
    }
    
    /**
     * Evaluates if the first value is smaller or equal to the second value.
     * 
     * @param value1	the first value for comparison
     * @param value2	the second value for comparison
     * @return			indication if the first value is smaller or equal to the second value
     */
    public static boolean evaluate(float value1, double value2)
    {
        if(value1 <= value2)
        {
            return true; 
        }
        else
        {
            return false;
        }
    }
    
    /**
     * Evaluates if the first value is smaller or equal to the second value.
     * 
     * @param value1	the first value for comparison
     * @param value2	the second value for comparison
     * @return			indication if the first value is smaller or equal to the second value
     */
    public static boolean evaluate(long value1, int value2)
    {
        if(value1 <= value2)
        {
            return true; 
        }
        else
        {
            return false;
        }
    }
    
    /**
     * Evaluates if the first value is smaller or equal to the second value.
     * 
     * @param value1	the first value for comparison
     * @param value2	the second value for comparison
     * @return			indication if the first value is smaller or equal to the second value
     */
    public static boolean evaluate(float value1, float value2)
    {
        if(value1 <= value2)
        {
            return true; 
        }
        else
        {
            return false;
        }
    }

    /**
     * Evaluates if the first date is smaller or equal to the second date - both provided as string values- using the specified date format.
     * 
     * @param value1	the first date value for comparison
     * @param value2	the second date value for comparison
     * @param format	the format to be used for the comparison according to the SimpleDateFormat class
     * @return			indication if the first value is smaller than the second value
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
	        
	        result = cal1.before(cal2) || cal1.equals(cal2);
        }
        catch(Exception ex)
        {
        	
        }
        return result;
    }
    
    /**
     * Evaluates if the first date is smaller or equal to the second date - provided as a string value - using the specified date format.
     * 
     * @param date1		the first date value for comparison
     * @param value2	the second date value for comparison
     * @param format	the format to be used for the comparison according to the SimpleDateFormat class
     * @return			indication if the first value is smaller than the second value
     */
    public static boolean evaluate(Date date1, String value2, String format)
    {
        
        SimpleDateFormat sdf = new SimpleDateFormat(format);

        boolean result = false;
        
        try
        {
	        Date date2 = sdf.parse(value2);
	        
	        Calendar cal1 = Calendar.getInstance();
	        cal1.setTime(date1);
	        
	        Calendar cal2 = Calendar.getInstance();
	        cal2.setTime(date2);
	        
	        result = cal1.before(cal2) || cal1.equals(cal2);
        }
        catch(Exception ex)
        {
        	
        }
        return result;
    
    }
    
    /**
     * Evaluates if the first date is smaller or equal to the second date - provided as a string value - using the default date format.
     * 
     * @param date1		the first date value for comparison
     * @param value		the second date value for comparison
     * @return			indication if the first value is smaller than the second value
     */
    public static boolean evaluate(Date date1, String value)
    {
        
        SimpleDateFormat sdf = new SimpleDateFormat(CheckConstants.DEFAULT_DATE_FORMAT);

        boolean result = false;
        
        try
        {
	        Date date2 = sdf.parse(value);
	        
	        Calendar cal1 = Calendar.getInstance();
	        cal1.setTime(date1);
	        
	        Calendar cal2 = Calendar.getInstance();
	        cal2.setTime(date2);
	        
	        result = cal1.before(cal2) || cal1.equals(cal2);
        }
        catch(Exception ex)
        {
        	
        }
        return result;
    
    }
    
    /**
     * Evaluates if the first date is smaller than the second date using the default date format.
     * 
     * @param date1		the first date value for comparison
     * @param date2		the second date value for comparison
     * @return			indication if the first value is smaller than the second value
     */
    public static boolean evaluate(Date date1, Date date2)
    {
    	if(date1!=null && date2!=null)
        {
    		return date1.equals(date2) || date1.before(date2);
        }
    	else
    	{
    		return false;
    	}
    }
}
