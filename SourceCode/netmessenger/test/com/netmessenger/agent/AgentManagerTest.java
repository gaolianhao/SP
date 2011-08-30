package com.netmessenger.agent;

import java.io.InputStream;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.netmessenger.core.IAgent;
import com.netmessenger.core.recipientprofile.RecipientAge;
import com.netmessenger.core.recipientprofile.RecipientGender;
import com.netmessenger.core.recipientprofile.RecipientJob;

public class AgentManagerTest {

	@Test
	public void shouldReadAgentInfoFromAgentSourceFile() {
		InputStream inputStream = this.getClass().getResourceAsStream("/testagents.xml");
		AgentManager agentManager = new AgentManager(inputStream);
		List<IAgent> agents = agentManager.findAgent(null);
		Assert.assertNotNull(agents);
		Assert.assertEquals(1, agents.size());
		Agent agent = (Agent)agents.get(0);
		Assert.assertEquals(RecipientAge.YOUTH, agent.getRecipientAge());
		Assert.assertEquals(RecipientJob.LIBERTY, agent.getRecipientJob());
		Assert.assertEquals(RecipientGender.FEMALE, agent.getRecipientGender());
		Assert.assertEquals(AgentTest.class, agent.getClass());
	}

}
