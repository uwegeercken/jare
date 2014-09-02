package com.datamelt.rules.core.action;


public class ActionInvocationException extends Exception
{
	
	  private static final long serialVersionUID=100000;
	
	  public ActionInvocationException()
	  {
		  super(); 
	  }
	  
	  public ActionInvocationException(String message) 
	  {
		  super(message); 
	  }
	  
	  public ActionInvocationException(String message, Throwable cause) 
	  {
		  super(message, cause); 
	  }
	  
	  public ActionInvocationException(Throwable cause) 
	  {
		  super(cause); 
	  }
}
