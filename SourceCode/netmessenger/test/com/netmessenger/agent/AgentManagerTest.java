package com.netmessenger.agent;

import java.io.InputStream;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.netmessenger.core.IAgent;
import com.netmessenger.core.IMessage;

public class AgentManagerTest {

	@Test
	public void shouldReadAgentInfoFromAgentSourceFile() {
		InputStream inputStream = this.getClass().getResourceAsStream("/testagents.xml");
		AgentManager agentManager = new AgentManager(inputStream);
		List<IAgent> agents = agentManager.findAgent(null);
		Assert.assertNotNull(agents);
		Assert.assertEquals(1, agents.size());
		Agent agent = (Agent)agents.get(0);
		Assert.assertEquals("testagent", agent.getName());
		Assert.assertEquals(AgentTest.class, agent.getClass());
	}
	
class AgentTest extends Agent{

	public AgentTest(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void deliverMessage(IMessage message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void findAndSaveRecipientInfo() {
		// TODO Auto-generated method stub
		
	}
	
}
}
