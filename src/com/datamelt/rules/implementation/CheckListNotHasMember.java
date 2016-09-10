/*
 * Created on 13.07.2014
 *
 * all code by uwe geercken
 */
package com.datamelt.rules.implementation;

/**
 * checks if a string containing multiple values separated by commas (,) 
 * not contains a given string. spaces between the individual values are removed (trim)
 * 
 * an example for a list would be:
 * 
 * 		"Rome, Paris, New York, Berlin"
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
public class CheckListNotHasMember extends GenericCheck
{
	/**
     * Evaluates if the list does not contain the given value as a member
     * 
     * @param list		list of string values separated by commas
     * @param value		the value to compare against
     * @return			indication if the given string is not a member of the list
     */
    public static boolean evaluate(String list, String value)
    {
        boolean matches = false;
        if(list!=null)
        {
        	String [] entries = list.split(",");
	        for(int i=0;i< entries.length;i++)
	    	{
	        	if(entries[i].trim().equals(value.trim()))
	    		{
	        		matches = true;
	    			break;
	    		}
	    	}
        }
        return !matches;
    }
    
    /**
     * Evaluates if the list does not contain the given value as a member
     * 
     * @param list			list of string values separated by commas
     * @param value			the value to compare against
     * @param ignoreCase	indication if the case of the values shall be ignored for comparison
     * @return				indication if the given string is not a member of the list
     */
    public static boolean evaluate(String list, String value, boolean ignoreCase)
    {
    	boolean matches = false;
    	if(list!=null)
    	{
	    	String [] entries = list.split(",");
	        for(int i=0;i<entries.length;i++)
	    	{
	        	if(!ignoreCase)
	    		{
		        	if(entries[i].trim().equals(value.trim()))
		    		{
		    			matches = true;
		    			break;
		    		}
	    		}
		        else
		        {
		        	if(entries[i].trim().toLowerCase().equals(value.trim().toLowerCase()))
		    		{
		    			matches = true;
		    			break;
		    		}
		        }
	    	}
    	}
        return !matches;
    }
    
    /**
     * Evaluates if the list does not contain the given value as a member
     * 
     * @param list		list of integer values separated by commas
     * @param value		the value to compare against
     * @return			indication if the given integer is not member of the list
     */
    public static boolean evaluate(String list, int value )
    {
    	boolean matches = false;
    	String [] entries = list.split(",");
        for(int i=0;i<entries.length;i++)
    	{
        	if(Integer.parseInt(entries[i].trim()) == value)
    		{
    			matches = true;
    			break;
    		}
    	}
        return !matches;
    }
    
    /**
     * Evaluates if the list does not contain the given value as a member
     * 
     * @param list		list of long values separated by commas
     * @param value		the value to compare against
     * @return			indication if the given long is not member of the list
     */
    public static boolean evaluate(String list, long value)
    {
    	boolean matches = false;
    	String [] entries = list.split(",");
        for(int i=0;i<entries.length;i++)
    	{
        	if(Long.parseLong(entries[i].trim()) == value)
    		{
    			matches = true;
    			break;
    		}
    	}
        return !matches;
    }
}
