package com.sxx.jcc.core.server.handler;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.corundumstudio.socketio.annotation.OnEvent;
import com.sxx.jcc.common.utils.XKDataContext;
import com.sxx.jcc.common.utils.XKTools;
import com.sxx.jcc.core.cache.SystemCache;
import com.sxx.jcc.core.server.message.AgentServiceMessage;
import com.sxx.jcc.core.server.message.AgentStatusMessage;
import com.sxx.jcc.system.pojo.AgentUser;
import com.sxx.jcc.system.pojo.SessionConfig;
import com.sxx.jcc.system.pojo.SysStaff;
import com.sxx.jcc.system.service.ISysStaffService;
import com.sxx.jcc.webim.pojo.AgentStatus;
import com.sxx.jcc.webim.pojo.AgentUserTask;
import com.sxx.jcc.webim.pojo.ChatMessage;
import com.sxx.jcc.webim.pojo.MessageOutContent;
import com.sxx.jcc.webim.service.IAgentStatusService;
import com.sxx.jcc.webim.service.IAgentUserTaskService;
import com.sxx.jcc.webim.service.IChatMessageService;
import com.sxx.jcc.webim.service.ISysAgentUserService;
import com.sxx.jcc.webim.service.ServiceQuene;
import com.sxx.jcc.webim.util.NettyClients;
import com.sxx.jcc.webim.util.router.OutMessageRouter;
  
public class AgentEventHandler 
{  

	protected SocketIOServer server;
	
    @Autowired  
    public AgentEventHandler(SocketIOServer server)   
    {  
    	this.server = server ;
    }  
    
    @OnConnect  
    public void onConnect(SocketIOClient client)  
    {  
    	System.out.println("---------------------------------------------onConnect");
    	String user = client.getHandshakeData().getSingleUrlParam("userid") ;
    	/**String orgi = client.getHandshakeData().getSingleUrlParam("orgi") ;
    	String session = client.getHandshakeData().getSingleUrlParam("session") ;
    	String admin = client.getHandshakeData().getSingleUrlParam("admin") ;**/
		if(StringUtils.isNotBlank(user)){
			client.set("agentno", user);
			/**IAgentStatusService agentStatusService = XKDataContext.getContext().getBean(IAgentStatusService.class);
			ISysStaffService staffservice = XKDataContext.getContext().getBean(ISysStaffService.class);
			SysStaff staff = staffservice.getStaffBywcode(user);
			List<AgentStatus> agentStatusList = agentStatusService.findByAgentnoAndOrgi(user);
			AgentStatus agentStatus;
			if(CollectionUtils.isNotEmpty(agentStatusList) && agentStatusList.size() > 0){
	    		agentStatus = agentStatusList.get(0) ;
	    		agentStatus.setBusy(true);
	    		agentStatus.setUsers(0);
	    		agentStatus.setLogindate(new Date());
				agentStatus.setUpdatetime(new Date());
				agentStatusService.update(agentStatus);
			}else{
				agentStatus= new AgentStatus();
				agentStatus.setAgentno(user);
				agentStatus.setBusy(true);
				agentStatus.setUsername(staff.getWcode());
				agentStatus.setUpdatetime(new Date());
				agentStatus.setLogindate(new Date());
				agentStatusService.add(agentStatus);
			}
			 SystemCache.putAgentStatus(agentStatus);**/
			
			/**InetSocketAddress address = (InetSocketAddress) client.getRemoteAddress()  ;
			String ip = UKTools.getIpAddr(client.getHandshakeData().getHttpHeaders(), address.getHostString()) ;
			
	    	
	    	WorkSessionRepository workSessionRepository = UKDataContext.getContext().getBean(WorkSessionRepository.class) ;
	    	int count = workSessionRepository.countByAgentAndDatestrAndOrgi(user, UKTools.simpleDateFormat.format(new Date()), orgi) ;
	    	
	    	workSessionRepository.save(UKTools.createWorkSession(user, UKTools.getContextID(client.getSessionId().toString()), session, orgi, ip, address.getHostName() , admin , count == 0)) ;
	    	**/
			NettyClients.getInstance().putAgentEventClient(user, client);
		}
    }  
      
    //添加@OnDisconnect事件，客户端断开连接时调用，刷新客户端信息  
    @OnDisconnect  
    public void onDisconnect(SocketIOClient client)  
    {  
    	String user = client.getHandshakeData().getSingleUrlParam("userid") ;
		String orgi = client.getHandshakeData().getSingleUrlParam("orgi") ;
		String admin = client.getHandshakeData().getSingleUrlParam("admin") ;
		if(!StringUtils.isBlank(user)){
			SessionConfig sessionConfig = ServiceQuene.initSessionConfig(orgi) ;
			System.out.println("---------------------------------------------onDisconnect");
			//if(sessionConfig!=null && sessionConfig.isAgentautoleave()) {
				ServiceQuene.deleteAgentStatus(user, orgi);
				NettyClients.getInstance().removeAgentEventClient(user , XKTools.getContextID(client.getSessionId().toString()));
				
				/**WorkSessionRepository workSessionRepository = UKDataContext.getContext().getBean(WorkSessionRepository.class) ;
				List<WorkSession> workSessionList = workSessionRepository.findByOrgiAndClientid(orgi, UKTools.getContextID(client.getSessionId().toString())) ;
				if(workSessionList.size() > 0) {
					WorkSession workSession = workSessionList.get(0) ;
					workSession.setEndtime(new Date());
					if(workSession.getBegintime()!=null) {
						workSession.setDuration((int) (System.currentTimeMillis() - workSession.getBegintime().getTime()));
					}else if(workSession.getCreatetime()!=null) {
						workSession.setDuration((int) (System.currentTimeMillis() - workSession.getCreatetime().getTime()));
					}
					if(workSession.isFirsttime()) {
						workSession.setFirsttimes(workSession.getDuration());
					}
					workSessionRepository.save(workSession) ;
				}**/
			//}
		}
    }  
      
    //消息接收入口，当接收到消息后，查找发送目标客户端，并且向该客户端发送消息，且给自己发送消息  
    @OnEvent(value = "service")  
    public void onEvent(SocketIOClient client, AckRequest request, AgentServiceMessage data)   
    {
    	
    }  
    
	//消息接收入口，当接收到消息后，查找发送目标客户端，并且向该客户端发送消息，且给自己发送消息  
    @OnEvent(value = "status")  
    public void onEvent(SocketIOClient client, AckRequest request, AgentStatusMessage data)   
    {
    	System.out.println("---------------------------agent event handle");
    	
    }
    
  //消息接收入口，当接收到消息后，查找发送目标客户端，并且向该客户端发送消息，且给自己发送消息  
    @OnEvent(value = "message")  
    public void onEvent(SocketIOClient client, AckRequest request, ChatMessage data)   
    {
    	String user = client.getHandshakeData().getSingleUrlParam("userid") ;
    	String touser = client.getHandshakeData().getSingleUrlParam("touserid") ;
    	AgentUser agentUser = SystemCache.getCachedAgentUser(data.getTouser());
    	MessageOutContent outMessage = new MessageOutContent() ;
    	
    	Document document = Jsoup.parse(data.getMessage()) ;
		if(document.select("script") != null) {
			//目前只检查了 Script ，其他还有IMG的情况（IMG需要排除表情） 
			data.setFilterscript(true);
		}
		data.setMessage(document.text());
    	
    	outMessage.setMessage(data.getMessage());
    	if(XKDataContext.MediaTypeEnum.COOPERATION.toString().equals(data.getMsgtype())){
    		outMessage.setMessageType(XKDataContext.MediaTypeEnum.COOPERATION.toString());
		}else{
			outMessage.setMessageType(XKDataContext.MediaTypeEnum.TEXT.toString());
		}
    	
    	outMessage.setAttachmentid(data.getAttachmentid());
    	
    	outMessage.setCalltype(XKDataContext.CallTypeEnum.OUT.toString());
    	outMessage.setAgentUser(agentUser);
    	outMessage.setSnsAccount(null);
    	AgentStatus agentStatus = SystemCache.getCachedAgentStatus(data.getUserid());//should be agentstatus.agentno(wcode)
    	
    	if(agentUser == null){
    		ISysAgentUserService agentUserService = XKDataContext.getContext().getBean(ISysAgentUserService.class);
    		agentUser = agentUserService.findAgentUserById(touser) ;//touser  agentuser.userid
    		try {
				ServiceQuene.serviceFinish(agentUser, data.getOrgi() , XKDataContext.EndByType.AGENT.toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
    	}
    	
    	if(agentUser!=null && user!=null && user.equals(agentUser.getAgentno())){
    		data.setId(XKTools.getUUID());
    		data.setContextid(agentUser.getContextid());
    		
    		data.setAgentserviceid(agentUser.getAgentserviceid());
    		data.setCreater(agentUser.getAgentno());
    		
    		if(XKDataContext.MediaTypeEnum.COOPERATION.toString().equals(data.getMsgtype())){
    			data.setMsgtype(XKDataContext.MediaTypeEnum.COOPERATION.toString());
    		}else{
    			data.setMsgtype(XKDataContext.MediaTypeEnum.TEXT.toString());
    		}
    		
    		data.setCalltype(XKDataContext.CallTypeEnum.OUT.toString());
    		if(!StringUtils.isBlank(agentUser.getAgentno())){
    			data.setTouser(agentUser.getUserid());
    		}
    		data.setChannel(agentUser.getChannel());
    		
    		data.setUsession(agentUser.getUserid());
    		
    		outMessage.setContextid(agentUser.getContextid());
    		outMessage.setFromUser(data.getUserid());
    		outMessage.setToUser(data.getTouser());
    		outMessage.setChannelMessage(data);
    		if(agentStatus!=null){
        		data.setUsername(agentStatus.getUsername());
        		outMessage.setNickName(agentStatus.getUsername());
        	}else{
        		outMessage.setNickName(data.getUsername());
        	}
    		outMessage.setCreatetime(data.getCreatetime());
    		
    		IAgentUserTaskService agentUserTaskService = XKDataContext.getContext().getBean(IAgentUserTaskService.class);
    		AgentUserTask agentUserTask = agentUserTaskService.getAgentUserTask(agentUser.getId()) ;
    		
    		if(agentUserTask!=null){
	    		if(agentUserTask.getLastgetmessage() != null && agentUserTask.getLastmessage()!=null){
	    			data.setLastagentmsgtime(agentUserTask.getLastgetmessage());
	    			data.setLastmsgtime(agentUserTask.getLastmessage());
	    			data.setAgentreplyinterval((int)((System.currentTimeMillis() - agentUserTask.getLastgetmessage().getTime())/1000));	//坐席上次回复消息的间隔
	    			data.setAgentreplytime((int)((System.currentTimeMillis() - agentUserTask.getLastmessage().getTime())/1000));		//坐席回复消息花费时间
	    		}
	    		
	    		agentUserTask.setAgentreplys(agentUserTask.getAgentreplys()+1);	//总咨询记录数量
    			agentUserTask.setAgentreplyinterval(agentUserTask.getAgentreplyinterval() + data.getAgentreplyinterval());	//总时长
    			if(agentUserTask.getAgentreplys()>0){
    				agentUserTask.setAvgreplyinterval(agentUserTask.getAgentreplyinterval() / agentUserTask.getAgentreplys());
    			}
    			
	    		agentUserTask.setLastgetmessage(new Date());
	    		
	    		if(data.isFilterscript()) {
	    			agentUserTask.setFilteragentscript(agentUserTask.getFilteragentscript()+1);
	    		}
	    		
	    		if(agentUserTask.getFirstreplytime() == 0) {
	    			if(agentUserTask.getLastgetmessage() != null && agentUserTask.getLastmessage()!=null){
	    				agentUserTask.setFirstreplytime(data.getAgentreplytime());
	    			}else if(agentUserTask.getServicetime()!=null){
	    				agentUserTask.setFirstreplytime((int)(System.currentTimeMillis() - agentUserTask.getServicetime().getTime())/1000);
	    			}
	    		}
//	    		agentUserTask.setReptime(null);
//	    		agentUserTask.setReptimes("0");
	    		
	    		agentUserTaskService.save(agentUserTask) ;
    		}
    		
    		/**
    		 * 保存消息
    		 */
    		IChatMessageService chatMessageService = XKDataContext.getContext().getBean(IChatMessageService.class);
    		chatMessageService.add(data) ;

    		//client.sendEvent(XKDataContext.MessageTypeEnum.MESSAGE.toString(), data);
    		NettyClients.getInstance().sendAgentEventMessage(data.getUserid(), XKDataContext.MessageTypeEnum.MESSAGE.toString(), data);
	    	if(!StringUtils.isBlank(data.getTouser())){
	    		OutMessageRouter router = (OutMessageRouter) XKDataContext.getContext().getBean(agentUser.getChannel()) ;
	    		if(router!=null){
	    			router.handler(data.getTouser(), XKDataContext.MessageTypeEnum.MESSAGE.toString(), agentUser.getAppid(), outMessage);
	    		}
	    	}
    	}else if(user!=null && agentUser!=null && !user.equals(agentUser.getAgentno())){
    		client.sendEvent(XKDataContext.MessageTypeEnum.END.toString(), agentUser);
    	}
    }  
}  