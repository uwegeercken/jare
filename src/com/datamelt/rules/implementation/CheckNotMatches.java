/*
 * Created on 15.09.2006
 *
 * all code by uwe geercken
 */
package com.datamelt.rules.implementation;

/**
 * checks if a string of characters does not match a given pattern.
 * the pattern is a regular expression as documented in the
 * java api.
 * 
 * @author uwe geercken
 */
public class CheckNotMatches extends GenericCheck
{
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
