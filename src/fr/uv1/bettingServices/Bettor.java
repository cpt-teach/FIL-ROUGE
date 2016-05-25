
package fr.uv1.bettingServices;


import fr.uv1.bettingServices.exceptions.*;
import fr.uv1.utils.*;

import java.sql.SQLException;
import java.util.*;

public class Bettor {
    private int id;
	private String name;
	private String username;
	private String password;
	private MyCalendar birthday;
	private int token;
	private boolean authentificate;

	public Bettor(String name,String username, String password, int token, boolean authentificate){
		this.name=name;
		this.username=username;
		this.password=password;
		this.token= token;
		this.authentificate=authentificate;
	}
	/*-------------------------------------getter and setter ----------------------------*/
	public int getId() {
		return id;
	}

	public String getFName(){
		return this.name;
	}
	
	public String getUsername(){
		return this.username;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public int gettoken(){
		return this.token;
	}
	
	public void setName(String name){
		this.name=name;
	}
	
	public void setPassword( String password){
		this.password=password;
	}
	/*----------------------End Getters and Setters----------------------*/
	
	
	public static void ValidateStringUsername(String username)
			throws BadParametersException {
		if (username == null)
			throw new BadParametersException("username can't be null");

		if (username.length() < 6)
			throw new BadParametersException("username length less than "
					+ 6 + "characters");

		if (!username.matches(new String("[0-9A-Za-z]*")))
			throw new BadParametersException("the username " + username
					+ " does not verify constraints ");
	}
	
	public void authenticateSubscriber(String password) 
			throws AuthenticationException {
		if (!this.password.equals(password))
			throw new AuthenticationException("wrong subscriber password");
	}
	
	
	
}
