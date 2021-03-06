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
 * Checks if a string is empty meaning: it is of zero length and is NOT null.
 * <p>
 * 
 * The first parameter of a given method is always the value of the field that shall be checked. The second parameter is either another field to check against 
 * or an expected value (fixed value) to check against the first value.
 * 
 * @author uwe geercken
 */
@CheckAnnotation(name="Check Is Empty", description="Checks if a string value is empty, meaning of zero length",nameDescriptive="is empty",checkSingleField=1)
public class CheckIsEmpty extends GenericCheck
{
    
    /**
     * Evaluates if a string is empty.
     * 
     * if the string is null this method will return true.
     * 
     * @param value	the value to evaluate
     * @return		indication if the given string is empty
     */
    public static boolean evaluate(String value)
    {
        if(value!=null)
        {
	    	if(value.equals(""))
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
        	return true;
        }
    }
    
}
