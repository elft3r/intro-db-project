package ch.ethz.inf.dbproject.model;

import java.io.Serializable;

/**
 * Object that represents a registered in user.
 */
public final class User implements Serializable {

	private static final long serialVersionUID = -5571244964876717431L;

	private int id;
	private String username;
	private String password;

	public User() {
	}

	public User(int id, String username, String password) {
		this.id = id;
		this.username = username;
		this.password = password;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
}
