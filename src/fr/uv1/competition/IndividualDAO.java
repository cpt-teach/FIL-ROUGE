package fr.uv1.competition;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import fr.uv1.bd.*;
import fr.uv1.utils.MyCalendar;


public class IndividualDAO {
	private String url = "jdbc:postgresql://localhost:5433/tests";
	private String user = "postgres";
	private String password ="postgres";
	
	public IndividualDAO() {
		
	}
	
	
	//-----------------------------------------------------------------------------------------------------------
		/**
		 * Persist (store) a individual in the data base.
		 * 
		 * @param individual the individual to be stored.
		 * @throws SQLException
		 */
	
	public static int getIdMax() throws SQLException{
		IndividualDAO dao = new IndividualDAO();
    	ResultSet result=selectBD.select(dao.user,dao.password,dao.url, 
	    		"SELECT max(id) FROM individual;");
    	result.next();
    	return (result.getInt(1));
     	}
	
	public static void persist(Individual individual) throws SQLException {
		IndividualDAO dao = new IndividualDAO();
		String request;
		request = "insert into individual(indi_id,name,sport)  values (" +
				 +individual.getId()+"," +individual.getLastName()+
				"," +individual.getFirstName()+","+individual.getBornDate().toString()+");";
		editBD.edit(dao.user,dao.password,dao.url, request );}
	
	
	/**
	   * Update a individual value on the database.
	   * 
	   * @param individual the individual to be updated.
	   * @throws SQLException
	   */
	public static void update(Individual individual) throws SQLException {
		IndividualDAO dao = new IndividualDAO();
	    // Get a database connection.yobvh
	    // Update the individual.
		String request;
		request = "update individual set (indi_id,name,sport)  values (" +
				 +individual.getId()+"," +individual.getLastName()+
				"," +individual.getFirstName()+","+individual.getBornDate().toString()+");";
		editBD.edit(dao.user,dao.password,dao.url, request );}
		
	    // Closing the database connection.

	
	/**
	   * Delete from the database a specific individual.
	   * 
	   * @param individual the individual to be deleted.
	   * @throws SQLException
	   */
	
	public static void delete(Individual individual) throws SQLException {
		IndividualDAO dao = new IndividualDAO();
	    editBD.edit(dao.user,dao.password,dao.url, 
	    		"DELETE FROM individual where indi_id="+individual.getId()+";");

	  }
	    
	    
	public static Individual  getIndividual(int id) throws SQLException{
		IndividualDAO dao = new IndividualDAO();
		ResultSet result= selectBD.select(dao.user,dao.password,dao.url, 
		    		"SELECT * FROM individual where indi_id="+id+";");
		result.next();
		return (Individual) result;
	}

	public static List<Team>  listTeams() throws SQLException{
		IndividualDAO dao = new IndividualDAO();
    	ResultSet result=selectBD.select(dao.user,dao.password,dao.url, 
	    		"SELECT * FROM individual;");
    	ArrayList<Team> consultList =new ArrayList<Team>();
    	while(result.next()){
    		consultList.add((Team) result);
				
		} 
    	return consultList;
}
	public static int selectIndividualId(String lastName,String firstName,MyCalendar bornDate)throws SQLException {
		int id = new Integer(32);
		IndividualDAO dao = new IndividualDAO();
		ResultSet result = selectBD.select(dao.user,dao.password,dao.url, "SELECT * FROM individual ;");
		while(result.next()){
			if(lastName.equals(result.getString(2))&&
			   firstName.equals(result.getString(3))&&
			   bornDate.equals(MyCalendar.fromString(result.getString(4)))){
		
				id = result.getInt(1);
			}
		}
	return id;
	}
}
	
	
	
	
	
	

