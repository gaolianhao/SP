package com.netmessenger.core;

import java.util.List;

public interface IAgentManager {

	List<IAgent> findAgent(IMessage message);
}
