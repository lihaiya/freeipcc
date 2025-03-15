package com.sxx.jcc.webim.rpc;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.MemberAttributeEvent;
import com.hazelcast.core.MembershipEvent;
import com.hazelcast.core.MembershipListener;
import com.sxx.jcc.common.utils.XKTools;

public class ClusterMember implements MembershipListener{

	private HazelcastInstance hazelcastInstance ;
	
	public ClusterMember(HazelcastInstance hazelcastInstance) {
		this.hazelcastInstance = hazelcastInstance ;
	}
	@Override
	public void memberAdded(MembershipEvent membershipEvent) {
		XKTools.voteMaster(hazelcastInstance);
	}

	@Override
	public void memberRemoved(MembershipEvent membershipEvent) {
		XKTools.voteMaster(hazelcastInstance);
	}

	@Override
	public void memberAttributeChanged(MemberAttributeEvent memberAttributeEvent) {
		
	}
}
