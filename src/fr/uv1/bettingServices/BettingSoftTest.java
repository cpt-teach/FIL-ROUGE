package fr.uv1.bettingServices;

import org.junit.Assert.*;
import fr.uv1.bettingServices.Exceptions.*;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

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
		this.sut = new BettingSoft("mdp");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test(expected = AuthenticationException.class)
	public void testAuthenticateMngrNull() throws AuthenticationException {
		this.sut.authenticateMngr(null);	
	}
	
	/*public static void main(String[] arg) {
		Result result = JUnitCore.runClasses(BettingSoftTest.class);
	      for (Failure failure : result.getFailures()) {
	         System.out.println(failure.toString());
	      }
	    System.out.println(result.wasSuccessful());
	}*/
}
