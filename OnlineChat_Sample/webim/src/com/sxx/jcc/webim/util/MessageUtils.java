package com.sxx.jcc.webim.util;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import com.sxx.jcc.common.utils.XKDataContext;
import com.sxx.jcc.core.cache.SystemCache;
import com.sxx.jcc.system.pojo.AgentUser;
import com.sxx.jcc.webim.pojo.AgentUserTask;
import com.sxx.jcc.webim.pojo.ChatMessage;
import com.sxx.jcc.webim.pojo.MessageOutContent;
import com.sxx.jcc.webim.service.IAgentUserTaskService;
import com.sxx.jcc.webim.service.IChatMessageService;
import com.sxx.jcc.webim.service.impl.AgentUserTaskServiceImpl;
import com.sxx.jcc.webim.service.impl.ChatMessageServiceImpl;

public class MessageUtils {
	/**
	 * 
	 * @param image
	 * @param userid
	 */
	public static ChatMessage uploadImage(String image ,String attachid, int size , String name  , String userid){
		return createMessage(image , size , name , XKDataContext.MediaTypeEnum.IMAGE.toString(), userid , attachid);
	}
	public static ChatMessage uploadImage(String image  , String attachid, int size , String name , String channel , String userid , String username , String appid , String orgi){
		return createMessage(image , size , name , channel , XKDataContext.MediaTypeEnum.IMAGE.toString(), userid , username, appid , orgi , attachid);
	}
	
	/**
	 * 
	 * @param image
	 * @param userid
	 */
	public static ChatMessage uploadFile(String url , int size , String name , String userid , String attachid){
		return createMessage(url , size , name , XKDataContext.MediaTypeEnum.FILE.toString(), userid , attachid);
	}
	public static ChatMessage uploadFile(String url , int size , String name, String channel , String userid , String username , String appid , String orgi , String attachid){
		return createMessage(url , size , name , channel , XKDataContext.MediaTypeEnum.FILE.toString(), userid , username, appid , orgi , attachid);
	}
	
	/**
	 * 
	 * @param image
	 * @param userid
	 */
	public static ChatMessage uploadVoice(String url , int size , String name , String userid , String attachid , int duration){
		return createMessage(url , size , name , XKDataContext.MediaTypeEnum.VOICE.toString(), userid , attachid , duration);
	}
	public static ChatMessage uploadVoice(String url , int size , String name, String channel , String userid , String username , String appid , String orgi , String attachid, int duration){
		return createMessage(url , size , name , channel , XKDataContext.MediaTypeEnum.VOICE.toString(), userid , username, appid , orgi , attachid , duration);
	}
	public static ChatMessage createMessage(String message , int length , String name , String msgtype , String userid , String attachid){
		return createMessage(message, length, name, msgtype, userid, attachid, 0) ;
	}
	
	public static ChatMessage createMessage(String message , int length , String name , String msgtype , String userid , String attachid , int duration){
		AgentUser agentUser = (AgentUser) SystemCache.fetchCacheDate(userid);
		ChatMessage data = new ChatMessage() ;
		data.setFilesize(length);
		data.setFilename(name);
		data.setAttachmentid(attachid);
		
		data.setMessage(message);
		
		data.setMsgtype(msgtype);
		data.setDuration(duration);
		data.setType(XKDataContext.MessageTypeEnum.MESSAGE.toString());
		
		if(agentUser != null){
			data.setUserid(agentUser.getUserid());
			data.setUsername(agentUser.getUsername());
			data.setTouser(agentUser.getAgentno());
			data.setAppid(agentUser.getAppid());
			data.setOrgi(agentUser.getOrgi());
			createMessage(data, msgtype, userid);
		}
		return data ;
	}
	/**
	 * 发送消息
	 * @param data
	 * @param msgtype
	 */
	private static void sendMessage(ChatMessage data ,MessageOutContent outMessage , String msgtype) {
		if(!StringUtils.isBlank(data.getUserid()) && XKDataContext.MessageTypeEnum.MESSAGE.toString().equals(data.getType())){
    		NettyClients.getInstance().sendIMEventMessage(data.getUserid(), XKDataContext.MessageTypeEnum.MESSAGE.toString(), outMessage);
    	}
	}
	
	public static ChatMessage createMessage(String message , int length , String name ,String channel ,String msgtype , String userid , String username , String appid , String orgi , String attachid ){
		return createMessage(message, length, name, channel, msgtype, userid, username, appid, orgi, attachid, 0) ;
	}
	
	private static ChatMessage createMessage(String message , int length , String name ,String channel ,String msgtype , String userid , String username , String appid , String orgi , String attachid , int duration){
		ChatMessage data = new ChatMessage() ;
		if(!StringUtils.isBlank(userid)){
			data.setUserid(userid);
			data.setUsername(username);
			data.setTouser(userid);
			data.setAppid(appid);
			data.setOrgi(orgi);
			data.setChannel(channel);
			data.setMessage(message);
			
			data.setFilesize(length);
			data.setFilename(name);
			data.setAttachmentid(attachid);
			
			data.setMsgtype(msgtype);
			
			data.setDuration(duration);
			
			data.setType(XKDataContext.MessageTypeEnum.MESSAGE.toString());
			createAiMessage(data , appid , channel, XKDataContext.CallTypeEnum.IN.toString() , XKDataContext.AiItemType.USERINPUT.toString() , msgtype, data.getUserid());
		}
		return data ;
	}
	
	public static void createMessage(ChatMessage data , String msgtype , String userid){
		AgentUser agentUser =  SystemCache.getCachedAgentUser(userid);
    	MessageOutContent outMessage = new MessageOutContent();
    	
    	outMessage.setMessage(data.getMessage());
    	outMessage.setFilename(data.getFilename());
    	outMessage.setFilesize(data.getFilesize());
    	
    	
    	outMessage.setMessageType(msgtype);
    	outMessage.setCalltype(XKDataContext.CallTypeEnum.IN.toString());
    	outMessage.setAgentUser(agentUser);
    	outMessage.setSnsAccount(null);
    	outMessage.setDuration(data.getDuration());
    	
    	outMessage.setId(data.getId());
    	
    	MessageOutContent statusMessage = null ;
    	
    	if(agentUser!=null){
    		if(StringUtils.isBlank(agentUser.getAgentno())){
        		statusMessage = new MessageOutContent() ;
        		statusMessage.setMessage(data.getMessage());
        		statusMessage.setMessageType(XKDataContext.MessageTypeEnum.STATUS.toString());
        		statusMessage.setCalltype(XKDataContext.CallTypeEnum.OUT.toString());
        		statusMessage.setMessage("当前坐席全忙，请稍候");
        	}else{
        		data.setUserid(agentUser.getUserid());
        		data.setTouser(agentUser.getAgentno());
        		data.setUsername(agentUser.getUsername());
        		data.setAgentuser(agentUser.getId());
        		
        		data.setAgentserviceid(agentUser.getAgentserviceid());
        		
        		data.setAppid(agentUser.getAppid());
        		data.setOrgi(agentUser.getOrgi());
        		
        		data.setMsgtype(msgtype);
        		
        		
        		data.setUsession(agentUser.getUserid());				//agentUser作为 session id
        		data.setContextid(agentUser.getContextid());
        		data.setCalltype(XKDataContext.CallTypeEnum.IN.toString());
        		if(!StringUtils.isBlank(agentUser.getAgentno())){
        			data.setTouser(agentUser.getAgentno());
        		}
        		data.setChannel(agentUser.getChannel());
        		
        		outMessage.setContextid(agentUser.getContextid());
        		outMessage.setFromUser(data.getUserid());
        		outMessage.setToUser(data.getTouser());
        		outMessage.setChannelMessage(data);
        		outMessage.setNickName(agentUser.getUsername());
        		outMessage.setCreatetime(data.getCreatetime());
        		
        		if(data.getType()!=null && data.getType().equals(XKDataContext.MessageTypeEnum.MESSAGE.toString())){
        			IAgentUserTaskService agentUserTaskService = XKDataContext.getContext().getBean(IAgentUserTaskService.class);
    	    		AgentUserTask agentUserTask = agentUserTaskService.getAgentUserTask(agentUser.getId()) ;
    	    		if(agentUserTask!=null){
    	    			if(agentUserTask.getLastgetmessage() != null && agentUserTask.getLastmessage()!=null){
    		    			data.setLastagentmsgtime(agentUserTask.getLastgetmessage());
    		    			data.setLastmsgtime(agentUserTask.getLastmessage());
    		    			data.setAgentreplyinterval((int)((System.currentTimeMillis() - agentUserTask.getLastgetmessage().getTime())/1000));	//坐席上次回复消息的间隔
    		    			data.setAgentreplytime((int)((System.currentTimeMillis() - agentUserTask.getLastmessage().getTime())/1000));		//坐席回复消息花费时间
    	    			}
    	    			agentUserTask.setUserasks(agentUserTask.getUserasks()+1);	//总咨询记录数量
    	    			agentUserTask.setAgentreplytime(agentUserTask.getAgentreplytime() + data.getAgentreplyinterval());	//总时长
    	    			if(agentUserTask.getUserasks()>0){
    	    				agentUserTask.setAvgreplytime(agentUserTask.getAgentreplytime() / agentUserTask.getUserasks());
    	    			}
    	    			
    		    		agentUserTask.setLastmessage(new Date());
    		    		agentUserTask.setWarnings("0");
    		    		agentUserTask.setWarningtime(null);
    		    		
    		    		/**
    		    		 * 脚本过滤
    		    		 */
    		    		if(data.isFilterscript()) {
    		    			agentUserTask.setFilterscript(agentUserTask.getFilterscript()+1);
    		    		}
    		    		
    		    		/**
    		    		 * 去掉坐席超时回复消息提醒
    		    		 */
    		    		agentUserTask.setReptime(null);
    		    		agentUserTask.setReptimes("0");
    		    		
    		    		agentUserTask.setLastmsg(data.getMessage().length() > 100 ? data.getMessage().substring(0 , 100) : data.getMessage());
    		    		agentUserTask.setTokenum(agentUserTask.getTokenum()+1);
    		    		data.setTokenum(agentUserTask.getTokenum());
    		    		agentUserTaskService.save(agentUserTask) ;
    	    		}
        		}
        		
        		/**
        		 * 保存消息
        		 */
        		if(XKDataContext.MessageTypeEnum.MESSAGE.toString().equals(data.getType())){
        			IChatMessageService chatMessageService = XKDataContext.getContext().getBean(IChatMessageService.class);
        			chatMessageService.add(data);
        		}
        	}
    		
    	}
    	if(StringUtils.isNotBlank(data.getUserid()) && XKDataContext.MessageTypeEnum.MESSAGE.toString().equals(data.getType())){
    		NettyClients.getInstance().sendIMEventMessage(data.getUserid(), XKDataContext.MessageTypeEnum.MESSAGE.toString(), outMessage);//发给自已
    		if(statusMessage!=null){
    			NettyClients.getInstance().sendIMEventMessage(data.getUserid(), XKDataContext.MessageTypeEnum.STATUS.toString(), statusMessage);
    		}
    	}
    	if(agentUser!=null && StringUtils.isNotBlank(agentUser.getAgentno())){
    		//将消息发送给 坐席
    		NettyClients.getInstance().sendAgentEventMessage(agentUser.getAgentno(), XKDataContext.MessageTypeEnum.MESSAGE.toString(), data);
    	}
	}
	
	public static ChatMessage createMessage(String message , int length , String name ,String channel ,String msgtype , String userid , String username , String appid , String orgi , String attachid ,String aiid){
		ChatMessage data = new ChatMessage() ;
		if(!StringUtils.isBlank(userid)){
			data.setUserid(userid);
			data.setUsername(username);
			data.setTouser(userid);
			data.setAppid(appid);
			data.setOrgi(orgi);
			data.setChannel(channel);
			data.setMessage(message);
			
			data.setAiid(aiid);
			
			data.setFilesize(length);
			data.setFilename(name);
			data.setAttachmentid(attachid);
			
			data.setMsgtype(msgtype);
			
			data.setType(XKDataContext.MessageTypeEnum.MESSAGE.toString());
			createAiMessage(data , appid , channel, XKDataContext.CallTypeEnum.IN.toString() , XKDataContext.AiItemType.USERINPUT.toString() , msgtype, data.getUserid());
		}
		return data ;
	}
	
	public static MessageOutContent createAiMessage(ChatMessage data , String appid,String channel , String direction , String chatype, String msgtype , String userid){
    	MessageOutContent outMessage = new MessageOutContent() ;
    	
    	outMessage.setMessage(data.getMessage());
    	outMessage.setMessageType(msgtype);
    	outMessage.setCalltype(direction);
    	outMessage.setAgentUser(null);
    	outMessage.setSnsAccount(null);
    	outMessage.setDuration(data.getDuration());
    	
    	if(!StringUtils.isBlank(userid)){
    		data.setUserid(userid);
    		data.setTouser(userid);
    		
    		data.setAgentuser(userid);
    		
    		data.setChatype(chatype);
    		
    		data.setChannel(channel);
    		
    		data.setAppid(data.getAppid());
    		data.setOrgi(data.getOrgi());
    		
    		data.setMsgtype(msgtype);
    		
    		data.setUsession(data.getUserid());				//agentUser作为 session id
    		data.setCalltype(direction);
    		
    		if(data.isQuickagent()) {
    			outMessage.setQuickagent(true);
    		}
    		outMessage.setContextid(data.getContextid());
    		outMessage.setFromUser(data.getUserid());
    		outMessage.setToUser(data.getTouser());
    		outMessage.setChannelMessage(data);
    		outMessage.setNickName(data.getUsername());
    		outMessage.setCreatetime(data.getCreatetime());
    		outMessage.setTopic(!StringUtils.isBlank(data.getTopicid()));
    		data.setUpdatetime(new Date());
    		
    		
    		/**
    		 * 保存消息
    		 */
    		if(XKDataContext.MessageTypeEnum.MESSAGE.toString().equals(data.getType())){
    			IChatMessageService chatMessageService = XKDataContext.getContext().getBean(ChatMessageServiceImpl.class);
    			chatMessageService.save(data) ;
    		}
    		outMessage.setId(data.getId());
    		AgentUser agentUser = (AgentUser) SystemCache.fetchCacheDate(userid);
    		if(agentUser!=null && !StringUtils.isBlank(agentUser.getAgentno())){
        		//将消息发送给 坐席
    			
    			if(XKDataContext.CallTypeEnum.OUT.toString().equals(direction)) {
    				data.setUserid(agentUser.getAgentno());
    			}
        		NettyClients.getInstance().sendAgentEventMessage(agentUser.getAgentno(), XKDataContext.MessageTypeEnum.MESSAGE.toString(), data);
        	}
    	}
    	return outMessage ;
	}
	
}
