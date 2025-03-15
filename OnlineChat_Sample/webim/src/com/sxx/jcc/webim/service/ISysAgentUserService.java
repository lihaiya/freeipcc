package com.sxx.jcc.webim.service;

import java.util.List;

import com.sxx.jcc.common.service.IBaseService;
import com.sxx.jcc.core.dao.hibernate.Page;
import com.sxx.jcc.system.pojo.AgentUser;

public interface ISysAgentUserService  extends IBaseService{
	public AgentUser  findAgentUserById(String id);
	public AgentUser  findAgentUserByuserId(String userid);
	public List<AgentUser> findByAgentnoAndStatusAndOrgi(String wcode,String agentUserStatus);
	public Page findAgentUsersPage(String startdate,String enddate,Page page) ;
}
