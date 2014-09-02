package com.datamelt.server;

import java.io.EOFException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.datamelt.rules.core.RuleGroup;
import com.datamelt.rules.engine.BusinessRulesEngine;
import com.datamelt.util.VelocityDataWriter;

public class ClientHandler extends Thread
{

	private Socket socket;
    private ObjectInputStream inputStream;
    private BusinessRulesEngine ruleEngine;
    private String templateFolder;
    private String template;

    ClientHandler(Socket socket,String templateFolder,String template) throws Exception
    {
        this.socket = socket;
        this.inputStream = new ObjectInputStream(socket.getInputStream());
        this.templateFolder=templateFolder;
        this.template= template;
        
    }

    @Override
    public void run()
    {
    	boolean ok=true;
        while (ok)
        {
            try
            {
                CheckData cd = (CheckData)inputStream.readObject();
                //System.out.println("running rule(s): " + cd);
                
                // initialize ruleengine with rules file
                ruleEngine = new BusinessRulesEngine(cd.getRulefile());
                // set the output of the ruleengine to null
                ruleEngine.setPrintStream(null);
                // run the rule engine
                ruleEngine.run(cd.getRow());
                
                cd.setTotalGroups(ruleEngine.getNumberOfGroups());
                cd.setGroupsPassed(ruleEngine.getNumberOfGroups() - ruleEngine.getNumberOfGroupsFailed());
                cd.setTotalRules(ruleEngine.getNumberOfRules());
                cd.setRulesPassed(ruleEngine.getNumberOfRulesPassed());
                
                // generate the output/results with the apache velocity template engine
                VelocityDataWriter writer = new VelocityDataWriter(templateFolder,template);
                // loop over all groups
                for(int i=0;i<ruleEngine.getGroups().size();i++)
                {
                	RuleGroup group = ruleEngine.getGroups().get(i);
                	// create a simple group which can be serialized
                	RuleGroupSimple groupSimple = new RuleGroupSimple();
                	// add the group
                	writer.addObject("group",group);
            		groupSimple.setFailed(group.getFailed());
            		// add the merged result to the groupSimple
            		groupSimple.setResults(writer.merge());
            		// add the groupSimple to the object we received
            		cd.addRuleGroup(groupSimple);
                }
            		
                // create an ouput stream
                ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
                // write the object to the stream
                outputStream.writeObject(cd);
            }
            catch (EOFException e)
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
}
