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
 * collection of RuleSubGroups which belong to a certain RuleGroup
 * 
 * @author uwe geercken
 */
public class RuleSubGroupCollection implements Serializable
{
	private ArrayList<RuleSubGroup> subGroups = new ArrayList<RuleSubGroup>();
    
	public static final long serialVersionUID = 1964070337;
	
    public void add(RuleSubGroup subGroup)
    {
        subGroups.add(subGroup);
    }
    
    public RuleSubGroup get(int index)
    {
        return (RuleSubGroup)subGroups.get(index);
    }
    
    public int size()
    {
        return subGroups.size();
    }
    
    public void clear()
    {
        subGroups.clear();
    }
    
    public ArrayList<RuleSubGroup> getSubGroups()
    {
        return subGroups;
    }
}
