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
package com.datamelt.rules.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;

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
public class XmlRule implements Cloneable, Serializable
{
	public static final long serialVersionUID = 1964070340;
	
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
    
    // to cache values from rules
    // these values are passed to the check to increase performance
    private HashSet<String> valueCache = new HashSet<>();

    /**
     * Constructor using the id and description of the rule.
     * 
     * @param	id			the id of the action
     * @param	description	the description of the action

     */
    public XmlRule(String id, String description)
    {
        this.id = id;
        this.description = description;
    }

    /**
     * method to clone the xml rule
     * 
     * @throws	CloneNotSupportedException	exception if the object can not be cloned
     */
    public Object clone() throws CloneNotSupportedException
    {
    	return (XmlRule)super.clone();
    }
    
    
    /**
     * returns the expected value of the rule
     * 
     * @return	the expected value for the rule
     */
    public String getExpectedValueRule()
    {
        return expectedValueRule;
    }
    
    /**
     * sets the expected value of the rule 
     * 
     * @param	value	the expected value of the rule
     */
    public void setExpectedValueRule(String value)
    {
        this.expectedValueRule = value;
        
        // fill the value cache
        fillValueCache(value);
    }
    
    /**
     * returns the type of the expected value 
     * 
     * @return	the type of the expected value of the rule
     */
    public String getExpectedValueRuleType()
    {
    	return expectedValueRuleType;
    }
    
    /**
     * sets the type of the expected value
     * @param	valueType	the type of the expected value of the rule 
     */
    public void setExpectedValueRuleType(String valueType)
    {
        this.expectedValueRuleType = valueType;
    }
    
    /**
     * returns the name of the check that will be executed.
     * 
     * @return the name of the check to execute
     */
    public String getCheckToExecute()
    {
        return checkToExecute;
    }
    
    /**
     * sets the name of the check that is to be executed. checks are classes
     * that implement the GenericCheck class.
     * 
     * @param	checkToExecute	the name of the class of the check
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
     * returns the GenericCheck object to be executed 
     * 
     * @return the check to execute
     */
    public GenericCheck getExecuteCheck()
    {
        return executeCheck;
    }
    
    
    /**
     * sets the GenericCheck object - a class that implements the GenricCheck class
     * 
     *  @param	executeCheck	the check to execute
     */
    public void setExecuteCheck(Object executeCheck)
    {
        this.executeCheck = (GenericCheck)executeCheck;
    }

    /**
     * returns the list of parameters that have to be passed to the method
     * to execute 
     * 
     * @return	list of parameters
     * 
     */
    public ArrayList<Parameter> getParameters()
    {
        return parameters;
    }
    
    /**
     * sets the list of parameters that have to be passed to the method
     * to execute 
     * 
     * @param	parameters	list of parameters
     */
    public void setParameters(ArrayList <Parameter>parameters)
    {
        this.parameters = parameters;
    }
    
    /**
     * add another parameter to the list of parameters of the method
     * 
     * @param	parameter	the parameter to add
     */
    public void addParameter(Parameter parameter)
    {
        parameters.add(parameter);
    }
    
    /**
     * returns the description of the rule 
     * 
     * @return the description of the rule
     * 
     */
    public String getDescription()
    {
        return description;
    }
    
    /**
     * sets the description of the rule 
     * 
     * @param	description	the description of the rule
     */
    public void setDescription(String description)
    {
        this.description = description;
    }
    
    /**
     * returns the id of the rule 
     * 
     * @return	the id of the rule
     */
    public String getId()
    {
        return id;
    }
    
    /**
     * sets the id of the rule 
     * 
     * @param	id	the id of the rule
     */
    public void setId(String id)
    {
        this.id = id;
    }

    /** returns a integer expression meaning [0] or [1]
     *  depending if the rule passed or failed.
     *  
     *  @return	indicator if the rule passed or failed
     */
    public int getFailed()
    {
        return failed;
    }
    
    /** returns a string expression meaning [true] or [false]
     *  depending if the rule passed or failed.
     *  
     *  @return	indicator - in the form of a string - if the rule passed or failed
     */
    public String getFailedAsString()
    {
        return "[" + Converter.convertIntegerToBooleanString(failed) + "]";
        
    }
    
    /**
     * sets the value of the failed variable depending if the rule
     * failed or passed when it was run 
     * 
     * @param failed	indicator if the rule passed or failed
     */
    public void setFailed(int failed)
    {
        this.failed = failed;
    }
    
    /**
     * sets the value of the failed variable depending if the rule
     * failed or passed when it was run
     * 
     * @param failed	indicator if the rule passed or failed
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
     * 
     * @return	collection of rule objects 
     */
    public RuleObjectCollection getRuleObjects()
    {
        return ruleObjects;
    }
    
    /**
     * sets the objects that are involved when running the rule
     * 
     * @param	ruleObjects	collection of rule objects
     */
    public void setRuleObjects(RuleObjectCollection ruleObjects)
    {
        this.ruleObjects = ruleObjects;
    }
    
    /**
     * returns the messages - passed and failed - that belong to this rule
     * 
     * @return	a collection of messages
     * 
     */
    public RuleMessageCollection getMessages()
    {
        return messages;
    }
    
    /**
     * sets the messages - one if the rule passes and one if it fails
     * that will be used for display purposes 
     * 
     * @param	messages	the messaged belonging to the rule
     */
    public void setMessages(RuleMessageCollection messages)
    {
        this.messages = messages;
    }
    
    /**
     * returns the name of the method that is to be executed 
     * 
     * @return	the name of the method to execute
     */
    public String getObjectMethodName()
    {
        return objectMethodName;
    }
    
    /**
     * returns the type of object that is returned 
     * by the method to be executed 
     * 
     * @return	the return type of the method
     */
    public String getObjectMethodReturnType()
    {
        return objectMethodReturnType;
    }
    
    /**
     * returns the message by type: either the message when the
     * rule passes or the message when the rule fails
     * 
     * @param	type		the type of the message - failed or passed
     * @return				the message of the given type
     * @throws	Exception	exception if the message can not be returned
     */
    public RuleMessage getMessage(int type)throws Exception
    {
        return messages.getByType(type); 
    }

    /**
     * gets a collection of actions
     * 
     * @return	the collection of actions
     */
	public XmlActionCollection getActions()
	{
		return actions;
	}

	/**
	 * sets the collection of actions
	 * 
	 * @param actions	the collection of actions
	 */
	public void setActions(XmlActionCollection actions)
	{
		this.actions = actions;
	}
	
	/**
	 * cache the possible values in a HashSet if
	 * we have multiple values
	 * 
	 * @param values	String representing a comma separated list of expected values
	 */
	private void fillValueCache(String values)
	{
		// we only cache if we have more than one value
		// avoid splitting strings into values over and over again
		if(values!=null)
		{
			String [] valuesArray = values.split(",");
			if(valuesArray.length>1)
			{
				if(valueCache.size()==0)
				{
					for(int i=0;i<valuesArray.length;i++)
					{
						valueCache.add(valuesArray[i]);
					}
				}
			}
		}
	}
	
	public HashSet<String> getValueCache()
	{
		return valueCache;
	}
}
