/*
 * Created on 06.06.2014
 *
 * all code by uwe geercken
 */
package com.datamelt.rules.implementation;

/**
 * checks if a given integer is a prime number
 * 
 * @author uwe geercken
 */
public class CheckIsNotPrime extends GenericCheck
{
	/**
     * checks if a given integer is a prime number
     */
    public static boolean evaluate(int value)
    {
    	if (value % 2==0)
    	{
    		return true;
    	}
        for(int i=3;i*i<=value;i+=2) 
        {
            if(value % i==0)
            {
                return true;
            }
        }
        return false;
    }
}
