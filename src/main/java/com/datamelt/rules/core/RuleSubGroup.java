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
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.datamelt.rules.core.util.Converter;
import com.datamelt.rules.implementation.GenericCheck;
import com.datamelt.util.ClassUtility;
import com.datamelt.util.FieldNotFoundException;

/**
 * @author uwe geercken
 */
public class RuleSubGroup implements Serializable
{
	public static final long serialVersionUID = 1964070336;
	
	private String id;
    private String description;
    
    // operator is used to connect the rules in one subgroup to each
    // other. can be <and> or <or>
    private int logicalOperatorRules=-1;
    
    // operator is used to connect the subgroups to each
    // other. can be <and> or <or>
    private int logicalOperatorSubGroup=-1;
    
    // specifies if all or only failed or only passed rule results should be output
    private int outputType;
    
    public static final int OPERATOR_AND = 0;
    public static final int OPERATOR_OR  = 1;
    
    public static final String OPERATOR_AND_EXPRESSION = "and";
    public static final String OPERATOR_OR_EXPRESSION  = "or";
    
    // list of all rules that have been loaded for this subgroup
    private RuleCollection rulesCollection = new RuleCollection();
    //  list of errors that occurred in this subgroup
    private RuleExecutionCollection executionCollection = new RuleExecutionCollection();
    
    private String timestampFormat;
    private boolean preserveRuleExcecutionResults=true;
    
    public RuleSubGroup(String id, String description,String operatorSubGroup,String operatorRules)
    {
        this.id = id;
        this.description = description;
        this.setLogicalOperatorRules(operatorRules);
        this.setLogicalOperatorSubGroup(operatorSubGroup);
    }
    
    public String getDescription()
    {
        return description;
    }
    
    public void setDescription(String description)
    {
        this.description = description;
    }
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public RuleCollection getRulesCollection()
    {
        return rulesCollection;
    }
    
    public void setRulesCollection(RuleCollection rulesCollection)
    {
        this.rulesCollection = rulesCollection;
    }
    
    /**
     * method runs all rules against the object that the rule has to be
     * checked against.
     * 
     * first the class and method are created using reflection and the relevant
     * value from the object is retrieved.
     * next an object from the business rule (from the xml file) is created using reflection
     * and the value is retrieved.
     * then any additional parameters from the rule are transformed into objects, again
     * using reflection.
     * lastly, both values (objects) plus the additional parameters (objects) are passed to the [evaluate] method
     * of an object that extends the GenericCheck class.
     * 
     * the result will be a boolean true or false, depending if the rule passed the test or not. if the result of
     * the rule is not a boolean, an exception is thrown.
     * 
     * all results running the rule engine are put in a collection, containing the rule that was run and the object the rule ran against.
     * 
     * @param	objectLabel		the label of the object
     * @param	object			the object to use
     * @throws	Exception		exception if the rules can not be run
     */	
    public void runRules(String objectLabel, Object object) throws Exception
    {
        // clear the list of errors
        executionCollection.clear();

        SimpleDateFormat sdf = new SimpleDateFormat(timestampFormat);
        
        // process all rules from all parsed xml files
        for(int i=0;i<rulesCollection.getRules().size();i++)
        {
            // get a xmlrule
            // this is the xml rule/business rule as defined in the external xml file
            XmlRule rule = (XmlRule)rulesCollection.getRules().get(i).clone(); 
            
            // we pass the value cache to the generic check
            rule.getExecuteCheck();
            GenericCheck.setValueCache(rule.getValueCache());
            
            // create object from the object as defined in the xml file
            Object result1=null;
            try
            {
            	result1 = createObjectFromXmlObject(rule.getRuleObjects().get(0),object);
            }
            catch(FieldNotFoundException fnfe)
            {
            	throw new FieldNotFoundException("error rule: [" + rule.getId() + "] - field not found: "+ fnfe.getMessage());
            }
            catch(InvocationTargetException ite)
            {
            	throw new Exception("error rule: [" + rule.getId() + "] invoking method for first object: "+ ite.getTargetException());
            }
            catch(Exception ex)
            {
            	throw new Exception("error rule: [" + rule.getId() + "] creating first object from xml object");
            }
            
            // create second object from the object if we compare two objects
            // and when defined in the xml file
            Object result2 = null;
            if(rule.getRuleObjects().size()>1)
            {
            	try
            	{
            		result2 = createObjectFromXmlObject(rule.getRuleObjects().get(1),object);
            	}
            	catch(FieldNotFoundException fnfe)
                {
                	throw new FieldNotFoundException("error rule: [" + rule.getId() + "] - field not found: "+ fnfe.getMessage());
                }
                catch(InvocationTargetException ite)
                {
                	throw new Exception("error rule: [" + rule.getId() + "] invoking method for second object: "+ ite.getTargetException());
                }
            	catch(Exception ex)
            	{
            		throw new Exception("error rule: [" + rule.getId() + "] creating second object from xml object");
            	}

            }
            
            // create the method for the rule that is to be executed
            Method ruleMethodToExecute;
            try
            {
            	ruleMethodToExecute = createMethodFromXmlRule(rule);
            }
            catch(Exception ex)
            {
            	throw new Exception("error rule: [" + rule.getId() + "] creating method from xml rule " + ex.getMessage());
            }

            // create the objects that will be used for the method
            Object[] arguments;
            try
            {
            	arguments = createRuleMethodArguments(rule,result1,result2);
            }
            catch(Exception ex)
            {
            	throw new Exception("error rule: [" + rule.getId() + "] creating method arguments from xml rule " + ex.getMessage());
            }
            
            if(arguments[0]!=null)
            {
	            // invoke the rule
	            // attention: all of these methods are deemed to be static, that is why <null> is used as first parameter here
            	Object executeRuleResult;
            	try
            	{
            		executeRuleResult = ruleMethodToExecute.invoke(null,arguments);
            	}
            	catch(Exception ex)
            	{
            		throw new Exception("error invoking method on rule: [" + rule.getId() + "]: " + ex.getMessage());
            	}
	            
	            // the result of the invocation must be a boolean
	            if(executeRuleResult instanceof Boolean)
	            {
	            	// create a result object
	                RuleExecutionResult executionResult = new RuleExecutionResult(sdf.format(new Date()), rule,objectLabel,getId());
	                // the result from the data object
	                executionResult.setResultObject1(result1);
	                // the result from the data object
	                executionResult.setResultObject2(result2);
	                
	                // so its a boolean we need to cast it to use it further on
	                Boolean b = (Boolean)executeRuleResult;
	                boolean result = b.booleanValue();
	                if(result==true)
	                {
	                    // the execution of the rule was sucessful                    
	                    executionResult.getRule().setFailed(0);
	                    executionCollection.increaseRulesPassedCount();
	                }
	                else // the execution of the rule was unsucessful
	                {
	                    // set the rule failed indicator of the xmlrule
	                    executionResult.getRule().setFailed(1);
	                    executionCollection.increaseRulesFailedCount();
	                }
	                
                	executionCollection.add(executionResult);
	            }
	            else
	            {
	                // throw an error if the return type is not a boolean
	                throw new Exception("error rule: [" + rule.getId() + "] return type of rule result must always be of type boolean");
	            }
            }
            else
            {
            	// if the arguments object is null then this means that the conversion of the value/field to the requested type
            	// ended in an exception. meaning it is of the wrong type.
            	// so we mark the rule as failed, because we cannot compare the value to the rule.
            	
            	// attention: this is not true if we explicitly check for a null value with the "com.datamelt.rules.implementation.CheckIsNull" check.
            	// in this case the rule has passed.
            	
            	// create a result object
                RuleExecutionResult executionResult = new RuleExecutionResult(sdf.format(new Date()), rule,objectLabel, getId());
                // the result from the data object
              
                executionResult.setResultObject1(result1);
                // the result from the data object
                executionResult.setResultObject2(result2);
                
                // here is the exception
                if (rule.getCheckToExecute().equals("com.datamelt.rules.implementation.CheckIsNull") || rule.getCheckToExecute().equals("com.datamelt.rules.implementation.CheckIsEmpty"))
                {
                	executionResult.getRule().setFailed(0);
                	executionCollection.increaseRulesPassedCount();
                }
                else
                {
                	executionResult.getRule().setFailed(1);
                	executionCollection.increaseRulesFailedCount();
                }
                
                // add result to list.
               	executionCollection.add(executionResult);
            }
            executionCollection.increaseRulesRunCount();
        }
    }
    
    private Object createObjectFromXmlObject(RuleObject ruleObject,Object object)throws Exception
    {
        //  create the classes array corresponding to the parameters/arguments of the method
        Class<?> parameters[]=null;
		if(ruleObject.getParameterType()!=null)
		{
		    parameters = new Class[1];
		    parameters[0]= ClassUtility.getClass(ruleObject.getParameterType());
		}
        
        // create the actual value of the method parameter/argument
        Object parameterValues[] = null;
        if(ruleObject.getParameterType()!=null)
        {
            parameterValues = new Object[1];
            parameterValues[0] = ClassUtility.getObject(ruleObject.getParameterType(), ruleObject.getParameter());
        }
        
        // get the data from the object in question
        Method method = object.getClass().getMethod(ruleObject.getMethodName(),parameters);
        Object result = method.invoke(object,parameterValues);
        
        return result;
    }
    
    private Method createMethodFromXmlRule(XmlRule rule) throws Exception
    {
        Class<?>[] classes;
        // when [expected value] und [type] of the rule are empty, then this means that no (expected) value
        // is required. meaning that the rule does not compare two values against each other, but one value will
        // be checked only.
        //
        // example: test for <null> of a value.
        //
        if(rule.getExpectedValueRuleType()!=null && rule.getExpectedValueRule()!=null)
        {
        
	        // create an array of classes
	        // based on the number of parameters
	        classes = new Class[2 + rule.getParameters().size()];
	
	        // get the correct class for the actual value
	        classes[0] = ClassUtility.getClass(rule.getRuleObjects().get(0).getMethodReturnType());
	        
	        // get the correct class for the expected value
	        classes[1] = ClassUtility.getClass(rule.getExpectedValueRuleType());
	        
	        // get all additional parameters of the method
	        // and create an equivalent class
	        for (int i=0;i<rule.getParameters().size();i++)
	        {
	            Parameter parameter = (Parameter)rule.getParameters().get(i);
	            classes[2+i]= ClassUtility.getClass(parameter.getType());
	        }
        }
        else
        {
	        if(rule.getRuleObjects().size()==1)
	        {
	            // create an array of classes
		        // based on the number of parameters
		        classes = new Class[1 + rule.getParameters().size()];
		
	            // get the correct class for the actual value
	            classes[0] = ClassUtility.getClass(rule.getRuleObjects().get(0).getMethodReturnType());
	        }
	        else
	        {
	            // create an array of classes
		        // based on the number of parameters
		        classes = new Class[2 + rule.getParameters().size()];
		
	            // get the correct class for the actual value
	            classes[0] = ClassUtility.getClass(rule.getRuleObjects().get(0).getMethodReturnType());
	            
	            // get the correct class for the actual value
	            classes[1] = ClassUtility.getClass(rule.getRuleObjects().get(1).getMethodReturnType());
	        }
	        
	        // get all additional parameters of the method
	        // and create an equivalent class
	        for (int i=0;i<rule.getParameters().size();i++)
	        {
	            Parameter parameter = (Parameter)rule.getParameters().get(i);
	            classes[classes.length-rule.getParameters().size()+i] = ClassUtility.getClass(parameter.getType());
	        }
        }
        Method m = null;
        GenericCheck gc = null;
        try
        {
        	 gc = rule.getExecuteCheck();
        }
        catch (Exception sue)
        {
        	sue.printStackTrace();
        }
       	m = gc.getClass().getMethod(GenericCheck.GENERIC_CHECK_METHOD_EVALUATE,classes);
        return m;
    }
    
    /**
     * before the method can be invoked, an array of objects has to be created. these will be passed into
     * the method and with these objects/values the method will be executed.
     * 
     * @param	rule			the rule to use
     * @param	result			the first object to use
     * @param	result2			the second object to use
     * @return					an array of objects/arguments to pass to the method
     * @throws	Exception		exception if the arguments of the rule method can not be created
     */
    private Object[] createRuleMethodArguments(XmlRule rule, Object result, Object result2) throws Exception
    {
        // if the expected type/value are null/empty and the number of objects of the rule is 1
        // then we are not comparing two values but only one: e.g. a test for <null> of a given value.

    	Object[] objects = null;
    	
        // check how many objects we have defined in the rule
        int numberOfObjects = rule.getRuleObjects().size();
        // check if we have an expected value
        if(rule.getExpectedValueRuleType()!=null && rule.getExpectedValueRule()!=null)
        {
        	numberOfObjects++;
        }
        
        // total number of objects is the number of objects plus the additional parameters
        objects = new Object[numberOfObjects + rule.getParameters().size()];
        
        // get the actual type of the first object
        String resultType = ClassUtility.getObjectType(result);
        
        // we always have at least one object.
        // if the actual type and the type defined in the rule are different
        // then convert/cast the object
        if(resultType!=null && !resultType.equals(rule.getRuleObjects().get(0).getMethodReturnType()))
        {
        	objects[0] = ClassUtility.getObject(rule.getRuleObjects().get(0).getMethodReturnType(),(String)result);	
        }
        else
        {
        	objects[0] = result;
        }

        // if we have an expected value
        if(rule.getExpectedValueRuleType()!=null && rule.getExpectedValueRule()!=null)
        {
            // get the object for the expected value
            objects[1] = ClassUtility.getObject(rule.getExpectedValueRuleType(),rule.getExpectedValueRule());
        }
        else
        {
            if(rule.getRuleObjects().size()==2)
	        {
            	// get the actual type of the second object
                String resultType2 = ClassUtility.getObjectType(result2);
                
                // if the actual type and the type defined in the rule are different
                // then convert/cast the object
                if(!resultType2.equals(rule.getRuleObjects().get(1).getMethodReturnType()))
                {
                	objects[1] = ClassUtility.getObject(rule.getRuleObjects().get(1).getMethodReturnType(),(String)result2);
                }
                else
                {
                	objects[1] = result2;
                }
            }
        }
        
        // get all additional parameters of the rule 
        // and create an equivalent class for each of them
        for (int i=0;i<rule.getParameters().size();i++)
        {
            Parameter parameter = (Parameter)rule.getParameters().get(i);
            objects[numberOfObjects+i]= ClassUtility.getObject(parameter.getType(),parameter.getValue());
        }
        return objects;
    }
    
    
    /**
     * the rules of one subgroup can be joined using either a logical 'and' or a logical 'or'.
     * if the rules are connected with an 'and', then all rules must pass for the subgroup to pass.
     * if they are connected with an 'or' condition, then at least one must pass for the subgroup to pass.
     * 
     * @return	 [1] if failed or [0] if the subgroup passed
     */
    public int getFailed()
    {
        if(logicalOperatorRules == OPERATOR_AND)
        {
            if(getNumberOfRulesFailed()>0)
            {
                // at least one rule failed which means, that the subgroup
                // overall failed because of the <and> condition
                return 1;
            }
            else
            {
                // no errors, so the subgroup passed
                return 0;
            }
        }
        else
        {
            // or condition here. if none of the rules passed, respectively all rules
            // failed, then the subgroup failed
            if(rulesCollection.size()-getNumberOfRulesFailed()==0)
            {
                return 1;
            }
            else
            {
                // at least one rule passed. this is sufficient for the group
                // to pass because of the <or> condition
                return 0;
            }
        }
    }
    
    /** returns a string expression meaning [true] or [false]
     *  depending if the subgroup passed or failed. 
     *  
     *  @return				indicator if the subgroup failed
     *  @throws	Exception	exception if the string can not be constructed
     */
    public String getFailedAsString() throws Exception
    {
        return "[" + Converter.convertIntegerToBooleanString(getFailed()) + "]";
        
    }
    
    /**
     * returns a collection of errors that occurred
     * when running the rules
     * 
     * @return	the rule execution collection
     */
    public RuleExecutionCollection getExecutionCollection()
    {
        return executionCollection;
    }
    
    public ArrayList <RuleExecutionResult>getResults()
    {
        return executionCollection.getResults();
    }
    
    /**
     * returns the number of rules that failed
     * when running them for this subgroup
     * 
     * @return	the number of rules that failed in the subgroup
     */
    public long getNumberOfRulesFailed()
    {
        return executionCollection.getRulesFailedCount();
    }
    
    /**
     * returns the number of rules that passed
     * when running them for this subgroup
     * 
     * @return	the number of rules that passed in the subgroup
     */
    public long getNumberOfRulesPassed()
    {
        return executionCollection.getRulesPassedCount();
    }
    
    /**
     * returns the number of rules
     * 
     * @return	the number of rules that ran in the subgroup
     */
    public long getNumberOfRulesRun()
    {
        return executionCollection.getRulesRunCount();
    }

    /**
     * returns the integer representation of how
     * the rules of a subgroup are logically connected
     * with each other: [0] meaning [AND] or [1] meaning [OR]
     * 
     * @return	the logical operator that connects the rules in the subgroup
     */
    public int getLogicalOperatorRules()
    {
        return logicalOperatorRules;
    }
    
    /**
     * returns the string representation of how
     * the rules of a subgroup are logically connected
     * with each other: [and] or [or]
     * 
     * @return	the logical operator - as a string - that connects the rules in the subgroup
     */
    public String getLogicalOperatorRulesAsString()
    {
        if( logicalOperatorRules==0)
        {
            return OPERATOR_AND_EXPRESSION;
        }
        else
        {
            return OPERATOR_OR_EXPRESSION;
        }
    }
    
    /**
     * set the value of how the rules of a subgroup are
     * logically connected with each other: [0] or [1]
     * 
     * @param	logicalOperator		the logical operator used to connect the rules
     */
    public void setLogicalOperatorRules(int logicalOperator)
    {
        this.logicalOperatorRules = logicalOperator;
    }
    
    /**
     * set the value of how the rules of a subgroup are
     * logically connected with each other.
     * must be either [and] or [or]
     * 
     * @param	operatorRules		the logical operator used to connect the rules
     */
    public void setLogicalOperatorRules(String operatorRules)
    {
        if (operatorRules.toLowerCase().equals(OPERATOR_OR_EXPRESSION))
        {
            logicalOperatorRules = OPERATOR_OR;
        }
        else
        {
            logicalOperatorRules = OPERATOR_AND;
        }
    }
    
    /**
     * returns the integer representation - [0] or [1]
     * of how this subgroup is connected to the PREVIOUS subgroup.
     * 
     * @return		the logical operator used to connect the subgroups
     */
    public int getLogicalOperatorSubGroup()
    {
        return logicalOperatorSubGroup;
    }
    
    /**
     * returns the String representation - [and] or [or]
     * of how this subgroup is connected to the previous subgroup.
     * 
     * @return		the logical operator - as a string - used to connect the subgroups
     */
    public String getLogicalOperatorSubGroupAsString()
    {
        if(logicalOperatorSubGroup==0)
        {
            return OPERATOR_AND_EXPRESSION;
        }
        else if(logicalOperatorSubGroup==1)
        {
            return OPERATOR_OR_EXPRESSION;
        }
        else
        {
        	return "";
        }
    }
    
    /**
     * set the value of how the subgroup is logically connected
     * to the previous one: [0] or [1]
     * 
     * @param	logicalOperator		the logical operator used to connect the subgroups
     */
    public void setLogicalOperatorSubGroup(int logicalOperator)
    {
        this.logicalOperatorSubGroup = logicalOperator;
    }
    
    /**
     * set the value of how the subgroup is logically connected
     * to the previous one: [and] or [or]
     * 
     * @param	operator		the logical operator used to connect the subgroups
     */
    public void setLogicalOperatorSubGroup(String operator)
    {
        if (operator!=null && operator.toLowerCase().equals(OPERATOR_OR_EXPRESSION))
        {
            logicalOperatorSubGroup = OPERATOR_OR;
        }
        else if (operator!=null && operator.toLowerCase().equals(OPERATOR_AND_EXPRESSION))
        {
            logicalOperatorSubGroup = OPERATOR_AND;
        }
    }
    
    public String getRuleLogic()
    {
        StringBuffer buffer = new StringBuffer("(");
        if (rulesCollection.size()>0)
        {
	        XmlRule rule0 = (XmlRule)rulesCollection.get(0);
	        buffer.append(rule0.getId());
	        for(int i=1;i<rulesCollection.size();i++)
	        {
	            XmlRule rule = (XmlRule)rulesCollection.get(i);
	            buffer.append(" " + Converter.convertIntToLogical(logicalOperatorRules) + " " + rule.getId());
	        }
        }
        buffer.append(")");
        return buffer.toString();
    }

    /**
     * specifies if all rule results should be output (2), only for failed rules (0),
     * only for passed rules (1). default is 0.
     * 
     * @return		the output type
     */
    public int getOutputType()
    {
        return outputType;
    }
    
    /**
     * sets if all rule results should be output (2), only for failed rules (0),
     * only for passed rules (1). default is 0.
     * 
     * @param	outputType the output type to use
     */
    public void setOutputType(int outputType)
    {
        this.outputType = outputType;
    }
    
    /**
     * returns the format of the timestamp used for timestamp formating
     * 
     * @return	the definition of the format of the timestamp
     */
    public String getTimestampFormat()
    {
        return timestampFormat;
    }
    
    /**
     * sets the format of the timestamp used for timestamp formating.
     * follows the rules of the java.text.SimpleDateFormat class
     * 
     * @param	timestampFormat	the format of the timestamp
     */
    public void setTimestampFormat(String timestampFormat)
    {
        this.timestampFormat = timestampFormat;
    }

    /**
     * indicator if the results of the rule execution is preserved/kept
     * or not
     * 
     * @return	indicator if results are preserved
     */
	public boolean isPreserveRuleExcecutionResults()
	{
		return preserveRuleExcecutionResults;
	}

    /**
     * indicator if the results of the rule execution is preserved/kept
     * or not
     * 
     * @param	preserveRuleExcecutionResults	indicator if results should be preserved
     */
	public void setPreserveRuleExcecutionResults(boolean preserveRuleExcecutionResults)
	{
		this.preserveRuleExcecutionResults = preserveRuleExcecutionResults;
		this.executionCollection.setPreserveRuleExcecutionResults(preserveRuleExcecutionResults);
	}

}
