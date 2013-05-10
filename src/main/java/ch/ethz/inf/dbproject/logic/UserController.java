package ch.ethz.inf.dbproject.logic;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import ch.ethz.inf.dbproject.database.DatastoreInterface;
import ch.ethz.inf.dbproject.database.DatastoreInterfaceSimpleDatabase;
import ch.ethz.inf.dbproject.model.FundingAmount;
import ch.ethz.inf.dbproject.model.User;
import ch.ethz.inf.utils.FacesContextUtils;
import ch.ethz.inf.utils.StringUtils;

@ManagedBean
@RequestScoped
public class UserController {

	private final DatastoreInterface dbInterface = new DatastoreInterfaceSimpleDatabase();

	@ManagedProperty(value = "#{sessionData}")
	private SessionData sessionData;
	private String username;
	private String password;

	public String login() {
		User user = dbInterface.getUserBy(username);
		if (user != null && user.getPassword().equals(password)) { // success
			sessionData.setUser(user);
		} else {
			FacesContextUtils.showMessage("Username or Password is invalid");
		}

		return null;
	}

	public String logout() {
		sessionData.setUser(null);
		return null;
	}

	public String register() {
		// input validation
		if (StringUtils.isNullOrEmpty(username) || StringUtils.isNullOrEmpty(password)) {
			FacesContextUtils.showMessage("Username and Password are required");
		} else if (dbInterface.getUserBy(username) != null) {
			// check for duplicate user
			FacesContextUtils.showMessage("Username already in use!");
		} else {
			// insert user
			sessionData.setUser(dbInterface.createUser(username, password));
			if (sessionData.getUser() == null) {
				FacesContextUtils.showMessage("Create user failed!");
			}
		}

		return null;
	}

	public List<FundingAmount> getFundingAmounts() {
		return dbInterface.getFundsByUserId(sessionData.getUser().getId());
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
