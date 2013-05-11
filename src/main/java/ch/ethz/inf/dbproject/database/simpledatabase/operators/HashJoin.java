package ch.ethz.inf.dbproject.database.simpledatabase.operators;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ch.ethz.inf.dbproject.database.simpledatabase.Tuple;
import ch.ethz.inf.dbproject.database.simpledatabase.TupleSchema;

/**
 * Note that in order to join correctly it is important that the left table is the one with a unique
 * column! The current implementation stores all tuples from the left table into a hash map with 
 * the specified leftColumnName - Value as key. It would be better if a List of Tuples would be
 * stored in the Hash Map, so that the order of the join tables doesn't matter..
 */
public class HashJoin extends Operator {

	private Operator left;
	private Operator right;
	private String leftColumnName;
	private String rightColumnName;

	private Map<String, Tuple> joinMap = new HashMap<>();

	public HashJoin(Operator left, Operator right, String leftColumnName, String rightColumnName) {
		this.left = left;
		this.right = right;
		this.leftColumnName = leftColumnName;
		this.rightColumnName = rightColumnName;
	}

	/**
	 * Return the next joined tuple
	 */
	@Override
	public boolean moveNext() {

		// add all tuples of the left relation
		while (left.moveNext()) {
			Tuple leftTuple = left.current;
			int leftIndex = leftTuple.getSchema().getIndex(leftColumnName);
			joinMap.put(leftTuple.get(leftIndex), leftTuple);
		}

		// iterate until we find a match between those two relations
		while (right.moveNext()) {
			Tuple rightTuple = right.current;
			int rightIndex = rightTuple.getSchema().getIndex(rightColumnName);

			// match: left.leftColumn = right.rightColumn
			if (joinMap.containsKey(rightTuple.get(rightIndex))) {
				Tuple leftTuple = joinMap.get(rightTuple.get(rightIndex));

				// merge tuple
				this.current = mergeTuples(rightTuple, leftTuple);
				return true;
			}
		}
		return false;
	}

	private Tuple mergeTuples(Tuple rightTuple, Tuple leftTuple) {
		String[] leftColumnNames = leftTuple.getSchema().getColumnNames();
		String[] rightColumnNames = rightTuple.getSchema().getColumnNames();

		List<String> joinedColumnValues = new ArrayList<>();
		List<String> joinedColumnNames = new ArrayList<>();

		for (int i = 0; i < leftColumnNames.length; i++) {
			String leftColumnName = leftColumnNames[i];
			joinedColumnNames.add(leftColumnName);
			joinedColumnValues.add(leftTuple.get(i));
		}

		for (int i = 0; i < rightColumnNames.length; i++) {
			String rightColumnName = rightColumnNames[i];
			joinedColumnNames.add(rightColumnName);
			joinedColumnValues.add(rightTuple.get(i));
		}

		String[] columnNames = joinedColumnNames.toArray(new String[1]);
		String[] columnValues = joinedColumnValues.toArray(new String[1]);
		return new Tuple(new TupleSchema(columnNames, null), columnValues);
	}
}
