/*
 * Created on 15.09.2006
 *
 * all code by uwe geercken
 */
package com.datamelt.rules.implementation;

/**
 * checks if a certain string of characters is contained in another string
 * of characters
 * @author uwe geercken
 */
public class CheckContains extends GenericCheck
{
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
    
}
