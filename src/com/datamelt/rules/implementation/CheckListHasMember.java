package com.datamelt.rules.implementation;

/**
 * Checks if a list of string values separated by commas contains a given string.
 * <p>
 * spaces between the individual values are removed (trim)
 * <p>
 * an example for a list would be:
 * <p>
 * 		"Rome, Paris, New York, Berlin"
 * <p>
 * The first parameter of a given method is always the value of the field that shall be checked. The second parameter is either another field to check against 
 * or an expected value (fixed value) to check against the first value.
 * 
 * @author uwe geercken
 */
public class CheckListHasMember extends GenericCheck
{
	/**
     * Evaluates if the list contains the given value as a member
     * 
     * @param list		list of string values separated by commas
     * @param value		the value to compare against
     * @return			indication if the given string is a member of the list
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
        return matches;
    }
    
    /**
     * Evaluates if the list contains the given value as a member
     * 
     * @param list			list of string values separated by commas
     * @param value			the value to compare against
     * @param ignoreCase	indication if the case of the values shall be ignored for comparison
     * @return				indication if the given string is a member of the list
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
        return matches;
    }
    
    /**
     * Evaluates if the list contains the given value as a member
     * 
     * @param list		list of integer values separated by commas
     * @param value		the value to compare against
     * @return			indication if the given integer is a member of the list
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
        return matches;
    }
    
    /**
     * Evaluates if the list contains the given value as a member
     * 
     * @param list		list of long values separated by commas
     * @param value		the value to compare against
     * @return			indication if the given long is a member of the list
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
        return matches;
    }
}
