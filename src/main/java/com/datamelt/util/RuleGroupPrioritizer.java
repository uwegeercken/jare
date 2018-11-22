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
package com.datamelt.util;

import java.util.ArrayList;
import com.datamelt.rules.core.RuleGroup;
/**
 * a rulegroup may depend on another rulegroup. this also means, that the dependent rulegroup
 * needs to be executed first, before the rulegroup depending on the group.
 * 
 * the prioritizer will make sure that all dependent rulegroups are first in the list and that
 * all other groups follow.
 * 
 * @author uwe
 *
 */
public class RuleGroupPrioritizer
{
	private ArrayList<RuleGroup> groups = new ArrayList<RuleGroup>();
	
	public RuleGroupPrioritizer(ArrayList<RuleGroup> groups)
	{
		this.groups = groups;
	}
	
	public ArrayList<RuleGroup> getPrioritizedList()
	{
		// list of rulegroups that other rulegroups depend on
		ArrayList<RuleGroup> dependentGroups = new ArrayList<RuleGroup>();
		// list of all other groups
        ArrayList<RuleGroup> otherGroups = new ArrayList<RuleGroup>();
        // list will contain the final list of all rulegroups after prioritization
        ArrayList<RuleGroup> prioritizedGroups = new ArrayList<RuleGroup>();
       
        // a list of all group ids where other groups depend on
        ArrayList<String>dependentGroupIds = new ArrayList<String>();
		
        // get all ids of groups that other groups depend on
        for(int i=0;i<groups.size();i++)
        {
        	RuleGroup group = groups.get(i);
        	// if a dependency is found capture the group id
        	if(group.getDependentRuleGroupId()!=null && !group.getDependentRuleGroupId().equals(""))
        	{
        		dependentGroupIds.add(group.getDependentRuleGroupId());
        	}
        }
		
        // find the rulegroups corresponding to the dependent group ids
		for(int f=0;f<groups.size();f++)
		{
			RuleGroup group = groups.get(f);
			int found=-1;
			for(int g=0;g<dependentGroupIds.size();g++)
			{
				String groupId = dependentGroupIds.get(g);
				if(group.getId().equals(groupId))
				{
					found=g;
					dependentGroups.add(group);
					break;
				}
			}
			// if the rulegroup is none of those that other rulegroups have depenencies to
			// add it to the list of other groups
			if(found==-1)
			{
				otherGroups.add(group);
			}
		}
		
		// first we have a list of rulegroups that other groups depend on
		// these will need to be executed first
		prioritizedGroups.addAll(dependentGroups);
		// second all other groups follow
		prioritizedGroups.addAll(otherGroups);
		return prioritizedGroups;
	}
}
