package fr.uv1.competition;
import fr.uv1.bettingServices.Exceptions.*;
import static org.junit.Assert.*;
import fr.uv1.utils.*;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import fr.uv1.competition.*;
import fr.uv1.bettingServices.Participation;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class Competition_Results_Tests {
	
	private Participation p1;
	private Participation p2;
	private Participation p3;
	private Participation p4;
	private Competition_Results sut;
	private Competition comp;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		this.p1 = new Participation(11,1,1,false);
		this.p2 = new Participation(22,2,1,false);
		this.p3 = new Participation(33,3,1,false);
		this.p4 = new Participation(44,4,1,false);
		this.comp = new Competition("Rolland Garros", "Tennis", MyCalendar.fromString("2016-05-27"));
		this.sut = new Competition_Results(p1, p2, p3, comp.getId());
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void TestGetterAndSetterFirst() {
		this.sut.setFirst(this.p1);
		
		assertEquals(this.p1, this.sut.getFirst());
	}
	
	@Test
	public void TestGetterAndSetterFirstWithNull() {
		this.sut.setFirst(null);
		
		assertNull(this.sut.getFirst());
	}
	@Test
	public void TestGetterAndSetterSecond() {
		this.sut.setFirst(this.p2);
		
		assertEquals(this.p2, this.sut.getSecond());
	}
	
	@Test
	public void TestGetterAndSetterSecondWithNull() {
		this.sut.setSecond(null);
		
		assertNull(this.sut.getSecond());
	}
	@Test
	public void TestGetterAndSetterThird() {
		this.sut.setThird(this.p3);
		
		assertEquals(this.p3, this.sut.getThird());
	}
	
	@Test
	public void TestGetterAndSetterThirdWithNull() {
		this.sut.setThird(null);
		
		assertNull(this.sut.getThird());
	}
	
	@Test(expected = BadParametersException.class )
	public void TestRankInPodiumWithNull() throws BadParametersException {
		this.sut.rankInPodium(null);
	}
	
	@Test
	public void TestGetterAndSetterCompetition() {
		this.sut.setCompetition(comp.getId());
		
		assertEquals(comp.getId(), this.sut.getCompetition());
	}
	
	
	@Test
	public void TestPoduimRank() throws BadParametersException {
		
		assertEquals(1, sut.rankInPodium(this.p1));
		assertEquals(2, sut.rankInPodium(this.p2));
		assertEquals(3, sut.rankInPodium(this.p3));
		assertEquals(-1, sut.rankInPodium(this.p4));
	}
	
	@Test(expected = BadParametersException.class)
	public void TestPoduimRankWithNulls() throws BadParametersException {
		Competition_Results sut = new Competition_Results(null, null, null, 77);
	}
	
	@Test(expected = BadParametersException.class)
	public void TestPoduimRankWithSameParticipation() throws BadParametersException {

		Competition_Results sut = new Competition_Results(p1, p1, p1, 77);
	}
	
	
	
	public static void main(String[] args) {
	      Result result = JUnitCore.runClasses(Competition_Results_Tests.class);
	      for (Failure failure : result.getFailures()) {
	         System.out.println(failure.toString());
	      }
	      System.out.println(result.wasSuccessful());
	}
}
