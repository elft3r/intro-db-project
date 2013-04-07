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

	private static final String SELECT_PROJECTS_BY_NAME_PREP = "select p.id, p.title, p.description, p.goal, "
			+ "p.start_date, p.end_date, c.name as city, cat.name as category from project p "
			+ "inner join city c on p.city_id = c.id inner join category cat on p.category_id = cat.id "
			+ "where p.title like ?";
	private PreparedStatement projectsByName;

	private static final String SELECT_PROJECTS_BY_CATEGORY_PREP = "select p.id, p.title, p.description, p.goal, "
			+ "p.start_date, p.end_date, c.name as city, cat.name as category from project p "
			+ "inner join city c on p.city_id = c.id inner join category cat on p.category_id = cat.id "
			+ "where cat.name like ?";
	private PreparedStatement projectsByCategory;

	private static final String SELECT_PROJECTS_BY_CITY_PREP = "select p.id, p.title, p.description, p.goal, "
			+ "p.start_date, p.end_date, c.name as city, cat.name as category from project p "
			+ "inner join city c on p.city_id = c.id inner join category cat on p.category_id = cat.id "
			+ "where c.name like ?";
	private PreparedStatement projectsByCity;

	private static final String SELECT_FUNDS_BY_USER = "select p.title, fa.amount, fa.reward from user u "
			+ "inner join funds f on u.id = f.user_id inner join funding_amount fa on f.fa_id = fa.id "
			+ "inner join project p on fa.project_id = p.id where u.id = ?";
	private PreparedStatement fundsByUser;

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

	private static final String INSERT_USER_PREP = "INSERT INTO user (username, password) VALUES (?, ?)";
	private PreparedStatement insertUser;

	private static final String INSERT_FUND_PREP = "INSERT INTO funds (user_id, fa_id) values(?, ?)";
	private PreparedStatement insertFund;

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
			this.insertUser = sqlConnection.prepareStatement(INSERT_USER_PREP);
			this.projectsByName = sqlConnection.prepareStatement(SELECT_PROJECTS_BY_NAME_PREP);
			this.projectsByCategory = sqlConnection.prepareStatement(SELECT_PROJECTS_BY_CATEGORY_PREP);
			this.projectsByCity = sqlConnection.prepareStatement(SELECT_PROJECTS_BY_CITY_PREP);
			this.fundsByUser = sqlConnection.prepareStatement(SELECT_FUNDS_BY_USER);
			this.insertFund = sqlConnection.prepareStatement(INSERT_FUND_PREP);
		} catch (SQLException e) {
			logger.log(Level.WARNING, "Failed to create prepared statements", e);
		}
	}

	public final Project getProjectById(final int id) {
		Project res = null;

		try {
			projectById.setInt(1, id);

			try (ResultSet rs = projectById.executeQuery();) {
				if (rs.next()) {
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

		try (Statement stmt = this.sqlConnection.createStatement();
				ResultSet rs = stmt.executeQuery(SELECT_ALL_PROJECTS);) {
			while (rs.next()) {
				projects.add(new Project(rs));
			}
		} catch (SQLException e) {
			logger.log(Level.WARNING, "Failed to retrieve the projects!", e);
		}

		return projects;
	}

	public List<Project> getProjectsByName(String name) {
		return retrieveProjects(projectsByName, name);
	}

	public List<Project> getProjectsByCategory(String name) {
		return retrieveProjects(projectsByCategory, name);
	}

	public List<Project> getProjectsByCity(String name) {
		return retrieveProjects(projectsByCity, name);
	}

	private List<Project> retrieveProjects(PreparedStatement retrieveProjectsStatement, String name) {
		List<Project> projects = new ArrayList<>();
		try {
			retrieveProjectsStatement.setString(1, "%" + name + "%");
			ResultSet resultSet = retrieveProjectsStatement.executeQuery();
			while (resultSet.next()) {
				Project project = new Project();
				project.setId(resultSet.getInt("id"));
				project.setTitle(resultSet.getString("title"));
				project.setDescription(resultSet.getString("description"));
				project.setCity(new City(resultSet.getString("city")));
				project.setCategory(new Category(resultSet.getString("category")));
				projects.add(project);
			}
		} catch (SQLException e) {
			logger.log(Level.WARNING, "Failed to retrieve projects", e);
		}
		return projects;
	}

	public List<FundingAmount> getAmountsOfProject(int projectId) {
		List<FundingAmount> res = new ArrayList<>();

		// set the projectId into the prepared statement
		try {
			faByProject.setInt(1, projectId);

			try (ResultSet rs = faByProject.executeQuery();) {
				while (rs.next()) {
					FundingAmount fundingAmount = new FundingAmount();
					fundingAmount.setId(rs.getInt("id"));
					fundingAmount.setAmount(rs.getBigDecimal("amount"));
					fundingAmount.setReward(rs.getString("reward"));

					Project project = new Project();
					project.setId(rs.getInt("project_id"));
					fundingAmount.setProject(project);
					res.add(fundingAmount);
				}
			}
		} catch (SQLException e) {
			logger.log(Level.WARNING, e.getMessage(), e);
		}
		return res;
	}

	public User getUserBy(String name) {
		User user = null;
		try {
			userByName.setString(1, name);
			ResultSet resultSet = userByName.executeQuery();
			if (resultSet.first()) {
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

	public User createUser(String username, String password) {
		try {
			insertUser.setString(1, username);
			insertUser.setString(2, password);
			insertUser.executeUpdate();
			return getUserBy(username);
		} catch (SQLException e) {
			logger.log(Level.WARNING, "Failed to insert new user", e);
		}
		return null;
	}

	public List<FundingAmount> getFundsByUserId(int id) {
		List<FundingAmount> fundingAmounts = new ArrayList<>();
		try {
			fundsByUser.setInt(1, id);
			ResultSet resultSet = fundsByUser.executeQuery();
			while (resultSet.next()) {
				FundingAmount fundingAmount = new FundingAmount();
				fundingAmount.setAmount(resultSet.getBigDecimal("amount"));
				fundingAmount.setReward(resultSet.getString("reward"));

				Project project = new Project();
				project.setTitle(resultSet.getString("title"));
				fundingAmount.setProject(project);
				fundingAmounts.add(fundingAmount);
			}
		} catch (SQLException e) {
			logger.log(Level.WARNING, "Failed to retrieve user funds", e);
		}
		return fundingAmounts;
	}

	public void fundProject(int userId, int fundingAmountId) {
		try {
			insertFund.setInt(1, userId);
			insertFund.setInt(2, fundingAmountId);
			insertFund.executeUpdate();
		} catch (SQLException e) {
			logger.log(Level.WARNING, "Failed to insert fund with userId = " + userId + " and fundingAmountId = "
					+ fundingAmountId, e);
		}
	}
}
