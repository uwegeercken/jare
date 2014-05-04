/*
 * Created on 16.11.2006
 *
 * all code by uwe geercken
 */
package com.datamelt.rules.core;

import java.util.ArrayList;

/**
 * collection of messages that belong to a rule in form of
 * RuleMessage objects. These have been parsed from the rule
 * xml files.
 * 
 * @author uwe geercken
 */
public class RuleMessageCollection
{
    private ArrayList<RuleMessage> messages = new ArrayList<RuleMessage>();
    
    public void add(RuleMessage message)
    {
        messages.add(message);
    }
    
    public RuleMessage get(int index)
    {
        return (RuleMessage)messages.get(index);
    }
    
    public RuleMessage getByType(int type)throws Exception
    {
        int index = -1;
        for(int i=0;i<messages.size();i++)
        {
            RuleMessage message = (RuleMessage)messages.get(i);
            if(message.getType()==type)
            {
                index = i;
                break;
            }
        }
        if(index==-1)
        {
            throw new Exception("message not found for type=" + type);
        }
        return (RuleMessage)messages.get(index);
    }

    public void remove(int index)
    {
        messages.remove(index);
    }
    
    public void clear()
    {
        messages.clear();
    }
    
    public int size()
    {
        return messages.size();
    }
    
    public ArrayList<RuleMessage> getMessages()
    {
        return messages;
    }
}
