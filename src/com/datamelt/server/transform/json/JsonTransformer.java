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
import java.util.List;

import org.jongo.Jongo;
import org.jongo.MongoCollection;

import com.datamelt.rules.core.RuleGroup;
import com.datamelt.server.RuleEngineServerObject;
import com.datamelt.server.transform.Transformer;
import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

public class JsonTransformer extends Transformer 
{
	private static final String PROPERTY_MONGODB_HOST = "mongodb.host";
	private static final String PROPERTY_MONGODB_PORT = "mongodb.port";
	private static final String PROPERTY_MONGODB_USER = "mongodb.user";
	private static final String PROPERTY_MONGODB_PASSWORD = "mongodb.password";
	private static final String PROPERTY_MONGODB_DATABASE = "mongodb.database";
	private static final String PROPERTY_MONGODB_COLLECTION = "mongodb.collection";
	
	private MongoCollection collection;
	MongoClient client;
	
	public JsonTransformer() throws Exception
	{
		super();
	}
	
	public void init() throws Exception
	{
		List<ServerAddress> servers = new ArrayList<ServerAddress>();
		String serverString = getProperties().get(PROPERTY_MONGODB_HOST).toString();
		String portString = getProperties().get(PROPERTY_MONGODB_PORT).toString();
		String[] serverArray = serverString.split(",");
		String[] portArray = portString.split(",");
		for(int i=0;i<serverArray.length;i++)
		{
			ServerAddress server = new ServerAddress(serverArray[i],Integer.parseInt(portArray[i]));
			servers.add(server);
		}
		
		String user = getProperties().get(PROPERTY_MONGODB_USER).toString().trim();
		String password = getProperties().get(PROPERTY_MONGODB_PASSWORD).toString().trim();
		if(user!=null && user.length()>0 && password!=null && password.length()>0)
		{
			List<MongoCredential> credentials = new ArrayList<MongoCredential>();
			MongoCredential cr = MongoCredential.createMongoCRCredential(getProperties().get(PROPERTY_MONGODB_USER).toString(), getProperties().get(PROPERTY_MONGODB_DATABASE).toString(), getProperties().get(PROPERTY_MONGODB_PASSWORD).toString().toCharArray());
			credentials.add(cr);
			client = new MongoClient(servers,credentials);
		}
		else
		{
			client = new MongoClient(servers);
		}
		DB db = client.getDB(getProperties().get(PROPERTY_MONGODB_DATABASE).toString());
		Jongo jongo = new Jongo(db);
		collection = jongo.getCollection(getProperties().get(PROPERTY_MONGODB_COLLECTION).toString());
		
	}
	public void write(RuleEngineServerObject serverObject,ArrayList<RuleGroup> groups) throws Exception
	{
		JsonRuleGroups ruleGroups = new JsonRuleGroups();
		ruleGroups.setObjectLabel(serverObject.getObjectLabel());
		ruleGroups.setProcessId(serverObject.getProcessId());
		
		for(int k=0;k<groups.size();k++)
		{
			RuleGroup group = groups.get(k);
			JsonRuleGroup jsonRuleGroup = new JsonRuleGroup();
			jsonRuleGroup.setId(group.getId());
			jsonRuleGroup.setFailed(group.getFailed());
			for(int i=0;i<group.getSubGroups().size();i++)
			{
				JsonRuleSubGroup jsonSubgroup = new JsonRuleSubGroup();
				jsonSubgroup.setId(group.getSubGroups().get(i).getId());
				jsonSubgroup.setFailed(group.getSubGroups().get(i).getFailed());
				for(int j=0;j<group.getSubGroups().get(i).getResults().size();j++)
				{
					JsonRuleResult jsonRuleResult = new JsonRuleResult();
					jsonRuleResult.setMessage(group.getSubGroups().get(i).getResults().get(j).getMessage());
					jsonRuleResult.setFailed(group.getSubGroups().get(i).getResults().get(j).getFailed());
					jsonSubgroup.getRuleResults().add(jsonRuleResult);
				}
				jsonRuleGroup.getSubgroups().add(jsonSubgroup);
			}
			ruleGroups.getGroups().add(jsonRuleGroup);
		}
		try
		{
			collection.insert(ruleGroups);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	public void close() throws Exception
	{
		
	}
}
