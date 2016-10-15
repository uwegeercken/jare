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
	// additional connection settings
	private static final String CONNECTSTRING_ADDITIONAL = "?useUnicode=true&characterEncoding=ISO-8859-1";
	// message when user tries to retrieve data but the connection object is undefined
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
     *
     * @param hostname 		hostname or IP adress of the database server 
     * @param databaseName 	the name of the database to use
     * @param port		 	the port the database server listens on
     * @param userid	 	the id of the user - with appropriate right
     * @param password	 	the password of the user
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
	 * uses the default MySQL port for the connection.
     *
     * @param hostname 		hostname or IP adress of the database server 
     * @param databaseName 	the name of the database to use
     * @param userid	 	the id of the user - with appropriate right
     * @param password	 	the password of the user
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
	 * constructor to create a connection using databasename, userid and password.
	 * uses localhost as hostname and the default MySQL port for the connection.
     *
     * @param databaseName 	the name of the database to use
     * @param userid	 	the id of the user - with appropriate right
     * @param password	 	the password of the user
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
	 * of hostname, databasename, userid and password. 
     *
     * @return				a connection object
     */
	public Connection getConnection() throws Exception
	{
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		return DriverManager.getConnection(CONNECTSTRING + hostname + ":" + port + "/" + databaseName + CONNECTSTRING_ADDITIONAL,userid,password);
	} 

	/**
	 * Executes the given SQL statement and returns the results.
	 * 
	 * @param sql 		the SQL statement to execute
     * @return			a ResultSet containing the results of the query
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
	 * Executes the given SQL statement and returns the results.
	 * 
     * @param resultsetType 		the equivalent type of the RecordSet class
     * @param resultsetConcurrency	the equivalent type of the RecordSet class
     * @param sql 					the SQL statement to execute
     * @return						a ResultSet containing the results of the query
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
	 * Creates a prepared statement object for the given SQL statement 
	 * 
     * @param sql 		the SQL statement to execute
     * @return			a prepared statement
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
	 * 
	 * if a record is inserted into a table and the table has an autoincrement
	 * column then the id of this autoincrement column will be returned. 
	 * 
     * @return			the id of the last inserted record
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
	 * Sets autocommit to true or false. if set to false, then
	 * commit() method has to be used explicitly to write changes to the database
	 * 
     * @param status 		indicator if autocommit shall be used or not
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

	/**
	 * connect to the server using the given parameters 
	 */
	private void connect() throws Exception
	{
		this.connection = getConnection();
	}

	/**
	 * get the hostname that is used for this connection object
	 * 
     * @return			the hostname
	 */
	public String getHostname() 
	{
		return hostname;
	}

	/**
	 * sets the hostname that is used for this connection object 
	 * 
     * @param hostname 		the hostname or IP adress of the database server
	 */
	public void setHostname(String hostname) 
	{
		this.hostname = hostname;
	}

	/**
	 * gets the database name that is used for this connection object 
	 * 
	 * @return			the database name 
	 */
	public String getDatabaseName() 
	{
		return databaseName;
	}

	/**
	 * sets the database name that is used for this connection object
	 * 
     * @param databaseName 		the name of the database
	 */
	public void setDatabaseName(String databaseName) 
	{
		this.databaseName = databaseName;
	}

	/**
	 * gets the userid that is used for this connection object 
	 * 
	 * @return			id of the user 
	 */
	public String getUserid() 
	{
		return userid;
	}

	/**
	 * sets the userid that is used for this connection object 
	 * 
	 * @param userid 		the id of the user
	 */
	public void setUserid(String userid) 
	{
		this.userid = userid;
	}

	/**
	 * gets the port that the database server listens on 
	 * 
	 * @return			id of the user 
	 */
	public int getPort()
	{
		return port;
	}

	/**
	 * sets the port that is used for this connection object 
	 * 
	 * @param port 		the numeric port number
	 */
	public void setPort(int port)
	{
		this.port = port;
	}
	
}