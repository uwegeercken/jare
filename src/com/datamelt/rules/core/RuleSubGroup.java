/*
 * Created on 08.11.2006
 *
 * all code by uwe geercken
 */
package com.datamelt.rules.core;

import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.datamelt.rules.core.util.Converter;
import com.datamelt.rules.implementation.GenericCheck;
import com.datamelt.util.ClassUtility;
import com.datamelt.util.FieldNotFoundException;
import com.datamelt.util.VelocityDataWriter;

/**
 * @author uwe geercken
 */
public class RuleSubGroup
{
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
    
    private PrintStream printStream;
    private VelocityDataWriter writer;
    
    private String timestampFormat;
    
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
     * then any additional parameters (from the xml file) are transformed into objects, again
     * using reflection.
     * lastly, both values (objects) plus the additional parameters (objects) are passed to the [evaluate] method
     * of an object that extends the GenericCheck class.
     * 
     * the result will be a boolean true or false, depending if the rule passed the test or not. if the result of
     * the rule is not a boolean, an exception is thrown.
     * 
     * all results running the rule engine are put in a collection, containing the rule that was run and the object the rule ran against.
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
            XmlRule rule = (XmlRule)rulesCollection.getRules().get(i); 
            
            // create object from the object as defined in the xml file
            Object result1=null;
            try
            {
            	result1 = createObjectFromXmlObject(rule.getRuleObjects().get(0),object);
            }
            catch(FieldNotFoundException fnfe)
            {
            	throw new FieldNotFoundException("error rule: [" + rule.getId() + "] creating field object: "+ fnfe.getMessage());
            }
            catch(InvocationTargetException ite)
            {
            	throw new Exception("error rule: [" + rule.getId() + "] invoking method: "+ ite.getTargetException());
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
                	throw new FieldNotFoundException("error rule: [" + rule.getId() + "] creating field object: "+ fnfe.getMessage());
                }
                catch(InvocationTargetException ite)
                {
                	throw new Exception("error rule: [" + rule.getId() + "] invoking method: "+ ite.getTargetException());
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
            		throw new Exception("error rule: [" + rule.getId() + "] invoking method: " + ex.getMessage());
            	}
	            
	            // the result of the invokation must be a boolean
	            if(executeRuleResult instanceof Boolean)
	            {
	            	// create a result object
	                RuleExecutionResult executionResult = new RuleExecutionResult(sdf.format(new Date()), rule,objectLabel);
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
	                }
	                else // the execution of the rule was unsucessful
	                {
	                    // set the rule failed indicator of the xmlrule
	                    executionResult.getRule().setFailed(1);
	                }
	                
	                // add result to list.
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
                RuleExecutionResult executionResult = new RuleExecutionResult(sdf.format(new Date()), rule,objectLabel);
                // the result from the data object
              
                executionResult.setResultObject1(result1);
                // the result from the data object
                executionResult.setResultObject2(result2);
                
                // here is the exception
                if (rule.getCheckToExecute().equals("com.datamelt.rules.implementation.CheckIsNull"))
                {
                	executionResult.getRule().setFailed(0);
                }
                else
                {
                	executionResult.getRule().setFailed(1);
                }
                
                // add result to list.
                executionCollection.add(executionResult);
            }
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
     */
    private Object[] createRuleMethodArguments(XmlRule rule, Object result, Object resultObject2) throws Exception
    {
        Object[] objects = null;
        // when value und type der rule leer/null sind, bedeutet dies, dass kein expected value
        // erwartet wird. d.h es werden nicht zwei werte miteinander verglichen, sondern lediglich ein
        // wert geprüft.
        // beispiel: test auf <null> eines wertes.
        //
        if(rule.getExpectedValueRuleType()!=null && rule.getExpectedValueRule()!=null)
        {
            // create an array of classes
            // with at least 2 parameters
	        // based on the number of parameters
	        objects = new Object[2 + rule.getParameters().size()];
	
	        // the actual value goes into the first parameter
	        objects[0] = result;
	        
            // get the correct class for the expected value
            objects[1] = ClassUtility.getObject(rule.getExpectedValueRuleType(),rule.getExpectedValueRule());

            // get all additional parameters of the method
	        // and create an equivalent class
	        for (int i=0;i<rule.getParameters().size();i++)
	        {
	            Parameter parameter = (Parameter)rule.getParameters().get(i);
	            objects[2+i]= ClassUtility.getObject(parameter.getType(),parameter.getValue());
	        }
        }
        else
        {
            if(resultObject2==null)
	        {
	            // create an array of classes
	            // with at least 2 parameters
		        // based on the number of parameters
		        objects = new Object[1 + rule.getParameters().size()];
		
		        // the actual value goes into the first parameter
		        objects[0] = result;
	        }
            else
            {
                // create an array of classes
	            // with at least 2 parameters
		        // based on the number of parameters
		        objects = new Object[2 + rule.getParameters().size()];
		
		        // the actual value goes into the first parameter
		        objects[0] = result;
		        
		        // the expected value goes into the second parameter
		        objects[1] = resultObject2;
            }
	        // get all additional parameters of the method
	        // and create an equivalent class
	        for (int i=0;i<rule.getParameters().size();i++)
	        {
	            Parameter parameter = (Parameter)rule.getParameters().get(i);
	            objects[objects.length-rule.getParameters().size()+i]= ClassUtility.getObject(parameter.getType(),parameter.getValue());
	        }
        }
        
        return objects;
    }
    
    
    /**
     * the rules of one subgroup can be joined using either a logical <and> or a logical <or>.
     * if the rules are connected with an <and>, then all rules must pass for the subgroup to pass.
     * if they are connected with an <or> condition, then at least one must pass for the subgroup to pass.
     * 
     * returns [1] if failed or [0] if the subgroup did not fail
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
     */
    public String getFailedAsString() throws Exception
    {
        return "[" + Converter.convertIntegerToBooleanString(getFailed()) + "]";
        
    }
    
    /**
     * returns a collection of errors that occurred
     * when running the rules
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
     */
    public int getNumberOfRulesFailed()
    {
        return executionCollection.getFailedRulesCount();
    }

    /**
     * returns the integer representation of how
     * the rules of a subgroup are logically connected
     * with each other: [0] meaning [AND] or [1] meaning [OR]
     */
    public int getLogicalOperatorRules()
    {
        return logicalOperatorRules;
    }
    
    /**
     * returns the string representation of how
     * the rules of a subgroup are logically connected
     * with each other: [and] or [or]
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
     */
    public void setLogicalOperatorRules(int logicalOperator)
    {
        this.logicalOperatorRules = logicalOperator;
    }
    
    /**
     * set the value of how the rules of a subgroup are
     * logically connected with each other.
     * must be either [and] or [or]
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
     * of how this subgroup is connected to the previous subgroup.
     */
    public int getLogicalOperatorSubGroup()
    {
        return logicalOperatorSubGroup;
    }
    
    /**
     * returns the String representation - [and] or [or]
     * of how this subgroup is connected to the previous subgroup.
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
     */
    public void setLogicalOperatorSubGroup(int logicalOperator)
    {
        this.logicalOperatorSubGroup = logicalOperator;
    }
    
    /**
     * set the value of how the subgroup is logically connected
     * to the previous one: [and] or [or]
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
     * returns the output stream used for his object
     */
    public PrintStream getPrintStream()
    {
        return printStream;
    }
    
    /**
     * sets the outputstream for his object 
     * 
     */
    public void setOutputStream(PrintStream stream)
    {
        this.printStream = stream;
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
        if(writer!=null)
        {
            this.writer = writer;
            writer.setOutputStream(printStream);
        }
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

}
