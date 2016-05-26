package fr.uv1.dao;

import java.sql.*;
import java.util.ArrayList;

import fr.uv1.bettingServices.Bet.Bet;
import fr.uv1.bettingServices.Compet.Competition;
import fr.uv1.bettingServices.Compet.Competitor;
import fr.uv1.bettingServices.Person.Subscriber;
import fr.uv1.bettingServices.Betting.BettingSoft;
import fr.uv1.bettingServices.Exceptions.AuthenticationException;
import fr.uv1.bettingServices.Exceptions.BadParametersException;
import fr.uv1.bettingServices.Exceptions.CompetitionException;
import fr.uv1.bettingServices.Exceptions.SubscriberException;
import fr.uv1.utils.*;

public class SubscriberDAO {
	
	
	
	//-----------------------------------------------------------------------------------------------------------
		/**
		 * Persist (store) a Subscriber in the data base.
		 * 
		 * @param Subscriber to be stored.
		 * @return the Subscriber with the updated value for the id.
		 * @throws SQLException
		 */
	
	
	public static void persist(Subscriber subscriber) throws SQLException {

		Connection c = DatabaseConnection.getConnection();
		try {
			// Persist the Subscriber
			c.setAutoCommit(false);
			
			
				PreparedStatement persistStatement = c.prepareStatement("insert into Subscriber(username,firstname,lastname,password,birthday,tokens values (?, ?, ?, ?, ?, ? )");
				//persistStatement.setInt(1, l'ID du Subscriber);
				persistStatement.setString(1,subscriber.getUserName());
				persistStatement.setString(2,subscriber.getFirstName());
				persistStatement.setString(3,subscriber.getLastName());
				persistStatement.setString(4, subscriber.getPassword());
				persistStatement.setString(5, subscriber.getBirthday().toString();
				persistStatement.setLong(6, subscriber.getTokens());
				persistStatement.executeUpdate();
				persistStatement.close();
			// Searching for the id_value of Subscriber
			PreparedStatement psIdValue = c.prepareStatement("select currval('subscriber_id') as value_id");
			ResultSet resultSet = psIdValue.executeQuery();
			Integer id  = null;
			while(resultSet.next())
				id = resultSet.getInt("value_id");
			resultSet.close();
			psIdValue.close();
			c.commit();
			Subscriber.setSubscriber_id(id);

		}}
		catch (SQLException e) {
			try {
				c.rollback();
			}
			catch (SQLException e1) {
				e1.printStackTrace();
			}
			c.setAutoCommit(true);
			throw e;
		}

		c.setAutoCommit(true);
		c.close();

		
	}
	
	
	/**
	   * Here Finding All bets in the database,
	   * @return the list of the bets instances
	   * @throws SQLException
	   */
	
	public static ArrayList<Subscriber> listOfSubscribers() throws SQLException {
		  
		// Get a database connection.
	    Connection c = DatabaseConnection.getConnection();
	    
	    // Retrieve the subscribers.
	    PreparedStatement psSelect = c.prepareStatement("select * from subscriber order by subscriber_id");
	    ResultSet resultSet = psSelect.executeQuery();
	    ArrayList<Subscriber> listOfSubscribers = new ArrayList<Subscriber>();
	    while(resultSet.next()) {
	    	Subscriber subscriber = null;
	    		subscriber = new Subscriber(resultSet.getInt("subscriber_id"),
	    				resultSet.getString("username"),
	    				resultSet.getString("password"),
	    				resultSet.getString("lastname"),
	    				resultSet.getString("firstname"),
	    				resultSet.getLong("tokens"),
	    				fromStringtoCalendar(resultSet.getString("birthdate")) 
	    				);// TODO Function

	    	listOfSubscribers.add(subscriber);
	    }
	    resultSet.close();
	    psSelect.close();
	    
	    // Closing the database connection.
	    c.close();
	    
	    return listOfSubscribers;
	  }
//$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
	
	
	/**
	   * Update a Subscriber on the database
	   * 
	   * @param subscriber to be updated
	   * @throws SQLException
	   */
	public static void update(Subscriber subscriber) throws SQLException {
		  
	    // Get a database connection.
	    Connection c = DatabaseConnection.getConnection();

	    // Update the bet.
	    	PreparedStatement updateStatement = c.prepareStatement("update subscriber set  username=?, password=?,lastname=?,firstname=?,tokens=?,birthdate=? where subscriber_id=? ");
	    	updateStatement.setString(1,subscriber.getUserName());
			updateStatement.setString(2, subscriber.getPassword());
			updateStatement.setString(3, subscriber.getLastName());
			updateStatement.setString(4, subscriber.getFirstName());
			updateStatement.setLong(5,subscriber.getTokens());
			updateStatement.setString(6, Subscriber.getBirthday().toString());
			updateStatement.setInt(7, subscriber.getSubscriber_id());
			updateStatement.executeUpdate();
			updateStatement.close();

		
	    // Closing the database connection.
	    c.close();
	  }

	
	/**
	   * Delete from the database a specific bet.
	   * 
	   * @param bet the bet to be deleted.
	   * @throws SQLException
	   */
	
	public static void delete(Subscriber subscriber) throws SQLException {
		  
		// Get a database connection.
	    Connection c = DatabaseConnection.getConnection();
	    
	    // Delete the bet.
	    PreparedStatement deleteStaement = c.prepareStatement("delete from subscriber where subscriber_id=?");
	    deleteStaement.setInt(1, subscriber.getSubscriber_id());
	    deleteStaement.executeUpdate();
	    deleteStaement.close();

	    // Closing the database connection.
	    c.close();
	  }
	public static Subscriber getSubscriberById(int subscriber_id){
		
		Connection c = DatabaseConnection.getConnection();
	
		PreparedStatement psSelect = c.prepareStatement("select * from subscriber where subscriber_id="+subscriber_id+);
		ResultSet resultSet = psSelect.executeQuery();
		Subscriber subscriber=null;
		
		while(resultSet.next()) {
	    		subscriber = new Subscriber(resultSet.getInt("subscriber_id"),
	    				resultSet.getString("username"),
	    				resultSet.getString("password"),
	    				resultSet.getString("lastname"),
	    				resultSet.getString("firstname"),
	    				resultSet.getLong("tokens"),
	    				fromStringtoCalendar(resultSet.getString("birthdate")) 
	    				);
	    		//TODO Function
		}
		
		resultSet.close();
	    psSelect.close();
	    
	    // Closing the database connection.
	    c.close();
	    
	    return subscriber;
	}
	public static Subscriber getSubscriberByUsername(String username){
		Connection c = DatabaseConnection.getConnection();
	
		PreparedStatement psSelect = c.prepareStatement("select * from subscriber where username="+username);
		ResultSet resultSet = psSelect.executeQuery();
		Subscriber subscriber=null;
		
		while(resultSet.next()) {
	    		subscriber = new Subscriber(resultSet.getInt("subscriber_id"),
	    				resultSet.getString("username"),
	    				resultSet.getString("password"),
	    				resultSet.getString("lastname"),
	    				resultSet.getString("firstname"),
	    				resultSet.getLong("tokens"),
	    				fromStringtoCalendar(resultSet.getString("birthdate")) 
	    				);
		}
		
		resultSet.close();
	    psSelect.close();
	    
	    // Closing the database connection.
	    c.close();
	    return subscriber;
	    
	}
	
}
