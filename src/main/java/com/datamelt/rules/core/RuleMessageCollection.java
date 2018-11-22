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

/**
 * collection of messages that belong to a rule in form of
 * RuleMessage objects. These have been parsed from the rule
 * xml files.
 * 
 * @author uwe geercken
 */
public class RuleMessageCollection implements Serializable
{
	private ArrayList<RuleMessage> messages = new ArrayList<RuleMessage>();
    
	public static final long serialVersionUID = 1964070333;
	
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
