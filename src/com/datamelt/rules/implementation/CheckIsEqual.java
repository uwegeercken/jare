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
 * checks if a string of characters is equal to antoher
 * string of characters
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
     * checks two long values for equality 
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
     * checks if a string of characters is equal to antoher
     * string of characters and ignores or does not ignore
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
     * checks if a string of characters is equal to antoher
     * string of characters
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
     * checks, if the first date is equal to the second
     * date.
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
     * checks, if the first date is equal to the second
     * date.
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
	        
	        result = cal1.equals(cal2);
        }
        catch(Exception ex)
        {
        	
        }
        return result;
    
    }
}
