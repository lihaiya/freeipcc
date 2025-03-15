package com.sxx.jcc.common.utils.event;

public class AiEvent {
	private long id ;

	private UserEvent event ;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public UserEvent getEvent() {
		return event;
	}

	public void setEvent(UserEvent event) {
		this.event = event;
	}
}
