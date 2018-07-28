package com.datamelt.rules.core.action;

import com.datamelt.rules.core.XmlAction;

public class ConvertAction
{
	/**
	 * converts a given string to an integer value
	 * 
	 * @param action 		the action to use
	 * @param value			the value to convert
	 * @return				int
	 * @throws Exception	when the string can not be converted
	 */
	public int toInteger(XmlAction action, String value) throws Exception
	{
		try
		{
			int i = Integer.parseInt(value);
			return i;
		}
		catch(Exception ex)
		{
			return 0;	
		}
	}
	
	/**
	 * converts a given string to an long value
	 * 
	 * @param action 		the action to use
	 * @param value			the value to convert
	 * @return				long
	 * @throws Exception	when the string can not be converted
	 */
	public long toLong(XmlAction action, String value) throws Exception
	{
		try
		{
			long l = Long.parseLong(value);
			return l;
		}
		catch(Exception ex)
		{
			return 0;	
		}
	}
	
	/**
	 * converts a given string to an double value
	 * 
	 * @param action 		the action to use
	 * @param value			the value to convert
	 * @return				double
	 * @throws Exception	when the string can not be converted
	 */
	public double toDouble(XmlAction action, String value) throws Exception
	{
		try
		{
			double d = Double.parseDouble(value);
			return d;
		}
		catch(Exception ex)
		{
			return 0.0d;	
		}
	}
	
	/**
	 * converts a given string to an float value
	 * 
	 * @param action 		the action to use
	 * @param value			the value to convert
	 * @return				float
	 * @throws Exception	when the string can not be converted
	 */
	public float toFloat(XmlAction action, String value) throws Exception
	{
		try
		{
			float f = Float.parseFloat(value);
			return f;
		}
		catch(Exception ex)
		{
			return 0.0f;	
		}
	}
}
