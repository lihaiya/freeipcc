package com.sxx.jcc.webim.util.router;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.sxx.jcc.webim.pojo.MessageOutContent;
import com.sxx.jcc.webim.util.NettyClients;

@Component
public class WebIMOutMessageRouter implements OutMessageRouter{

	@Bean(name="webim")
	public WebIMOutMessageRouter initWebIMessageRouter(){
		return new WebIMOutMessageRouter() ;
	}
	@Override
	public void handler(String touser, String msgtype, String appid,
			MessageOutContent outMessage) {
		NettyClients.getInstance().sendIMEventMessage(touser, msgtype, outMessage);
	}

}
