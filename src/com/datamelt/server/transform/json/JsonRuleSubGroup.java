package com.datamelt.server.transform.json;

import java.util.ArrayList;

public class JsonRuleSubGroup 
{
	private String id;
	private int failed;
	private String intergroupLogic;
	private String rulesLogic;
	private ArrayList<JsonRuleResult>ruleResults = new ArrayList<JsonRuleResult>();
	
	public String getId() 
	{
		return id;
	}
	
	public void setId(String id) 
	{
		this.id = id;
	}
	
	public int isFailed() 
	{
		return failed;
	}
	
	public void setFailed(int failed) 
	{
		this.failed = failed;
	}
	
	public String getIntergroupLogic() 
	{
		return intergroupLogic;
	}
	
	public void setIntergroupLogic(String intergroupLogic) 
	{
		this.intergroupLogic = intergroupLogic;
	}
	
	public String getRulesLogic() 
	{
		return rulesLogic;
	}
	
	public void setRulesLogic(String rulesLogic) 
	{
		this.rulesLogic = rulesLogic;
	}
	
	public ArrayList<JsonRuleResult> getRuleResults() 
	{
		return ruleResults;
	}
	
	public void setRuleResults(ArrayList<JsonRuleResult> ruleResults) 
	{
		this.ruleResults = ruleResults;
	}
}
