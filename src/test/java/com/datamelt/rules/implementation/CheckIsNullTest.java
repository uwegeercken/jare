package com.datamelt.rules.implementation;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class CheckIsNullTest {

	private Long valueLong = null;
	private Float valueFloat = null;
	private Double valueDouble = null;

	@BeforeEach
	void init() 
	{
		//checkIsNull = new CheckIsNull();

	}

	@Test
	void testString() 
	{
		String value = null;
		
		boolean result = CheckIsNull.evaluate(value);
		
		assertTrue(result);
	}
	
	@Test
	void testDate() 
	{
		Date value = null;

		boolean result = CheckIsNull.evaluate(value);
		
		assertTrue(result);
	}
	
	@Disabled
	@Test
	void testLong() 
	{
		boolean result = CheckIsNull.evaluate(valueLong.longValue());
		
		assertTrue(result);
	}

	@Disabled
	@Test
	void testFloat() 
	{
		boolean result = CheckIsNull.evaluate(valueFloat.floatValue());
		
		assertTrue(result);

	}

	@Disabled
	@Test
	void testDouble() 
	{
		boolean result = CheckIsNull.evaluate(valueDouble.doubleValue());
		
		assertTrue(result);

	}
	
	@Test
	void testStringNotNull() 
	{
		String  value = "testing";

		boolean result = CheckIsNull.evaluate(value);
		
		assertFalse(result);

	}

	@AfterEach
	void tearDown() 
	{

	}
	
}
