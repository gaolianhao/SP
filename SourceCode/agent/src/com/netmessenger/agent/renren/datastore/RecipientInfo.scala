package com.netmessenger.agent.renren.datastore;

import com.netmessenger.core.recipientprofile.RecipientAge;
import com.netmessenger.core.recipientprofile.RecipientGender;
import com.netmessenger.core.recipientprofile.RecipientJob;

class RecipientInfo {

	var name = "";
	var gender = RecipientGender.ALL;
	var homePage = "";
	var job = RecipientJob.ALL;
	var age = RecipientAge.ALL;
}
