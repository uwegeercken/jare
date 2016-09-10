/*
 * Created on 15.09.2006
 *
 * all code by uwe geercken
 */
package com.datamelt.rules.implementation;

/**
 * checks, if the length of the given value (number of digits/characters) is equal to the given length.
 * <p>
 * The first parameter of a given method is always the value of the field that shall be checked. The second parameter is either another field to check against 
 * or an expected value (fixed value) to check against the first value.
 * 
 * @author uwe geercken
 */
public class CheckLength extends GenericCheck
{
    
	/**
     * Evaluates if the length (number of digits/characters) of the value is equal to the given length.
     * 
     * @param value		the value to compare
     * @param length	the length to compare against
     * @return			indication if the length of the value is equal to the expected length
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
     * Evaluates if the length (number of digits/characters) of the value is equal to the given length.
     * 
     * @param value		the value to compare
     * @param length	the length to compare against
     * @return			indication if the length of the value is equal to the expected length
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
     * Evaluates if the length (number of digits/characters) of the value is equal to the given length.
     * 
     * @param value		the value to compare
     * @param length	the length to compare against
     * @return			indication if the length of the value is equal to the expected length
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
     * Evaluates if the length (number of digits/characters) of the value is equal to the given length.
     * 
     * @param value		the value to compare
     * @param length	the length to compare against
     * @return			indication if the length of the value is equal to the expected length
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
     * Evaluates if the length (number of digits/characters) of the value is equal to the given length.
     * 
     * @param value		the value to compare
     * @param length	the length to compare against
     * @return			indication if the length of the value is equal to the expected length
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
