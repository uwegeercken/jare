package com.datamelt.rules.implementation;

/**
 * checks if a given value contains lowercase characters only.
 * <p>
 * The first parameter of a given method is always the value of the field that shall be checked. The second parameter is either another field to check against 
 * or an expected value (fixed value) to check against the first value.
 * 
 * @author uwe geercken
 */
public class CheckIsLowercase extends GenericCheck
{
    /**
     * Evaluates if the given string contains lowercase characters only
     * 
     * @param value		the value to compare against
     * @return			indication if the value contains lowercase characters only
     */
    public static boolean evaluate(String value)
    {
        if(value!=null)
        {
	    	if(value.equals(value.toLowerCase()))
	        {
	            return true; 
	        }
	        else
	        {
	            return false;
	        }
        }
        else
        {
        	return false;
        }
    }
    
}
