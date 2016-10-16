/*
 * Created on 16.11.2006
 *
 * all code by uwe geercken
 */
package com.datamelt.rules.core;

import java.io.Serializable;


/**
 * 	a RuleObject identifies an object that will be instantiated and one of its methods
 *  will be run. The result will later in the process be compared to another value
 *  using a rule as defined in the respective rule definition xml file.
 * 
 * RuleObjects are defined in xml files.
 * 
 * @author uwe geercken
 */
public class RuleObject implements Serializable
{
	private String className;
    private String methodName;
    private String methodReturnType;
    private String parameter;
    private String parameterType;
    
    public static final long serialVersionUID = 1964070334;
    
    /**
     * constructor using the classname, name of the method to execute and the return
     * type of the method
     * 
     * @param	className			the name of the class to instantiate
     * @param	methodName			the name of the method to execute
     * @param	methodReturnType	the return type of the method
     */
    public RuleObject(String className, String methodName, String methodReturnType)
    {
        this.className = className;
        this.methodName = methodName;
        this.methodReturnType = methodReturnType;
    }
    
    /**
     * constructor using the classname, name of the method to execute and the return
     * type of the method, the parameter that has to be passed to the method and its type
     * 
     * @param	className			the name of the class to instantiate
     * @param	methodName			the name of the method to execute
     * @param	methodReturnType	the return type of the method
     * @param	parameter			the parameter to be passed
     * @param	parameterType		the type of the parameter
     */
    public RuleObject(String className, String methodName, String methodReturnType, String parameter, String parameterType)
    {
        this.className = className;
        this.methodName = methodName;
        this.methodReturnType = methodReturnType;
        this.parameter = parameter;
        this.parameterType = parameterType;
    }

    /**
     * returns the name of the class that is used 
     * 
     * @return	the name of the class
     */
    public String getClassName()
    {
        return className;
    }
    
    /**
     * sets the name of the class, which will be used to instantiate an object
     * 
     *  @param	className	the name of the class
     */
    public void setClassName(String className)
    {
        this.className = className;
    }
    
    /**
     * returns the name of the method that will be executed
     * 
     * @return	the name of the method
     */
    public String getMethodName()
    {
        return methodName;
    }
    
    /**
     * sets the name of the method that will be executed 
     * 
     * @param	methodName	the name of the method to execute
     */
    public void setMethodName(String methodName)
    {
        this.methodName = methodName;
    }
    
    /**
     * returns the type that the method will return 
     * 
     * @return 	the return type of the method
     */
    public String getMethodReturnType()
    {
        return methodReturnType;
    }
    
    /**
     * sets the return type of the method 
     * 
     * @param methodReturnType 	the return type of the method
     */
    public void setMethodReturnType(String methodReturnType)
    {
        this.methodReturnType = methodReturnType;
    }
    
    /**
     * gets the parameter that has to be passed to the method
     * 
     * @return	the parameter to be passed
     */
    public String getParameter()
    {
        return parameter;
    }
    
    /**
     * sets the parameter that will be passed to the method 
     * 
     * @param	parameter	the parameter to be passed
     */
    public void setParameter(String parameter)
    {
        this.parameter = parameter;
    }
    
    /**
     * returns the type of the parameter that will be passed to the method 
     * 
     * @return		the type of the parameter
     */
    public String getParameterType()
    {
        return parameterType;
    }
    
    /**
     * sets the type of the parameter that will be passed to the method 
     * 
     * @param	parameterType		the type of the parameter
     */
    public void setParameterType(String parameterType)
    {
        this.parameterType = parameterType;
    }
}
