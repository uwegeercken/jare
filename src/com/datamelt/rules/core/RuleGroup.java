/*

 * Created on 08.11.2006
 *
 * all code by uwe geercken
 */
package com.datamelt.rules.core;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.datamelt.rules.core.RuleSubGroupCollection;
import com.datamelt.rules.core.action.Action;
import com.datamelt.rules.core.util.Converter;

/**
 * Rules are organized in groups and subgroups. Subgroups can contain multiple rules
 * and groups can contain multiple subgroups. Subgroups and rules can be connected
 * using [and] or [or] conditions.
 * 
 * rule groups are defined in xml files. one xml file can only contain one group. one
 * can also say, that one rulegroup (and therefore one file) defines a rule scenario of 
 * a number of rules (one to many rules).
 * 
 * @author uwe geercken
 */
public class RuleGroup implements Serializable
{
	private String id;
    private String description;
    private boolean outputAfterActions=false;
    private String validFrom;
    private String validUntil;
    private int outputType;
    private String timestampFormat;
    private String dependentRuleGroupId;
    private int dependentRuleGroupExecuteIf;
    private boolean preserveRuleExcecutionResults=true;
    private int numberOfActionsExecuted;
    private int skipped;

    // list of all subgroups belonging to this group
    private RuleSubGroupCollection subGroupCollection = new RuleSubGroupCollection();
    // list of all action that shall be excuted for this group
    private ArrayList <XmlAction>actions = new ArrayList<XmlAction>();
    
    private static final int OPERATOR_AND = 0;
    
    public static final int TYPE_PASSED = 0;
    public static final int TYPE_FAILED = 1;

    public static final long serialVersionUID = 1964070331;
    
    /**
     * constructor for a rule group using its id and description
     * 
     * @param	id				the id of the rulegroup
     * @param	description		the description of the rulegroup
     */
    public RuleGroup(String id, String description)
    {
        this.id = id;
        this.description = description;
    }
    
    /**
     * returns the description of the group 
     * 
     * @return the description of the rulegroup
     */
    public String getDescription()
    {
        return description;
    }
    
    /**
     * sets the description of the group 
     * 
     * @param description 	the description of the rulegroup
     */
    public void setDescription(String description)
    {
        this.description = description;
    }
    
    /**
     * returns the valid from string of the group 
     * 
     * @return 	the valid from date of the group
     */
    public String getValidFrom()
    {
        return validFrom;
    }
    
    /**
     * sets the valid from string of the group 
     * 
     * @param validFrom	the valid from date of the group
     */
    public void setValidFrom(String validFrom)
    {
        this.validFrom = validFrom;
    }
    
    /**
     * checks if the rulegroup is valid on today's date by comparing
     * the valid from and valid until settings
     * 
     * @return				indicator if the rulegroup is valid
     * @throws Exception	throws exception if the date object can not be instantiated
     */
    public boolean isValid() throws Exception
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dateFrom = sdf.parse(validFrom);
		Date dateUntil = sdf.parse(validUntil);
		
		Calendar today = Calendar.getInstance();
		today.set(Calendar.HOUR_OF_DAY, 0);
		today.set(Calendar.MINUTE, 0);
		today.set(Calendar.SECOND, 0);
		today.set(Calendar.MILLISECOND, 0);

		Calendar from = Calendar.getInstance();
		from.setTime(dateFrom);
		from.set(Calendar.HOUR_OF_DAY, 0);
		from.set(Calendar.MINUTE, 0);
		from.set(Calendar.SECOND, 0);
		from.set(Calendar.MILLISECOND, 0);
		
		Calendar until = Calendar.getInstance();
		until.setTime(dateUntil);
		until.set(Calendar.HOUR_OF_DAY, 0);
		until.set(Calendar.MINUTE, 0);
		until.set(Calendar.SECOND, 0);
		until.set(Calendar.MILLISECOND, 0);

		// from javadoc:
		// ... the value 0 if the time represented by the argument is equal to the time represented by this Calendar
		// a value less than 0 if the time of this Calendar is before the time represented by the argument
		// and a value greater than 0 if the time of this Calendar is after the time represented by the argument.
		//
		return today.compareTo(from)>=0 && today.compareTo(until)<=0;
	}
    
    /**
     * returns the valid until string of the group 
     * 
     * @return	the date until which the group is valid
     */
    public String getValidUntil()
    {
        return validUntil;
    }
    
    /**
     * sets the valid until string of the group 
     * 
     * @param	validUntil	the date until which the rulegroup is valid
     */
    public void setValidUntil(String validUntil)
    {
        this.validUntil = validUntil;
    }
    
    /**
     * returns the id of the group 
     * 
     * @return	the id of the rulegroup
     */
    public String getId()
    {
        return id;
    }
    
    /**
     * sets the id of the group
     * 
     * @param	id	the id of the rulegroup 
     */
    public void setId(String id)
    {
        this.id = id;
    }
    
    /**
     * returns the collection of subgroups that belong to the group 
     * 
     * @return	a collection of subgroups
     */
    public RuleSubGroupCollection getSubGroupCollection()
    {
        return subGroupCollection;
    }
    
    /**
     * returns the subgroups that are in a collection of subgroups that belong to the group 
     * 
     * @return	a list of subgroups
     */
    public ArrayList <RuleSubGroup>getSubGroups()
    {
        return subGroupCollection.getSubGroups();
    }
    
    /**
     * sets the collection of subgroups that belong to the group
     * 
     * @param	collection of subgroups
     */
    public void setSubGroups(RuleSubGroupCollection collection)
    {
        this.subGroupCollection = collection;
    }
    
    /**
     * this method is used to run all rules in all subgroups.
     * 
     * @param	objectLabel		the label used for the object
     * @param	object			the object to use
     * @throws	Exception		throws an exception if the rulegroup or action can not be executed
     */
    public void runRules(String objectLabel,Object object)throws Exception
    {
        for(int i=0;i<subGroupCollection.size();i++)
        {
            RuleSubGroup subGroup = (RuleSubGroup)subGroupCollection.get(i);
            subGroup.setTimestampFormat(timestampFormat);
            subGroup.setOutputType(outputType);
            subGroup.setPreserveRuleExcecutionResults(preserveRuleExcecutionResults);
            subGroup.runRules(objectLabel, object);
        }
        // execute all actions on this object
        // the mothod gives back the number of actions that were executed
        Action action = new Action(this.getFailed(), object, outputAfterActions);
        numberOfActionsExecuted = action.executeActions(actions);
    }
    
    /**
     * returns the total number of rules over all subgroups
     * 
     * @return		the number of rules of the rulegroup
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
     * 
     * @return		the number of actions of the rulegroup
     */
    public int getNumberOfActions()
    {
        return actions.size();
    }
    
    /**
     * returns the total number of actions executed of this group
     * 
     * @return	the number of actions executed
     * 
     */
    public int getNumberOfActionsExecuted()
    {
        return numberOfActionsExecuted;
    }
    
    /**
     * returns the number of rules that failed over all subgroups
     * 
     * @return		the number of rules run
     */
    public long getNumberOfRulesRun()
    {
        long count = 0;
        for(int i=0;i<subGroupCollection.size();i++)
        {
            RuleSubGroup subGroup = (RuleSubGroup)subGroupCollection.get(i);
            count = count + subGroup.getNumberOfRulesRun();
        }
        return count;
    }
    
    /**
     * returns the number of rules that failed over all subgroups
     * 
     * @return	the number of rules that failed
     */
    public long getNumberOfRulesFailed()
    {
        long count = 0;
        for(int i=0;i<subGroupCollection.size();i++)
        {
            RuleSubGroup subGroup = (RuleSubGroup)subGroupCollection.get(i);
            count = count + subGroup.getNumberOfRulesFailed();
        }
        return count;
    }
    
    /**
     * returns the number of rules that passed over all subgroups
     * 
     * @return	the number of rules passed
     */
    public long getNumberOfRulesPassed()
    {
        long count = 0;
        for(int i=0;i<subGroupCollection.size();i++)
        {
            RuleSubGroup subGroup = (RuleSubGroup)subGroupCollection.get(i);
            count = count + subGroup.getNumberOfRulesPassed();
        }
        return count;
    }
    
    /**
     * returns a collection of results from all subgroups and rules that ran
     * 
     * @return	the collection of rule execution results
     */
    public RuleExecutionCollection getExecutionCollection()
    {
        RuleExecutionCollection collection = new RuleExecutionCollection();
        for(int i=0;i<subGroupCollection.size();i++)
        {
            RuleSubGroup subGroup = (RuleSubGroup)subGroupCollection.get(i);
            collection.addAll(subGroup.getExecutionCollection().getResults());
            collection.addNumberOfRulesRun(subGroup.getNumberOfRulesRun());
            collection.addNumberOfRulesPassed(subGroup.getNumberOfRulesPassed());
            collection.addNumberOfRulesFailed(subGroup.getNumberOfRulesFailed());
        }
        return collection;
    }
    
    /**
     * returns the results list of the rule execution collection
     * 
     * @return	a list of results
     */
    public ArrayList <RuleExecutionResult>getResults()
    {
        return getExecutionCollection().getResults();
    }
    
    /**
     * the subgroups can be combined using either a logical 'and' or a logical 'or'.
     * the logical operator is stored with the subgroup and expresses how to logically combine
     * the subgroup to the PREVIOUS subgroup.
     * the first subgroup will be combined with the second subgroup. the result is a '0' or '1' (passed or failed).
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
     * @return				indicator if the rulegroup failed
     * @throws	Exception	exception if the result could not be determined
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
            // there are no subgroups defined for the rulegroup. so there are also
        	// no rules.
        	// so we set the failed value to 0 meaning the rulegroup passed
        	failed = 0;
        }
        return failed;
        
    }
    
    /** returns a string expression meaning [true] or [false]
     *  depending if the group passed or failed.
     *  
     *   @return				indicator if the rulegroup failed
     *   @throws	Exception	exception if the reult could not be determined
     */
    public String getFailedAsString() throws Exception
    {
        return "[" + Converter.convertIntegerToBooleanString(getFailed()) + "]";
        
    }
    
    /**
     * returns (0 or 1) if two subgroups fail when you connect them
     * using the defined condition [and] or [or]
     * 
     * used to evaluate the result when chaining groups together 
     * 
     * @param	group1Failed	indicator if the first subgroup failed
     * @param	group2Failed	indicator if the second subgroup failed
     * @param	operator		the operator how the subgroups are connected to each other
     * @return					indicator if the chained subgroups failed
     * 
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
    
    /**
     * creates a string representation of the logic of a rulegroup
     * 
     * @return	the logic of a rulegroup in a textual representation
     */
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

    /**
     * specifies if all rule results should be output (2), only for failed rules (0),
     * only for passed rules (1). default is 0.
     * 
     * @return	the output type
     */
    public int getOutputType()
    {
        return outputType;
    }
    
    /**
     * sets if all rule results should be output (2), only for failed rules (0),
     * only for passed rules (1). default is 0.
     * 
     * @param	outputType	the output type
     */
    public void setOutputType(int outputType)
    {
        this.outputType = outputType;
    }
    
    /**
     * returns the format of the timestamp used for timestamp formating
     * 
     * @return	the format of the timestamp
     */
    public String getTimestampFormat()
    {
        return timestampFormat;
    }
    
    /**
     * sets the format of the timestamp used for timestamp formating.
     * follows the rules of the java.text.SimpleDateFormat class
     * 
     * @param	timestampFormat		the format to use for the timestamp
     */
    public void setTimestampFormat(String timestampFormat)
    {
        this.timestampFormat = timestampFormat;
    }

    /**
     * add an action to this group that shall be executed
     * 
     * @param	action	the action to add
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

	public String getDependentRuleGroupId()
	{
		return dependentRuleGroupId;
	}

	public void setDependentRuleGroupId(String dependentRuleGroupId)
	{
		this.dependentRuleGroupId = dependentRuleGroupId;
	}

	public int getDependentRuleGroupExecuteIf()
	{
		return dependentRuleGroupExecuteIf;
	}

	public void setDependentRuleGroupExecuteIf(int dependentRuleGroupExecuteIf)
	{
		this.dependentRuleGroupExecuteIf = dependentRuleGroupExecuteIf;
	}

	public boolean getPreserveRuleExcecutionResults()
	{
		return preserveRuleExcecutionResults;
	}

	public void setPreserveRuleExcecutionResults(boolean preserveRuleExcecutionResults)
	{
		this.preserveRuleExcecutionResults = preserveRuleExcecutionResults;
	}

	public int getSkipped()
	{
		return skipped;
	}

	public void setSkipped(int skipped)
	{
		this.skipped = skipped;
	}
    
}
