package fr.uv1.bettingServices;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import fr.uv1.competition.*;
import fr.uv1.bd.editBD;
import fr.uv1.bd.selectBD;
import fr.uv1.bettingServices.Exceptions.*;
import fr.uv1.utils.MyCalendar;

public class SubscriberDAO{

	private static String url="jdbc:postgresql://localhost:5433/tests";
	private static String user="postgres";
	private static String password="postgres";
	
	public SubscriberDAO () {
		
	}

	public void persist(Subscriber subscriber) throws SQLException {
		String request="insert into subscriber(username,firstname,lastname,password,birthday,tokens) values ('"+subscriber.getUserName()+"','"
				+subscriber.getFirstName()+"', '"
				+subscriber.getLastName()+"', '"
				+subscriber.getPassword()+"','"
				+subscriber.getBirthday().toString2()+"','"
				+subscriber.getTokens()+"');";;
		editBD.edit(user,password,url, request);
			request="select subscriber_id FROM subscriber WHERE username='"+subscriber.getUserName()+"';";
			ResultSet resultSet=selectBD.select(user,password,url,request);
			int id  = 0;
			while(resultSet.next())
				id = resultSet.getInt("subscriber_id");

			subscriber.setSubscriber_id(id);		
	}
	
	
	
	/**
	   * Here Finding All bets in the database,
	   * @return the list of the bets instances
	   * @throws SQLException
	 * @throws BadParametersException 
	   */
	
	public ArrayList<Subscriber> listOfSubscribers() throws SQLException, BadParametersException{
		  
		// Get a database connection.
	    String request="select * from subscriber order by subscriber_id;";
	    ResultSet resultSet=selectBD.select(user,password,url,request);
	    ArrayList<Subscriber> listOfSubscribers = new ArrayList<Subscriber>();
	    while(resultSet.next()) {
	    	Subscriber subscriber = null;
	    		subscriber = new Subscriber(resultSet.getInt("subscriber_id"),
	    				resultSet.getString("username"),
	    				resultSet.getString("password"),
	    				resultSet.getString("lastname"),
	    				resultSet.getString("firstname"),
	    				resultSet.getLong("tokens"),
	    				MyCalendar.fromString(resultSet.getString("birthday")) 
	    				);
	    	listOfSubscribers.add(subscriber);
	    }	    
	    return listOfSubscribers;
	  }
//$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
	
	
	/**
	   * Update a Subscriber on the database
	   * 
	   * @param subscriber to be updated
	   * @throws SQLException
	   */
	public void update(Subscriber subscriber) throws SQLException {
	    	String request="update subscriber set  username='"+subscriber.getUserName()+"'," +
	    			" password='"+subscriber.getPassword()+"'," +
					"lastname='"+subscriber.getLastName()+"'," +
							"firstname='"+subscriber.getFirstName()+"'," +
									"tokens='"+subscriber.getTokens()+"'," +
											"birthday='"+subscriber.getBirthday().toString2()+"' where subscriber_id='"+subscriber.getSubscriber_id()+"';";

	    	editBD.edit(user,password,url, request);
	  }

	
	/**
	   * Delete from the database a specific bet.
	   * 
	   * @param bet the bet to be deleted.
	   * @throws SQLException
	   */
	
	public static void delete(Subscriber subscriber) throws SQLException {
		  
	    	String request="delete from subscriber where subscriber_id='"+subscriber.getSubscriber_id()+"';";
	    // Delete the bet.
	    	editBD.edit(user,password,url, request);
	    // Closing the database connection.
	  }
	public static Subscriber getSubscriberById(int subscriber_id) throws BadParametersException, SQLException{
		
		String request="select * from subscriber where subscriber_id='"+subscriber_id+"';";
		ResultSet resultSet=selectBD.select(user,password,url,request);
		Subscriber subscriber=null;
		while(resultSet.next()) {
	    		subscriber = new Subscriber(resultSet.getInt("subscriber_id"),
	    				resultSet.getString("username"),
	    				resultSet.getString("password"),
	    				resultSet.getString("lastname"),
	    				resultSet.getString("firstname"),
	    				resultSet.getLong("tokens"),
	    				MyCalendar.fromString(resultSet.getString("birthday")) 
	    				);
		}
		
	    return subscriber;
	}
	public static Subscriber getSubscriberByUsername(String username) throws BadParametersException, SQLException{
	
		String request="select * from subscriber where username LIKE '"+username+"';";
		ResultSet resultSet=selectBD.select(user,password,url,request);
		Subscriber subscriber=null;
		while(resultSet.next()) {
	    		subscriber = new Subscriber(resultSet.getInt("subscriber_id"),
	    				resultSet.getString("username"),
	    				resultSet.getString("password"),
	    				resultSet.getString("lastname"),
	    				resultSet.getString("firstname"),
	    				resultSet.getLong("tokens"),
	    				MyCalendar.fromString(resultSet.getString("birthday"))
	    				);

		}
		
	    return subscriber;
	    
	}
	
	public static ArrayList<Bet> subscriberBets(Subscriber subscriber) throws SQLException, BadParametersException, ExistingCompetitorException, ExistingCompetitionException {
		String request="select * from bets where bettor_Id="+subscriber.getSubscriber_id()+";";
		ResultSet resultSet=selectBD.select(user, password, url, request);
		ArrayList<Bet> subscriberBets = new ArrayList<Bet>();
		Competition_ResultsDAO comp_res_dao=new Competition_ResultsDAO();
		while(resultSet.next()){
			Bet bet=null;
			if(resultSet.getInt("ifPodium")==1){
				bet=new Bet(resultSet.getLong("montant"),comp_res_dao.getCompetitorById(resultSet.getInt("winner_Id")),
						comp_res_dao.getCompetitorById(resultSet.getInt("second_Id")),
						comp_res_dao.getCompetitorById(resultSet.getInt("third_Id")),CompetitionDAO.selectCompetitionById(resultSet.getInt("competition_ID")),
						getSubscriberById(resultSet.getInt("bettor_Id")),1);
				subscriberBets.add(bet);
			}
			if(resultSet.getInt("ifPodium")==0){
				bet=new Bet(resultSet.getLong("montant"),comp_res_dao.getCompetitorById(resultSet.getInt("winner_Id")),
						comp_res_dao.getCompetitorById(resultSet.getInt("winner_Id")),
						comp_res_dao.getCompetitorById(resultSet.getInt("winner_Id")),CompetitionDAO.selectCompetitionById(resultSet.getInt("competition_ID")),
						getSubscriberById(resultSet.getInt("bettor_Id")),0);
				subscriberBets.add(bet);
			}
			}
		return subscriberBets;
			
			
		}
	
	}
	

