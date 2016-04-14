package com.datamelt.junit.action;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.datamelt.rules.core.action.DateAction;


public class DateActionTest 
{
	DateAction action;
	Date today;
	SimpleDateFormat sdf;
	
	String dateFormat = "yyyy-MM-dd";
	String dateFormat2 = "dd.MM.yyyy";
	
	@Before
	public void method() 
	{
		action = new DateAction();
		today = new Date();
	}
	
	@Test
	public void setValueShouldReturnSameDate()throws Exception
	{
		assertEquals(today, action.setValue(null,today));
		
		Calendar calendar = Calendar.getInstance();

		calendar.add(Calendar.DAY_OF_MONTH, 0);
		assertEquals(calendar.getTime(), action.setValue(null,calendar.getTime()));
		
		calendar.add(Calendar.DAY_OF_MONTH, -1000);
		assertEquals(calendar.getTime(), action.setValue(null,calendar.getTime()));
		
		
		String someDate= "2015-12-16";
		String someDate2= "07.03.1964";
		
		sdf = new SimpleDateFormat(dateFormat);
		calendar.setTime(sdf.parse(someDate));
		assertEquals(calendar.getTime(), action.setValue(null, someDate));
		
		sdf = new SimpleDateFormat(dateFormat2);
		calendar.setTime(sdf.parse(someDate2));
		assertEquals(calendar.getTime(), action.setValue(null, someDate2,dateFormat2));

		
		
	}
	
	@Test
	public void setTodayDateWithDateFormatShouldReturnTodaysDate()
	{
		sdf = new SimpleDateFormat(dateFormat);
		assertEquals(sdf.format(today), action.setTodayDate(null,dateFormat));
		
		sdf = new SimpleDateFormat(dateFormat2);
		assertEquals(sdf.format(today), action.setTodayDate(null,dateFormat2));
		
		
	}
	
	@Test
	public void setTodayDateWithDateFormatAndOffsetShouldReturnTodaysDate()
	{
		sdf = new SimpleDateFormat(dateFormat);
		Calendar calendar = Calendar.getInstance();

		calendar.add(Calendar.DAY_OF_MONTH, 0);
		assertEquals(sdf.format(calendar.getTime()), action.setTodayDate(null,dateFormat,0));
		
		calendar.add(Calendar.DAY_OF_MONTH, -0);
		assertEquals(sdf.format(calendar.getTime()), action.setTodayDate(null,dateFormat,-0));
		
		calendar.add(Calendar.DAY_OF_MONTH, 27);
		assertEquals(sdf.format(calendar.getTime()), action.setTodayDate(null,dateFormat,27));
		
		calendar.add(Calendar.DAY_OF_MONTH, -45);
		assertEquals(sdf.format(calendar.getTime()), action.setTodayDate(null,dateFormat,-18));
		
		calendar.add(Calendar.DAY_OF_MONTH, 18);
		assertEquals(sdf.format(calendar.getTime()), action.setTodayDate(null,dateFormat,0));
		
	}

}
