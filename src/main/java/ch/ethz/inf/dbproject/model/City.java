package ch.ethz.inf.dbproject.model;

import java.io.Serializable;

/**
 * This class represents a City
 */
public final class City implements Serializable {

	private static final long serialVersionUID = 2114872906439481482L;

	private String name;

	public City(String name) {
		this.name = name;
	}

	public final String getName() {
		return name;
	}
}