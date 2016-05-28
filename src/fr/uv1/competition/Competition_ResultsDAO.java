package fr.uv1.competition;


import fr.uv1.bd.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import fr.uv1.bettingServices.Exceptions.*;

public class Competition_ResultsDAO {
	//protected static List<Competition> currentCompetitions = new ArrayList<Competition>();
	private static String url="jdbc:postgresql://localhost:5433/tests";
	private static String user="postgres";
	private static String password="postgres";
	
	//Constructor
	public Competition_ResultsDAO (){
		
	}
	
	public void persist(Competition_Results CompetitionResults) throws SQLException {
		String request;
		request = "insert into competition_results(id_comp,podium1,podium2,podium3)  values (" +
				 +CompetitionResults.getCompetition()+
				"," +CompetitionResults.getFirst().getParticipationId()+
				"," +CompetitionResults.getSecond().getParticipationId()+
				"," +CompetitionResults.getThird().getParticipationId()+");";
		editBD.edit(user,password,url, request );}
	
	
	public static void update(Competition_Results CompetitionResults) throws SQLException { 
	    // Get a database connection.yobvh
	    // Update the competition.
		String request;
		request = "update competition_results set (id_comp,podium1,podium2,podium3)  values (" +
				+CompetitionResults.getCompetition()+
				"," +CompetitionResults.getFirst().getParticipationId()+
				"," +CompetitionResults.getSecond().getParticipationId()+
				"," +CompetitionResults.getThird().getParticipationId()+");";
		editBD.edit(user,password,url, request );}
	
	
	public int[] ResultCompetition(Competition competition) throws SQLException {
	    String request = "SELECT * FROM competition_results WHERE id_comp = "+ competition.getId() +";";
	    ResultSet result = selectBD.select(user,password,url,request );
	    int[] list_Result = new int[3];
		result.next();		
	    list_Result [0] = result.getInt(2);
		list_Result [1] = result.getInt(3); 
		list_Result [2] = result.getInt(4);
		return list_Result;			
		}	
	
	public int[] ParticipationToCompetitor(int[] Ids) throws SQLException {
	    String request1 = "SELECT * FROM participation WHERE participation_id = "+ Ids[0] +";";
	    String request2 = "SELECT * FROM participation WHERE participation_id = "+ Ids[1] +";";
	    String request3 = "SELECT * FROM participation WHERE participation_id = "+ Ids[2] +";";
	    ResultSet result1 = selectBD.select(user,password,url,request1 );
	    ResultSet result2 = selectBD.select(user,password,url,request2 );
	    ResultSet result3 = selectBD.select(user,password,url,request3 );
		result1.next();
		result2.next();
		result3.next();
	    int[] list_Result = new int[3];	
	    list_Result [0] = result1.getInt(1);
		list_Result [1] = result2.getInt(1); 
		list_Result [2] = result3.getInt(1);
		return list_Result;			
		}	
	
	public Competitor getCompetitorById(int Id) throws SQLException, BadParametersException, ExistingCompetitorException, ExistingCompetitionException {
		String requestTeam = "SELECT * FROM team WHERE team_id = "+ Id +";";
		String requestIndividual = "SELECT * FROM individual WHERE indi_id = "+ Id +";";
	    ResultSet resultTeam = selectBD.select(user,password,url,requestTeam );
	    ResultSet resultIndividual = selectBD.select(user,password,url,requestIndividual );
	    Competitor test = null;
	    if (resultTeam.next()) {
	    	test = new Team(resultTeam.getString(2));
	    }
	    if (resultIndividual.next()) {
	    	test = new Individual(resultIndividual.getString(2),resultIndividual.getString(3),resultIndividual.getString(4));
	    }
	    return test;
	}
}
	

