package ch.ethz.inf.dbproject.logic;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import ch.ethz.inf.dbproject.model.Amount;
import ch.ethz.inf.dbproject.model.Comment;
import ch.ethz.inf.dbproject.model.DatastoreInterface;
import ch.ethz.inf.dbproject.model.Project;

@ManagedBean
@RequestScoped
public class ProjectController {

	private DatastoreInterface dbInterface = new DatastoreInterface();
	
	private String comment;

	@ManagedProperty(value="#{param.id}")  // read from request parameter
	private int projectId;
	
	
	public Project getSelectedProject(){
		return dbInterface.getProjectById(getProjectId());
	}
	
	public List<Amount> getPaymentAmounts(){
		/*******************************************************
		 * Construct a table for all payment amounts
		 *******************************************************/
		//TODO implement this
		//List<Amount> comments = this.dbInterface.getAmountsOfProject(projectId);
		//Create a table to display the amounts the same way as above
		//The table needs to have a link column the allows a registered user to fund that amount
		//We need to catch the click a store the funding in the database
		//session.setAttribute("amountTable", table);
		
		return new ArrayList<Amount>();
	}
	
	
	public String addComment(){

		// TODO implement this
		//String username = UserManagement.getCurrentlyLoggedInUser(session).getUsername();
		//Comment commentObj = new Comment(username, comment);
		
		//TODO implement this
		//this.dbInterface.addCommentForProject(id, commentObj);
		
		return "Project.jsf?id=" + getProjectId();
	}
	
	public List<Comment> getComments(){
		//TODO implement this
		//List<Comment> comments = this.dbInterface.getCommentsOfProject(id);
		//Create a table to display the comments the same way as above	
		//session.setAttribute("commentTable", table);
		
		return new ArrayList<Comment>();
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}
}
