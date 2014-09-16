/*
 * Created on 15.09.2006
 *
 * all code by uwe geercken
 */
package com.datamelt.rules.implementation;

/**
 * checks, if the length of the string
 * (number of digits) if exactly equal to the given length
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
public class CheckLength extends GenericCheck
{
    
    /**
     * checks, if the length of the string
     * (number of digits) is exactly to the given length
     */
    public static boolean evaluate(String value, int length)
    {
        if(value!=null)
        {
	    	if(value.length()==length)
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
     * checks, if the length of the string
     * (number of digits) is exactly to the given length
     */
    public static boolean evaluate(String value, long length)
    {
        if(value!=null)
        {
	    	if(value.length()==length)
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
     * checks if the length of the integer value
     * (number of digits) is equal to the given length 
     */
    public static boolean evaluate(int value, int length)
    {
        // we make a string out of the number.
    	
    	// be aware, that leading zeros might have been
    	// trimmed from the number
    	String valueString = "" + value;
    	if(valueString.length()==length)
        {
            return true; 
        }
        else
        {
            return false;
        }
    }
    
    /**
     * checks if the length of the integer value
     * (number of digits) is equal to the given length 
     */
    public static boolean evaluate(int value, long length)
    {
        // we make a string out of the number.
    	
    	// be aware, that leading zeros might have been
    	// trimmed from the number
    	String valueString = "" + value;
    	if(valueString.length()==length)
        {
            return true; 
        }
        else
        {
            return false;
        }
    }
    
    /**
     * checks if the length of the long value
     * (number of digits) is equal to the given length 
     */
    public static boolean evaluate(long value, int length)
    {
        // we make a string out of the number.
    	
    	// be aware, that leading zeros might have been
    	// trimmed from the number
    	String valueString = "" + value;
    	if(valueString.length()==length)
        {
            return true; 
        }
        else
        {
            return false;
        }
    }
}
