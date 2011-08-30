package com.netmessenger.agent.agentkaixin;

import org.junit.Test;

import com.netmessenger.core.recipientprofile.RecipientAge;
import com.netmessenger.core.recipientprofile.RecipientGender;
import com.netmessenger.core.recipientprofile.RecipientJob;
import com.netmessenger.message.Message;

public class AgentKaixinTest {

	@Test
	public void testDeliveryMessage() {
		AgentKaixin agentKaixin001 = new AgentKaixin();
		Message message = new Message
				(RecipientAge.ALL,
				RecipientJob.ALL,
				RecipientGender.ALL,
				"Hello");
		agentKaixin001.deliverMessage(message);
	}
	
	@Test
	public void testFuelAgent() {
		AgentKaixin agentKaixin001 = new AgentKaixin();
		agentKaixin001.fuelAgent();
	}	

}
