/*
 * Created on 15.09.2006
 *
 * all code by uwe geercken
 */
package com.datamelt.rules.implementation;

/**
 * checks if a string of characters is NOT empty meaning: it is NOT of zero
 * length and is NOT null.
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
public class CheckIsNotEmpty extends GenericCheck
{
	/**
	 * specify a string to be evaluated
	 */
    public static boolean evaluate(String value)
    {
    	if(value!=null)
    	{
	        if(!value.equals(""))
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
