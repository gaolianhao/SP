package com.netmessenger.agent.agentkaixin;

import com.netmessenger.recipient.RecipientInfoDAO
import com.netmessenger.agent.Agent

class AgentKaixin(name : String) extends Agent(name) with TFuelAgent with TDeliverMessage {

  override def fuelAgent() : Unit = {
    val dao = new RecipientInfoDAO(name);
    this.fuelAgent(dao);
  }
}