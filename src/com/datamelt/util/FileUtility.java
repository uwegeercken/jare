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
	
	/**
     * adds the current timestamp to the given filename
     * by trying to locate the dot and setting the timestamp
     * before it. if none is found, the timestamp is appended. 
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
     * returns a array of files that are in the specified
     * folder.
     */
    public static File[] getFiles(String folder)
    {
    	 File dir = new File(folder);
         return dir.listFiles();
    }
}
