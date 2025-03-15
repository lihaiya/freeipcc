package com.sxx.jcc.core.struts;

import java.util.ArrayList;
import java.util.List;

import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;

@SuppressWarnings("serial")
public abstract class ReportBaseAction<T> extends SimpleActionSupport implements
		ModelDriven<T>, Preparable {
	private List<T> resultList = new ArrayList<T>();
	@Override
	public String execute() throws Exception {
		into();
		return SUCCESS;
	}

	public abstract String into() throws Exception;

	public abstract String show() throws Exception;

	public void prepare() throws Exception {
	}

	public void prepareShow() throws Exception {
		prepareModel();
	}

	protected abstract void prepareModel() throws Exception;

	public List<T> getResultList() {
		return resultList;
	}

	public void setResultList(List<T> resultList) {
		this.resultList = resultList;
	}
}
