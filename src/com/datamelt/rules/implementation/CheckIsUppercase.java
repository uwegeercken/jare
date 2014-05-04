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
public class CheckIsUppercase extends GenericCheck
{
   
	/**
     * checks, if the string consists of uppercase characters
     * only
     */
	public static boolean evaluate(String value)
    {
        if(value!=null)
        {
			if(value.equals(value.toUpperCase()))
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
