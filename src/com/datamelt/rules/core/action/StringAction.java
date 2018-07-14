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
 * Class containing possible actions that are related to string handling.
 * 
 * Actions belong to a rulegroup and are execute depending on the status of rulegroup - if it passed or failed (or both).
 *
 * @author uwe geercken
 * 
 */
public class StringAction
{
	public String setValue(XmlAction action, String value) throws Exception
	{
		return value;
	}
	
	public String replaceValue(XmlAction action, String value, String regex, String replacement) throws Exception
	{
		return value.replaceAll(regex, replacement);
	}
	
	public String replaceValueFromMap(XmlAction action, String originalValue, String filename) throws Exception
	{
		String value = action.getMappingCollection().getValue(filename, originalValue);
		if(value!=null)
		{
			return value;
		}
		else
		{
			return originalValue;
		}
	}
	
	public String replaceValueFromList(XmlAction action, int index, String list) throws Exception
	{
		String values[] = list.split(",");
		if(values!=null && values.length>0 && index < values.length)
		{
			return values[index].trim();
		}
		else
		{
			return null;
		}
	}
	
	public String subStringValue(XmlAction action, String value, String untilString)
	{
		int pos = value.indexOf(untilString);
		if (pos>-1)
		{
			return value.substring(0,pos).trim();
		}
		else
		{
			return value;			
		}
	}
	
	public String subStringValue(XmlAction action, String value, int beginIndex)
	{
		return value.substring(beginIndex);
	}
	
	public String subStringValue(XmlAction action, String value, int beginIndex, int endIndex)
	{
		return value.substring(beginIndex, endIndex);
	}
	
	public String maskValue(XmlAction action, String value, String mask, int beginIndex)
	{
		StringBuffer maskedString = new StringBuffer();
		if(value!=null && mask!=null)
		{
			for(int i=0;i<value.length();i++)
			{
				if(i<beginIndex && beginIndex<value.length())
				{
					maskedString.append(value.substring(i,i+1));
				}
				else
				{
					maskedString.append(mask);
				}
			}
			return maskedString.toString();
		}
		else
		{
			return value;
		}
		
	}
	
	public String maskValue(XmlAction action, String value, String mask, int beginIndex, int endIndex)
	{
		StringBuffer maskedString = new StringBuffer();
		if(value!=null && mask!=null)
		{
			for(int i=0;i<value.length();i++)
			{
				if((i<beginIndex || i>= endIndex) && beginIndex<value.length() && endIndex<value.length())
				{
					maskedString.append(value.substring(i,i+1));
				}
				else
				{
					maskedString.append(mask);
				}
			}
			return maskedString.toString();
		}
		else
		{
			return value;
		}
	}
	
	public String maskValue(XmlAction action, String value, String mask)
	{
		StringBuffer maskedString = new StringBuffer();
		if(value!=null && mask!=null)
		{
			for(int i=0;i<value.length();i++)
			{
				maskedString.append(mask);
			}
			return maskedString.toString();
		}
		else
		{
			return value;
		}
	}
	
	public String concatValues(XmlAction action, String value, String value2) throws Exception
	{
		return value + value2;
	}
	
	public String concatValues(XmlAction action, int value, String value2) throws Exception
	{
		return value + value2;
	}
	
	public String concatValues(XmlAction action, long value, String value2) throws Exception
	{
		return value + value2;
	}
	
	public String concatValues(XmlAction action, float value, String value2) throws Exception
	{
		return value + value2;
	}
	
	public String concatValues(XmlAction action, double value, String value2) throws Exception
	{
		return value + value2;
	}

	public String concatValues(XmlAction action, String value, int value2) throws Exception
	{
		return value + value2;
	}
	
	public String concatValues(XmlAction action, String value, long value2) throws Exception
	{
		return value + value2;
	}
	
	public String concatValues(XmlAction action, String value, float value2) throws Exception
	{
		return value + value2;
	}
	
	public String concatValues(XmlAction action, String value, double value2) throws Exception
	{
		return value + value2;
	}

	public String concatValues(XmlAction action, String value, String value2, String seperator) throws Exception
	{
		return value + seperator + value2;
	}
	
	public String concatValues(XmlAction action, String value, int value2, String seperator) throws Exception
	{
		return value + seperator + value2;
	}
	
	public String concatValues(XmlAction action, String value, long value2, String seperator) throws Exception
	{
		return value + seperator + value2;
	}
	
	public String concatValues(XmlAction action, String value, float value2, String seperator) throws Exception
	{
		return value + seperator + value2;
	}
	
	public String concatValues(XmlAction action, String value, double value2, String seperator) throws Exception
	{
		return value + seperator + value2;
	}
	
	public String concatValues(XmlAction action, int value, String value2, String seperator) throws Exception
	{
		return value + seperator + value2;
	}
	
	public String concatValues(XmlAction action, long value, String value2, String seperator) throws Exception
	{
		return value + seperator + value2;
	}
	
	public String concatValues(XmlAction action, float value, String value2, String seperator) throws Exception
	{
		return value + seperator + value2;
	}
	
	public String concatValues(XmlAction action, double value, String value2, String seperator) throws Exception
	{
		return value + seperator + value2;
	}
	
	public String appendValue(XmlAction action, String value, String appendValue) throws Exception
	{
		return value + appendValue;
	}
	
	public String appendValue(XmlAction action, String value, String appendValue, String seperator) throws Exception
	{
		return value + seperator + appendValue;
	}
	
	public String appendValue(XmlAction action, String value, int appendValue) throws Exception
	{
		return value + appendValue;
	}
	
	public String appendValue(XmlAction action, String value, int appendValue, String seperator) throws Exception
	{
		return value + seperator + appendValue;
	}
	
	public String appendValue(XmlAction action, String value, long appendValue) throws Exception
	{
		return value + appendValue;
	}
	
	public String appendValue(XmlAction action, String value, long appendValue, String seperator) throws Exception
	{
		return value + seperator + appendValue;
	}
	
	public String prependValue(XmlAction action, String value, String prependValue) throws Exception
	{
		return prependValue + value;
	}

	public String prependValue(XmlAction action, String value, int prependValue, String seperator) throws Exception
	{
		return prependValue + seperator + value;
	}

	public String prependValue(XmlAction action, String value, int prependValue) throws Exception
	{
		return prependValue + value;
	}

	public String prependValue(XmlAction action, String value, long prependValue, String seperator) throws Exception
	{
		return prependValue + seperator + value;
	}

	public String prependValue(XmlAction action, String value, long prependValue) throws Exception
	{
		return prependValue + value;
	}

	public String prependValue(XmlAction action, String value, String prependValue, String seperator) throws Exception
	{
		return prependValue + seperator + value;
	}

	/**
	 * method will add leading zeros to the objects value until the objects length
	 * is the same as specified in the length argument
	 * 
	 * @param	action		the action to use
	 * @param	value		the string to use
	 * @param	length		the desired length of the result returned
	 * @return				string with leading zeros
	 */
	public String addLeadingZeros(XmlAction action,String value, int length)
	{
		while(value.length()<length)
		{
			value = "0" + value;
		}
		return value;
	}
	
	/**
	 * method will add leading spaces to the objects value until the objects length
	 * is the same as specified in the length argument
	 * 
	 * @param	action		the action to use
	 * @param	value		the string to use
	 * @param	length		the desired length of the result returned
	 * @return				string with leading spaces
	 */
	public String addLeadingSpaces(XmlAction action,String value, int length)
	{
		while(value.length()<length)
		{
			value = " " + value;
		}
		return value;
	}
	
	/**
	 * removes leading and trailing spaces fron the string
	 * 
	 * @param action	the action to use
	 * @param value		the value to trim
	 * @return			the trimmed string
	 */
	public String trimValue(XmlAction action,String value)
	{
		return value.trim();
	}
	
	/**
	 * sets all characters to upper case
	 * 
	 * @param action	the action to use
	 * @param value		the value to use
	 * @return			the string all upper case
	 */
	public String upperCaseValue(XmlAction action,String value)
	{
		return value.toUpperCase();
	}

	/**
	 * sets all characters to lower case
	 * 
	 * @param action	the action to use
	 * @param value		the value to use
	 * @return			the string all lower case
	 */
	public String lowerCaseValue(XmlAction action,String value)
	{
		return value.toLowerCase();
	}
}
