package ch.ethz.inf.dbproject.database.simpledatabase;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A tuple in our database. A tuple consists of a schema (describing the names
 * of the columns) and values. A tuple is created and modified by operators.
 * 
 * You can use String to store the values. In case you need a specific type, you
 * can use the additional getType methods.
 */
public class Tuple {

	private final TupleSchema schema;
	private final String[] values;
	private static final SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public Tuple(final TupleSchema schema, final String... values) {
		this.schema = schema;
		this.values = values;
	}

	public final TupleSchema getSchema() {
		return this.schema;
	}

	public final String get(final int index) {
		return this.values[index];
	}

	public final String get(final String column) {
		return get(schema.getIndex(column));
	}

	public final short getShort(final int index) {
		return Short.parseShort(this.values[index]);
	}

	public final int getInt(final int index) {
		return Integer.parseInt(this.values[index]);
	}

	public final int getInt(final String column) {
		return getInt(schema.getIndex(column));
	}

	public final float getFloat(final int index) {
		return Float.parseFloat(this.values[index]);
	}

	public final double getDouble(final int index) {
		return Double.parseDouble(this.values[index]);
	}

	public final Date getDate(final int index) {
		try {
			return dateFormatter.parse(this.values[index]);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public final Date getDate(final String column) {
		return getDate(schema.getIndex(column));
	}
	
	public final BigDecimal getBigDecimal(final int index){
		return new BigDecimal(values[index]);
	}
	
	public BigDecimal getBigDecimal(String column) {
		return getBigDecimal(schema.getIndex(column));
	}

	/**
	 * This method generates a comma separated string of all values, which
	 * represent this tuple. The returned String could be used to insert a new
	 * tuple into the database.
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
