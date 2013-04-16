package ch.ethz.inf.dbproject.model;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * Object that represents a user comment.
 */
public class Comment implements Serializable {

	private static final long serialVersionUID = -4310060262411008475L;

	private DatastoreInterface dbInterface;

	private int id;
	private String comment;
	private int userId;
	private User user;
	private Date createDate;
	private int projectId;
	private Project project;

	public Comment(final ResultSet rs) throws SQLException {
		this.comment = rs.getString("text");
		this.createDate = rs.getTimestamp("create_date");
		this.id = rs.getInt("id");
		this.projectId = rs.getInt("project_id");
		this.userId = rs.getInt("user_id");
		
		// first try to resolve all the values and then add it to the value
		User tmpUser = new User();
		tmpUser.setId(rs.getInt("u.id"));
		tmpUser.setUsername(rs.getString("u.username"));
		
		this.user = tmpUser;
	}

	public Comment() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int id) {
		this.userId = id;
	}

	public User getUser() {
		if (user == null) {
			initDbInterface();
			user = dbInterface.getUserById(userId);
		}

		return user;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public int getProjectId() {
		return this.projectId;
	}

	public void setProjectId(int id) {
		this.projectId = id;
	}

	public Project getProject() {
		if (project == null) {
			initDbInterface();
			project = dbInterface.getProjectById(id);
		}

		return project;
	}

	private void initDbInterface() {
		if (dbInterface == null) {
			dbInterface = new DatastoreInterface();
		}
	}
}
