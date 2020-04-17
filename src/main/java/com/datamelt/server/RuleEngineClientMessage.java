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

import java.util.Arrays;

import org.apache.log4j.Logger;

import com.datamelt.server.ClientHandler;
import com.datamelt.server.RuleEngineClient;

/**
 * utility to be used to request status from a running rule engine server
 * by sending a keyword and receiving a response.
 * 
 * keywords allow to get information about the server such as uptime or the name of the
 * rule engine project file in use. can also be used to reload the project file in case
 * it has changed.
 *  * 
 * @author uwe geercken
 *
 */
public class RuleEngineClientMessage 
{
    private static String hostname							= "localhost";
    private static int port									= 9000;
    private static String message;
    
    final static Logger logger = Logger.getLogger(RuleEngineClientMessage.class);
    
	public static void main(String[] args) throws Exception
	{
		if(args.length!=3 && args.length!=1)
		{
			help();
		}
		else
		{
			processArguments(args);
			
			if(message!=null)
			{
				boolean validMessage = checkMessageValidity(message);
				if(validMessage)
				{
					RuleEngineClient client = null;
					try
					{
						// create a client to communicate with the server
						client = new RuleEngineClient(hostname,port);
						
						String response = client.getServerObject(message);
				    	logger.info("server response: " + response);
				    	
				    	// send an exit signal
				    	client.getServerObject(ClientHandler.RESPONSE_EXIT);
					}
					catch(Exception ex)
					{
						logger.error(ex.getMessage());
					}
			    	
					// cleanup
			    	client.closeOutputStream();
				    client.closeSocket();
				}
				else
				{
					logger.info("the keyword provided is invalid. possible keywords are: " + Arrays.deepToString(ClientHandler.MESSAGES));
				}
			}
			else
			{
				logger.error("keyword is undefined - exiting");
			}
		}
    }
	
	private static boolean checkMessageValidity(String message)
	{
		boolean validMessage=false;
		for(String clientHandlerMessage : ClientHandler.MESSAGES)
		{
			if(message.trim().equals(clientHandlerMessage.trim()))
			{
				validMessage = true;
				break;
			}
		}
		return validMessage;
	}
	
	private static void processArguments(String[] args)
	{
		for(int i=0;i<args.length;i++)
		{
			if(args[i].startsWith("-h="))
			{
				hostname=args[i].substring(3);
			}
			else if(args[i].startsWith("-p="))
			{
				port=Integer.parseInt(args[i].substring(3));
			}
			else if(args[i].startsWith("-m="))
			{
				message=args[i].substring(3);
			}
		}
	}
	
	private static void help()
	{
		System.out.println("RuleEngineClientMessage. Utility to be used to request status from a running rule engine server by sending a keyword.");
		System.out.println("Valid keywords are:");
		System.out.println("- uptime       : request response on the uptime of the Jare rule engine server");
		System.out.println("- rulefile     : request response on the name of the rule engine project file in use");
		System.out.println("- reload       : request the Jare rule engine server to reload the rule engine project file in use");
		System.out.println("- rowsprocessed: request response on the total number of rows processed of the Jare rule engine server");
		System.out.println("- processid    : request response on the Java process id of the Jare rule engine server");
		System.out.println("- version      : request response on the Jare rule engine version in use");
		System.out.println("- hello        : request friendly response");
    	System.out.println();
    	System.out.println("RuleEngineClientMessage -h=[hostname] -p=[port] -m=[keyword]");
    	System.out.println("where [hostname] : optional. the hostname or IP address of the server running the Jare rule engine. default: localhost");
    	System.out.println("      [port]     : optional. the port that the Jare rule engine server listens on. default: 9000");
    	System.out.println("      [keyword]  : required. the keyword to be sent to the Jare rule engine server");
    	System.out.println();
    	System.out.println("example: RuleEngineClientMessage -h=localhost -p=9000 -m=uptime");
    	System.out.println("example: RuleEngineClientMessage -m=version");
    	System.out.println();
    	System.out.println("published as open source under the Apache License. read the licence notice");
    	System.out.println("all code by uwe geercken, 2006-2020. uwe.geercken@web.de");
    	System.out.println();
	}

}
