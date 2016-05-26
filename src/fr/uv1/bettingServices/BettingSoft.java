package fr.uv1.bettingServices;
import java.text.SimpleDateFormat; 
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import fr.uv1.bettingServices.*;
import fr.uv1.competition.*;
import fr.uv1.bettingServices.Exceptions.*;
import fr.uv1.utils.MyCalendar;

public class BettingSoft implements Betting {
	
	private String managerPassword;
	private ArrayList<Subscriber> subscribers;
	private ArrayList<Competition> competitions;
	private Competition comp;
	private ArrayList<Bet> bets;
	private ArrayList<Competitor> competitors;
	private ArrayList<Bet> listpodiumbets;
	private ArrayList<Bet> listwinnerbets;
	private Subscriber subs;

	
	public BettingSoft(String managerPassword) {
		setManagerPassword(managerPassword);
		setSubscribers(new ArrayList<Subscriber>());
		setCompetitions(new ArrayList<Competition>());
		setCompetitors(new ArrayList<Competitor>());
		setListpodiumbets(new ArrayList<Bet>());
		setListwinnerbets(new ArrayList<Bet>());
	}
	
	public ArrayList<Subscriber> getSubscribers() {
		return subscribers;
	}

	public void setSubscribers(ArrayList<Subscriber> subscribers) {
		this.subscribers = subscribers;
	}

	public ArrayList<Competition> getCompetitions() {
		return competitions;
	}

	public void setCompetitions(ArrayList<Competition> competitions) {
		this.competitions = competitions;
	}

	public ArrayList<Competitor> getCompetitors() {
		return competitors;
	}

	public void setCompetitors(ArrayList<Competitor> competitors) {
		this.competitors = competitors;
	}

	public ArrayList<Bet> getListpodiumbets() {
		return listpodiumbets;
	}

	public void setListpodiumbets(ArrayList<Bet> listpodiumbets) {
		this.listpodiumbets = listpodiumbets;
	}

	public ArrayList<Bet> getListwinnerbets() {
		return listwinnerbets;
	}

	public void setListwinnerbets(ArrayList<Bet> listwinnerbets) {
		this.listwinnerbets = listwinnerbets;
	}

	public String getManagerPassword() {
		return managerPassword;
	}

	public void setManagerPassword(String managerPassword) {
		this.managerPassword = managerPassword;
	}

	public void addCompetition(String competitionName,
			Calendar endDate, ArrayList<Competitor> listCompetitors,
			String managerPassword) throws CompetitionException, ExistingCompetitionException, AuthenticationException {
		

	}
	


	@Override
	public String subscribe(String lastname, String firstname, String username, String borndate,
			String managerPassword) throws SubscriberException, ExistingSubscriberException, AuthenticationException, BadParametersException {
		
	}

	@Override
	public long unsubscribe(String username, String managerPassword) throws ExistingSubscriberException, AuthenticationException {

		             
	
	}
	
	
	
	@Override
	public ArrayList<String> infosSubscriber(String username, String pwdSubs) throws ExistingSubscriberException {
		
	}

	

	@Override
	public void betOnWinner(long tokens, String competitionName,
			Competitor winner, String username, String pwdSubs) throws ExistingSubscriberException, ExistingCompetitionException, CompetitionException, SubscriberException, BadParametersException, AuthenticationException {
	
		subs = findSubscriberByUsername(username);
		comp = findCompetitionByName(competitionName);
		
		//check if the subs and comp exists
		if(subs == null||!subs.getPassword().equals(pwdSubs))
		    throw new ExistingSubscriberException("(username,password) is not correct");
		
		
		if(comp == null)
			throw new ExistingCompetitionException("This name doesn't match any competition");
		
		//Check if the competition date has expired, using isInThePast method from MyCalendar
		Calendar enddate = comp.getEndDate();
		MyCalendar d = new MyCalendar(enddate.get(Calendar.YEAR),enddate.get(Calendar.MONTH),enddate.get(Calendar.DAY_OF_MONTH));
		if(d.isInThePast())
			throw new CompetitionException("Competition is finished");
		
		//Check if the bet amount is inferior (or equals) to the value of the subsTokens of the subscriber
		long subsTokens = subs.gettokens();
		if(subsTokens < tokens)
			throw new SubscriberException("Not enough tokens");
		
		//Check if the registered bet is superior than 0 ,feels bad
		if(tokens <= 0) 
			throw new BadParametersException("The bet amount is less (or equals) to 0");	
			
		//Check if winner competitor exists
		competitors = comp.getListCompetitors();
		if(findCompetitor(winner) == null) 
			throw new CompetitionException("winner competitor doesn't exist for the person competition");	
						
		//Create the winner bet and add it to the list of winner bets 
		Bet winnerBet = new Bet(0000,tokens,winner,winner,winner, comp, subs,0);
		listwinnerbets = comp.getListBetsOnWinner();
		listwinnerbets.add(winnerBet);
		System.out.println(listwinnerbets);
		
		//Debit the subsTokens of the bettor
		debitSubscriber(username, tokens, this.managerPassword);	
		
	}
	
  
	@Override
	public void betOnPodium(long tokens, String competitionName,
			Competitor winner, Competitor second, Competitor third,
			String username, String pwdSubs) throws ExistingSubscriberException, ExistingCompetitionException, CompetitionException, SubscriberException, BadParametersException, AuthenticationException {
			
		Subscriber subs = findSubscriberByUsername(username);
		Competition comp = findCompetitionByName(competitionName);
		
		//Check if the (username,password) exists
		if(subs == null) 
		    throw new ExistingSubscriberException("This username doesn't match any subscriber");

		if(!subs.getPassword().equals(pwdSubs))
			throw new AuthenticationException("The password is not correct");
		
			
		//Check if the competition exists
		if(comp == null)
			throw new ExistingCompetitionException("This name doesn't match any competition");

		//Check if the competition date has expired
		Calendar end = comp.getEndDate();
		MyCalendar d = new MyCalendar(end.get(Calendar.YEAR),end.get(Calendar.MONTH),end.get(Calendar.DAY_OF_MONTH));
		if(d.isInThePast())
			throw new CompetitionException("Competition is finished");
		
		//Check if the bet amount is inferior (or equals) to the value of the subsTokens of the subscriber
		long subsTokens = subs.gettokens();
		if(subsTokens < tokens)
			throw new SubscriberException("Not enough tokens");
		
		//Check if the  bet amount is greater than 0
		if(tokens <= 0) 
			throw new BadParametersException("The bet amount is less (or equals) to 0");
		
		competitors = comp.getListCompetitors();
	
		//Check if the bettor is a competitor of the competition
		String sFirstname = subs.getFirstname();
		String sLastname = subs.getLastname();
		for(Competitor c : competitors) {
			if(c instanceof PersonCompetitor) {
				PersonCompetitor pc =(PersonCompetitor) c;
				if(pc.getFirstName().equals(sFirstname) && pc.getLastName().equals(sLastname) ) {
					throw new CompetitionException("The bettor is a competitor");
				}
			}	
	    }
	
		//Check if first, second and third competitors exist
		if(findCompetitor(winner) == null || findCompetitor(second) == null || findCompetitor(third) == null) 
			throw new CompetitionException("One or more of the competitors doesn't exist for team the competition");
		
		//Create the objet podium bet and add it to the list of podium bets 	
		Bet podiumBet = new Bet(0000,tokens,winner,second,third, comp, subs,1);
		listpodiumbets = comp.getListBetsOnPodium();
		listpodiumbets.add(podiumBet);
		
		//Debit the subsTokens of the bettor
		debitSubscriber(username, tokens, managerPassword);			
	}	
    
	
	
	

	
}
