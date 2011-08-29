package com.netmessenger.agent;

import org.junit.Test;

import com.netmessenger.agent.agentkaixin.AgentKaixin;
import com.netmessenger.core.recipientprofile.RecipientAge;
import com.netmessenger.core.recipientprofile.RecipientGender;
import com.netmessenger.core.recipientprofile.RecipientJob;
import com.netmessenger.message.Message;

public class AgentKaixinTest {

	@Test
	public void testDeliveryMessage() {
		AgentKaixin agentKaixin001 = new AgentKaixin("kaixin");
		Message message = new Message
				(RecipientAge.ALL,
				RecipientJob.ALL,
				RecipientGender.ALL,
				"Hello");
		agentKaixin001.deliverMessage(message);
	}
	
	@Test
	public void testFuelAgent() {
		AgentKaixin agentKaixin001 = new AgentKaixin("kaixin");
		agentKaixin001.fuelAgent();
	}	

}
