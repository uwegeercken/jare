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
package com.datamelt.rules.core.action;

import com.datamelt.rules.core.XmlAction;

/**
 * Class containing actions that are related to the conversion of values.
 * 
 * Actions belong to a rulegroup and are execute depending on the status of rulegroup - if it passed or failed (or both).
 *
 * @author uwe geercken
 * 
 */

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
	 * converts a given double value to an integer value
	 * 
	 * @param action 		the action to use
	 * @param value			the value to convert
	 * @return				integer
	 * @throws Exception	when the double value can not be converted
	 */
	public int toInteger(XmlAction action, double value) throws Exception
	{
			return (int)value;
	}
	
	/**
	 * converts a given float value to an integer value
	 * 
	 * @param action 		the action to use
	 * @param value			the value to convert
	 * @return				integer
	 * @throws Exception	when the float value can not be converted
	 */
	public int toInteger(XmlAction action, float value) throws Exception
	{
			return (int)value;
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
	
	/**
	 * converts a given int to a string value
	 * 
	 * @param action 		the action to use
	 * @param value			the value to convert
	 * @return				String
	 * @throws Exception	when the int can not be converted
	 */
	public String toString(XmlAction action, int value) throws Exception
	{
		return "" + value;
	}
	
	/**
	 * converts a given string of four characters which represent hour and minutes in
	 * the format "hhmm" to an integer value.
	 * 
	 * example 0133 = 1 hour, 33 minutes = 93 minutes
	 * 
	 * @param action 		the action to use
	 * @param value			the value to convert
	 * @return				integer
	 * @throws Exception	when the string can not be converted
	 */
	public int fourDigitTimetoMinutes(XmlAction action, String value) throws Exception
	{
		try
		{
			String hours;
			String minutes;
			if(value!=null && !value.trim().equals("") && value.length()==4)
			{
				hours = value.substring(0,2);
				minutes = value.substring(2,4);
			}
			else
			{
				 hours = "00";
				 minutes = "00";
			}
			int totalMinutes = 60 * Integer.parseInt(hours) + Integer.parseInt(minutes);
			
			return totalMinutes;
		}
		catch(Exception ex)
		{
			return 0;	
		}
	}
	
	/**
	 * converts a given string of five characters which represent hour and minutes in
	 * the format "hh:mm" to an integer value. The third character is the delimiter between
	 * hours and minutes.
	 * 
	 * example 30:26 = 30 hours, 26 minutes = 1826 minutes
	 * 
	 * @param action 		the action to use
	 * @param value			the value to convert
	 * @return				integer
	 * @throws Exception	when the string can not be converted
	 */
	public int fiveDigitTimetoMinutes(XmlAction action, String value) throws Exception
	{
		try
		{
			String hours;
			String minutes;
			if(value!=null && !value.trim().equals("") && value.length()==5)
			{
				hours = value.substring(0,2);
				minutes = value.substring(3,5);
			}
			else
			{
				 hours = "00";
				 minutes = "00";
			}
			int totalMinutes = 60 * Integer.parseInt(hours) + Integer.parseInt(minutes);
			
			return totalMinutes;
		}
		catch(Exception ex)
		{
			return 0;	
		}
	}
	
	
}
