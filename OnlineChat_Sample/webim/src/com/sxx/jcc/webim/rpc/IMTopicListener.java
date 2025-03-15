package com.sxx.jcc.webim.rpc;

import com.hazelcast.core.Message;
import com.hazelcast.core.MessageListener;

public class IMTopicListener implements MessageListener<Object>{
	@Override
    public void onMessage(Message<Object> message) {
		RPCDataBean rpcDataBean = (RPCDataBean) message.getMessageObject() ;
	
    }
}
