package com.sxx.jcc.core.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.sxx.jcc.common.utils.XKDataContext;
import com.sxx.jcc.system.pojo.AgentUser;
import com.sxx.jcc.system.pojo.SessionConfig;
import com.sxx.jcc.webim.pojo.AgentStatus;

@Component
public class SystemCache{
	protected static Logger log = Logger.getLogger(SystemCache.class);
	public static enum CacheServiceEnum{
		AGENT_STATUS_CACHE, 
		QUENE_USER_CACHE,
		ONLINE_CACHE ,   
		API_USER_CACHE , 
		CALLCENTER_CURRENT_CALL ,
		CALLCENTER_AGENT,
		JOB_CACHE,
		HAZLCAST_CALLOUT_CACHE , 
		HAZLCAST_QC_QUEUE;
		public String toString(){
			return super.toString().toLowerCase();
		}
	}
	
	@PostConstruct
	public void loadDataToCache(){
		log.info("Loading....");
		//MemCached.getInstance().getCache().put(CacheServiceEnum.AGENT_STATUS_CACHE.toString(), "Ready");
		SessionConfig sessionconfig = new SessionConfig();
		sessionconfig.setHourcheck(false);
		sessionconfig.setNotinwhmsg("工作时间为09：00-21:00,请谢咨询！");
		sessionconfig.setMaxuser(10);
		MemCached.getInstance().getCache().put(XKDataContext.SYSTEM_CACHE_SESSION_CONFIG+"_"+XKDataContext.SYSTEM_ORGI, sessionconfig);
		SystemCache.put(XKDataContext.SYSTEM_CACHE_CONFIG, "systemConfig");
	}
	
	public static Object fetchCacheDate(String key){
		return MemCached.getInstance().getCache().get(key);
	}
	
	public static void put(String key,Object obj){
		MemCached.getInstance().getCache().put(key,obj);
	}
	
	public static void remove(String key){
		MemCached.getInstance().getCache().remove(key);
	}
	
	public static SessionConfig fetchSessionConfig(){
		Object object = fetchCacheDate(XKDataContext.SYSTEM_CACHE_SESSION_CONFIG+"_"+XKDataContext.SYSTEM_ORGI);
		if (object != null){
			return (SessionConfig)object;
		}
		return null;
		
	}
	
	public static void putSessionConfigCahced(SessionConfig sessionConfig){
		MemCached.getInstance().getCache().put(XKDataContext.SYSTEM_CACHE_SESSION_CONFIG+"_"+XKDataContext.SYSTEM_ORGI, sessionConfig);
	}
	
	public static Map<String,AgentStatus> fetchAgentStatusDate(){
		Object object = MemCached.getInstance().getCache().get(CacheServiceEnum.AGENT_STATUS_CACHE.toString());
		if(object ==null){
			return new HashMap<String,AgentStatus>();
		}else{
			return (HashMap<String,AgentStatus>)object;
		}
	}
	
	public static void putAgentStatus(AgentStatus agentStatus){
		Map<String,AgentStatus> agentStatusCached = fetchAgentStatusDate();
		agentStatusCached.put(agentStatus.getAgentno(),agentStatus);
		MemCached.getInstance().getCache().put(CacheServiceEnum.AGENT_STATUS_CACHE.toString(), agentStatusCached);
	}
	
	public static void removeAgentStatus(String agentno){
		Map<String,AgentStatus> agentStatusCached = fetchAgentStatusDate();
		agentStatusCached.remove(agentno);
		MemCached.getInstance().getCache().put(CacheServiceEnum.AGENT_STATUS_CACHE.toString(), agentStatusCached);
	}
	
	public static AgentStatus getCachedAgentStatus(String agentno){
		Map<String,AgentStatus> agentStatusCached = fetchAgentStatusDate();
		return agentStatusCached.get(agentno);
	}
	
	public static Map<String,AgentUser> fetchAllAgentUser(){
		Object object = MemCached.getInstance().getCache().get(CacheServiceEnum.QUENE_USER_CACHE.toString());
		if(object ==null){
			return new HashMap<String,AgentUser>();
		}else{
			return (HashMap<String,AgentUser>)object;
		}
	}
	
	
	public static void putAgentUser(AgentUser agentUser){
		Map<String,AgentUser> agentUsersCached = fetchAllAgentUser();
		agentUsersCached.put(agentUser.getUserid(),agentUser);
		MemCached.getInstance().getCache().put(CacheServiceEnum.QUENE_USER_CACHE.toString(), agentUsersCached);
	}
	
	public static void removeAgentUser(AgentUser agentUser){
		Map<String,AgentUser> agentUsersCached = fetchAllAgentUser();
		agentUsersCached.remove(agentUser.getUserid());
		MemCached.getInstance().getCache().put(CacheServiceEnum.QUENE_USER_CACHE.toString(), agentUsersCached);
	}
	
	public static AgentUser getCachedAgentUser(String userid){
		Map<String,AgentUser> agentUsersCached = fetchAllAgentUser();
		return agentUsersCached.get(userid);
	}
	
	public static void refreshCache(String dataType, String objId) {
		if (StringUtils.isBlank(dataType) || StringUtils.isBlank(objId)) {
			return;
		}
		/*if (dataType.equals("SYS_METADATA")) {
			MetadataInit.loadMetadata2Cache();
		}*/

	}
	
}
