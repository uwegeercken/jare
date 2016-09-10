/*
 * Created on 06.06.2014
 *
 * all code by uwe geercken
 */
package com.datamelt.rules.implementation;

/**
 * checks if a given integer is not a prime number
 * <p>
 * The first parameter of a given method is always the value of the field that shall be checked. The second parameter is either another field to check against 
 * or an expected value (fixed value) to check against the first value.
 * 
 * @author uwe geercken
 */
public class CheckIsNotPrime extends GenericCheck
{
	/**
     * Evaluates if a number is not a prime number
     * 
     * @param value		the value for comparison
     * @return			indication if the value is not a prime number
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
