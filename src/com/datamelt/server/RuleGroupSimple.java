package com.datamelt.server;

import java.io.Serializable;
import java.util.ArrayList;

public class RuleGroupSimple implements Serializable
{
	private int failed=0;
	private String results;
	
	public int getFailed() 
	{
		return failed;
	}
	
	public void setFailed(int failed) 
	{
		this.failed = failed;
	}
	
	public String getResults() 
	{
		return results;
	}
	
	public void setResults(String results) 
	{
		this.results = results;
	}
	
}
