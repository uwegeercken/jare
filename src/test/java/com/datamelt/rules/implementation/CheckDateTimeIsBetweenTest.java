package com.datamelt.rules.implementation;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Calendar;
import java.util.Date;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CheckDateTimeIsBetweenTest 
{
	Calendar calendar;
	
	private static String startTime = "08:00:00";
	private static String endTime = "14:00:00";
	
	@BeforeEach
	void init() 
	{
		calendar = Calendar.getInstance();
	}
	
	@Test
	void testIsDateTimeIsBetweenValid() 
	{
		calendar.set(Calendar.HOUR_OF_DAY,10);
		calendar.set(Calendar.MINUTE,0);
		calendar.set(Calendar.SECOND,0);
		
		String dateRange = startTime + "," + endTime;
		
		boolean result = CheckDateTimeIsBetween.evaluate(calendar.getTime(), dateRange);
		
		assertTrue(result);
	}
	
	@Test
	void testIsDateTimeIsInvalidEarlier() 
	{
		calendar.set(Calendar.HOUR_OF_DAY,06);
		calendar.set(Calendar.MINUTE,0);
		calendar.set(Calendar.SECOND,0);
		
		String dateRange = startTime + "," + endTime;
		
		boolean result = CheckDateTimeIsBetween.evaluate(calendar.getTime(), dateRange);
		
		assertFalse(result);
	}
	
	@Test
	void testIsDateTimeIsInvalidLater() 
	{
		calendar.set(Calendar.HOUR_OF_DAY,23);
		calendar.set(Calendar.MINUTE,0);
		calendar.set(Calendar.SECOND,0);
		
		String dateRange = startTime + "," + endTime;
		
		boolean result = CheckDateTimeIsBetween.evaluate(calendar.getTime(), dateRange);
		
		assertFalse(result);
	}
	
	@AfterEach
	void tearDown() 
	{
		calendar = null;
	}
}
