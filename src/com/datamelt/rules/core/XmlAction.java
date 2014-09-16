package com.datamelt.rules.core;

import java.util.ArrayList;

/**
 * this class holds the information for a specific action as defined in the xml file
 * 
 * 
 * @author uwe geercken
 *
 */
public class XmlAction
{
	public static final int TYPE_PASSED = 0;
    public static final int TYPE_FAILED = 1;
    
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
     */
    public XmlAction(String id, String description)
    {
        this.id = id;
        this.description = description;
    }
    
	/**
	 * returns the name of the action class to run
	 * 
	 */
	
	public String getClassName()
	{
		return className;
	}

	/**
	 * sets the name of the action class to run
	 * 
	 */
	public void setClassName(String className)
	{
		this.className = className;
	}
	
	 /**
     * returns the list of parameters that have to be passed to the method
     * to execute 
     */
    public ArrayList <Parameter>getParameters()
    {
        return parameters;
    }
    
    /**
     * sets the list of parameters that have to be passed to the method
     * to execute 
     */
    public void setParameters(ArrayList<Parameter> parameters)
    {
        this.parameters = parameters;
    }
    
    /**
     * add another parameter to the list of parameters of the method 
     */
    public void addParameter(Parameter parameter)
    {
        parameters.add(parameter);
    }

    /**
     * indicator when the action will run: if the group passed or if it failed
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
	 */
	public void setExecuteIf(int executeIf)
	{
		this.executeIf = executeIf;
	}

	/**
	 * returns the name of the method to run
	 * 
	 */
	public String getMethodName()
	{
		return methodName;
	}

	/**
	 * sets the name of the method to run
	 * 
	 */
	public void setMethodName(String methodName)
	{
		this.methodName = methodName;
	}
	
	/**
	 * returns the list of objects that have been collected from the xml file
	 * and are required to run the action
	 * 
	 */
	public ArrayList<ActionObject> getActionGetterObjects()
	{
		return actionGetterObjects;
	}

	/**
	 * returns the list of objects that have been collected from the xml file
	 * and are required to run the action
	 * 
	 */
	public ActionObject getActionSetterObject()
	{
		return actionSetterObject;
	}

	/**
	 * sets the list of objects that are required to run the relevant actions
	 * 
	 */
	public void setActionGetterObjects(ArrayList<ActionObject> actionGetterObjects)
	{
		this.actionGetterObjects = actionGetterObjects;
	}
	
	/**
	 * adds an action object to the list of objects required to run the action
	 * 
	 */
	public void addActionGetterObject(ActionObject actionGetterObject)
	{
		this.actionGetterObjects.add(actionGetterObject);
	}
	
	/**
	 * removes an object from the list of action objects
	 * 
	 */
	public void removeActionGetterObject(int index)
	{
		this.actionGetterObjects.remove(index);
	}

	/**
	 * returns the index number of that parameter that is defined as
	 * the value to set the object to.
	 * 
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

	public void setActionSetterObject(ActionObject actionSetterObject)
	{
		this.actionSetterObject = actionSetterObject;
	}
	
	public String getDescription()
    {
        return description;
    }
    
    public void setDescription(String description)
    {
        this.description = description;
    }
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
	
}
