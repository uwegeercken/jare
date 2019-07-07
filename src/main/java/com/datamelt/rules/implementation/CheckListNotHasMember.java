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
 * checks if a string containing multiple values separated by commas (,) 
 * not contains a given string. spaces between the individual values are removed (trim)
 * 
 * an example for a list would be:
 * 
 * 		"Rome, Paris, New York, Berlin"
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
@CheckAnnotation(name="Check List Not Has Member", description="Checks if a comma seperated list of values does not contain a given member",nameDescriptive="has not list member",checkSingleField=0)
public class CheckListNotHasMember extends GenericCheck
{
	/**
     * Evaluates if the list does not contain the given value as a member
     * 
     * @param list		list of string values separated by commas
     * @param value		the value to compare against
     * @return			indication if the given string is not a member of the list
     */
	@CheckMethodAnnotation(note="First String is a comma separated list of values")
    public static boolean evaluate(String list, String value)
    {
        boolean matches = false;
        if(list!=null)
        {
        	String [] entries = list.split(",");
	        for(int i=0;i< entries.length;i++)
	    	{
	        	if(entries[i].trim().equals(value.trim()))
	    		{
	        		matches = true;
	    			break;
	    		}
	    	}
        }
        return !matches;
    }
    
    /**
     * Evaluates if the list does not contain the given value as a member
     * 
     * @param list			list of string values separated by commas
     * @param value			the value to compare against
     * @param ignoreCase	indication if the case of the values shall be ignored for comparison
     * @return				indication if the given string is not a member of the list
     */
	@CheckMethodAnnotation(note="First String is a comma separated list of values",noteParameter={"Ignore case differences during comparison"})
    public static boolean evaluate(String list, String value, boolean ignoreCase)
    {
    	boolean matches = false;
    	if(list!=null)
    	{
	    	String [] entries = list.split(",");
	        for(int i=0;i<entries.length;i++)
	    	{
	        	if(!ignoreCase)
	    		{
		        	if(entries[i].trim().equals(value.trim()))
		    		{
		    			matches = true;
		    			break;
		    		}
	    		}
		        else
		        {
		        	if(entries[i].trim().toLowerCase().equals(value.trim().toLowerCase()))
		    		{
		    			matches = true;
		    			break;
		    		}
		        }
	    	}
    	}
        return !matches;
    }
    
    /**
     * Evaluates if the list does not contain the given value as a member
     * 
     * @param list		list of integer values separated by commas
     * @param value		the value to compare against
     * @return			indication if the given integer is not member of the list
     */
	@CheckMethodAnnotation(note="First String is a comma separated list of values")
    public static boolean evaluate(String list, int value )
    {
    	boolean matches = false;
    	String [] entries = list.split(",");
        for(int i=0;i<entries.length;i++)
    	{
        	if(Integer.parseInt(entries[i].trim()) == value)
    		{
    			matches = true;
    			break;
    		}
    	}
        return !matches;
    }
    
    /**
     * Evaluates if the list does not contain the given value as a member
     * 
     * @param list		list of long values separated by commas
     * @param value		the value to compare against
     * @return			indication if the given long is not member of the list
     */
	@CheckMethodAnnotation(note="First String is a comma separated list of values")
    public static boolean evaluate(String list, long value)
    {
    	boolean matches = false;
    	String [] entries = list.split(",");
        for(int i=0;i<entries.length;i++)
    	{
        	if(Long.parseLong(entries[i].trim()) == value)
    		{
    			matches = true;
    			break;
    		}
    	}
        return !matches;
    }
}
