package fr.uv1.competition;
import fr.uv1.bd.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import fr.uv1.utils.MyCalendar;
import fr.uv1.competition.Exceptions.BadParametersException;
import fr.uv1.competition.Exceptions.ExistingCompetitionException;
import fr.uv1.competition.Exceptions.ExistingCompetitorException;

public class Competition {
	protected String name;
	protected String sport;
	protected MyCalendar endDate;
	protected List<Competitor> competitors;
	
	// Constructors 
	// The list of competitors is not given (new competition)
	
	public Competition(String name, String sport, MyCalendar endDate) throws BadParametersException, ExistingCompetitionException {
		this.setName(name);
		this.sport = sport;
		this.endDate = endDate;
		this.competitors = new ArrayList<Competitor>();
		CompetitionDAO.addCompetition(this);
	}
	// The list of competitors is given
	public Competition(String name, String sport, List<Competitor> competitors, MyCalendar endDate) throws BadParametersException, ExistingCompetitionException {
		this.setName(name);
		this.sport = sport;
		this.endDate = endDate;
		this.competitors = competitors;
		CompetitionDAO.addCompetition(this);
	}
	
	//Basic getters and setters
	
	public String getName() {
		return name;
	}

	public void setName(String name) throws BadParametersException {
		if (!Utilitary.isValidName(name)) { throw new BadParametersException("invalid name");}
		this.name = name;
	}

	public String getSport() {
		return sport;
	}

	public static Competition getCompetitionByName(String Competition_name){ // TODO in DAO
		ResultSet result = selectBD.select("postgres","postgres","jdbc:postgresql://localhost:54321/Test", "SELECT * FROM competition WHERE name LIKE"+Competition_name+";");
		while(result.next()){
				Competition competition = new Competition(getString(2),getString(3));
	
				ResultSet result1 = selectBD.edit("postgres","postgres","jdbc:postgresql://localhost:54321/Test", "SELECT * FROM participation WHERE comp_id LIKE"+getString(1)+";");
			while(result1.next()){	
					ResultSet result2 = selectBD.select("postgres","postgres","jdbc:postgresql://localhost:54321/Test", "SELECT * FROM individual WHERE team_id LIKE"++";");
						while(result2.next()){
							MyCalendar calendar = MyCalendar.fromString(getString(3));
							competition.addCompetitor(result1.getString(1),getString(2),calendar);
					}
		}
		}
		return competition;
}

	
	//Main methods of the class
	
	
	// Adds a competitor to this competition
	public void addCompetitor(Competitor competitor) throws ExistingCompetitorException {
		if (competitors.contains(competitor)){ throw new ExistingCompetitorException();	}
		else { competitors.add(competitor); }
	}
	
	// Deletes a competitor from this competition
	public void deleteCompetitor(Competitor competitor) throws ExistingCompetitorException {
		if (! competitors.contains(competitor)){ throw new ExistingCompetitorException(); }
		else { competitors.remove(competitor); }
	}
	
	// Returns the list of competitors in this competition
	public List<Competitor> listCompetitors() {
		return competitors;
	}
	
	// Returns True if the competition is closed, False if it's still running
	public boolean isClosed() {
		return this.endDate.isInThePast();
	}
	
	// Returns True if the competition contains the competitor, False otherwise.
	public boolean checkCompetitor(Competitor competitor) {
		return competitors.contains(competitor);
	}

	
	

	
}
