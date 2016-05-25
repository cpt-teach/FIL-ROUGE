package fr.uv1.competition;
import java.util.ArrayList;
import java.util.List;

import fr.uv1.competition.Exceptions.BadParametersException;
import fr.uv1.competition.Exceptions.ExistingCompetitorException;


public class Team implements Competitor{
	protected String name;
	protected List<Competitor> members;

	
	public Team(String name) throws BadParametersException, ExistingCompetitorException {
		this.setName(name);
		this.members = new ArrayList <Competitor>();
		CompetitorDAO.addCompetitor(this);
	}
	
	// Getters and setters
	public String getName() {
		return name;
	}

	public void setName(String name) throws BadParametersException {
		if (! Utilitary.isValidName(name)) {throw new BadParametersException("Invalid Parameter Team Name"); }
		this.name = name;
	}
	
	
	// Inherited from Competitor
	
	public void addMember(Competitor member) throws ExistingCompetitorException, BadParametersException {
		if (members.contains(member)){ throw new ExistingCompetitorException();	}
		else if (member.isTeam()){ throw new BadParametersException("Invalid Parameter : member"); }
		else { members.add(member); }
	}

	public void deleteMember(Competitor member) throws BadParametersException, ExistingCompetitorException {
		if ( member.isTeam()) { throw new BadParametersException("Invalid Parameter : member"); }
		else if (! members.contains(member)){ throw new ExistingCompetitorException(); }
		else { members.remove(member); }
	}
	
	public List<Competitor> listMembers() {
		return members;
	}

	public boolean hasValidName() {
		// We ensured that the name is valid thanks to setName (called in the constructor)
		return true;
	}

	public boolean isTeam() {
		return true;
	}
	
}
