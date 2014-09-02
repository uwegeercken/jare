/*
 * Created on 27.06.2014
 *
 * all code by uwe geercken
 */
package com.datamelt.rules.implementation;

/**
 * checks if a given integer is not an even number
 * 
 * @author uwe geercken
 */
public class CheckIsNotEven extends GenericCheck
{
	/**
     * checks if a given integer is not an even number
     */
    public static boolean evaluate(int value)
    {
    	if (value % 2==0)
    	{
    		return false;
    	}
    	else
    	{
    		return true;
    	}
    }
    
    /**
     * checks if a given long is not an even number
     */
    public static boolean evaluate(long value)
    {
    	if (value % 2==0)
    	{
    		return false;
    	}
    	else
    	{
    		return true;
    	}
    }
}
