/*
 * Created on 28.06.2008
 *
 * all code by uwe geercken
 */
package com.datamelt.rules.implementation;

/**
 * checks if an integer is not between two given numbers 
 * 
 * @author uwe geercken
 */
public class CheckIsNotBetween extends GenericCheck
{
    public static boolean evaluate(int value,int valueLowerLimit, int valueUpperLimit)
    {
        return value<valueLowerLimit || value>valueUpperLimit;
    }
    
    public static boolean evaluate(long value,long valueLowerLimit, long valueUpperLimit)
    {
        return value<valueLowerLimit || value>valueUpperLimit;
    }
    
    public static boolean evaluate(int value,String valueLowerUpperLimit)
    {
    	String[] stringValues= valueLowerUpperLimit.split(",");
    	int lowerLimit = Integer.parseInt(stringValues[0].trim());
    	int upperLimit = Integer.parseInt(stringValues[1].trim());
    	
        return value<lowerLimit || value>upperLimit;
    }
    
    public static boolean evaluate(long value,String valueLowerUpperLimit)
    {
    	String[] stringValues= valueLowerUpperLimit.split(",");
    	long lowerLimit = Long.parseLong(stringValues[0].trim());
    	long upperLimit = Long.parseLong(stringValues[1].trim());
    	
        return value<lowerLimit || value>upperLimit;
    }
}
