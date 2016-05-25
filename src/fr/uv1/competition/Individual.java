package Competition;
import java.util.List;

import Exceptions.BadParametersException;
import Exceptions.ExistingCompetitorException;
import Exceptions.NotATeamException;


public class Individual implements Competitor {
	protected String lastName;
	protected String firstName;
	protected MyCalendar bornDate;

	public Individual(String lastName, String firstName, String bornDate) throws BadParametersException, ExistingCompetitorException {
		this.setLastName(lastName);
		this.setFirstName(firstName);
		
		MyCalendar calendar = MyCalendar.fromString(bornDate);
		if (! Utilitary.isValidDate(calendar)) {throw new BadParametersException(); }
		this.bornDate = calendar;
		CompetitorDAO.addCompetitor(this);
	}

	// Getters and setters
	
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) throws BadParametersException {
		if (! Utilitary.isValidName(lastName)) {throw new BadParametersException(); }
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) throws BadParametersException {
		if (! Utilitary.isValidName(firstName)) {throw new BadParametersException(); }
		this.firstName = firstName;
	}

	public MyCalendar getBornDate() {
		return bornDate;
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

}
