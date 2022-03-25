package com.datamelt.rules.core.action;

import com.datamelt.rules.core.XmlAction;
import com.datamelt.util.ActionAnnotation;

public class MathActionTrigonometry extends GenericAction {

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


}
