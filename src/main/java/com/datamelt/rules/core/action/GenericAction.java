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

import com.datamelt.rules.core.XmlAction;
import com.datamelt.util.ActionAnnotation;
import java.io.Serializable;

/**
 * marker class for action classes. used for extracting annotations using reflection.
 * 
 * @author uwe
 *
 */
public class GenericAction implements Serializable 
{

    /**
     * sets the date to the given date
     *
     * @param action 		the action use
     * @param value			the value to set
     * @return				date
     * @throws Exception	exception in date handling
     */
    @ActionAnnotation(description= "Set a value to another value",methodDisplayname="set value ()")
    public Object setValue(XmlAction action, Object  value) throws Exception {
        return value;
    }

}
