package ch.ethz.inf.dbproject.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import ch.ethz.inf.dbproject.database.DatabaseConnection;

/**
 * This class should be the interface between the web application and the database. Keeping all the
 * data-access methods here will be very helpful for part 2 of the project.
 */
public final class DatastoreInterface {
	private static final Logger logger = Logger.getLogger(DatastoreInterface.class.getName());
	
	private static final String SELECT_ALL_PROJECTS = "SELECT * FROM project";
	
	/**
	 * {@link PreparedStatement} for retrieving the funding amounts for a single project
	 */
	private static final String SELECT_FUNDING_AMOUNT_PREP = "SELECT * FROM funding_amount WHERE project_id = ?";
	private PreparedStatement faByProject;
	
	/**
	 * {@link PreparedStatement} for retrieving a single project
	 */
	private static final String SELECT_PROJECY_BY_ID_PREP = "SELECT * FROM project WHERE id = ?";
	private PreparedStatement projectById;

	/**
	 * {@link PreparedStatement} for retrieving a single user
	 */
	private static final String SELECT_USER_BY_NAME_PREP = "SELECT * FROM user WHERE username = ?";
	private PreparedStatement userByName;	
	
	private Connection sqlConnection = null;

	public DatastoreInterface() {
		// get the connection to the DB
		try {
			this.sqlConnection = DatabaseConnection.getMySQLInstance().getConnection();
		} catch (Exception e) {
			logger.log(Level.WARNING, "Errro while connection to DB!", e);
		}
		
		// in here we specify all the prepared statements we need
		try {
			this.faByProject = sqlConnection.prepareStatement(SELECT_FUNDING_AMOUNT_PREP);
			this.projectById = sqlConnection.prepareStatement(SELECT_PROJECY_BY_ID_PREP);
			this.userByName = sqlConnection.prepareStatement(SELECT_USER_BY_NAME_PREP);
		} catch (SQLException e) {
			logger.log(Level.WARNING, "Failed to create prepared statements", e);
		}
	}

	public final Project getProjectById(final int id) {
		Project res = null;
		
		try {
			projectById.setInt(1, id);
			
			try (ResultSet rs = projectById.executeQuery();) {
				if(rs.next()) {
					res = new Project(rs);
				}
			}
		} catch (SQLException e) {
			logger.log(Level.WARNING, "Failed to retrieve Project with id = '" + id + "'.", e);
		}
		
		return res;
	}

	public final List<Project> getAllProjects() {
		List<Project> projects = new ArrayList<>();
		
		try(Statement stmt = this.sqlConnection.createStatement();
				ResultSet rs = stmt.executeQuery(SELECT_ALL_PROJECTS);) {
			while(rs.next()) {
				projects.add(new Project(rs));
			}
		} catch (SQLException e) {
			logger.log(Level.WARNING, "Failed to retrieve the projects!", e);
		}
		
		return projects;
	}

	public List<Amount> getAmountsOfProject(int projectId) {
		List<Amount> res = new ArrayList<>();
		
		// set the projectId into the prepared statement
		try {
			faByProject.setInt(1, projectId);
			
			try (ResultSet rs = faByProject.executeQuery();) {
				while(rs.next()) {
					res.add(new Amount(rs));
				}
			}
		} catch (SQLException e) {
			logger.log(Level.WARNING, e.getMessage(), e);
		}

		return res;
	}

	public User getUserBy(String name) {
		User user = null;
		try{
			userByName.setString(1, name);
			ResultSet resultSet = userByName.executeQuery();
			if(resultSet.first()){
				user = new User();
				user.setId(resultSet.getInt("id"));
				user.setUsername(resultSet.getString("username"));
				user.setPassword(resultSet.getString("password"));
			}
		} catch (SQLException e) {
			logger.log(Level.WARNING, "Failed to retrieve user by name", e);
		}
		return user;
	}
}
