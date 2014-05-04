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
 * @author uwe geercken
 */
public class CheckNotSoundsLike extends GenericCheck
{
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
