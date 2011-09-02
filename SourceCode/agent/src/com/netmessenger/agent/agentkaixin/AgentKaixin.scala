package com.netmessenger.agent.agentkaixin;


import com.netmessenger.agent.agentkaixin.datastore.IRecipientInfoDAO
import com.netmessenger.agent.agentkaixin.datastore.RecipientInfoDAO
import com.netmessenger.agent.base.Agent
import com.netmessenger.core.IMessage
import com.netmessenger.database.DBConnection
import com.netmessenger.agent.agentkaixin.datastore.RecipientInfoDBMaintance
import java.sql.Connection
import java.util.Properties
import java.nio.charset.Charset

class AgentKaixin extends Agent with TFuelAgent with TDeliverMessage {
 
  override def fuelAgent() : Unit = {
    logger.info("fuel agent kaixin");
    var con = DBConnection.getConnection();
    val dao = new RecipientInfoDAO(con).asInstanceOf[IRecipientInfoDAO];
    
    this.fuelAgent(dao,this.properties);
    con.close();
  }
  
  override def deliverMessage(message : IMessage) : Unit = {
    logger.info("agent kaixin deliver message");
    var con = DBConnection.getConnection();
    val dao = new RecipientInfoDAO(DBConnection.getConnection());
    this.deliverMessage(message,dao,this.properties);
    con.close();
  }
  
  override def prepareRunningEnvironment():Unit = {
    logger.info("prepare agent kaixin emvironment");
    var con = DBConnection.getConnection();
    val dbm = new RecipientInfoDBMaintance(con);
    dbm.clearTable();
    dbm.createTable();
  }
  
  def properties: Properties = {
    var charset = Charset.defaultCharset();
    logger.info("JVM charset : " + charset.name());
    
    val prop = new Properties();
    prop.load(this.getClass().getClassLoader().getResourceAsStream("agent_kaixin.properties"));
    
    logger.info("------------propertes loaded");
    var it = prop.stringPropertyNames().iterator()
    while(it.hasNext())
    {
      var key = it.next();
      logger.info(key + ":" + prop.getProperty(key));
    }
    
    return prop;
    
  }
}