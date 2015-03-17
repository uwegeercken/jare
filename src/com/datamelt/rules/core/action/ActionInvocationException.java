package com.datamelt.rules.core.action;

import java.io.Serializable;


public class ActionInvocationException extends Exception implements Serializable
{
	public static final long serialVersionUID = 1964070321;
	
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
