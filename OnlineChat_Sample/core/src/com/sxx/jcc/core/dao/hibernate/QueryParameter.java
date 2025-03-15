package com.sxx.jcc.core.dao.hibernate;

public class QueryParameter {

	public static final String ASC = "asc";
	public static final String DESC = "desc";

	protected int page = 1;
	protected int pagesize = -1;
	protected String sortname = "";
	protected String sortorder = ASC;
	protected boolean autoCount = true;

	
	/**
	 * 根据pageNo和pageSize计算当前页第一条记录在总结果集中的位置,序号从0开始.
	 */
	public int getFirst() {
		if (page < 1 || pagesize < 1)
			return -1;
		else
			return ((page - 1) * pagesize);
	}
	
	/**
	 * 是否已设置第一条记录记录在总结果集中的位置.
	 */
	public boolean isFirstSetted() {
		return (page > 0 && pagesize > 0);
	}

	/**
	 * 是否自动获取总页数,默认为false.
	 * 注意本属性仅于query by Criteria时有效,query by HQL时本属性无效.
	 */
	public boolean isAutoCount() {
		return autoCount;
	}

	public void setAutoCount(boolean autoCount) {
		this.autoCount = autoCount;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPagesize() {
		return pagesize;
	}

	public void setPagesize(int pagesize) {
		this.pagesize = pagesize;
	}

	public String getSortname() {
		return sortname;
	}

	public void setSortname(String sortname) {
		this.sortname = sortname;
	}

	public String getSortorder() {
		return sortorder;
	}

	public void setSortorder(String sortorder) {
		this.sortorder = sortorder;
	}
	
	
}