/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.datamelt.rules.implementation;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Checks if a given string is not equal to another string, if two numbers are not equal or if two dates are not equal.
 * <p>
 * The default format for comparing dates is yyyy-MM-dd.
 * <p>
 * The first parameter of a given method is always the value of the field that shall be checked. The second parameter is either another field to check against 
 * or an expected value (fixed value) to check against the first value.
 * 
 * @author uwe geercken
 */
public class CheckIsNotEqual extends GenericCheck
{
	/**
     * Evaluates if two integer values are not equal
     * 
     * @param value1	first value for comparison
     * @param value2	second value for comparison
     * @return			indication if the two values are not equal
     */
	public static boolean evaluate(int value1, int value2)
    {
        if(value1 != value2)
        {
            return true; 
        }
        else
        {
            return false;
        }
    }
    
	/**
     * Evaluates if two long values are not equal
     * 
     * @param value1	first value for comparison
     * @param value2	second value for comparison
     * @return			indication if the two values are not equal
     */
    public static boolean evaluate(long value1, long value2)
    {
        if(value1 != value2)
        {
            return true; 
        }
        else
        {
            return false;
        }
    }
    
    /**
     * Evaluates if a long value and an integer value are not equal
     * 
     * @param value1	first value for comparison
     * @param value2	second value for comparison
     * @return			indication if the two values are not equal
     */
    public static boolean evaluate(long value1, int value2)
    {
        if(value1 != value2)
        {
            return true; 
        }
        else
        {
            return false;
        }
    }
    
    /**
     * Evaluates if an integer value and an long value are not equal
     * 
     * @param value1	first value for comparison
     * @param value2	second value for comparison
     * @return			indication if the two values are not equal
     */
    public static boolean evaluate(int value1, long value2)
    {
        if(value1 != value2)
        {
            return true; 
        }
        else
        {
            return false;
        }
    }
    
    /**
     * Evaluates if two double values are not equal
     * 
     * @param value1	first value for comparison
     * @param value2	second value for comparison
     * @return			indication if the two values are not equal
     */
    public static boolean evaluate(double value1, double value2)
    {
        if(value1 != value2)
        {
            return true; 
        }
        else
        {
            return false;
        }
    }
    
    /**
     * Evaluates if two float values are not equal
     * 
     * @param value1	first value for comparison
     * @param value2	second value for comparison
     * @return			indication if the two values are not equal
     */
    public static boolean evaluate(float value1, float value2)
    {
        if(value1 != value2)
        {
            return true; 
        }
        else
        {
            return false;
        }
    }
    
    /**
     * Evaluates if two boolean values are not equal
     * 
     * @param value1	first value for comparison
     * @param value2	second value for comparison
     * @return			indication if the two values are not equal
     */
    public static boolean evaluate(boolean value1, boolean value2)
    {
        return value1!=value2; 
    }
    
    /**
     * Evaluates if two string values are not equal
     * 
     * @param value1	first value for comparison
     * @param value2	second value for comparison
     * @return			indication if the two values are not equal
     */
    public static boolean evaluate(String value1, String value2)
    {
    	if(value1!=null && value2!=null)
    	{
    		return evaluate(value1,value2,false);
    	}
    	else
    	{
    		return false;
    	}
    }

    /**
     * Evaluates if two string values are not equal.
     * 
     * @param value1		first value for comparison
     * @param value2		second value for comparison
     * @param ignoreCase	indication if the case of the values shall be ignored for comparison
     * @return				indication if the two values are not equal
     */
    public static boolean evaluate(String value1, String value2, boolean ignoreCase)
    {
    	if(value1!=null && value2!=null)
    	{
	        if(ignoreCase)
	        {
		        if(!value1.toLowerCase().equals(value2.toLowerCase()))
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
	            if(!value1.equals(value2))
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
     * Evaluates if two dates provided as string values are not equal using the specified date format.
     * 
     * @param value1		first value for comparison
     * @param value2		second value for comparison
     * @param format		the format of both dates according to the SimpleDateFormat class
     * @return				indication if the two values are not equal
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
	        
	        result = !cal1.equals(cal2);
        }
        catch(Exception ex)
        {
        	
        }
        return result;
    
    }
    
    /**
     * Evaluates if the date and the date provided as string value are not equal using the specified date format.
     * 
     * @param date1			first value for comparison
     * @param value2		second value for comparison
     * @param format		the format of the date provided as string according to the SimpleDateFormat class
     * @return				indication if the two values are not equal
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
	        
	        result = !cal1.equals(cal2);
        }
        catch(Exception ex)
        {
        	
        }
        return result;
    
    }
    
    /**
     * Evaluates if the date and the date provided as string value are not equal using the default date format.
     * 
     * @param date1			first value for comparison
     * @param value			second value for comparison
     * @return				indication if the two values are not equal
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
	        
	        result = !cal1.equals(cal2);
        }
        catch(Exception ex)
        {
        	
        }
        return result;
    
    }
    
    /**
     * Evaluates if the two dates are not equal using the specified date format.
     * 
     * @param date1			first value for comparison
     * @param date2			second value for comparison
     * @param format		the format of the date provided as string according to the SimpleDateFormat class
     * @return				indication if the two values are not equal
     */
    public static boolean evaluate(Date date1, Date date2, String format)
    {
    	SimpleDateFormat sdf = new SimpleDateFormat(format);
        
    	String date1String=null;
    	String date2String=null;
    	
    	if(date1!=null)
    	{
    		date1String = sdf.format(date1);
    	}
    	if(date2!=null)
    	{
    		date2String = sdf.format(date2);
    	}
    	return !date1String.equals(date2String);
    }
    
    /**
     * Evaluates if the date and the date provided as string value are not equal using the default date format.
     * 
     * @param date1			first value for comparison
     * @param date2			second value for comparison
     * @return				indication if the two values are not equal
     */
    public static boolean evaluate(Date date1, Date date2)
    {
    	if(date1!=null && date2!=null)
        {
    		return !date1.equals(date2);
        }
    	else
    	{
    		return false;
    	}
    }
}
