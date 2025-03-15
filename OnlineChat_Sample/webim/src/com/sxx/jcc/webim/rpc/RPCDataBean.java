package com.sxx.jcc.webim.rpc;

import java.io.Serializable;

public class RPCDataBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String id ;
	
	private String event ;
	
	private Object data ;
	
	private long start ; 
	
	private String host ;
	
	private int port ;
	
	public RPCDataBean(String id , String event , Object data) {
		this.id = id ;
		this.event = event ;
		this.data = data ;
	}
	
	public RPCDataBean(String id , String host , int port , long start) {
		this.host = host;
		this.port = port;
		this.start = start ;
		this.id = id ;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public long getStart() {
		return start;
	}

	public void setStart(long start) {
		this.start = start;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}
}
