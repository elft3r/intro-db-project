package ch.ethz.inf.dbproject.model;

import java.io.Serializable;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class Project implements Serializable {

	private static final long serialVersionUID = 413320471268725530L;

	private int id;
	private String title;
	private String description;
	private Date startDate;
	private Date endDate;
	private City city;
	private Category category;
	private User owner;

	public Project() {

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

	public void setTitle(String title) {
		this.title = title;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}
}