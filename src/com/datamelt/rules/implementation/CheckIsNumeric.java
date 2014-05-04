/*
 * Created on 15.09.2006
 *
 * all code by uwe geercken
 */
package com.datamelt.rules.implementation;

/**
 * @author uwe geercken
 */
public class CheckIsNumeric extends GenericCheck
{
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
