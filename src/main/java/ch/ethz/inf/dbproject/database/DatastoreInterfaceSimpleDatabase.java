package ch.ethz.inf.dbproject.database;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import ch.ethz.inf.dbproject.database.simpledatabase.Tuple;
import ch.ethz.inf.dbproject.database.simpledatabase.operators.Container;
import ch.ethz.inf.dbproject.database.simpledatabase.operators.HashJoin;
import ch.ethz.inf.dbproject.database.simpledatabase.operators.Insert;
import ch.ethz.inf.dbproject.database.simpledatabase.operators.Like;
import ch.ethz.inf.dbproject.database.simpledatabase.operators.Scan;
import ch.ethz.inf.dbproject.database.simpledatabase.operators.Select;
import ch.ethz.inf.dbproject.model.Category;
import ch.ethz.inf.dbproject.model.City;
import ch.ethz.inf.dbproject.model.Comment;
import ch.ethz.inf.dbproject.model.FundingAmount;
import ch.ethz.inf.dbproject.model.Project;
import ch.ethz.inf.dbproject.model.StretchedGoals;
import ch.ethz.inf.dbproject.model.User;
import ch.ethz.inf.utils.FacesContextUtils;

import com.sun.istack.internal.logging.Logger;

public final class DatastoreInterfaceSimpleDatabase implements
		DatastoreInterface {

	private static final Logger logger = Logger
			.getLogger(DatastoreInterfaceSimpleDatabase.class);

	private static final String USER_TABLE = "user";
	private static final String[] USER_COLUMNS = { "id", "username", "password" };

	private static final String PROJECT_TABLE = "project";
	private static final String[] PROJECT_COLUMNS = { "id", "title",
			"description", "goal", "start_date", "end_date", "city_id",
			"category_id", "owner_id" };

	private static final String CITY_TABLE = "city";
	private static final String[] CITY_COLUMNS = { "id", "name" };

	private static final String CATEGORY_TABLE = "category";
	private static final String[] CATEGORY_COLUMNS = { "id", "name" };

	@Override
	public final Project getProjectById(final int id) {

		/**
		 * TODO this method should return the product with the given id
		 */
		final Scan scan = new Scan("projects.txt", new String[] { "id", "name",
				"field2", "field3" });

		final Select<Integer> select = new Select<Integer>(scan, "id", id);
		if (select.moveNext()) {
			Tuple tuple = select.current();
			Project p = new Project();
			p.setId(tuple.getInt(0));
			p.setTitle(tuple.get(1));
			return p;
		}
		return null;
	}

	@Override
	public final List<Category> getAllCategories() {

		/**
		 * TODO This method should return all the different categories in the
		 * database.
		 * 
		 * For the time being we return some random values.
		 */
		final List<Category> categories = new ArrayList<Category>();
		categories.add(new Category("Technology"));
		categories.add(new Category("Art"));
		return categories;
	}

	@Override
	public List<Project> getAllProjects() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Project> getProjectsByName(String name) {
		Scan projectScan = new Scan(PROJECT_TABLE, PROJECT_COLUMNS, "p");
		Scan cityScan = new Scan(CITY_TABLE, CITY_COLUMNS, "c");
		Scan categoryScan = new Scan(CATEGORY_TABLE, CATEGORY_COLUMNS, "cat");

		// optimization: push down selection
		Like projectSelect = new Like(projectScan, "p.title", name);

		HashJoin projectCityJoin = new HashJoin(cityScan, projectSelect, "c.id", "p.city_id");
		HashJoin projectCityCategoryJoin = new HashJoin(categoryScan, projectCityJoin, "cat.id", "p.category_id");

		return retrieveProjects(projectCityCategoryJoin);
	}

	private List<Project> retrieveProjects(HashJoin joinOperation) {
		List<Project> projects = new ArrayList<>();
		while (joinOperation.moveNext()) {
			Tuple tuple = joinOperation.current();

			Project project = new Project();
			project.setId(tuple.getInt("p.id"));
			project.setTitle(tuple.get("p.title"));
			project.setDescription(tuple.get("p.description"));
			project.setGoal(tuple.getBigDecimal("p.goal"));
			project.setStartDate(tuple.getDate("p.start_date"));
			project.setEndDate(tuple.getDate("p.end_date"));
			project.setCityId(tuple.getInt("p.city_id"));
			project.setCategoryId(tuple.getInt("p.category_id"));
			project.setOwnerId(tuple.getInt("p.owner_id"));
			projects.add(project);
		}
		return projects;
	}

	@Override
	public Project getProjectByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Project> getProjectsByCategory(String name) {
		Scan projectScan = new Scan(PROJECT_TABLE, PROJECT_COLUMNS, "p");
		Scan cityScan = new Scan(CITY_TABLE, CITY_COLUMNS, "c");
		Scan categoryScan = new Scan(CATEGORY_TABLE, CATEGORY_COLUMNS, "cat");

		// optimization: push down selection
		Like categorySelect = new Like(categoryScan, "cat.name", name);

		HashJoin categoryProjectJoin = new HashJoin(categorySelect, projectScan, "cat.id", "p.category_id");
		HashJoin cityCategoryProjectJoin = new HashJoin(cityScan, categoryProjectJoin, "c.id", "p.city_id");

		return retrieveProjects(cityCategoryProjectJoin);
	}

	@Override
	public List<Project> getProjectsByCity(String name) {
		Scan projectScan = new Scan(PROJECT_TABLE, PROJECT_COLUMNS, "p");
		Scan cityScan = new Scan(CITY_TABLE, CITY_COLUMNS, "c");
		Scan categoryScan = new Scan(CATEGORY_TABLE, CATEGORY_COLUMNS, "cat");

		// optimization: push down selection
		Like citySelect = new Like(cityScan, "c.name", name);

		HashJoin cityProjectJoin = new HashJoin(citySelect, projectScan, "c.id", "p.city_id");
		HashJoin categoryCityProjectJoin = new HashJoin(categoryScan, cityProjectJoin, "cat.id", "p.category_id");

		return retrieveProjects(categoryCityProjectJoin);
	}

	@Override
	public List<FundingAmount> getAmountsOfProject(int projectId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User getUserBy(String name) {

		Scan scan = new Scan(USER_TABLE, USER_COLUMNS);
		Select<String> select = new Select<String>(scan, "username", name);
		if (select.moveNext()) {
			Tuple current = select.current();
			User user = new User();
			user.setId(current.getInt(0));
			user.setUsername(current.get(1));
			user.setPassword(current.get(2));
			return user;
		}
		return null;
	}

	@Override
	public User createUser(String username, String password) {
		String id = getNextId(USER_TABLE);
		Tuple tuple = new Tuple(null, id, username, password);
		Insert insert = new Insert(new Container(tuple), USER_TABLE);

		if (insert.moveNextAndClose()) { // successful insert
			return new User(Integer.parseInt(id), username, password);
		}
		return null;
	}

	@Override
	public List<FundingAmount> getFundsByUserId(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void fundProject(int userId, int fundingAmountId) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<City> getAllCities() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public City createCity(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Category createCategory(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Project createProject(Project project) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Category getCategoryById(int id) {
		Scan scan = new Scan(CATEGORY_TABLE, CATEGORY_COLUMNS);
		Select<String> select = new Select<String>(scan, "id", String.valueOf(id));
		Category category = null;
		while(select.moveNext()){ // use while in order to close resources!
			Tuple tuple = select.current();
			category = new Category(tuple.get("name"));
		}
		return category;
	}

	@Override
	public User getUserById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public City getCityById(int id) {
		Scan scan = new Scan(CITY_TABLE, CITY_COLUMNS);
		Select<String> select = new Select<String>(scan, "id", String.valueOf(id));
		City city = null;
		while(select.moveNext()){ // use while in order to close resources!
			Tuple tuple = select.current();
			city = new City(tuple.get("name"));
		}
		return city;
	}

	@Override
	public List<Comment> getCommentsByProjectId(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Comment createComment(Comment comment) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Project> getSoonEndingProjects() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Project> getMostFundedProjects() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Project> getMostPopularProjects() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FundingAmount createFundingAmount(FundingAmount fa) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StretchedGoals createStretchedGoal(StretchedGoals sg) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<StretchedGoals> getStretchedGoals(int projectId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BigDecimal getFundingProgressByProject(int projectId) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Gets the next id for the specified table or 1 if the table doesn't exist
	 * yet! Note: The next id for a table is simply: #amountOfTuples + 1
	 */
	private String getNextId(String tableName) {
		try {
			InputStream inStream = FacesContextUtils
					.getInputStreamToDb(tableName);
			if (inStream != null) {
				LineNumberReader inReader = new LineNumberReader(
						new InputStreamReader(inStream));
				while (inReader.skip(Long.MAX_VALUE) > 0)
					;
				inReader.close();
				return "" + (inReader.getLineNumber() + 1);
			}
		} catch (Exception e) {
			logger.warning("Failed to autoincrement " + tableName);
		}

		return "1";
	}

}
