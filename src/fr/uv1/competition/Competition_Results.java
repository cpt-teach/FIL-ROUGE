package fr.uv1.competition;
import fr.uv1.bd.selectBD;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
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
		
	public int IsPodium(Participation id){
	if(id.getId()==podium1.getId())  //getId est une méthode de la classe Participation
		return 1;
	else if(id.getId()==podium2.getId())  //getId est une méthode de la classe Participation
		return 2;
	else if(id.getId()==podium3.getId())  //getId est une méthode de la classe Participation
		return 3;
	else
		return -1;
	}
	public static java.util.ArrayList<String> consultResultsCompetition(java.lang.String competition)
                                                          throws SQLException{
               	ArrayList<String> List = new ArrayList<String>(); // ArrayList<Competitor> List = new ArrayList<Competitor>;
	  	//Check if all the parameters are valid
		//competition
		competition_object = getCompetitionByName(competition); // doit être impélmentée par le lot1
		if(competition_object == null)
			throw new ExistingCompetitionException("The competition named does not exist");
		
        List = ResultCompetition (competition_object);
		} }
		return List;
		}

		competition = getCompetitionByName(competitionName); // methode de la classe competition
		
		
		//Vérifier que les competitors
		competitors = competition.getListCompetitors();
		if(findCompetitor(winner) == null || findCompetitor(second) == null || findCompetitor(third)== null) // find competitor est une méthode de competitors
			printf("Il ya une erreur sur les competitors");
	
			
		// Calcul des jetons et créditer les winners.
	  		  	
	}

	
