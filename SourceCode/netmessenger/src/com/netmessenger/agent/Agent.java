package com.netmessenger.agent;

import com.netmessenger.core.IAgent;
import com.netmessenger.core.IMessage;
import com.netmessenger.core.recipientprofile.RecipientAge;
import com.netmessenger.core.recipientprofile.RecipientGender;
import com.netmessenger.core.recipientprofile.RecipientJob;

public abstract class Agent implements IAgent {

	private RecipientAge recipientAge;
	private RecipientJob recipientJob;
	private RecipientGender recipientGender;
	
	public Agent(RecipientAge recipientAge, RecipientJob recipientJob,
			RecipientGender recipientGender) {
		this.recipientAge = recipientAge;
		this.recipientGender = recipientGender;
		this.recipientJob = recipientJob;
	}

	abstract public void deliverMessage(IMessage message);

	public RecipientAge getRecipientAge() {
		return recipientAge;
	}


	public RecipientJob getRecipientJob() {
		return recipientJob;
	}


	public RecipientGender getRecipientGender() {
		return recipientGender;
	}

}
