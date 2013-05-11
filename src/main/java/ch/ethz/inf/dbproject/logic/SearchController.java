package ch.ethz.inf.dbproject.logic;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import ch.ethz.inf.dbproject.database.DatastoreInterface;
import ch.ethz.inf.dbproject.database.DatastoreInterfaceMySQL;
import ch.ethz.inf.dbproject.database.DatastoreInterfaceSimpleDatabase;
import ch.ethz.inf.dbproject.model.Project;

@ManagedBean
@RequestScoped
public class SearchController {

	private DatastoreInterface dbInterface = new DatastoreInterfaceSimpleDatabase();

	private String name;
	private String category;
	private String city;

	private List<Project> projects = new ArrayList<Project>();

	public String search() {

		if (name != null) {
			projects = dbInterface.getProjectsByName(name);
		} else if (category != null) {
			projects = dbInterface.getProjectsByCategory(category);
		} else if (city != null) {
			projects = dbInterface.getProjectsByCity(city);
		}

		return "Search.jsf";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public List<Project> getProjects() {
		return projects;
	}

	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}

}
