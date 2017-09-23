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
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericRecord;

/**
 * the RowFieldCollection class represents a container with RowField objects.
 * 
 * a RowField object holds the information of the name and value of a field.
 * 
 * @author uwe geercken
 */
public class RowFieldCollection implements Serializable
{
	private long id;
    private boolean collectionUpdated;
    private boolean rowFailed=false;
    private ArrayList<RowField> fields = new ArrayList<RowField>();
    private HeaderRow header = new HeaderRow();
    
    public static final long serialVersionUID = 1964070314;
    
    /**
     * empty default constructor
     * 
     */
    public RowFieldCollection()
    {
    }
    
    /**
     * constructor that takes an array of field names as parameter
     * 
     * the field names define the names of the fields of the row
     *  
     * @param fieldNames	an array of field names
     */
    public RowFieldCollection(String[] fieldNames)
    {
    	header = new HeaderRow(fieldNames);
    }
    
    /**
     * constructor that takes an array of row fields as parameter.
     * 
     * as no header fields are specified here, the fields will be added with a default name
     *  
     * @param fields	array of row fields
     */
    public RowFieldCollection(ArrayList<RowField> fields)
    {
        setFields(fields);
    }
    
    /**
     * constructor that takes an array of field names and an array of values as parameter.
     * RowField objects will be created and added the the list of fields. 
     *  
     * @param fieldNames	an array of field names
     * @param fields		an array of field values
     */
    public RowFieldCollection(String[] fieldNames,String[] fields)
    {
    	header = new HeaderRow(fieldNames);
    	setFields(fields);
    }
    
    /**
     * constructor that takes an array of field names and an array of values as parameter.
     * RowField objects will be created and added the the list of fields. 
     *  
     * @param header		the header row
     * @param fields		an array of field values
     */
    public RowFieldCollection(HeaderRow header,String[] fields)
    {
    	this.header = header;
    	setFields(fields);
    }
    
    /**
     * constructor that takes a HeaderRow and an array of objects/values as parameter.
     * RowField objects will be created and added the the list of fields.
     *  
     * @param header		the header row object
     * @param fields		an array of field values
     */
    public RowFieldCollection(HeaderRow header,Object[] fields)
    {
    	this.header = header;
        setFields(fields);
    }
    
    /**
     * constructor that takes an array of field names and an array of objects/values as parameter.
     * RowField objects will be created and added the the list of fields.
     *  
     * @param header		the array of field names
     * @param fields		an array of field values
     */
    public RowFieldCollection(String[] fieldNames,Object[] fields)
    {
    	this.header = new HeaderRow(fieldNames);
        setFields(fields);
    }
    
    /**
     * constructor that takes an array of field names and an array of objects/values as parameter.
     * RowField objects will be created and added the the list of fields.
     *  
     * @param header		the array of field names
     * @param fields		an array of field values
     */
    public RowFieldCollection(ArrayList<String> fieldNames,Object[] fields)
    {
    	this.header = new HeaderRow(fieldNames);
        setFields(fields);
    }
    
    /**
     * constructor that takes an avro generic record and the avro schema as parameter.
     * RowField objects will be created and added the the list of fields.
     *  
     * @param record		an avro record
     * @param fields		the avro schema corresponding to the record
     */
    public RowFieldCollection(GenericRecord record, Schema schema) throws Exception
    {
    	AvroSchemaUtility schemaUtility = new AvroSchemaUtility(schema);
    	String[] fieldNames = schemaUtility.getFieldNames();
    	Object[] objects = schemaUtility.getGenericRecordData(record);
    	
    	this.header = new HeaderRow(fieldNames);
        setFields(objects);
    }
    
    /**
     * constructor that takes an array of objects as parameter
     *  
     * @param fields	array of fields values
     */
    public RowFieldCollection(Object[] fields)
    {
    	setFields(fields);
    }
    
    /**
     * returns the array of fields that belong to the given row object
     *  
     * @return		array of fields
     */
    public ArrayList<RowField> getFields()
    {
        return fields;
    }
    
    /**
     * sets the array of fields that belong to the given row object
     *  
     * @param fields	sets the fields to the given array
     */
    public void setFields(ArrayList<RowField> fields)
    {
        this.fields = fields;
    }
    
    /**
     * sets the array of fields that belong to the given row object.
     * if we have field names (a header) then the number of fields in the header will steer the number of fields.
     * 
     * if we have no field names (header) then the fields will get the name: DEFAULT_FIELDNAME plus an underbar character plus a running number (e.g. field_3)
     *  
     * @param fields	an array of objects
     */
    public void setFields(Object[] fields)
    {
    	// clear the existing list of fields
    	this.fields.clear();
    	
    	String[] headerFields = header.getFieldNames();
    	
    	// if the static fieldNames array is defined
    	if(headerFields!=null)
    	{
	    	for(int i=0;i<headerFields.length;i++)
	        {
	    		// only add field if there is one. this could happen if the array of field names
	    		// specified more fields than the actual row has
	    		if(i<fields.length)
	        	{
	    			this.fields.add(new RowField(headerFields[i], fields[i]));
	        	}
	        }
    	} 
    	// otherwise give each field a default name and a running number
    	else
    	{
    		for(int i=0;i<fields.length;i++)
            {
    			this.fields.add(new RowField(RowField.DEFAULT_FIELDNAME +"_" + i, fields[i]));
            }
    	}
    }
    
    /**
     * Add a field to the array of fields.
     * 
     * @param field		a field to be added
     */
    public void addField(RowField field)
    {
    	// field needs to be added to the list of header fields
    	if(header!=null)
    	{
    		header.addField(field.getName());
    	}
    	
    	// add the field to the list of fields
    	fields.add(field);
    }
    
    
    /**
     * Add a field to the array of fields by specifying the name and value.
     * 
     * @param fieldName		the name of the field
     * @param fieldValue	the valu of the field
     */
    public void addField(String fieldName, Object fieldValue)
    {
    	// field needs to be added to the list of header fields    	
    	if(header!=null)
    	{
    		header.addField(fieldName);
    	}
    	
    	// add the field to the list of fields
    	if(fieldValue!=null)
    	{
    		fields.add(new RowField(fieldName, fieldValue));
    	}
    	else
    	{
    		fields.add(new RowField(fieldName));
    	}
    }
    
    /**
     * Remove a field from the array of fields.
     * 
     * @param fieldName					the name of the field to remove
     * @throws FieldNotFoundException	thrown when the specified field does not exist
     */
    public void removeField(String fieldName) throws FieldNotFoundException
    {
    	int found=-1;
    	for(int i=0;i<fields.size();i++)
    	{
    		RowField field = fields.get(i);
    		if(field.getName().equals(fieldName))
    		{
    			found = i;
    			break;
    		}
    	}
    	if(found> -1)
    	{
    		fields.remove(found);
    	}
    	else
    	{
    		throw new FieldNotFoundException("field: [" + fieldName + "] not found");

    	}
    }

    /**
     * Remove a field from the array of fields.
     * 
     * @param index		the index of the field to remove
     */
    public void removeField(int index)
    {
   		fields.remove(index);
    }
    
    /**
     * Remove all fields from the array of fields.
     * 
     */
    public void removeFields()
    {
   		fields.clear();
    }

    /**
     * returns the number of fields that belong to the given row object
     *  
     * @return	the number of fields in the array
     */
    public int getNumberOfFields()
    {
        if(fields!=null)
        {
        	return fields.size();
        }
        else
        {
        	return 0;
        }
    }
    
    /**
     * returns a field from the array of fields by specifying its index.
     *  
     * @param number		the index of the field to retrieve
     * @return				a field from the array
     * @throws Exception	when the field was not found
     */
    public RowField getField(int number) throws Exception
    {
        if(number<0 || number>fields.size()-1)
        {
            throw new FieldNotFoundException("field: [" + number + "] not found");
        }
        return fields.get(number);
    }
    
    /**
     * returns a field from the array of fields  by specifying its name.
     * 
     * @param name			the name of the field to retrieve
     * @return				a field from the array
     * @throws Exception	when the field was not found
     */
    public RowField getField(String name) throws Exception
    {
    	int fieldIndex = header.getFieldIndex(name);
    	if(fieldIndex>=0)
    	{
    		return fields.get(fieldIndex);
    	}
    	else
    	{
    		throw new FieldNotFoundException("field: [" + name + "] not found");

    	}
    }
    
    /**
     * returns the field value from the array of fields by specifying its name
     *  
     * @param name			the name of the field
     * @return				an object representing the field value
     * @throws Exception	exception when the field was not found
     */
    public Object getFieldValue(String name) throws Exception
    {
    	Integer fieldIndex = header.getFieldIndex(name);
    	if(fieldIndex>=0)
    	{
    		return fields.get(fieldIndex).getValue();
    	}
    	else
    	{
    		throw new FieldNotFoundException("field: [" + name + "] not found");

    	}
    }
    
    /**
     * returns the field value from the array of fields by specifying its index.
     *  
     * @param index			the index of the field in the array
     * @return				an object representing the field value
     * @throws Exception	exception when the field was not found
     */
    public Object getFieldValue(int index) throws Exception
    {
   		Object obj = fields.get(index).getValue();
    	return obj;
    }
    
    /**
     * sets the field value of the field by specifying its name and value
     *  
     * @param name			the name of the field
     * @param object		the value of the fields
     * @throws Exception	exception when the field was not found
     */
    public void setFieldValue(String name,Object object) throws Exception
    {
    	int found=-1;
    	for(int i=0;i<fields.size();i++)
    	{
    		RowField field = fields.get(i);
    		if(field.getName().equals(name))
    		{
    			found = i;
    			field.setValue(object);
    			field.setUpdated(true);
    			collectionUpdated=true;
    			break;
    		}
    	}
    	if(found== -1)
    	{
    		throw new FieldNotFoundException("field: [" + name + "] not found");
    	}
    }
    
    /**
     * sets the field value of the field by specifying its name and value
     *  
     * @param name			the name of the field
     * @param value			the value of the field
     * @throws Exception	exception when the field was not found
     */
    public void setFieldValue(String name,double value) throws Exception
    {
    	int found=-1;
    	for(int i=0;i<fields.size();i++)
    	{
    		RowField field = fields.get(i);
    		if(field.getName().equals(name))
    		{
    			found = i;
    			field.setValue(value);
    			field.setUpdated(true);
    			collectionUpdated=true;
    			break;
    		}
    	}
    	if(found== -1)
    	{
    		throw new FieldNotFoundException("field: [" + name + "] not found");
    	}
    }
    
    /**
     * sets the field value of the field by specifying its name and value
     *  
     * @param name			the name of the field
     * @param value			the value of the field
     * @throws Exception	exception when the field was not found
     */
    public void setFieldValue(String name,float value) throws Exception
    {
    	int found=-1;
    	for(int i=0;i<fields.size();i++)
    	{
    		RowField field = fields.get(i);
    		if(field.getName().equals(name))
    		{
    			found = i;
    			field.setValue(value);
    			field.setUpdated(true);
    			collectionUpdated=true;
    			break;
    		}
    	}
    	if(found== -1)
    	{
    		throw new FieldNotFoundException("field: [" + name + "] not found");
    	}
    }
    
    /**
     * sets the field value of the field by specifying its name and value
     *  
     * @param name			the name of the field
     * @param value			the value of the field
     * @throws Exception	exception when the field was not found
     */
    public void setFieldValue(String name,int value) throws Exception
    {
    	int found=-1;
    	for(int i=0;i<fields.size();i++)
    	{
    		RowField field = fields.get(i);
    		if(field.getName().equals(name))
    		{
    			found = i;
    			field.setValue(value);
    			field.setUpdated(true);
    			collectionUpdated=true;
    			break;
    		}
    	}
    	if(found== -1)
    	{
    		throw new FieldNotFoundException("field: [" + name + "] not found");
    	}
    }
    
    /**
     * sets the field value of the field by specifying its name and value
     *  
     * @param name			the name of the field
     * @param value			the value of the field
     * @throws Exception	exception when the field was not found
     */
    public void setFieldValue(String name,String value) throws Exception
    {
    	int found=-1;
    	for(int i=0;i<fields.size();i++)
    	{
    		RowField field = fields.get(i);
    		if(field.getName().equals(name))
    		{
    			found = i;
    			field.setValue(value);
    			field.setUpdated(true);
    			collectionUpdated=true;
    			break;
    		}
    	}
    	if(found== -1)
    	{
    		throw new FieldNotFoundException("field: [" + name + "] not found");
    	}
    }
    
    /**
     * sets the field value of the field by specifying its name and value
     *  
     * @param name			the name of the field
     * @param value			the value of the field
     * @throws Exception	exception when the field was not found
     */
    public void setFieldValue(String name,boolean value) throws Exception
    {
    	int found=-1;
    	for(int i=0;i<fields.size();i++)
    	{
    		RowField field = fields.get(i);
    		if(field.getName().equals(name))
    		{
    			found = i;
    			field.setValue(value);
    			field.setUpdated(true);
    			collectionUpdated=true;
    			break;
    		}
    	}
    	if(found== -1)
    	{
    		throw new FieldNotFoundException("field: [" + name + "] not found");
    	}
    }
    
    /**
     * sets the field value of the field by specifying its name and value
     *  
     * @param name			the name of the field
     * @param value			the value of the field
     * @throws Exception	exception when the field was not found
     */
    public void setFieldValue(String name,long value) throws Exception
    {
    	int found=-1;
    	for(int i=0;i<fields.size();i++)
    	{
    		RowField field = fields.get(i);
    		if(field.getName().equals(name))
    		{
    			found = i;
    			field.setValue(value);
    			field.setUpdated(true);
    			collectionUpdated=true;
    			break;
    		}
    	}
    	if(found== -1)
    	{
    		throw new FieldNotFoundException("field: [" + name + "] not found");
    	}
    }
    
    /**
     * sets the field value of the field by specifying its name and value
     *  
     * @param name			the name of the field
     * @param value			the value of the field
     * @throws Exception	exception when the field was not found
     */
    public void setFieldValue(String name,BigDecimal value) throws Exception
    {
    	int found=-1;
    	for(int i=0;i<fields.size();i++)
    	{
    		RowField field = fields.get(i);
    		if(field.getName().equals(name))
    		{
    			found = i;
    			field.setValue(value);
    			field.setUpdated(true);
    			collectionUpdated=true;
    			break;
    		}
    	}
    	if(found== -1)
    	{
    		throw new FieldNotFoundException("field: [" + name + "] not found");
    	}
    }
    
    /**
     * sets the field value of the field by specifying its name and value
     *  
     * @param name			the name of the field
     * @param value			the value of the field
     * @throws Exception	exception when the field was not found
     */
    public void setFieldValue(String name,Date value) throws Exception
    {
    	int found=-1;
    	for(int i=0;i<fields.size();i++)
    	{
    		RowField field = fields.get(i);
    		if(field.getName().equals(name))
    		{
    			found = i;
    			field.setValue(value);
    			field.setUpdated(true);
    			collectionUpdated=true;
    			break;
    		}
    	}
    	if(found== -1)
    	{
    		throw new FieldNotFoundException("field: [" + name + "] not found");
    	}
    }
    
    /**
     * sets the field value of the field by specifying its name and value
     *  
     * @param name			the name of the field
     * @param value			the value of the field
     * @throws Exception	exception when the field was not found
     */
    public void setFieldValue(String name,BigInteger value) throws Exception
    {
    	int found=-1;
    	for(int i=0;i<fields.size();i++)
    	{
    		RowField field = fields.get(i);
    		if(field.getName().equals(name))
    		{
    			found = i;
    			field.setValue(value);
    			field.setUpdated(true);
    			collectionUpdated=true;
    			break;
    		}
    	}
    	if(found== -1)
    	{
    		throw new FieldNotFoundException("field: [" + name + "] not found");
    	}
    }

    /**
     * sets the field value of the field by specifying its index and value
     *  
     * @param index			the index of the field
     * @param value			the value of the field
     * @throws Exception	exception when the field was not found
     */
    public void setFieldValue(int index,String value) throws Exception
    {
   		if(index>=0 && index < fields.size())
    	{
   			RowField field = fields.get(index);
   			field.setValue(value);
   			field.setUpdated(true);
   			collectionUpdated=true;
   		}
   		else
    	{
    		throw new FieldNotFoundException("field with index: [" + index + "] not found");
    	}
    }
    
    /**
     * sets the field value of the field by specifying its index and value
     *  
     * @param index			the index of the field
     * @param value			the value of the field
     * @throws Exception	exception when the field was not found
     */
    public void setFieldValue(int index,BigInteger value) throws Exception
    {
   		if(index>=0 && index < fields.size())
    	{
   			RowField field = fields.get(index);
   			field.setValue(value);
   			field.setUpdated(true);
   			collectionUpdated=true;
   		}
   		else
    	{
    		throw new FieldNotFoundException("field with index: [" + index + "] not found");
    	}
    }
    
    /**
     * sets the field value of the field by specifying its index and value
     *  
     * @param index			the index of the field
     * @param value			the value of the field
     * @throws Exception	exception when the field was not found
     */
    public void setFieldValue(int index,BigDecimal value) throws Exception
    {
   		if(index>=0 && index < fields.size())
    	{
   			RowField field = fields.get(index);
   			field.setValue(value);
   			field.setUpdated(true);
   			collectionUpdated=true;
   		}
   		else
    	{
    		throw new FieldNotFoundException("field with index: [" + index + "] not found");
    	}
    }
    
    /**
     * sets the field value of the field by specifying its index and value
     *  
     * @param index			the index of the field
     * @param value			the value of the field
     * @throws Exception	exception when the field was not found
     */
    public void setFieldValue(int index,Object value) throws Exception
    {
   		if(index>=0 && index < fields.size())
    	{
   			RowField field = fields.get(index);
   			field.setValue(value);
   			field.setUpdated(true);
   			collectionUpdated=true;
   		}
   		else
    	{
    		throw new FieldNotFoundException("field with index: [" + index + "] not found");
    	}
    }
    
    /**
     * sets the field value of the field by specifying its index and value
     *  
     * @param index			the index of the field
     * @param value			the value of the field
     * @throws Exception	exception when the field was not found
     */
    public void setFieldValue(int index,int value) throws Exception
    {
   		if(index>=0 && index < fields.size())
    	{
   			RowField field = fields.get(index);
   			field.setValue(value);
   			field.setUpdated(true);
   			collectionUpdated=true;
   		}
   		else
    	{
    		throw new FieldNotFoundException("field with index: [" + index + "] not found");
    	}
    }
    
    /**
     * sets the field value of the field by specifying its index and value
     *  
     * @param index			the index of the field
     * @param value			the value of the field
     * @throws Exception	exception when the field was not found
     */
    public void setFieldValue(int index,long value) throws Exception
    {
   		if(index>=0 && index < fields.size())
    	{
   			RowField field = fields.get(index);
   			field.setValue(value);
   			field.setUpdated(true);
   			collectionUpdated=true;
   		}
   		else
    	{
    		throw new FieldNotFoundException("field with index: [" + index + "] not found");
    	}
    }

    /**
     * sets the field value of the field by specifying its index and value
     *  
     * @param index			the index of the field
     * @param value			the value of the field
     * @throws Exception	exception when the field was not found
     */
    public void setFieldValue(int index,boolean value) throws Exception
    {
   		if(index>=0 && index < fields.size())
    	{
   			RowField field = fields.get(index);
   			field.setValue(value);
   			field.setUpdated(true);
   			collectionUpdated=true;
   		}
   		else
    	{
    		throw new FieldNotFoundException("field with index: [" + index + "] not found");
    	}
    }
    
    /**
     * sets the field value of the field by specifying its index and value
     *  
     * @param index			the index of the field
     * @param value			the value of the field
     * @throws Exception	exception when the field was not found
     */
    public void setFieldValue(int index,float value) throws Exception
    {
   		if(index>=0 && index < fields.size())
    	{
   			RowField field = fields.get(index);
   			field.setValue(value);
   			field.setUpdated(true);
   			collectionUpdated=true;
   		}
   		else
    	{
    		throw new FieldNotFoundException("field with index: [" + index + "] not found");
    	}
    }
    
    /**
     * sets the field value of the field by specifying its index and value
     *  
     * @param index			the index of the field
     * @param value			the value of the field
     * @throws Exception	exception when the field was not found
     */
    public void setFieldValue(int index,double value) throws Exception
    {
   		if(index>=0 && index < fields.size())
    	{
   			RowField field = fields.get(index);
   			field.setValue(value);
   			field.setUpdated(true);
   			collectionUpdated=true;
   		}
   		else
    	{
    		throw new FieldNotFoundException("field with index: [" + index + "] not found");
    	}
    }
    
    /**
     * sets the field value of the field by specifying its index and value
     *  
     * @param index			the index of the field
     * @param value			the value of the field
     * @throws Exception	exception when the field was not found
     */
    public void setFieldValue(int index,Date value) throws Exception
    {
   		if(index>=0 && index < fields.size())
    	{
   			RowField field = fields.get(index);
   			field.setValue(value);
   			field.setUpdated(true);
   			collectionUpdated=true;
   		}
   		else
    	{
    		throw new FieldNotFoundException("field with index: [" + index + "] not found");
    	}
    }
    
    /**
     * sets if the field has been updated
     *  
     * @param name			the name of the field
     * @param value			the boolean value of the field
     * @throws Exception	exception when the field was not found
     */
    public void setFieldUpdated(String name, boolean value) throws Exception
    {
    	for(int i=0;i<fields.size();i++)
    	{
    		RowField field = fields.get(i);
    		if(field.getName().equals(name))
    		{
    			field.setUpdated(value);
    			break;
    		}
    	}
    }
    
    /**
     * returns the id of the row object
     * 
     * @return	the id of the row
     */
    public long getId()
    {
        return id;
    }
    
    /**
     * sets the id of the row object
     * 
     * @param id	the id of the row
     */
    public void setId(long id)
    {
        this.id = id;
    }

	/**
	 * If the collection of fields has been updated.
	 * 
	 * @return		indicator if the collection of fields has been updated
	 */
	public boolean isCollectionUpdated()
	{
		return collectionUpdated;
	}

	/**
	 * If the row failed.
	 * 
	 * @return	indicator if the row failed
	 */
	public boolean isRowFailed()
	{
		return rowFailed;
	}

	/**
	 * Sets the indicator if the row failed.
	 * 
	 * @param rowFailed		boolean value if the row failed
	 */
	public void setRowFailed(boolean rowFailed) 
	{
		this.rowFailed = rowFailed;
	}
	
	/**
	 * Get the names of the fields as a string.
	 * 
	 * @return		names of all fields
	 */
	public String getFieldNames()
	{
		if(header.getFieldNames()!=null && header.getFieldNames().length>0)
		{
			return Arrays.toString(header.getFieldNames());
		}
		else
		{
			return null;
		}
	}
	
	/**
	 * Gets the values of all fields as a string.
	 * 
	 * @return		values of all fields
	 */
	public String getFieldValues()
	{
		String[] fieldValues = new String[fields.size()];
		for(int i=0;i<fields.size();i++) 
    	{
			if(fields.get(i).getValue()!=null)
			{
				fieldValues[i]=fields.get(i).getValue().toString();
			}
			else
			{
				return null;
			}
    	}
		return Arrays.toString(fieldValues);
	}

	/**
	 * Sets the header row for the fields.
	 * 
	 * If there is a header row available, it should be set before assigning the fields to this collection.
	 * 
	 * @param fieldNames		names of the fields of the header row
	 */
	public void setFieldNames(String[] fieldNames)
	{
		header = new HeaderRow(fieldNames);
	}

	public HeaderRow getHeader()
	{
		return header;
	}

	/**
	 * Sets the header row names for the fields.
	 * 
	 * If there is a header row available, it should be set before assigning the fields to this collection.
	 * 
	 * @param header		HeaderRow object defining the header fields
	 */
	public void setHeader(HeaderRow header)
	{
		this.header = header;
	}
	
	
}
