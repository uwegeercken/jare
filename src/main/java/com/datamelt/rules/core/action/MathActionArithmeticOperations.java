package com.datamelt.rules.core.action;

import com.datamelt.rules.core.XmlAction;
import com.datamelt.util.ActionAnnotation;
import com.datamelt.util.ActionMethodAnnotation;

import java.math.BigDecimal;
import java.util.Date;

public class MathActionArithmeticOperations extends GenericAction {


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

}
