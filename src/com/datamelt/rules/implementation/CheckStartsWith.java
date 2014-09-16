/*
 * Created on 15.09.2006
 *
 * all code by uwe geercken
 */
package com.datamelt.rules.implementation;

/**
 * checks if a given value starts with the compare value.
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
public class CheckStartsWith extends GenericCheck
{
	/**
     * checks if a string of characters starts with a certain value
     */
    public static boolean evaluate(String value,String compareValue)
    {
    	if(value!=null && compareValue!=null)
        {
    		return value.startsWith(compareValue);
        }
    	else
    	{
    		return false;
    	}
    	
    }
    
    /**
     * checks if a string of characters starts with a certain value
     * and ignores or does not ignore the case of the values. 
     */
    public static boolean evaluate(String value,String compareValue,boolean ignoreCase)
    {
    	if(value!=null && compareValue!=null)
        {
	    	if(!ignoreCase)
	        {
	            return value.startsWith(compareValue);
	        }
	        else
	        {
	            return value.toLowerCase().startsWith(compareValue.toLowerCase());
	        }
        }
    	else
    	{
    		return false;
    	}
    }
    
}
