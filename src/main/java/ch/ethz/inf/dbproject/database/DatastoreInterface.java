package ch.ethz.inf.dbproject.database;

import java.math.BigDecimal;
import java.util.List;

import ch.ethz.inf.dbproject.model.Category;
import ch.ethz.inf.dbproject.model.City;
import ch.ethz.inf.dbproject.model.Comment;
import ch.ethz.inf.dbproject.model.FundingAmount;
import ch.ethz.inf.dbproject.model.Project;
import ch.ethz.inf.dbproject.model.StretchedGoals;
import ch.ethz.inf.dbproject.model.User;

public interface DatastoreInterface {

	public abstract Project getProjectById(int id);

	public abstract List<Project> getAllProjects();

	/**
	 * Returns a list of {@link Project}s that have a similar name
	 */
	public abstract List<Project> getProjectsByName(String name);

	/**
	 * @return {@link Project} with the identical name or null
	 */
	public abstract Project getProjectByName(String name);

	public abstract List<Project> getProjectsByCategory(String name);

	public abstract List<Project> getProjectsByCity(String name);

	public abstract List<FundingAmount> getAmountsOfProject(int projectId);

	public abstract User getUserBy(String name);

	public abstract User createUser(String username, String password);

	public abstract List<FundingAmount> getFundsByUserId(int id);

	public abstract void fundProject(int userId, int fundingAmountId);

	public abstract List<Category> getAllCategories();

	public abstract List<City> getAllCities();

	public abstract City createCity(String name);

	public abstract Category createCategory(String name);

	public abstract Project createProject(Project project);

	public abstract Category getCategoryById(int id);

	public abstract User getUserById(int id);

	public abstract City getCityById(int id);

	public abstract List<Comment> getCommentsByProjectId(int id);

	public abstract Comment createComment(Comment comment);

	public abstract List<Project> getSoonEndingProjects();

	public abstract List<Project> getMostFundedProjects();

	public abstract List<Project> getMostPopularProjects();

	public abstract FundingAmount createFundingAmount(FundingAmount fa);

	public abstract StretchedGoals createStretchedGoal(StretchedGoals sg);

	public abstract List<StretchedGoals> getStretchedGoals(int projectId);

	public abstract BigDecimal getFundingProgressByProject(int projectId);

}