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
* holds the key/value mappings for a given file
* 
* the key/value pairs are read from the file and stored in a properties object.
* 
* @author uwe geercken
*/
public class KeyValueMap
{
	private Properties properties = new Properties();
    private String filename;
	
	public KeyValueMap(String filename) throws Exception
	{
		this.filename = filename;
		properties = new Properties();
        properties.load(new FileInputStream(filename));
	}
	
	/*
	 * gets a value corresponding to the given key
	 * 
	 * @param	key		the key to lookup
	 * @returns			the value corresponding to the key
	 */
	public String getValue(String key)
	{
		return properties.getProperty(key);
	}

	/*
	 * get the filename of the mappings file used
	 * 
	 * @returns			the filename of the mappings file
	 */
	public String getFilename()
	{
		return filename;
	}
}
