package com.netmessenger.recipient;

import com.netmessenger.core.recipientprofile.RecipientGender;

public class RecipientInfo {

	String name;
	RecipientGender gender;
	String homePage;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public RecipientGender getGender() {
		return gender;
	}
	public void setGender(RecipientGender gender) {
		this.gender = gender;
	}
	public String getHomePage() {
		return homePage;
	}
	public void setHomePage(String homePage) {
		this.homePage = homePage;
	}
	
	
	
	
}
