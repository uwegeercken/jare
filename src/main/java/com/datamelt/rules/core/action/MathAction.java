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

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.Random;

import com.datamelt.rules.core.XmlAction;
import com.datamelt.util.ActionAnnotation;
import com.datamelt.util.ActionMethodAnnotation;
/**
 * Class containing possible actions that are related to mathematical calculations.
 * 
 * Actions belong to a rulegroup and are execute depending on the status of rulegroup - if it passed or failed (or both).
 *
 * @author uwe geercken
 * 
 */
public class MathAction extends GenericAction
{

	@ActionAnnotation(description= "Set a value to null",methodDisplayname="set null value (number)")
	public Integer setNull(XmlAction action, int value) throws Exception
	{
		return null;
	}
	
	@ActionAnnotation(description= "Set a value to null",methodDisplayname="set null value (number)")
	public Long setNull(XmlAction action, long value) throws Exception
	{
		return null;
	}
	
	@ActionAnnotation(description= "Set a value to null",methodDisplayname="set null value (number)")
	public Float setNull(XmlAction action, float value) throws Exception
	{
		return null;
	}
	
	@ActionAnnotation(description= "Set a value to null",methodDisplayname="set null value (number)")
	public Double setNull(XmlAction action, double value) throws Exception
	{
		return null;
	}
	
	@ActionAnnotation(description= "Set a value to null",methodDisplayname="set null value (number)")
	public Boolean setNull(XmlAction action, boolean value) throws Exception
	{
		return null;
	}
	
	@ActionAnnotation(description= "Set a value to null",methodDisplayname="set null value (number)")
	public BigDecimal setNull(XmlAction action, BigDecimal value) throws Exception
	{
		return null;
	}

	@ActionAnnotation(description= "Remainder value (modulus)",methodDisplayname="remainder value")
	public long remainderValue(XmlAction action, long value, long value2)
	{
		return value % value2;
	}
	
	@ActionAnnotation(description= "Remainder value (modulus)",methodDisplayname="remainder value")
	public long remainderValue(XmlAction action, long value, int value2)
	{
		return value % value2;
	}
	
	@ActionAnnotation(description= "Remainder value (modulus)",methodDisplayname="remainder value")
	public int remainderValue(XmlAction action, int value, int value2)
	{
		return value % value2;
	}

	@ActionAnnotation(description= "Subtract a percentage of a value from the value",methodDisplayname="subtract percentage")
	public double subtractPercentageValue(XmlAction action, double value, double percentage) throws Exception
	{
		double percentageValue = value * percentage / 100;
		return value - percentageValue;
	}
	
	@ActionAnnotation(description= "Subtract a percentage of a value from the value",methodDisplayname="subtract percentage")
	public double subtractPercentageValue(XmlAction action, double value, float percentage) throws Exception
	{
		double percentageValue = value * percentage / 100;
		return value - percentageValue;
	}
	
	@ActionAnnotation(description= "Subtract a percentage of a value from the value",methodDisplayname="subtract percentage")
	public double subtractPercentageValue(XmlAction action, double value, int percentage) throws Exception
	{
		double percentageValue = value * percentage / 100;
		return value - percentageValue;
	}
	
	@ActionAnnotation(description= "Subtract a percentage of a value from the value",methodDisplayname="subtract percentage")
	public double subtractPercentageValue(XmlAction action, int value, int percentage) throws Exception
	{
		double percentageValue = (double)value * percentage / 100;
		return value - percentageValue;
	}
	
	@ActionAnnotation(description= "Subtract a percentage of a value from the value",methodDisplayname="subtract percentage")
	public double subtractPercentageValue(XmlAction action, int value, double percentage) throws Exception
	{
		double percentageValue = (double)value * percentage / 100;
		return value - percentageValue;
	}
	
	@ActionAnnotation(description= "Subtract a percentage of a value from the value",methodDisplayname="subtract percentage")
	public double subtractPercentageValue(XmlAction action, int value, float percentage) throws Exception
	{
		double percentageValue = (double)value * percentage / 100;
		return value - percentageValue;
	}
	
	@ActionAnnotation(description= "Subtract a percentage of a value from the value",methodDisplayname="subtract percentage")
	public double subtractPercentageValue(XmlAction action, long value, int percentage) throws Exception
	{
		double percentageValue = (double)value * percentage / 100;
		return value - percentageValue;
	}
	
	@ActionAnnotation(description= "Subtract a percentage of a value from the value",methodDisplayname="subtract percentage")
	public double subtractPercentageValue(XmlAction action, long value, double percentage) throws Exception
	{
		double percentageValue = (double)value * percentage / 100;
		return value - percentageValue;
	}
	
	@ActionAnnotation(description= "Subtract a percentage of a value from the value",methodDisplayname="subtract percentage")
	public double subtractPercentageValue(XmlAction action, long value, float percentage) throws Exception
	{
		double percentageValue = (double)value * percentage / 100;
		return value - percentageValue;
	}
	
	@ActionAnnotation(description= "Add a percentage of a value frotom the value",methodDisplayname="add percentage")
	public double addPercentageValue(XmlAction action, double value, double percentage) throws Exception
	{
		double percentageValue = value * percentage / 100;
		return value + percentageValue;
	}
	
	@ActionAnnotation(description= "Add a percentage of a value to the value",methodDisplayname="add percentage")
	public double addPercentageValue(XmlAction action, double value, float percentage) throws Exception
	{
		double percentageValue = value * percentage / 100;
		return value + percentageValue;
	}
	
	@ActionAnnotation(description= "Add a percentage of a value to the value",methodDisplayname="add percentage")
	public double addPercentageValue(XmlAction action, double value, int percentage) throws Exception
	{
		double percentageValue = value * percentage / 100;
		return value + percentageValue;
	}
	
	@ActionAnnotation(description= "Add a percentage of a value to the value",methodDisplayname="add percentage")
	public double addPercentageValue(XmlAction action, int value, int percentage) throws Exception
	{
		double percentageValue = (double)value * percentage / 100;
		return value + percentageValue;
	}
	
	@ActionAnnotation(description= "Add a percentage of a value to the value",methodDisplayname="add percentage")
	public double addPercentageValue(XmlAction action, int value, double percentage) throws Exception
	{
		double percentageValue = (double)value * percentage / 100;
		return value + percentageValue;
	}
	
	@ActionAnnotation(description= "Add a percentage of a value to the value",methodDisplayname="add percentage")
	public double addPercentageValue(XmlAction action, int value, float percentage) throws Exception
	{
		double percentageValue = (double)value * percentage / 100;
		return value + percentageValue;
	}
	
	@ActionAnnotation(description= "Add a percentage of a value to the value",methodDisplayname="add percentage")
	public double addPercentageValue(XmlAction action, long value, int percentage) throws Exception
	{
		double percentageValue = (double)value * percentage / 100;
		return value + percentageValue;
	}
	
	@ActionAnnotation(description= "Add a percentage of a value to the value",methodDisplayname="add percentage")
	public double addPercentageValue(XmlAction action, long value, double percentage) throws Exception
	{
		double percentageValue = (double)value * percentage / 100;
		return value + percentageValue;
	}
	
	@ActionAnnotation(description= "Add a percentage of a value to the value",methodDisplayname="add percentage")
	public double addPercentageValue(XmlAction action, long value, float percentage) throws Exception
	{
		double percentageValue = (double)value * percentage / 100;
		return value + percentageValue;
	}
	
	/**
	 * returns the hash code of the value
	 * 
	 * @param action	the action to use
	 * @param value		the value to use
	 * @return			the hash code for the value
	 */
	@ActionAnnotation(description= "Hash value",methodDisplayname="hash value")
	public int hashValue(XmlAction action,int value)
	{
		Integer i = new Integer(value);
		return i.hashCode();
	}
	
	/**
	 * returns the hash code of the value
	 * 
	 * @param action	the action to use
	 * @param value		the value to use
	 * @return			the hash code for the value
	 */
	@ActionAnnotation(description= "Hash value",methodDisplayname="hash value")
	public int hashValue(XmlAction action,long value)
	{
		Long l = new Long(value);
		return l.hashCode();
	}
	
	/**
	 * returns the hash code of the value
	 * 
	 * @param action	the action to use
	 * @param value		the value to use
	 * @return			the hash code for the value
	 */
	@ActionAnnotation(description= "Hash value",methodDisplayname="hash value")
	public int hashValue(XmlAction action,double value)
	{
		Double d = new Double(value);
		return d.hashCode();
	}
	
	/**
	 * returns the hash code of the value
	 * 
	 * @param action	the action to use
	 * @param value		the value to use
	 * @return			the hash code for the value
	 */
	@ActionAnnotation(description= "Hash value",methodDisplayname="hash value")
	public int hashValue(XmlAction action,float value)
	{
		Float f = new Float(value);
		return f.hashCode();
	}
	
	/**
	 * returns the hash code of the value
	 * 
	 * @param action	the action to use
	 * @param value		the value to use
	 * @return			the hash code for the value
	 */
	@ActionAnnotation(description= "Hash value",methodDisplayname="hash value")
	public int hashValue(XmlAction action,boolean value)
	{
		Boolean b = new Boolean(value);
		return b.hashCode();
	}
	
	/**
	 * returns the hash code of the value
	 * 
	 * @param action	the action to use
	 * @param value		the value to use
	 * @return			the hash code for the value
	 */
	@ActionAnnotation(description= "Hash value",methodDisplayname="hash value")
	public int hashValue(XmlAction action,BigDecimal value)
	{
		return value.hashCode();
	}
	
}
