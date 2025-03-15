package com.sxx.jcc.webim.action;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.sxx.jcc.common.action.BaseAction;
import com.sxx.jcc.common.utils.XKDataContext;
import com.sxx.jcc.core.cache.SystemCache;
import com.sxx.jcc.core.dao.hibernate.Page;
import com.sxx.jcc.system.pojo.AgentService;
import com.sxx.jcc.system.pojo.AgentUser;
import com.sxx.jcc.system.pojo.SessionConfig;
import com.sxx.jcc.system.pojo.SysStaff;
import com.sxx.jcc.system.service.ISysAgentService;
import com.sxx.jcc.system.service.ISysOnlineUserService;
import com.sxx.jcc.webim.pojo.AgentServiceSummary;
import com.sxx.jcc.webim.pojo.AgentStatus;
import com.sxx.jcc.webim.pojo.AgentUserTask;
import com.sxx.jcc.webim.pojo.ChatMessage;
import com.sxx.jcc.webim.pojo.MessageLeave;
import com.sxx.jcc.webim.pojo.OnlineUser;
import com.sxx.jcc.webim.pojo.QuickReply;
import com.sxx.jcc.webim.pojo.QuickType;
import com.sxx.jcc.webim.service.IAgentStatusService;
import com.sxx.jcc.webim.service.IAgentUserTaskService;
import com.sxx.jcc.webim.service.IChatMessageService;
import com.sxx.jcc.webim.service.IQuickReplyService;
import com.sxx.jcc.webim.service.IQuickTypeService;
import com.sxx.jcc.webim.service.ISummaryService;
import com.sxx.jcc.webim.service.ISysAgentUserService;
import com.sxx.jcc.webim.service.ServiceQuene;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@Scope("prototype")
@ParentPackage(value = "default")
@Namespace("/agent")
public class AgentController extends BaseAction {

	@Resource
	private ISysAgentUserService sysAgentUserService;
	
	@Resource
	private ISysAgentService sysAgentService;
	
	@Resource
	private IChatMessageService chatMessageService;

	@Autowired
	private ISysOnlineUserService sysOnlineUserService;

	@Resource
	private IAgentUserTaskService agentUserTaskService;
	@Resource
	private IQuickReplyService  quickReplyService;
	@Resource
	private IQuickTypeService quickTypeService;
	@Resource
	private IAgentStatusService agentStatusService;
	
	@Resource
	private ISummaryService summaryService;
	
	private String path;
	private int serviceCount;
	private AgentService agentService;
	private AgentUser currentAgentUser;
	private String agentUserid;
	private OnlineUser onlineUser;
	private List<AgentServiceSummary> summaryList;
	private List<ChatMessage> agentUserMessageList;
	private List<QuickReply> quickReplyList; 
	private List<QuickType> pubQuickTypeList;
	private List<AgentUser> agentUserList = new ArrayList<AgentUser>();
	private AgentUser curagentuser;
	private String startdate;
	private String enddate;
    
	@Action(value = "agentindex", results = { @Result(name = SUCCESS, location = "/pages/agent/index.jsp")})
	public String agentIndex() throws Exception {
		SysStaff staff = (SysStaff)request.getSession().getAttribute(XKDataContext.STAFF_SESSION);
		//List<AgentUser> agentUserList = new ArrayList<AgentUser>();
		agentUserList = sysAgentUserService.findByAgentnoAndStatusAndOrgi(staff.getWcode(), "");

		SessionConfig sessionConfig = ServiceQuene.initSessionConfig("");
		/**AgentUser agentUserf = new AgentUser();
		agentUserf.setUsername("Guest_0cwx1d");
		agentUserf.setUserid("0cwx1d");
		agentUserf.setStatus("end");
		agentUserf.setId("001");
		agentUserf.setServicetime(new Date());
		agentUserList.add(agentUserf);
		agentUserf = new AgentUser();
		agentUserf.setUsername("Guest_0cwx1f");
		agentUserf.setUserid("0cwx1f");
		agentUserf.setServicetime(new Date());
		agentUserf.setId("002");
		agentUserList.add(agentUserf);**/
		if (agentUserList.size() > 0) {
			AgentUser agentUser = agentUserList.get(0);
			curagentuser = agentUser;
			//agentUser = (AgentUser) agentUserList.get(0);
			//view.addObject("curagentuser", agentUser);
			if (StringUtils.isNotBlank(agentUser.getAgentserviceid())) {
				/*
				 * AgentServiceSummary summary =
				 * this.serviceSummaryRes.findByAgentserviceidAndOrgi(agentUser.
				 * getAgentserviceid(), super.getOrgi(request)) ;
				 * if(summary!=null){ view.addObject("summary", summary) ; }
				 */
				summaryList = new ArrayList<AgentServiceSummary>();
				//this.serviceSummaryRes.findByOrgiAndUserid(agentUser.getUserid());
				// Page<AgentServiceSummary> summaryList =
				// this.serviceSummaryRes.findByOrgiAndAgentserviceid(super.getOrgi(request),
				// agentUser.getAgentserviceid(),new PageRequest(0,
				// super.getPs(request), Sort.Direction.DESC, new String[] {
				// "createtime" }));
			}

			if (sessionConfig.isOtherquickplay() && StringUtils.isNotBlank(sessionConfig.getOqrsearchurl())) {
				 //OnlineUserUtils.search(null, super.getOrgi(request), super.getUser(request),agentUser.getAppid());
			}
			/**agentUserMessageList = new ArrayList<ChatMessage>();
			ChatMessage chatMessage = new ChatMessage();
			chatMessage.setCalltype("");
			chatMessage.setUsername("Guest_0dv01i");
			chatMessage.setMessage("发票会过期吗？");
			chatMessage.setCreatetime("2019/06/15 14:50");
			agentUserMessageList.add(chatMessage);
			chatMessage = new ChatMessage();
			chatMessage.setCalltype("out");
			chatMessage.setUsername("客服");
			chatMessage.setMessage("不会");
			chatMessage.setCreatetime("2019/06/15 14:58");
			agentUserMessageList.add(chatMessage);**/
			agentUserMessageList = this.getChatMessageService().findChatMessageList(curagentuser.getUserid(),"20");
			//view.addObject("agentUserMessageList",this.chatMessageRepository.findByUsessionAndOrgi(agentUser.getUserid(), super.getOrgi(request),new PageRequest(0, 20, Direction.DESC, "updatetime")));
			if (StringUtils.isNotBlank(agentUser.getAgentserviceid())) {
				agentService = sysAgentService.findAgentService(agentUser.getAgentserviceid());
				//view.addObject("curAgentService", agentService);
				if (agentService != null) {
					/**
					 * 获取关联数据
					 */
					//processRelaData(request, agentService, map);
					//map.addAttribute("dataid", agentService.getId());
				}
			}

			List<OnlineUser> onlineUserList = this.sysOnlineUserService.findOnlineUserByUserid(agentUser.getUserid());//获取咨询用户
			if (CollectionUtils.isNotEmpty(onlineUserList) && onlineUserList.size() > 0) {
				OnlineUser onlineUser = onlineUserList.get(0);
				if (XKDataContext.OnlineUserOperatorStatus.OFFLINE.toString().equals(onlineUser.getStatus())) {
					onlineUser.setBetweentime((int) (onlineUser.getUpdatetime().getTime() - onlineUser.getLogintime().getTime()));
				} else {
					onlineUser.setBetweentime((int) (System.currentTimeMillis() - onlineUser.getLogintime().getTime()));
				}
			}
			
			serviceCount = sysAgentService.countByUseridAndOrgiAndStatus(agentUser.getUserid(), XKDataContext.AgentUserStatusEnum.END.toString());

			//quickReplyList = quickReplyService.findByOrgiAndCreater(staff.getStaffId());
			//List<QuickType> quickTypeList = quickTypeService.findByOrgiAndQuicktype(XKDataContext.QuickTypeEnum.PUB.toString());
			
			//List<QuickType> priQuickTypeList = quickTypeService.findByOrgiAndQuicktypeAndCreater(XKDataContext.QuickTypeEnum.PRI.toString(), staff.getStaffId());
			
			//quickTypeList.addAll(priQuickTypeList);
			//pubQuickTypeList = quickTypeList;
			// 文字客服
			/**SysDic sysDic = sysDicRes.findByCode("sessionWords");
			if (agentService != null && sysDic != null) {
				List<SessionType> sessionTypeList = sessionTypeRes.findByOrgiAndCtype(super.getOrgi(request),
						sysDic.getId());
				for (SessionType ses : sessionTypeList) {
					if (!StringUtils.isBlank(agentService.getSessiontype())
							&& ses.getId().equals(agentService.getSessiontype())) {
						map.addAttribute("agentSessionType", ses.getName());
					}
				}
			}**/

		}
		return SUCCESS;
	}
	
	
	@Action(value = "ready")
    public void ready() throws IOException{ 
		setResponseParas();
		SysStaff staff = (SysStaff)request.getSession().getAttribute(XKDataContext.STAFF_SESSION);
		String id = request.getParameter("id");
    	List<AgentStatus> agentStatusList = agentStatusService.findByAgentnoAndOrgi(staff.getWcode());
    	
    	
    	AgentStatus agentStatus = null ;
    	if(agentStatusList.size() > 0){
    		agentStatus = agentStatusList.get(0) ;
    	}else{
    		agentStatus = new AgentStatus() ;
	    	//agentStatus.setUserid(staff.getStaffId());
	    	agentStatus.setUsername(staff.getWcode());
	    	agentStatus.setAgentno(staff.getWcode());
	    	agentStatus.setLogindate(new Date());
	    	
	    	agentStatus.setUpdatetime(new Date());
	    	SessionConfig sessionConfig = ServiceQuene.initSessionConfig("") ;
	    	
	    	agentStatus.setUsers(agentStatusService.countByAgentnoAndStatusAndOrgi(staff.getStaffId(), XKDataContext.AgentUserStatusEnum.INSERVICE.toString()));
	    	
	    	agentStatus.setOrgi(XKDataContext.SYSTEM_ORGI);
	    	agentStatus.setMaxusers(sessionConfig.getMaxuser());
	    	agentStatusService.save(agentStatus) ;
    	}
    	if(agentStatus!=null){
	    	/**
	    	 * 更新当前用户状态
	    	 */
    		agentStatus.setBusy(false);
	    	agentStatus.setUsers(ServiceQuene.getAgentUsers(agentStatus.getAgentno(),""));
	    	agentStatus.setStatus(XKDataContext.AgentStatusEnum.READY.toString());
	    	//SystemCache.put(agentStatus.getAgentno(), agentStatus);
	    	SystemCache.putAgentStatus(agentStatus);
	    	
	    	ServiceQuene.allotAgent(agentStatus.getAgentno(), "");
	    	
	    	ServiceQuene.recordAgentStatus(agentStatus.getAgentno(),agentStatus.getUsername(), agentStatus.getAgentno(), agentStatus.getSkill() ,agentStatus.getAgentno(), XKDataContext.AgentStatusEnum.OFFLINE.toString(), XKDataContext.AgentStatusEnum.READY.toString(), XKDataContext.AgentWorkType.MEIDIACHAT.toString() , agentStatus.getOrgi() , null);
    	}
    	
    	response.getWriter().print("{\"reuslt\":\"s\"}");
    }
	
	@Action(value = "notready")
    public void notready() throws IOException{ 
		SysStaff staff = (SysStaff)request.getSession().getAttribute(XKDataContext.STAFF_SESSION);
		if(staff!=null) {
			ServiceQuene.deleteAgentStatus(staff.getWcode(), "");
		}
		response.getWriter().print("{\"reuslt\":\"s\"}");
    }
	
	@Action(value = "busy")
    public void busy() throws IOException{ 
		SysStaff staff = (SysStaff)request.getSession().getAttribute(XKDataContext.STAFF_SESSION);
		List<AgentStatus> agentStatusList = agentStatusService.findByAgentnoAndOrgi(staff.getWcode());
		AgentStatus agentStatus = null ;
    	if(agentStatusList.size() > 0){
    		agentStatus = agentStatusList.get(0) ;
			agentStatus.setBusy(true);
			ServiceQuene.recordAgentStatus(agentStatus.getAgentno(),agentStatus.getUsername(), agentStatus.getAgentno(), agentStatus.getSkill(),agentStatus.getAgentno(), XKDataContext.AgentStatusEnum.READY.toString(), XKDataContext.AgentStatusEnum.BUSY.toString(), XKDataContext.AgentWorkType.MEIDIACHAT.toString() , agentStatus.getOrgi() , agentStatus.getUpdatetime());
			agentStatus.setUpdatetime(new Date());
			agentStatusService.save(agentStatus);
			SystemCache.put(agentStatus.getAgentno(), agentStatus);
		}
    	ServiceQuene.publishMessage("", "agent" , "busy" , staff.getWcode());
    	
    	response.getWriter().print("{\"reuslt\":\"s\"}");
    }
	
	@Action(value = "end")
	public void end() throws Exception {
		SysStaff staff = (SysStaff)request.getSession().getAttribute(XKDataContext.STAFF_SESSION);
		AgentUser agentUser = sysAgentUserService.findAgentUserById(agentUserid);
		if(agentUser!=null && staff.getWcode().equals(agentUser.getAgentno())){
			ServiceQuene.deleteAgentUser(agentUser, "" , XKDataContext.EndByType.AGENT.toString());
			if(StringUtils.isNotBlank(agentUser.getAgentserviceid())){
				AgentService agentService = sysAgentService.findAgentService(agentUser.getAgentserviceid()) ;
				agentService.setStatus(XKDataContext.AgentUserStatusEnum.END.toString());
				sysAgentService.save(agentService) ;
			}
		}
		response.getWriter().print("{\"reuslt\":\"s\"}");
	}
	
	@Action(value = "readmsg", results = { @Result(name = SUCCESS, location = "/pages/agent/index.jsp")})
	public String readmsg()
			throws Exception {
		List<AgentUserTask> agentUserTaskList = agentUserTaskService.findAgentUserId(agentUserid);
		if(agentUserTaskList.size() > 0){
			AgentUserTask agentUserTask = agentUserTaskList.get(0) ;
			agentUserTask.setTokenum(0);
			agentUserTaskService.save(agentUserTask);
		}
		return SUCCESS;
	}
	
	@Action(value = "summaryList", results = { @Result(name = SUCCESS, location = "/pages/agent/addsum.jsp")})
    public String summaryList(){ 
		SysStaff staff = (SysStaff)request.getSession().getAttribute(XKDataContext.STAFF_SESSION);
		if(StringUtils.isNotBlank(staff.getWcode()) && StringUtils.isNotBlank(agentUserid)){
//			AgentUser agentUser = this.agentUserRepository.findByIdAndOrgi(agentuserid, super.getOrgi(request)) ;
//			if(agentUser!=null && !StringUtils.isBlank(agentUser.getAgentserviceid())){
//				AgentServiceSummary summary = this.serviceSummaryRes.findByAgentserviceidAndOrgi(agentUser.getAgentserviceid(), super.getOrgi(request)) ;
//				if(summary!=null){
//					map.addAttribute("summary", summary) ;
//				}
//			}
			//map.addAttribute("tagsSummary", tagRes.findByOrgiAndTagtype(super.getOrgi(request) , XKDataContext.ModelType.SUMMARY.toString())) ;
			//map.addAttribute("userid", userid) ;
			//map.addAttribute("agentserviceid", agentserviceid) ;
			//map.addAttribute("agentuserid", agentUserid) ;
			
		}
		return SUCCESS;
    	//return request(super.createRequestPageTempletResponse("/apps/agent/addsum")) ; 
    }
	
	@Action(value = "summarySave", results = { @Result(name = SUCCESS, location = "/pages/agent/addsum.jsp")})
    public String summarysave(){ 
		SysStaff staff = (SysStaff)request.getSession().getAttribute(XKDataContext.STAFF_SESSION);
		AgentServiceSummary summary = new AgentServiceSummary();
		if(StringUtils.isNotBlank(staff.getWcode()) && StringUtils.isNotBlank(agentUserid)){
			summary.setOrgi(XKDataContext.SYSTEM_ORGI);
			summary.setCreater(staff.getWcode());
			
			summary.setCreatetime(new Date());
			
			AgentService service = sysAgentService.findAgentService(agentUserid) ;
			summary.setAgent(service.getAgentno());
			summary.setAgentno(service.getAgentno());
			summary.setUsername(service.getUsername());
			summary.setAgentusername(service.getAgentusername());
			summary.setChannel(service.getChannel());
			summary.setLogindate(service.getLogindate());
			summary.setContactsid(service.getContactsid());
			summary.setEmail(service.getEmail());
			summary.setPhonenumber(service.getPhone());
			summaryService.save(summary) ;
		}
		
    	return SUCCESS ; 
    }

	@Action(value = "agentusermsg")
	public void getBrandCascadeList() throws Exception{
		setResponseParas();
		SysStaff staff = (SysStaff)request.getSession().getAttribute(XKDataContext.STAFF_SESSION);
		String id = request.getParameter("id");
		String rownum = request.getParameter("rownum");
		if(StringUtils.isBlank(rownum)){
			rownum = "20";
		}
		AgentUser agentuser = this.getSysAgentUserService().findAgentUserById(id);
		this.currentAgentUser = agentuser;
		agentUserMessageList = this.getChatMessageService().findChatMessageList(agentuser.getUserid(),rownum);
		//AgentUser agentUser = agentUserRepository.findByIdAndOrgi(id, super.getOrgi(request));
		//this.chatMessageRepository.findByUsessionAndOrgi(agentUser.getUserid() , super.getOrgi(request), new PageRequest(0, 20, Direction.DESC , "updatetime")
		//List<Map> stypeList = this.brandCascadeService.getMultOptionList(Long.valueOf(accNbrType),Long.valueOf(regionId));
		response.getWriter().print(JSONArray.fromObject(agentUserMessageList).toString());
	}
	

	@Action(value = "agentchathis", results = { @Result(name = SUCCESS, location = "/pages/agent/agentUserList.jsp")})
    public String showAgentChatHis() throws Exception{
		this.setStartdate(new SimpleDateFormat("yyyy-MM-dd").format(new Date())+" 00:00:00");
		return SUCCESS;
    }
	
	@Action(value = "loadAgentUserList")
	public String loadAgentUserList() {
		Page page = sysAgentUserService.findAgentUsersPage(startdate,enddate,this.getPage());
		try {
			Map<String,Object> map=new HashMap<String,Object>();
			
			map.put("Rows", page.getResult());
			map.put("Total", page.getTotalCount());
			
			response.setContentType("text/html;charset=UTF-8");
			response.getWriter().write( JSONObject.fromObject(map).toString());
			//response.getWriter().write("{Rows:[{'id':'ALFKI','title':'ALFKI'}],Total:1}");
			
		} catch (Exception e) {
			try {
				response.getWriter().write("");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return NONE;
	}
	

	
	
	public IChatMessageService getChatMessageService() {
		return chatMessageService;
	}


	public void setChatMessageService(IChatMessageService chatMessageService) {
		this.chatMessageService = chatMessageService;
	}


	public ISysAgentUserService getSysAgentUserService() {
		return sysAgentUserService;
	}

	public void setSysAgentUserService(ISysAgentUserService sysAgentUserService) {
		this.sysAgentUserService = sysAgentUserService;
	}

	public ISysAgentService getSysAgentService() {
		return sysAgentService;
	}

	public void setSysAgentService(ISysAgentService sysAgentService) {
		this.sysAgentService = sysAgentService;
	}

	public ISysOnlineUserService getSysOnlineUserService() {
		return sysOnlineUserService;
	}

	public void setSysOnlineUserService(ISysOnlineUserService sysOnlineUserService) {
		this.sysOnlineUserService = sysOnlineUserService;
	}

	public IAgentUserTaskService getAgentUserTaskService() {
		return agentUserTaskService;
	}

	public void setAgentUserTaskService(IAgentUserTaskService agentUserTaskService) {
		this.agentUserTaskService = agentUserTaskService;
	}

	public IQuickReplyService getQuickReplyService() {
		return quickReplyService;
	}

	public void setQuickReplyService(IQuickReplyService quickReplyService) {
		this.quickReplyService = quickReplyService;
	}

	public IQuickTypeService getQuickTypeService() {
		return quickTypeService;
	}

	public void setQuickTypeService(IQuickTypeService quickTypeService) {
		this.quickTypeService = quickTypeService;
	}

	public IAgentStatusService getAgentStatusService() {
		return agentStatusService;
	}

	public void setAgentStatusService(IAgentStatusService agentStatusService) {
		this.agentStatusService = agentStatusService;
	}

	public ISummaryService getSummaryService() {
		return summaryService;
	}

	public void setSummaryService(ISummaryService summaryService) {
		this.summaryService = summaryService;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public int getServiceCount() {
		return serviceCount;
	}

	public void setServiceCount(int serviceCount) {
		this.serviceCount = serviceCount;
	}

	public AgentService getAgentService() {
		return agentService;
	}

	public void setAgentService(AgentService agentService) {
		this.agentService = agentService;
	}

	public AgentUser getCurrentAgentUser() {
		return currentAgentUser;
	}

	public void setCurrentAgentUser(AgentUser currentAgentUser) {
		this.currentAgentUser = currentAgentUser;
	}

	public String getAgentUserid() {
		return agentUserid;
	}

	public void setAgentUserid(String agentUserid) {
		this.agentUserid = agentUserid;
	}

	public OnlineUser getOnlineUser() {
		return onlineUser;
	}

	public void setOnlineUser(OnlineUser onlineUser) {
		this.onlineUser = onlineUser;
	}

	public List<AgentServiceSummary> getSummaryList() {
		return summaryList;
	}

	public void setSummaryList(List<AgentServiceSummary> summaryList) {
		this.summaryList = summaryList;
	}

	public List<ChatMessage> getAgentUserMessageList() {
		return agentUserMessageList;
	}

	public void setAgentUserMessageList(List<ChatMessage> agentUserMessageList) {
		this.agentUserMessageList = agentUserMessageList;
	}

	public List<QuickReply> getQuickReplyList() {
		return quickReplyList;
	}

	public void setQuickReplyList(List<QuickReply> quickReplyList) {
		this.quickReplyList = quickReplyList;
	}

	public List<QuickType> getPubQuickTypeList() {
		return pubQuickTypeList;
	}

	public void setPubQuickTypeList(List<QuickType> pubQuickTypeList) {
		this.pubQuickTypeList = pubQuickTypeList;
	}

	public List<AgentUser> getAgentUserList() {
		return agentUserList;
	}

	public void setAgentUserList(List<AgentUser> agentUserList) {
		this.agentUserList = agentUserList;
	}

	public AgentUser getCuragentuser() {
		return curagentuser;
	}

	public void setCuragentuser(AgentUser curagentuser) {
		this.curagentuser = curagentuser;
	}


	public String getStartdate() {
		return startdate;
	}


	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}


	public String getEnddate() {
		return enddate;
	}


	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}

	/**
	@Action(value = "agentuser", results = { @Result(name = SUCCESS, location = "/pages/agent/mainagentuser.jsp")})
	public String agentuser() throws Exception{
		currentAgentUser = sysAgentUserService.findByUserid(agentUserid);
		if(currentAgentUser!=null){
			List<AgentUserTask> agentUserTaskList = agentUserTaskService.findAgentUserId(agentUserid) ;
			if(agentUserTaskList.size() > 0){
				AgentUserTask agentUserTask = agentUserTaskList.get(0) ;
				agentUserTask.setTokenum(0);
				agentUserTaskService.save(agentUserTask) ;
			}
			
			if(!StringUtils.isBlank(agentUser.getAgentserviceid())){
				Page<AgentServiceSummary> summaryList = this.serviceSummaryRes.findByOrgiAndUserid(super.getOrgi(request), agentUser.getUserid() , new PageRequest(0, super.getPs(request), Sort.Direction.DESC, new String[] { "createtime" }));
				//Page<AgentServiceSummary> summaryList = this.serviceSummaryRes.findByOrgiAndAgentserviceid(super.getOrgi(request), agentUser.getAgentserviceid(),new PageRequest(0, super.getPs(request), Sort.Direction.DESC, new String[] { "createtime" }));
				view.addObject("summaryList", summaryList) ;
			}
			
			view.addObject("agentUserMessageList", this.chatMessageRepository.findByUsessionAndOrgi(agentUser.getUserid() , super.getOrgi(request), new PageRequest(0, 20, Direction.DESC , "updatetime")));
			AgentService agentService = null ;
			if(!StringUtils.isBlank(agentUser.getAgentserviceid())){
				agentService = this.agentServiceRepository.findOne(agentUser.getAgentserviceid()) ;
				view.addObject("curAgentService", agentService) ;
				if(agentService!=null){
					processRelaData(request, agentService, map);
					map.addAttribute("dataid", agentService.getId()) ;
				}
			}
			//文字客服
			SysDic sysDic = sysDicRes.findByCode("sessionWords");
			if(agentService != null &&sysDic != null){
				List<SessionType> sessionTypeList = sessionTypeRes.findByOrgiAndCtype(super.getOrgi(request), sysDic.getId());
				for(SessionType  ses : sessionTypeList){
					if(!StringUtils.isBlank(agentService.getSessiontype()) && ses.getId().equals(agentService.getSessiontype())){
						map.addAttribute("agentSessionType", ses.getName());
					}
				}
			}
			
			if(UKDataContext.ChannelTypeEnum.WEIXIN.toString().equals(agentUser.getChannel())){
				List<WeiXinUser> weiXinUserList = weiXinUserRes.findByOpenidAndOrgi(agentUser.getUserid(), super.getOrgi(request)) ;
				if(weiXinUserList.size() > 0){
					WeiXinUser weiXinUser = weiXinUserList.get(0) ;
					view.addObject("weiXinUser",weiXinUser);
				}
			}else if(UKDataContext.ChannelTypeEnum.WEBIM.toString().equals(agentUser.getChannel())){
				List<OnlineUser> onlineUserList = this.onlineUserRes.findByUseridAndOrgi(agentUser.getUserid(), super.getOrgi(request)) ;
				if(onlineUserList.size()  > 0){
					OnlineUser onlineUser = onlineUserList.get(0) ;
					if(onlineUser.getLogintime()!=null) {
						if(UKDataContext.OnlineUserOperatorStatus.OFFLINE.toString().equals(onlineUser.getStatus())){
							onlineUser.setBetweentime((int) (onlineUser.getUpdatetime().getTime() - onlineUser.getLogintime().getTime()));
						}else{
							onlineUser.setBetweentime((int) (System.currentTimeMillis() - onlineUser.getLogintime().getTime()));
						}
					}
					view.addObject("onlineUser",onlineUser);
				}
			}else if(UKDataContext.ChannelTypeEnum.PHONE.toString().equals(agentUser.getChannel())){
				if(agentService!=null && !StringUtils.isBlank(agentService.getOwner())) {
					StatusEvent statusEvent = this.statusEventRes.findAgentUserId(agentService.getOwner()) ;
					if(statusEvent!=null){
						if(!StringUtils.isBlank(statusEvent.getHostid())) {
							PbxHost pbxHost = pbxHostRes.findAgentUserId(statusEvent.getHostid()) ;
							view.addObject("pbxHost",pbxHost);
						}
						view.addObject("statusEvent",statusEvent);
					}
				}
			}
			
	
			view.addObject("serviceCount", Integer
					.valueOf(this.agentServiceRepository
							.countByUseridAndOrgiAndStatus(agentUser
									.getUserid(), super.getOrgi(request),
									UKDataContext.AgentUserStatusEnum.END
											.toString())));
			view.addObject("tagRelationList", tagRelationRes.findByUserid(agentUser.getUserid())) ;
		}
		
		SessionConfig sessionConfig = ServiceQuene.initSessionConfig(super.getOrgi(request)) ;
		
		view.addObject("sessionConfig", sessionConfig) ;
		if(sessionConfig.isOtherquickplay()) {
			view.addObject("topicList", OnlineUserUtils.search(null, super.getOrgi(request), super.getUser(request) , agentUser.getAppid())) ;
		}
		
		
		view.addObject("tags", tagRes.findByOrgiAndTagtype(super.getOrgi(request) , UKDataContext.ModelType.USER.toString())) ;
		view.addObject("quickReplyList", quickReplyRes.findByOrgiAndCreater(super.getOrgi(request) , super.getUser(request).getId() , null)) ;
		List<QuickType> quickTypeList = quickTypeRes.findByOrgiAndQuicktype(super.getOrgi(request), UKDataContext.QuickTypeEnum.PUB.toString()) ;
		List<QuickType> priQuickTypeList = quickTypeRes.findByOrgiAndQuicktypeAndCreater(super.getOrgi(request), UKDataContext.QuickTypeEnum.PRI.toString(), super.getUser(request).getId()) ; 
		quickTypeList.addAll(priQuickTypeList) ;
		view.addObject("pubQuickTypeList", quickTypeList) ;
		map.addAttribute("tagsSummary", tagRes.findByOrgiAndTagtype(super.getOrgi(request) , UKDataContext.ModelType.SUMMARY.toString())) ;
		return view ;
	}**/
	
}