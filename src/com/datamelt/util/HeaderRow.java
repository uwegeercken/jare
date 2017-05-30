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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * the HeaderRow class represents a header row of field/column names in an ASCII file.
 * 
 * the row is split into its individual fields using the default or a provided separator.
 * 
 * it then contains an array of fields according to the fields of the header row.
 * 
 * @author uwe geercken
 */
public class HeaderRow implements Serializable
{
    private static final String DEFAULT_SEPARATOR = ";";
    private String separator = DEFAULT_SEPARATOR;
    
    // hashmap contains all field names are their index to quickly find a field by its name
    private HashMap<String,Integer> fields = new HashMap<String,Integer>();
    // the array of field names if used to quickly find a field by its index
    
    //private String[] fieldNames;
    private ArrayList<String> fieldNames = new ArrayList<String>();
    
    public static final long serialVersionUID 		= 1964070327;
    
    /**
     * default constructor
     * 
     */
    public HeaderRow()
    {

    }
    
    /**
     * constructor that takes a complete header row as parameter
     * 
     * the row will be split into the individual fields using the default separator
     * 
     * @param header	the complete header row	
     */
    public HeaderRow(String header)
    {
    	this(header, DEFAULT_SEPARATOR);
    }
    
    /**
     * constructor that takes a complete header row as parameter 
     * 
     * the row will be split into the individual fields using the provided separator
     * 
     * @param header	the complete header row
     * @param separator	separator to use to split the header row into fields
     */
    public HeaderRow(String header, String separator)
    {
    	String [] fieldNames = header.split(separator);
    	this.fieldNames = new ArrayList<String>(Arrays.asList(fieldNames));
    	addToFieldNamesHashMap(fieldNames);
    }

    /**
     * constructor that takes an array of names of fields as parameter 
     * 
     * @param fieldNames	array of names of fields
     */
    public HeaderRow(String[] fieldNames)
    {
    	this.fieldNames = new ArrayList<String>(Arrays.asList(fieldNames));
    	addToFieldNamesHashMap(fieldNames);
    }
    
    /**
     * add fields to the list of fields 
     * 
     * @param fieldNames	array of names of fields
     */
    private void addToFieldNamesHashMap(String[] fieldNames)
    {
    	if(fieldNames!=null)
    	{
	    	for (int i=0;i<fieldNames.length;i++)
	    	{
	    		fields.put(fieldNames[i].trim(),i);
	    	}
    	}
    }
    
    /**
     * add a field to the list of fields 
     * 
     * @param fieldName		name of the field to add
     */
    public void addField(String fieldName)
    {
    	int hashMapLargestIndex = fields.size()-1;
    	int newFieldIndex = hashMapLargestIndex +1;
    	fields.put(fieldName, newFieldIndex);

    	fieldNames.add(fieldName);
    }
    
    public HashMap<String, Integer> getFields()
	{
		return fields;
	}

	/**
     * returns the number of fields that belong to the given row object
     *  
     * @return	the total number of fields
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
     * returns a field from the array of fields
     *  
     * @param number		the index number of the field to get
     * @return				the field name as a string
     * @throws Exception	when a wrong field number is specified
     */
    public String getFieldName(int number) throws Exception
    {
    	if(number<0 || number>fieldNames.size()-1)
        {
            throw new FieldNotFoundException("fieldfor index: [" + number + "] not found");
        }
        return fieldNames.get(number);
    }        
    
    /**
     * returns a field from the HashMap of fields
     *  
     * @param name			the name of the field to retrieve
     * @return				the field index number
     * @throws Exception	when a wrong field number is specified
     */
    public int getFieldIndex(String name) throws Exception
    {
    	Integer fieldIndex = fields.get(name);
    	if(fieldIndex!=null)
        {
    		return fieldIndex;
        }
    	else
    	{
            throw new Exception("field [" + name + "] does not exist in header row");

    	}
    }
    
    /**
     * returns the array of header fields
     *  
     * @return				the array of fields
     */
    public String[] getFieldNames()
	{
		return fieldNames.toArray(new String[fieldNames.size()]);
	}

    /**
     * returns the header row as a string.
     * the fields are separated by each other using the defined separator
     *  
     * @return				header row as a string including separators
     */
    public String getFieldNamesWithSeparator()
	{
    	StringBuffer buffer = new StringBuffer();
    	if(fieldNames!=null)
    	{
	    	for(int i=0;i<fieldNames.size();i++)
	    	{
	    		buffer.append(fieldNames.get(i));
	    		if(i<fieldNames.size() -1)
	    		{
	    			buffer.append(separator);
	    		}
	    	}
    	}
		return buffer.toString();
	}
    
	/**
     * returns the separator that was used to divide the fields from each other
     * 
     * @return	the separator used to separate fields from each other
     */
    public String getSeparator()
    {
        return separator;
    }
}
