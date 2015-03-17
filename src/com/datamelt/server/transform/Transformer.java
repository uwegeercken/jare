package com.datamelt.server.transform;

import java.util.ArrayList;
import java.util.Properties;

import com.datamelt.rules.core.RuleGroup;
import com.datamelt.server.RuleEngineServerObject;

public abstract class Transformer 
{
	private Properties properties;
	
	protected Transformer() throws Exception
	{
	}
	
	public abstract void init() throws Exception;
	public abstract void write(RuleEngineServerObject serverObject,ArrayList<RuleGroup> group) throws Exception;
	public abstract void close() throws Exception;
	
	public Properties getProperties() 
	{
		return properties;
	}

	public void setProperties(Properties properties) 
	{
		this.properties = properties;
	}
	
}
