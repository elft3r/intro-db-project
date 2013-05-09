package ch.ethz.inf.dbproject.logic;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import ch.ethz.inf.dbproject.database.DatastoreInterface;
import ch.ethz.inf.dbproject.model.Category;

@ManagedBean
@RequestScoped
public class HomeController {

	private DatastoreInterface dbInterface = new DatastoreInterface();
	
	public List<Category> getAllCategories() {
		return dbInterface.getAllCategories();
	}
}
