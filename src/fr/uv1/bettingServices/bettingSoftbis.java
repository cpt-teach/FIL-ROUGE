package fr.uv1.bettingServices;
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
import fr.uv1.bettingServices.*;
import fr.uv1.bettingServices.Exceptions.*;
import fr.uv1.competition.*;
import fr.uv1.utils.MyCalendar;
import fr.uv1.bd.*;

public class bettingSoftbis {

	private static String url="jdbc:postgresql://localhost:5433/tests";
	private static String user="postgres";
	private static String password="postgres";
	
	private String managerPassword;
	private ArrayList<Subscriber> subscribers;
	private ArrayList<Competition> competitions;
	private Competition comp;
	private ArrayList<Bet> bets;
	private ArrayList<Competitor> competitors;
	private ArrayList<Bet> listBets;
	private Subscriber subs;

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
			   ExistingCompetitorException{	
		
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
					
					Subscriber.debitSubscriber(losingBets.get(i).getBettor().getUserName(), 
											   losingBets.get(i).getBettorBet(), 
											    managerPwd); // BS
				}
			// Credit the winners 
				for(int i=0; i<winningBets.size(); i++) {
					
					Subscriber.creditSubscriber(winningBets.get(i).getBettor().getUserName(), 
											   (winningBets.get(i).getBettorBet()*total)/totalwinner, 
											    managerPwd); // BS
				}
			}

		}	

	}

	public void settlePodium(String competition, Competitor winner, Competitor second, Competitor third, String managerPwd)
        		throws AuthenticationException,
         	   ExistingCompetitionException,
         	   CompetitionException,
 			   BadParametersException,
 			   SQLException,
 			   NotATeamException,
 			   ExistingCompetitorException {	
		
			
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
						
						Subscriber.debitSubscriber(losingBets.get(i).getBettor().getUserName(), 
												   losingBets.get(i).getBettorBet(), 
												    managerPwd); // BS
					}
				// Credit the winners 
					for(int i=0; i<winningBets.size(); i++) {
						
						Subscriber.creditSubscriber(winningBets.get(i).getBettor().getUserName(), 
												   (winningBets.get(i).getBettorBet()*total)/totalwinner, 
												    managerPwd); // BS
					}
				}

			}
			//delete comp
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
		
		

}