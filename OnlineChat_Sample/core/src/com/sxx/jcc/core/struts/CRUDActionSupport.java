package com.sxx.jcc.core.struts;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.sxx.jcc.core.dao.hibernate.Page;
import com.sxx.jcc.core.exception.AppBaseException;
import com.sxx.jcc.core.taglib.model.HtmlColumn;
import com.sxx.jcc.core.utils.Constant;
import com.sxx.jcc.core.view.ExcelView;
import com.sxx.jcc.core.view.ExcelViewExporter;
import com.sxx.jcc.core.view.ExportType;
import com.sxx.jcc.core.view.View;

@SuppressWarnings("serial")
public abstract class CRUDActionSupport<T> extends SimpleActionSupport {

	
	public static final String RELOAD = "reload";
	public static final String APPERROR = "appError";
	
	protected List<HtmlColumn> columns;

	protected String exportName;

	
	private Long id;

	//public abstract String input() throws Exception;


	public abstract String save() throws Exception;


	public abstract String delete() throws Exception;


	protected void renderExport(View view,ExportType exportType) {
		try {
			if (exportType == ExportType.EXCEL) {
				new ExcelViewExporter(view, super.getRespose(), exportName).export();
			}
		} catch (Exception e) {
			logger.error("Not able to perform the " + exportType + " export.");
		}
	}

	protected abstract void initColumns();

	public List<HtmlColumn> getColumns() {
		return columns;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}



}