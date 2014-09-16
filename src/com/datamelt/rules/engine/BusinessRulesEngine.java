/*
 * All intellectual property rights remain with Datamelt.com.
 * Created on 07.11.2006
 * all code by uwe geercken
 */
package com.datamelt.rules.engine;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.PrintStream;
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
import com.datamelt.rules.core.util.VariableReplacer;
import com.datamelt.util.FileUtility;
import com.datamelt.util.Row;
import com.datamelt.util.Splitter;
import com.datamelt.util.VelocityDataWriter;

/**
 * <p>engine for executing business rules.</p>
 * 
 * <p>rules are based on xml. additionally a replacements (properties) file might
 * be specified, where variable names and replacement values for them are
 * specified. this way values, that are used repeatedly in an xml file
 * might be defined once and used multiple times.</p>
 * 
 * <p>the engine executes rules which are defined in the form of groups and
 * subgroups. rules in a subgroup belong together and are chained using
 * either <and> or <or>. additionally subgroups may be chained together
 * <and> or <or>.</p>
 * 
 * <p>the engine accepts multiple xml rule files as input. this way the rules may
 * be divided into multiple files. each file must contain a complete group of
 * rules; groups can not be split over multiple files.<p>
 * 
 * <p>rule xml files can also be put into a zip archive. the program will process
 * the zip file and read all rule xml files from it.</p>
 * 
 * <p>the rules are executed against the data using a certain check or condition.
 * these are defined in classes implementing the GenericCheck interface.</p>
 * 
 * <p>rules defined in the xml file(s) are instantiated using java reflection.
 * usually the return value of a method is compared to an expected value. or
 * two object are compared against each other. or the return value is compared
 * against a fix condition such as "not null".</p> 
 * 
 * <p>the getRuleDefinition method returns a simple readable representation and overview
 * of the rule logic.</p>
 * 
 * <p>version 0.60 introduces actions that can be applied to a group of rules after
 * the checks of a rulegroup are done. an action can be run after a group passes or fails the relevant
 * checks.
 * </p>
 * 
 * <p>please read the provided documentation.</p>
 * 
 * <p>last update: 2014-08-14</p>
 * 
 * @author uwe geercken - uwe.geercken@web.de - www.datamelt.com
 * 
 * published under GPL3
 */
public class BusinessRulesEngine
{
	// the version of the business rule engine
	private static final String VERSION = "0.70";
	
    // contains all groups, subgroups and rules that have been parsed from one or more files
    private ArrayList<RuleGroup> groups = new ArrayList<RuleGroup>();
    private int status;
    
    // used to replace variables in xml rule files by actual values from a file
    private VariableReplacer replacer;
    // used to send the output somewhere
    private PrintStream stream;
    
 // used to send the output somewhere
    private PrintStream actionsStream;
    
    // this label will be used - together with a running number - for all objects (that are to be tested)
    // to identify them in the output
    private String objectsLabel = OBJECT_LABEL_DEFAULT;
    
    // used for formatting the running number for the objectlabel during output
    private String objectsLabelNumberFormat = OBJECT_LABEL_NUMBERFORMAT_DEFAULT;
    
    // type of output that is written
    private int outputType = OUTPUT_TYPE_FAILED_ONLY;
    
    // path and name of the output template.
    // template will be used to create output
    //private String templatePath;
    private String messageTemplateName;
    
    // velocity template writer
    private VelocityDataWriter writer;

    // used for putting a timestamp in the output file name
    private String timestampFormat = TIMESTAMP_FORMAT_DEFAULT;
    
    private static final String TIMESTAMP_FORMAT_DEFAULT          = "yyyy-MM-dd hh:mm:ss";
    
    // will be used for labeling in output files
    private static final String OBJECT_LABEL_DEFAULT              = "object"; 
    private static final String OBJECT_LABEL_NUMBERFORMAT_DEFAULT = "000000"; 
    
    // output all rule results (failed AND passed ones)?
    // default is failed rules only
    public static final int OUTPUT_TYPE_FAILED_ONLY               = 0;
    public static final int OUTPUT_TYPE_PASSED_ONLY               = 1;
    public static final int OUTPUT_TYPE_FAILED_AND_PASSED         = 2;
    public static final int OUTPUT_TYPE_NO_OUTPUT		          = 3;
    
    // status of the engine
    private static final int STATUS_ENGINE_EXECUTED               = 1;
    
    private static final String PROPERTY_REPLACEMENTS_FILE			 = "replacements_file";
    private static final String PROPERTY_TEMPLATES_FOLDER 			 = "templates_folder";
    private static final String PROPERTY_MESSAGES_TEMPLATE 			 = "messages_template";
    private static final String PROPERTY_OUPUT_FILE 				 = "output_file";
    private static final String PROPERTY_OUPUT_FILE_TIMESTAMP_FORMAT = "output_file_timestamp";
    private static final String PROPERTY_OUPUT_TYPE 				 = "output_type";
    private static final String PROPERTY_OBJECT_LABEL 				 = "object_label";
    private static final String PROPERTY_OBJECT_LABEL_FORMAT		 = "object_label_format";
    private static final String PROPERTY_RULELOGIC_TEMPLATE 	     = "rule_logic_template";
    private static final String PROPERTY_ACTIONS_OUPUT_FILE			 = "actions_output_file";
    // name of the replacements file
    private String replacementsFile;
    // name of the folder where the templates are stored
    private String templatesFolder;
    // name of the template for message output
    private String messagesTemplate;
    // name of the output file
    private String outputFile;
    // format of the outputfile timestamp
    private String outputFileTimestampFormat;
    // name of the template that contains the format of the rule logic
    private String ruleLogicTemplate;
    // name of the output file for actions
    private String actionsOutputFile;
    // format of the actions outputfile timestamp
    private String actionsOutputFileTimestampFormat;
    // contains the results of the execution of the rules
    private RuleExecutionCollection executionCollection = new RuleExecutionCollection();

    /** 
     * returns the version of the business rule engine
     */
    public String getVersion()
    {
    	return VERSION;
    }
    
    /**
     * engine can be instantiated by passing the filename
     * the file will be parsed and all rules
     * will be collected
     */
    public BusinessRulesEngine(String rulesFilename) throws Exception
    {
        this.setPrintStream(System.out);
        this.setActionsPrintStream(System.out);
        parseXmlFile(rulesFilename);
    }

    /**
     * engine can be instantiated by passing the filename
     * the file will be parsed and all rules will be collected
     * the properties file is an external file that is used
     * to define several parameters used by the engine
     */
    public BusinessRulesEngine(String rulesFilename,String propertiesFile) throws Exception
    {
        if(propertiesFile!=null)
        {
            loadProperties(propertiesFile);
        }
        parseXmlFile(rulesFilename);
    }
    
    /**
     * engine can be instantiated by passing an array of files
     * the files will be parsed and all rules from all files
     * will be collected
     */
    public BusinessRulesEngine(File[] rulesFiles) throws Exception
    {
        this.setPrintStream(System.out);
        this.setActionsPrintStream(System.out);
        for(int i=0;i<rulesFiles.length;i++)
        {
            parseXmlFile(rulesFiles[i].getPath());
        }
    }
    
    /**
     * engine can be instantiated by passing a zipfile containg xml
     * rule files.
     * the files will be parsed and all rules from all files
     * will be collected
     */
    public BusinessRulesEngine(ZipFile zipFile) throws Exception
    {
        this.setPrintStream(System.out);
        this.setActionsPrintStream(System.out);
        for(Enumeration<?> entries = zipFile.entries();entries.hasMoreElements();)
        {
            ZipEntry entry = (ZipEntry)entries.nextElement();
            if(!entry.isDirectory())
            {
            	parseXmlInputStream(zipFile.getInputStream(entry));
            }
        }
        zipFile.close();
    }
    
    /**
     * engine can be instantiated by passing a zipfile containg xml
     * rule files.
     * the files will be parsed and all rules from all files
     * will be collected
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
    }
    
    /**
     * engine can be instantiated by passing an array of files
     * the files will be parsed and all rules from all files
     * will be collected
     * the properties file is an external file that is used
     * to define several parameters used by the engine
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
        
    }
    
    /**
     * method runs the rules for all groups and subgroups
     * against the object with the given label
     */
    public void run(String objectLabel, Object object)throws Exception
    {
        status = STATUS_ENGINE_EXECUTED;
        for(int i=0;i<groups.size();i++)
        {
            RuleGroup group = (RuleGroup)groups.get(i);
            group.setPrintStream(stream);
            group.setActionsPrintStream(actionsStream);
            group.setWriter(writer);
            group.setTimestampFormat(timestampFormat);
            group.setOutputType(outputType);
            group.setRuleLogicTemplate(ruleLogicTemplate);
            group.runRules(objectLabel, object);
            if(group.getFailed()==1) // group failed
            {
            	// increase the counter of failed groups
            	executionCollection.increaseFailedGroupCount();
            }
            
            executionCollection.addAll(group.getExecutionCollection().getResults());
        }
    }
    
    /**
     * method runs the rules for all groups and subgroups
     * for all objects of the collection.
     * objectLabel is used to label the result of a rule in the output
     * so that it can be identified.
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
     * using the defined field seperator.
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
        // its fields using - in this case - the default semicolon (;) seperator
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
		        Row row = splitter.getRow(line); 
		        // run rules on this data
		        run("row: " + counter, row);
		        counter++;
	        }
	    }	
    	reader.close();
    }
    
    /**
     * method runs the rules for all groups and subgroups
     * against the object.
     * a default label for the objects will be used.
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
     * method is used to parse the given xml file
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
    }
    
    /**
     * method is used to parse the given xml input stream
     */
    private void parseXmlInputStream(InputStream stream)throws Exception
    {
        // create a parser object to parse
        // the xml input stream
        
        Parser parser = new Parser(replacer);
        parser.parse(stream); 
        
        // get the created xmlrule arraylist from the parser
        // and add them to the list
        groups.addAll(parser.getGroups());
    }
    
    /**
     * method returns a collection of results from all groups and subgroups
     */
    public RuleExecutionCollection getRuleExecutionCollection()
    {
        return executionCollection;
    }
    
    /**
     * method returns the list of groups as defined in the xml file
     */
    public ArrayList<RuleGroup> getGroups()
    {
        return groups;
    }
    
    /**
     * method returns the a group identified by it's id
     * as defined in the xml file
     */
    public RuleGroup getGroupByid(String groupId)
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
     */
    public int getNumberOfRules()
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
     */
    public int getNumberOfActions()
    {
        int count = 0;
        for(int i=0;i<groups.size();i++)
        {
            RuleGroup group = (RuleGroup)groups.get(i);
            count = count + group.getNumberOfActions();
        }
        return count;
    }
    
    /**
     * method returns the number of failed rules from all groups and subgroups
     */
    public int getNumberOfRulesFailed()
    {
        return executionCollection.getFailedRulesCount();
    }
    
    /**
     * method returns the number of passed rules from all groups and subgroups
     */
    public int getNumberOfRulesPassed()
    {
        return executionCollection.getPassedRulesCount();
    }
    
    /**
     * method returns the number of groups that failed
     */
    public long getNumberOfGroupsFailed() throws Exception
    {
    	return executionCollection.getFailedGroupsCount();
    }

    /**
     * method returns the total number of groups
     */
    public long getNumberOfGroups() throws Exception
    {
    	return groups.size();
    }
    
    /**
     * returns if the method <run> has been invoked or not,
     * meaning if the rules ran or not.
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
     */
    public String getRuleLogic(int index)throws Exception
    {
        if(index>-1 && groups.get(index)!=null)
        {
        	if(templatesFolder!=null && ruleLogicTemplate!=null)
        	{
        		VelocityDataWriter writer = new VelocityDataWriter(getTemplatesFolder(),getRuleLogicTemplateName());
        		writer.addObject("group",(RuleGroup)groups.get(index));
        		return writer.merge();
        	}
        	else
        	{
        		RuleGroup group = (RuleGroup)groups.get(index);
        		return group.getRuleLogic();
        	}
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
     */
    public VariableReplacer getReplacer()
    {
        return replacer;
    }
    
    /**
     * sets the replacer object, which is used to replace variables specified
     * in the xml files with real values as specified in an external
     * properties file.
     */
    public void setReplacer(VariableReplacer replacer)
    {
        this.replacer = replacer;
    }
    
    /**
     * 
     * the printstream that is used for output
     */
    public PrintStream getPrintStream()
    {
        return stream;
    }
    
    /**
     * sets the printstream to a new value
     * @param stream
     */
    public void setPrintStream(PrintStream stream)
    {
        this.stream = stream;
    }
    
    /**
     * 
     * the printstream that is used for output of actions
     */
    public PrintStream getActionsPrintStream()
    {
        return actionsStream;
    }
    
    /**
     * sets the printstream for actions to a new value
     * @param stream
     */
    public void setActionsPrintStream(PrintStream stream)
    {
        this.actionsStream = stream;
    }

    /**
    * this label will be used - together with a running number - for all objects (that are to be tested)
    * to identify them in the output
     */
    public String getObjectsLabel()
    {
        return objectsLabel;
    }
    
    /**
     * set the label to a different one, if you want to modify, how the object
     * is identified during ourput.
     */
    public void setObjectsLabel(String objectsLabel)
    {
        this.objectsLabel = objectsLabel;
    }
    
    /**
     * the objectlabel that is used to identify an object in output additionally has a running
     * number.
     * the format of this number is returned here.
     */
    public String getObjectLabelNumberFormat()
    {
        return objectsLabelNumberFormat;
    }
    
    /**
     * the objectlabel that is used to identify an object in output additionally has a running
     * number.
     * specifiy a different format here that will be used during output.
     */
    public void setObjectLabelNumberFormat(String format)
    {
        this.objectsLabelNumberFormat = format;
    }
    
    /**
     * specifies if all rule results should be output (2), only for failed rules (0),
     * only for passed rules (1). default is 0.
     */
    public int getOutputType()
    {
        return outputType;
    }
    
    /**
     * sets if all rule results should be output (2), only for failed rules (0),
     * only for passed rules (1). default is 0.
     */
    public void setOutputType(int outputType)
    {
        this.outputType = outputType;
    }
    
    /**
     * sets the name and path of the template to use
     * and initializes the velocity template engine for use.
     * results of the rules will me merged with the template
     * and output to the defined printstream.
     */
    public void setMessageTemplate(String name, String path) throws Exception
    {
        if(!path.endsWith("/")&& !path.endsWith("\\"))
        {
            path = path + "/";
        }
        writer = new VelocityDataWriter(path,name);
    }
    
    /**
     * sets the name and path of the template to use
     * and initializes the velocity template engine for use.
     * results of the rules will me merged with the template
     * and output to the defined printstream.
     * 
     * The argument name must include the path and the name
     * of the template file
     */
    public void setMessageTemplate(String pathAndName) throws Exception
    {
    	File file = new File(pathAndName);
    	String path = file.getParent();
    	String name = file.getName();
        if(path!=null && !path.endsWith("/")&& !path.endsWith("\\"))
        {
            path = path + "/";
        }
        else
        {
        	path="";
        }
        writer = new VelocityDataWriter(path,name);
    }
    
    /**
     * name of the output template for rule messages.
     */
    public String getMessageTemplateName()
    {
        return messageTemplateName;
    }
    
    /**
     * name of the output template for rule logic.
     */
    public String getRuleLogicTemplateName()
    {
        return ruleLogicTemplate;
    }
    
    /**
     * path to the output template(s).
     * template will be used to create output
     */

    public String getTemplatesFolder()
    {
        return templatesFolder;
    }
    
    /**
     * path to the output template(s).
     * template will be used to create output
     */

    public void setTemplatesFolder(String templatesFolder)
    {
        this.templatesFolder=templatesFolder;
    }
    
    /**
     * gets the datawriter that is used for merging rule results with a
     * template. the output will go to the defined printstream.
     */
    public VelocityDataWriter getWriter()
    {
        return writer;
    }
    
    /**
     * returns the format of the timestamp used for timestamp formating
     */
    public String getTimestampFormat()
    {
        return timestampFormat;
    }
    
    /**
     * sets the format of the timestamp used for timestamp formating.
     * follows the rules of the java.text.SimpleDateFormat class
     */
    public void setTimestampFormat(String timestampFormat)
    {
        this.timestampFormat = timestampFormat;
    }
    
    /**
     * load the properties for the rule engine from the given filename
     */
    private void loadProperties(String filename) throws Exception
	{
		
	    Properties props = new Properties();
		File f;
	    f = new File(filename);
		FileInputStream input = new FileInputStream(f);
		props.load(input);
		
	    replacementsFile = props.getProperty(PROPERTY_REPLACEMENTS_FILE);
	    
	    templatesFolder = props.getProperty(PROPERTY_TEMPLATES_FOLDER);
	    messagesTemplate = props.getProperty(PROPERTY_MESSAGES_TEMPLATE);
	    ruleLogicTemplate = props.getProperty(PROPERTY_RULELOGIC_TEMPLATE);
	    
	    outputFile = props.getProperty(PROPERTY_OUPUT_FILE);
	    outputFileTimestampFormat = props.getProperty(PROPERTY_OUPUT_FILE_TIMESTAMP_FORMAT);
	    outputType = Integer.parseInt(props.getProperty(PROPERTY_OUPUT_TYPE));
	    
	    objectsLabel = props.getProperty(PROPERTY_OBJECT_LABEL);
	    objectsLabelNumberFormat = props.getProperty(PROPERTY_OBJECT_LABEL_FORMAT); 

	    actionsOutputFile = props.getProperty(PROPERTY_ACTIONS_OUPUT_FILE);
	    actionsOutputFileTimestampFormat = props.getProperty(PROPERTY_OUPUT_FILE_TIMESTAMP_FORMAT);
	    
	    if (replacementsFile!=null)
        {
            replacer = new VariableReplacer(replacementsFile);
        }
        if(outputFile!=null)
        {
            String outputFileName=null;
        	if(outputFileTimestampFormat!=null && !outputFileTimestampFormat.equals(""))
            {
            	outputFileName = FileUtility.getTimeStampedFilename(outputFile,outputFileTimestampFormat);
            }
            else
            {
            	outputFileName = outputFile;
            }
        	this.setPrintStream(new PrintStream(new FileOutputStream(new File(outputFileName))));
        }
        else
        {
            this.setPrintStream(System.out);
        }
        if(actionsOutputFile!=null)
        {
        	String actionsOutputFileName=null;
        	if(actionsOutputFileTimestampFormat!=null && !actionsOutputFileTimestampFormat.equals(""))
            {
        		actionsOutputFileName = FileUtility.getTimeStampedFilename(actionsOutputFile,actionsOutputFileTimestampFormat);
            }
            else
            {
            	actionsOutputFileName = actionsOutputFile;
            }
        	this.setActionsPrintStream(new PrintStream(new FileOutputStream(new File(actionsOutputFileName))));
        }
        else
        {
            this.setActionsPrintStream(System.out);
        }
        if(templatesFolder!=null && messagesTemplate!=null)
        {
            setMessageTemplate(messagesTemplate,templatesFolder);
        }
        input.close();

	}
    
    /**
     * sets the path and name of the output file which contains the
     * results of the rule engine.
     */
    public void setOutputFile(String outputFile)
    {
		this.outputFile = outputFile;
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
    	System.out.println("published as open source under the GPL. read the licence notice");
    	System.out.println("all code by uwe geercken, 2006-2014. uwe.geercken@web.de - www.datamelt.com");
    	System.out.println("published under GPL3");
    	System.out.println();
    }
    
    public String getOutputFile()
    {
    	return outputFile;
    }

	public String getObjectsLabelNumberFormat()
	{
		return objectsLabelNumberFormat;
	}

	public String getReplacementsFile()
	{
		return replacementsFile;
	}

	public String getMessagesTemplate()
	{
		return messagesTemplate;
	}

	public String getOutputFileTimestampFormat() 
	{
		return outputFileTimestampFormat;
	}

	public String getActionsOutputFileTimestampFormat()
	{
		return actionsOutputFileTimestampFormat;
	}

	public void setActionsOutputFileTimestampFormat(String actionsOutputFileTimestampFormat)
	{
		this.actionsOutputFileTimestampFormat = actionsOutputFileTimestampFormat;
	}

	public String getRuleLogicTemplate()
	{
		return ruleLogicTemplate;
	}

	public String getActionsOutputFile()
	{
		return actionsOutputFile;
	}
	
	
}
