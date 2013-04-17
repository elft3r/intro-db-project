package ch.ethz.inf.dbproject.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StretchedGoals {

	private int id;
	private BigDecimal goal;
	private String bonus;
	private int projectId;

	public StretchedGoals() {
	}

	public StretchedGoals(ResultSet rs) throws SQLException {
		this.id = rs.getInt("id");
		this.goal = rs.getBigDecimal("goal");
		this.bonus = rs.getString("bonus");
		this.projectId = rs.getInt("project_id");
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public BigDecimal getGoal() {
		return goal;
	}

	public void setGoal(BigDecimal goal) {
		this.goal = goal;
	}

	public String getBonus() {
		return bonus;
	}

	public void setBonus(String bonus) {
		this.bonus = bonus;
	}

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}
}
