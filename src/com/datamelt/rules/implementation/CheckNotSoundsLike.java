/*
 * Created on 28.06.2008
 *
 * all code by uwe geercken
 */
package com.datamelt.rules.implementation;

import com.datamelt.rules.core.util.Soundex;

/**
 * checks if a string does not sound like a given string using
 * the soundex algorithm 
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
public class CheckNotSoundsLike extends GenericCheck
{
	/**
	 * checks if a string does not sound like a given string using
	 * the soundex algorithm 
	 */
    public static boolean evaluate(String value, String expectedValue)
    {
        if(value!=null && expectedValue!=null)
        {
        	return !Soundex.soundex(value).equals(Soundex.soundex(expectedValue));
        }
        else
        {
        	return false;
        }
    }

}
