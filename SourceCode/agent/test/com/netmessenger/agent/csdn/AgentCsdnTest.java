package com.netmessenger.agent.csdn;

import org.junit.Test;

import com.netmessenger.core.recipientprofile.RecipientAge;
import com.netmessenger.core.recipientprofile.RecipientGender;
import com.netmessenger.core.recipientprofile.RecipientJob;
import com.netmessenger.message.Message;

public class AgentCsdnTest {

	@Test
	public void testDeliverMessage() {
		AgentCsdn csdn = new AgentCsdn();
		Message message = new Message
				(RecipientAge.ALL,
				RecipientJob.ALL,
				RecipientGender.ALL,
				"顶一个");
		csdn.deliverMessage(message);
	}

}
