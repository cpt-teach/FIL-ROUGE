package Competition;
import java.util.List;

import Exceptions.BadParametersException;
import Exceptions.ExistingCompetitorException;
import Exceptions.NotATeamException;


public interface Competitor {
	public abstract void addMember(Competitor member) throws NotATeamException, ExistingCompetitorException, BadParametersException;
	public abstract void deleteMember(Competitor member) throws NotATeamException, BadParametersException, ExistingCompetitorException;
	public abstract List<Competitor> listMembers() throws NotATeamException;
	public abstract boolean hasValidName();
	public abstract boolean isTeam();
}
