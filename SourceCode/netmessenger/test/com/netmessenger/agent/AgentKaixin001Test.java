package com.netmessenger.agent;

import org.junit.Test;

import com.netmessenger.core.recipientprofile.RecipientAge;
import com.netmessenger.core.recipientprofile.RecipientGender;
import com.netmessenger.core.recipientprofile.RecipientJob;
import com.netmessenger.message.Message;

public class AgentKaixin001Test {

	@Test
	public void test() {
		AgentKaixin001 agentKaixin001 = new AgentKaixin001("kaixin");
		Message message = new Message
				(RecipientAge.ALL,
				RecipientJob.ALL,
				RecipientGender.ALL,
				"Hello");
		agentKaixin001.deliverMessage(message);
	}

}
