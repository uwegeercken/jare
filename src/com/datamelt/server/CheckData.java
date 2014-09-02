package com.datamelt.server;

import java.io.Serializable;
import java.util.ArrayList;

import com.datamelt.util.Row;

public class CheckData implements Serializable
{
     /**
	 * 
	 */
	 private String rulefile;
     private Row row;
     private ArrayList<RuleGroupSimple> groups = new ArrayList<RuleGroupSimple>();
     private boolean passed=false;
     private long groupsPassed;
     private long totalGroups;
     private long rulesPassed;
     private long totalRules;
     
     private static final long serialVersionUID=200000;

     public CheckData(String rulefile, Row row) 
     {
        this.rulefile = rulefile;
        this.row=row;
     }
     
     public CheckData(String rulefile, String[] fields)
     {
        this.rulefile = rulefile;
        this.row=new Row(fields);
     }

	public String getRulefile()
	{
		return rulefile;
	}

	public boolean isPassed()
	{
		return passed;
	}

	public void setPassed(boolean passed) 
	{
		this.passed = passed;
	}
	
	public String toString()
	{
		return "rulefile: " + getRulefile() + ", row: " + getRow();
	}

	public Row getRow()
	{
		return row;
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

	public ArrayList<RuleGroupSimple> getRuleGroups() {
		return groups;
	}

	public void setRuleGroups(ArrayList<RuleGroupSimple> groups) {
		this.groups = groups;
	}
	
	public void addRuleGroup(RuleGroupSimple groupSimple)
	{
		groups.add(groupSimple);
	}
	
	
}
