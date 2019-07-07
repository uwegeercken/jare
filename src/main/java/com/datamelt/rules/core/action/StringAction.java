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

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

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
		if(value==null)
		{
			value="";
		}
		return value;
	}
	
	public String setNull(XmlAction action, String value) throws Exception
	{
		return null;
	}
	
	public String replaceValue(XmlAction action, String value, String regex, String replacement) throws Exception
	{
		if(value==null)
		{
			value="";
		}
		if(regex==null)
		{
			regex="";
		}
		if(replacement==null)
		{
			replacement="";
		}
		return value.replaceAll(regex, replacement);
	}
	
	public String replaceValueFromMap(XmlAction action, String originalValue, String filename) throws Exception
	{
		if(originalValue==null)
		{
			originalValue="";
		}
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
		if(list==null)
		{
			list="";
		}
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
		if(value==null)
		{
			value="";
		}
		if(untilString==null)
		{
			untilString="";
		}
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
		if(value==null)
		{
			value="";
		}
		return value.substring(beginIndex);
	}
	
	public String subStringValue(XmlAction action, String value, int beginIndex, int endIndex)
	{
		if(value==null)
		{
			value="";
		}
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
		if(value==null)
		{
			value="";
		}
		if(value2==null)
		{
			value2="";
		}
		return value + value2;
	}
	
	public String concatValues(XmlAction action, int value, String value2) throws Exception
	{
		if(value2==null)
		{
			value2="";
		}
		return value + value2;
	}
	
	public String concatValues(XmlAction action, long value, String value2) throws Exception
	{
		if(value2==null)
		{
			value2="";
		}
		return value + value2;
	}
	
	public String concatValues(XmlAction action, float value, String value2) throws Exception
	{
		if(value2==null)
		{
			value2="";
		}
		return value + value2;
	}
	
	public String concatValues(XmlAction action, double value, String value2) throws Exception
	{
		if(value2==null)
		{
			value2 = "";
		}
		return "" + value + value2;
	}

	public String concatValues(XmlAction action, String value, int value2) throws Exception
	{
		if(value==null)
		{
			value = "";
		}
		return value + value2;
	}
	
	public String concatValues(XmlAction action, String value, long value2) throws Exception
	{
		if(value==null)
		{
			value = "";
		}
		return value + value2;
	}
	
	public String concatValues(XmlAction action, String value, float value2) throws Exception
	{
		if(value==null)
		{
			value = "";
		}
		return value + value2;
	}
	
	public String concatValues(XmlAction action, String value, double value2) throws Exception
	{
		if(value==null)
		{
			value = "";
		}
		return value + value2;
	}

	public String concatValues(XmlAction action, String value, String value2, String seperator) throws Exception
	{
		if(value==null)
		{
			value = "";
		}
		if(value2==null)
		{
			value2 = "";
		}
		if(seperator==null)
		{
			seperator = "";
		}
		return value + seperator + value2;
	}
	
	public String concatValues(XmlAction action, String value, int value2, String seperator) throws Exception
	{
		if(value==null)
		{
			value = "";
		}
		if(seperator==null)
		{
			seperator = "";
		}
		return value + seperator + value2;
	}
	
	public String concatValues(XmlAction action, String value, long value2, String seperator) throws Exception
	{
		if(value==null)
		{
			value = "";
		}
		if(seperator==null)
		{
			seperator = "";
		}
		return value + seperator + value2;
	}
	
	public String concatValues(XmlAction action, String value, float value2, String seperator) throws Exception
	{
		if(value==null)
		{
			value = "";
		}
		if(seperator==null)
		{
			seperator = "";
		}
		return value + seperator + value2;
	}
	
	public String concatValues(XmlAction action, String value, double value2, String seperator) throws Exception
	{
		if(value==null)
		{
			value = "";
		}
		if(seperator==null)
		{
			seperator = "";
		}
		return value + seperator + value2;
	}
	
	public String concatValues(XmlAction action, int value, String value2, String seperator) throws Exception
	{
		if(value2==null)
		{
			value2 = "";
		}
		if(seperator==null)
		{
			seperator = "";
		}
		return "" + value + seperator + value2;
	}
	
	public String concatValues(XmlAction action, long value, String value2, String seperator) throws Exception
	{
		if(value2==null)
		{
			value2 = "";
		}
		if(seperator==null)
		{
			seperator = "";
		}
		return "" + value + seperator + value2;
	}
	
	public String concatValues(XmlAction action, float value, String value2, String seperator) throws Exception
	{
		if(value2==null)
		{
			value2 = "";
		}
		if(seperator==null)
		{
			seperator = "";
		}
		return "" + value + seperator + value2;
	}
	
	public String concatValues(XmlAction action, double value, String value2, String seperator) throws Exception
	{
		if(value2==null)
		{
			value2 = "";
		}
		if(seperator==null)
		{
			seperator = "";
		}
		return "" + value + seperator + value2;
	}
	
	public String appendValue(XmlAction action, String value, String appendValue) throws Exception
	{
		if(value==null)
		{
			value = "";
		}
		if(appendValue==null)
		{
			appendValue = "";
		}
		return value + appendValue;
	}
	
	public String appendValue(XmlAction action, String value, String appendValue, String seperator) throws Exception
	{
		if(value==null)
		{
			value = "";
		}
		if(appendValue==null)
		{
			appendValue = "";
		}
		if(seperator==null)
		{
			seperator = "";
		}
		return value + seperator + appendValue;
	}
	
	public String appendValue(XmlAction action, String value, int appendValue) throws Exception
	{
		if(value==null)
		{
			value = "";
		}
		return value + appendValue;
	}
	
	public String appendValue(XmlAction action, String value, int appendValue, String seperator) throws Exception
	{
		if(value==null)
		{
			value = "";
		}
		if(seperator==null)
		{
			seperator = "";
		}
		return value + seperator + appendValue;
	}
	
	public String appendValue(XmlAction action, String value, long appendValue) throws Exception
	{
		if(value==null)
		{
			value = "";
		}
		return value + appendValue;
	}
	
	public String appendValue(XmlAction action, String value, long appendValue, String seperator) throws Exception
	{
		if(value==null)
		{
			value = "";
		}
		if(seperator==null)
		{
			seperator = "";
		}
		return value + seperator + appendValue;
	}
	
	public String prependValue(XmlAction action, String value, String prependValue) throws Exception
	{
		if(value==null)
		{
			value = "";
		}
		if(prependValue==null)
		{
			prependValue = "";
		}
		return prependValue + value;
	}

	public String prependValue(XmlAction action, String value, int prependValue, String seperator) throws Exception
	{
		if(value==null)
		{
			value = "";
		}
		if(seperator==null)
		{
			seperator = "";
		}
		return prependValue + seperator + value;
	}

	public String prependValue(XmlAction action, String value, int prependValue) throws Exception
	{
		if(value==null)
		{
			value = "";
		}
		return "" + prependValue + value;
	}

	public String prependValue(XmlAction action, String value, long prependValue, String seperator) throws Exception
	{
		if(value==null)
		{
			value = "";
		}
		if(seperator==null)
		{
			seperator = "";
		}
		return "" + prependValue + seperator + value;
	}

	public String prependValue(XmlAction action, String value, long prependValue) throws Exception
	{
		if(value==null)
		{
			value = "";
		}
		return "" + prependValue + value;
	}

	public String prependValue(XmlAction action, String value, String prependValue, String seperator) throws Exception
	{
		if(value==null)
		{
			value = "";
		}
		if(prependValue==null)
		{
			prependValue = "";
		}
		if(seperator==null)
		{
			seperator = "";
		}
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
		if(value==null)
		{
			value = "";
		}
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
		if(value==null)
		{
			value="";
		}
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
		if(value==null)
		{
			value="";
		}
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
		if(value==null)
		{
			value="";
		}
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
		if(value==null)
		{
			value="";
		}
		return value.toLowerCase();
	}
	
	/**
	 * returns the hash code of the value
	 * 
	 * @param action	the action to use
	 * @param value		the value to use
	 * @return			the has code for the value
	 */
	public int hashValue(XmlAction action,String value)
	{
		if(value==null)
		{
			value="";
		}
		return value.hashCode();
	}
	
	/**
	 * encrypts a given value with the specified key
	 * 
	 * returns the original value if the encryption failed.
	 * 
	 * @param action	the action to use
	 * @param value		the value to use
	 * @param key		the key to use
	 * @return			the encrypted string
	 */
	public String encryptValue(XmlAction action,String value,String key) 
	{
		if(value==null)
		{
			value="";
		}
		if(key==null)
		{
			key="";
		}
		try
		{
			SecretKeySpec skeyspec = new SecretKeySpec(key.getBytes(),"Blowfish");
			Cipher cipher = Cipher.getInstance("Blowfish");
			cipher.init(Cipher.ENCRYPT_MODE, skeyspec);
			byte[] encrypted = cipher.doFinal(value.getBytes("UTF-8"));
			String encryptedString = DatatypeConverter.printHexBinary(encrypted);
			return encryptedString;
		} 
		catch (Exception e) 
		{
			return value;
		}
	}
	
	/**
	 * decrypts a given value with the specified key.
	 * 
	 * returns the original value if the decryption failed.
	 * 
	 * @param action	the action to use
	 * @param value		the value to use
	 * @param key		the key to use
	 * @return			the decrypted string
	 */
	public String decryptValue(XmlAction action,String value,String key)
	{
		if(value==null)
		{
			value="";
		}
		if(key==null)
		{
			key="";
		}
		try 
		{
			SecretKeySpec skeyspec=new SecretKeySpec(key.getBytes(),"Blowfish");
			Cipher cipher=Cipher.getInstance("Blowfish");
			cipher.init(Cipher.DECRYPT_MODE, skeyspec);
			byte[] valueBytes = DatatypeConverter.parseHexBinary(value);
			byte[] decrypted = cipher.doFinal(valueBytes);
			return new String(decrypted, "UTF-8");
		} 
		catch (Exception e) 
		{
			return value;
		}
	}
}
