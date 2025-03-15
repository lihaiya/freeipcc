package com.sxx.jcc.webim.util.router;

import com.sxx.jcc.webim.pojo.MessageDataBean;


public abstract class Router {
	
	public abstract MessageDataBean handler(MessageDataBean message) ;
	
}
