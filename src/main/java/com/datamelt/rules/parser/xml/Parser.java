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
package com.datamelt.rules.parser.xml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import com.datamelt.rules.core.ActionObject;
import com.datamelt.rules.core.Parameter;
import com.datamelt.rules.core.ReferenceField;
import com.datamelt.rules.core.RuleGroup;
import com.datamelt.rules.core.RuleMessage;
import com.datamelt.rules.core.RuleObject;
import com.datamelt.rules.core.RuleSubGroup;
import com.datamelt.rules.core.XmlRule;
import com.datamelt.rules.core.XmlAction;
import com.datamelt.rules.core.util.VariableReplacer;

/**
 * this parser is used to parse rule definition files. rule files have to be
 * written using the syntax as documented. groups, subgroups and rules are
 * collected from the file.
 * 
 * after the xml rule definition file has been parsed, an arraylist of groups
 * is available, containing the subgroups and rules.
 * 
 * @author uwe geercken
 */
public class Parser extends DefaultHandler implements ContentHandler
{
    private ArrayList<RuleGroup>groups = new ArrayList<RuleGroup>();
    private ArrayList<ReferenceField>referenceFields = new ArrayList<ReferenceField>();
    
    private VariableReplacer replacer;
    
    private RuleGroup group;
    private RuleSubGroup subgroup;
    private XmlRule xmlRule;
    private XmlAction action;
    private ActionObject actionObject;
    
    private boolean groupTagActive;
    private boolean subgroupTagActive;
    private boolean ruleTagActive;
    private boolean executeTagActive;
    private boolean actionTagActive;
    private boolean objectTagActive;
    
    private static final String TAG_GROUP                 				= "group";
    private static final String TAG_GROUP_ID              				= "id";
    private static final String TAG_GROUP_DESCRIPTION     				= "description";
    private static final String TAG_GROUP_OUTPUT_AFTER_ACTIONS 			= "outputafteractions";
    private static final String TAG_GROUP_VALID_FROM	  				= "validfrom";
    private static final String TAG_GROUP_VALID_UNTIL	  				= "validuntil";
    private static final String TAG_GROUP_DEPENDENT_GROUP_ID  			= "dependentgroupid";
    private static final String TAG_GROUP_DEPENDENT_GROUP_EXECUTE_IF 	= "dependentgroupexecuteif";
    private static final String TAG_SUBGROUP	          				= "subgroup";
    private static final String TAG_SUBGROUP_ID          				= "id";
    private static final String TAG_SUBGROUP_DESCRIPTION  				= "description";
    private static final String TAG_INTERGROUP_OPERATOR   				= "intergroupoperator";
    private static final String TAG_SUBGROUP_RULEOPERATOR 				= "ruleoperator";
    private static final String TAG_RULE                  				= "rule";
    private static final String TAG_RULE_ID               				= "id";
    private static final String TAG_RULE_DESCRIPTION      				= "description";
    private static final String TAG_OBJECT                				= "object";
    private static final String TAG_OBJECT_CLASSNAME      				= "classname";
    private static final String TAG_OBJECT_METHOD         				= "method";
	private static final String TAG_OBJECT_TYPE           				= "type";
    private static final String TAG_OBJECT_TYPE2          				= "returntype";
    private static final String TAG_OBJECT_PARAMETER      				= "parameter";
    private static final String TAG_OBJECT_PARAMETER_TYPE 				= "parametertype";
    private static final String TAG_OBJECT_PARAMETER_IS_SETTER_VALUE 	= "settervalue";
    private static final String TAG_EXPECTED              				= "expected";
    private static final String TAG_EXPECTED_VALUE        				= "value";
    private static final String TAG_EXPECTED_TYPE         				= "type";
    private static final String TAG_EXECUTE               				= "execute";
    private static final String TAG_EXECUTE_VALUE         				= "value";
    private static final String TAG_PARAMETER             				= "parameter";
    private static final String TAG_PARAMETER_VALUE       				= "value";
    private static final String TAG_PARAMETER_TYPE        				= "type";
    private static final String TAG_MESSAGE               				= "message";
    private static final String TAG_MESSAGE_TYPE		  				= "type";
    private static final String TAG_MESSAGE_TEXT		  				= "text";
    private static final String TAG_ACTION                				= "action";
    private static final String TAG_ACTION_ID             				= "id";
    private static final String TAG_ACTION_DESCRIPTION    				= "description";
    private static final String TAG_ACTION_CLASSNAME      				= "classname";
    private static final String TAG_ACTION_METHOD		  				= "method";
    private static final String TAG_ACTION_METHOD_TYPE	  				= "type";
    private static final String TAG_ACTION_METHOD_RETURNTYPE 			= "returntype";
    private static final String TAG_ACTION_PARAMETER      				= "parameter";
    private static final String TAG_ACTION_PARAMETERTYPE  				= "type";
    private static final String TAG_ACTION_PARAMETERVALUE 				= "value";
    private static final String TAG_ACTION_EXECUTEIF	  				= "executeif";
    
    private static final String TAG_REFERENCE_FIELD		  				= "field";
    private static final String TAG_REFERENCE_FIELD_NAME  				= "name";
    private static final String TAG_REFERENCE_FIELD_NAME_DESCRIPTIVE	= "namedescriptive";
    private static final String TAG_REFERENCE_FIELD_DESCRIPTION			= "description";
    private static final String TAG_REFERENCE_FIELD_JAVA_TYPE_ID		= "javatypeid";
    
    private static final String TAG_TYPE_TRUE   = "true";
        
    private static final String TAG_TYPE_FAILED = "failed";
    private static final String TAG_TYPE_PASSED = "passed";
    private static final String TAG_TYPE_ALWAYS = "always";
    
    private static final String TAG_TYPE_SETTER = "setter";
    
	/**
	 * constructor for a parser object that is used to parse a rule definition
	 * xml file. pass a VariableReplacer object to this constructor, which
	 * will be used to replace variables in the xml rule file with actual values.
	 * 
	 * @param	replacer	the replacer object
	 */
    public Parser(VariableReplacer replacer)
    {
        this.replacer = replacer;
    }
    
    /**
     * pass a filename of an xml rule definition file to this method, which will
     * in turn be parsed using SAX. 
     * 
     * @param	filename	the xml file to parse
     * @throws	Exception	exception if the xml file cannot be located or parsed
     */
    public void parse(String filename) throws Exception
    {
		File f = new File(filename);
		if(!f.exists())
		{
			throw new FileNotFoundException("file not found: " + filename);
		}
        try
        {
        	SAXParserFactory factory = SAXParserFactory.newInstance();
        	SAXParser saxParser = factory.newSAXParser();
        	saxParser.parse(filename,this);
        }
        catch(FileNotFoundException ex)
        {
        	throw new FileNotFoundException("error: " + filename + " not found");
        }
        catch(SAXParseException ex)
        {
        	throw new SAXException("error parsing xml file: " + filename + " line: " + ex.getLineNumber());
        }
    }
    
    /**
     * pass an inputstream of a xml file to this method, which will
     * in turn be parsed using SAX.
     * 
     *  @param	stream		the input stream to use for parsing
     *  @throws	Exception	exception during parsing the xml file
     */
    public void parse(InputStream stream) throws Exception
    {
    	try
    	{
    		SAXParserFactory factory = SAXParserFactory.newInstance();
    		SAXParser saxParser = factory.newSAXParser();
    		saxParser.parse(new org.xml.sax.InputSource(stream),this);
    	}
    	catch(SAXParseException ex)
        {
        	throw new SAXException("error parsing xml inputstream line: " + ex.getLineNumber());
        }
    }
    
    public void startDocument() throws SAXException
    {
    }
    
    /**
     * 
     * 
     */
    public void startElement( String namespaceURI, String localName, String qName, Attributes atts ) throws SAXException
	  {
		// new rule starts here
        if(qName.equals(TAG_GROUP)&& !groupTagActive)
        {
            groupTagActive=true;
            // group description may be empty
            String groupDescription = "";
            if(atts.getValue(TAG_GROUP_DESCRIPTION)!=null)
            {
            	groupDescription = atts.getValue(TAG_GROUP_DESCRIPTION);
            }
            group = new RuleGroup(atts.getValue(TAG_GROUP_ID),groupDescription);
            if(atts.getValue(TAG_GROUP_OUTPUT_AFTER_ACTIONS)!=null && atts.getValue(TAG_GROUP_OUTPUT_AFTER_ACTIONS).equals(TAG_TYPE_TRUE))
            {
            	group.setOutputAfterActions(Boolean.TRUE);
            }
            if(atts.getValue(TAG_GROUP_VALID_FROM)!=null)
            {
            	group.setValidFrom(atts.getValue(TAG_GROUP_VALID_FROM));
            }
            if(atts.getValue(TAG_GROUP_VALID_UNTIL)!=null)
            {
            	group.setValidUntil(atts.getValue(TAG_GROUP_VALID_UNTIL));
            }
            if(atts.getValue(TAG_GROUP_DEPENDENT_GROUP_ID)!=null)
            {
           		group.setDependentRuleGroupId(atts.getValue(TAG_GROUP_DEPENDENT_GROUP_ID));
            }
            if(atts.getValue(TAG_GROUP_DEPENDENT_GROUP_EXECUTE_IF)!=null)
            {
            	if(atts.getValue(TAG_GROUP_DEPENDENT_GROUP_EXECUTE_IF).equals(TAG_TYPE_PASSED))
                {
            		group.setDependentRuleGroupExecuteIf(0);
                }
            	else
                {
            		group.setDependentRuleGroupExecuteIf(1);
                }
            }
        }
        if(qName.equals(TAG_OBJECT) && groupTagActive && actionTagActive)
        {
        	objectTagActive=true;
        	actionObject = new ActionObject(atts.getValue(TAG_OBJECT_CLASSNAME),atts.getValue(TAG_OBJECT_METHOD));
        	actionObject.setReturnType(atts.getValue(TAG_ACTION_METHOD_RETURNTYPE));
        	if(atts.getValue(TAG_ACTION_METHOD_TYPE)!=null)
        	{
        		if(atts.getValue(TAG_ACTION_METHOD_TYPE).equals(Parser.TAG_TYPE_SETTER))
        		{
        			actionObject.setIsGetter(ActionObject.METHOD_SETTER);
        			action.setActionSetterObject(actionObject);
        		}
        		else
        		{
        			actionObject.setIsGetter(ActionObject.METHOD_GETTER);
        			action.addActionGetterObject(actionObject);
        		}
        	}
        	else
        	{
        		actionObject.setIsGetter(ActionObject.METHOD_GETTER);
        		action.addActionGetterObject(actionObject);
        	}
        }
        if(qName.equals(TAG_PARAMETER) && objectTagActive && actionTagActive)
        {
        	Parameter parameter = new Parameter(atts.getValue(TAG_ACTION_PARAMETERTYPE),atts.getValue(TAG_ACTION_PARAMETERVALUE));
            if(atts.getValue(TAG_OBJECT_PARAMETER_IS_SETTER_VALUE)!=null)
            {
            	if(atts.getValue(TAG_OBJECT_PARAMETER_IS_SETTER_VALUE).equals(TAG_TYPE_TRUE))
            	{
            		parameter.setSetterValue(true);
            	}
            }
        	actionObject.addParameter(parameter);
        }
        if(qName.equals(TAG_ACTION) && groupTagActive &&! objectTagActive &&!subgroupTagActive && !ruleTagActive)
        {
        	actionTagActive=true;
        	// action description may be empty
            String actionDescription = "";
            if(atts.getValue(TAG_ACTION_DESCRIPTION)!=null)
            {
            	actionDescription = atts.getValue(TAG_ACTION_DESCRIPTION);
            }
            // action id may be empty
            String actionId = "";
            if(atts.getValue(TAG_ACTION_ID)!=null)
            {
            	actionId = atts.getValue(TAG_ACTION_ID);
            }
        	if(atts.getValue(TAG_ACTION_EXECUTEIF).equals(TAG_TYPE_FAILED))
            {
        		action = new XmlAction(actionId,actionDescription);
        		action.setClassName(atts.getValue(TAG_ACTION_CLASSNAME));
        		action.setMethodName(atts.getValue(TAG_ACTION_METHOD));
        		action.setExecuteIf(1);
            }
        	else if(atts.getValue(TAG_ACTION_EXECUTEIF).equals(TAG_TYPE_PASSED))
        	{
        		action = new XmlAction(actionId,actionDescription);
        		action.setClassName(atts.getValue(TAG_ACTION_CLASSNAME));
        		action.setMethodName(atts.getValue(TAG_ACTION_METHOD));
        		action.setExecuteIf(0);
        	}
        	else if(atts.getValue(TAG_ACTION_EXECUTEIF).equals(TAG_TYPE_ALWAYS))
        	{
        		action = new XmlAction(actionId,actionDescription);
        		action.setClassName(atts.getValue(TAG_ACTION_CLASSNAME));
        		action.setMethodName(atts.getValue(TAG_ACTION_METHOD));
        		action.setExecuteIf(2);
        	}
        }
        if(qName.equals(TAG_PARAMETER) && actionTagActive && groupTagActive &&! objectTagActive &&!subgroupTagActive && !ruleTagActive)
        {
        	action.addParameter(new Parameter(atts.getValue(TAG_ACTION_PARAMETERTYPE),atts.getValue(TAG_ACTION_PARAMETERVALUE)));
        }
        if(qName.equals(TAG_SUBGROUP) && groupTagActive && !subgroupTagActive)
        {
            subgroupTagActive=true;
            // subgroup description may be empty
            String subgroupDescription = "";
            if(atts.getValue(TAG_SUBGROUP_DESCRIPTION)!=null)
            {
            	subgroupDescription = atts.getValue(TAG_SUBGROUP_DESCRIPTION);
            }
            // if intergroup operator is not specified, it is AND per default
            String intergroupOperator = RuleSubGroup.OPERATOR_AND_EXPRESSION;
            if(atts.getValue(TAG_INTERGROUP_OPERATOR)!=null)
            {
            	intergroupOperator = atts.getValue(TAG_INTERGROUP_OPERATOR);
            }
            // if rule operator is not specified it is AND per default
            String ruleOperator = RuleSubGroup.OPERATOR_AND_EXPRESSION;
            if(atts.getValue(TAG_SUBGROUP_RULEOPERATOR) !=null)
            {
            	ruleOperator = atts.getValue(TAG_SUBGROUP_RULEOPERATOR);
            }
            subgroup = new RuleSubGroup(atts.getValue(TAG_SUBGROUP_ID),subgroupDescription,intergroupOperator,ruleOperator);
        }
        if(qName.equals(TAG_RULE) && subgroupTagActive && !ruleTagActive)
        {
            ruleTagActive=true;
            // rule description may be empty
            String ruleDescription = "";
            if(atts.getValue(TAG_RULE_DESCRIPTION) !=null)
            {
            	ruleDescription = atts.getValue(TAG_RULE_DESCRIPTION);
            }
            xmlRule = new XmlRule(atts.getValue(TAG_RULE_ID),ruleDescription);
        }
        
        if(ruleTagActive && qName.equals(TAG_OBJECT))
        {
            String parameterValue= atts.getValue(TAG_OBJECT_PARAMETER);
            if (replacer!=null && replacer.getNumberOfVariables()>0)
            {
                try
                {
                    parameterValue = replacer.getValue(parameterValue);
                }
                catch(Exception ex)
                {
                    throw new SAXException(ex.getMessage());
                }
            }
            // the following lines are just for the better understanding of the xml syntax
            // the TAG_OBJECT_TYPE = type is not clear enough; it is really the return type 
            // of the method
            String methodReturnType = null;
            if(atts.getValue(TAG_OBJECT_TYPE)!=null)
            {
            	methodReturnType = atts.getValue(TAG_OBJECT_TYPE);
            }
            else
            {
            	methodReturnType = atts.getValue(TAG_OBJECT_TYPE2);
            }
            xmlRule.getRuleObjects().add(new RuleObject(atts.getValue(TAG_OBJECT_CLASSNAME),atts.getValue(TAG_OBJECT_METHOD),methodReturnType,parameterValue,atts.getValue(TAG_OBJECT_PARAMETER_TYPE)));
        }
        else if(ruleTagActive && qName.equals(TAG_EXPECTED))
        {
            // replacer object replaces variables with real
            // values based on a properties file
            try
            {
                String expectedRuleValue = atts.getValue(TAG_EXPECTED_VALUE);
                if (replacer!=null && replacer.getNumberOfVariables()>0)
                {
                    expectedRuleValue = replacer.getValue(expectedRuleValue);
                }
                xmlRule.setExpectedValueRule(expectedRuleValue);
                xmlRule.setExpectedValueRuleType(atts.getValue(TAG_EXPECTED_TYPE));
            }
            catch(Exception ex)
            {
                throw new SAXException(ex.getMessage());
            }
        }
        else if(ruleTagActive && qName.equals(TAG_EXECUTE))
        {
            executeTagActive=true;
            xmlRule.setCheckToExecute(atts.getValue(TAG_EXECUTE_VALUE));
        }
        else if(ruleTagActive && qName.equals(TAG_MESSAGE))
        {
            if(atts.getValue(TAG_MESSAGE_TYPE).equals(TAG_TYPE_FAILED))
            {
                xmlRule.getRuleMessages().add(new RuleMessage(RuleMessage.TYPE_FAILED, atts.getValue(TAG_MESSAGE_TEXT)));
            }
            else if(atts.getValue(TAG_MESSAGE_TYPE).equals(TAG_TYPE_PASSED))
            {
                xmlRule.getRuleMessages().add(new RuleMessage(RuleMessage.TYPE_PASSED, atts.getValue(TAG_MESSAGE_TEXT)));
            }
        }
        else if(ruleTagActive && qName.equals(TAG_ACTION))
        {
        	int type;
        	if(atts.getValue(TAG_ACTION_EXECUTEIF)==TAG_TYPE_FAILED)
        	{
        		type=XmlAction.TYPE_FAILED;
        	}
        	else if(atts.getValue(TAG_ACTION_EXECUTEIF)==TAG_TYPE_PASSED)
        	{
        		type=XmlAction.TYPE_PASSED;
        	}
        	else
        	{
        		type=XmlAction.TYPE_ALWAYS;
        	}
        	// action description may be empty
            String actionDescription = "";
            if(atts.getValue(TAG_ACTION_DESCRIPTION)!=null)
            {
            	actionDescription = atts.getValue(TAG_ACTION_DESCRIPTION);
            }
            // action id may be empty
            String actionId = "";
            if(atts.getValue(TAG_ACTION_ID)!=null)
            {
            	actionId = atts.getValue(TAG_ACTION_ID);
            }
            XmlAction action= new XmlAction(actionId,actionDescription);
        	action.setClassName(atts.getValue(TAG_ACTION_CLASSNAME));
        	action.setMethodName(atts.getValue(TAG_ACTION_METHOD));
        	action.setExecuteIf(type);
        	action.addParameter(new Parameter(atts.getValue(TAG_ACTION_PARAMETERTYPE),atts.getValue(TAG_ACTION_PARAMETER)));
        	action.setDescription(actionDescription);
        	action.setId(actionId);
        	xmlRule.getRuleActions().add(action);
        }
        if(executeTagActive && qName.equals(TAG_PARAMETER))
        {
            String parameterValue= atts.getValue(TAG_PARAMETER_VALUE);
            if (replacer!=null && replacer.getNumberOfVariables()>0)
            {
                try
                {
                    parameterValue = replacer.getValue(parameterValue);
                }
                catch(Exception ex)
                {
                    throw new SAXException(ex.getMessage());
                }
            }
            Parameter para = new Parameter(atts.getValue(TAG_PARAMETER_TYPE),parameterValue);
            xmlRule.addParameter(para);
        }
        
        if(qName.equals(TAG_REFERENCE_FIELD))
        {
            ReferenceField field = new ReferenceField();
        	if(atts.getValue(TAG_REFERENCE_FIELD_NAME) !=null)
            {
            	field.setName(atts.getValue(TAG_REFERENCE_FIELD_NAME));
            }
        	if(atts.getValue(TAG_REFERENCE_FIELD_NAME_DESCRIPTIVE) !=null)
            {
            	field.setNameDescriptive(atts.getValue(TAG_REFERENCE_FIELD_NAME_DESCRIPTIVE));
            }
        	if(atts.getValue(TAG_REFERENCE_FIELD_DESCRIPTION) !=null)
            {
            	field.setDescription(atts.getValue(TAG_REFERENCE_FIELD_DESCRIPTION));
            }
        	if(atts.getValue(TAG_REFERENCE_FIELD_JAVA_TYPE_ID) !=null)
            {
            	field.setJavaTypeId(Integer.parseInt(atts.getValue(TAG_REFERENCE_FIELD_JAVA_TYPE_ID)));
            }
        	referenceFields.add(field);
        }
    }

    public void endElement( String namespaceURI, String localName, String qName )
    {
        if(qName.equals(TAG_RULE))
        {
            ruleTagActive=false;
            subgroup.getRulesCollection().add(xmlRule);
        }
        else if(qName.equals(TAG_EXECUTE))
        {
            executeTagActive=false;
        }
        else if(qName.equals(TAG_SUBGROUP))
        {
            subgroupTagActive=false;
            group.getSubGroupCollection().add(subgroup);
        }
        else if(qName.equals(TAG_ACTION))
        {
            actionTagActive=false;
        	group.addAction(action);
        }
        else if(qName.equals(TAG_OBJECT))
        {
            objectTagActive=false;
        }
        else if(qName.equals(TAG_GROUP))
        {
            groupTagActive=false;
            
            try
            {
            	// only add the group if it is valid on the current date
            	// and if the valid from and valid until dates can be parsed correctly
            	if(group.isValid())
            	{
                	groups.add(group);
                }
            }
            catch(Exception ex)
            {
            	ex.printStackTrace();
            }
            
        }
    }

    public void endDocument()
    {
    }
    
    /**
     * returns an arraylist of groups that have been constructed by parsing
     * a xml rule definition file 
     * 
     * @return	list of rulegroups
     */
    public ArrayList<RuleGroup> getGroups()
    {
        return groups;
    }
    
    /**
     * returns an arraylist of fields that have been constructed by parsing
     * a xml rule definition file 
     * 
     * @return	list of fields
     */
    public ArrayList<ReferenceField> getReferenceFields()
    {
        return referenceFields;
    }
}
