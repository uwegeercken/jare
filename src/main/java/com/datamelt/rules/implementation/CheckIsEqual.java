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
import com.datamelt.util.ClassUtility;

/**
 * Checks if a given string is equal to another string, if two numbers are equal or if two dates are equal.
 * <p>
 * The default format for comparing dates is yyyy-MM-dd.
 * <p>
 * The first parameter of a given method is always the value of the field that shall be checked. The second parameter is either another field to check against 
 * or an expected value (fixed value) to check against the first value.
 * 
 * @author uwe geercken
 */

@CheckAnnotation(name="Check is equal", description="Check for equality of values",nameDescriptive="is equal to",checkSingleField=0)
public class CheckIsEqual extends GenericCheck
{
    // unique id of this check. used to generate methods for the maintenance tool database 
    public static final long checkId = 1;
	
	/**
     * Evaluates if two long values are equal
     * 
     * @param value1	first value for comparison
     * @param value2	second value for comparison
     * @return			indication if the two values are equal
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
     * Evaluates if a long value and an integer value are equal
     * 
     * @param value1	first value for comparison
     * @param value2	second value for comparison
     * @return			indication if the two values are equal
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
     * Evaluates if an integer value and a long value are equal
     * 
     * @param value1	first value for comparison
     * @param value2	second value for comparison
     * @return			indication if the two values are equal
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
     * Evaluates if two double values are equal
     * 
     * @param value1	first value for comparison
     * @param value2	second value for comparison
     * @return			indication if the two values are equal
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
     * Evaluates if an float value and an double value are equal
     * 
     * @param value1	first value for comparison
     * @param value2	second value for comparison
     * @return			indication if the two values are equal
     */
    public static boolean evaluate(float value1, double value2)
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
     * Evaluates if an double value and an float value are equal
     * 
     * @param value1	first value for comparison
     * @param value2	second value for comparison
     * @return			indication if the two values are equal
     */
    public static boolean evaluate(double value1, float value2)
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
     * Evaluates if two float values are equal
     * 
     * @param value1	first value for comparison
     * @param value2	second value for comparison
     * @return			indication if the two values are equal
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
     * Evaluates if two integer values are equal
     * 
     * @param value1	first value for comparison
     * @param value2	second value for comparison
     * @return			indication if the two values are equal
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
     * Evaluates if two boolean values are equal
     * 
     * @param value1	first value for comparison
     * @param value2	second value for comparison
     * @return			indication if the two values are equal
     */
    public static boolean evaluate(boolean value1, boolean value2)
    {
        return value1 == value2; 
    }
    
    /**
     * Evaluates if a boolean value and a string value - converted to a boolean value - are equal
     * 
     * @param value1	first value for comparison
     * @param value2	second value for comparison
     * @return			indication if the two values are equal
     */
    @CheckMethodAnnotation(note="The String value is converted to a boolean value using the format yyyy-MM-dd")
    public static boolean evaluate(boolean value1, String value2)
    {
        return value1 == (Boolean)ClassUtility.getObject(ClassUtility.TYPE_BOOLEAN, value2); 
    }
    
    /**
     * Evaluates if a boolean value and an integer value - converted to a boolean value - are equal.
     * 
     * An integer of value 1 is converted to a boolean true, an integer of value 0 is converted to a
     * boolean false. In all other cases false is returned.
     * 
     * @param value1	first value for comparison
     * @param value2	second value for comparison
     * @return			indication if the two values are equal
     */
    @CheckMethodAnnotation(note="The integer value is converted to a boolean value")
    public static boolean evaluate(boolean value1, int value2)
    {
        if(value2 == 1)
        {
        	return value1 == true;
        }
        else if(value2==0)
        {
        	return value1 == false;
        }
        else
        {
        	return false;
        }
    }
    
    /**
     * Evaluates if a boolean value and an integer value - converted to a boolean value - are equal.
     * 
     * An integer of value 1 is converted to a boolean true, an integer of value 0 is converted to a
     * boolean false. In all other cases false is returned.
     * 
     * @param value1	first value for comparison
     * @param value2	second value for comparison
     * @return			indication if the two values are equal
     */
    @CheckMethodAnnotation(note="The integer value is converted to a boolean value")
    public static boolean evaluate(int value1, boolean value2)
    {
        if(value1 == 1)
        {
        	return value2 == true;
        }
        else if(value1==0)
        {
        	return value2 == false;
        }
        else
        {
        	return false;
        }
    }
    
    /**
     * Evaluates if a string - converted to a boolean value - value and a boolean value are equal
     * 
     * @param value1	first value for comparison
     * @param value2	second value for comparison
     * @return			indication if the two values are equal
     */
    @CheckMethodAnnotation(note="Values are compared case sensitive. The String value is converted to a boolean value")
    public static boolean evaluate(String value1, boolean value2)
    {
        return value2 == (Boolean)ClassUtility.getObject(ClassUtility.TYPE_BOOLEAN, value1); 
    }
    
    /**
     * Evaluates if two string values are equal.
     * 
     * @param value1		first value for comparison
     * @param value2		second value for comparison
     * @param ignoreCase	indication if the case of the values shall be ignored for comparison
     * @return				indication if the two values are equal
     */
    @CheckMethodAnnotation(note="Without parameter Strings are compared case sensitive",noteParameter={"Indicator if the String values shall be compared case sensitive"})
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
     * Evaluates if two string values are equal.
     * 
     * @param value1		first value for comparison
     * @param value2		second value for comparison
     * @return				indication if the two values are equal
     */
    @CheckMethodAnnotation(note="Values are compared case sensitive")
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
     * Evaluates if two dates provided as string values are equal using the specified date format.
     * 
     * @param value1		first value for comparison
     * @param value2		second value for comparison
     * @param format		the format of both dates according to the SimpleDateFormat class
     * @return				indication if the two values are equal
     */
    @CheckMethodAnnotation(noteParameter={"Specify the date format of the values for the comparison"})
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
     * Evaluates if the date and the date provided as string value are equal using the specified date format.
     * 
     * @param date1			first value for comparison
     * @param value2		second value for comparison
     * @param format		the format of the date provided as string according to the SimpleDateFormat class
     * @return				indication if the two values are equal
     */
    @CheckMethodAnnotation(note="Without parameter the String is converted to a Date using the format yyyy-MM-dd",noteParameter={"Provide a date format different from the default"})
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
     * Evaluates if the date and the date provided as string value are equal using the default date format.
     * 
     * @param date1			first value for comparison
     * @param value2		second value for comparison
     * @return				indication if the two values are equal
     */
    @CheckMethodAnnotation(note="String is converted to a date and values are compared using the format yyyy-MM-dd")
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
     * Evaluates if the two dates are equal using the default date format.
     * 
     * @param date1			first value for comparison
     * @param date2			second value for comparison
     * @return				indication if the two values are equal
     */
    public static boolean evaluate(Date date1, Date date2)
    {
    	SimpleDateFormat sdf = new SimpleDateFormat(CheckConstants.DEFAULT_DATE_FORMAT);
        
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
    	return date1String.equals(date2String);
    }
    
    /**
     * Evaluates if the two dates are equal using the specified date format.
     * 
     * @param date1			first value for comparison
     * @param date2			second value for comparison
     * @param format		the format of the date provided as string according to the SimpleDateFormat class
     * @return				indication if the two values are equal
     */
    @CheckMethodAnnotation(noteParameter={"Provide a date format different from the default yyyy-MM-dd"})
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
    	return date1String.equals(date2String);
    }
}
