package com.sxx.jcc.webim.util.router;

import com.sxx.jcc.common.utils.XKDataContext;
import com.sxx.jcc.common.utils.XKTools;
import com.sxx.jcc.system.pojo.AgentService;
import com.sxx.jcc.webim.pojo.MessageDataBean;
import com.sxx.jcc.webim.pojo.MessageOutContent;
import com.sxx.jcc.webim.service.ServiceQuene;
import com.sxx.jcc.webim.util.NettyClients;

public class MessageRouter extends Router{

	@Override
	public MessageDataBean handler(MessageDataBean inMessage) {
		MessageOutContent outMessage = new MessageOutContent() ;
		try {
			outMessage.setOrgi(inMessage.getOrgi());
			outMessage.setFromUser(inMessage.getToUser());
			outMessage.setToUser(inMessage.getFromUser());
			outMessage.setId(XKTools.genID());
			outMessage.setMessageType(inMessage.getMessageType());
			outMessage.setUser(inMessage.getUser());
			outMessage.setAgentUser(inMessage.getAgentUser());
			/**
			 * 首先交由 IMR处理 MESSAGE指令 ， 如果当前用户是在 坐席对话列表中， 则直接推送给坐席，如果不在，则执行 IMR
			 */
			if(outMessage.getAgentUser()!=null && outMessage.getAgentUser().getStatus()!=null){
				System.out.println("--------------------------inqueue");
				if(outMessage.getAgentUser().getStatus().equals(XKDataContext.AgentUserStatusEnum.INQUENE.toString())){
					int queneIndex = ServiceQuene.getQueneIndex(inMessage.getAgentUser().getAgent() , inMessage.getOrgi(), inMessage.getAgentUser().getSkill()) ;
					if(XKDataContext.AgentUserStatusEnum.INQUENE.toString().equals(outMessage.getAgentUser().getStatus())){
						outMessage.setMessage(ServiceQuene.getQueneMessage(queneIndex , outMessage.getAgentUser().getChannel(),inMessage.getOrgi()));
					}
				}else if(outMessage.getAgentUser().getStatus().equals(XKDataContext.AgentUserStatusEnum.INSERVICE.toString())){
					
				}
			}else if(XKDataContext.MessageTypeEnum.NEW.toString().equals(inMessage.getMessageType())){
				/**
				 * 找到空闲坐席，如果未找到坐席， 则将该用户放入到 排队队列 
				 * 
				 */
				System.out.println("--------------------------new");
				AgentService agentService = ServiceQuene.allotAgent(inMessage.getAgentUser(), inMessage.getOrgi()) ;
				if(agentService!=null && XKDataContext.AgentUserStatusEnum.INSERVICE.toString().equals(agentService.getStatus())){
					outMessage.setMessage(ServiceQuene.getSuccessMessage(agentService , inMessage.getAgentUser().getChannel(),inMessage.getOrgi()));
					NettyClients.getInstance().sendAgentEventMessage(agentService.getAgentno(), XKDataContext.MessageTypeEnum.NEW.toString(), inMessage.getAgentUser());//send to agnet
				}else{
					if(agentService!=null && agentService.getQueneindex() > 0){	//当前有坐席
						outMessage.setMessage(ServiceQuene.getQueneMessage(agentService.getQueneindex(), inMessage.getAgentUser().getChannel(), inMessage.getOrgi()));
					}else{
						outMessage.setMessage(ServiceQuene.getNoAgentMessage(agentService.getQueneindex(), inMessage.getAgentUser().getChannel(), inMessage.getOrgi()));
					}
				}
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return outMessage ;
	}

}
