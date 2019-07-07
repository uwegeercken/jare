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
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.datamelt.rules.core.ActionObject;
import com.datamelt.rules.core.Parameter;
import com.datamelt.rules.core.XmlAction;
import com.datamelt.rules.implementation.GenericCheck;

/**
 * Utility class to translate types, retrieve methods and invoke methods on objects
 * 
 * @author uwe geercken
 *
 */
public class ClassUtility
{
	public static final String TYPE_INTEGER   = "integer";
    public static final String TYPE_INT       = "int";
    public static final String TYPE_LONG      = "long";
    public static final String TYPE_STRING    = "string";
    public static final String TYPE_BOOLEAN   = "boolean";
    public static final String TYPE_DOUBLE    = "double";
    public static final String TYPE_FLOAT     = "float";
    public static final String TYPE_DATE      = "date";
    public static final String TYPE_OBJECT    = "object";
    public static final String TYPE_ARRAYLIST = "arraylist";
    public static final String TYPE_BIGDECIMAL= "bigdecimal";
    
	 /**
     * method is used to return a class corresponding to the type passed to it.
     * 
     */
    /**
     * @param type	the name of the type according to constants defined in this class
     * @return		a Java class corresponding to the type specified
     */
    public static Class<?> getClass(String type)
    {
        if(type.toLowerCase().equals(TYPE_INTEGER) || type.toLowerCase().equals(TYPE_INT))
        {
            return int.class;
        }
        else if(type.toLowerCase().equals(TYPE_LONG))
        {
            return  long.class;
        }
        else if(type.toLowerCase().equals(TYPE_STRING))
        {
            return  String.class;
        }
        else if(type.toLowerCase().equals(TYPE_BOOLEAN))
        {
            return  boolean.class;
        }
        else if(type.toLowerCase().equals(TYPE_DOUBLE))
        {
            return  double.class;
        }
        else if(type.toLowerCase().equals(TYPE_FLOAT))
        {
            return  float.class;
        }
        else if(type.toLowerCase().equals(TYPE_DATE))
        {
            return  Date.class;
        }
        else if(type.toLowerCase().equals(TYPE_OBJECT))
        {
            return  Object.class;
        }
        else if(type.toLowerCase().equals(TYPE_ARRAYLIST))
        {
            return  ArrayList.class;
        }
        else if(type.toLowerCase().equals(TYPE_BIGDECIMAL))
        {
            return  BigDecimal.class;
        }
        else
        {
            return null;
        }
    }
    
    /**
     * in the xml file of a rule 'type' and 'value' are specified.
     * using these two components, a corresponding object is created. these objects will be passed
     * into a method as an array of objects, so that the method of the objects can be invoked.
     * 
     * @param type		the name of the type according to constants defined in this class		
     * @param value		the value for the object to be created
     * @return			an object corresponding to the type and value specified. return null if type is unknown or a conversion can not be done.
     */
    public static Object getObject(String type, String value)
    {
        if(type.toLowerCase().equals(TYPE_INTEGER))
        {
        	int i=0;
        	try
        	{
        		i = Integer.parseInt(value);
        	}
        	catch(Exception ex)
        	{
        	}
            return Integer.valueOf(i);
        }
        else if(type.toLowerCase().equals(TYPE_LONG))
        {
        	long l=0;
        	try
        	{
        		l = Long.parseLong(value);
        	}
        	catch(Exception ex)
        	{
        	}
            return  Long.valueOf(l);
        }
        else if(type.toLowerCase().equals(TYPE_STRING))
        {
            return  value;
        }
        else if(type.toLowerCase().equals(TYPE_BOOLEAN))
        {
            boolean b= false;
            try
            {
            	b = Boolean.parseBoolean(value);
            }
            catch(Exception ex)
        	{
        	}
            return  Boolean.valueOf(b);
        }
        else if(type.toLowerCase().equals(TYPE_DOUBLE))
        {
            double d=0;
            try
            {
            	d = Double.parseDouble(value);
            }
            catch(Exception ex)
        	{
        	}
            return  Double.valueOf(d);
        }
        else if(type.toLowerCase().equals(TYPE_FLOAT))
        {
            float f = 0;
            try
            {
            	f = Float.parseFloat(value);
            }
            catch(Exception ex)
        	{
        	}
            return  Float.valueOf(f);
        }
        else if(type.toLowerCase().equals(TYPE_BIGDECIMAL))
        {
        	BigDecimal bd= new BigDecimal(0);
        	try
        	{
        		bd = new BigDecimal(value);
        	}
        	catch(Exception ex)
        	{
        	}
            return  bd;
        }
        // try to convert the value to a datetime value first
        else if(type.toLowerCase().equals(TYPE_DATE))
        {
        	SimpleDateFormat sdtf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        	try
        	{
        		Date d = sdtf.parse(value);
                return  d;
        	}
        	// if it does not work, try to make a date from the value
        	catch(Exception ex)
        	{
        		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            	try
            	{
            		Date d = sdf.parse(value);
                    return  d;
            	}
            	catch(Exception ex2)
            	{
            		return null;
            	}
        	}
        }
        else
        {
            return null;
        }
    }
    
    /**
     * evaluates which class type the relevant object is
     * 
     * @param object		an object		
     * @return				the type of the object
     * @throws Exception	when a wrong field type was detected
     */
    public static String getObjectType(Object object) throws Exception
    {
    	if(object instanceof String)
    	{
    		return TYPE_STRING;
    	}
    	else if(object instanceof Integer)
    	{
    		return TYPE_INTEGER;
    	}
    	else if(object instanceof Long)
    	{
    		return TYPE_LONG;
    	}
    	else if(object instanceof Float)
    	{
    		return TYPE_FLOAT;
    	}
    	else if(object instanceof Double)
    	{
    		return TYPE_DOUBLE;
    	}
    	else if(object instanceof BigDecimal)
    	{
    		return TYPE_BIGDECIMAL;
    	}
    	else if(object instanceof Boolean)
    	{
    		return TYPE_BOOLEAN;
    	}
    	else if(object instanceof Date)
    	{
    		return TYPE_DATE;
    	}
    	else if(object == null)
    	{
    		return null;
    	}
    	else
    	{
    		throw new Exception("object has an invalid field type");
    	}
    }
    
    /**
     * Invokes the method on the given object and with the specified parameters.
     * 
     * @param object		the object to invoke the method on
     * @param method		the method to be invoked
     * @param parameters	the parameters required for the method
     * @return				the object that is created by invoking the method
     * @throws Exception	an exception if the method can not be invoked
     */
    public static Object invokeObjectMethod(Object object, Method method, ArrayList <Parameter> parameters) throws Exception
    {
    	 // create the actual value of the method parameter/argument
        Object parameterObjects[] = new Object[parameters.size()];
        // add the additional parameters as defined in the xml file
        for(int i=0;i<parameters.size();i++)
        {
        	Parameter parameter = parameters.get(i);
        	if(!parameter.isSetterValue())
        	{
        		parameterObjects[i] = ClassUtility.getObject(parameter.getType(), parameter.getValue());
        	}
        	else
        	{
        		parameterObjects[i] = object;
        	}
        }
        // invoke the method
        Object result;
        try
        {
        	result = method.invoke(object,parameterObjects);
        }
        catch(Exception ex)
        {
        	throw new Exception("error invoking object: " + object.getClass().getName() + " method: " + method.getName());
        }
        return result;
        
    }
    
    /**
     * Invokes the setter method on the given object and with the specified parameters.
     * 
     * @param object		the object to invoke the method on
     * @param resultObject	the resulting object
     * @param method		the method to be invoked
     * @param parameters	the parameters required for the method
     * @return				the object that is created by invoking the method
     * @throws Exception	an exception if the method can not be invoked
     */
    public static Object invokeObjectSetterMethod(Object object, Object resultObject, Method method, ArrayList <Parameter> parameters) throws Exception
    {
    	 // create the actual value of the method parameter/argument
        Object parameterObjects[] = new Object[parameters.size()];
        // add the additional parameters as defined in the xml file
        for(int i=0;i<parameters.size();i++)
        {
        	Parameter parameter = parameters.get(i);
        	if(!parameter.isSetterValue())
        	{
        		parameterObjects[i] = ClassUtility.getObject(parameter.getType(), parameter.getValue());
        	}
        	else
        	{
        		parameterObjects[i] = resultObject;
        	}
        }

        // invoke the method
        Object result;
        try
        {
        	result = method.invoke(object,parameterObjects);
        }
        catch(Exception ex)
        {
        	throw new Exception("error invoking object: " + object.getClass().getName() + " setter method: " + method.getName());
        }
        return result;
        
    }
    
    /**
     * Creates a list of parameter values.
     * 
     * @param parameters	list of parameters
     * @return				a list of parameter values corresponding to the parameters provided
     */
    public static Object getParameterValuesArray(ArrayList <Parameter> parameters)
    {
    	 // create the actual value of the method parameter/argument
        Object parameterValues[] = new Object[parameters.size()];
        // add the additional parameters as defined in the xml file
        for(int i=0;i<parameters.size();i++)
        {
            parameterValues[i] = ClassUtility.getObject(parameters.get(i).getType(), parameters.get(i).getValue());
        }
    	return parameterValues;
    }
    
    
    /**
     * Creates a method of an action.
     * 
     * @param action		the action for which the method is created
     * @return				a method for the action
     * @throws Exception	throws an exception if the method can not be created
     */
    public static Method getActionMethod(XmlAction action) throws Exception
    {
    	Class<?> parameterTypes[]=null;
    	int arraySize=0;
    	if(action.getActionGetterObjects().size()>0)
    	{
    		parameterTypes = new Class[1 + action.getParameters().size() + action.getActionGetterObjects().size()];
    		parameterTypes[0] = action.getClass();
    		for(int i=0;i<action.getActionGetterObjects().size();i++)
    		{
    			parameterTypes[1+i] = ClassUtility.getClass(action.getActionGetterObjects().get(i).getReturnType());
    		}
    		arraySize= 1 + action.getActionGetterObjects().size();
    	}
    	else
    	{
    		// action might not have a getter object 
			parameterTypes    = new Class[action.getParameters().size() + 1];
			parameterTypes[0] = action.getClass();
			arraySize=1;
    	}
    	// add the parameter types
		for(int i=0;i<action.getParameters().size();i++)
        {
        	parameterTypes[i+arraySize]= ClassUtility.getClass(action.getParameters().get(i).getType());
        }
		Object object = Class.forName(action.getClassName()).newInstance();
        return  object.getClass().getMethod(action.getMethodName(),parameterTypes);
    }
    
    /**
     * Invokes a method of an action.
     * 
     * @param action		the action for which the method is created
     * @param object		the object to invoke the action on
     * @param methodAction	the method to invoke
     * @return				the resulting object of the invoked method
     * @throws Exception	throws an exception if the method can not be invoked
     */
    public static Object invokeActionMethod(XmlAction action, Object[] object, Method methodAction) throws Exception
    {
    	int numberOfObjects=0;
    	if(object!=null)
    	{
    		numberOfObjects=object.length;
    	}
    	int numberOfAdditionalParameters= 1 + numberOfObjects;
    	
        Object parameterObjects[] = new Object[action.getParameters().size() + numberOfAdditionalParameters];
        // first parameter is the action
        parameterObjects[0] = action;
    	// if the getter object of the action is not null we take it
        if(object!=null)
        {
        	for(int i=0;i<object.length;i++)
        	{
        		parameterObjects[1+i]= object[i];
        	}
        }
        // the remaining arguments are the additional parameters that the method of the action expects
        for(int i=0;i< action.getParameters().size();i++)
        {
        	parameterObjects[i+numberOfAdditionalParameters] = ClassUtility.getObject(action.getParameters().get(i).getType(), action.getParameters().get(i).getValue());
        }
        // invoke the method of the action object
        Object result;
        try
        {
        	result = methodAction.invoke(Class.forName(action.getClassName()).newInstance(),parameterObjects);
        }
        catch(Exception ex)
        {
        	throw new Exception("error invoking action: " + action.getClassName() + " method: " + methodAction.getName());
        }
		// return the result
        return result;
    }
    
    /**
     * Get the method corresponding to the given action and the concrete action object.
     * 
     * @param action			the action for which to create the method
     * @param actionObject		the concrete action object
     * @return					the method created from the action
     * @throws Exception		throws an exception if the method can not be retrieved for the object
     */
    public static Method getObjectMethod(XmlAction action, ActionObject actionObject) throws Exception
    {
    	Class<?> parameterTypes[]= new Class[actionObject.getParameters().size()];
    	//parameterTypes[0]= Object.class;
        // add the parameters of the action object as defined in the xml file
    	for(int i=0;i<actionObject.getParameters().size();i++)
        {
        	parameterTypes[i]= ClassUtility.getClass(actionObject.getParameters().get(i).getType());
        }
        
    	Class<?> cl = Class.forName(actionObject.getClassName());
    	
        //Object object = cl.newInstance();
    	return cl.getMethod(actionObject.getMethodName(),parameterTypes);
    }
    
    /**
     * Get all methods of a class.
     * 
     * @param className			the name of the class, including package information
     * @return					an array of methods for the given class
     * @throws Exception		throws an exception if the class was not found
     */
    public static Method[] getMethods(String className) throws Exception
	{
		Class <?> check = Class.forName(className);
		return check.getMethods();
	}

    /**
     * Get the checkId of a check class.
     * 
     * The id of a check in the database uniquely identifies the check and should not change
     * as the business rules are based on them.
     * 
     * @param clazz				the class to use
     * @return					the static checkId of the class
     * @throws Exception		throws an exception if the class was not found or the field is undefined
     */
    public static long getCheckId(Class<GenericCheck> clazz) throws Exception
	{
		java.lang.reflect.Field field = clazz.getField("checkId");
		return field.getLong(null);
	}
}
