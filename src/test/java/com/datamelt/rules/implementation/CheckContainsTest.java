package com.datamelt.rules.implementation;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class CheckContainsTest 
{
	private static String value;
	
	@BeforeAll
	static void init() 
	{
		value = "Yesterday Evening everything was fine";
	}

	@Test
	void testContainsBeginningValid() 
	{
		String compareValue= "Yesterday";
		
		boolean result = CheckContains.evaluate(value, compareValue);
		
		assertTrue(result);
	}

	@Test
	void testContainsMiddleValid() 
	{
		String compareValue= "Evening";
		
		boolean result = CheckContains.evaluate(value, compareValue);
		
		assertTrue(result);
	}

	@Test
	void testContainsEndValid() 
	{
		String compareValue= "fine";
		
		boolean result = CheckContains.evaluate(value, compareValue);
		
		assertTrue(result);
	}
	
	@Test
	void testContainsWithLowercaseValid() 
	{
		String compareValue= "WAS";
		
		boolean result = CheckContains.evaluate(value, compareValue, true);
		
		assertTrue(result);
	}

	@Test
	void testContainsWithoutLowercaseInValid() 
	{
		String compareValue= "EVENING";
		
		boolean result = CheckContains.evaluate(value, compareValue);
		
		assertFalse(result);
	}

	@Test
	void testContainsWithBothNull() 
	{
		boolean result = CheckContains.evaluate(null, null);
		
		assertFalse(result);
	}

	@Test
	void testContainsWithFirstNull() 
	{
		String compareValue= "fine";
		
		boolean result = CheckContains.evaluate(null, compareValue);
		
		assertFalse(result);
	}

	@Test
	void testContainsWithSecondNull() 
	{
		
		boolean result = CheckContains.evaluate(value, null);
		
		assertFalse(result);
	}

}
