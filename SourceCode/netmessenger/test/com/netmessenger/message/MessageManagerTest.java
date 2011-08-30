package com.netmessenger.message;

import org.junit.Assert;
import org.junit.Test;

import com.netmessenger.core.IMessage;
import com.netmessenger.core.recipientprofile.RecipientAge;
import com.netmessenger.core.recipientprofile.RecipientGender;
import com.netmessenger.core.recipientprofile.RecipientJob;

public class MessageManagerTest {

	@Test
	public void testFindMessage() {
		MessageManager messageManager = new MessageManager(this.getClass().getResourceAsStream("/testmessages.xml"));
		IMessage message = messageManager.findMessage();
		Assert.assertNotNull(message);
		Assert.assertEquals(RecipientAge.SENIOR, message.getRecipientAge());
		Assert.assertEquals(RecipientJob.STUDENT, message.getRecipientJob());
		Assert.assertEquals(RecipientGender.MALE, message.getRecipientGender());
		Assert.assertEquals("Hello", message.GetContent());
	}

}
