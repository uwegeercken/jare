/*
 * Created on 07.11.2006
 *
 * all code by uwe geercken
 */
package com.datamelt.rules.parser.xml;

import java.util.ArrayList;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.datamelt.util.Field;

/**
 * this parser is used to parse row definition files. these describe how
 * a fixed length ASCII file is constructed in regards to its fields. 
 * 
 * the file has to be written using the syntax as documented.
 * 
 * @author uwe geercken
 */
public class RowDefinitionParser extends DefaultHandler implements ContentHandler
{
    private ArrayList<Field> fields = new ArrayList<Field>();
    
    private Field field;
    
    private boolean fieldTagActive;
    
    private static final String TAG_FIELD                 = "field";
    private static final String TAG_FIELD_NAME            = "name";
    private static final String TAG_FIELD_DESCRIPTION     = "description";
    private static final String TAG_FIELD_START           = "start";
    private static final String TAG_FIELD_LENGTH	      = "length";
    
    /*
     * parses a given xml file, which defines the layout of the fields
     * in a fixed length ASCII file. every field has a fixed start
     * and length.
     */
    public void parse(String filename) throws Exception
    {
    	try
    	{
    		SAXParserFactory factory = SAXParserFactory.newInstance();
    		SAXParser saxParser = factory.newSAXParser();
    		saxParser.parse(filename,this);
    	}
    	catch(Exception ex)
    	{
    		throw new Exception("error parsing xml file: " + filename);
    	}
    }
    
    public void startDocument() throws SAXException
    {
      
    }
    
    public void startElement( String namespaceURI, String localName, String qName, Attributes atts ) throws SAXException
	  {
		// new rule starts here
        if(qName.equals(TAG_FIELD)&& !fieldTagActive)
        {
        	String fieldName = atts.getValue(TAG_FIELD_NAME);
        	String fieldDescription = "";
        	if(atts.getValue(TAG_FIELD_DESCRIPTION)!=null)
        	{
        		fieldDescription = atts.getValue(TAG_FIELD_DESCRIPTION);
        	}
        	int fieldStart= Integer.parseInt(atts.getValue(TAG_FIELD_START));
        	int fieldLength= Integer.parseInt(atts.getValue(TAG_FIELD_LENGTH));
        	
            field = new Field(fieldName,fieldDescription,fieldStart,fieldLength);
        	fieldTagActive=true;
        }
        
	  }

    public void endElement( String namespaceURI, String localName, String qName )
    {
        if(qName.equals(TAG_FIELD))
        {
            fieldTagActive=false;
            fields.add(field);
        }
    }

    public void endDocument()
    {
        
    }
    
    public ArrayList<Field> getFields()
    {
        return fields;
    }
    
    /**
     * returns the number of fields 
     */
    public int getNumberOfFields()
    {
        if(fields!=null)
        {
        	return fields.size();
        }
        else
        {
        	return 0;
        }
    }
    

}
