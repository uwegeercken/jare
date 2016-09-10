package com.datamelt.rules.implementation;

/**
 * Checks if one number is between two given other numbers - including the upper and lower limit.
 * <p>
 * 
 * The first parameter of a given method is always the value of the field that shall be checked. The second parameter is either another field to check against 
 * or an expected value (fixed value) to check against the first value.
 * 
 * @author uwe geercken
 */
public class CheckIsBetween extends GenericCheck
{
	/**
	 * Evaluate if an integer value is between an upper and the lower limit provided as string values and separated by a comma.
	 *  
     * @param value					the first value for comparison
     * @param valueLowerUpperLimit	comma separated list of values for upper and lower limit
     * @return						indication if the first value is between the upper and lower limit
     */
    public static boolean evaluate(int value,String valueLowerUpperLimit)
    {
    	String[] stringValues= valueLowerUpperLimit.split(",");
    	int lowerLimit = Integer.parseInt(stringValues[0].trim());
    	int upperLimit = Integer.parseInt(stringValues[1].trim());
    	
        return value>=lowerLimit && value<=upperLimit;
    }
    
    /**
	 * Evaluate if a long value is between an upper and the lower limit provided as string values and separated by a comma.
	 *  
     * @param value					the first value for comparison
     * @param valueLowerUpperLimit	comma separated list of values for upper and lower limit
     * @return						indication if the first value is between the upper and lower limit
     */
    public static boolean evaluate(long value,String valueLowerUpperLimit)
    {
    	String[] stringValues= valueLowerUpperLimit.split(",");
    	long lowerLimit = Long.parseLong(stringValues[0].trim());
    	long upperLimit = Long.parseLong(stringValues[1].trim());
    	
        return value>=lowerLimit && value<=upperLimit;
    }
    
    /**
	 * Evaluate if a double value is between an upper and the lower limit provided as string values and separated by a comma.
	 *  
     * @param value					the first value for comparison
     * @param valueLowerUpperLimit	comma separated list of values for upper and lower limit
     * @return						indication if the first value is between the upper and lower limit
     */
    public static boolean evaluate(double value,String valueLowerUpperLimit)
    {
    	String[] stringValues= valueLowerUpperLimit.split(",");
    	double lowerLimit = Double.parseDouble(stringValues[0].trim());
    	double upperLimit = Double.parseDouble(stringValues[1].trim());
    	
        return value>=lowerLimit && value<=upperLimit;
    }
    
    /**
	 * Evaluate if a float value is between an upper and the lower limit provided as string values and separated by a comma.
	 *  
     * @param value					the first value for comparison
     * @param valueLowerUpperLimit	comma separated list of values for upper and lower limit
     * @return						indication if the first value is between the upper and lower limit
     */
    public static boolean evaluate(float value,String valueLowerUpperLimit)
    {
    	String[] stringValues= valueLowerUpperLimit.split(",");
    	float lowerLimit = Float.parseFloat(stringValues[0].trim());
    	float upperLimit = Float.parseFloat(stringValues[1].trim());
    	
        return value>=lowerLimit && value<=upperLimit;
    }
    
    /**
	 * Evaluate if an integer value is between an upper and the lower limit provided as integer values.
	 *  
     * @param value				the first value for comparison
     * @param valueLowerLimit	the lower limit for the comparison
     * @param valueUpperLimit	the upper limit for the comparison
     * @return					indication if the first value is between the upper and lower limit
     */
    public static boolean evaluate(int value,int valueLowerLimit, int valueUpperLimit)
    {
        return value>=valueLowerLimit && value<=valueUpperLimit;
    }
    
    /**
   	* Evaluate if a long value is between an upper and the lower limit provided as long values.
   	*  
    * @param value				the first value for comparison
    * @param valueLowerLimit	the lower limit for the comparison
    * @param valueUpperLimit	the upper limit for the comparison
    * @return					indication if the first value is between the upper and lower limit
    */
    public static boolean evaluate(long value,long valueLowerLimit, long valueUpperLimit)
    {
        return value>=valueLowerLimit && value<=valueUpperLimit;
    }
    
    /**
   	* Evaluate if a double value is between an upper and the lower limit provided as double values.
   	*  
    * @param value				the first value for comparison
    * @param valueLowerLimit	the lower limit for the comparison
    * @param valueUpperLimit	the upper limit for the comparison
    * @return					indication if the first value is between the upper and lower limit
    */
    public static boolean evaluate(double value,double valueLowerLimit, double valueUpperLimit)
    {
        return value>=valueLowerLimit && value<=valueUpperLimit;
    }
    
    /**
   	* Evaluate if a float value is between an upper and the lower limit provided as float values.
   	*  
    * @param value				the first value for comparison
    * @param valueLowerLimit	the lower limit for the comparison
    * @param valueUpperLimit	the upper limit for the comparison
    * @return					indication if the first value is between the upper and lower limit
    */
    public static boolean evaluate(float value,float valueLowerLimit, float valueUpperLimit)
    {
        return value>=valueLowerLimit && value<=valueUpperLimit;
    }

}
