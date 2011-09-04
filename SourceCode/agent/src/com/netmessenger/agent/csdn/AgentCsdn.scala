package com.netmessenger.agent.csdn
import com.netmessenger.agent.base.Agent
import java.util.Properties
import java.nio.charset.Charset
import com.netmessenger.core.IMessage

class AgentCsdn extends Agent with TDeliverMessage {

  override def deliverMessage(message: IMessage) = {
    logger.info("agent csdn deliver message");
    this.deliverMessage(message, this.properties);
  }

  override def fuelAgent = {
    logger.info("fuel agent csdn");
    logger.info("Nothing needs to be done!");
  }

  override def prepareRunningEnvironment(): Unit = {
    logger.info("prepare agent csdn emvironment");
    logger.info("Nothing needs to be done!");
  }

  def properties: Properties = {
    var charset = Charset.defaultCharset();
    logger.info("JVM charset : " + charset.name());

    val prop = new Properties();
    prop.load(this.getClass().getClassLoader().getResourceAsStream("agent_csdn.properties"));

    logger.info("------------propertes loaded");
    var it = prop.stringPropertyNames().iterator()
    while (it.hasNext()) {
      var key = it.next();
      logger.info(key + ":" + prop.getProperty(key));
    }

    return prop;

  }
}