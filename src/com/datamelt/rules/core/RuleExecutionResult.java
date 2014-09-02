/*
 * Created on 07.11.2006
 *
 * all code by uwe geercken
 */
package com.datamelt.rules.core;

import com.datamelt.rules.core.util.Converter;


/**
 * this class carries the results of the execution of one rule.
 * parts of this class are the rule it self, the involved objects
 * that were constructed from the xml definition file.
 * 
 * for identifying the object in the output an objectlabel - a simple string -
 * is used. a timestamp is assigned when the rule was executed.
 * 
 * @author uwe geercken
 */
public class RuleExecutionResult
{
    private XmlRule rule;
    private Object resultObject1;
    private Object resultObject2;
    private String objectLabel;
    private String timestamp;
    
    /**
     * constructor including the timestamp of when the rule was executed
     * 
     */
    public RuleExecutionResult(String timestamp)
    {
        this.timestamp = timestamp;
    }
    
    /**
     * constructor for this class using the timestamp, the rule and a label
     * for the rule used during output.
     */
    public RuleExecutionResult(String timestamp, XmlRule rule, String objectLabel)
    {
        this.timestamp = timestamp;
        this.rule = rule;
        this.objectLabel = objectLabel;
    }

    /**
     * returns the rule that was executed 
     */
    public XmlRule getRule()
    {
        return rule;
    }
    
    /**
     * sets the rule that was executed 
     */
    public void setRule(XmlRule rule)
    {
        this.rule = rule;
    }
    
    /**
     * returns the first object that was constructed 
     */
    public Object getResultObject1()
    {
        return resultObject1;
    }
    
    /**
     * sets the first object that was executed 
     */
    public void setResultObject1(Object result)
    {
        this.resultObject1 = result;
    }
    
    /**
     * gets the second object that was executed 
     */
    public Object getResultObject2()
    {
        return resultObject2;
    }
    
    /**
     * sets the second object that was executed 
     */
    public void setResultObject2(Object result)
    {
        this.resultObject2 = result;
    }

    /**
     * a rule has a message - defined in the xml file - in case it fails and one message in case it passes.
     * this method returns the message assigned to the rule. at the same time variables in the message text
     * are replaced with values from the objects that were constructed.
     * 
     */
    public String getMessage()
    {
        String messageText="";
        // get message for failed rule
        try
        {
            RuleMessage message = rule.getMessage(getFailed());
            messageText = message.getText();
        }
        catch(Exception ex)
        {
            messageText="[undefined message]";
        }
        String resultString1="";
        try
        {
        	resultString1 = resultObject1.toString();
        }
        catch(Exception ex)
        {
        	if(rule.getExpectedValueRuleType() == null)
        	{
        		resultString1 = "null";
        	}
        	else
        	{
        		resultString1 = "invalid type conversion: " + "[" + rule.getExpectedValueRuleType() +"]";
        	}
        }

        if(messageText!=null)
        {
	        if(rule.getExpectedValueRule()!=null && rule.getExpectedValueRuleType()!=null)
	        {
	            if(rule.getRuleObjects().get(0).getParameter()!=null)
	            {
	                messageText =  messageText.replaceAll("\\$1","[" + resultString1 +"]");
	            }
	            messageText =  messageText.replaceAll("\\$0","[" + rule.getExpectedValueRule()+"]");
	        }
	        else
	        {
	            if(rule.getRuleObjects().size()==2)
	            {
	            	String resultString2="";
	                try
	                {
	                	resultString2 = resultObject2.toString();
	                }
	                catch(Exception ex)
	                {
	                	if(rule.getExpectedValueRuleType()==null)
	                	{
	                		return "null";
	                	}
	                	else
	                	{
	                		resultString2 = "invalid type conversion: " + "[" + rule.getExpectedValueRuleType() + "]";
	                	}
	                }
	            	if(rule.getRuleObjects().get(0).getParameter()!=null)
	                {
	                    messageText =  messageText.replaceAll("\\$0","[" + resultString1 +"]");
	                }
		            if(rule.getRuleObjects().get(1).getParameter()!=null)
		            {
		                messageText =  messageText.replaceAll("\\$1","[" + resultString2 + "]" );
		            }
	            }
	            else
	            {
	                if(rule.getRuleObjects().get(0).getParameter()!=null)
	                {
	                    messageText =  messageText.replaceAll("\\$1","[" + resultString1 +"]");
	                }
	            	
	            }
	        }
        }
        return messageText;
    }

    /**
     * returns the object label that was assign for use
     * during output
     */
    public String getObjectLabel()
    {
        return objectLabel;
    }
    
    /**
     * sets the object label that will be used
     * during output
     */
    public void setObjectLabel(String objectLabel)
    {
        this.objectLabel = objectLabel;
    }

    /**
     * returns the timestamp that was assign to this object 
     */
    public String getTimestamp()
    {
        return timestamp;
    }
    
    /**
     * returns if the rule that belongs to this result failed or passed
     * the test 
     */
    public int getFailed()
    {
        return rule.getFailed();  // ruleFailed;
     }
    
    /** returns a string expression meaning [true] or [false]
     *  depending if the rule that belongs to this result passed or failed. 
     */
    public String getFailedAsString()
    {
        return "[" + Converter.convertIntegerToBooleanString(rule.getFailed()) + "]";
        
    }
}
