package com.datamelt.rules.core.action;

import java.util.ArrayList;

import com.datamelt.rules.core.Parameter;
/**
 * simple interface for actions related to the business rule engine
 * 
 * @author uwe geercken
 *
 */
public interface ActionInterface
{
	// all classes implementing this interface shall implement this method
	public static final String METHOD_TO_EXECUTE="executeAction";
	/**
	 * method to be executed for all actions
	 * 
	 * the first argument is the object that a specific action has to be applied to. it is that same object that
	 * the rule is used for checking the rules.
	 * 
	 * @param object		the object the action is applied to
	 * @param parameters	a list of parameters to pass to the action
	 * @throws Exception	throws exception if the action can not be executed
	 */
	public void executeAction(Object object,ArrayList<Parameter> parameters) throws Exception;
	
}
