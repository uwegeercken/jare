/*
 * Created on 15.09.2006
 *
 * all code by uwe geercken
 */
package com.datamelt.rules.implementation;

/**
 * <p>Checks if a string is contained in another string.</p>
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
public class CheckContains extends GenericCheck
{
	/**
	 * Checks if a string is contained in another string.
	 */
    public static boolean evaluate(String value,String compareValue)
    {
        if(value!=null && compareValue!=null)
        {
	    	int pos = value.indexOf(compareValue);
	        if(pos>-1)
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
    
    /**
	 * Checks if a string is contained in another string and ignores or does not ignore
	 * the case of the values.
	 */
    public static boolean evaluate(String value,String compareValue, boolean ignoreCase)
    {
    	if(value!=null && compareValue!=null)
        {
	    	if(ignoreCase)
	        {
		    	int pos = value.toLowerCase().indexOf(compareValue.toLowerCase());
		        if(pos>-1)
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
		    	int pos = value.indexOf(compareValue);
		        if(pos>-1)
		        {
		            return true; 
		        }
		        else
		        {
		            return false;
		        }
	        }
        }
        else
        {
        	return false;
        }

	}
}

