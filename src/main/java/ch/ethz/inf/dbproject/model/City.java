package ch.ethz.inf.dbproject.model;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class represents a City
 */
public final class City implements Serializable {

	private static final long serialVersionUID = 2114872906439481482L;

	private int id;
	private String name;
	
	public City(final ResultSet rs) throws SQLException {
		this.id = rs.getInt("id");
		this.name = rs.getString("name");
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public City(String name) {
		this.name = name;
	}

	public final String getName() {
		return name;
	}
}