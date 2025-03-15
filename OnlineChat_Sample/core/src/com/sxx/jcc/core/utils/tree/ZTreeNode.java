package com.sxx.jcc.core.utils.tree;

import java.util.ArrayList;
import java.util.List;

public class ZTreeNode implements java.io.Serializable{
	private String id;
	private String pid;
	private String name;
	private String value;
	private boolean isParent = false;
	private String icon;
	private String iconSkin;
	private boolean open;
	private String url;
	private boolean checked;
	private boolean nocheck;
	private boolean chkDisabled;
	private boolean doCheck;
	private int disporder = 0;
	private List childNodes;
	private String val1;
	private String val2;
	private String val3;
	private String val4;
	private String val5;
	private String val6;

	public ZTreeNode() {
		childNodes = new ArrayList();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public boolean isParent() {
		return isParent;
	}

	public void setParent(boolean isParent) {
		this.isParent = isParent;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getIconSkin() {
		return iconSkin;
	}

	public void setIconSkin(String iconSkin) {
		this.iconSkin = iconSkin;
	}

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public boolean isNocheck() {
		return nocheck;
	}

	public void setNocheck(boolean nocheck) {
		this.nocheck = nocheck;
	}

	public boolean isChkDisabled() {
		return chkDisabled;
	}

	public void setChkDisabled(boolean chkDisabled) {
		this.chkDisabled = chkDisabled;
	}

	public boolean isDoCheck() {
		return doCheck;
	}

	public void setDoCheck(boolean doCheck) {
		this.doCheck = doCheck;
	}

	public List getChildNodes() {
		return childNodes;
	}

	public void setChildNodes(List childNodes) {
		this.childNodes = childNodes;
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

	public int getDisporder() {
		return disporder;
	}

	public void setDisporder(int disporder) {
		this.disporder = disporder;
	}

}
