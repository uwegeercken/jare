package com.datamelt.rules.core;

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

/**
 * Defines a reference field as it is used and output by the BusinessRulesMaintenance Tool.
 * 
 * It basically defines one field of a row of fields from a record (e.g. a line from a CSV file).
 * 
 * The field has a name and Java type assigned to it. The ruleengine will use this information
 * to correctly process the value of the field.
 * 
 * @author uwe geercken
 */
public class ReferenceField
{
	public static final int FIELD_TYPE_ID_STRING		= 1;
	public static final int FIELD_TYPE_ID_INTEGER		= 2;
	public static final int FIELD_TYPE_ID_FLOAT		= 3;
	public static final int FIELD_TYPE_ID_DOUBLE		= 4;
	public static final int FIELD_TYPE_ID_BOOLEAN		= 5;
	public static final int FIELD_TYPE_ID_LONG		= 6;
	public static final int FIELD_TYPE_ID_BIGDECIMAL	= 7;
	public static final int FIELD_TYPE_ID_DATE		= 8;
	
	public static final String[] FIELDTYPES = {"string","integer","float", "double","boolean","long","bigdecimal","date"};
	
	private String name;
	private String nameDescriptive;
	private String description;
	private int javaTypeId;

	public ReferenceField()
	{
		
	}
	
	public String getName()
	{
		return name;
	}

	public void setName(String name) 
	{
		this.name = name;
	}

	
	public String getNameDescriptive() 
	{
		return nameDescriptive;
	}

	public void setNameDescriptive(String nameDescriptive) 
	{
		this.nameDescriptive = nameDescriptive;
	}

	public long getJavaTypeId() 
	{
		return javaTypeId;
	}

	public String getJavaTypeName() 
	{
		return FIELDTYPES[javaTypeId];
	}

	public void setJavaTypeId(int javaTypeId)
	{
		this.javaTypeId = javaTypeId;
	}

	public String getDescription() 
	{
		return description;
	}

	public void setDescription(String description) 
	{
		this.description = description;
	}

}
