package fr.uv1.bettingServices;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.*;
import fr.uv1.competition.*;


import fr.uv1.bettingServices.Exceptions.*;

import fr.uv1.utils.MyCalendar;


public class BettingSoft {

	private String managerPassword;
	private ArrayList<Subscriber> subscribers;
	private ArrayList<Competition> competitions;
	private Competition comp;
	private ArrayList<Bet> bets;
	private ArrayList<Competitor> competitors;
	private Subscriber subs;
  
// -- LOT 3--
	
	
	public void authenticateMngr(String managerPassword) throws AuthenticationException {

		if (managerPassword == null)
			throw new AuthenticationException("Enter a manager password");
		if (!this.managerPassword.equals(managerPassword))
			throw new AuthenticationException("Incorrect password ");		
	}
	
	public void settleWinner(java.lang.String competition, Competitor winner, java.lang.String managerPwd)

	        throws AuthenticationException,
	        	   ExistingCompetitionException,
	        	   CompetitionException,
				   BadParametersException,
				   SQLException,
				   NotATeamException,
				   ExistingCompetitorException,
				   ExistingSubscriberException   {	
			
	       // First we authenticate the manager
			this.authenticateMngr(managerPwd);
			
			//Check if all the parameters are valid
			// - competition
			Competition competition_object = Competition.getCompetitionByName(competition);
			if(competition_object == null)
				throw new ExistingCompetitionException("The competition named does not exist");
			// - winner
			
			 if(!competition_object.checkCompetitor(winner))
			 	throw new CompetitionException("Winner doesn't compete in the named competition ");
	          		 
			//If the competition is closed we credit all the winning bettors.
			if(!competition_object.isClosed()) {
				throw new CompetitionException("Competition still running");}
			else {
				long total = 0 ;	    
				int totalwinner = 0;
				// Getting the list of bettors on the competition
				List<Bet> listBets = Bet.consultBetsCompetition(competition); //BS to discuss TODO
				List<Bet> winningBets = new ArrayList<Bet>();
				List<Bet> losingBets = new ArrayList<Bet>();

				for(int i=0; i<listBets.size(); i++) {
					total = total + listBets.get(i).getBettorBet();
					if (listBets.get(i).getfirst().getId() == (winner.getId())) {
						totalwinner = totalwinner + (int)listBets.get(i).getBettorBet();
						winningBets.add(listBets.get(i));
					}	
					else {
						losingBets.add(listBets.get(i));
					}
				}
				if(totalwinner !=0){
				//Debit the losers
					for(int i=0; i<losingBets.size(); i++) {
						
						debitSubscriber(losingBets.get(i).getBettor().getUserName(), 
												   losingBets.get(i).getBettorBet(), 
												    managerPwd); 
					}
				// Credit the winners 
					for(int i=0; i<winningBets.size(); i++) {
						
						creditSubscriber(winningBets.get(i).getBettor().getUserName(), 
												   (winningBets.get(i).getBettorBet()*total)/totalwinner, 
												    managerPwd); 
					}
				}
				deleteCompetition(competition, managerPwd);

			}	
		}

		public void settlePodium(String competition, Competitor winner, Competitor second, Competitor third, String managerPwd)
	        		throws AuthenticationException,
	         	   ExistingCompetitionException,
	         	   CompetitionException,
	 			   BadParametersException,
	 			   SQLException,
	 			   NotATeamException,
	 			   ExistingCompetitorException,
				   ExistingSubscriberException {	
			
				
				 // First we authenticate the manager
				this.authenticateMngr(managerPwd);
				
				//Check if all the parameters are valid
				// - competition
				Competition competition_object = Competition.getCompetitionByName(competition);
				if(competition_object == null)
					throw new ExistingCompetitionException("The competition named does not exist");
				// - winner second third
				
				 if(!competition_object.checkCompetitor(winner))
				 		throw new CompetitionException("First doesn't compete in the named competition ");
				 if(!competition_object.checkCompetitor(winner))
					 	throw new CompetitionException("Second doesn't compete in the named competition ");
				 if(!competition_object.checkCompetitor(winner))
					 	throw new CompetitionException("Third doesn't compete in the named competition ");
		          		 
				//If the competition is closed we credit all the winning bettors.
				if(!competition_object.isClosed()) {
					throw new CompetitionException("Competition still running");}
				else {
					long total = 0 ;	    
					int totalwinner = 0;
					// Getting the list of bettors on the competition
					List<Bet> listBets = Bet.consultBetsCompetition(competition); //BS to discuss TODO
					List<Bet> winningBets = new ArrayList<Bet>();
					List<Bet> losingBets = new ArrayList<Bet>();

					for(int i=0; i<listBets.size(); i++) {
						total = total + listBets.get(i).getBettorBet();
						if (listBets.get(i).getfirst().getId() == (winner.getId()) &&
							listBets.get(i).getsecond().getId() == (second.getId()) &&
							listBets.get(i).getthird().getId() == (third.getId())) {
							totalwinner = totalwinner + (int)listBets.get(i).getBettorBet();
							winningBets.add(listBets.get(i));
						}	
						else {
							losingBets.add(listBets.get(i));
						}
					}
					if(totalwinner !=0){
					//Debit the losers
						for(int i=0; i<losingBets.size(); i++) {
							
							debitSubscriber(losingBets.get(i).getBettor().getUserName(), 
													   losingBets.get(i).getBettorBet(), 
													    managerPwd); // BS
						}
					// Credit the winners 
						for(int i=0; i<winningBets.size(); i++) {
							
							creditSubscriber(winningBets.get(i).getBettor().getUserName(), 
													   (winningBets.get(i).getBettorBet()*total)/totalwinner, 
													    managerPwd); // BS
						}
					}
					deleteCompetition(competition, managerPwd);

				}
		}
		

		

		public static java.util.ArrayList<Competitor> consultResultsCompetition(java.lang.String competition)
	            throws AuthenticationException,
	     	   ExistingCompetitionException,
	     	   CompetitionException,
				   BadParametersException,
				   SQLException,
				   NotATeamException,
				   ExistingCompetitorException{
			Competition_ResultsDAO dao = new Competition_ResultsDAO();
			//Check if all the parameters are valid
			//competition
			Competition competition_object = Competition.getCompetitionByName(competition);
			if(competition_object == null) {
				throw new ExistingCompetitionException("The competition named does not exist"); }
			int[] PodiumId = dao.ResultCompetition(competition_object);
			int[] PodiumIdC = dao.ParticipationToCompetitor(PodiumId);
			ArrayList<Competitor> list = new ArrayList<Competitor>();
			list.add(dao.getCompetitorById(PodiumIdC[0]));
			list.add(dao.getCompetitorById(PodiumIdC[1]));
			list.add(dao.getCompetitorById(PodiumIdC[2]));
			
			return list;
			}
			
		
		public void deleteCompetition(java.lang.String competition, java.lang.String managerPwd) {
			
		}
			

// -- LOT 2--	
		
		
	public ArrayList<Subscriber> listSubscribers(String managerPwd) throws AuthenticationException, BadParametersException{ 
		SubscriberDAO dao = new SubscriberDAO();
		this.authenticateMngr(managerPwd);
		ArrayList<Subscriber> listSubscribers = new ArrayList<Subscriber>();
			try {
				listSubscribers=dao.listOfSubscribers();
			} catch (SQLException exception) {
				exception.printStackTrace();
			}

		return listSubscribers;

	}
	public long unsubscribe(String managerPwd, String username)throws BadParametersException, ExistingSubscriberException, AuthenticationException{
		this.authenticateMngr(managerPwd);
		Subscriber subscriber=Subscriber.getSubscriberByUsername(username);
		long remainingTokens= subscriber.getTokens();
			try {
				SubscriberDAO.delete(subscriber);
			} catch (SQLException exception) {
				exception.printStackTrace();
			}
		return remainingTokens;
				}
	
	public String susbscribe(String lastname, String firstname, String username, MyCalendar birthday, String managerPwd) throws BadParametersException, ExistingSubscriberException, AuthenticationException{
		this.authenticateMngr(managerPwd);
		Subscriber subscriber=Subscriber.getSubscriberByUsername(username);
		if(subscriber!=null){
			throw new ExistingSubscriberException("Username "+username+" already used");
		}
		subscriber = new Subscriber(username, lastname, firstname, birthday);
		
		return subscriber.getPassword();
	}
		public void creditSubscriber(String username, long numberTokens, String managerPwd) throws BadParametersException, AuthenticationException, ExistingSubscriberException{
			this.authenticateMngr(managerPwd);
			SubscriberDAO dao = new SubscriberDAO();
			Subscriber subscriber = Subscriber.getSubscriberByUsername(username);
			if(subscriber==null){
				throw new ExistingSubscriberException("Subscriber doesn't exist");
			}
			if (numberTokens < 0)
				throw new BadParametersException("The number of tokens must be positive value (given : " + numberTokens + ")");
		// Credit the subscriber
		long existingTokens=subscriber.getTokens();
		subscriber.setTokens(existingTokens+ numberTokens);
		// Update to the database
			try {
				dao.update(subscriber);
			} catch (SQLException exception) {
				exception.printStackTrace();
			}
	}
		public void debitSubscriber(String username, long numberTokens, String managerPwd) throws AuthenticationException, BadParametersException, ExistingSubscriberException{
			authenticateMngr(managerPwd);
			SubscriberDAO dao = new SubscriberDAO();
			Subscriber subscriber=Subscriber.getSubscriberByUsername(username);
			if(subscriber==null){
				throw new ExistingSubscriberException("Subscriber doesn't exist");
			}
			if (numberTokens < 0)
				throw new BadParametersException("The number of tokens must be positive value (given : " + numberTokens + ")");
		// Credit the subscriber
		long existingTokens=subscriber.getTokens();
		subscriber.setTokens(existingTokens - numberTokens);
		// Update to the database
			try {
				dao.update(subscriber);
			} catch (SQLException exception) {
				exception.printStackTrace();
			}
	}
		public void changeSubsPwd(String username, String newPwd, String currentPwd) throws BadParametersException, AuthenticationException{
			SubscriberDAO dao = new SubscriberDAO();
			Subscriber subscriber=Subscriber.getSubscriberByUsername(username);
			Subscriber.authenticateSubscriber(username,currentPwd);
			subscriber.setPassword(newPwd);
			try {
				dao.update(subscriber);
			} catch (SQLException exception) {
				exception.printStackTrace();
			}		
		}

}
