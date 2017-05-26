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

import java.util.Date;

/**
 * checks if a given value is NULL or - in case of a string value - checks if its value is equal to the string 'null' or 'Null'.
 * <p>
 * The first parameter of a given method is always the value of the field that shall be checked. The second parameter is either another field to check against 
 * or an expected value (fixed value) to check against the first value.
 * 
 * @author uwe geercken
 */
public class CheckIsNull extends GenericCheck
{
	/**
     * Evaluates if a string value is null or if the
     * literal value is "null"
     * 
     * @param value		the value for comparison
     * @return			indication if the value is null
     */
    public static boolean evaluate(String value)
    {
        if(value==null || value.toLowerCase().trim().equals("null"))
        {
            return true; 
        }
        else
        {
            return false;
        }
    }
    
    /**
     * Evaluates if a long value is null
     * 
     * @param value		the value for comparison
     * @return			indication if the value is null
     */
    public static boolean evaluate(long value)
    {
    	Long longValue = value;
   		return longValue == null;
    }
    
    /**
     * Evaluates if an integer value is null
     * 
     * @param value		the value for comparison
     * @return			indication if the value is null
     */
    public static boolean evaluate(int value)
    {
    	Integer integerValue = value;
    	return integerValue == null;
    }
    
    /**
     * Evaluates if a double value is null
     * 
     * @param value		the value for comparison
     * @return			indication if the value is null
     */
    public static boolean evaluate(double value)
    {
    	Double doubleValue = value;
    	return doubleValue==null;
    }
    
    /**
     * Evaluates if a float value is null
     * 
     * @param value		the value for comparison
     * @return			indication if the value is null
     */
    public static boolean evaluate(float value)
    {
    	Float floatValue = value;
    	return floatValue==null;
    }
    
    /**
     * Evaluates if a date value is null
     * 
     * @param value		the value for comparison
     * @return			indication if the value is null
     */
    public static boolean evaluate(Date value)
    {
    	if(value==null)
        {
            return true; 
        }
        else
        {
            return false;
        }
    }
}
