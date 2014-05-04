/*
 * Created on 15.09.2006
 *
 * all code by uwe geercken
 */
package com.datamelt.rules.implementation;

/**
 * checks if a given value is null.
 * 
 * @author uwe geercken
 */
public class CheckIsNull extends GenericCheck
{
   
    public static boolean evaluate(String value)
    {
        if(value==null || value.toLowerCase().equals("null"))
        {
            return true; 
        }
        else
        {
            return false;
        }
    }
    
}
