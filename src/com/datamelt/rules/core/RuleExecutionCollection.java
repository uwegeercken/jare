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
 * is used to collect the results in form of RuleExecutionResult objects.
 * 
 * @author uwe geercken
 */
public class RuleExecutionCollection implements Serializable
{
	private ArrayList <RuleExecutionResult>results = new ArrayList<RuleExecutionResult>();
	// carries the number of rules
    private long rulesRunCount = 0;
    // carries the number of rules that failed
    private long rulesFailedCount = 0;
    // carries the number of rules that passed
    private long rulesPassedCount = 0;
    // carries the number of failed groups
    private long failedGroupsCount = 0;
    // carries the number of passed groups
    private long passedGroupsCount = 0;
    // carries the number of skipped groups because the dependent rulegroup
    // did not have to expected result (passed/failed)
    private long skippedGroupsCount = 0;
    // number of actions executed
    private long actionsExecutedCount = 0;
    // flag if rule execution results should be preserved
    private boolean preserveRuleExcecutionResults=true;
    
    public static final long serialVersionUID = 1964070329;
    
    /**
     * add one result of the executing of the business rule engine
     * to the collection of results
     * 
     * @param result	the result of the execution of a rule
     */
    public void add(RuleExecutionResult result)
    {
        // only add the result if preserveRuleExcecutionResults is set to true
    	if(preserveRuleExcecutionResults)
        {
        	results.add(result);
        }
    }
    
    /**
     * add results to the collection in form of an arraylist.
     * the results will be merged with existing results if there are
     * already any
     * 
     * @param ruleResults	list of rule execution results to be added
     */
    public void addAll(ArrayList <RuleExecutionResult>ruleResults)
    {
        for(int i=0;i<ruleResults.size();i++)
        {
            RuleExecutionResult result = (RuleExecutionResult)ruleResults.get(i);
            // call the method in this class to add a single result object
            this.add(result);
        }
    }
    
    /**
     * returns the (array)list of results that were collected when the
     * business rule engine executed rules
     * 
     * @return 	a list of rule execution results
     */
    public ArrayList<RuleExecutionResult> getResults()
    {
        return results;
    }
    
    /**
     * retrieve a specific entry in the list of
     * results
     * 
     * @param index		the index of the rule execution result to retrieve
     * @return			a rule execution result
     */
    public RuleExecutionResult get(int index)
    {
        return (RuleExecutionResult)results.get(index);
    }
    
    /**
     * retrieve a specific entry in the list of
     * results for a given subgroup id and rule id
     * 
     * @param subgroupId	the id of the subgroup
     * @param ruleId		the id of the rule
     * @return				the rule execution result for the given subgroup and rule
     */
    public RuleExecutionResult getSubgroupRuleResult(String subgroupId, String ruleId)
    {
        int found = -1;
    	for(int i=0;i<results.size();i++)
        {
        	RuleExecutionResult result = results.get(i);
        	if(result.getSubgroupId().equals(subgroupId) && result.getRule().getId().equals(ruleId))
        	{
        		found = i;
        		break;
        	}
        }
    	if (found>-1)
    	{
    		return (RuleExecutionResult)results.get(found);
    	}
    	else
    	{
    		return null;
    	}
    }
    
    
    /**
     * returns the number of results collected when the
     * business rule engine executed rules
     * 
     * @return	the number of the rule execution results
     */
    public int size()
    {
        return results.size();
    }
    
    /**
     * method clears the list of results that was built when the
     * business rule engine executed rules. also clears the counters
     */
    public void clear()
    {
        results.clear();
        clearCounters();
    }

    /**
     * method clears the various counters
     */
    public void clearCounters() 
    {
        rulesRunCount = 0;
        rulesFailedCount = 0;
        rulesPassedCount = 0;
        passedGroupsCount=0;
        failedGroupsCount = 0;
        skippedGroupsCount=0;
        actionsExecutedCount=0;
    }
    
    /**
     * returns the number of rules
     * that ran during execution of the business rule engine
     * 
     * @return the number of rules executed
     */
    public long getRulesRunCount()
    {
    	return rulesRunCount;
    }
    
    /**
     * returns the number of rules
     * that failed during execution of the business rule engine
     * 
     * @return the number of rules that failed during execution
     */
    public long getRulesFailedCount()
    {
    	return rulesFailedCount;
    }
    
    /**
     * returns the number of rules
     * that failed during execution of the business rule engine
     * 
     * @return the number of rules that passed during execution
     */
    public long getRulesPassedCount()
    {
    	return rulesPassedCount;
    }
    
    /**
     * returns the total number of groups that failed 
     * during execution of the business rule engine
     * 
     * @return the number of rulegroups that failed during execution
     */
    public long getFailedGroupsCount()
    {
    	return failedGroupsCount;
    }
    
    /**
     * returns the total number of groups that passed 
     * during execution of the business rule engine
     * 
     * @return the number of rulegroups that passed during execution
     */
    public long getPassedGroupsCount()
    {
    	return passedGroupsCount;
    }
    
    /**
     * returns the total number of actions that were 
     * executed by the business rule engine
     * 
     * @return the number of actions executed
     */
    public long getActionsExecutedCount()
    {
    	return actionsExecutedCount;
    }
    
    /**
     * returns the total number of groups that where skipped 
     * during execution of the business rule engine.
     * 
     * groups that are dependent on other groups are skipped according
     * to the status (passed/failed) of the execution of the group they
     * depend on. 
     * 
     * @return the number of rulegroups that were skipped during execution
     */
    public long getSkippedGroupsCount()
    {
    	return skippedGroupsCount;
    }
    
    /**
     * increases the count of failed groups by one (1)
     * 
     * used to track how many groups in total failed during execution
     */
    public void increaseFailedGroupCount()
    {
    	failedGroupsCount ++;
    }
    
    /**
     * increases the count of failed groups by one (1)
     * 
     * used to track how many groups in total failed during execution
     */
    public void increasePassedGroupCount()
    {
    	passedGroupsCount ++;
    }
    
    /**
     * increases the count of skipped groups by one (1)
     * 
     * used to track how many groups in total were skipped during execution
     * because the dependent rulegroup did not have the expected result (passed/failed)
     */
    public void increaseSkippedGroupCount()
    {
    	skippedGroupsCount ++;
    }
    
    /**
     * increases the count of rules by one (1)
     * 
     */
    public void increaseRulesRunCount()
    {
    	rulesRunCount ++;
    }
    
    /**
     * increases the count of rules that failed by one (1)
     * 
     */
    public void increaseRulesFailedCount()
    {
    	rulesFailedCount ++;
    }
    
    /**
     * increases the count of rules that passed by one (1)
     * 
     */
    public void increaseRulesPassedCount()
    {
    	rulesPassedCount ++;
    }

    /**
     * add the number of actions executed by a rulegroup to the counter
     * 
     * @param number	the number to add to the count of executed actions
     */
    public void addNumberOfActionsExecuted(int number)
    {
    	actionsExecutedCount =  actionsExecutedCount + number;
    }
    
    /**
     * add the number of rules run to the counter
     * 
     * @param number	the number to add to the count of rules run
     */
    public void addNumberOfRulesRun(long number)
    {
    	rulesRunCount =  rulesRunCount + number;
    }
    
    /**
     * add the number of rules that failed to the counter
     * 
     * @param number	the number to add to the count of failed rules
     */
    public void addNumberOfRulesFailed(long number)
    {
    	rulesFailedCount =  rulesFailedCount + number;
    }
    
    /**
     * add the number of rules that passed to the counter
     * 
     * @param number	the number to add to the count of passed rules
     * 
     */
    public void addNumberOfRulesPassed(long number)
    {
    	rulesPassedCount =  rulesPassedCount + number;
    }

    /**
     * Gets the indicator if the results of the rule execution should be preserved
     * 
     * @return	the indicator if results should be preserved
     */
	public boolean getPreserveRuleExcecutionResults()
	{
		return preserveRuleExcecutionResults;
	}

	/**
     * Sets the indicator if the results of the rule execution should be preserved
     * 
     * @param preserveRuleExcecutionResults	the indicator if results should be preserved
     */
	public void setPreserveRuleExcecutionResults(boolean preserveRuleExcecutionResults)
	{
		this.preserveRuleExcecutionResults = preserveRuleExcecutionResults;
	}
    
    
}
