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
import java.util.ArrayList;

import com.datamelt.rules.core.XmlAction;
/**
 * class collect XmlAction objects
 * 
 * @author uwe geercken
 * 
 */
public class XmlActionCollection implements Serializable
{
	// list of action objects
	private ArrayList<XmlAction> actions = new ArrayList<XmlAction>();
    
	public static final long serialVersionUID = 1964070339;
	
	/**
	 * Add an action to the collection
	 * 
	 * @param action 		the action to add to the collection
     */
    public void add(XmlAction action)
    {
        actions.add(action);
    }
    
    /**
	 * Get an action from the collection
	 * 
	 * @param index 	the index of the action in the collection
	 * @return			an action object
     */
    public XmlAction get(int index)
    {
        return (XmlAction)actions.get(index);
    }
    
    /**
	 * Remove an action from the collection
	 * 
	 * @param index 	the index of the action in the collection
     */
    public void remove(int index)
    {
    	actions.remove(index);
    }
    
    /**
	 * Remove all actions from the collection
	 * 
     */
    public void clear()
    {
    	actions.clear();
    }
    
    /**
	 * Get the number of actions currently in the collection
	 * 
	 * @return			number of actions
     */
    public int size()
    {
        return actions.size();
    }
}
