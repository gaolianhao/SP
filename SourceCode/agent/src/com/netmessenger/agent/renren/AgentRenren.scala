package com.netmessenger.agent.renren;

import java.util.List
import java.util.NoSuchElementException
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.firefox.FirefoxDriver
import com.netmessenger.agent.base.LiteWebDriver
import com.netmessenger.agent.base.Agent
import com.netmessenger.database.DBConnection
import com.netmessenger.agent.renren.datastore.RecipientInfoDBMaintance
import java.util.Properties
import java.nio.charset.Charset
import com.netmessenger.core.IMessage
import com.netmessenger.agent.renren.datastore.RecipientInfoDAO
import com.netmessenger.agent.renren.datastore.IRecipientInfoDAO

class AgentRenren extends Agent  with TFuelAgent with TDeliverMessage{

  override def deliverMessage(message: IMessage) = {
    logger.info("agent renren deliver message");
    var con = DBConnection.getConnection();
    val dao = new RecipientInfoDAO(DBConnection.getConnection());
    this.deliverMessage(message,dao,this.properties);
    con.close();
  }

  override def fuelAgent = {
    logger.info("fuel agent renren");
	var con = DBConnection.getConnection();
    val dao = new RecipientInfoDAO(con).asInstanceOf[IRecipientInfoDAO];
    
    this.fuelAgent(dao,this.properties);
    con.close();
  }
  
  override def prepareRunningEnvironment():Unit = {
    logger.info("prepare agent renren emvironment");
	var con = DBConnection.getConnection();
    val dbm = new RecipientInfoDBMaintance(con);
    dbm.clearTable();
    dbm.createTable();
  }
  
  
  def properties: Properties = {
    var charset = Charset.defaultCharset();
    logger.info("JVM charset : " + charset.name());
    
    val prop = new Properties();
    prop.load(this.getClass().getClassLoader().getResourceAsStream("agent_renren.properties"));
    
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

