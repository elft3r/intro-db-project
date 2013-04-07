package ch.ethz.inf.dbproject.model;

import java.io.Serializable;

/**
 * Object that represents a category of project (i.e. Art, Music...)
 */
public final class Category implements Serializable {

	private static final long serialVersionUID = -5710036722033128519L;

	private String name;

	public Category(String name) {
		this.name = name;
	}

	public final String getName() {
		return name;
	}

}
