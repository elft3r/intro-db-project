package ch.ethz.inf.dbproject.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class Project {

	/**
	 * TODO The properties of the project should be added here
	 */
	private final int id;
	private final String title;
	private final String description;

	/**
	 * Construct a new project.
	 * 
	 * @param title
	 *            The name of the project
	 */
	public Project(final int id, final String title, final String description) {
		this.id = id;
		this.title = title;
		this.description = description;
	}

	public Project(final ResultSet rs) throws SQLException {
		// TODO These need to be adapted to your schema
		// TODO Extra properties need to be added
		this.id = rs.getInt("id");
		this.title = rs.getString("title");
		this.description = rs.getString("description");
	}
	
	public String getTitle() {
		return title;
	}

	public int getId() {
		return id;
	}

	public String getDescription() {
		return description;
	}
}