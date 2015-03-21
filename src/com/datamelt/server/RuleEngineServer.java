package com.datamelt.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.net.ServerSocketFactory;

import com.datamelt.server.transform.Transformer;
import com.datamelt.util.FileUtility;

public class RuleEngineServer extends Thread
{
    private ServerSocket serverSocket;
    private String ruleFileFolder;
    private String ruleFile;
    private Properties properties = new Properties();
    private Transformer transformer;
    private int port;
    private String propertiesFileFullname;
    
    private static final String PROPERTIES_FILE = "server.properties";
    
    private static final String PROPERTY_PORT = "server.port";
    private static final String PROPERTY_FOLDER_RULEFILE = "rulefile.folder";
    private static final String PROPERTY_RULEFILE = "rulefile.name";
    private static final String PROPERTY_TRANSFORMER = "transformer.classname";

    private static final int DEFAULT_PORT = 9000;
    private static final String DEFAULT_FOLDER_RULEFOLDER = ".";
    private static final String DEFAULT_RULEFILE = "rules.zip";
    
    private RuleEngineServer() throws Exception
    {
    	loadProperties();
    	setVariables();
    	createSocket();
        createTransformer();
        
    }
    
    private RuleEngineServer(String propertiesFile) throws Exception
    {
    	propertiesFileFullname = propertiesFile;
    	loadProperties(propertiesFile);
    	setVariables();
    	createSocket();
        createTransformer();
    }
    
    private void loadProperties() throws IOException
    {
    	propertiesFileFullname = FileUtility.adjustSlash(RuleEngineServer.class.getClassLoader().getResource("").getPath()) + PROPERTIES_FILE;
    	properties.load(RuleEngineServer.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE));
    }
    
    private void loadProperties(String propertiesFilename) throws IOException
    {
    	FileInputStream inputStream = new FileInputStream(new File(propertiesFilename));
    	properties.load(inputStream);
    	inputStream.close();
    }
    
    private void setVariables()
    {
    	if(properties.getProperty(PROPERTY_PORT)!=null)
    	{
    		port = Integer.parseInt(properties.getProperty(PROPERTY_PORT));
    	}
    	else
    	{
    		port = DEFAULT_PORT;
    	}
    	if(getProperty(PROPERTY_FOLDER_RULEFILE)!=null)
    	{
    		ruleFileFolder = FileUtility.adjustSlash(getProperty(PROPERTY_FOLDER_RULEFILE));
    	}
    	else
    	{
    		ruleFileFolder = RuleEngineServer.class.getClassLoader().getResource(DEFAULT_FOLDER_RULEFOLDER).getPath();
    	}
    	if(getProperty(PROPERTY_RULEFILE)!=null)
    	{
    		ruleFile = getProperty(PROPERTY_RULEFILE);
    	}
    	else
    	{
    		ruleFile = DEFAULT_RULEFILE;
    	}
    }
    
    private void createSocket() throws IOException
    {
    	serverSocket = ServerSocketFactory.getDefault().createServerSocket(port);
    }
    
    private void createTransformer() throws Exception
    {
    	if(getProperty(PROPERTY_TRANSFORMER)!=null)
    	{
    		String className = getProperty(PROPERTY_TRANSFORMER).trim();
	    	if(className.length()>0)
	    	{
	    		Class<?> transformerClass = Class.forName(className);
	    		transformer = (Transformer)transformerClass.newInstance();
	    		transformer.setProperties(properties);
	    		transformer.init();
	    	}
    	}
    }

    public static void main(String args[]) throws Exception
    {
    	RuleEngineServer server = null;
    	if(args.length==0)
    	{
    		server = new RuleEngineServer();
    	}
    	else
    	{
    		if(args[0].equals("-h")||args[0].equals("-help"))
    		{
    			//help();
    		}
    		else
    		{
    			server = new RuleEngineServer(args[0]);
    		}
    	}
    	
    	if(FileUtility.fileExists(server.ruleFileFolder, server.ruleFile))
    	{
    		System.out.println("using properties from: " + server.propertiesFileFullname);
    		System.out.println("running rule engine file: " + FileUtility.adjustSlash(server.ruleFileFolder) + server.ruleFile);
    		if(server.transformer!=null)
    		{
    			System.out.println("output with transformer: " + server.transformer.getClass());
    		}
    		else
    		{
    			System.out.println("no transformer defined: no ouput generated");
    		}
    		server.start();
            System.out.println("waiting on: " + server.serverSocket.getInetAddress() + ", port: " + server.port + " for connections");

    	}
    	else
    	{
    		System.out.println("error: file not found or is not a file: " + FileUtility.adjustSlash(server.ruleFileFolder) + server.ruleFile);
    	}
    }
    
    @Override
    public void run()
    {
    	boolean ok=true;
        while (ok)
        {
            try 
            {
                final Socket socketToClient = serverSocket.accept();
                System.out.println("client connected from: " + socketToClient.getInetAddress());
                ClientHandler clientHandler = new ClientHandler(getProcessId(),socketToClient,FileUtility.adjustSlash(ruleFileFolder),ruleFile,transformer);
                clientHandler.start();
            }
            catch (Exception e)
            {
            	ok = false;
            	if(!serverSocket.isClosed())
            	{
            		try 
            		{
						serverSocket.close();
					} 
            		catch (IOException e1)
            		{
						e1.printStackTrace();
					}
            	}
                e.printStackTrace();
            }
        }
    }

	private String getProperty(String key)
	{
		return properties.getProperty(key);
	}
	
	private String getProcessId()
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		return ruleFile + "_" + sdf.format(new Date());
	}
}
