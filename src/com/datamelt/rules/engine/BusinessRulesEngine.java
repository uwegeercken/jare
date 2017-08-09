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

package com.datamelt.rules.engine;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import com.datamelt.rules.parser.xml.Parser;
import com.datamelt.rules.core.RuleExecutionCollection;
import com.datamelt.rules.core.RuleGroup;
import com.datamelt.rules.core.util.MappingCollection;
import com.datamelt.rules.core.util.VariableReplacer;
import com.datamelt.util.FileUtility;
import com.datamelt.util.RowFieldCollection;
import com.datamelt.util.RuleGroupPrioritizer;
import com.datamelt.util.Splitter;


/**
 * engine for executing business rules.
 * <p>
 * rules are defined in xml files. additionally a replacements (properties) file might
 * be specified, where variable names and replacement values for them are
 * specified. this way values, that are used repeatedly in an xml file
 * might be defined once and used multiple times.
 * <p>
 * the engine executes rules which are defined in the form of groups and
 * subgroups. rules in a subgroup belong together and are chained using
 * either 'and' or 'or'. additionally subgroups may be chained together
 * 'and' or 'or'.
 * <p>
 * the engine accepts multiple xml rule files as input. this way the rules may
 * be divided into multiple files. each file must contain a complete group of
 * rules; groups can not be split over multiple files.
 * <p>
 * rule xml files can also be put into a zip archive. the program will process
 * the zip file and read all rule xml files from it.
 * <p>
 * the rules are executed against the data using a certain check or condition.
 * these are defined in classes implementing the GenericCheck interface.
 * <p>
 * rules defined in the xml file(s) are instantiated using java reflection.
 * usually the return value of a method is compared to an expected value. or
 * two object are compared against each other. or the return value is compared
 * against a fix condition such as 'not null'. 
 * <p>
 * the getRuleDefinition method returns a simple readable representation and overview
 * of the rule logic.
 * <p>
 * please read the available documentation.</p>
 * 
 * @author uwe geercken - uwe.geercken@web.de - Copyright 2008-2017
 * 
 */
public class BusinessRulesEngine
{
	// the version of the business rule engine
	private static final String VERSION = "0.82";
	private static final String REVISION = "6";
	private static final String LAST_UPDATE = "2017-08-09";
	
    // contains all groups, subgroups and rules that have been parsed from one or more files
    private ArrayList<RuleGroup> groups = new ArrayList<RuleGroup>();
    // indicator if the rule engine ran
    private int status;
    
    // used to replace variables in xml rule files by actual values from a file
    private VariableReplacer replacer = null;

    // this label will be used - together with a running number - for all objects (that are to be tested)
    // to identify them in the output
    private String objectsLabel = OBJECT_LABEL_DEFAULT;
    
    // used for formatting the running number for the objectlabel during output
    private String objectsLabelNumberFormat = OBJECT_LABEL_NUMBERFORMAT_DEFAULT;
    
    // type of output that is written
    private int outputType = OUTPUT_TYPE_FAILED_ONLY;
    
    // used for putting a timestamp in the output file name
    private String timestampFormat = TIMESTAMP_FORMAT_DEFAULT;
    
    private static final String TIMESTAMP_FORMAT_DEFAULT          = "yyyy-MM-dd hh:mm:ss";
    
    // will be used for labeling in output files
    private static final String OBJECT_LABEL_DEFAULT              = "object"; 
    private static final String OBJECT_LABEL_NUMBERFORMAT_DEFAULT = "0000000000"; 
    
    // default is failed rules only
    public static final int OUTPUT_TYPE_FAILED_ONLY               = 0;
    public static final int OUTPUT_TYPE_PASSED_ONLY               = 1;
    public static final int OUTPUT_TYPE_FAILED_AND_PASSED         = 2;
    public static final int OUTPUT_TYPE_NO_OUTPUT		          = 3;
    
    // status of the engine
    private static final int STATUS_ENGINE_EXECUTED               = 1;
    
    private static final String PROPERTY_REPLACEMENTS_FILE			 = "replacements_file";
    private static final String PROPERTY_OUPUT_TYPE 				 = "output_type";
    private static final String PROPERTY_OBJECT_LABEL 				 = "object_label";
    private static final String PROPERTY_OBJECT_LABEL_FORMAT		 = "object_label_format";

    // name of the replacements file
    private String replacementsFile;
    // the collection of mappings. contains key/values from multiple mapping files
    private MappingCollection mappingCollection = new MappingCollection();
    // contains the results of the execution of the rules
    private RuleExecutionCollection executionCollection = new RuleExecutionCollection();
    // indicated if the results of the rule execution should be kept
    private boolean preserveRuleExcecutionResults=true;

    /** 
     * returns the version and revision of the business rule engine
     * 
     * @return		the version and revision of the engine
     */
    public static String getVersion()
    {
    	return VERSION + "-R" + REVISION;
    }
    
    /** 
     * returns the last date of an update of the business rule engine
     * 
     * @return		the last updated date
     */
    public static String getLastUpdateDate()
    {
    	return LAST_UPDATE;
    }
    
    /**
     * engine can be instantiated by passing the filename
     * the file will be parsed and all rules
     * will be collected
     * 
     * Rulegroups are prioritized to execute those groups first, that
     * other groups depend on.
     * 
     * @param		rulesFilename	path and name of the xml rule file
     * @exception	Exception		exception when the file(s) could not be located or parsed
     */
    public BusinessRulesEngine(String rulesFilename) throws Exception
    {
        parseXmlFile(rulesFilename);
        prioritizeRuleGroups();
    }

    /**
     * engine can be instantiated by passing the filename
     * the file will be parsed and all rules will be collected
     * the properties file is an external file that is used
     * to define several parameters used by the engine
     * 
     * Rulegroups are prioritized to execute those groups first, that
     * other groups depend on.
     * 
     * @param		rulesFilename	the path and name of the XML rule file
     * @param		propertiesFile	the path and name of the properties file
     * @exception	Exception		exception when the file were not found or could not be processed
     */
    public BusinessRulesEngine(String rulesFilename,String propertiesFile) throws Exception
    {
        if(propertiesFile!=null)
        {
            loadProperties(propertiesFile);
        }
        parseXmlFile(rulesFilename);
        prioritizeRuleGroups();
    }
    
    /**
     * engine can be instantiated by passing an array of files
     * the files will be parsed and all rules from all files
     * will be collected.
     * 
     * Rulegroups are prioritized to execute those groups first, that
     * other groups depend on.
     * 
     * @param		rulesFiles		an array of rule xml files
     * @exception	Exception		exception when the file(s) could not be located or parsed
     */
    public BusinessRulesEngine(File[] rulesFiles) throws Exception
    {
        for(int i=0;i<rulesFiles.length;i++)
        {
            parseXmlFile(rulesFiles[i].getPath());
        }
        prioritizeRuleGroups();
    }
    
    /**
     * engine can be instantiated by passing a zip file containing xml
     * rule files.
     * the files will be parsed and all rules from all files
     * will be collected
     * 
     * Rulegroups are prioritized to execute those groups first, that
     * other groups depend on.
     * 
     * @param		zipFile			path and name of the zip file
     * @exception	Exception		exception when the file could not be located or parsed
     */
    public BusinessRulesEngine(ZipFile zipFile) throws Exception
    {
        for(Enumeration<?> entries = zipFile.entries();entries.hasMoreElements();)
        {
            ZipEntry entry = (ZipEntry)entries.nextElement();
            if(!entry.isDirectory())
            {
            	parseXmlInputStream(zipFile.getInputStream(entry));
            }
        }
        zipFile.close();
        prioritizeRuleGroups();
    }
    
    /**
     * engine can be instantiated by passing a zip file containing xml
     * rule files.
     * the files will be parsed and all rules from all files
     * will be collected
     * 
     * Rulegroups are prioritized to execute those groups first, that
     * other groups depend on.
     * 
     * @param		zipFile			path and name of the zip file
     * @param		propertiesFile	path and name of the properties file
     * @exception	Exception		exception when the file could not be located or parsed
     */
    public BusinessRulesEngine(ZipFile zipFile, String propertiesFile) throws Exception
    {
    	if(propertiesFile!=null)
        {
            loadProperties(propertiesFile);
        }
        for(Enumeration<?> entries = zipFile.entries();entries.hasMoreElements();)
        {
            ZipEntry entry = (ZipEntry)entries.nextElement();
            if(!entry.isDirectory())
            {
            	parseXmlInputStream(zipFile.getInputStream(entry));
            }
        }
        zipFile.close();
        prioritizeRuleGroups();
    }
    
    /**
     * engine can be instantiated by passing a zip file containing xml
     * rule files and specifying an array of the names of the rule files to process.
     * all other rule files will be ignored.
     * 
     * the files will be parsed and all rules from all relevant files
     * will be collected
	 *
	 * Rulegroups are prioritized to execute those groups first, that
     * other groups depend on.
     * 
     * @param		zipFile			path and name of the zip file
     * @param		ruleFiles		array containing the rulefile names to process
     * @exception	Exception		exception when the file could not be located or parsed
     */
    public BusinessRulesEngine(ZipFile zipFile, String[] ruleFiles) throws Exception
    {
        for(Enumeration<?> entries = zipFile.entries();entries.hasMoreElements();)
        {
            ZipEntry entry = (ZipEntry)entries.nextElement();
            if(!entry.isDirectory())
            {
            	for(int i=0;i<ruleFiles.length;i++)
            	{
            		if(ruleFiles[i].equals(entry.getName()))
            		{
            			parseXmlInputStream(zipFile.getInputStream(entry));
            		}
            	}
            }
        }
        zipFile.close();
        prioritizeRuleGroups();
    }
    
    /**
     * engine can be instantiated by passing an array of files
     * the files will be parsed and all rules from all files
     * will be collected
     * 
     * the properties file is an external file that is used
     * to define several parameters used by the engine
     * 
     * Rulegroups are prioritized to execute those groups first, that
     * other groups depend on.
     * 
     * @param		rulesFiles		an array of rule xml files
     * @param		propertiesFile	path and name of the properties file
     * @exception	Exception		exception when the file(s) could not be located or parsed
     */
    public BusinessRulesEngine(File[] rulesFiles, String propertiesFile) throws Exception
    {
        if(propertiesFile!=null)
        {
            loadProperties(propertiesFile);
        }
        for(int i=0;i<rulesFiles.length;i++)
        {
            parseXmlFile(rulesFiles[i].getPath());
        }
        prioritizeRuleGroups();
    }
    
    /**
     * method runs the rules for all groups and subgroups
     * against the object with the given label
     * 
     * @param		objectLabel		the label to use for the object
     * @param		object			the actual object to use
     * @throws		Exception		exception running the rule against the object
     */
    public void run(String objectLabel, Object object)throws Exception
    {
        status = STATUS_ENGINE_EXECUTED;
        
        for(int i=0;i<groups.size();i++)
        {
        	// get the next group
            RuleGroup group = groups.get(i);

            run(group,objectLabel,object);            
        }
    }
    
    /**
     * method runs the rules for a given rule group by specifying the group id.
     * against the object with the given label.
     * 
     * Only the given rulegroup will be executed. Note that if the given rulegroup depends
     * on another group result (passed/failed), then that rulegroup shall be execute first. 
     * 
     * The compare of the rulegroup name is not case sensitive.
     * 
     * @param		rulegroupName	the name of the rulegroup to run
     * @param		objectLabel		the label to use for the object
     * @param		object			the actual object to use
     * @throws		Exception		exception running the rule against the object
     */
    public void run(String rulegroupName, String objectLabel, Object object)throws Exception
    {
        status = STATUS_ENGINE_EXECUTED;
        
        for(int i=0;i<groups.size();i++)
        {
        	// get the next group
            RuleGroup group = groups.get(i);
            
            // check if the name of the group corresponds to the specified name
            if(group.getId().toLowerCase().equals(rulegroupName.toLowerCase()))
            {
            	run(group,objectLabel,object);
            }
        }
    }
    
    /**
     * method runs the rules for a given array of rule groups by specifying the group names.
     * against the object with the given label.
     * 
     * The rulegroups will be executed in the order that they appear in the project zip file, but
     * in any case those groups other groups depend on are executed first.
     * 
     * Only the given rulegroup will be executed. Note that if the given rulegroup depends
     * on another group result (passed/failed), then that rulegroup shall be execute first. 
     * 
     * The compare of the rulegroup name is not case sensitive.
     * 
     * @param		rulegroupNames	array of names of the rulegroups to run
     * @param		objectLabel		the label to use for the object
     * @param		object			the actual object to use
     * @throws		Exception		exception running the rule against the object
     */
    public void run(String[] rulegroupNames, String objectLabel, Object object)throws Exception
    {
        status = STATUS_ENGINE_EXECUTED;
        
        for(int i=0;i<groups.size();i++)
        {
        	// get the next group
            RuleGroup group = groups.get(i);
            
            // loop over the array of rule groups
            for(int j=0;j<rulegroupNames.length;j++)
            {
	            String rulegroupName = rulegroupNames[j];
	            // check if the name of the group corresponds to the specified name
	            if(group.getId().toLowerCase().equals(rulegroupName.toLowerCase()))
	            {
	            	run(group,objectLabel,object);
	            }
            }
        }
    }

    /**
     * method runs the rules for a given rule group against the object with the given label.
     * 
     * if you run a single rule group and it is depending on another rule group
     * make sure that the rule group it depends on is run first - a check on the result
     * of the group it depends on (passed/failed) will be made in this method to determine if the
     * rule group should run.
     * 
     * @param		group			the rule group to run
     * @param		objectLabel		the label to use for the object
     * @param		object			the actual object to use
     * @throws		Exception		exception running the rule against the object
     */
    public void run(RuleGroup group, String objectLabel, Object object)throws Exception
    {
        // we reset the skipped flag of the group here
    	// a group may be skipped if it depends on another rulegroup
    	// and that groups execution result is not as expected
        group.setSkipped(0);
        
        // set the collection of maps containing key/value pairs
        group.setMappingCollection(mappingCollection);
        
        // per default each rulegroup will be run
        boolean runGroup = true;
        // check if we have a dependent rulegroup
        if(group.getDependentRuleGroupId()!=null && !group.getDependentRuleGroupId().equals(""))
        {
        	// get the dependent group from the list of groups
        	RuleGroup dependentRuleGroup = getGroupById(group.getDependentRuleGroupId());
        	// don't run the group if the dependent group does not exist or does not have the correct status (passed/failed)
        	if(dependentRuleGroup!=null && dependentRuleGroup.getFailed()!=group.getDependentRuleGroupExecuteIf())
        	{
        		runGroup= false;
        		group.setSkipped(1);
        		// increase the counter for the skipped rule groups
        		executionCollection.increaseSkippedGroupCount();
        	}
        }
        if(runGroup)
        {
            group.runRules(objectLabel, object);
            if(group.getFailed()==1) // group failed
            {
            	// increase the counter of failed groups
            	executionCollection.increaseFailedGroupCount();
            }
            else
            {
            	// increase the counter of failed groups
            	executionCollection.increasePassedGroupCount();
            	
            }
            // execution results will be added unless the preserveRuleExcecutionResults is set to false
            executionCollection.addAll(group.getExecutionCollection().getResults());
            // add the number of executed actions by the rulegroup
            executionCollection.addNumberOfActionsExecuted(group.getNumberOfActionsExecuted());
            executionCollection.addNumberOfRulesRun(group.getNumberOfRulesRun());
            executionCollection.addNumberOfRulesFailed(group.getNumberOfRulesFailed());
            executionCollection.addNumberOfRulesPassed(group.getNumberOfRulesPassed());
        }
    }
    
    /**
     * method runs the rules for all groups and subgroups
     * for all objects of the collection.
     * objectLabel is used to label the result of a rule in the output
     * so that it can be identified.
     * 
     * @param		objects			a collection of objects to run the rule against
     * @throws		Exception		exception running the rule against the object
     */
    public void run(Collection<Object> objects) throws Exception
    {
        status = STATUS_ENGINE_EXECUTED;
        DecimalFormat df = new DecimalFormat(objectsLabelNumberFormat);
        int i=0;
        for(Iterator<Object> iterator = objects.iterator(); iterator.hasNext(); )
        {
            Object object = iterator.next();
	        String label = objectsLabel;
	        if(label !=null && !label.trim().equals(""))
	        {
	            label = label + " [" + df.format(i) + "]";
	        }
            run(label, object);
            i++;
        }
    }
    
    /**
     * method runs the rules for all groups and subgroups.
     * the csv file is parsed, split into rows and fields
     * using the defined field separator.
     * 
     * @param		csvfileName		the CSV file to use
     * @param		fieldSeperator	the separator between the fields/columns in the csv file
     * @throws		Exception		exception running the rule against the object
     */
    public void run(String csvfileName,String fieldSeperator) throws Exception
    {
    	status = STATUS_ENGINE_EXECUTED;
    	// reader for the data file
	    BufferedReader reader = new BufferedReader(new FileReader(csvfileName));
    	String line;
    	
    	// counts number of lines in data file
	    long counter=0;
    	
	    // splitter object will split the row from the datafile into
        // its fields using the default semicolon (;) separator
        Splitter splitter = new Splitter(Splitter.TYPE_COMMA_SEPERATED, fieldSeperator);

    	while ((line=reader.readLine())!=null)
	    {
        	// only if the line is not empty 
        	// and does not start with a hash sign (comment).
        	// otherwise the line will be NOT be processed nor counted!
	        if(!line.trim().equals("") && !line.startsWith("#"))
	        {
	        	line = line.replace("\"","");
		        // get a row object containing the fields and data
	        	RowFieldCollection row = splitter.getRowFieldCollection(line); 
		        // run rules on this data
		        run("row: " + counter, row);
		        counter++;
	        }
	    }	
    	reader.close();
    }
    
    /**
     *  the prioritizer will make sure that all rulegroups that other rulegroups
     *  depend on will be executed first.
     *	   
     */
    private void prioritizeRuleGroups()
    {
        RuleGroupPrioritizer prioritizer = new RuleGroupPrioritizer(groups);
        groups = prioritizer.getPrioritizedList();
    }
    
    /**
     * method runs the rules for all groups and subgroups
     * against the object.
     * a default label for the objects will be used.
     * 
     * @param		object		the object to run the rule against
     * @exception	Exception	exception when the rule could not be run
     */
    public void run(Object object)throws Exception
    {
        status = STATUS_ENGINE_EXECUTED;
        DecimalFormat df = new DecimalFormat(objectsLabelNumberFormat);
        String label = objectsLabel;
        if(label !=null && !label.trim().equals(""))
        {
            label = label + " [" + df.format(0) + "]";
        }
        run(label, object);
    }
    
    /**
     * applies settings to the rulegroup
     */
    private void applyGroupSettings()
    {
    	for(int i=0;i<groups.size();i++)
    	{
    		RuleGroup group = groups.get(i);
    		group.setTimestampFormat(timestampFormat);
            group.setOutputType(outputType);
            group.setPreserveRuleExcecutionResults(preserveRuleExcecutionResults);
    	}
    }
    
    /**
     * method is used to parse the given xml file
     * 
     * @param		filename	path and name of the xml file
     * @exception	Exception	exception when the xml file could not be parsed
     */
    private void parseXmlFile(String filename)throws Exception
    {
        // create a parser object to parse
        // the xml file
        
        Parser parser = new Parser(replacer);
        parser.parse(filename); 
        
        // get the created xmlrule arraylist from the parser
        // and add them to the list
        groups.addAll(parser.getGroups());
        applyGroupSettings();
    }
    
    /**
     * method is used to parse the given xml input stream
     * 
     * @param		stream		the input stream to parse from
     * @exception	Exception	exception when the inputstream could not be parsed
     */
    private void parseXmlInputStream(InputStream stream)throws Exception
    {
        // create a parser object to parse
        // the xml input stream
        
        Parser parser = new Parser(replacer);
        parser.parse(stream); 
        
        groups.addAll(parser.getGroups());
        applyGroupSettings();
    }
    
    /**
     * method returns a collection of results from all groups and subgroups
     * 
     * @return	collection of rule execution results
     */
    public RuleExecutionCollection getRuleExecutionCollection()
    {
        return executionCollection;
    }
    
    /**
     * method returns a boolean indicating if the rule group failed.
     * in case a group with the given id is not found returns false.
     * 
     * you can evaluate if a group failed or not after the business
     * rule engine was run.
     * 
     * @param	groupId		the id of the group
     * @return				indicator if the rule group failed
     */
    public boolean getRuleGroupFailed(String groupId)
    {
    	boolean failed = false;
    	for(int i=0;i<groups.size();i++)
    	{
    		RuleGroup group = groups.get(i);
    		if(group.getId().toLowerCase().equals(groupId.toLowerCase()))
    		{
    			if(group.getFailed()==1)
    			{
    				failed = true;
    			}
    			else
    			{
    				failed = false;
    			}
    			break;
    		}
    	}
    	return failed;
    }
    
    /**
     * checks if any - at least one - of the groups in an array of rule group ids failed. 
     * 
     * a check of each group of the array is done, if it failed. if the group does not exist then
     * the result for that group will be returned as false.
     * 
     * you can evaluate if a group failed or not after the business
     * rule engine was run.

     * @param	 groupIds	an array of group ids
     * @return				indicator if any of the groups failed
     */
    public boolean getRuleGroupsFailed(String[] groupIds)
    {
    	boolean failed = false;
    	for(int i=0;i<groupIds.length;i++)
    	{
    		boolean groupFailed = getRuleGroupFailed(groupIds[i]);
    		
    		if(groupFailed)
    		{
    			failed = true;
    		}
    	}
    	return failed;
    }
    
    /**
     * method returns a boolean indicating if the rule group exists.
     * 
     * @param	groupId		the id of the group
     * @return				indicator if the rule group exists
     */
    public boolean getRuleGroupExists(String groupId)
    {
    	boolean exists = false;
    	for(int i=0;i<groups.size();i++)
    	{
    		RuleGroup group = groups.get(i);
    		if(group.getId().toLowerCase().equals(groupId.toLowerCase()))
    		{
   				exists = true;
    			break;
    		}
    	}
    	return exists;
    }

    /**
     * indicator if the execution results should be preserved or not
     * 
     * @return indicator if results are preserved
     */
    public boolean getPreserveRuleExcecutionResults()
    {
    	return executionCollection.getPreserveRuleExcecutionResults();
    }
    
    /**
     * indicator if the execution results should be preserved or not
     * 
     * @param	preserveRuleExcecutionResults indicator if results shall be preserved
     */
    public void setPreserveRuleExcecutionResults(boolean preserveRuleExcecutionResults)
    {
    	this.preserveRuleExcecutionResults = preserveRuleExcecutionResults;
    	executionCollection.setPreserveRuleExcecutionResults(preserveRuleExcecutionResults);
    }
    
    /**
     * method returns the list of groups as defined in the xml file
     * 
     * @return	list of rulegroups
     */
    public ArrayList<RuleGroup> getGroups()
    {
        return groups;
    }
    
    /**
     * method returns the a group identified by it's id
     * as defined in the xml file
     * 
     * @param	groupId	the id of the group
     * @return			the rulegroup
     */
    public RuleGroup getGroupById(String groupId)
    {
    	int found=-1;
    	for(int i=0;i< groups.size();i++)
    	{
    		RuleGroup group = groups.get(i);
    		if(group.getId().equals(groupId))
    		{
    			found=i;
    			break;
    		}
    	}
    	if(found > -1)
    	{
    		return groups.get(found);
    	}
    	else
    	{
    		return null;
    	}
    }
    
    /**
     * method returns the number of rules from all groups and subgroups
     * 
     * @return	number of rules
     */
    public long getNumberOfRules()
    {
        int count = 0;
        for(int i=0;i<groups.size();i++)
        {
            RuleGroup group = (RuleGroup)groups.get(i);
            count = count + group.getNumberOfRules();
        }
        return count;
    }
    
    /**
     * method returns the number of actions from all groups
     * 
     * @return	number of actions
     */
    public long getNumberOfActions()
    {
        int count = 0;
        for(int i=0;i<groups.size();i++)
        {
            RuleGroup group = (RuleGroup)groups.get(i);
            count = count + group.getNumberOfActions();
        }
        return count;
    }
    
    public long getNumberOfActionsExecuted()
    {
    	return executionCollection.getActionsExecutedCount();
    }
    
    /**
     * method returns the number of failed rules from all groups and subgroups
     * 
     * @return number of failed rules
     */
    public long getNumberOfRulesFailed()
    {
        return executionCollection.getRulesFailedCount();
    }
    
    /**
     * method returns the number of passed rules from all groups and subgroups
     * 
     * @return number of passed rules
     */
    public long getNumberOfRulesPassed()
    {
        return executionCollection.getRulesPassedCount();
    }
    
    /**
     * method returns the number of groups that failed
     * 
     * @return					number of groups failed
     */
    public long getNumberOfGroupsFailed()
    {
    	return executionCollection.getFailedGroupsCount();
    }
    
    /**
     * method returns the number of groups that failed
     * 
     * @return 					number of passed groups
     */
    public long getNumberOfGroupsPassed() 
    {
    	return executionCollection.getPassedGroupsCount();
    }

    /**
     * method returns the number of groups that were skipped.
     * 
     * groups may be skipped when they depend on another group and the execution
     * result (failed/passed) of that group is different than the expected result.
     * 
     * @return 					number of skipped groups
     */
    public long getNumberOfGroupsSkipped()
    {
    	return executionCollection.getSkippedGroupsCount();
    }

    /**
     * method returns the total number of groups
     * 
     * @return 					number of groups
     */
    public long getNumberOfGroups()
    {
    	return groups.size();
    }
    
    /**
     * method returns the total number of executed groups - excluding the skipped groups
     * 
     * @return 					number of executed groups
     */
    public long getNumberOfExecutedGroups()
    {
    	return groups.size() - getNumberOfGroupsSkipped();
    }
    
    /**
     * returns if the method: 'run' has been invoked or not,
     * meaning if the ruleengine ran or not.
     * 
     * @return the status of the rule engine
     */
    public boolean getStatus()
    {
        return status==STATUS_ENGINE_EXECUTED;
    }
    
    /**
     * returns a string representing the rule logic for the specified group
     * 
     * the subgroup(s) will be parsed and the logic will be chained together
     * using brackets for an easier overview.
     * 
     * @param		groupId			the id of the group
     * @return 						the rule logic in a textual form
     * @exception	Exception		exception when the logic could not be constructed
     */
    public String getRuleLogic(String groupId) throws Exception
    {
        int foundGroupIndex = -1;
        for(int i=0;i<groups.size();i++)
        {
            RuleGroup group = (RuleGroup)groups.get(i);
            if(group.getId().equals(groupId))
            {
                foundGroupIndex = i;
                break;
            }
        }
        if(foundGroupIndex>-1)
        {
            return getRuleLogic(foundGroupIndex);
        }
        else
        {
            return null;
        }
    }
    
    /**
     * returns a string representing the rule logic for the specified group
     * 
     * the subgroup(s) will be parsed and the logic will be chained together
     * using brackets for an easier overview.
     * 
     * @param		index		the index of the rulegroup
     * @return					the rule logic in a textual expression
     * @exception	Exception	exception when the logic could not be constructed
     */
    public String getRuleLogic(int index)throws Exception
    {
        if(index>-1 && groups.get(index)!=null)
        {
       		RuleGroup group = (RuleGroup)groups.get(index);
       		return group.getRuleLogic();
        }
        else
        {
        	return null;
        }
    }
    
    /**
     * returns the replacer object. the replacer is used
     * to replace placeholders in the xml files with actual
     * values.
     * 
     * @return		replacer object
     */
    public VariableReplacer getReplacer()
    {
        return replacer;
    }
    
    /**
     * sets the replacer object, which is used to replace variables specified
     * in the xml files with real values as specified in an external
     * properties file.
     * 
     * @param	replacer	a variable replacer object
     */
    public void setReplacer(VariableReplacer replacer)
    {
        this.replacer = replacer;
    }
    
    /**
    * this label will be used - together with a running number - for all objects (that are to be tested)
    * to identify them in the output
    * 
    * @return	the label used for the objects
     */
    public String getObjectsLabel()
    {
        return objectsLabel;
    }
    
    /**
     * set the label to a different one, if you want to modify, how the object
     * is identified during ourput.
     * 
     * @param	objectsLabel	the label for the objects
     */
    public void setObjectsLabel(String objectsLabel)
    {
        this.objectsLabel = objectsLabel;
    }
    
    /**
     * the objectlabel that is used to identify an object in output additionally has a running
     * number.
     * the format of this number is returned here.
     * 
     * @return		number format of the object label
     */
    public String getObjectLabelNumberFormat()
    {
        return objectsLabelNumberFormat;
    }
    
    /**
     * the objectlabel that is used to identify an object in output additionally has a running
     * number.
     * specifiy a different format here that will be used during output.
     * 
     * @param	format	number format of the object label
     */
    public void setObjectLabelNumberFormat(String format)
    {
        this.objectsLabelNumberFormat = format;
    }
    
    /**
     * specifies if all rule results should be output (2), only for failed rules (0),
     * only for passed rules (1). default is 0.
     * 
     * @return		the selected output type
     */
    public int getOutputType()
    {
        return outputType;
    }
    
    /**
     * sets if all rule results should be output (2), only for failed rules (0),
     * only for passed rules (1). default is 0.
     * 
     * @param	outputType	the selected output type
     */
    public void setOutputType(int outputType)
    {
        this.outputType = outputType;
    }
    
    /**
     * returns the format of the timestamp used for timestamp formating
     * 
     * @return		format of the timestamp
     */
    public String getTimestampFormat()
    {
        return timestampFormat;
    }
    
    /**
     * sets the format of the timestamp used for timestamp formating.
     * follows the rules of the java.text.SimpleDateFormat class
     * 
     * @param	timestampFormat	format of the timestamp
     */
    public void setTimestampFormat(String timestampFormat)
    {
        this.timestampFormat = timestampFormat;
    }
    
    /**
     * load the properties for the rule engine from the given filename
     * 
     * @param		filename		name of the properties file
     * @exception	Exception		exception when the file could not be loaded
     */
    private void loadProperties(String filename) throws Exception
	{
		
	    Properties props = new Properties();
		File f;
	    f = new File(filename);
		FileInputStream input = new FileInputStream(f);
		props.load(input);
		
	    replacementsFile = props.getProperty(PROPERTY_REPLACEMENTS_FILE);
	    
	    outputType = Integer.parseInt(props.getProperty(PROPERTY_OUPUT_TYPE));
	    
	    objectsLabel = props.getProperty(PROPERTY_OBJECT_LABEL);
	    objectsLabelNumberFormat = props.getProperty(PROPERTY_OBJECT_LABEL_FORMAT); 

	    if (replacementsFile!=null)
        {
            replacer = new VariableReplacer(replacementsFile);
        }
        
        input.close();
	}
    
	public static void main(String[] args) throws Exception
    {
        if (args.length==0 || args.length<2 || args.length>5)
        {
        	help();
        }
        else
        {
	    	// the name of the zipFile
	        String zipfileName=null;
	        
	        // the name of the folder where the xml rules are
	        String rulesFolder=null;
	        
	        // the name of the csv data file
	        String csvfileName=null;
	        
	        // the seperator to be used to devide
	        // the fields of a row of the csv file
	        String fieldSeperator=Splitter.SEPERATOR_SEMICOLON;
	        
	        // the name of the properties file
	        String propertiesfileName=null;
	        
	        // if there should be some output the user should
	        // specify the -v parameter as an argument to the program
	        boolean verbose=false;
	        
	    	for(int i=0;i<args.length;i++)
	    	{
	    		if (args[i].startsWith("-z="))
	    		{
	    			zipfileName = args[i].substring(3);
	    		}
	    		else if (args[i].startsWith("-c="))
	    		{
	    			csvfileName = args[i].substring(3);
	    		}
	    		else if (args[i].startsWith("-p="))
	    		{
	    			propertiesfileName = args[i].substring(3);
	    		}
	    		else if (args[i].startsWith("-r="))
	    		{
	    			rulesFolder = args[i].substring(3);
	    		}
	    		else if (args[i].startsWith("-s="))
	    		{
	    			fieldSeperator = args[i].substring(3);
	    		}
	    		else if(args[i].startsWith("-v"))
	    		{
	    			verbose=true;
	    		}
	    	}
	    	if(zipfileName==null && rulesFolder==null)
	    	{
	    		throw new Exception("either zipfile name or the rule folder must be specified");
	    	}
	    	if(csvfileName==null)
	    	{
	    		throw new Exception("csv file must be specified");
	    	}
	    	
	    	// capture start time
	    	Calendar start = Calendar.getInstance();
	    	if(verbose)
	    	{
	    		System.out.println("start of rules engine:     " + start.getTime());
    			System.out.println("using data file:           " + csvfileName);
	    	}
		    
	    	BusinessRulesEngine engine = null;
	    	if (zipfileName!=null)
	    	{
	    		if(verbose)
	    		{
	    			System.out.println("parsing rules from:        " + zipfileName);
	    		}
	    		if(propertiesfileName!=null)
	    		{
	    			engine= new BusinessRulesEngine(new ZipFile(zipfileName),propertiesfileName);
	    		}
	    		else
	    		{
	    			engine= new BusinessRulesEngine(new ZipFile(zipfileName));
	    		}
	    		engine.run(csvfileName, fieldSeperator);
	    	}
	    	else if(rulesFolder!=null)
	    	{
	    		if(verbose)
	    		{
	    			System.out.println("parsing rules from:        " + rulesFolder);
	    		}
	    		if(propertiesfileName!=null)
	    		{
	    			engine = new BusinessRulesEngine(FileUtility.getXmlFiles(rulesFolder),propertiesfileName);
	    		}
	    		else
	    		{
	    			engine = new BusinessRulesEngine(FileUtility.getXmlFiles(rulesFolder));
	    		}
	    		engine.run(csvfileName, fieldSeperator);
	    	}
	    	
	    	if(verbose)
	    	{
	            System.out.println("total number of rules:     " + engine.getNumberOfRules());
	            System.out.println("rules passed/failed:       " + engine.getNumberOfRulesPassed() + "/" + engine.getNumberOfRulesFailed());
	            System.out.println("groups failed:             " + engine.getNumberOfGroupsFailed());
	            
	            Calendar end = Calendar.getInstance();
	            long elapsed = end.getTimeInMillis() - start.getTimeInMillis();
	            long elapsedSeconds = (elapsed/1000);

	    	    System.out.println("elapsed time:              " + elapsedSeconds + " second(s)");
	    	    System.out.println("end of process.");
	    	    System.out.println();

	    	}
        }    	
    }
    
    public static void help()
    {
    	System.out.println("BusinessRulesEngine. program to process business rules defined in xml format");
    	System.out.println("against data from a file or a database.");
    	System.out.println("Provide either a zipfile which contains the business rules or specify a folder");
    	System.out.println("where the busness rules are located.");
    	System.out.println("A properties file may be specified, defining various parameters for the");
    	System.out.println("business rules engine - see documentation.");
    	System.out.println("Specify a CSV file containing rows of data with fields being devided from each");
    	System.out.println("other by the field seperator. If no field seperator is specified as argument the");
    	System.out.println("program uses a semicolon (;) as the seperator.");
    	System.out.println("");
    	System.out.println("In the com.datamelt.rules.reader package, there are sample classes defined for");
    	System.out.println("reading data from csv files, fix length ascii files or a database.");
    	System.out.println();
    	System.out.println("For further functionality consult the API documentation and the handbook.");
    	System.out.println();
    	System.out.println("BusinessRulesEngine -z=[zipfile name]|-r=[rules folder] -p=[properties file] -c=[csv file name] -s=[field seperator] -v");
    	System.out.println("where [zipfile name]   : optional. required if rules folder undefined. name of the zip file containing the business rules.");
    	System.out.println("      [rules folder]   : optional. required if zip file undefined. name of the folder containing the business rules.");
    	System.out.println("      [properties file]: optional. rule engine properties file");
    	System.out.println("      [csv file name]  : required. name of the csv file - containing the data - to use");
    	System.out.println("      [field seperator]: optional. field seperator used between the individual fields of the rows in the csv file");
    	System.out.println("      -v               : optional. verbose mode. if specified some output will be generated.");
    	System.out.println();
    	System.out.println("example: BusinessRulesEngine -z=rules_1.zip -p=engine.properties -c=datafile.csv -v");
    	System.out.println("         BusinessRulesEngine -z=rules_1.zip -p=engine.properties -c=datafile.csv -s=,");
    	System.out.println("         BusinessRulesEngine -r=/temp/rules -p=engine.properties -c=datafile.csv");
    	System.out.println("         BusinessRulesEngine -r=/temp/rules -p=engine.properties -c=datafile.csv -s=; -v");
    	System.out.println();
    	System.out.println("published as open source under the Apache License. read the licence notice");
    	System.out.println("all code by uwe geercken, 2006-2017. uwe.geercken@web.de");
    	System.out.println();
    }
    
	public String getObjectsLabelNumberFormat()
	{
		return objectsLabelNumberFormat;
	}

	public String getReplacementsFile()
	{
		return replacementsFile;
	}
	
	public MappingCollection getMappingCollection()
	{
		return mappingCollection;
	}

}
