package fr.uv1.competition;
import fr.uv1.bd.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
import fr.uv1.utils.MyCalendar;
import fr.uv1.competition.Exceptions.BadParametersException;
import fr.uv1.competition.Exceptions.ExistingCompetitionException;
import fr.uv1.competition.Exceptions.ExistingCompetitorException;
import fr.uv1.competition.Exceptions.NotATeamException;


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
	public void setSport(String sport){
		this.sport=sport;
	}

	public static Competition getCompetitionByName(String Competition_name)throws SQLException, BadParametersException, ExistingCompetitorException, ExistingCompetitionException,NotATeamException{ // TODO in DAO
		Competition competition = null;
		ResultSet result = selectBD.select("postgres","postgres","jdbc:postgresql://localhost:54321/Test", "SELECT * FROM competition WHERE name LIKE"+Competition_name+";");
		while(result.next()){
			ResultSet result1 = selectBD.select("postgres","postgres","jdbc:postgresql://localhost:54321/Test", "SELECT * FROM participation WHERE comp_id LIKE"+result.getString(1)+";");
			while(result1.next()){
				int isteam = new Integer(32);
				isteam = Integer.parseInt(result1.getString(4));
				if(isteam==1){
					List<Competitor> liste_competitor = new ArrayList<Competitor>();
					ResultSet result2 = selectBD.select("postgres","postgres","jdbc:postgresql://localhost:54321/Test", "SELECT * FROM team WHERE team_id LIKE"+result1.getString(2)+";");
					while(result2.next()){
						ResultSet result4 = selectBD.select("postgres","postgres","jdbc:postgresql://localhost:54321/Test", "SELECT * FROM inteam WHERE team_id LIKE"+result2.getString(1)+";");
						while(result4.next()){	
							
							
							Competitor competitor_team = new Team(result2.getString(2));
							ResultSet result3 = selectBD.select("postgres","postgres","jdbc:postgresql://localhost:54321/Test", "SELECT * FROM individual WHERE indi_id LIKE"+result4.getString(1)+";");
							while(result3.next()){
								Competitor member = new Individual(result3.getString(2),result3.getString(3),result3.getString(4));
								competitor_team.addMember(member);
							}
						liste_competitor.add(competitor_team);
					
					}
				}
					MyCalendar endDate = MyCalendar.fromString(result.getString(4));
					competition = new Competition(result.getString(2), result.getString(3), liste_competitor, endDate);
					
				}
				else if(isteam==0){
					List<Competitor> liste_competitor = new ArrayList<Competitor>();
					ResultSet result2 = selectBD.select("postgres","postgres","jdbc:postgresql://localhost:54321/Test", "SELECT * FROM individual WHERE indi_id LIKE"+result1.getString(1)+";");
					while(result2.next()){
						Competitor competitor_indi = new Individual(result2.getString(2),result2.getString(3),result2.getString(4));
						liste_competitor.add(competitor_indi);	
		}
					MyCalendar endDate = MyCalendar.fromString(result.getString(4));
					competition = new Competition(result.getString(2), result.getString(3), liste_competitor, endDate);
				
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
