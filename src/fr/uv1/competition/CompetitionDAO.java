package fr.uv1.competition;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import fr.uv1.bd.*;

public class CompetitionDAO {
	private String url = "jdbc:postgresql://localhost:5433/tests";
	private String user = "postgres";
	private String password ="postgres";
	
	public CompetitionDAO() {
		
	}
	protected static List<Competition> currentCompetitions = new ArrayList<Competition>();
		
	// Static methods
	public static int getIdMax() throws SQLException{
		CompetitionDAO dao = new CompetitionDAO();
    	ResultSet result=selectBD.select(dao.user,dao.password,dao.url, 
	    		"SELECT max(id) FROM competition;");
    	result.next();
    	return (result.getInt(1));
     	}
	
	
	/**
	 * Persist (store) a competition in the data base.
	 * 
	 * @param competition the competition to be stored.
	 * @throws SQLException
	 */
	public static void persist(Competition competition) throws SQLException {
		CompetitionDAO dao = new CompetitionDAO();
		String request;
		request = "insert into competition(comp_id,name,sport)  values (" +
				 +competition.getId()+"," +competition.getName()+
				"," +competition.getSport()+");";
		editBD.edit(dao.user,dao.password,dao.url, request );}
	
	
	/**
	   * Update a competition value on the database.
	   * 
	   * @param competition the competition to be updated.
	   * @throws SQLException
	   */
	public static void update(Competition competition) throws SQLException {
		CompetitionDAO dao = new CompetitionDAO();  
	    // Get a database connection.yobvh
	    // Update the competition.
		String request;
		request = "update competition set (comp_id,name,sport)  values (" +
				 +competition.getId()+"," +competition.getName()+
				"," +competition.getSport()+");";
		editBD.edit(dao.user,dao.password,dao.url, request );}
	
	
	// Closing the database connection.

	
		/**
		   * Delete from the database a specific competition.
		   * 
		   * @param competition the competition to be deleted.
		   * @throws SQLException
		   */
		
		public static void delete(Competition competition) throws SQLException {
			CompetitionDAO dao = new CompetitionDAO();
		    editBD.edit(dao.user,dao.password,dao.url, 
		    		"DELETE FROM competition where comp_id="+competition.getId()+";");

		  }
		
		public static Competition  getCompetitionById(int id) throws SQLException{
			CompetitionDAO dao = new CompetitionDAO();
			ResultSet result= selectBD.select(dao.user,dao.password,dao.url, 
			    		"SELECT * FROM competition where comp_id="+id+";");
			result.next();
			return (Competition) result;
		}

		public static List<Competition>  listCompetitions() throws SQLException{
				CompetitionDAO dao = new CompetitionDAO();
		    	ResultSet result=selectBD.select(dao.user,dao.password,dao.url, 
			    		"SELECT * FROM competition;");
		    	ArrayList<Competition> consultList =new ArrayList<Competition>();
		    	while(result.next()){
		    		consultList.add((Competition) result);
						
				} 
		    	return consultList;
		}
}
