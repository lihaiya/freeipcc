package com.sxx.jcc.core.dao.hibernate;

import com.sxx.jcc.core.dao.hibernate.PropertyFilter.MatchType;
import com.sxx.jcc.core.dao.hibernate.PropertyFilter.OperatorType;

public class QueryConditionFilter {
	private String column;
	private Object value;
	private MatchType mathType;
	private OperatorType operatorType;
	
	public QueryConditionFilter(String column, Object value) {
		this(column,value,MatchType.LIKE,null);
	}

	public QueryConditionFilter(String column, Object value, MatchType mathType) {
		this(column,value,mathType,null);
	}
	
	public QueryConditionFilter(String column, Object value, MatchType mathType,OperatorType operatorType) {
		this.column = column;
		this.value = value;
		this.mathType = mathType;
		this.operatorType = operatorType;
	}

	public String getColumn() {
		return column;
	}

	public void setColumn(String column) {
		this.column = column;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	
	public OperatorType getOperatorType() {
		return operatorType;
	}

	public void setOperatorType(OperatorType operatorType) {
		this.operatorType = operatorType;
	}

	public MatchType getMathType() {
		return mathType;
	}

	public void setMathType(MatchType mathType) {
		this.mathType = mathType;
	}
}