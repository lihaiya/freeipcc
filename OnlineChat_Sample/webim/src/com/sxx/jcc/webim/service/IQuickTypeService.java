package com.sxx.jcc.webim.service;

import java.util.List;

import com.sxx.jcc.common.service.IBaseService;
import com.sxx.jcc.webim.pojo.QuickType;

public interface IQuickTypeService extends IBaseService{
	public List<QuickType> findByOrgiAndQuicktype(String quickType);
	public List<QuickType> findByOrgiAndQuicktypeAndCreater(String quickType,Long staffId);
}
