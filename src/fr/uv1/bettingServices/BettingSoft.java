package fr.uv1.bettingServices;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.*;
import fr.uv1.competition.*;


import fr.uv1.bettingServices.Exceptions.*;

import fr.uv1.utils.MyCalendar;


public class BettingSoft {

	private String managerPassword;
	private ArrayList<Subscriber> subscribers;
	private ArrayList<Competition> competitions;
	private Competition comp;
	private ArrayList<Bet> bets;
	private ArrayList<Competitor> competitors;
	private Subscriber subs;

	public void authenticateMngr(String managerPassword) throws AuthenticationException {

		if (managerPassword == null)
			throw new AuthenticationException("Enter a manager password");
		if (!this.managerPassword.equals(managerPassword))
			throw new AuthenticationException("Incorrect password ");		
	}
	

	public ArrayList<Subscriber> listSubscribers(String managerPwd) throws AuthenticationException, BadParametersException{ 

		this.authenticateMngr(managerPwd);
		ArrayList<Subscriber> listSubscribers=new ArrayList<Subscriber>();
			try {
				listSubscribers=SubscriberDAO.listOfSubscribers();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		return listSubscribers;

	}
	public long unsubscribe(String managerPwd, String username)throws BadParametersException, ExistingSubscriberException, AuthenticationException{
		this.authenticateMngr(managerPwd);
		Subscriber subscriber=Subscriber.getSubscriberByUsername(username);
		long remainingTokens= subscriber.getTokens();
			try {
				SubscriberDAO.delete(subscriber);
			} catch (SQLException exception) {
				exception.printStackTrace();
			}
		return remainingTokens;
				}
	
	public String susbscribe(String lastname, String firstname, String username, MyCalendar birthday, String managerPwd) throws BadParametersException, ExistingSubscriberException, AuthenticationException{
		this.authenticateMngr(managerPwd);
		Subscriber subscriber=Subscriber.getSubscriberByUsername(username);
		if(subscriber!=null){
			throw new ExistingSubscriberException("Username "+username+" already used");
		}
		subscriber=new Subscriber(username, lastname, firstname, birthday);
		
		return subscriber.getPassword();
	}
		public void creditSubscriber(String username, long numberTokens, String managerPwd) throws BadParametersException, AuthenticationException, ExistingSubscriberException{
			this.authenticateMngr(managerPwd);
			Subscriber subscriber = Subscriber.getSubscriberByUsername(username);
			if(subscriber==null){
				throw new ExistingSubscriberException("Subscriber doesn't exist");
			}
			if (numberTokens < 0)
				throw new BadParametersException("The number of tokens must be positive value (given : " + numberTokens + ")");
		// Credit the subscriber
		long existingTokens=subscriber.getTokens();
		subscriber.setTokens(existingTokens+ numberTokens);
		// Update to the database
			try {
				SubscriberDAO.update(subscriber);
			} catch (SQLException exception) {
				exception.printStackTrace();
			}
	}
		public void debitSubscriber(String username, long numberTokens, String managerPwd) throws AuthenticationException, BadParametersException, ExistingSubscriberException{
			authenticateMngr(managerPwd);
			Subscriber subscriber=Subscriber.getSubscriberByUsername(username);
			if(subscriber==null){
				throw new ExistingSubscriberException("Subscriber doesn't exist");
			}
			if (numberTokens < 0)
				throw new BadParametersException("The number of tokens must be positive value (given : " + numberTokens + ")");
		// Credit the subscriber
		long existingTokens=subscriber.getTokens();
		subscriber.setTokens(existingTokens - numberTokens);
		// Update to the database
			try {
				SubscriberDAO.update(subscriber);
			} catch (SQLException exception) {
				exception.printStackTrace();
			}
	}
		public void changeSubsPwd(String username, String newPwd, String currentPwd) throws BadParametersException, AuthenticationException{
			Subscriber subscriber=Subscriber.getSubscriberByUsername(username);
			Subscriber.authenticateSubscriber(username,currentPwd);
			subscriber.setPassword(newPwd);
			try {
				SubscriberDAO.update(subscriber);
			} catch (SQLException exception) {
				exception.printStackTrace();
			}		
		}

}
