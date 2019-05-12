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

import java.util.List;

import org.apache.avro.Schema;
import org.apache.avro.Schema.Field;
import org.apache.avro.Schema.Type;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.util.Utf8;

/**
 * utility class to simplify the retrieval of fields and data of an Avro file.
 * 
 * 
 * @author uwe geercken
 */

public class AvroSchemaUtility
{
	private static final String FIELDTYPE_UNION			= "union";
	private static final String COMPLEX_TYPE_RECORD		= "record";
	
	private List<Field> fields;
	
	/*
	 * constructor for AvroSchemaUtility object. 
	 * 
	 * note that only schemas with a complex type = RECORD are processed.
	 * 
	 * @param avroSchema	an Avro schema
	 */
	public AvroSchemaUtility (Schema avroSchema) throws Exception
	{
		Type schemaType = avroSchema.getType();
		if(!schemaType.getName().toLowerCase().equals(COMPLEX_TYPE_RECORD))
		{
			throw new Exception("complex type of the Avro schema is not equal to [RECORD]");
		}
		
		this.fields = avroSchema.getFields();
	}

	/*
	 * method to return an array of field types 
	 * 
	 * @return		array of field types
	 */
	public String[] getFieldTypes() throws Exception
	{
		String[] fieldTypes = new String[fields.size()];
		for(int i=0;i<fields.size();i++)
		{
			Type fieldType = getFieldType(fields.get(i));
			fieldTypes[i] = fieldType.getName();
		}
		return fieldTypes;
	}
	
	/**
	 * retrieves the type of the field from an Avro schema
	 * 
	 * the type of a field is defined in the Avro schema. The type can have one or two values:
	 * either it has simply a single type of string, int, float, etc. (see Avro documentation for possible types)
	 * or it can have the type of "null". If more than these two types are defined, this method will throw an
	 * exception, as the type can not be determined. Otherwise the method will return the type (string, int, etc.).
	 *  
	 * @param schemaField	the Avro schema field
	 * @throws Exception	exception thrown when a field has more than two possible types
	 * @return				the Avro type of the field
	 */
	public Type getFieldType(Field schemaField) throws Exception
	{
		// get the fields schema
		Schema schema = schemaField.schema();
		// get the fields type
		Type fieldType = schema.getType();
		
		// if the field is a union then loop over the types
		if(fieldType.getName().equals(FIELDTYPE_UNION))
		{
			for (Schema s : schema.getTypes())
		    {
				// if we have two types and one is null, then the other one is the type
				if(schema.getTypes().size()==2)
				{
					if (s.getType() != Type.NULL )
					{
						fieldType = s.getType();
					}
				}
				// we have more than two types
				else
				{
					throw new Exception("field: [" + schemaField.name() + "] has more than two possible values for the type - can not determine type");
				}
		    }
		}
		
		return fieldType;
	}
	
	/**
	 * extracts the names of all fields of a complex type=RECORD from
	 * an Avro schema 
	 * 
	 * @return				an array of field names
	 */
	public String[] getFieldNames()
	{
		String[] fieldNames = new String[fields.size()];
		
		for(int i=0;i<fields.size();i++)
		{
			fieldNames[i] = fields.get(i).name();
		}
		
		return fieldNames;
	}
	
	/**
	 * Collects all values of the avro record for the array of field names
	 * 
	 * @param record		an avro record
	 * @return				array of objects
	 */
	public Object[] getGenericRecordData(GenericRecord record)
	{
		Object[] objects = new Object[getFieldNames().length];
		for(int i=0;i<getFieldNames().length;i++)
		{
			objects[i] = record.get(getFieldNames()[i]);
			if(objects[i] instanceof Utf8)
			{
				objects[i] = objects[i].toString();
			}
		}
		return objects;
	}

}
