package ch.ethz.inf.utils;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.Test;

public class SqlUtilsTest {
	
	// location of the SQL files we need for testing
	private static final String PATH_TO_SQL_SCRIPT = "src" + File.separatorChar + "test" + File.separatorChar
			+ "resources" + File.separatorChar
			+ SqlUtilsTest.class.getPackage().getName().replace('.', File.separatorChar);
	
	// the file we need for the parseSqlScriptTest
	private static final String PARSE_SQL_SCRIPT_FILE = "schema.sql";
	
	/*
	 * The single lines of the 'schema.sql' file
	 */
	private static final String line01 = "CREATE DATABASE dmdb2013";
	private static final String line02 = "CREATE TABLE user (\n"
			+ "	id			INTEGER PRIMARY KEY AUTO_INCREMENT,\n"
			+ "	username	VARCHAR(255) NOT NULL UNIQUE,\n"
			+ "	password	CHAR(128) NOT NULL\n"
			+ ")";
	private static final String line03 = "CREATE TABLE category (\n"
			+ "	id		INTEGER PRIMARY KEY AUTO_INCREMENT,\n"
			+ "	name	VARCHAR(255) NOT NULL UNIQUE\n"
			+ ")";
	private static final String line04 = "CREATE TABLE city (\n"
			+ "	id		INTEGER PRIMARY KEY AUTO_INCREMENT,\n"
			+ "	name	VARCHAR(255) NOT NULL UNIQUE\n"
			+ ")";
	
	@Test
	public void parseSqlScriptTest() throws Exception {
		String[] array = SqlUtils.parseSqlScript(PATH_TO_SQL_SCRIPT + File.separatorChar + PARSE_SQL_SCRIPT_FILE);
		
		assertEquals("We exepect four different SQL command", 4, array.length);
		
		// now check the single lines
		assertEquals(line01, array[0]);
		assertEquals(line02, array[1]);
		assertEquals(line03, array[2]);
		assertEquals(line04, array[3]);
	}
}
