/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.datamelt.rules.core.util;

import java.io.FileInputStream;
import java.util.Properties;

/**
 * class does simple replacement of one value with the other.
 * purpose is to avoid duplication of values that are frequently
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
     * to load/use
     * 
     * @param	filename	the name of the file containing the mappings
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
     * 
     * @param key		the key to lookup from the file
     * @return			the related value for the given key
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
     * gets the name of the replacements file is use
     * 
     * @return		the name of the replacement file
     */
    public String getFilename()
    {
        return filename;
    }
    
    /**
     * gets the number of variables/keys used in the replacement file
     * 
     * @return		number of variables/keys
     */
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
