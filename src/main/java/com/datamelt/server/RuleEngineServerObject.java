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
package com.datamelt.server;

import java.io.Serializable;
import java.util.ArrayList;

import com.datamelt.rules.core.RuleExecutionCollection;
import com.datamelt.rules.core.RuleGroup;
import com.datamelt.util.RowField;
import com.datamelt.util.RowFieldCollection;

public class RuleEngineServerObject implements Serializable
{
     private RowFieldCollection fields;
     private ArrayList<RuleGroup> groups = new ArrayList<RuleGroup>();
     private RuleExecutionCollection ruleExecutionCollection;
     private boolean passed=false;
     private long groupsFailed;
     private long totalGroups;
     private long groupsSkipped;
     private long rulesFailed;
     private long totalRules;
     private long totalActions;
     private int outputType;
     private String objectLabel;
     private String processId;
     private boolean ruleEngineException = false;
     private String ruleEngineExceptionMessage;
     
     public static final long serialVersionUID = 200003;
     
     public RuleEngineServerObject() 
     {
        
     }
     
     public RuleEngineServerObject(RowFieldCollection fields, int outputType) 
     {
        this.fields = fields;
        this.outputType = outputType;
     }
     
     public RuleEngineServerObject(String[] header, String[] fields, int outputType)
     {
    	 this.fields = new RowFieldCollection(header,fields);
    	 this.outputType = outputType;
     }
     
     public RuleEngineServerObject(ArrayList <RowField>rowFields, int outputType)
     {
    	 this.fields = new RowFieldCollection(rowFields);
    	 this.outputType = outputType;
     }

	public boolean isPassed()
	{
		return passed;
	}

	public void setPassed(boolean passed) 
	{
		this.passed = passed;
	}
	
	public RowFieldCollection getFields()
	{
		return fields;
	}

	public long getGroupsFailed() {
		return groupsFailed;
	}
	
	public void setGroupsFailed(long groupsFailed) {
		this.groupsFailed = groupsFailed;
	}

	public long getTotalGroups() {
		return totalGroups;
	}
	
	public void setTotalGroups(long totalGroups) {
		this.totalGroups = totalGroups;
	}
	
	public long getGroupsSkipped() {
		return groupsSkipped;
	}

	public void setGroupsSkipped(long groupsSkipped) {
		this.groupsSkipped = groupsSkipped;
	}

	public long getRulesFailed() {
		return rulesFailed;
	}

	public void setRulesFailed(long rulesFailed) {
		this.rulesFailed = rulesFailed;
	}

	public long getTotalRules() {
		return totalRules;
	}

	public void setTotalRules(long totalRules) {
		this.totalRules = totalRules;
	}

	public ArrayList<RuleGroup> getRuleGroups() {
		return groups;
	}

	public void setRuleGroups(ArrayList<RuleGroup> groups)
	{
		this.groups = groups;
	}
	
	public void addRuleGroup(RuleGroup group)
	{
		groups.add(group);
	}

	public RuleExecutionCollection getRuleExecutionCollection() 
	{
		return ruleExecutionCollection;
	}

	public void setRuleExecutionCollection(RuleExecutionCollection ruleExecutionCollection) 
	{
		this.ruleExecutionCollection = ruleExecutionCollection;
	}

	public int getOutputType()
	{
		return outputType;
	}

	public void setOutputType(int outputType) 
	{
		this.outputType = outputType;
	}

	public ArrayList<RuleGroup> getGroups() 
	{
		return groups;
	}

	public String getObjectLabel()
	{
		return objectLabel;
	}

	public void setObjectLabel(String objectLabel) 
	{
		this.objectLabel = objectLabel;
	}

	public String getProcessId() 
	{
		return processId;
	}

	public void setProcessId(String processId) 
	{
		this.processId = processId;
	}

	public long getTotalActions() 
	{
		return totalActions;
	}

	public void setTotalActions(long totalActions) 
	{
		this.totalActions = totalActions;
	}

	public boolean getRuleEngineException() 
	{
		return ruleEngineException;
	}

	public void setRuleEngineException(boolean ruleEngineException) 
	{
		this.ruleEngineException = ruleEngineException;
	}

	public String getRuleEngineExceptionMessage() 
	{
		return ruleEngineExceptionMessage;
	}

	public void setRuleEngineExceptionMessage(String ruleEngineExceptionMessage) 
	{
		this.ruleEngineExceptionMessage = ruleEngineExceptionMessage;
	}
}
