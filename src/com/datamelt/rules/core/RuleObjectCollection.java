/*
 * Created on 16.11.2006
 *
 * all code by uwe geercken
 */
package com.datamelt.rules.core;

import java.util.ArrayList;

/**
 * collection of RuleObject objects that are created from parsing the
 * rule definition xml file(s). These objects are later in the process
 * used to compare an expected result against the actual result.
 * 
 * @author uwe geercken
 */
public class RuleObjectCollection
{
    private ArrayList<RuleObject> ruleObjects = new ArrayList<RuleObject>();
    
    public void add(RuleObject ruleObject)
    {
        ruleObjects.add(ruleObject);
    }
    
    public RuleObject get(int index)
    {
        return (RuleObject)ruleObjects.get(index);
    }
    
    public void remove(int index)
    {
        ruleObjects.remove(index);
    }
    
    public void clear()
    {
        ruleObjects.clear();
    }
    
    public int size()
    {
        return ruleObjects.size();
    }
}
