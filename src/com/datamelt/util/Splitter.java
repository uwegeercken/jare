package com.datamelt.util;

import com.datamelt.util.Field;
import com.datamelt.util.Row;
import com.datamelt.rules.parser.xml.RowDefinitionParser;

/**
 * splitter class which does the splitting of ASCII format based rows
 * into separate fields using either a separator or an xml file, which
 * defines the field positions in a fixed length ASCII file.
 * 
 * the default is to use the semicolon (;) as a separator. a given row
 * will be split into its fields using this separator or another given
 * separator. it uses the java split method of the String class.
 * 
 * the other possibility is to split a given row into its fields by
 * providing an xml file, which defines the layout of the row. this way
 * fixed length ASCII files can used, where the row has a fixed layout
 * rather than a separator as the devider.
 * in case a xml file is used, you have to pass its name to the
 * setRowDefinitionFile() method. The fiel will be parsed and the input
 * data (line) will be split into its fields accordingly.
 * 
 * the getRow() method returns a Row object containing an array of fields
 * representing a given row of data (passed as argument) after it has
 * been split.
 * 
 * @author uwe geercken
 *
 */

public class Splitter
{

	public static final int TYPE_COMMA_SEPERATED 	= 0;
	public static final int TYPE_FIXED_LENGTH	 	= 1;
	
	//default type
	private int type                                = TYPE_COMMA_SEPERATED;
	private boolean trimFields                      = true;
	private boolean removeQuotes                    = false;
	
    public static final String SEPERATOR_SEMICOLON 	= ";";
    public static final String SEPERATOR_COMMA     	= ",";
    public static final String SEPERATOR_TAB     	= "\t";
    
    //default field seperator
    private String fieldSeperator 					= SEPERATOR_SEMICOLON;
	private RowDefinitionParser parser = null;
	
	/** 
	 * default constructor using the default type
	 * which is a comma seperated field 
	 *
	 */
	public Splitter()
	{
	}

	/**
	 * constructor indicating which type of data file is used:
	 * a csv file or a fixed length ascii file
	 * 
	 */
	public Splitter(int type)
	{
		this.type = type;
	}
	
	/**
	 * constructor indicating which type of data file is used:
	 * a csv file or a fixed length ascii file
	 * 
	 */
	public Splitter(int type, String fieldSeperator)
	{
		this.type = type;
		this.fieldSeperator = fieldSeperator;
	}
	
	/**
	 * the layout of a fixed length ascii row, can be defined in
	 * an external xml file.
	 * pass the name of the file to this method. the xml file will
	 * be parsed, so that the layout can be used.
	 */
	public void setRowDefinitionFile(String fileName) throws Exception
	{
		parser = new RowDefinitionParser();
		parser.parse(fileName);
	}

	/**
	 * returns a Row object, containing the fields that the row
	 * consists of. the line argument is a line (or row) from an 
	 * ASCII file.
	 */
	public Row getRow(String line) throws Exception
	{
		if(type==TYPE_FIXED_LENGTH && parser==null)
		{
			throw new Exception("no row definition xml file specified for fixed length ascii file");
		}
		return new Row(splitRow(line));
		
	}
	
	private String[] splitRow(String line) throws Exception
	{
		String [] fields;
		if(type==TYPE_COMMA_SEPERATED)
		{
			fields = line.split(fieldSeperator,-1);
			if(removeQuotes)
			{
				for(int i=0;i<fields.length;i++)
				{
					if(fields[i].startsWith("\"") && fields[i].endsWith("\""));
					{
						fields[i] = fields[i].substring(1,fields[i].length()-1);
					}
				}
			}
			else
			{
				
			}
		}
		else if(type==TYPE_FIXED_LENGTH)
		{
			fields = new String[parser.getFields().size()];
			for(int i=0;i<parser.getFields().size();i++)
			{
				Field field = (Field)parser.getFields().get(i);
				String fieldValue = line.substring(field.getStart(),field.getStart()+field.getLength());
				if(trimFields)
				{
					fields[i]=fieldValue.trim();
				}
				else
				{
					fields[i]=fieldValue;
				}
			}
		}
		else
		{
			throw new Exception("undefined row type");
		}
		return fields;
	}

	/**
	 * returns the currently used field seperator that is used as 
	 * delimiter for a CSV file.
	 */
	public String getFieldSeperator() 
	{
		return fieldSeperator;
	}

	/** 
	 * sets the field seperator that is used to delimit fields of a
	 * CSV file. default is a semicolon (;)
	 */
	public void setFieldSeperator(String fieldSeperator) 
	{
		this.fieldSeperator = fieldSeperator;
	}

	/**
	 * the type can be either a CSV file, which uses a seperator to devide
	 * one field from the other. or it can be a fixed length layout, where
	 * fields have an exact predefined length. in this case an xml file
	 * with the layout of the fields needs to be defined.
	 */
	public int getType() 
	{
		return type;
	}

	/**
	 * indicates if all input data (fields) from the ascii file should be trimmed (both sides).
	 * only used for fix-length ascii files
	 * 
	 */
	public boolean getTrimFields() 
	{
		return trimFields;
	}

	/**
	 * sets if the input data (fields) shall be trimmed (both sides).
	 * the value will only be used for fixed length ascii fiels.
	 * 
	 */
	public void setTrimFields(boolean trimFields)
	{
		this.trimFields = trimFields;
	}

	/**
	 * indicates if a leading and trailing quote character (") should be removed.
	 * only used for csv files.
	 * 
	 */
	public boolean getRemoveQuotes() {
		return removeQuotes;
	}

	/**
	 * sets if the leading and trailing quote character (") should be removed.
	 * only used for csv files.
	 * 
	 */
	public void setRemoveQuotes(boolean removeQuotes) {
		this.removeQuotes = removeQuotes;
	}
	

}
