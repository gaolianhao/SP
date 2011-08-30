package com.netmessenger.agent.agentkaixin.datastore;

import com.netmessenger.core.recipientprofile.RecipientAge;
import com.netmessenger.core.recipientprofile.RecipientGender;
import com.netmessenger.core.recipientprofile.RecipientJob;

public class RecipientInfo {

	String name;
	RecipientGender gender = RecipientGender.ALL;
	String homePage;
	RecipientJob job = RecipientJob.ALL;
	RecipientAge age = RecipientAge.ALL;
	
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
	public RecipientJob getJob() {
		return job;
	}
	public void setJob(RecipientJob job) {
		this.job = job;
	}
	public RecipientAge getAge() {
		return age;
	}
	public void setAge(RecipientAge age) {
		this.age = age;
	}
	
	
	
}
