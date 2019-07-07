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

import com.datamelt.util.CheckAnnotation;
import com.datamelt.util.CheckMethodAnnotation;

/**
 * Checks if one value is greater than the other value. In case a string value is used, it checks, if the length of the
 * string if greater than the given length.
 * <p>
 * The first parameter of a given method is always the value of the field that shall be checked. The second parameter is either another field to check against 
 * or an expected value (fixed value) to check against the first value.
 * 
 * @author uwe geercken
 */
@CheckAnnotation(name="Check Is Greater", description="Check if a numeric value is greater than the other one. In case of a String checks if the length of a String is greater than the other one",nameDescriptive="is greater than",checkSingleField=0)
public class CheckIsGreater extends GenericCheck
{
    
    /**
     * Evaluates if the string length is greater than the given length.
     * 
     * @param value		the value to compare
     * @param length	the length to compare against
     * @return			indication if the length of the value is greater than the expected length
     */
	@CheckMethodAnnotation(note="Checks if the length of the String is greater than the integer value")
    public static boolean evaluate(String value, int length)
    {
        if(value!=null)
        {
	    	if(value.length()> length)
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
     * Evaluates if the string length is greater than the given length.
     * 
     * @param value		the value to compare
     * @param length	the length to compare against
     * @return			indication if the length of the value is greater than the expected length
     */
	@CheckMethodAnnotation(note="Checks if the length of the String is greater than the long value")
    public static boolean evaluate(String value, long length)
    {
        if(value!=null)
        {
	    	if(value.length()> length)
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
     * Evaluates if the first value is greater than the second value.
     * 
     * @param value1	the first value for comparison
     * @param value2	the second value for comparison
     * @return			indication if the first value is greater than the second value
     */
    public static boolean evaluate(int value1, int value2)
    {
        if(value1 > value2)
        {
            return true; 
        }
        else
        {
            return false;
        }
    }
    
    /**
     * Evaluates if the first value is greater than the second value.
     * 
     * @param value1	the first value for comparison
     * @param value2	the second value for comparison
     * @return			indication if the first value is greater than the second value
     */
    public static boolean evaluate(long value1, long value2)
    {
        if(value1 > value2)
        {
            return true; 
        }
        else
        {
            return false;
        }
    }
    
    /**
     * Evaluates if the first value is greater than the second value.
     * 
     * @param value1	the first value for comparison
     * @param value2	the second value for comparison
     * @return			indication if the first value is greater than the second value
     */
    public static boolean evaluate(double value1, long value2)
    {
        if(value1 > value2)
        {
            return true; 
        }
        else
        {
            return false;
        }
    }
    
    /**
     * Evaluates if the first value is greater than the second value.
     * 
     * @param value1	the first value for comparison
     * @param value2	the second value for comparison
     * @return			indication if the first value is greater than the second value
     */
    public static boolean evaluate(double value1, float value2)
    {
        if(value1 > value2)
        {
            return true; 
        }
        else
        {
            return false;
        }
    }
    
    /**
     * Evaluates if the first value is greater than the second value.
     * 
     * @param value1	the first value for comparison
     * @param value2	the second value for comparison
     * @return			indication if the first value is greater than the second value
     */
    public static boolean evaluate(double value1, int value2)
    {
        if(value1 > value2)
        {
            return true; 
        }
        else
        {
            return false;
        }
    }
    
    /**
     * Evaluates if the first value is greater than the second value.
     * 
     * @param value1	the first value for comparison
     * @param value2	the second value for comparison
     * @return			indication if the first value is greater than the second value
     */
    public static boolean evaluate(float value1, int value2)
    {
        if(value1 > value2)
        {
            return true; 
        }
        else
        {
            return false;
        }
    }

    /**
     * Evaluates if the first value is greater than the second value.
     * 
     * @param value1	the first value for comparison
     * @param value2	the second value for comparison
     * @return			indication if the first value is greater than the second value
     */
    public static boolean evaluate(long value1, int value2)
    {
        if(value1 > value2)
        {
            return true; 
        }
        else
        {
            return false;
        }
    }
    
    /**
     * Evaluates if the first value is greater than the second value.
     * 
     * @param value1	the first value for comparison
     * @param value2	the second value for comparison
     * @return			indication if the first value is greater than the second value
     */
    public static boolean evaluate(double value1, double value2)
    {
        if(value1 > value2)
        {
            return true; 
        }
        else
        {
            return false;
        }
    }
    
    /**
     * Evaluates if the first value is greater than the second value.
     * 
     * @param value1	the first value for comparison
     * @param value2	the second value for comparison
     * @return			indication if the first value is greater than the second value
     */
    public static boolean evaluate(float value1, float value2)
    {
        if(value1 > value2)
        {
            return true; 
        }
        else
        {
            return false;
        }
    }
    
    /**
     * Evaluates if the first value is greater than the second value.
     * 
     * @param value1	the first value for comparison
     * @param value2	the second value for comparison
     * @return			indication if the first value is greater than the second value
     */
    public static boolean evaluate(float value1, long value2)
    {
        if(value1 > value2)
        {
            return true; 
        }
        else
        {
            return false;
        }
    }

    /**
     * Evaluates if the first value is greater than the second value.
     * 
     * @param value1	the first value for comparison
     * @param value2	the second value for comparison
     * @return			indication if the first value is greater than the second value
     */
    public static boolean evaluate(float value1, double value2)
    {
        if(value1 > value2)
        {
            return true; 
        }
        else
        {
            return false;
        }
    }
    
    /**
     * Evaluates if the first date is greater than the second date - both provided as string values- using the specified date format.
     * 
     * @param value1	the first date value for comparison
     * @param value2	the second date value for comparison
     * @param format	the format to be used for the comparison according to the SimpleDateFormat class
     * @return			indication if the first value is greater than the second value
     */
    @CheckMethodAnnotation(note="Compare two String values that contain dates ",noteParameter={"Provide the date format of the string values"})
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
	        
	        result = cal1.after(cal2);
        }
        catch(Exception ex)
        {
        	
        }
        return result;
    
    }
    
    /**
     * Evaluates if the first date is greater than the second date - provided as a string value - using the specified date format.
     * 
     * @param date1		the first date value for comparison
     * @param value2	the second date value for comparison
     * @param format	the format to be used for the comparison according to the SimpleDateFormat class
     * @return			indication if the first value is greater than the second value
     */
    @CheckMethodAnnotation(note="Without parameter the String is converted to a Date using the format yyyy-MM-dd",noteParameter={"Provide a date format different from the default"})
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
	        
	        result = cal1.after(cal2);
        }
        catch(Exception ex)
        {
        	
        }
        return result;
    
    }
    
    /**
     * Evaluates if the first date is greater than the second date - provided as a string value - using the default date format.
     * 
     * @param date1		the first date value for comparison
     * @param value2	the second date value for comparison
     * @return			indication if the first value is greater than the second value
     */
    public static boolean evaluate(Date date1, String value2)
    {
        
        SimpleDateFormat sdf = new SimpleDateFormat(CheckConstants.DEFAULT_DATE_FORMAT);

        boolean result = false;
        
        try
        {
	        Date date2 = sdf.parse(value2);
	        
	        Calendar cal1 = Calendar.getInstance();
	        cal1.setTime(date1);
	        
	        Calendar cal2 = Calendar.getInstance();
	        cal2.setTime(date2);
	        
	        result = cal1.after(cal2);
        }
        catch(Exception ex)
        {
        	
        }
        return result;
    
    }
    
    /**
     * Evaluates if the first date is greater than the second date using the default date format.
     * 
     * @param date1		the first date value for comparison
     * @param date2		the second date value for comparison
     * @return			indication if the first value is greater than the second value
     */
    public static boolean evaluate(Date date1, Date date2)
    {
        if(date1!=null && date2!=null)
        {
        	return date1.after(date2);
        }
        else
        {
        	return false;
        }
    }
}
