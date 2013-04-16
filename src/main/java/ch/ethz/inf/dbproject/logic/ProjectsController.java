package ch.ethz.inf.dbproject.logic;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import ch.ethz.inf.dbproject.model.DatastoreInterface;
import ch.ethz.inf.dbproject.model.Project;

@ManagedBean
@RequestScoped
public class ProjectsController {
	
	private final DatastoreInterface dbInterface = new DatastoreInterface();
	
	// The filter parameter defines what to show on the Projects page
	@ManagedProperty(value="#{param.filter}")  // read from request parameter
	private String filter;
	
	@ManagedProperty(value="#{param.category}") // read from request parameter
	private String category;
	
	public List<Project> getProjects(){
		if (filter == null && category == null) {
			// If no filter is specified, then we display all the projects!
			return dbInterface.getAllProjects();

		} else if (category != null) {
			return dbInterface.getProjectsByCategory(category);
		} else if (filter != null) {
		
			if(filter.equals("popular")) {

				// TODO implement this!
				//return dbInterface.getMostPopularProjects();

			} else if (filter.equals("funded")) {

				// TODO implement this!
				// return dbInterface.getMostFundedProjects();

			} else if (filter.equals("ending")) {

				// TODO implement this!
				// return dbInterface.getSoonEndingProjects();

			}
			
		} else {
			throw new RuntimeException("Code should not be reachable!");
		}
		
		return null;
	}

	public String getFilter() {
		return filter;
	}

	public void setFilter(String filter) {
		this.filter = filter;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
}
