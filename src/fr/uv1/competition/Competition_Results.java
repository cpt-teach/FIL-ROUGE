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
		
		//Definirtion des setteurs
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
		//Définirtion des getteurs
		
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
	
		//Autres méthodes
		
	public int IsPodium(Participation id){ // return the rank of the participatioon in the podium or -1 if not in podium
	if(id.getParticipationId()==podium1.getParticipationId())  //getId est une méthode de la classe Participation
		return 1;
	else if(id.getParticipationId()==podium2.getParticipationId())  //getId est une méthode de la classe Participation
		return 2;
	else if(id.getParticipationId()==podium3.getParticipationId())  //getId est une méthode de la classe Participation
		return 3;
	else
		return -1;
	}
}
