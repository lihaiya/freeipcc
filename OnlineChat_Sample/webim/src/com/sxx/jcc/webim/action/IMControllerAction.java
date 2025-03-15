package com.sxx.jcc.webim.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.sxx.jcc.common.action.BaseAction;
import com.sxx.jcc.common.utils.XKDataContext;
import com.sxx.jcc.common.utils.XKTools;
import com.sxx.jcc.core.cache.SystemCache;
import com.sxx.jcc.system.pojo.AgentUser;
import com.sxx.jcc.system.pojo.SysStaff;
import com.sxx.jcc.webim.pojo.BlackEntity;
import com.sxx.jcc.webim.pojo.ChatMessage;
import com.sxx.jcc.webim.pojo.MessageLeave;
import com.sxx.jcc.webim.service.IAgentStatusService;

import net.sf.json.JSONArray;



@Controller
@Scope("prototype")
@ParentPackage(value = "struts-default")
@Namespace("/im")
public class IMControllerAction extends BaseAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = IMControllerAction.class.hashCode();

	@Value("${xk.im.server.host}")  
    private String host;  
  
    @Value("${xk.im.server.port}")  
    private Integer port; 
    
    private String userid;
    private String appid;
    private String sessionid;
    private String orgi;
    private String lvtype;
    private String username;
    private String msg;
    private String orgid;
    private String msgDetail;
    private String msgEmail;
    @Resource
	private IAgentStatusService agentStatusService;
    
    private List<ChatMessage> chatMessageList = new ArrayList<ChatMessage>();
    @Action(value = "login", results = { @Result(name = SUCCESS, location = "/pages/im/point.jsp") })
	public String login() throws Exception {
    	
    	HttpServletRequest request = ServletActionContext.getRequest();
    	sessionid = request.getSession().getId();
    	orgi = XKDataContext.SYSTEM_ORGI;
    	if(StringUtils.isBlank(userid)){
    		userid = XKTools.genIDByKey(sessionid);	
    	}
    	String data  = (String)SystemCache.fetchCacheDate(SystemCache.CacheServiceEnum.AGENT_STATUS_CACHE.toString());
    	System.out.print(data);
		return SUCCESS;
	}

    @Action(value = "chatindex", results = { @Result(name = SUCCESS, location = "/pages/im/index.jsp"),
	@Result(name = "leavemsg",location="/pages/im/leavemsg.jsp")})
	public String chatindex() throws Exception {
    	BlackEntity black = null ;
    	if(StringUtils.isNotBlank(orgi)){
    		orgi= XKDataContext.SYSTEM_ORGI;
    	}
    	appid = this.getOrgid();
    	//System.out.println("---------------------$$$$"+orgid);
    	HttpServletRequest request = ServletActionContext.getRequest();
    	sessionid = request.getSession().getId();
    	userid = XKTools.genIDByKey(sessionid);	
    	
    	if(StringUtils.isBlank(userid)) {
    		//black = (BlackEntity) CacheHelper.getSystemCacheBean().getCacheObject(userid, orgi) ;
    	}
    	
    	if(StringUtils.isBlank(appid)){
    		appid="1111111111111";
    	}
    	
    	//if(StringUtils.isNotBlank(appid) &&  (black == null || (black.getEndtime()!=null && black.getEndtime().before(new Date()))) ){
		//SessionConfig sessionConfig = ServiceQuene.initSessionConfig(orgi) ;
		//SessionConfig sessionConfig = null;
		String userID = null;
		if(StringUtils.isNotBlank(userid)){
			userID = XKTools.genIDByKey(userid) ;
		}else{
			userid = sessionid;
			userID = XKTools.genIDByKey(sessionid);	
		}
		//username = "Guest_" + userID;
		/**ChatMessage cmsg = new ChatMessage();
		String test="中文测试";
		System.out.println(test);
		cmsg.setUsername("座席001");
		cmsg.setCalltype("in");
		cmsg.setMessage("May I help you?");
		
		chatMessageList.add(cmsg);
		cmsg = new ChatMessage();
		cmsg.setUsername("Guest_001");
		cmsg.setCalltype("out");
		cmsg.setMessage("东西打折吗?");
		chatMessageList.add(cmsg);**/
		//AgentReport report = ServiceQuene.getAgentReport(orgi) ;
    	/**if(report.getAgents() ==0 ||  (sessionConfig.isHourcheck() && !UKTools.isInWorkingHours(sessionConfig,"webim"))){
			if(report.getAgents() == 0) {
				lvtype = "noagent";
			}else {
				lvtype = "notinworktime";
			}
			//return "leavemsg";
		}**/
    	return SUCCESS;
	}
    
	@Action(value = "leaveMsg")
	public void leaveMsg() throws Exception{
		setResponseParas();
		//SysStaff staff = (SysStaff)request.getSession().getAttribute(XKDataContext.STAFF_SESSION);
		MessageLeave ml = new MessageLeave();
		ml.setAppid(this.getAppid());
		ml.setCreateDate(new Date());
		ml.setMsgDetail(this.getMsgDetail());
		ml.setMsgEmail(this.getMsgEmail());
		ml.setUserid(this.getUserid());
		try{
			agentStatusService.save(ml);
			response.getWriter().print("{\"isSuccess\":true}");
		}catch (Exception e){
			response.getWriter().print("{\"isSuccess\":false}");
		}
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
	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getSessionid() {
		return sessionid;
	}

	public void setSessionid(String sessionid) {
		this.sessionid = sessionid;
	}

	public String getOrgi() {
		return orgi;
	}

	public void setOrgi(String orgi) {
		this.orgi = orgi;
	}

	public String getLvtype() {
		return lvtype;
	}

	public void setLvtype(String lvtype) {
		this.lvtype = lvtype;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public List<ChatMessage> getChatMessageList() {
		return chatMessageList;
	}

	public void setChatMessageList(List<ChatMessage> chatMessageList) {
		this.chatMessageList = chatMessageList;
	}

	public String getOrgid() {
		return orgid;
	}

	public void setOrgid(String orgid) {
		this.orgid = orgid;
	}

	public IAgentStatusService getAgentStatusService() {
		return agentStatusService;
	}

	public void setAgentStatusService(IAgentStatusService agentStatusService) {
		this.agentStatusService = agentStatusService;
	}
    
}
