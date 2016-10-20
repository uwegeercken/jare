package com.datamelt.rules.core;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * this class holds the information for a specific action as defined in the xml file
 * 
 * 
 * @author uwe geercken
 *
 */
public class XmlAction implements Serializable
{
	public static final int TYPE_PASSED = 0;
    public static final int TYPE_FAILED = 1;
    public static final int TYPE_ALWAYS = 2;
    
    public static final long serialVersionUID = 1964070338;
    
    private String id;
    private String description;
    // name of the class to invoke
	private String className;
	// name of the method to invoke
	private String methodName;
	// indicator of when to invoke the action
	private int executeIf;
	// contains a list of parameters to invoke the action
	private ArrayList <Parameter>parameters = new ArrayList<Parameter>();
	// list of objects required to invoke the method
	private ArrayList<ActionObject> actionGetterObjects = new ArrayList<ActionObject>();
	private ActionObject actionSetterObject = null;

	/**
     * Constructor using the id and description of the rule. 
     * 
     * @param	id			the id of the action
     * @param	description	the description of the action
     */
    public XmlAction(String id, String description)
    {
        this.id = id;
        this.description = description;
    }
    
	/**
	 * returns the name of the action class to run
	 * 
	 * @return	the name of the class
	 */
	
	public String getClassName()
	{
		return className;
	}

	/**
	 * sets the name of the action class to run
	 * 
	 * @param className	the name of the class
	 * 
	 */
	public void setClassName(String className)
	{
		this.className = className;
	}
	
	 /**
     * returns the list of parameters that have to be passed to the method
     * to execute 
     * 
     * @return	the list of parameters for this action
     */
    public ArrayList <Parameter>getParameters()
    {
        return parameters;
    }
    
    /**
     * sets the list of parameters that have to be passed to the method
     * to execute 
     * 
     * @param	parameters	the list of parameters for this action
     */
    public void setParameters(ArrayList<Parameter> parameters)
    {
        this.parameters = parameters;
    }
    
    /**
     * add another parameter to the list of parameters of the method
     * 
     * @param	parameter	the parameter to add
     */
    public void addParameter(Parameter parameter)
    {
        parameters.add(parameter);
    }

    /**
     * indicator when the action will run: if the group passed or if it failed
     * 
     * @return	indicator when the action will be run
     * 
     */
	public int getExecuteIf()
	{
		return executeIf;
	}

	/**
	 * sets the indicator when the action should run: if the group passed or if
	 * the group failed
	 * 
	 * @param	executeIf	indicator when the action will be run
	 * 
	 */
	public void setExecuteIf(int executeIf)
	{
		this.executeIf = executeIf;
	}

	/**
	 * returns the name of the method to run
	 * 
	 * @return	the name of the method to run
	 */
	public String getMethodName()
	{
		return methodName;
	}

	/**
	 * sets the name of the method to run
	 * 
	 * @param	methodName	name of the method to run
	 * 
	 */
	public void setMethodName(String methodName)
	{
		this.methodName = methodName;
	}
	
	/**
	 * returns the list of objects to get data that have been collected from the xml file
	 * and are required to run the action
	 * 
	 * @return	the list of getter objects
	 */
	public ArrayList<ActionObject> getActionGetterObjects()
	{
		return actionGetterObjects;
	}

	/**
	 * returns the list of objects to set (modify) data that have been collected from the xml file
	 * and are required to run the action
	 * 
	 * 	@return	the list of setter objects
	 */
	public ActionObject getActionSetterObject()
	{
		return actionSetterObject;
	}

	/**
	 * sets the list of getter objects that are required to run the relevant actions
	 * 
	 * @param	actionGetterObjects	list of getter objects
	 * 
	 */
	public void setActionGetterObjects(ArrayList<ActionObject> actionGetterObjects)
	{
		this.actionGetterObjects = actionGetterObjects;
	}
	
	/**
	 * adds an action object to the list of objects required to run the action
	 * 
	 * @param	actionGetterObject	the getter object to add
	 * 
	 */
	public void addActionGetterObject(ActionObject actionGetterObject)
	{
		this.actionGetterObjects.add(actionGetterObject);
	}
	
	/**
	 * removes an object from the list of action objects
	 * 
	 * @param	index	the index of the getter object to remove
	 */
	public void removeActionGetterObject(int index)
	{
		this.actionGetterObjects.remove(index);
	}

	/**
	 * returns the index number of that parameter that is defined as
	 * the value to set the object to.
	 * 
	 * @return	the index of the value
	 */
	public int getActionObjectSetterValueIndex()
	{
		ActionObject actionObject = this.getActionSetterObject();
		int found=-1;
		for (int i=0;i< actionObject.getParameters().size();i++)
		{
			Parameter parameter = actionObject.getParameters().get(i);
			if(parameter.isSetterValue())
			{
				found=i;
				break;
			}
			
		}
		return found;
	}

	/**
	 * sets the object that is used for setting/updating data
	 * 
	 * @param actionSetterObject the object used for setting data
	 */
	public void setActionSetterObject(ActionObject actionSetterObject)
	{
		this.actionSetterObject = actionSetterObject;
	}
	
	/**
	 * the description of the action
	 * 
	 * @return	the description of the action
	 */
	public String getDescription()
    {
        return description;
    }
    
	/**
	 * sets the description of the action
	 * 
	 * @param description the description of the action
	 */
    public void setDescription(String description)
    {
        this.description = description;
    }
    
    /**
     * the id of the action
     * 
     * @return	the id of the action
     */
    public String getId()
    {
        return id;
    }
    
    /**
     * sets the id of the action
     * 
     * @param id the id of the action
     */
    public void setId(String id)
    {
        this.id = id;
    }
}
