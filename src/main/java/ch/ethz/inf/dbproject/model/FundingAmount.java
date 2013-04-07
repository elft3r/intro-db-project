package ch.ethz.inf.dbproject.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class FundingAmount implements Serializable {

	private static final long serialVersionUID = 3001711922478091154L;

	private int id;
	private BigDecimal amount;
	private String reward;
	private Project Project;

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
		return Project;
	}

	public void setProject(Project project) {
		Project = project;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}