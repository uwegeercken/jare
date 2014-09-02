package com.datamelt.rules.core.action;

import com.datamelt.rules.core.XmlAction;

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
	
	public String subStringValue(XmlAction action, String value, int beginIndex)
	{
		return value.substring(beginIndex);
	}
	
	public String subStringValue(XmlAction action, String value, int beginIndex, int endIndex)
	{
		return value.substring(beginIndex, endIndex);
	}
	
	public String concatValues(XmlAction action, String value, String value2) throws Exception
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
	
	public String appendValue(XmlAction action, String value, String appendValue) throws Exception
	{
		return value + appendValue;
	}
	
	public String appendValue(XmlAction action, String value, String appendValue, String seperator) throws Exception
	{
		return value + seperator + appendValue;
	}
	
	public String prependValue(XmlAction action, String value, String prependValue) throws Exception
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
	 */
	public String addLeadingSpaces(XmlAction action,String value, int length)
	{
		while(value.length()<length)
		{
			value = " " + value;
		}
		return value;
	}
	
	public String trimValue(XmlAction action,String value)
	{
		return value.trim();
	}
	
	public String upperCaseValue(XmlAction action,String value)
	{
		return value.toUpperCase();
	}

	public String lowerCaseValue(XmlAction action,String value)
	{
		return value.toLowerCase();
	}

}
