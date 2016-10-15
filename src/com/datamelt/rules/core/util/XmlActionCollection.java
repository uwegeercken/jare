package com.datamelt.rules.core.util;

import java.io.Serializable;
import java.util.ArrayList;

import com.datamelt.rules.core.XmlAction;
/**
 * class collect XmlAction objects
 * 
 * @author uwe geercken
 * 
 */
public class XmlActionCollection implements Serializable
{
	// list of action objects
	private ArrayList<XmlAction> actions = new ArrayList<XmlAction>();
    
	public static final long serialVersionUID = 1964070339;
	
	/**
	 * Add an action to the collection
	 * 
	 * @param action 		the action to add to the collection
     */
    public void add(XmlAction action)
    {
        actions.add(action);
    }
    
    /**
	 * Get an action from the collection
	 * 
	 * @param index 	the index of the action in the collection
	 * @return			an action object
     */
    public XmlAction get(int index)
    {
        return (XmlAction)actions.get(index);
    }
    
    /**
	 * Remove an action from the collection
	 * 
	 * @param index 	the index of the action in the collection
     */
    public void remove(int index)
    {
    	actions.remove(index);
    }
    
    /**
	 * Remove all actions from the collection
	 * 
     */
    public void clear()
    {
    	actions.clear();
    }
    
    /**
	 * Get the number of actions currently in the collection
	 * 
	 * @return			number of actions
     */
    public int size()
    {
        return actions.size();
    }
}
