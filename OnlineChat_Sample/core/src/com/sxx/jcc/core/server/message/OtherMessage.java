package com.sxx.jcc.core.server.message;

import java.util.List;

public class OtherMessage implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3520656734252136303L;
	
	private String msgtype ;
	private String title;
	private String message ;
	private String type ;
	private String id ;
	private boolean trans;
	private String code ;
	private String score ;
	private boolean detail ;
	private String matchtype ;
	
	private String ckind ;
	private String ckindname ;
	
	private String clabel ;
	private String clabelname ;
	
	private int duration ;
	
	private List<OtherMessageItem> items ;
	
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
	public List<OtherMessageItem> getItems() {
		return items;
	}
	public void setItems(List<OtherMessageItem> items) {
		this.items = items;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public boolean isDetail() {
		return detail;
	}
	public void setDetail(boolean detail) {
		this.detail = detail;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public boolean isTrans() {
		return trans;
	}
	public void setTrans(boolean trans) {
		this.trans = trans;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getMatchtype() {
		return matchtype;
	}
	public void setMatchtype(String matchtype) {
		this.matchtype = matchtype;
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
