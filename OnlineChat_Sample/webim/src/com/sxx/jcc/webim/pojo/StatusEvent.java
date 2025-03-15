package com.sxx.jcc.webim.pojo;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Proxy;

import com.sxx.jcc.common.utils.XKDataContext;
import com.sxx.jcc.common.utils.XKTools;
import com.sxx.jcc.common.utils.event.UserEvent;



@Entity
@Table(name = "uk_callcenter_event")
public class StatusEvent implements Serializable, Comparable<StatusEvent>,UserEvent{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2796276564445713776L;

	private String id ;
	private Date createtime = new Date();
	private Date updatetime = new Date() ;
	
	private boolean inside ;		//是否内线
	private String code ;
	
	private String creater ;		//变更用处，标识是否已接通
	
	private String source ;	//来源
	private String answer ;//应答时间
	
	private boolean callback ;	//是否是回呼
	private String ccquene ;	//队列
	
	private String calltype ;	//呼叫方向类型 | 计费类型
	
	private String voicecalled ;//
	
	private String servicestatus ;	//通话状态
	
	private String channelstatus ;	//通道状态
	
	private Date answertime ;//应答时间
	private int ringduration ;//振铃时长
	
	
	private boolean current ;//
	private boolean forecast ;
	private String skill ;
	private String forecastid;
	
	private boolean init ;//
	private String tracesip ;	//SIP消息记录
	
	private String caller ;//呼叫发起号码
	
	private String calling ;//呼叫对象
	private String called ;//被叫号码
	
	private String discaller;//
	private String discalled;//
	
	private String agentype ;	//callback
	private String quene ;		//呼入队列
	
	private String ani ;//主叫号码
	
	private String touser ;//目标用户
	private String direction ;//呼叫方向
	
	private String calldir ;	//呼叫方向
	private String otherdir;	//对边呼叫方向
	
	private boolean waste ;		//是否作废名单
	private boolean apstatus ;	//是否预约名单
	
	private String otherlegdest ;	//对边呼叫号码
	
	private long time ;///
	private String localdatetime ;//时间发起时间
	
	private Date starttime ;//通话开始时间
	private Date endtime ;//通话结束时间
	
	private int duration ;//通话时长

	
	private String status ;//
	private String state ;//
	private String agent ;//坐席工号
	private String action ;//
	private String name;//
	private String host ;//时间主机
	private String ipaddr ;//主机IP	
	private String sipaddr ;	//SIP 客户端IP
	
	private String extention ;//联系人ID
	private String hostid ;//服务器ID
	
	private String metaname ;		//呼叫名单表名称
	private String taskid ;			//呼叫ID，电销业务产生的
	private String actid ;			//呼叫ID，电销业务产生的
	private String batid ;			//呼叫ID，电销业务产生的
	private String dataid ;			//呼叫ID，电销业务产生的
	private String nameid ;			//名单ID，电销业务产生的
	private String statustype = XKDataContext.StatusTypeEnum.INBOUND.toString();		//呼叫类型 ： 电销业务产生的
	private String disphonenum ;	//是否隐藏号码 
	private String distype ;		//号码隐藏方式
	
	private String busstype ;		//业务类型
	
	private String siptrunk ;		//自定义 SIP Trunk
	private boolean prefix ;		//是否启用加0前缀
	
	private boolean callstatus ;	//拨打状态  ， 成功或失败
	
	
	private String qualitystatus ;//质检状态  ， 已分配/未分配
	private String qualitydisorgan ;	//分配的质检部门
	private String qualitydisuser;		//分配的质检人
	private Date qualitydistime;		//分配的时间
	private String assuser ;			//分配执行人
	private String templateid ;			//分配的质检模板
	private String qualitydistype;				//分配状态  ，未分配not/分配到部门disorgan/分配到坐席disagent
	
	private String qualityorgan ;		//实际的质检部门
	private String qualityuser;			//实际的质检人
	private int qualityscore ;			//质检评分
	private Date qualitytime ;			//质检时间
	private String qualitytype ;			//质检类型
	
	private String qualityactid;	//质检活动id
	private String qualityfilterid;	//质检筛选表单id
	
	
	private boolean record ;//是否录音
	
	private Date startrecord ;//开始录音时间
	private Date endrecord ;//结束录音时间
	private int recordtime ;//录音时长
	private String recordfile ;//开始录音时间
	private String recordfilename ;//结束录音时间
	
	private String contactsid ;//
	
	private String bridgeid ;			//桥接对方ID
	private boolean bridge ;			//是否桥接
	
	private boolean misscall = true;	//是否漏话
	
	private boolean servicesummary ;	//是否记录服务小结
	private String serviceid 	;		//服务小结ID
	
	private int calls ;//
	
	private String orgi ;//
	
	private String country ;	//国家
	private String province ;	//省份
	private String city ;		//城市
	private String isp ;		//运营商
	private boolean satisf ;	//是否记录满意度调查
	private String satisfaction 	;		//满意度评价
	private Date satisfdate ;				//满意度调查提交时间
	
	private String datestr = XKTools.simpleDateFormat.format(new Date());
	private String hourstr = new SimpleDateFormat("HH").format(new Date());
	
	private String userid;
	private String username;
	private String organ;
	
	private boolean dtmf ;		//是否记录了DTMF信息
	private String dtmfrec ;	//DTMF记录
	
	private int trans;		//是否语音转写（0未转写1已转写）
	private Date transbegin;//语音转写开始时间
	private Date transend;	//语音转写结束时间
	private String transtime;//语音转写用时
	private String transtatus;//语音转写状态
	private int transcost;	  //语音转写费用
	private String tranid;//语音转写任务ID
	
	private int qualitypass=2;//质检是否合格(默认2为未质检)
	
	private String workstatus ;
	
	private String hangupcase ;		//挂断原因	
	private String hangupinitiator ;//挂断发起方
	
	public String getWorkstatus() {
		return workstatus;
	}
	public void setWorkstatus(String workstatus) {
		this.workstatus = workstatus;
	}
	private String extno;
	@Transient
	public String getExtno() {
		return extno;
	}
	public void setExtno(String extno) {
		this.extno = extno;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	private String callresult;
	@Id
	@Column(length = 32)
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "assigned")
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getAgent() {
		return agent;
	}
	public void setAgent(String agent) {
		this.agent = agent;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getIpaddr() {
		return ipaddr;
	}
	public void setIpaddr(String ipaddr) {
		this.ipaddr = ipaddr;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public String getCaller() {
		return caller;
	}
	public void setCaller(String caller) {
		this.caller = caller;
	}
	public String getTouser() {
		return touser;
	}
	public void setTouser(String touser) {
		this.touser = touser;
	}
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	public String getLocaldatetime() {
		return localdatetime;
	}
	public void setLocaldatetime(String localdatetime) {
		this.localdatetime = localdatetime;
	}
	public String getCalling() {
		return calling;
	}
	public void setCalling(String calling) {
		this.calling = calling;
	}
	public String getCalled() {
		return called;
	}
	public void setCalled(String called) {
		this.called = called;
	}
	public String getAni() {
		return ani;
	}
	public void setAni(String ani) {
		this.ani = ani;
	}
	public String getAgentype() {
		return agentype;
	}
	public void setAgentype(String agentype) {
		this.agentype = agentype;
	}
	public String getQuene() {
		return quene;
	}
	public void setQuene(String quene) {
		this.quene = quene;
	}
	@Override
	public int compareTo(StatusEvent o) {
		return (int) (o.getTime() - this.getTime());
	}
	@Column(name="scurrent")
	public boolean isCurrent() {
		return current;
	}
	public void setCurrent(boolean current) {
		this.current = current;
	}
	public boolean isInit() {
		return init;
	}
	public void setInit(boolean init) {
		this.init = init;
	}
	public String getOrgi() {
		return orgi;
	}
	public void setOrgi(String orgi) {
		this.orgi = orgi;
	}
	@Transient
	public int getCalls() {
		return calls;
	}
	public void setCalls(int calls) {
		this.calls = calls;
	}
	public Date getStarttime() {
		return starttime;
	}
	public void setStarttime(Date starttime) {
		this.starttime = starttime;
	}
	public Date getEndtime() {
		return endtime;
	}
	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public Date getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}
	@Column(name="srecord")
	public boolean isRecord() {
		return record;
	}
	public void setRecord(boolean record) {
		this.record = record;
	}
	public int getRecordtime() {
		return recordtime;
	}
	public void setRecordtime(int recordtime) {
		this.recordtime = recordtime;
	}
	public boolean isMisscall() {
		return misscall;
	}
	public void setMisscall(boolean misscall) {
		this.misscall = misscall;
	}
	public boolean isInside() {
		return inside;
	}
	public void setInside(boolean inside) {
		this.inside = inside;
	}
	public Date getStartrecord() {
		return startrecord;
	}
	public void setStartrecord(Date startrecord) {
		this.startrecord = startrecord;
	}
	public Date getEndrecord() {
		return endrecord;
	}
	public void setEndrecord(Date endrecord) {
		this.endrecord = endrecord;
	}
	public Date getAnswertime() {
		return answertime;
	}
	public void setAnswertime(Date answertime) {
		this.answertime = answertime;
	}
	public int getRingduration() {
		return ringduration;
	}
	public void setRingduration(int ringduration) {
		this.ringduration = ringduration;
	}
	public boolean isServicesummary() {
		return servicesummary;
	}
	public void setServicesummary(boolean servicesummary) {
		this.servicesummary = servicesummary;
	}
	public String getServiceid() {
		return serviceid;
	}
	public void setServiceid(String serviceid) {
		this.serviceid = serviceid;
	}
	public String getRecordfile() {
		return recordfile;
	}
	public void setRecordfile(String recordfile) {
		this.recordfile = recordfile;
	}
	public boolean isCallback() {
		return callback;
	}
	public void setCallback(boolean callback) {
		this.callback = callback;
	}
	public String getCcquene() {
		return ccquene;
	}
	public void setCcquene(String ccquene) {
		this.ccquene = ccquene;
	}
	public String getServicestatus() {
		return servicestatus;
	}
	public void setServicestatus(String servicestatus) {
		this.servicestatus = servicestatus;
	}
	public String getChannelstatus() {
		return channelstatus;
	}
	public void setChannelstatus(String channelstatus) {
		this.channelstatus = channelstatus;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getIsp() {
		return isp;
	}
	public void setIsp(String isp) {
		this.isp = isp;
	}
	public String getVoicecalled() {
		return voicecalled;
	}
	public void setVoicecalled(String voicecalled) {
		this.voicecalled = voicecalled;
	}
	public String getContactsid() {
		return contactsid;
	}
	public void setContactsid(String contactsid) {
		this.contactsid = contactsid;
	}
	public String getExtention() {
		return extention;
	}
	public void setExtention(String extention) {
		this.extention = extention;
	}
	public String getHostid() {
		return hostid;
	}
	public void setHostid(String hostid) {
		this.hostid = hostid;
	}
	public String getCalltype() {
		return calltype;
	}
	public void setCalltype(String calltype) {
		this.calltype = calltype;
	}
	public String getCalldir() {
		return calldir;
	}
	public void setCalldir(String calldir) {
		this.calldir = calldir;
	}
	public String getOtherdir() {
		return otherdir;
	}
	public void setOtherdir(String otherdir) {
		this.otherdir = otherdir;
	}
	public String getOtherlegdest() {
		return otherlegdest;
	}
	public void setOtherlegdest(String otherlegdest) {
		this.otherlegdest = otherlegdest;
	}
	public String getBridgeid() {
		return bridgeid;
	}
	public void setBridgeid(String bridgeid) {
		this.bridgeid = bridgeid;
	}
	public boolean isBridge() {
		return bridge;
	}
	public void setBridge(boolean bridge) {
		this.bridge = bridge;
	}
	public String getRecordfilename() {
		return recordfilename;
	}
	public void setRecordfilename(String recordfilename) {
		this.recordfilename = recordfilename;
	}
	public String getDiscaller() {
		return discaller;
	}
	public void setDiscaller(String discaller) {
		this.discaller = discaller;
	}
	public String getDiscalled() {
		return discalled;
	}
	public void setDiscalled(String discalled) {
		this.discalled = discalled;
	}
	public boolean isSatisf() {
		return satisf;
	}
	public void setSatisf(boolean satisf) {
		this.satisf = satisf;
	}
	public String getSatisfaction() {
		return satisfaction;
	}
	public void setSatisfaction(String satisfaction) {
		this.satisfaction = satisfaction;
	}
	public Date getSatisfdate() {
		return satisfdate;
	}
	public void setSatisfdate(Date satisfdate) {
		this.satisfdate = satisfdate;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
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
	public String getTaskid() {
		return taskid;
	}
	public void setTaskid(String taskid) {
		this.taskid = taskid;
	}
	public String getActid() {
		return actid;
	}
	public void setActid(String actid) {
		this.actid = actid;
	}
	public String getBatid() {
		return batid;
	}
	public void setBatid(String batid) {
		this.batid = batid;
	}
	public String getDataid() {
		return dataid;
	}
	public void setDataid(String dataid) {
		this.dataid = dataid;
	}
	public String getStatustype() {
		return statustype;
	}
	public void setStatustype(String statustype) {
		this.statustype = statustype;
	}
	public String getDisphonenum() {
		return disphonenum;
	}
	public void setDisphonenum(String disphonenum) {
		this.disphonenum = disphonenum;
	}
	public String getDistype() {
		return distype;
	}
	public void setDistype(String distype) {
		this.distype = distype;
	}
	public String getNameid() {
		return nameid;
	}
	public void setNameid(String nameid) {
		this.nameid = nameid;
	}
	public String getSiptrunk() {
		return siptrunk;
	}
	public void setSiptrunk(String siptrunk) {
		this.siptrunk = siptrunk;
	}
	public boolean isPrefix() {
		return prefix;
	}
	public void setPrefix(boolean prefix) {
		this.prefix = prefix;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getOrgan() {
		return organ;
	}
	public void setOrgan(String organ) {
		this.organ = organ;
	}
	public String getTracesip() {
		return tracesip;
	}
	public void setTracesip(String tracesip) {
		this.tracesip = tracesip;
	}
	public String getBusstype() {
		return busstype;
	}
	public void setBusstype(String busstype) {
		this.busstype = busstype;
	}
	public String getMetaname() {
		return metaname;
	}
	public void setMetaname(String metaname) {
		this.metaname = metaname;
	}
	public boolean isWaste() {
		return waste;
	}
	public void setWaste(boolean waste) {
		this.waste = waste;
	}
	public String getCreater() {
		return creater;
	}
	public void setCreater(String creater) {
		this.creater = creater;
	}
	public boolean isCallstatus() {
		return callstatus;
	}
	public void setCallstatus(boolean callstatus) {
		this.callstatus = callstatus;
	}
	public boolean isApstatus() {
		return apstatus;
	}
	public void setApstatus(boolean apstatus) {
		this.apstatus = apstatus;
	}
	public String getQualitystatus() {
		return qualitystatus;
	}
	public void setQualitystatus(String qualitystatus) {
		this.qualitystatus = qualitystatus;
	}
	public String getQualitydisorgan() {
		return qualitydisorgan;
	}
	public void setQualitydisorgan(String qualitydisorgan) {
		this.qualitydisorgan = qualitydisorgan;
	}
	public String getQualitydisuser() {
		return qualitydisuser;
	}
	public void setQualitydisuser(String qualitydisuser) {
		this.qualitydisuser = qualitydisuser;
	}
	public String getQualityorgan() {
		return qualityorgan;
	}
	public void setQualityorgan(String qualityorgan) {
		this.qualityorgan = qualityorgan;
	}
	public String getQualityuser() {
		return qualityuser;
	}
	public void setQualityuser(String qualityuser) {
		this.qualityuser = qualityuser;
	}
	public int getQualityscore() {
		return qualityscore;
	}
	public void setQualityscore(int qualityscore) {
		this.qualityscore = qualityscore;
	}
	public Date getQualitytime() {
		return qualitytime;
	}
	public void setQualitytime(Date qualitytime) {
		this.qualitytime = qualitytime;
	}
	public String getQualitytype() {
		return qualitytype;
	}
	public void setQualitytype(String qualitytype) {
		this.qualitytype = qualitytype;
	}
	public Date getQualitydistime() {
		return qualitydistime;
	}
	public void setQualitydistime(Date qualitydistime) {
		this.qualitydistime = qualitydistime;
	}
	public String getAssuser() {
		return assuser;
	}
	public void setAssuser(String assuser) {
		this.assuser = assuser;
	}
	public String getTemplateid() {
		return templateid;
	}
	public void setTemplateid(String templateid) {
		this.templateid = templateid;
	}
	public String getQualitydistype() {
		return qualitydistype;
	}
	public void setQualitydistype(String qualitydistype) {
		this.qualitydistype = qualitydistype;
	}
	public String getQualityactid() {
		return qualityactid;
	}
	public void setQualityactid(String qualityactid) {
		this.qualityactid = qualityactid;
	}
	public String getQualityfilterid() {
		return qualityfilterid;
	}
	public void setQualityfilterid(String qualityfilterid) {
		this.qualityfilterid = qualityfilterid;
	}
	public int getTrans() {
		return trans;
	}
	public void setTrans(int trans) {
		this.trans = trans;
	}
	public Date getTransbegin() {
		return transbegin;
	}
	public void setTransbegin(Date transbegin) {
		this.transbegin = transbegin;
	}
	public Date getTransend() {
		return transend;
	}
	public void setTransend(Date transend) {
		this.transend = transend;
	}
	public String getTranstime() {
		return transtime;
	}
	public void setTranstime(String transtime) {
		this.transtime = transtime;
	}
	public String getTranstatus() {
		return transtatus;
	}
	public void setTranstatus(String transtatus) {
		this.transtatus = transtatus;
	}
	public int getTranscost() {
		return transcost;
	}
	public void setTranscost(int transcost) {
		this.transcost = transcost;
	}
	public int getQualitypass() {
		return qualitypass;
	}
	public void setQualitypass(int qualitypass) {
		this.qualitypass = qualitypass;
	}
	public String getTranid() {
		return tranid;
	}
	public void setTranid(String tranid) {
		this.tranid = tranid;
	}
	public boolean isForecast() {
		return forecast;
	}
	public void setForecast(boolean forecast) {
		this.forecast = forecast;
	}
	public String getSkill() {
		return skill;
	}
	public void setSkill(String skill) {
		this.skill = skill;
	}
	public String getForecastid() {
		return forecastid;
	}
	public void setForecastid(String forecastid) {
		this.forecastid = forecastid;
	}
	public String getCallresult() {
		return callresult;
	}
	public void setCallresult(String callresult) {
		this.callresult = callresult;
	}
	public boolean isDtmf() {
		return dtmf;
	}
	public void setDtmf(boolean dtmf) {
		this.dtmf = dtmf;
	}
	public String getDtmfrec() {
		return dtmfrec;
	}
	public void setDtmfrec(String dtmfrec) {
		this.dtmfrec = dtmfrec;
	}
	public String getHangupcase() {
		return hangupcase;
	}
	public void setHangupcase(String hangupcase) {
		this.hangupcase = hangupcase;
	}
	public String getHangupinitiator() {
		return hangupinitiator;
	}
	public void setHangupinitiator(String hangupinitiator) {
		this.hangupinitiator = hangupinitiator;
	}
	public String getSipaddr() {
		return sipaddr;
	}
	public void setSipaddr(String sipaddr) {
		this.sipaddr = sipaddr;
	}
	
}
