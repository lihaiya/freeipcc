package com.sxx.jcc.webim.config;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.sxx.jcc.common.utils.disruptor.UserDataEventFactory;
import com.sxx.jcc.common.utils.disruptor.UserEventHandler;
import com.sxx.jcc.common.utils.event.UserDataEvent;

@Component
public class DisruptorConfigure {
	@SuppressWarnings({ "unchecked"})
	@Bean(name="disruptor")   
    public Disruptor<UserDataEvent> disruptor() {   
		Executor executor = Executors.newCachedThreadPool();
    	 UserDataEventFactory factory = new UserDataEventFactory();
    	 Disruptor<UserDataEvent> disruptor = new Disruptor<UserDataEvent>(factory, 1024, executor, ProducerType.MULTI , new BlockingWaitStrategy());
    	 disruptor.handleEventsWith(new UserEventHandler());
    	 disruptor.start();
         return disruptor;   
    }   
}
