package fr.uv1.competition;
import java.util.List;

import fr.uv1.competition.Exceptions.BadParametersException;
import fr.uv1.competition.Exceptions.ExistingCompetitorException;
import fr.uv1.competition.Exceptions.NotATeamException;


public interface Competitor {
	public abstract void addMember(Competitor member) throws NotATeamException, ExistingCompetitorException, BadParametersException;
	public abstract void deleteMember(Competitor member) throws NotATeamException, BadParametersException, ExistingCompetitorException;
	public abstract List<Competitor> listMembers() throws NotATeamException;
	public abstract boolean hasValidName();
	public abstract boolean isTeam();
}
