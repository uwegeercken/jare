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
import java.util.ArrayList;
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
    
    private static final String RESPONSE_UPTIME = "uptime";
    private static final String RESPONSE_RULEFILE = "rulefile";
    private static final String RESPONSE_EXIT = "exit";
    private static final String RESPONSE_ROWSPROCESSED = "rowsprocessed";
    private static final String RESPONSE_RELOAD = "reload";
    private static final String RESPONSE_PROCESSID = "processid";

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
	                
	                // set the fields of the object by using the results from the rule engine
	                serverObject.setTotalGroups(ruleEngine.getNumberOfGroups());
	                serverObject.setGroupsPassed(ruleEngine.getNumberOfGroups() - ruleEngine.getNumberOfGroupsFailed());
	                serverObject.setTotalRules(ruleEngine.getNumberOfRules());
	                serverObject.setRulesPassed(ruleEngine.getNumberOfRulesPassed());
	                serverObject.setTotalActions(ruleEngine.getNumberOfActions());
	                serverObject.setRuleGroups(ruleEngine.getGroups());
	                serverObject.setObjectLabel(serverObject.getFields().getFieldValues());
	                serverObject.setProcessId(processId);
	                serverObject.setRuleExecutionCollection(ruleEngine.getRuleExecutionCollection());	
	                
	                DataOutputStream outputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
	                
	                outputStream.writeLong(ruleEngine.getNumberOfGroups());
	                outputStream.writeLong(ruleEngine.getNumberOfGroups() - ruleEngine.getNumberOfGroupsFailed());
	                outputStream.writeLong(ruleEngine.getNumberOfRules());
	                outputStream.writeLong(ruleEngine.getNumberOfRulesPassed());
	                outputStream.writeLong(ruleEngine.getNumberOfActions());
	                outputStream.flush();
	                
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
    	                String responseMessage = "stopping thread...";
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
            			
    	                String responseMessage = "reloaded rule file: " + ruleFileFolder + ruleFile;
    	                sendMessage(responseMessage);
    	                
    	                System.out.println(responseMessage);
            		}
            		else if(serverObject.equals(RESPONSE_UPTIME))
            		{
    	                String responseMessage = "thread running since: " + getRunTime();
    	                sendMessage(responseMessage);
    	                
    	                System.out.println(responseMessage);
            		}
            		else if(serverObject.equals(RESPONSE_ROWSPROCESSED))
            		{
    	                String responseMessage = "rows processed: " + rowsProcessed;
    	                sendMessage(responseMessage);
    	                
    	                System.out.println(responseMessage);
            		}
            		else if(serverObject.equals(RESPONSE_PROCESSID))
            		{
    	                String responseMessage = processId;
    	                sendMessage(responseMessage);
    	                
    	                System.out.println(responseMessage);
            		}
            		else if(serverObject.equals(RESPONSE_RULEFILE))
            		{
    	                String responseMessage = "running rule file: " + ruleFileFolder + ruleFile;
    	                sendMessage(responseMessage);
    	                
    	                System.out.println(responseMessage);
            		}
            		else
            		{
    	                String responseMessage = "unknown request: " + serverObject;
    	                sendMessage(responseMessage);
    	                
    	                System.out.println(responseMessage);
            		}
            	}
            	else
            	{
            		String responseMessage = "unknown or unhandled object received: ";
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
            finally
            {
            	try
            	{
            		transformer.close();
            		socket.close();
            	}
            	catch(Exception ex)
            	{
            		ex.printStackTrace();
            	}
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
