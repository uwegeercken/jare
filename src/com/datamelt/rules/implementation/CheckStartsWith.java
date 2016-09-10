package com.datamelt.rules.implementation;

/**
 * Checks if a string starts with a given other string.
 * <p>
 * The first parameter of a given method is always the value of the field that shall be checked. The second parameter is either another field to check against 
 * or an expected value (fixed value) to check against the first value.
 * 
 * @author uwe geercken
 */
public class CheckStartsWith extends GenericCheck
{
	/**
     * Check if the specified value starts with given compare value
     * 
     * @param value			the first value for comparison
     * @param compareValue	the second value for comparison - to compare against the first value
     * @return				indication if the first value starts with the second value
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
     * Check if the specified value starts with given compare value
     * 
     * @param value			the first value for comparison
     * @param compareValue	the second value for comparison - to compare against the first value
     * @param ignoreCase	indication if the case of the values shall be ignored for comparison
     * @return				indication if the first value starts with the second value
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
