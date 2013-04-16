package ch.ethz.inf.dbproject.logic;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import ch.ethz.inf.dbproject.model.Comment;
import ch.ethz.inf.dbproject.model.DatastoreInterface;
import ch.ethz.inf.dbproject.model.FundingAmount;
import ch.ethz.inf.dbproject.model.Project;
import ch.ethz.inf.utils.FacesContextUtils;
import ch.ethz.inf.utils.StringUtils;

/**
 * This is a hack: Note that a persistent scope such as ViewScope/SessionScoped is required in order
 * to invoke the commandLink inside the datatable. The proper way would be to construct a
 * ProjectModel which contains all the domain objects. But since this is only a school project we
 * will stick to this solution...
 */
@ManagedBean
@ViewScoped
public class ProjectController implements Serializable {

	private static final long serialVersionUID = -6272753030255122907L;

	private DatastoreInterface dbInterface = new DatastoreInterface();
	
	private String newComment;
	private List<Comment> comments;
	private int projectId;
	private Project selectedProject;
	private List<FundingAmount> fundingAmounts;

	@ManagedProperty(value = "#{sessionData}")
	private SessionData sessionData;

	@PostConstruct
	public void init() {
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		String id = context.getRequestParameterMap().get("id");
		if (StringUtils.isNotNullNorEmpty(id)) {
			projectId = Integer.valueOf(id);
		}

		selectedProject = dbInterface.getProjectById(getProjectId());
		fundingAmounts = dbInterface.getAmountsOfProject(projectId);
		comments = dbInterface.getCommentsByProjectId(projectId);
	}

	public Project getSelectedProject() {
		return selectedProject;
	}

	public List<FundingAmount> getFundingAmounts() {
		return fundingAmounts;
	}

	public String support() {
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		String id = context.getRequestParameterMap().get("fundingAmountId");
		if (StringUtils.isNotNullNorEmpty(id)) {
			int fundingAmountId = Integer.valueOf(id);
			dbInterface.fundProject(sessionData.getUser().getId(), fundingAmountId);
		}
		return "User.jsf?faces-redirect=true";
	}

	public String addComment() {
		try {
			// get the user ID out of the session data
			int userId = sessionData.getUser().getId();

			// build a new Comment object
			Comment comment = new Comment();
			comment.setComment(newComment);
			// get the current time
			comment.setCreateDate(new Date(System.currentTimeMillis()));
			comment.setProjectId(projectId);
			comment.setUserId(userId);

			// make sure that no matter what happens we will get redirected to the same page
			FacesContextUtils.redirect("Project.jsf?id=" + projectId);
			
			// try to create the new comment and if it fails show a message
			if (dbInterface.createComment(comment) == null) {
				throw new Exception("Failed to create a new comment. Please try it again!");
			}
		} catch (Exception e) {
			FacesContextUtils.showMessage(e.getMessage());
		}
		
		return null;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	public SessionData getSessionData() {
		return sessionData;
	}

	public void setSessionData(SessionData sessionData) {
		this.sessionData = sessionData;
	}

	public String getNewComment() {
		return newComment;
	}

	public void setNewComment(String newComment) {
		this.newComment = newComment;
	}
}
