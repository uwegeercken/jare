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
package com.datamelt.rules.implementation;

import java.util.Date;

import com.datamelt.util.CheckAnnotation;

import java.util.Calendar;

/**
 * Checks if the time part of a date is between two given times.
 * 
 * The times (lower limit/upper limit)  specified must be in the format: HH:mm:ss (hours, minutes, seconds). Example: 10:22:35
 * <p>
 * 
 * The first parameter of a given method is always the value of the field that shall be checked. The second parameter is either another field to check against 
 * or an expected value (fixed value) to check against the first value.
 * 
 * @author uwe geercken
 */
@CheckAnnotation(name="Check Date Time Is Between", description="Checks if the time part of a date is between two given time value specified in the format HH:mm:ss (hours, minutes, seconds). Separate the time values using a comma",nameDescriptive="time part is between",checkSingleField=1)
public class CheckDateTimeIsBetween extends GenericCheck
{
	/**
	 * Evaluate if the time part of a date is between two given times.
	 * 
	 * The times (lower limit/upper limit) specified must be in the format: HH:mm:ss (hours, minutes, seconds). The
	 * 
     * @param date					the first value for comparison
     * @param timeValues			time value for the lower limit and upper limit separated by a comma
     * @return						indication if the time part of the date is between the upper and lower limit
     */
    public static boolean evaluate(Date date, String timeValues)
    {
    	Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date);
        
    	int dateHours = cal1.get(Calendar.HOUR_OF_DAY);
    	int dateMinutes = cal1.get(Calendar.MINUTE);
    	int dateSeconds = cal1.get(Calendar.SECOND);
    	
    	int totalDateSeconds = dateSeconds + dateMinutes * 60 + dateHours * 3600;
    	
    	int totalTime1Seconds = 0;
    	int totalTime2Seconds = 0;

        String time1 = "";
        String time2 = "";

    	if(timeValues!=null)
    	{
	    	String[] times = timeValues.split(",");
	        
	        time1 = times[0].trim();
	        time2 = times[1].trim();
    	}
    	if(time1.length()==8)
    	{
    	
	    	int time1Hours = Integer.parseInt(time1.substring(0,2));
	    	int time1Minutes = Integer.parseInt(time1.substring(3,5));
	    	int time1Seconds = Integer.parseInt(time1.substring(6,8));

	    	totalTime1Seconds = time1Seconds + time1Minutes * 60 + time1Hours * 3600;
    	}
    	
    	if(time2.length()==8)
    	{
	    	int time2Hours = Integer.parseInt(time2.substring(0,2));
	    	int time2Minutes = Integer.parseInt(time2.substring(3,4));
	    	int time2Seconds = Integer.parseInt(time2.substring(6,8));
    	
	    	totalTime2Seconds = time2Seconds + time2Minutes * 60 + time2Hours * 3600;
    	}
    	
        return totalDateSeconds >= totalTime1Seconds && totalDateSeconds <= totalTime2Seconds;
    }
    
}
