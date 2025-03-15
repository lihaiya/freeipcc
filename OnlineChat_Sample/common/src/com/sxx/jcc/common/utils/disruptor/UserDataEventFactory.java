package com.sxx.jcc.common.utils.disruptor;

import com.lmax.disruptor.EventFactory;
import com.sxx.jcc.common.utils.event.UserDataEvent;
public class UserDataEventFactory implements EventFactory<UserDataEvent>{

	@Override
	public UserDataEvent newInstance() {
		return new UserDataEvent();
	}
}
