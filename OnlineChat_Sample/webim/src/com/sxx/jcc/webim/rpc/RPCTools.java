package com.sxx.jcc.webim.rpc;

import com.hazelcast.core.HazelcastInstance;
import com.sxx.jcc.common.utils.XKDataContext;

/**
 * 本来下面的四个方法可以合并成一个，通过传入订阅的主题名称来区分，但是，不同的主题的消息数量和负载不一样，
 * IM的负载和EntIM的负载明显会高于另外两类主题，所以，分开处理的目的是为了将来能够更方便的扩展， 按照负载要求不一样，提供多种订阅模式
 * 和实现方式
 * @author iceworld
 *
 */
public class RPCTools {
	/**
	 * 
	 * @param id
	 * @param event
	 * @param data
	 */
	public static void sendIMEventMessage(String id , String event , Object data){
		XKDataContext.getContext().getBean(HazelcastInstance.class).getTopic(XKDataContext.UCKeFuTopic.TOPIC_IM.toString()).publish(new RPCDataBean(id, event, data));
	}
	/**
	 * 
	 * @param id
	 * @param event
	 * @param data
	 */
	public static void sendEntIMEventMessage(String id , String event , Object data){
		XKDataContext.getContext().getBean(HazelcastInstance.class).getTopic(XKDataContext.UCKeFuTopic.TOPIC_ENTIM.toString()).publish(new RPCDataBean(id, event, data));
	}
	/**
	 * 
	 * @param id
	 * @param event
	 * @param data
	 */
	public static void sendAgentEventMessage(String id , String event , Object data){
		XKDataContext.getContext().getBean(HazelcastInstance.class).getTopic(XKDataContext.UCKeFuTopic.TOPIC_AGENT.toString()).publish(new RPCDataBean(id, event, data));
	}
	/**
	 * 
	 * @param id
	 * @param event
	 * @param data
	 */
	public static void sendCallCenterMessage(String id , String event , Object data){
		XKDataContext.getContext().getBean(HazelcastInstance.class).getTopic(XKDataContext.UCKeFuTopic.TOPIC_CALLCENTER.toString()).publish(new RPCDataBean(id, event, data));
	}
	
	
	/**
	 * 
	 * @param id
	 * @param event
	 * @param data
	 */
	public static void sendJobDetailMessage(String id , String event , Object data){
		XKDataContext.getContext().getBean(HazelcastInstance.class).getTopic(XKDataContext.UCKeFuTopic.TOPIC_JOBDETAIL.toString()).publish(new RPCDataBean(id, event, data));
	}
	
	/**
	 * 
	 * @param id
	 * @param event
	 * @param data
	 */
	public static void published(String name , String event , Object data){
		XKDataContext.getContext().getBean(HazelcastInstance.class).getTopic(XKDataContext.UCKeFuTopic.NAMESPACE.toString()).publish(new RPCDataBean(name, event, data));
	}
}
