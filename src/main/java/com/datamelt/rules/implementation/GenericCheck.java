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

import java.io.Serializable;
import java.util.HashSet;

import com.datamelt.rules.core.XmlRule;

/**
 * this class is the base class of all classes that check
 * certain conditions in regards to provided values
 * 
 * @author uwe geercken
 */
public class GenericCheck implements Serializable
{
	public static final long serialVersionUID = 1964070325;
	public static final String GENERIC_CHECK_METHOD_EVALUATE = "evaluate";
	
	private static HashSet<Object> set;
	
	/**
	 * cache the possible values in a HashSet but only on the first
	 * record or row received.
	 * 
	 * @param values	A comma separated list of expected values
	 */
	public static void fillHashSet(String values)
	{
		if(set == null)
		{
			set = new HashSet<Object>();
			
			String [] valuesArray = values.split(",");
			for(int i=0;i<valuesArray.length;i++)
			{
				set.add(valuesArray[i]);
			}
		}
	}
	
	public static HashSet<Object> getHashSet()
	{
		return set;
	}
}
