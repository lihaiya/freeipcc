package com.sxx.jcc.webim.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.sxx.jcc.common.service.BaseService;
import com.sxx.jcc.webim.pojo.AgentStatus;
import com.sxx.jcc.webim.service.IAgentStatusService;
@Service
public class AgentStatusServiceImpl extends BaseService implements IAgentStatusService{

	@Override
	public List<AgentStatus> findByAgentnoAndOrgi(String wcode) {
		if (StringUtils.isBlank(wcode)) {
			return null;
		} else {
			String sql = "select * from xk_agentstatus where agentno = ? ";
			List paramList = new ArrayList();
			paramList.add(wcode);
			return this.queryForObjectList(sql, AgentStatus.class,paramList.toArray());
		}
	}

	@Override
	public int countByAgentnoAndStatusAndOrgi(Long staffId, String agentUserStatus) {
		// TODO Auto-generated method stub
		return 0;
	}


}
