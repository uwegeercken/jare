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
    private BusinessRulesEngine ruleEngine;
    private String ruleFileFolder;
    private String ruleFile;
    private long clientStart;
    private long serverStart;
    private long rowsProcessed=0;
    private Transformer transformer;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;
    
    // list of possible messages
    // the "exit" message is explicitly excluded here
    public static final String[] MESSAGES					= {"uptime","rulefile","rowsprocessed","reload","processid","version","groups","hello"};
    
    public static final String RESPONSE_UPTIME 				= "uptime";
    public static final String RESPONSE_RULEFILE 			= "rulefile";
    public static final String RESPONSE_EXIT 				= "exit";
    public static final String RESPONSE_ROWSPROCESSED 		= "rowsprocessed";
    public static final String RESPONSE_RELOAD 				= "reload";
    public static final String RESPONSE_PROCESSID 			= "processid";
    public static final String RESPONSE_RULEENGINE_VERSION	= "version";
    public static final String RESPONSE_NUMBER_OF_GROUPS	= "groups";
    public static final String RESPONSE_HELLO				= "hello client";
    
    private static final String DEFAULT_DATETIME_FORMAT		= "yyyy-MM-dd hh:mm:ss";
    private static SimpleDateFormat sdf						= new SimpleDateFormat(DEFAULT_DATETIME_FORMAT);
    
    ClientHandler(String processId, Socket socket, String ruleFileFolder, String ruleFile, Transformer transformer, long serverStart) throws Exception
    {
    	this.clientStart = System.currentTimeMillis();
    	this.serverStart = serverStart;
    	this.processId= processId;
    	this.ruleFileFolder = ruleFileFolder;
    	this.ruleFile = ruleFile;
        this.transformer = transformer;
        this.socket = socket;
        
        this.ruleEngine = new BusinessRulesEngine(new ZipFile(ruleFileFolder + ruleFile));
        
        // if no transformer is defined then no detailed output is generated. so we don't need
        // the detailed results of the rule engine. if one is defined, we keep them.
        if(transformer!=null)
        {
        	ruleEngine.setPreserveRuleExcecutionResults(true);
        }
        
        this.outputStream = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        // flush MUST be called after creating the output stream, otherwise the stream blocks
        outputStream.flush();
        this.inputStream = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
    }

    @Override
    public void run()
    {
    	try
        {
    		boolean ok=true;
    		while (ok)
    		{
            	// waiting for a server object on the input stream
            	Object object = inputStream.readObject();

            	if(object instanceof RuleEngineServerObject)
            	{
	            	RuleEngineServerObject serverObject = (RuleEngineServerObject) object;
	            		
	                // set the output type
	                ruleEngine.setOutputType(serverObject.getOutputType());
	                
	                // run the rule engine
	                ruleEngine.run("row_" + rowsProcessed + "_" + sdf.format(new Date()), serverObject.getFields());
	                
	                // count the processed rows
	                rowsProcessed++;
	                
	                // set the fields of the object by using the results from the rule engine
	                serverObject.setTotalGroups(ruleEngine.getNumberOfGroups());
	                serverObject.setGroupsFailed(ruleEngine.getNumberOfGroupsFailed());
	                serverObject.setGroupsSkipped(ruleEngine.getNumberOfGroupsSkipped());
	                serverObject.setTotalRules(ruleEngine.getNumberOfRules());
	                serverObject.setRulesFailed(ruleEngine.getNumberOfRulesFailed());
	                serverObject.setTotalActions(ruleEngine.getNumberOfActions());
	                serverObject.setObjectLabel(serverObject.getFields().getFieldValues());
	                serverObject.setProcessId(processId);
	                
	                outputStream.writeObject(serverObject);
	                outputStream.flush();
	                
	                // add additional information of the rule engine to the server object for output purposes
	                serverObject.setRuleGroups(ruleEngine.getGroups());
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
    	                String responseMessage = "exit";
    	                sendMessage(responseMessage);
    	                
    	                System.out.println(sdf.format(new Date()) + " - client requested exit - closing client socket");

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
            			
    	                String responseMessage = "reloaded rule file: " + ruleFileFolder + ruleFile;
    	                sendMessage(responseMessage);
    	                
    	                System.out.println(sdf.format(new Date()) + " - reloaded rule file: " + responseMessage);
            		}
            		else if(serverObject.equals(RESPONSE_UPTIME))
            		{
    	                String responseMessage = getRunTime();
    	                sendMessage(responseMessage);
    	                
    	                //System.out.println(sdf.format(new Date()) + " - server running since: " + responseMessage);
            		}
            		else if(serverObject.equals(RESPONSE_ROWSPROCESSED))
            		{
    	                String responseMessage = "" + rowsProcessed;
    	                sendMessage(responseMessage);
    	                
    	                //System.out.println(sdf.format(new Date()) + " - rows processed: " + responseMessage);
            		}
            		else if(serverObject.equals(RESPONSE_PROCESSID))
            		{
    	                String responseMessage = processId;
    	                sendMessage(responseMessage);
    	                
    	                //System.out.println(sdf.format(new Date()) + " - process id: " + responseMessage);
            		}
            		else if(serverObject.equals(RESPONSE_RULEFILE))
            		{
    	                String responseMessage = ruleFileFolder + ruleFile;
    	                sendMessage(responseMessage);
    	                
    	                //System.out.println(sdf.format(new Date()) + " - running rule file: " + responseMessage);
            		}
            		else if(serverObject.equals(RESPONSE_RULEENGINE_VERSION))
            		{
    	                String responseMessage = BusinessRulesEngine.getVersion();
    	                sendMessage(responseMessage);
    	                
    	                //System.out.println(sdf.format(new Date()) + " - rule engine version: " + responseMessage);
            		}
            		else if(serverObject.equals(RESPONSE_NUMBER_OF_GROUPS))
            		{
    	                String responseMessage = "" + ruleEngine.getNumberOfGroups();
    	                sendMessage(responseMessage);
    	                
    	                //System.out.println(sdf.format(new Date()) + " - number of rulegroups: " + responseMessage);
            		}
            		else if(serverObject.equals(RESPONSE_HELLO))
            		{
    	                sendMessage(RESPONSE_HELLO);
            		}
            		else
            		{
    	                String responseMessage = "unknown request: " + serverObject;
    	                sendMessage(responseMessage);
    	                
    	                System.out.println(sdf.format(new Date()) + " - " + responseMessage);
            		}
            	}
            	else
            	{
            		String responseMessage = "unknown or unhandled object received";
	                sendMessage(responseMessage);
	                
	                System.out.println(sdf.format(new Date()) + " - " + responseMessage);
            	}
            }
            
        }
    	catch (EOFException e)
        {
        	// something went wrong here
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
        //ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
        // write message to stream
        outputStream.writeObject(responseMessage);
       	outputStream.flush();
    }
    
    private String getRunTime()
    {
    	long runTime = System.currentTimeMillis() - serverStart;
    	long seconds = runTime/1000;
    	
    	if(seconds < 60)
    	{
    		return "" + seconds + " seconds";
    	}
    	else if(seconds >= 60 && seconds < 3600)
    	{
    		return "" + seconds/60 + " minute(s)";
    	}
    	else if(seconds >= 3600 && seconds < 86400)
    	{
    		return "" + seconds/3600 + " hour(s)";
    	}
    	else 
    	{
    		return "" + seconds/86400 + " day(s)";
    	}
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
