package com.netmessenger.message;

import com.netmessenger.core.IMessage;
import com.netmessenger.core.recipientprofile.RecipientAge;
import com.netmessenger.core.recipientprofile.RecipientGender;
import com.netmessenger.core.recipientprofile.RecipientJob;

public class Message implements IMessage {

	private RecipientAge recipientAge;
	private RecipientJob recipientJob;
	private RecipientGender recipientGender;
	private String content = null;
	
	public Message(RecipientAge recipientAge, RecipientJob recipientJob,
			RecipientGender recipientGender, String content) {
		this.recipientAge = recipientAge;
		this.recipientGender = recipientGender;
		this.recipientJob = recipientJob;
		this.content = content;
	}

	@Override
	public String GetContent() {

		return this.content;
	}

	@Override
	public RecipientAge getRecipientAge() {
		return this.recipientAge;
	}

	@Override
	public RecipientGender getRecipientGender() {
		
		return this.recipientGender;
	}

	@Override
	public RecipientJob getRecipientJob() {
		
		return this.recipientJob;
	}

}
