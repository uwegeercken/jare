/*
 * Created on 15.09.2006
 *
 * all code by uwe geercken
 */
package com.datamelt.rules.implementation;

/**
 * checks if a given value does NOT contain another value.
 * @author uwe geercken
 */
public class CheckNotContains extends GenericCheck
{
    public static boolean evaluate(String value,String compareValue)
    {
        if(value!=null && compareValue!=null)
        {
	        int pos = value.indexOf(compareValue);
	        if(pos>-1)
	        {
	            return false; 
	        }
	        else
	        {
	            return true;
	        }
        }
        else
        {
        	return false;
        }
    }
    
}
