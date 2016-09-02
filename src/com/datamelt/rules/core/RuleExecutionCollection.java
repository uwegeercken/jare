/*
 * Created on 08.11.2006
 *
 * all code by uwe geercken
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
     */
    
    public ArrayList<RuleExecutionResult> getResults()
    {
        return results;
    }
    
    /**
     * retrieve a specific entry in the list of
     * results
     */
    public RuleExecutionResult get(int index)
    {
        return (RuleExecutionResult)results.get(index);
    }
    
    /**
     * retrieve a specific entry in the list of
     * results for a given subgroup id and rule id
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
     */
    public int size()
    {
        return results.size();
    }
    
    /**
     * method clears the list of results that was built when the
     * business rule engine executed rules
     */
    public void clear()
    {
        results.clear();
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
     * that failed during execution of the business rule engine
     */
    public long getRulesRunCount()
    {
    	return rulesRunCount;
    }
    
    /**
     * returns the number of rules
     * that failed during execution of the business rule engine
     */
    public long getRulesFailedCount()
    {
    	return rulesFailedCount;
    }
    
    /**
     * returns the number of rules
     * that failed during execution of the business rule engine
     */
    public long getRulesPassedCount()
    {
    	return rulesPassedCount;
    }
    
    /**
     * returns the total number of groups that failed 
     * during execution of the business rule engine
     */
    public long getFailedGroupsCount()
    {
    	return failedGroupsCount;
    }
    
    /**
     * returns the total number of groups that passed 
     * during execution of the business rule engine
     */
    public long getPassedGroupsCount()
    {
    	return passedGroupsCount;
    }
    
    /**
     * returns the total number of groups that failed 
     * during execution of the business rule engine
     */
    public long getActionsExecutedCount()
    {
    	return actionsExecutedCount;
    }
    
    /**
     * returns the total number of groups that where skipped 
     * during execution of the business rule engine
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
     * increases the count of rules
     * 
     */
    public void increaseRulesRunCount()
    {
    	rulesRunCount ++;
    }
    
    /**
     * increases the count of rules that failed
     * 
     */
    public void increaseRulesFailedCount()
    {
    	rulesFailedCount ++;
    }
    
    /**
     * increases the count of rules that passed
     * 
     */
    public void increaseRulesPassedCount()
    {
    	rulesPassedCount ++;
    }

    /**
     * add the number of actions executed by a rulegroup
     * 
     */
    public void addNumberOfActionsExecuted(int number)
    {
    	actionsExecutedCount =  actionsExecutedCount + number;
    }
    
    /**
     * add the number of rules run
     * 
     */
    public void addNumberOfRulesRun(long number)
    {
    	rulesRunCount =  rulesRunCount + number;
    }
    
    /**
     * add the number of rules that failed
     * 
     */
    public void addNumberOfRulesFailed(long number)
    {
    	rulesFailedCount =  rulesFailedCount + number;
    }
    
    /**
     * add the number of rules that passed
     * 
     */
    public void addNumberOfRulesPassed(long number)
    {
    	rulesPassedCount =  rulesPassedCount + number;
    }

	public boolean getPreserveRuleExcecutionResults()
	{
		return preserveRuleExcecutionResults;
	}

	public void setPreserveRuleExcecutionResults(boolean preserveRuleExcecutionResults)
	{
		this.preserveRuleExcecutionResults = preserveRuleExcecutionResults;
	}
    
    
}
