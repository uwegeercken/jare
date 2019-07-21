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
package com.datamelt.util;

import java.math.BigDecimal;

public class Constants 
{
	// default values for RowField
	public static String		ROWFIELD_TYPE_STRING_DEFAULT_VALUE					= "";
	public static int			ROWFIELD_TYPE_INTEGER_DEFAULT_VALUE					= 0;
	public static long			ROWFIELD_TYPE_LONG_DEFAULT_VALUE					= 0;
	public static float			ROWFIELD_TYPE_FLOAT_DEFAULT_VALUE					= 0.0f;
	public static double		ROWFIELD_TYPE_DOUBLE_DEFAULT_VALUE					= 0.0d;
	public static BigDecimal	ROWFIELD_TYPE_BIGDECIMAL_DEFAULT_VALUE				= new BigDecimal(0);
	public static boolean		ROWFIELD_TYPE_BOOLEAN_DEFAULT_VALUE					= false;
	
	// the default was implemented for Avro format as Date is not defined there - this needs to be reconsidered
	public static long	 		ROWFIELD_TYPE_DATE_DEFAULT_VALUE					= 0;
}
