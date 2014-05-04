/*
 * Created on 28.06.2008
 *
 * all code by uwe geercken
 */
package com.datamelt.rules.reader;

import java.util.zip.ZipFile;
import com.datamelt.rules.core.util.Converter;
import com.datamelt.rules.engine.BusinessRulesEngine;

/**
 * SimpleReader class is used to run the business rule engine
 * using a rule xml file against a simple set of data.
 * 
 * Demonstrates that you can check single records or sets of
 * data.
 *  
 * An array of fields of type string is created representing the
 * fields of a record, a Row object is created from it and a rule
 * file is run using the rule engine.
 * 
 * @author uwe geercken
 */
public class SimpleReader
{
    public static final String XML_FILE_EXTENSION  			= ".xml";
    
    public static void main(String[] args) throws Exception
    {
    	System.out.println("start of process...");
    	
    	// create an engine object, passing a reference to the rules file
        BusinessRulesEngine engine = new BusinessRulesEngine(new ZipFile(args[0]),args[1]);
        // set the output of the engine to NO_OUTPUT
        //engine.setOutputType(BusinessRulesEngine.OUTPUT_TYPE_FAILED_AND_PASSED);
        //engine.setPrintStream(new PrintStream(new FileOutputStream(new File("/home/uwe/temp/output.txt"))));
        // create a simple row object with some fields
    	//String [] fields= {"Peter","Paulsen","Paris","Europa","6","1"};
    	//Row row = new Row(fields);

    	// run the engine to check the rules against the row object
    	engine.run("/home/uwe/temp/test.csv",";");
        
        // total number of rules
        int numberOfRules = engine.getNumberOfRules();
        // total number of failed rules
        int numberOfErrors = engine.getNumberOfRulesFailed();
        System.out.println("number of rules       :    " + numberOfRules);
        System.out.println("number of rules failed:    " + numberOfErrors);
        System.out.println("group failed: " + Converter.convertIntegerToBooleanString((int)engine.getNumberOfGroupsFailed()));
        System.out.println("end of process.");
    }
    
}
