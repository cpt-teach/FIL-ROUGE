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

public class BetDAO {
	
	
	
	//-----------------------------------------------------------------------------------------------------------
		/**
		 * Persist (store) a bet in the data base.
		 * 
		 * @param bet the bet to be stored.
		 * @return the bet with the updated value for the id.
		 * @throws SQLException
		 */
	
	
	public static Bet persist(Bet bet) throws SQLException {

		//Connection c = DatabaseConnection.getConnection();
		//(long numberTokens, Competition competition, Competitor first, Competitor second, Competitor third,Subscriber subscriber)
		Connection c = DatabaseConnection.getConnection();
		try {
			// Persist the bet
			c.setAutoCommit(false);
			//bettor_Id,participation_Id,competition,Bet_result,montant
			
			if (bet.getifPodium()==0) { // This is a winner type bet
				PreparedStatement persistStatement = c.prepareStatement("insert into bets(bettor_Id, participation_Id, competition_ID,montant,winner_Id,ifPodium)  values (?, ?, ?, ?, ?, ? )");
				persistStatement.setInt(1, bet.getBettor().getSubscriberId());
				persistStatement.setInt(2,bet.getParticipation().getParticipationId());
				persistStatement.setInt(3, bet.getCompetition().getCompetitionId());
				persistStatement.setLong(4, bet.getBettorBet());
				persistStatement.setInt(5, bet.getfirst().getId());
				persistStatement.setInt(6,0);
				persistStatement.executeUpdate();
				persistStatement.close();
			if (bet.getifPodium()==1) { // This is a podium type bet
				// Create the bet of the first competitor hashtag winner
				PreparedStatement persistStatement1 = c.prepareStatement("insert into bets(bettor_Id, participation_Id, competition_ID,montant,winner_Id, second_Id, third_Id,ifPodium)  values (?, ?, ?, ?,?,?,?,?");
				persistStatement1.setInt(1, bet.getBettor().getSubscriberId());
				persistStatement1.setInt(2,bet.getParticipation().getParticipationId());
				persistStatement1.setInt(3, bet.getCompetition().getCompetitionId());
				persistStatement1.setLong(4, bet.getBettorBet());
				persistStatement1.setInt(5, bet.getfirst().getID());
				persistStatement1.setInt(6, bet.getsecond().getID());
				persistStatement1.setInt(7, bet.getthird().getID());
				persistStatement.setInt(8,1);
				persistStatement1.executeUpdate();
				persistStatement1.close();
			}

			// Retrieving the value of the id with a request on the sequence (bets_id_seq).
			PreparedStatement psIdValue = c.prepareStatement("select currval('bets_id_seq') as value_id");
			ResultSet resultSet = psIdValue.executeQuery();
			Integer id  = null;
			while(resultSet.next())
				id = resultSet.getInt("value_id");
			resultSet.close();
			psIdValue.close();
			c.commit();
			bet.setId(id);

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

		return bet;
	}
	
	
	/**
	   * Here Finding All bets in the database,
	   * @return the list of the bets instances
	   * @throws SQLException
	   */
	
	public static ArrayList<Bet> findAll(BettingSoft bettingSoft) throws SQLException {
		  
		// Get a database connection.
	    Connection c = DatabaseConnection.getConnection();
	    
	    // Retrieve the subscribers.
	    PreparedStatement psSelect = c.prepareStatement("select * from bets order by ifPodium");
	    ResultSet resultSet = psSelect.executeQuery();
	    ArrayList<Bet> bets = new ArrayList<Bet>();
	    while(resultSet.next()) {
	    	Bet bet = null;
	    	if (resultSet.getInt("ifPodium") != 0) // This is a podium type bet
	    		/** for this we need a method in Competition and competitor DAO to 
	    		 * get the object from ID
	    		 */
	    		bet = new Bet(resultSet.getInt("Id"),
	    				resultSet.getLong("montant"),
	    				resultSet.getInt("winner_Id"),
	    				resultSet.getInt("second_Id"),
	    				resultSet.getInt("thirdID"), 
	    				resultSet.getInt("competition_ID"),
	    				resultSet.getInt("bettor_Id"),
	    				resultSet.getInt("ifPodium")
	    				);//Need to find specific object with their identifier
	    	else // This is a winner type bet
	    		bet = new Bet(resultSet.getInt("id"),
	    	    				resultSet.getLong("montant"),
	    	    				resultSet.getInt("winner_Id"),
	    	    				resultSet.getInt("second_Id"),
	    	    				resultSet.getInt("thirdID"), 
	    	    				resultSet.getInt("competition_ID"),
	    	    				resultSet.getInt("bettor_Id"),
	    	    				resultSet.getInt("ifPodium")
	    	    				);
	    	bets.add(bet);
	    }
	    resultSet.close();
	    psSelect.close();
	    
	    // Closing the database connection.
	    c.close();
	    
	    return bets;
	  }
//$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
	
	
	/**
	   * Update a bet value on the database.
	   * 
	   * @param bet the bet to be updated.
	   * @throws SQLException
	   */
	public static void update(Bet bet) throws SQLException {
		  
	    // Get a database connection.
	    Connection c = DatabaseConnection.getConnection();

	    // Update the bet.
		if (bet instanceof Bet) { // This is a winner type bet
	    	PreparedStatement updateStatement = c.prepareStatement("update bets set bettor_Id=?, participation_Id=?, competition_ID=?,montant=?,winner_Id=?,ifPodium=? where id=? ");
	    	updateStatement.setInt(1, bet.getBettor().getSubscriberId());
	    	updateStatement.setInt(2,bet.getParticipation().getParticipationId());
			updateStatement.setInt(3, bet.getCompetition().getCompetitionId());
			updateStatement.setLong(4, bet.getBettorBet());
			updateStatement.setInt(5, bet.getfirst().getId());
			updateStatement.setInt(6,0);
			updateStatement.setLong(7, bet.getId());
			updateStatement.executeUpdate();
			updateStatement.close();
		}
	    if (bet instanceof Bet) { // This is a podium type bet
	    	PreparedStatement updateStatement = c.prepareStatement("update bets set bettor_Id=?, participation_Id=?, competition_ID=?,montant=?,winner_Id=?,ifPodium=?  secondID=?, thirdID=? where id=?");
	    	updateStatement.setInt(1, bet.getBettor().getSubscriberId());
	    	updateStatement.setInt(2,bet.getParticipation().getParticipationId());
			updateStatement.setInt(3, bet.getCompetition().getCompetitionId());
			updateStatement.setLong(4, bet.getBettorBet());
			updateStatement.setInt(5, bet.getfirst().getId());
			updateStatement.setInt(6, bet.getsecond().getId());
			updateStatement.setInt(7, bet.getthird().getId());
			updateStatement.setInt(8,1);
			updateStatement.setLong(9, bet.getId());
	    	updateStatement.executeUpdate();
	    	updateStatement.close();
	    }
		
	    // Closing the database connection.
	    c.close();
	  }

	
	/**
	   * Delete from the database a specific bet.
	   * 
	   * @param bet the bet to be deleted.
	   * @throws SQLException
	   */
	
	public static void delete(Bet bet) throws SQLException {
		  
		// Get a database connection.
	    Connection c = DatabaseConnection.getConnection();
	    
	    // Delete the bet.
	    PreparedStatement deleteStaement = c.prepareStatement("delete from bets where id=?");
	    deleteStaement.setInt(1, bet.getId());
	    deleteStaement.executeUpdate();
	    deleteStaement.close();

	    // Closing the database connection.
	    c.close();
	  }
	
	
	
	
	
	

}
