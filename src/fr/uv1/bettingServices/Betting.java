package fr.uv1.bettingServices.Betting;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import fr.uv1.bettingServices.Compet.Competition;
import fr.uv1.bettingServices.Compet.Competitor;
import fr.uv1.bettingServices.Compet.PersonCompetitor;
import fr.uv1.bettingServices.Compet.TeamCompetitor;
import fr.uv1.bettingServices.Exceptions.AuthenticationException;
import fr.uv1.bettingServices.Exceptions.BadParametersException;
import fr.uv1.bettingServices.Exceptions.CompetitionException;
import fr.uv1.bettingServices.Exceptions.ExistingCompetitionException;
import fr.uv1.bettingServices.Exceptions.ExistingSubscriberException;
import fr.uv1.bettingServices.Exceptions.SubscriberException;

public interface Betting {

	void authenticateMngr(String managerPassword) throws AuthenticationException;
		
	String subscribe(String lastname, String firstname, String username, String borndate, String managerPassword) throws SubscriberException, ExistingSubscriberException, AuthenticationException, BadParametersException;
	
	long unsubscribe(String username, String managerPassword) throws ExistingSubscriberException, AuthenticationException;
	
	List<List<String>> listSubscribers(String managerPassword) throws AuthenticationException;

	void addCompetition(String competitionName, Calendar endDate, ArrayList<Competitor> listCompetitors, String managerPassword) throws CompetitionException, ExistingCompetitionException, AuthenticationException;

	void removeCompetition(String competitionName, String managerPassword) throws ExistingCompetitionException, AuthenticationException;
	
	void addCompetitor(String competitionName, Competitor competitor, String managerPassword) throws CompetitionException, ExistingCompetitionException, AuthenticationException;
	
	void deleteCompetitor(String competitionName, Competitor competitor, String managerPassword) throws AuthenticationException, ExistingCompetitionException, CompetitionException;
	
	PersonCompetitor createPersonCompetitor(String lastName, String firstName, String bornDate, String managerPassword) throws AuthenticationException;
	
	TeamCompetitor createTeamCompetitor(String teamName, String managerPassword) throws AuthenticationException;

	void creditSubscriber(String username, long tokens, String managerPassword) throws AuthenticationException, ExistingSubscriberException, BadParametersException;

	void debitSubscriber(String username, long tokens, String managerPassword) throws SubscriberException, AuthenticationException, ExistingSubscriberException, BadParametersException;

	void settleWinner(String competitionName, Competitor winner, String managerPassword) throws ExistingCompetitionException, CompetitionException, AuthenticationException, ExistingSubscriberException, BadParametersException;

	void settlePodium(String competitionName, Competitor first, Competitor second,
			Competitor third, String managerPassword) throws ExistingCompetitionException, CompetitionException, AuthenticationException, ExistingSubscriberException, BadParametersException;
	
	void betOnWinner(long tokens, String competitionName, Competitor winner,
			String username, String pwdSubs) throws ExistingSubscriberException, ExistingCompetitionException, CompetitionException, SubscriberException, BadParametersException, AuthenticationException;
	
	void betOnPodium(long tokens, String competitionName, Competitor first,
			Competitor second, Competitor third, String username, String pwdSubs) throws ExistingSubscriberException, ExistingCompetitionException, CompetitionException, SubscriberException, BadParametersException, AuthenticationException;
	
	void changeSubsPwd(String username, String newPwd, String currentPwd) throws ExistingSubscriberException;

	ArrayList<String> infosSubscriber(String username, String pwdSubs) throws ExistingSubscriberException;
	
	void deleteBet(String competitionName, String username, int betNumber,
			String managerPassword) throws AuthenticationException, ExistingSubscriberException, BadParametersException;
	
	void consultBets(String competitionName) throws AuthenticationException, ExistingCompetitionException;

	ArrayList<Competition> listCompetitions();
	
}