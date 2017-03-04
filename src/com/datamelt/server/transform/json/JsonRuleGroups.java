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
