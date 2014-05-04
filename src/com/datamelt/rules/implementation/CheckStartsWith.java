/*
 * Created on 15.09.2006
 *
 * all code by uwe geercken
 */
package com.datamelt.rules.implementation;

/**
 * checks if a given value starts with the compare value.
 * 
 * @author uwe geercken
 */
public class CheckStartsWith extends GenericCheck
{
    public static boolean evaluate(String value,String compareValue)
    {
    	if(value!=null && compareValue!=null)
        {
    		return value.startsWith(compareValue);
        }
    	else
    	{
    		return false;
    	}
    	
    }
    
    /**
     * checks if a string of characters starts with a certain value
     * and ignores or does not ignore the case of the values. 
     */
    public static boolean evaluate(String value,String compareValue,boolean ignoreCase)
    {
    	if(value!=null && compareValue!=null)
        {
	    	if(!ignoreCase)
	        {
	            return value.startsWith(compareValue);
	        }
	        else
	        {
	            return value.toLowerCase().startsWith(compareValue.toLowerCase());
	        }
        }
    	else
    	{
    		return false;
    	}
    }
    
}
