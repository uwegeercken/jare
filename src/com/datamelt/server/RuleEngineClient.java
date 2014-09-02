package com.datamelt.server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.datamelt.util.Row;
import com.datamelt.util.Splitter;

public class RuleEngineClient
{
	// the csv input file
	private static String csvFilename;
	// the seperator deviding the individual fields
	private static String fieldSeperator=";";
	// the server adress - default is localhost
	private static String server="127.0.0.1";
	// the port the server runs on - default 9999
	private static int port=9999;
	// name of the rulefile to use
	private static String ruleFile;
	// outputType 0 = output all groups
	// outputType 1 = output only failed groups
	// outputType 2 = output only passed groups
	// default outputType = 1
	private static int outputType =1;
	
	
	public static void main(String[] args) throws Exception
	{
		// we first check if there are any arguments
		// if so we assign the appropriate variables
    	parseArguments(args);
		
    	// create a socket for the given server
		Socket socketToServer = new Socket(server, port);
		// create an output stream to the server
		ObjectOutputStream outStream = new ObjectOutputStream(socketToServer.getOutputStream());
        
		// create a file
		File inputFile = new File(csvFilename);
		if(!inputFile.isFile() || !inputFile.exists())
		{
			socketToServer.close();
			outStream.close();
			// if not exist or not a file throw an exception
			throw new FileNotFoundException("error: file not found or is not a file: " + csvFilename);
		}
		// prepare a reader for the file
		BufferedReader reader = new BufferedReader(new FileReader(inputFile));

		String line;
	    long counter=0;

	    // the splitter will devide the individual fields of a row in the file into 
	    Splitter splitter = new Splitter(Splitter.TYPE_COMMA_SEPERATED, fieldSeperator);
        
	    // input stream of the socket server
	    ObjectInputStream inputStream=null;
	    // read all lines of the file
    	while ((line=reader.readLine())!=null)
    	{
    		// create a row object for the line/row of the csv file
    		Row row = splitter.getRow(line); 
			counter++;
			
			// create a checkdate object
			CheckData cd = new CheckData(ruleFile, row);
			// send the checkdata object to the server
	       	outStream.writeObject(cd);
	        
	       	// create an inputstream from the server
			inputStream = new ObjectInputStream(socketToServer.getInputStream());
			// create a checkdata object from the object we received from the server
	        CheckData cdReceived = (CheckData)inputStream.readObject();
	        
	        if(outputType==0 || (outputType==1 && cdReceived.getTotalGroups()!=cdReceived.getGroupsPassed()) || (outputType==2 && cdReceived.getTotalGroups()==cdReceived.getGroupsPassed())) // output group results
        	{
	        	System.out.println("row [" + counter + "]: " + cdReceived.getRow());
        	}
            for(int i=0;i<cdReceived.getRuleGroups().size();i++)
            {
            	RuleGroupSimple group = cdReceived.getRuleGroups().get(i);
            	if(outputType==0 ||(outputType==1 && group.getFailed()==1) || (outputType==2 && group.getFailed()==0)) // output group results
            	{
            		System.out.println(group.getResults());
            	}
            }
    	}
    	try
    	{
    		reader.close();
    		socketToServer.close();
    		inputStream.close(); 	
    		outStream.close();
    	}
    	catch(Exception ex)
    	{
    		ex.printStackTrace();
    	}
    }
	
	/**
	 *	parses the arguments that where passed to this program 
	 */
	private static void parseArguments(String args[]) throws Exception
	{
		// arguments shall be in the form:
		// -key=value
		//
		// example: -f=test.csv
		
		for(int i=0;i<args.length;i++)
		{
			if (args[i].startsWith("-f"))
			{
				csvFilename = args[i].substring(3);
			}
			else if (args[i].startsWith("-p"))
			{
				port = Integer.parseInt(args[i].substring(3));
			}
			else if (args[i].startsWith("-s"))
			{
				fieldSeperator = args[i].substring(3);
			}
			else if (args[i].startsWith("-h"))
			{
				server = args[i].substring(3);
			}
			else if (args[i].startsWith("-r"))
			{
				ruleFile = args[i].substring(3);
			}
			else if (args[i].startsWith("-o"))
			{
				outputType = Integer.parseInt(args[i].substring(3));
			}
		}
		
		if(csvFilename==null)
		{
			throw new Exception("argument [-f=] (csv filename) must be specified as an argument");
		}
		if(ruleFile==null)
		{
			throw new Exception("argument [-r=] (rulefile) must be specified as an argument");
		}
	}
}
