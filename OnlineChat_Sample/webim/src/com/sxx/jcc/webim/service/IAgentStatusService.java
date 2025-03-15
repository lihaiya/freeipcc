package com.sxx.jcc.webim.service;

import java.util.List;

import com.sxx.jcc.common.service.IBaseService;
import com.sxx.jcc.webim.pojo.AgentStatus;

public interface IAgentStatusService extends IBaseService{
	public List<AgentStatus> findByAgentnoAndOrgi(String wcode);
	public int countByAgentnoAndStatusAndOrgi(Long staffId, String agentUserStatus);
}
