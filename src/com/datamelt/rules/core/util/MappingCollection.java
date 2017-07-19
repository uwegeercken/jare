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

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
* collection containing maps of key/value pairs.
* 
* these maps can be used to replace the original value of a row of data by a value defined in a mapping file. The mapping file
* is identified by it's filename.
* 
* @author uwe geercken
*/
public class MappingCollection implements Serializable
{
	public static final long serialVersionUID = 1964070879;
	
	// a hashmap of KeyValueMaps
	private Map <String,KeyValueMap> maps = new HashMap<String,KeyValueMap>();
	
	/*
	 * gets a KeyValueMap from the maps
	 * 
	 * @param filename 	the path and filename of the mapping file
     * @return			KeyValueMap containing all key/value mappings
	 */
	private KeyValueMap getMap(String filename) throws Exception
	{
		KeyValueMap map = maps.get(filename);
		// in case the map is null we load it
		if(map==null)
		{
			map = new KeyValueMap(filename);
			// add it to the maps
			maps.put(filename, map);
		}
		return map;
	}
	
	/*
	 * get a value from a mapping file by providing the filename and the key to lookup
	 * 
	 * @param	filename	the path and filename of the mapping file
	 * @param	key			the key to search for in the mapping file
	 */
	public String getValue(String filename, String key) throws Exception
	{
		KeyValueMap map = getMap(filename);
		return map.getValue(key);
	}
}
