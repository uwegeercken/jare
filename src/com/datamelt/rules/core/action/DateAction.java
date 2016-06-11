package com.datamelt.rules.core.action;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.datamelt.rules.core.XmlAction;

public class DateAction
{
	
	public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
	
	public Date setValue(XmlAction action, Date value) throws Exception
	{
		return value;
	}
	
	public Date setValue(XmlAction action, String value) throws Exception
	{
		SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
		Calendar cal = Calendar.getInstance();
		cal.setTime(sdf.parse(value));
		return cal.getTime();
	}

	public Date setValue(XmlAction action, String value, String dateFormat) throws Exception
	{
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		Calendar cal = Calendar.getInstance();
		cal.setTime(sdf.parse(value));
		return cal.getTime();
	}
	/**
	 * method will set the value of the relevant object to the actual date.
	 * the dateFormat parameter is used to specify the date format according
	 * to the specification defined in the SimpleDateFormat class.
	 */
	public String setTodayDate(XmlAction action,String dateFormat)
	{
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		return sdf.format(new Date());
	}
	
	/**
	 * method will set the value of the relevant object to the current date.
	 */
	public Date setTodayDate(XmlAction action)
	{
		return new Date();
	}
	
	/**
	 * method will set the value of the relevant object to the current date plus/minus
	 * the specified number of days offset.
	 * the dateFormat parameter is used to specify the date format according
	 * to the specification defined in the SimpleDateFormat class.
	 */
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
	 */
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
	 */
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
	 */
	public Date setLastDayOfMonth(XmlAction action,Date date)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
	    cal.set(Calendar.DAY_OF_MONTH, 1);
	    cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		
		return cal.getTime();
	}
	
	/**
	 * method will set the value of the relevant object to the date of the
	 * last day of the month.
	 * the dateFormat parameter is used to specify the date format according
	 * to the specification defined in the SimpleDateFormat class.
	 */
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
	 */
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
	 */
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
	 */
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
	 */
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
	 */
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
	 */
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
	 */
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
	 */
	public String setMidDayOfMonth(XmlAction action,int year, int month,String dateFormat)
	{
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		Calendar cal = Calendar.getInstance();
		
	    cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month -1);
	    cal.set(Calendar.DAY_OF_MONTH, 15);

		return sdf.format(cal.getTime());
	}


}
