package com.sxx.jcc.core.server.message;


public class OtherMessageItem implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3520656734252136303L;
	
	private String msgtype ;
	private String title;
	private String id ;
	private String content ;
	private String headimg ;	//图文消息
	private String type ;		//图文消息类型
	private String url ;		//图文消息跳转URL
	
	
	private String ckind ;
	private String ckindname ;
	
	private String clabel ;
	private String clabelname ;
	
	private int duration ;
	
	public String getMsgtype() {
		return msgtype;
	}
	public void setMsgtype(String msgtype) {
		this.msgtype = msgtype;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getId() {
		return id != null ? id : "";
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getHeadimg() {
		return headimg;
	}
	public void setHeadimg(String headimg) {
		this.headimg = headimg;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	public String getCkind() {
		return ckind;
	}
	public void setCkind(String ckind) {
		this.ckind = ckind;
	}
	public String getCkindname() {
		return ckindname;
	}
	public void setCkindname(String ckindname) {
		this.ckindname = ckindname;
	}
	public String getClabel() {
		return clabel;
	}
	public void setClabel(String clabel) {
		this.clabel = clabel;
	}
	public String getClabelname() {
		return clabelname;
	}
	public void setClabelname(String clabelname) {
		this.clabelname = clabelname;
	}
}
