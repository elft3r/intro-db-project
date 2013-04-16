package ch.ethz.inf.dbproject.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class Project implements Serializable {

	private static final long serialVersionUID = 413320471268725530L;

	/**
	 * we only we want to initialize this, if we really need t get either the whole user, city or
	 * category
	 */
	private DatastoreInterface dbInterface = null;

	private int id;
	private String title;
	private String description;
	private Date startDate;
	private Date endDate;
	private int cityId;
	private City city;
	private int categoryId;
	private Category category;
	private int ownerId;
	private User user;
	private BigDecimal goal;

	public Project() {

	}

	public Project(final ResultSet rs) throws SQLException {
		this.categoryId = rs.getInt("category_id");
		this.cityId = rs.getInt("city_id");
		this.description = rs.getString("description");
		this.endDate = rs.getDate("end_date");
		this.goal = rs.getBigDecimal("goal");
		this.id = rs.getInt("id");
		this.ownerId = rs.getInt("owner_id");
		this.startDate = rs.getDate("start_date");
		this.title = rs.getString("title");
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

	public int getCityId() {
		return cityId;
	}

	public City getCity() {
		if (city == null) {
			initDbInterface();
			city = dbInterface.getCityById(cityId);
		}

		return city;
	}

	public void setCityId(int id) {
		this.cityId = id;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public Category getCategory() {
		if (category == null) {
			initDbInterface();
			category = dbInterface.getCategoryById(categoryId);
		}

		return category;
	}

	public void setCategoryId(int id) {
		this.categoryId = id;
	}

	public User getOwner() {
		if (user == null) {
			initDbInterface();
			user = dbInterface.getUserById(ownerId);
		}

		return user;
	}

	public void setOwnerId(int id) {
		this.ownerId = id;
	}

	public BigDecimal getGoal() {
		return goal;
	}

	public void setGoal(BigDecimal goal) {
		this.goal = goal;
	}

	private void initDbInterface() {
		dbInterface = new DatastoreInterface();
	}
	
	public String getRemainingFundingTime() {
		String res = "Project is closed for funding!";
		
		long diff = endDate.getTime() - System.currentTimeMillis();
		long diffDays = diff / (24 * 60 * 60 * 1000);
		if(diffDays > 0) {
			res = diffDays + " days remaining!";
		}
		
		return res;
	}
}