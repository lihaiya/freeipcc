package com.sxx.jcc.webim.pojo;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.sxx.jcc.common.utils.XKTools;


@Entity
@Table(name = "xk_webim_monitor")
public class AgentReport implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5931219598388385394L;
	
	private String id ;
	private Date createtime = new Date();
	private int agents ;		//坐席数量
	private int readyagents ;	//就绪坐席
	private int incall;			//通话中
	private int users ;			//服务中的用户
	private int inquene ;		//队列中的用户
	private int busy ;			//队列中忙的坐席
	private String orgi;
	
	private String worktype ;
	private String workresult ;
	private String dataid ;
	
	private String datestr = XKTools.simpleDateFormat.format(new Date());
	private String hourstr = new SimpleDateFormat("HH").format(new Date());
	private String datehourstr = new SimpleDateFormat("yyyy-MM-dd HH").format(new Date());
	public String getOrgi() {
		return orgi;
	}
	public void setOrgi(String orgi) {
		this.orgi = orgi;
	}
	private String type = "status";	//坐席状态
	
	public int getAgents() {
		return agents;
	}
	public void setAgents(int agents) {
		this.agents = agents;
	}
	public int getUsers() {
		return users;
	}
	public void setUsers(int users) {
		this.users = users;
	}
	public int getInquene() {
		return inquene;
	}
	public void setInquene(int inquene) {
		this.inquene = inquene;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getBusy() {
		return busy;
	}
	public void setBusy(int busy) {
		this.busy = busy;
	}
	@Id
	@Column(length = 32)
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public String getDatestr() {
		return datestr;
	}
	public void setDatestr(String datestr) {
		this.datestr = datestr;
	}
	public String getHourstr() {
		return hourstr;
	}
	public void setHourstr(String hourstr) {
		this.hourstr = hourstr;
	}
	public String getDatehourstr() {
		return datehourstr;
	}
	public void setDatehourstr(String datehourstr) {
		this.datehourstr = datehourstr;
	}
	public String getWorktype() {
		return worktype;
	}
	public void setWorktype(String worktype) {
		this.worktype = worktype;
	}
	public String getWorkresult() {
		return workresult;
	}
	public void setWorkresult(String workresult) {
		this.workresult = workresult;
	}
	public String getDataid() {
		return dataid;
	}
	public void setDataid(String dataid) {
		this.dataid = dataid;
	}
	public int getReadyagents() {
		return readyagents;
	}
	public void setReadyagents(int readyagents) {
		this.readyagents = readyagents;
	}
	public int getIncall() {
		return incall;
	}
	public void setIncall(int incall) {
		this.incall = incall;
	}
}
