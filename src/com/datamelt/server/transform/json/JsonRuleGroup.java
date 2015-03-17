package com.datamelt.server.transform.json;

import java.util.ArrayList;

public class JsonRuleGroup 
{
	private String id;
	private int failed;
	private ArrayList<JsonRuleSubGroup> subgroups = new ArrayList<JsonRuleSubGroup>();
	
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
	
	public ArrayList<JsonRuleSubGroup> getSubgroups() 
	{
		return subgroups;
	}
	
	public void setSubgroups(ArrayList<JsonRuleSubGroup> subgroups) 
	{
		this.subgroups = subgroups;
	}


	
	
}
