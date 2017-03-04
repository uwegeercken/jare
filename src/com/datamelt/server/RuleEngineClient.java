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
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import com.datamelt.util.RowFieldCollection;

public class RuleEngineClient
{
	// the server adress - default is 127.0.0.1
	private String server="127.0.0.1";
	// the port the server runs on - default 9000
	private int port=9000;
	// output type for rule results from the rule engine
	private int outputType =1;
	// the output stream to the server
	private ObjectOutputStream outStream;
	// the socket to the server
	private Socket socket;
	
	private DataInputStream inputStream;
	
	private long resetInterval=1000;
	private long counter=0;
	
	public static final int OUTPUT_TYPE_ALL_GROUPS = 0;
	public static final int OUTPUT_TYPE_FAILED_GROUPS_ONLY = 1;
	public static final int OUTPUT_TYPE_PASSED_GROUPS_ONLY = 2;
	
	public RuleEngineClient(String server, int port) throws UnknownHostException, IOException
	{
		this.server = server;
		this.port = port;
		
		init();
	}
	
	public RuleEngineClient(String server) throws UnknownHostException, IOException
	{
		this.server = server;
		
		init();
	}
	
	private void init() throws UnknownHostException, IOException
	{
		getServerSocket(server, port);
		getOutputStream(socket);
		inputStream = new DataInputStream(getDataInputStream(socket));
	}
	
	public RuleEngineServerObject getServerObject(RowFieldCollection fields) throws IOException
	{
		sendObject(fields);
		return receiveObject();
	}
	
	public String getServerObject(String message) throws IOException, ClassNotFoundException
	{
		sendMessageObject(message);
		return receiveMessageObject();
	}
	
	private void sendObject(RowFieldCollection fields) throws IOException
	{
		// create a server object
		RuleEngineServerObject object = new RuleEngineServerObject(fields,outputType);
		
		counter ++;
		// send the object to the server
       	
		outStream.writeObject(object);
       	outStream.flush();
       	if(counter % resetInterval==0)
       	{
       		outStream.reset();
       	}
	}
	
	private RuleEngineServerObject receiveObject() throws IOException
	{
		//DataInputStream inputStream = new DataInputStream(getDataInputStream(socket));
       	RuleEngineServerObject response = new RuleEngineServerObject();
       	response.setTotalGroups(inputStream.readLong());
       	response.setGroupsPassed(inputStream.readLong());
       	response.setTotalRules(inputStream.readLong());
       	response.setRulesPassed(inputStream.readLong());
       	response.setTotalActions(inputStream.readLong());
       	return response;	
	}
	
	private void sendMessageObject(String message) throws IOException
	{
		// send the message to the server
       	outStream.writeObject(message);
       	outStream.flush();
	}
	
	private String receiveMessageObject() throws IOException, ClassNotFoundException
	{
		// get an input stream from the server
       	ObjectInputStream inputStream = getObjectInputStream(socket);
       	
		// create a response object from the object we received from the server
        return (String)inputStream.readObject();
	}
	
	private void getOutputStream(Socket socket) throws IOException
	{
		// create an output stream to the server
		outStream =  new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
	}
	
	private DataInputStream getDataInputStream(Socket socket) throws IOException
	{
       	// create an inputstream from the server
		return new DataInputStream(new BufferedInputStream(socket.getInputStream()));
	}
	
	private ObjectInputStream getObjectInputStream(Socket socket) throws IOException
	{
       	// create an inputstream from the server
		return new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
	}
	
	private void getServerSocket(String server, int port) throws UnknownHostException, IOException
	{
		// create a socket for the given server
		this.socket = new Socket(server, port);
	}
	
	public void closeSocket() throws IOException
	{
		if(!socket.isClosed())
		{
			socket.close();
		}
	}
	
	public void closeOutputStream() throws IOException
	{
		outStream.close();
	}
	
	public String getServer() 
	{
		return server;
	}

	public void setServer(String server) 
	{
		this.server = server;
	}

	public int getPort() 
	{
		return port;
	}

	public void setPort(int port) 
	{
		this.port = port;
	}

	public int getOutputType() 
	{
		return outputType;
	}

	public void setOutputType(int outputType) 
	{
		this.outputType = outputType;
	}

	public long getCounter() 
	{
		return counter;
	}

}
