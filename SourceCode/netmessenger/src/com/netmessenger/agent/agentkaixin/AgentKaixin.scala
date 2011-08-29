package com.netmessenger.agent.agentkaixin;

import com.netmessenger.recipient.RecipientInfoDAO
import com.netmessenger.agent.Agent
import com.netmessenger.core.IMessage

class AgentKaixin(name : String) extends Agent(name) with TFuelAgent with TDeliverMessage {

  override def fuelAgent() : Unit = {
    val dao = new RecipientInfoDAO(name);
    this.fuelAgent(dao);
  }
  
  override def deliverMessage(message : IMessage) : Unit = {
    val dao = new RecipientInfoDAO(name);
    this.deliverMessage(message,dao);
  }
}