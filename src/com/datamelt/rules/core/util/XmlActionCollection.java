package com.datamelt.rules.core.util;

import java.util.ArrayList;
import com.datamelt.rules.core.XmlAction;

public class XmlActionCollection
{
private ArrayList<XmlAction> actions = new ArrayList<XmlAction>();
    
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
