/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
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
