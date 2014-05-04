/*
 * Created on 08.11.2006
 *
 * all code by uwe geercken
 */
package com.datamelt.rules.core;

import java.util.ArrayList;

/**
 * collection of rules in the form of XmlRule objects. These
 * objects were filled by parsing the respective rule xml files.
 * 
 * @author uwe geercken
 */
public class RuleCollection
{
    private ArrayList<XmlRule> rules = new ArrayList<XmlRule>();
    
    public void add(XmlRule rule)
    {
        rules.add(rule);
    }
    
    public XmlRule get(int index)
    {
        return (XmlRule)rules.get(index);
    }
    
    public int size()
    {
        return rules.size();
    }
    
    public void clear()
    {
        rules.clear();
    }
    
    public ArrayList<XmlRule> getRules()
    {
        return rules;
    }
}
