package com.netmessenger.agent
import com.netmessenger.core.IMessage

class AgentTest extends Agent{

	override def deliverMessage( message:IMessage) : Unit = {}
	override def fuelAgent() : Unit = {};
}