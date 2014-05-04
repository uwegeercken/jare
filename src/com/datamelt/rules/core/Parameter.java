/*
 * Created on 08.11.2006
 *
 * all code by uwe geercken
 */
package com.datamelt.rules.core;

import org.xml.sax.SAXException;

/**
 * represents a class used to store parameters that will be passed
 * to a method using reflection.
 * 
 * @author uwe geercken
 */
public class Parameter
{
	// type of the parameter: integer, string, float, etc
    private String type;
    // the value of the parameter
    private String value;
    // indicates that this variable is the actual value used
    // by a setter method
    private boolean isSetterValue=false;
    
    /**
     * constructor for creating a parameter. expects the type of the
     * parameter and the value 
     */
    public Parameter(String type, String value) throws SAXException
    {
       this.type = type;
       this.value = value;
       
       if(type == null)
       {
    	   throw new SAXException("parameter type can not be null");
       }
       
    }
    
    /**
     * constructor for creating a parameter. expects the type of the
     * parameter and the value 
     */
    public Parameter(String type, String value, boolean isSetterValue)
    {
       this.type = type;
       this.value = value;
       this.isSetterValue = isSetterValue;
    }
    
    /**
     * returns the type of the parameter 
     */
    public String getType()
    {
        return type;
    }
    
    /**
     * sets the type of the parameter 
     */
    public void setType(String type)
    {
        this.type = type;
    }
    
    /**
     * returns the value of the parameter 
     */
    public String getValue()
    {
        return value;
    }

    /**
     * sets the value of the parameter 
     */
    public void setValue(String value)
    {
        this.value = value;
    }

    /**
     * returns the boolean value indicating, if the parameter value is used to set a value.
     * 
     */
	public boolean isSetterValue()
	{
		return isSetterValue;
	}

	/**
	 * sets if the parameter is a parameter of a method, used to set a value 
	 * 
	 */
	public void setSetterValue(boolean isSetterValue)
	{
		this.isSetterValue = isSetterValue;
	}
    
}
