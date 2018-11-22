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
 * the row class represents a row in an ASCII file.
 * 
 * it contains an array of fields according to the fields of the line/row.
 * 
 * @author uwe geercken
 */
public class Row implements Serializable
{
    private long id;
    private String [] fields;
    private String seperator = DEFAULT_SEPERATOR;
    private static final String DEFAULT_SEPERATOR = ";";
    
    public static final long serialVersionUID = 1964070327;
    
    public Row()
    {
    	
    }
    
    /**
     * constructor that takes an array of fields as parameter 
     * 
     * @param fields	array of strings to assign to fields
     */
    public Row(String[] fields)
    {
        this.fields = fields;
    }
    
    /**
     * constructor that takes an array of objects as parameter.
     * the objects are converted to their string representation.
     * 
     * @param objects	array of objects to assign to fields
     */
    public Row(Object[] objects)
    {
    	fields = new String[objects.length];
    	for (int i=0;i<objects.length;i++)
    	{
    		if(objects[i]!=null)
    		{
    			fields[i] = objects[i].toString();
    		}
    	}
    }
    
    /**
     * returns the array of fields that belong to the given row object
     *  
     * @return		an array of strings representing the fields
     */
    public String[] getFields()
    {
        return fields;
    }
    
    /**
     * sets the array of fields that belong to the given row object
     *  
     * @param fields	array of strings representing the fields
     */
    public void setFields(String[] fields)
    {
        this.fields = fields;
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
        	return fields.length;
        }
        else
        {
        	return 0;
        }
    }
    
    /**
     * sets the value of a field in the array of fields
     *  
     * @param index			index of the field to change
     * @param value			the value of the field
     * @throws Exception	exception if the field is not in the array of fields
     */
    public void setField(int index, String value)throws Exception
    {
        fields[index]=value;
    }
    
    /**
     * sets the value of a field in the array of fields
     *  
     * @param index			index of the field to change
     * @param value			the value of the field
     * @throws Exception	exception if the field is not in the array of fields
     */
    public void setField(int index, int value)throws Exception
    {
        fields[index]= "" + value;
    }
    
    /**
     * returns a field from the array of fields
     *  
     * @param number		the number of the field to get
     * @return				the field as a string
     * @throws Exception	when a wrong field number is specified
     */
    public String getField(int number) throws Exception
    {
        if(number>fields.length-1)
        {
            throw new Exception("invalid field number: " + number + ". number of fields is: " + fields.length + " - " + getLine());
        }
        return fields[number];
    }
    
    /**
     * returns an integer field from the array of fields
     *  
     * @param number		the number of the field to get
     * @return				the field as an integer
     * @throws Exception	when a wrong field number is specified
     */
    public int getIntegerField(int number) throws Exception
    {
    	if(number>fields.length-1)
        {
            throw new Exception("invalid field number: " + number + ". number of fields is: " + fields.length + " - " + getLine());
        }
   		
    	int i = Integer.parseInt(fields[number]);	
   		return i;
    }
    
    /**
     * returns a long field from the array of fields
     *  
     * @param number		the number of the field to get
     * @return				the field as a long
     * @throws Exception	when a wrong field number is specified
     */
    public long getLongField(int number) throws Exception
    {
        if(number>fields.length-1)
        {
            throw new Exception("invalid field number: " + number + ". number of fields is: " + fields.length + " - " + getLine());
        }
        return Long.parseLong(fields[number]);
    }
    
    /**
     * returns a double field from the array of fields
     * 
     * @param number		the number of the field to get
     * @return				the field as a double
     * @throws Exception	when a wrong field number is specified
     */
    public double getDoubleField(int number) throws Exception
    {
        if(number>fields.length-1)
        {
            throw new Exception("invalid field number: " + number + ". number of fields is: " + fields.length + " - " + getLine());
        }
        return Double.parseDouble(fields[number]);
    }

    /**
     * returns a float field from the array of fields
     *  
     * @param number		the number of the field to get
     * @return				the field as a float
     * @throws Exception	when a wrong field number is specified
     */
    public float getFloatField(int number) throws Exception
    {
        if(number>fields.length-1)
        {
            throw new Exception("invalid field number: " + number + ". number of fields is: " + fields.length + " - " + getLine());
        }
        return Float.parseFloat(fields[number]);
    }

    /**
     * returns a boolean field from the array of fields
     *  
     * @param number		the number of the field to get
     * @return				the field as a boolean
     * @throws Exception	when a wrong field number is specified
     */
    public boolean getBooleanField(int number) throws Exception
    {
        if(number>fields.length-1)
        {
            throw new Exception("invalid field number: " + number + ". number of fields is: " + fields.length + " - " + getLine());
        }
        return Boolean.parseBoolean((fields[number]));
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
     * returns the line as it was retrieved from the originating input file
     * 
     * @return	the complete line as read from an input file
     */
    public String getLine()
    {
        StringBuffer buffer = new StringBuffer();
        for(int i=0;i<fields.length;i++)
        {
            buffer.append(fields[i]);
            if(i<(fields.length -1))
            {
                buffer.append(seperator);
            }
        }
        return buffer.toString();
    }
    
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    public String toString()
    {
    	return getLine();
    }
    
    /**
     * returns the separator that was used to divide the fields from each other
     * 
     * @return	the separator used to separate fields from each other
     */
    public String getSeperator()
    {
        return seperator;
    }
    
    /**
     * sets the separator that is used to divide the fields from each other
     * 
     * @param seperator		separator to be used
     */
    public void setSeperator(String seperator)
    {
        this.seperator = seperator;
    }
    
}
