package com.datamelt.junit.action;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;

import org.junit.Before;
import org.junit.Test;

import com.datamelt.rules.core.action.StringAction;

public class StringActionTest
{
	StringAction action;
	SimpleDateFormat sdf;
	
	@Before
	public void method() 
	{
		action =  new StringAction();

	}
	
	@Test
	public void leadingSpacesShouldBePresent()
	{
		int totalLength = 30;
		
		String testString="hello how are you";
		String resultString = testString;

		while(resultString.length()<totalLength)
		{
			resultString = " " + resultString;
		}
		assertEquals(resultString, action.addLeadingSpaces(null, testString, totalLength));
		
		totalLength=3;
		resultString = testString;

		while(resultString.length()<totalLength)
		{
			resultString = " " + resultString;
		}
		
		assertEquals(resultString, action.addLeadingSpaces(null, testString, totalLength));
		
		totalLength=-199;
		resultString = testString;

		while(resultString.length()<totalLength)
		{
			resultString = " " + resultString;
		}
		
		assertEquals(resultString, action.addLeadingSpaces(null, testString, totalLength));
		
	}
	
	@Test
	public void leadingZerosShouldBePresent()
	{
		int totalLength = 30;
		
		String testString="hello how are you";
		String resultString = testString;

		while(resultString.length()<totalLength)
		{
			resultString = "0" + resultString;
		}
		assertEquals(resultString, action.addLeadingZeros(null, testString, totalLength));
		
		totalLength=3;
		resultString = testString;

		while(resultString.length()<totalLength)
		{
			resultString = "0" + resultString;
		}
		
		assertEquals(resultString, action.addLeadingZeros(null, testString, totalLength));
		
		totalLength=-199;
		resultString = testString;

		while(resultString.length()<totalLength)
		{
			resultString = "0" + resultString;
		}
		
		assertEquals(resultString, action.addLeadingZeros(null, testString, totalLength));
		
	}
	
	@Test
	public void appendingStrings() throws Exception
	{
		
		String testString="hello how are you";
		String appendString="appended";
		String seperator = "---";
		
		assertEquals(testString + appendString, action.appendValue(null, testString, appendString));
		assertEquals(testString + seperator + appendString, action.appendValue(null, testString, appendString,seperator));
		
	}
	
	@Test
	public void prependingStrings() throws Exception
	{
		
		String testString="hello how are you";
		String prependString="prepended";
		String seperator = "---";
		
		assertEquals(prependString + testString , action.prependValue(null, testString, prependString));
		assertEquals(prependString + seperator + testString , action.prependValue(null, testString, prependString,seperator));
		
	}

	@Test
	public void concatenateStrings() throws Exception
	{
		
		String testString="hello how are you";
		String appendString="appended";
		String seperator = "---";
		double doubleValue = 1.234d;
		float floatValue = 234.4587f;
		int intValue = 999;
		long longValue = 123456789;
		
		assertEquals(testString + appendString, action.concatValues(null, testString, appendString));
		assertEquals(testString + seperator + appendString, action.concatValues(null, testString, appendString,seperator));
		
		assertEquals(testString + doubleValue, action.concatValues(null, testString, doubleValue));
		assertEquals(testString + seperator + doubleValue, action.concatValues(null, testString, doubleValue,seperator));
		
		assertEquals(testString + floatValue, action.concatValues(null, testString, floatValue));
		assertEquals(testString + seperator + floatValue, action.concatValues(null, testString, floatValue,seperator));
		
		assertEquals(testString + intValue, action.concatValues(null, testString, intValue));
		assertEquals(testString + seperator + intValue, action.concatValues(null, testString, intValue,seperator));
		
		assertEquals(testString + longValue, action.concatValues(null, testString, longValue));
		assertEquals(testString + seperator + longValue, action.concatValues(null, testString, longValue,seperator));

	}
	
	@Test
	public void lowercaseStrings() throws Exception
	{
		
		String testStringAllUpper="HELLO HOW ARE YOU";
		String testStringAllLower="monday is a good day";
		String testStringMixed="We Were Wild and Free !!!";
		
		assertEquals(testStringAllUpper.toLowerCase(), action.lowerCaseValue(null, testStringAllUpper));
		assertEquals(testStringAllLower.toLowerCase(), action.lowerCaseValue(null, testStringAllLower));
		assertEquals(testStringMixed.toLowerCase(), action.lowerCaseValue(null, testStringMixed));

	}
	
	@Test
	public void uppercaseStrings() throws Exception
	{
		
		String testStringAllUpper="HELLO HOW ARE YOU";
		String testStringAllLower="monday is a good day";
		String testStringMixed="We Were Wild and Free !!!";
		
		assertEquals(testStringAllUpper.toUpperCase(), action.upperCaseValue(null, testStringAllUpper));
		assertEquals(testStringAllLower.toUpperCase(), action.upperCaseValue(null, testStringAllLower));
		assertEquals(testStringMixed.toUpperCase(), action.upperCaseValue(null, testStringMixed));

	}
	
	

}
