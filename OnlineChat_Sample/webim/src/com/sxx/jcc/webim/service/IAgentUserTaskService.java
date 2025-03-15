package com.sxx.jcc.webim.service;

import java.util.List;

import com.sxx.jcc.common.service.IBaseService;
import com.sxx.jcc.webim.pojo.AgentUserTask;

public interface IAgentUserTaskService extends IBaseService{
	public List<AgentUserTask> findAgentUserId(String agentUserid);
	public AgentUserTask getAgentUserTask(String agentUserid);
}
