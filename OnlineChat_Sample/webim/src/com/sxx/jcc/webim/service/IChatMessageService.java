package com.sxx.jcc.webim.service;

import java.util.List;

import com.sxx.jcc.common.service.IBaseService;
import com.sxx.jcc.webim.pojo.ChatMessage;

public interface IChatMessageService extends IBaseService{
	List<ChatMessage> findChatMessageList(String userid,String rownum);

}
