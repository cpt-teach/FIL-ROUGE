package fr.uv1.bettingServices;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import fr.uv1.bettingServices.Exceptions.*;
import fr.uv1.utils.*;

/**
 * 
 * @author prou, segarra<br>
 * <br>
 *         This class represents a subscriber for a betting application. <br>
 * <br>
 *         The constructor of the class creates a password for the subscriber. <br>
 *         <ul>
 *         <li>subscriber's password validity:
 *         <ul>
 *         <li>only letters and digits are allowed</li>
 *         <li>password size should be at least 8 characters</li>
 *         </ul>
 *         </li>
 *         <li>for the username validity:
 *         <ul>
 *         <li>only letters and digits are allowed</li>
 *         <li>size should be at least 4 characters</li>
 *         </ul>
 *         </li>
 *         </ul>
 * 
 */
public class Subscriber implements Serializable {
	private static final long serialVersionUID = 6050931528781005411L;
	/*
	 * Minimal size for a subscriber's username
	 */
	private static final int LONG_USERNAME = 4;
	/*
	 * Constraints for last and firstname and username
	 */
	private static final String REGEX_NAME = new String("[a-zA-Z][a-zA-Z\\-\\ ]*");
	private static final String REGEX_USERNAME = new String("[a-zA-Z0-9]*");
	private long tokens;
	private String firstname;
	private String lastname;
	private int subscriber_id;
	/** 
	 * @uml.property name="username"
	 */
	private String username;
	private String password;
	private MyCalendar birthday;

	/*
	 * the constructor calculates a password for the subscriber. No test on the
	 * validity of names
	 */
	public Subscriber(String a_username, String a_name, String a_firstName, MyCalendar a_birthday)
			throws BadParametersException {
		SubscriberDAO dao = new SubscriberDAO();
		this.subscriber_id=0;
		this.setLastname(a_name);
		this.setFirstname(a_firstName);
		this.setUsername(a_username);
		// Generate password
		this.setPassword(RandPass.getPass(Constraints.LONG_PWD));
		this.setbirthday(a_birthday);
				try {
				dao.persist(this);
				} catch (SQLException e) {
					e.printStackTrace();
				}
	}
	public Subscriber(int subscriber_id, String a_username, String password, String a_name, String a_firstName, long tokens, MyCalendar a_birthday)
			throws BadParametersException {
		this.subscriber_id=subscriber_id;
		this.setLastname(a_name);
		this.setFirstname(a_firstName);
		this.setUsername(a_username);
		this.password = password;
		this.setbirthday(a_birthday);
		this.setTokens(tokens);	
	}
    public long getTokens(){
    	return tokens;
    }
    public String getFirstName() {
		return firstname;
	}
	public String getLastName() {
		return lastname;
	}
	public String getUserName() {
		return username;
	}
	public String getPassword() {
		return password;
	}
	public MyCalendar getBirthday() {
		return birthday;
	}
	public int getSubscriber_id(){
		return this.subscriber_id;
	}

	
	
	private void setLastname(String lastname) throws BadParametersException {
		if (lastname == null)
			throw new BadParametersException("lastname is not valid");
		checkStringLastName(lastname);
		this.lastname = lastname;
	}
	private void setFirstname(String firstname) throws BadParametersException {
		if (firstname == null)
			throw new BadParametersException("firstname is not valid");
		checkStringFirstName(firstname);
		this.firstname = firstname;
		
	}
	private void setUsername(String username) throws BadParametersException {
		if (username == null)
			throw new BadParametersException("username is not valid");
		checkStringUsername(username);
		this.username = username;
	}
	public void setPassword(String password) throws BadParametersException {
		if (password == null)
			throw new BadParametersException("password is not valid");
		if (!BettingPasswordsVerifier.verify(password))
			throw new BadParametersException("password is not valid");
		this.password = password;
		
	}
	private void setbirthday(MyCalendar birthday) throws BadParametersException{
		if (birthday == null)
			throw new BadParametersException("birthady is not valid");
		this.birthday=birthday;
		
	}
	public void setSubscriber_id(int subscriber_id) throws BadParametersException{
		if(subscriber_id<=0)
			throw new BadParametersException("subscriber_id is not valid");
		this.subscriber_id=subscriber_id;
	}
	public void setTokens(long Tokens) throws BadParametersException{
		if(Tokens<0) {
			throw new BadParametersException("enter a positive tokens number");}
		this.tokens=Tokens;
	}
	
//	private ArrayList<String> listOfBets() {
//		ArrayList<String> result = new ArrayList<String>();
//		long sum = 0;
//		for (Bet b : betlist) {
//			sum += b.getBettorBet();
//			result.add(b.toString());
//		}
//		result.add(0, Long.toString(sum));
//		return result;
//	}
	
	/*
	 * check if this subscriber has the username of the parameter
	 * 
	 * @param username the username to check
	 * 
	 * @return true if this username is the same as the parameter false
	 * otherwise
	 */
	public boolean hasUsername(String username) {
		if (username == null)
			return false;
		return this.username.equals(username);
	}

	/*
	 * Two subscribers are equal if they have the same username
	 */
	@Override
	public boolean equals(Object an_object) {
		if (!(an_object instanceof Subscriber))
			return false;
		Subscriber s = (Subscriber) an_object;
		return this.username.equals(s.username);
	}

	@Override
	public String toString() {
		return " " + this.firstname + " " + this.lastname + " " + this.username;
	}
	public ArrayList<String> infosSubscriber(Subscriber subscriber) {
		ArrayList<String> result = new ArrayList<String>();
		result.add(subscriber.getLastName());
		result.add(subscriber.getFirstName());
		result.add(subscriber.getBirthday().toString2());
		result.add(subscriber.getUserName());
		result.add(Long.toString(subscriber.getTokens()));
		return result;
	}


	/**
	 * check the validity of a string for a subscriber lastname, letters, dashes
	 * and spaces are allowed. First character should be a letter. lastname
	 * length should at least be 1 character
	 * 
	 * @param a_lastname
	 *            string to check.
	 * 
	 * @throws BadParametersException
	 *             raised if invalid.
	 */
	private static void checkStringLastName(String a_lastname)
			throws BadParametersException {

		if (a_lastname == null)
			throw new BadParametersException("name not instantiated");
		if (a_lastname.length() < 1)
			throw new BadParametersException(
					"name length less than 1 character");
		// First character should be a letter ; then just letters, dashes or
		// spaces
		if (!a_lastname.matches(REGEX_NAME))
			throw new BadParametersException("the name " + a_lastname
					+ " does not verify constraints ");
	}

	/**
	 * check the validity of a string for a subscriber firstname, letters,
	 * dashes and spaces are allowed. First character should be a letter.
	 * firstname length should at least be 1 character
	 * 
	 * @param a_firstname
	 *            string to check.
	 * 
	 * @throws BadParametersException
	 *             raised if invalid.
	 */
	private static void checkStringFirstName(String a_firstname)
			throws BadParametersException {
		// Same rules as for the last name
		checkStringLastName(a_firstname);

	}

	/**
	 * check the validity of a string for a subscriber username, letters and
	 * digits are allowed. username length should at least be LONG_USERNAME
	 * characters
	 * 
	 * @param a_username
	 *            string to check.
	 * 
	 * @throws BadParametersException
	 *             raised if invalid.
	 */
	private static void checkStringUsername(String a_username)
			throws BadParametersException {
		if (a_username == null)
			throw new BadParametersException("username not instantiated");

		if (a_username.length() < LONG_USERNAME)
			throw new BadParametersException("username length less than "
					+ LONG_USERNAME + "characters");
		// Just letters and digits are allowed
		if (!a_username.matches(REGEX_USERNAME))
			throw new BadParametersException("the username " + a_username
					+ " does not verify constraints ");
	}
	
	
	
	public static void authenticateSubscriber(String username, String password) 
												throws AuthenticationException, BadParametersException, SubscriberException {
			System.out.println(username);
			Subscriber subscriber = getSubscriberByUsername(username);
			String userPassword = subscriber.getPassword();
			if (password==null){
				throw new AuthenticationException("enter user password");
			}
			if (!userPassword.equals(password))
				throw new AuthenticationException("please verify the password");	
	}
	

	public static Subscriber getSubscriberByUsername(String username)
			throws BadParametersException, SubscriberException {
			Subscriber subscriber = null;
			try {
				subscriber = SubscriberDAO.getSubscriberByUsername(username);
			} catch (SQLException exception) {
				exception.printStackTrace();
			}
			return subscriber;
	}
	public static Subscriber getSubscriberById(int subscriber_id)
			throws BadParametersException, SubscriberException{
			Subscriber subscriber=null;
			try {
				subscriber=SubscriberDAO.getSubscriberById(subscriber_id);
			} catch (SQLException exception) {
				exception.printStackTrace();
			}
			return subscriber;
	}
/*	 public static void main (String [] arg ) throws BadParametersException, AuthenticationException, SubscriberException {
		Subscriber saad=new Subscriber("loulou","el mahfoudi","saad",MyCalendar.fromString("1994,04,23"));
		Subscriber anas=new Subscriber("loulou1","Irhboula","anas",MyCalendar.fromString("1998,03,23"));
		Subscriber issam=new Subscriber("loulou2","nasser","issam",MyCalendar.fromString("1995,05,23"));
		Subscriber mellas=new Subscriber("loulou2","nasser","issam",MyCalendar.fromString("1995,05,23"));
		Subscriber s = getSubscriberByUsername("loulou");
		BettingSoft.changeSubsPwd("loulou1","fuckyouu",anas.getPassword());
		Subscriber s2 = getSubscriberByUsername("caca");
		BettingSoft.changeSubsPwd("caca","fuckyouuu", "nTch3jPs");
		System.out.println(anas.getFirstName()); 
		
	}*/

	
	
	
}