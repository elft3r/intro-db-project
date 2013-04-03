package ch.ethz.inf.dbproject.database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import ch.ethz.inf.utils.SqlUtils;

public abstract class DatabaseConnection {

	private static final Logger logger = Logger.getLogger(DatabaseConnection.class.getName());
	
	private static final String DB_SCHEMA = "schema.sql";
	private static final String DB_DATA = "data.sql";

	/**
	 * We use this enumeration to indicate which DB we want to use
	 */
	public enum DBType {
		/**
		 * This is the DB we need to code in the second part of the project
		 */
		HOMEBREW("Homebrew Database"),

		/**
		 * In the first part we use a normal MySQL DB
		 */
		MySQL("MySQL Database");

		private DBType(String type) {
			this.type = type;
		}

		public String type;
	}

	/**
	 * Singleton instance: We want to avoid re-establishing connections across web server requests.
	 */
	private static DatabaseConnection instance = null;

	public static DatabaseConnection getMySQLInstance() throws Exception {
		return getInstance(DBType.MySQL);
	}

	public static DatabaseConnection getHomebrewInstance() throws Exception {
		return getInstance(DBType.HOMEBREW);
	}

	public static synchronized DatabaseConnection getInstance(DBType type) throws Exception {
		// for now we use as Default the MySQL DB
		// TODO change the default to the Homebrew DB as soon as we have it
		if (type == null) {
			type = DBType.MySQL;
		}

		if (instance == null) {
			switch (type) {
				case MySQL:
					instance = new MySQLConnection();
					break;
				case HOMEBREW:
					// TODO Implement this
					// break;
				default:
					throw new Exception("The DBType was not properly set!");
			}
		} else {
			if (DBType.MySQL.equals(type) && !(instance instanceof MySQLConnection)) {
				throw new Exception("The available instance is not of type MySQLConnection.");
			}

			// TODO extend this check as soon as we have the homebrew version
		}

		return instance;
	}

	public void initDatabase() throws Exception {
		// we only initialize the db if it is not already done
		if (!tablesArePresent()) {
			// first we need to retrieve the commands to create the DB
			String[] dbSchemaCommands = SqlUtils
					.parseSqlScript(DatabaseConnection.class.getResourceAsStream(DB_SCHEMA));
			String[] dbDataCommands = SqlUtils.parseSqlScript(DatabaseConnection.class.getResourceAsStream(DB_DATA));
			
			// first we add the tables to the DB
			try (Statement stmt = getConnection().createStatement();) {
				// first we create the schema
				for (String command : dbSchemaCommands) {
					stmt.execute(command);
				}
			} catch (Exception e) {
				logger.log(Level.SEVERE, "Failed to create database!", e);
			}
			
			// then we add some data to the tables
			try (Statement stmt = getConnection().createStatement();) {
				// then we add the data
				for (String command : dbDataCommands) {
					stmt.execute(command);
				}
			} catch (Exception e) {
				logger.log(Level.SEVERE, "Failed to add data to the database tables!", e);
			}
		}
	}

	private boolean tablesArePresent() throws Exception {
		boolean res = false;

		// Check whether at least one table is present, if so we assume that all tables are present
		DatabaseMetaData metaData = getConnection().getMetaData();
		ResultSet result = metaData.getTables(null, null, null, new String[] { "TABLE" });
		if (result.next()) {
			res = true;
		}

		return res;
	}

	public abstract Connection getConnection();
}
