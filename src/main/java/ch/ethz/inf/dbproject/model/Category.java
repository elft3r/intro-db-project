package ch.ethz.inf.dbproject.model;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Object that represents a category of project (i.e. Art, Music...)
 */
public final class Category implements Serializable {

	private static final long serialVersionUID = -5710036722033128519L;

	private int id;
	private String name;
	
	public Category(final ResultSet rs) throws SQLException {
		this.name = rs.getString("name");
		this.id = rs.getInt("id");
	}

	public Category(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public final String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
