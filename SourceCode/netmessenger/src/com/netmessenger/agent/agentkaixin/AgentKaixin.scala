package com.netmessenger.agent.agentkaixin;

import com.netmessenger.agent.Agent
import com.netmessenger.core.IMessage
import com.netmessenger.database.DBConnection
import com.netmessenger.agent.agentkaixin.datastore.RecipientInfoDAO
import com.netmessenger.agent.agentkaixin.datastore.IRecipientInfoDAO

class AgentKaixin extends Agent with TFuelAgent with TDeliverMessage {

  override def fuelAgent() : Unit = {
    val con = new DBConnection();
    val dao = new RecipientInfoDAO(con.getConnection()).asInstanceOf[IRecipientInfoDAO];
    this.fuelAgent(dao);
    con.getConnection().close();
  }
  
  override def deliverMessage(message : IMessage) : Unit = {
    val con = new DBConnection();
    val dao = new RecipientInfoDAO(con.getConnection());
    this.deliverMessage(message,dao);
    con.getConnection().close();
  }
}