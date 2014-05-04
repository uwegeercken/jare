/*
 * Created on 10.11.2006
 *
 * all code by uwe geercken
 */
package com.datamelt.rules.core.util;

import java.io.FileInputStream;
import java.util.Properties;

/**
 * class does simple replacement of one value with the other.
 * purpose is to avoid duplication of data that is frequently
 * used in rules.
 * 
 * the user specifies a variable in the xml rule file instead
 * of a value. e.g. $VARIABLE1. this variable will be replaced
 * with the value as specified in the properties file.
 * 
 * the parameter value and the expected value can be replaced
 * using this class.
 * 
 * 
 * @author uwe geercken
 */
public class VariableReplacer
{
    private Properties properties = new Properties();
    private String filename;
    
    /*
     * constructor requires the full path and name of the file
     * to load
     */
    public VariableReplacer(String filename) throws Exception
    {
        this.filename = filename;
        properties = new Properties();
        properties.load(new FileInputStream(filename));
    }
    
    /*
     * returns the value from the properties file
     * corresponding to the key parameter.
     * if non is found
     */
    public String getValue(String key)throws Exception
    {
        // only process keys starting with a dollar sign
        if(key.startsWith("$"))
        {
	        String value = properties.getProperty(key); 
	        if(value!=null)
	        {
	            return value;
	        }
	        else
	        {
	            throw new Exception("key: [" + key + "] not found in: " + filename );
	        }
        }
        else
        {
            return key;
        }
    }
    
    /**
     * 
     * returns the name of the replacements file
     */
    public String getFilename()
    {
        return filename;
    }
    
    public int getNumberOfVariables()
    {
    	if(properties!=null)
    	{
    		return properties.size();
    	}
    	else
    	{
    		return 0;
    	}
    }
}
