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
import java.util.Calendar;
import java.util.zip.ZipFile;

import com.datamelt.rules.engine.BusinessRulesEngine;

public class ClientHandler extends Thread
{

	private Socket socket;
    private ObjectInputStream inputStream;
    private BusinessRulesEngine ruleEngine;
    private String ruleFileFolder;
    private String ruleFile;
    private long clientStart;
    private long rowsProcessed=0;
    
    private static final String RESPONSE_UPTIME = "uptime";
    private static final String RESPONSE_EXIT = "exit";
    private static final String RESPONSE_ROWSPROCESSED = "rowsprocessed";
    private static final String RESPONSE_RELOAD = "reload";

    ClientHandler(Socket socket, String ruleFileFolder, String ruleFile) throws Exception
    {
    	this.clientStart = System.currentTimeMillis();
    	this.ruleFileFolder = ruleFileFolder;
    	this.ruleFile = ruleFile;
        this.socket = socket;
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
	                ruleEngine.run(serverObject.getFields());
	                
	                // count the processed rows
	                rowsProcessed++;
	                
	                // set the fields of the object by using the results from the rule engine
	                serverObject.setTotalGroups(ruleEngine.getNumberOfGroups());
	                serverObject.setGroupsPassed(ruleEngine.getNumberOfGroups() - ruleEngine.getNumberOfGroupsFailed());
	                serverObject.setTotalRules(ruleEngine.getNumberOfRules());
	                serverObject.setRulesPassed(ruleEngine.getNumberOfRulesPassed());
	                serverObject.setRuleGroups(ruleEngine.getGroups());
	                serverObject.setRuleExecutionCollection(ruleEngine.getRuleExecutionCollection());	
	                
	                // create an ouput stream
	                ObjectOutputStream outputStream = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
	                
	                // write object to stream
	               	outputStream.writeObject(serverObject);
	               	outputStream.flush();
	             
	               	// clear the execution results, otherwise they get cumulated
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
            	}
            }
            catch (EOFException e)
            {
            	// something went wrong here
            	ok=false;
            }
            catch (SocketException e)
            {
            	// something went wrong here
            	ok=false;
            }
            catch (Exception e)
            {
            	// something went wrong here
            	ok=false;
                e.printStackTrace();
            }
            finally
            {
            	try
            	{
            		//socket.close();
            	}
            	catch(Exception ex)
            	{
            		ex.printStackTrace();
            	}
            }
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
    
    private String getStartDateTime()
    {
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	Calendar cal = Calendar.getInstance();
    	cal.setTimeInMillis(clientStart);
    	return sdf.format(cal.getTime());
    }
    
    private String getRunTime()
    {
    	long runTime = System.currentTimeMillis() - clientStart; 	
    	    	
    	return "" + runTime/1000;
    }
}
