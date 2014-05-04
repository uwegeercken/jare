/*
 * Created on 16.11.2006
 *
 * all code by uwe geercken
 */
package com.datamelt.rules.core;

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
public class ActionObject
{
	public static final int METHOD_GETTER = 0;
	public static final int METHOD_SETTER = 1;
	
    private String className;
    private String methodName;
    private String returnType;
    private int isGetter;
    private ArrayList <Parameter>parameters = new ArrayList<Parameter>();
    
    /**
     * constructor using the classname, name of the method to execute and the return
     * type of the method
     */
    public ActionObject(String className, String methodName)
    {
        this.className = className;
        this.methodName = methodName;
    }
    
     /**
     * returns the name of the class that is used 
     */
    public String getClassName()
    {
        return className;
    }
    
    /**
     * setrs the name of the class, which will be used to instantiate an object 
     */
    public void setClassName(String className)
    {
        this.className = className;
    }
    
    /**
     * returns the name of the method that will be executed
     */
    public String getMethodName()
    {
        return methodName;
    }
    
    /**
     * sets the name of the method that will be executed 
     */
    public void setMethodName(String methodName)
    {
        this.methodName = methodName;
    }
    
 
    /**
     * sets the list of parameters that have to be passed to the method
     * to execute 
     */
    public void setParameters(ArrayList<Parameter> parameters)
    {
        this.parameters = parameters;
    }
    
    /**
     * add another parameter to the list of parameters of the method 
     */
    public void addParameter(Parameter parameter)
    {
        parameters.add(parameter);
    }

	/**
	 * returns the arraylist of parameters belonging to this
	 * actions object
	 * 
	 */
    public ArrayList<Parameter> getParameters()
	{
		return parameters;
	}

	/**
	 * returns a number 0 or 1 indicating if the action object is a getter object,
	 * which means if the method for this object is used to retrieve a value.
	 * 
	 */
	public int isGetter()
	{
		return isGetter;
	}

	/**
	 * sets the value of the isGetter variable. it indicates if the action object
	 * and the corresponding method is used to get a value or not.
	 */
	public void setIsGetter(int isGetter)
	{
		this.isGetter = isGetter;
	}

	public String getReturnType()
	{
		return returnType;
	}

	public void setReturnType(String returnType) 
	{
		this.returnType = returnType;
	}
}
