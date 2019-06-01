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

import java.util.HashSet;

/**
 * Checks if the given string value is not contained in a list of values separated by comma.
 * Spaces in the individual values are removed from the beginning and the end.
 * 
 * On the first record/row the list of expected values from the rule is cached into a
 * HashSet for better performance.
 * 
 * An example for a list would be:
 *
 * 		Rome, Paris, New York, Berlin
 *
 * The first parameter of a given method is always the value of the field that shall be checked. The second parameter is either another field to check against 
 * or an expected value (fixed value) to check against the first value.
 * 
 * @author uwe geercken
 */

public class CheckIsNotInList extends GenericCheck
{
	/**
     * Checks if the given string value is not contained in a list of values separated by comma.
     * 
     * @param value		the first value for the comparison
     * @param list		list of string values separated by commas
     * @return			indication if the value is not contained in the list of values
     */
    public static boolean evaluate(String value,String list)
    {
    	boolean matches = false;
        if(value!=null)
        {
        	fillHashSet(list);
        	if(getHashSet().contains(value))
        	{
	        	matches = true;
	    	}
        }
        return !matches;
    }
    
    /**
     * Checks if the given string value is not contained in a list of values separated by comma.
     * 
     * @param value			the first value for the comparison
     * @param list			list of string values separated by commas
     * @param ignoreCase	indication if the case of the values shall be ignored for comparison
     * @return				indication if the value is not contained in the list of values
     */
    public static boolean evaluate(String value,String list, boolean ignoreCase)
    {
    	boolean matches = false;
    	if(value!=null)
    	{
    		fillHashSet(list);
        	if(!ignoreCase)
    		{
	        	if(getHashSet().contains(value))
	    		{
	    			matches = true;
	    		}
    		}
	        else
	        {
	        	if(getHashSet().contains(value.toLowerCase())|| getHashSet().contains(value.toUpperCase()))
	    		{
	    			matches = true;
	    		}
	        }
    	}
        return !matches;
    }
    
    /**
     * Checks if the given integer value is not contained in a list of values separated by comma.
     * <p>
     * Each value in the list is converted to an integer value.
     * 
     * @param value		the first value for the comparison
     * @param list		list of integer values separated by commas
     * @return			indication if the value is not contained in the list of values
     */
    public static boolean evaluate(int value, String list)
    {
    	boolean matches = false;
    	fillHashSet(list);
    	if(getHashSet().contains(String.valueOf(value)))
		{
			matches = true;

    	}
        return !matches;
    }
    
    /**
     * Checks if the given long value is not contained in a list of values separated by comma.
     * <p>
     * Each value in the list is converted to a long value.
     * 
     * @param value		the first value for the comparison
     * @param list		list of long values separated by commas
     * @return			indication if the value is not contained in the list of values
     */
    public static boolean evaluate(long value, String list)
    {
    	boolean matches = false;
    	fillHashSet(list);
    	if(getHashSet().contains(String.valueOf(value)))
		{
			matches = true;

    	}
        return matches;
    }
}
