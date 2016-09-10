package com.datamelt.rules.implementation;

/**
 * checks if a given string value is numeric by checking if all characters are numbers.
 * <p>
 * The first parameter of a given method is always the value of the field that shall be checked. The second parameter is either another field to check against 
 * or an expected value (fixed value) to check against the first value.
 * 
 * @author uwe geercken
 */
public class CheckIsNumeric extends GenericCheck
{
	/**
     * Evaluates if a string value contains only numbers
     * 
     * @param value		the value for comparison
     * @return			indication if the value is null
     */
    public static boolean evaluate(String value)
    {
        boolean isDigit=true;
        if(value!=null)
        {
	        for(int i=0; i<value.length(); i++)
	        {
	            boolean check = Character.isDigit(value.charAt(i));
		        if(!check)
		        {
		            isDigit = false;
		            break;
		        }
	        }
        }
        else
        {
        	isDigit = false;
        }
        return isDigit;
    }
}
