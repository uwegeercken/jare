/*

 * Created on 08.11.2006
 *
 * all code by uwe geercken
 */
package com.datamelt.rules.core;

import java.io.PrintStream;
import java.util.ArrayList;

import com.datamelt.rules.core.RuleSubGroupCollection;
import com.datamelt.rules.core.action.Action;
import com.datamelt.rules.core.util.Converter;
import com.datamelt.rules.engine.BusinessRulesEngine;
import com.datamelt.util.VelocityDataWriter;

/**
 * Rules are organized in groups and subgroups. Subgroups can contain multiple rules
 * and groups can contain multiple subgroups. Subgroups and rules can be connected
 * using [and] or [or] conditions.
 * 
 * rule groups are defined in xml files. one xml file can only contain one group. one
 * can also say, that one rule (and therefor one file) defines a rule scenario of 
 * a number of rules (one to many rules).
 * 
 * @author uwe geercken
 */
public class RuleGroup
{
    private String id;
    private String description;
    private boolean outputAfterActions=false;
    private String validFrom;
    private String validUntil;
    private PrintStream printStream;
    private PrintStream actionsPrintStream;
    private VelocityDataWriter writer;
    private int outputType;
    private String timestampFormat;
    private String ruleLogicTemplate;

    // list of all subgroups belonging to this group
    private RuleSubGroupCollection subGroupCollection = new RuleSubGroupCollection();
    // list of all action that shall be excuted for this group
    private ArrayList <XmlAction>actions = new ArrayList<XmlAction>();
    
    private static final int OPERATOR_AND = 0;
    
    /**
     * constructor for a rule group using its id 
     */
    public RuleGroup(String id, String description)
    {
        this.id = id;
        this.description = description;
    }
    
    /**
     * returns the description of the group 
     */
    public String getDescription()
    {
        return description;
    }
    
    /**
     * sets the description of the group 
     */
    public void setDescription(String description)
    {
        this.description = description;
    }
    
    /**
     * returns the valid from string of the group 
     */
    public String getValidFrom()
    {
        return validFrom;
    }
    
    /**
     * sets the valid from string of the group 
     */
    public void setValidFrom(String validFrom)
    {
        this.validFrom = validFrom;
    }
    
    /**
     * returns the valid until string of the group 
     */
    public String getValidUntil()
    {
        return validUntil;
    }
    
    /**
     * sets the valid until string of the group 
     */
    public void setValidUntil(String validUntil)
    {
        this.validUntil = validUntil;
    }
    
    /**
     * returns the id of the group 
     */
    public String getId()
    {
        return id;
    }
    
    /**
     * stes the id of the group 
     */
    public void setId(String id)
    {
        this.id = id;
    }
    
    /**
     * returns the collection of subgroups that belong to the group 
     */
    public RuleSubGroupCollection getSubGroupCollection()
    {
        return subGroupCollection;
    }
    
    /**
     * returns the subgroups that are in a collection of subgroups that belong to the group 
     */
    public ArrayList <RuleSubGroup>getSubGroups()
    {
        return subGroupCollection.getSubGroups();
    }
    
    /**
     * sets the collection of subgroups that belong to the group 
     */
    public void setSubGroups(RuleSubGroupCollection collection)
    {
        this.subGroupCollection = collection;
    }
    
    /**
     * this method is used to run all rules in all subgroups.
     */
    public void runRules(String objectLabel,Object object)throws Exception
    {
        for(int i=0;i<subGroupCollection.size();i++)
        {
            RuleSubGroup subGroup = (RuleSubGroup)subGroupCollection.get(i);
            subGroup.setOutputStream(printStream);
            subGroup.setWriter(writer);
            subGroup.setTimestampFormat(timestampFormat);
            subGroup.setOutputType(outputType);
            subGroup.runRules(objectLabel, object);
        }
        if(outputType!=BusinessRulesEngine.OUTPUT_TYPE_NO_OUTPUT)
        {
	        if(printStream!=null && writer==null)
	        {
	            String output = "group " + id + " failed=";
	        	if(outputType==BusinessRulesEngine.OUTPUT_TYPE_FAILED_ONLY)
	            {
	                if(getFailed()==1)
	                {
	                    // objectLabel used to identify object in output
	                    printStream.println(output + getFailedAsString());
	                }
	            }
	            else if(outputType==BusinessRulesEngine.OUTPUT_TYPE_PASSED_ONLY)
	            {
	                if(getFailed()==0)
	                {
	                    // objectLabel used to identify object in output
	                    printStream.println(output + getFailedAsString());
	                }
	            }
	            else if(outputType==BusinessRulesEngine.OUTPUT_TYPE_FAILED_AND_PASSED)
	            {
	                // objectLabel used to identify object in output
	                printStream.println(output + getFailedAsString());
	            }
	        }
	        else if(printStream!=null && writer!=null)
	        {
	            //RuleExecutionCollection col = getExecutionCollection();
	            if(outputType==BusinessRulesEngine.OUTPUT_TYPE_FAILED_ONLY)
	            {
	                if(getFailed()==1)
	                {
	                    writer.addObject("group", this);
	                    writer.addObject("objectlabel", objectLabel);
	                    writer.write();
	                }
	            }
	            else if(outputType==BusinessRulesEngine.OUTPUT_TYPE_PASSED_ONLY)
	            {
	                if(getFailed()==0)
	                {
	                    writer.addObject("group", this);
	                    writer.addObject("objectlabel", objectLabel);
	                    writer.write();
	                }
	            }
	            else if(outputType==BusinessRulesEngine.OUTPUT_TYPE_FAILED_AND_PASSED)
	            {
	                writer.addObject("group", this);
	                writer.addObject("objectlabel", objectLabel);
	                writer.write();
	            }
	        }
        }
        // execute all actions on this object
        Action action = new Action(this.getFailed(), object, outputAfterActions);
       	action.setPrintStream(actionsPrintStream);
        action.executeActions(actions);
    }
    
    /**
     * returns the total number of rules over all subgroups
     */
    public int getNumberOfRules()
    {
        int count = 0;
        for(int i=0;i<subGroupCollection.size();i++)
        {
            RuleSubGroup subGroup = (RuleSubGroup)subGroupCollection.get(i);
            count = count + subGroup.getRulesCollection().size();
        }
        return count;
    }
    
    /**
     * returns the total number of actions of this group
     */
    public int getNumberOfActions()
    {
        return actions.size();
    }
    /**
     * returns the number of rules that failed over all
     * subgroups
     */
    public int getNumberOfRulesFailed()
    {
        int count = 0;
        for(int i=0;i<subGroupCollection.size();i++)
        {
            RuleSubGroup subGroup = (RuleSubGroup)subGroupCollection.get(i);
            count = count + subGroup.getNumberOfRulesFailed();
        }
        return count;
    }
    
    /**
     * returns a collection of results from all subgroups and rules
     * that ran.
     */
    public RuleExecutionCollection getExecutionCollection()
    {
        RuleExecutionCollection collection = new RuleExecutionCollection();
        for(int i=0;i<subGroupCollection.size();i++)
        {
            RuleSubGroup subGroup = (RuleSubGroup)subGroupCollection.get(i);
            collection.addAll(subGroup.getExecutionCollection().getResults());
        }
        return collection;
    }
    
    public ArrayList <RuleExecutionResult>getResults()
    {
        return getExecutionCollection().getResults();
    }
    
    /**
     * the subgroups can be combined using either a logical <and> or a logical <or>.
     * the logical operator is stored with the subgroup and expresses how to logically combine
     * the subgroup to the PREVIOUS subgroup.
     * the first subgroup will be combined with the second subgroup. the result is a <0> or <1> (passed or failed).
     * then the result will be compared to the next/third subgroup using the logical operator of the third subgroup.
     * 
     * here is the logic in a diagram form:
     * 0---
     *    |---result---
     * 1---            |---result---
     *             2---             |---result---
     *                          3---
     * 
     * 
     * in total one can say that the subgroups are chained together using the logical operators. the result is an
     * indicator, if the complete group failed (0) or not (1).
     * 
     */
    public int getFailed() throws Exception
    {
        int failed=0;
        
        if (subGroupCollection.size()>1)
        {
            // set the failed variable to the result of the first subgroup
            // note: this is only for the first subgroup necessary
            failed = ((RuleSubGroup)subGroupCollection.get(0)).getFailed();
	        // loop over the remaining subgroups
            for(int i=1;i<subGroupCollection.size();i++)
	        {
	            // get the subgroup
	            RuleSubGroup subGroup = (RuleSubGroup)subGroupCollection.get(i);
	            // get the logical operator that should be applied
	            int logicalOperator = subGroup.getLogicalOperatorSubGroup();
	            
	            failed = getFailed(failed,subGroup.getFailed(),logicalOperator);
	        }
        }
        else if (subGroupCollection.size()==1)
        {
            RuleSubGroup subGroup = (RuleSubGroup)subGroupCollection.get(0);
            if(subGroup.getFailed()==0)
            {
                failed = 0;
            }
            else
            {
                failed = 1;
            }
        }
        else
        {
            throw new Exception("no rule groups defined");
        }
        return failed;
        
    }
    
    /** returns a string expression meaning [true] or [false]
     *  depending if the group passed or failed. 
     */
    public String getFailedAsString() throws Exception
    {
        return "[" + Converter.convertIntegerToBooleanString(getFailed()) + "]";
        
    }
    
    /**
     * group returns (0 or 1) if two groups fail when you connect them
     * using the defined condition [and] or [or]
     * 
     * is used to detect the condition when chaining groups together 
     */
    private int getFailed(int group1Failed, int group2Failed, int operator)
    {
        
        if(operator == OPERATOR_AND)
        {
            if(group1Failed==0 && group2Failed==0 )
            {
                return 0;
            }
            else
            {
                return 1;
            }
        }
        else
        {
            if(group1Failed==0 || group2Failed==0 )
            {
                 return 0;
            }
            else
            {
                return 1;
            }
        }
    }
    
    public String getRuleLogic()
    {
        StringBuffer buffer = new StringBuffer("(");
        if (subGroupCollection.size()>0)
        {
	        RuleSubGroup subGroup0 = subGroupCollection.get(0);
	        buffer.append(subGroup0.getRuleLogic());
	        for(int i=1;i<subGroupCollection.size();i++)
	        {
	            RuleSubGroup subGroup = subGroupCollection.get(i);
	            if(i==1)
	            {
	            	buffer.append(subGroup.getRuleLogic());
	            }
	            else
	            {
	            	buffer.append(" " + Converter.convertIntToLogical(subGroup.getLogicalOperatorSubGroup())+ " " + subGroup.getRuleLogic());
	            }
	            buffer.append(")");
	        }
        }
        buffer.append(")");
        for(int j=1;j<subGroupCollection.size();j++)
        {
            buffer.insert(0,"(");
        }
        return buffer.toString();
    }

    public PrintStream getPrintStream()
    {
        return printStream;
    }
    
    public void setPrintStream(PrintStream printStream)
    {
        this.printStream = printStream;
    }
    
    public PrintStream getActionsPrintStream()
    {
        return actionsPrintStream;
    }
    
    public void setActionsPrintStream(PrintStream printStream)
    {
        this.actionsPrintStream = printStream;
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
     * gets the template writer that is used for output
     */
    public VelocityDataWriter getWriter()
    {
        return writer;
    }
    
    /**
     * sets the velocity template writer for use.
     * results of the rules will me merged with the template
     * and output to the defined printstream.
     */
    public void setWriter(VelocityDataWriter writer)
    {
        this.writer = writer;
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
     * returns the name of the template that is used for outputing
     * the rule logic
     */
    public String getRuleLogicTemplate()
    {
        return ruleLogicTemplate;
    }
    
    /**
     * sets the name of the template that is used for outputing
     * the rule logic
     */
    public void setRuleLogicTemplate(String ruleLogicTemplate)
    {
        this.ruleLogicTemplate = ruleLogicTemplate;
    }
    
    /**
     * add an action to this group that shall be executed
     */
    
    public void addAction(XmlAction action)
    {
    	actions.add(action);
    }

	public boolean getOutputAfterActions()
	{
		return outputAfterActions;
	}

	public void setOutputAfterActions(boolean outputAfterActions)
	{
		this.outputAfterActions = outputAfterActions;
	}
    
	public ArrayList <XmlAction> getActions()
	{
		return actions;
	}
    
}
