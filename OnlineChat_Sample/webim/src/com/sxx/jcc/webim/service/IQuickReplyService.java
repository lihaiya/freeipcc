package com.sxx.jcc.webim.service;

import java.util.List;

import com.sxx.jcc.common.service.IBaseService;
import com.sxx.jcc.webim.pojo.QuickReply;

public interface IQuickReplyService extends IBaseService{
	public List<QuickReply> findByOrgiAndCreater(Long userId);
}
