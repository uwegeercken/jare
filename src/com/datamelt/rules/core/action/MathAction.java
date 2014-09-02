package com.datamelt.rules.core.action;

import java.math.BigDecimal;

import com.datamelt.rules.core.XmlAction;

public class MathAction
{
	public int setValue(XmlAction action, int value) throws Exception
	{
		return value;
	}
	
	public float setValue(XmlAction action, float value) throws Exception
	{
		return value;
	}
	
	public long setValue(XmlAction action, long value) throws Exception
	{
		return value;
	}
	
	public double setValue(XmlAction action, double value) throws Exception
	{
		return value;
	}
	
	public boolean setValue(XmlAction action, boolean value) throws Exception
	{
		return value;
	}
	
	public int addValues(XmlAction action, int value, int value2) throws Exception
	{
		return value + value2;
	}
	
	public long addValues(XmlAction action, long value, long value2) throws Exception
	{
		return value + value2;
	}
	
	public long addValues(XmlAction action, long value, int value2) throws Exception
	{
		return value + value2;
	}
	
	public long addValues(XmlAction action, int value, long value2) throws Exception
	{
		return value + value2;
	}
	
	public double addValues(XmlAction action, double value, double value2) throws Exception
	{
		return value + value2;
	}
	
	public float addValues(XmlAction action, float value, float value2) throws Exception
	{
		return value + value2;
	}
	
	public int substractValues(XmlAction action, int value, int value2) throws Exception
	{
		return value - value2;
	}
	
	public long substractValues(XmlAction action, long value, long value2) throws Exception
	{
		return value - value2;
	}
	
	public long substractValues(XmlAction action, long value, int value2) throws Exception
	{
		return value - value2;
	}
	
	public long substractValues(XmlAction action, int value, long value2) throws Exception
	{
		return value - value2;
	}
	
	public double substractValues(XmlAction action, double value, double value2) throws Exception
	{
		return value - value2;
	}
	
	public float substractValues(XmlAction action, float value, float value2) throws Exception
	{
		return value - value2;
	}
	
	public int multiplyValues(XmlAction action, int value, int value2) throws Exception
	{
		return value * value2;
	}
	
	public long multiplyValues(XmlAction action, long value, long value2) throws Exception
	{
		return value * value2;
	}
	
	public long multiplyValues(XmlAction action, long value, int value2) throws Exception
	{
		return value * value2;
	}
	
	public long multiplyValues(XmlAction action, int value, long value2) throws Exception
	{
		return value * value2;
	}
	
	public double multiplyValues(XmlAction action, long value, double value2) throws Exception
	{
		return value * value2;
	}
	
	public double multiplyValues(XmlAction action, double value, double value2) throws Exception
	{
		return value * value2;
	}
	
	public double multiplyValues(XmlAction action, double value, int value2) throws Exception
	{
		return value * value2;
	}
	
	public float multiplyValues(XmlAction action, float value, float value2) throws Exception
	{
		return value * value2;
	}
	
	public double devideValues(XmlAction action, int value, int value2) throws Exception
	{
		return value / value2;
	}
	
	public double devideValues(XmlAction action, long value, long value2) throws Exception
	{
		return (double)value / value2;
	}
	
	public double devideValues(XmlAction action, long value, int value2) throws Exception
	{
		return value / value2;
	}
	
	public double devideValues(XmlAction action, int value, long value2) throws Exception
	{
		return value / value2;
	}
	
	public double devideValues(XmlAction action, long value, double value2) throws Exception
	{
		return value / value2;
	}
	
	public double devideValues(XmlAction action, double value, double value2) throws Exception
	{
		return value / value2;
	}
	
	public BigDecimal devideValues(XmlAction action, BigDecimal value, BigDecimal value2) throws Exception
	{
		return value.divide(value2);
	}
	
	public double devideValues(XmlAction action, double value, int value2) throws Exception
	{
		return value / value2;
	}

	public float devideValues(XmlAction action, float value, float value2) throws Exception
	{
		return value / value2;
	}
}
