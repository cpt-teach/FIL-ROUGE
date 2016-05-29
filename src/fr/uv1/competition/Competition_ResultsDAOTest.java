package fr.uv1.competition;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import fr.uv1.bettingServices.Participation;
import fr.uv1.bettingServices.Exceptions.*;
import fr.uv1.utils.MyCalendar;
import java.sql.SQLException;

public class Competition_ResultsDAOTest {
	private Participation podium1;
	private Participation podium2;
	private Participation podium3;
	private Participation podium4;
	private Competition_Results res;
	private Competition_ResultsDAO sut;
	private Competition comp;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		comp = new Competition("comp#1","sport", MyCalendar.fromString("2017,01,01"));
		podium1 = new Participation(1,1,1,false);
		podium2 = new Participation(2,2,1,false);
		podium3 = new Participation(3,3,1,false);
		podium4 = new Participation(4,4,1,false);
		res = new Competition_Results(podium1,podium2,podium3,comp.getId());
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testpersist() throws BadParametersException,
									 SQLException, 
									 ExistingCompetitionException {
		
		Competition_ResultsDAO.persist(res);
		int[] r = sut.ResultCompetition(comp);
		int[] result = sut.ParticipationToCompetitor(r);
		assertEquals(podium1.getTeamId(),result[1]);
		assertEquals(podium2.getTeamId(),result[2]);
		assertEquals(podium3.getTeamId(),result[3]);		
	}

	@Test
	public void testupdate() throws BadParametersException,
									SQLException, 
									ExistingCompetitionException {
		res.setFirst(podium4);
		Competition_ResultsDAO.update(res);
		int[] r = sut.ResultCompetition(comp);
		int[] result = sut.ParticipationToCompetitor(r);
		assertEquals(podium4.getTeamId(),result[1]);	
	}
	
	@Test
	public void testResultCompetition() throws BadParametersException,
											   SQLException, 
											   ExistingCompetitionException {
		
		int[] r = sut.ResultCompetition(comp);
		assertEquals(podium4.getParticipationId(),r[1]);	
	}	

	@Test
	public void testParticipationToCompetitor() throws BadParametersException,
											   SQLException, 
											   ExistingCompetitionException {
		
		int[] r = sut.ResultCompetition(comp);
		int[] result = sut.ParticipationToCompetitor(r);
		assertEquals(podium4.getTeamId(),result[1]);
	}
	
	@Test
	public void testgetCompetitorByIdcaseTeam() throws BadParametersException,
											   		   SQLException, 
											   		   ExistingCompetitionException, 
											   		   ExistingCompetitorException {
		Competitor competitor1 = new Team("teamtest","sporttest");
		int id_test = competitor1.getId();
		Competitor competitor2 = sut.getCompetitorById(id_test);
		assertEquals(competitor1,competitor2);
	}
	
	@Test
	public void testgetCompetitorByIdcaseIndividual() throws BadParametersException,
											   		   		 SQLException, 
											   		   		 ExistingCompetitionException, 
											   		   		 ExistingCompetitorException {
		Competitor competitor1 = new Individual("lastnametest","firstnametest","1994/06/28");
		int id_test = competitor1.getId();
		Competitor competitor2 = sut.getCompetitorById(id_test);
		assertEquals(competitor1,competitor2);
	}
}
