/*
 * Created on 28.06.2008
 *
 * all code by uwe geercken
 */
package com.datamelt.rules.implementation;

/**
 * <p>Checks if one number is between two given numbers.</p>
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
public class CheckIsBetween extends GenericCheck
{
	/**
	 * Checks if one number is between two given numbers. Specify an integer evaluate and a string which contains the upper and the lower limit
	 * to check seperated by a comma. 
	 */
    public static boolean evaluate(int value,String valueLowerUpperLimit)
    {
    	String[] stringValues= valueLowerUpperLimit.split(",");
    	int lowerLimit = Integer.parseInt(stringValues[0].trim());
    	int upperLimit = Integer.parseInt(stringValues[1].trim());
    	
        return value>=lowerLimit && value<=upperLimit;
    }
    
    /**
	 * Checks if one number is between two given numbers. Specify a long to evaluate and a string which contains the upper and the lower limit
	 * to check seperated by a comma 
	 */
    public static boolean evaluate(long value,String valueLowerUpperLimit)
    {
    	String[] stringValues= valueLowerUpperLimit.split(",");
    	long lowerLimit = Long.parseLong(stringValues[0].trim());
    	long upperLimit = Long.parseLong(stringValues[1].trim());
    	
        return value>=lowerLimit && value<=upperLimit;
    }
    
    /**
	 * Checks if one number is between two given numbers. Specify a double to evaluate and a string which contains the upper and the lower limit
	 * to check seperated by a comma 
	 */
    public static boolean evaluate(double value,String valueLowerUpperLimit)
    {
    	String[] stringValues= valueLowerUpperLimit.split(",");
    	double lowerLimit = Double.parseDouble(stringValues[0].trim());
    	double upperLimit = Double.parseDouble(stringValues[1].trim());
    	
        return value>=lowerLimit && value<=upperLimit;
    }
    
    /**
	 * Checks if one number is between two given numbers. Specify a float to evaluate and a string which contains the upper and the lower limit
	 * to check seperated by a comma 
	 */
    public static boolean evaluate(float value,String valueLowerUpperLimit)
    {
    	String[] stringValues= valueLowerUpperLimit.split(",");
    	float lowerLimit = Float.parseFloat(stringValues[0].trim());
    	float upperLimit = Float.parseFloat(stringValues[1].trim());
    	
        return value>=lowerLimit && value<=upperLimit;
    }
    
    /**
	 * Checks if one number is between two given numbers. Specify an integer to evaluate, an integer for the lower limit and an integer for the upper limit
	 */
    public static boolean evaluate(int value,int valueLowerLimit, int valueUpperLimit)
    {
        return value>=valueLowerLimit && value<=valueUpperLimit;
    }
    
    /**
	 * Checks if one number is between two given numbers. Specify a long to evaluate, a long for the lower limit and a long for the upper limit
	 */
    public static boolean evaluate(long value,long valueLowerLimit, long valueUpperLimit)
    {
        return value>=valueLowerLimit && value<=valueUpperLimit;
    }
    
    /**
	 * Checks if one number is between two given numbers. Specify a double to evaluate, a double for the lower limit and a double for the upper limit
	 */
    public static boolean evaluate(double value,double valueLowerLimit, double valueUpperLimit)
    {
        return value>=valueLowerLimit && value<=valueUpperLimit;
    }
    
    /**
	 * Checks if one number is between two given numbers. Specify a float to evaluate, a float for the lower limit and a float for the upper limit
	 */
    public static boolean evaluate(float value,float valueLowerLimit, float valueUpperLimit)
    {
        return value>=valueLowerLimit && value<=valueUpperLimit;
    }
}
