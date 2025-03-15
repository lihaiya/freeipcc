package com.sxx.jcc.webim.util.router;

import com.sxx.jcc.webim.pojo.MessageOutContent;

public interface OutMessageRouter {
	
	public void handler(String touser, String msgtype , String appid ,  MessageOutContent outMessage)  ;
}
