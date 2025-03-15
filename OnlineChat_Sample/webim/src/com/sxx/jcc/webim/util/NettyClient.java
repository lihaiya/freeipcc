package com.sxx.jcc.webim.util;

import java.util.List;

import com.corundumstudio.socketio.SocketIOClient;
import com.google.common.collect.ArrayListMultimap;
import com.sxx.jcc.common.utils.XKTools;

public abstract class NettyClient {
	
	protected ArrayListMultimap<String, SocketIOClient> clientsMap = ArrayListMultimap.create();
	
	public int size() {
		return clientsMap.size() ;
	}
	
	public List<SocketIOClient> getClients(String key){
		return clientsMap.get(key) ;
	}
	
	public void putClient(String key , SocketIOClient client) {
		clientsMap.put(key, client) ;
	}
	
	public void removeClient(String key , String id) {
		List<SocketIOClient> keyClients = this.getClients(key) ;
		for(SocketIOClient client : keyClients){
			if(XKTools.getContextID(client.getSessionId().toString()).equals(id)){
				keyClients.remove(client) ;
				break ;
			}
		}
		if(keyClients.size() == 0){
			clientsMap.removeAll(key) ;
		}
	}
}
