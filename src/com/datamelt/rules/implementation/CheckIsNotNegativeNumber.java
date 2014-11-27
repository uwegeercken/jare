/*
 * Created on 15.09.2006
 *
 * all code by uwe geercken
 */
package com.datamelt.rules.implementation;

/**
 * checks if a given value is not smaller than zero (0)
 * 
 * <p>
 * The first argument of a method is always the value of the field that one wants to check. The second argument is either another field 
 * or an expected (fixed) value to check against the first value.
 * </p>
 * <p>
 * Some methods may have additional arguments that can be passed to it.
 * </p>
 * 
 * @author uwe geercken
 */
public class CheckIsNotNegativeNumber extends GenericCheck
{
	/**
     * checks, if the integer is a number not smaller than zero
     */
    public static boolean evaluate(int value1)
    {
        if(value1 >= 0)
        {
            return true; 
        }
        else
        {
            return false;
        }
    }
    
	/**
     * checks, if the long is a number not smaller than zero
     */
    public static boolean evaluate(long value1)
    {
        if(value1 >= 0)
        {
            return true; 
        }
        else
        {
            return false;
        }
    }
    
	/**
     * checks, if the double is a number not smaller than zero
     */
    public static boolean evaluate(double value1)
    {
        if(value1 >= 0)
        {
            return true; 
        }
        else
        {
            return false;
        }
    }
    
	/**
     * checks, if the float is a number not smaller than zero
     */
    public static boolean evaluate(float value1)
    {
        if(value1 >= 0)
        {
            return true; 
        }
        else
        {
            return false;
        }
    }
}
