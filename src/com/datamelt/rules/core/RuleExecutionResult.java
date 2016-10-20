/*
 * Created on 07.11.2006
 *
 * all code by uwe geercken
 */
package com.datamelt.rules.core;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.datamelt.rules.core.util.Converter;


/**
 * this class carries the results of the execution of one rule.
 * parts of this class are the rule itself, the involved objects
 * that were constructed from the xml definition file.
 * 
 * for identifying the object in the output an objectlabel - a simple string -
 * is used. a timestamp is assigned when the rule was executed.
 * 
 * @author uwe geercken
 */
public class RuleExecutionResult implements Serializable
{
	private XmlRule rule;
    private Object resultObject1;
    private Object resultObject2;
    private String objectLabel;
    private String timestamp;
    private String subgroupId;
    
    public static final long serialVersionUID = 1964070330;
    
    /**
     * constructor including the timestamp of when the rule was executed
     * 
     * @param timestamp		the timestamp for the execution result
     */
    public RuleExecutionResult(String timestamp)
    {
        this.timestamp = timestamp;
    }
    
    /**
     * constructor for this class using the timestamp, the rule and a label
     * for the rule used during output.
     * 
     * @param timestamp		the timestamp for the execution result
     * @param rule			the rule belonging to the execution result
     * @param objectLabel	the label for the object
     * @param subgroupId	the id of the subgroup
     */
    public RuleExecutionResult(String timestamp, XmlRule rule, String objectLabel, String subgroupId)
    {
        this.timestamp = timestamp;
        this.rule = rule;
        this.objectLabel = objectLabel;
        this.subgroupId = subgroupId;
    }

    /**
     * returns the rule that was executed 
     * 
     * @return		the rule executed
     */
    public XmlRule getRule()
    {
        return rule;
    }
    
    /**
     * sets the rule that was executed 
     * 
     * @param rule 	the rule that was executed
     */
    public void setRule(XmlRule rule)
    {
        this.rule = rule;
    }
    
    /**
     * gets the first object that was constructed 
     * 
     * @return 	the first object that was constructed
     */
    public Object getResultObject1()
    {
        return resultObject1;
    }
    
    /**
     * sets the first object that was executed 
     * 
     * @param result 	the first object that was constructed
     */
    public void setResultObject1(Object result)
    {
        this.resultObject1 = result;
    }
    
    /**
     * gets the second object that was executed 
     * 
     * @return 	the second object that was constructed
     */
    public Object getResultObject2()
    {
        return resultObject2;
    }
    
    /**
     * sets the second object that was executed 
     * 
     * @param result 	the second object that was constructed
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
     * @return 	the appropriate message belonging to the rule with placeholders replaced by actual values
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
        	if(resultObject1!=null)
        	{
        		if(resultObject1 instanceof Date)
        		{
        			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        			resultString1 = sdf.format(resultObject1);
        		}
        		else
        		{
        			resultString1 = resultObject1.toString();
        		}
        	}
        	else
        	{
        		resultString1 = "null";
        	}
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
	        // replace some of the chracters that would fail the regular expression match
        	resultString1 = resultString1.replaceAll("\\\\", "/");
	        resultString1 = resultString1.replaceAll("\\$", "\\\\\\$");
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
	                	if(resultObject2!=null)
	                	{
	                		if(resultObject2 instanceof Date)
	                		{
	                			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	                			resultString2 = sdf.format(resultObject2);
	                		}
	                		else
	                		{
	                			resultString2 = resultObject2.toString();
	                		}
	                	}
	                	else
	                	{
	                		resultString2 = "null";
	                	}
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
	                    messageText =  messageText.replaceAll("\\$1","[" + resultString1 +"]");
	                }
		            if(rule.getRuleObjects().get(1).getParameter()!=null)
		            {
		                messageText =  messageText.replaceAll("\\$0","[" + resultString2 + "]" );
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
     * 
     * @return	the label of the object
     */
    public String getObjectLabel()
    {
        return objectLabel;
    }
    
    /**
     * sets the object label that will be used
     * during output
     * 
     * @param objectLabel	the label of the object
     */
    public void setObjectLabel(String objectLabel)
    {
        this.objectLabel = objectLabel;
    }

    /**
     * returns the timestamp that was assign to rule execution result
     * 
     * @return 	the timestamp of the rule execution result
     */
    public String getTimestamp()
    {
        return timestamp;
    }
    
    /**
     * returns if the rule that belongs to this result failed or passed
     * the test 
     * 
     * @return	indicator if the rule failed or passed
     */
    public int getFailed()
    {
        return rule.getFailed();  // ruleFailed;
    }
    
    /**
     * returns if the rule that belongs to this result failed or passed
     * the test 
     * 
     * @return	indicator if the rule failed or passed
     */
    public boolean isFailed()
    {
        return rule.getFailed()==1;  // ruleFailed;
    }
    
    /** returns a string expression meaning [true] or [false]
     *  depending if the rule that belongs to this result passed or failed. 
     *  
     *  @return 	indicator if the rule passed or failed as true or false
     */
    public String getFailedAsString()
    {
        return "[" + Converter.convertIntegerToBooleanString(rule.getFailed()) + "]";
        
    }

    /**
     * get the id of the subgroup which the rule belongs to
     * 
     * @return the id of the subgroup
     */
	public String getSubgroupId() 
	{
		return subgroupId;
	}

	/**
     * set the id of the subgroup which the rule belongs to
     * 
     * @param subgroupId	the id of the subgroup
     */
	public void setSubgroupId(String subgroupId) 
	{
		this.subgroupId = subgroupId;
	}
    
    
}
