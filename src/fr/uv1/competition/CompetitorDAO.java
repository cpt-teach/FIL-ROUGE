package Competition;

import java.util.ArrayList;
import java.util.List;

import Exceptions.ExistingCompetitorException;

public class CompetitorDAO {
	static List<Competitor> competitors = new ArrayList<Competitor>();
	
	// Static methods
	
	public static List<Competitor> listCompetitors() {
		return competitors;
	}
		
	public static void addCompetitor(Competitor competitor) throws ExistingCompetitorException {
		if (competitors.contains(competitor)){ throw new ExistingCompetitorException();	}
		else { competitors.add(competitor); }
	}
		
	public static void deleteCompetitor(Competitor competitor) throws ExistingCompetitorException {
		if (! competitors.contains(competitor)){ throw new ExistingCompetitorException(); }
		else { competitors.remove(competitor); }
	}
}
