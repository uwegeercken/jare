/*
 * Created on 09.11.2006
 *
 * all code by uwe geercken
 */
package com.datamelt.rules.core.util;

import com.datamelt.rules.core.RuleSubGroup;

/**
 * helper class to do conversions that are used internally.
 * 
 * @author uwe geercken
 */
public class Converter
{
    /*
     * converts an integer value to logical value:
     * [and], [or] or [] and provides the result 
     * in form of a string.
     */
    public static String convertIntToLogical(int i)
    {
        if(i==RuleSubGroup.OPERATOR_AND)
        {
            return "and";
        }
        else if(i==RuleSubGroup.OPERATOR_OR) 
        {
            return "or";
        }
        else
        {
            return "[]";
        }
    }
    
    /*
     * converts an integer value to boolean value and
     * provides the result in form of a string.
     */
    public static String convertIntegerToBooleanString(int i) 
    {
        if(i==0)
        {
            return "false";
        }
        else
        {
            return "true";
        }
    }
}
