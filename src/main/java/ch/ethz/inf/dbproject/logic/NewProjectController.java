package ch.ethz.inf.dbproject.logic;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import ch.ethz.inf.dbproject.model.Category;
import ch.ethz.inf.dbproject.model.City;
import ch.ethz.inf.dbproject.model.DatastoreInterface;
import ch.ethz.inf.dbproject.model.Project;
import ch.ethz.inf.utils.FacesContextUtils;
import ch.ethz.inf.utils.StringUtils;

@ManagedBean
@RequestScoped
public class NewProjectController implements Serializable {

	private static final long serialVersionUID = 3957968651102264563L;

	private final DatastoreInterface dbInterface = new DatastoreInterface();

	@ManagedProperty(value = "#{sessionData}")
	private SessionData sessionData;

	private String title;
	private String description;
	private BigDecimal goal;
	private Date startDate;
	private Date endDate;
	private String city;
	private String category;

	// we store the cities and categories so that we can later lookup the id if necessary
	private List<City> cities = null;
	private List<Category> categories = null;

	public String create() {
		try {
			// when the input is valid, add the new project to the DB
			if (isInputValid()) {
				Project project = new Project();
				project.setCategoryId(addCategory().getId());
				project.setCityId(addCity().getId());
				project.setDescription(description);
				project.setEndDate(endDate);
				project.setGoal(goal);
				project.setOwnerId(sessionData.getUser().getId());
				project.setStartDate(startDate);
				project.setTitle(title);

				// now create the project
				project = dbInterface.createProject(project);
				if(project == null) {
					throw new Exception("Failed to create the new Project. Please try again!");
				}
				
				FacesContextUtils.redirect("Project.jsf?id=" + project.getId());
			}
		} catch (Exception e) {
			FacesContextUtils.showMessage(e.getMessage());
		}

		return null;
	}

	private boolean isInputValid() {
		return isTitleValid() && isGoalValid() && areDatesValid() && isCityValid() && isCategoryValid();
	}

	private boolean isTitleValid() {
		boolean res = true;

		if (StringUtils.isNullOrEmpty(title)) {
			FacesContextUtils.showMessage("Please provide a title");
			res = false;
		} else if (dbInterface.getProjectByName(title) != null) {
			FacesContextUtils.showMessage("A project with the same title already exists!");
			res = false;
		}

		return res;
	}

	private boolean isGoalValid() {
		boolean res = true;

		if (goal.signum() == -1) {
			FacesContextUtils.showMessage("Goal cannot be negativ");
			res = false;
		}

		return res;
	}

	private boolean areDatesValid() {
		boolean res = true;

		if (startDate.compareTo(endDate) > 0) {
			FacesContextUtils.showMessage("Start date needs to be before end date");
			res = false;
		}

		return res;
	}

	private boolean isCityValid() {
		boolean res = true;

		if (StringUtils.isNullOrEmpty(city)) {
			FacesContextUtils.showMessage("Please provide a city!");
			res = false;
		}

		return res;
	}

	private boolean isCategoryValid() {
		boolean res = true;

		if (StringUtils.isNullOrEmpty(category)) {
			FacesContextUtils.showMessage("Please provide a category!");
			res = false;
		}

		return res;
	}

	private City addCity() throws Exception {
		City res = null;

		// do we have the City already
		if (cities == null) {
			getCities();
		}
		
		for (City c : cities) {
			if (getCity().equals(c.getName())) {
				res = c;
				break;
			}
		}

		if (res == null) {
			// we need to create the city
			City newCity = dbInterface.createCity(getCity());
			if (newCity == null) {
				throw new Exception("Failed to create the city. Please try again!");
			}

			res = newCity;
		}

		return res;
	}

	private Category addCategory() throws Exception {
		Category res = null;

		// do we have the category already
		if (categories == null) {
			getCategories();
		}

		for (Category c : categories) {
			if (getCategory().equals(c.getName())) {
				res = c;
				break;
			}
		}

		if (res == null) {
			// we need to create the category
			Category newCat = dbInterface.createCategory(getCategory());
			if (newCat == null) {
				throw new Exception("Failed to create the category. Please try again!");
			}

			res = newCat;
		}

		return res;
	}

	/**
	 * Returns all available categories in the form ["Category01, "Category02"], as it is needed in
	 * javascript
	 */
	public String getCategories() {
		// when we do not have the categories yet, we retrieve them from the DB
		if (categories == null) {
			categories = dbInterface.getAllCategories();
		}

		// extract the names of the categories
		List<String> catNames = new ArrayList<>();
		for (Category cat : categories) {
			catNames.add(cat.getName());
		}

		return StringUtils.toJSArray(catNames);
	}

	public String getCities() {
		// when we do not have the cities yet, we retrieve them from the DB
		if (cities == null) {
			cities = dbInterface.getAllCities();
		}

		// now extract the names, so that we can add them as autocompletion on the UI
		List<String> cityNames = new ArrayList<>();
		for (City c : cities) {
			cityNames.add(c.getName());
		}

		return StringUtils.toJSArray(cityNames);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getGoal() {
		return goal;
	}

	public void setGoal(BigDecimal goal) {
		this.goal = goal;
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

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public SessionData getSessionData() {
		return sessionData;
	}

	public void setSessionData(SessionData sessionData) {
		this.sessionData = sessionData;
	}
}
