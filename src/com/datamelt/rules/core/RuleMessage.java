/*
 * Created on 23.02.2007
 *
 * all code by uwe geercken
 */
package com.datamelt.rules.core;

import java.io.Serializable;


/**
 * this class is used for the messages that are used for a rule. a rule can have
 * a message in case it fails and one in case it passes.
 * 
 * message can have placeholders in the text, which will be replaced by real values
 * from the objects in use. 
 * 
 * @author uwe geercken
 */
public class RuleMessage implements Serializable
{
	private String text;
    private int type;
    
    public static final int TYPE_PASSED = 0;
    public static final int TYPE_FAILED = 1;
    
    public static final long serialVersionUID = 1964070332;
    
    /**
     * constructor using the type of the message (failed or passed) and the
     * text of the message 
     * 
     * @param	type	the type of the message
     * @param	text	the text of the message
     */
    public RuleMessage(int type, String text)
    {
        this.type = type;
        this.text = text;
    }
    
    /**
     * constructor using the type of the message (failed or passed)
     * 
     * @param	type	the type of the message
     */
    public RuleMessage(int type)
    {
        this.type = type;
    }
    
    /**
     * used to retrieve the text of the message 
     * 
     * @return	the text of the message
     */
    public String getText()
    {
        return text;
    }
    
    /**
     * sets the text of the message 
     * 
     * @param	text	the message to use
     */
    public void setText(String text)
    {
        this.text = text;
    }
    
    /**
     * rules always have two messages one message if the rule fails and one if it passes.
     * method returns the type (passed or failed) of the message 
     * 
     * 
     * @return	the type of the message
     */
    public int getType()
    {
        return type;
    }
}
