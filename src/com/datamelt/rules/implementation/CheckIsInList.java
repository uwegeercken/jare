package com.datamelt.rules.implementation;

/**
 * Checks if the given string value is contained in a list of values separated by comma.
 * <p>
 * spaces in the individual values are removed
 * <p>
 * an example for a list would be:
 * <p>
 * 		Rome, Paris, New York, Berlin
 * <p>
 * The first parameter of a given method is always the value of the field that shall be checked. The second parameter is either another field to check against 
 * or an expected value (fixed value) to check against the first value.
 * 
 * @author uwe geercken
 */
public class CheckIsInList extends GenericCheck
{
	
    /**
     * Checks if the given string value is contained in a list of values separated by comma.
     * 
     * @param value		the first value for the comparison
     * @param list		list of string values separated by commas
     * @return			indication if the value is contained in the list of values
     */
    public static boolean evaluate(String value,String list)
    {
        boolean matches = false;
        if(value!=null)
        {
        	String [] values = list.split(",");
	        for(int i=0;i< values.length;i++)
	    	{
	        	if(values[i].trim().equals(value.trim()))
	    		{
	        		matches = true;
	    			break;
	    		}
	    	}
        }
        return matches;
    }
    
    /**
     * Checks if the given string value is contained in a list of values separated by comma.
     * 
     * @param value			the first value for the comparison
     * @param list			list of string values separated by commas
     * @param ignoreCase	indication if the case of the values shall be ignored for comparison
     * @return				indication if the value is contained in the list of values
     */
    public static boolean evaluate(String value, String list, boolean ignoreCase)
    {
    	boolean matches = false;
    	if(value!=null)
    	{
	    	String [] values = list.split(",");
	        for(int i=0;i<values.length;i++)
	    	{
	        	if(!ignoreCase)
	    		{
		        	if(values[i].trim().equals(value.trim()))
		    		{
		    			matches = true;
		    			break;
		    		}
	    		}
		        else
		        {
		        	if(values[i].trim().toLowerCase().equals(value.trim().toLowerCase()))
		    		{
		    			matches = true;
		    			break;
		    		}
		        }
	    	}
    	}
        return matches;
    }
    
    /**
     * Checks if the given integer value is contained in a list of values separated by comma.
     * <p>
     * Each value in the list is converted to an integer value.
     * 
     * @param value		the first value for the comparison
     * @param list		list of integer values separated by commas
     * @return			indication if the value is contained in the list of values
     */
    public static boolean evaluate(int value, String list)
    {
    	boolean matches = false;
    	String [] values = list.split(",");
        for(int i=0;i<values.length;i++)
    	{
        	if(Integer.parseInt(values[i].trim()) == value)
    		{
    			matches = true;
    			break;
    		}
    	}
        return matches;
    }
    
    /**
     * Checks if the given long value is contained in a list of values separated by comma.
     * <p>
     * Each value in the list is converted to a long value.
     * 
     * @param value		the first value for the comparison
     * @param list		list of long values separated by commas
     * @return			indication if the value is contained in the list of values
     */
    public static boolean evaluate(long value, String list)
    {
    	boolean matches = false;
    	String [] values = list.split(",");
        for(int i=0;i<values.length;i++)
    	{
        	if(Long.parseLong(values[i].trim()) == value)
    		{
    			matches = true;
    			break;
    		}
    	}
        return matches;
    }
}
