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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

/**
 * the RowFieldCollection class represents a container with RowField objects.
 * 
 * a RowField object hold the information of the name and value of a field, both
 * as a string.
 * 
 * The value of the field that is not a string can be retrieved using the available
 * methods such as e.g. getIntegerFieldValue().
 * 
 * @author uwe geercken
 */

public class RowFieldCollection
{
    private long id;
    private boolean collectionUpdated;
    private boolean rowFailed=false;
    private ArrayList<RowField> fields = new ArrayList<RowField>();
    
    public RowFieldCollection()
    {
    }
    
    /**
     * constructor that takes an array of fields as parameter 
     */
    public RowFieldCollection(ArrayList<RowField> fields)
    {
        this.fields = fields;
    }
    
    /**
     * returns the array of fields that belong to the given row object 
     */
    public ArrayList<RowField> getFields()
    {
        return fields;
    }
    
    /**
     * sets the array of fields that belong to the given row object 
     */
    public void setFields(ArrayList<RowField> fields)
    {
        this.fields = fields;
    }
    
    public void addField(RowField field)
    {
    	fields.add(field);
    }
    
    public void addField(String fieldName, Object fieldValue)
    {
    	fields.add(new RowField(fieldName, fieldValue));
    }
    
    /**
    public void addField(String fieldName, String fieldValue)
    {
    	fields.add(new RowField(fieldName, fieldValue));
    }
    
    public void addField(String fieldName, int fieldValue)
    {
    	fields.add(new RowField(fieldName, fieldValue));
    }
    
    public void addField(String fieldName, long fieldValue)
    {
    	fields.add(new RowField(fieldName, fieldValue));
    }
    
    public void addField(String fieldName, double fieldValue)
    {
    	fields.add(new RowField(fieldName, fieldValue));
    }
    
    public void addField(String fieldName, float fieldValue)
    {
    	fields.add(new RowField(fieldName, fieldValue));
    }
    
    public void addField(String fieldName, boolean fieldValue)
    {
    	fields.add(new RowField(fieldName, fieldValue));
    }
    
    public void addField(String fieldName, BigDecimal fieldValue)
    {
    	fields.add(new RowField(fieldName, fieldValue));
    }
    
    public void addField(String fieldName, Date fieldValue)
    {
    	fields.add(new RowField(fieldName, fieldValue));
    }
    
    */
    
    /**
     * returns the number of fields that belong to the given row object 
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
     * returns a field from the array of fields 
     */
    public RowField getField(String name) throws Exception
    {
    	int found=-1;
    	for(int i=0;i<fields.size();i++)
    	{
    		RowField field = fields.get(i);
    		if(field.getName().equals(name))
    		{
    			found = i;
    			break;
    		}
    	}
    	if(found> -1)
    	{
    		return fields.get(found);
    	}
    	else
    	{
    		throw new FieldNotFoundException("field: [" + name + "] not found");

    	}
    }
    
    /**
     * returns the field value from the array of fields 
     */
    public Object getFieldValue(String name) throws Exception
    {
    	int found=-1;
    	for(int i=0;i<fields.size();i++)
    	{
    		RowField field = fields.get(i);
    		if(field.getName().equals(name))
    		{
    			found = i;
    			break;
    		}
    	}
    	if(found> -1)
    	{
    		return fields.get(found).getValue();
    	}
    	else
    	{
    		throw new FieldNotFoundException("field: [" + name + "] not found");
    	}
    }
    
    /**
     * sets the field value of the field 
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
     * sets the field value of the field 
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
     * sets the field value of the field 
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
     * sets the field value of the field 
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
     * sets the field value of the field 
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
     * sets the field value of the field 
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
     * sets the field value of the field 
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
     * sets the field value of the field 
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
     * sets the field value of the field 
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
     * sets the field value of the field 
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

	public boolean isCollectionUpdated()
	{
		return collectionUpdated;
	}

	public boolean isRowFailed()
	{
		return rowFailed;
	}

	public void setRowFailed(boolean rowFailed) 
	{
		this.rowFailed = rowFailed;
	}
	
}
