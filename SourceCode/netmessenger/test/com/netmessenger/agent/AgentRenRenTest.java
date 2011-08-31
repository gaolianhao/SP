package com.netmessenger.agent;

import static org.junit.Assert.*;

import org.junit.Test;

import com.netmessenger.core.recipientprofile.RecipientAge;
import com.netmessenger.core.recipientprofile.RecipientGender;
import com.netmessenger.core.recipientprofile.RecipientJob;
import com.netmessenger.message.Message;

public class AgentRenRenTest {

	@Test
	public void test() {
		AgentRenren agentRenren = new AgentRenren(RecipientAge.ALL, RecipientJob.ALL, RecipientGender.ALL);
		Message message = new Message
				(RecipientAge.ALL,
				RecipientJob.ALL,
				RecipientGender.ALL,
				"你好！");
		agentRenren.deliverMessage(message);
	}

}
