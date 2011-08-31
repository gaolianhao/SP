package com.netmessenger.core;

import com.netmessenger.core.recipientprofile.RecipientAge;
import com.netmessenger.core.recipientprofile.RecipientGender;
import com.netmessenger.core.recipientprofile.RecipientJob;

public interface IMessage {
	RecipientAge getRecipientAge();
	RecipientGender getRecipientGender();
	RecipientJob getRecipientJob();
	String getContent();
}
