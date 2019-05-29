package com.datamelt.rules.implementation;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CheckIsNotEmptyTest 
{

	@Test
	void testIsNotEmptyValid() 
	{
		String value= "Hello";
		
		boolean result = CheckIsNotEmpty.evaluate(value);
		
		assertTrue(result);
	}
	
	@Test
	void testIsNotEmptyInvalid() 
	{
		String value= "";
		
		boolean result = CheckIsNotEmpty.evaluate(value);
		
		assertFalse(result);
	}
	
	@Test
	void testIsNull() 
	{
		String value = null;
		
		boolean result = CheckIsNotEmpty.evaluate(value);
		
		assertFalse(result);
	}

}
