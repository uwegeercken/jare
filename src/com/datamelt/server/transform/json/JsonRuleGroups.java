package com.datamelt.server.transform.json;

import java.util.ArrayList;

public class JsonRuleGroups 
{
	private ArrayList<JsonRuleGroup> groups = new ArrayList<JsonRuleGroup>();
	private String objectLabel;
	private String processId;

	public ArrayList<JsonRuleGroup> getGroups() 
	{
		return groups;
	}

	public void setGroups(ArrayList<JsonRuleGroup> groups) 
	{
		this.groups = groups;
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

}
