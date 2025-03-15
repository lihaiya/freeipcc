package com.sxx.jcc.core.taglib.ui;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ui.AbstractUITag;

import com.sxx.jcc.core.taglib.bean.PageTable;
import com.opensymphony.xwork2.util.ValueStack;

public class pageTableTag extends AbstractUITag {

	private static final long serialVersionUID = pageTableTag.class.hashCode();
	// core attributes
	private String caption;
	private String captionKey;
	private String action;


	// style attributes
	private String tableRenderer;
	private String width;
	private String border;
	private String columns;
	private String results;
	private String exports;
	private String selectType;
	private String deleteDisable;
	private String operatorDisable;
	

	public Component getBean(ValueStack stack, HttpServletRequest req,
			HttpServletResponse res) {
		return new PageTable(stack, req, res);
	}

	/* (non-Javadoc)
	 * @see org.apache.struts2.views.jsp.ui.AbstractUITag#populateParams()
	 */
	protected void populateParams() {
		super.populateParams();

		PageTable pageTable = ((PageTable) component);
		pageTable.setCaption(caption);
		pageTable.setCssClass(cssClass);
		pageTable.setCaptionKey(captionKey);
		pageTable.setAction(action);
		pageTable.setId(id);
		pageTable.setTableRenderer(tableRenderer);
		pageTable.setWidth(width);
		pageTable.setBorder(border);
		pageTable.setName(name);
		pageTable.setColumns(columns);
		pageTable.setResults(results);
		pageTable.setExports(exports);
		pageTable.setSelectType(selectType);
		pageTable.setDeleteDisable(deleteDisable);
		pageTable.setOperatorDisable(operatorDisable);
	}

	public String getExports() {
		return exports;
	}

	public void setExports(String exports) {
		this.exports = exports;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public String getCaptionKey() {
		return captionKey;
	}

	public void setCaptionKey(String captionKey) {
		this.captionKey = captionKey;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getTableRenderer() {
		return tableRenderer;
	}

	public void setTableRenderer(String tableRenderer) {
		this.tableRenderer = tableRenderer;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getBorder() {
		return border;
	}

	public void setBorder(String border) {
		this.border = border;
	}

	public String getColumns() {
		return columns;
	}

	public void setColumns(String columns) {
		this.columns = columns;
	}
	
	public String getResults() {
		return results;
	}

	public void setResults(String results) {
		this.results = results;
	}

	public String getSelectType() {
		return selectType;
	}

	public void setSelectType(String selectType) {
		this.selectType = selectType;
	}

	public String getDeleteDisable() {
		return deleteDisable;
	}

	public void setDeleteDisable(String deleteDisable) {
		this.deleteDisable = deleteDisable;
	}

	public String getOperatorDisable() {
		return operatorDisable;
	}

	public void setOperatorDisable(String operatorDisable) {
		this.operatorDisable = operatorDisable;
	}
	
}
