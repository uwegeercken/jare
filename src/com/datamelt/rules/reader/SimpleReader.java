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

import java.util.zip.ZipFile;
import com.datamelt.rules.core.util.Converter;
import com.datamelt.rules.engine.BusinessRulesEngine;
import com.datamelt.util.RowFieldCollection;

/**
 * SimpleReader class is used to run the business rule engine
 * using a rule zip file against a simple set of data.
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
        //BusinessRulesEngine engine = new BusinessRulesEngine(new ZipFile(args[0]),args[1]);
        BusinessRulesEngine engine = new BusinessRulesEngine(new ZipFile(args[0]));
        
        // set the output of the engine to NO_OUTPUT
        engine.setOutputType(BusinessRulesEngine.OUTPUT_TYPE_FAILED_AND_PASSED);
        //engine.setPrintStream(new PrintStream(new FileOutputStream(new File("/home/uwe/temp/output.txt"))));
        
        // create a simple row object with some fields
    	String [] fields= {"Peter","Paulsen","Paris","Europa","6","1"};
    	//Row row = new Row(fields);

    	RowFieldCollection collection = new RowFieldCollection(fields);
    	
    	// run the engine to check the rules against the row object
    	//engine.run(args[0],args[1]);
        
    	engine.run(collection);
    	
    	System.out.println(engine.getRuleLogic(0));
    	
        // total number of rules
        long numberOfRules = engine.getNumberOfRules();
        // total number of failed rules
        long numberOfErrors = engine.getNumberOfRulesFailed();
        System.out.println("number of rules       :    " + numberOfRules);
        System.out.println("number of rules failed:    " + numberOfErrors);
        System.out.println("group failed: " + Converter.convertIntegerToBooleanString((int)engine.getNumberOfGroupsFailed()));
        System.out.println("end of process.");
    }
    
}
