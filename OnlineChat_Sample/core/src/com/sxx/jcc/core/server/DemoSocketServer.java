package com.sxx.jcc.core.server;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIONamespace;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.sxx.jcc.common.utils.XKDataContext;
import com.sxx.jcc.webim.pojo.SocketIOMessage;

public class DemoSocketServer {

	public static void main(String[] args) throws InterruptedException {

        Configuration config = new Configuration();
        config.setHostname("localhost");
        config.setPort(9092);

        SocketIOServer server = new SocketIOServer(config);
        SocketIONamespace imSocketNameSpace = server.addNamespace(XKDataContext.NameSpaceEnum.IM.getNamespace());
        imSocketNameSpace.addConnectListener(new ConnectListener() {
            @Override
            public void onConnect(SocketIOClient client) {
            	  System.out.println("========================00000");
            	SocketIOMessage message = new SocketIOMessage();
				message.setMessage("测试");
              
                client.sendEvent(XKDataContext.MessageTypeEnum.STATUS.toString(), message);
            }
        });
        SocketIONamespace agentSocketIONameSpace = server.addNamespace(XKDataContext.NameSpaceEnum.AGENT.getNamespace()) ;
        agentSocketIONameSpace.addConnectListener(new ConnectListener() {
            @Override
            public void onConnect(SocketIOClient client) {
            	SocketIOMessage message = new SocketIOMessage();
				message.setMessage("测2试");
              
                client.sendEvent(XKDataContext.MessageTypeEnum.STATUS.toString(), message);
                System.out.println("+++++++++++++++++++++++++++9999");
            }
        });
        server.start();
        Thread.sleep(Integer.MAX_VALUE);
        server.stop();
    }
}
