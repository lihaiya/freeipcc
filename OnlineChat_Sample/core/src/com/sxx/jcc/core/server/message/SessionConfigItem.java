package com.sxx.jcc.core.server.message;

public class SessionConfigItem implements java.io.Serializable{
	/**
	 * 存放客服服务时间段
	 */
	private static final long serialVersionUID = 3520656734252136303L;
	
	private String workinghours ;
	private String type;
	private String worktype;
	public String getWorkinghours() {
		return workinghours;
	}
	public void setWorkinghours(String workinghours) {
		this.workinghours = workinghours;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getWorktype() {
		return worktype;
	}
	public void setWorktype(String worktype) {
		this.worktype = worktype;
	}
	
}
