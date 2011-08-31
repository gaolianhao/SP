package com.netmessenger.agent;

import com.netmessenger.core.IAgent;
import com.netmessenger.core.IMessage;
import com.netmessenger.recipient.RecipientInfoDAO;

public abstract class Agent implements IAgent {

	private String name = null;
	protected RecipientInfoDAO dao;
	
	public Agent(String name){
		this.name = name;
		this.dao = new RecipientInfoDAO(this);
	}
	
	abstract public void deliverMessage(IMessage message);
	
	abstract public void findAndSaveRecipientInfo();
	
	public String getName() {
		return name;
	}


}
