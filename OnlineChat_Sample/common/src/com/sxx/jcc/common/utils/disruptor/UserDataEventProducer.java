package com.sxx.jcc.common.utils.disruptor;

import com.lmax.disruptor.RingBuffer;
import com.sxx.jcc.common.utils.event.UserDataEvent;
import com.sxx.jcc.common.utils.event.UserEvent;

public class UserDataEventProducer {
	private final RingBuffer<UserDataEvent> ringBuffer;

    public UserDataEventProducer(RingBuffer<UserDataEvent> ringBuffer)
    {
        this.ringBuffer = ringBuffer;
    }

    public void onData(UserEvent userEvent)
    {
        long id = ringBuffer.next();  // Grab the next sequence
        try{
        	UserDataEvent event = ringBuffer.get(id);
        	event.setEvent(userEvent);
        }finally{
            ringBuffer.publish(id);
        }
    }
}
