/*
 * Created on 15.09.2006
 *
 * all code by uwe geercken
 */
package com.datamelt.rules.implementation;

/**
 * Checks if a string is empty meaning: it is of zero length and is NOT null.
 * <p>
 * 
 * The first parameter of a given method is always the value of the field that shall be checked. The second parameter is either another field to check against 
 * or an expected value (fixed value) to check against the first value.
 * 
 * @author uwe geercken
 */
public class CheckIsEmpty extends GenericCheck
{
    
    /**
     * Evaluates if a string is empty
     * 
     * @param value	the value to evaluate
     * @return		indication if the given string is empty
     */
    public static boolean evaluate(String value)
    {
        if(value!=null)
        {
	    	if(value.equals(""))
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
