package com.sxx.jcc.webim.pojo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Proxy;

@Entity
@Table(name = "xk_message_leave")
@Proxy(lazy = false)
public class MessageLeave implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = MessageLeave.class.hashCode();
	private String appid;
	private String id;
	private String msgDetail;
    private String msgEmail;
    private String userid;
    private Date createDate;
    
	@Id
	@Column(length = 32)
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	public String getId() {
		return id;
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMsgDetail() {
		return msgDetail;
	}

	public void setMsgDetail(String msgDetail) {
		this.msgDetail = msgDetail;
	}

	public String getMsgEmail() {
		return msgEmail;
	}

	public void setMsgEmail(String msgEmail) {
		this.msgEmail = msgEmail;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
}
