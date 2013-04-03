package ch.ethz.inf.dbproject.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A Wrapper around an SQL Connection
 */
public final class MySQLConnection extends DatabaseConnection {

	/**
	 * We use the java logging mechanism
	 */
	private static final Logger logger = Logger.getLogger(MySQLConnection.class.getName());

	/*
	 * The connection parameters. You should change these to point to your installation specific
	 * values.
	 */
	public static final String USERNAME = "dmdb";
	public static final String PASSWORD = "1234";
	public static final String HOSTNAME = "localhost";
	public static final int PORT = 3306;
	public static final String DATABASE = "dmdb2013";

	private final Connection connection;

	MySQLConnection() {
		Connection connection = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://" + HOSTNAME + ":" + PORT + "/" + DATABASE,
					USERNAME, PASSWORD);
		} catch (final SQLException e) {
			logger.log(Level.SEVERE, "Could not connect to MYSQL.", e);
		} catch (ClassNotFoundException e) {
			logger.log(Level.SEVERE, "Could not find the MySQL Driver", e);
		}

		this.connection = connection;
		
		try {
			initDatabase();
		} catch (Exception e) {
			logger.log(Level.WARNING, "Could not initialize database!", e);
		}
	}
	
	@Override
	public final Connection getConnection() {
		return this.connection;
	}
}