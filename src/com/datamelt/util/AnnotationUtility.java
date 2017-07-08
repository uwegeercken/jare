package com.datamelt.util;

import java.lang.reflect.Method;

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
