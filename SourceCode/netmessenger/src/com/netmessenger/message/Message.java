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
	private Boolean autoDecor = false;
	
	public Message(RecipientAge recipientAge, RecipientJob recipientJob,
			RecipientGender recipientGender, String content) {
		this.recipientAge = recipientAge;
		this.recipientGender = recipientGender;
		this.recipientJob = recipientJob;
		this.content = content;
	}
	public Message(RecipientAge recipientAge, RecipientJob recipientJob,
			RecipientGender recipientGender, Boolean autoDecor, String content) {
		this.recipientAge = recipientAge;
		this.recipientGender = recipientGender;
		this.recipientJob = recipientJob;
		this.autoDecor = autoDecor;
		this.content = content;
	}

	@Override
	public String getContent() {

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

	@Override
	public Boolean autoDecor() {
		// TODO Auto-generated method stub
		return autoDecor;
	}

}
