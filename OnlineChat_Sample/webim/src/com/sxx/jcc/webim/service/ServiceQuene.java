package com.sxx.jcc.webim.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.sxx.jcc.common.utils.XKDataContext;
import com.sxx.jcc.common.utils.XKTools;
import com.sxx.jcc.core.cache.SystemCache;
import com.sxx.jcc.system.pojo.AgentService;
import com.sxx.jcc.system.pojo.AgentUser;
import com.sxx.jcc.system.pojo.SessionConfig;
import com.sxx.jcc.system.service.ISysAgentService;
import com.sxx.jcc.system.service.ISysOnlineUserService;
import com.sxx.jcc.system.service.ISysSessionConfigService;
import com.sxx.jcc.system.service.impl.SysSessionConfigServiceImpl;
import com.sxx.jcc.webim.pojo.AgentReport;
import com.sxx.jcc.webim.pojo.AgentStatus;
import com.sxx.jcc.webim.pojo.AgentUserTask;
import com.sxx.jcc.webim.pojo.MessageOutContent;
import com.sxx.jcc.webim.pojo.OnlineUser;
import com.sxx.jcc.webim.pojo.WorkMonitor;
import com.sxx.jcc.webim.util.NettyClients;
import com.sxx.jcc.webim.util.router.OutMessageRouter;

public class ServiceQuene {
	static Lock lock = new ReentrantLock();
	/**
	 * 载入坐席 ACD策略配置
	 * @param orgi
	 * @return
	 */
	public static SessionConfig initSessionConfig(String orgi){
		SessionConfig sessionConfig = null;
		if(XKDataContext.getContext() != null && (sessionConfig =  SystemCache.fetchSessionConfig()) == null){
			ISysSessionConfigService sysSessionConfigService = XKDataContext.getContext().getBean(SysSessionConfigServiceImpl.class) ;
			//sessionConfig = agentUserRepository.findByOrgi(orgi) ;
			sessionConfig = sysSessionConfigService.findbyRegion();
			if(sessionConfig != null){
				SystemCache.putSessionConfigCahced(sessionConfig) ;
			}
		}
		if(sessionConfig == null) {
			sessionConfig = new SessionConfig() ;
		}
		return sessionConfig ;
	}
	
	/**
	 * 获得 当前服务状态
	 * @param orgi
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static AgentReport getAgentReport(String orgi){
		/**
		 * 统计当前在线的坐席数量
		 */
		AgentReport report = new AgentReport() ;
		Map<String,AgentStatus> agentStatusCachedMap = SystemCache.fetchAgentStatusDate();
		int agents=0;
		int busys=0;
		for (AgentStatus agentStatusCached : agentStatusCachedMap.values()) {
			agents++;
			if(agentStatusCached.isBusy()){
				busys++;
			}

		}
		report.setAgents(agents);
		
		report.setBusy(busys);
		report.setOrgi(orgi);
		
		/**
		 * 统计当前服务中的用户数量
		 */
		Map<String,AgentUser> agentUserCachedList = SystemCache.fetchAllAgentUser();
		int users=agentUserCachedList.size();
		int usersinque=0;
		for (AgentUser agentUserCached : agentUserCachedList.values()) {
			if(XKDataContext.AgentUserStatusEnum.INSERVICE.toString().equalsIgnoreCase(agentUserCached.getStatus())){
				users++;
			}
			if(XKDataContext.AgentUserStatusEnum.INQUENE.toString().equalsIgnoreCase(agentUserCached.getStatus())){
				usersinque++;
			}
		}
		report.setUsers(users);
		report.setInquene(usersinque);
		
		return report;
	}
	
	@SuppressWarnings("unchecked")
	public static int getQueneIndex(String agent , String orgi , String skill){
		
		int queneUsers = 0 ;
		Map<String,AgentUser> agentUserCachedList = SystemCache.fetchAllAgentUser();
		
		if(StringUtils.isNotBlank(skill)){
			for (AgentUser agentUserCached : agentUserCachedList.values()) {
				if(XKDataContext.AgentUserStatusEnum.INQUENE.toString().equalsIgnoreCase(agentUserCached.getStatus()) && skill.equalsIgnoreCase(agentUserCached.getSkill())){
					queneUsers++;
				}
			}
		}else if(StringUtils.isNotBlank(agent)){
			for (AgentUser agentUserCached : agentUserCachedList.values()) {
				if(XKDataContext.AgentUserStatusEnum.INQUENE.toString().equalsIgnoreCase(agentUserCached.getStatus()) && agent.equalsIgnoreCase(agentUserCached.getAgent())){
					queneUsers++;
				}
			}
		}else{
			/**for (AgentUser agentUserCached : agentUserCachedList.values()) {
				if(XKDataContext.AgentUserStatusEnum.INQUENE.toString().equalsIgnoreCase(agentUserCached.getStatus())){
					queneUsers++;
				}
			}**/
		}
		return queneUsers;
	}
	
	public static String getQueneMessage(int queneIndex , String channel,String orgi){
		
		String queneTip = "<span id='queneindex'>"+queneIndex+"</span>" , indexTip = "<span id='queneindex'>"+(queneIndex + 1)+"</span>";
		if(!XKDataContext.ChannelTypeEnum.WEBIM.toString().equals(channel)){
			queneTip = String.valueOf(queneIndex) ;
			indexTip = String.valueOf(queneIndex+1) ;
		}
		SessionConfig sessionConfig = initSessionConfig(orgi) ;
		//String agentBusyTipMsg = "坐席全忙，已进入等待队列，正在排队，请稍候,在您之前，还有  "+queneTip+" 位等待用户。"  ;
		String agentBusyTipMsg = "坐席全忙，已进入等待队列, 您也可以留言，我们会尽快给您答复！"  ;
		if(!StringUtils.isBlank(sessionConfig.getAgentbusymsg())){
			agentBusyTipMsg = sessionConfig.getAgentbusymsg().replaceAll("\\{num\\}", queneTip).replaceAll("\\{index\\}", indexTip) ;
		}
		return agentBusyTipMsg;
	}
	
	/**
	 * 为用户分配坐席
	 * @param agentUser
	 */
	@SuppressWarnings("unchecked")
	public static AgentService allotAgent(AgentUser agentUser , String orgi){
		/**
		 * 查询条件，当前在线的 坐席，并且 未达到最大 服务人数的坐席
		 */
		SessionConfig config = initSessionConfig(orgi) ;
		AgentStatus agentStatus = null ;
		Map<String,AgentStatus> agentStatusCachedMap = SystemCache.fetchAgentStatusDate();
		for (AgentStatus agentStatusCached : agentStatusCachedMap.values()) {
			/**if(users == -1){
				users = agentStatusCached.getUsers();
				//agentStatus = agentStatusCached;
			}**/
			if(!agentStatusCached.isBusy()){
				agentStatus = agentStatusCached;
			}

		}
		
		
		AgentService agentService = null ;	//放入缓存的对象
		if(agentStatus !=null && config !=null && agentStatus.getUsers() >= config.getMaxuser()){
			agentStatus = null ;
			/**
			 * 判断当前有多少人排队中 ， 分三种情况：1、请求技能组的，2、请求坐席的，3，默认请求的
			 * 
			 */
			
		}
		
		try {
			agentService = processAgentService(agentStatus, agentUser, orgi) ;
			if(agentService!=null && agentService.getStatus().equals(XKDataContext.AgentUserStatusEnum.INQUENE.toString())){
				agentService.setQueneindex(getQueneIndex(agentUser.getAgent(), orgi, agentUser.getSkill()));
			}
			
		}catch(Exception ex){
			ex.printStackTrace(); 
		}
		publishMessage(orgi, "user" , agentService!=null && agentService.getStatus().equals(XKDataContext.AgentUserStatusEnum.INSERVICE.toString()) ? "inservice" : "inquene" , agentUser.getId());
		return agentService;
	}
	
	/**
	 * 为访客 分配坐席， ACD策略，此处 AgentStatus 是建议 的 坐席，  如果启用了  历史服务坐席 优先策略， 则会默认检查历史坐席是否空闲，如果空闲，则分配，如果不空闲，则 分配当前建议的坐席
	 * @param agentStatus
	 * @param agentUser
	 * @param orgi
	 * @return
	 * @throws Exception
	 */
	private static AgentService processAgentService(AgentStatus agentStatus , AgentUser agentUser , String orgi) throws Exception{
		return processAgentService(agentStatus, agentUser, orgi , false) ;
	}
	
	
	/**
	 * 为访客 分配坐席， ACD策略，此处 AgentStatus 是建议 的 坐席，  如果启用了  历史服务坐席 优先策略(暂不起用)， 则会默认检查历史坐席是否空闲，如果空闲，则分配，如果不空闲，则 分配当前建议的坐席
	 * @param agentStatus  座席
	 * @param agentUser 访客
	 * @param orgi
	 * @return
	 * @throws Exception
	 */
	private static AgentService processAgentService(AgentStatus agentStatus , AgentUser agentUser , String orgi , boolean finished) throws Exception{
		AgentService agentService = new AgentService();	//放入缓存的对象
		if(StringUtils.isNotBlank(agentUser.getAgentserviceid())) {
			agentService.setId(agentUser.getAgentserviceid());
		}
		agentService.setOrgi(orgi);
		
		XKTools.copyProperties(agentUser, agentService); //复制属性
		
		agentService.setChannel(agentUser.getChannel());
		
		agentService.setSessionid(agentUser.getSessionid());
		ISysOnlineUserService onlineUserService = XKDataContext.getContext().getBean(ISysOnlineUserService.class);
		ISysAgentUserService agentUserService = XKDataContext.getContext().getBean(ISysAgentUserService.class);
		ISysAgentService agentServiceRes = XKDataContext.getContext().getBean(ISysAgentService.class);
		agentUser.setLogindate(new Date());
		List<OnlineUser> onlineUserList = onlineUserService.findOnlineUserByUserid(agentUser.getUserid());
		OnlineUser onlineUser = null ;
		if (CollectionUtils.isNotEmpty(onlineUserList)  && onlineUserList.size() > 0) {
			onlineUser = onlineUserList.get(0) ;
		}
		
		if(agentStatus!=null){	
			agentService.setAgent(agentStatus.getAgentno());
			agentService.setSkill(agentUser.getSkill());
			agentUser.setStatus(XKDataContext.AgentUserStatusEnum.INSERVICE.toString());
			agentService.setStatus(XKDataContext.AgentUserStatusEnum.INSERVICE.toString());
			
			agentService.setSessiontype(XKDataContext.AgentUserStatusEnum.INSERVICE.toString());
			agentUser.setAgent(agentStatus.getAgentno());
			agentService.setAgentno(agentStatus.getAgentno());
			agentService.setAgentusername(agentStatus.getUsername());	//agent
		}else{
			/**if(finished == true) {
				agentUser.setStatus(XKDataContext.AgentUserStatusEnum.END.toString());
				agentService.setStatus(XKDataContext.AgentUserStatusEnum.END.toString());
				agentService.setSessiontype(XKDataContext.AgentUserStatusEnum.END.toString());
				if(agentStatus==null) {
					agentService.setLeavemsg(true); //是留言
					agentService.setLeavemsgstatus(XKDataContext.LeaveMsgStatus.NOTPROCESS.toString()); //未处理的留言
				}
			}else {
				agentUser.setStatus(XKDataContext.AgentUserStatusEnum.INQUENE.toString());
				agentUser.setAgentno(null);
				agentService.setStatus(XKDataContext.AgentUserStatusEnum.INQUENE.toString());
				
				agentService.setSessiontype(XKDataContext.AgentUserStatusEnum.INQUENE.toString());
			}**/
			agentUser.setStatus(XKDataContext.AgentUserStatusEnum.INQUENE.toString());
			agentUser.setAgentno(null);
			agentService.setStatus(XKDataContext.AgentUserStatusEnum.INQUENE.toString());
			
			agentService.setSessiontype(XKDataContext.AgentUserStatusEnum.INQUENE.toString());
		}
		
		if(finished == true) {
			agentUser.setStatus(XKDataContext.AgentUserStatusEnum.END.toString());
			agentService.setStatus(XKDataContext.AgentUserStatusEnum.END.toString());
			agentService.setSessiontype(XKDataContext.AgentUserStatusEnum.END.toString());
			if(agentStatus==null) {
				agentService.setLeavemsg(true); //是留言
				agentService.setLeavemsgstatus(XKDataContext.LeaveMsgStatus.NOTPROCESS.toString()); //未处理的留言
			}
		}
		
		if(finished || agentStatus!=null) {
			//			agentService.setId(null);
	
			agentService.setAgentuserid(agentUser.getId());
			
			agentService.setInitiator(XKDataContext.ChatInitiatorType.USER.toString());
	
			long waittingtime = 0  ;
			if(agentUser.getWaittingtimestart()!=null){
				waittingtime = System.currentTimeMillis() - agentUser.getWaittingtimestart().getTime() ;
			}else if(agentUser.getCreatetime()!=null){
				waittingtime = System.currentTimeMillis() - agentUser.getCreatetime().getTime() ;
			}
			agentUser.setWaittingtime((int)waittingtime);
	
			agentUser.setServicetime(new Date());
	
			agentService.setOwner(agentUser.getOwner());
			
			agentService.setTimes(0);
			agentUser.setAgentno(agentService.getAgentno());
	
			
	
			if(!StringUtils.isBlank(agentUser.getName())){
				agentService.setName(agentUser.getName());
			}
			if(!StringUtils.isBlank(agentUser.getPhone())){
				agentService.setPhone(agentUser.getPhone());
			}
			if(!StringUtils.isBlank(agentUser.getEmail())){
				agentService.setEmail(agentUser.getEmail());
			}
			if(!StringUtils.isBlank(agentUser.getResion())){
				agentService.setResion(agentUser.getResion());
			}
	
			if(!StringUtils.isBlank(agentUser.getSkill())) {
				agentService.setAgentskill(agentUser.getSkill());
			}else if(agentStatus!=null) {
				agentService.setAgentskill(agentStatus.getSkill());
			}
	
			agentService.setServicetime(new Date());
			if(agentUser.getCreatetime()!=null){
				agentService.setWaittingtime((int) (System.currentTimeMillis() - agentUser.getCreatetime().getTime()));
				agentUser.setWaittingtime(agentService.getWaittingtime());
			}
			if (onlineUser != null ) {
				agentService.setOsname(onlineUser.getOpersystem());
				agentService.setBrowser(onlineUser.getBrowser());
				agentService.setDataid(onlineUser.getId());		//记录  onlineuser 的id
			}
			agentService.setLogindate(agentUser.getCreatetime());
			if(StringUtils.isNotBlank(agentService.getId())){
				agentServiceRes.save(agentService);
			}else{
				agentService.setId(XKTools.getUUID());
				agentServiceRes.add(agentService);
			}
			agentUser.setAgentserviceid(agentService.getId());
			agentUser.setLastgetmessage(new Date());
			agentUser.setLastmessage(new Date());
		}
		
		agentService.setDataid(agentUser.getId());
		/**
		 * 分配成功以后， 将用户 和坐席的对应关系放入到 缓存
		 */
		/**
		 * 将 AgentUser 放入到 当前坐席的 服务队列
		 */
		//AgentUserRepository agentUserRepository = UKDataContext.getContext().getBean(AgentUserRepository.class) ;
		
		/**
		 * 更新OnlineUser对象，变更为服务中，不可邀请
		 */
		
		if(onlineUser!=null){
			onlineUser.setInvitestatus(XKDataContext.OnlineUserInviteStatus.INSERV.toString());
			onlineUserService.save(onlineUser) ;
		}
		
		/**
		 * 
		 */
		agentUserService.save(agentUser);

		//SystemCache.put(agentUser.getUserid(), agentUser);
		SystemCache.putAgentUser(agentUser);
		if(agentStatus != null){
			
			updateAgentStatus(agentStatus  , agentUser , orgi , true) ;
		}
		
		return agentService ;
	}
	
	
	/**
	 * @param agentStatus
	 * @throws Exception 
	 */
	public static void serviceFinish(AgentUser agentUser , String orgi , String endby) throws Exception{
		if(agentUser!=null){
			AgentStatus agentStatus = null;
			if(XKDataContext.AgentUserStatusEnum.INSERVICE.toString().equals(agentUser.getStatus()) && agentUser.getAgentno()!=null){
				agentStatus = SystemCache.getCachedAgentStatus(agentUser.getAgentno());
			}
			SystemCache.removeAgentUser(agentUser);
			ISysAgentUserService agentUserService = XKDataContext.getContext().getBean(ISysAgentUserService.class) ;
			
			AgentUser agentUseDataBean = agentUserService.findAgentUserById(agentUser.getId()) ;
			SessionConfig sessionConfig = ServiceQuene.initSessionConfig(orgi) ;
			if(agentUseDataBean!=null){
				agentUseDataBean.setStatus(XKDataContext.AgentUserStatusEnum.END.toString()) ;
				if(agentUser.getServicetime()!=null){
					agentUseDataBean.setSessiontimes(System.currentTimeMillis() - agentUser.getServicetime().getTime()) ;
				}
				
				agentUserService.save(agentUseDataBean) ;
				
				/**
				 * 更新OnlineUser对象，变更为服务中，不可邀请 , WebIM渠道专用
				 */
				/**if(XKDataContext.ChannelTypeEnum.WEBIM.toString().equals(agentUser.getChannel())){
					ISysOnlineUserService onlineUserService = XKDataContext.getContext().getBean(ISysOnlineUserService.class);
					List<OnlineUser> onlineUserList = onlineUserService.findOnlineUserByUserid(agentUser.getUserid());
					if (onlineUserList.size() > 0) {
						OnlineUser onlineUser = onlineUserList.get(0) ;
						onlineUser.setInvitestatus(XKDataContext.OnlineUserInviteStatus.DEFAULT.toString());
						onlineUserService.save(onlineUser) ;
					}
				}**/
			}
			
			ISysAgentService agentServiceRes = XKDataContext.getContext().getBean(ISysAgentService.class);
			AgentService service = 	null ;
			if(StringUtils.isNotBlank(agentUser.getAgentserviceid())){
				service = 	agentServiceRes.findAgentService(agentUser.getAgentserviceid()) ;
			}
			if(service == null) {//当做留言处理
				service = processAgentService(agentStatus, agentUser, orgi , true) ;
			}else{
				service.setStatus(XKDataContext.AgentUserStatusEnum.END.toString());
				service.setEndtime(new Date());
				service.setEndby(endby);
				if(service.getServicetime()!=null){
					service.setSessiontimes(System.currentTimeMillis() - service.getServicetime().getTime());
				}
				IAgentUserTaskService agentUserTaskService = XKDataContext.getContext().getBean(IAgentUserTaskService.class);
				AgentUserTask agentUserTask=  agentUserTaskService.getAgentUserTask(agentUser.getId()) ;
				if(agentUserTask != null){
					service.setAgentreplyinterval(agentUserTask.getAgentreplyinterval());
					service.setAgentreplytime(agentUserTask.getAgentreplytime());
					service.setAvgreplyinterval(agentUserTask.getAvgreplyinterval());
					service.setAvgreplytime(agentUserTask.getAvgreplytime());
					
					service.setFilteragentscript(agentUserTask.getFilteragentscript());
					service.setFilterscript(agentUserTask.getFilterscript());

					service.setUserasks(agentUserTask.getUserasks());
					service.setAgentreplys(agentUserTask.getAgentreplys());
					
					service.setFirstreplytime(agentUserTask.getFirstreplytime());
				}


				/**
				 * 启用了质检任务，开启质检
				 */
				 if(sessionConfig.isQuality() && service.getUserasks() > 0) {	//开启了质检，并且是有效对话
					 service.setQualitystatus(XKDataContext.QualityStatus.NODIS.toString());	//未分配质检任务
				 }else {
					 service.setQualitystatus(XKDataContext.QualityStatus.NO.toString());	//未开启质检 或无效对话无需质检
				 }
				 agentServiceRes.save(service) ;
			}
			
			
			if(agentStatus!=null){
				NettyClients.getInstance().sendAgentEventMessage(agentUser.getAgentno(), XKDataContext.MessageTypeEnum.END.toString(), agentUser);
			}
			
			OutMessageRouter router = null ; 
    		router  = (OutMessageRouter) XKDataContext.getContext().getBean(agentUser.getChannel()) ;
    		if(router!=null){
    			MessageOutContent outMessage = new MessageOutContent() ;
				outMessage.setMessage(ServiceQuene.getServiceFinishMessage(agentUser.getChannel(),orgi));
				outMessage.setMessageType(XKDataContext.AgentUserStatusEnum.END.toString());
				outMessage.setCalltype(XKDataContext.CallTypeEnum.IN.toString());
				if(agentStatus!=null){
					outMessage.setNickName(agentStatus.getUsername());
				}else{
					outMessage.setNickName(agentUser.getUsername());
				}
				outMessage.setCreatetime(XKTools.dateFormate.format(new Date()));
				outMessage.setAgentserviceid(agentUser.getAgentserviceid());
				
    			router.handler(agentUser.getUserid(), XKDataContext.MessageTypeEnum.STATUS.toString(), agentUser.getAppid(), outMessage);
    		}
			
    		if(agentStatus!=null){
				updateAgentStatus(agentStatus  , agentUser , orgi , false) ;
				
				long maxusers = sessionConfig!=null ? sessionConfig.getMaxuser() : XKDataContext.AGENT_STATUS_MAX_USER ;
				if(agentStatus.getUsers() < maxusers){
					allotAgent(agentStatus.getAgentno(), orgi);
				}
			}
			publishMessage(orgi, "end" , "success" ,agentUser!=null ? agentUser.getId() : null);
		}
	}
	
	/**
	 * 为坐席批量分配用户
	 * @param agentStatus
	 */
	@SuppressWarnings("unchecked")
	public static void allotAgent(String agentno , String orgi){
		if(StringUtils.isBlank(orgi)){
			orgi = XKDataContext.SYSTEM_ORGI;
		}
		AgentStatus agentStatus = SystemCache.getCachedAgentStatus(agentno);
		List<AgentUser> agentUserList = new ArrayList<AgentUser>();
		Map<String,AgentUser> agentUserCachedList = SystemCache.fetchAllAgentUser();
	
		for (AgentUser agentUserCached : agentUserCachedList.values()) {
			if(XKDataContext.AgentUserStatusEnum.INQUENE.toString().equalsIgnoreCase(agentUserCached.getStatus()) && (StringUtils.isBlank(agentUserCached.getAgent()) || agentno.equalsIgnoreCase(agentUserCached.getAgent()))){
				agentUserList.add(agentUserCached);
			}
		}
			
		
		for(AgentUser agentUser : agentUserList){
			SessionConfig sessionConfig = ServiceQuene.initSessionConfig(orgi) ;
			long maxusers = sessionConfig!=null ? sessionConfig.getMaxuser() : XKDataContext.AGENT_STATUS_MAX_USER ;
			if(agentStatus != null && agentStatus.getUsers() < maxusers){		//坐席未达到最大咨询访客数量
				SystemCache.removeAgentUser(agentUser);
				try{
					AgentService agentService = processAgentService(agentStatus, agentUser, orgi) ;

					MessageOutContent outMessage = new MessageOutContent() ;
					outMessage.setMessage(ServiceQuene.getSuccessMessage(agentService , agentUser.getChannel(),orgi));
					outMessage.setMessageType(XKDataContext.MediaTypeEnum.TEXT.toString());
					outMessage.setCalltype(XKDataContext.CallTypeEnum.IN.toString());
					outMessage.setNickName(agentStatus.getUsername());
					outMessage.setCreatetime(XKTools.dateFormate.format(new Date()));

					if(!StringUtils.isBlank(agentUser.getUserid())){
						OutMessageRouter router = null ; 
						router  = (OutMessageRouter) XKDataContext.getContext().getBean(agentUser.getChannel()) ;
						if(router!=null){
							router.handler(agentUser.getUserid(), XKDataContext.MessageTypeEnum.STATUS.toString(), agentUser.getAppid(), outMessage);
						}
					}

					NettyClients.getInstance().sendAgentEventMessage(agentService.getAgentno(), XKDataContext.MessageTypeEnum.NEW.toString(), agentUser);
				}catch(Exception ex){
					ex.printStackTrace();
				}
			}else{
				break ;
			}
		}
		publishMessage(orgi , "agent" , "success" , agentno);
	}
	
	/**
	 * 
	 * @param agentStatus
	 * @return
	 */
	public static String getServiceFinishMessage(String channel,String orgi){
		SessionConfig sessionConfig = initSessionConfig(orgi) ;
		String queneTip = "坐席已断开和您的对话" ;
		if(!StringUtils.isBlank(sessionConfig.getFinessmsg())){
			queneTip = sessionConfig.getFinessmsg();
		}
		return queneTip ;
	}
	
	/**
	 * 
	 * @param agentStatus
	 * @return
	 */
	public static String getSuccessMessage(AgentService agentService ,String channel,String orgi){
		String queneTip = "<span id='agentno'>"+agentService.getAgentusername()+"</span>" ;
		//String queneTip = agentService.getAgentusername() ;
		if(!XKDataContext.ChannelTypeEnum.WEBIM.toString().equals(channel)){
			queneTip = agentService.getAgentusername() ;
		}
		SessionConfig sessionConfig = initSessionConfig(orgi) ;
		String successMsg = "分配成功 "+queneTip+"号坐席为您服务。"  ;
		if(!StringUtils.isBlank(sessionConfig.getSuccessmsg())){
			successMsg = sessionConfig.getSuccessmsg().replaceAll("\\{agent\\}", queneTip) ;
		}
		return successMsg ;
	}
	
	/**
	 * 更新坐席当前服务中的用户状态，需要分布式锁
	 * @param agentStatus
	 * @param agentUser
	 * @param orgi
	 */
	public synchronized static void updateAgentStatus(AgentStatus agentStatus , AgentUser agentUser , String orgi , boolean in){
		int users = getAgentUsers(agentStatus.getAgentno(), orgi) ;
		lock.lock();
		try{
			agentStatus.setUsers(users);
			agentStatus.setUpdatetime(new Date());
			SystemCache.putAgentStatus(agentStatus);
			//SystemCache.put(agentStatus.getAgentno(), agentStatus);
		}finally{
			lock.unlock();
		}
	}
	
	@SuppressWarnings("unchecked")
	public static int getAgentUsers(String agent , String orgi){
		/**
		 * agentno自动是 服务的坐席， agent 是请求的坐席
		 */
		List<AgentUser> agentUserList = new ArrayList<AgentUser>();
		Map<String,AgentUser> agentUserCachedList = SystemCache.fetchAllAgentUser();
		for (AgentUser agentUserCached : agentUserCachedList.values()) {
			if(XKDataContext.AgentUserStatusEnum.INSERVICE.toString().equalsIgnoreCase(agentUserCached.getStatus()) && agent.equalsIgnoreCase(agentUserCached.getAgent())){
				agentUserList.add(agentUserCached);
			}
		}
		return agentUserList.size();
	}
	
	public static void publishMessage(String orgi ,String worktype,String workresult ,String dataid){
		if(StringUtils.isBlank(orgi)){
			orgi = XKDataContext.SYSTEM_ORGI;
		}
		/**
		 * 坐席状态改变，通知监测服务
		 */
		AgentReport agentReport = ServiceQuene.getAgentReport(orgi) ;
		IAgentReportService agentReportRes = XKDataContext.getContext().getBean(IAgentReportService.class) ;
		if(agentReportRes!=null) {
			agentReport.setOrgi(orgi);
			agentReport.setWorktype(worktype);
			agentReport.setWorkresult(workresult);
			agentReport.setDataid(dataid);
			
			agentReportRes.save(agentReport) ;
		}
		NettyClients.getInstance().published("agentNamespace", "status", agentReport);
	}
	
	public static String getNoAgentMessage(int queneIndex , String channel,String orgi){
		if(queneIndex < 0){
			queneIndex = 0 ;
		}
		String queneTip = "<span id='queneindex'>"+queneIndex+"</span>" ;
		if(!XKDataContext.ChannelTypeEnum.WEBIM.toString().equals(channel)){
			queneTip = String.valueOf(queneIndex) ;
		}
		SessionConfig sessionConfig = initSessionConfig(orgi) ;
		String noAgentTipMsg = "抱歉，当前暂无人工客服在线，如有问题请留言，我们会尽快给您答复！";
		if(StringUtils.isNotBlank(sessionConfig.getNoagentmsg())){
			noAgentTipMsg = sessionConfig.getNoagentmsg().replaceAll("\\{num\\}", queneTip) ;
		}
		return noAgentTipMsg;
	}
	
	
	/**
	 * 
	 * @param agent  坐席
	 * @param skill	 技能组
	 * @param userid 用户ID  
	 * @param status	工作状态
	 * @param worktype  类型 ： 语音OR 文本
	 * @param orgi
	 * @param lasttime
	 */
	public static void recordAgentStatus(String agent,String username,String extno,String skill,String userid, String status ,String current ,String worktype ,String orgi , Date lasttime){
		IWorkMonitorService workMonitorRes = XKDataContext.getContext().getBean(IWorkMonitorService.class) ;
		WorkMonitor workMonitor = new WorkMonitor() ;
		if(!StringUtils.isBlank(agent) && !StringUtils.isBlank(status)) {
			workMonitor.setAgent(agent);
			workMonitor.setAgentno(agent);
			workMonitor.setStatus(status);
			workMonitor.setUsername(username);
			workMonitor.setExtno(extno);
			workMonitor.setWorktype(worktype);
			if(lasttime!=null) {
				workMonitor.setDuration((int) (System.currentTimeMillis() - lasttime.getTime())/1000);
			}
			if(status.equals(XKDataContext.AgentStatusEnum.BUSY.toString())) {
				workMonitor.setBusy(true);
			}
			if(status.equals(XKDataContext.AgentStatusEnum.READY.toString())) {
				int count = workMonitorRes.countByAgentAndDatestrAndStatusAndOrgi(agent,XKTools.simpleDateFormat.format(new Date()), XKDataContext.AgentStatusEnum.READY.toString(), orgi) ;
				if(count == 0) {
					workMonitor.setFirsttime(true);
				}
			}
			if(current.equals(XKDataContext.AgentStatusEnum.NOTREADY.toString())) {
				List<WorkMonitor> workMonitorList = workMonitorRes.findByOrgiAndAgentAndDatestrAndFirsttime(orgi , agent , XKTools.simpleDateFormat.format(new Date()));
				if(CollectionUtils.isNotEmpty(workMonitorList)&& workMonitorList.size() > 0) {
					WorkMonitor firstWorkMonitor = workMonitorList.get(0) ;
					if(firstWorkMonitor.getFirsttimes() == 0) {
						firstWorkMonitor.setFirsttimes((int) (System.currentTimeMillis() - firstWorkMonitor.getCreatetime().getTime())/1000);
						workMonitorRes.save(firstWorkMonitor) ;
					}
				}
			}
			workMonitor.setCreatetime(new Date());
			workMonitor.setDatestr(XKTools.simpleDateFormat.format(new Date()));
			
			workMonitor.setName(agent);
			workMonitor.setOrgi(orgi);
			workMonitor.setSkill(skill);
			workMonitor.setUserid(userid);
			
			workMonitorRes.save(workMonitor) ;
		}
	}
	
	/**
	 * 坐席离线 
	 * @param userid
	 * @param status
	 */
	public static void deleteAgentStatus(String wcode , String orgi) {
		IAgentStatusService agentStatusRes = XKDataContext.getContext().getBean(IAgentStatusService.class) ;
		List<AgentStatus> agentStatusList = agentStatusRes.findByAgentnoAndOrgi(wcode);
		for(AgentStatus agentStatus : agentStatusList){
			ServiceQuene.recordAgentStatus(agentStatus.getAgentno(),agentStatus.getUsername() , agentStatus.getAgentno(), agentStatus.getSkill(), agentStatus.getAgentno(), agentStatus.isBusy() ? XKDataContext.AgentStatusEnum.BUSY.toString():XKDataContext.AgentStatusEnum.NOTREADY.toString(), XKDataContext.AgentStatusEnum.NOTREADY.toString(), XKDataContext.AgentWorkType.MEIDIACHAT.toString() , agentStatus.getOrgi() , agentStatus.getUpdatetime());
			agentStatus.setBusy(true);
			agentStatus.setStatus(XKDataContext.AgentStatusEnum.NOTREADY.toString());
			agentStatusRes.save(agentStatus);
		}
		SystemCache.removeAgentStatus(wcode);
    	ServiceQuene.publishMessage(orgi , "agent" , "leave" , wcode);
	}
	
	public static AgentUser deleteAgentUser(AgentUser agentUser, String orgi ,String endby)
			throws Exception {
		if (agentUser!=null) {
			if (!XKDataContext.AgentUserStatusEnum.END.toString().equals(agentUser.getStatus())) {
				serviceFinish(agentUser, orgi , endby);
			}
			if(StringUtils.isNotBlank(agentUser.getId())){
				ISysAgentUserService agentUserRes = XKDataContext.getContext().getBean(ISysAgentUserService.class) ;
				agentUser = agentUserRes.findAgentUserById(agentUser.getId());
				if(agentUser!=null){
					agentUserRes.delete(agentUser);
				}
			}
		}
		return agentUser;
	}
}
