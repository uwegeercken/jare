/*
 * Created on 08.11.2006
 *
 * all code by uwe geercken
 */
package com.datamelt.rules.core;

import java.util.ArrayList;

/**
 * is used to collect the results in form of RuleExecutionResult objects.
 * 
 * @author uwe geercken
 */
public class RuleExecutionCollection
{
    private ArrayList <RuleExecutionResult>results = new ArrayList<RuleExecutionResult>();
    // carries the number of failed rules
    private int failedRulesCount = 0;
    // carries the number of failed groups
    private int failedGroupsCount = 0;
    
    /**
     * add one result of the executing of the business rule engine
     * to the collection of results
     */
    public void add(RuleExecutionResult result)
    {
        results.add(result);
        // count those, that failed
        if (result.getRule().getFailed()==1) 
        {
        	failedRulesCount++;
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
        failedRulesCount = 0;
        failedGroupsCount = 0;
    }
    
    /**
     * returns the number of rules
     * that failed during execution of the business rule engine
     */
    public int getFailedRulesCount()
    {
    	return failedRulesCount;
    }
    
    /**
     * returns the number of rules
     * that failed during execution of the business rule engine
     */
    public int getPassedRulesCount()
    {
    	return results.size() - failedRulesCount;
    }
    
    /**
     * returns the total number of groups that failed 
     * during execution of the business rule engine
     */
    public int getFailedGroupsCount()
    {
    	return failedGroupsCount;
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
}
