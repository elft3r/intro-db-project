package ch.ethz.inf.dbproject.logic;

import java.io.Serializable;
import java.math.BigDecimal;
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
import ch.ethz.inf.dbproject.model.StretchedGoals;
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
	private List<StretchedGoals> stretchedGoals;

	private BigDecimal newFundingAmount;
	private String newReward;
	
	private BigDecimal newGoal;
	private String newBonus;

	@ManagedProperty(value = "#{sessionData}")
	private SessionData sessionData;

	@PostConstruct
	public void init() {
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		String id = context.getRequestParameterMap().get("id");
		if (StringUtils.isNotNullNorEmpty(id)) {
			projectId = Integer.valueOf(id);
		}

		//TODO do we want to have this in one SQL query?
		selectedProject = dbInterface.getProjectById(getProjectId());
		fundingAmounts = dbInterface.getAmountsOfProject(projectId);
		comments = dbInterface.getCommentsByProjectId(projectId);
		setStretchedGoals(dbInterface.getStretchedGoals(projectId));
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

			// try to create the new comment and if it fails show a message
			if (dbInterface.createComment(comment) == null) {
				throw new Exception("Failed to create a new comment. Please try it again!");
			}
			
			// make sure that when we created the comment successfully we reload the page
			FacesContextUtils.redirect("Project.jsf?id=" + projectId);
		} catch (Exception e) {
			FacesContextUtils.showMessage(e.getMessage());
		}

		return null;
	}

	public String addFundingAmount() {
		try {
			FundingAmount fa = new FundingAmount();
			fa.setAmount(newFundingAmount);
			fa.setProjectId(projectId);
			fa.setReward(newReward);
			
			if(dbInterface.createFundingAmount(fa) == null) {
				throw new Exception("Failed to create the new funding amount. Please try it again!");
			}
			
			// make sure that when we created the new funding amount successfully we reload the page
			FacesContextUtils.redirect("Project.jsf?id=" + projectId);
		} catch (Exception e) {
			FacesContextUtils.showMessage(e.getMessage());
		}

		return null;
	}
	
	public String addStretchedGoal() {
		try {
			StretchedGoals sg = new StretchedGoals();
			sg.setBonus(newBonus);
			sg.setGoal(newGoal);
			sg.setProjectId(projectId);

			if (dbInterface.createStretchedGoal(sg) == null) {
				throw new Exception("Failed to create the new stretched goal. Please try again!");
			}

			// make sure that when we created the new stretched goal successfully we reload the page
			FacesContextUtils.redirect("Project.jsf?id=" + projectId);
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

	public BigDecimal getNewFundingAmount() {
		return newFundingAmount;
	}

	public void setNewFundingAmount(BigDecimal newFundingAmount) {
		this.newFundingAmount = newFundingAmount;
	}

	public String getNewReward() {
		return newReward;
	}

	public void setNewReward(String newReward) {
		this.newReward = newReward;
	}

	public List<StretchedGoals> getStretchedGoals() {
		return stretchedGoals;
	}

	public void setStretchedGoals(List<StretchedGoals> stretchedGoals) {
		this.stretchedGoals = stretchedGoals;
	}

	public BigDecimal getNewGoal() {
		return newGoal;
	}

	public void setNewGoal(BigDecimal newGoal) {
		this.newGoal = newGoal;
	}

	public String getNewBonus() {
		return newBonus;
	}

	public void setNewBonus(String newBonus) {
		this.newBonus = newBonus;
	}
}
