package ch.ethz.inf.dbproject.logic;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import ch.ethz.inf.dbproject.model.DatastoreInterface;
import ch.ethz.inf.dbproject.model.FundingAmount;
import ch.ethz.inf.dbproject.model.User;
import ch.ethz.inf.utils.StringUtils;

@ManagedBean
@RequestScoped
public class UserController {

	private final DatastoreInterface dbInterface = new DatastoreInterface();

	@ManagedProperty(value = "#{sessionData}")
	private SessionData sessionData;
	private String username;
	private String password;

	public String login() {
		User user = dbInterface.getUserBy(username);
		if (user != null && user.getPassword().equals(password)) { // success
			sessionData.setUser(user);
		} else {
			addMessage("Username or Password is invalid");
		}

		return "User.jsf";
	}

	public String logout() {
		sessionData.setUser(null);
		return "User.jsf";
	}

	public String register() {
		// input validation
		if (StringUtils.isNullOrEmpty(username) || StringUtils.isNullOrEmpty(password)) {
			addMessage("Username and Password are required");
			return "User.jsf";
		}

		// check for duplicate user
		if (dbInterface.getUserBy(username) != null) {
			addMessage("Username already in use!");
			return "User.jsf";
		}

		// insert user
		sessionData.setUser(dbInterface.createUser(username, password));
		if (sessionData.getUser() == null) {
			addMessage("Create user failed!");
		}

		return "User.jsf";
	}

	public List<FundingAmount> getFundingAmounts() {
		return dbInterface.getFundsByUserId(sessionData.getUser().getId());
	}

	/**
	 * Adds an information message, which will be displayed on the next site.
	 */
	public void addMessage(String text) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(text));
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
