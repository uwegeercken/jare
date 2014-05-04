/*
 * Created on 28.06.2008
 *
 * all code by uwe geercken
 */
package com.datamelt.rules.reader;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Calendar;

import com.datamelt.db.MySqlConnection;
import com.datamelt.rules.core.RuleGroup;
import com.datamelt.rules.engine.BusinessRulesEngine;
import java.sql.ResultSet;

/**
 * DatabaseReader class is used to run the business rule engine
 * against data from a MySql database.
 * 
 * Data from the database is read, Row objects are constructed and
 * are run against the rule engine using rules as defined in a rule
 * xml file.
 * 
 * @author uwe geercken
 */
public class DatabaseReader
{
    public static final String XML_FILE_EXTENSION = ".xml";
    
    public static void main(String[] args) throws Exception
    {
    	// capture start time
    	Calendar start = Calendar.getInstance();
	    System.out.println("start:                     " + start.getTime());

	    // counts number of records processed
	    long counter=0;
	    
	    // get the values for database access from the arguments
	    String hostname = args[0];
	    String databasename = args[1];
	    String userid = args[2];
	    String password = args[3];
	    
	    // get the sql string for retrieval of the data
	    String sql = args[4];
	    
	    // select all xml files in the given folder containing rule definitions
	    // but only those that end in .xml
        File dir = new File(args[5]);
        File[] fileList = dir.listFiles(new FilenameFilter() {
            public boolean accept(File f, String s) {return s.endsWith(XML_FILE_EXTENSION);}
        	});
        
        System.out.println("parsing xml rule files...");
        // create an engine object, passing a reference to the properties file
        BusinessRulesEngine engine = new BusinessRulesEngine(fileList, args[6]);
        //BusinessRulesEngine engine = new BusinessRulesEngine(fileList);

        // output the number of groups in total as parsed from the xml files
        System.out.println("number of groups:          " + engine.getGroups().size());
        // output the number of rules in total as parsed from the xml files
        System.out.println("number of rules:           " + engine.getNumberOfRules());
        // output the number of actions in total as parsed from the xml files
        System.out.println("number of actions:         " + engine.getNumberOfActions());
        
        // loop over all groups and output the definition/relation between
        // groups, subgroups and rules
        for(int i = 0;i<engine.getGroups().size();i++)
        {
            RuleGroup group = (RuleGroup)engine.getGroups().get(i);
            System.out.println("group logic:               " + group.getId() + ": "+ engine.getRuleLogic(i));
        }
        
        // create a connection object using the arguments passed to this program
        MySqlConnection connection = new MySqlConnection(hostname,databasename,userid,password);
        // the resultset will contain the reocrds
        ResultSet rs = connection.getResultSet(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE,sql);
        // loop over all records of the resultset
        while (rs.next())
	    {
		        // run rules on this recordset
		        engine.run("row: " + counter, rs);
		        counter++;
	    }
        
        System.out.println("number of lines of data:   " + counter);
        if(engine.getOutputFile()!=null)
        {
        	System.out.println("output to              :   " + engine.getOutputFile());
        }
        // total number of rules
        int numberOfRules = engine.getNumberOfRules();
        // total number of failed rules
        int numberOfErrors = engine.getNumberOfRulesFailed();
        // total number of successful run rules
        long numbersOfSuccessfulRules = numberOfRules * counter - numberOfErrors;
        // total number of failed groups
        long numberOfFailedGroups = engine.getNumberOfGroupsFailed();
        
        System.out.println("total number of rules:     " + (numbersOfSuccessfulRules + numberOfErrors) );
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
