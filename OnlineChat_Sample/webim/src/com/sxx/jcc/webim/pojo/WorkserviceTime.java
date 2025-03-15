package com.sxx.jcc.webim.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "xk_workservice_time")
public class WorkserviceTime implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1115593425069549681L;
	
	private String id ;
	private String timetype ;//日期类型（字典项 com.dic.workservice.time）
	private String scope ; //日期范围类型（单天 one/范围 more/ 星期 week）
	private String begin ; //日期开始    格式   2018-9-3
	private String end ; //日期结束     格式   2018-9-3
	private Date createtime ;
	private String creater;
	private Date updatetime ;
	private String orgi ;
	private String apply;//适用场景（文字客服 webim / 排班 sche）
	private String week;
	
	public WorkserviceTime(){}
	
	public WorkserviceTime(String id){
		this.id = id ;
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

	public String getTimetype() {
		return timetype;
	}

	public void setTimetype(String timetype) {
		this.timetype = timetype;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getBegin() {
		return begin;
	}

	public void setBegin(String begin) {
		this.begin = begin;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	public Date getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}

	public String getOrgi() {
		return orgi;
	}

	public void setOrgi(String orgi) {
		this.orgi = orgi;
	}

	public String getApply() {
		return apply;
	}

	public void setApply(String apply) {
		this.apply = apply;
	}

	public String getWeek() {
		return week;
	}

	public void setWeek(String week) {
		this.week = week;
	}
	


}
