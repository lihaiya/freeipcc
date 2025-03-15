package com.sxx.jcc.webim.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.sxx.jcc.common.service.BaseService;
import com.sxx.jcc.webim.pojo.AgentUserTask;
import com.sxx.jcc.webim.service.IAgentUserTaskService;

@Service
public class AgentUserTaskServiceImpl extends BaseService implements IAgentUserTaskService{

	@Override
	public List<AgentUserTask> findAgentUserId(String agentUserid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AgentUserTask getAgentUserTask(String agentUserid) {
		try{
			String sql =" select * from xk_agentuser where id =?";
			List paramList = new ArrayList();
			paramList.add(agentUserid);
			return this.queryForObject(sql, AgentUserTask.class, paramList.toArray());
		}catch(Exception e){
			return null;
		}
	}

	
}
