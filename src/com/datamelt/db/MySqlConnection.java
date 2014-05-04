package com.datamelt.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import java.sql.ResultSet;

/**
 * helper class to connect to a MySql database. encapsulates the most
 * often used functions into this simple class.
 * 
 * simply instantiate a connection object using one of the constructors. then
 * pass a sql string to the getResultset() methods to get a resultset containing
 * the database records.
 * 
 * call the getPreparedStatement() method using a sql string as parameter to get
 * a prepared statement, which in turn can be used to retrieve database records or
 * to update the database.
 *
 * @author uwe geercken
 * 
 */

public class MySqlConnection
{
	// default setting is to use localhost
	private static final String HOSTNAME_LOCALHOST = "localhost";
	// the connection string
	private static final String CONNECTSTRING = "jdbc:mysql://";
	// message when user tries to retrieve data but exception object
	// is undefined
	private static final String EXCEPTION_CONNECTION_OBJECT_UNDEFINED = "connection object is undefined";
	
	// set hostname to default
	private String hostname = HOSTNAME_LOCALHOST;
	// variable to contain the name of the database
	private String databaseName;
	// variable to contain the id of the user that connects to the database
	private String userid;
	// variable to contain the password of the user
	private String password;
	// variable to contain the port of the database server
	private int port = 3306;
	// a java sql connection object
	private Connection connection;
	
	
	/**
	 * constructor to create a connection using hostname, databasename, port, userid and password. 
	 */
	public MySqlConnection(String hostname,String databaseName,int port,String userid, String password) throws Exception
	{
		this.hostname = hostname;
		this.databaseName = databaseName;
		this.port = port;
		this.userid = userid;
		this.password = password;
		
		connect();
	}
	
	/**
	 * constructor to create a connection using hostname, databasename, userid and password. 
	 */
	public MySqlConnection(String hostname,String databaseName,String userid, String password) throws Exception
	{
		this.hostname = hostname;
		this.databaseName = databaseName;
		this.userid = userid;
		this.password = password;
		
		connect();
	}
	
	/**
	 * constructor to create a connection using databasename, userid and password. localhost
	 * is assumed to be the database server 
	 */
	public MySqlConnection(String databaseName,String userid, String password) throws Exception
	{
		
		this.databaseName = databaseName;
		this.userid = userid;
		this.password = password;
		
		connect();
	}
	/**
	 * returns a java.sql.Connection object using the given parameters
	 * of hostname, databasename, userid and password 
	 */
	public Connection getConnection() throws Exception
	{
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		return DriverManager.getConnection(CONNECTSTRING + hostname + ":" + port + "/" + databaseName + "?useUnicode=true&chracterEncoding=ISO-8859-1",userid,password);
	} 

	/**
	 * pass a valid sql string to this method and it returns a java sql resultset.
	 * throws an error if the connection object is undefined 
	 */
	public ResultSet getResultSet(String sql) throws Exception
	{
		if(connection!=null)
		{
			return connection.createStatement().executeQuery(sql);
		}
		else
		{
			throw new Exception(EXCEPTION_CONNECTION_OBJECT_UNDEFINED);
		}
	}
	
	/**
	 * pass a valid sql string to this method and it returns a java sql resultset.
	 * throws an error if the connection object is undefined 
	 * 
	 * resultsetType and resultsetConcurrency are the eqivalent values fron the$
	 * recordset class
	 */
	public ResultSet getResultSet(int resultsetType, int resultsetConcurrency,String sql) throws Exception
	{
		if(connection!=null)
		{
			return connection.createStatement(resultsetType,resultsetConcurrency).executeQuery(sql);
		}
		else
		{
			throw new Exception(EXCEPTION_CONNECTION_OBJECT_UNDEFINED);
		}
	}
	
	/**
	 * pass a valid sql string to this method and it returns a java sql preparedstatement.
	 * throws an error if the connection object is undefined 
	 */
	public PreparedStatement getPreparedStatement(String sql) throws Exception
	{
		if(connection!=null)
		{
			return connection.prepareStatement(sql);
		}
		else
		{
			throw new Exception(EXCEPTION_CONNECTION_OBJECT_UNDEFINED);
		}
		
	}

	/**
	 * returns the last_insert_id. this is that id that was last assigned to
	 * an autoincrement column of the mysql database.
	 * so if you insert a record into a table and the table has an autoincrement
	 * column then the id of this autoincrement column will be returned. 
	 */
	public long getLastInsertId() throws Exception
	{
		ResultSet rs = getResultSet("select last_insert_id() as lastid");
		rs.next();
		long lastId = rs.getLong("lastid");
		rs.close();
		return lastId;
	}

	/**
	 * sets the value of autocommit to true or false. if set to false, then
	 * you have to explicitly use the commit() function to write changes to the
	 * database.
	 */
	public void setAutoCommit(boolean status) throws Exception
	{
		connection.setAutoCommit(status);
	}
	
	/**
	 * closes the connection object. 
	 */
	public void close() throws Exception
	{
		connection.close();
	}
	
	/**
	 * commits a transaction that has been started 
	 */
	public void commit() throws Exception
	{
		connection.commit();
	}

	/**
	 * rolls back a transaction that has been started. this mean
	 * that all changes belonging to this transaction will NOT be
	 * applied to the database. 
	 */
	public void rollback() throws Exception
	{
		connection.rollback();
	}

	private void connect() throws Exception
	{
		this.connection = getConnection();
	}

	/**
	 * returns the hostname that is used for this connection object 
	 */
	public String getHostname() 
	{
		return hostname;
	}

	/**
	 * sets the hostname that is used for this connection object 
	 */
	public void setHostname(String hostname) 
	{
		this.hostname = hostname;
	}

	/**
	 * returns the database name that is used for this connection object 
	 */
	public String getDatabaseName() 
	{
		return databaseName;
	}

	/**
	 * sets the database name that is used for this connection object 
	 */
	public void setDatabaseName(String databaseName) 
	{
		this.databaseName = databaseName;
	}

	/**
	 * returns the userid that is used for this connection object 
	 */
	public String getUserid() 
	{
		return userid;
	}

	/**
	 * sets the userid that is used for this connection object 
	 */
	public void setUserid(String userid) 
	{
		this.userid = userid;
	}

	public int getPort()
	{
		return port;
	}

	public void setPort(int port)
	{
		this.port = port;
	}
	
}