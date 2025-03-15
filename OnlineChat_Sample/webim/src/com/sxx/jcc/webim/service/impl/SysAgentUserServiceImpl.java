package com.sxx.jcc.webim.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.sxx.jcc.common.service.BaseService;
import com.sxx.jcc.core.dao.hibernate.Page;
import com.sxx.jcc.system.pojo.AgentUser;
import com.sxx.jcc.system.pojo.CallDetail;
import com.sxx.jcc.webim.service.ISysAgentUserService;

@Service
public class SysAgentUserServiceImpl extends BaseService implements ISysAgentUserService{

	@Override
	public AgentUser findAgentUserById(String id) {
		List paramList = new ArrayList();
		String sql="select * from xk_agentuser where id=?";
		paramList.add(id);
		return this.queryForObject(sql, AgentUser.class, paramList.toArray());
	}

	@Override
	public List<AgentUser> findByAgentnoAndStatusAndOrgi(String wcode, String agentUserStatus) {
		List paramList = new ArrayList();
		String sql =" select ag.id,au.username,au.agentno,au.userid,au.createtime,ag.status";
		sql += " from (select distinct username,agentno,userid,(select max(createtime) from xk_agentuser a where a.userid=xa.userid and  agentno=? and status!='end') createtime  from xk_agentuser xa  ) au,xk_agentuser ag ";
		sql+= " where ag.agentno=? and au.agentno=ag.agentno and au.createtime=ag.createtime and au.userid= ag.userid";
		paramList.add(wcode);
		paramList.add(wcode);
		if(StringUtils.isNotBlank(agentUserStatus)){
			sql+=" and ag.status=?";
			paramList.add(agentUserStatus);
		}
		sql+= " order by createtime desc";
		return this.queryForObjectList(sql, AgentUser.class, paramList.toArray());
	}

	@Override
	public Page findAgentUsersPage(String startdate,String enddate,Page page) {
		List paramList = new ArrayList();
		String sql = "select a.*,s.name agentname,to_char(a.logindate,'yyyy/MM/dd hh24:mi:ss') start_time,to_char(a.lastmessage,'yyyy/MM/dd hh24:mi:ss') end_time from xk_agentuser a,sys_staff s where a.agentno = s.wcode(+) ";
		//来电时间起
		if(StringUtils.isNotBlank(startdate)){
			sql += " and a.LOGINDATE >=  to_date('"+startdate+"','yyyy-MM-dd hh24:mi:ss')";
		}
		//来电时间止
		if(StringUtils.isNotBlank(enddate)){
			sql += " and a.LOGINDATE <=  to_date('"+enddate+"','yyyy-MM-dd hh24:mi:ss')";
		}
		sql += " order by a.logindate desc";
		return this.queryForMapPage(sql, page, paramList.toArray());
	}
	
	@Override
	public AgentUser findAgentUserByuserId(String userid) {
		List paramList = new ArrayList();
		String sql="select * from xk_agentuser where userid=? order by createtime desc";
		paramList.add(userid);
		List<AgentUser> agentuserlist = this.queryForObjectList(sql, AgentUser.class, paramList.toArray());
		AgentUser agentUser = null;
		if(CollectionUtils.isNotEmpty(agentuserlist) && agentuserlist.size()>0){
			agentUser = agentuserlist.get(0);
		}
		return agentUser;
	}

}
