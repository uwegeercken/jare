package com.datamelt.server.transform.log;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;

import com.datamelt.rules.core.RuleGroup;
import com.datamelt.server.RuleEngineServerObject;
import com.datamelt.server.transform.Transformer;
import com.datamelt.util.VelocityDataWriter;

public class LogTransformer extends Transformer 
{
	private static final String PROPERTY_OUPUT_FILENAME = "output.filename";
	private static final String PROPERTY_TEMPLATE_FOLDER = "template.folder";
	private static final String PROPERTY_TEMPLATE_FILENAME = "template.filename";
	
	private BufferedWriter writer;
	private VelocityDataWriter dataWriter;
	
	public LogTransformer() throws Exception
	{
		super();
	}
	
	public void init() throws Exception
	{
		// writer for the output file. results are appended to the file
		writer = new BufferedWriter(new FileWriter((String) getProperties().get(PROPERTY_OUPUT_FILENAME)));
						
		// velocity writer
		dataWriter = new VelocityDataWriter(getProperties().getProperty(PROPERTY_TEMPLATE_FOLDER), getProperties().getProperty(PROPERTY_TEMPLATE_FILENAME));
	}
	public void write(RuleEngineServerObject serverObject,ArrayList<RuleGroup> groups) throws Exception
	{
		// review this loop: depends on how the output should look like
		// currently it outputs all groups seperately
		for(int i=0;i<groups.size();i++)
		{
			dataWriter.addObject("serverobject" , serverObject);
			dataWriter.addObject("group" , groups.get(i));
			writer.write(dataWriter.merge());
			dataWriter.clearObjects();
		}
	}
	
	public void close() throws Exception
	{
		writer.close();
	}
}
