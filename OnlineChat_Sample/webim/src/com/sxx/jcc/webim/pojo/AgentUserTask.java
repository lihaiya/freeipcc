package com.sxx.jcc.webim.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="xk_agentuser")
public class AgentUserTask  implements java.io.Serializable{

	private String id ;
	private String userid ;
	private String orgi ;
	
	private int tokenum ;
	
	private String warnings ;
	private Date warningtime ;
	
	private Date logindate;
	private Date servicetime;
	
	private int agentreplyinterval;//回复时间间隔
	private int agentreplytime;
	private int avgreplyinterval;
	private int avgreplytime;
	
	private int agenttimeout;//坐席回复超时时长
	private int agenttimeouttimes;//坐席超时回复次数
	private boolean servicetimeout;//坐席与访客的会话是否超时
	private int agentservicetimeout;//会话超时时长
	private int agentfrewords;//客服提起敏感词次数
	private int servicefrewords;//客户提起敏感词次数
	private int firstreplytime ;//首次消息回复时间
	
	private int filterscript;			//访客段脚本过滤次数
	private int filteragentscript;		//座席端脚本过滤次数
	
	private int sensitiveword;				//访客端敏感词触发次数
	private int sensitivewordagent;			//坐席端敏感词触发次数
	
	private int msgtimeout;				//访客端消息超时次数
	private int msgtimeoutagent;		//坐席端消息敏感词触发次数
	
	private int sessiontimeout;			//会话超时次数
	
	
	private int agentreplys;
	private int userasks;
	
	private Date lastmessage;
	private Date lastgetmessage;
	private String lastmsg;
	
	private String status ;
	
	private Date waittingtimestart;
	
	private Date reptime ;	//坐席长时间未回复 ，由系统发送消息 ，该字段记录 最后一次发送消息的时间 
	private String reptimes ;	//坐席长时间未回复 ，由系统发送消息 ，该字段记录系统发送的次数
	
	@Id
	@Column(length=32)
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy="uuid")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getTokenum() {
		return tokenum;
	}

	public void setTokenum(int tokenum) {
		this.tokenum = tokenum;
	}

	public Date getLastmessage() {
		return lastmessage;
	}

	public void setLastmessage(Date lastmessage) {
		this.lastmessage = lastmessage;
	}

	public Date getWaittingtimestart() {
		return waittingtimestart;
	}

	public void setWaittingtimestart(Date waittingtimestart) {
		this.waittingtimestart = waittingtimestart;
	}

	public Date getLastgetmessage() {
		return lastgetmessage;
	}

	public void setLastgetmessage(Date lastgetmessage) {
		this.lastgetmessage = lastgetmessage;
	}

	public Date getWarningtime() {
		return warningtime;
	}

	public void setWarningtime(Date warningtime) {
		this.warningtime = warningtime;
	}

	public Date getReptime() {
		return reptime;
	}

	public void setReptime(Date reptime) {
		this.reptime = reptime;
	}

	
	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getOrgi() {
		return orgi;
	}

	public void setOrgi(String orgi) {
		this.orgi = orgi;
	}

	public String getWarnings() {
		return warnings;
	}

	public void setWarnings(String warnings) {
		this.warnings = warnings;
	}

	public String getReptimes() {
		return reptimes;
	}

	public void setReptimes(String reptimes) {
		this.reptimes = reptimes;
	}

	public String getLastmsg() {
		return lastmsg;
	}

	public void setLastmsg(String lastmsg) {
		this.lastmsg = lastmsg;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getAgentreplyinterval() {
		return agentreplyinterval;
	}

	public void setAgentreplyinterval(int agentreplyinterval) {
		this.agentreplyinterval = agentreplyinterval;
	}

	public int getAgentreplytime() {
		return agentreplytime;
	}

	public void setAgentreplytime(int agentreplytime) {
		this.agentreplytime = agentreplytime;
	}

	public int getAvgreplyinterval() {
		return avgreplyinterval;
	}

	public void setAvgreplyinterval(int avgreplyinterval) {
		this.avgreplyinterval = avgreplyinterval;
	}

	public int getAvgreplytime() {
		return avgreplytime;
	}

	public void setAvgreplytime(int avgreplytime) {
		this.avgreplytime = avgreplytime;
	}

	public int getAgentreplys() {
		return agentreplys;
	}

	public void setAgentreplys(int agentreplys) {
		this.agentreplys = agentreplys;
	}

	public int getUserasks() {
		return userasks;
	}

	public void setUserasks(int userasks) {
		this.userasks = userasks;
	}

	public int getAgenttimeout() {
		return agenttimeout;
	}

	public void setAgenttimeout(int agenttimeout) {
		this.agenttimeout = agenttimeout;
	}

	public int getAgenttimeouttimes() {
		return agenttimeouttimes;
	}

	public void setAgenttimeouttimes(int agenttimeouttimes) {
		this.agenttimeouttimes = agenttimeouttimes;
	}

	public boolean isServicetimeout() {
		return servicetimeout;
	}

	public void setServicetimeout(boolean servicetimeout) {
		this.servicetimeout = servicetimeout;
	}

	public int getAgentservicetimeout() {
		return agentservicetimeout;
	}

	public void setAgentservicetimeout(int agentservicetimeout) {
		this.agentservicetimeout = agentservicetimeout;
	}

	public int getAgentfrewords() {
		return agentfrewords;
	}

	public void setAgentfrewords(int agentfrewords) {
		this.agentfrewords = agentfrewords;
	}

	public int getServicefrewords() {
		return servicefrewords;
	}

	public void setServicefrewords(int servicefrewords) {
		this.servicefrewords = servicefrewords;
	}

	public int getFirstreplytime() {
		return firstreplytime;
	}

	public void setFirstreplytime(int firstreplytime) {
		this.firstreplytime = firstreplytime;
	}

	public Date getLogindate() {
		return logindate;
	}

	public void setLogindate(Date logindate) {
		this.logindate = logindate;
	}

	public Date getServicetime() {
		return servicetime;
	}

	public void setServicetime(Date servicetime) {
		this.servicetime = servicetime;
	}

	public int getFilterscript() {
		return filterscript;
	}

	public void setFilterscript(int filterscript) {
		this.filterscript = filterscript;
	}

	public int getFilteragentscript() {
		return filteragentscript;
	}

	public void setFilteragentscript(int filteragentscript) {
		this.filteragentscript = filteragentscript;
	}

	public int getSensitiveword() {
		return sensitiveword;
	}

	public void setSensitiveword(int sensitiveword) {
		this.sensitiveword = sensitiveword;
	}

	public int getSensitivewordagent() {
		return sensitivewordagent;
	}

	public void setSensitivewordagent(int sensitivewordagent) {
		this.sensitivewordagent = sensitivewordagent;
	}

	public int getMsgtimeout() {
		return msgtimeout;
	}

	public void setMsgtimeout(int msgtimeout) {
		this.msgtimeout = msgtimeout;
	}

	public int getMsgtimeoutagent() {
		return msgtimeoutagent;
	}

	public void setMsgtimeoutagent(int msgtimeoutagent) {
		this.msgtimeoutagent = msgtimeoutagent;
	}

	public int getSessiontimeout() {
		return sessiontimeout;
	}

	public void setSessiontimeout(int sessiontimeout) {
		this.sessiontimeout = sessiontimeout;
	}
}
