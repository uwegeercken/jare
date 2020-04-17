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

import java.io.BufferedReader;
import java.io.FileReader;

import org.apache.log4j.Logger;

import com.datamelt.server.ClientHandler;
import com.datamelt.server.RuleEngineClient;
import com.datamelt.util.HeaderRow;
import com.datamelt.util.RowFieldCollection;
import com.datamelt.util.Splitter;

/**
 * Utility class which can be used to send CSV data to a running rule engine server.
 * 
 * Information of the header always has to be defined. The CSV data can either be sent as as an
 * argument or as a filename of a CSV file which shall be processed. Specify the separator that
 * is used between individual columns of the header and the data.
 * 
 * The default host to connect to is localhost on port 9000. 
 * 
 * 
 * @author uwe geercken
 *
 */
public class RuleEngineClientCSV 
{
    private static String hostname							= "localhost";
    private static int port									= 9000;
    private static String separator							= ";";
    private static String header;
    private static String data;
    private static String inputFile;
    
    private static Splitter splitter;
    private static HeaderRow headerRow;
    
    final static Logger logger = Logger.getLogger(RuleEngineClientCSV.class);
    
	public static void main(String[] args) throws Exception
	{
		if(args.length<2)
		{
			help();
		}
		else
		{
			processArguments(args);
			
			// used to split line of data into single columns
			splitter = new Splitter(Splitter.TYPE_COMMA_SEPERATED,separator);
			
			RuleEngineClient client = null;
			boolean socketConnectionOk = false;
			try
			{
				// create a client to communicate with the server
				client = new RuleEngineClient(hostname,port);
				socketConnectionOk = true;
			}
			catch(Exception ex)
			{
				logger.error(ex.getMessage());
			}

			if(socketConnectionOk)
			{
				if(header!=null && (data!=null || inputFile!=null))
				{
					// create a header row from the given header fields
					headerRow = new HeaderRow(header, separator);
					long counter=0;

					if(data!=null)
					{
						counter++;
						RowFieldCollection collection = getRowFieldCollection(data);
						
						// get the results from the server
						RuleEngineServerObject response = client.getServerObject(collection);
						logger.info("server response: " + response.getFields().getFieldValues());
					}
					else if(inputFile!=null)
					{
					    try(BufferedReader reader = new BufferedReader(new FileReader(inputFile));)
					    {
						    String line;
						    while ((line=reader.readLine())!=null)
						    {
					        	// only if the line is NOT empty 
					        	// and does NOT start with a hash sign (comment).
						        if(!line.trim().equals("") && !line.startsWith("#"))
						        {
						        	counter++;
						        	RowFieldCollection collection = getRowFieldCollection(line);
							        
						        	// get the results from the server
						        	try
						        	{
						        		RuleEngineServerObject response = client.getServerObject(collection);
						        		if(!response.getRuleEngineException())
						        		{
						        			logger.info("server response: " + response.getFields().getFieldValues());
						        		}
						        		else
						        		{
						        			logger.error("error processing data: " + response.getRuleEngineExceptionMessage());
						        		}
						        	}
						        	catch(Exception ex)
						        	{
						        		logger.error("error receiving result object from server. check server logs.");
						        	}
						        }
						    }
					    }
					    catch(Exception ex)
					    {
					    	logger.error("input file not found or could not be processed - exiting");
					    }
					}
					else
					{
						logger.error("data or input file needs to be defined - exiting");
					}
			    	
					logger.debug("sending exit signal to server");
					try
					{
				    	client.getServerObject(ClientHandler.RESPONSE_EXIT);
					}
					catch(Exception ex)
					{
						logger.error("error sending exit signal");
					}

			    	logger.debug("closing stream and socket");
					try
					{
				    	client.closeOutputStream();
					    client.closeSocket();
					}
					catch(Exception ex)
					{
						logger.error("error closing output stream or socket");
					}
				    
				    logger.debug("processed lines: " + counter);
				}
				else
				{
					logger.error("header and either data or inputfile needs to be defined - exiting");
				}
			}
		}
    }
	
	private static RowFieldCollection getRowFieldCollection(String line) throws Exception
	{
		Object[] fields = splitter.getFields(line);
		return new RowFieldCollection(headerRow,fields);
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
			else if(args[i].startsWith("-n="))
			{
				header=args[i].substring(3);
			}
			else if(args[i].startsWith("-d="))
			{
				data=args[i].substring(3);
			}
			else if(args[i].startsWith("-f="))
			{
				inputFile=args[i].substring(3);
			}
			else if(args[i].startsWith("-s="))
			{
				separator=args[i].substring(3);
			}
		}
	}
	
	private static void help()
	{
		System.out.println("RuleEngineClientCSV. Sample implementation to send csv data to a running Jare rule engine server.");
		System.out.println("The header for the CSV data has to be specified. It defines all fields of either the data passed with");
		System.out.println("the -d argument or the CSV file as specified in the -f argument. Either data in form of a string or");
		System.out.println("an input file has to be specified.");
		System.out.println();
		System.out.println("The server runs a certain rule engine project zip file containing all rule groups, rules and actions.");
		System.out.println("Make sure that all fields required to process the data - as defined in the zip file - are specified here");
		System.out.println("in the header and also either the data or the CSV file.");
		System.out.println();
		System.out.println("The response from the server is an object containing the results of the execution of the rules against");
		System.out.println("the given set of data. This sample simply outputs the resulting data for the demonstrations purpose.");
		System.out.println();
    	System.out.println("RuleEngineClientCSV -h=[hostname] -p=[port] -n=[header] -d=[data] -s=[separator]");
    	System.out.println("where [hostname]  : optional. the hostname or IP address of the server running the Jare rule engine. default: localhost");
    	System.out.println("      [port]      : optional. the port that the Jare rule engine server listens on. default: 9000");
    	System.out.println("      [header]    : required. a string containing all the field names. individual fields are devided using a separator.");
    	System.out.println("      [data]      : optional. a string containing the data. individual fields are devided using a separator.");
    	System.out.println("      [inputfile] : optional. name of the file containing the data");
    	System.out.println("      [separator] : optional. the separator used between the fields. default: semicolon");
    	System.out.println();
    	System.out.println("examples: RuleEngineClientCSV -h=localhost -p=9000 -n=field1;field2;field3 -d=value1;value2;value3");
    	System.out.println("          RuleEngineClientCSV -h=localhost -n=field1,field2,field3 -f=myfile.csv -s=,");
    	System.out.println("          RuleEngineClientCSV -n=field1;field2;field3 -f=myfile.csv");
    	System.out.println();
    	System.out.println("published as open source under the Apache License. read the licence notice");
    	System.out.println("all code by uwe geercken, 2006-2020. uwe.geercken@web.de");
    	System.out.println();
	}

}
