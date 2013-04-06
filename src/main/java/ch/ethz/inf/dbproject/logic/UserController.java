package ch.ethz.inf.dbproject.logic;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import ch.ethz.inf.dbproject.model.DatastoreInterface;
import ch.ethz.inf.dbproject.model.User;

@ManagedBean
@RequestScoped
public class UserController {
	
	private final DatastoreInterface dbInterface = new DatastoreInterface();
	
	@ManagedProperty(value = "#{sessionData}")
	private SessionData sessionData;
	private String username;
	private String password;
	
	public String login(){
		User user = dbInterface.getUserBy(username);
		if(user != null){ // success
			sessionData.setUser(user);
		}else{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("User or Password is invalid"));
		}
		
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
