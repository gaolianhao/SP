package com.netmessenger.agent;

import com.netmessenger.core.IMessage;
import com.netmessenger.core.recipientprofile.RecipientAge;
import com.netmessenger.core.recipientprofile.RecipientGender;
import com.netmessenger.core.recipientprofile.RecipientJob;

public class AgentKaixin001 extends Agent {
	
	public AgentKaixin001(RecipientAge recipientAge, RecipientJob recipientJob,
			RecipientGender recipientGender) {
		super(recipientAge, recipientJob, recipientGender);
	}

	@Override
	public void deliverMessage(IMessage message) {
		

	}

}
