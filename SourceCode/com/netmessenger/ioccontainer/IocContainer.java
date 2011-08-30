package com.netmessenger.ioccontainer;

import com.netmessenger.agent.AgentManager;
import com.netmessenger.core.IAgentManager;
import com.netmessenger.core.IMessageManager;
import com.netmessenger.message.MessageManager;

public class IocContainer {
	public static IocContainer INSTANCE = new IocContainer();
	
	public IMessageManager getMessageManager(){
		return new MessageManager(this.getClass().getResourceAsStream("/messages.xml"));
	}
	
	public IAgentManager getAgentManager(){
		return new AgentManager(this.getClass().getResourceAsStream("/agents.xml"));
	}
}
