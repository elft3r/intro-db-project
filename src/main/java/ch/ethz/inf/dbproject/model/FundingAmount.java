package ch.ethz.inf.dbproject.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class FundingAmount implements Serializable {

	private static final long serialVersionUID = 3001711922478091154L;

	private int id;
	private BigDecimal amount;
	private String reward;
	private Project project;
	private int projectId;

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getReward() {
		return reward;
	}

	public void setReward(String reward) {
		this.reward = reward;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}
}