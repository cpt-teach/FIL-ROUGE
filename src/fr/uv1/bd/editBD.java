package fr.uv1.bd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;

public class editBD {
	public static void edit(String user, String password, String url, String sqlrequest){
		try {

		     Class.forName("org.postgresql.Driver");
		     Connection conn = DriverManager.getConnection(url, user, password);
		     Statement state = conn.createStatement();
		     state.executeUpdate(sqlrequest);
		    } catch (Exception exp) {
			     exp.printStackTrace();
			  			     
			   }
	}

}

