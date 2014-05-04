package com.datamelt.util;

public class FieldNotFoundException extends Exception
{
	  public FieldNotFoundException()
	  {
		  super(); 
	  }
	  
	  public FieldNotFoundException(String message) 
	  {
		  super(message); 
	  }
	  
	  public FieldNotFoundException(String message, Throwable cause) 
	  {
		  super(message, cause); 
	  }
	  
	  public FieldNotFoundException(Throwable cause) 
	  {
		  super(cause); 
	  }
}
