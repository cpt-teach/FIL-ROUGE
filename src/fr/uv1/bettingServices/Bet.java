package fr.uv1.bettingServices;


import java.sql.SQLException;
import java.util.ArrayList;

import fr.uv1.competition.*;
import fr.uv1.bettingServices.Subscriber;
import fr.uv1.bettingServices.BetDAO;
import fr.uv1.bettingServices.Exceptions.*;



	public class Bet {
		protected int id;
		private long numberTokens; 
	    private Competitor first;
	    private Competitor second; 
	    private Competitor third;
	    private Competition competition;
	    private Subscriber bettor;
	    private int ifPodium;
	    
	    // Constructor
		public Bet(long bettorbet, Competitor first,Competitor second
				,Competitor third, Competition competition, Subscriber bettor, int ifPodium){
			this.numberTokens=bettorbet;
		    this.first=first;
		    this.second=second;
		    this.third=third;
		    this.competition=competition;
		    this.bettor=bettor;
		    this.ifPodium=ifPodium;
		}
	//getter	
		
		
		public int getId(){
			return this.id;
		}
		public long getBettorBet(){
			return this.numberTokens;
		}
		public Competitor getfirst(){
			return this.first;
		}
		public Competitor getsecond(){
			return this.second;
		}
		public Competitor getthird(){
			return this.third;
		}
		public Competition getCompetition(){
			return this.competition;
		}
		public Subscriber getBettor(){
			return this.bettor;
		}
				
		public int getifPodium(){
			return this.ifPodium;
		}
	//Setter
		public void setId(int id){
			this.id=id;
		}
		public void setBettorbet(long tokens){
			this.numberTokens=tokens;
		}
		public void setfirst(Competitor first){
			this.first=first;
		}
		public void setsecond(Competitor second){
			this.first=second;
		}
		public void setthird(Competitor third){
			this.first=third;
		}
		public void setSubscriber(Subscriber bettor){
			this.bettor=bettor;
		}
		
		public void setifPodium(int ifPodium){
			this.ifPodium=ifPodium;
		}
		
		public void betOnPodium(long tokens, String competitionName,
				Competitor winner, Competitor second, Competitor third,
				String username, String pwdSubs) throws ExistingSubscriberException, CompetitionException, SubscriberException, BadParametersException, AuthenticationException, SQLException, NotATeamException, ExistingCompetitorException, ExistingCompetitionException, BadParametersException{
				
			Subscriber subs = Subscriber.getSubscriberByUsername(username);
			Competition comp = Competition.getCompetitionByName(competitionName);
			
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
			competitors = Competition.listCompetitors(competitionName);// BS
		
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
		Competition comp = Competition.getCompetitionByName(competitionName);
		
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
	    		
	    		
		// checks if the podium is valid
		public boolean validPodium(int firstId, int secondId,
				int thirdId) throws SQLException {
			return (this.first.getId()==(firstId) && 
					this.second.getId()==(secondId) && 
					this.third.getId()==(thirdId));
		}

//for betting soft
		public static void deleteBetsCompetition(String competition, String username,
				String pwdSubs) throws AuthenticationException,
				CompetitionException, ExistingCompetitionException, SQLException, ExistingCompetitorException, ExistingCompetitionException, NotATeamException, BadParametersException, BadParametersException {
			// Look if a subscriber with this username exists
			Subscriber sub = Subscriber.getSubscriberByUsername(username);
			if (sub == null)
				throw new AuthenticationException("Subscriber with username " + username + " does not exist");
			// Look if a competition with this name exists
			Competition comp = Competition.getCompetitionByName(competition);
			if (comp == null)
				throw new ExistingCompetitionException("Competition with name " + competition + " does not exist");
			// Delete the bets
			try {
				BetDAO.delete(comp,sub);
			} catch (SQLException exception) {
				exception.printStackTrace();
			}
	}
		
//betting soft
		
	public static ArrayList<Bet> consultBetsCompetition(String competition)
			throws ExistingCompetitionException, SQLException, BadParametersException, ExistingCompetitorException, ExistingCompetitionException, NotATeamException {
		// look if the name given match a competition in the db
		Competition competition_object = Competition.getCompetitionByName(competition);
		if (competition == null) {
			throw new ExistingCompetitionException("Competition with name " + competition + " does not exist");}
		// Consult the bets
		  ArrayList<Bet> bets = BetDAO.consult(competition_object);
		   return bets;
	}
	}

