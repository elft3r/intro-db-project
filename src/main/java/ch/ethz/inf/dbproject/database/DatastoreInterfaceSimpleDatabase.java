package ch.ethz.inf.dbproject.database;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import ch.ethz.inf.dbproject.database.simpledatabase.Tuple;
import ch.ethz.inf.dbproject.database.simpledatabase.operators.Scan;
import ch.ethz.inf.dbproject.database.simpledatabase.operators.Select;
import ch.ethz.inf.dbproject.model.Category;
import ch.ethz.inf.dbproject.model.City;
import ch.ethz.inf.dbproject.model.Comment;
import ch.ethz.inf.dbproject.model.FundingAmount;
import ch.ethz.inf.dbproject.model.Project;
import ch.ethz.inf.dbproject.model.StretchedGoals;
import ch.ethz.inf.dbproject.model.User;


public final class DatastoreInterfaceSimpleDatabase implements DatastoreInterface {

	@Override
	public final Project getProjectById(final int id) {
	
		/**
		 * TODO this method should return the product with the given id
		 */
		final Scan scan = new Scan("projects.txt", 
			new String[] {
				"id",
				"name",
				"field2",
				"field3"
			}
		);
		
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Project getProjectByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Project> getProjectsByCategory(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Project> getProjectsByCity(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<FundingAmount> getAmountsOfProject(int projectId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User getUserBy(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User createUser(String username, String password) {
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User getUserById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public City getCityById(int id) {
		// TODO Auto-generated method stub
		return null;
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
}
