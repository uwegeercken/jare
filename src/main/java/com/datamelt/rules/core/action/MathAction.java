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
	@ActionAnnotation(description= "Set a value to another value",methodDisplayname="set value (number)")
	public int setValue(XmlAction action, int value) throws Exception
	{
		return value;
	}
	
	@ActionAnnotation(description= "Set a value to another value",methodDisplayname="set value (number)")
	public float setValue(XmlAction action, float value) throws Exception
	{
		return value;
	}
	
	@ActionAnnotation(description= "Set a value to another value",methodDisplayname="set value (number)")
	public long setValue(XmlAction action, long value) throws Exception
	{
		return value;
	}
	
	@ActionAnnotation(description= "Set a value to another value",methodDisplayname="set value (number)")
	public double setValue(XmlAction action, double value) throws Exception
	{
		return value;
	}
	
	@ActionAnnotation(description= "Set a value to another value",methodDisplayname="set value (number)")
	public boolean setValue(XmlAction action, boolean value) throws Exception
	{
		return value;
	}
	
	@ActionAnnotation(description= "Set a value to another value",methodDisplayname="set value (number)")
	public BigDecimal setValue(XmlAction action, BigDecimal value) throws Exception
	{
		return value;
	}
	
	@ActionAnnotation(description= "Set a value to another value",methodDisplayname="set value (number)")
	public BigInteger setValue(XmlAction action, BigInteger value) throws Exception
	{
		return value;
	}
	
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
	
	@ActionAnnotation(description= "Add two values",methodDisplayname="add values")
	public int addValues(XmlAction action, int value, int value2) throws Exception
	{
		return value + value2;
	}
	
	@ActionAnnotation(description= "Add two values",methodDisplayname="add values")
	public long addValues(XmlAction action, long value, long value2) throws Exception
	{
		return value + value2;
	}
	
	@ActionAnnotation(description= "Add two values",methodDisplayname="add values")
	public long addValues(XmlAction action, long value, int value2) throws Exception
	{
		return value + value2;
	}
	
	@ActionAnnotation(description= "Add two values",methodDisplayname="add values")
	public long addValues(XmlAction action, int value, long value2) throws Exception
	{
		return value + value2;
	}
	
	@ActionAnnotation(description= "Add two values",methodDisplayname="add values")
	public double addValues(XmlAction action, double value, double value2) throws Exception
	{
		return value + value2;
	}
	
	@ActionAnnotation(description= "Add two values",methodDisplayname="add values")
	public float addValues(XmlAction action, float value, float value2) throws Exception
	{
		return value + value2;
	}
	
	@ActionAnnotation(description= "Subtract two values",methodDisplayname="subtract values")
	public int subtractValues(XmlAction action, int value, int value2) throws Exception
	{
		return value - value2;
	}
	
	@ActionAnnotation(description= "Subtract two values",methodDisplayname="subtract values")
	public long subtractValues(XmlAction action, long value, long value2) throws Exception
	{
		return value - value2;
	}
	
	@ActionAnnotation(description= "Subtract two values",methodDisplayname="subtract values")
	public long subtractValues(XmlAction action, long value, int value2) throws Exception
	{
		return value - value2;
	}
	
	@ActionAnnotation(description= "Subtract two values",methodDisplayname="subtract values")
	public long subtractValues(XmlAction action, int value, long value2) throws Exception
	{
		return value - value2;
	}
	
	@ActionAnnotation(description= "Subtract two values",methodDisplayname="subtract values")
	public double subtractValues(XmlAction action, double value, double value2) throws Exception
	{
		return value - value2;
	}
	
	@ActionAnnotation(description= "Subtract two values",methodDisplayname="subtract values")
	public float subtractValues(XmlAction action, float value, float value2) throws Exception
	{
		return value - value2;
	}
	
	@ActionAnnotation(description= "Subtract two values",methodDisplayname="subtract values")
	@ActionMethodAnnotation(note= "return value is in seconds")
	public long subtractValues(XmlAction action, Date value, Date value2) throws Exception
	{
		// difference is in seconds
		long difference = (Math.abs(value.getTime() - value2.getTime()))/1000;
		
		if(difference>0)
		{
			return difference;
		}
		else
		{
			return 0;
		}

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
	
	@ActionAnnotation(description= "Multiply two values",methodDisplayname="multiply value")
	public long multiplyValues(XmlAction action, int value, int value2) throws Exception
	{
		return value * value2;
	}
	
	@ActionAnnotation(description= "Multiply two values",methodDisplayname="multiply value")
	public long multiplyValues(XmlAction action, long value, long value2) throws Exception
	{
		return value * value2;
	}
	
	@ActionAnnotation(description= "Multiply two values",methodDisplayname="multiply value")
	public long multiplyValues(XmlAction action, long value, int value2) throws Exception
	{
		return value * value2;
	}
	
	@ActionAnnotation(description= "Multiply two values",methodDisplayname="multiply value")
	public long multiplyValues(XmlAction action, int value, long value2) throws Exception
	{
		return value * value2;
	}
	
	@ActionAnnotation(description= "Multiply two values",methodDisplayname="multiply value")
	public double multiplyValues(XmlAction action, long value, double value2) throws Exception
	{
		return value * value2;
	}
	
	@ActionAnnotation(description= "Multiply two values",methodDisplayname="multiply value")
	public double multiplyValues(XmlAction action, double value, double value2) throws Exception
	{
		return value * value2;
	}
	
	@ActionAnnotation(description= "Multiply two values",methodDisplayname="multiply value")
	public double multiplyValues(XmlAction action, double value, int value2) throws Exception
	{
		return value * value2;
	}
	
	@ActionAnnotation(description= "Multiply two values",methodDisplayname="multiply value")
	public float multiplyValues(XmlAction action, float value, float value2) throws Exception
	{
		return value * value2;
	}
	
	@ActionAnnotation(description= "Devide two values",methodDisplayname="devide value")
	public double devideValues(XmlAction action, int value, int value2) throws Exception
	{
		return value / value2;
	}
	
	@ActionAnnotation(description= "Devide two values",methodDisplayname="devide value")
	public double devideValues(XmlAction action, long value, long value2) throws Exception
	{
		return (double)value / value2;
	}
	
	@ActionAnnotation(description= "Devide two values",methodDisplayname="devide value")
	public double devideValues(XmlAction action, long value, int value2) throws Exception
	{
		return value / value2;
	}
	
	@ActionAnnotation(description= "Devide two values",methodDisplayname="devide value")
	public double devideValues(XmlAction action, int value, long value2) throws Exception
	{
		return value / value2;
	}
	
	@ActionAnnotation(description= "Devide two values",methodDisplayname="devide value")
	public double devideValues(XmlAction action, long value, double value2) throws Exception
	{
		return value / value2;
	}
	
	@ActionAnnotation(description= "Devide two values",methodDisplayname="devide value")
	public double devideValues(XmlAction action, double value, double value2) throws Exception
	{
		return value / value2;
	}
	
	@ActionAnnotation(description= "Devide two values",methodDisplayname="devide value")
	public BigDecimal devideValues(XmlAction action, BigDecimal value, BigDecimal value2) throws Exception
	{
		return value.divide(value2);
	}
	
	@ActionAnnotation(description= "Devide two values",methodDisplayname="devide value")
	public double devideValues(XmlAction action, double value, int value2) throws Exception
	{
		return value / value2;
	}

	@ActionAnnotation(description= "Devide two values",methodDisplayname="devide value")
	public float devideValues(XmlAction action, float value, float value2) throws Exception
	{
		return value / value2;
	}
	
	@ActionAnnotation(description= "Set a value to the square root of a value",methodDisplayname="square root value")
	public double squareRootValue(XmlAction action, double value) throws Exception
	{
		return Math.sqrt(value);
	}
	
	@ActionAnnotation(description= "Set a value to the square root of a value",methodDisplayname="square root value")
	public double squareRootValue(XmlAction action, float value) throws Exception
	{
		return Math.sqrt(value);
	}
	
	@ActionAnnotation(description= "Set a value to the square root of a value",methodDisplayname="square root value")
	public double squareRootValue(XmlAction action, int value) throws Exception
	{
		return Math.sqrt(value);
	}
	
	@ActionAnnotation(description= "Set a value to the square root of a value",methodDisplayname="square root value")
	public double squareRootValue(XmlAction action, long value) throws Exception
	{
		return Math.sqrt(value);
	}
	
	@ActionAnnotation(description= "Set a value to the square of a value",methodDisplayname="square value")
	public double squareValue(XmlAction action, double value) throws Exception
	{
		return value * value;
	}

	@ActionAnnotation(description= "Set a value to the square of a value",methodDisplayname="square value")
	public long squareValue(XmlAction action, int value) throws Exception
	{
		return value * value;
	}
	
	@ActionAnnotation(description= "Set a value to the square of a value",methodDisplayname="square value")
	public double squareValue(XmlAction action, float value) throws Exception
	{
		return value * value;
	}

	@ActionAnnotation(description= "Set a value to the square of a value",methodDisplayname="square value")
	public long squareValue(XmlAction action, long value) throws Exception
	{
		return value * value;
	}

	@ActionAnnotation(description= "Set a value to its absolute value",methodDisplayname="absolute value")
	public double absValue(XmlAction action, double value) throws Exception
	{
		return Math.abs(value);
	}

	@ActionAnnotation(description= "Set a value to its absolute value",methodDisplayname="absolute value")
	public float absValue(XmlAction action, float value) throws Exception
	{
		return Math.abs(value);
	}
	
	@ActionAnnotation(description= "Set a value to its absolute value",methodDisplayname="absolute value")
	public long absValue(XmlAction action, long value) throws Exception
	{
		return Math.abs(value);
	}
	
	@ActionAnnotation(description= "Set a value to its absolute value",methodDisplayname="absolute value")
	public int absValue(XmlAction action, int value) throws Exception
	{
		return Math.abs(value);
	}
	
	@ActionAnnotation(description= "Round a value",methodDisplayname="round value")
	public long roundValue(XmlAction action, double value) throws Exception
	{
		return Math.round(value);
	}
	
	@ActionAnnotation(description= "Round a value",methodDisplayname="round value")
	public int roundValue(XmlAction action, float value) throws Exception
	{
		return Math.round(value);
	}
	
	@ActionAnnotation(description= "Set a value to the floor of a value",methodDisplayname="floor")
	public double floorValue(XmlAction action, double value) throws Exception
	{
		return Math.floor(value);
	}
	
	@ActionAnnotation(description= "Set a value to the ceiling of a value",methodDisplayname="ceil")
	public double ceilValue(XmlAction action, double value) throws Exception
	{
		return Math.ceil(value);
	}
	
	@ActionAnnotation(description= "Set a value to a random value",methodDisplayname="random value")
	public int randomValue(XmlAction action, int min, int max)
	{
		Random rand = new Random();

	    return rand.nextInt((max - min) + 1) + min;
	}
	
	@ActionAnnotation(description= "Set a value to the cosinus of a value",methodDisplayname="cosinus")
	public double cosValue(XmlAction action, double value) throws Exception
	{
		return Math.cos(value);
	}
	
	@ActionAnnotation(description= "Set a value to the cosinush of a value",methodDisplayname="cosinush")
	public double coshValue(XmlAction action, double value) throws Exception
	{
		return Math.cosh(value);
	}

	@ActionAnnotation(description= "Set a value to the acosinus of a value",methodDisplayname="acosinus")
	public double acosValue(XmlAction action, double value) throws Exception
	{
		return Math.acos(value);
	}
	
	@ActionAnnotation(description= "Set a value to the sinus of a value",methodDisplayname="sinus")
	public double sinValue(XmlAction action, double value) throws Exception
	{
		return Math.sin(value);
	}
	
	@ActionAnnotation(description= "Set a value to the sinush of a value",methodDisplayname="sinush")
	public double sinhValue(XmlAction action, double value) throws Exception
	{
		return Math.sinh(value);
	}
	
	@ActionAnnotation(description= "Set a value to the asinus of a value",methodDisplayname="asinus")
	public double asinValue(XmlAction action, double value) throws Exception
	{
		return Math.asin(value);
	}
	
	@ActionAnnotation(description= "Set a value to the tangens of a value",methodDisplayname="tangens")
	public double tanValue(XmlAction action, double value) throws Exception
	{
		return Math.tan(value);
	}
	
	@ActionAnnotation(description= "Set a value to the tangensh of a value",methodDisplayname="tangensh")
	public double tanhValue(XmlAction action, double value) throws Exception
	{
		return Math.tanh(value);
	}
	
	@ActionAnnotation(description= "Set a value to the atangens of a value",methodDisplayname="atangens")
	public double atanValue(XmlAction action, double value) throws Exception
	{
		return Math.atan(value);
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
	 * @return			the has code for the value
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
	 * @return			the has code for the value
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
	 * @return			the has code for the value
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
	 * @return			the has code for the value
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
	 * @return			the has code for the value
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
	 * @return			the has code for the value
	 */
	@ActionAnnotation(description= "Hash value",methodDisplayname="hash value")
	public int hashValue(XmlAction action,BigDecimal value)
	{
		return value.hashCode();
	}
	
}
