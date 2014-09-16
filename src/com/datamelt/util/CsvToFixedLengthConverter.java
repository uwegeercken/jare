package com.datamelt.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import com.datamelt.util.Field;
import com.datamelt.rules.parser.xml.RowDefinitionParser;
import com.datamelt.util.Splitter;

/**
 * converts a csv file where the fields are seperated 
 * by a specific character, into a fixed length file,
 * where each field has a defined start and length.
 * 
 * - the xml file contains the definition of the fields and their length.
 * - the data file contains the data in form of a CSV file
 * - the output file is the new file in fixed length ASCII format that will be created
 * 
 * @author uwe geercken
 */
public class CsvToFixedLengthConverter
{
	
	/**
	 * converts a csv file to a fixed length ASCII file using the xml file containing the
	 * definition of the single fields of the output file.  
	 */
	public void convertToFixedLengthFile(String xmlFilename, String dataFilename, String outputFilename) throws Exception
	{
		// read the xml file, that defines the structure of
		// the fixed length fields
		
		RowDefinitionParser parser = new RowDefinitionParser();
		parser.parse(xmlFilename);
		
		//get the parsed fields
		ArrayList<Field> definitionFields = parser.getFields();
		
        // splitter object will split the row from the datafile into
        // its fields using - in this case - the default semicolon (;) seperator
        Splitter splitter = new Splitter(Splitter.TYPE_COMMA_SEPERATED);

        // file for output
        File outputFile = new File(outputFilename);
        OutputStream o = new FileOutputStream(outputFile);
        PrintStream out = new PrintStream(o);
        
        // reader for the data file
	    BufferedReader reader = new BufferedReader(new FileReader(dataFilename));
	    String line;
	    // counts number of lines in data file
	    long counter=0;
	    // loop over all lines of the file
	    while ((line=reader.readLine())!=null && line.trim().length()>0)
	    {
	        line = line.replace("\"","");
	        // get a row object containing the fields and data
	        Row row = splitter.getRow(line);
	        // run rules on this data
	        StringBuffer buffer = new StringBuffer();
	        
	        for(int i=0;i<definitionFields.size();i++)
	        {
	        	Field definitionField = (Field)definitionFields.get(i);
	        	String data = row.getField(i);
	        	int expectedLength = definitionField.getLength();

	        	// adjust the length of the fields according to the
				// definitions from the xml file
	        	String result = adjustLength(data,expectedLength);
	        	// apend to the data buffer
	        	buffer.append(result);
	        }
	        
	        //System.out.println(buffer.toString());

	        // write out the data to a new file
	        out.println(buffer.toString());

	        counter++;
	    }
        
	    reader.close();
	    // close stream
	    out.close();
        
        System.out.println("number of lines of data:   " + counter);
		
	}
	
	/*
	 * method adjusts the length of a value to the expected length.
	 * if the value is longer, then if will be trimmed to the expected
	 * length, if it is shorter, it will be filled with trailing spaces.
	 */
	private String adjustLength(String value, int expectedLength)
	{
		int valueLength= value.length();
		
		if(valueLength < expectedLength)
		{
			for(int i=0;i<(expectedLength-valueLength);i++)
			{
				value = value + " ";
			}
			return value;
		}
		else if(valueLength == expectedLength)
		{
			return value;
		}
		else
		{
			return value.substring(0,expectedLength);
		}
	}
}
