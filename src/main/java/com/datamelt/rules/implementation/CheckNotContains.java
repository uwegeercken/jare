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

/**
 * checks if a given value does not contain another value.
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
public class CheckNotContains extends GenericCheck
{
    /**
	 * Checks if a string is not contained in another string and ignores or does not ignore
	 * the case of the values.
	 *
     * @param value 		the first value for comparison
     * @param compareValue 	the second value for comparison - to compare against the first value
     * @return				indication if the compareValue is not contained in the value
     */
    public static boolean evaluate(String value,String compareValue)
    {
        if(value!=null && compareValue!=null)
        {
	        int pos = value.indexOf(compareValue);
	        if(pos>-1)
	        {
	            return false; 
	        }
	        else
	        {
	            return true;
	        }
        }
        else
        {
        	return false;
        }
    }
    
    /**
	 * Checks if a string is not contained in another string and ignores or does not ignore
	 * the case of the values.
	 *
     * @param value 		the first value for comparison
     * @param compareValue 	the second value for comparison - to compare against the first value
     * @param ignoreCase	indication if the case of the values shall be ignored for comparison
     * @return				indication if the compareValue is not contained in the value
     */
    public static boolean evaluate(String value,String compareValue, boolean ignoreCase)
    {
    	if(value!=null && compareValue!=null)
        {
	    	if(ignoreCase)
	        {
		    	int pos = value.toLowerCase().indexOf(compareValue.toLowerCase());
		        if(pos>-1)
		        {
		            return false; 
		        }
		        else
		        {
		            return true;
		        }
	        }
	        else
	        {
		    	int pos = value.indexOf(compareValue);
		        if(pos>-1)
		        {
		            return false; 
		        }
		        else
		        {
		            return true;
		        }
	        }
        }
        else
        {
        	return false;
        }
	}
}
