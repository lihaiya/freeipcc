package com.sxx.jcc.webim.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.sxx.jcc.common.service.BaseService;
import com.sxx.jcc.webim.pojo.ChatMessage;
import com.sxx.jcc.webim.service.IChatMessageService;

@Service
public class ChatMessageServiceImpl  extends BaseService implements IChatMessageService{

	@Override
	public List<ChatMessage> findChatMessageList(String userid,String rownum) {
		List paramList = new ArrayList();
		String sql = "select * from (select * from xk_chat_message where userid=? or touser=? order by updatetime desc ) where 1=1 ";
		paramList.add(userid);
		paramList.add(userid);
		if(StringUtils.isNotBlank(rownum) && !"-1".equals(rownum)){
			sql+= " and rownum<=?";
			paramList.add(rownum);
		}else{
			sql+= " and updatetime>=updatetime-5";
		}
		sql+=" order by updatetime";
		return this.queryForObjectList(sql, ChatMessage.class, paramList.toArray());
	}

}
