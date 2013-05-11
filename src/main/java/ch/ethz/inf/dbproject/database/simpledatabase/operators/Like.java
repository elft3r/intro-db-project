package ch.ethz.inf.dbproject.database.simpledatabase.operators;

import ch.ethz.inf.dbproject.database.simpledatabase.Tuple;
import ch.ethz.inf.utils.StringUtils;

public class Like extends Operator{

	private Operator operator;
	private String column;
	private String substring;

	public Like(Operator operator, String column, String substring){
		this.operator = operator;
		this.column = column;
		this.substring = substring;
	}
	
	@Override
	public boolean moveNext() {
		while(operator.moveNext()){
			if(accept(operator.current)){
				this.current = operator.current;
				return true;
			}
		}
		return false;
	}

	private boolean accept(Tuple current) {
		return StringUtils.isNullOrEmpty(substring) || current.get(column).indexOf(substring) >= 0;
	}

}
