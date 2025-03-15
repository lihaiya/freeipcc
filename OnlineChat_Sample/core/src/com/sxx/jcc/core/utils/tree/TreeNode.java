package com.sxx.jcc.core.utils.tree;

import java.util.ArrayList;
import java.util.List;

public class TreeNode implements Comparable {
	// 节点ID
	private String id;
	// 节点父ID
	private String pid;
	// 节点显示名称
	private String text;

	private String value;

	private String url;
	private String childNum;
	private String val1;
	private String val2;
	private String val3;
	private String val4;
	private String val5;
	private String val6;
	private String val7;
	private String checkState;
	private boolean showCheck = true;
	private boolean isExpand;
	private boolean hasChildren = true;
	private List childNodes;
	private String id_temp;

	public TreeNode() {
		childNodes = new ArrayList();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getCheckState() {
		return checkState;
	}

	public void setCheckState(String checkState) {
		this.checkState = checkState;
	}

	public boolean isShowCheck() {
		return showCheck;
	}

	public void setShowCheck(boolean showCheck) {
		this.showCheck = showCheck;
	}

	public boolean isExpand() {
		return isExpand;
	}

	public void setExpand(boolean isExpand) {
		this.isExpand = isExpand;
	}

	public boolean isHasChildren() {
		return hasChildren;
	}

	public void setHasChildren(boolean hasChildren) {
		this.hasChildren = hasChildren;
	}

	public List getChildNodes() {
		return childNodes;
	}

	public void setChildNodes(List childNodes) {
		this.childNodes = childNodes;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		return 0;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getVal1() {
		return val1;
	}

	public void setVal1(String val1) {
		this.val1 = val1;
	}

	public String getVal2() {
		return val2;
	}

	public void setVal2(String val2) {
		this.val2 = val2;
	}

	public String getVal3() {
		return val3;
	}

	public void setVal3(String val3) {
		this.val3 = val3;
	}

	public String getVal4() {
		return val4;
	}

	public void setVal4(String val4) {
		this.val4 = val4;
	}

	public String getVal5() {
		return val5;
	}

	public void setVal5(String val5) {
		this.val5 = val5;
	}

	public String getVal6() {
		return val6;
	}

	public void setVal6(String val6) {
		this.val6 = val6;
	}

	public String getChildNum() {
		return childNum;
	}

	public void setChildNum(String childNum) {
		this.childNum = childNum;
	}

	public String getVal7() {
		return val7;
	}

	public void setVal7(String val7) {
		this.val7 = val7;
	}
	
}
