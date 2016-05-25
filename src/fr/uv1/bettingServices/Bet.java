package fr.uv1.bettingServices;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;

import fr.uv1.competition.Competition;
import fr.uv1.competition.Competitor;
import fr.uv1.bettingServices.Subscriber;
import fr.uv1.bettingServices.BetDAO;
import fr.uv1.bettingServices.Exceptions.AuthenticationException;
import fr.uv1.bettingServices.Exceptions.BadParametersException;
import fr.uv1.bettingServices.Exceptions.CompetitionException;
import fr.uv1.bettingServices.Exceptions.ExistingCompetitionException;
import fr.uv1.bettingServices.Exceptions.SubscriberException;
import fr.uv1.utils.*;

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
		public Bet(int id,long bettorbet, Competitor first,Competitor second
				,Competitor third, Competition competition, Subscriber bettor, int ifPodium){
			this.id=id;
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
/*
	    public void betOnPodium(long numberTokens, Competition competition, Competitor first, Competitor second, Competitor third,
				Subscriber subscriber) throws SubscriberException, CompetitionException, BadParametersException {
			int first_Id,second_Id,third_Id;
			// First we should Check competitors
			if (!competition.checkCompetitor(first))
				throw new CompetitionException("Couldn't find the competitor " + first.getName()+"within the cometition "+competition.getName());
			if (!competition.checkCompetitor(second))
				throw new CompetitionException("Couldn't find the competitor " + second.getName()+"within the cometition "+competition.getName());
			if (!competition.checkCompetitor(third))
				throw new CompetitionException("Couldn't find the competitor " + third.getName()+"within the cometition "+competition.getName());
			//  Then we Create the bet
			this.first = first;
			this.second = second;
			this.third = third;
			// then connect to the DB and edit 
	        // first find competitors in the participation table
			if (DatabaseConnection.PERSISTENCE_ENABLED)
				try {
					BetDAO.persist(this);
				} catch (SQLException e) {
					e.printStackTrace();
				}
	    }



	    public void betOnWinner(int id, long numberTokens, Competition competition, Competitor winner,
				Subscriber subscriber) {
			// Instantiate the attributeshe winner
			if (!competition.checkCompetitor(first))
				throw new CompetitionException("Couldn't find the competitor " 
			+ first.getName()+"within the cometition "+competition.getName());
			//then create the bet 
			this.first = winner;
			this.ifPodium=0;
			//then connect to BD and edit 
			winner.getparticipation_Id;
			if (DatabaseConnection.PERSISTENCE_ENABLED)
				try {
					BetDAO.persist(this);
				} catch (SQLException e) {
					e.printStackTrace();
				}
		} */

		public void delete() throws CompetitionException {
			// check if the competition is closed
			if (competition.isClosed())
				throw new CompetitionException("Competition with name " + competition.getName() + " is closed");
			// Credit the subscriber
			try {
				subscriber.creditSubscriber(numberTokens); // TODO Parameters to be defined correctly
			} catch (BadParametersException exception) {
				exception.printStackTrace("Bad parameter input");
			}
			// Delete it from the database
				try {
					BetDAO.delete(this);
				} catch (SQLException exception) {
					exception.printStackTrace();
				}
		}
		// checks if the podium is valid
		public boolean validPodium(int firstId, int secondId,
				int thirdId) {
			return (this.first.getId().equals(firstId) && 
					this.second.getId().equals(secondId) && 
					this.third.getId().equals(thirdId));
		}


//for betting soft
		public static void deleteBetsCompetition(String competition, String username,
				String pwdSubs) throws AuthenticationException,
				CompetitionException, ExistingCompetitionException {
			// Look if a subscriber with this username exists
			Subscriber s = searchSubscriberByUsername(username);//Saad will do dis
			if (s == null)
				throw new AuthenticationException("Subscriber with username " + username + " does not exist");
			// Look if a competition with this name exists
			Competition c = searchCompetitionByName(competition);
			if (c == null)
				throw new ExistingCompetitionException("Competition with name " + competition + " does not exist");
			// Delete the bets
			s.deleteBetsCompetition(c, pwdSubs);//ths function will go to subs
		}
//betting soft
		
		public ArrayList<String> consultBetsCompetition(String competition)
				throws ExistingCompetitionException {
			// look if the name given match a competition in the db
			Competition c = searchCompetitionByName(competition);// another betting soft method
			if (c == null)
				throw new ExistingCompetitionException("Competition with name " + competition + " does not exist");
			// Consult the bets
			return c.consultBetsCompetition();
		}

	}

