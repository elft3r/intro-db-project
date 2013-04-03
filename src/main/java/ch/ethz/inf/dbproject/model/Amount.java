package ch.ethz.inf.dbproject.model;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Object that represents a funding amount.
 */
public class Amount {

	private final double amount;
	private final String reward;
	
	public Amount(final double amount, final String reward) {
		this.amount = amount;
		this.reward = reward;
	}
	
	public Amount(final ResultSet rs) throws SQLException {
		this.amount = rs.getDouble("amount");
		this.reward = rs.getString("reward");
	}

	public double getAmount() {
		return amount;
	}

	public String getReward() {
		return reward;
	}		
}
