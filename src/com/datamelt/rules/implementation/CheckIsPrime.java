package com.datamelt.rules.implementation;

/**
 * checks if a given integer is a prime number
 * <p>
 * The first parameter of a given method is always the value of the field that shall be checked. The second parameter is either another field to check against 
 * or an expected value (fixed value) to check against the first value.
 * 
 * @author uwe geercken
 */
public class CheckIsPrime extends GenericCheck
{
	/**
     * Evaluates if a number is a prime number
     * 
     * @param value		the value for comparison
     * @return			indication if the value is a prime number
     */
    public static boolean evaluate(int value)
    {
    	if (value % 2==0)
    	{
    		return false;
    	}
        for(int i=3;i*i<=value;i+=2) 
        {
            if(value % i==0)
            {
                return false;
            }
        }
        return true;
    }
}
