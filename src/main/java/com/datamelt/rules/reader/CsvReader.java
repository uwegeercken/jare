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
package com.datamelt.rules.reader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.zip.ZipFile;

import com.datamelt.util.HeaderRow;
import com.datamelt.util.RowFieldCollection;
import com.datamelt.util.Splitter;
import com.datamelt.rules.core.RuleExecutionCollection;
import com.datamelt.rules.core.RuleExecutionResult;
import com.datamelt.rules.core.RuleGroup;
import com.datamelt.rules.core.RuleSubGroup;
import com.datamelt.rules.core.XmlRule;
import com.datamelt.rules.engine.BusinessRulesEngine;

/**
 * CsvReader class is used to run the business rule engine
 * against data from a CSV file. The default separator used
 * is the semicolon (;) but may be changed to another character.
 * 
 * Example:
 * Hamburg;Europe;3.750000;german 
 * Denver;United States;6.000000;english
 * Stockholm;Sweden;2.578300;swedish
 * 
 * Data from the file is read, Row objects are constructed and
 * are run against the rule engine using rules as defined in a rule
 * xml file.
 * 
 * @author uwe geercken
 */
public class CsvReader 
{
    public static final String XML_FILE_EXTENSION  			= ".xml";
    
    public static void main(String[] args) throws Exception
    {
    	// if no arguments are defined or help requested, output help info
    	if(args== null || args.length==0 || args[0].equals("-h") || args[0].equals("--help"))
    	{
    		help();
    		System.exit(0);
    	}
    	
    	// capture start time
    	Calendar start = Calendar.getInstance();
	    System.out.println("start:                     " + start.getTime());

	    // reader for the data file
	    BufferedReader reader = new BufferedReader(new FileReader(args[0]));
	    String line;
	    // counts number of lines in data file
	    long counter=0;
	    
	    // the rule engine processes the rules from a zip file generated with
	    // the Business Rules Maintenance Tool. The zip file contains all the
	    // business logic for a given project.
        ZipFile zipFile = new ZipFile(args[1]);

        // create an engine object, passing a reference to the zipfile 
        // that contains the business rules
        BusinessRulesEngine engine = new BusinessRulesEngine(zipFile);
        //BusinessRulesEngine engine = new BusinessRulesEngine(fileList);

        // splitter object will split the row of the  data/csv file into
        // its fields using - in this case - the default semicolon (;) seperator
        Splitter splitter = new Splitter(Splitter.TYPE_COMMA_SEPERATED,Splitter.SEPERATOR_TAB);

        // output the number of groups in total as parsed from the xml files
        System.out.println("number of groups:          " + engine.getGroups().size());
        // output the number of rules in total as parsed from the xml files
        System.out.println("number of rules:           " + engine.getNumberOfRules());
        
        // loop over all groups and output the definition/relation between
        // groups, subgroups and rules
        for(int i = 0;i<engine.getGroups().size();i++)
        {
            RuleGroup group = (RuleGroup)engine.getGroups().get(i);
            System.out.println("group logic:               " + group.getId() + ": "+ engine.getRuleLogic(i));
        }
        
        HeaderRow header = new HeaderRow(line=reader.readLine(),Splitter.SEPERATOR_TAB);
        
        // read the input file line by line
        while ((line=reader.readLine())!=null)
	    {
        	// only if the line is NOT empty 
        	// and does NOT start with a hash sign (comment).
        	// otherwise the line will be NOT be processed nor counted!
	        if(!line.trim().equals("") && !line.startsWith("#"))
	        {
	        	line = line.replace("\"","");
		        // get a row object containing the fields and data
	        	RowFieldCollection row = new RowFieldCollection(header, splitter.getFields(line));
		        //RowFieldCollection row = splitter.getRowFieldCollection(line); 
		        
	        		System.out.println("row:                       " + counter);
	        		System.out.println("row values:                " + row.getFieldValues());
	        	 
		        // run rules on this data
		        engine.run("row: " + counter, row);
		        
		        // loop over groups, subgroups and results to get the
		        // details of the execution of the rules
		        for(int f=0;f<engine.getGroups().size();f++)
		        {
		        	RuleGroup group = engine.getGroups().get(f);
		        	
		        	System.out.println("group:                     " + group.getId());
		        	System.out.println("group failed:              " + group.getFailedAsString());
		        	
		        	for(int g=0;g<group.getSubGroups().size();g++)
		            {
		        		RuleSubGroup subgroup = group.getSubGroups().get(g);
		        		
		        		//System.out.println("subgroup:                  " + group.getId());
		            	//System.out.println("subgroup failed:           " + group.getFailedAsString());
		        		
		        		ArrayList <RuleExecutionResult> results = subgroup.getExecutionCollection().getResults();
		        		for (int h= 0;h< results.size();h++)
		                {
		        			RuleExecutionResult result = results.get(h);
		        			XmlRule rule = result.getRule();
		        			
		        			System.out.println("rule    :                  " + rule.getRuleId());
		                	System.out.println("rule failed:               " + result.getFailedAsString());
		                	System.out.println("rule message:              " + result.getMessage());
		                }
		            }
		        }
		        		        
		        counter++;
	        }
	    }
        // close the reader
        reader.close();

        // get all execution results at the end of the process
        //RuleExecutionCollection r = engine.getRuleExecutionCollection();
        //ArrayList<RuleExecutionResult> re = r.getResults();
        
        System.out.println("number of lines of data:   " + counter);
        
        // total number of rules
        long numberOfRules = engine.getNumberOfRules();
        // total number of failed rules
        long numberOfErrors = engine.getNumberOfRulesFailed();
        // total number of successful run rules
        long numbersOfSuccessfulRules = numberOfRules * counter - numberOfErrors;
        // total number of failed groups
        long numberOfFailedGroups = engine.getNumberOfGroupsFailed();
        
        System.out.println("total number of rules:     " + (numbersOfSuccessfulRules +numberOfErrors) );
        System.out.println("number of rules passed:    " + numbersOfSuccessfulRules);
        System.out.println("number of rules failed:    " + numberOfErrors);
        System.out.println("number of groups failed:   " + numberOfFailedGroups);

	    // calculate elapsed time in seconds
        Calendar end = Calendar.getInstance();
        long elapsed = end.getTimeInMillis() - start.getTimeInMillis();
        long elapsedSeconds = (elapsed/1000);

	    System.out.println("end:                       " + end.getTime());
	    System.out.println("elapsed time:              " + elapsedSeconds + " second(s)");
	    System.out.println("end of process.");
    }
    
    public static void help()
    {
    	System.out.println("program to process a CSV file row by row against a given set of business rules.");
    	System.out.println("the business rules zip file can be created with the Business Rules Maintenance Tool.");
    	System.out.println();
    	System.out.println("pass the path and name of the data file as the first argument.");
    	System.out.println("pass the path and name of the project zip file containing all rules as the second argument.");
    	System.out.println();
    	System.out.println("example: CsvReader /somefolder/mycsvfile.csv /otherfolder/testrules.zip");
    	System.out.println();
    }
    
}
