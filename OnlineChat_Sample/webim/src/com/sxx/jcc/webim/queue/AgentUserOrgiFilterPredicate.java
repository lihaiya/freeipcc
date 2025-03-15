package com.sxx.jcc.webim.queue;

import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.hazelcast.query.Predicate;
import com.sxx.jcc.system.pojo.AgentUser;
/**
 * @author liuyonghong
 *
 */
public class AgentUserOrgiFilterPredicate implements Predicate<String, AgentUser> {

	private static final long serialVersionUID = 1236581634096258855L;
	private String orgi;
	private String status;

	public AgentUserOrgiFilterPredicate() {
	}

	public AgentUserOrgiFilterPredicate(String orgi, String status) {
		this.orgi = orgi;
		this.status = status;
	}

	@Override
	public boolean apply(Map.Entry<String, AgentUser> mapEntry) {
		return mapEntry.getValue()!=null && mapEntry.getValue().getStatus()!=null && !StringUtils.isBlank(orgi) && orgi.equals(mapEntry.getValue().getOrgi()) && mapEntry.getValue().getStatus()!=null && mapEntry.getValue().getStatus().equals(status);
	}
}
