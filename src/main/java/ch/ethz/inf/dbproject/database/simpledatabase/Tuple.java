package ch.ethz.inf.dbproject.database.simpledatabase;

/**
 * A tuple in our database. A tuple consists of a schema (describing the names
 * of the columns) and values. A tuple is created and modified by operators.
 * 
 * You can use String to store the values. In case you need a specific type,
 * you can use the additional getType methods.
 */
public class Tuple {

	private final TupleSchema schema;
	private final String[] values;

	public Tuple(final TupleSchema schema, final String[] values) {
		this.schema = schema;
		this.values = values;
	}

	public final TupleSchema getSchema() {
		return this.schema;
	}

	public final String get(final int index) {
		return this.values[index];
	}

	public final short getShort(final int index) {
		return Short.parseShort(this.values[index]);
	}
	
	public final int getInt(final int index) {
		return Integer.parseInt(this.values[index]);
	}
	
	public final float getFloat(final int index) {
		return Float.parseFloat(this.values[index]);
	}
	
	public final double getDouble(final int index) {
		return Double.parseDouble(this.values[index]);
	}

	
	/**
	 * This method generates a comma separated string of all values, which represent this tuple.
	 * The returned String could be used to insert a new tuple into the database. 
	 */
	public final String toString() {
		final StringBuilder buf = new StringBuilder();
		for (int i = 0; i < values.length; i++) {
			buf.append(values[i]);
			if (i < values.length - 1) {
				buf.append(",");
			}
		}
		return buf.toString();
	}

}
