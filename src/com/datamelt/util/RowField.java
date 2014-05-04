package com.datamelt.util;

import java.math.BigDecimal;
import java.util.Date;


/**
 * the RowField class represents a field defined by a name and value.
 * both name and value are of type string and are converted dynamically
 * using reflection when run by the rule engine.
 * 
 * 
 * @author uwe geercken
 */
public class RowField 
{
	private String name;
	private Object object;
	private boolean updated=false;
	
	public RowField(String name, Object object)
	{
		this.name = name;
		this.object = object;
	}
	
	public RowField(String name, String object)
	{
		this.name = name;
		this.object = object;
	}
	
	public RowField(String name, int object)
	{
		this.name = name;
		this.object = object;
	}
	
	public RowField(String name, long object)
	{
		this.name = name;
		this.object = object;
	}
	
	public RowField(String name, double object)
	{
		this.name = name;
		this.object = object;
	}
	
	public RowField(String name, float object)
	{
		this.name = name;
		this.object = object;
	}
	
	public RowField(String name, boolean object)
	{
		this.name = name;
		this.object = object;
	}

	public RowField(String name, BigDecimal object)
	{
		this.name = name;
		this.object = object;
	}

	public RowField(String name, Date object)
	{
		this.name = name;
		this.object = object;
	}

	public String getName()
	{
		return name;
	}
	
	public void setName(String name) 
	{
		this.name = name;
	}
	
	public Object getValue() 
	{
		return object;
	}
	
	public void setValue(Object object)
	{
		this.object=object;
	}
	
	public boolean isUpdated()
	{
		return updated;
	}

	public void setUpdated(boolean updated) 
	{
		this.updated = updated;
	}
	
	
}
