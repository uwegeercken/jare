package com.datamelt.rules.implementation;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CheckContainsTest 
{
	String value;
	
	@BeforeEach
	void init() 
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
		String compareValue= "evening";
		
		boolean result = CheckContains.evaluate(value, null);
		
		assertFalse(result);
	}

	@AfterEach
	void tearDown() 
	{
		
	}
}
