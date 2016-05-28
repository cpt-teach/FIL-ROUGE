package fr.uv1.competition;
import fr.uv1.bettingServices.*;

public class Competition_Results {
	
	private Participation podium1;
	private Participation podium2;
	private Participation podium3;
	private int Competition_Id;
	
	public Competition_Results(Participation podium1, Participation podium2, Participation podium3, int Competition_Id){
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
		
	public int rankInPodium(Participation participation){ 
		
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
