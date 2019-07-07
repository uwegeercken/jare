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
 * checks if a given value is greater or equal to zero (0).
 * 
 * <p>
 * The first parameter of a given method is always the value of the field that shall be checked. The second parameter is either another field to check against 
 * or an expected value (fixed value) to check against the first value.
 * 
 * @author uwe geercken
 */
@CheckAnnotation(name="Check Is Not Negative Number", description="Checks if a number is greater than or equal to zero",nameDescriptive="not is negativ number",checkSingleField=1)
public class CheckIsNotNegativeNumber extends GenericCheck
{
	/**
     * Evaluates if the given value is greater or equal to zero (0).
     * 
     * @param value1	the value for comparison	
     * @return			indication if the value is greater or equal to zero
     */
    public static boolean evaluate(int value1)
    {
        if(value1 >= 0)
        {
            return true; 
        }
        else
        {
            return false;
        }
    }
    
    /**
     * Evaluates if the given value is greater or equal to zero (0).
     * 
     * @param value1	the value for comparison	
     * @return			indication if the value is greater or equal to zero
     */
    public static boolean evaluate(long value1)
    {
        if(value1 >= 0)
        {
            return true; 
        }
        else
        {
            return false;
        }
    }
    
    /**
     * Evaluates if the given value is greater or equal to zero (0).
     * 
     * @param value1	the value for comparison	
     * @return			indication if the value is greater or equal to zero
     */
    public static boolean evaluate(double value1)
    {
        if(value1 >= 0)
        {
            return true; 
        }
        else
        {
            return false;
        }
    }
    
    /**
     * Evaluates if the given value is greater or equal to zero (0).
     * 
     * @param value1	the value for comparison	
     * @return			indication if the value is greater or equal to zero
     */
    public static boolean evaluate(float value1)
    {
        if(value1 >= 0)
        {
            return true; 
        }
        else
        {
            return false;
        }
    }
}
