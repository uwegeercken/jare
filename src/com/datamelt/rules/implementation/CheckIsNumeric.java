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
 * @author uwe geercken
 */
public class CheckIsNumeric extends GenericCheck
{
	/**
     * checks, if cahracters of the string are numbers
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
