/*
 * Created on 08.11.2006
 *
 * all code by uwe geercken
 */
package com.datamelt.rules.core;

import java.io.Serializable;

import org.xml.sax.SAXException;

/**
 * represents a class used to store parameters that will be passed
 * to a method using reflection.
 * 
 * @author uwe geercken
 */
public class Parameter implements Serializable
{
	// type of the parameter: integer, string, float, etc
    private String type;
    // the value of the parameter
    private String value;
    // indicates that this variable is the actual value used
    // by a setter method
    private boolean isSetterValue=false;
    
    public static final long serialVersionUID = 1964070326;
    
    /**
     * constructor for creating a parameter. expects the type of the
     * parameter and the value 
     * 
     * @param type			the type of the parameter
     * @param value			the value of the parameter
     * @throws SAXException	exception if the type of the parameter is undefined
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
     * parameter, the value and an indicator if it is a setter value
     * 
     * @param type				the type of the parameter
     * @param value				the value of the parameter
     * @param isSetterValue		indicator if the parameter is belonging to a setter method
 
     */
    public Parameter(String type, String value, boolean isSetterValue)
    {
       this.type = type;
       this.value = value;
       this.isSetterValue = isSetterValue;
    }
    
    /**
     * returns the type of the parameter 
     * 
     * @return 	the type of the parameter
     */
    public String getType()
    {
        return type; 
    }
    
    /**
     * sets the type of the parameter 
     * 
     * @param type		the type of the parameter
     */
    public void setType(String type)
    {
        this.type = type;
    }
    
    /**
     * returns the value of the parameter 
     * 
     * @return 	the value of the parameter
     */
    public String getValue()
    {
        return value;
    }

    /**
     * sets the value of the parameter 
     * 
     * @param value		sets the value of the parameter
     */
    public void setValue(String value)
    {
        this.value = value;
    }

    /**
     * returns the boolean value indicating, if the parameter value is used to set a value.
     * 
     * @return	indicator if it is a setter value
     * 
     */
	public boolean isSetterValue()
	{
		return isSetterValue;
	}

	/**
	 * sets if the parameter is a parameter of a method, used to set a value 
	 * 
	 * @param isSetterValue		sets the indicator if the parameter is used to set a value
	 */
	public void setSetterValue(boolean isSetterValue)
	{
		this.isSetterValue = isSetterValue;
	}
    
}
