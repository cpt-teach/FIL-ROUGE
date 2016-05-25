package lot2;

import java.io.Serializable;
import bd.editBD;
import java.sql.ResultSet;
import java.util.ArrayList;

import bd.selectBD;
import fr.uv1.bettingServices.exceptions.AuthenticationException;
import fr.uv1.bettingServices.exceptions.BadParametersException;
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
	
	private ArrayList<Bet> betlist;
	private String firstname;
	private String lastname;
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
	public Subscriber(String a_name, String a_firstName, String a_username, MyCalendar a_birthday)
			throws BadParametersException {
		this.setLastname(a_name);
		this.setFirstname(a_firstName);
		this.setUsername(a_username);
		// Generate password
		this.password = RandPass.getPass(Constraints.LONG_PWD);
		this.setbirthday(a_birthday);
	}
    public long gettokens(){
    	return tokens;
    }
    public String getFirstname() {
		return firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public String getUsername() {
		return username;
	}
	public String getPassword() {
		return password;
	}
	public MyCalendar getBirthday() {
		return birthday;
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
	private void setPassword(String password) throws BadParametersException {
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

	
	private ArrayList<String> listOfBets() {
		ArrayList<String> result = new ArrayList<String>();
		long sum = 0;
		for (Bet b : betlist) {
			sum += b.getBettorBet();
			result.add(b.toString());
		}
		result.add(0, Long.toString(sum));
		return result;
	}
	
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
		return this.firstname.equals(s.firstname);
	}

	@Override
	public int hashCode() {
		return this.username.hashCode();
	}

	@Override
	public String toString() {
		return " " + firstname + " " + lastname + " " + username;
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
		// Same rules as for the last namehttps://www.google.fr/search?client=ubuntu&channel=fs&q=jobteaser+telecom+bretagne&ie=utf-8&oe=utf-8&gfe_rd=cr&ei=joowV8rOMtLFaKWBsfAF
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
		if (a_username == null)https://www.google.fr/search?client=ubuntu&channel=fs&q=jobteaser+telecom+bretagne&ie=utf-8&oe=utf-8&gfe_rd=cr&ei=joowV8rOMtLFaKWBsfAF
			throw new BadParametersException("username not instantiated");

		if (a_username.length() < LONG_USERNAME)
			throw new BadParametersException("username length less than "
					+ LONG_USERNAME + "characters");
		// Just letters and digits are allowed
		if (!a_username.matches(REGEX_USERNAME))
			throw new BadParametersException("the username " + a_username
					+ " does not verify constraints ");
	}
	
	
	
	
	
	
	
	private void authenticateSubscriber(String password) 
			throws AuthenticationException {
		if (!this.password.equals(password))
			throw new AuthenticationException("please verify the password");
	}
	
	
	public void creditSubscriber(long Tokens)
			throws BadParametersException {
		// Check parameter
		if (Tokens < 0)
			throw new BadParametersException("The number of tokens must be positive value (given : " + Tokens + ")");
		// Credit the subscriber
		tokens = (int) (tokens + Tokens);
		// Update to the database
		update();
	}
	public void debitSubscriber(long Tokens)
			throws BadParametersException {
		// Check parameter
		if (Tokens < 0)
			throw new BadParametersException("The number of tokens must be positive value (given : " + Tokens + ")");
		// Credit the subscriber
		tokens = (int) (tokens - Tokens);
		// Update to the database
		update();
	}
	
	
	
/*waiting for Competition*/
	public void betOnWinner(long Tokens, Competition competition,
			Competitor winner, String bettorPassword)
					throws AuthenticationException, SubscriberException,
					BadParametersException, CompetitionException {//still need to program those exceptions
		// Authenticate subscriber
		authenticateSubscriber(bettorPassword);
		// Check if the subscriber is not competing
		Competitor c = new Competitor( lastname, firstname, birthday); //will update this after getting the Competitor Structure
		if (competition.hasCompetitor(c))
			throw new CompetitionException("The subscriber is a competitor of the competition");
		// Create the bet
		new Bet(Tokens,Competition, Competitior, winner, this);// we need to create this class
	}
		
	public void betOnPodium(long numberTokens, Competition competition,
			Competitor1, Competitor2, Competitor3,
			String pwdSubs)
					throws AuthenticationException, BadParametersException,
					CompetitionException, SubscriberException {//still need to program those exceptions
		// Authenticate subscriber
		authenticateSubscriber(pwdSubs);
		// Check if the subscriber is a competitor of the competition
		Competitor c = new Competitor(-1, lastname, firstname, birthday);
		if (competition.hasCompetitor(c))
			throw new CompetitionException("The subscriber is a competitor of the competition");
		// Create the bet
		new Bet(numberTokens, competition, Competitor1, Competitor2 , Competitor3, this);
	}
	
	
	public void deleteBetsCompetition(Competition competition, String pwdSubs)
			throws AuthenticationException, CompetitionException {
		// Authenticate subscriber
		authenticateSubscriber(pwdSubs);
		// Delete bets if they are about the specified competition
		ArrayList<Bet> betsToRemove = new ArrayList<Bet>();
		for (Bet b : bets)
			if (b.getCompetition().equals(competition)){
				betsToRemove.add(b);
				}
		for (Bet b : betsToRemove){
				b.delete();
				}
	}
	public void update(){
		editBD.edit("postgres","postgres","jdbc:postgresql://localhost:54321/Test", "update subscribers set lastname="+this.lastname+", firstname=+"+this.firstname+", birthday="+this.birthday.toString()+", password="+this.password+", Tokens="+Long.toString(this.tokens)+" where username="+username+";");
		
	}

	public int getSubscriberId(){
		ResultSet result = selectBD.select("postgres","postgres","jdbc:postgresql://localhost:54321/Test", "SELECT id FROM participation WHERE username="+this.username+";");
		return result.getInt(1);
	}
	
	public void changeSubsPwd(String currentPwd, String newPwd)
			throws BadParametersException, AuthenticationException {
		// Authenticate Subscriber
		authenticateSubscriber(currentPwd);
		// Change password
		this.password = newPwd;
		// Update to the database
		update();
	
	}
	public static Subscriber getSubscriberByUsername(String username){
		// STUB TODO By Mahfoudi
	}
	
	void debitSubscriber(java.lang.String username, // BS
            long numberTokens,
            java.lang.String managerPwd){

		
		//TODO By Mahfoudi
	}
	
	void creditSubscriber(java.lang.String username, // BS
            long numberTokens,
            java.lang.String managerPwd){

		
		//TODO By Mahfoudi
	}
}