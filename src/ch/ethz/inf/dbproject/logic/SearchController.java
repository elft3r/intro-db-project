package ch.ethz.inf.dbproject.logic;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import ch.ethz.inf.dbproject.model.DatastoreInterface;
import ch.ethz.inf.dbproject.model.Project;

@ManagedBean
@RequestScoped
public class SearchController {
	
	private DatastoreInterface dbInterface = new DatastoreInterface();
	
	private String name;
	private String category;
	private String city;
	
	private List<Project> projects = new ArrayList<Project>();

	public String search(){

		if(name != null) {

			// TODO implement this!
			// projects.add(this.dbInterface.searchByName(name));
			
		} else if (category != null) {

			// TODO implement this!
			// projects.add(this.dbInterface.searchByName(name));
		} else if (city != null) {

			// TODO implement this!
			// city.add(this.dbInterface.searchByName(name));
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
