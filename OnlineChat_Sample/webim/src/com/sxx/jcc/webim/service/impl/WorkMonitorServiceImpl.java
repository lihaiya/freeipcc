package com.sxx.jcc.webim.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sxx.jcc.common.service.BaseService;
import com.sxx.jcc.webim.pojo.WorkMonitor;
import com.sxx.jcc.webim.service.IWorkMonitorService;
@Service
public class WorkMonitorServiceImpl extends BaseService implements IWorkMonitorService{

	@Override
	public int countByAgentAndDatestrAndStatusAndOrgi(String wcode, String date, String agentStatusEnum, String orgi) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<WorkMonitor> findByOrgiAndAgentAndDatestrAndFirsttime(String orgi, String wcode, String date) {
		// TODO Auto-generated method stub
		return null;
	}

}
