/*
 * Created on 15.09.2006
 *
 * all code by uwe geercken
 */
package com.datamelt.rules.implementation;

/**
 * Checks if a string is contained in another string.
 * <p>
 * The first parameter of a given method is always the value of the field that shall be checked. The second parameter is either another field to check against 
 * or an expected value (fixed value) to check against the first value.
 * 
 * @author uwe geercken
 */
public class CheckContains extends GenericCheck
{
	/**
	 * Checks if the string provided as the second parameter is contained in the string provided as the first parameter.
     *
     * @param value 		the first value for comparison
     * @param compareValue 	the second value for comparison - to compare against the first value
     * @return				indication if the compareValue is contained in the value
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
	 *
     * @param value 		the first value for comparison
     * @param compareValue 	the second value for comparison - to compare against the first value
     * @param ignoreCase	indication if the case of the values shall be ignored for comparison
     * @return				indication if the compareValue is contained in the value
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

