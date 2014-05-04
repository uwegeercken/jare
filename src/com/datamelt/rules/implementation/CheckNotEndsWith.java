/*
 * Created on 15.09.2006
 *
 * all code by uwe geercken
 */
package com.datamelt.rules.implementation;

/**
 * checks if a string of characters does not end with a certain compare value
 * @author uwe geercken
 */
public class CheckNotEndsWith extends GenericCheck
{
    public static boolean evaluate(String value,String compareValue)
    {
    	if(value!=null && compareValue!=null)
        {
    		return !value.endsWith(compareValue);
        }
    	else
    	{
    		return false;
    	}
    }
    
    /**
     * checks if a string of characters does not end with a certain value
     * and ignores or does not ignore the case of the values. 
     */
    public static boolean evaluate(String value,String compareValue,boolean ignoreCase)
    {
    	if(value!=null && compareValue!=null)
        {
	    	if(!ignoreCase)
	        {
	            return !value.endsWith(compareValue);
	        }
	        else
	        {
	            return !value.toLowerCase().endsWith(compareValue.toLowerCase());
	        }
        }
    	else
    	{
    		return false;
    	}
    }
    
}
