package com.sxx.jcc.core.cache;


public interface CacheInstance {
	/**
	 * 坐席状态
	 * @return
	 */
	public CacheBean getAgentStatusCacheBean() ;
	
	
	/**
	 * 服务中用户
	 * @return
	 */
	public CacheBean getAgentUserCacheBean();
	
	
	/**
	 * 在线用户
	 * @return
	 */
	public CacheBean getOnlineCacheBean();
	
	/**
	 * 系统缓存
	 * @return
	 */
	public CacheBean getSystemCacheBean();
	
	
	/**
	 * IMR指令
	 * @return
	 */
	public CacheBean getIMRCacheBean();
	
	/**
	 * IMR指令
	 * @return
	 */
	public CacheBean getCallCenterCacheBean();
	
	/**
	 * IMR指令
	 * @return
	 */
	public CacheBean getCallCenterAgentCacheBean();
	
	/**
	 * IMR指令
	 * @return
	 */
	public CacheBean getApiUserCacheBean();
	
	/**
	 * IMR指令
	 * @return
	 */
	public CacheBean getJobCacheBean();
	
	/**
	 * 外呼
	 * @return
	 */
	public CacheBean getCallOutCacheBean();
	
	
	/**
	 * QC队列
	 * @return
	 */
	public CacheBean getQcQueueBean() ;
	
}