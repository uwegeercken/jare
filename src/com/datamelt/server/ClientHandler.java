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
package com.datamelt.server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.zip.ZipFile;

import com.datamelt.rules.core.RuleGroup;
import com.datamelt.rules.engine.BusinessRulesEngine;
import com.datamelt.server.transform.Transformer;

public class ClientHandler extends Thread
{
	private String processId;
	private Socket socket;
    private ObjectInputStream inputStream;
    private BusinessRulesEngine ruleEngine;
    private String ruleFileFolder;
    private String ruleFile;
    private long clientStart;
    private long rowsProcessed=0;
    private Transformer transformer;
    private DataOutputStream outputStream;
    
    private static final String RESPONSE_UPTIME 			= "uptime";
    private static final String RESPONSE_RULEFILE 			= "rulefile";
    private static final String RESPONSE_EXIT 				= "exit";
    private static final String RESPONSE_ROWSPROCESSED 		= "rowsprocessed";
    private static final String RESPONSE_RELOAD 			= "reload";
    private static final String RESPONSE_PROCESSID 			= "processid";
    private static final String RESPONSE_RULEENGINE_VERSION	= "processid";
    private static final String DEFAULT_DATETIME_FORMAT		= "yyyy-MM-dd hh:mm:ss";
    
    private static SimpleDateFormat sdf						= new SimpleDateFormat(DEFAULT_DATETIME_FORMAT);
    
    ClientHandler(String processId, Socket socket, String ruleFileFolder, String ruleFile, Transformer transformer) throws Exception
    {
    	this.clientStart = System.currentTimeMillis();
    	this.processId= processId;
    	this.ruleFileFolder = ruleFileFolder;
    	this.ruleFile = ruleFile;
        this.socket = socket;
        this.transformer = transformer;
        
        this.ruleEngine = new BusinessRulesEngine(new ZipFile(ruleFileFolder + ruleFile));
        this.inputStream = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
        
        this.outputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        
    }

    @Override
    public void run()
    {
    	boolean ok=true;
        while (ok)
        {
            try
            {
            	// waiting for a server object on the input stream
            	Object object = inputStream.readObject();
            	
            	if(object instanceof RuleEngineServerObject)
            	{
	            	RuleEngineServerObject serverObject = (RuleEngineServerObject) object;
	            		
	                // set the output type
	                ruleEngine.setOutputType(serverObject.getOutputType());
	                
	                // run the rule engine
	                ruleEngine.run(serverObject.getFields().getFieldValues(), serverObject.getFields());
	                
	                // count the processed rows
	                rowsProcessed++;
	                
	                outputStream.writeLong(ruleEngine.getNumberOfGroups());
	                outputStream.writeLong(ruleEngine.getNumberOfGroups() - ruleEngine.getNumberOfGroupsFailed());
	                outputStream.writeLong(ruleEngine.getNumberOfGroupsSkipped());
	                outputStream.writeLong(ruleEngine.getNumberOfRules());
	                outputStream.writeLong(ruleEngine.getNumberOfRulesPassed());
	                outputStream.writeLong(ruleEngine.getNumberOfActions());
	                outputStream.flush();

	                // set the fields of the object by using the results from the rule engine
	                serverObject.setTotalGroups(ruleEngine.getNumberOfGroups());
	                serverObject.setGroupsPassed(ruleEngine.getNumberOfGroups() - ruleEngine.getNumberOfGroupsFailed());
	                serverObject.setGroupsSkipped(ruleEngine.getNumberOfGroupsSkipped());
	                serverObject.setTotalRules(ruleEngine.getNumberOfRules());
	                serverObject.setRulesPassed(ruleEngine.getNumberOfRulesPassed());
	                serverObject.setTotalActions(ruleEngine.getNumberOfActions());
	                serverObject.setRuleGroups(ruleEngine.getGroups());
	                serverObject.setObjectLabel(serverObject.getFields().getFieldValues());
	                serverObject.setProcessId(processId);
	                serverObject.setRuleExecutionCollection(ruleEngine.getRuleExecutionCollection());	
	                
	                // output the results
	                output(serverObject,ruleEngine.getGroups());
	                
	               	// clear the execution results, otherwise they get accumulated
	               	ruleEngine.getRuleExecutionCollection().clear();
            	}
            	else if(object instanceof String)
            	{
            		String serverObject = (String)object;
            		if(serverObject.equals(RESPONSE_EXIT))
            		{
            			// write response message
    	                String responseMessage = sdf.format(new Date()) + " - client requested exit - stopping thread...";
    	                sendMessage(responseMessage);
    	                
    	                System.out.println(responseMessage);

    	                if(!socket.isClosed())
            			{
            				socket.close();
            			}
    	               	ok=false;
            		}
            		else if(serverObject.equals(RESPONSE_RELOAD))
            		{
            			// create a new instance of the rule engine
            			ruleEngine = new BusinessRulesEngine(new ZipFile(ruleFileFolder + ruleFile));
            			
    	                String responseMessage = sdf.format(new Date()) + " - client requested reload of rule file: " + ruleFileFolder + ruleFile;
    	                sendMessage(responseMessage);
    	                
    	                System.out.println(responseMessage);
            		}
            		else if(serverObject.equals(RESPONSE_UPTIME))
            		{
    	                String responseMessage = sdf.format(new Date()) + " - thread running since: " + getRunTime();
    	                sendMessage(responseMessage);
    	                
    	                System.out.println(responseMessage);
            		}
            		else if(serverObject.equals(RESPONSE_ROWSPROCESSED))
            		{
    	                String responseMessage = sdf.format(new Date()) + " - rows processed: " + rowsProcessed;
    	                sendMessage(responseMessage);
    	                
    	                System.out.println(responseMessage);
            		}
            		else if(serverObject.equals(RESPONSE_PROCESSID))
            		{
    	                String responseMessage = sdf.format(new Date()) + " - " + processId;
    	                sendMessage(responseMessage);
    	                
    	                System.out.println(responseMessage);
            		}
            		else if(serverObject.equals(RESPONSE_RULEFILE))
            		{
    	                String responseMessage = sdf.format(new Date()) + " - running rule file: " + ruleFileFolder + ruleFile;
    	                sendMessage(responseMessage);
    	                
    	                System.out.println(responseMessage);
            		}
            		else if(serverObject.equals(RESPONSE_RULEENGINE_VERSION))
            		{
    	                String responseMessage = sdf.format(new Date()) + " - ruleengine version: " + BusinessRulesEngine.getVersion();
    	                sendMessage(responseMessage);
    	                
    	                System.out.println(responseMessage);
            		}
            		else
            		{
    	                String responseMessage = sdf.format(new Date()) + " - unknown request: " + serverObject;
    	                sendMessage(responseMessage);
    	                
    	                System.out.println(responseMessage);
            		}
            	}
            	else
            	{
            		String responseMessage = sdf.format(new Date()) + " - unknown or unhandled object received: ";
	                sendMessage(responseMessage);
	                
	                System.out.println(responseMessage);
            	}
            }
            catch (EOFException e)
            {
            	// something went wrong here
            	ok=false;
            	try
            	{
            		transformer.close();
            		if(!socket.isClosed())
         			{
         				socket.close();
         			}
            	}
            	catch(Exception ex)
            	{
            		
            	}
            }
            catch (SocketException e)
            {
            	// something went wrong here
            	ok=false;
            	try
            	{
            		transformer.close();
            		if(!socket.isClosed())
         			{
         				socket.close();
         			}
            	}
            	catch(Exception ex)
            	{
            		
            	}
            }
            catch (Exception e)
            {
            	// something went wrong here
            	ok=false;
            	try
            	{
            		transformer.close();
            		if(!socket.isClosed())
         			{
         				socket.close();
         			}
            	}
            	catch(Exception ex)
            	{
            		
            	}
                e.printStackTrace();
            }
        }
    }
    
    private void output(RuleEngineServerObject serverObject,ArrayList<RuleGroup> groups) throws Exception
    {
   		if(transformer!=null)
   		{
   			transformer.write(serverObject,groups);
   		}
    }
    
    private void sendMessage(String responseMessage) throws IOException
    {
        // create an ouput stream
        ObjectOutputStream outputStream = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        // write message to stream
        outputStream.writeObject(responseMessage);
       	outputStream.flush();
    }
    
    private String getRunTime()
    {
    	long runTime = System.currentTimeMillis() - clientStart; 	
    	    	
    	return "" + runTime/1000;
    }

	public String getProcessId()
	{
		return processId;
	}

	public BusinessRulesEngine getRuleEngine() 
	{
		return ruleEngine;
	}

	public String getRuleFileFolder() 
	{
		return ruleFileFolder;
	}

	public String getRuleFile() 
	{
		return ruleFile;
	}

	public long getClientStart() 
	{
		return clientStart;
	}

	public long getRowsProcessed() 
	{
		return rowsProcessed;
	}

	public Transformer getTransformer() 
	{
		return transformer;
	}
}
