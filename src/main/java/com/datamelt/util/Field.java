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
package com.datamelt.util;

import java.io.Serializable;

/**
 * defines a field in a fixed length ASCII file. fields have a fixed length
 * and position. so the fields are defined by a start position and a length.
 * 
 * optionally a field description may be given.
 */
public class Field implements Serializable
{
	public String name;
	public String description;
	public int start;
	public int length;
	
	public static final long serialVersionUID = 1964070323;
	
	/**
	 * constructor for a field using the name, start and length of the field 
	 *
	 * @param name		the name of the field
	 * @param start		the position where the field starts
	 * @param length	the length of the field
	 */
	public Field(String name, int start, int length)
	{
		this.name = name;
		this.start = start;
		this.length = length;
	}
	
	/**
	 * constructor for a field using the name, description, start and length of the field 
	 *
	 * @param name			the name of the field
	 * @param description	the description of the field
	 * @param start			the position where the field starts
	 * @param length		the length of the field
	 */
	public Field(String name, String description, int start, int length)
	{
		this.name = name;
		this.description = description;
		this.start = start;
		this.length = length;
	}

	/**
	 * retrieves the description of the field
	 * 
	 * @return	the description of the field
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * sets the description of the field
	 * 
	 * @param description	the description of the field
	 */
	public void setDescription(String description) 
	{
		this.description = description;
	}

	/**
	 * retrieves the length of the field
	 * 
	 * @return	the length of the field
	 */
	public int getLength()
	{
		return length;
	}

	/**
	 * sets the length of the field
	 * 
	 * @param length	the length of the field
	 */
	public void setLength(int length) 
	{
		this.length = length;
	}

	/**
	 * retrieves the name of the field
	 * 
	 * @return	the name of the field
	 */
	public String getName() 
	{
		return name;
	}

	/**
	 * sets the name of the field
	 * 
	 * @param name	the name of the field
	 */
	public void setName(String name) 
	{
		this.name = name;
	}

	/**
	 * retrieves the start position of the field
	 * 
	 * @return	the start position of the field
	 */
	public int getStart() 
	{
		return start;
	}

	/**
	 * sets the start position of the field
	 * 
	 * @param start		the start position of the field
	 */
	public void setStart(int start) 
	{
		this.start = start;
	}

}
