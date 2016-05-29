package fr.uv1.bettingServices;

import java.sql.SQLException;
import java.util.*;
import fr.uv1.competition.*;


import fr.uv1.bettingServices.Exceptions.*;

import fr.uv1.utils.MyCalendar;


public class BettingSoft {

	private String managerPassword;
	private ArrayList<Competitor> competitors;
<<<<<<< HEAD
	
	public BettingSoft(String managerPassword){
		setmanagerPassword(managerPassword);
		competitors = new ArrayList<Competitor>();
	}
  
// -- LOT 3------------------------------------------------------
	public void setmanagerPassword(String managerPassword){
		this.managerPassword = managerPassword;
	} 
		
	public String getmanagerPassword(){
		return this.managerPassword;
	}
=======

	public BettingSoft(String managerPassword) {
		this.managerPassword = managerPassword;
		this.competitors = new ArrayList<Competitor>();
	}
  
// -- LOT 3------------------------------------------------------
		public void setmanagerPassword(String managerPassword){
			this.managerPassword = managerPassword;
		} 
			
		public String getmanagerPassword(){
			return this.managerPassword;
		}
		
		public void authenticateMngr(String managerPassword) throws AuthenticationException {
	
			if (managerPassword == null)
				throw new AuthenticationException("Enter a manager password");
			if (!this.managerPassword.equals(managerPassword))
				throw new AuthenticationException("Incorrect password ");		
		}
		
		public void settleWinner(String competition, 
								 Competitor winner, 
								 String managerPwd) throws AuthenticationException,
														   ExistingCompetitionException,
														   CompetitionException,
														   BadParametersException,
														   SQLException,
														   NotATeamException,
														   ExistingCompetitorException,
														   ExistingSubscriberException, 
														   SubscriberException   {	
				
		       // First we authenticate the manager
				this.authenticateMngr(managerPwd);
				
				//Check if all the parameters are valid
				// - competition
				Competition competition_object = getCompetitionByName(competition);
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
					List<Bet> listBets = consultBetsCompetitionB(competition); 
					List<Bet> winningBets = new ArrayList<Bet>();
					List<Bet> losingBets = new ArrayList<Bet>();
>>>>>>> e458eb618ff9dc90dc3eaded30b54bc455a0d45f
	
	public void authenticateMngr(String managerPassword) throws AuthenticationException {

		if (managerPassword == null)
			throw new AuthenticationException("Enter a manager password");
		if (!this.managerPassword.equals(managerPassword))
			throw new AuthenticationException("Incorrect password ");		
	}
	
	public void settleWinner(String competition, 
							 Competitor winner, 
							 String managerPwd) throws AuthenticationException,
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
			Competition competition_object = getCompetitionByName(competition);
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
				List<Bet> listBets = consultBetsCompetitionB(competition); 
				List<Bet> winningBets = new ArrayList<Bet>();
				List<Bet> losingBets = new ArrayList<Bet>();

<<<<<<< HEAD
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
=======
		public void settlePodium(String competition, 
								 Competitor winner, 
								 Competitor second, 
								 Competitor third, 
								 String managerPwd)	 throws AuthenticationException,
													 ExistingCompetitionException,
													 CompetitionException,
									 			     BadParametersException,
									 			     SQLException,
									 			     NotATeamException,
									 			     ExistingCompetitorException,
									 			     ExistingSubscriberException, 
									 			     SubscriberException {	
>>>>>>> e458eb618ff9dc90dc3eaded30b54bc455a0d45f
						
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
	

	public void settlePodium(String competition, 
							 Competitor winner, 
							 Competitor second, 
							 Competitor third, 
							 String managerPwd)	 throws AuthenticationException,
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
			Competition competition_object = getCompetitionByName(competition);
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
				List<Bet> listBets = consultBetsCompetitionB(competition);
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
	

	

	public static ArrayList<Competitor> consultResultsCompetition(
			String competition) throws AuthenticationException,
									   ExistingCompetitionException,
									   CompetitionException,
									   BadParametersException,
									   SQLException,
									   NotATeamException,
									   ExistingCompetitorException {
		Competition_ResultsDAO dao = new Competition_ResultsDAO();
		//Check if all the parameters are valid
		//competition
		Competition competition_object = getCompetitionByName(competition);
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
		
	
	public void deleteCompetition(String competition, 
								  String managerPwd) throws AuthenticationException,
															SQLException,
															BadParametersException, 
															ExistingCompetitorException, 
															ExistingCompetitionException, 
															NotATeamException {
		authenticateMngr(managerPwd);
		Competition competition_object = getCompetitionByName(competition);
		if(competition == null) {
			throw new ExistingCompetitionException("competition does not exist!");
			}
			CompetitionDAO.delete(competition_object);
		}
		
		
		public void cancelCompetition(String competition,
									  String managerPwd ) throws AuthenticationException,
															     SQLException,
															     BadParametersException, 
																 ExistingCompetitorException, 
																 ExistingCompetitionException, 
																 NotATeamException {
			deleteCompetition(competition, managerPwd);
			
		}
		
		
		public static Competition getCompetitionByName(String Competition_name)throws SQLException, 
																					  BadParametersException, 
																					  ExistingCompetitorException, 
																					  ExistingCompetitionException, 
																					  NotATeamException{

			Competition competition = CompetitionDAO.selectCompetitionByName(Competition_name);
			return competition;
}
		
		public Competitor createCompetitor(String name, String managerPwd) throws AuthenticationException,
					   															  BadParametersException, 
					   															  ExistingCompetitorException, 
					   															  ExistingCompetitionException, 
					   															  SQLException{
			
			authenticateMngr(managerPwd);
			Team newCompetitor = new Team(name,"");	
return newCompetitor;
}
		
// -- LOT 2--------------------------------------------------------

		
		
	public ArrayList<Subscriber> listSubscribers(String managerPwd) throws AuthenticationException, 
																		   BadParametersException { 
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
	
	public long unsubscribe(String managerPwd, String username)throws BadParametersException, 
																	  ExistingSubscriberException, 
																	  AuthenticationException, 
																	  SubscriberException {
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
	
	public String susbscribe(String lastname, 
							 String firstname, 
							 String username, MyCalendar birthday, 
							 String managerPwd) throws BadParametersException, 
													   ExistingSubscriberException, 
													   AuthenticationException, 
													   SubscriberException {
		this.authenticateMngr(managerPwd);
		Subscriber subscriber=Subscriber.getSubscriberByUsername(username);
		if(subscriber!=null){
			throw new ExistingSubscriberException("Username "+username+" already used");
		}
		subscriber = new Subscriber(username, lastname, firstname, birthday);
		
		return subscriber.getPassword();
		}
	
	public void creditSubscriber(String username, 
								 long numberTokens, 
								 String managerPwd) throws BadParametersException, 
														   AuthenticationException, 
														   ExistingSubscriberException, 
														   SubscriberException {
		this.authenticateMngr(managerPwd);
		SubscriberDAO dao = new SubscriberDAO();
		Subscriber subscriber = Subscriber.getSubscriberByUsername(username);
		if(subscriber==null){
			throw new ExistingSubscriberException("Subscriber doesn't exist");
		}
		if (numberTokens < 0){
			throw new BadParametersException("The number of tokens must be positive value (given : " + numberTokens + ")");
		}
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
	
	public void debitSubscriber(String username, 
			                    long numberTokens, 
			                    String managerPwd) throws AuthenticationException, 
														  BadParametersException, 
														  ExistingSubscriberException, 
														  SubscriberException {
		authenticateMngr(managerPwd);
		SubscriberDAO dao = new SubscriberDAO();
		Subscriber subscriber=Subscriber.getSubscriberByUsername(username);
		if(subscriber==null){
			throw new ExistingSubscriberException("Subscriber doesn't exist");
		}
		if (numberTokens < 0){
			throw new BadParametersException("The number of tokens must be positive value (given : " + numberTokens + ")");
		}
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
		
	public static void changeSubsPwd(String username, 
									 String newPwd, 
									 String currentPwd) throws BadParametersException, 
															   AuthenticationException, 
															   SubscriberException {
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
	public static void deleteBetsCompetition(String competition, 
											 String username,
											 String pwdSubs) throws AuthenticationException,
																	CompetitionException, 
																	ExistingCompetitionException, 
																	SQLException, 
																	ExistingCompetitorException, 
																	ExistingCompetitionException, 
																	NotATeamException, 
																	BadParametersException, 
																	BadParametersException, 
																	SubscriberException {
		// Look if a subscriber with this username exists
		Subscriber sub = Subscriber.getSubscriberByUsername(username);
		if (sub == null){
			throw new AuthenticationException("Subscriber with username " + username + " does not exist");
		}
			// Look if a competition with this name exists
		Competition comp = BettingSoft.getCompetitionByName(competition);
		if (comp == null){
			throw new ExistingCompetitionException("Competition with name " + competition + " does not exist");
		}
		// Delete the bets
		try {
			BetDAO.delete(comp,sub);
		} catch (SQLException exception) {
			exception.printStackTrace();
		}
}
		
	public static ArrayList<Bet> consultBetsCompetitionB(
			String competition) throws ExistingCompetitionException, 
									   SQLException, 
									   BadParametersException, 
									   ExistingCompetitorException, 
									   ExistingCompetitionException, 
									   NotATeamException {
		// look if the name given match a competition in the db
		Competition competition_object = BettingSoft.getCompetitionByName(competition);
		if (competition_object == null) {
			throw new ExistingCompetitionException("Competition with name " + competition + " does not exist");}
		// Consult the bets
		  ArrayList<Bet> bets = BetDAO.consult(competition_object);
		   return bets;
	}
	

	public static ArrayList<String> consultBetsCompetition(
			String competition)throws ExistingCompetitionException, 
									  SQLException, 
									  BadParametersException, 
									  ExistingCompetitorException, 
									  NotATeamException {
		// look if the name given match a competition in the db
		Competition competition_object = getCompetitionByName(competition);// another betting soft method
		if (competition_object == null) {
			throw new ExistingCompetitionException("Competition with name " + competition + " does not exist");
		}
		// Consult the bets
		  ArrayList<Bet> bets = BetDAO.consult(competition_object);
		  ArrayList<String> consultbets =new ArrayList<String>();
		  for (Bet b: bets){
			  String Pathkaml="bet_Id"+b.getId()+"in this competition"+competition+",the competitors"+b.getfirst()+","+b.getsecond()+","
		  +b.getthird()+"made by the Subscriber"+b.toString();
			  consultbets.add(Pathkaml);}
		  return consultbets;
	}
	
	public void betOnPodium(long tokens, 
							String competitionName,
							Competitor winner, 
							Competitor second, 
							Competitor third,
							String username, 
							String pwdSubs) throws ExistingSubscriberException, 
												   CompetitionException, 
												   SubscriberException, 
												   BadParametersException, 
												   AuthenticationException, 
												   SQLException, 
												   NotATeamException, 
												   ExistingCompetitorException, 
												   ExistingCompetitionException, 
												   BadParametersException {
			
		Subscriber subs = Subscriber.getSubscriberByUsername(username);
		Competition comp = BettingSoft.getCompetitionByName(competitionName);
		
		//Checking subscriber
		if(subs == null) 
		    throw new ExistingSubscriberException("The username given doesn't match any subscriber");

		if(!subs.getPassword().equals(pwdSubs))
			throw new AuthenticationException("The password is not correct");
		
			
		//Checking competition
		if(comp == null)
			throw new ExistingCompetitionException("This name doesn't match any competition");

		//Checking if competition is closed or not
		else if(comp.isClosed())
			throw new CompetitionException("Competition is finished");
		
		//Checking subscriberTokens higher than tokens
		long subscriberTokens = subs.getTokens();
		if(subscriberTokens < tokens)
			throw new SubscriberException("Not enough tokens");
		
		java.util.Collection<Competitor>  competitors;
		competitors = listCompetitors(competitionName);// BS
	
		//Check if the bettor is a competitor of the competition
		
		String subscriberFirstname = subs.getFirstName();
		String subscriberLastname = subs.getLastName();
		for(Competitor c : competitors) {
			if(c instanceof Individual) {
				Individual pc =(Individual) c;
				if(pc.getFirstName().equals(subscriberFirstname) && 
				   pc.getLastName().equals(subscriberLastname) ) {
					throw new CompetitionException("The bettor is a competitor");
				}
			}	
	    }
	
		//Check if first, second and third competitors exist
		if( !comp.checkCompetitor(winner)) 
			throw new CompetitionException("winner competitor doesn't exist for the person competition");
		if( !comp.checkCompetitor(second)) 
			throw new CompetitionException("second competitor doesn't exist for the person competition");
		if( !comp.checkCompetitor(third)) 
			throw new CompetitionException("third competitor doesn't exist for the person competition");
		//Creating the bet	
		Bet podiumBet = new Bet(tokens,winner,second,third, comp, subs,1);
				
			try {
				BetDAO.persist(podiumBet);
			} catch (SQLException exception) {
				exception.printStackTrace();
			}
		
	}	


	public void betOnWinner(long tokens, String competitionName,
			Competitor winner, String username, String pwdSubs) throws ExistingSubscriberException, BadParametersException, SQLException,
			BadParametersException, ExistingCompetitorException, ExistingCompetitionException, NotATeamException, ExistingCompetitionException, CompetitionException, SubscriberException{
	
		Subscriber subs = Subscriber.getSubscriberByUsername(username); 
		Competition comp = BettingSoft.getCompetitionByName(competitionName);
		
		//check if the subs and competition exists
		if(subs == null || !subs.getPassword().equals(pwdSubs))
		    throw new ExistingSubscriberException("Authentification failed");
		
		
		if(comp == null)
			throw new ExistingCompetitionException("Couldn't find competition");
		
		//Check if the competition date has closed
		else if(comp.isClosed())
			throw new CompetitionException("Competition is finished");
		
		//Check if the bet amount is inferior to the number of the subscriber's tokens
		long subsTokens = subs.getTokens();
		if(subsTokens < tokens)
			throw new SubscriberException("Not enough tokens");	
			
		//Check if winner competitor exists
		if( !comp.checkCompetitor(winner)) 
			throw new CompetitionException("winner competitor doesn't exist for the person competition");	
						
		//Create the winner bet and add it to the list of winner bets 
		Bet winnerBet = new Bet(tokens,winner,winner,winner, comp, subs,0);
			try {
				BetDAO.persist(winnerBet);
			} catch (SQLException exception) {
				exception.printStackTrace();
			}
		
	}
	
	public void addCompetition(java.lang.String competition,
			   MyCalendar closingDate,
			   List<Competitor> competitors,
			   java.lang.String managerPwd) throws AuthenticationException,
							                       ExistingCompetitionException,
							                       CompetitionException,
							                       BadParametersException,
							                       SQLException {
		Competition competition_object = new Competition( competition , "", competitors , closingDate);
		CompetitionDAO.persist(competition_object);
		
		}
	public ArrayList<String> infosSubscriber(String username,String pwdSubs) throws BadParametersException, 
																					AuthenticationException, 
																					ExistingCompetitorException, 
																					ExistingCompetitionException {
		Subscriber.authenticateSubscriber(username, pwdSubs);
		Subscriber subscriber=Subscriber.getSubscriberByUsername(username);
		ArrayList<String> result = new ArrayList<String>();
		long tokensBetted=0;
		ArrayList<Bet> bets=new ArrayList<Bet>();
		result.add("last name : "+subscriber.getLastName());
		result.add("first name : "+subscriber.getFirstName());
		result.add("birthday : "+subscriber.getBirthday().toString2());
		result.add("username : "+subscriber.getUserName());
		result.add("Tokens : "+Long.toString(subscriber.getTokens()));
		try {
			bets=SubscriberDAO.subscriberBets(subscriber);
		} catch (SQLException exception) {
			exception.printStackTrace();
		}
		
		
		for(Bet b : bets){
			tokensBetted=tokensBetted+b.getBettorBet();
			result.add(b.toString());	
		}
		result.add("Total tokens Betted : "+Long.toString(tokensBetted));
		return result;
	}
	
	
	
//--LOT 1------------------------------------------------------------------------
	
	/** Returns the list of competitors in this competition
	 * 
	 * @return Returns the list of the competitors of this competition
	 */
	public static java.util.Collection<Competitor> listCompetitors(String competition)throws ExistingCompetitorException, ExistingCompetitionException, CompetitionException, NotATeamException, SQLException, BadParametersException { // BS
		return getCompetitionByName(competition).getCompetitors();
	}
	
	
	public static List<Competition> listCompetitions() throws SQLException {
		return fr.uv1.competition.CompetitionDAO.listCompetitions();
	}
	
	/** Adds a competitor to this competition
	 * 
	 * @param competitor
	 * @throws ExistingCompetitorException
	 */
	
	public void addCompetitor(Competitor competitor) throws ExistingCompetitorException {
		if (competitors.contains(competitor)){ throw new ExistingCompetitorException();	}
		else { competitors.add(competitor); }
	}
	
	/** Deletes a competitor from this competition
	 * 
	 * @param competitor
	 * @throws ExistingCompetitorException
	 */
	public void deleteCompetitor(Competitor competitor) throws ExistingCompetitorException {
		if (! competitors.contains(competitor)){ throw new ExistingCompetitorException(); }
		else { competitors.remove(competitor); }
	}
	
}
