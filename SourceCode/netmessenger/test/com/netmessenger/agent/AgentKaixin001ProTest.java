package com.netmessenger.agent;

import org.junit.Test;

import com.netmessenger.core.recipientprofile.RecipientAge;
import com.netmessenger.core.recipientprofile.RecipientGender;
import com.netmessenger.core.recipientprofile.RecipientJob;
import com.netmessenger.message.Message;

public class AgentKaixin001ProTest {

	@Test
	public void test() {
		AgentKaixin001Pro agentKaixin001 = new AgentKaixin001Pro("kaixin", null);
		Message message = new Message
				(RecipientAge.ALL,
				RecipientJob.ALL,
				RecipientGender.ALL,
				"你好！");
		agentKaixin001.deliverMessage(message);
	}

}
