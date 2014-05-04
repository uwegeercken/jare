/*
 * Created on 07.11.2006
 *
 * all code by uwe geercken
 */
package com.datamelt.rules.core;

import java.util.ArrayList;

import com.datamelt.rules.core.util.Converter;
import com.datamelt.rules.core.util.XmlActionCollection;
import com.datamelt.rules.implementation.GenericCheck;


/**
 * XmlRule objects are constructed from xml files that describe one or
 * more business rules. 
 * 
 * The rule describes how to get the data of an object and
 * a check (a class that implements the GenricCheck class) that should
 * run against the data. The result is compared to the expected value
 * to determine if the rule passed or failed.
 *  
 * @author uwe geercken
 */
public class XmlRule
{
    // indicates if a rule failed or passed
    public static final int FAILED = 1;
    public static final int PASSED = 0;
    
    // indicates if a rule failed or passed as a string expression
    public static final String FAILED_TEXT     = "[true]";
    public static final String NOT_FAILED_TEXT = "[false]";
    
    // id of the rule
    private String id;
    // description of the rule
    private String description;
    // the method of the object to envoke
    private String objectMethodName;
    // the returntype of the method to envoke
    private String objectMethodReturnType;
    
    private RuleObjectCollection ruleObjects = new RuleObjectCollection();
    // the messages of the rule: one in case it fails, one in case it passes
    private RuleMessageCollection messages = new RuleMessageCollection();
    // the actions that belong to the rule
    private XmlActionCollection actions = new XmlActionCollection();
    
    private String checkToExecute;
    private ArrayList<Parameter> parameters = new ArrayList<Parameter>();
    
    // value and type of the expected value
    private String expectedValueRule;
    private String expectedValueRuleType;
    
    private int failed; // 1 means failed, 0 means passed
    
    // the class that implements the GenericCheck class
    private GenericCheck executeCheck;

    /**
     * Constructor using the id and description of the rule. 
     */
    public XmlRule(String id, String description)
    {
        this.id = id;
        this.description = description;
    }

    /**
     * returns the expected value of the rule 
     */
    public String getExpectedValueRule()
    {
        return expectedValueRule;
    }
    
    /**
     * sets the expected value of the rule 
     */
    public void setExpectedValueRule(String value)
    {
        this.expectedValueRule = value;
    }
    
    /**
     * returns the type of the expected value 
     */
    public String getExpectedValueRuleType()
    {
    	return expectedValueRuleType;
    }
    
    /**
     * sets the type of the expected value 
     */
    public void setExpectedValueRuleType(String valueType)
    {
        this.expectedValueRuleType = valueType;
    }
    
    /**
     * returns the name of the check that will be executed. 
     */
    public String getCheckToExecute()
    {
        return checkToExecute;
    }
    
    /**
     * sets the name of the check that is to be executed. checks are classes
     * that implement the GenericCheck class. 
     */
    public void setCheckToExecute(String checkToExecute)
    {
        this.checkToExecute = checkToExecute;
        try
        {
            setExecuteCheck(Class.forName(checkToExecute).newInstance());
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        
    }
    
    /**
     * returns the GenricCheck object to be executed 
     */
    public GenericCheck getExecuteCheck()
    {
        return executeCheck;
    }
    
    
    /**
     * sets the GenericCheck object - a class that implements the GenricCheck class 
     */
    public void setExecuteCheck(Object executeCheck)
    {
        this.executeCheck = (GenericCheck)executeCheck;
    }

    /**
     * returns the list of parameters that have to be passed to the method
     * to execute 
     */
    public ArrayList<Parameter> getParameters()
    {
        return parameters;
    }
    
    /**
     * sets the list of parameters that have to be passed to the method
     * to execute 
     */
    public void setParameters(ArrayList <Parameter>parameters)
    {
        this.parameters = parameters;
    }
    
    /**
     * add another parameter to the list of parameters of the method 
     */
    public void addParameter(Parameter parameter)
    {
        parameters.add(parameter);
    }
    
    /**
     * returns the description of the rule 
     */
    public String getDescription()
    {
        return description;
    }
    
    /**
     * sets the description of the rule 
     */
    public void setDescription(String description)
    {
        this.description = description;
    }
    
    /**
     * returns the id of the rule 
     */
    public String getId()
    {
        return id;
    }
    
    /**
     * sets the id of the rule 
     */
    public void setId(String id)
    {
        this.id = id;
    }

    /** returns a integer expression meaning [0] or [1]
     *  depending if the rule passed or failed. 
     */
    public int getFailed()
    {
        return failed;
    }
    
    /** returns a string expression meaning [true] or [false]
     *  depending if the rule passed or failed. 
     */
    public String getFailedAsString()
    {
        return "[" + Converter.convertIntegerToBooleanString(failed) + "]";
        
    }
    
    /**
     * sets the value of the failed variable depending if the rule
     * failed or passed when it was run 
     */
    public void setFailed(int failed)
    {
        this.failed = failed;
    }
    
    /**
     * sets the value of the failed variable depending if the rule
     * failed or passed when it was run 
     */
    public void setFailed(boolean failed)
    {
        if (failed)
        {
            this.failed = FAILED;
        }
        else
        {
            this.failed = PASSED;
        }
    }
    
    /**
     * returns the objects that are involved when running the rule
     * 
     * the first object is constructed from the actual data and the
     * second object is the expected value from the rule
     */
    public RuleObjectCollection getRuleObjects()
    {
        return ruleObjects;
    }
    
    /**
     * sets the objects that are involved when running the rule 
     */
    public void setRuleObjects(RuleObjectCollection ruleObjects)
    {
        this.ruleObjects = ruleObjects;
    }
    
    /**
     * returns the messages that belong to this rule
     */
    public RuleMessageCollection getMessages()
    {
        return messages;
    }
    
    /**
     * sets the messages - one if the rule passes and one if it fails
     * that will be used for display purposes 
     */
    public void setMessages(RuleMessageCollection messages)
    {
        this.messages = messages;
    }
    
    /**
     * returns the name of the method that is to be executed 
     */
    public String getObjectMethodName()
    {
        return objectMethodName;
    }
    
    /**
     * returns the type of object that is returned 
     * by the method to be executed 
     */
    public String getObjectMethodReturnType()
    {
        return objectMethodReturnType;
    }
    
    /**
     * returns the message by type: either the message when the
     * rule passes or the message when the rule failes
     */
    public RuleMessage getMessage(int type)throws Exception
    {
        return messages.getByType(type); 
    }

	public XmlActionCollection getActions()
	{
		return actions;
	}

	public void setActions(XmlActionCollection actions)
	{
		this.actions = actions;
	}
    
}
