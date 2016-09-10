package com.datamelt.rules.implementation;

import com.datamelt.rules.core.util.Soundex;

/**
 * checks if a string sounds like a given string using the soundex algorithm. 
 * <p>
 * The first parameter of a given method is always the value of the field that shall be checked. The second parameter is either another field to check against 
 * or an expected value (fixed value) to check against the first value.
 * 
 * @author uwe geercken
 */
public class CheckSoundsLike extends GenericCheck
{
	/**
	 * Checks if a string sounds like a given string using the soundex algorithm 
     * 
     * @param value			the first value for comparison
     * @param expectedValue	the second value for comparison - to compare against the first value
     * @return				indication if the first value sounds like the second value
     */
    public static boolean evaluate(String value, String expectedValue)
    {
    	if(value!=null && expectedValue!=null)
        {
    		return Soundex.soundex(value).equals(Soundex.soundex(expectedValue));
        }
    	else
    	{
    		return false;
    	}
    }

}
