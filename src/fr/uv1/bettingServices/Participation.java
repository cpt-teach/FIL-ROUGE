package fr.uv1.bettingServices;

import java.io.Serializable;

import fr.uv1.bettingServices.exceptions.AuthenticationException;
import fr.uv1.bettingServices.exceptions.BadParametersException;
import fr.uv1.utils.*;

public class Participation {
	protected int participation_id;
	private int team_id;
	private int comp_id;
	private boolean ifteam;
//waiting competitor and competition
	public Participation( int participation_id,int team_id,int comp_id, boolean ifteam){
		this.participation_id=participation_id;
		this.team_id=team_id;
		this.comp_id=comp_id;
		this.ifteam=ifteam;
	}
	
//getter	
	public int getParticipationId(){
		return this.participation_id;
	}
	public int getTeamId(){
		return this.team_id;
	}
	public int getCompetitionId(){
		return this.comp_id;}

	public boolean getIfTeam(){
		return this.ifteam;
	}
	
//Setter
	public void setParticipationId(int participation_id){
		this.participation_id=participation_id;
	}
	public void setTeamId(int team_id){
		this.team_id=team_id;
	}
	public void setCompetitionId(int comp_id){
		this.comp_id=comp_id;}

	public void setIfTeam(boolean ifteam){
		this.ifteam=ifteam;

	}
}