package com.sxx.jcc.core.server.message;

import com.sxx.jcc.common.utils.XKTools;



public class AgentServiceMessage extends Message{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3520656734252136303L;
	
	private String type ;
	private String id = XKTools.getUUID();

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
