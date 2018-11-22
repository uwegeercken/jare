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
package com.datamelt.rules.core;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * collection of RuleObject objects that are created from parsing the
 * rule definition xml file(s). These objects are later in the process
 * used to compare an expected result against the actual result.
 * 
 * @author uwe geercken
 */
public class RuleObjectCollection implements Serializable
{
	private ArrayList<RuleObject> ruleObjects = new ArrayList<RuleObject>();
    
	public static final long serialVersionUID = 1964070335;
	/**
	 * adds a rule object to the collection of rule objects
	 * 
	 * @param ruleObject	the rule object
	 */
    public void add(RuleObject ruleObject)
    {
        ruleObjects.add(ruleObject);
    }
    
    /**
     * gets a rule object from the collection
     * 
     * @param index		the index of the rule object in the collection
     * @return			the rule object
     */
    public RuleObject get(int index)
    {
        return (RuleObject)ruleObjects.get(index);
    }
    
    /**
     * remove the rule object from the collection
     * 
     * @param index		the index of the rule object in the collection
     */
    public void remove(int index)
    {
        ruleObjects.remove(index);
    }
    
    /**
     * clears the collection
     */
    public void clear()
    {
        ruleObjects.clear();
    }
    
    /**
     * gets the number of elements in the collection of rule objects
     * 
     * @return	the number of rule objects
     */
    public int size()
    {
        return ruleObjects.size();
    }
}
