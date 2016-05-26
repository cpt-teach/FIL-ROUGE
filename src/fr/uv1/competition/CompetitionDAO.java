package fr.uv1.competition;

import java.util.ArrayList;
import java.util.List;

import fr.uv1.bettingServices.Exceptions.ExistingCompetitionException;

public class CompetitionDAO {
	protected static List<Competition> currentCompetitions = new ArrayList<Competition>();

	// Static methods
	
	public static List<Competition> listCompetitions() {
		return currentCompetitions;
	}
	
	public static void addCompetition(Competition competition) throws ExistingCompetitionException {
		if (currentCompetitions.contains(competition)){ throw new ExistingCompetitionException("Competition already exists");	}
		else { currentCompetitions.add(competition); }
	}
	
	public static void cancelCompetition(Competition competition) throws ExistingCompetitionException {
		if (! currentCompetitions.contains(competition)){ throw new ExistingCompetitionException("Competition already canceled or finished "); }
		else { currentCompetitions.remove(competition); }
	}
}
