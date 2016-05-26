package fr.uv1.bd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;

public class selectBD {
	public static ResultSet select(String user, String password, String url, String sqlrequest){
		try {

		     Class.forName("org.postgresql.Driver");
		     Connection conn = DriverManager.getConnection(url, user, password);
		     Statement state = conn.createStatement();
		     return state.executeQuery(sqlrequest);
		    } catch (Exception exp) {
			     exp.printStackTrace();
			     return null;			     
			   }
	}

}

