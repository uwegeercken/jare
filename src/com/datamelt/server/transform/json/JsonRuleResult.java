package com.datamelt.server.transform.json;

public class JsonRuleResult 
{
	private String id;
	private int failed;
	private String message;
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
	
	public String getMessage() 
	{
		return message;
	}
	
	public void setMessage(String message) 
	{
		this.message = message;
	}
}
