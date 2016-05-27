package fr.uv1.bettingServices;
import fr.uv1.bd.*;
import java.sql.*;
import java.util.ArrayList;

import fr.uv1.competition.*;


public class BetDAO {
	
	private String url="jdbc:postgresql://localhost:54321/Test";
	private String user="postgres";
	private String password="postgres";
	
	public BetDAO() {
		
	}
	
	//-----------------------------------------------------------------------------------------------------------
		/**
		 * Persist (store) a bet in the data base.
		 * 
		 * @param bet the bet to be stored.
		 * @return the bet with the updated value for the id.
		 * @throws SQLException
		 */

	
	public static void persist(Bet bet) throws SQLException {
		BetDAO dao = new BetDAO();
		//Connection c = DatabaseConnection.getConnection();
		//(long numberTokens, Competition competition, Competitor first, Competitor second, Competitor third,Subscriber subscriber)
		
		
			
			if (bet.getifPodium()==0) { // This is a winner type bet
				String request;
				request = "insert into bets(bettor_Id,competition_ID,montant,winner_Id,ifPodium)  values (" +
						 +bet.getBettor().getSubscriber_id()+"," +bet.getCompetition().getId()+
						"," +bet.getBettorBet()+
						"," +bet.getfirst().getId()+
						",0);";
				editBD.edit(dao.user,dao.password,dao.url, request );}

			if (bet.getifPodium()==1) { // This is a podium type bet
				// Create the bet of the first competitor hashtag winner
				String request;
				request = "insert into bets(bettor_Id, competition_ID,montant,winner_Id, second_Id, third_Id,ifPodium)  values (" +
						 +bet.getBettor().getSubscriber_id()+"," +bet.getCompetition().getId()+
						"," +bet.getBettorBet()+
						"," +bet.getfirst().getId()+bet.getsecond().getId()+bet.getthird().getId()+
						",1);";
				editBD.edit(dao.user,dao.password,dao.url, request );
			}



	}
	

	
	
	
	/**
	   * Update a bet value on the database.
	   * 
	   * @param bet the bet to be updated.
	   * @throws SQLException
	   */
	public static void update(Bet bet) throws SQLException {
		BetDAO dao = new BetDAO();  
	    // Get a database connection.yobvh
	    // Update the bet.
		if (bet instanceof Bet && bet.getifPodium()==0) { // This is a winner type bet
			String request;
			request = "update bets set (bettor_Id,competition_ID,montant,winner_Id,ifPodium)  values (" +
					 +bet.getBettor().getSubscriber_id()+"," +bet.getCompetition().getId()+
					"," +bet.getBettorBet()+
					"," +bet.getfirst().getId()+
					",0);";
			editBD.edit(dao.user,dao.password,dao.url, request );}
		
	    if (bet instanceof Bet && bet.getifPodium()==1) { // This is a podium type bet
	    	String request;
			request = "update bets set(bettor_Id, competition_ID,montant,winner_Id, second_Id, third_Id,ifPodium)  values (" +
					 +bet.getBettor().getSubscriber_id()+"," +bet.getCompetition().getId()+
					"," +bet.getBettorBet()+
					"," +bet.getfirst().getId()+bet.getsecond().getId()+bet.getthird().getId()+
					",1);";
			editBD.edit(dao.user,dao.password,dao.url, request );
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

		// Get a database connection.
	    
	    // Delete the bet.
		BetDAO dao = new BetDAO();
	    editBD.edit(dao.user,dao.password,dao.url, 
	    		"DELETE FROM bets where competition_id="+competition.getId()+", bettor_id"+subscriber.getSubscriber_id()+";");

	  }
	    
	    
	    public static ArrayList<Bet>  consult(Competition competition) throws SQLException{
	    	BetDAO dao = new BetDAO();
	    	ResultSet result=selectBD.select(dao.user,dao.password,dao.url, 
		    		"SELECT* FROM bets where competition_id="+competition.getId()+";");
	    	ArrayList<Bet> consultList =new ArrayList<Bet>();
	    	while(result.next()){
	    		consultList.add((Bet) result);
					
			} 
	    	return consultList;
	    }
	    	
}
	
	
	
	
	
	


