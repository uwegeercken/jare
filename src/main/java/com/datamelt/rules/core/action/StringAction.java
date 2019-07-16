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
import com.datamelt.util.ActionAnnotation;
import com.datamelt.util.ActionMethodAnnotation;
import com.datamelt.util.CheckAnnotation;

/**
 * Class containing possible actions that are related to string handling.
 * 
 * Actions belong to a rulegroup and are execute depending on the status of rulegroup - if it passed or failed (or both).
 *
 * @author uwe geercken
 * 
 */

public
class StringAction extends GenericAction
{
	@ActionAnnotation(description= "Set a value to another value",methodDisplayname="set value (string)")
	public String setValue(XmlAction action, String value) throws Exception
	{
		if(value==null)
		{
			value="";
		}
		return value;
	}
	
	@ActionAnnotation(description= "Set a value to null",methodDisplayname="set null value (string)")
	public String setNull(XmlAction action, String value) throws Exception
	{
		return null;
	}
	
	@ActionAnnotation(description= "Replace a value by providing a regular expression to search for and a replacement value",methodDisplayname="replace value")
	@ActionMethodAnnotation(note= "2nd parameter: regular expression, 3rd parameter: replacement")
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
	
	@ActionAnnotation(description= "Replace value from mapping file",methodDisplayname="replace value from map")
	@ActionMethodAnnotation(note= "2nd parameter: path and name of the mapping file")
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

	@ActionAnnotation(description= "Replace value from a list of values",methodDisplayname="replace value from list")
	@ActionMethodAnnotation(note= "2nd parameter: list of values separated by comma")
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
	
	@ActionAnnotation(description= "Set a value to the substring of a value",methodDisplayname="substring of value")
	@ActionMethodAnnotation(note= "2nd parameter: the String up to which the substring shall extend")
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

	@ActionAnnotation(description= "Set a value to the substring of a value",methodDisplayname="substring of value")
	@ActionMethodAnnotation(note= "2nd parameter: start of the substring")
	public String subStringValue(XmlAction action, String value, int beginIndex)
	{
		if(value==null)
		{
			value="";
		}
		return value.substring(beginIndex);
	}

	@ActionAnnotation(description= "Set a value to the substring of a value",methodDisplayname="substring of value")
	@ActionMethodAnnotation(note= "2nd parameter: start of substring, 3rd parameter: end of substring")
	public String subStringValue(XmlAction action, String value, int beginIndex, int endIndex)
	{
		if(value==null)
		{
			value="";
		}
		return value.substring(beginIndex, endIndex);
	}
	
	@ActionAnnotation(description= "Replace value with a mask character",methodDisplayname="mask value")
	@ActionMethodAnnotation(note= "2nd parameter: mask characters, 3rd parameter: start position")
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
	
	@ActionAnnotation(description= "Replace value with a mask character",methodDisplayname="mask value")
	@ActionMethodAnnotation(note= "2nd parameter: mask characters, 3rd parameter: start position, 4th parameter: end position")
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
	
	@ActionAnnotation(description= "Replace value with a mask character",methodDisplayname="mask value")
	@ActionMethodAnnotation(note= "2nd parameter: mask characters")
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
	
	@ActionAnnotation(description= "Concatenate two values",methodDisplayname="concat values")
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
	
	@ActionAnnotation(description= "Concatenate two values",methodDisplayname="concat values")
	public String concatValues(XmlAction action, int value, String value2) throws Exception
	{
		if(value2==null)
		{
			value2="";
		}
		return value + value2;
	}
	
	@ActionAnnotation(description= "Concatenate two values",methodDisplayname="concat values")
	public String concatValues(XmlAction action, long value, String value2) throws Exception
	{
		if(value2==null)
		{
			value2="";
		}
		return value + value2;
	}
	
	@ActionAnnotation(description= "Concatenate two values",methodDisplayname="concat values")
	public String concatValues(XmlAction action, float value, String value2) throws Exception
	{
		if(value2==null)
		{
			value2="";
		}
		return value + value2;
	}
	
	@ActionAnnotation(description= "Concatenate two values",methodDisplayname="concat values")
	public String concatValues(XmlAction action, double value, String value2) throws Exception
	{
		if(value2==null)
		{
			value2 = "";
		}
		return "" + value + value2;
	}

	@ActionAnnotation(description= "Concatenate two values",methodDisplayname="concat values")
	public String concatValues(XmlAction action, String value, int value2) throws Exception
	{
		if(value==null)
		{
			value = "";
		}
		return value + value2;
	}
	
	@ActionAnnotation(description= "Concatenate two values",methodDisplayname="concat values")
	public String concatValues(XmlAction action, String value, long value2) throws Exception
	{
		if(value==null)
		{
			value = "";
		}
		return value + value2;
	}
	
	@ActionAnnotation(description= "Concatenate two values",methodDisplayname="concat values")
	public String concatValues(XmlAction action, String value, float value2) throws Exception
	{
		if(value==null)
		{
			value = "";
		}
		return value + value2;
	}
	
	@ActionAnnotation(description= "Concatenate two values",methodDisplayname="concat values")
	public String concatValues(XmlAction action, String value, double value2) throws Exception
	{
		if(value==null)
		{
			value = "";
		}
		return value + value2;
	}

	@ActionAnnotation(description= "Concatenate two values",methodDisplayname="concat values")
	@ActionMethodAnnotation(note= "last parameter: separator to use")
	public String concatValues(XmlAction action, String value, String value2, String separator) throws Exception
	{
		if(value==null)
		{
			value = "";
		}
		if(value2==null)
		{
			value2 = "";
		}
		if(separator==null)
		{
			separator = "";
		}
		return value + separator + value2;
	}
	
	@ActionAnnotation(description= "Concatenate two values",methodDisplayname="concat values")
	@ActionMethodAnnotation(note= "last parameter: separator to use")
	public String concatValues(XmlAction action, String value, int value2, String separator) throws Exception
	{
		if(value==null)
		{
			value = "";
		}
		if(separator==null)
		{
			separator = "";
		}
		return value + separator + value2;
	}
	
	@ActionAnnotation(description= "Concatenate two values",methodDisplayname="concat values")
	@ActionMethodAnnotation(note= "last parameter: separator to use")
	public String concatValues(XmlAction action, String value, long value2, String separator) throws Exception
	{
		if(value==null)
		{
			value = "";
		}
		if(separator==null)
		{
			separator = "";
		}
		return value + separator + value2;
	}
	
	@ActionAnnotation(description= "Concatenate two values",methodDisplayname="concat values")
	@ActionMethodAnnotation(note= "last parameter: separator to be used")
	public String concatValues(XmlAction action, String value, float value2, String separator) throws Exception
	{
		if(value==null)
		{
			value = "";
		}
		if(separator==null)
		{
			separator = "";
		}
		return value + separator + value2;
	}
	
	@ActionAnnotation(description= "Concatenate two values",methodDisplayname="concat values")
	@ActionMethodAnnotation(note= "last parameter: separator to be used")
	public String concatValues(XmlAction action, String value, double value2, String separator) throws Exception
	{
		if(value==null)
		{
			value = "";
		}
		if(separator==null)
		{
			separator = "";
		}
		return value + separator + value2;
	}
	
	@ActionAnnotation(description= "Concatenate two values",methodDisplayname="concat values")
	@ActionMethodAnnotation(note= "last parameter: separator to be used")
	public String concatValues(XmlAction action, int value, String value2, String separator) throws Exception
	{
		if(value2==null)
		{
			value2 = "";
		}
		if(separator==null)
		{
			separator = "";
		}
		return "" + value + separator + value2;
	}
	
	@ActionAnnotation(description= "Concatenate two values",methodDisplayname="concat values")
	@ActionMethodAnnotation(note= "last parameter: separator to be used")
	public String concatValues(XmlAction action, long value, String value2, String separator) throws Exception
	{
		if(value2==null)
		{
			value2 = "";
		}
		if(separator==null)
		{
			separator = "";
		}
		return "" + value + separator + value2;
	}
	
	@ActionAnnotation(description= "Concatenate two values",methodDisplayname="concat values")
	@ActionMethodAnnotation(note= "last parameter: separator to be used")
	public String concatValues(XmlAction action, float value, String value2, String separator) throws Exception
	{
		if(value2==null)
		{
			value2 = "";
		}
		if(separator==null)
		{
			separator = "";
		}
		return "" + value + separator + value2;
	}
	
	@ActionAnnotation(description= "Concatenate two values",methodDisplayname="concat values")
	@ActionMethodAnnotation(note= "last parameter: separator to be used")
	public String concatValues(XmlAction action, double value, String value2, String separator) throws Exception
	{
		if(value2==null)
		{
			value2 = "";
		}
		if(separator==null)
		{
			separator = "";
		}
		return "" + value + separator + value2;
	}
	
	@ActionAnnotation(description= "Add a value to the end of a value",methodDisplayname="append value")
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
	
	@ActionAnnotation(description= "Add a value to the end of a value",methodDisplayname="append value")
	@ActionMethodAnnotation(note= "last parameter: separator to use")
	public String appendValue(XmlAction action, String value, String appendValue, String separator) throws Exception
	{
		if(value==null)
		{
			value = "";
		}
		if(appendValue==null)
		{
			appendValue = "";
		}
		if(separator==null)
		{
			separator = "";
		}
		return value + separator + appendValue;
	}
	
	@ActionAnnotation(description= "Add a value to the end of a value",methodDisplayname="append value")
	public String appendValue(XmlAction action, String value, int appendValue) throws Exception
	{
		if(value==null)
		{
			value = "";
		}
		return value + appendValue;
	}
	
	@ActionAnnotation(description= "Add a value to the end of a value",methodDisplayname="append value")
	@ActionMethodAnnotation(note= "last parameter: separator to use")
	public String appendValue(XmlAction action, String value, int appendValue, String separator) throws Exception
	{
		if(value==null)
		{
			value = "";
		}
		if(separator==null)
		{
			separator = "";
		}
		return value + separator + appendValue;
	}
	
	@ActionAnnotation(description= "Add a value to the end of a value",methodDisplayname="append value")
	public String appendValue(XmlAction action, String value, long appendValue) throws Exception
	{
		if(value==null)
		{
			value = "";
		}
		return value + appendValue;
	}
	
	@ActionAnnotation(description= "Add a value to the end of a value",methodDisplayname="append value")
	@ActionMethodAnnotation(note= "last parameter: separator to use")
	public String appendValue(XmlAction action, String value, long appendValue, String separator) throws Exception
	{
		if(value==null)
		{
			value = "";
		}
		if(separator==null)
		{
			separator = "";
		}
		return value + separator + appendValue;
	}
	
	@ActionAnnotation(description= "Add a value to the beginning of a value",methodDisplayname="prepend value")
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

	@ActionAnnotation(description= "Add a value to the beginning of a value",methodDisplayname="prepend value")
	@ActionMethodAnnotation(note= "last parameter: separator to use")
	public String prependValue(XmlAction action, String value, int prependValue, String separator) throws Exception
	{
		if(value==null)
		{
			value = "";
		}
		if(separator==null)
		{
			separator = "";
		}
		return prependValue + separator + value;
	}

	@ActionAnnotation(description= "Add a value to the beginning of a value",methodDisplayname="prepend value")
	public String prependValue(XmlAction action, String value, int prependValue) throws Exception
	{
		if(value==null)
		{
			value = "";
		}
		return "" + prependValue + value;
	}

	@ActionAnnotation(description= "Add a value to the beginning of a value",methodDisplayname="prepend value")
	@ActionMethodAnnotation(note= "last parameter: separator to use")
	public String prependValue(XmlAction action, String value, long prependValue, String separator) throws Exception
	{
		if(value==null)
		{
			value = "";
		}
		if(separator==null)
		{
			separator = "";
		}
		return "" + prependValue + separator + value;
	}

	@ActionAnnotation(description= "Add a value to the beginning of a value",methodDisplayname="prepend value")
	public String prependValue(XmlAction action, String value, long prependValue) throws Exception
	{
		if(value==null)
		{
			value = "";
		}
		return "" + prependValue + value;
	}

	@ActionAnnotation(description= "Add a value to the beginning of a value",methodDisplayname="prepend value")
	@ActionMethodAnnotation(note= "last parameter: separator to use")
	public String prependValue(XmlAction action, String value, String prependValue, String separator) throws Exception
	{
		if(value==null)
		{
			value = "";
		}
		if(prependValue==null)
		{
			prependValue = "";
		}
		if(separator==null)
		{
			separator = "";
		}
		return prependValue + separator + value;
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
	@ActionAnnotation(description= "Add leading zeros to a value",methodDisplayname="add leading zeros")
	@ActionMethodAnnotation(note="last parameter: maximum length of the String ") 
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
	@ActionAnnotation(description= "Add leading spaces to a value",methodDisplayname="add leading spaces")
	@ActionMethodAnnotation(note="last parameter: number of spaces to add at the begining") 
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
	@ActionAnnotation(description= "Remove (trim) all spaces from the beginning and the end of a value",methodDisplayname="trim")
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
	@ActionAnnotation(description= "Set a value to its uppercase representation",methodDisplayname="uppercase")
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
	@ActionAnnotation(description= "Set a value to its lowercase representation",methodDisplayname="lowercase")
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
	@ActionAnnotation(description= "Hash value",methodDisplayname="hash value")
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
	@ActionAnnotation(description= "Encrypt value (Blowfish) using a key",methodDisplayname="encrypt value")
	@ActionMethodAnnotation(note= "last parameter: encryption key to use")
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
	@ActionAnnotation(description= "Decrypt value (Blowfish) using a key",methodDisplayname="decrypt value")
	@ActionMethodAnnotation(note= "last parameter: encryption key to use")
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
