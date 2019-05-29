package com.datamelt.rules.implementation;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CheckIsEmptyTest 
{

	@Test
	void testIsEmptyValid() 
	{
		String value= "";
		
		boolean result = CheckIsEmpty.evaluate(value);
		
		assertTrue(result);
	}
	
	@Test
	void testIsEmptyInvalid() 
	{
		String value= "christmas";
		
		boolean result = CheckIsEmpty.evaluate(value);
		
		assertFalse(result);
	}

	@Test
	void testIsNull() 
	{
		String value = null;
		
		boolean result = CheckIsEmpty.evaluate(value);
		
		assertTrue(result);
	}
}
