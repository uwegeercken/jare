/*
 * Created on 15.09.2006
 *
 * all code by uwe geercken
 */
package com.datamelt.rules.implementation;

/**
 * checks if a string of characters matches a given pattern.
 * the pattern is a regular expression as documented in the
 * java api.
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
 */
public class CheckMatches extends GenericCheck
{
	/**
	 * specify the string to evaluate and a regular expression pattern 
	 */
    public static boolean evaluate(String value,String pattern)
    {
    	if(value!=null && pattern!=null)
    	{
    		return value.matches(pattern);
    	}
    	else
    	{
    		return false;
    	}
    }
}
