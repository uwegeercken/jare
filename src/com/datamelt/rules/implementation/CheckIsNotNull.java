/*
 * Created on 15.09.2006
 *
 * all code by uwe geercken
 */
package com.datamelt.rules.implementation;

/**
 * checks if a given value is NOT NULL and also
 * checks if its value is not equal to the string "null"
 * 
 * @author uwe geercken
 */
public class CheckIsNotNull extends GenericCheck
{
    public static boolean evaluate(String value)
    {
    	if(value!=null && !value.toLowerCase().equals("null"))
        {
            return true; 
        }
        else
        {
            return false;
        }
    }
    
}
