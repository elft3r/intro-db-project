package ch.ethz.inf.dbproject.database;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import ch.ethz.inf.utils.StringUtils;

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

			// this is the default setting
			String username = USERNAME;
			String password = PASSWORD;
			String dbUrl = "mysql://" + HOSTNAME + ":" + PORT + "/" + DATABASE;

			// check whether we have an URL we can use to connect to
			String uri = System.getenv("DATABASE_URL");
			if (StringUtils.isNotNullNorEmpty(uri)) {
				URI dbUri = new URI(uri);

				username = dbUri.getUserInfo().split(":")[0];
				password = dbUri.getUserInfo().split(":")[1];
				dbUrl = "mysql://" + dbUri.getHost() + dbUri.getPath();
			}

			connection = DriverManager.getConnection("jdbc:" + dbUrl, username, password);

		} catch (final SQLException e) {
			logger.log(Level.SEVERE, "Could not connect to MYSQL.", e);
		} catch (ClassNotFoundException e) {
			logger.log(Level.SEVERE, "Could not find the MySQL Driver", e);
		} catch (URISyntaxException e) {
			logger.log(Level.SEVERE, "Failed to create a valid URI", e);
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