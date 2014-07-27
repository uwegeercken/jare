/*
 * Created on 15.09.2006
 *
 * all code by uwe geercken
 */
package com.datamelt.rules.implementation;

/**
 * checks if a string of characters is empty meaning: it is of zero
 * length and is NOT null.
 * 
 * @author uwe geercken
 */
public class CheckIsEmpty extends GenericCheck
{
    /**
	 * specify a string to be evaluated
	 */
    public static boolean evaluate(String value)
    {
        if(value!=null)
        {
	    	if(value.equals(""))
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
