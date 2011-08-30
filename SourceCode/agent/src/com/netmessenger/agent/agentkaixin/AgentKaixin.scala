package com.netmessenger.agent.agentkaixin;


import com.netmessenger.agent.agentkaixin.datastore.IRecipientInfoDAO
import com.netmessenger.agent.agentkaixin.datastore.RecipientInfoDAO
import com.netmessenger.agent.base.Agent
import com.netmessenger.core.IMessage
import com.netmessenger.database.DBConnection
import com.netmessenger.agent.agentkaixin.datastore.RecipientInfoDBMaintance
import java.sql.Connection

class AgentKaixin extends Agent with TFuelAgent with TDeliverMessage {

  override def fuelAgent() : Unit = {
    var con = DBConnection.getConnection();
    val dao = new RecipientInfoDAO(con).asInstanceOf[IRecipientInfoDAO];
    this.fuelAgent(dao);
    con.close();
  }
  
  override def deliverMessage(message : IMessage) : Unit = {
    var con = DBConnection.getConnection();
    val dao = new RecipientInfoDAO(DBConnection.getConnection());
    this.deliverMessage(message,dao);
    con.close();
  }
  
  override def prepareRunningEnvironment():Unit = {
    var con = DBConnection.getConnection();
    val dbm = new RecipientInfoDBMaintance(con);
    dbm.clearTable();
    dbm.createTable();
  }
}