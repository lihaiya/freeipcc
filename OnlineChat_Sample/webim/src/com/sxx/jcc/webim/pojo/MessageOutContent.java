package com.sxx.jcc.webim.pojo;

import java.util.List;

import com.sxx.jcc.core.server.message.OtherMessageItem;

public class MessageOutContent extends MessageInContent{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<OtherMessageItem> suggest = null ;

	public List<OtherMessageItem> getSuggest() {
		return suggest;
	}

	public void setSuggest(List<OtherMessageItem> suggest) {
		this.suggest = suggest;
	}
}
