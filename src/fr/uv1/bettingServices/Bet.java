package fr.uv1.bettingServices.Bet;




import java.io.Serializable;
import java.sql.SQLException;

import fr.uv1.bettingServices.Compet.Competition;
import fr.uv1.bettingServices.Compet.Competitor;
import fr.uv1.bettingServices.Person.Subscriber;
import fr.uv1.dao.BetDAO;
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
	    private Participation participation;
	    private int ifPodium;
	    
	//waiting competitor and competition
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
		    // then we need to add the bet into the competition and subscriber
		    competition.addBet(this);
			subscriber.addBet(this);
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
		
		public Participation getParticipation(){
			return this.participation;
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
		public void  setParticipation(Participation participation){
			this.participation=participation;
		}
		
		public void setifPodium(int ifPodium){
			this.ifPodium=ifPodium;
		}
		
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
			this.winner = winner;
			//then connect to BD and edit 
			winner.getparticipation_Id;
			if (DatabaseConnection.PERSISTENCE_ENABLED)
				try {
					BetDAO.persist(this);
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}

		public void delete() throws CompetitionException {
			// check if the competition is closed
			if (competition.isEnded())
				throw new CompetitionException("Competition with name " + competition.getName() + " is closed");
			// Credit the subscriber
			try {
				subscriber.creditSubscriber(numberTokens);
			} catch (BadParametersException e) {
				// Can not be raised because the number of tokens is always a positive value here
				e.printStackTrace();
			}
			// Delete it from the attributes of the competition and the subscriber instance
			Subscriber.deleteBet(this);
			competition.deleteBet(this);
			// Delete it from the database
			if (DatabaseConnection.PERSISTENCE_ENABLED)
				try {
					BetDAO.delete(this);
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}

		public boolean pronosticPodium(Competitor first, Competitor second,
				Competitor third) {
			return (this.first.equals(first) && this.second.equals(second) && this.third.equals(third));
		}

		public void deleteBetForSubsAndCompetition(){
			// Delete a bet for a subscriber and a competition.
			subscriber.deleteBet(this);
			competition.deleteBet(this);
			// Delete it from the database
			editBD.edit("postgres","postgres","jdbc:postgresql://localhost:54321/Test", 
				"DELETE FROM bet WHERE competition="+this.competition+",bettor_Id="+this.subscriber.getSubscriberId()+";")
		}
//for betting soft
		public void deleteBetsCompetition(String competition, String username,
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


	}

