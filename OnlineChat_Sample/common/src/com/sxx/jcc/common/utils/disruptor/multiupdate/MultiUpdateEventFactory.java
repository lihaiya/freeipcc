package com.sxx.jcc.common.utils.disruptor.multiupdate;

import com.lmax.disruptor.EventFactory;
import com.sxx.jcc.common.utils.event.UserDataEvent;

public class MultiUpdateEventFactory implements EventFactory<UserDataEvent>{

	@Override
	public UserDataEvent newInstance() {
		return new UserDataEvent();
	}
}
