package com.datamelt.util;

/**
 * defines a field in a fixed length ASCII file. fields have a fixed length
 * and position. so the fields are defined by a start position and a length.
 * 
 * optionally a field description may be given.
 */
public class Field 
{
	public String name;
	public String description;
	public int start;
	public int length;
	
	/**
	 * constructor for a field using the name, start and length of the field 
	 */
	public Field(String name, int start, int length)
	{
		this.name = name;
		this.start = start;
		this.length = length;
	}
	
	/**
	 * constructor for a field using the name, description, start and length of the field 
	 */
	public Field(String name, String description, int start, int length)
	{
		this.name = name;
		this.description = description;
		this.start = start;
		this.length = length;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description) 
	{
		this.description = description;
	}

	public int getLength()
	{
		return length;
	}

	public void setLength(int length) 
	{
		this.length = length;
	}

	public String getName() 
	{
		return name;
	}

	public void setName(String name) 
	{
		this.name = name;
	}

	public int getStart() 
	{
		return start;
	}

	public void setStart(int start) 
	{
		this.start = start;
	}

}
