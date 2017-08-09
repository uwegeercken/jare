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
package com.datamelt.util;

import java.lang.reflect.Method;

/**
 * Utility class to compare two methods and if the type of the first two arguments are the same,
 * returns the methos with the most additional parameters.
 * 
 * @author uwe geercken
 *
 */
public class AnnotationUtility
{
	public Method compareMethods(Method method1, Method method2)
	{
		// get all parameters for both methods
		Class<?> []method1ParameterClasses = method1.getParameterTypes();
		Class<?> []method2ParameterClasses = method2.getParameterTypes();
		
		// get the classes of the parameters
		Class<?> method1Parameter1Class = method1ParameterClasses[0];
		Class<?> method1Parameter2Class = method1ParameterClasses[1];
		
		Class<?> method2Parameter1Class = method2ParameterClasses[0];
		Class<?> method2Parameter2Class = method2ParameterClasses[1];

		// check if the first and the second parameter class is the same
		if(method1Parameter1Class.getName().equals(method2Parameter1Class.getName()) && method1Parameter2Class.getName().equals(method2Parameter2Class.getName()))
		{
			int method1NumberOfParameters = method1ParameterClasses.length;
			int method2NumberOfParameters = method2ParameterClasses.length;	
			
			// return the method with the most parameters
			if(method1NumberOfParameters >= method2NumberOfParameters)
			{
				return method1;
			}
			else
			{
				return method2;
			}
		}
		else
		{
			return method1;
		}
	}
}
