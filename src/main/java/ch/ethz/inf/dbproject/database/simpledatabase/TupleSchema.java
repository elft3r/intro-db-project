package ch.ethz.inf.dbproject.database.simpledatabase;

import java.util.HashMap;

import ch.ethz.inf.utils.StringUtils;

/**
 * The schema contains meta data about a tuple. So far we only store the name of
 * each column. Other meta data, such cardinalities, indexes, etc. could be
 * specified here.
 */
public class TupleSchema {

	private final String[] columnNames;
	private final HashMap<String, Integer> columnNamesMap;

	/**
	 * Constructs a new tuple schema.
	 * 
	 * @param columnNames
	 *            column names
	 */
	public TupleSchema(final String[] columnNames) {
		this(columnNames, null);
	}
	
	/**
	 * Constructs a new tuple schema.
	 * 
	 * @param columnNames
	 *            column names
	 * @param prefix
	 *            the prefix of the column names (just required for join
	 *            operation in order to distinguish columns with belong to
	 *            different tables but share the same name)
	 */
	public TupleSchema(final String[] columnNames, String prefix) {
		this.columnNames = new String[columnNames.length];
		this.columnNamesMap = new HashMap<String, Integer>();

		for (int i = 0; i < columnNames.length; ++i) {
			String columnName = columnNames[i].toUpperCase();
			if (StringUtils.isNotNullNorEmpty(prefix)) {
				columnName = prefix.toUpperCase() + "." + columnName;
			}
			this.columnNames[i] = columnName;
			this.columnNamesMap.put(columnName, i);
		}
	}

	/**
	 * Given the name of a column, returns the index in the respective tuple.
	 * 
	 * @param column
	 *            column name
	 * @return index of column in tuple
	 */
	public int getIndex(final String column) {

		final Integer index = this.columnNamesMap.get(column.toUpperCase());
		if (index == null) {
			return -1; // error
		} else {
			return index;
		}
	}

	public String[] getColumnNames() {
		return columnNames;
	}

}
