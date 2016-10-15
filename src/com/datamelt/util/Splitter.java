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
 * rather than a separator as the divider.
 * in case a xml file is used, you have to pass its name to the
 * setRowDefinitionFile() method. The file will be parsed and the input
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
	 * a csv file or a fixed length ASCII file. 
	 * 
	 * you can use the defined type constants in this class. 
	 * 
	 * @param	type	the type of the ASCII file to use (0 or 1)
	 */
	public Splitter(int type)
	{
		this.type = type;
	}
	
	/**
	 * constructor indicating which type of data file is used:
	 * a csv file or a fixed length ASCII file and the field separator
	 * used in the file to distinguish the fields.
	 * 
	 * you can use the defined type constants in this class. 
	 * 
	 * @param	type			the type of the ASCII file to use (0 or 1)
	 * @param	fieldSeperator	the separator that distinguishes the fields
	 */
	public Splitter(int type, String fieldSeperator)
	{
		this.type = type;
		this.fieldSeperator = fieldSeperator;
	}
	
	/**
	 * the layout of a fixed length ASCII row, can be defined in
	 * an external XML file.
	 * pass the name of the file to this method. the XML file will
	 * be parsed, so that the layout can be used.
	 * 
	 * @param fileName		the name of the row definition file
	 * @throws Exception	exception when the file was not found or can not be read
	 */
	public void setRowDefinitionFile(String fileName) throws Exception
	{
		parser = new RowDefinitionParser();
		parser.parse(fileName);
	}

	/**
	 * Gets a row object by specifying a line of data
	 * 
	 * @param line			a line of data
	 * @return				a row object
	 * @throws Exception	exception when no row definition file is defined
	 */
	public Row getRow(String line) throws Exception
	{
		if(type==TYPE_FIXED_LENGTH && parser==null)
		{
			throw new Exception("no row definition xml file specified for fixed length ascii file");
		}
		return new Row(splitRow(line));
		
	}
	
	/**
	 * Gets a RowFieldCollection object, containing the fields that the row
	 * consists of. the line argument is a line (row) from an ASCII file.
	 * 
	 * @param line			a line of data
	 * @return				a row field collection object
	 * @throws Exception	exception when no row definition file is defined
	 */
	public RowFieldCollection getRowFieldCollection(String line) throws Exception
	{
		if(type==TYPE_FIXED_LENGTH && parser==null)
		{
			throw new Exception("no row definition xml file specified for fixed length ascii file");
		}
		return new RowFieldCollection(splitRow(line));
		
	}
	
	/**
	 * returns an array of strings, containing the fields that the row
	 * consists of. the line argument is a line (or row) from an 
	 * ASCII file.
	 * 
	 * @param line			a line of data
	 * @return				an array of string values
	 * @throws Exception	exception if no row definition file is defined
	 */
	public String[] getFields(String line) throws Exception
	{
		if(type==TYPE_FIXED_LENGTH && parser==null)
		{
			throw new Exception("no row definition xml file specified for fixed length ASCII file");
		}
		return splitRow(line);
		
	}
	
	/**
	 * Splits a row of data from an ASCII file into its individual fields
	 * using the defined field separator.
	 * 
	 * @param line			a line of data
	 * @return				an array of string values
	 * @throws Exception	exception if the type of the file is undefined
	 */
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
	 * 
	 * @return		the field separator currently in use
	 */
	public String getFieldSeperator() 
	{
		return fieldSeperator;
	}

	/** 
	 * sets the field seperator that is used to delimit fields of a
	 * CSV file. default is a semicolon (;)
	 * 
	 * @param fieldSeperator		the field seperator to be used to distinguish individual fields
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
	 * 
	 * @return		the type of ASCII file
	 */
	public int getType() 
	{
		return type;
	}

	/**
	 * indicates if all input data (fields) from the ascii file should be trimmed (both sides).
	 * only used for fix-length ascii files
	 * 
	 * @return		an indicator if all field values should be trimmed
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
	 * @param	trimFields	indicator if all field values should be trimmed
	 */
	public void setTrimFields(boolean trimFields)
	{
		this.trimFields = trimFields;
	}

	/**
	 * indicates if a leading and trailing quote character (") should be removed.
	 * only used for csv files.
	 * 
	 * @return		indicator if all quote characters should be removed
	 * 
	 */
	public boolean getRemoveQuotes() {
		return removeQuotes;
	}

	/**
	 * sets if the leading and trailing quote character (") should be removed.
	 * only used for csv files.
	 * 
	 * @param 	removeQuotes	indication if all quote characters should be removed
	 */
	public void setRemoveQuotes(boolean removeQuotes) {
		this.removeQuotes = removeQuotes;
	}
	

}
