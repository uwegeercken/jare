/* All code by Datamelt.com.
 * 
 * Use of this software and code is only allowed after
 * prior permission by Datamelt.com.
 * 
 * All intellectual property rights remain with Datamelt.com.
 *
 * Created on 13.01.2005
 * Author uwe geercken
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
    private static final long serialVersionUID=500000;
    public Row()
    {
    	
    }
    
    /**
     * constructor that takes an array of fields as parameter 
     */
    public Row(String[] fields)
    {
        this.fields = fields;
    }
    
    /**
     * constructor that takes an array of objects as parameter.
     * the objects are converted to their string representation.
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
     */
    public String[] getFields()
    {
        return fields;
    }
    
    /**
     * sets the array of fields that belong to the given row object 
     */
    public void setFields(String[] fields)
    {
        this.fields = fields;
    }
    
    /**
     * returns the number of fields that belong to the given row object 
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
     */
    public void setField(int index, String value)throws Exception
    {
        fields[index]=value;
    }
    
    /**
     * sets the value of a field in the array of fields 
     */
    public void setField(int index, int value)throws Exception
    {
        fields[index]= "" + value;
    }
    
    /**
     * returns a field from the array of fields 
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
     * throws an exception if the returned value is not an integer 
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
     * throws an exception if the returned value is not an integer 
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
     * throws an exception if the returned value is not an integer 
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
     * throws an exception if the returned value is not an integer 
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
     * throws an exception if the returned value is not a boolean 
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
     */
    public long getId()
    {
        return id;
    }
    
    /**
     * sets the id of the row object
     */
    public void setId(long id)
    {
        this.id = id;
    }
    
    /**
     * returns the line as it was retrieved from the originating
     * ascii file
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
    
    public String toString()
    {
    	return getLine();
    }
    
    /**
     * returns the seperator that was used to devide the fields from
     * each other
     */
    public String getSeperator()
    {
        return seperator;
    }
    
    /**
     * sets the seperator that was used to devide the fields from
     * each other
     */
    public void setSeperator(String seperator)
    {
        this.seperator = seperator;
    }
    
}
