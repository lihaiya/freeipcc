package com.sxx.jcc.core.server;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIONamespace;
import com.corundumstudio.socketio.SocketIOServer;
import com.sxx.jcc.common.utils.XKDataContext;
import com.sxx.jcc.core.server.handler.AgentEventHandler;
import com.sxx.jcc.core.server.handler.IMEventHandler;

@Component("BeanDefineConfigue")
public class ServerRunner implements ApplicationListener<ContextRefreshedEvent>{

    private  SocketIOServer server;
    private  SocketIONamespace imSocketNameSpace ;
    private  SocketIONamespace agentSocketIONameSpace ;
    
    public SocketIONamespace getIMSocketIONameSpace(){
    	//imSocketNameSpace.addListeners(new IMEventHandler(server));
    	return imSocketNameSpace;
    }
    
    
    public SocketIONamespace getAgentSocketIONameSpace(){
    	//agentSocketIONameSpace.addListeners(new AgentEventHandler(server));
    	return agentSocketIONameSpace;
    }
    
	@Override
	public void onApplicationEvent(ContextRefreshedEvent arg0) {
		/**System.out.println("---------------init socket!");
    	Configuration config = new Configuration();
    	config.setHostname("localhost");
    	config.setPort(UKDataContext.getWebIMPort());
    	server  = new SocketIOServer(config);
    	
    	System.out.println("start****************************server**************************end");
        imSocketNameSpace = server.addNamespace(UKDataContext.NameSpaceEnum.IM.getNamespace());
        imSocketNameSpace.addListeners(new IMEventHandler(server));
        agentSocketIONameSpace = server.addNamespace(UKDataContext.NameSpaceEnum.AGENT.getNamespace()) ;
        agentSocketIONameSpace.addListeners(new AgentEventHandler(server));
        server.start(); 
    	UKDataContext.setIMServerStatus(true);	//IMServer 启动成功
        System.out.println("start****************************namespace**************************end");**/
		Configuration config = new Configuration();
        
        //config.setPort(9094);

        config.setHostname("localhost");
		//config.setHostname("10.20.3.200");
        config.setPort(9093);
        
        server = new SocketIOServer(config);
        imSocketNameSpace = server.addNamespace(XKDataContext.NameSpaceEnum.IM.getNamespace());
        imSocketNameSpace.addListeners(new IMEventHandler(server));
        agentSocketIONameSpace = server.addNamespace(XKDataContext.NameSpaceEnum.AGENT.getNamespace());
        agentSocketIONameSpace.addListeners(new AgentEventHandler(server));
        
        /**SocketIONamespace imSocketNameSpace = server.addNamespace(UKDataContext.NameSpaceEnum.IM.getNamespace());
        imSocketNameSpace.addConnectListener(new ConnectListener() {
            @Override
            public void onConnect(SocketIOClient client) {
             client.sendEvent("advert_info","客户端你好，我是服务端，有什么能帮助你");		
                System.out.println("=============server1===========");
            }
        });
        SocketIONamespace agentSocketIONameSpace = server.addNamespace(UKDataContext.NameSpaceEnum.AGENT.getNamespace()) ;
        agentSocketIONameSpace.addConnectListener(new ConnectListener() {
            @Override
            public void onConnect(SocketIOClient client) {
            	System.out.println("+++++++++++++++server2++++++++++++");
            	client.sendEvent("advert_info","客户端你好，我是服务端，有什么能帮助你");	
                
            }
        });**/
        server.start();
        System.out.println("start****************************namespace**************************"+config.getHostname()+":"+config.getPort());
	}
}  