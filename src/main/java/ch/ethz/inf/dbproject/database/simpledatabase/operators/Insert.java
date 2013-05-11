package ch.ethz.inf.dbproject.database.simpledatabase.operators;

import java.io.PrintWriter;

import com.sun.istack.internal.logging.Logger;

import ch.ethz.inf.utils.FacesContextUtils;

/**
 * This implementation of the insert operator allows to insert multiple tuples by
 * providing an additional operator which gets the next tuple to insert by invoking
 * operator.moveNext().
 */
public class Insert extends Operator {
	
	private static final Logger logger = Logger.getLogger(Insert.class);
	
	private Operator operator;
	private PrintWriter writer;

	public Insert(Operator operator, String tableName){
		this.operator = operator;
		try{
			writer = FacesContextUtils.getOutputStreamToDb(tableName);
		}catch(Exception e){
			// ignore
		}
	}

	@Override
	public boolean moveNext() {
		if(writer == null)
			return false;
		
		if(operator.moveNext()){
			current = operator.current;
			
			// insert current tuple element
			writer.println(current);
			logger.info("insert tuple: " + current);
			return true;
		}
		
		writer.close();
		return false;
	}
	
	/**
	 * This is a convenient method when only a single tuple has to be inserted!
	 */
	public boolean moveNextAndClose(){
		boolean moveNext = moveNext();
		if(writer != null){
			writer.close();
		}
		return moveNext;
	}
}
