 /*
 * Created on 07.05.2003
 *
 */
 
package com.datamelt.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.StringWriter;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;


 /** 
 * Utility class to write objects to a ASCII based format.
 * 
 * The format will be specified in external velocity templates.
 * 
 * @author uwegeercken
 *
 */
public class VelocityDataWriter
{
    public static final String XML_FILE_EXTENSION    = ".xml";
    public static final String HTML_FILE_EXTENSION   = ".html";
	private static final String DEFAULT_OBJECT_NAME  = "dbObject";
	
	private static final String RESOURCE_PATH        = "file.resource.loader.path";
	
	private String templatePath;
    private String templateName;
    private PrintStream out;
    
    private Template template;
    private Hashtable <String,Object>objects= new Hashtable<String,Object>();
    
    /**
     * constructor for velocity datawriter class expects the path and the name of a template 
     */
    public VelocityDataWriter(String templatePath, String templateName) throws Exception
    {
        this.templatePath = templatePath;
        this.templateName = templateName;
		Properties properties = new Properties();
        properties.setProperty(RESOURCE_PATH, templatePath);
        
        VelocityEngine ve = new VelocityEngine();
        ve.init(properties);
        template = ve.getTemplate(templateName);
    }
    
    /**
     * re-initialises the datawriter with a new template. this is useful
     * to re-use an existing datawriter object 
     */
    public void reInit(String templateName) throws Exception
    {
        objects.clear();
        this.templateName = templateName;
		Properties properties = new Properties();
        properties.setProperty(RESOURCE_PATH, templatePath);
        VelocityEngine ve = new VelocityEngine();
        ve.init(properties);
        template = ve.getTemplate(templateName);
    }
    
    /**
     * removes all objects that had been passed to this datawriter object
     *
     */
    public void clearObjects()
    {
        objects.clear();
    }
    
    /**
     * sets the output stream of the writer 
     */
    public void setOutputStream(PrintStream out)
    {
        this.out = out;
    }

    /**
     * add an object to a hashtable of objects so that they can be processed
     * at a later stage. the objectName argument will be that name which is
     * used in the velocity template to identify the object 
     */
    public void addObject(String objectName, Object object)
    {
    	objects.put(objectName,object);
    }
    
    /**
     * add an object to a hashtable of objects so that they can be processed
     * at a later stage. the object gets a default name of: dbObject which
     * identifies it in a velocity template 
     */
	public void addObject(Object object)
	{
		objects.put(DEFAULT_OBJECT_NAME,object);
	}
    
	/**
	 * merges all objects that have been passed to this writer with the given
	 * template and returns the resulting string representation. 
	 */
	public String merge() throws Exception
	{
		VelocityContext context = new VelocityContext();
    
		if (objects.size()== 0)
		{
			throw new Exception("VelocityDataWriter: no objects to process");
		}
		for (Enumeration<String> e = objects.keys(); e.hasMoreElements() ;)
		{
			String objectKey = (String)e.nextElement();
			context.put(objectKey, objects.get(objectKey));
		}
		StringWriter writer = new StringWriter();
		template.merge(context,writer);
		return writer.toString();
	}    
    
	/**
	 * first merges the objects that have been passed to this datawriter object
	 * with the velocity template and then writes the output to the given path
	 * and file. the filename is amended to contain the current timestamp.
	 */
    public void write(String path, String fileName) throws Exception
    {

		String result = merge();        
        String checkedPath = adjustPath(path);
        File f = new File(checkedPath);
        f.mkdirs();
        OutputStream o = new FileOutputStream(new File(checkedPath + fileName));
        out = new PrintStream(o);
        out.print(result);
        out.close();  
    }

	/**
	 * first merges the objects that have been passed to this datawriter object
	 * with the velocity template and then writes the output to the given
	 * outputstream
	 */
    public void write() throws Exception
    {
		String result = merge();        
        out.print(result);
    }

    /**
     * adjusts a given path to that it has a trailing forward slash
     * in case in is missing it 
     */
    private String adjustPath(String path)
	{
		if (path.endsWith("\\") || path.endsWith("/"))
		{
			return path;
		}
		else
		{
			return path + "/";
		}
	}

	/**
     * the path to the velocity templates
	 * @return path to the templates
	 */
	public String getTemplatePath()
	{
		return templatePath;
	}

	/**
	 * sets the path where the template may be found
	 */
	public void setTemplatePath(String path)
	{
		templatePath = path;
	}

	/**
	 * returns the name of the template
	 */
	public String getTemplateName()
	{
		return templateName;
	}

	/**
	 * sets the name of the template to be used
	 */
	public void setTemplateName(String string)
	{
		templateName = string;
	}

}
