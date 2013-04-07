package ch.ethz.inf.dbproject.model;

import java.io.Serializable;

/**
 * Object that represents a user comment.
 */
public class Comment implements Serializable {

	private static final long serialVersionUID = -4310060262411008475L;

	private final String username;
	private final String comment;

	public Comment(final String username, final String comment) {
		this.username = username;
		this.comment = comment;
	}

	public String getUsername() {
		return username;
	}

	public String getComment() {
		return comment;
	}
}
