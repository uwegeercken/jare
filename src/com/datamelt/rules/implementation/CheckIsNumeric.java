/*
 * Created on 15.09.2006
 *
 * all code by uwe geercken
 */
package com.datamelt.rules.implementation;

/**
 * checks if a given string value is numeric by checking if
 * all characters are numbers
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
public class CheckIsNumeric extends GenericCheck
{
	/**
     * checks, if characters of the string are numbers
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
