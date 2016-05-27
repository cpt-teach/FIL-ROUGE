package fr.uv1.competition;
import fr.uv1.utils.*;


import java.util.List;


import java.sql.SQLException;
import fr.uv1.bettingServices.Exceptions.*;


public class Individual implements Competitor {
	protected int id;
	protected String lastName;
	protected String firstName;
	protected MyCalendar bornDate;

	public Individual(String lastName, String firstName, String bornDate) 
			throws BadParametersException, ExistingCompetitorException, SQLException {
		this.id = IndividualDAO.getIdMax();
		this.setLastName(lastName);
		this.setFirstName(firstName);
		
		MyCalendar calendar = MyCalendar.fromString(bornDate);
		if (! Utilitary.isValidDate(calendar)) {throw new BadParametersException("Invalid Parameter(borndate)"); }
		this.bornDate = calendar;
		IndividualDAO.persist(this);
	}

	// Getters and setters
	
	public String getLastName() {
		return lastName;
	}
	
	public int getId() {
		return id;
	}

	public void setLastName(String lastName) throws BadParametersException, SQLException {
		if (! Utilitary.isValidName(lastName)) {throw new BadParametersException("Invalid Parameter (lastName)"); }
		this.lastName = lastName;
		IndividualDAO.update(this);
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) throws BadParametersException, SQLException {
		if (! Utilitary.isValidName(firstName)) {throw new BadParametersException("Invalid Parameter (firstName)"); }
		this.firstName = firstName;
		IndividualDAO.update(this);
	}

	public MyCalendar getBornDate() {
		return bornDate;
	}
	// Setter borndate added
	public void setBornDate(String bornDate)throws SQLException {
		this.bornDate=MyCalendar.fromString(bornDate);
		IndividualDAO.update(this);
	}
	
	// Inherited from Competitor
	
	public void addMember(Competitor member) throws NotATeamException {
		throw new NotATeamException();
	}

	public void deleteMember(Competitor member) throws NotATeamException {
		throw new NotATeamException();
	}
	

	public boolean hasValidName() {
		return Utilitary.isValidName(lastName) && Utilitary.isValidName(firstName);
	}

	public boolean isTeam() {
		return false;
	}

	public List<Competitor> listMembers() throws NotATeamException {
		throw new NotATeamException();
	}
	//new functions/methods
	
	public String getName(){
		return this.lastName + " " + this.firstName; 
	}
	
	public int getIdbd()throws SQLException {
		int id = IndividualDAO.selectIndividualId(this.lastName,this.firstName,this.bornDate);
		return id;
		
	}
}
