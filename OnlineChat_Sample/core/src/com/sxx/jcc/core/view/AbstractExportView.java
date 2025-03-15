package com.sxx.jcc.core.view;

import java.util.List;

import com.sxx.jcc.core.taglib.model.HtmlColumn;


public abstract class AbstractExportView implements View {
	private String caption;
	private List<?> items;
	private List<HtmlColumn> columns;

	protected AbstractExportView(String caption, List<?> items,List<HtmlColumn> columns) {
        this.caption = caption;
        this.items = items;
        this.columns=columns;
    }
	
	public List<HtmlColumn> getColumns() {
		return columns;
	}

	public void setColumns(List<HtmlColumn> columns) {
		this.columns = columns;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public List<?> getItems() {
		return items;
	}

	public void setItems(List<?> items) {
		this.items = items;
	}

}
