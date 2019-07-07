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
 * Checks if a string starts with a given other string.
 * <p>
 * The first parameter of a given method is always the value of the field that shall be checked. The second parameter is either another field to check against 
 * or an expected value (fixed value) to check against the first value.
 * 
 * @author uwe geercken
 */
@CheckAnnotation(name="Check Starts With", description="Check if a String starts with a certain String",nameDescriptive="starts with",checkSingleField=0)
public class CheckStartsWith extends GenericCheck
{
	/**
     * Check if the specified value starts with given compare value
     * 
     * @param value			the first value for comparison
     * @param compareValue	the second value for comparison - to compare against the first value
     * @return				indication if the first value starts with the second value
     */
	@CheckMethodAnnotation(note="The default is to compare the values case sensitive")
    public static boolean evaluate(String value,String compareValue)
    {
    	if(value!=null && compareValue!=null)
        {
    		return value.startsWith(compareValue);
        }
    	else
    	{
    		return false;
    	}
    	
    }
    
    /**
     * Check if the specified value starts with given compare value
     * 
     * @param value			the first value for comparison
     * @param compareValue	the second value for comparison - to compare against the first value
     * @param ignoreCase	indication if the case of the values shall be ignored for comparison
     * @return				indication if the first value starts with the second value
     */
	@CheckMethodAnnotation(note="The default is to compare the values case sensitive",noteParameter={"Ignore case differences during comparison"})
    public static boolean evaluate(String value,String compareValue,boolean ignoreCase)
    {
    	if(value!=null && compareValue!=null)
        {
	    	if(!ignoreCase)
	        {
	            return value.startsWith(compareValue);
	        }
	        else
	        {
	            return value.toLowerCase().startsWith(compareValue.toLowerCase());
	        }
        }
    	else
    	{
    		return false;
    	}
    }
    
}
