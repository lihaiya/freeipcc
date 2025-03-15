package com.sxx.jcc.webim.service;

import java.util.List;

import com.sxx.jcc.common.service.IBaseService;
import com.sxx.jcc.webim.pojo.WorkMonitor;

public interface IWorkMonitorService extends IBaseService{
	public int countByAgentAndDatestrAndStatusAndOrgi(String wcode, String date, String agentStatusEnum, String orgi);
	List<WorkMonitor> findByOrgiAndAgentAndDatestrAndFirsttime(String orgi , String wcode , String date);
}
