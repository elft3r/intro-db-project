package ch.ethz.inf.dbproject.logic;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import ch.ethz.inf.dbproject.model.DatastoreInterface;

@ManagedBean
@RequestScoped
public class UserController {
	
	private final DatastoreInterface dbInterface = new DatastoreInterface();
	
	@ManagedProperty(value = "#{sessionData}")
	private SessionData sessionData;
	
	private String username;
	
	private String password;
	
	public String login(){
		
		// TODO
		// Ask the data store interface if it knows this user
		// Retrieve User
		// Store this user into the session
		
		return "User.jsf";
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public SessionData getSessionData() {
		return sessionData;
	}

	public void setSessionData(SessionData sessionData) {
		this.sessionData = sessionData;
	}
}
