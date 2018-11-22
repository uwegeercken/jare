/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
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
