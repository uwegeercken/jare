package com.datamelt.rules.core.action;

import com.datamelt.rules.core.XmlAction;
import com.datamelt.util.ActionAnnotation;

import java.util.Random;

public class MathActionOperations extends GenericAction {
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

}
