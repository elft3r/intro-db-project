package ch.ethz.inf.dbproject.logic;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import ch.ethz.inf.dbproject.model.User;

@ManagedBean
@SessionScoped
public class SessionData implements Serializable{

	private static final long serialVersionUID = 561873816449327384L;
	
	private User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
