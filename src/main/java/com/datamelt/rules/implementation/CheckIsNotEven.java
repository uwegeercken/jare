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

/**
 * Checks if a given number is not an even number
 * <p>
 * The first parameter of a given method is always the value of the field that shall be checked. The second parameter is either another field to check against 
 * or an expected value (fixed value) to check against the first value.
 * 
 * @author uwe geercken
 */
@CheckAnnotation(name="Check Is Not Even", description="Checks if a number is not an even number",nameDescriptive="is not even",checkSingleField=1)
public class CheckIsNotEven extends GenericCheck
{
	/**
     * Evaluates if the given integer value is not an even number.
     * 
     * @param value	the value for comparison
     * @return		indication if the value is an even number
     */
    public static boolean evaluate(int value)
    {
    	if (value % 2==0)
    	{
    		return false;
    	}
    	else
    	{
    		return true;
    	}
    }
    
    /**
     * Evaluates if the given long value is not an even number.
     * 
     * @param value	the value for comparison
     * @return		indication if the value is an even number
     */
    public static boolean evaluate(long value)
    {
    	if (value % 2==0)
    	{
    		return false;
    	}
    	else
    	{
    		return true;
    	}
    }
}
