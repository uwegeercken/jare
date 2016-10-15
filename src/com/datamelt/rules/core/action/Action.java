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
/**
 * class to execute one or a list of actions. An action is executed against a given object.
 * 
 * Actions belong to a rulegroup and are execute depending on the status of rulegroup - if it passed or failed (or both).
 *
 * @author uwe geercken
 * 
 */
public class Action implements Serializable
{
	private Object object;
	private int ruleGroupFailed;
	private PrintStream stream;
	private boolean outputAfterActions;
	
	public static final long serialVersionUID = 1964070320;
	
	/**
	 * constructor to define the object used for the action and additional settings
     *
     * @param ruleGroupFailed 		indicator if the related rulegroup failed
     * @param object	 			the object to use
     * @param outputAfterActions	indicator if the result should be output after execution of the action
     */
	public Action(int ruleGroupFailed,Object object,boolean outputAfterActions)
	{
		this.object = object;
		this.ruleGroupFailed = ruleGroupFailed;
		this.outputAfterActions = outputAfterActions;
	}
	
	/**
	 * Executes a list of XmlAction objects
	 * 
	 * @param actions 		the list of actions
     * @return				the number of actions executed
     */
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
	
	/**
	 * Executes a single XmlAction objects
	 * 
	 * @param action 		the action to execute
     */
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
	
	/**
	 * gets the object that is used for this action 
	 * 
	 * @return			the object of the action 
	 */
	public Object getObject()
	{
		return object;
	}

	/**
	 * sets the object that is used for this action 
	 * 
	 * @param object 		the object of the action
	 */
	public void setObject(Object object)
	{
		this.object = object;
	}

	/**
	 * gets the printstream that is used for this action 
	 * 
	 * @return			the printstream used 
	 */
	public PrintStream getPrintStream()
	{
		return stream;
	}

	/**
	 * sets the printstream that is used for this action 
	 * 
	 * @param stream 		the printstream to be used for the action
	 */
	public void setPrintStream(PrintStream stream)
	{
		this.stream = stream;
	}

	
}
