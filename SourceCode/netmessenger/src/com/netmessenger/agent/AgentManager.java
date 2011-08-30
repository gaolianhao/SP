package com.netmessenger.agent;

import java.io.InputStream;

import com.netmessenger.core.IAgent;
import com.netmessenger.core.IAgentManager;
import com.netmessenger.core.IMessage;

public class AgentManager implements IAgentManager {

	private InputStream agentSource;
	
	public AgentManager(InputStream agentSource){
		this.agentSource = agentSource;
	}
	
	public IAgent findAgent(IMessage message) {
		
		return new Agent();
	}

}
