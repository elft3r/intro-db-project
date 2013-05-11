package ch.ethz.inf.dbproject.database.simpledatabase.operators;

import ch.ethz.inf.dbproject.database.simpledatabase.Tuple;

/**
 * This class acts as a static container in order to process multiple tuples.
 * E.g. to insert an arbitrary amount of tuples.
 */
public class Container extends Operator {
	
	private Tuple[] tuples;
	int index;

	public Container(Tuple... tuples){
		this.tuples = tuples;
	}

	@Override
	public boolean moveNext() {
		if(index < tuples.length){
			current = tuples[index++];
			return true;
		}
		return false;
	}

}
