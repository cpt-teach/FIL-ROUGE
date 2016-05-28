package fr.uv1.competition;

import fr.uv1.competition.Competition_Results;
import fr.uv1.bettingServices.*;

import org.junit.Before
import org.junit.Test;
import static org.junit.Assert.*;


public class Competition_Results {
	
	private Participation p1;
	private Participation p2;
	private Participation p3;
	private Participation p4;
	private Competition_Results sut;
	
	@Before
	public void SetUp() {
		this.p1 = new Participation();
		this.p2 = new Participation();
		this.p3 = new Participation();
		this.p4 = new Participation();
		
		this.c = new Competition();
		
		this.sut = new Competition_Results();
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
		this.sut.setSecond(this.p1);
		
		assertEquals(this.p1, this.sut.getSecond());
	}
	
	@Test
	public void TestGetterAndSetterSecondWithNull() {
		this.sut.setSecond(null);
		
		assertNull(this.sut.getSecond());
	}
	
	@Test
	public void TestGetterAndSetterThird() {
		this.sut.setThird(this.p1);
		
		assertEquals(this.p1, this.sut.getThird());
	}
	
	@Test
	public void TestGetterAndSetterThirdWithNull() {
		this.sut.setThird(null);
		
		assertNull(cr.getThird());
	}
	
	@Test
	public void TestGetterAndSetterCompetition() {
		this.sut.setCompetition(77);
		
		assertEquals(77, this.sut.getCompetition());
	}
	
	@Test
	public void TestPoduimRank() {
		this.p1.setParticipationId(11); 
		this.p2.setParticipationId(22); 
		this.p3.setParticipationId(33);
		this.p4.setParticipationId(44);

		Competition_Results sut = new Competition_Results(p1, p2, p3, 77);
		
		assertEquals(1, sut.IsPodium(this.p1);
		assertEquals(2, sut.IsPodium(this.p2);
		assertEquals(3, sut.IsPodium(this.p3);
		assertEquals(-1, sut.IsPodium(this.p4);
	}
	
	/* 
		Actually NullPointerException Thrown 
		Because if you call IsPodium in this case, you call getParticipationId
		on null - Can't be
	*/
	@Test(expected = YourCustomExceptionInvalidParticipation.class)
	public void TestPoduimRankWithNulls() {
		Competition_Results sut = new Competition_Results(null, null, null, 77);
	}
	
	@Test(expected = YourCustomExceptionParticipationCantBeInAllThree.class)
	public void TestPoduimRankWithSameParticipation() {
		this.p1.setParticipationId(11); 

		Competition_Results sut = new Competition_Results(p1, p1, p1, 77);
	}
	
}