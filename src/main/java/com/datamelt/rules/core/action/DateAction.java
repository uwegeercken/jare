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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.datamelt.rules.core.XmlAction;
import com.datamelt.util.ActionAnnotation;
import com.datamelt.util.ActionMethodAnnotation;
/**
 * Class containing possible actions that are related to dates.
 * 
 * Actions belong to a rulegroup and are execute depending on the status of rulegroup - if it passed or failed (or both).
 *
 * @author uwe geercken
 * 
 */
public class DateAction extends GenericAction
{
	// the default date format used whenever no other format is specified
	public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";

	/**
	 * sets the date to the given date and returns it as a String value
	 * 
	 * @param action 		the action use
	 * @param value			the value to set
	 * @param dateFormat	date format to use
	 * @return				date
	 * @throws Exception	exception in date handling
	 */
	@ActionAnnotation(description= "Set a value to a given date",methodDisplayname="set value (date)")
	@ActionMethodAnnotation(note= "last parameter: the date format")
	public String setValue(XmlAction action, Date value, String dateFormat) throws Exception
	{
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		try
		{
			return sdf.format(value);
		}
		catch(Exception ex)
		{
			return null;
		}
		
	}
	
	/**
	 * sets the date to the given date
	 * 
	 * @param action		the action to use
	 * @param value			the value to set
	 * @return				the date in the default format
	 */
	@ActionAnnotation(description= "Set a value to a given date represented as a string",methodDisplayname="set value (date)")
	@ActionMethodAnnotation(note= "the string will be interpreted as a date using format: " + DEFAULT_DATE_FORMAT)
	public Date setValue(XmlAction action, String value)
	{
		SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
		Calendar cal = Calendar.getInstance();
		try
		{
			cal.setTime(sdf.parse(value));
			return cal.getTime();
		}
		catch(Exception ex)
		{
			return null;
		}
	}

	/**
	 * sets the date to the given date
	 * 
	 * @param action		the action to use
	 * @param value			the value to set
	 * @param dateFormat	the format to use for the date
	 * @return				the date in the default format
	 */
	@ActionAnnotation(description= "Set a value to a given date represented as a string",methodDisplayname="set value (date)")
	@ActionMethodAnnotation(note= "the last parameter specifies the date format of the string")
	public Date setValue(XmlAction action, String value, String dateFormat)
	{
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		Calendar cal = Calendar.getInstance();
		try
		{
			cal.setTime(sdf.parse(value));
			return cal.getTime();
		}
		catch(Exception ex)
		{
			return null;
		}
	}
	
	/**
	 * sets the date to the given date and returns it in the specified format
	 * as a string
	 * 
	 * @param action		the action to use
	 * @param value			the value to set
	 * @param dateFormat	the format to use for the date
	 * @param returnFormat	the  return format to use for the date
	 * @return				the date in the specified format
	 */
	@ActionAnnotation(description= "Set a value to a given date represented as a string",methodDisplayname="set value (date)")
	@ActionMethodAnnotation(note= "the second parameter specifies the date format of the string, the third parameter specifies the return format")
	public String setValue(XmlAction action, String value, String dateFormat, String returnFormat)
	{
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		SimpleDateFormat sdfReturn = new SimpleDateFormat(returnFormat);
		Calendar cal = Calendar.getInstance();
		try
		{
			cal.setTime(sdf.parse(value));
			return sdfReturn.format(cal.getTime());
		}
		catch(Exception ex)
		{
			return null;
		}
	}
	
	/**
	 * calculates the number of minutes from a decimal representation of hours.
	 * 
	 * note that the seconds part (if present) will be stripped away.
	 * 
	 * example: 41.5 hours = 41*60 minutes + 0.5 * 60 minutes
	 * 
	 * @param action		the action to use
	 * @param value			the value to convert
	 * @return				the number of minutes calculated
	 */
	@ActionAnnotation(description= "Calculates the number of minutes from a decimal representation of hours",methodDisplayname="decimal hours to minutes")
	public long decimalHoursToMinutes(XmlAction action, float value)
	{
		int hours = (int) value;
		int minutes = (int) (value * 60) % 60;
		return hours * 60 + minutes;
	}
	
	/**
	 * calculates the hours and minutes from a decimal representation of hours and outputs
	 * the result as a string formatted as: hh:mm
	 * 
	 * note that the seconds part (if present) will be stripped away.
	 * 
	 * example: 41.5 hours = 41:30
	 * 
	 * @param action		the action to use
	 * @param value			the value to convert
	 * @return				the hours and minutes formatted as a string
	 */
	@ActionAnnotation(description= "Calculates the hours and minutes from a decimal representation of hours",methodDisplayname="decimal hours to hours and minutes")
	@ActionMethodAnnotation(note= "return format is hh:mm")
	public String decimalHoursToHoursMinutes(XmlAction action, float value)
	{
		int hours = (int) value;
		int minutes = (int) (value * 60) % 60;
		return hours + ":" + minutes;
	}
	
	/**
	 * calculates the number of seconds from a decimal representation of hours.
	 * 
	 * @param action		the action to use
	 * @param value			the value to convert
	 * @return				the number of seconds calculated
	 */
	@ActionAnnotation(description= "Calculates the number of seconds from a decimal representation of hours",methodDisplayname="decimal hours to seconds")
	public long decimalHoursToSeconds(XmlAction action, float value)
	{
		int hours = (int) value;
		int minutes = (int) (value * 60) % 60;
		int seconds = (int) (value * (60*60)) % 60;
		
		return hours * 3600 + minutes * 60 + seconds;
	}
	
	/**
	 * calculates the hours and minutes and seconds from a decimal representation of hours and outputs
	 * the result as a string formatted as: hh:mm:ss
	 * 
	 * example: 41.5 hours = 41:30:00
	 * 
	 * @param action		the action to use
	 * @param value			the value to convert
	 * @return				the hours and minutes and seconds formatted as a string
	 */
	@ActionAnnotation(description= "Calculates the hours,minutes and seconds from a decimal representation of hours",methodDisplayname="decimal hours to hours, minutes, seconds")
	@ActionMethodAnnotation(note= "return format is hh:mm:ss")
	public String decimalHoursToHoursMinutesSeconds(XmlAction action, float value)
	{
		int hours = (int) value;
		int minutes = (int) (value * 60) % 60;
		int seconds = (int) (value * (60*60)) % 60;
		return hours + ":" + minutes + ":" + seconds;
	}
	
	/**
	 * method will set the value of the relevant object to the current date.
	 * the dateFormat parameter is used to specify the date format according
	 * to the specification defined in the SimpleDateFormat class.
	 * 
	 * @param action		the action to use
	 * @param dateFormat	the format to use for the date
	 * @return				the date as a string in the default format
	 */
	@ActionAnnotation(description= "Set a value to todays date",methodDisplayname="set today date")
	@ActionMethodAnnotation(note= "last parameter is the format of the returned date")
	public String setTodayDate(XmlAction action,String dateFormat)
	{
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		return sdf.format(new Date());
	}
	
	/**
	 * method will set the value of the relevant object to the current date.
	 * 
	 * @param action		the action to use
	 * @return				the date in the default format
	 */
	@ActionAnnotation(description= "Set a value to todays date",methodDisplayname="set today date")
	public Date setTodayDate(XmlAction action)
	{
		return new Date();
	}
	
	/**
	 * method will set the value of the relevant object to the current date plus/minus
	 * the specified number of days offset.
	 * the dateFormat parameter is used to specify the date format according
	 * to the specification defined in the SimpleDateFormat class.
	 * 
 	 * @param action		the action to use
	 * @param dateFormat	the format to use for the date
	 * @param daysOffset	the number of days offset from today

	 * @return	todays date in the given format as a string
	 */
	@ActionAnnotation(description= "Set a value to todays date",methodDisplayname="set today date")
	@ActionMethodAnnotation(note= "2nd parameter is the format of the returned date, 3rd parameter is number of days offset")
	public String setTodayDate(XmlAction action,String dateFormat, int daysOffset)
	{
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, daysOffset);
		return sdf.format(cal.getTime());
	}
	
	/**
	 * method will set the value of the relevant object to the date of the
	 * last day of the month.
	 * The default date format will be used to format the returned string
	 * 
	 * @param action		the action to use
	 * @param year			the year used for calculation
	 * @param month			the month used for calculation
	 * @return	todays date in the default format as a string
	 */
	@ActionAnnotation(description= "Set a value to the last day of the month",methodDisplayname="set last day of month")
	@ActionMethodAnnotation(note= "2nd parameter is the year, 3rd parameter is the month, using the default date format: " + DEFAULT_DATE_FORMAT)
	public String setLastDayOfMonth(XmlAction action,int year, int month)
	{
		SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
		Calendar cal = Calendar.getInstance();
		
	    cal.set(Calendar.YEAR, year);
	    cal.set(Calendar.MONTH, month -1);
	    cal.set(Calendar.DAY_OF_MONTH, 1);
	    cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		
		return sdf.format(cal.getTime());
	}
	
	/**
	 * method will set the value of the relevant object to the date of the
	 * last day of the current month.
	 * 
	 * @param action		the action to use
	 * @return				last day of the current month
	 */
	@ActionAnnotation(description= "Set a value to the last day of the month",methodDisplayname="set last day of month")
	public Date setLastDayOfMonth(XmlAction action)
	{
		Calendar cal = Calendar.getInstance();
	    cal.set(Calendar.DAY_OF_MONTH, 1);
	    cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		
		return cal.getTime();
	}
	
	/**
	 * method will set the value of the relevant object to the date of the
	 * last day of the month.
	 * the dateFormat parameter is used to specify the date format according
	 * to the specification defined in the SimpleDateFormat class.
	 * 
	 * @param action		the action to use
	 * @param date			the date involved
	 * @return				last day of the month of the given date
	 */
	@ActionAnnotation(description= "Set a value to the last day of the month",methodDisplayname="set last day of month")
	public Date setLastDayOfMonth(XmlAction action,Date date)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
	    cal.set(Calendar.DAY_OF_MONTH, 1);
	    cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		
		return cal.getTime();
	}
	
	/**
	 * method will set the value of the relevant object to the year of the given date
	 * 
	 * @param action		the action to use
	 * @param date			the date involved
	 * @return				the year value of the given date
	 */
	@ActionAnnotation(description= "Set a value to the year of a given date",methodDisplayname="set year")
	public int setYear(XmlAction action, Date date)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		
		return cal.get(Calendar.YEAR);
	}
	
	/**
	 * method will set the value of the relevant object to the month of the given date
	 * 
	 * @param action		the action to use
	 * @param date			the date involved
	 * @return				the month value of the given date
	 */
	@ActionAnnotation(description= "Set a value to the month of a given date",methodDisplayname="set month")
	public int setMonth(XmlAction action, Date date)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		
		return cal.get(Calendar.MONTH);
	}
	
	/**
	 * method will set the value of the relevant object to the week of year of the given date
	 * 
	 * @param action		the action to use
	 * @param date			the date involved
	 * @return				the week of year value of the given date
	 */
	@ActionAnnotation(description= "Set a value to the wwek number of a given date",methodDisplayname="set week")
	public int setWeek(XmlAction action, Date date)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		
		return cal.get(Calendar.WEEK_OF_YEAR);
	}
	
	/**
	 * method will set the value of the relevant object to the day of week of the given date
	 * 
	 * @param action		the action to use
	 * @param date			the date involved
	 * @return				the day of week value of the given date
	 */
	@ActionAnnotation(description= "Set a value to the day of week of a given date",methodDisplayname="set day of week")
	public int setDayOfWeek(XmlAction action, Date date)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
	
		return cal.get(Calendar.DAY_OF_WEEK);
	}
	
	/**
	 * method will set the value of the relevant object to the day of week of the given date
	 * 
	 * @param action			the action to use
	 * @param date				the date involved
	 * @param firstDayOfWeek	the day which starts the week
	 * @return					the day of week value of the given date
	 */
	@ActionAnnotation(description= "Set a value to the day of week of a given date",methodDisplayname="set day of week")
	@ActionMethodAnnotation(note= "last parameter is the indicator which day starts the week")
	public int setDayOfWeek(XmlAction action, Date date, int firstDayOfWeek)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.setFirstDayOfWeek(firstDayOfWeek);
		
		return cal.get(Calendar.DAY_OF_WEEK);
	}
	
	/**
	 * 
	 * @param action		the action to use
	 * @param date			the date involved
	 * @return				null
	 */
	@ActionAnnotation(description= "Set a value to null",methodDisplayname="set null value (date)")
	@ActionMethodAnnotation(note= "returned as a Long with value of null")
	public Long setNull(XmlAction action, Date date)
	{
		return null;
	}
	
	/**
	 * method will set the value of the relevant object to the quarter of the year of the given date
	 * 
	 * @param action		the action to use
	 * @param date			the date involved
	 * @return				the quarter of the year value of the given date
	 */
	@ActionAnnotation(description= "Set a value to the quarter of a given date",methodDisplayname="set quarter")
	public int setQuarter(XmlAction action, Date date)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		
		int month =  cal.get(Calendar.MONTH);
		if(month<=3)
		{
			return 1;
		}
		else if(month <=6)
		{
			return 2;
		}
		else if(month <=9)
		{
			return 3;
		}
		else
		{
			return 4;
		}
	}
	
	/**
	 * method will set the value of the relevant object to the date of the
	 * last day of the month.
	 * the dateFormat parameter is used to specify the date format according
	 * to the specification defined in the SimpleDateFormat class.
	 * 
	 * @param action		the action to use
	 * @param year			the year to use	 
	 * @param month			the month to use
	 * @param dateFormat	the date format to use
	 * @return				last day of the given year and month in the given format as a string
	 */
	@ActionAnnotation(description= "Set a value to the last day of the month",methodDisplayname="set last day of month")
	@ActionMethodAnnotation(note= "2nd parameter is the year, 3rd parameter is the month, 4th parameter is the date format to use")
	public String setLastDayOfMonth(XmlAction action,int year, int month,String dateFormat)
	{
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		Calendar cal = Calendar.getInstance();
		
	    cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month -1);
	    cal.set(Calendar.DAY_OF_MONTH, 1);
	    cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		
		return sdf.format(cal.getTime());
	}
	
	/**
	 * method will set the value of the relevant object to the date of the
	 * first day of the month.
	 * The default date format will be used to format the returned string
	 * 
	 * @param action		the action to use
	 * @param year			the year to use	 
	 * @param month			the month to use
	 * @return				first day of the given year and month in the default format
	 */
	@ActionAnnotation(description= "Set a value to the first day of the month",methodDisplayname="set first day of month")
	@ActionMethodAnnotation(note= "2nd parameter is the year, 3rd parameter is the month")
	public String setFirstDayOfMonth(XmlAction action,int year, int month)
	{
		SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
		Calendar cal = Calendar.getInstance();
		
	    cal.set(Calendar.YEAR, year);
	    cal.set(Calendar.MONTH, month -1);
	    cal.set(Calendar.DAY_OF_MONTH, 1);
		
		return sdf.format(cal.getTime());
	}
	
	/**
	 * method will set the value of the relevant object to the date of the
	 * first day of the current month.
	 * 
	 * @param action		the action to use
	 * @return				first day of the current month
	 */
	@ActionAnnotation(description= "Set a value to the first day of the current month",methodDisplayname="set first day of month")
	public Date setFirstDayOfMonth(XmlAction action)
	{
		Calendar cal = Calendar.getInstance();
	    cal.set(Calendar.DAY_OF_MONTH, 1);
		
		return cal.getTime();
	}
	
	/**
	 * method will set the value of the relevant object to the date of the
	 * first day of the month.
	 * the dateFormat parameter is used to specify the date format according
	 * to the specification defined in the SimpleDateFormat class.
	 * 
	 * @param action		the action to use
	 * @param date			the date involved	 
	 * @return				first day of the month of the given date
	 */
	@ActionAnnotation(description= "Set a value to the first day of the month",methodDisplayname="set first day of month")
	public Date setFirstDayOfMonth(XmlAction action,Date date)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
	    cal.set(Calendar.DAY_OF_MONTH, 1);
		
		return cal.getTime();
	}
	
	/**
	 * method will set the value of the relevant object to the date of the
	 * first day of the month.
	 * the dateFormat parameter is used to specify the date format according
	 * to the specification defined in the SimpleDateFormat class.
	 * 
	 * @param action		the action to use
	 * @param year			the year to use	 
	 * @param month			the month to use
	 * @param dateFormat	the date format to use
	 * @return				first day of the month of the given year and month in the specified format
	 */
	@ActionAnnotation(description= "Set a value to the first day of the month",methodDisplayname="set first day of month")
	@ActionMethodAnnotation(note= "2nd parameter is the year, 3rd parameter is the month, 4th parameter is the date format to use")
	public String setFirstDayOfMonth(XmlAction action,int year, int month,String dateFormat)
	{
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		Calendar cal = Calendar.getInstance();
		
	    cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month -1);
	    cal.set(Calendar.DAY_OF_MONTH, 1);
		
		return sdf.format(cal.getTime());
	}

	/**
	 * method will set the value of the relevant object to the date of the
	 * mid day of the month.
	 * The default date format will be used to format the returned string
	 * 
	 * @param action		the action to use
	 * @param year			the year to use	 
	 * @param month			the month to use
	 * @return				mid day of the month of the given year and month
	 */
	@ActionAnnotation(description= "Set a value to the mid day (15th) of the month",methodDisplayname="set mid day of month")
	@ActionMethodAnnotation(note= "2nd parameter is the year, 3rd parameter is the month")
	public String setMidDayOfMonth(XmlAction action,int year, int month)
	{
		SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
		Calendar cal = Calendar.getInstance();
		
	    cal.set(Calendar.YEAR, year);
	    cal.set(Calendar.MONTH, month -1);
	    cal.set(Calendar.DAY_OF_MONTH, 15);
		
		return sdf.format(cal.getTime());
	}
	
	/**
	 * method will set the value of the relevant object to the date of the
	 * mid day of the current month.
	 * 
	 * @param action		the action to use
	 * @return				mid day of the month of the current date
	 */
	@ActionAnnotation(description= "Set a value to the mid day (15th) of the month",methodDisplayname="set mid day of month")
	public Date setMidDayOfMonth(XmlAction action)
	{
		Calendar cal = Calendar.getInstance();
	    cal.set(Calendar.DAY_OF_MONTH, 15);
		
		return cal.getTime();
	}
	
	/**
	 * method will set the value of the relevant object to the date of the
	 * mid day of the month.
	 * the dateFormat parameter is used to specify the date format according
	 * to the specification defined in the SimpleDateFormat class.
	 * 
	 * @param action		the action to use
	 * @param date			the date involved	 
	 * @return				mid day of the month of the given date
	 */
	@ActionAnnotation(description= "Set a value to the mid day of the month",methodDisplayname="set mid day of month")
	public Date setMidDayOfMonth(XmlAction action,Date date)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
	    cal.set(Calendar.DAY_OF_MONTH, 15);
		
		return cal.getTime();
	}
	
	/**
	 * method will set the value of the relevant object to the date of the
	 * mid day of the month.
	 * the dateFormat parameter is used to specify the date format according
	 * to the specification defined in the SimpleDateFormat class.
	 * 
	 * @param action		the action to use
	 * @param year			the year to use	 
	 * @param month			the month to use
	 * @param dateFormat	the date format to use
	 * @return				mid day of the month of the given year and month in the specified format as a string
	 */
	@ActionAnnotation(description= "Set a value to the first day of the month",methodDisplayname="set first day of month")
	@ActionMethodAnnotation(note= "2nd parameter is the year, 3rd parameter is the month, 4th parameter is the date format to use")
	public String setMidDayOfMonth(XmlAction action,int year, int month,String dateFormat)
	{
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		Calendar cal = Calendar.getInstance();
		
	    cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month -1);
	    cal.set(Calendar.DAY_OF_MONTH, 15);

		return sdf.format(cal.getTime());
	}

	/**
	 * method will add the given number of seconds to the provided date
	 * 
	 * @param action		the action to use
	 * @param date			the date involved	 
	 * @param seconds		number of seconds to add
	 * @return				the given date plus the given seconds as a date
	 */
	@ActionAnnotation(description= "Add seconds to a date",methodDisplayname="add seconds")
	public Date addSeconds(XmlAction action, Date date, long seconds)
	{
		final long ONE_SECOND_IN_MILLISECONDS = 1000;
		
		long dateInMilliseconds = date.getTime();
	    return new Date(dateInMilliseconds + (seconds * ONE_SECOND_IN_MILLISECONDS));
	}
	
	/**
	 * method will add the given number of minutes to the provided date
	 * 
	 * @param action		the action to use
	 * @param date			the date involved	 
	 * @param minutes		number of minutes to add
	 * @return				the given date plus the given minutes as a date
	 */
	@ActionAnnotation(description= "Add minutes to a date",methodDisplayname="add minutes")
	public Date addMinutes(XmlAction action, Date date, long minutes)
	{
		final long ONE_MINUTE_IN_MILLISECONDS = 60000;
		
		long dateInMilliseconds = date.getTime();
	    return new Date(dateInMilliseconds + (minutes * ONE_MINUTE_IN_MILLISECONDS));
	}

	/**
	 * method will add the given number of hours to the provided date
	 * 
	 * @param action		the action to use
	 * @param date			the date involved	 
	 * @param hours		number of hours to add
	 * @return				the given date plus the given hours as a date
	 */
	@ActionAnnotation(description= "Add hours to a date",methodDisplayname="add hours")
	public Date addHours(XmlAction action, Date date, long hours)
	{
		final long ONE_HOUR_IN_MILLISECONDS = 60000 * 60 ;
		
		long dateInMilliseconds = date.getTime();
	    return new Date(dateInMilliseconds + (hours * ONE_HOUR_IN_MILLISECONDS));
	}
	
	/**
	 * method will add the given number of days to the provided date
	 * 
	 * @param action		the action to use
	 * @param date			the date involved	 
	 * @param days		number of days to add
	 * @return				the given date plus the given days as a date
	 */
	@ActionAnnotation(description= "Add days to a date",methodDisplayname="add days")
	public Date addDays(XmlAction action, Date date, long days)
	{
		final long ONE_DAY_IN_MILLISECONDS = 60000 * 60 * 24;
		
		long dateInMilliseconds = date.getTime();
	    return new Date(dateInMilliseconds + (days * ONE_DAY_IN_MILLISECONDS));
	}

	/**
	 * method will subtract the given number of seconds from the provided date
	 * 
	 * @param action		the action to use
	 * @param date			the date involved	 
	 * @param seconds		number of seconds to subtract
	 * @return				the given date minus the given seconds as a date
	 */
	@ActionAnnotation(description= "Subtract seconds from a date",methodDisplayname="subtract seconds")
	public Date subtractSeconds(XmlAction action, Date date, long seconds)
	{
		final long ONE_SECOND_IN_MILLISECONDS = 1000;
		
		long dateInMilliseconds = date.getTime();
	    return new Date(dateInMilliseconds - (seconds * ONE_SECOND_IN_MILLISECONDS));
	}
	
	/**
	 * method will subtract the given number of minutes from the provided date
	 * 
	 * @param action		the action to use
	 * @param date			the date involved	 
	 * @param minutes		number of minutes to subtract
	 * @return				the given date minus the given minutes as a date
	 */
	@ActionAnnotation(description= "Subtract minutes from a date",methodDisplayname="subtract minutes")
	public Date subtractMinutes(XmlAction action, Date date, long minutes)
	{
		final long ONE_MINUTE_IN_MILLISECONDS = 60000;
		
		long dateInMilliseconds = date.getTime();
	    return new Date(dateInMilliseconds - (minutes * ONE_MINUTE_IN_MILLISECONDS));
	}

	/**
	 * method will subtract the given number of hours from the provided date
	 * 
	 * @param action		the action to use
	 * @param date			the date involved	 
	 * @param hours		number of hours to subtract
	 * @return				the given date minus the given hours as a date
	 */
	@ActionAnnotation(description= "Subtract hours from a date",methodDisplayname="subtract hours")
	public Date subtractHours(XmlAction action, Date date, long hours)
	{
		final long ONE_HOUR_IN_MILLISECONDS = 60000 * 60 ;
		
		long dateInMilliseconds = date.getTime();
	    return new Date(dateInMilliseconds - (hours * ONE_HOUR_IN_MILLISECONDS));
	}
	
	/**
	 * method will subtract the given number of days from the provided date
	 * 
	 * @param action		the action to use
	 * @param date			the date involved	 
	 * @param days		number of days to add
	 * @return				the given date subtract the given days as a date
	 */
	@ActionAnnotation(description= "Subtract days from a date",methodDisplayname="subtract days")
	public Date subtractDays(XmlAction action, Date date, long days)
	{
		final long ONE_DAY_IN_MILLISECONDS = 60000 * 60 * 24;
		
		long dateInMilliseconds = date.getTime();
	    return new Date(dateInMilliseconds - (days * ONE_DAY_IN_MILLISECONDS));
	}

}
