/*
 * Created on 08.11.2006
 *
 * all code by uwe geercken
 */
package com.datamelt.rules.core;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * collection of rules in the form of XmlRule objects. These
 * objects were filled by parsing the respective rule xml files.
 * 
 * @author uwe geercken
 */
public class RuleCollection implements Serializable
{
	// list of rules
	private ArrayList<XmlRule> rules = new ArrayList<XmlRule>();
    
	public static final long serialVersionUID = 1964070328;
	
	/**
	 * adds a rule to the list
	 * @param rule	add the rule to the list
	 */
    public void add(XmlRule rule)
    {
        rules.add(rule);
    }
    
    /**
     * gets a rule from the list
     * @param index		the index of the rule to retrieve
     * @return			a rule
     */
    public XmlRule get(int index)
    {
        return (XmlRule)rules.get(index);
    }
    
    /**
     * gets the number of rules in the list
     * 
     * @return	the number of rules
     */
    public int size()
    {
        return rules.size();
    }
    
    /**
     * clear the list
     */
    public void clear()
    {
        rules.clear();
    }
    
    /**
     * gets the list of rules
     * @return	the list of rules
     */
    public ArrayList<XmlRule> getRules()
    {
        return rules;
    }
}
