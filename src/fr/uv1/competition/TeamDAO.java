package fr.uv1.competition;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import fr.uv1.bd.*;



public class TeamDAO {
	private String url = "jdbc:postgresql://localhost:5433/tests";
	private String user = "postgres";
	private String password ="postgres";
	
	public TeamDAO() {
		
	}
	
	
	//-----------------------------------------------------------------------------------------------------------

	
	public static int getIdMax() throws SQLException{
		TeamDAO dao = new TeamDAO();
    	ResultSet result=selectBD.select(dao.user,dao.password,dao.url, 
	    		"SELECT max(id) FROM team;");
    	result.next();
    	return (result.getInt(1));
     	}
	
	public static void addIndividual(Competitor individual, Team team) throws SQLException{
		TeamDAO dao = new TeamDAO();
		String request;
		request = "insert into inteam(indi_id, team_id) values ("
				+individual.getId()+","+team.getId()+";";
		editBD.edit(dao.user,dao.password,dao.url, request );
	}
	
	public static void delIndividual(int indi_id, Team team) {
		TeamDAO dao = new TeamDAO();
		String request;
		request = "DELETE FROM inteam where indi_id ="+indi_id+"AND team_id="+team.getId()+";";
		editBD.edit(dao.user,dao.password,dao.url, request );
	}
	
	
	/**
	 * Persist (store) a team in the data base.
	 * 
	 * @param team the team to be stored.
	 * @throws SQLException
	 */
	public static void persist(Team team) throws SQLException {
		TeamDAO dao = new TeamDAO();

		String request;
		request = "insert into team(team_id,name,sport)  values (" +
				 team.getId()+"," +team.getName()+","+team.getSport()+";";
		editBD.edit(dao.user,dao.password,dao.url, request );
		for (Competitor i : team.listMembers()) {
			addIndividual(i, team);
		}
	}
	
	
	/**
	   * Delete from the database a specific team.
	   * 
	   * @param team the team to be deleted.
	   * @throws SQLException
	   */
	
	public static void delete(Team team) throws SQLException {
		TeamDAO dao = new TeamDAO();
	    editBD.edit(dao.user,dao.password,dao.url, 
	    		"DELETE FROM team where team_id="+team.getId()+";");
	    String request;
		request = "DELETE FROM inteam where team_id="+team.getId()+";";
		editBD.edit(dao.user,dao.password,dao.url, request );
	  }
	    
	    
	public static Team  getTeam(int id) throws SQLException{
		TeamDAO dao = new TeamDAO();
		ResultSet result= selectBD.select(dao.user,dao.password,dao.url, 
		    		"SELECT * FROM team where team_id="+id+";");
		result.next();
		return (Team) result;
	}

	public static void update(Team team) throws SQLException {
		TeamDAO dao = new TeamDAO();
		  
	    // Get a database connection.yobvh
	    // Update the individual.
		String request;
		request = "update team set (team_id,name,sport)  values (" + team.getId()
				+ "," +team.getName()+","+team.getSport()+";";;
		editBD.edit(dao.user,dao.password,dao.url, request );}
	
	public static List<Team>  listTeam() throws SQLException{
		TeamDAO dao = new TeamDAO();
    	ResultSet result=selectBD.select(dao.user,dao.password,dao.url, 
	    		"SELECT * FROM team;");
    	ArrayList<Team> consultList =new ArrayList<Team>();
    	while(result.next()){
    		consultList.add((Team) result);
				
		} 
    	return consultList;
}
    	
}