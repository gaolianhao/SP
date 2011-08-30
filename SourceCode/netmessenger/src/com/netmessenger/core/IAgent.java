package com.netmessenger.core;

public interface IAgent {
	
	void deliverMessage(IMessage message);
	void fuelAgent();
	void prepareRunningEnvironment();
}
