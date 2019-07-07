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

import com.datamelt.util.CheckAnnotation;
import com.datamelt.util.CheckMethodAnnotation;

/**
 * Checks if one number is between two given other numbers - including the upper and lower limit.
 * <p>
 * 
 * The first parameter of a given method is always the value of the field that shall be checked. The second parameter is either another field to check against 
 * or an expected value (fixed value) to check against the first value.
 * 
 * @author uwe geercken
 */
@CheckAnnotation(name="Check Is Between", description="Checks if a numeric value is between two given values",nameDescriptive="is between",checkSingleField=0)
public class CheckIsBetween extends GenericCheck
{
	/**
	 * Evaluate if an integer value is between an upper and the lower limit provided as string values and separated by a comma.
	 *  
     * @param value					the first value for comparison
     * @param valueLowerUpperLimit	comma separated list of values for upper and lower limit
     * @return						indication if the first value is between the upper and lower limit
     */
	@CheckMethodAnnotation(note="String is comma separated list of lower limit, upper limit")
    public static boolean evaluate(int value,String valueLowerUpperLimit)
    {
    	if(valueLowerUpperLimit!=null)
    	{
	    	String[] stringValues= valueLowerUpperLimit.split(",");
	    	int lowerLimit = Integer.parseInt(stringValues[0].trim());
	    	int upperLimit = Integer.parseInt(stringValues[1].trim());
	    	
	        return value>=lowerLimit && value<=upperLimit;
    	}
    	else
    	{
    		return false;
    	}
    }
    
    /**
	 * Evaluate if a long value is between an upper and the lower limit provided as string values and separated by a comma.
	 *  
     * @param value					the first value for comparison
     * @param valueLowerUpperLimit	comma separated list of values for upper and lower limit
     * @return						indication if the first value is between the upper and lower limit
     */
	@CheckMethodAnnotation(note="String is comma separated list of lower limit, upper limit")
    public static boolean evaluate(long value,String valueLowerUpperLimit)
    {
    	if(valueLowerUpperLimit!=null)
    	{
	    	String[] stringValues= valueLowerUpperLimit.split(",");
	    	long lowerLimit = Long.parseLong(stringValues[0].trim());
	    	long upperLimit = Long.parseLong(stringValues[1].trim());
	    	
	        return value>=lowerLimit && value<=upperLimit;
    	}
    	else
    	{
    		return false;
    	}
    }
    
    /**
	 * Evaluate if a double value is between an upper and the lower limit provided as string values and separated by a comma.
	 *  
     * @param value					the first value for comparison
     * @param valueLowerUpperLimit	comma separated list of values for upper and lower limit
     * @return						indication if the first value is between the upper and lower limit
     */
    @CheckMethodAnnotation(note="String is comma separated list of lower limit, upper limit")
    public static boolean evaluate(double value,String valueLowerUpperLimit)
    {
    	if(valueLowerUpperLimit!=null)
    	{
    		String[] stringValues= valueLowerUpperLimit.split(",");
    		double lowerLimit = Double.parseDouble(stringValues[0].trim());
    		double upperLimit = Double.parseDouble(stringValues[1].trim());
    	
    		return value>=lowerLimit && value<=upperLimit;
    	}
    	else
    	{
    		return false;
    	}
    }
    
    /**
	 * Evaluate if a float value is between an upper and the lower limit provided as string values and separated by a comma.
	 *  
     * @param value					the first value for comparison
     * @param valueLowerUpperLimit	comma separated list of values for upper and lower limit
     * @return						indication if the first value is between the upper and lower limit
     */
    @CheckMethodAnnotation(note="String is comma separated list of lower limit, upper limit")
    public static boolean evaluate(float value,String valueLowerUpperLimit)
    {
    	if(valueLowerUpperLimit!=null)
    	{
	    	String[] stringValues= valueLowerUpperLimit.split(",");
	    	float lowerLimit = Float.parseFloat(stringValues[0].trim());
	    	float upperLimit = Float.parseFloat(stringValues[1].trim());
    	
	    	return value>=lowerLimit && value<=upperLimit;
    	}
    	else
    	{
    		return false;
    	}
    }
    
    /**
	 * Evaluate if an integer value is between an upper and the lower limit provided as integer values.
	 *  
     * @param value				the first value for comparison
     * @param valueLowerLimit	the lower limit for the comparison
     * @param valueUpperLimit	the upper limit for the comparison
     * @return					indication if the first value is between the upper and lower limit
     */
    @CheckMethodAnnotation(note="The compare to value is the lower limit",noteParameter={"Parameter 1 defines the upper limit"})
    public static boolean evaluate(int value,int valueLowerLimit, int valueUpperLimit)
    {
        return value>=valueLowerLimit && value<=valueUpperLimit;
    }
    
    /**
   	* Evaluate if a long value is between an upper and the lower limit provided as long values.
   	*  
    * @param value				the first value for comparison
    * @param valueLowerLimit	the lower limit for the comparison
    * @param valueUpperLimit	the upper limit for the comparison
    * @return					indication if the first value is between the upper and lower limit
    */
    @CheckMethodAnnotation(note="The compare to value is the lower limit",noteParameter={"Parameter 1 defines the upper limit"})
    public static boolean evaluate(long value,long valueLowerLimit, long valueUpperLimit)
    {
        return value>=valueLowerLimit && value<=valueUpperLimit;
    }
    
    /**
   	* Evaluate if a double value is between an upper and the lower limit provided as double values.
   	*  
    * @param value				the first value for comparison
    * @param valueLowerLimit	the lower limit for the comparison
    * @param valueUpperLimit	the upper limit for the comparison
    * @return					indication if the first value is between the upper and lower limit
    */
    @CheckMethodAnnotation(note="The compare to value is the lower limit",noteParameter={"Parameter 1 defines the upper limit"})
    public static boolean evaluate(double value,double valueLowerLimit, double valueUpperLimit)
    {
        return value>=valueLowerLimit && value<=valueUpperLimit;
    }
    
    /**
   	* Evaluate if a float value is between an upper and the lower limit provided as float values.
   	*  
    * @param value				the first value for comparison
    * @param valueLowerLimit	the lower limit for the comparison
    * @param valueUpperLimit	the upper limit for the comparison
    * @return					indication if the first value is between the upper and lower limit
    */
    @CheckMethodAnnotation(note="The compare to value is the lower limit",noteParameter={"Parameter 1 defines the upper limit"})
    public static boolean evaluate(float value,float valueLowerLimit, float valueUpperLimit)
    {
        return value>=valueLowerLimit && value<=valueUpperLimit;
    }

}
