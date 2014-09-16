/*
 * Created on 15.09.2006
 *
 * all code by uwe geercken
 */
package com.datamelt.rules.implementation;

/**
 * <p>Checks if a string ends with a certain other string.</p>
 * 
 * <p>
 * The first argument of a method is always the value of the field that one wants to check. The second argument is either another field 
 * or an expected (fixed) value to check against the first value.
 * </p>
 * <p>
 * Some methods may have additional arguments that can be passed to it.
 * </p>
 * 
 * @author uwe geercken
 * 
 */
public class CheckEndsWith extends GenericCheck
{
    public static boolean evaluate(String value,String compareValue)
    {
        if(value!=null && compareValue!=null)
        {
        	return value.endsWith(compareValue);
        }
        else
        {
        	return false;
        }
    }
    
    /**
     * checks if a string of characters ends with a certain string
     * and ignores or does not ignore the case of the values. 
     */
    public static boolean evaluate(String value,String compareValue,boolean ignoreCase)
    {
    	if(value!=null && compareValue!=null)
        {
	    	if(!ignoreCase)
	        {
	            return value.endsWith(compareValue);
	        }
	        else
	        {
	            return value.toLowerCase().endsWith(compareValue.toLowerCase());
	        }
        }
    	else
        {
        	return false;
        }
    }
}
