package com.sxx.jcc.core.server.handler;

import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.tools.ant.util.DateUtils;
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
import com.sxx.jcc.core.model.IMClient;
import com.sxx.jcc.core.server.message.AgentStatusMessage;
import com.sxx.jcc.core.server.message.NewRequestMessage;
import com.sxx.jcc.system.pojo.AgentUser;
import com.sxx.jcc.webim.pojo.ChatMessage;
import com.sxx.jcc.webim.pojo.Contacts;
import com.sxx.jcc.webim.pojo.MessageInContent;
import com.sxx.jcc.webim.pojo.SocketIOMessage;
import com.sxx.jcc.webim.service.ServiceQuene;
import com.sxx.jcc.webim.util.MessageUtils;
import com.sxx.jcc.webim.util.NettyClients;
import com.sxx.jcc.webim.util.OnlineUserUtils;

public class IMEventHandler     
{  
	protected Logger log = Logger.getLogger(IMEventHandler.class.getName());
	protected SocketIOServer server;
	
    @Autowired  
    public IMEventHandler(SocketIOServer server)   
    {  
        this.server = server ;
    }  
    

	@OnConnect  
    public void onConnect(SocketIOClient client) 
    {
    	String sa = client.getRemoteAddress().toString();
        String clientIp = sa.substring(1,sa.indexOf(":"));//获取客户端ip
        System.out.println(clientIp+"-------------------------"+"客户端已连接");
        Map<?, ?> params = client.getHandshakeData().getUrlParams();

        //获取客户端连接的uuid参数
        Object object = params.get("myparam");
        String test = "";
        if(object != null){
            test = ((List<String>)object).get(0);
        }
        SocketIOMessage message = new SocketIOMessage();
		message.setMessage("测2试");
		client.sendEvent(XKDataContext.MessageTypeEnum.MESSAGE.toString(), message);
    }  
    
    
  //消息接收入口，用于接受网站资源用户传入的 个人信息
    @OnEvent(value = "new")  
    public void onEvent(SocketIOClient client, AckRequest request, IMClient im, Contacts contacts)   
    {
    	
    	try {
    		client.set("im", im);
    		System.out.println("---------------userid="+im.getUser()+" session="+im.getSession());
			if(StringUtils.isNotBlank(im.getUser())){
				// 用户进入到对话连接 ， 排队用户请求 , 如果返回失败，表示当前坐席全忙，用户进入排队状态，当前提示信息 显示 当前排队的队列位置，不可进行对话，用户发送的消息作为留言处理
				client.getHandshakeData().getHttpHeaders();
				InetSocketAddress address = (InetSocketAddress) client.getRemoteAddress()  ;
				String ip = XKTools.getIpAddr(client.getHandshakeData().getHttpHeaders(), address.getHostName()) ;
				NewRequestMessage newRequestMessage = OnlineUserUtils.newRequestMessage(im.getUser(), im.getOrgi(), im.getSession(), im.getAppid(), ip , im.getOsname() , im.getBrowser() , XKDataContext.ChannelTypeEnum.WEBIM.toString() , im.getSkill(), im.getAgent(), im.getNickname(), im.getTitle() , im.getUrl(), im.getTraceid() , XKDataContext.ChatInitiatorType.USER.toString()) ;
				NettyClients.getInstance().putIMEventClient(im.getUser(), client);
		 
				if(newRequestMessage!=null && !StringUtils.isBlank(newRequestMessage.getMessage())){
					SocketIOMessage message = new SocketIOMessage();
					message.setMessage(newRequestMessage.getMessage());
					Map<String, String> map = new HashMap<String, String>();
				    map.put("message", newRequestMessage.getMessage());
					MessageInContent outMessage = new MessageInContent() ;
			    	outMessage.setMessage(newRequestMessage.getMessage());
			    	if(newRequestMessage.isNoagent()){
			    		outMessage.setMessageType(XKDataContext.MessageTypeEnum.NOAGENTS.toString());
			    	}else{
			    		if(newRequestMessage.getQueueindex()>0){
			    			outMessage.setMessageType(XKDataContext.MessageTypeEnum.INQUEUE.toString());
			    		}
			    	}
			    	
			    	outMessage.setCalltype(XKDataContext.CallTypeEnum.IN.toString());
			    	outMessage.setNickName(newRequestMessage.getUsername());
					outMessage.setCreatetime(XKTools.dateFormate.format(new Date()));
					outMessage.setAgentserviceid(newRequestMessage.getAgentserviceid());
					
					client.sendEvent(XKDataContext.MessageTypeEnum.STATUS.toString(), outMessage);//send to user client
				}
			}else{
				client.disconnect();
			}
		} catch (Exception e) {
			e.printStackTrace();
			client.disconnect();
		}
    }  
    
    //添加@OnDisconnect事件，客户端断开连接时调用，刷新客户端信息  
    @OnDisconnect  
    public void onDisconnect(SocketIOClient client)  
    {  
    	System.out.println("-------------------------客户端已断开");
    	IMClient im = client.get("im") ;
		if(im!=null && im.getUser()!=null){
			try {
				/**
				 * 用户主动断开服务
				 */
				ServiceQuene.serviceFinish(SystemCache.getCachedAgentUser(im.getUser()), im.getOrgi() , XKDataContext.EndByType.USER.toString()); 
			} catch (Exception e) {
				e.printStackTrace();
			}
			NettyClients.getInstance().removeIMEventClient(im.getUser() ,XKTools.getContextID(client.getSessionId().toString()));
		}
    }  
      
    
    
    //消息接收入口，坐席状态更新  
    @OnEvent(value = "agentstatus")  
    public void onEvent(SocketIOClient client, AckRequest request, AgentStatusMessage data)   
    {
    	System.out.println(data.getMessage());
    } 
    
    //消息接收入口，收发消息，用户向坐席发送消息和 坐席向用户发送消息  
    @OnEvent(value = "message")  
    public void onEvent(SocketIOClient client, AckRequest request, ChatMessage data) throws UnsupportedEncodingException   
    {
    	if(data.getType() == null){
    		data.setType("message");
    	}
    	if(StringUtils.isNotBlank(data.getMessage()) && data.getMessage().length() > 300){
    		data.setMessage(data.getMessage().substring(0 , 300));
    	}
    	/**
		 * 处理表情
		 */
    	data.setMessage(XKTools.processEmoti(data.getMessage()));
		
		Document document = Jsoup.parse(data.getMessage()) ;
		if(document.select("script") != null) {
			//目前只检查了 Script ，其他还有IMG的情况（IMG需要排除表情） 
			data.setFilterscript(true);
		}
		data.setMessage(document.text());
    	//data.setCreatetime(DateUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
    	MessageUtils.createMessage(data , XKDataContext.MediaTypeEnum.TEXT.toString(), data.getUserid());
    } 
}  