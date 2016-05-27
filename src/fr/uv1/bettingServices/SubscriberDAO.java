package fr.uv1.bettingServices;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import fr.uv1.bd.editBD;
import fr.uv1.bd.selectBD;
import fr.uv1.bettingServices.Exceptions.BadParametersException;
import fr.uv1.utils.MyCalendar;

public class SubscriberDAO{

	private static String url="jdbc:postgresql://localhost:5433/tests";
	private static String user="postgres";
	private static String password="postgres";
	
	public SubscriberDAO () {
		
	}

	public void persist(Subscriber subscriber) throws SQLException {
		String request="insert into Subscriber(username,firstname,lastname,password,birthday,tokens values ("+subscriber.getUserName()+","
				+subscriber.getFirstName()+", "
				+subscriber.getLastName()+", "
				+subscriber.getPassword()+","
				+subscriber.getBirthday().toString()+", "
				+subscriber.getTokens()+");";
		editBD.edit(user,password,url, request);
			request="select currval('subscriber_id') as value_id";
			ResultSet resultSet=selectBD.select(user,password,url,request);
			int id  = 0;
			while(resultSet.next())
				id = resultSet.getInt("value_id");

			subscriber.setSubscriber_id(id);		
	}
	
	
	/**
	   * Here Finding All bets in the database,
	   * @return the list of the bets instances
	   * @throws SQLException
	 * @throws BadParametersException 
	   */
	
	public ArrayList<Subscriber> listOfSubscribers() throws SQLException, BadParametersException{
		  
		// Get a database connection.
	    String request="select * from subscriber order by subscriber_id;";
	    ResultSet resultSet=selectBD.select(user,password,url,request);
	    ArrayList<Subscriber> listOfSubscribers = new ArrayList<Subscriber>();
	    while(resultSet.next()) {
	    	Subscriber subscriber = null;
	    		subscriber = new Subscriber(resultSet.getInt("subscriber_id"),
	    				resultSet.getString("username"),
	    				resultSet.getString("password"),
	    				resultSet.getString("lastname"),
	    				resultSet.getString("firstname"),
	    				resultSet.getLong("tokens"),
	    				MyCalendar.fromString(resultSet.getString("birthdate")) 
	    				);
	    	listOfSubscribers.add(subscriber);
	    }	    
	    return listOfSubscribers;
	  }
//$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
	
	
	/**
	   * Update a Subscriber on the database
	   * 
	   * @param subscriber to be updated
	   * @throws SQLException
	   */
	public void update(Subscriber subscriber) throws SQLException {
		  
	    	String request="update subscriber set  username="+subscriber.getUserName()+"," +
	    			" password="+subscriber.getPassword()+"," +
					"lastname="+subscriber.getLastName()+"," +
							"firstname="+subscriber.getFirstName()+"," +
									"tokens="+subscriber.getTokens()+"," +
											"birthdate="+subscriber.getBirthday().toString()+" where subscriber_id="+subscriber.getSubscriber_id()+";";

	    	editBD.edit(user,password,url, request);
	  }

	
	/**
	   * Delete from the database a specific bet.
	   * 
	   * @param bet the bet to be deleted.
	   * @throws SQLException
	   */
	
	public static void delete(Subscriber subscriber) throws SQLException {
		  
	    	String request="delete from subscriber where subscriber_id="+subscriber.getSubscriber_id()+";";
	    // Delete the bet.
	    	editBD.edit(user,password,url, request);
	    // Closing the database connection.
	  }
	public static Subscriber getSubscriberById(int subscriber_id) throws BadParametersException, SQLException{
		
		String request="select * from subscriber where subscriber_id="+subscriber_id+";";
		ResultSet resultSet=selectBD.select(user,password,url,request);
		Subscriber subscriber=null;
		while(resultSet.next()) {
	    		subscriber = new Subscriber(resultSet.getInt("subscriber_id"),
	    				resultSet.getString("username"),
	    				resultSet.getString("password"),
	    				resultSet.getString("lastname"),
	    				resultSet.getString("firstname"),
	    				resultSet.getLong("tokens"),
	    				MyCalendar.fromString(resultSet.getString("birthdate")) 
	    				);
		}
		
	    return subscriber;
	}
	public static Subscriber getSubscriberByUsername(String username) throws BadParametersException, SQLException{
	
		String request="select * from subscriber where subscriber_id="+username+";";
		ResultSet resultSet=selectBD.select(user,password,url,request);
		Subscriber subscriber=null;
		while(resultSet.next()) {
	    		subscriber = new Subscriber(resultSet.getInt("subscriber_id"),
	    				resultSet.getString("username"),
	    				resultSet.getString("password"),
	    				resultSet.getString("lastname"),
	    				resultSet.getString("firstname"),
	    				resultSet.getLong("tokens"),
	    				MyCalendar.fromString(resultSet.getString("birthdate")) 
	    				);
		}
		
	    return subscriber;
	    
	}
	}
	

