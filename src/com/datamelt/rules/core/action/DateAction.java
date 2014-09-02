package com.datamelt.rules.core.action;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.datamelt.rules.core.XmlAction;

public class DateAction
{
	public Date setValue(XmlAction action, Date value) throws Exception
	{
		return value;
	}

	/**
	 * method will set the value of the relevant object to the actual date.
	 * the dateFormat parameter is used to specify the date format according
	 * to the rules defined in the SimpleDateFormat class.
	 */
	public String setTodayDate(XmlAction action,String dateFormat)
	{
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		return sdf.format(new Date());
	}
	
	/**
	 * method will set the value of the relevant object to the actual date plus/minus
	 * the specified number of days offset.
	 * the dateFormat parameter is used to specify the date format according
	 * to the rules defined in the SimpleDateFormat class.
	 */
	public String setTodayDate(XmlAction action,String dateFormat, int daysOffset)
	{
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, daysOffset);
		return sdf.format(cal.getTime());
	}
	

}
