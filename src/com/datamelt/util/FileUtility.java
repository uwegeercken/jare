package com.datamelt.util;

import java.io.File;
import java.io.FilenameFilter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileUtility
{
	// the default timestamp format for the filename
	public static final String DEFAULT_TIMESTAMP_FORMAT     = "yyyyMMddHHmmss";
	public static final String XML_FILE_EXTENSION  			= ".xml";
	public static final String FORWARD_SLASH				= "/";
	
	/**
     * adds the current timestamp to the given filename
     * by trying to locate the dot and setting the timestamp
     * before it. if none is found, the timestamp is appended.
     *  
     * @param filename	the name of the file
     * @return			the name of the file and the prepended timestamp
     */
    public static String getTimeStampedFilename(String filename)
    {
    	SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_TIMESTAMP_FORMAT);
    	int dotPosition = filename.lastIndexOf(".");
    	if(dotPosition>0)
    	{
    		return filename.substring(0,dotPosition) + "_" + sdf.format(new Date()) + filename.substring(dotPosition);
    	}
    	else
    	{
    		return filename + "_" + sdf.format(new Date());
    	}
    	
    }
    
    /**
     * adds the current timestamp to the given filename
     * by trying to locate the dot and setting the timestamp
     * before it. if none is found, the timestamp is appended.
     *  
     * @param filename			the name of the file
     * @param timestampFormat	the format of the timestamp according to the SimpleDateFormat class
     * @return					the name of the file and the prepended timestamp
     */
    public static String getTimeStampedFilename(String filename, String timestampFormat)
    {
    	SimpleDateFormat sdf = new SimpleDateFormat(timestampFormat);
    	int dotPosition = filename.lastIndexOf(".");
    	if(dotPosition>0)
    	{
    		return filename.substring(0,dotPosition) + "_" + sdf.format(new Date()) + filename.substring(dotPosition);
    	}
    	else
    	{
    		return filename + "_" + sdf.format(new Date());
    	}
    }
    
    /**
     * returns a array of xml files that are in the specified
     * folder.
     * only files that end in .xml are returned.
     *   
     * @param folder		the name of the folder where the xml files are stored
     * @return				list of xml files
     */
    public static File[] getXmlFiles(String folder)
    {
    	 File dir = new File(folder);
         File[] fileList = dir.listFiles(new FilenameFilter() {
             public boolean accept(File f, String s) {return s.endsWith(XML_FILE_EXTENSION);}
         	});
         return fileList;
    }
    
    /**
     * returns a array of files that are in the specified folder.
     * 
     * @param folder	the name of the folder where the xml files are stored
     * @return			array of files
     */
    public static File[] getFiles(String folder)
    {
    	 File dir = new File(folder);
         return dir.listFiles();
    }
    
    /**
     * Add a trailing slash if not already present
     * 
     * @param folder	the name of the folder
     * @return			the name of the folder with a slash character at the end
     */
    public static String adjustSlash(String folder)
    {
    	if(folder.endsWith("/"))
    	{
    		return folder;
    	}
    	else
    	{
    		return folder + FORWARD_SLASH;
    	}
    }
    
    /**
     * Checks if the given filename exists as a file and is a file (not directory)
     * 
     * @param folder		the name of the folder
     * @param filename		the name of the file to check
     * @return				indicator if the file exists and is a file
     */
    public static boolean fileExists(String folder, String filename)
    {
    	String fullFilename = adjustSlash(folder) + filename;
    	File file = new File(fullFilename);
    	if(file.exists() && file.isFile())
    	{
    		return true;
    	}
    	else
    	{
    		return false;
    	}
    }
    
    /**
     * Checks if the given filename exists as a file and is a file (not directory)
     * 
     * @param fullFilename	the complete path and name of the file
     * @return				indicator if the file exists and is a file
     */
    public static boolean fileExists(String fullFilename)
    {
    	File file = new File(fullFilename);
    	if(file.exists() && file.isFile())
    	{
    		return true;
    	}
    	else
    	{
    		return false;
    	}
    }
}
