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
package com.datamelt.server.transform.log;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;

import com.datamelt.rules.core.RuleGroup;
import com.datamelt.server.RuleEngineServerObject;
import com.datamelt.server.transform.Transformer;
import com.datamelt.util.VelocityDataWriter;

public class LogTransformer extends Transformer 
{
	private static final String PROPERTY_OUPUT_FILENAME = "output.filename";
	private static final String PROPERTY_TEMPLATE_FOLDER = "template.folder";
	private static final String PROPERTY_TEMPLATE_FILENAME = "template.filename";
	
	private BufferedWriter writer;
	private VelocityDataWriter dataWriter;
	
	public LogTransformer() throws Exception
	{
		super();
	}
	
	public void init() throws Exception
	{
		// writer for the output file. results are appended to the file
		writer = new BufferedWriter(new FileWriter((String) getProperties().get(PROPERTY_OUPUT_FILENAME)));
						
		// velocity writer
		dataWriter = new VelocityDataWriter(getProperties().getProperty(PROPERTY_TEMPLATE_FOLDER), getProperties().getProperty(PROPERTY_TEMPLATE_FILENAME));
	}
	public void write(RuleEngineServerObject serverObject,ArrayList<RuleGroup> groups) throws Exception
	{
		// review this loop: depends on how the output should look like
		// currently it outputs all groups separately
		
		try
		{
			for(int i=0;i<groups.size();i++)
			{
				dataWriter.addObject("serverobject" , serverObject);
				dataWriter.addObject("group" , groups.get(i));
				writer.write(dataWriter.merge());
				dataWriter.clearObjects();
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	public void close() throws Exception
	{
		writer.close();
	}
}
