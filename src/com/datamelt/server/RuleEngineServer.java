package com.datamelt.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.net.ServerSocketFactory;

import com.datamelt.util.FileUtility;

public class RuleEngineServer extends Thread
{
    private ServerSocket serverSocket;
    private String ruleFileFolder;
    private String ruleFile;
    private long resetInterval=1000;

    private RuleEngineServer(int port) throws Exception
    {
        serverSocket = ServerSocketFactory.getDefault().createServerSocket(port);
        System.out.println("waiting on: " + serverSocket.getInetAddress() + ", port: " + port + " for connections");
    }

    public static void main(String args[]) throws Exception
    {
    	RuleEngineServer server = new RuleEngineServer(Integer.parseInt(args[0]));
    	server.ruleFileFolder = FileUtility.adjustSlash(args[1]);
		server.ruleFile = args[2];
		server.resetInterval = Long.parseLong(args[3]);
    	if(FileUtility.fileExists(server.ruleFileFolder, server.ruleFile))
    	{
    		
    		System.out.println("server starting with rule file: " + server.ruleFileFolder + server.ruleFile);
    		server.start();
    	}
    	else
    	{
    		System.out.println("file not found or is not a file: " + server.ruleFileFolder + server.ruleFile);
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
                ClientHandler clientHandler = new ClientHandler(socketToClient,ruleFileFolder,ruleFile,resetInterval);
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
}
