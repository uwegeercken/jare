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
     private long groupsPassed;
     private long totalGroups;
     private long rulesPassed;
     private long totalRules;
     private int outputType;
     
     private static final long serialVersionUID=2000000;

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
    	 this.fields = new RowFieldCollection(header, fields);
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

	public long getGroupsPassed() {
		return groupsPassed;
	}

	public void setGroupsPassed(long groupsPassed) {
		this.groupsPassed = groupsPassed;
	}

	public long getTotalGroups() {
		return totalGroups;
	}

	public void setTotalGroups(long totalGroups) {
		this.totalGroups = totalGroups;
	}

	public long getRulesPassed() {
		return rulesPassed;
	}

	public void setRulesPassed(long rulesPassed) {
		this.rulesPassed = rulesPassed;
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
	
	
}