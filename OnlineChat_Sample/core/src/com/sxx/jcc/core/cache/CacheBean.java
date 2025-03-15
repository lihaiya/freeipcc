package com.sxx.jcc.core.cache;

import java.util.Collection;
import java.util.concurrent.locks.Lock;

import com.hazelcast.internal.json.JsonObject;


public interface CacheBean {
	/**
	 * 
	 */
	public void put(String key , Object value , String orgi) ;
	

	/**
	 * 
	 * @param key
	 * @param orgi
	 * @return
	 */
	public Object getCacheObject(String key, String orgi) ;
	
	
	public CacheBean getCacheInstance(String cacheName);
	
	
	
}
