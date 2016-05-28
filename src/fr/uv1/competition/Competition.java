package fr.uv1.competition;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import fr.uv1.utils.MyCalendar;
import fr.uv1.bettingServices.Exceptions.*;


public class Competition {
	protected int id;
	protected String name;
	protected String sport;
	protected MyCalendar endDate;
	protected List<Competitor> competitors;
	
	// Constructors 
	// The list of competitors is not given (new competition)
	
	public Competition(String name, String sport, MyCalendar endDate) throws BadParametersException, ExistingCompetitionException, SQLException {
		this.id = CompetitionDAO.getIdMax(); 
		this.setName(name);
		this.sport = sport;
		this.endDate = endDate;
		this.competitors = new ArrayList<Competitor>();
		this.addToCompetition();
		}
	
	// The list of competitors is given
	public Competition(String name, String sport, List<Competitor> competitors, MyCalendar endDate) throws BadParametersException, ExistingCompetitionException, SQLException {
		this.setName(name);
		this.sport = sport;
		this.endDate = endDate;
		this.competitors = competitors;
		this.addToCompetition();
	}
	
	
	// DAO interactions
	
	
	public  void addToCompetition() throws ExistingCompetitionException, SQLException {
		CompetitionDAO.persist(this);
	}
	
	public void cancelThisCompetition(Competition competition) throws ExistingCompetitionException, SQLException {
		CompetitionDAO.delete(this);
	}
	
	
	//Basic getters and setters
	
	public String getName() {
		return name;
	}
	
	/*public java.util.Collection<Competitor>  getCompetitors() {
		return this.competitors;
	}*/
	
	public int getIdbd() throws SQLException {
		int id = CompetitionDAO.selectCompetitionId(this.name,this.sport,this.endDate);
		return id;
	}
	
	public int getId() {
		return id;
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
	public List<Competitor> getCompetitors() {
		return competitors;
	}
	
	
	
	//Main methods of the class
	
	
	// Returns True if the competition is closed, False if it's still running
	public boolean isClosed() {
		return this.endDate.isInThePast();
	}
	
	// Returns True if the competition contains the competitor, False otherwise.
	public boolean checkCompetitor(Competitor competitor) {
		return competitors.contains(competitor);
	}

	
	/*public static void main (String [] arg ) throws SQLException, BadParametersException, ExistingCompetitorException, ExistingCompetitionException,NotATeamException {
		Competition comp = getCompetitionByName("garros");
		System.out.println(comp.getName());
		System.out.println(comp.getId());
	}*/

	
}
