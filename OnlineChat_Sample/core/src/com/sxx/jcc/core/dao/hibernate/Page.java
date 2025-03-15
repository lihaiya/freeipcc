package com.sxx.jcc.core.dao.hibernate;


import java.util.List;

import com.sxx.jcc.core.view.ExportType;


public class Page extends QueryParameter {

	private List result = null;

	private long totalCount = -1;

	private ExportType exportType;
	
	public Page() {
	}

	public Page(int pagesize) {
		this.pagesize = pagesize;
	}

	public Page(int startIndex,int pagesize, long totalCount,List result) {
		this.page = startIndex;
		this.pagesize = pagesize;
		this.totalCount = totalCount;
		this.result = result;
	}

	/**
	 * 取得倒转的排序方向
	 */
	public String getInverseOrder() {
		if (sortorder.endsWith(DESC))
			return ASC;
		else
			return DESC;
	}

	/**
	 * 页内的数据列表.
	 */
	public List getResult() {
		return result;
	}

	public void setResult(List result) {
		this.result = result;
	}

	/**
	 * 总记录数.
	 */
	public long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}

	/**
	 * 计算总页数.
	 */
	public int getTotalPages() {
		if (totalCount == -1)
			return -1;

		int count = (int) (totalCount / pagesize);
		if (totalCount % pagesize > 0) {
			count++;
		}
		return count;
	}

	/**
	 * 是否还有下一页.
	 */
	public boolean isHasNext() {
		return (page + 1 <= getTotalPages());
	}

	/**
	 * 返回下页的页号,序号从1开始.
	 */
	public int getNextPage() {
		if (isHasNext())
			return page + 1;
		else
			return page;
	}

	/**
	 * 是否还有上一页. 
	 */
	public boolean isHasPre() {
		return (page - 1 >= 1);
	}

	/**
	 * 返回上页的页号,序号从1开始.
	 */
	public int getPrePage() {
		if (isHasPre())
			return page - 1;
		else
			return page;
	}
	
	public ExportType getExportType() {
		return exportType;
	}

	public void setExportType(String exportType) {
		if ("EXCEL".equalsIgnoreCase(exportType)) {
			this.exportType = ExportType.EXCEL;
		}
	}
	public static int getStartOfPage(int pageNo, int pageSize) {
		return (pageNo - 1) * pageSize;
	}
}
