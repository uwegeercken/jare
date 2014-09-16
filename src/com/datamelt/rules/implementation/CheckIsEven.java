/*
 * Created on 27.06.2014
 *
 * all code by uwe geercken
 */
package com.datamelt.rules.implementation;

/**
 * checks if a given integer is an even number
 * 
 * @author uwe geercken
 */
public class CheckIsEven extends GenericCheck
{
	/**
     * checks if a given integer is an even number
     */
    public static boolean evaluate(int value)
    {
    	if (value % 2==0)
    	{
    		return true;
    	}
    	else
    	{
    		return false;
    	}
    }
    
    /**
     * checks if a given long is an even number
     */
    public static boolean evaluate(long value)
    {
    	if (value % 2==0)
    	{
    		return true;
    	}
    	else
    	{
    		return false;
    	}
    }
}
