/*
 * Created on 15.09.2006
 *
 * all code by uwe geercken
 */
package com.datamelt.rules.implementation;

/**
 * checks if a given value is NOT NULL and also
 * checks if its value is not equal to the string "null"
 * or "Null"
 * 
 * @author uwe geercken
 */
public class CheckIsNotNull extends GenericCheck
{
	/**
     * checks, if the string is not null and does not equal the
     * string "null" or "Null"
     */
    public static boolean evaluate(String value)
    {
    	if(value!=null && !value.toLowerCase().equals("null"))
        {
            return true; 
        }
        else
        {
            return false;
        }
    }
    
    
    public static boolean evaluate(long value)
    {
    	Long longValue = value;
    	if(longValue!=null)
        {
            return true; 
        }
        else
        {
            return false;
        }
    }
    
    public static boolean evaluate(int value)
    {
    	Integer integerValue = value;
    	if(integerValue!=null)
        {
            return true; 
        }
        else
        {
            return false;
        }
    }
    
    public static boolean evaluate(double value)
    {
    	Double doubleValue = value;
    	if(doubleValue!=null)
        {
            return true; 
        }
        else
        {
            return false;
        }
    }
    
    public static boolean evaluate(float value)
    {
    	Float floatValue = value;
    	if(floatValue!=null)
        {
            return true; 
        }
        else
        {
            return false;
        }
    }
    
}
