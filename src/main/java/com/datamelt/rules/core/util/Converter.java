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
 
package com.datamelt.rules.core.util;

import com.datamelt.rules.core.RuleSubGroup;

/**
 * helper class to do conversions that are used internally.
 * 
 * @author uwe geercken
 */
public class Converter
{
    /*
     * converts an integer value to logical value:
     * [and], [or] or [] and provides the result 
     * in form of a string.
     * 
     * @param i 		the integer value to convert
     * @return			the equivalent string representation of the integer value
     */
    public static String convertIntToLogical(int i)
    {
        if(i==RuleSubGroup.OPERATOR_AND)
        {
            return "and";
        }
        else if(i==RuleSubGroup.OPERATOR_OR) 
        {
            return "or";
        }
        else
        {
            return "[]";
        }
    }
    
    /*
     * converts an integer value to boolean value and
     * provides the result in form of a string.
     * 
     * @param i 		the integer value to convert
     * @return			the equivalent string representation of the integer value
     */
    public static String convertIntegerToBooleanString(int i) 
    {
        if(i==0)
        {
            return "false";
        }
        else
        {
            return "true";
        }
    }
    
    /*
     * converts an integer value to boolean value 
     * 
     * @param i 		the integer value to convert
     * @return			the equivalent boolean representation of the integer value
     */
    public static boolean convertIntegerToBoolean(int i) 
    {
        if(i==0)
        {
            return false;
        }
        else
        {
            return true;
        }
    }
    
    /*
     * converts an integer value which represents the action type and
     * provides the result in form of a string.
     * 
     * @param i 		the integer value to convert
     * @return			the equivalent string representation of the integer value
     */
    public static String convertActionTypeToString(int i) 
    {
        if(i==0)
        {
            return "passed";
        }
        else if(i==1)
        {
            return "failed";
        }
        else
        {
        	return "always";
        }
    }
}
