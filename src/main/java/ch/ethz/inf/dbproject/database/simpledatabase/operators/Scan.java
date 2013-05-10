package ch.ethz.inf.dbproject.database.simpledatabase.operators;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import ch.ethz.inf.dbproject.database.simpledatabase.Tuple;
import ch.ethz.inf.dbproject.database.simpledatabase.TupleSchema;

/**
 * The scan operator reads tuples from a file. The lines in the file contain the
 * values of a tuple. The line a comma separated.
 */
public class Scan extends Operator {

	private static final Logger logger = Logger.getLogger(Scan.class.getName());

	private TupleSchema schema;
	private Scanner scanner;

	/**
	 * Contructs a new scan operator.
	 * 
	 * @param tableName
	 *            file to read tuples from
	 */
	public Scan(final String tableName, String[] columnNames) {

		String fileName = "localdb" + File.pathSeparator + tableName + ".txt";
		
		// create schema
		this.schema = new TupleSchema(columnNames);

		try {
			scanner = new Scanner(new File(fileName));
		} catch (final FileNotFoundException e) {
			logger.log(Level.WARNING, "could not find file " + fileName);
		}
	}

	@Override
	public boolean moveNext() {

		if (scanner == null) // in case that the file doesn't exist
			return false;

		try {

			if (scanner.hasNextLine()) {
				String nextLine = scanner.nextLine();
				String[] values = nextLine.split(",");
				current = new Tuple(schema, values);
				return true;
			}
			scanner.close();
		} catch (Exception e) {
			logger.warning("failed to close scanner");
		}
		return false;
	}
}
