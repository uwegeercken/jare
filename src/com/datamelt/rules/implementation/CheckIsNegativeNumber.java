package com.datamelt.rules.implementation;

/**
 * checks if a given value is smaller than zero (0).
 * 
 * <p>
 * The first parameter of a given method is always the value of the field that shall be checked. The second parameter is either another field to check against 
 * or an expected value (fixed value) to check against the first value.
 * 
 * @author uwe geercken
 */
public class CheckIsNegativeNumber extends GenericCheck
{
	
    /**
     * Evaluates if the given value is smaller than zero (0).
     * 
     * @param value1	the value for comparison	
     * @return			indication if the value is smaller zero
     */
    public static boolean evaluate(int value1)
    {
        if(value1 < 0)
        {
            return true; 
        }
        else
        {
            return false;
        }
    }
    
    /**
     * Evaluates if the given value is smaller than zero (0).
     * 
     * @param value1	the value for comparison	
     * @return			indication if the value is smaller zero
     */
    public static boolean evaluate(long value1)
    {
        if(value1 < 0)
        {
            return true; 
        }
        else
        {
            return false;
        }
    }
    
    /**
     * Evaluates if the given value is smaller than zero (0).
     * 
     * @param value1	the value for comparison	
     * @return			indication if the value is smaller zero
     */
    public static boolean evaluate(double value1)
    {
        if(value1 < 0)
        {
            return true; 
        }
        else
        {
            return false;
        }
    }
    
    /**
     * Evaluates if the given value is smaller than zero (0).
     * 
     * @param value1	the value for comparison	
     * @return			indication if the value is smaller zero
     */
    public static boolean evaluate(float value1)
    {
        if(value1 < 0)
        {
            return true; 
        }
        else
        {
            return false;
        }
    }
}
