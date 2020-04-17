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
package com.datamelt.rules.core;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 	a RuleObject identifies an object that will be instantiated and one of its methods
 *  will be run. The result will later in the process be compared to another value
 *  using a rule as defined in the respective rule definition xml file.
 * 
 * RuleObjects are defined in xml files.
 * 
 * @author uwe geercken
 */
public class ActionObject implements Serializable
{
	public static final int METHOD_GETTER = 0;
	public static final int METHOD_SETTER = 1;
	
    private String className;
    private String methodName; 
    private String returnType;
    private int isGetter;
    private ArrayList <Parameter>parameters = new ArrayList<Parameter>();
    
    public static final long serialVersionUID = 1964070322;
    
    /**
     * constructor using the classname, name of the method to execute and the return
     * type of the method
     * 
     * @param className		the name of the class of the action object
     * @param methodName	the name of the method to be executed
     */
    public ActionObject(String className, String methodName)
    {
        this.className = className;
        this.methodName = methodName;
    }
    
     /**
     * returns the name of the class that is used
     * 
     * @return	name of the class
     */
    public String getClassName()
    {
        return className;
    }
    
    /**
     * sets the name of the class, which will be used to instantiate an object
     * 
     * @param className		the name of the class to be used
     */
    public void setClassName(String className)
    {
        this.className = className;
    }
    
    /**
     * returns the name of the method that will be executed
     * 
     * @return	name of the method
     */
    public String getMethodName()
    {
        return methodName;
    }
    
    /**
     * sets the name of the method that will be executed 
     * 
     * @param methodName		the name of the method to be used
     */
    public void setMethodName(String methodName)
    {
        this.methodName = methodName;
    }
    
 
    /**
     * sets the list of parameters that have to be passed to the method
     * to execute 
     * 
     * @param parameters	a list of parameters to be used with the given method
     */
    public void setParameters(ArrayList<Parameter> parameters)
    {
        this.parameters = parameters;
    }
    
    /**
     * add another parameter to the list of parameters of the method 
     * 
     * @param parameter		the parameter to be added
     */
    public void addParameter(Parameter parameter)
    {
        parameters.add(parameter);
    }

	/**
	 * returns the arraylist of parameters belonging to this
	 * actions object
	 * 
	 * @return	a list of parameters used
	 */
    public ArrayList<Parameter> getParameters()
	{
		return parameters;
	}

	/**
	 * returns a number 0 or 1 indicating if the action object is a getter object,
	 * which means if the method for this object is used to retrieve a value.
	 * 
	 * @return	indicator if the method is a getter method
	 */
	public int isGetter()
	{
		return isGetter;
	}

	/**
	 * sets the value of the isGetter variable. it indicates if the action object
	 * and the corresponding method is used to get a value or not.
	 * 
	 * @param isGetter		indicator if the method is a getter method
	 */
	public void setIsGetter(int isGetter)
	{
		this.isGetter = isGetter;
	}

	/**
	 * returns the type of the object returned from the action.
	 * 
	 * Can be String, Date, long, int, etc.
	 * 
	 * 
	 * @return		the type that is returned from the action
	 */
	public String getReturnType()
	{
		return returnType;
	}

	/**
	 * sets the type of the object returned from the action.
	 * 
	 * Can be String, Date, long, int, etc.
	 * 
	 * 
	 * @param returnType		the type that is returned from the action
	 */
	public void setReturnType(String returnType) 
	{
		this.returnType = returnType;
	}
}
