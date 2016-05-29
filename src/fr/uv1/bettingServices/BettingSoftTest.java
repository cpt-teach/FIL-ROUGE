
package fr.uv1.bettingServices;

import org.junit.Assert.*;
import fr.uv1.bettingServices.Exceptions.*;
import fr.uv1.utils.MyCalendar;
import fr.uv1.competition.*;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BettingSoftTest {
	
	private BettingSoft sut;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		sut = new BettingSoft("mdp");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test(expected = AuthenticationException.class)
	public void testAuthenticateMngrNull() throws AuthenticationException {
		this.sut.authenticateMngr(null);	
	}
	
	@Test(expected = AuthenticationException.class)
	public void testAuthenticateMngrError() throws AuthenticationException {
		this.sut.authenticateMngr("error");	
	}
	
	@Test(expected = ExistingCompetitionException.class)
	public void testExistingCompetitionNull() throws AuthenticationException,
	   											 ExistingCompetitionException,
	   											 CompetitionException,
	   											 BadParametersException,
	   											 SQLException,
	   											 NotATeamException,
	   											 ExistingCompetitorException,
	   											 ExistingSubscriberException, 
	   											 SubscriberException   {	
		//Competition competition = new Competition("comp#1","sport", MyCalendar.fromString("2017,01,01"));
		Competitor Winner = new Team("","");
		sut.settleWinner("comp#n",Winner,"mdp");
	}
	
	@Test(expected = CompetitionException.class)
	public void testExistingCompetitionWinnerNull() throws AuthenticationException,
				 									 ExistingCompetitionException,
				 									 CompetitionException,
				 									 BadParametersException,
				 									 SQLException,
				 									 NotATeamException,
				 									 ExistingCompetitorException,
				 									 ExistingSubscriberException, 
				 									 SubscriberException   {	
		Competition competition = new Competition("comp#1","sport", MyCalendar.fromString("2017,01,01"));
		Competitor Winner = new Team("1","");
		Participation Winnerp = new Participation(1,Winner.getId(),competition.getId(),false);
		Competitor Loser = new Team("2","");
		//Participation Loserp = new Participation(2,Loser.getId(),competition.getId(),false);
		Competition_Results compRes = new Competition_Results(Winnerp,Winnerp,Winnerp,competition.getId());
		Competition_ResultsDAO.persist(compRes);
		sut.settleWinner("comp#1",Loser,"mdp");
	}
	
	@Test(expected = CompetitionException.class)
	public void testExistingCompetitionClosed() throws AuthenticationException,
				 									 ExistingCompetitionException,
				 									 CompetitionException,
				 									 BadParametersException,
				 									 SQLException,
				 									 NotATeamException,
				 									 ExistingCompetitorException,
				 									 ExistingSubscriberException, 
				 									 SubscriberException   {	
		Competition competition = new Competition("comp#1","sport", MyCalendar.fromString("2015,01,01"));
		Competitor Winner = new Team("1","");
		Participation Winnerp = new Participation(1,Winner.getId(),competition.getId(),false);
		Competition_Results compRes = new Competition_Results(Winnerp,Winnerp,Winnerp,competition.getId());
		Competition_ResultsDAO.persist(compRes);
		sut.settleWinner("comp#1",Winner,"mdp");
	}
	
	@Test
	public void testSettleWinnerCreditcase1() throws AuthenticationException,
				 									 ExistingCompetitionException,
				 									 CompetitionException,
				 									 BadParametersException,
				 									 SQLException,
				 									 NotATeamException,
				 									 ExistingCompetitorException,
				 									 ExistingSubscriberException, 
				 									 SubscriberException   {	
		Competition competition = new Competition("comp#1","sport", MyCalendar.fromString("2017,01,01"));
		Competitor Winner = new Team("1","");
		Participation Winnerp = new Participation(1,Winner.getId(),competition.getId(),false);
		Competitor Loser = new Team("2","");
		//Participation Loserp = new Participation(2,Loser.getId(),competition.getId(),false);
		Competition_Results compRes = new Competition_Results(Winnerp,Winnerp,Winnerp,competition.getId());
		Competition_ResultsDAO.persist(compRes);
		List<Bet> listBets = new ArrayList<Bet>();
		List<Subscriber> listSubs = new ArrayList<Subscriber>();
		Subscriber subscriber1 = new Subscriber(1,"Champignon","Mdp","mellas","saad belkhadir",15, MyCalendar.fromString("1994,04,23"));
		
		listSubs.add(subscriber1);
		Subscriber subscriber2 = new Subscriber(2,"Champ","Mdp","el mahfoudi","saad",15, MyCalendar.fromString("1994,04,23"));
		listSubs.add(subscriber2);
		Subscriber subscriber3 = new Subscriber(3,"Champignone","Mdp","nasser","issam",15, MyCalendar.fromString("1994-04-23"));
		listSubs.add(subscriber3);
		for(int i=0; i<3; i++ ){
			if(i!=1){
				Bet bet = new Bet(6,Winner,Winner,Winner,competition,listSubs.get(i),0);
				listBets.add(bet);
			}
			else{
				Bet bet = new Bet(6,Loser,Loser,Loser,competition,listSubs.get(i),0);
				listBets.add(bet);
			}
			
		}
		sut.settleWinner("comp#1",Winner,"mdp");
		assertTrue(subscriber1.getTokens()==18);
		assertTrue(subscriber2.getTokens()==9);
		assertTrue(subscriber3.getTokens()==18);
		assertNull(BettingSoft.getCompetitionByName("comp#1"));
	}
	
	@Test
	public void testSettleWinnerCreditcase2() throws AuthenticationException,
				 									 ExistingCompetitionException,
				 									 CompetitionException,
				 									 BadParametersException,
				 									 SQLException,
				 									 NotATeamException,
				 									 ExistingCompetitorException,
				 									 ExistingSubscriberException, 
				 									 SubscriberException   {	
		Competition competition = new Competition("comp#1","sport", MyCalendar.fromString("2017,01,01"));
		Competitor Winner = new Team("1","");
		Participation Winnerp = new Participation(1,Winner.getId(),competition.getId(),false);
		Competitor Loser = new Team("2","");
		//Participation Loserp = new Participation(2,Loser.getId(),competition.getId(),false);
		Competition_Results compRes = new Competition_Results(Winnerp,Winnerp,Winnerp,competition.getId());
		Competition_ResultsDAO.persist(compRes);
		List<Bet> listBets = new ArrayList<Bet>();
		List<Subscriber> listSubs = new ArrayList<Subscriber>();
		Subscriber subscriber1 = new Subscriber(1,"Champignon","Mdp","mellas","saad belkhadir",15, MyCalendar.fromString("1994,04,23"));
		
		listSubs.add(subscriber1);
		Subscriber subscriber2 = new Subscriber(2,"Champ","Mdp","el mahfoudi","saad",15, MyCalendar.fromString("1994,04,23"));
		listSubs.add(subscriber2);
		Subscriber subscriber3 = new Subscriber(3,"Champignone","Mdp","nasser","issam",15, MyCalendar.fromString("1994-04-23"));
		listSubs.add(subscriber3);
		for(int i=0; i<3; i++ ){
			
			Bet bet = new Bet(6,Loser,Loser,Loser,competition,listSubs.get(i),0);
			listBets.add(bet);
		
			
		}
		sut.settleWinner("comp#1",Winner,"mdp");
		assertTrue(subscriber1.getTokens()==15);
		assertTrue(subscriber2.getTokens()==15);
		assertTrue(subscriber3.getTokens()==15);
		assertNull(BettingSoft.getCompetitionByName("comp#1"));
	}
	
	@Test
	public void testSettlePodiumCreditcase1() throws AuthenticationException,
				 									 ExistingCompetitionException,
				 									 CompetitionException,
				 									 BadParametersException,
				 									 SQLException,
				 									 NotATeamException,
				 									 ExistingCompetitorException,
				 									 ExistingSubscriberException, 
				 									 SubscriberException   {	
		Competition competition = new Competition("comp#1","sport", MyCalendar.fromString("2017,01,01"));
		Competitor Winner = new Team("1","");
		Participation Winnerp = new Participation(1,Winner.getId(),competition.getId(),false);
		Competitor Second = new Team("2","");
		Participation Secondp = new Participation(2,Second.getId(),competition.getId(),false);
		Competitor Third = new Team("3","");
		Participation Thirdp = new Participation(3,Third.getId(),competition.getId(),false);
		Competitor Loser = new Team("4","");
		//Participation Loserp = new Participation(2,Loser.getId(),competition.getId(),false);
		Competition_Results compRes = new Competition_Results(Winnerp,Secondp,Thirdp,competition.getId());
		Competition_ResultsDAO.persist(compRes);
		List<Bet> listBets = new ArrayList<Bet>();
		List<Subscriber> listSubs = new ArrayList<Subscriber>();
		Subscriber subscriber1 = new Subscriber(1,"Champignon","Mdp","mellas","saad belkhadir",15, MyCalendar.fromString("1994,04,23"));
		
		listSubs.add(subscriber1);
		Subscriber subscriber2 = new Subscriber(2,"Champ","Mdp","el mahfoudi","saad",15, MyCalendar.fromString("1994,04,23"));
		listSubs.add(subscriber2);
		Subscriber subscriber3 = new Subscriber(3,"Champignone","Mdp","nasser","issam",15, MyCalendar.fromString("1994-04-23"));
		listSubs.add(subscriber3);
		for(int i=0; i<3; i++ ){
			if(i!=1){
				Bet bet = new Bet(6,Winner,Second,Third,competition,listSubs.get(i),0);
				listBets.add(bet);
			}
			else{
				Bet bet = new Bet(6,Second,Winner,Third,competition,listSubs.get(i),0);
				listBets.add(bet);
			}
			
		}
		sut.settleWinner("comp#1",Winner,"mdp");
		assertTrue(subscriber1.getTokens()==18);
		assertTrue(subscriber2.getTokens()==9);
		assertTrue(subscriber3.getTokens()==18);
		assertNull(BettingSoft.getCompetitionByName("comp#1"));
	}
	
	@Test
	public void testSettlePodiumCreditcase2() throws AuthenticationException,
				 									 ExistingCompetitionException,
				 									 CompetitionException,
				 									 BadParametersException,
				 									 SQLException,
				 									 NotATeamException,
				 									 ExistingCompetitorException,
				 									 ExistingSubscriberException, 
				 									 SubscriberException   {	
		Competition competition = new Competition("comp#1","sport", MyCalendar.fromString("2017,01,01"));
		Competitor Winner = new Team("1","");
		Participation Winnerp = new Participation(1,Winner.getId(),competition.getId(),false);
		Competitor Second = new Team("2","");
		Participation Secondp = new Participation(2,Second.getId(),competition.getId(),false);
		Competitor Third = new Team("3","");
		Participation Thirdp = new Participation(3,Third.getId(),competition.getId(),false);
		Competitor Loser = new Team("4","");
		//Participation Loserp = new Participation(2,Loser.getId(),competition.getId(),false);
		Competition_Results compRes = new Competition_Results(Winnerp,Secondp,Thirdp,competition.getId());
		Competition_ResultsDAO.persist(compRes);
		List<Bet> listBets = new ArrayList<Bet>();
		List<Subscriber> listSubs = new ArrayList<Subscriber>();
		Subscriber subscriber1 = new Subscriber(1,"Champignon","Mdp","mellas","saad belkhadir",15, MyCalendar.fromString("1994,04,23"));
		
		listSubs.add(subscriber1);
		Subscriber subscriber2 = new Subscriber(2,"Champ","Mdp","el mahfoudi","saad",15, MyCalendar.fromString("1994,04,23"));
		listSubs.add(subscriber2);
		Subscriber subscriber3 = new Subscriber(3,"Champignone","Mdp","nasser","issam",15, MyCalendar.fromString("1994-04-23"));
		listSubs.add(subscriber3);
		for(int i=0; i<3; i++ ){
			if(i!=1){
				Bet bet = new Bet(6,Winner,Second,Third,competition,listSubs.get(i),0);
				listBets.add(bet);
			}
			else{
				Bet bet = new Bet(6,Winner,Loser,Third,competition,listSubs.get(i),0);
				listBets.add(bet);
			}
			
		}
		sut.settleWinner("comp#1",Winner,"mdp");
		assertTrue(subscriber1.getTokens()==18);
		assertTrue(subscriber2.getTokens()==9);
		assertTrue(subscriber3.getTokens()==18);
		assertNull(BettingSoft.getCompetitionByName("comp#1"));
	}
	
	
	@Test
	public void testSettlePodiumCreditcase3() throws AuthenticationException,
				 									 ExistingCompetitionException,
				 									 CompetitionException,
				 									 BadParametersException,
				 									 SQLException,
				 									 NotATeamException,
				 									 ExistingCompetitorException,
				 									 ExistingSubscriberException, 
				 									 SubscriberException   {	
		Competition competition = new Competition("comp#1","sport", MyCalendar.fromString("2017,01,01"));
		Competitor Winner = new Team("1","");
		Participation Winnerp = new Participation(1,Winner.getId(),competition.getId(),false);
		Competitor Second = new Team("2","");
		Participation Secondp = new Participation(2,Second.getId(),competition.getId(),false);
		Competitor Third = new Team("3","");
		Participation Thirdp = new Participation(3,Third.getId(),competition.getId(),false);
		Competitor Loser = new Team("4","");
		//Participation Loserp = new Participation(2,Loser.getId(),competition.getId(),false);
		Competition_Results compRes = new Competition_Results(Winnerp,Secondp,Thirdp,competition.getId());
		Competition_ResultsDAO.persist(compRes);
		List<Bet> listBets = new ArrayList<Bet>();
		List<Subscriber> listSubs = new ArrayList<Subscriber>();
		Subscriber subscriber1 = new Subscriber(1,"Champignon","Mdp","mellas","saad belkhadir",15, MyCalendar.fromString("1994,04,23"));
		
		listSubs.add(subscriber1);
		Subscriber subscriber2 = new Subscriber(2,"Champ","Mdp","el mahfoudi","saad",15, MyCalendar.fromString("1994,04,23"));
		listSubs.add(subscriber2);
		Subscriber subscriber3 = new Subscriber(3,"Champignone","Mdp","nasser","issam",15, MyCalendar.fromString("1994-04-23"));
		listSubs.add(subscriber3);
		for(int i=0; i<3; i++ ){
			
			Bet bet = new Bet(6,Loser,Loser,Loser,competition,listSubs.get(i),0);
			listBets.add(bet);
		
			
		}
		sut.settleWinner("comp#1",Winner,"mdp");
		assertTrue(subscriber1.getTokens()==15);
		assertTrue(subscriber2.getTokens()==15);
		assertTrue(subscriber3.getTokens()==15);
		assertNull(BettingSoft.getCompetitionByName("comp#1"));
	}
	
	@Test
	public static void testtherightresult() throws AuthenticationException,
									   					  ExistingCompetitionException,
									   					  CompetitionException,
									   					  BadParametersException,
									   					  SQLException,
									   					  NotATeamException,
									   					  ExistingCompetitorException {
		Competition competition = new Competition("comp#1","sport", MyCalendar.fromString("2017,01,01"));
		Competitor Winner = new Team("1","");
		Participation Winnerp = new Participation(1,Winner.getId(),competition.getId(),false);
		Competitor Second = new Team("2","");
		Participation Secondp = new Participation(2,Second.getId(),competition.getId(),false);
		Competitor Third = new Team("3","");
		Participation Thirdp = new Participation(3,Third.getId(),competition.getId(),false);
		Competition_Results compRes = new Competition_Results(Winnerp,Secondp,Thirdp,competition.getId());
		Competition_ResultsDAO.persist(compRes);
		ArrayList<Competitor> results =  BettingSoft.consultResultsCompetition("comp#1");
		assertEquals(results.get(1),Winner);
		assertEquals(results.get(2),Second);
		assertEquals(results.get(3),Third);
		
	}
	@Test(expected = ExistingCompetitionException.class)
	public void testDeleteCompetitionNull() throws AuthenticationException,
	   											   ExistingCompetitionException,
	   											   CompetitionException,
	   											   BadParametersException,
	   											   SQLException,
	   											   NotATeamException,
	   											   ExistingCompetitorException,
	   											   ExistingSubscriberException, 
	   											   SubscriberException   {	
		Competition competition = new Competition("comp#1","sport", MyCalendar.fromString("2017,01,01"));
		sut.deleteCompetition("comp#1", "mdp");
		Competition competitionTest = BettingSoft.getCompetitionByName("comp#1");
		}
	
	@Test
	public void testCreatCompetitor() throws AuthenticationException,
		  									 BadParametersException, 
		  									 ExistingCompetitorException, 
		  									 ExistingCompetitionException, 
		  									 SQLException{
		Competitor competitor = sut.createCompetitor("Test", sut.getmanagerPassword());
		Competition_ResultsDAO dao = new Competition_ResultsDAO();
		Competitor test = dao.getCompetitorById(competitor.getId());
		assertEquals(competitor,test);
	}
	
	public static void main(String[] arg) {
		Result result = JUnitCore.runClasses(BettingSoftTest.class);
	      for (Failure failure : result.getFailures()) {
	         System.out.println(failure.toString());
	      }
	    System.out.println(result.wasSuccessful());
	}
}

