package fr.uv1.competition;
import java.util.List;

import fr.uv1.bettingServices.Exceptions.*;
import java.sql.SQLException;

public interface Competitor {
	public void addMember(Competitor member) throws NotATeamException, ExistingCompetitorException, BadParametersException, SQLException;
	public void deleteMember(Competitor member) throws NotATeamException, BadParametersException, ExistingCompetitorException, SQLException;
	public List<Competitor> listMembers() throws NotATeamException;
	public boolean hasValidName();
	public boolean isTeam();
	public int getId() throws SQLException;
}
