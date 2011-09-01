package com.netmessenger.agent.agentkaixin;

import org.junit.Test;

import com.netmessenger.agent.agentkaixin.datastore.RecipientInfoDBMaintance;
import com.netmessenger.core.recipientprofile.RecipientAge;
import com.netmessenger.core.recipientprofile.RecipientGender;
import com.netmessenger.core.recipientprofile.RecipientJob;
import com.netmessenger.database.DBConnection;
import com.netmessenger.message.Message;

public class AgentKaixinTest {

	@Test
	public void testDeliveryMessage() {
		AgentKaixin agentKaixin001 = new AgentKaixin();
		Message message = new Message
				(RecipientAge.ALL,
				RecipientJob.ALL,
				RecipientGender.FEMALE,
				false,
				"Hello");
		agentKaixin001.deliverMessage(message);
	}
	
	@Test
	public void testFuelAgent() throws Exception{
		AgentKaixin agentKaixin001 = new AgentKaixin();
		agentKaixin001.prepareRunningEnvironment();
		agentKaixin001.fuelAgent();
	}	

}
