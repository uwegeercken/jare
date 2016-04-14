package com.datamelt.junit.action;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.datamelt.rules.core.action.MathAction;


public class MathActionTest 
{
	MathAction action;
	
	@Before
	public void method() 
	{
		action = new MathAction();
	}
	
	@Test
	public void shouldReturnAbsoluteValue() throws Exception
	{
		double doubleValue = 12370343.456;
		float floatValue = -34.456f;
		int intValue = -5999;
		long longValue = -1000000;
		
		assertEquals(Math.abs(doubleValue), action.absValue(null, doubleValue), 0);
		assertEquals(Math.abs(floatValue), action.absValue(null, floatValue), 0);
		assertEquals(Math.abs(intValue), action.absValue(null, intValue));
		assertEquals(Math.abs(longValue), action.absValue(null, longValue));
		
	}
}

