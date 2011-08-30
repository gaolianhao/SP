package com.netmessenger.agent.agentkaixin.datastore;

import java.util.List;


public interface IRecipientInfoDAO {

	public abstract boolean isExist(RecipientInfo recipientInfo);

	public abstract void add(RecipientInfo recipientInfo);

	public abstract int countRecipients();

	public List<RecipientInfo> findAll();
	
	public abstract void save();
	
	public abstract void close();

}