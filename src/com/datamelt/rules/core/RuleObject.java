/*
 * Created on 16.11.2006
 *
 * all code by uwe geercken
 */
package com.datamelt.rules.core;

/**
 * 	a RuleObject identifies an object that will be instantiated and one of its methods
 *  will be run. The result will later in the process be compared to another value
 *  using a rule as defined in the respective rule definition xml file.
 * 
 * RuleObjects are defined in xml files.
 * 
 * @author uwe geercken
 */
public class RuleObject
{
    private String className;
    private String methodName;
    private String methodReturnType;
    private String parameter;
    private String parameterType;
    
    /**
     * constructor using the classname, name of the method to execute and the return
     * type of the method
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
     * returns the type that the method will return 
     */
    public String getMethodReturnType()
    {
        return methodReturnType;
    }
    
    /**
     * sets the return type of the method 
     */
    public void setMethodReturnType(String methodReturnType)
    {
        this.methodReturnType = methodReturnType;
    }
    
    //gets the parameter that has to be passed to the method
    public String getParameter()
    {
        return parameter;
    }
    
    /**
     * sets the parameter that will be passed to the method 
     */
    public void setParameter(String parameter)
    {
        this.parameter = parameter;
    }
    
    /**
     * returns the type of the parameter that will be passed to the method 
     */
    public String getParameterType()
    {
        return parameterType;
    }
    
    /**
     * sets the type of the parameter that will be passed to the method 
     */
    public void setParameterType(String parameterType)
    {
        this.parameterType = parameterType;
    }
}
