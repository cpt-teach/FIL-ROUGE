package fr.uv1.bettingServices;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import fr.uv1.utils.*;
import fr.uv1.bettingServices.Exceptions.*;

public class SubscriberTest {
	public String managerPwd= "lol";
	public BettingSoft manager=new BettingSoft(managerPwd);
	
	

	
	@Test
	public void testGetTokens() throws BadParametersException, AuthenticationException, ExistingSubscriberException, SubscriberException{
		BettingSoft elmanager = new BettingSoft(managerPwd);
		//case 1
	Subscriber subscriber = new Subscriber("lelolo","el mahfoudi","saad", MyCalendar.fromString("1994,04,23"));
	assertTrue(subscriber.getTokens()==0);
		//case 2
	elmanager.creditSubscriber("lelolo", 15, managerPwd);
	assertEquals(15,subscriber.getTokens());
	elmanager.debitSubscriber("lelolo", 13, managerPwd);
	assertEquals(2,subscriber.getTokens());
	Subscriber subscriber1 = new Subscriber(5,"Champ","passss","el mahfoudi","saad",13, MyCalendar.fromString("1994,04,23"));
	assertEquals(13,subscriber1.getTokens());
	
	
	}
/*
	@Test
	public void testGetFirstName() throws BadParametersException {
	Subscriber subscriber = new Subscriber("Champ","el mahfoudi","saad", MyCalendar.fromString("1994,04,23"));
	assertEquals("saad",subscriber.getFirstName());
	Subscriber subscriber1 = new Subscriber(5,"Champ","passss","el mahfoudi","saad",15, MyCalendar.fromString("1994,04,23"));
	assertEquals("saad",subscriber1.getFirstName());
		
	}

	@Test
	public void testGetLastName() throws BadParametersException {
	Subscriber subscriber = new Subscriber("Champion","el mahfoudi","saad", MyCalendar.fromString("1994,04,23"));
	assertEquals("el mahfoudi",subscriber.getLastName());
	Subscriber subscriber1 = new Subscriber(5,"Champlala","passss","irhboula","anas",15, MyCalendar.fromString("1994,04,23"));
	assertEquals("irhboula",subscriber1.getLastName());
	}

	@Test
	public void testGetUserName() throws BadParametersException {
	Subscriber subscriber = new Subscriber(5,"Champignon","Mdp","mellas","saad belkhadir",15, MyCalendar.fromString("1994,04,23"));
	assertEquals("Champignon",subscriber.getUserName());
	assertFalse("Champlala"==subscriber.getUserName());
	Subscriber subscriber2 = new Subscriber("Champ","el mahfoudi","saad", MyCalendar.fromString("1994,04,23"));
	assertEquals("Champ",subscriber2.getUserName());
	}

	@Test
	public void testGetPassword() throws BadParametersException {
	Subscriber subscriber = new Subscriber(5,"Champignon","Mdp","mellas","saad belkhadir",15, MyCalendar.fromString("1994,04,23"));
	assertEquals("Mdp",subscriber.getPassword());
	assertFalse("Champlala"==subscriber.getPassword());
	Subscriber subscriber2 = new Subscriber("Champ","el mahfoudi","saad", MyCalendar.fromString("1994,04,23"));
	assertFalse(""==subscriber2.getPassword());
	}

	@Test
	public void testGetBirthday() throws BadParametersException {
	Subscriber subscriber = new Subscriber(5,"Champignon","Mdp","mellas","saad belkhadir",15, MyCalendar.fromString("1994-04-23"));
	assertEquals("1994-4-23",subscriber.getBirthday().toString2());
	//an order in birthday is imposed (aaaa-mm-dd)
	assertFalse("23-4-1994"==subscriber.getBirthday().toString2()); 
	Subscriber subscriber1 = new Subscriber("Champ","el mahfoudi","saad", MyCalendar.fromString("1994-04-23"));
	assertFalse("2016-4-23"==subscriber1.getBirthday().toString2());
	}

	@Test
	public void testGetSubscriber_id() throws BadParametersException {
	Subscriber subscriber = new Subscriber(5,"Champignon","Mdp","mellas","saad belkhadir",15, MyCalendar.fromString("1994,04,23"));
	assertEquals(5,subscriber.getSubscriber_id());
	assertFalse(0==subscriber.getSubscriber_id());
	Subscriber subscriber2 = new Subscriber("Champ","el mahfoudi","saad", MyCalendar.fromString("1994,04,23"));
	assertFalse(0==subscriber2.getSubscriber_id());
	}
	
	@Test
	public void testSubscriberIntStringStringStringStringLongMyCalendar() throws BadParametersException {
	Subscriber subscriber = new Subscriber(5,"Champignon","Mdp","mellas","saad belkhadir",15, MyCalendar.fromString("1994-04-23"));
	assertTrue(subscriber.getSubscriber_id()==5);
	assertTrue(subscriber.getUserName()=="Champignon");
	assertTrue(subscriber.getPassword()=="Mdp");
	assertTrue(subscriber.getLastName()=="mellas");
	assertTrue(subscriber.getFirstName()=="saad belkhadir");
	assertTrue(subscriber.getTokens()==15);
	assertEquals("1994-4-23",subscriber.getBirthday().toString2());
	}
	
	
	@Test
	public void testSubscriberStringStringStringMyCalendar() throws BadParametersException {
	Subscriber subscriber = new Subscriber("Champignone","nasser","issam", MyCalendar.fromString("1994-04-23"));
	assertTrue(subscriber.getUserName()=="Champignone");
	assertTrue(subscriber.getLastName()=="nasser");
	assertTrue(subscriber.getFirstName()=="issam");
	assertEquals("1994-4-23",subscriber.getBirthday().toString2());
	}
	
	@Test(expected=BadParametersException.class)
	public void testInvalidSubscriberStringStringStringMyCalendar() throws BadParametersException {
	//case 1
	Subscriber subscriber = new Subscriber("","nasser","issam", MyCalendar.fromString("1994-04-23"));
	//case 2
	Subscriber subscriber1 = new Subscriber("l","","issam", MyCalendar.fromString("1994-04-23"));
	//case 3
	Subscriber subscriber2 = new Subscriber("champignon","123","issam", MyCalendar.fromString("1994-04-23"));
	//case 4
	Subscriber subscriber3 = new Subscriber("Champ","nasser","123", MyCalendar.fromString("1994-04-23"));
	}
	
	@Test(expected=BadParametersException.class)
	public void testInvalidSubscriberIntStringStringStringStringLongMyCalendar() throws BadParametersException {
	//case 1
	Subscriber subscriber = new Subscriber(5,"Champignon","Motdepasse","mellas","saad belkhadir",-3, MyCalendar.fromString("1994-04-23"));
	//case 2
	Subscriber subscriber2 = new Subscriber(5,"Champignon","","mellas","saad belkhadir",15, MyCalendar.fromString("1994-04-23"));
	//case 3
	Subscriber subscriber3 = new Subscriber(5,"Champignon","Mdp","mellas","saad belkhadir",13, MyCalendar.fromString("1994-04-23"));
	//case 4
	Subscriber subscriber4 = new Subscriber(5,"Champignon","Motdepasse","me","",0, MyCalendar.fromString("1994-04-23"));
	//case 5
	Subscriber subscriber5 = new Subscriber(5,"ch","Mdp","mellas","saad belkhadir",-3, MyCalendar.fromString("1994-04-23"));
		
	}
	@Test
	public void testSetPassword() throws BadParametersException {
	Subscriber subscriber = new Subscriber(5,"Champignon","Motdepasse","el mahfoudi","saadoune",0, MyCalendar.fromString("1994-04-23"));
	subscriber.setPassword("Motdepasse123456");
	assertEquals("Motdepasse123456",subscriber.getPassword());
	}
	@Test(expected=BadParametersException.class)
	public void testInvalidSetPassword() throws BadParametersException{
	//case 1
	Subscriber subscriber = new Subscriber(5,"Champignon","Motdepasse","el mahfoudi","saadoune",0, MyCalendar.fromString("1994-04-23"));
	//Password must have more than 8 caracters
	subscriber.setPassword("Mdp");
	//case 2
	Subscriber subscriber2 = new Subscriber(5,"Champignon","Motdepasse123","el mahfoudi","saadoune",0, MyCalendar.fromString("1994-04-23"));
	subscriber.setPassword("1235");
	}
	

	@Test
	public void testSetSubscriber_id() throws BadParametersException {
	Subscriber subscriber = new Subscriber(5,"Champignon","Motdepasse","el mahfoudi","saadoune",0, MyCalendar.fromString("1994-04-23"));
	subscriber.setSubscriber_id(12);
	assertEquals(12,subscriber.getSubscriber_id());
	}
	@Test(expected=BadParametersException.class)
	public void testInvalidSetSubscriber_id() throws BadParametersException{
	Subscriber subscriber = new Subscriber(5,"Champignon","Motdepasse","el mahfoudi","saadoune",0, MyCalendar.fromString("1994-04-23"));
	subscriber.setSubscriber_id(-3);
	}

	@Test
	public void testSetTokens() throws BadParametersException {
	Subscriber subscriber = new Subscriber(5,"Champignon","Motdepasse","el mahfoudi","saadoune",0, MyCalendar.fromString("1994-04-23"));
	subscriber.setTokens(33);
	assertEquals(33,subscriber.getTokens());
	
	}
	@Test(expected=BadParametersException.class)
	public void testInvalidSetTokens() throws BadParametersException {
		Subscriber subscriber = new Subscriber(5,"Champignon","Motdepasse","el mahfoudi","saadoune",0, MyCalendar.fromString("1994-04-23"));
		subscriber.setTokens(-4);
		}

	@Test
	public void testAuthenticateSubscriber() throws BadParametersException, AuthenticationException, SubscriberException {
		Subscriber subscriber1 = new Subscriber(5,"Champignon","Motdepasse","el mahfoudi","saadoune",0, MyCalendar.fromString("1994-04-23"));
		Subscriber.authenticateSubscriber("Champignon", "Motdepasse");//Valider après l'accès à la bdd
		Subscriber subscriber2 = new Subscriber(5,"Champlala","passss","nasser","issam",15, MyCalendar.fromString("1994-04-23"));
		Subscriber.authenticateSubscriber(subscriber2.getUserName(), subscriber2.getPassword());
	
	}
	public void testHasUsername() throws BadParametersException {
		Subscriber subscriber = new Subscriber(5,"Champignon","Motdepasse","el mahfoudi","saadoune",0, MyCalendar.fromString("1994-04-23"));
		subscriber.hasUsername("Champignon");
	}

	@Test
	public void testEqualsObject() throws BadParametersException {
		Subscriber subscriber = new Subscriber(5,"Champlala","passss","irhboula","anas",15, MyCalendar.fromString("1994,04,23"));
		Subscriber subscriber1 = new Subscriber(5,"Champignon","Motdepasse","el mahfoudi","saadoune",0, MyCalendar.fromString("1994-04-23"));
		Subscriber subscriber2 = new Subscriber(5,"Champignon","Motdepassedd","Inconnu","saadouness",0, MyCalendar.fromString("1994-05-23"));
		assertFalse(subscriber.equals(subscriber1));
		assertTrue(subscriber1.equals(subscriber2));
		assertFalse(subscriber.equals(subscriber2));
	}

	@Test
	public void testToString() throws BadParametersException {
		Subscriber subscriber = new Subscriber(5,"Champlala","Motdepasse","irhboula","anas",15, MyCalendar.fromString("1994,04,23"));
		assertEquals(" anas irhboula Champlala",subscriber.toString());
	}
	@Test
	public void testInfosSubscriber() throws BadParametersException {
		Subscriber subscriber = new Subscriber(5,"Champlala","Motdepasse","nasser","issam",15, MyCalendar.fromString("1994-04-23"));
		ArrayList<String> result = new ArrayList<String>();
		result.add("nasser");
		result.add("issam");
		result.add("1994-4-23");
		result.add("Champlala");
		result.add("15");
		assertEquals(result,subscriber.infosSubscriber(subscriber));
		
	}

	@Test(expected=AuthenticationException.class)
	public void testInvalidAuthenticateSubscriber() throws BadParametersException, AuthenticationException, SubscriberException {
		Subscriber subscriber1 = new Subscriber(5,"Champignon","Motdepasse0","el mahfoudi","saadoune",0, MyCalendar.fromString("1994-04-23"));
		Subscriber.authenticateSubscriber("Champignon", "Motdepasse");//Valider après l'accès à la bdd
		Subscriber subscriber2 = new Subscriber(5,"Champlala","Moooootdepasse","nasser","issam",15, MyCalendar.fromString("1994-4-23"));
		Subscriber.authenticateSubscriber(subscriber2.getUserName(), "Motdepasse");
	
	}

	@Test
	public void testGetSubscriberByUsername() throws BadParametersException, SubscriberException {
		Subscriber subscriber1 = new Subscriber(5,"Champignon","","el mahfoudi","saadoune",0, MyCalendar.fromString("1994-04-23"));
		Subscriber subscriber2 =Subscriber.getSubscriberByUsername("Champignon");
	}
	@Test(expected=SubscriberException.class)
	public void testInvalidGetSubscriberByUsername() throws BadParametersException, SubscriberException, ExistingSubscriberException, AuthenticationException {
		Subscriber subscriber1 = new Subscriber("Champignon","el mahfoudi","saadoune",MyCalendar.fromString("1994-04-23"));
		manager.unsubscribe(managerPwd, "Champignon");
		Subscriber subscriber2=Subscriber.getSubscriberByUsername("Champignon");

	}
	
	

	@Test
	public void testGetSubscriberById() throws BadParametersException, SubscriberException {
		Subscriber subscriber1 = new Subscriber("Champignon","el mahfoudi","saadoune",MyCalendar.fromString("1994-04-23"));
		int id=subscriber1.getSubscriber_id();
		Subscriber subscriber2 =Subscriber.getSubscriberById(id);
		assertTrue(subscriber1.equals(subscriber2));
	}*/

}
