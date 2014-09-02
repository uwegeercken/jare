/*
 * Created on 27.12.2006
 *
 * all code by uwe geercken
 */
package com.datamelt.rules.reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.util.Calendar;

import com.datamelt.rules.core.RuleGroup;
import com.datamelt.rules.engine.BusinessRulesEngine;
import com.datamelt.util.Row;
import com.datamelt.util.Splitter;

/**
 * TextFileReader class is used to run the business rule engine
 * against data from a fixed length ASCII file. This is a file
 * where all lines have the same length. Fields are filled with
 * blanks up to their maximum length.
 * 
 * Example:
 * Hamburg         Europe            3.750000 german 
 * Denver          United States     6.000000 english
 * Stockholm       Sweden            2.578300 swedish
 * 
 * The structure of each line of the fixed length text file is
 * defined in a xml file indicating the start of each field and
 * its length.
 * 
 * Data from the file is read, Row objects are constructed and
 * are run against the rule engine using rules as defined in a rule
 * xml file.
 * 
 * @author uwe geercken
 */
public class TextFileReader
{
    public static final String XML_FILE_EXTENSION  			= ".xml";

    public static void main(String[] args) throws Exception
    {
    	// capture start time
    	Calendar start = Calendar.getInstance();
	    System.out.println("start:                     " + start.getTime());

	    // reader for the data file
	    BufferedReader reader = new BufferedReader(new FileReader(args[0]));
	    String line;
	    // counts number of lines in data file
	    long counter=0;
	    
	    // select all xml files in the given folder containing rule definitions
	    // but only those that end in .xml
        File dir = new File(args[1]);
        File[] fileList = dir.listFiles(new FilenameFilter() {
            public boolean accept(File f, String s) {return s.endsWith(XML_FILE_EXTENSION);}
        	});
        
        System.out.println("parsing xml rule files...");
        // create an engine object, passing a reference to the properties file
        BusinessRulesEngine engine = new BusinessRulesEngine(fileList, args[2]);
        //BusinessRulesEngine engine = new BusinessRulesEngine(fileList);

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
        
        // splitter object will split the row from the datafile into
        // its fields using the definition found in the corresponding
        // xml row definition file
        Splitter splitter = new Splitter(Splitter.TYPE_FIXED_LENGTH);
        //set the row definition file for the fixed length ascii file
    	splitter.setRowDefinitionFile(args[3]);
    	//we want to trim whitespace from the fields
    	splitter.setTrimFields(true);

        while ((line=reader.readLine())!=null)
	    {
        	// only if the line is not empty 
        	// and does not start with a hash sign (comment).
        	// otherwise the line will be NOT be processed nor counted!
        	if(!line.trim().equals("") && !line.startsWith("#"))
	        {
        		line = line.replace("\"","");
        		// get a row object containing the fields and data
        		Row row = splitter.getRow(line);
            
		        // run rules on this data
		        engine.run("row: " + counter, row);
		        counter++;
	        }
	    }
        
        reader.close();
        System.out.println("number of lines of data:   " + counter);
        
        // total number of rules
        int numberOfRules = engine.getNumberOfRules();
        // total number of failed rules
        int numberOfErrors = engine.getNumberOfRulesFailed();
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
}
