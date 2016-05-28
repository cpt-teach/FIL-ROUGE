package fr.uv1.competition;
import fr.uv1.bettingServices.*;
import fr.uv1.bettingServices.Exceptions.*;

public class Competition_Results {
	
	private Participation podium1;
	private Participation podium2;
	private Participation podium3;
	private int Competition_Id;
	
	public Competition_Results(Participation podium1, 
							   Participation podium2, 
							   Participation podium3, 
							   int Competition_Id) throws BadParametersException {
		if (podium1 == null || podium2 == null || podium3 == null) {
			throw new BadParametersException("The Results can't be null");
		}
		if (podium1.getParticipationId() == podium2.getParticipationId() ||
			podium1.getParticipationId() == podium3.getParticipationId() ||
			podium2.getParticipationId() == podium3.getParticipationId()) {
			throw new BadParametersException("The Podium can't be filled with the same");
		}
		this.setFirst(podium1);
		this.setSecond(podium2);
		this.setThird(podium3);
		this.setCompetition(Competition_Id);
		}
		
		//Definition des setteurs
	public void setFirst(Participation first){
		this.podium1 = first;
	} 
	
	public void setSecond(Participation second){
		this.podium2 = second;
	} 
	
	public void setThird(Participation third){
		this.podium3 = third;
	} 
	
	public void setCompetition(int Competition_Id){
		this.Competition_Id = Competition_Id;
	} 
		//Definition des getteurs
		
	public Participation getFirst(){
		return this.podium1;
	}
	
	public Participation getSecond(){
		return this.podium2;
	}
	
	public Participation getThird(){
		return this.podium3;
	}
	
	public int getCompetition(){
		return this.Competition_Id;
	}
	
// Other methods
	// return the rank of the participation in the podium or -1 if not in podium
		
	public int rankInPodium(Participation participation) throws BadParametersException { 
		if (participation == null) {
			throw new BadParametersException(" Give a participation first ");
		}
		
		if(participation.getParticipationId() == podium1.getParticipationId())  
			return 1;
		else if(participation.getParticipationId()== podium2.getParticipationId()) 
			return 2;
		else if(participation.getParticipationId()==podium3.getParticipationId())  
			return 3;
		else
			return -1;
		}
}
