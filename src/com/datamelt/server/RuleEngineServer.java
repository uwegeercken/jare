package com.datamelt.server;

import java.net.ServerSocket;
import java.net.Socket;

import javax.net.ServerSocketFactory;

public class RuleEngineServer extends Thread
{
    private ServerSocket serverSocket;
    private String templateFolder;
    private String template;

    RuleEngineServer(int port) throws Exception
    {
        serverSocket = ServerSocketFactory.getDefault().createServerSocket(port);
        System.out.println("waiting on: " + serverSocket.getInetAddress() + " port: " + port + " for connections");
    }

    public static void main(String args[]) throws Exception
    {
    	RuleEngineServer server = new RuleEngineServer(Integer.parseInt(args[0]));
    	server.templateFolder=args[1];
    	server.template=args[2];
        server.start();
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
                ClientHandler clientHandler = new ClientHandler(socketToClient,templateFolder,template);
                clientHandler.start();
            }
            catch (Exception e)
            {
            	ok = false;
                e.printStackTrace();
            }
        }
    }

}
