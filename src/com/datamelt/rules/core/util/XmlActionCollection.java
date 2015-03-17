package com.datamelt.rules.core.util;

import java.io.Serializable;
import java.util.ArrayList;

import com.datamelt.rules.core.XmlAction;

public class XmlActionCollection implements Serializable
{
	private ArrayList<XmlAction> actions = new ArrayList<XmlAction>();
    
	public static final long serialVersionUID = 1964070339;
	
    public void add(XmlAction action)
    {
        actions.add(action);
    }
    
    public XmlAction get(int index)
    {
        return (XmlAction)actions.get(index);
    }
    
    public void remove(int index)
    {
    	actions.remove(index);
    }
    
    public void clear()
    {
    	actions.clear();
    }
    
    public int size()
    {
        return actions.size();
    }
}
