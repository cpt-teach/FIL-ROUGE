package fr.uv1.competition;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import fr.uv1.bd.*;
import fr.uv1.bettingServices.Exceptions.BadParametersException;
import fr.uv1.bettingServices.Exceptions.ExistingCompetitionException;
import fr.uv1.bettingServices.Exceptions.ExistingCompetitorException;
import fr.uv1.bettingServices.Exceptions.NotATeamException;
import fr.uv1.utils.MyCalendar;

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
		
		public static Competition  selectCompetitionById(int id) throws SQLException{
			CompetitionDAO dao = new CompetitionDAO();
			ResultSet result= selectBD.select(dao.user,dao.password,dao.url, 
			    		"SELECT * FROM competition where comp_id="+id+";");
			result.next();
			return (Competition) result;
		}
		
		public static int selectCompetitionId(String name,String sport,MyCalendar enddate) throws SQLException {
			ResultSet result = selectBD.select("postgres","postgres", "jdbc:postgresql://localhost:5433/tests",
					 "SELECT * FROM competition WHERE  name = '"+ name +"' AND sport ='"+ sport+"' AND endDate = '" + enddate.toString2()+"'");
			result.next();
			return result.getInt(1);
		}
		
		public static Competition selectCompetitionByName(String Competition_name)throws SQLException, 
																						 BadParametersException, 
																						 ExistingCompetitorException, 
																						 ExistingCompetitionException, 
																						 NotATeamException{ // TODO in DAO
			Competition competition = null;
			CompetitionDAO dao = new CompetitionDAO();
			ResultSet result = selectBD.select(dao.user,dao.password,dao.url, "SELECT * FROM competition WHERE name LIKE '"+Competition_name+"';");
			while(result.next()){
				ResultSet result1 = selectBD.select(dao.user,dao.password,dao.url, "SELECT * FROM participation WHERE comp_id = "+result.getInt(1)+";");
				while(result1.next()){
					int isteam = new Integer(32);
					isteam = Integer.parseInt(result1.getString(4));
					if(isteam==1){
						List<Competitor> liste_competitor = new ArrayList<Competitor>();
						ResultSet result2 = selectBD.select(dao.user,dao.password,dao.url, "SELECT * FROM team WHERE team_id ="+result1.getInt(2)+";");
						while(result2.next()){
							ResultSet result4 = selectBD.select(dao.user,dao.password,dao.url, "SELECT * FROM inteam WHERE team_id = "+result2.getInt(1)+";");
							while(result4.next()){	
								
								
								Competitor competitor_team = new Team(result2.getString(2));
								ResultSet result3 = selectBD.select(dao.user,dao.password,dao.url, "SELECT * FROM individual WHERE indi_id = "+result4.getInt(1)+";");
								while(result3.next()){
									Competitor member = new Individual(result3.getString(2),result3.getString(3),result3.getString(4));
									competitor_team.addMember(member);
								}
							liste_competitor.add(competitor_team);
						
						}
					}
						MyCalendar endDate = MyCalendar.fromString(result.getString(4));
						competition = new Competition(result.getString(2), result.getString(3), liste_competitor, endDate);
						
					}
					else if(isteam==0){
						List<Competitor> liste_competitor = new ArrayList<Competitor>();
						ResultSet result2 = selectBD.select(dao.user,dao.password,dao.url, "SELECT * FROM individual WHERE indi_id LIKE "+result1.getInt(1)+";");
						while(result2.next()){
							Competitor competitor_indi = new Individual(result2.getString(2),result2.getString(3),result2.getString(4));
							liste_competitor.add(competitor_indi);	
			}
						MyCalendar endDate = MyCalendar.fromString(result.getString(4));
						competition = new Competition(result.getString(2), result.getString(3), liste_competitor, endDate);
					
			}
			
			}
			}
			return competition;
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
