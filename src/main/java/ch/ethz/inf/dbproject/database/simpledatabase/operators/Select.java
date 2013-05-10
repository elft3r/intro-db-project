package ch.ethz.inf.dbproject.database.simpledatabase.operators;

import ch.ethz.inf.dbproject.database.simpledatabase.Tuple;

/**
 * Selection in relational algebra. Only returns those tuple for which the given
 * column matches the value.
 * 
 * This class is a generic to allow comparing any types of values.
 * 
 * i.e. SELECT * FROM USERS WHERE USER_ID=1 would require these operators:
 * 
 * Scan usersScanOperator = new Scan(filename, columnNames); Select<Integer>
 * selectOp = new Select<Integer>( usersScanOperator, "user_id", 1);
 * 
 * Similarly, this query:
 * 
 * SELECT * FROM USERS WHERE USENAME=john would require these operators:
 * 
 * Scan usersScanOperator = new Scan(filename, columnNames); Select<String>
 * selectOp = new Select<String>( usersScanOperator, "username", "john");
 * 
 */
public class Select<T> extends Operator {

	private final Operator op;
	private final String column;
	private final T compareValue;

	/**
	 * Contructs a new selection operator.
	 * 
	 * @param op
	 *            operator to pull from
	 * @param column
	 *            column name that gets compared
	 * @param compareValue
	 *            value that must be matched
	 */
	public Select(final Operator op, final String column, final T compareValue) {
		this.op = op;
		this.column = column;
		this.compareValue = compareValue;
	}

	private final boolean accept(final Tuple tuple) {
		final int columnIndex = tuple.getSchema().getIndex(this.column);
		return tuple.get(columnIndex).equals(this.compareValue.toString());
	}

	@Override
	public boolean moveNext() {
		while(op.moveNext()){ // loop until we either find a match or op has no more tuples
			Tuple candidate = op.current(); 
			if(accept(candidate)){ // check if our predicate accepts this candidate
				current = candidate;
				return true;
			}
		}
		return false;
	}

}
