package com.datamelt.rules.core.action;

import java.io.PrintStream;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import com.datamelt.rules.core.ActionObject;
import com.datamelt.rules.core.XmlAction;
import com.datamelt.util.ClassUtility;
import com.datamelt.util.FieldNotFoundException;

public class Action implements Serializable
{
	private Object object;
	private int ruleGroupFailed;
	private PrintStream stream;
	private boolean outputAfterActions;
	
	public static final long serialVersionUID = 1964070320;
	
	public Action(int ruleGroupFailed,Object object,boolean outputAfterActions)
	{
		this.object = object;
		this.ruleGroupFailed = ruleGroupFailed;
		this.outputAfterActions = outputAfterActions;
	}
	
	public int executeActions(ArrayList<XmlAction> actions) throws Exception
	{
			int counter=0;
			for(int i=0;i<actions.size();i++)
			{
				XmlAction action = actions.get(i);
				if(ruleGroupFailed==action.getExecuteIf() || action.getExecuteIf()==XmlAction.TYPE_ALWAYS)
				{
					executeAction(action);
					counter++;
				}
			}
			if(counter>0 && this.outputAfterActions)
			{
				stream.println(object);
			}
			return counter;
	}
	
	public void executeAction(XmlAction action) throws Exception
	{
		Method methodSetterObject = null;
		
        ArrayList<ActionObject> actionGetterObjects = action.getActionGetterObjects();
        ActionObject actionSetterObject = action.getActionSetterObject();
		// if we have a getter method, we invoke the method to get the actual object
		// then we will pass the object to the action method
        Object[] getterObjects = null;
        if(actionGetterObjects.size()>0 && actionSetterObject!=null)
        {
        	getterObjects = new Object[action.getActionGetterObjects().size()];
        	// construct the setter method to execute on the object from the xml action
    		methodSetterObject = ClassUtility.getObjectMethod(action,action.getActionSetterObject());
    		
    		// create the actual object
        	try
        	{
        		for(int i=0;i<actionGetterObjects.size();i++)
        		{
        			Method methodGetterObject = ClassUtility.getObjectMethod(action,actionGetterObjects.get(i));
        			getterObjects[i] = ClassUtility.invokeObjectMethod(object, methodGetterObject, actionGetterObjects.get(i).getParameters());
        		}
        	}
        	catch(ActionInvocationException aie)
        	{
        		throw new ActionInvocationException("error invoking action object method: "+ aie.getMessage());
        	}
        	catch(FieldNotFoundException fnfe)
        	{
        		throw new FieldNotFoundException("error action field not found: "+ fnfe.getMessage());
        	}
        	catch(InvocationTargetException ite)
            {
            	throw new Exception("error invoking action method: "+ ite.getTargetException());
            }
        }
        // if we only have a setter method, then we will simply update the object with the given action parameter(s)
        else if(actionGetterObjects.size()==0 && actionSetterObject!=null)
        {
        	// construct the setter method to execute on the object from the xml action
    		try
    		{
    			methodSetterObject = ClassUtility.getObjectMethod(action,action.getActionSetterObject());
    		}
    		catch(NoSuchMethodException nsm)
    		{
    			throw new Exception("no such method: "+ nsm.getMessage());
    		}
    		catch(Exception ex)
    		{
    			throw new Exception("error invoking action method: "+ ex.getMessage());
    		}
    		
        }
        Object actionClassResult=null;
        if(action.getClassName()!=null)
        {
	        // construct the method to execute from the xml action
			Method methodAction = ClassUtility.getActionMethod(action);
	     
			// invoke the method of the xml action
			// create the actual value of the method parameter/argument
			
			try
	        {
				actionClassResult = ClassUtility.invokeActionMethod(action, getterObjects ,methodAction);
	        }
	        catch(ActionInvocationException aie)
	        {
	        	throw new ActionInvocationException("error invoking action method: "+ aie.getMessage());
	        }
	        catch(FieldNotFoundException fnfe)
        	{
        		throw new FieldNotFoundException("error action field not found: "+ fnfe.getMessage());
        	}
	        catch(InvocationTargetException ite)
            {
            	throw new Exception("error invoking action getter method: "+ ite.getTargetException());
            }
        }        

        // invoke the method on the object
        if(actionSetterObject!=null)
        {
        	try
        	{
        		ClassUtility.invokeObjectSetterMethod(object, actionClassResult, methodSetterObject,action.getActionSetterObject().getParameters());
        	}
        	catch(ActionInvocationException aie)
        	{
        		throw new ActionInvocationException("error invoking action object method: "+ aie.getMessage());
        	}
        	catch(FieldNotFoundException fnfe)
        	{
        		throw new FieldNotFoundException("error action field not found: "+ fnfe.getMessage());
        	}
        	catch(InvocationTargetException ite)
            {
            	throw new Exception("error invoking action setter method: "+ ite.getTargetException());
            }
        	catch(Exception ex)
        	{
        		throw new Exception("error invoking action setter method: "+ ex.getMessage());
        	}
        }
	}
	
	public Object getObject()
	{
		return object;
	}

	public void setObject(Object object)
	{
		this.object = object;
	}

	public PrintStream getPrintStream()
	{
		return stream;
	}

	public void setPrintStream(PrintStream stream)
	{
		this.stream = stream;
	}

	
}
