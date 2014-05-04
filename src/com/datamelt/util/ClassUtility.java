package com.datamelt.util;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.datamelt.rules.core.ActionObject;
import com.datamelt.rules.core.Parameter;
import com.datamelt.rules.core.XmlAction;

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
     * in the xml file a <type> and a <value> is specified.
     * using these two components, a corresponding object is created. these objects will be passed
     * into a method as an array of objects, so that the method of the objects can be invoked .
     */
    public static Object getObject(String type, String value)
    {
        if(type.equals(TYPE_INTEGER))
        {
            int i = Integer.parseInt(value);
            return Integer.valueOf(i);
        }
        else if(type.equals(TYPE_LONG))
        {
            long l = Long.parseLong(value);
            return  Long.valueOf(l);
        }
        else if(type.equals(TYPE_STRING))
        {
            return  value;
        }
        else if(type.equals(TYPE_BOOLEAN))
        {
            boolean b = Boolean.parseBoolean(value);
            return  Boolean.valueOf(b);
        }
        else if(type.equals(TYPE_DOUBLE))
        {
            double d = Double.parseDouble(value);
            return  Double.valueOf(d);
        }
        else if(type.equals(TYPE_FLOAT))
        {
            float f = Float.parseFloat(value);
            return  Float.valueOf(f);
        }
        else if(type.equals(TYPE_BIGDECIMAL))
        {
            return  new BigDecimal(value);
        }
        else if(type.equals(TYPE_DATE))
        {
        	SimpleDateFormat sdf = new SimpleDateFormat();
        	try
        	{
        		Date d = sdf.parse(value);
                return  d;
        	}
        	catch(Exception ex)
        	{
        		return null;
        	}
        }
        else
        {
            return null;
        }
    }
    
    
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
        return method.invoke(object,parameterObjects);
    }
    
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
        return method.invoke(object,parameterObjects);
    }
    
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
        Object result = methodAction.invoke(Class.forName(action.getClassName()).newInstance(),parameterObjects);
		// return the result
        return result;
    }
    
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
        return  cl.getMethod(actionObject.getMethodName(),parameterTypes);
    }

}
