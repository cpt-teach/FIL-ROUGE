package fr.uv1.bettingServices;
import fr.uv1.bd.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import fr.uv1.bettingServices.*;
import fr.uv1.competition.*;
import fr.uv1.bettingServices.Exceptions.*;

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
	private static String url="jdbc:postgresql://localhost:54321/Test";
	private static String user="postgres";
	private static String password="postgres";
	
	public static void persist(Bet bet) throws SQLException {

		//Connection c = DatabaseConnection.getConnection();
		//(long numberTokens, Competition competition, Competitor first, Competitor second, Competitor third,Subscriber subscriber)
		
		
			
			if (bet.getifPodium()==0) { // This is a winner type bet
				String request;
				request = "insert into bets(bettor_Id,competition_ID,montant,winner_Id,ifPodium)  values (" +
						 +bet.getBettor().getSubscriberId()+"," +bet.getCompetition().getCompetitionId()+
						"," +bet.getBettorBet()+
						"," +bet.getfirst().getID()+
						",0);";
				ResultSet result = editBD.edit(user,password,url, request );}

			if (bet.getifPodium()==1) { // This is a podium type bet
				// Create the bet of the first competitor hashtag winner
				String request;
				request = "insert into bets(bettor_Id, competition_ID,montant,winner_Id, second_Id, third_Id,ifPodium)  values (" +
						 +bet.getBettor().getSubscriberId()+"," +bet.getCompetition().getCompetitionId()+
						"," +bet.getBettorBet()+
						"," +bet.getfirst().getID()+bet.getsecond().getID()+bet.getthird().getID()+
						",1);";
				ResultSet result1 = editBD.edit(user,password,url, request );
			}



	}
	

	
	
	
	/**
	   * Update a bet value on the database.
	   * 
	   * @param bet the bet to be updated.
	   * @throws SQLException
	   */
	public static void update(Bet bet) throws SQLException {
		  
	    // Get a database connection.yobvh
	    // Update the bet.
		if (bet instanceof Bet && bet.getifPodium()==0) { // This is a winner type bet
			String request;
			request = "update bets set (bettor_Id,competition_ID,montant,winner_Id,ifPodium)  values (" +
					 +bet.getBettor().getSubscriberId()+"," +bet.getCompetition().getCompetitionId()+
					"," +bet.getBettorBet()+
					"," +bet.getfirst().getID()+
					",0);";
			ResultSet result = editBD.edit(user,password,url, request );}
		
	    if (bet instanceof Bet && bet.getifPodium()==1) { // This is a podium type bet
	    	String request;
			request = "update bets set(bettor_Id, competition_ID,montant,winner_Id, second_Id, third_Id,ifPodium)  values (" +
					 +bet.getBettor().getSubscriberId()+"," +bet.getCompetition().getCompetitionId()+
					"," +bet.getBettorBet()+
					"," +bet.getfirst().getID()+bet.getsecond().getID()+bet.getthird().getID()+
					",1);";
			ResultSet result1 = editBD.edit(user,password,url, request );
	    }
		
	    // Closing the database connection.

}

	
	/**
	   * Delete from the database a specific bet.
	   * 
	   * @param bet the bet to be deleted.
	   * @throws SQLException
	   */
	
	public static void delete(Competition competition, Subscriber subscriber) throws SQLException {
		  //DELETE FROM Customers
		//WHERE CustomerName='Alfreds Futterkiste' AND ContactName='Maria Anders'; 
		// Get a database connection.
	    
	    // Delete the bet.
	    ResultSet result = editBD.edit(user,password,url, 
	    		"DELETE FROM bets where competition_id="+competition.getId()+", bettor_id"+subscriber.getId()+";");

	  }
	    
	    
	    public static ArrayList<Bet>  consult(Competition competition) throws SQLException{
	    	ResultSet result=selectBD.select(user,password,url, 
		    		"SELECT* FROM bets where competition_id="+competition.getId()+";");
	    	ArrayList<Bet> consultList =new ArrayList<Bet>();
	    	while(result.next()){
	    		consultList.add((Bet) result);
					
			} 
	    	return consultList;
	    }
	    	
}
	
	
	
	
	
	


