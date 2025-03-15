package com.sxx.jcc.webim.rpc;

import com.hazelcast.core.Message;
import com.hazelcast.core.MessageListener;
public class AgentTopicListener implements MessageListener<Object>{
	@Override
    public void onMessage(Message<Object> message) {
    }
}
