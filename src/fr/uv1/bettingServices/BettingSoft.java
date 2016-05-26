package lot3;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import lot2.Exceptions.*;

import fr.uv1.bettingServices.Exceptions.AuthenticationException;
import fr.uv1.bettingServices.Exceptions.BadParametersException;
import fr.uv1.bettingServices.Exceptions.CompetitionException;
import fr.uv1.bettingServices.Exceptions.ExistingCompetitionException;
import fr.uv1.bettingServices.Exceptions.ExistingSubscriberException;
import fr.uv1.bettingServices.Exceptions.SubscriberException;
import fr.uv1.bettingServices.Person.Subscriber;
import fr.uv1.bettingServices.Person.Visitor;
import fr.uv1.utils.MyCalendar;
import bd.editBD;
import bd.selectBD;

public class BettingSoft {

	private static String url="jdbc:postgresql://localhost:54321/Test";
	private static String user="postgres";
	private static String password="postgres";
	
	private String managerPassword;
	private ArrayList<Subscriber> subscribers;
	private ArrayList<Competition> competitions;
	private Competition comp;
	private ArrayList<Bet> bets;
	private ArrayList<Competitor> competitors;
	private ArrayList<BetOnPodium> listpodiumbets;
	private ArrayList<BetOnWinner> listwinnerbets;
	private Subscriber subs;

	public void authenticateMngr(String managerPassword) throws AuthenticationException {

		if (managerPassword == null)
			throw new AuthenticationException("Enter a manager password");
		if (!this.managerPassword.equals(managerPassword))
			throw new AuthenticationException("Incorrect password ");		
	}
	
	public void settleWinner(java.lang.String competition, Competitor winner, java.lang.String managerPwd){
		// First we authenticate the manager
		this.authenticateMngr(managerPwd);
		
		//Check if all the parameters are valid
		//competition
		competition_object = getCompetitionByName(competition); // doit être impélmentée par le lot1
		if(competition_object == null)
			throw new ExistingCompetitionException("The competition named does not exist");
		//winner
		competitors = competition_object.listCompetitors();
		boolean exists = false; 
		 for(int i=0; i<competitors.size(); i++) {
		 	if (competitors.getName().equals(winner.getName()))
		 		Exists = True;
		 }
		 if(exists == false)
		 	throw new CompetitionException("Winner doesn't compete in the named competition ");
          		 
		//If the competition is closed we credit all the winning bettors.
		MyCalendar endCompetition = competition_object.getEndDate();
		if(!(endCompetition.isInThePast())){
			throw new CompetitionException("Competition still running");}
		long total = 0 ;	    
		int totalwinner = 0;
		// Getting the list of bettors on the competition
		listBettors = competition_object.consultBetsCompetition()
		  	  		 	
		for(int i=0; i<listBettors.size(); i++) {
			total = total + listBettors.get(i).getBettorBet();
			if (listBettors.get(i).getCompetitor1().getName().equals(winner.getName())){
				totalwinner = totalwinner + listBettors.get(i).getBettorBet();}		
		}
		
		// Credit the winners 
		if(totalwinner !=0){
		for(int i=0; i<bettor_winners.size(); i++){
			
			creditSubscriber(bettor_winners.get(i).getUsername(), (bettor_winners.get(i).getBettorBet()*total)/totalwinner, managerPwd);
		
	}
}
	     else {
	     	creditSubscriber(bettor_winners.get(i).getUsername(), (bettor_winners.get(i).getBettorBet(), managerPwd);
	     }
	
		
}	


	public void settlePodium(java.lang.String competition, Competitor winner, Competitor second, Competitor third, java.lang.String managerPwd){
			// First we authenticate the manager
			this.authenticateMngr(managerPwd);
			
			//Check if all the parameters are valid
			//competition
			competition_object = getCompetitionByName(competition); // doit être impélmentée par le lot1
			if(competition_object == null)
				throw new ExistingCompetitionException("The competition named does not exist");
			//winner
			competitors = competition_object.listCompetitors();
			boolean exists = false; 
			 for(int i=0; i<competitors.size(); i++) {
			 	int j=0;
                String competitors_name = competitors.getName();
			 	if (competitors_name.equals(winner.getName()) || competitors_name.equals(second.getName()) || competitors_name().equals(third.getName())){
			 		j++;
			 	}
			 	if(j==3) {	
			 		exists = true;
                }
			 }
			 if(exists == false){
			 	throw new CompetitionException("Winner doesn't compete in the named competition ");}
	          		 
			//If the competition is closed we credit all the winning bettors.
			MyCalendar endCompetition = competition_object.getEndDate();
			if(!(endCompetition.isInThePast())){
				throw new CompetitionException("Competition still running");}
			long total = 0 ;	    
			int totalwinner = 0;
			// Getting the list of bettors on the competition
			listBettors = competition_object.consultBetsCompetition();
			List<Subscriber> bettor_winners = new ArrayList<Subscriber>();			 	
			for(int i=0; i<listBettors.size(); i++) {
				total = total + listBettors.get(i).getBettorBet();
				if (
                    listBettors.get(i).getCompetitor1().getId().equals(winner.getId()) && // getId TODO
                    listBettors.get(i).getCompetitor2().getId().equals(second.getId()) && 
                    listBettors.get(i).getCompetitor3().getId().equals(third.getId()))
					bettor_winners.add(listBettors.get(i))//TODO Verify the function
					totalwinner = totalwinner + listBettors.get(i).getBettorBet();
			}

		}	
			// Credit the winners 
			if(totalwinner !=0){
			for(int i=0; i<bettor_winners.size(); i++){
				
				creditSubscriber(bettor_winners.get(i).getUsername(), (bettor_winners.get(i).getBettorBet()*total)/totalwinner, managerPwd);	
		}
	}
		     else {
		     	creditSubscriber(bettor_winners.get(i).getUsername(), (bettor_winners.get(i).getBettorBet(),String managerPwd);
		     }
}
    public static Competition getCompetitorByName(String Competitor_name){
    //pas encore modifié
		ResultSet result = selectBD.edit("postgres","postgres","jdbc:postgresql://localhost:54321/Test", "SELECT * FROM competition WHERE name LIKE"+Competiton_name+";");
		while(result.next()){
			Competition competition = new Competition(getString(2),getString(3));

			ResultSet result1 = selectBD.edit("postgres","postgres","jdbc:postgresql://localhost:54321/Test", "SELECT * FROM participation WHERE comp_id LIKE"+getString(1)+";");
		while(result1.next()){	
				ResultSet result2 = selectBD.edit("postgres","postgres","jdbc:postgresql://localhost:54321/Test", "SELECT * FROM individual WHERE team_id LIKE"++";");
					while(result2.next()){
						MyCalendar calendar = MyCalendar.fromString(getString(3))
						competition.addCompetitor(getString(1),getString(2),calendar)
		}
	}
return competition;
}
	public ArrayList<Subscriber> listSubscribers(String managerPwd){ 

		this.authenticateMngr(managerPwd);
		ArrayList<Subscriber> listSubscribers=new ArrayList<Subscriber>();
		if (DatabaseConnection.PERSISTENCE_ENABLED)
			try {
				listSubscribers=SubscriberDAO.listOfSubscribers();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		return listSubscribers;

	}
	public String unsubscribe(String managerPwd, String username){
		this.authenticateMngr(managerPwd);
		Subscriber subscriber=Subscriber.getSubscriberByUsername(username);
		int remainingTokens= subscriber.getTokens();
		if(subscriber==null){
			//Throw exception existing Subscriber
			System.out.println("username doesn't exist");
		if (DatabaseConnection.PERSISTENCE_ENABLED)
			try {
				SubscriberDAO.delete(subscriber);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		return remainingTokens;
	}
	public String susbscribe(String lastname, String firstname, String username, MyCalendar birthday, String managerPwd){
		this.authenticateMngr(managerPwd);
		Subscriber subscriber=Subscriber.getSubscriberByUsername(username);
		if(subscriber!=null){
			//Throw exception existing Subscriber
			System.out.println("username already exist");
		subscriber=new Subscriber(username, lastname, firstname, birthday);
		
		return subscriber.getPassword();
		
		}
	}
		public void creditSubscriber(String username, long numberTokens, String managerPwd){
			BettingSoft.authenticateMngr(managerPassword);
			Subscriber subscriber=Subscriber.getSubscriberByUsername(username);
			throws BadParametersException {
		// Check parameter
		if (numberTokens < 0)
			throw new BadParametersException("The number of tokens must be positive value (given : " + Tokens + ")");
		// Credit the subscriber
		tokens = (int) (subscriber.getTokens()+ numberTokens);
		// Update to the database
		if (DatabaseConnection.PERSISTENCE_ENABLED)
			try {
				SubscriberDAO.update(subscriber);
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
		public void debitSubscriber(String username, long numberTokens, String managerPwd){
			BettingSoft.authenticateMngr(managerPassword);
			Subscriber subscriber=Subscriber.getSubscriberByUsername(username);
			throws BadParametersException {
		// Check parameter
		if (numberTokens < 0)
			throw new BadParametersException("The number of tokens must be positive value (given : " + Tokens + ")");
		// Credit the subscriber
		tokens = (int) (subscriber.getTokens()- numberTokens);
		// Update to the database
		if (DatabaseConnection.PERSISTENCE_ENABLED)
			try {
				SubscriberDAO.update(subscriber);
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
		public void changeSubsPwd(String username, String newPwd, String currentPwd){
			Subscriber subscriber=Subscriber.getSubscriberByUsername(username);
			
					
		}



/*
	public ArrayList<Competitor> consultResultsCompetition(java.lang.String competition){
		ArrayList<Competitor> winners = new ArrayList<Competitor>
		competition_object = getCompetitionByName(competition); 
		ResultSet result = selectBD.edit("postgres","postgres","jdbc:postgresql://localhost:54321/Test", "SELECT comp_id FROM competition WHERE name LIKE"+Competiton+";");
		while(result.next()){
			int id_comp=Integer.parseInt(get.String(1))
		}
	ResultSet result = selectBD.edit("postgres","postgres","jdbc:postgresql://localhost:54321/Test", "SELECT * FROM Competition_Results WHERE id_comp LIKE"+String.valueOf(i)+";");
		while(result.next()){
			for (int i=0; i<3; i++){}
				
		}
	
	}*/

}
