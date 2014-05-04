/*
 * Created on 15.09.2006
 *
 * all code by uwe geercken
 */
package com.datamelt.rules.implementation;

/**
 * checks if a given value is null.
 * 
 * @author uwe geercken
 */
public class CheckIsLowercase extends GenericCheck
{
	/**
     * checks, if the string consists of lowercase characters
     * only
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
