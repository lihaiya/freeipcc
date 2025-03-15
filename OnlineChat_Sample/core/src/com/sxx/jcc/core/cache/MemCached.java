package com.sxx.jcc.core.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alisoft.xplatform.asf.cache.ICacheManager;
import com.alisoft.xplatform.asf.cache.IMemcachedCache;
import com.alisoft.xplatform.asf.cache.memcached.CacheUtil;
import com.alisoft.xplatform.asf.cache.memcached.MemcachedCacheManager;

public class MemCached {

	protected final static Logger logger = LoggerFactory.getLogger(MemCached.class);

	private static MemCached instance;

	private static ICacheManager<IMemcachedCache> manager;

	private static IMemcachedCache cache;

	private MemCached() {
		manager = CacheUtil.getCacheManager(IMemcachedCache.class, MemcachedCacheManager.class.getName());
		manager.setConfigFile("memcached.xml");
		manager.setResponseStatInterval(0);
		manager.start();
		cache = manager.getCache("mclient0");
	}

	public static synchronized MemCached getInstance() {
		if (instance == null) {
			try {
				instance = new MemCached();
			} catch (Exception e) {
				logger.error(e.toString());
			}
		}
		return instance;
	}

	public IMemcachedCache getCache() {
		return cache;
	}

}
