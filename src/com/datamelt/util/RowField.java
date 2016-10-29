package com.datamelt.util;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;


/**
 * the RowField class represents a field defined by a name and its value (in form of an object).
 * 
 * 
 * @author uwe geercken
 */
public class RowField implements Serializable
{
	private String name;
	private Object object;
	private boolean updated=false;
	
	public static final long serialVersionUID = 1964070313;
	
	/**
     * constructor that takes the name of the field as argument. because no object/value is
     * specified, the object/value is set to null 
     * 
     * @param name	the name of the field
     */
	public RowField(Object name)
	{
		this.name = (String)name;
		this.object = null;
	}
	
	/**
     * constructor that takes the name and an object/value of the field as arguments.
     * 
     * @param name		the name of the field
     * @param object	the object/value of the field
     */
	public RowField(Object name, Object object)
	{
		this.name = (String)name;
		this.object = object;
	}
	
	/**
     * constructor that takes the name and an object/value of the field as arguments.
     * 
     * @param name		the name of the field
     * @param object	the object/value of the field
     */
	public RowField(String name, Object object)
	{
		this.name = name;
		this.object = object;
	}
	
	/**
     * constructor that takes the name and an object/value of the field as arguments.
     * 
     * @param name		the name of the field
     * @param object	the object/value of the field
     */
	public RowField(String name, String object)
	{
		this.name = name;
		this.object = object;
	}
	
	/**
     * constructor that takes the name and an object/value of the field as arguments.
     * 
     * @param name		the name of the field
     * @param object	the object/value of the field
     */
	public RowField(String name, int object)
	{
		this.name = name;
		this.object = object;
	}
	
	/**
     * constructor that takes the name and an object/value of the field as arguments.
     * 
     * @param name		the name of the field
     * @param object	the object/value of the field
     */
	public RowField(String name, long object)
	{
		this.name = name;
		this.object = object;
	}
	
	/**
     * constructor that takes the name and an object/value of the field as arguments.
     * 
     * @param name		the name of the field
     * @param object	the object/value of the field
     */
	public RowField(String name, double object)
	{
		this.name = name;
		this.object = object;
	}
	
	/**
     * constructor that takes the name and an object/value of the field as arguments.
     * 
     * @param name		the name of the field
     * @param object	the object/value of the field
     */
	public RowField(String name, float object)
	{
		this.name = name;
		this.object = object;
	}
	
	/**
     * constructor that takes the name and an object/value of the field as arguments.
     * 
     * @param name		the name of the field
     * @param object	the object/value of the field
     */
	public RowField(String name, boolean object)
	{
		this.name = name;
		this.object = object;
	}

	/**
     * constructor that takes the name and an object/value of the field as arguments.
     * 
     * @param name		the name of the field
     * @param object	the object/value of the field
     */
	public RowField(String name, BigDecimal object)
	{
		this.name = name;
		this.object = object;
	}

	/**
     * constructor that takes the name and an object/value of the field as arguments.
     * 
     * @param name		the name of the field
     * @param object	the object/value of the field
     */
	public RowField(String name, BigInteger object)
	{
		this.name = name;
		this.object = object;
	}

	/**
     * constructor that takes the name and an object/value of the field as arguments.
     * 
     * @param name		the name of the field
     * @param object	the object/value of the field
     */
	public RowField(String name, Date object)
	{
		this.name = name;
		this.object = object;
	}

	/**
     * gets the name of the field
     * 
     * @return the name of the field
     */
	public String getName()
	{
		return name;
	}

	/**
     * sets the name of the field
     * 
     * @param name		the name of the field
     */
	public void setName(String name) 
	{
		this.name = name;
	}
	
	/**
     * returns the value of the field in the form of an object
     * 
     * @return object	the object/value of the field
     */
	public Object getValue() 
	{
		return object;
	}
	
	/**
     * sets the value of the field
     * 
     * @param object		the value of the field
     */
	public void setValue(Object object)
	{
		this.object=object;
	}
	
	/**
     * gets an indication if the field was updated or not
     * 
     * @return 		indicator if the field was updated
     */
	public boolean isUpdated()
	{
		return updated;
	}

	/**
     * sets the indicator that the field was updated to the given value
     * 
     * @param updated		indicator if the field was updated or not
     */
	public void setUpdated(boolean updated) 
	{
		this.updated = updated;
	}
	
	/**
	 * gets the name and value of the field as a string representation
	 * in the form of: fieldname=[fieldvalue]
	 */
	public String toString()
	{
		return name + "=[" + object + "]";
	}
}
