package com.sxx.jcc.webim.pojo;

import com.sxx.jcc.system.pojo.AgentUser;


public interface MessageDataBean {
	
	public String getId() ;
	
	public String getNickName();
	
	
	public String getOrgi() ;
	
	/**
	 * 对话的文本内容
	 * @return
	 */
	public String getMessage() ;
	
	/**
	 * 消息类型
	 * @return
	 */
	public String getMessageType() ;
	
	/**
	 * 来源用户
	 * @return
	 */
	public String getFromUser() ;
	
	/**
	 * 目标用户
	 * @return
	 */
	public String getToUser();
	/**
	 * 渠道信息
	 * @return
	 */
	public SNSAccount getSnsAccount();
	/**
	 * 坐席用户信息
	 * @return
	 */
	public AgentUser getAgentUser() ;
	
	/**
	 * 获取渠道来源的消息信息
	 * @return
	 */
	public Object getChannelMessage() ;
	
	/**
	 * 渠道上对应的用户信息
	 * @return
	 */
	public Object getUser();
	
	
	public void setContextid(String contextid) ;
	
	public String getContextid() ;
	
}
