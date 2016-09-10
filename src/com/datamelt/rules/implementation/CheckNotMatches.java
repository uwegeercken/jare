/*
 * Created on 15.09.2006
 *
 * all code by uwe geercken
 */
package com.datamelt.rules.implementation;

/**
 * checks if a string of characters does not match a given pattern.
 * <p>
 * the pattern is a regular expression as documented in the java api.
 * <p>
 * The first parameter of a given method is always the value of the field that shall be checked. The second parameter is either another field to check against 
 * or an expected value (fixed value) to check against the first value.
 * 
 * @author uwe geercken
 */
public class CheckNotMatches extends GenericCheck
{
	/**
     * Evaluates if the string does not match the given regular expression pattern
     * 
     * @param value		the value to compare
     * @param pattern	the pattern to compare against
     * @return			indication if the string does not match the pattern
     */
    public static boolean evaluate(String value,String pattern)
    {
        if(value!=null && pattern!=null)
        {
        	return !value.matches(pattern);
        }
        else
        {
        	return false;
        }
    }
    
}
