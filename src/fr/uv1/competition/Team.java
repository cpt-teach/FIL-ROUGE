package fr.uv1.competition;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import fr.uv1.bettingServices.Exceptions.*;




public class Team implements Competitor{
	protected int id;
	protected String name;
	protected String sport;
	protected List<Competitor> members;

	
	public Team(String name) 
			throws BadParametersException, ExistingCompetitorException, ExistingCompetitionException, SQLException {
		this.id = TeamDAO.getIdMax();
		this.setName(name);
		this.setSport(sport);
		this.members = new ArrayList <Competitor>();
		TeamDAO.persist(this);
	}
	
	// Getters and setters
	public String getName() {
		return name;
	}
	
	public String getSport() {
		return sport;
	}

	public void setSport(String sport) throws SQLException {
		this.sport = sport;
		TeamDAO.update(this);
	}

	public int getId() {
		return id;
	}

	public void setName(String name) throws BadParametersException, SQLException {
		if (! Utilitary.isValidName(name)) {throw new BadParametersException("Invalid Name"); }
		this.name = name;
		TeamDAO.update(this);
	}
	
	
	
	// Inherited from Competitor
	
	public void addMember(Competitor member) throws ExistingCompetitorException, BadParametersException, SQLException {
		if (members.contains(member)){ throw new ExistingCompetitorException();	}
		else if (member.isTeam()){ throw new BadParametersException("Invalid Parameter : member"); }
		else { 
			members.add(member); 
			TeamDAO.addIndividual(member, this);
		}
	}

	public void deleteMember(Competitor member) 
			throws BadParametersException, ExistingCompetitorException, SQLException {
		if ( member.isTeam()) { throw new BadParametersException("Invalid Parameter : member"); }
		else if (! members.contains(member)){ throw new ExistingCompetitorException(); }
		else { 
			members.remove(member); 
			TeamDAO.delIndividual(member.getId(), this);
		}
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
	public int getIdbd() throws SQLException {
		int id = new Integer(32);
		id = TeamDAO.selectTeamId(this.name);
	return id;	
	}
	
}
