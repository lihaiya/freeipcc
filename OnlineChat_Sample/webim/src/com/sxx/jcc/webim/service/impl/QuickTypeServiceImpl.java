package com.sxx.jcc.webim.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sxx.jcc.common.service.BaseService;
import com.sxx.jcc.webim.pojo.QuickType;
import com.sxx.jcc.webim.service.IQuickTypeService;
@Service
public class QuickTypeServiceImpl extends BaseService implements IQuickTypeService{

	@Override
	public List<QuickType> findByOrgiAndQuicktype(String quickType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<QuickType> findByOrgiAndQuicktypeAndCreater(String quickType, Long staffId) {
		// TODO Auto-generated method stub
		return null;
	}


}
