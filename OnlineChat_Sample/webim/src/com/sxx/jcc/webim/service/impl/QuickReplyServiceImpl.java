package com.sxx.jcc.webim.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sxx.jcc.common.service.BaseService;
import com.sxx.jcc.webim.pojo.QuickReply;
import com.sxx.jcc.webim.service.IQuickReplyService;
@Service
public class QuickReplyServiceImpl extends BaseService implements IQuickReplyService{

	@Override
	public List<QuickReply> findByOrgiAndCreater(Long userId) {
		// TODO Auto-generated method stub
		return null;
	}

}
