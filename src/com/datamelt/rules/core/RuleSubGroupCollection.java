/*
 * Created on 08.11.2006
 *
 * all code by uwe geercken
 */
package com.datamelt.rules.core;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * collection of RuleSubGroups which belong to a certain RuleGroup
 * 
 * @author uwe geercken
 */
public class RuleSubGroupCollection implements Serializable
{
	private ArrayList<RuleSubGroup> subGroups = new ArrayList<RuleSubGroup>();
    
	public static final long serialVersionUID = 1964070337;
	
    public void add(RuleSubGroup subGroup)
    {
        subGroups.add(subGroup);
    }
    
    public RuleSubGroup get(int index)
    {
        return (RuleSubGroup)subGroups.get(index);
    }
    
    public int size()
    {
        return subGroups.size();
    }
    
    public void clear()
    {
        subGroups.clear();
    }
    
    public ArrayList<RuleSubGroup> getSubGroups()
    {
        return subGroups;
    }
}
